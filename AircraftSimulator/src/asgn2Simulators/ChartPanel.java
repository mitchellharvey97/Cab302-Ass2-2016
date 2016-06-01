/**
 * 
 */
package asgn2Simulators;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
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
        SetData(initializeData());
        System.out.println("Initializing Chart Constructor");
    }

    public org.jfree.chart.ChartPanel getChartPanel() {
        System.out.println("Giving Chart Panel away for free");
        return new org.jfree.chart.ChartPanel(createChart(data_points));
    }

    private TimeSeriesCollection initializeData() {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries dataset = new TimeSeries("Amount of Alchol Consumed to make this report");
        // Base time, data set up - the calendar is needed for the time points
        Calendar cal = GregorianCalendar.getInstance();
        for (int i = 0; i <= 20; i++) {
            cal.set(2016, 0, i, 6, 0);
            dataset.add(new Day(cal.getTime()), Math.pow(i,10));
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
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    public JFreeChart createBarChart(final CategoryDataset dataset) {
     // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart Demo",         // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        renderer.setSeriesFillPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    }
    
    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    public CategoryDataset createBarDataset() {
        
        // row keys...
        final String series1 = "First";
        final String series2 = "Second";
        final String series3 = "Third";

        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);
        
        return dataset;
        
    }
}
