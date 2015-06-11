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

//    /**
//     * Si se esta utilizando el metodo de prediccion por acumulacion de
//     * recompensas parciales, esta funcion debe retornar la recompensa real
//     * obtenida en el ultimo turno (no debe ser la recompesa predicha por el
//     * perceptron). Si no se utiliza el metodo acumulativo esta funcion debe
//     * retornar el valor real que representa el state como salida en lugar de
//     * utilizar el perceptron para calcularlo.
//     * <p>
//     * @param outputNeuronIndex <p>
//     * @return
//     */
//    public double translateRealOutputToNormalizedPerceptronOutputFrom(int outputNeuronIndex);
//    //TODO: verificar este comentario
//    /**
//     * Codifica la recompensa como salidas normalizadas del perceptron
//     * utilizado, y devuelve el valor de la neurona de salida con el indice
//     * {@code outputNeuronIndex}. Recordar normalizar las entradas y salidas del
//     * Perceptron.
//     * <p>
//     * @param outputNeuronIndex neurona de la capa de salida (la neurona 0 es la
//     *                          primera)
//     * <p>
//     * @return valor de recompensa normalizado a la neurona con el indice
//     *         {@code outputNeuronIndex}
//     */
//    public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex);
//    /**
//     * Utilizado para evaluar los estados terminales. La evaluacion de un IStatePerceptron
//     * final debe ser el valor real y no el predicho por el perceptron.
//     * <p>
//     * @return
//     */
//    public IPrediction translateFinalStateToPrediction();
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
