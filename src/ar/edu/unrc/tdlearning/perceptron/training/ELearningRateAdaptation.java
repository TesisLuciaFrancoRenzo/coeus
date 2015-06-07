/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public enum ELearningRateAdaptation {

    /**
     * las constantes de aprendizaje van disminuyendo a travez del tiempo
     * mediante la formula: µ(t) = µ(0)/(1 + t/T)
     */
    annealing,
    /**
     * Utiliza los valores de alpha fijos a travez del tiempo
     */
    fixed
}
