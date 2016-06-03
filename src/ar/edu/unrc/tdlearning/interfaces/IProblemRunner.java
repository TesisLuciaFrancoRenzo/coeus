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
package ar.edu.unrc.tdlearning.interfaces;

import java.util.ArrayList;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IProblemRunner {

    /**
     *
     * @param output salida del perceptron no normalizada.<p>
     * @param actor  jugador actual que necesita interpretar {@code output}
     *               desde su punto de vista
     * <p>
     * @return un valor representativo que interpreta la salida del perceptron
     *         {@code output} desde el punto de vista del jugador {@code player}
     */
    public Double computeNumericRepresentationFor(final Object[] output, final IActor actor);

    /**
     *
     * @param value <p>
     * @return
     */
    public double denormalizeValueFromPerceptronOutput(final Object value);

    /**
     *
     * @param turnInitialState estado del poblema sobre el cual hacer calculos
     * <p>
     * @return una lista de todas las acciones validas que se pueden aplicar al
     *         estado s
     */
    public ArrayList<IAction> listAllPossibleActions(final IState turnInitialState);

    /**
     * Calcula el estado intermedio del turno, que es el estado al que llega el
     * problema inmediatamente luego de aplicar la accion deterministica 'a',
     * pero antes de aplicar las acciones no deterministicas. Al computar el
     * afterstate se debe cargar la puntuacion parcial obtenida en
     * {@code turnInitialState} para ser utilizado en diferentes algorimos.
     * <p>
     * @param turnInitialState estado inicial
     * @param action           accion a aplicar
     * <p>
     * @return estado intermedio deterministico resulante de aplicar la accion
     *         'a' al estado 's', con su recompensa parcial en caso de ser
     *         utilizado el calculo acumulativo en TDLearning
     */
    public IState computeAfterState(final IState turnInitialState, final IAction action);

    /**
     * Prediccion realizada por el perceptron de que tan bueno es el estado
     * {@code state}.
     * <p>
     * @param state estado intermedio si se utiliza afterstate, o inicio de
     *              estado
     * <p>
     * @return prediccion del perceptron, normalizado (si la funcion de
     *         activacion necesita normalizacion).
     */
    public Object[] evaluateBoardWithPerceptron(final IState state);

}
