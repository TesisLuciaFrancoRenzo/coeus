package ar.edu.unrc.coeus.tdlearning.utils;

/**
 * Created by franc on 16/12/2016.
 */
public
class StatisticCalculator {
    long counter;
    private double average;

    public
    StatisticCalculator() {
        average = 0;
        counter = 0;
    }

    public synchronized
    void addSample( double sample ) {
        average += sample;
        counter++;
    }

    public
    double getAverage() {
        return average / ( counter * 1d );
    }
}
