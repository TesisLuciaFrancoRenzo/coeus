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

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IProblemToTrain extends IProblemRunner {

    /**
     *
     * @return el Actor (jugador si es un juego) que se va a entrenar
     */
    public IActor getActorToTrain();

    /**
     *
     * @param finalState
     * @param outputNeuron <p>
     * @return
     */
    public double getFinalReward(final IState finalState, final int outputNeuron);

    /**
     * Se debe inicializar el problema y avanzar hasta el punto en el que el
     * {@code actor} le toque actuar por primera vez
     * <p>
     * @param actor que se va a aentrenar durante el problema.
     * <p>
     * @return Inicializa el problema y devuelve su estado inicial
     */
    public IState initialize(final IActor actor);

    //TODO actualizar descripcion!
    /**
     * Este metodo debe:
     * <p>
     * 1) crear un estado que llamaremos 'nextState', que comineza siendo igual
     * a @afterstate.
     * <p>
     * 2) modificar 'nextState' aplicandole las acciones no deterministicas que
     * se aplican luego de las acciones deterministicas realizadas en
     * <p>
     * @afterstate, hasta llegar al proximo estado (turno)
     * <p>
     * 3) retornar 'nextState'
     * <p>
     * @param afterstate estado de transicion (luego de aplicar una accion
     *                   deterministica al estado actual del problema)
     * <p>
     * @return el estado 'nextState', que representa el siguiente turno o
     *         siguiente estado inicial (ver descripcion para mas detalles)
     */
    public IState computeNextTurnStateFromAfterstate(final IState afterstate);

    /**
     * Modifica el estado del problema para que el nuevo estado sea @newState.
     * En otras palabras, simboliza el final del turno anterior y se hacen
     * efectivos todos los movimientos de la IA y de lascciones no
     * deterministicas que le siguen.
     * <p>
     * @param nextTurnState nuevo estado inicial
     */
    public void setCurrentState(final IState nextTurnState);

    /**
     *
     * @param value <p>
     * @return
     */
    public double normalizeValueToPerceptronOutput(final Object value);

}
