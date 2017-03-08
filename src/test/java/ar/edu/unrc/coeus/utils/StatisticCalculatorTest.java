package ar.edu.unrc.coeus.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class StatisticCalculatorTest {
    @Test
    public
    void addSample()
            throws Exception {
        final StatisticCalculator estimator = new StatisticCalculator(5, 2);
        estimator.addSample(10.4);
        estimator.addSample(4.0);
        assertThat(estimator.getItemCounter(), is(2));

        estimator.addSample(5.0);
        estimator.addSample(9.0);
        assertThat(estimator.getItemCounter(), is(4));

        estimator.addSample(2.0);
        assertThat(estimator.getItemCounter(), is(5));

        estimator.addSample(1.0);
        assertThat(estimator.getItemCounter(), is(5));
        estimator.addSample(1.0);
        estimator.addSample(1.0);
        estimator.addSample(1.0);
        estimator.addSample(1.0);
        assertThat(estimator.getItemCounter(), is(5));

        assertThat(estimator.getAverage(), is(1.0d));
    }

    @Test
    public
    void getAverage()
            throws Exception {
        final StatisticCalculator sc = new StatisticCalculator();
        sc.addSample(10.0);
        sc.addSample(20.5);
        sc.addSample(2.698);
        sc.addSample(5.4678);
        sc.addSample(-26.5);
        sc.addSample(0);
        assertThat(sc.getAverage(), is(2.027633333333334d));
    }

    @Test
    public
    void printableFullCapacityAverage()
            throws Exception {
        StatisticCalculator estimator = new StatisticCalculator(5, 2);
        estimator.addSample(10.4);
        estimator.addSample(1.5);
        assertThat(estimator.printableFullCapacityAverage(), is("?"));

        estimator.addSample(5.0);
        estimator.addSample(9.0);
        assertThat(estimator.printableFullCapacityAverage(), is("?"));

        estimator.addSample(2.0);
        assertThat(estimator.printableFullCapacityAverage(), is("5,58"));

        estimator.addSample(2.0);
        estimator.addSample(2.0);
        estimator.addSample(3.0);
        estimator.addSample(2.0);
        estimator.addSample(3.0);
        assertThat(estimator.printableFullCapacityAverage(), is("2,4"));

        estimator = new StatisticCalculator(30, 2);
        for ( int i = 0; i < 29; i++ ) {
            estimator.addSample(0);
        }
        estimator.addSample(3.2);
        assertThat(estimator.printableFullCapacityAverage(), is("0,11"));
    }

    @Test
    public
    void reset()
            throws Exception {
        final StatisticCalculator estimator = new StatisticCalculator(5, 2);
        estimator.addSample(10.4);
        estimator.addSample(4.0);
        estimator.addSample(5.0);
        estimator.addSample(9.0);
        assertThat(estimator.getItemCounter(), is(4));

        estimator.reset();
        assertThat(estimator.getItemCounter(), is(0));
    }

}
