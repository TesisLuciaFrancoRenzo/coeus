/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

/**
 * Estado del problema. Un estado debe poder ser traducido a entradas de un
 * Perceptrón mediante alguna fórmula adecuada.
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IStatePerceptron extends IState {

    /**
     * Codifica un Estado del Problema como entradas al perceptron utilizado, y
     * devuelve el valor de la neurona de entrada con el indice
     * {@code neuronIndex}. Recordar normalizar las entradas y salidas del
     * Perceptron.
     * <p>
     * @param neuronIndex neurona de la capa de entrada (la neurona 0 es la
     *                    primera)
     * <p>
     * @return valor de entrada normalizado a la neurona con el indice
     *         {@code neuronIndex}
     */
    public double translateToPerceptronInput(int neuronIndex);

}
