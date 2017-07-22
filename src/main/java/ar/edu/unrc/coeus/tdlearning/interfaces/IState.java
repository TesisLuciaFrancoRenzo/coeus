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
 * Representa el estado del problema en diferentes turnos (y entre-turnos) del problema.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
interface IState {

    /**
     * @return una copia del {@code IState}.
     */
    IState getCopy();

    /**
     * Retorna la recompensa parcial calculada tras realizar las acciones deterministas, según la neurona de salida {@code outputNeuron}.
     *
     * @param outputNeuron neurona de salida
     *
     * @return recompensa parcial luego de aplicar la acción determinística que da este estado a la neurona {@code outputNeuron}.
     */
    double getStateReward( final int outputNeuron );

    /**
     * @return true si el estado es final para el problema.
     */
    boolean isTerminalState();

}
