/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;

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
    private final IAction action;


    private final Double numericRepresentation;
    /**
     * Es la salida predicha del final del juego, debe ser codificada como
     * comparable para poder identificar, dadas dos salidas, cual es la mejor
     * prediccion.
     */
    private final Double[] prediction;

    /**
     *
     * @param action                acción relacionada a {@code prediction}
     * @param prediction            predicción del perceptrón si se elige la
     *                              acción {@code action}.
     * @param numericRepresentation para comparar diferentes
     *                              {@code ActionPrediction} cuando hay varias
     *                              neuronas de salida
     */
    public ActionPrediction(IAction action, Double[] prediction, Double numericRepresentation) {
        this.action = action;
        this.prediction = prediction;
        this.numericRepresentation = numericRepresentation;
    }

    @Override
    public int compareTo(ActionPrediction other) {
        return numericRepresentation.compareTo(other.getNumericRepresentation());
    }

    /**
     * @return acción asociada a la prediccíon del final del problema
     */
    public IAction getAction() {
        return action;
    }

    /**
     * @return the numericRepresentation
     */
    public double getNumericRepresentation() {
        return numericRepresentation;
    }

    /**
     * @return predicción del fianl del problema si se toma la acción asociada
     */
    public Double[] getPrediction() {
        return prediction;
    }

}
