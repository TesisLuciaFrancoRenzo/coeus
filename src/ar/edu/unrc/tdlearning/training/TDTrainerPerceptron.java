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
package ar.edu.unrc.tdlearning.training;

import ar.edu.unrc.tdlearning.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.interfaces.IProblemToTrain;
import ar.edu.unrc.tdlearning.interfaces.IState;
import ar.edu.unrc.tdlearning.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.training.perceptrons.Layer;
import ar.edu.unrc.tdlearning.training.perceptrons.NeuralNetCache;
import ar.edu.unrc.tdlearning.training.perceptrons.Neuron;
import ar.edu.unrc.tdlearning.training.perceptrons.PartialNeuron;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public final class TDTrainerPerceptron implements ITrainer {

    private boolean[] concurrencyInLayer;

    /**
     * Primer componente: indice de la capa de la neurona<br>
     * Segunda componente: indice de la neurona<br>
     * Tercera componente: indice de la segunda neurona involucrada en el
     * calculo del peso <br>
     * Cuarta componente: indice de la neurona de salida con respecto de la cual
     * se esta actualizando el peso<br>
     * Quinta componente: turno (m) de la traza de eligibilidad
     */
    private List<List<List<List<Double>>>> elegibilityTraces;
    private IProblemToTrain problem;
    /**
     * constante de tasa de aprendizaje
     */
    protected double[] alpha;
    /**
     * turno actual
     */
    protected int currentTurn;

    /**
     *
     */
    protected double gamma;

    /**
     * Constante que se encuentra en el intervalo [0,1]
     */
    protected double lambda;

    /**
     *
     */
    protected List<Double> nextTurnOutputs;
    /**
     *
     */
    protected NeuralNetCache nextTurnStateCache;

    /**
     *
     */
    protected final IPerceptronInterface perceptron;

    /**
     *
     */
    protected boolean resetEligibilitiTraces;
    /**
     * Vector de errores TD para la capa de salida, comparando el turno actual
     * con el siguiente
     */
    protected List<Double> tDError;
    /**
     * Cache utilizada para reciclar calculos y evitar recursiones
     */
    protected NeuralNetCache turnCurrentStateCache;

    /**
     * @param lambda                 constante que se encuentra en el intervalo
     *                               [0,1]
     * @param gamma                  tasa de descuento
     * @param resetEligibilitiTraces permite resetear las trazas de elegibilidad
     *                               en caso de movimientos al azar
     * @param perceptron
     */
    public TDTrainerPerceptron(final IPerceptronInterface perceptron, final double lambda, final double gamma, final boolean resetEligibilitiTraces) {
        this.perceptron = perceptron;
        currentTurn = 1;
        elegibilityTraces = null;
        nextTurnStateCache = null;
        turnCurrentStateCache = null;
        this.lambda = lambda;
        this.gamma = gamma;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        if ( lambda > 0 ) {
            createEligibilityCache();
        }
    }

    /**
     * @return the currentTurn
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Primer componente: indice de la capa de la neurona<br>
     * Segunda componente: indice de la neurona<br>
     * Tercera componente: indice de la segunda neurona involucrada en el
     * calculo del peso <br>
     * Cuarta componente: indice de la neurona de salida con respecto de la cual
     * se esta actualizando el peso<br>
     * Quinta componente: turno (m) de la traza de eligibilidad
     * <p>
     * @return the elegibilityTraces
     */
    public List<List<List<List<Double>>>> getElegibilityTraces() {
        return elegibilityTraces;
    }

    /**
     *
     */
    public void printLastCache() {
        System.out.println("\n================= turno actual ===============================\n");
//        turnCurrentStateCache.printDebug();
        System.out.println("\n================= turno siguiente ===============================\n");
//        nextTurnStateCache.printDebug();
    }

    @Override
    public void reset() {
        currentTurn = 1;
    }

    /**
     *
     */
    public void resetEligibilityCache() {
        int outputLayerNeuronQuantity = perceptron.getNeuronQuantityInLayer(perceptron.getLayerQuantity() - 1);
        for ( int layerIndex = 0; layerIndex < perceptron.getLayerQuantity(); layerIndex++ ) {
            int neuronQuantityInLayer = perceptron.getNeuronQuantityInLayer(layerIndex);
            for ( int neuronIndex = 0; neuronIndex < neuronQuantityInLayer; neuronIndex++ ) {
                if ( layerIndex != 0 ) {
                    int neuronQuantityInPreviousLayer = perceptron.getNeuronQuantityInLayer(layerIndex - 1);
                    if ( perceptron.hasBias(layerIndex) ) {
                        neuronQuantityInPreviousLayer++;
                    }
                    for ( int previousNeuronIndex = 0; previousNeuronIndex < neuronQuantityInPreviousLayer; previousNeuronIndex++ ) {
                        for ( int outputNeuronIndex = 0; outputNeuronIndex < outputLayerNeuronQuantity; outputNeuronIndex++ ) {
                            elegibilityTraces.get(layerIndex).get(neuronIndex).get(previousNeuronIndex).set(outputNeuronIndex, 0d);
                        }
                    }
                }
            }
        }
    }

    /**
     * Entrenamos la red neuronal con un turno. Incluye la actualizacion de las
     * bias. Es necesario invocar el metodo {@code train} desde el turno 1, esto
     * significa que si llamamos a este metodo desde el turno 6, primero hay que
     * llamarlo desde el tunro 5, y para llamarlo desde el turno 5, primero hay
     * que invocarlo desde el turno 4, etc.
     * <p>
     * @param problem
     * @param state              estado del problema en el turno
     *                           {@code currentTurn}
     * @param nextTurnState      estado del problema en el turno que sigue de
     *                           {@code currentTurn}
     * <p>
     * @param alpha              constante de tasa de aprendizaje
     * @param concurrencyInLayer
     * @param isARandomMove
     */
    @Override
    public void train(
            final IProblemToTrain problem,
            final IState state,
            final IState nextTurnState,
            final double[] alpha,
            final boolean[] concurrencyInLayer,
            final boolean isARandomMove
    ) {
        this.alpha = alpha;
        this.problem = problem;
        this.concurrencyInLayer = concurrencyInLayer;

        //creamos o reciclamos caches
        if ( currentTurn == 1 ) {
            this.turnCurrentStateCache = createCache((IStatePerceptron) state, null);
            if ( lambda > 0 ) {
                resetEligibilityCache();
            }

            //iniciamos vector con errores
            int neuronQuantityAtOutput = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();
            if ( tDError == null ) {
                this.tDError = new ArrayList<>(neuronQuantityAtOutput);
                for ( int i = 0; i < neuronQuantityAtOutput; i++ ) {
                    tDError.add(null);
                }
            }
            //iniciamos vector de las salidas del proximo turno
            if ( nextTurnOutputs == null ) {
                this.nextTurnOutputs = new ArrayList<>(neuronQuantityAtOutput);
                for ( int i = 0; i < neuronQuantityAtOutput; i++ ) {
                    nextTurnOutputs.add(null);
                }
            }
        } else {
            this.turnCurrentStateCache = createCache((IStatePerceptron) state, turnCurrentStateCache);
        }

        computeNextTurnOutputs((IStatePerceptron) nextTurnState);

        calculateTDError((IStatePerceptron) nextTurnState);

        for ( int layerIndex = turnCurrentStateCache.getOutputLayerIndex(); layerIndex >= 1; layerIndex-- ) {
            //capa de mas hacia adelante
            int layerIndexJ = layerIndex; //varialbes efectivamente finales para los calculos funcionales
            //capa de mas atras, pero contigua a J
            int layerIndexK = layerIndex - 1;

            int maxIndexK;
            int neuronQuantityInK = turnCurrentStateCache.getLayer(layerIndexK).getNeurons().size();
            if ( perceptron.hasBias(layerIndexJ) ) {
                maxIndexK = neuronQuantityInK;
            } else {
                maxIndexK = neuronQuantityInK - 1;
            }
            Layer currentLayer = turnCurrentStateCache.getLayer(layerIndexJ);

            IntStream layerJStream = IntStream
                    .range(0, currentLayer.getNeurons().size());

            if ( concurrencyInLayer[layerIndexJ] ) {
                layerJStream = layerJStream.parallel();
            } else {
                layerJStream = layerJStream.sequential();
            }

            layerJStream.forEach(neuronIndexJ -> {
                PartialNeuron currentNeuron = currentLayer.getNeuron(neuronIndexJ);
                IntStream layerKStream = IntStream.rangeClosed(0, maxIndexK);

                if ( concurrencyInLayer[layerIndexK] ) {
                    layerKStream = layerKStream.parallel();
                } else {
                    layerKStream = layerKStream.sequential();
                }

                layerKStream.forEach(neuronIndexK -> {
                    double oldWeight = currentNeuron.getWeight(neuronIndexK);
                    if ( !isARandomMove || nextTurnState.isTerminalState() ) {
                        //calculamos el nuevo valor para el peso o bias, sumando la correccion adecuada a su valor anterior

                        double newDiferential = computeWeightError(
                                layerIndexJ, neuronIndexJ,
                                layerIndexK, neuronIndexK,
                                isARandomMove);

                        if ( neuronIndexK == neuronQuantityInK ) {
                            // si se es una bias, actualizamos la bias en la red neuronal original
                            perceptron.setBias(layerIndexJ,
                                    neuronIndexJ,
                                    oldWeight + newDiferential);

                        } else {
                            // si se es un peso, actualizamos el peso en la red neuronal original
                            perceptron.setWeight(layerIndexJ,
                                    neuronIndexJ,
                                    neuronIndexK,
                                    oldWeight + newDiferential);

                        }
                    } else {
                        //TODO asegurar safethread con estas funciones!
                        updateEligibilityTraceOnly(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isARandomMove); //FIXME corregir esto para momentum?
                    }
                });
            });

        }

        currentTurn++;
    }

    private void createEligibilityCache() {
        int outputLayerNeuronQuantity = perceptron.getNeuronQuantityInLayer(perceptron.getLayerQuantity() - 1);
        // inicializamos la traza de eligibilidad si no esta inicializada
        /**
         * Primer componente: indice de la capa de la neurona<br>
         * Segunda componente: indice de la neurona<br>
         * Tercera componente: indice de la segunda neurona involucrada en el
         * calculo del peso, o la bias (ultimo valor del vector, si hay bias)
         * <br>
         * Cuarta componente: indice de la neurona de salida con respecto de la
         * cual se esta actualizando el peso<br>
         * Quinta componente: turno (m) de la traza de eligibilidad
         */
        this.elegibilityTraces = new ArrayList<>(perceptron.getLayerQuantity());
        for ( int layerIndex = 0; layerIndex < perceptron.getLayerQuantity(); layerIndex++ ) {
            int neuronQuantityInLayer = perceptron.getNeuronQuantityInLayer(layerIndex);
            List<List<List<Double>>> layer = new ArrayList<>(neuronQuantityInLayer);
            for ( int neuronIndex = 0; neuronIndex < neuronQuantityInLayer; neuronIndex++ ) {
                List<List<Double>> neuron = null;
                if ( layerIndex != 0 ) {
                    int neuronQuantityInPreviousLayer = perceptron.getNeuronQuantityInLayer(layerIndex - 1);
                    if ( perceptron.hasBias(layerIndex) ) {//TODO recordar cambiar hasbias para soportar por capa y no por perceptron
                        neuronQuantityInPreviousLayer++;
                    }
                    neuron = new ArrayList<>(neuronQuantityInPreviousLayer);
                    for ( int previousNeuronIndex = 0; previousNeuronIndex < neuronQuantityInPreviousLayer; previousNeuronIndex++ ) {
                        List<Double> previousNeuron = new ArrayList<>(outputLayerNeuronQuantity);
                        for ( int outputNeuronIndex = 0; outputNeuronIndex < outputLayerNeuronQuantity; outputNeuronIndex++ ) {
                            previousNeuron.add(0d); //outputNeuron
                        }
                        neuron.add(previousNeuron);
                    }
                }
                layer.add(neuron);
            }
            this.elegibilityTraces.add(layer);
        }
    }

    /**
     * Calcula la salida de una neurona, mediante la formula a(k,m)
     * <p>
     * @param layerIndex  indice de una capa
     * @param neuronIndex indice de una neurona
     * <p>
     * @return salida de una neurona
     */
    protected Double calculateNeuronOutput(final int layerIndex, final int neuronIndex) {
        Layer currentLayer = turnCurrentStateCache.getLayer(layerIndex);
        if ( neuronIndex == currentLayer.getNeurons().size() ) {
            //retorno la salida de la neurona falsa
            return 1d;
        } else {
            // si es la coordenada de una neurona, devuelvo su f(net) o la entrada (si es capa de entrada)
            return ((Neuron) currentLayer.getNeuron(neuronIndex)).getOutput();
        }
    }

    /**
     * calcula el vector de Error TD para todas las neuronas de salida
     * <p>
     * @param nextTurnState
     */
    protected void calculateTDError(final IStatePerceptron nextTurnState) {
        Layer outputLayerCurrentState = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex());
        int neuronQuantityAtOutput = outputLayerCurrentState.getNeurons().size();

        IntStream lastLayerStream = IntStream
                .range(0, neuronQuantityAtOutput);

        if ( concurrencyInLayer[turnCurrentStateCache.getOutputLayerIndex()] ) {
            lastLayerStream = lastLayerStream.parallel();
        } else {
            lastLayerStream = lastLayerStream.sequential();
        }

        lastLayerStream.forEach(outputNeuronIndex -> {
            double output = ((Neuron) outputLayerCurrentState.getNeuron(outputNeuronIndex)).getOutput();
            double nextTurnOutput = nextTurnOutputs.get(outputNeuronIndex);
            double nextTurnStateBoardReward = problem.normalizeValueToPerceptronOutput(nextTurnState.getStateReward(outputNeuronIndex));

            if ( !nextTurnState.isTerminalState() ) {
                tDError.set(outputNeuronIndex, nextTurnStateBoardReward + gamma * nextTurnOutput - output);
            } else {
                double finalReward = problem.normalizeValueToPerceptronOutput(problem.getFinalReward(nextTurnState, outputNeuronIndex));
                tDError.set(outputNeuronIndex, gamma * finalReward - output);
            }
        });
    }

    /**
     * Computo de la traza de elegibilidad para el estado actual
     * <p>
     * @param outputNeuronIndex indice de una neurona de salida
     * @param layerIndexJ       indice de la capa de neuronas de mas hacia
     *                          adelante
     * @param neuronIndexJ      indice de la neurona de mas hacia adelante
     * @param layerIndexK       indice de la capa de neuronas de mas hacia atras
     * @param neuronIndexK      indice de la neurona de mas hacia atras
     * <p>
     * @param isRandomMove      true si el ultimo movimiento fue elegido al azar
     * <p>
     * @return un valor correspondiente a la formula "e" de la teoria
     */
    protected Double computeEligibilityTrace(
            final int outputNeuronIndex,
            final int layerIndexJ,
            final int neuronIndexJ,
            final int layerIndexK,
            final int neuronIndexK,
            final boolean isRandomMove) {
        double derivatedOutput = delta(outputNeuronIndex, layerIndexJ, neuronIndexJ) * calculateNeuronOutput(layerIndexK, neuronIndexK);
        if ( this.lambda > 0 ) {
            List<Double> neuronKEligibilityTrace = elegibilityTraces.get(layerIndexJ).get(neuronIndexJ).get(neuronIndexK);
            if ( isRandomMove && resetEligibilitiTraces ) {
                neuronKEligibilityTrace.set(outputNeuronIndex, 0d);
                return 0d;
            } else if ( isRandomMove && !resetEligibilitiTraces ) {
                double newEligibilityTrace = neuronKEligibilityTrace.get(outputNeuronIndex) * lambda * gamma; //reutilizamos las viejas trazas
                neuronKEligibilityTrace.set(outputNeuronIndex, newEligibilityTrace);
                return newEligibilityTrace;
            } else {
                double newEligibilityTrace = neuronKEligibilityTrace.get(outputNeuronIndex) * lambda * gamma; //reutilizamos las viejas trazas
                newEligibilityTrace += derivatedOutput;
                neuronKEligibilityTrace.set(outputNeuronIndex, newEligibilityTrace);
                return newEligibilityTrace;
            }
        } else {
            return derivatedOutput;
        }
    }

    /**
     *
     * @param nextTurnState
     */
    protected void computeNextTurnOutputs(final IStatePerceptron nextTurnState) {
        this.nextTurnStateCache = createCache(nextTurnState, nextTurnStateCache);
        for ( int i = 0; i < this.nextTurnOutputs.size(); i++ ) {
            this.nextTurnOutputs.set(i, ((Neuron) nextTurnStateCache.getLayer(nextTurnStateCache.getOutputLayerIndex()).getNeuron(i)).getOutput());
        }
    }

    /**
     *
     * @param layerIndexJ  indice de la capa de neuronas de mas hacia adelante
     * @param neuronIndexJ indice de la neurona de mas hacia adelante
     * @param layerIndexK  indice de la capa de neuronas de mas hacia atras
     * @param neuronIndexK indice de la neurona de mas hacia atras
     * @param isRandomMove <p>
     * @return
     */
    protected Double computeWeightError(
            final int layerIndexJ,
            final int neuronIndexJ,
            final int layerIndexK,
            final int neuronIndexK,
            final boolean isRandomMove) {
        int outputLayerSize = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();
        //caso especial para la ultima capa de pesos. No debemos hacer la sumatoria para toda salida.
        if ( layerIndexJ == turnCurrentStateCache.getOutputLayerIndex() ) {
            return alpha[layerIndexK] * tDError.get(neuronIndexJ)
                    * computeEligibilityTrace(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
        } else { //FIXME cambiar alpha por beta en casos necesarios
            IntStream lastLayerStream = IntStream
                    .range(0, outputLayerSize);

            if ( concurrencyInLayer[turnCurrentStateCache.getOutputLayerIndex()] ) {
                lastLayerStream = lastLayerStream.parallel();
            } else {
                lastLayerStream = lastLayerStream.sequential();
            }

            return lastLayerStream.mapToDouble(outputNeuronIndex -> {
                return alpha[layerIndexJ] * tDError.get(outputNeuronIndex)
                        * computeEligibilityTrace(outputNeuronIndex, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
            }).sum();
        }
    }

    /**
     *
     * @param state    estado al cual se debe crear una cache
     * @param oldCache <p>
     * @return
     */
    @SuppressWarnings( "null" )
    protected NeuralNetCache createCache(final IStatePerceptron state, final NeuralNetCache oldCache) {
        int outputLayerNeuronQuantity = perceptron.getNeuronQuantityInLayer(perceptron.getLayerQuantity() - 1);

        // inicializamos la Cache o reciclamos alguna vieja
        NeuralNetCache currentCache;
        if ( oldCache == null ) {
            currentCache = new NeuralNetCache(perceptron.getLayerQuantity());
        } else {
            currentCache = oldCache;
        }
        IntStream
                .range(0, perceptron.getLayerQuantity())
                .sequential() //no se puede en paralelo porque se necesitan las neuronas de la capa anterior para f(net) y otros
                .forEach(l -> {
                    //inicializamos la variable para que sea efectivamente final, y poder usar paralelismo funcional
                    int currentLayerIndex = l;
                    //creamos una capa o reciclamos una vieja
                    Layer layer;
                    if ( oldCache == null ) {
                        layer = new Layer(perceptron.getNeuronQuantityInLayer(currentLayerIndex));
                    } else {
                        layer = oldCache.getLayer(currentLayerIndex);
                    }

                    IntStream currentLayerStream = IntStream
                            .range(0, perceptron.getNeuronQuantityInLayer(currentLayerIndex));

                    if ( concurrencyInLayer[currentLayerIndex] ) {
                        currentLayerStream = currentLayerStream.parallel();
                    } else {
                        currentLayerStream = currentLayerStream.sequential();
                    }

                    //recorremos cada neurona que deberia ir en la capa, la inicializamos, y la cargamos en dicha capa
                    currentLayerStream.forEach(currentNeuronIndex -> {
                        Neuron neuron;
                        Layer oldCacheCurrentLayer;
                        if ( oldCache != null ) {
                            oldCacheCurrentLayer = oldCache.getLayer(currentLayerIndex);
                        } else {
                            oldCacheCurrentLayer = null;
                        }
                        if ( currentLayerIndex == 0 ) {
                            //configuramos la neurona de entrada creando una o reciclando una vieja
                            if ( oldCache == null ) {
                                neuron = new Neuron(0, 0);
                            } else {
                                neuron = (Neuron) oldCacheCurrentLayer.getNeuron(currentNeuronIndex);
                            }
                            neuron.setOutput(state.translateToPerceptronInput(currentNeuronIndex));
                            neuron.setDerivatedOutput(null);

                        } else {
                            // iniciamos variables efectivamente constantes para la programacion funcional
                            int previousLayerIndex = currentLayerIndex - 1;
                            //configuramos la neurona creando una o reciclando una vieja
                            if ( oldCache == null ) {
                                neuron = new Neuron(perceptron.getNeuronQuantityInLayer(previousLayerIndex), outputLayerNeuronQuantity);
                            } else {
                                neuron = (Neuron) oldCacheCurrentLayer.getNeuron(currentNeuronIndex);
                                neuron.clearDeltas();
                            }
                            if ( perceptron.hasBias(currentLayerIndex) ) {
                                //TODO hacer testing para redes neuronales con capas con y sin bias, MIXTO
                                neuron.setBias(perceptron.getBias(currentLayerIndex, currentNeuronIndex));
                            }
                            //net = SumatoriaH(w(i,h,m)*a(h,m))
                            Layer previousCurrentLayer = currentCache.getLayer(previousLayerIndex);

                            IntStream previousLayerStream = IntStream
                                    .range(0, perceptron.getNeuronQuantityInLayer(previousLayerIndex));

                            if ( concurrencyInLayer[previousLayerIndex] ) {
                                previousLayerStream = previousLayerStream.parallel();
                            } else {
                                previousLayerStream = previousLayerStream.sequential();
                            }

                            Double net = previousLayerStream.mapToDouble(previousLayerNeuronIndex -> {
                                //cargamos el peso que conecta las 2 neuronas
                                neuron.setWeight(previousLayerNeuronIndex,
                                        perceptron.getWeight(currentLayerIndex, currentNeuronIndex, previousLayerNeuronIndex));
                                // devolvemmos la multiplicacion para luego sumar
                                return ((Neuron) previousCurrentLayer.getNeuron(previousLayerNeuronIndex)).getOutput()
                                        * neuron.getWeight(previousLayerNeuronIndex);
                            }).sum();
                            if ( perceptron.hasBias(currentLayerIndex) ) {
                                net += neuron.getBias();
                            }
                            neuron.setOutput(perceptron.getActivationFunction(currentLayerIndex - 1).apply(net));
                            neuron.setDerivatedOutput(perceptron.getDerivatedActivationFunction(currentLayerIndex - 1).apply(neuron.getOutput()));
                        }
                        //cargamos la nueva neurona, si es que creamos una nueva cache
                        if ( oldCache == null ) {
                            layer.setNeuron(currentNeuronIndex, neuron);
                        }
                    });
                    //cargamos la nueva capa, si es que creamos una nueva cache
                    if ( oldCache == null ) {
                        currentCache.setLayer(currentLayerIndex, layer);
                    }
                });
        return currentCache;
    }

    /**
     *
     * @param outputNeuronIndex indice de una neurona de salida
     * @param layerIndex        indice de una capa de neuronas
     * @param neuronIndex       indice de una neurona
     * <p>
     * @return
     */
    //TODO parallelComputation?? necesita ser threadsafe?
    @SuppressWarnings( "null" )
    protected Double delta(final int outputNeuronIndex, final int layerIndex, final int neuronIndex) {
        Layer currentLayer = turnCurrentStateCache.getLayer(layerIndex);
        Layer nextLayer;
        if ( turnCurrentStateCache.isOutputLayer(layerIndex) ) {
            nextLayer = null;
        } else {
            nextLayer = turnCurrentStateCache.getLayer(layerIndex + 1);
        }
        Neuron neuronO = (Neuron) currentLayer.getNeuron(neuronIndex);
        Double delta = neuronO.getDelta(outputNeuronIndex);
        if ( delta == null ) {
            if ( turnCurrentStateCache.isOutputLayer(layerIndex) ) {
                //i==o ^ o pertenece(I) => f'(net(i,m))
                assert outputNeuronIndex == neuronIndex;
                delta = ((Neuron) currentLayer.getNeuron(outputNeuronIndex)).getDerivatedOutput();
                neuronO.setDelta(outputNeuronIndex, delta);
            } else if ( turnCurrentStateCache.isNextToLasyLayer(layerIndex) ) {
                //i!=o ^ o pertenece(I-1) => f'(net(o,m))*delta(i,i,m)*w(i,o,m)
                delta = neuronO.getDerivatedOutput()
                        * delta(outputNeuronIndex, turnCurrentStateCache.getOutputLayerIndex(), outputNeuronIndex)
                        * nextLayer.getNeuron(outputNeuronIndex).getWeight(neuronIndex);
                neuronO.setDelta(outputNeuronIndex, delta);
            } else {
                //i!=o ^ o !pertenece(I-1) => f'(net(o,m))*SumatoriaP(delta(i,p,m)*w(p,o,m))

                IntStream nextLayerStream = IntStream
                        .range(0, nextLayer.getNeurons().size());

                if ( concurrencyInLayer[layerIndex + 1] ) { //TODO revisar si ESTO ESTA CORRECTO!!!!!
                    nextLayerStream = nextLayerStream.parallel();
                } else {
                    nextLayerStream = nextLayerStream.sequential();
                }

                double sum = nextLayerStream.mapToDouble(neuronIndexP -> {
                    @SuppressWarnings( "null" )
                    Neuron neuronP = (Neuron) nextLayer.getNeuron(neuronIndexP);
                    Double deltaP = neuronP.getDelta(outputNeuronIndex);
                    assert deltaP != null; // llamar la actualizacion de pesos de tal forma que no haga recursividad
                    return deltaP * neuronP.getWeight(neuronIndex);
                }).sum();
                delta = neuronO.getDerivatedOutput() * sum;
                neuronO.setDelta(outputNeuronIndex, delta);
            }
        }
        return delta;
    }

    /**
     * @return the resetEligibilitiTraces
     */
    protected boolean isResetEligibilitiTraces() {
        return resetEligibilitiTraces;
    }

    /**
     * @param resetEligibilitiTraces the resetEligibilitiTraces to set
     */
    protected void setResetEligibilitiTraces(boolean resetEligibilitiTraces) {
        this.resetEligibilitiTraces = resetEligibilitiTraces;
    }

    /**
     *
     */
    protected void sinchornizeCaches() {

    }

    /**
     *
     * @param layerIndexJ  indice de la capa de neuronas de mas hacia adelante
     * @param neuronIndexJ indice de la neurona de mas hacia adelante
     * @param layerIndexK  indice de la capa de neuronas de mas hacia atras
     * @param neuronIndexK indice de la neurona de mas hacia atras
     * @param isRandomMove si el ultimo movimiento fue elegido al azar
     */
    protected void updateEligibilityTraceOnly(
            final int layerIndexJ,
            final int neuronIndexJ,
            final int layerIndexK,
            final int neuronIndexK,
            final boolean isRandomMove) {
        int outputLayerSize = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();

        //caso especial para la ultima capa de pesos. No debemos hacer la sumatoria para toda salida.
        if ( layerIndexJ == turnCurrentStateCache.getOutputLayerIndex() ) {
            computeEligibilityTrace(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
        } else {
            IntStream outputLayerStream = IntStream
                    .range(0, outputLayerSize);

            if ( concurrencyInLayer[turnCurrentStateCache.getOutputLayerIndex()] ) {
                outputLayerStream = outputLayerStream.parallel();
            } else {
                outputLayerStream = outputLayerStream.sequential();
            }

            outputLayerStream.forEach(outputNeuronIndex -> {
                // System.out.println("outputNeuronIndex:" + outputNeuronIndex + " layerIndexJ:" + layerIndexJ + " neuronIndexJ:" + neuronIndexJ + " layerIndexK:" + layerIndexK + " neuronIndexK:" + neuronIndexK + " E:" + e(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK));
                computeEligibilityTrace(outputNeuronIndex, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
            });
        }
    }

}
