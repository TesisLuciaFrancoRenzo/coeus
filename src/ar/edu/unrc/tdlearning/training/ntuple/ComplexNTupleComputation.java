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
package ar.edu.unrc.tdlearning.training.ntuple;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
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
    public void setDerivatedOutput(final double derivatedOutput) {
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
    public void setIndexes(final int[] indexes) {
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
    public void setOutput(final double output) {
        this.output = output;
    }
}
