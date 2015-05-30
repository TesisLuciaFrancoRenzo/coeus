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
public interface IState {

    /**
     *
     * @return true si el estado es final para el problema
     */
    public boolean isTerminalState();

    /**
     *
     * @param outputNeuronIndex <p>
     * @return
     */
    public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex);

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

    /**
     * Esta función solo se ejecuta sobre estados finales. Codifica un Estado
     * del Problema como salidas del perceptron utilizado, y retorna el valor
     * que deberia devolver de la neurona de salida con el indice
     * {@code neuronIndex} si es que se ejecuta el perceptron con este tablero
     * final. Recordar normalizar las entradas y salidas del Perceptron. Esta
     * función no deberia calcular una salida utilizando el perceptron, sino que
     * deberia traducir literalmente este estado final a una salida válida del
     * perceptron, tal y como deberia devolver el perceptron una vez que este
     * entrenado a su maximo teórico. Esta funcion solo se llamara al finalizar
     * el problema (o sea el estado debe ser FINAL) y se utiliza para comparar
     * los resultados predichos con los valores reales que debería haber
     * predicho.
     * <p>
     * @param neuronIndex neurona de la capa de salida (la neurona 0 es la
     *                    primera)
     * <p>
     * @return valor de salida normalizado de la neurona con el indice
     *         {@code neuronIndex} que deberia devolver el perceptron si se
     *         ejecuta con este estado (si es final).
     */
//    public double translateThisFinalStateToPerceptronOutput(int neuronIndex);
}
