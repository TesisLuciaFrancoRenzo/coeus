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
 * Algoritmos de entrenamiento para diferentes tipos de redes neuronales.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class Trainer {

    /**
     * Tasa de descuento, entre [0,1].
     */
    protected double gamma;

    /**
     * escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     */
    protected double lambda;

    /**
     * true si se permite reiniciar las trazas de elegibilidad en caso de movimientos al azar durante el entrenamiento.
     */
    protected boolean replaceEligibilityTraces;

    /**
     * Reinicia el entrenador.
     */
    public abstract
    void reset();

    /**
     * Entrenamos la red neuronal con un turno. Incluye la actualización de las bias. Es necesario invocar el método
     * {@code train} desde el turno 1. Esto significa que si llamamos a este método desde el turno 6, primero hay que
     * llamarlo desde el turno 5. Para llamarlo desde el turno 5, primero hay que invocarlo desde el turno 4, etc.
     *
     * @param problem            problema a solucionar.
     * @param state              estado del problema en el turno {@code currentTurn}
     * @param nextTurnState      estado del problema en el turno siguiente a {@code currentTurn}
     * @param alpha              tasa de aprendizaje.
     * @param concurrencyInLayer true en las capas que se puede calcular usando concurrencia.
     * @param isARandomMove      true si la acción realizada en el turno {@code currentTurn} fue al azar en lugar de calculada por la red neuronal.
     */
    public abstract
    void train(
            final IProblemToTrain problem,
            final IState state,
            final IState nextTurnState,
            final double[] alpha,
            final boolean[] concurrencyInLayer,
            final boolean isARandomMove
    );
}
