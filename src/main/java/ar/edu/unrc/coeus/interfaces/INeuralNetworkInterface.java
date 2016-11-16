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
package ar.edu.unrc.coeus.interfaces;

import java.util.function.Function;

/**
 * interfaz que se debe implementar con el objetivo de comunicar la red neuronal utilizada con los métodos de
 * entrenamiento de esta librería.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
interface INeuralNetworkInterface {

    //TODO add bias entry point configuration, like encog

    /**
     * función de activación de todas las neuronas de la red neuronal en la capa {@code layerIndex}.
     *
     * @param layerIndex índice de la capa.
     *
     * @return función de activación, cuyo parámetro es "net"
     */
    Function<Double, Double> getActivationFunction(final int layerIndex);

    /**
     * Bias asociada a la neurona {@code neuronIndex} en la capa {@code layerIndex}. Las capas de entrada no deberían
     * tener bias.
     *
     * @param layerIndex  índice de una capa de neuronas
     * @param neuronIndex índice de una neurona
     *
     * @return valor de la bias de la neurona
     */
    double getBias(
            final int layerIndex,
            final int neuronIndex
    );

    /**
     * Derivada de la función de activación de las neuronas de la red neuronal en la capa {@code layerIndex}. Tener en
     * cuenta que por motivos de optimización, el parámetro de la función debe tener aplicada la función sin derivar.
     * Por ejemplo, si la función sigmoideo es:<br>
     * "(value) -> 1d / (1d + exp(-value))"<br>
     * la función derivada debería ser:<br>
     * "(fValue) -> fValue * (1 - fValue)"<br>
     * siendo fValue un valor, con previa función sigmoideo ya aplicada. No debería ser:<br>
     * "(value) -> sigmoid.apply(value) * (1 - sigmoid.apply(value))"<br>
     * ya que de esta ultima manera, recalculamos 2 veces sigmoideo en lugar de una vez.
     *
     * @param layerIndex índice de la capa.
     *
     * @return derivada de la función de activación, cuyo parámetro es "f(net)" y no "net".
     */
    Function<Double, Double> getDerivedActivationFunction(final int layerIndex);

    /**
     * Cantidad de capas de la red neuronal.
     *
     * @return cantidad de capas que tiene la red neuronal (incluidas las capas de entrada y de salida)
     */
    int getLayerQuantity();

    /**
     * @param layerIndex índice de la capa (el valor 0 debe pertenecer a la capa de entrada).
     *
     * @return cantidad de neuronas en la capa {@code layerIndex}.
     */
    int getNeuronQuantityInLayer(final int layerIndex);

    /**
     * Peso de la conexión entre dos neuronas. Las dos neuronas deben estar en capas contiguas y la primera coordenada
     * introducida corresponde a la neurona que este en la capa mas cercana a la salida de la red neuronal. Ya que las
     * coordenadas de la segunda neurona corresponden a una capa anterior, solo se pide como parámetro el índice de la
     * neurona y no el de su capa.
     *
     * @param layerIndex               índice de la capa de la neurona de mas cercana a la capa de salida.
     * @param neuronIndex              índice de la neurona de mas cercana a la capa de salida.
     * @param neuronIndexPreviousLayer índice de la neurona de mas alejada de la capa de salida.
     *
     * @return peso entre las dos neuronas especificadas con las coordenadas establecidas.
     */
    double getWeight(
            final int layerIndex,
            final int neuronIndex,
            final int neuronIndexPreviousLayer
    );

    /**
     * @param layerIndex índice de una capa de la red neuronal.
     *
     * @return true si la red neuronal posee bias en todas las neuronas de la capa {@code layerIndex}
     */
    boolean hasBias(final int layerIndex);

    /**
     * Cambia el valor de la bias de la neurona {@code neuronIndex} en la capa {@code layerIndex} con el valor de
     * {@code newBias}
     *
     * @param layerIndex  índice de una capa de neuronas
     * @param neuronIndex índice de una neurona
     * @param newBias     nuevo bias para la neurona indicada
     */
    void setBias(
            final int layerIndex,
            final int neuronIndex,
            final double newBias
    );

    /**
     * Cambia el peso entre la neurona {@code neuronIndex} en la capa {@code layerIndex} (la mas cercana a la capa de
     * salida) y la neurona {@code neuronIndexPreviousLayer} de la capa mas alejada a capa de salida, con el valor de
     * {@code newWeight}.
     *
     * @param layerIndex               índice de la capa de la neurona de mas cercana a la capa de salida.
     * @param neuronIndex              índice de la neurona de mas cercana a la capa de salida.
     * @param neuronIndexPreviousLayer índice de la neurona de mas alejada a la capa de salida.
     * @param newWeight                nuevo peso entre las dos neuronas indicadas
     */
    void setWeight(
            final int layerIndex,
            final int neuronIndex,
            final int neuronIndexPreviousLayer,
            final double newWeight
    );
}
