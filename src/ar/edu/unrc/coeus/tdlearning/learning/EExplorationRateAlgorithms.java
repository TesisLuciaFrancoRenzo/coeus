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

/**
 *  * Establece como aumenta, disminuye o se mantiene el ritmo de exploración durante el entrenamiento. Los ritmos de
 * exploración van desde 0.0 a 1.0 y representan las probabilidades de que la acción actual sea tomada al azar en lugar
 * de ser una acción calculada por la red neuronal.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public enum EExplorationRateAlgorithms {

    /**
     * El valor de ritmo de exploración se mantiene constante durante todo el entrenamiento.
     */
    fixed,
    /**
     * El valor de ritmo de exploración decrementa o incrementa durante el entrenamiento dependiendo de una función
     * lineal.
     */
    linear
}
