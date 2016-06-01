/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import asgn2Aircraft.AircraftException;
import asgn2Passengers.PassengerException;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private JPanel pnlDisplay;
    private JPanel pnlTop;
    private JPanel pnlBottom;
    private JPanel pnlRight;
    private JPanel pnlLeft;

    private JButton btnRun;
    private JButton btnSwitch;

    private JTextArea txtDisplay;

    private JTextField txtSeed;
    private JTextField txtMean;
    private JTextField txtQueue;
    private JTextField txtCancel;
    private JTextField txtFirst;
    private JTextField txtBusiness;
    private JTextField txtPremium;
    private JTextField txtEconomy;
    
    private JLabel lblSimTitle;
    private JLabel lblFareTitle;
    private JLabel lblSeed;
    private JLabel lblMean;
    private JLabel lblQueue;
    private JLabel lblCancel;
    private JLabel lblFirst;
    private JLabel lblBusiness;
    private JLabel lblPremium;
    private JLabel lblEconomy;

    /**
     * @param arg0
     * @throws HeadlessException
     */
    public GUISimulator(String arg0) throws HeadlessException {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        createGUI();
    }

    /**
     * @param args
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        // JFrame.setDefaultLookAndFeelDecorated(false);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new GUISimulator("Aircraft Simulator"));
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panels
        pnlTop = createPanel(); //Color.BLACK);
        pnlLeft = createPanel(); //Color.YELLOW);
        pnlRight = createPanel(); //Color.RED);
        pnlDisplay = createPanel(); //Color.WHITE);
        pnlBottom = createPanel(); //Color.BLUE);
        
        // JFreeChart
        final TimeSeriesCollection dataset = createTimeSeriesData();
        JFreeChart chart = createChart(dataset);
        pnlDisplay.add(new ChartPanel(chart), BorderLayout.CENTER);
        
        // Text Area
        txtDisplay = createTextArea();
        pnlDisplay.setLayout(new BorderLayout());
        pnlDisplay.add(txtDisplay, BorderLayout.CENTER);
        
        // Labels
        lblSimTitle = createLabel("Simulation", new Font("Arial", Font.BOLD, 24));
        lblFareTitle = createLabel("Fare Classes", new Font("Arial", Font.BOLD, 24));
        lblSeed = createLabel("Seed");
        lblMean = createLabel("Mean");
        lblQueue = createLabel("Queue Size");
        lblCancel = createLabel("Pr(Cancel)");
        lblFirst = createLabel("Pr(First)");
        lblBusiness = createLabel("Pr(Business)");
        lblPremium = createLabel("Pr(Premium)");
        lblEconomy = createLabel("Pr(Economy)");
        
        // Text Fields
        txtSeed = createTextField(String.valueOf(Constants.DEFAULT_SEED));
        txtMean = createTextField(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
        txtQueue = createTextField(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
        txtCancel = createTextField(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
        txtFirst = createTextField(String.valueOf(Constants.DEFAULT_FIRST_PROB));
        txtBusiness = createTextField(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
        txtPremium = createTextField(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
        txtEconomy = createTextField(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));

        // Buttons
        btnRun = createButton("Run Simulation");
        btnSwitch = createButton("Switch Charts");

        layoutButtonPanel();

        this.getContentPane().add(pnlTop, BorderLayout.NORTH);
        this.getContentPane().add(pnlLeft, BorderLayout.WEST);
        this.getContentPane().add(pnlRight, BorderLayout.EAST);
        this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);
        this.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        repaint();

        this.setVisible(true);
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

    private JPanel createPanel() { // Color c) {
        JPanel jp = new JPanel();
        // jp.setBackground(c);
        return jp;
    }

    private JButton createButton(String str) {
        JButton jb = new JButton(str);
        jb.addActionListener(this);
        return jb;
    }

    private JTextField createTextField(String str) {
        JTextField jtf = new JTextField(str);
        jtf.setFont(new Font("Arial", Font.PLAIN, 12));
        jtf.setBorder(BorderFactory.createEtchedBorder());
        return jtf;
    }

    private JTextArea createTextArea() {
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setFont(new Font("Arial", Font.BOLD, 24));
        jta.setBorder(BorderFactory.createEtchedBorder());
        return jta;
    }

    private JLabel createLabel(String str, Font fnt) {
        JLabel jl = new JLabel(str);
        jl.setFont(fnt);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        return jl;
    }

    private JLabel createLabel(String str) {
        JLabel jl = new JLabel(str);
        jl.setFont(new Font("Arial", Font.PLAIN, 12));
        jl.setHorizontalAlignment(SwingConstants.RIGHT);
        return jl;
    }

    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Dynamic Series", "Days", "Passengers", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }

    private void layoutButtonPanel() {
        pnlBottom.setLayout(new GridBagLayout());

        // Add components to grid
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 5);
        c.weightx = 1;
        c.weighty = 1;

        c.anchor = GridBagConstraints.WEST; //x, y, w, h
        addToPanel(pnlBottom, lblSimTitle, c, 0, 0, 3, 1);
        addToPanel(pnlBottom, lblSeed,     c, 0, 1, 1, 1);
        addToPanel(pnlBottom, lblMean,     c, 0, 2, 1, 1);
        addToPanel(pnlBottom, lblQueue,    c, 0, 3, 1, 1);
        addToPanel(pnlBottom, lblCancel,   c, 0, 4, 1, 1);
        addToPanel(pnlBottom, txtSeed,     c, 1, 1, 2, 1);
        addToPanel(pnlBottom, txtMean,     c, 1, 2, 2, 1);
        addToPanel(pnlBottom, txtQueue,    c, 1, 3, 2, 1);
        addToPanel(pnlBottom, txtCancel,   c, 1, 4, 2, 1);

        addToPanel(pnlBottom, lblFareTitle, c, 3, 0, 3, 1);
        addToPanel(pnlBottom, lblFirst,     c, 3, 1, 1, 1);
        addToPanel(pnlBottom, lblBusiness,  c, 3, 2, 1, 1);
        addToPanel(pnlBottom, lblPremium,   c, 3, 3, 1, 1);
        addToPanel(pnlBottom, lblEconomy,   c, 3, 4, 1, 1);
        addToPanel(pnlBottom, txtFirst,     c, 4, 1, 2, 1);
        addToPanel(pnlBottom, txtBusiness,  c, 4, 2, 2, 1);
        addToPanel(pnlBottom, txtPremium,   c, 4, 3, 2, 1);
        addToPanel(pnlBottom, txtEconomy,   c, 4, 4, 2, 1);
        
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.BOTH;
        addToPanel(pnlBottom, btnRun,    c, 6, 1, 2, 2);
        addToPanel(pnlBottom, btnSwitch, c, 6, 3, 2, 2);
    }
    
    /**
     * 
     * A convenience method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann 
     *
     * @param c the component to add
     * @param constraints the grid bag constraints to use
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width
     * @param h the grid height
     */
    private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get event source
        Object src = e.getSource();

        // Determine which button was pressed
        if (src == btnRun) {
            complete_sim();
            JButton btn = ((JButton) src);
            txtDisplay.setText(btn.getText().trim());
        } else if (src == btnSwitch) {
            JOptionPane.showMessageDialog(this, "A Warning Message", "Wiring Class: Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void createErrorMessage(String errorBody) {
        JOptionPane.showMessageDialog(this, "Please Input a valid number for the " + errorBody, "Input Value Error", JOptionPane.WARNING_MESSAGE);

    }

    // Simulation Running Code
    private Log l;
    private Simulator sim;

    private void complete_sim() {
        try {
            if (prepareSim()) {
                runSim();
            } else {
                System.out.println("There where errors in setting up");
            }
        } catch (IOException | SimulationException | AircraftException | PassengerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Integer str_to_int(String input) {
        Integer value;
        try {
            if ((value = Integer.parseInt(input))> 0){
                return value;                
            }
            else{
                return null;
             }
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }

    private Double str_to_dbl(String input, Double max) {
        Double value;
        try {            
            if ((value = Double.parseDouble(input)) > 0 && (max == null || value <= max)){
                return value;
            }
               return null; 
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }
    
    
    
    private boolean prepareSim() throws IOException, SimulationException {
        Integer seed,queue_size;
        
        Double dailyMean, cancel, first, business, premium ,economy ,sd_booking;
         
        if ((seed = str_to_int(txtSeed.getText())) == null) {
            
            createErrorMessage("Seed value");
            return false;
        }
        if ((dailyMean = str_to_dbl(txtMean.getText(), null)) == null) {
            createErrorMessage("Daily Mean");
            return false;
        }

        if ((queue_size = str_to_int(txtQueue.getText())) == null) {
            createErrorMessage("Max Queue Size");
            return false;
        }

        if ((cancel = str_to_dbl(txtCancel.getText(),1.0)) == null) {
            createErrorMessage("Cancel Value");
            return false;
        }

        if ((first = str_to_dbl(txtFirst.getText(),1.0)) == null) {
            createErrorMessage("First Percent");
            return false;
        }
        if ((business = str_to_dbl(txtBusiness.getText(),1.0)) == null) {
            createErrorMessage("Business Percent");
            return false;
        }  
        
        if ((premium = str_to_dbl(txtPremium.getText(),1.0)) == null) {
            createErrorMessage("Premium Percent");
            return false;
        }        
        if ((economy = str_to_dbl(txtEconomy.getText(),1.0)) == null) {
            createErrorMessage("Economy Percent");
            return false;
        }
        
        if ((first + business + premium + economy) != 1){
            createErrorMessage("Passenger split");
            return false;
        }
        
        sd_booking = 0.33 * dailyMean;
        sd_booking  = 429.0;
        
        
        l = new Log();

        sim = new Simulator(seed, queue_size, dailyMean, sd_booking, first, business, premium, economy, cancel);

        return true;
    }

    private void runSim() throws AircraftException, PassengerException, SimulationException, IOException {
        System.out.println("Running the main sim");
        this.sim.createSchedule();
        this.l.initialEntry(this.sim);

        // Main simulation loop
        int cumulativeBusness = 0,cumulativeEconomy = 0, cumulativeFirst = 0, cumulativePremium = 0 ;
        
        
        
        for (int time = 0; time <= Constants.DURATION; time++) {
            this.sim.resetStatus(time);
            this.sim.rebookCancelledPassengers(time);
            this.sim.generateAndHandleBookings(time);
            this.sim.processNewCancellations(time);
            if (time >= Constants.FIRST_FLIGHT) {
                this.sim.processUpgrades(time);
                this.sim.processQueue(time);
                this.sim.flyPassengers(time);
                this.sim.updateTotalCounts(time);
                this.l.logFlightEntries(time, sim);
            } else {
                this.sim.processQueue(time);
            }
            int dailyBusiness = sim.getTotalBusiness() - cumulativeBusness;
            cumulativeBusness = sim.getTotalBusiness();
            
            int dailyEconomy = sim.getTotalEconomy() - cumulativeEconomy;
            cumulativeEconomy = sim.getTotalEconomy();
                
            int dailyFirst = sim.getTotalFirst() - cumulativeFirst;
            cumulativeFirst= sim.getTotalFirst();
           
            int dailyPremium = sim.getTotalPremium() - cumulativePremium;
            cumulativePremium= sim.getTotalPremium();
                           
            
            System.out.println("Day: " + time);
            System.out.println("First Class: " + dailyFirst + " Business Class: " + dailyBusiness + " Premium: " + dailyPremium + " Economy: " + dailyEconomy);
            
            this.l.logQREntries(time, sim);
            this.l.logEntry(time, this.sim);
        }
        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.l.logQREntries(Constants.DURATION, sim);
        this.l.finalise(this.sim);

    }
    

}
