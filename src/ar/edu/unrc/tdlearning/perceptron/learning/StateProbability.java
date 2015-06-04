/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;

/**
 *
 * @author Franco
 */
public class StateProbability {

    private IState nextTurnState;
    private double probability;

    /**
     *
     * @param nextTurnState posible siguiente estado luego de calcular acciones
     *                      no deterministicas
     * @param probability   probabilidad de que ocurra {@code nextTurnState}
     *                      como estado efectivo en el siguiente turno
     */
    public StateProbability(IState nextTurnState, double probability) {
        this.nextTurnState = nextTurnState;
        this.probability = probability;
    }

    /**
     * @return the nextTurnState
     */
    public IState getNextTurnState() {
        return nextTurnState;
    }

    /**
     * @param nextTurnState posible siguiente estado luego de calcular acciones
     *                      no deterministicas
     */
    public void setNextTurnState(IState nextTurnState) {
        this.nextTurnState = nextTurnState;
    }

    /**
     * @return probabilidad de que ocurra {@code nextTurnState} como estado
     *         efectivo en el siguiente turno
     */
    public double getProbability() {
        return probability;
    }

    /**
     * @param probability probabilidad de que se alcance el estado
     *                    {@code nextTurnState}
     */
    public void setProbability(double probability) {
        if ( probability < 0 && probability > 1 ) {
            throw new IllegalArgumentException("probability debe estar en el rango 0<=probability<=1");
        }
        this.probability = probability;
    }
}
