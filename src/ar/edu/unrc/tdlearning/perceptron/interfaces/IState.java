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
     * @return recompensa obtenida en el estado actual. Si se es un estado
     *         inicial no deberia tener recompensa, pero si es parcial deberia
     *         poder calcularse esta recompensa parcial.
     */
    public IReward getReward();

    /**
     *
     * @return true si el estado es final para el problema
     */
    public boolean isTerminalState();
}
