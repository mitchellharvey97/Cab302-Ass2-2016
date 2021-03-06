/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
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
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
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
    private JButton btnLog;
    private JButton btnSwitch;
    private JButton btnRestore;

    private JLabel lblStartImg;
    private ChartPanel pnlChartController;
    private org.jfree.chart.ChartPanel pnlChart;

    private JScrollPane scrlLog;
    private JTextArea txtLog;

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

    private boolean lineGraph = true;
    private boolean valuesLoaded = false;
    private int seed;
    private int maxQueueSize;
    private double meanBookings;
    private double sdBookings;
    private double firstProb;
    private double businessProb;
    private double premiumProb;
    private double economyProb;
    private double cancelProb;

    String customLog = "";

    // Line Chart Variables
    TimeSeries tmsTotal = new TimeSeries("Total Bookings");
    TimeSeries tmsFirst = new TimeSeries("First");
    TimeSeries tmsBusiness = new TimeSeries("Business");
    TimeSeries tmsPremium = new TimeSeries("Premium");
    TimeSeries tmsEconomy = new TimeSeries("Economy");
    TimeSeries tmsEmpty = new TimeSeries("Empty");
    TimeSeriesCollection lineChartDataPoints = new TimeSeriesCollection();

    JFreeChart lineChart;
    // Bar Chart Variables
    int barCapacity;
    int barQueue;
    int barRefused;
    JFreeChart barChart;
    DefaultCategoryDataset barChartDataSet = new DefaultCategoryDataset();

    // Create the data set and much much more
    // Simulation Running Code
    private Log log;
    private Simulator sim;

    /**
     * @param arg0
     * @throws HeadlessException
     */
    public GUISimulator() throws HeadlessException {
        super("Aircraft Simulator");
    }

    public GUISimulator(int seed, int maxQueueSize, double meanBookings, double sdBookings, double firstProb,
            double businessProb, double premiumProb, double economyProb, double cancelProb)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        // Assign all given values
        this.valuesLoaded = true;
        this.seed = seed;
        this.maxQueueSize = maxQueueSize;
        this.meanBookings = meanBookings;
        this.sdBookings = sdBookings;
        this.firstProb = firstProb;
        this.businessProb = businessProb;
        this.premiumProb = premiumProb;
        this.economyProb = economyProb;
        this.cancelProb = cancelProb;
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
        startGUI(new GUISimulator());
    }

    /**
     * @param args
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void startGUI(GUISimulator g) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(g);
    }

    private void createGUI() {
        // Window properties
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

        // Start Panel
        lblStartTop = createLabel("Thank you for flying Air Hogie!", new Font("Arial", Font.BOLD, 15));
        lblStartBottom = createLabel("We hope you enjoy our wide selection of in-flight memes.",
                new Font("Arial", Font.BOLD, 15));
        lblStartImg = new JLabel(new ImageIcon(getClass().getResource("img/jim.png")));
        layoutStartPanel();
        pnlDisplay.add(pnlStart);

        // JFreeChart
        pnlChartController = new ChartPanel();
        pnlChart = pnlChartController.getChartPanel();
        pnlDisplay.setLayout(new BorderLayout());

        // Scrollable Log
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setWrapStyleWord(true);
        scrlLog = new JScrollPane(txtLog);
        scrlLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Labels
        lblSimTitle = createLabel("Simulation Settings", new Font("Arial", Font.BOLD, 15));
        lblFareTitle = createLabel("Fare Class Probabilities", new Font("Arial", Font.BOLD, 15));
        lblSeed = createLabel("RNG Seed");
        lblMean = createLabel("Daily Mean");
        lblQueue = createLabel("Queue Size");
        lblCancel = createLabel("Cancels (%)");
        lblFirst = createLabel("First (%)");
        lblBusiness = createLabel("Business (%)");
        lblPremium = createLabel("Premium (%)");
        lblEconomy = createLabel("Economy (%)");

        // Text Fields
        if (!valuesLoaded) {
            loadDefaultValues();
        }
        valSeed = createNumSpinner(this.seed);
        valMean = createNumSpinner(this.meanBookings);
        valQueue = createNumSpinner(this.maxQueueSize);
        valCancel = createNumSpinner(this.cancelProb * 100);
        valFirst = createNumSpinner(this.firstProb * 100);
        valBusiness = createNumSpinner(this.businessProb * 100);
        valPremium = createNumSpinner(this.premiumProb * 100);
        valEconomy = createNumSpinner(this.economyProb * 100);

        // Buttons
        btnRun = createButton("Run Simulation");
        btnLog = createButton("Show Logs");
        btnSwitch = createButton("Switch Charts");
        btnRestore = createButton("Restore Defaults");
        btnLog.setEnabled(false);
        btnSwitch.setEnabled(false);
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
        // repaint();

        this.setVisible(true);
    }

    private void loadDefaultValues() {
        this.seed = Constants.DEFAULT_SEED;
        this.meanBookings = Constants.DEFAULT_DAILY_BOOKING_MEAN;
        this.maxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;
        this.cancelProb = Constants.DEFAULT_CANCELLATION_PROB;
        this.firstProb = Constants.DEFAULT_FIRST_PROB;
        this.businessProb = Constants.DEFAULT_BUSINESS_PROB;
        this.premiumProb = Constants.DEFAULT_PREMIUM_PROB;
        this.economyProb = Constants.DEFAULT_ECONOMY_PROB;
    }

    /**
     * Method to create 
     * @param str
     * @return
     */
    private JButton createButton(String str) {
        JButton jb = new JButton(str);
        jb.addActionListener(this);
        return jb;
    }

    /**
     * Method to create a spinner for the GUI
     * @param val Default value of the spinner
     * @return JSpinner
     */
    private JSpinner createNumSpinner(double val) {
        JSpinner js = new JSpinner();
        js.setValue(val);

        // Center spinner value
        JSpinner.DefaultEditor e = (JSpinner.DefaultEditor) js.getEditor();
        e.getTextField().setHorizontalAlignment(JTextField.CENTER);

        return js;
    }

    /**
     * Method to create a label for the GUI
     * @param str Test to place inside the label
     * @param fnt Font to draw the text in
     * @return JLabel
     */
    private JLabel createLabel(String str, Font fnt) {
        JLabel jl = new JLabel(str);
        jl.setFont(fnt);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        return jl;
    }

    /**
     * Alternative constructor to create a label for the GUI
     * @param str Text to place inside the label
     * @return JLabel
     */
    private JLabel createLabel(String str) {
        JLabel jl = new JLabel(str);
        jl.setFont(new Font("Arial", Font.PLAIN, 12));
        jl.setHorizontalAlignment(SwingConstants.RIGHT);
        return jl;
    }

    /**
     * Method to display the start screen to the user
     */
    private void displayStart() {
        // Start Image
        lblStartTop = createLabel("Thank you for flying Air Hogie!", new Font("Arial", Font.BOLD, 15));
        lblStartBottom = createLabel("We hope you enjoy our wide selection of in-flight memes.",
                new Font("Arial", Font.BOLD, 15));
        lblStartImg = new JLabel(new ImageIcon(getClass().getResource("img/jim.png")));
        pnlDisplay.add(pnlStart);
    }

    /**
     * Method to display the chart results to the user
     */
    private void displayChart() {
        // Remove the placeholder Screen
        if (pnlStart.getParent() == pnlDisplay) {
            pnlDisplay.remove(pnlStart);
        }

        // Remove the log screen
        if (scrlLog.getParent() == pnlDisplay) {
            pnlDisplay.remove(scrlLog);
        }

        // Add the chart panel
        if (pnlChart.getParent() != pnlDisplay) {
            pnlDisplay.add(pnlChart);
        }

        // Check a Boolean to decide which graph to load
        if (lineGraph) {
            System.out.println("Showing Line Chart");
            pnlChart.setChart(lineChart);
        } else {
            System.out.println("Showing Bar Chart");
            pnlChart.setChart(barChart);
        }

        lineGraph = !lineGraph;
        this.setVisible(true);
        repaint();
    }

    /**
     * Method to display simulation logs to the user
     */
    private void displayLogs() {
        System.out.println("Showing logs");

        // Remove the placeholder Screen
        if (pnlStart.getParent() == pnlDisplay) {
            pnlDisplay.remove(pnlStart);
        }

        // Remove the chart panel
        if (pnlChart.getParent() == pnlDisplay) {
            pnlDisplay.remove(pnlChart);
        }

        txtLog.setText(customLog);

        // Add the log screen
        if (scrlLog.getParent() != pnlDisplay) {
            pnlDisplay.add(scrlLog);
        }

        this.setVisible(true);
        repaint();
    }

    /**
     * Method to handle placing all elements within the start panel
     */
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

    /**
     * Method to handle placing all elements within the bottom button panel
     */
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
        addToPanel(pnlBottom, btnRun, c, 6, 1, 2, 1);
        addToPanel(pnlBottom, btnLog, c, 6, 2, 2, 1);
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
     * @param hthe grid height
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
            completeSim();
        } else if (src == btnSwitch) {
            displayChart();
        } else if (src == btnRestore) {
            valSeed.setValue(Constants.DEFAULT_SEED);
            valMean.setValue(Constants.DEFAULT_DAILY_BOOKING_MEAN);
            valQueue.setValue(Constants.DEFAULT_MAX_QUEUE_SIZE);
            valCancel.setValue(Constants.DEFAULT_CANCELLATION_PROB * 100);
            valFirst.setValue(Constants.DEFAULT_FIRST_PROB * 100);
            valBusiness.setValue(Constants.DEFAULT_BUSINESS_PROB * 100);
            valPremium.setValue(Constants.DEFAULT_PREMIUM_PROB * 100);
            valEconomy.setValue(Constants.DEFAULT_ECONOMY_PROB * 100);
        } else if (src == btnLog) {
            displayLogs();
        }
    }

    /**
     * Create an error message for the user to be notified of an error.
     * @param errorBody Text for the body of the message dialog
     */
    private void createErrorMessage(String errorBody) {
        JOptionPane.showMessageDialog(this, errorBody, "WE'VE CRASHED! Retrieving Black Box records...",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Reads and parses value from JSpinner, checking range.
     * @param js JSpinner
     * @param min Minimum value
     * @param max Maximum value
     * @param percent Determines whether value provided is in percentage format
     * @return value in JSpinner in (double) format, or null if it is out of bounds
     */
    private Double valueInRange(JSpinner js, Integer min, Integer max, boolean percent) {
        // A crazy way of extracting a double from a spinner
        Object returned_value = js.getValue();
        Double returned = Double.parseDouble(returned_value.toString());
        // Check if there are valid parameters then if the value is in bounds
        if (returned == null || (min != null && returned < min) || (max != null && returned > max)) {
            return null;
        }
        // if it is a percent value then act upon it
        if (percent) {
            returned /= 100;
        }
        return returned;
    }

    /**
     * Controller to run everything and handle errors
     */
    private void completeSim() {
        try {
            if (prepareSim()) {
                runSim();
                repaint();
            } else {
                System.out.println("Errors in setting up!");
            }
        } catch (IOException | SimulationException e) {
            e.printStackTrace();
        }
        System.out.println("All Done for now");
    }

    /**
     * Prepare the simulation by checking all inputs and generating new log and simulator objects
     * @return
     * @throws IOException
     * @throws SimulationException
     */
    private boolean prepareSim() throws IOException, SimulationException {
        // Integer seed, queueSize;
        // Double dailyMean, cancel, first, business, premium, economy,
        // sdBooking;
        // Check everything for errors, return false if there is a problem,
        // otherwise fall through and return true

        Double sdBooking, mean, cancel, first, business, premium, economy;

        Integer seed, queue;
        Double tmp;

        // Extra Confusing for Integer Values

        if (((tmp = (valueInRange(valSeed, 0, null, false))) == null) || (seed = tmp.intValue()) == null) {
            createErrorMessage("Please input a valid number for the seed value.");
            return false;
        }

        if (((tmp = (valueInRange(valQueue, 0, null, false))) == null) || (queue = tmp.intValue()) == null) {
            createErrorMessage("Please input a valid number for the queue value.");
            return false;
        }

        // A much easier way without casting to Integers
        if ((mean = valueInRange(valMean, 0, null, false)) == null) {
            createErrorMessage("Please input a valid number for the mean value.");
            return false;
        }

        if ((cancel = valueInRange(valCancel, 0, 100, true)) == null) {
            createErrorMessage("Please input a valid number for the 'Cancel' probability.");
            return false;
        }

        if ((first = valueInRange(valFirst, 0, 100, true)) == null) {
            createErrorMessage("Please input a valid number for the 'First' probability.");
            return false;
        }

        if ((business = valueInRange(valBusiness, 0, 100, true)) == null) {
            createErrorMessage("Please input a valid number for the 'Business' probability.");
            return false;
        }

        if ((premium = valueInRange(valPremium, 0, 100, true)) == null) {
            createErrorMessage("Please input a valid number for the 'Premium' probability.");
            return false;
        }

        if ((economy = valueInRange(valEconomy, 0, 100, true)) == null) {
            createErrorMessage("Please input a valid number for the 'Economy' probability.");
            return false;
        }

        if ((first + business + premium + economy) != 1) {
            createErrorMessage("Please input a valid number for the Fare Class probabilities.");
            return false;
        }

        sdBooking = sdBookings;
        sdBooking = 0.33 * mean;

        log = new Log();
        sim = new Simulator(seed, queue, mean, sdBooking, first, business, premium, economy, cancel);
        return true;
    }

    /**
     *  Reset Chart data to allow for multi run through
     */
    private void cleanupCharts() {
        lineChartDataPoints = new TimeSeriesCollection();
        barChartDataSet = new DefaultCategoryDataset();

        // Clean Series
        tmsTotal = new TimeSeries("Total Bookings");
        tmsFirst = new TimeSeries("First");
        tmsBusiness = new TimeSeries("Business");
        tmsPremium = new TimeSeries("Premium");
        tmsEconomy = new TimeSeries("Economy");
        tmsEmpty = new TimeSeries("Empty");

    }

    /**
     * Run the custom simulation
     */
    private void runSim() {
        cleanupCharts();

        System.out.println("Running the main sim");

        // SimulationRunner sr = new SimulationRunner();
        // sr.runSimulation();

        // Main simulation loop
        Bookings todaysBookings;
        barQueue = 0;
        barRefused = 0;

        try {
            this.sim.createSchedule();
            this.log.initialEntry(this.sim);

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
                    this.log.logFlightEntries(time, sim);
                    // we don't start logging till the first flight leaves
                    barCapacity = (sim.getFlights(time).getCurrentCounts().getTotal()
                            + sim.getFlights(time).getCurrentCounts().getAvailable());

                    barQueue = Math.max(sim.numInQueue(), barQueue);

                    barRefused = Math.max(sim.numInQueue(), barRefused);

                    todaysBookings = this.sim.getFlights(time).getCurrentCounts();
                    // pass values to be logged
                    addDailyValues(time, todaysBookings);
                } else {
                    this.sim.processQueue(time);
                }
                this.log.logQREntries(time, sim);
                this.log.logEntry(time, this.sim);
            }

            this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
            this.log.logQREntries(Constants.DURATION, sim);
            this.log.finalise(this.sim);
        } catch (AircraftException | PassengerException | SimulationException | IOException ex) {
            createErrorMessage(ex.getMessage());
        }

        // Add all the data to the charts
        prepareData();

        System.out.println("Updating Chart");
        displayChart();
        btnLog.setEnabled(true);
        btnSwitch.setEnabled(true);
    }

    private void prepareData() {
        // Add Line Chart Points to data set
        lineChartDataPoints.addSeries(tmsFirst);
        lineChartDataPoints.addSeries(tmsBusiness);
        lineChartDataPoints.addSeries(tmsPremium);
        lineChartDataPoints.addSeries(tmsEconomy);
        lineChartDataPoints.addSeries(tmsTotal);
        lineChartDataPoints.addSeries(tmsEmpty);
        lineChart = pnlChartController.createLineChart(lineChartDataPoints);

        // Bar graph
        barChartDataSet.addValue(barQueue, "Queue Size", "");
        barChartDataSet.addValue(barRefused, "Passengers Refused", "");
        barChartDataSet.addValue(barCapacity, "Daily Capacity", "");
        barChart = pnlChartController.createBarChart(barChartDataSet);

        customLog += "Final Statistics\n" + "----------\n" + "First Class: " + sim.getTotalFirst() + "\n"
                + "Business Class: " + sim.getTotalBusiness() + "\n" + "Premium Class: " + sim.getTotalPremium() + "\n"
                + "Economy Class: " + sim.getTotalEconomy() + "\n" + "Empty Seats: " + sim.getTotalEmpty()
                + "\nRefused: " + sim.numRefused() + "\n" + "Queued: " + sim.numInQueue() + "\n" + "Flown: "
                + sim.getTotalFlown();
    }

    private void addDailyValues(int time, Bookings todaysBookings) {
        int firstClass = todaysBookings.getNumFirst();
        int businessClass = todaysBookings.getNumBusiness();
        int premiumClass = todaysBookings.getNumPremium();
        int economyClass = todaysBookings.getNumEconomy();
        int totalClass = todaysBookings.getTotal();
        int emptySeats = todaysBookings.getAvailable();
        int queuedSeats = sim.numInQueue();
        int refusedSeats = sim.numRefused();

        Calendar cal = GregorianCalendar.getInstance();
        cal.set(2016, 0, time, 6, 0);
        java.util.Date timePoint = cal.getTime();

        Day day_val = new Day(timePoint);

        String lineBreak = "\n----------\n";
        customLog += "Day " + time + "\n" + "Daily Stats" + lineBreak + "First Class: " + firstClass + "\n"
                + "Business Class: " + businessClass + "\n" + "Premium Class: " + premiumClass + "\n"
                + "Economy Class: " + economyClass + "\n" + "Empty Seats: " + emptySeats + "\n" + "Refused: "
                + refusedSeats + "\n" + "Queued: " + queuedSeats + "\n" + "Flown: " + totalClass + lineBreak + "\n";

        tmsTotal.add(day_val, totalClass);
        tmsFirst.add(day_val, firstClass);
        tmsBusiness.add(day_val, businessClass);
        tmsPremium.add(day_val, premiumClass);
        tmsEconomy.add(day_val, economyClass);
        tmsEmpty.add(day_val, emptySeats);
    }
}
