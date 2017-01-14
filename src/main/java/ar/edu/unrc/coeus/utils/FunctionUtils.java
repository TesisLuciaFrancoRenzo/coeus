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
package ar.edu.unrc.coeus.utils;

import java.util.function.Function;

import static java.lang.Math.exp;

/**
 * Funciones y sus derivadas listas para utilizar como funciones de activación.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public final
class FunctionUtils {

    /**
     * Función lineal
     */
    public static final Function< Double, Double > LINEAR         = ( value ) -> value;
    /**
     * Derivada de la función lineal.
     */
    public static final Function< Double, Double > LINEAR_DERIVED = ( value ) -> 1.0d;

    /**
     * Función Sigmoideo
     */
    public static final Function< Double, Double > SIGMOID = ( value ) -> 1.0d / ( 1.0d + exp(-value) );

    /**
     * Derivada de la función Sigmoideo. {@code fValue} debe ser SIGMOID(value) por cuestiones de optimización.
     */
    public static final Function< Double, Double > SIGMOID_DERIVED = ( fValue ) -> fValue * ( 1.0d - fValue );

    /**
     * Función tangente hiperbólica.
     */
    public static final Function< Double, Double > TANH = Math::tanh;

    /**
     * Derivada de la función tangente hiperbólica. {@code fValue} debe ser TANH(value) por cuestiones de optimización.
     */
    public static final Function< Double, Double > TANH_DERIVED = ( fValue ) -> 1.0d - ( fValue * fValue );

    private
    FunctionUtils() {
        super();
    }
}
