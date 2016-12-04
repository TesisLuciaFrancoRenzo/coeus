/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.coeus.tdlearning.training;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.interfaces.IProblemToTrain;
import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStatePerceptron;
import ar.edu.unrc.coeus.tdlearning.training.perceptrons.Layer;
import ar.edu.unrc.coeus.tdlearning.training.perceptrons.NeuralNetworkCache;
import ar.edu.unrc.coeus.tdlearning.training.perceptrons.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Algoritmos de entrenamiento para redes neuronales genéricas.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TDTrainerPerceptron
        extends Trainer {

    /**
     * Red neuronal a entrenar.
     */
    private final INeuralNetworkInterface        neuralNetwork;
    private       double[]                       alpha;
    private       boolean[]                      concurrencyInLayer;
    /**
     * <ul>
     * <li>Primer componente: índice de la capa de la neurona.
     * <li>Segunda componente: índice de la neurona.
     * <li>Tercera componente: índice de la segunda neurona involucrada en el calculo del peso.
     * <li>Cuarta componente: índice de la neurona de salida con respecto de la cual se esta actualizando el peso.
     * <li>Quinta componente: turno (m) de la traza de elegibilidad.
     * </ul>
     */
    private       List<List<List<List<Double>>>> eligibilityTraces;
    /**
     * Indica si es el primer turno.
     */
    private       boolean                        firstTurn;
    private       List<Double>                   nextTurnOutputs;
    private       NeuralNetworkCache             nextTurnStateCache;
    /**
     * Problema a solucionar.
     */
    private       IProblemToTrain                problem;
    /**
     * Vector de errores TD para la capa de salida, comparando el turno actual con el siguiente
     */
    private       List<Double>                   tDError;
    /**
     * Cache utilizada para reciclar cálculos y soluciones previas.
     */
    private       NeuralNetworkCache             turnCurrentStateCache;

    /**
     * @param lambda        escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param gamma         tasa de descuento, entre [0,1].
     * @param neuralNetwork red neuronal a entrenar.
     */
    public
    TDTrainerPerceptron(
            final INeuralNetworkInterface neuralNetwork,
            final double lambda,
            final double gamma
    ) {
        this.neuralNetwork = neuralNetwork;
        firstTurn = true;
        eligibilityTraces = null;
        nextTurnStateCache = null;
        turnCurrentStateCache = null;
        this.lambda = lambda;
        this.gamma = gamma;
        if (lambda > 0) {
            createEligibilityCache();
        }
    }

    /**
     * Calcula la salida de una neurona, mediante la formula a(k,m)
     *
     * @param layerIndex  índice de una capa
     * @param neuronIndex índice de una neurona
     *
     * @return salida de una neurona
     */
    private
    Double calculateNeuronOutput(
            final int layerIndex,
            final int neuronIndex
    ) {
        final Layer currentLayer = turnCurrentStateCache.getLayer(layerIndex);
        final int   nextLayer    = layerIndex + 1;
        if (neuronIndex == currentLayer.getNeurons().size() &&
            turnCurrentStateCache.getOutputLayerIndex() != layerIndex &&
            neuralNetwork.hasBias(nextLayer)) {
            //retorno la salida de la neurona falsa
            return 1d;
        } else {
            // si es la coordenada de una neurona, devuelvo su f(net) o la entrada (si es capa de entrada)
            return currentLayer.getNeuron(neuronIndex).getOutput();
        }
    }

    /**
     * calcula el vector de Error TD para todas las neuronas de salida
     *
     * @param nextTurnState estado del siguiente turno.
     */
    private
    void calculateTDError(final IStatePerceptron nextTurnState) {
        final Layer outputLayerCurrentState = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex());
        IntStream   lastLayerStream         = IntStream.range(0, outputLayerCurrentState.getNeurons().size());

        if (concurrencyInLayer[turnCurrentStateCache.getOutputLayerIndex()]) {
            lastLayerStream = lastLayerStream.parallel();
        } else {
            lastLayerStream = lastLayerStream.sequential();
        }

        lastLayerStream.forEach(outputNeuronIndex -> {
            final double output                   = outputLayerCurrentState.getNeuron(outputNeuronIndex).getOutput();
            final double nextTurnStateBoardReward = problem.normalizeValueToPerceptronOutput(nextTurnState.getStateReward(outputNeuronIndex));
            if (!nextTurnState.isTerminalState()) {
                tDError.set(outputNeuronIndex, nextTurnStateBoardReward + gamma * nextTurnOutputs.get(outputNeuronIndex) - output);
            } else {
                tDError.set(outputNeuronIndex, nextTurnStateBoardReward - output);
            }
        });
    }

    /**
     * Computo de la traza de elegibilidad para el estado actual
     *
     * @param outputNeuronIndex índice de una neurona de salida.
     * @param layerIndexJ       índice de la capa de neuronas de mas cercana a la capa de salida.
     * @param neuronIndexJ      índice de la neurona mas cercana a la capa de salida.
     * @param layerIndexK       índice de la capa de neuronas mas alejada de la capa de salida.
     * @param neuronIndexK      índice de la neurona mas alejada de la capa de salida.
     *
     * @return un valor correspondiente a la formula "e" de la teoría.
     */
    private
    Double computeEligibilityTrace(
            final int outputNeuronIndex,
            final int layerIndexJ,
            final int neuronIndexJ,
            final int layerIndexK,
            final int neuronIndexK
    ) {
        final double derivedOutput = derivative(outputNeuronIndex, layerIndexJ, neuronIndexJ) * calculateNeuronOutput(layerIndexK, neuronIndexK);
        if (lambda > 0) {
            final List<Double> neuronKEligibilityTrace = eligibilityTraces.get(layerIndexJ).get(neuronIndexJ).get(neuronIndexK);
            final double newEligibilityTrace = (neuronKEligibilityTrace.get(outputNeuronIndex) * lambda * gamma) +
                                               derivedOutput; //reutilizamos las viejas trazas
            neuronKEligibilityTrace.set(outputNeuronIndex, newEligibilityTrace);
            return newEligibilityTrace;
        } else {
            return derivedOutput;
        }
    }

    /**
     * Calcula el error del peso, dependiendo del estado del problema en el turno siguiente.
     *
     * @param layerIndexJ  índice de la capa de neuronas de mas cercana a la capa de salida.
     * @param neuronIndexJ índice de la neurona mas cercana a la capa de salida.
     * @param layerIndexK  índice de la capa de neuronas mas alejada de la capa de salida.
     * @param neuronIndexK índice de la neurona mas alejada de la capa de salida.
     *
     * @return error del peso.
     */
    private
    Double computeWeightError(
            final int layerIndexJ,
            final int neuronIndexJ,
            final int layerIndexK,
            final int neuronIndexK
    ) {
        final int outputLayerSize = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();
        //caso especial para la ultima capa de pesos. No debemos hacer la sumatoria para toda salida.
        if (layerIndexJ == turnCurrentStateCache.getOutputLayerIndex()) {
            return alpha[layerIndexK] *
                   tDError.get(neuronIndexJ) * computeEligibilityTrace(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK);
        } else {
            IntStream lastLayerStream = IntStream.range(0, outputLayerSize);

            if (concurrencyInLayer[turnCurrentStateCache.getOutputLayerIndex()]) {
                lastLayerStream = lastLayerStream.parallel();
            } else {
                lastLayerStream = lastLayerStream.sequential();
            }

            return lastLayerStream.mapToDouble(outputNeuronIndex -> alpha[layerIndexJ] *
                                                                    tDError.get(outputNeuronIndex) *
                                                                    computeEligibilityTrace(outputNeuronIndex,
                                                                            layerIndexJ,
                                                                            neuronIndexJ,
                                                                            layerIndexK, neuronIndexK
                                                                    )).sum();
        }
    }

    /**
     * Crea o recicla una {@code NeuralNetworkCache}.
     *
     * @param state    estado al cual se le debe crear una {@code NeuralNetworkCache}
     * @param oldCache {@code NeuralNetworkCache} anterior, null en caso de no tener.
     *
     * @return neva o actualizada {@code NeuralNetworkCache}.
     */
    @SuppressWarnings("null")
    private
    NeuralNetworkCache createCache(
            final IStatePerceptron state,
            final NeuralNetworkCache oldCache
    ) {
        final int outputLayerNeuronQuantity = neuralNetwork.getNeuronQuantityInLayer(neuralNetwork.getLayerQuantity() - 1);

        // inicializamos la Cache o reciclamos alguna vieja
        NeuralNetworkCache currentCache;
        if (oldCache == null) {
            currentCache = new NeuralNetworkCache(neuralNetwork.getLayerQuantity());
        } else {
            currentCache = oldCache;
        }
        IntStream.range(0, neuralNetwork.getLayerQuantity())
                 .sequential() //no se puede en paralelo porque se necesitan las neuronas de la capa anterior para f(net) y otros
                 .forEach(l -> {
                     //inicializamos la variable para que sea efectivamente final, y poder usar paralelismo funcional
                     final int currentLayerIndex = l;
                     //creamos una capa o reciclamos una vieja
                     final Layer layer;
                     if (oldCache == null) {
                         layer = new Layer(neuralNetwork.getNeuronQuantityInLayer(currentLayerIndex));
                     } else {
                         layer = oldCache.getLayer(currentLayerIndex);
                     }

                     IntStream currentLayerStream = IntStream.range(0, neuralNetwork.getNeuronQuantityInLayer(currentLayerIndex));

                     if (concurrencyInLayer[currentLayerIndex]) {
                         currentLayerStream = currentLayerStream.parallel();
                     } else {
                         currentLayerStream = currentLayerStream.sequential();
                     }

                     // Recorremos cada neurona que debería ir en la capa,
                     // la inicializamos, y la cargamos en dicha capa
                     currentLayerStream.forEach(currentNeuronIndex -> {
                         final Neuron neuron;
                         final Layer  oldCacheCurrentLayer;
                         if (oldCache != null) {
                             oldCacheCurrentLayer = oldCache.getLayer(currentLayerIndex);
                         } else {
                             oldCacheCurrentLayer = null;
                         }
                         if (currentLayerIndex == 0) {
                             //configuramos la neurona de entrada
                             // creando una o reciclando una vieja
                             if (oldCache == null) {
                                 neuron = new Neuron(0, 0);
                             } else {
                                 neuron = oldCacheCurrentLayer.getNeuron(currentNeuronIndex);
                             }
                             neuron.setOutput(state.translateToPerceptronInput(currentNeuronIndex));
                             neuron.setDerivedOutput(null);

                         } else {
                             // Iniciamos variables efectivamente constantes
                             // para la programación funcional
                             final int previousLayerIndex = currentLayerIndex - 1;
                             //configuramos la neurona creando una o reciclando una vieja
                             if (oldCache == null) {
                                 neuron = new Neuron(neuralNetwork.getNeuronQuantityInLayer(previousLayerIndex), outputLayerNeuronQuantity);
                             } else {
                                 neuron = oldCacheCurrentLayer.getNeuron(currentNeuronIndex);
                                 neuron.clearDeltas();
                             }
                             if (neuralNetwork.hasBias(currentLayerIndex)) {
                                 neuron.setBias(neuralNetwork.getBias(currentLayerIndex, currentNeuronIndex));
                             }
                             //net = SumatoriaH(w(i,h,m)*a(h,m))
                             final Layer previousCurrentLayer = currentCache.getLayer(previousLayerIndex);

                             IntStream previousLayerStream = IntStream.range(0, neuralNetwork.getNeuronQuantityInLayer(previousLayerIndex));

                             if (concurrencyInLayer[previousLayerIndex]) {
                                 previousLayerStream = previousLayerStream.parallel();
                             } else {
                                 previousLayerStream = previousLayerStream.sequential();
                             }

                             Double net = previousLayerStream.mapToDouble(previousLayerNeuronIndex -> {
                                 //cargamos el peso que conecta las 2 neuronas
                                 neuron.setWeight(previousLayerNeuronIndex,
                                         neuralNetwork.getWeight(currentLayerIndex, currentNeuronIndex, previousLayerNeuronIndex)
                                 );
                                 // devolvemos la multiplicación para luego sumar
                                 return previousCurrentLayer.getNeuron(previousLayerNeuronIndex).getOutput() *
                                        neuron.getWeight(previousLayerNeuronIndex);
                             }).sum();
                             if (neuralNetwork.hasBias(currentLayerIndex)) {
                                 net += neuron.getBias();
                             }
                             neuron.setOutput(neuralNetwork.getActivationFunction(currentLayerIndex).apply(net));
                             neuron.setDerivedOutput(neuralNetwork.getDerivedActivationFunction(currentLayerIndex).apply(neuron.getOutput()));
                         }
                         //cargamos la nueva neurona, si es que creamos una nueva cache
                         if (oldCache == null) {
                             layer.setNeuron(currentNeuronIndex, neuron);
                         }
                     });
                     //cargamos la nueva capa, si es que creamos una nueva cache
                     if (oldCache == null) {
                         currentCache.setLayer(currentLayerIndex, layer);
                     }
                 });
        return currentCache;
    }

    /**
     * Crea una estructura para almacenar y reciclar cálculos temporales.
     */
    private
    void createEligibilityCache() {
        final int outputLayerNeuronQuantity = neuralNetwork.getNeuronQuantityInLayer(neuralNetwork.getLayerQuantity() - 1);
        // inicializamos la traza de elegibilidad si no esta inicializada
        eligibilityTraces = new ArrayList<>(neuralNetwork.getLayerQuantity());
        for (int layerIndex = 0; layerIndex < neuralNetwork.getLayerQuantity(); layerIndex++) {
            final int                      neuronQuantityInLayer = neuralNetwork.getNeuronQuantityInLayer(layerIndex);
            final List<List<List<Double>>> layer                 = new ArrayList<>(neuronQuantityInLayer);
            for (int neuronIndex = 0; neuronIndex < neuronQuantityInLayer; neuronIndex++) {
                List<List<Double>> neuron = null;
                if (layerIndex != 0) {
                    int neuronQuantityInPreviousLayer = neuralNetwork.getNeuronQuantityInLayer(layerIndex - 1);
                    if (neuralNetwork.hasBias(layerIndex)) {
                        neuronQuantityInPreviousLayer++;
                    }
                    neuron = new ArrayList<>(neuronQuantityInPreviousLayer);
                    for (int previousNeuronIndex = 0; previousNeuronIndex < neuronQuantityInPreviousLayer; previousNeuronIndex++) {
                        final List<Double> previousNeuron = new ArrayList<>(outputLayerNeuronQuantity);
                        for (int outputNeuronIndex = 0; outputNeuronIndex < outputLayerNeuronQuantity; outputNeuronIndex++) {
                            previousNeuron.add(0d); //outputNeuron
                        }
                        neuron.add(previousNeuron);
                    }
                }
                layer.add(neuron);
            }
            eligibilityTraces.add(layer);
        }
    }

    /**
     * Calcula derivative en las coordenadas de la red neuronal establecidas.
     *
     * @param outputNeuronIndex índice de una neurona de salida
     * @param layerIndex        índice de una capa de neuronas
     * @param neuronIndex       índice de una neurona
     *
     * @return derivative.
     */
    @SuppressWarnings("null")
    private
    Double derivative(
            final int outputNeuronIndex,
            final int layerIndex,
            final int neuronIndex
    ) {
        final Layer currentLayer = turnCurrentStateCache.getLayer(layerIndex);
        final Layer nextLayer;
        if (turnCurrentStateCache.isOutputLayer(layerIndex)) {
            nextLayer = null;
        } else {
            nextLayer = turnCurrentStateCache.getLayer(layerIndex + 1);
        }
        final Neuron neuronO = currentLayer.getNeuron(neuronIndex);
        Double       delta   = neuronO.getDelta(outputNeuronIndex);
        if (delta == null) {
            if (turnCurrentStateCache.isOutputLayer(layerIndex)) {
                //i==o ^ o pertenece(I) => f'(net(i,m))
                //                assert outputNeuronIndex == neuronIndex;
                delta = currentLayer.getNeuron(outputNeuronIndex).getDerivedOutput();
                neuronO.setDelta(outputNeuronIndex, delta);
            } else if (turnCurrentStateCache.isNextToLastLayer(layerIndex)) {
                //i!=o ^ o pertenece(I-1) => f'(net(o,m))*derivative(i,i,m)*w(i,o,m)
                assert nextLayer != null;
                delta = neuronO.getDerivedOutput() * derivative(outputNeuronIndex, turnCurrentStateCache.getOutputLayerIndex(), outputNeuronIndex) *
                        nextLayer.getNeuron(outputNeuronIndex).getWeight(neuronIndex);
                neuronO.setDelta(outputNeuronIndex, delta);
            } else {
                //i!=o ^ o !pertenece(I-1) => f'(net(o,m))*SumatoriaP(derivative(i,p,m)*w(p,o,m))
                assert nextLayer != null;
                IntStream nextLayerStream = IntStream.range(0, nextLayer.getNeurons().size());
                if (concurrencyInLayer[layerIndex + 1]) {
                    nextLayerStream = nextLayerStream.parallel();
                } else {
                    nextLayerStream = nextLayerStream.sequential();
                }

                final double sum = nextLayerStream.mapToDouble(neuronIndexP -> {
                    @SuppressWarnings("null") final Neuron neuronP = nextLayer.getNeuron(neuronIndexP);
                    final Double                           deltaP  = neuronP.getDelta(outputNeuronIndex);
                    assert deltaP != null; // llamar la actualización de pesos de tal forma que no haga recursividad
                    return deltaP * neuronP.getWeight(neuronIndex);
                }).sum();
                delta = neuronO.getDerivedOutput() * sum;
                neuronO.setDelta(outputNeuronIndex, delta);
            }
        }
        return delta;
    }

    @Override
    public
    void reset() {
        firstTurn = true;
    }

    /**
     * Reinicia los datos temporales de la traza de elegibilidad.
     */
    private
    void resetEligibilityCache() {
        final int outputLayerNeuronQuantity = neuralNetwork.getNeuronQuantityInLayer(neuralNetwork.getLayerQuantity() - 1);
        for (int layerIndex = 0; layerIndex < neuralNetwork.getLayerQuantity(); layerIndex++) {
            final int neuronQuantityInLayer = neuralNetwork.getNeuronQuantityInLayer(layerIndex);
            for (int neuronIndex = 0; neuronIndex < neuronQuantityInLayer; neuronIndex++) {
                if (layerIndex != 0) {
                    int neuronQuantityInPreviousLayer = neuralNetwork.getNeuronQuantityInLayer(layerIndex - 1);
                    if (neuralNetwork.hasBias(layerIndex)) {
                        neuronQuantityInPreviousLayer++;
                    }
                    for (int previousNeuronIndex = 0; previousNeuronIndex < neuronQuantityInPreviousLayer; previousNeuronIndex++) {
                        for (int outputNeuronIndex = 0; outputNeuronIndex < outputLayerNeuronQuantity; outputNeuronIndex++) {
                            eligibilityTraces.get(layerIndex).get(neuronIndex).get(previousNeuronIndex).set(outputNeuronIndex, 0d);
                        }
                    }
                }
            }
        }
    }

    @Override
    public
    void train(
            final IProblemToTrain problem,
            final IState state,
            final IState nextTurnState,
            final double[] alpha,
            final boolean[] concurrencyInLayer
    ) {
        this.alpha = alpha;
        this.problem = problem;
        this.concurrencyInLayer = concurrencyInLayer;

        //creamos o reciclamos caches
        if (firstTurn) {
            turnCurrentStateCache = createCache((IStatePerceptron) state, null);
            if (lambda > 0) {
                resetEligibilityCache();
            }

            //iniciamos vector con errores
            final int neuronQuantityAtOutput = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();
            if (tDError == null) {
                tDError = new ArrayList<>(neuronQuantityAtOutput);
                for (int i = 0; i < neuronQuantityAtOutput; i++) {
                    tDError.add(null);
                }
            }
            //iniciamos vector de las salidas del próximo turno
            if (nextTurnOutputs == null) {
                nextTurnOutputs = new ArrayList<>(neuronQuantityAtOutput);
                for (int i = 0; i < neuronQuantityAtOutput; i++) {
                    nextTurnOutputs.add(null);
                }
            }
            firstTurn = false;
        } else {
            turnCurrentStateCache = createCache((IStatePerceptron) state, turnCurrentStateCache);
        }

        // Computamos las salidas del perceptron con el estado del siguiente turno.
        nextTurnStateCache = createCache((IStatePerceptron) nextTurnState, nextTurnStateCache);
        for (int i = 0; i < nextTurnOutputs.size(); i++) {
            nextTurnOutputs.set(i, nextTurnStateCache.getLayer(nextTurnStateCache.getOutputLayerIndex()).getNeuron(i).getOutput());
        }

        // Calculamos el TD error.
        calculateTDError((IStatePerceptron) nextTurnState);

        for (int layerIndex = turnCurrentStateCache.getOutputLayerIndex(); layerIndex >= 1; layerIndex--) {
            //capa de mas hacia adelante
            final int layerIndexJ = layerIndex; //variables efectivamente finales para los cálculos funcionales
            //capa de mas atrás, pero contigua a J
            final int layerIndexK = layerIndex - 1;

            final int maxIndexK;
            final int neuronQuantityInK = turnCurrentStateCache.getLayer(layerIndexK).getNeurons().size();
            if (neuralNetwork.hasBias(layerIndexJ)) {
                maxIndexK = neuronQuantityInK;
            } else {
                maxIndexK = neuronQuantityInK - 1;
            }
            final Layer currentLayer = turnCurrentStateCache.getLayer(layerIndexJ);

            IntStream layerJStream = IntStream.range(0, currentLayer.getNeurons().size());
            if (concurrencyInLayer[layerIndexJ]) {
                layerJStream = layerJStream.parallel();
            } else {
                layerJStream = layerJStream.sequential();
            }
            //TODO hacer TEST para mostrar equivalencia entre NTuple y Perceptron trainers
            layerJStream.forEach(neuronIndexJ -> {
                final Neuron currentNeuron = currentLayer.getNeuron(neuronIndexJ);
                IntStream    layerKStream  = IntStream.rangeClosed(0, maxIndexK);
                if (concurrencyInLayer[layerIndexK]) {
                    layerKStream = layerKStream.parallel();
                } else {
                    layerKStream = layerKStream.sequential();
                }
                layerKStream.forEach(neuronIndexK -> {
                    // Calculamos el nuevo valor para el peso o bias,
                    //sumando la corrección adecuada a su valor anterior
                    final double newDifferential = computeWeightError(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK);
                    if (newDifferential != 0d) {
                        final double oldWeight = currentNeuron.getWeight(neuronIndexK);
                        if (neuronIndexK == neuronQuantityInK) {
                            // Si se es una bias, actualizamos la bias en
                            // la red neuronal original
                            neuralNetwork.setBias(layerIndexJ, neuronIndexJ, oldWeight + newDifferential);
                        } else {
                            // Si se es un peso, actualizamos el peso en
                            // la red neuronal original
                            neuralNetwork.setWeight(layerIndexJ, neuronIndexJ, neuronIndexK, oldWeight + newDifferential);
                        }
                    }
                });
            });

        }
    }

    /**
     * Actualiza la traza de elegibilidad solamente. Útil para casos en que se realizo una acción al azar en lugar de
     * evaluar con la red neuronal.
     *
     * @param layerIndexJ  índice de la capa de neuronas de mas cercana a la capa de salida.
     * @param neuronIndexJ índice de la neurona mas cercana a la capa de salida.
     * @param layerIndexK  índice de la capa de neuronas mas alejada de la capa de salida.
     * @param neuronIndexK índice de la neurona mas alejada de la capa de salida.
     */
    private
    void updateEligibilityTraceOnly(
            final int layerIndexJ,
            final int neuronIndexJ,
            final int layerIndexK,
            final int neuronIndexK
    ) {
        final int outputLayerSize = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();

        //caso especial para la ultima capa de pesos. No debemos hacer la sumatoria para toda salida.
        if (layerIndexJ == turnCurrentStateCache.getOutputLayerIndex()) {
            computeEligibilityTrace(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK);
        } else {
            IntStream outputLayerStream = IntStream.range(0, outputLayerSize);
            if (concurrencyInLayer[turnCurrentStateCache.getOutputLayerIndex()]) {
                outputLayerStream = outputLayerStream.parallel();
            } else {
                outputLayerStream = outputLayerStream.sequential();
            }

            outputLayerStream.forEach(outputNeuronIndex -> {
                // System.out.println("outputNeuronIndex:" + outputNeuronIndex + " layerIndexJ:" + layerIndexJ + " neuronIndexJ:" + neuronIndexJ + " layerIndexK:" + layerIndexK + " neuronIndexK:" + neuronIndexK + " E:" + e(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK));
                computeEligibilityTrace(outputNeuronIndex, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK);
            });
        }
    }

}
