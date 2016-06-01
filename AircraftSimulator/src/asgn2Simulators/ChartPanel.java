/**
 * 
 */
package asgn2Simulators;

import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * @author Andrew
 *
 */
public class ChartPanel extends Component {

    protected static JFreeChart chart;
    private static final String TITLE = "Random Bookings";
    private TimeSeriesCollection data_points;

    /**
     * @param chart2
     * 
     */
    public ChartPanel() {
        // data_points = createTimeSeriesData();
        data_points = initializeData();
    }

    public org.jfree.chart.ChartPanel getChartPanel() {
        return new org.jfree.chart.ChartPanel(createChart(data_points));
    }

    private TimeSeriesCollection initializeData() {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(create_dataset("Test"));
        return tsc;

    }

    public void SetData(TimeSeriesCollection data) {
        data_points = data;
    }

    /**
     * Helper method to deliver the Chart - currently uses default colours and
     * auto range
     * 
     * @param dataset
     *            TimeSeriesCollection for plotting
     * @returns chart to be added to panel
     */
    public JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE, "Days", "Passengers", dataset, true, true,
                false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }

    /**
     * Utility method to implement a
     * <a href="http://en.wikipedia.org/wiki/Bernoulli_trial">Bernoulli
     * Trial</a>, a coin toss with two outcomes: success (probability
     * successProb) and failure (probability 1-successProb)
     * 
     * @param successProb
     *            double holding the success probability
     * @param rng
     *            Random object
     * @return true if trial was successful, false otherwise
     */
    private boolean randomSuccess(double successProb, Random rng) {
        boolean result = rng.nextDouble() <= successProb;
        return result;
    }

    public TimeSeries create_dataset(String set_name) {
        TimeSeries dataset = new TimeSeries(set_name);

        // Base time, data set up - the calendar is needed for the time points
        Calendar cal = GregorianCalendar.getInstance();
        Random rng = new Random(250);

        int value = 0;

        // Hack loop to make it interesting. Grows for half of it, then declines
        for (int i = 0; i <= 18 * 7; i++) {
            // These lines are important
            cal.set(2016, 0, i, 6, 0);
            Date timePoint = cal.getTime();

            // HACK BEGINS
            if (i < 9 * 7) {
                if (randomSuccess(0.2, rng)) {
                    value++;
                }

            } else if (i < 18 * 7) {
                if (randomSuccess(0.15, rng)) {
                    value++;
                }
                if (randomSuccess(0.05, rng)) {
                    value++;
                }
            } else {
                value = 0;
            }

            // HACK ENDS
            dataset.add(new Day(timePoint), value);
        }

        return dataset;
    }

    /**
     * Private method creates the dataset. Lots of hack code in the middle, but
     * you should use the labelled code below
     * 
     * @return collection of time series for the plot
     */
    private TimeSeriesCollection createTimeSeriesData() {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries bookTotal = new TimeSeries("Total Bookings");
        TimeSeries econTotal = new TimeSeries("Economy");
        TimeSeries busTotal = new TimeSeries("Business");

        // Base time, data set up - the calendar is needed for the time points
        Calendar cal = GregorianCalendar.getInstance();
        Random rng = new Random(250);

        int economy = 0;
        int business = 0;

        // Hack loop to make it interesting. Grows for half of it, then declines
        for (int i = 0; i <= 18 * 7; i++) {
            // These lines are important
            cal.set(2016, 0, i, 6, 0);
            Date timePoint = cal.getTime();

            // HACK BEGINS
            if (i < 9 * 7) {
                if (randomSuccess(0.2, rng)) {
                    economy++;
                }
                if (randomSuccess(0.1, rng)) {
                    business++;
                }
            } else if (i < 18 * 7) {
                if (randomSuccess(0.15, rng)) {
                    economy++;
                } else if (randomSuccess(0.4, rng)) {
                    economy = Math.max(economy - 1, 0);
                }
                if (randomSuccess(0.05, rng)) {
                    business++;
                } else if (randomSuccess(0.2, rng)) {
                    business = Math.max(business - 1, 0);
                }
            } else {
                economy = 0;
                business = 0;
            }
            // HACK ENDS

            // This is important - steal it shamelessly
            busTotal.add(new Day(timePoint), business);
            econTotal.add(new Day(timePoint), economy);
            bookTotal.add(new Day(timePoint), economy + business);
        }

        // Collection
        tsc.addSeries(bookTotal);
        tsc.addSeries(econTotal);
        tsc.addSeries(busTotal);
        return tsc;
    }

}
