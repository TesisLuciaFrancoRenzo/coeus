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
package ar.edu.unrc.coeus.tdlearning.training;

import ar.edu.unrc.coeus.tdlearning.interfaces.IProblemToTrain;
import ar.edu.unrc.coeus.tdlearning.interfaces.IState;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract class Trainer {

    /**
     *
     */
    protected double gamma;

    /**
     * Constante que se encuentra en el intervalo [0,1]
     */
    protected double lambda;

    /**
     *
     */
    protected boolean replaceEligibilityTraces;

    /**
     *
     */
    public abstract void reset();

    /**
     *
     * @param problem
     * @param state
     * @param nextTurnState
     * @param currentAlpha
     * @param concurrencyInLayer
     * @param aRandomMove
     */
    public abstract void train(
            final IProblemToTrain problem,
            final IState state,
            final IState nextTurnState,
            final double[] currentAlpha,
            final boolean[] concurrencyInLayer,
            final boolean aRandomMove
    );
}
