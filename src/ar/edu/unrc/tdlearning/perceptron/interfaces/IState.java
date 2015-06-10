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

    public double getBoardRewardToNormalizedPerceptronOutput();

    /**
     *
     * @return recompensa obtenida en el estado actual.
     */
    public IReward getStateReward();

    /**
     *
     * @return true si el estado es final para el problema
     */
    public boolean isTerminalState();

    public double getCurrentRewardNormalizedPerceptronOutput();
}
