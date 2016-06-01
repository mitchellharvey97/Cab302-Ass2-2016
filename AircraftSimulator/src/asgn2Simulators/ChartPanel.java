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
@SuppressWarnings("serial")
public class ChartPanel extends Component {

    protected static JFreeChart chart;
    private static final String TITLE = "Random Bookings";
    private TimeSeriesCollection data_points;

    public ChartPanel() {
        data_points = initializeData();
    }

    public org.jfree.chart.ChartPanel getChartPanel() {
        return new org.jfree.chart.ChartPanel(createChart(data_points));
    }

    private TimeSeriesCollection initializeData() {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries dataset = new TimeSeries("Amount of Alchol Consumed to make this report");
        // Base time, data set up - the calendar is needed for the time points
        Calendar cal = GregorianCalendar.getInstance();
        for (int i = 0; i <= 20; i++) {
            cal.set(2016, 0, i, 6, 0);
            dataset.add(new Day(cal.getTime()), i);
        }       
        tsc.addSeries(dataset);
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
        System.out.println("Creating the chart");
        final JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE, "Days", "Passengers", dataset, true, true,
                false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }
}
