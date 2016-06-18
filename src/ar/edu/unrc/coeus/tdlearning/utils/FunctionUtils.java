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
package ar.edu.unrc.coeus.tdlearning.utils;

import static java.lang.Math.exp;
import java.util.function.Function;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class FunctionUtils {

    /**
     * Función de activación LINEAR
     */
    public static final Function<Double, Double> LINEAR = (value) ->
            value;
    /**
     * Función de activación LINEAR
     */
    public static final Function<Double, Double> LINEAR_DERIVATED = (value) ->
            1d;

    /**
     * Función de activación Sigmoideo
     */
    public static final Function<Double, Double> SIGMOID = (value) ->
            1d / (1d + exp(-value));
    /**
     * Derivada de la función de activación Sigmoideo. {@code fValue} debe ser SIGMOID(value) por
     * cuestiones de optimización
     */
    public static final Function<Double, Double> SIGMOID_DERIVATED = (fValue) ->
            fValue * (1d - fValue);

    /**
     * Función de activación TANH
     */
    public static final Function<Double, Double> TANH = (value) ->
            Math.tanh(
                    value);
    /**
     * Derivada de la función de activación TANH. {@code fValue} debe ser TANH(value) por cuestiones
     * de optimización
     */
    public static final Function<Double, Double> TANH_DERIVATED = (fValue) ->
            1d - fValue * fValue;

    private FunctionUtils() {
    }
}
