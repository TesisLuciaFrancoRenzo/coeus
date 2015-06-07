/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IStateNTuple {

    /**
     *
     * @param nTupleIndex
     * <p>
     * @return
     */
    public List<SamplePointState> getNTuple(int nTupleIndex);

}
