/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import static java.lang.Math.exp;
import java.util.function.Function;

/**
 *
 * @author Renzo
 */
public class FunctionUtils {

    /**
     * Derivada de la función de activación Sigmoideo. {@code fValue} debe ser
     * sigmoid(value) por cuestiones de optimización
     */
    public static final Function<Double, Double> derivatedSigmoid = (fValue) -> fValue * (1d - fValue);

    /**
     * Derivada de la función de activación TANH. {@code fValue} debe ser
     * tanh(value) por cuestiones de optimización
     */
    public static final Function<Double, Double> derivatedTanh = (fValue) -> 1d - fValue * fValue;
    /**
     * Función de activación Sigmoideo
     */
    public static final Function<Double, Double> sigmoid = (value) -> 1d / (1d + exp(-value));
    /**
     * Función de activación TANH
     */
    public static final Function<Double, Double> tanh = (value) -> Math.tanh(value);

    /**
     * Función de activación lineal
     */
    public static final Function<Double, Double> lineal = (value) -> value;

    /**
     * Función de activación lineal
     */
    public static final Function<Double, Double> derivatedLineal = (value) -> 1d;
}
