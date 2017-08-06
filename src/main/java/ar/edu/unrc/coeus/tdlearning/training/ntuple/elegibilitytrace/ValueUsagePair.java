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
package ar.edu.unrc.coeus.tdlearning.training.ntuple.elegibilitytrace;

/**
 * Par que almacena valores de los pesos de la traza de elegibilidad, en conjunto a la cantidad de usos que le queda dentro de la traza antes de
 * desaparecer o ser actualizado con un nuevo valor.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class ValueUsagePair {

    private int    usagesLeft;
    private double value;

    /**
     * Elemento de la traza de elegibilidad.
     */
    public
    ValueUsagePair() {
        value = 0.0d;
        usagesLeft = 0;
    }

    /**
     * @return cantidad de usos que le queda dentro de la traza.
     */
    public
    int getUsagesLeft() {
        return usagesLeft;
    }

    /**
     * @param usagesLeft nueva cantidad de usos dentro de la traza.
     */
    public
    void setUsagesLeft( final int usagesLeft ) {
        this.usagesLeft = usagesLeft;
    }

    /**
     * @return valor actual del elemento de la traza.
     */
    public
    double getValue() {
        return value;
    }

    /**
     * @param value nuevo valor del elemento de la traza.
     */
    public
    void setValue( final double value ) {
        this.value = value;
    }

    void reset() {
        value = 0.0d;
        usagesLeft = 0;
    }

    /**
     * Utiliza el elemento de la traza, disminuyendo su vida.
     */
    public
    void use() {
        usagesLeft--;
    }

}
