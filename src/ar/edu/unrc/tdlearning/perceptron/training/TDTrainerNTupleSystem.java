/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainerNTupleSystem extends TDTrainerPerceptron {

    /**
     *
     * @param perceptron
     */
    public TDTrainerNTupleSystem(IPerceptronInterface perceptron) {
        super(perceptron);
    }

    @Override
    protected NeuralNetCache createCache(IState state, NeuralNetCache oldCache) {
        int outputLayerNeuronQuantity = 1;

        // inicializamos la Cache o reciclamos alguna vieja
        NeuralNetCache currentCache;
        if ( oldCache == null ) {
            currentCache = new NeuralNetCache(perceptron.getLayerQuantity());
        } else {
            currentCache = oldCache;
        }
        IntStream
                .range(0, 2)
                .sequential() //no se puede en paralelo porque se necesitan las neuronas de la capa anterior para f(net) y otros
                .forEach(l -> {
                    //inicializamos la variable para que sea efectivamente final, y poder usar paralelismo funcional
                    int currentLayerIndex = l;
                    //creamos una capa o reciclamos una vieja
                    Layer layer;
                    if ( oldCache == null ) {
                        layer = new Layer(perceptron.getNeuronQuantityInLayer(currentLayerIndex));
                    } else {
                        layer = oldCache.getLayer(currentLayerIndex);
                    }
                    //recorremos en paralelo cada neurona que deberia ir ne la capa, la inicializamos, y la cargamos en dicha capa
                    IntStream
                    .range(0, perceptron.getNeuronQuantityInLayer(currentLayerIndex))
                    .parallel()
                    .forEach(currentNeuronIndex -> {
                        Neuron neuron;
                        if ( currentLayerIndex == 0 ) {
                            //configuramos la neurona de entrada creando una o reciclando una vieja
                            if ( oldCache == null ) {
                                neuron = new Neuron(0, 0);
                            } else {
                                neuron = (Neuron) oldCache.getNeuron(currentLayerIndex, currentNeuronIndex);
                            }
                            neuron.setOutput(1d);
                            neuron.setDerivatedOutput(null);

                        } else {
                            // iniciamos variables efectivamente constantes para la programacion funcional
                            int previousLayer = currentLayerIndex - 1;
                            //configuramos la neurona creando una o reciclando una vieja
                            if ( oldCache == null ) {
                                neuron = new Neuron(perceptron.getNeuronQuantityInLayer(previousLayer), outputLayerNeuronQuantity);
                            } else {
                                neuron = (Neuron) oldCache.getNeuron(currentLayerIndex, currentNeuronIndex);
                                neuron.clearDeltas();
                            }
                            if ( perceptron.hasBias(currentLayerIndex) ) {
                                //TODO hacer testing para redes neuronales con capas con y sin bias, MIXTO
                                neuron.setBias(perceptron.getBias(currentLayerIndex, currentNeuronIndex));
                            }
                            //net = SumatoriaH(w(i,h,m)*a(h,m))
                            Double net = IntStream
                            .range(0, perceptron.getNeuronQuantityInLayer(previousLayer))
                            .parallel()
                            .mapToDouble(previousLayerNeuronIndex -> {
                                //cargamos el peso que conecta las 2 neuronas
                                neuron.setWeight(previousLayerNeuronIndex,
                                        perceptron.getWeight(currentLayerIndex, currentNeuronIndex, previousLayerNeuronIndex));
                                // devolvemmos la multiplicacion para luego sumar
//                                assert !((Neuron) currentCache.getNeuron(previousLayer, previousLayerNeuronIndex)).getOutput().isNaN();
                                return ((Neuron) currentCache.getNeuron(previousLayer, previousLayerNeuronIndex)).getOutput()
                                * neuron.getWeight(previousLayerNeuronIndex);
                            }).sum();
                            if ( perceptron.hasBias(currentLayerIndex) ) {
                                net += neuron.getBias();
                            }
                            neuron.setOutput(perceptron.getActivationFunction(currentLayerIndex).apply(net));

//                            assert !neuron.getOutput().isNaN();
                            neuron.setDerivatedOutput(perceptron.getDerivatedActivationFunction(currentLayerIndex).apply(neuron.getOutput()));
//                            assert !neuron.getDerivatedOutput().isNaN();
                        }
                        //cargamos la nueva neurona, si es que creamos una nueva cache
                        if ( oldCache == null ) {
                            layer.setNeuron(currentNeuronIndex, neuron);
                        }
                    });
                    //cargamos la nueva capa, si es que creamos una nueva cache
                    if ( oldCache == null ) {
                        currentCache.setLayer(currentLayerIndex, layer);
                    }
                });
        return currentCache;
    }

    /**
     *
     */
    @Override
    protected void sinchornizeCaches() {

    }
}
