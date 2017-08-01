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
package ar.edu.unrc.coeus.tdlearning.interfaces;

/**
 * Debe ser extendido por las clases que pueden hacer uso de los algoritmos provistos en esta librería con el objetivo de entrenar redes neuronales.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
interface IProblemToTrain
        extends IProblemRunner {

    /**
     * True si en este turno se puede realizar acciones al azar a modo de exploración. Esto permite por ejemplo, activar la exploración luego de
     * cierto turno especifico, evitando utilizar exploración en secciones del problema ya aprendidos.
     *
     * @param currentTurn turno actual de ejecución del problema sobre el cual se esta entrenando.
     *
     * @return true si se habilita utilizar la función de exploración en el turno {@code currentTurn}.
     */
    boolean canExploreThisTurn( long currentTurn );

    /**
     * Este método debe: <ol> <li>Crear un estado que llamaremos 'nextState', que comienza siendo igual a {@code afterState}, el cual ya contiene
     * la acciones determinísticas aplicada. <li>Modificar 'nextState' aplicándole las acciones estocásticas hasta que llegue al próximo estado.
     * <li>retornar 'nextState' </ol>
     * En caso de existir multiples jugadores, considerar todas las jugadas de los contrincantes de la IA dentro de la acciones es estocásticas.
     * @param afterState estado de transición, luego de aplicar una acción determinística.
     *
     * @return el estado 'nextState', que representa el siguiente turno o siguiente estado inicial, tras aplicar acciones estocásticas.
     */
    IState computeNextTurnStateFromAfterState( final IState afterState );

    /**
     * Se debe inicializar el problema y avanzar hasta que le toque actuar por primera vez a la IA.
     */
    IState initialize();

    /**
     * Normaliza {@code value} entre los valores permitidos para la salida de la red neuronal, asumiendo que todas las neuronas de salida utilizan la
     * misma función de normalización.
     *
     * @param value a normalizar.
     *
     * @return {@code value} normalizado.
     */
    double normalizeValueToPerceptronOutput( final Object value );

    /**
     * Modifica el estado del problema para que el nuevo estado sea {@code nextTurnState}. En otras palabras, simboliza el final del turno anterior y
     * se hacen efectivos todos los movimientos de la IA y de las acciones estocásticas que se aplicaron al calcular.
     *
     * @param nextTurnState nuevo estado inicial
     */
    void setCurrentState( final IState nextTurnState );

}
