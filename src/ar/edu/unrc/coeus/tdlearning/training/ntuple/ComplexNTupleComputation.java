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
package ar.edu.unrc.coeus.tdlearning.training.ntuple;

/**
 * resultado que se obtiene al computar la salida de una red neuronal de tipo NTupla.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ComplexNTupleComputation {

    private double derivatedOutput;
    private int[] indexes;
    private double output;

    /**
     * @return la derivada de la salida de la red neuronal.
     */
    public double getDerivatedOutput() {
        return derivatedOutput;
    }

    /**
     * @param derivatedOutput nueva derivada de la salida de la red neuronal.
     */
    public void setDerivatedOutput(final double derivatedOutput) {
        this.derivatedOutput = derivatedOutput;
    }

    /**
     * @return los índices involucrados en el cálculo de la salida de la red neuronal.
     */
    public int[] getIndexes() {
        return indexes;
    }

    /**
     * @param indexes los índices involucrados en el cálculo de la salida de la red neuronal.
     */
    public void setIndexes(final int[] indexes) {
        this.indexes = indexes;
    }

    /**
     * @return salida de la red neuronal.
     */
    public double getOutput() {
        return output;
    }

    /**
     * @param output nueva salida de la red neuronal.
     */
    public void setOutput(final double output) {
        this.output = output;
    }
}
