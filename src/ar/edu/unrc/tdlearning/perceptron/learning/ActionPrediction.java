/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPrediction;

/**
 * Tupla que contiene una acción y la predicción que calcula el perceptron sobre
 * el final del problema, si es que se toma dicha acción
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ActionPrediction implements Comparable<ActionPrediction> {

    /**
     * Acción asociada a la prediccion de llegar al final del juego.
     */
    private IAction action;

    /**
     * Es la salida predicha del final del juego, debe ser codificada como
     * comparable para poder identificar, dadas dos salidas, cual es la mejor
     * prediccion.
     */
    private IPrediction prediction;

    /**
     *
     * @param action     acción relacionada a {@code prediction}
     * @param prediction predicción del perceptrón si se elige la acción
     *                   {@code action}. La prediccion debe contener sumada la
     *                   recompensa que se obtendra al tomar dicha accion, si es
     *                   que se esta utilizando el metodo acumulativo de
     *                   TDlearning
     */
    public ActionPrediction(IAction action, IPrediction prediction) {
        this.action = action;
        this.prediction = prediction;
    }

    @Override
    public int compareTo(ActionPrediction other) {
        return prediction.compareTo(other.getPrediction());
    }

    /**
     * @return acción asociada a la prediccíon del final del problema
     */
    public IAction getAction() {
        return action;
    }

    //TODO: Revisar este comentario
    /**
     * @param action La acción a setear (establecer)
     */
    public void setAction(IAction action) {
        this.action = action;
    }

    /**
     * @return predicción del fianl del problema si se toma la acción asociada
     */
    public IPrediction getPrediction() {
        return prediction;
    }

    //TODO: Revisar este comentario
    /**
     * @param prediction La prediccion a setear (establecer)
     */
    public void setPrediction(IPrediction prediction) {
        this.prediction = prediction;
    }

}
