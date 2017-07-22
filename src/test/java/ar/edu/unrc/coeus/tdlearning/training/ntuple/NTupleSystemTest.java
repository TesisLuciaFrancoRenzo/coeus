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
package ar.edu.unrc.coeus.tdlearning.training.ntuple;

import ar.edu.unrc.coeus.utils.FunctionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.pow;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NTupleSystemTest {

    /**
     * Test of getNeuron method, of class Layer.
     */
    @Test
    public
    void testFuseLut() {
        System.out.println("fuseLut");

        final Function< Double, Double > activationFunction        = FunctionUtils.LINEAR;
        final Function< Double, Double > derivedActivationFunction = FunctionUtils.LINEAR_DERIVED;
        final int                        maxTile                   = 2;

        final int[] nTuplesLength = new int[2];
        for ( int i = 0; i < 2; i++ ) {
            nTuplesLength[i] = 2;
        }

        final List< SamplePointValue > allSamplePointPossibleValues = new ArrayList<>();
        for ( int i = 0; i <= maxTile; i++ ) {
            allSamplePointPossibleValues.add(new CustomTile(i));
        }

        final List< NTupleSystem > nTupleSystems = new ArrayList<>(2);
        nTupleSystems.add(new NTupleSystem(allSamplePointPossibleValues, nTuplesLength, activationFunction, derivedActivationFunction, false));
        nTupleSystems.add(new NTupleSystem(allSamplePointPossibleValues, nTuplesLength, activationFunction, derivedActivationFunction, false));

        nTupleSystems.get(0).setWeights(new double[] { 5, 9, 20, 40, -30, 10.1, 10, 5, 9, 20, 40, -30, 10.1, 10, 5, 9, 20, 40 });
        nTupleSystems.get(1).setWeights(new double[] { 15, 11, 0, -20, 50, 9.9, 10, 15, 11, 0, -20, 50, 9.9, 10, 15, 11, 0, -20 });

        NTupleSystem.fuseLut(nTupleSystems);
        final double[] expResult = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
        assertThat("NTupleSystem 0 ", nTupleSystems.get(0).getLut(), is(expResult));
        assertThat("NTupleSystem 1 ", nTupleSystems.get(1).getLut(), is(expResult));
    }

    private
    class CustomTile
            implements SamplePointValue {

        private final int code;
        private final int gameValue;

        /**
         * @param num numero del tablero en base 2.
         */
        CustomTile( final int num ) {
            super();
            code = num;
            gameValue = ( code == 0 ) ? 0 : (int) pow(2, code);
        }

        @Override
        public
        boolean equals( final Object obj ) {
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            final CustomTile other = (CustomTile) obj;
            return code == other.code;
        }

        /**
         * @return código del valor del tile en base 2.
         */
        public
        int getCode() {
            return code;
        }


        /**
         * @return valor del tile.
         */
        public
        int getGameValue() {
            return gameValue;
        }

        @Override
        public
        int hashCode() {
            int hash = 7;
            hash = ( 37 * hash ) + code;
            return hash;
        }

        /**
         * @return true si el tile esta vacío.
         */
        public
        boolean isEmpty() {
            return code == 0;
        }
    }

}
