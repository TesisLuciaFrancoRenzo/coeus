/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.tdlearning.interfaces;

import java.util.function.Function;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IPerceptronInterface {

    /**
     * Bias de la neurona. Las capas de entrada no deberian tener bias.
     * <p>
     * @param layerIndex  indice de una capa de neuronas
     * @param neuronIndex indice de una neurona
     * <p>
     * @return valor de la bias de la neurona
     */
    public double getBias(final int layerIndex, final int neuronIndex);

    /**
     * Funcion de activacion de todas las neuronas del perceptron en la capa
     * {@code layerIndex}.
     * <p>
     * @param layerIndex <p>
     * @return fución de activación, cuyo parametro es "net"
     */
    public Function<Double, Double> getActivationFunction(final int layerIndex);

    /**
     * Derivada de la funcion de activacion de las neuronas del perceptron.
     * Tener en cuenta que por motivos de optimizacion, el parametro de la
     * funcion debe tener aplicada la funcion sin derivar. POr ejemplo, si la
     * funcion sigmoideo es "(value) -> 1d / (1d + exp(-value))", la funcion de
     * derivada deberia ser "(fValue) -> fValue * (1 - fValue)" (siendo fValue
     * un valor pero con previa funcion sigmoideo aplicada) y no "(value) ->
     * sigmoid.apply(value) * (1 - sigmoid.apply(value))" ya que de esta ultima
     * manera, recalulariamos 2 veces sigmoideo en lugar de una vez.
     * <p>
     * @param layerIndex <p>
     * @return derivada de la fución de activación, cuyo parametro es "f(net)" y
     *         no "net"
     */
    public Function<Double, Double> getDerivatedActivationFunction(final int layerIndex);

    /**
     *
     * @return cantidad de capas que tiene el perceptron (inlcuidas las capas de
     *         entrada y de salida)
     */
    public int getLayerQuantity();

    /**
     *
     * @param layerIndex capa de interes (el valor 0 debe pertenecer a la capa
     *                   de entrada)
     * <p>
     * @return
     */
    public int getNeuronQuantityInLayer(final int layerIndex);

    /**
     * Peso de la conexion entre dos neuronas. Las dos neuronas deben estar en
     * capas contiguas y la primera coordenada introducida correspone a la
     * neurona que este en la capa mas a hacia adelante con respecto a la salida
     * del perceptron. Ya que las coordenadas de la segunda neurona corresponden
     * a una capa anterior, solo se pide como parametro el indice de la neurona
     * y no su capa.
     * <p>
     * @param layerIndex               indice de la capa de la neurona de mas
     *                                 hacia adelante
     * @param neuronIndex              indice de la neurona de mas hacia
     *                                 adelante
     * @param neuronIndexPreviousLayer indice de la neurona de mas hacia atras
     * <p>
     * @return peso entre las dos neuronas especificadas con las coordenadas
     *         establecidas en los otros parametros
     */
    public double getWeight(final int layerIndex, final int neuronIndex, final int neuronIndexPreviousLayer);

    /**
     *
     * @param layerIndex indice de una capa del perceptron
     * <p>
     * @return true si el perceptron posee bias en todas las neuronas de la capa
     *         {@code layerIndex}
     */
    public boolean hasBias(final int layerIndex);

    /**
     * Cambia el valor de la bias de una neurona con el valor de
     * {@code correctedBias}
     * <p>
     * @param layerIndex    indice de una capa de neuronas
     * @param neuronIndex   indice de una neurona
     * @param correctedBias nueva bias corregida para la neurona indicada
     */
    public void setBias(final int layerIndex, final int neuronIndex, final double correctedBias);

    /**
     * Cambia el peso entre dos neuronas con el valor de {@code correctedWeight}
     * <p>
     * @param layerIndex               indice de la capa de la neurona de mas
     *                                 hacia adelante
     * @param neuronIndex              indice de la neurona de mas hacia
     *                                 adelante
     * @param neuronIndexPreviousLayer indice de la neurona de mas hacia atras
     * @param correctedWeight          nuevo peso corregido entre las dos
     *                                 neuronas indicadas
     */
    public void setWeight(final int layerIndex, final int neuronIndex, final int neuronIndexPreviousLayer, final double correctedWeight);
}
