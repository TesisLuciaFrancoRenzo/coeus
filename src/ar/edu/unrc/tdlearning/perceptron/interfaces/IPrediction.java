/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

/**
 * Prediccion realizada por el perceptron sobre un problema dado un estado.
 * Tambien puede representar la prediccion acumulativa la cual contiene sumada
 * la recompensa parcial en la prediccion realizada del perceptron (asegurarse
 * de sumar dicho valor manualmente, ya que el perceptron no predice la
 * recompensa parcial). Dos salidas deben poder compararse, para saber cual
 * predice un mejor futuro que otra. Esta implementacion debe permitir ordenar
 * una lista de predicciones de menor a mayor beneficio futuro (mediante la
 * implementacion de {@code Comparable<IPrediction>}). El algoritmo de
 * entrenamiento no necesita entender que contiene la predicción o como se
 * interpreta, pero si necesita saber comparar y ordenar predicciones.
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IPrediction extends Comparable<IPrediction> {

    /**
     *
     * @param reward recompensa a sumar a la prediccion. Solo util para las
     *               predicciones acumulativas de TDLearning
     */
    public void addReword(IReward reward);

    /**
     * multiplica los elementos de la prediccion por un valor
     * {@code probability}. Solo util para el lmetodo de TDLearningState
     * <p>
     * @param probability
     */
    public void multiplyBy(double probability);

}
