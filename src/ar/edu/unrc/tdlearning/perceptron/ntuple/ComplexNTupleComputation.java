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
public class ComplexNTupleComputation {
    private double derivatedOutput;

    private int[] indexes;
    private double output;

    /**
     * @return the derivatedOutput
     */
    public double getDerivatedOutput() {
        return derivatedOutput;
    }

    /**
     * @param derivatedOutput the derivatedOutput to set
     */
    public void setDerivatedOutput(double derivatedOutput) {
        this.derivatedOutput = derivatedOutput;
    }

    /**
     * @return the indexes
     */
    public int[] getIndexes() {
        return indexes;
    }

    /**
     * @param indexes the indexes to set
     */
    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    /**
     * @return the output
     */
    public double getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(double output) {
        this.output = output;
    }
}
