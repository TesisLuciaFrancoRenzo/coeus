/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

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
    public double getBias(int layerIndex, int neuronIndex);

    /**
     * Funcion de activacion de todas las neuronas del perceptron. Por motivos
     * matematicos, el perceptron debe tener la misma funcion de activacion en
     * todas las capas.
     * <p>
     * @param layerIndex <p>
     * @return fución de activación, cuyo parametro es "net"
     */
//TODO: revisar el comentario. No es necesario que todas las neuronas tiengan la misma fcion de activacion???
    public Function<Double, Double> getActivationFunction(int layerIndex);

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
    public Function<Double, Double> getDerivatedActivationFunction(int layerIndex);

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
    public int getNeuronQuantityInLayer(int layerIndex);

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
    public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer);

    /**
     *
     * @param layerIndex indice de una capa del perceptron
     * <p>
     * @return true si el perceptron posee bias en todas las neuronas de la capa
     *         {@code layerIndex}
     */
    public boolean hasBias(int layerIndex);

    /**
     * Cambia el valor de la bias de una neurona con el valor de
     * {@code correctedBias}
     * <p>
     * @param layerIndex    indice de una capa de neuronas
     * @param neuronIndex   indice de una neurona
     * @param correctedBias nueva bias corregida para la neurona indicada
     */
    public void setBias(int layerIndex, int neuronIndex, double correctedBias);

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
    public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight);
}
