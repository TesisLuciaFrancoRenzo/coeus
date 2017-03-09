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
package ar.edu.unrc.coeus.tdlearning.learning;

import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import org.jetbrains.annotations.NotNull;

/**
 * Tupla que contiene una acción y la predicción que calcula la red neuronal sobre la recompensa final del problema, si
 * es que se toma dicha acción.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class ActionPrediction
        implements Comparable< ActionPrediction > {

    private final IAction action;
    private final IState  afterState;
    private final Double  numericRepresentation;

    /**
     * Tupla que relaciona la salida de una red neuronal a una representación numérica {@code numericRepresentation}
     * (útil en caso de que existan varias neuronas de salida). Mientras mayor se el valor de
     * {@code numericRepresentation}, mas importancia se le dará a esta tupla a la hora de ser elegida.
     *
     * @param action                acción relacionada a {@code prediction}
     * @param numericRepresentation para comparar diferentes {@code ActionPrediction} cuando hay varias neuronas de salida
     * @param afterState
     */
    public
    ActionPrediction(
            final IAction action,
            final Double numericRepresentation,
            final IState afterState
    ) {
        super();
        this.action = action;
        this.numericRepresentation = numericRepresentation;
        this.afterState = afterState;
    }

    @Override
    public
    int compareTo( @NotNull final ActionPrediction other ) {
        return numericRepresentation.compareTo(other.numericRepresentation);
    }

    /**
     * @return acción asociada a la predicción de la recompensa final del problema.
     */
    public
    IAction getAction() {
        return action;
    }

    /**
     * @return estado intermedio calculado al tomar la acción, para no tener que ser recalculado en caso de ser elegida esta ActionPrediction
     */
    public
    IState getAfterState() {
        return afterState;
    }

    /**
     * @return la representación numérica de esta acción. Mientras mayor se el valor de {@code numericRepresentation}, mas importancia se le dará a
     * esta tupla a la hora de ser elegida.
     */
    public
    double getNumericRepresentation() {
        return numericRepresentation;
    }

}
