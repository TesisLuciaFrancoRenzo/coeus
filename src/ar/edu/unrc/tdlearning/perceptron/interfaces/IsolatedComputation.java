/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

/**
 * Los objetos instanciados con esta interfaz deben representar calculos que no
 * causan efectos colaterales en el objeto donde se estan ejecutando. Es util
 * para poder calcular el contenido en paralelo.
 * <p>
 * @author Franco
 * @param <T> tipo de salida esperada de la computacion
 */
public interface IsolatedComputation<T> {

    /**
     *
     * @return
     */
    public T compute();
}
