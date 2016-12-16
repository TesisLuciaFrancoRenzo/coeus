package ar.edu.unrc.coeus.tdlearning.utils;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by franc on 16/12/2016.
 */
public
class StatisticCalculator {
    private final int             capacity;
    private final DecimalFormat   formatter;
    private final Queue< Double > history;
    private final int             outputDecimals;
    private       double          average;
    private       int             itemCounter;

    public
    StatisticCalculator(
            int capacity,
            int outputDecimals
    ) {

        this.outputDecimals = outputDecimals;
        this.average = 0;
        this.itemCounter = 0;
        if ( capacity > 0 ) {
            this.capacity = capacity;
            this.history = new ArrayDeque<>(capacity);
        } else {
            this.capacity = 0;
            this.history = null;
        }
        if ( outputDecimals > 0 ) {
            StringBuilder pattern = new StringBuilder(outputDecimals);
            for ( int i = 0; i < outputDecimals; i++ ) {
                pattern.append('#');
            }
            formatter = new DecimalFormat("#." + pattern.toString());
        } else if ( outputDecimals == 0 ) {
            formatter = new DecimalFormat("#"); //TODO testear
        } else {
            formatter = null;
        }
    }

    public
    StatisticCalculator() {
        this(0, -1);
    }

    public
    StatisticCalculator( int outputDecimals ) {
        this(0, outputDecimals);
    }

    public synchronized
    void addSample( double sample ) {
        if ( history != null ) {
            if ( history.size() >= capacity ) {
                average -= history.remove();
                itemCounter--;
            }
            history.add(sample);
            average += sample;
            itemCounter++;
            assert itemCounter <= capacity;
        } else {
            average += sample;
            itemCounter++;
        }
    }

    public
    double getAverage() {
        return average / ( itemCounter * 1d );
    }

    public
    int getCapacity() {
        return capacity;
    }

    public
    Double getFullCapacityAverage() {
        if ( itemCounter == capacity ) {
            return getAverage();
        } else {
            return null;
        }
    }

    public
    int getItemCounter() {
        return itemCounter;
    }

    public
    int getOutputDecimals() {
        return outputDecimals;
    }

    public
    String printableAverage() {
        if ( itemCounter < 1 ) {
            return "?";
        } else {
            if ( formatter != null ) {
                return formatter.format(getAverage());
            } else {
                return Double.toString(getAverage());
            }
        }
    }

    public
    String printableFullCapacityAverage() {
        if ( itemCounter < capacity ) {
            return "?";
        } else {
            if ( formatter != null ) {
                return formatter.format(getAverage());
            } else {
                return Double.toString(getAverage());
            }
        }
    }

    public
    void reset() {
        average = 0;
        itemCounter = 0;
        if ( history != null ) {
            history.clear();
        }
    }
}
