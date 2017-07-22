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
 * Representación de un estado del problema, especializado para Perceptrones. Un estado debe poder ser traducido a entradas de una Red Neuronal
 * mediante alguna fórmula.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
interface IStatePerceptron
        extends IState {

    /**
     * Codifica un Estado del Problema como entradas a la red neuronal utilizada, y devuelve el valor de la neurona de entrada con el índice {@code
     * neuronIndex}. Recordar normalizar las entradas y salidas de la red neuronal de ser necesario.
     *
     * @param neuronIndex neurona de la capa de entrada (la neurona 0 es la primera)
     *
     * @return valor de entrada normalizado a la neurona con el índice {@code neuronIndex}
     */
    Double translateToPerceptronInput( final int neuronIndex );

}
