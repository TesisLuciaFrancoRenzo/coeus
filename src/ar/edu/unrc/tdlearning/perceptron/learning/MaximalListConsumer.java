/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author franco
 */
class MaximalListConsumer implements Consumer<ActionPrediction> {

    private List<ActionPrediction> list;

    public MaximalListConsumer() {
        list = new ArrayList<>(); //TODO optimizar: es mejor esta implementacion?
    }

    @Override
    public void accept(ActionPrediction actionPrediction) {
        if ( list.isEmpty() ) {
            list.add(actionPrediction);
        } else {
            int comparation = list.get(0).getPrediction().compareTo(actionPrediction.getPrediction());
            if ( comparation == 0 ) {
                list.add(actionPrediction);
            } else if ( comparation < 0 ) {
                list.clear();
                list.add(actionPrediction);
            }
        }
    }

    public void combine(MaximalListConsumer other) {
        if ( list.isEmpty() ) {
            list = other.list;
        } else if ( !other.list.isEmpty() ) {
            int comparation = list.get(0).getPrediction().compareTo(other.list.get(0).getPrediction());

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
