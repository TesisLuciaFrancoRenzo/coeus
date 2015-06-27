/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.util.List;

/**
 *
 * @author franco
 */
public interface IProblemState extends IProblem {

    /**
     * Solo se necesita implementar para el modo de aprendizaje
     * TDLambdaLearningAfterstate
     * <p>
     * @param afterState estado intermedio que contiene las acciones
     *                   deterministicas tomadas hasta el momento.
     * <p>
     * @return lista de los estados a los que se pueden alcanzar luego de
     *         computar las acciones no deterministicas. Cada estado debe estar
     *         emparejado con la probabilidad de que estos estados ocurran
     */
    public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(IState afterState);

}
