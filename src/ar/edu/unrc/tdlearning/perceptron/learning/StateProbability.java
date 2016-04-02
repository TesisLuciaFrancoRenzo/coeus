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
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class StateProbability {

    private IStatePerceptron nextTurnState;
    private double probability;

    /**
     *
     * @param nextTurnState posible siguiente estado luego de calcular acciones
     *                      no deterministicas
     * @param probability   probabilidad de que ocurra {@code nextTurnState}
     *                      como estado efectivo en el siguiente turno
     */
    public StateProbability(IStatePerceptron nextTurnState, double probability) {
        this.nextTurnState = nextTurnState;
        this.probability = probability;
    }

    /**
     * @return the nextTurnState
     */
    public IStatePerceptron getNextTurnState() {
        return nextTurnState;
    }

    /**
     * @param nextTurnState posible siguiente estado luego de calcular acciones
     *                      no deterministicas
     */
    public void setNextTurnState(IStatePerceptron nextTurnState) {
        this.nextTurnState = nextTurnState;
    }

    /**
     * @return probabilidad de que ocurra {@code nextTurnState} como estado
     *         efectivo en el siguiente turno
     */
    public double getProbability() {
        return probability;
    }

    /**
     * @param probability probabilidad de que se alcance el estado
     *                    {@code nextTurnState}
     */
    public void setProbability(double probability) {
        if ( probability < 0 && probability > 1 ) {
            throw new IllegalArgumentException("probability debe estar en el rango 0<=probability<=1");
        }
        this.probability = probability;
    }
}
