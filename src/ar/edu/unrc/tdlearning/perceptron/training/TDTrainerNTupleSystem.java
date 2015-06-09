/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainerNTupleSystem extends TDTrainerPerceptron {

    /**
     *
     * @param perceptron
     */
    public TDTrainerNTupleSystem(IPerceptronInterface perceptron) {
        super(perceptron);
    }

    /**
     * Entrenamos la red neuronal con un turno. Incluye la actualizacion de las
     * bias. Es necesario invocar el metodo {@code train} desde el turno 1, esto
     * significa que si llamamos a este metodo desde el turno 6, primero hay que
     * llamarlo desde el tunro 5, y para llamarlo desde el turno 5, primero hay
     * que invocarlo desde el turno 4, etc.
     * <p>
     * @param turnCurrentState         estado del problema en el turno
     *                                 {@code currentTurn}
     * @param nextTurnState            estado del problema en el turno que sigue
     *                                 de {@code currentTurn}
     * @param lamdba                   constante que se encuentra en el
     *                                 intervalo [0,1]
     * @param alpha                    constante de tasa de aprendizaje
     * @param isARandomMove
     * @param gamma                    tasa de descuento
     * @param momentum                 0 <= m < 1
     * @param resetEligibilitiTraces   permite resetear las trazas de
     *                                 elegibilidad en caso de movimientos al
     *                                 azar
     * @param replaceEligibilitiTraces permite reemplazar las trazas en caso de
     *                                 que el peso sea 0, para que cada vez
     *                                 tenga menos influencia en lso calculos
     */
    @Override
    public void train(IState turnCurrentState, IState nextTurnState, double[] alpha, double lamdba, boolean isARandomMove, double gamma, double momentum, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces) {
        this.lambda = lamdba;
        this.alpha = alpha;
        this.gamma = gamma;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        this.replaceEligibilitiTraces = replaceEligibilitiTraces;

        //creamos o reciclamos caches
        if ( currentTurn == 1 ) {
            this.turnCurrentStateCache = createCache(turnCurrentState, null);
            createEligibilityTrace();

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
            this.turnCurrentStateCache = createCache(turnCurrentState, turnCurrentStateCache);
        }

        computeNextTurnOutputs(nextTurnState);

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

                                double newDiferential;
                                if ( momentum > 0 ) {
                                    newDiferential = weightCorrection(
                                            layerIndexJ, neuronIndexJ,
                                            layerIndexK, neuronIndexK,
                                            oldWeight)
                                    + momentum * momentumCache.getNeuron(layerIndexJ, neuronIndexJ).getWeight(neuronIndexK);
                                } else {
                                    newDiferential = weightCorrection(
                                            layerIndexJ, neuronIndexJ,
                                            layerIndexK, neuronIndexK,
                                            oldWeight);
                                }

                                if ( neuronIndexK == neuronQuantityInK ) {
                                    // si se es una bias, actualizamos la bias en la red neuronal original
                                    perceptron.setBias(layerIndexJ,
                                            neuronIndexJ,
                                            oldWeight + newDiferential);
                                    if ( momentum > 0 ) {
                                        momentumCache.getNeuron(layerIndexJ, neuronIndexJ).setBias(newDiferential);
                                    }
                                } else {
                                    // si se es un peso, actualizamos el peso en la red neuronal original
                                    perceptron.setWeight(layerIndexJ,
                                            neuronIndexJ,
                                            neuronIndexK,
                                            oldWeight + newDiferential);
                                    if ( momentum > 0 ) {
                                        momentumCache.getNeuron(layerIndexJ, neuronIndexJ).setWeight(neuronIndexK, newDiferential);
                                    }
                                }
                            } else {
                                updateEligibilityTraceOnly(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, oldWeight, isARandomMove); //FIXME corregir esto para momentum?
                            }
                        });
                    });

        }

        currentTurn++;
    }
}
