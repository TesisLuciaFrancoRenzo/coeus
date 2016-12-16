package ar.edu.unrc.coeus.tdlearning.utils;

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
    void getAverage()
            throws Exception {
        StatisticCalculator sc = new StatisticCalculator();
        sc.addSample(10);
        sc.addSample(20.5);
        sc.addSample(2.698);
        sc.addSample(5.4678);
        sc.addSample(-26.5);
        sc.addSample(0);
        assertThat(sc.getAverage(), is(2.027633333333334d));
    }

}
