/**
 * 
 */
package asgn2Simulators;

import java.awt.Color;
import java.awt.Component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author Andrew & Mitch
 *
 */
@SuppressWarnings("serial")
public class ChartPanel extends Component {

    protected static JFreeChart chart;
    private static final String TITLE = "Random Bookings";
<<<<<<< HEAD

    public org.jfree.chart.ChartPanel getChartPanel() {
        System.out.println("Giving Chart Panel away for free");
        return new org.jfree.chart.ChartPanel(null);
=======
    private XYSeriesCollection data_points;

    public ChartPanel() {
        System.out.println("Initializing Chart Constructor");
    }

    public org.jfree.chart.ChartPanel getChartPanel() {
        System.out.println("Giving Chart Panel away for free");
        return new org.jfree.chart.ChartPanel(createChart(data_points));
    }

    public void SetData(XYSeriesCollection data) {
        data_points = data;
    }

    public void SetBarData(XYSeriesCollection data) {
        data_points = data;
>>>>>>> 7bc2fde7fdef20605fae479a5d597a88013c7315
    }

    /**
     * Helper method to deliver the Chart - currently uses default colours and
     * auto range
     * 
     * @param dataset
     *            TimeSeriesCollection for plotting
     * @returns chart to be added to panel
     */
    public JFreeChart createLineChart(final XYDataset dataset) {
        System.out.println("Creating the line chart");
        final JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE,
                "Days", "Passengers", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        r.setSeriesPaint(0, Color.BLACK); // First
        r.setSeriesPaint(1, Color.BLUE); // Business
        r.setSeriesPaint(2, Color.CYAN); // Premium
        r.setSeriesPaint(3, Color.GRAY); // Economy
        r.setSeriesPaint(4, Color.GREEN); // Total
        r.setSeriesPaint(5, Color.RED); // Seats Available
        plot.setRenderer(r);
        
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }

    /**
     * Creates a sample chart.
     * 
     * @param dataset
     *            the dataset.
     * 
     * @return The chart.
     */
    public JFreeChart createBarChart(final CategoryDataset dataset) {
        System.out.println("Creating the bar chart");
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart("Bar Chart Demo", // chart title
                "Category", // domain axis label
                "Value", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        //chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customization...
        final CategoryPlot plot = chart.getCategoryPlot();
        
        //plot.setBackgroundPaint(Color.lightGray);
        //plot.setDomainGridlinePaint(Color.white);
        //plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        //final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        //renderer.setDrawBarOutline(false);
        renderer.setSeriesPaint(0, Color.BLACK); // Queue size
        renderer.setSeriesPaint(1, Color.RED); // Passengers refused
        renderer.setSeriesPaint(2, Color.GREEN); // Daily passenger capacity

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }
}
