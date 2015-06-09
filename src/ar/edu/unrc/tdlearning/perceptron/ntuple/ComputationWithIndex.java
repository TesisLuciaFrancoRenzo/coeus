/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

/**
 *
 * @author Franco
 */
public class ComputationWithIndex {

    private int[] indexes;
    private double output;

    /**
     * @return the indexes
     */
    protected int[] getIndexes() {
        return indexes;
    }

    /**
     * @param indexes the indexes to set
     */
    protected void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    /**
     * @return the output
     */
    protected double getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    protected void setOutput(double output) {
        this.output = output;
    }
}
