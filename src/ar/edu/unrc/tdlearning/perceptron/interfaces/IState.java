/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

/**
 *
 * @author Franco
 */
public interface IState {

    /**
     *
     * @param outputNeuron neurona de salida<p>
     * @return recompensa parcial que da este estado para la neurona
     *         {@code outputNeuron}.
     */
    public double getStateReward(int outputNeuron);

    /**
     *
     * @return true si el estado es final para el problema
     */
    public boolean isTerminalState();

    /**
     *
     * @return
     */
    public IState getCopy();

}
