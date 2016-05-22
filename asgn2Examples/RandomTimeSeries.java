/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Examples;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/** 
 * Example code based on the Stack Overflow example and the 
 * standard JFreeChart demos showing the construction of a time series 
 * data set. Some of the data creation code is clearly a quick hack
 * and should not be taken as indicative of the required coding style. 
 * @see http://stackoverflow.com/questions/5048852
 * 
 *  */
@SuppressWarnings("serial")
public class RandomTimeSeries extends ApplicationFrame {

    private static final String TITLE = "Random Bookings";
    
    /**
     * Constructor shares the work with the run method. 
     * @param title Frame display title
     */
    public RandomTimeSeries(final String title) {
        super(title);
        final TimeSeriesCollection dataset = createTimeSeriesData(); 
        JFreeChart chart = createChart(dataset);
        this.add(new ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
    }

    /**
     * Private method creates the dataset. Lots of hack code in the 
     * middle, but you should use the labelled code below  
	 * @return collection of time series for the plot 
	 */
	private TimeSeriesCollection createTimeSeriesData() {
		TimeSeriesCollection tsc = new TimeSeriesCollection(); 
		TimeSeries bookTotal = new TimeSeries("Total Bookings");
		TimeSeries econTotal = new TimeSeries("Economy"); 
		TimeSeries busTotal = new TimeSeries("Business");
		
		//Base time, data set up - the calendar is needed for the time points
		Calendar cal = GregorianCalendar.getInstance();
		Random rng = new Random(250); 
		
		int economy = 0;
		int business = 0; 
		
		//Hack loop to make it interesting. Grows for half of it, then declines
		for (int i=0; i<=18*7; i++) {
			//These lines are important 
			cal.set(2016,0,i,6,0);
	        Date timePoint = cal.getTime();
	        
	        //HACK BEGINS
	        if (i<9*7) {
	        	if (randomSuccess(0.2,rng)) {
	        		economy++; 
	        	}
	        	if (randomSuccess(0.1,rng)) {
	        		business++;
	        	}
	        } else if (i < 18*7) {
	        	if (randomSuccess(0.15,rng)) {
	        		economy++; 
	        	} else if (randomSuccess(0.4,rng)) {
	        		economy = Math.max(economy-1,0);
	        	}
	        	if (randomSuccess(0.05,rng)) {
	        		business++; 
	        	} else if (randomSuccess(0.2,rng)) {
	        		business = Math.max(business-1,0);
	        	}
	        } else {
	        	economy=0; 
	        	business =0;
	        }
	        //HACK ENDS
	        
	        //This is important - steal it shamelessly 
	        busTotal.add(new Day(timePoint),business);
			econTotal.add(new Day(timePoint),economy);
			bookTotal.add(new Day(timePoint),economy+business);
		}
		
		//Collection
		tsc.addSeries(bookTotal);
		tsc.addSeries(econTotal);
		tsc.addSeries(busTotal);
		return tsc; 
	}
	
	/**
	 * Utility method to implement a <a href="http://en.wikipedia.org/wiki/Bernoulli_trial">Bernoulli Trial</a>, 
	 * a coin toss with two outcomes: success (probability successProb) and failure (probability 1-successProb)
	 * @param successProb double holding the success probability 
	 * @param rng Random object 
	 * @return true if trial was successful, false otherwise
	 */
	private boolean randomSuccess(double successProb,Random rng) {
		boolean result = rng.nextDouble() <= successProb;
		return result;
	}

    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            TITLE, "Days", "Passengers", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }

    /**
     * Simple main GUI runner 
     * @param args ignored 
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RandomTimeSeries demo = new RandomTimeSeries(TITLE);
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
            }
        });
    }
}