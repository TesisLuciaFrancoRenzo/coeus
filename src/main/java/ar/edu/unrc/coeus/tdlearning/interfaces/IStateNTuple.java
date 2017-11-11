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

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;

/**
 * Representación de un estado del problema, especializado para NTuplas. Un estado debe poder ser traducido a entradas de una Red Neuronal mediante
 * alguna fórmula relacionada con NTuplas.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
interface IStateNTuple
        extends IState {

    /**
     * Calcula la NTupla con el índice {@code nTupleIndex} del estado.
     *
     * @param nTupleIndex índice de la NTupla.
     *
     * @return cálculo de la NTupla con el índice {@code nTupleIndex}
     */
    SamplePointValue[] getNTuple( final int nTupleIndex );

}
