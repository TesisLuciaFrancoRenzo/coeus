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

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public final
class Normalization {

    private
    Normalization() {
        super();
    }

    /**
     * Desnormaliza un valor.
     *
     * @param value          valor a desnormalizar.
     * @param actualHigh     valor mas alto posible de la muestra.
     * @param actualLow      valor mas bajo posible de la muestra.
     * @param normalizedHigh valor mas alto deseado a normalizar.
     * @param normalizedLow  valor mas bajo deseado a normalizar.
     *
     * @return valor desnormalizado.
     */
    public static
    double deNormalize(
            final double value,
            final double actualHigh,
            final double actualLow,
            final double normalizedHigh,
            final double normalizedLow
    ) {
        return ( ( ( ( actualLow - actualHigh ) * value ) - ( normalizedHigh * actualLow ) ) + ( actualHigh * normalizedLow ) ) /
               ( normalizedLow - normalizedHigh );
    }

    /**
     * Normaliza un valor.
     *
     * @param value          valor a normalizar.
     * @param actualHigh     valor mas alto posible de la muestra.
     * @param actualLow      valor mas bajo posible de la muestra.
     * @param normalizedHigh valor mas alto deseado a normalizar.
     * @param normalizedLow  valor mas bajo deseado a normalizar.
     *
     * @return valor normalizado.
     */
    public static
    double normalize(
            final double value,
            final double actualHigh,
            final double actualLow,
            final double normalizedHigh,
            final double normalizedLow
    ) {
        if ( value > actualHigh ) {
            return normalizedHigh;
        } else if ( value < actualLow ) {
            return normalizedLow;
        } else {
            return ( ( ( value - actualLow ) / ( actualHigh - actualLow ) ) * ( normalizedHigh - normalizedLow ) ) + normalizedLow;
        }
    }

}
