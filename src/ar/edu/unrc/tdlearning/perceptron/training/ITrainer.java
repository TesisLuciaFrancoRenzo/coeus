/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;

/**
 *
 * @author Franco
 */
public interface ITrainer {

    /**
     *
     */
    public void reset();

    /**
     *
     * @param problem
     * @param state
     * @param nextTurnState
     * @param currentAlpha
     * @param aRandomMove
     */
    public void train(IProblem problem, IState state, IState nextTurnState, double[] currentAlpha, boolean aRandomMove);
}
