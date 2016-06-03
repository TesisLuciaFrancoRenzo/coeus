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
package ar.edu.unrc.tdlearning.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Utilizado para acumular Predicciones y calcular la maxima o mas prometedora
 * de ellas
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class MaximalListConsumer implements Consumer<ActionPrediction> {

    private List<ActionPrediction> list;

    /**
     *
     */
    public MaximalListConsumer() {
        list = new ArrayList<>();
    }

    @Override
    public void accept(final ActionPrediction actionPrediction) {
        if ( list.isEmpty() ) {
            list.add(actionPrediction);
        } else {
            int comparation = list.get(0).compareTo(actionPrediction);
            if ( comparation == 0 ) {
                list.add(actionPrediction);
            } else if ( comparation < 0 ) {
                list.clear();
                list.add(actionPrediction);
            }
        }
    }

    /**
     *
     * @param other
     */
    public void combine(final MaximalListConsumer other) {
        if ( list.isEmpty() ) {
            list = other.list;
        } else if ( !other.list.isEmpty() ) {
            int comparation = list.get(0).compareTo(other.list.get(0));

            if ( comparation == 0 ) {
                list.addAll(other.list);
            } else if ( comparation < 0 ) {
                list = other.list;
            }
        }
    }

    /**
     * @return the list
     */
    public List<ActionPrediction> getList() {
        return list;
    }

}
