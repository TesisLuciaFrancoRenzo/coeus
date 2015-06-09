/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;

/**
 *
 * @author Franco
 */
public interface ITrainer {

    public void createMomentumCache();

    public void createEligibilityCache();

    public void reset();

    public void train(IState afterstate, IState afterStateNextTurn, double[] currentAlpha, double lamdba, boolean aRandomMove, double gamma, double momentum, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces);
}
