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
package ar.edu.unrc.tdlearning.training.perceptrons;

import java.util.ArrayList;
import java.util.List;

/**
 * Util ara el calculo de cache de momentums ya que no es necesario todos los
 * otros valores
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class PartialNeuron {

    private final List<Double> weights;

    /**
     *
     * @param weightsQuantity
     * @param outputLayerNeuronQuantity
     */
    public PartialNeuron(final int weightsQuantity, final int outputLayerNeuronQuantity) {
        if ( weightsQuantity > 0 ) {
            weights = new ArrayList(weightsQuantity + 1);
            for ( int i = 0; i < weightsQuantity + 1; i++ ) {
                weights.add(null);
            }
        } else {
            weights = null;
        }
    }

    /**
     * @return the bias, null si no tiene bias
     */
    public Double getBias() {
        return getWeights().get(getWeights().size() - 1);
    }

    /**
     * @param newBias
     */
    public void setBias(final Double newBias) {
        getWeights().set(getWeights().size() - 1, newBias);
    }

    /**
     * @param previousLayerNeuronIndex <p>
     * @return the weights
     */
    public Double getWeight(final int previousLayerNeuronIndex) {
        return getWeights().get(previousLayerNeuronIndex);
    }

    /**
     * @return the weights
     */
    public List<Double> getWeights() {
        return weights;
    }

    /**
     * @param previousLayerNeuronIndex
     * @param weight
     */
    public void setWeight(final int previousLayerNeuronIndex, final Double weight) {
        getWeights().set(previousLayerNeuronIndex, weight);
    }

}
