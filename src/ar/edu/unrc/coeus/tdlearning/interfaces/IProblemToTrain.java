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
 * Debe ser extendido por las clases que pueden hacer uso de los algoritmos provistos en esta
 * librería con el objetivo de entrenar redes neuronales.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public interface IProblemToTrain extends IProblemRunner {

    /**
     * En caso de que un problema se resuelva entre varios actores, esta función debe retornar al
     * actor del turno corriente del entrenamiento.
     *
     * @return el Actor que se va a entrenar en el turno actual.
     */
    public IActor getActorToTrain();

    /**
     * Retorna la recompensa final total obtenida al llegar al final del problema, por cada neurona
     * de salida.
     *
     * @param finalState   estado final del problema.
     * @param outputNeuron neurona de salida.
     *
     * @return
     */
    public double getFinalReward(final IState finalState,
            final int outputNeuron);

    /**
     * Se debe inicializar el problema y avanzar hasta el punto en el que el {@code actor} le toque
     * actuar por primera vez
     *
     * @param actor que se va a entrenar durante el problema.
     *
     * @return estado inicial del problema en el momento que le toque actual al {@code actor}.
     */
    public IState initialize(final IActor actor);

    /**
     * Este método debe:
     * <p>
     * <ol>
     * <li>Crear un estado que llamaremos 'nextState', que comienza siendo igual a
     * {@code afterstate}, el cual ya contiene la acciones determinísticas aplicada.
     * <li>Modificar 'nextState' aplicándole las acciones no determinísticas hasta que llegue al
     * próximo estado.
     * <li>retornar 'nextState'
     * </ol>
     * <p>
     * @param afterstate estado de transición, luego de aplicar una acción determinística.
     *
     * @return el estado 'nextState', que representa el siguiente turno o siguiente estado inicial,
     *         tras aplicar acciones no determinísticas.
     */
    public IState computeNextTurnStateFromAfterstate(final IState afterstate);

    /**
     * Modifica el estado del problema para que el nuevo estado sea {@code nextTurnState}. En otras
     * palabras, simboliza el final del turno anterior y se hacen efectivos todos los movimientos de
     * la IA y de las acciones no determinísticas que se aplicaron al calcular.
     *
     * @param nextTurnState nuevo estado inicial
     */
    public void setCurrentState(final IState nextTurnState);

    /**
     * Normaliza {@code value} entre los valores permitidos para la salida de la red neuronal,
     * asumiendo que todas las neuronas de salida utilizan la misma función de normalización.
     *
     * @param value a normalizar.
     *
     * @return {@code value} normalizado.
     */
    public double normalizeValueToPerceptronOutput(final Object value);

}
