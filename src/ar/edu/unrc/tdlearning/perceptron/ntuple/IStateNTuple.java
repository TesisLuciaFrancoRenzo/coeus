/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IStateNTuple {

    /**
     *
     * @param nTupleIndex <p>
     * @return estado de la NTupla con el indice {@code nTupleIndex}
     */
    public SamplePointState[] getNTuple(int nTupleIndex);

}
