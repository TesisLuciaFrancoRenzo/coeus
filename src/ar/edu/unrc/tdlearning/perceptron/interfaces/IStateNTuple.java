/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IStateNTuple extends IState {

    /**
     *
     * @param nTupleIndex <p>
     * @return estado de la NTupla con el indice {@code nTupleIndex}
     */
    public SamplePointState[] getNTuple(int nTupleIndex);

    /**
     *
     * @return
     */
    public double translateRealOutputToNormalizedPerceptronOutput();

    /**
     *
     * @return
     */
    public double translateRewordToNormalizedPerceptronOutput();

}
