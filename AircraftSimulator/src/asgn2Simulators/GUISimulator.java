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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JSpinner;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

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
    private JPanel pnlStart;

    private JButton btnRun;
    private JButton btnSwitch;
    private JButton btnRestore;

    private JLabel lblStartImg;
    private ChartPanel pnlChartController;
    private org.jfree.chart.ChartPanel pnlChart;

    private JSpinner valSeed;
    private JSpinner valMean;
    private JSpinner valQueue;
    private JSpinner valCancel;
    private JSpinner valFirst;
    private JSpinner valBusiness;
    private JSpinner valPremium;
    private JSpinner valEconomy;

    private JLabel lblStartTop;
    private JLabel lblStartBottom;

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
        pnlTop = new JPanel();
        pnlLeft = new JPanel();
        pnlRight = new JPanel();
        pnlDisplay = new JPanel();
        pnlBottom = new JPanel();
        pnlStart = new JPanel();

        // JFreeChart
        pnlChartController = new ChartPanel();
        pnlChart = pnlChartController.getChartPanel();
        pnlDisplay.setLayout(new BorderLayout());
        // pnlDisplay.add(pnlChart);

        // Start Panel
        lblStartTop = createLabel("Thank you for flying Air Hogie!", new Font("Arial", Font.BOLD, 15));
        lblStartBottom = createLabel("We hope you enjoy our wide selection of in-flight memes.",
                new Font("Arial", Font.BOLD, 15));
        lblStartImg = new JLabel(new ImageIcon(getClass().getResource("img/jim.png")));
        layoutStartPanel();
        pnlDisplay.add(pnlStart);

        // Labels
        lblSimTitle = createLabel("Simulation Settings", new Font("Arial", Font.BOLD, 15));
        lblFareTitle = createLabel("Fare Class Probabilities", new Font("Arial", Font.BOLD, 15));
        lblSeed = createLabel("RNG Seed");
        lblMean = createLabel("Daily Mean");
        lblQueue = createLabel("Queue Size");
        lblCancel = createLabel("Cancel %");
        lblFirst = createLabel("First (%)");
        lblBusiness = createLabel("Business (%)");
        lblPremium = createLabel("Premium (%)");
        lblEconomy = createLabel("Economy (%)");

        // Text Fields
        valSeed = createNumSpinner(Constants.DEFAULT_SEED);
        valMean = createNumSpinner(Constants.DEFAULT_DAILY_BOOKING_MEAN);
        valQueue = createNumSpinner(Constants.DEFAULT_MAX_QUEUE_SIZE);
        valCancel = createNumSpinner(Constants.DEFAULT_CANCELLATION_PROB * 100);
        valFirst = createNumSpinner(Constants.DEFAULT_FIRST_PROB * 100);
        valBusiness = createNumSpinner(Constants.DEFAULT_BUSINESS_PROB * 100);
        valPremium = createNumSpinner(Constants.DEFAULT_PREMIUM_PROB * 100);
        valEconomy = createNumSpinner(Constants.DEFAULT_ECONOMY_PROB * 100);

        // Buttons
        btnRun = createButton("Run Simulation");
        btnSwitch = createButton("Switch Charts");
        btnRestore = createButton("Restore Defaults");
        layoutButtonPanel();

        // Define the chart controller
        pnlChartController = new ChartPanel();
        displayStart();
        // displayGraph();
        
        this.getContentPane().add(pnlTop, BorderLayout.NORTH);
        this.getContentPane().add(pnlLeft, BorderLayout.WEST);
        this.getContentPane().add(pnlRight, BorderLayout.EAST);
        this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);
        this.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        //repaint();

        this.setVisible(true);
    }

    private JButton createButton(String str) {
        JButton jb = new JButton(str);
        jb.addActionListener(this);
        return jb;
    }

    private JSpinner createNumSpinner(double val) {
        JSpinner js = new JSpinner();
        js.setValue(val);

        // Center spinner value
        JSpinner.DefaultEditor e = (JSpinner.DefaultEditor) js.getEditor();
        e.getTextField().setHorizontalAlignment(JTextField.CENTER);

        return js;
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

    private void displayStart() {
        // Start Image
        lblStartTop = createLabel("Thank you for flying Air Hogie!", new Font("Arial", Font.BOLD, 15));
        lblStartBottom = createLabel("We hope you enjoy our wide selection of in-flight memes.",
                new Font("Arial", Font.BOLD, 15));
        lblStartImg = new JLabel(new ImageIcon(getClass().getResource("img/jim.png")));
        pnlDisplay.add(pnlStart);
    }

    private void displayGraph() {
        System.out.println("Showing Chart");

        pnlDisplay.remove(pnlStart);
        pnlChart = pnlChartController.getChartPanel();
        pnlDisplay.setLayout(new BorderLayout());
        pnlDisplay.add(pnlChart);
        pnlDisplay.repaint();
        this.setVisible(true);
    }

    private void layoutStartPanel() {
        // Set grid bag constraints
        pnlStart.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 20, 0);
        c.weightx = 1;
        c.weighty = 1;

        // Add Labels to start screen
        addToPanel(pnlStart, lblStartTop, c, 0, 0, 1, 1);
        addToPanel(pnlStart, lblStartImg, c, 0, 1, 1, 1);
        addToPanel(pnlStart, lblStartBottom, c, 0, 2, 1, 1);
    }

    private void layoutButtonPanel() {
        // Set default grid bag constraints
        pnlBottom.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 5);

        // Simulation settings
        c.anchor = GridBagConstraints.WEST; // x, y, w, h
        c.weightx = 1;
        c.weighty = 1;
        addToPanel(pnlBottom, lblSimTitle, c, 0, 0, 3, 1);
        addToPanel(pnlBottom, lblSeed, c, 0, 1, 1, 1);
        addToPanel(pnlBottom, lblMean, c, 0, 2, 1, 1);
        addToPanel(pnlBottom, lblQueue, c, 0, 3, 1, 1);
        addToPanel(pnlBottom, lblCancel, c, 0, 4, 1, 1);
        c.weightx = 100;
        c.weighty = 100;
        addToPanel(pnlBottom, valSeed, c, 1, 1, 2, 1);
        addToPanel(pnlBottom, valMean, c, 1, 2, 2, 1);
        addToPanel(pnlBottom, valQueue, c, 1, 3, 2, 1);
        addToPanel(pnlBottom, valCancel, c, 1, 4, 2, 1);

        // Fare class probabilities
        c.weightx = 1;
        c.weighty = 1;
        addToPanel(pnlBottom, lblFareTitle, c, 3, 0, 3, 1);
        addToPanel(pnlBottom, lblFirst, c, 3, 1, 1, 1);
        addToPanel(pnlBottom, lblBusiness, c, 3, 2, 1, 1);
        addToPanel(pnlBottom, lblPremium, c, 3, 3, 1, 1);
        addToPanel(pnlBottom, lblEconomy, c, 3, 4, 1, 1);
        c.weightx = 100;
        c.weighty = 100;
        addToPanel(pnlBottom, valFirst, c, 4, 1, 2, 1);
        addToPanel(pnlBottom, valBusiness, c, 4, 2, 2, 1);
        addToPanel(pnlBottom, valPremium, c, 4, 3, 2, 1);
        addToPanel(pnlBottom, valEconomy, c, 4, 4, 2, 1);

        // Control buttons
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.BOTH;
        addToPanel(pnlBottom, btnRun, c, 6, 1, 2, 2);
        addToPanel(pnlBottom, btnSwitch, c, 6, 3, 2, 1);
        addToPanel(pnlBottom, btnRestore, c, 6, 4, 2, 1);
    }

    /**
     * A convenience method to add a component to given grid bag layout
     * locations. Code due to Cay Horstmann
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
        Object src = e.getSource();

        // Determine which button was pressed
        if (src == btnRun) {
            complete_sim();
        } else if (src == btnSwitch) {
            displayGraph();
            // JOptionPane.showMessageDialog(this, "A Warning Message", "Wiring
            // Class: Warning",
            // JOptionPane.WARNING_MESSAGE);
        } else if (src == btnRestore) {
            valSeed.setValue(Constants.DEFAULT_SEED);
            valMean.setValue(Constants.DEFAULT_DAILY_BOOKING_MEAN);
            valQueue.setValue(Constants.DEFAULT_MAX_QUEUE_SIZE);
            valCancel.setValue(Constants.DEFAULT_CANCELLATION_PROB / 100);
            valFirst.setValue(Constants.DEFAULT_FIRST_PROB / 100);
            valBusiness.setValue(Constants.DEFAULT_BUSINESS_PROB / 100);
            valPremium.setValue(Constants.DEFAULT_PREMIUM_PROB / 100);
            valEconomy.setValue(Constants.DEFAULT_ECONOMY_PROB / 100);
        }
    }

    private void createErrorMessage(String errorBody) {
        JOptionPane.showMessageDialog(this, "Please Input a valid number for the " + errorBody, "Input Value Error",
                JOptionPane.WARNING_MESSAGE);
    }

    private Double valueInRange(JSpinner js, double min, double max){
        
        Double returned = (Double) js.getValue();
        


        return returned;
       
    }
    
    // Create the data set and much much more
    // Simulation Running Code
    private Log l;
    private Simulator sim;

    private void complete_sim() {
        try {
            if (prepareSim()) {
                runSim();
                repaint();
            } else {
                System.out.println("There where errors in setting up");
            }
        } catch (IOException | SimulationException | AircraftException | PassengerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("All Done for now");
    }

    private boolean prepareSim() throws IOException, SimulationException {
        // TODO check boundaries
        // Integer seed, queueSize;
        // Double dailyMean, cancel, first, business, premium, economy,
        // sdBooking;
        // Check everything for errors, return false if there is a problem,
        // otherwise fall through and return true

        Double sdBooking;

        int seed = ((Double) valSeed.getValue()).intValue();
        double mean = (Double) valMean.getValue();
        int queue = ((Double) valSeed.getValue()).intValue();
        double cancel = (Double) valCancel.getValue();
        double first = (Double) valFirst.getValue() / 100;
        double business = (Double) valBusiness.getValue() / 100;
        double premium = (Double) valPremium.getValue() / 100;
        double economy = (Double) valEconomy.getValue() / 100;

        if ((first + business + premium + economy) != 1) {
            createErrorMessage("Passenger split");
            return false;
        }

        //overwriting the values
        sdBooking = 0.33 * mean;
        sdBooking  = 429.0;

        l = new Log();
        sim = new Simulator(seed, queue, mean, sdBooking, first, business, premium, economy, cancel);
        return true;
    }

    private void runSim() throws AircraftException, PassengerException, SimulationException, IOException {
        // Add chart to pnlDisplay

        System.out.println("Running the main sim");
        this.sim.createSchedule();
        this.l.initialEntry(this.sim);
        // Main simulation loop

        int cumulativeBusness = 0, cumulativeEconomy = 0, cumulativeFirst = 0, cumulativePremium = 0;

        TimeSeries tmsBooking = new TimeSeries("Bookings");
        TimeSeries tmsFirst = new TimeSeries("First");
        TimeSeries tmsBusiness = new TimeSeries("Business");
        TimeSeries tmsPremium = new TimeSeries("Premium");
        TimeSeries tmsEconomy = new TimeSeries("Economy");

        Calendar cal = GregorianCalendar.getInstance();

        for (int time = 0; time <= Constants.DURATION / 10; time++) {
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
            cumulativeFirst = sim.getTotalFirst();

            int dailyPremium = sim.getTotalPremium() - cumulativePremium;

            cumulativePremium = sim.getTotalPremium();

            cal.set(2016, 0, time, 6, 0);
            Date timePoint = cal.getTime();

            tmsBooking.add(new Day(timePoint), time);

            System.out.println(sim.getTotalBusiness());
            
            tmsFirst.add(new Day(timePoint), dailyFirst);
            tmsBusiness.add(new Day(timePoint), dailyBusiness);
            tmsPremium.add(new Day(timePoint), dailyPremium);
            tmsEconomy.add(new Day(timePoint), dailyEconomy);

            this.l.logQREntries(time, sim);
            this.l.logEntry(time, this.sim);
        }

        TimeSeriesCollection data_points = new TimeSeriesCollection();

        data_points.addSeries(tmsFirst);
        data_points.addSeries(tmsBusiness);
        data_points.addSeries(tmsPremium);
        data_points.addSeries(tmsEconomy);
        data_points.addSeries(tmsBooking);  
    
        System.out.println("Updating Chart");
        pnlChartController.SetData(data_points);
        displayGraph();

        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.l.logQREntries(Constants.DURATION, sim);
        this.l.finalise(this.sim);
    }
}
