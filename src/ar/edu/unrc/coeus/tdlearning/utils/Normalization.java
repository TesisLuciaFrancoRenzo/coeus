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

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Normalization {

    /**
     *
     * @param value          Value to denormalize.
     * @param actualHigh     The actual high from the sample data.
     * @param actualLow      The actual low from the sample data.
     * @param normalizedHigh The desired normalized high.
     * @param normalizedLow  The desired normalized low from the sample data.
     *
     * @return denormalized value
     */
    public final static double deNormalize(final double value,
            final double actualHigh,
            final double actualLow,
            final double normalizedHigh,
            final double normalizedLow) {
        final double result = ((actualLow - actualHigh) * value
                - normalizedHigh * actualLow + actualHigh
                * normalizedLow)
                / (normalizedLow - normalizedHigh);
        return result;
    }

    /**
     *
     * @param value          Value to normalize.
     * @param actualHigh     The actual high from the sample data.
     * @param actualLow      The actual low from the sample data.
     * @param normalizedHigh The desired normalized high.
     * @param normalizedLow  The desired normalized low from the sample data.
     *
     * @return normalized value
     */
    public final static double normalize(final double value,
            final double actualHigh,
            final double actualLow,
            final double normalizedHigh,
            final double normalizedLow) {
        if ( value > actualHigh ) {
            return normalizedHigh;
        } else if ( value < actualLow ) {
            return normalizedLow;
        } else {
            return ((value - actualLow) / (actualHigh - actualLow))
                    * (normalizedHigh - normalizedLow)
                    + normalizedLow;
        }
    }

}
