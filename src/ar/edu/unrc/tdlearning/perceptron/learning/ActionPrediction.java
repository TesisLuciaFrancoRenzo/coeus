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

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;

/**
 * Tupla que contiene una acción y la predicción que calcula el perceptron sobre
 * el final del problema, si es que se toma dicha acción
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ActionPrediction implements Comparable<ActionPrediction> {

    /**
     * Acción asociada a la prediccion de llegar al final del juego.
     */
    private final IAction action;
    private final Double numericRepresentation;

    /**
     *
     * @param action                acción relacionada a {@code prediction}
     * @param numericRepresentation para comparar diferentes
     *                              {@code ActionPrediction} cuando hay varias
     *                              neuronas de salida
     */
    public ActionPrediction(IAction action, Double numericRepresentation) {
        this.action = action;
        this.numericRepresentation = numericRepresentation;
    }

    @Override
    public int compareTo(ActionPrediction other) {
        return numericRepresentation.compareTo(other.getNumericRepresentation());
    }

    /**
     * @return acción asociada a la prediccíon del final del problema
     */
    public IAction getAction() {
        return action;
    }

    /**
     * @return the numericRepresentation
     */
    public double getNumericRepresentation() {
        return numericRepresentation;
    }

}
