/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainer {

    /**
     * turno actual
     */
    private int currentTurn;

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
    private double gamma;

    /**
     * Constante que se encuentra en el intervalo [0,1]
     */
    private double lambda;

    private final IPerceptronInterface perceptron;
    private boolean replaceEligibilitiTraces;
    private boolean resetEligibilitiTraces;
    /**
     * constante de tasa de aprendizaje
     */
    protected double[] alpha;

    /**
     *
     */
    protected NeuralNetCache momentumCache;

    /**
     *
     */
    protected NeuralNetCache nextTurnStateCache;
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
     *
     * @param perceptron
     */
    public TDTrainer(IPerceptronInterface perceptron) {
        this.perceptron = perceptron;
        elegibilityTraces = null;
        currentTurn = 1;
        momentumCache = createMomentumCache();
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

    /**
     * Entrenamos la red neuronal con un turno. Incluye la actualizacion de las
     * bias. Es necesario invocar el metodo {@code train} desde el turno 1, esto
     * significa que si llamamos a este metodo desde el turno 6, primero hay que
     * llamarlo desde el tunro 5, y para llamarlo desde el turno 5, primero hay
     * que invocarlo desde el turno 4, etc.
     * <p>
     * @param turnCurrentState estado del problema en el turno
     *                         {@code currentTurn}
     * @param nextTurnState    estado del problema en el turno que sigue de
     *                         {@code currentTurn}
     * @param lamdba           constante que se encuentra en el intervalo [0,1]
     * @param alpha            constante de tasa de aprendizaje
     * @param isARandomMove
     * @param gamma            tasa de descuento
     * @param momentum         0 <= m < 1
     */
    public void train(IState turnCurrentState, IState nextTurnState, double[] alpha, double lamdba, boolean isARandomMove, double gamma, double momentum) {
        this.lambda = lamdba;
        this.alpha = alpha;
        this.gamma = gamma;

        //creamos o reciclamos caches
        if ( currentTurn == 1 ) {
            this.turnCurrentStateCache = createCache(turnCurrentState, null);
            this.nextTurnStateCache = createCache(nextTurnState, null);
            assert elegibilityTraces == null;
            createEligibilityTrace();

            //iniciamos vector con errores
            int neuronQuantityAtOutput = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();
            this.tDError = new ArrayList<>(neuronQuantityAtOutput);
            for ( int i = 0; i < neuronQuantityAtOutput; i++ ) {
                tDError.add(null);
            }
        } else {
            this.turnCurrentStateCache = createCache(turnCurrentState, turnCurrentStateCache);
            this.nextTurnStateCache = createCache(nextTurnState, nextTurnStateCache);
        }

        calculateTDError(nextTurnState);

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
            IntStream
                    .range(0, turnCurrentStateCache.getLayer(layerIndexJ).getNeurons().size())
                    .parallel()
                    .forEach(neuronIndexJ -> {
                        IntStream
                        .rangeClosed(0, maxIndexK)
                        .parallel()
                        .forEach(neuronIndexK -> {
                            double oldWeight = turnCurrentStateCache.getNeuron(layerIndexJ, neuronIndexJ).getWeight(neuronIndexK);
                            if ( !isARandomMove || nextTurnState.isTerminalState() ) {
                                //calculamos el nuevo valor para el peso o bias, sumando la correccion adecuada a su valor anterior

                                double newDiferential = weightCorrection(
                                        layerIndexJ, neuronIndexJ,
                                        layerIndexK, neuronIndexK,
                                        oldWeight)
                                + momentum * momentumCache.getNeuron(layerIndexJ, neuronIndexJ).getWeight(neuronIndexK);

                                if ( neuronIndexK == neuronQuantityInK ) {
                                    // si se es una bias, actualizamos la bias en la red neuronal original
                                    perceptron.setBias(layerIndexJ,
                                            neuronIndexJ,
                                            oldWeight + newDiferential);
                                    momentumCache.getNeuron(layerIndexJ, neuronIndexJ).setBias(newDiferential);
                                } else {
                                    // si se es un peso, actualizamos el peso en la red neuronal original
                                    perceptron.setWeight(layerIndexJ,
                                            neuronIndexJ,
                                            neuronIndexK,
                                            oldWeight + newDiferential);
                                    momentumCache.getNeuron(layerIndexJ, neuronIndexJ).setWeight(neuronIndexK, newDiferential);
                                }
                            } else {
                                updateEligibilityTraceOnly(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, oldWeight, isARandomMove); //FIXME corregir esto para momentum?
                            }
                        });
                    });

        }
        currentTurn++;
    }

    /**
     * Calcula la salida de una neurona, mediante la formula a(k,m)
     * <p>
     * @param layerIndex  indice de una capa
     * @param neuronIndex indice de una neurona
     * <p>
     * @return salida de una neurona
     */
    private Double calculateNeuronOutput(int layerIndex, int neuronIndex) {
        if ( neuronIndex == turnCurrentStateCache.getLayer(layerIndex).getNeurons().size() ) {
            //retorno la salida de la neurona falsa
            return 1d;
        } else {
            // si es la coordenada de una neurona, devuelvo su f(net) o la entrada (si es capa de entrada)
            return ((Neuron) turnCurrentStateCache.getNeuron(layerIndex, neuronIndex)).getOutput();
        }
    }

    /**
     *
     * @param state estado al cual se debe crear una cache
     */
    private NeuralNetCache createCache(IState state, NeuralNetCache oldCache) {
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
                    //recorremos en paralelo cada neurona que deberia ir ne la capa, la inicializamos, y la cargamos en dicha capa
                    IntStream
                    .range(0, perceptron.getNeuronQuantityInLayer(currentLayerIndex))
                    .parallel()
                    .forEach(currentNeuronIndex -> {
                        Neuron neuron;
                        if ( currentLayerIndex == 0 ) {
                            //configuramos la neurona de entrada creando una o reciclando una vieja
                            if ( oldCache == null ) {
                                neuron = new Neuron(0, 0);
                            } else {
                                neuron = (Neuron) oldCache.getNeuron(currentLayerIndex, currentNeuronIndex);
                            }
                            neuron.setOutput(state.translateToPerceptronInput(currentNeuronIndex));
                            if ( neuron.getOutput().isNaN() ) {
                                try { //FIXME hacer mas lindo
                                    throw new Exception("wrong input translation state.translateToPerceptronInput(" + currentNeuronIndex + ")= " + neuron.getOutput());
                                } catch ( Exception ex ) {
                                    Logger.getLogger(TDTrainer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            neuron.setDerivatedOutput(null);

                        } else {
                            // iniciamos variables efectivamente constantes para la programacion funcional
                            int previousLayer = currentLayerIndex - 1;
                            //configuramos la neurona creando una o reciclando una vieja
                            if ( oldCache == null ) {
                                neuron = new Neuron(perceptron.getNeuronQuantityInLayer(previousLayer), outputLayerNeuronQuantity);
                            } else {
                                neuron = (Neuron) oldCache.getNeuron(currentLayerIndex, currentNeuronIndex);
                                neuron.clearDeltas();
                            }
                            if ( perceptron.hasBias(currentLayerIndex) ) {
                                //TODO hacer testing para redes neuronales con capas con y sin bias, MIXTO
                                neuron.setBias(perceptron.getBias(currentLayerIndex, currentNeuronIndex));
                            }
                            //net = SumatoriaH(w(i,h,m)*a(h,m))
                            Double net = IntStream
                            .range(0, perceptron.getNeuronQuantityInLayer(previousLayer))
                            .parallel()
                            .mapToDouble(previousLayerNeuronIndex -> {
                                //cargamos el peso que conecta las 2 neuronas
                                neuron.setWeight(previousLayerNeuronIndex,
                                        perceptron.getWeight(currentLayerIndex, currentNeuronIndex, previousLayerNeuronIndex));
                                // devolvemmos la multiplicacion para luego sumar
                                assert !((Neuron) currentCache.getNeuron(previousLayer, previousLayerNeuronIndex)).getOutput().isNaN();
                                return ((Neuron) currentCache.getNeuron(previousLayer, previousLayerNeuronIndex)).getOutput()
                                * neuron.getWeight(previousLayerNeuronIndex);
                            }).sum();
                            if ( perceptron.hasBias(currentLayerIndex) ) {
                                net += neuron.getBias();
                            }
                            neuron.setOutput(perceptron.getActivationFunction(currentLayerIndex).apply(net));

                            assert !neuron.getOutput().isNaN();
                            neuron.setDerivatedOutput(perceptron.getDerivatedActivationFunction(currentLayerIndex).apply(neuron.getOutput()));
                            assert !neuron.getDerivatedOutput().isNaN();
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

    private void createEligibilityTrace() {

        // inicializamos la traza de eligibilidad si no esta inicializada
        int outputLayerNeuronQuantity = perceptron.getNeuronQuantityInLayer(perceptron.getLayerQuantity() - 1);
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
     *
     */
    private NeuralNetCache createMomentumCache() {
        int outputLayerNeuronQuantity = perceptron.getNeuronQuantityInLayer(perceptron.getLayerQuantity() - 1);

        // inicializamos la Cache o reciclamos alguna vieja
        NeuralNetCache currentCache = new NeuralNetCache(perceptron.getLayerQuantity());
        IntStream
                .range(0, perceptron.getLayerQuantity())
                .sequential() //no se puede en paralelo porque se necesitan las neuronas de la capa anterior para f(net) y otros
                .forEach(l -> {
                    //inicializamos la variable para que sea efectivamente final, y poder usar paralelismo funcional
                    int currentLayerIndex = l;
                    //creamos una capa o reciclamos una vieja
                    Layer layer = new Layer(perceptron.getNeuronQuantityInLayer(currentLayerIndex));
                    //recorremos en paralelo cada neurona que deberia ir ne la capa, la inicializamos, y la cargamos en dicha capa
                    IntStream
                    .range(0, perceptron.getNeuronQuantityInLayer(currentLayerIndex))
                    .parallel()
                    .forEach(currentNeuronIndex -> {
                        PartialNeuron neuron;
                        if ( currentLayerIndex == 0 ) {
                            //configuramos la neurona de entrada creando una o reciclando una vieja
                            neuron = new PartialNeuron(0, 0);
                        } else {
                            // iniciamos variables efectivamente constantes para la programacion funcional
                            int previousLayer = currentLayerIndex - 1;
                            //configuramos la neurona creando
                            neuron = new PartialNeuron(perceptron.getNeuronQuantityInLayer(previousLayer), outputLayerNeuronQuantity);
                            //iniciamos los pesos utilizados para momentums en cero
                            for ( int i = 0; i < neuron.getWeights().size(); i++ ) {
                                neuron.getWeights().set(i, 0d);
                            }
                        }
                        //cargamos la nueva neurona
                        layer.setNeuron(currentNeuronIndex, neuron);
                    });
                    //cargamos la nueva capa
                    currentCache.setLayer(currentLayerIndex, layer);
                });
        return currentCache;
    }

    /**
     * calcula el vector de Error TD para todas las neuronas de salida
     * <p>
     * @param nextTurnState
     */
    protected void calculateTDError(IState nextTurnState) {
        Layer outputLayerCurrentState = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex());
        int neuronQuantityAtOutput = outputLayerCurrentState.getNeurons().size();

        int outputLayer = turnCurrentStateCache.getOutputLayerIndex();
        IntStream
                .range(0, neuronQuantityAtOutput)
                .parallel()
                .forEach(outputNeuronIndex -> {
                    //calculamos el TD error
                    // assert !nextTurnStateCache.getNeuron(outputLayer, outputNeuronIndex).getOutput().isNaN();
                    double nextTurnOutput;
                    if ( !nextTurnState.isTerminalState() ) {
                        nextTurnOutput = ((Neuron) nextTurnStateCache.getNeuron(outputLayer, outputNeuronIndex)).getOutput();
                    } else {
                        nextTurnOutput = nextTurnState.translateRealOutputToNormalizedPerceptronOutputFrom(outputNeuronIndex);
                    }

                    tDError.set(outputNeuronIndex, //FIXME trae errores con tangente? ya que es negativo y resta siempre las rewards
                            nextTurnState.translateRewordToNormalizedPerceptronOutputFrom(outputNeuronIndex)
                            + gamma * nextTurnOutput
                            - ((Neuron) outputLayerCurrentState.getNeuron(outputNeuronIndex)).getOutput()
                    );
                    assert tDError.get(outputNeuronIndex) != null && !tDError.get(outputNeuronIndex).isNaN();
                });
    }

    /**
     * Computo de la traza de elegibilidad para el estado actual
     * <p>
     * @param outputNeuronIndex  indice de una neurona de salida
     * @param layerIndexJ        indice de la capa de neuronas de mas hacia
     *                           adelante
     * @param neuronIndexJ       indice de la neurona de mas hacia adelante
     * @param layerIndexK        indice de la capa de neuronas de mas hacia
     *                           atras
     * @param neuronIndexK       indice de la neurona de mas hacia atras
     * <p>
     * @param currentWeightValue ultimo valor que tenia el peso
     * <p>
     * @param isRandomMove       true si el ultimo movimiento fue elegido al
     *                           azar
     * <p>
     * @return un valor correspondiente a la formula "e" de la teoria
     */
    protected double computeEligibilityTrace(int outputNeuronIndex, int layerIndexJ, int neuronIndexJ, int layerIndexK, int neuronIndexK, double currentWeightValue, boolean isRandomMove) {

        if ( this.lambda != 0 ) {
            List<Double> neuronKEligibilityTrace = elegibilityTraces.get(layerIndexJ).get(neuronIndexJ).get(neuronIndexK);
            if ( isRandomMove && resetEligibilitiTraces ) {
                neuronKEligibilityTrace.set(outputNeuronIndex, 0d);
                return 0d;
            } else {
                double newEligibilityTrace;
                if ( currentWeightValue == 0 && replaceEligibilitiTraces ) {
                    newEligibilityTrace = 0;
                } else {
                    newEligibilityTrace = neuronKEligibilityTrace.get(outputNeuronIndex) * lambda * gamma; //reutilizamos las viejas trazas
                }
                newEligibilityTrace += delta(outputNeuronIndex, layerIndexJ, neuronIndexJ) * calculateNeuronOutput(layerIndexK, neuronIndexK);
                neuronKEligibilityTrace.set(outputNeuronIndex, newEligibilityTrace);
                return newEligibilityTrace;
            }
        } else {
            return delta(outputNeuronIndex, layerIndexJ, neuronIndexJ) * calculateNeuronOutput(layerIndexK, neuronIndexK);
        }
    }

    /**
     *
     * @param outputNeuronIndex indice de una neurona de salida
     * @param layerIndex        indice de una capa de neuronas
     * @param neuronIndex       indice de una neurona
     * <p>
     * @return
     */
    protected double delta(int outputNeuronIndex, int layerIndex, int neuronIndex) {
        Neuron neuronO = (Neuron) turnCurrentStateCache.getNeuron(layerIndex, neuronIndex);
        Double delta = neuronO.getDelta(outputNeuronIndex);
        if ( delta == null ) {
            if ( turnCurrentStateCache.isOutputLayer(layerIndex) ) {
                //i==o ^ o pertenece(I) => f'(net(i,m))
                assert outputNeuronIndex == neuronIndex;
                delta = ((Neuron) turnCurrentStateCache.getNeuron(layerIndex, outputNeuronIndex)).getDerivatedOutput();
                neuronO.setDelta(outputNeuronIndex, delta);
            } else if ( turnCurrentStateCache.isNextToLasyLayer(layerIndex) ) {
                //i!=o ^ o pertenece(I-1) => f'(net(o,m))*delta(i,i,m)*w(i,o,m)
                delta = neuronO.getDerivatedOutput()
                        * delta(outputNeuronIndex, turnCurrentStateCache.getOutputLayerIndex(), outputNeuronIndex)
                        * turnCurrentStateCache.getNeuron(layerIndex + 1, outputNeuronIndex).getWeight(neuronIndex);
                neuronO.setDelta(outputNeuronIndex, delta);
            } else {
                //i!=o ^ o !pertenece(I-1) => f'(net(o,m))*SumatoriaP(delta(i,p,m)*w(p,o,m))
                double sum = IntStream
                        .range(0, turnCurrentStateCache.getLayer(layerIndex + 1).getNeurons().size())
                        .parallel()
                        .mapToDouble(neuronIndexP -> {
                            Neuron neuronP = (Neuron) turnCurrentStateCache.getNeuron(layerIndex + 1, neuronIndexP);
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
     * @return the replaceEligibilitiTraces
     */
    protected boolean isReplaceEligibilitiTraces() {
        return replaceEligibilitiTraces;
    }

    /**
     * @param replaceEligibilitiTraces the replaceEligibilitiTraces to set
     */
    protected void setReplaceEligibilitiTraces(boolean replaceEligibilitiTraces) {
        this.replaceEligibilitiTraces = replaceEligibilitiTraces;
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
     * @param layerIndexJ        indice de la capa de neuronas de mas hacia
     *                           adelante
     * @param neuronIndexJ       indice de la neurona de mas hacia adelante
     * @param layerIndexK        indice de la capa de neuronas de mas hacia
     *                           atras
     * @param neuronIndexK       indice de la neurona de mas hacia atras
     * @param currentWeightValue ultimo valor que tenia el peso
     * @param isRandomMove       si el ultimo movimiento fue elegido al azar
     */
    protected void updateEligibilityTraceOnly(int layerIndexJ, int neuronIndexJ, int layerIndexK, int neuronIndexK, double currentWeightValue, boolean isRandomMove) {
        int outputLayerSize = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();

        //caso especial para la ultima capa de pesos. No debemos hacer la sumatoria para toda salida.
        if ( layerIndexJ == turnCurrentStateCache.getOutputLayerIndex() ) {
            computeEligibilityTrace(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, currentWeightValue, isRandomMove);
        } else {
            IntStream
                    .range(0, outputLayerSize)
                    .parallel()
                    .forEach(outputNeuronIndex -> {
                        // System.out.println("outputNeuronIndex:" + outputNeuronIndex + " layerIndexJ:" + layerIndexJ + " neuronIndexJ:" + neuronIndexJ + " layerIndexK:" + layerIndexK + " neuronIndexK:" + neuronIndexK + " E:" + e(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK));
                        computeEligibilityTrace(outputNeuronIndex, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, currentWeightValue, isRandomMove);
                    });
        }
    }

    /**
     *
     * @param layerIndexJ        indice de la capa de neuronas de mas hacia
     *                           adelante
     * @param neuronIndexJ       indice de la neurona de mas hacia adelante
     * @param layerIndexK        indice de la capa de neuronas de mas hacia
     *                           atras
     * @param neuronIndexK       indice de la neurona de mas hacia atras
     * @param currentWeightValue ultimo valor que tenia el peso
     * <p>
     * @return
     */
    protected double weightCorrection(int layerIndexJ, int neuronIndexJ, int layerIndexK, int neuronIndexK, double currentWeightValue) {
        int outputLayerSize = turnCurrentStateCache.getLayer(turnCurrentStateCache.getOutputLayerIndex()).getNeurons().size();

        //caso especial para la ultima capa de pesos. No debemos hacer la sumatoria para toda salida.
        if ( layerIndexJ == turnCurrentStateCache.getOutputLayerIndex() ) {
            // System.out.println("outputNeuronIndex:" + neuronIndexJ + " layerIndexJ:" + layerIndexJ + " neuronIndexJ:" + neuronIndexJ + " layerIndexK:" + layerIndexK + " neuronIndexK:" + neuronIndexK + " E:" + e(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK));
            return alpha[layerIndexK] * tDError.get(neuronIndexJ)
                    * computeEligibilityTrace(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, currentWeightValue, false);
        } else { //FIXME cambiar alpha por beta en casos necesarios
            return IntStream
                    .range(0, outputLayerSize)
                    .parallel()
                    .mapToDouble(outputNeuronIndex -> {
                        // System.out.println("outputNeuronIndex:" + outputNeuronIndex + " layerIndexJ:" + layerIndexJ + " neuronIndexJ:" + neuronIndexJ + " layerIndexK:" + layerIndexK + " neuronIndexK:" + neuronIndexK + " E:" + e(neuronIndexJ, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK));
                        return alpha[layerIndexJ] * tDError.get(outputNeuronIndex)
                        * computeEligibilityTrace(outputNeuronIndex, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, currentWeightValue, false);
                    }).sum();
        }
    }

}
