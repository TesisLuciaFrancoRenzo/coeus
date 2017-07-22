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
 * Establece como aumenta, disminuye o se mantiene Alpha durante el entrenamiento.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
enum ELearningRateAdaptation {

    /**
     * Las constantes de aprendizaje alpha dejan de ser constantes, y van disminuyendo a través del tiempo mediante la formula: µ(t) = µ(0)/(1 + t/T)
     */
    ANNEALING,
    /**
     * Utiliza valores de alpha fijos a través del tiempo
     */
    FIXED
}
