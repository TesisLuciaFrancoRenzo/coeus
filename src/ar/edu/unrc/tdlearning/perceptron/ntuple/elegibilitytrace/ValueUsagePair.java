/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace;

/**
 *
 * @author franco
 */
public class ValueUsagePair {

    private int usagesLeft;
    private double value;

    /**
     *
     */
    public ValueUsagePair() {
        value = 0;
        usagesLeft = 0;
    }

    /**
     * @return the usagesLeft
     */
    public int getUsagesLeft() {
        return usagesLeft;
    }

    /**
     * @param usagesLeft the usagesLeft to set
     */
    public void setUsagesLeft(int usagesLeft) {
        this.usagesLeft = usagesLeft;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     *
     */
    public void use() {
        usagesLeft--;
    }

    void reset() {
        value = 0;
        usagesLeft = 0;
    }

}
