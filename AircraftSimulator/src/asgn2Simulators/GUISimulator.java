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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    private boolean startPage = true;

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

    private JTextField txtSeed;
    private JTextField txtMean;
    private JTextField txtQueue;
    private JTextField txtCancel;
    private JTextField txtFirst;
    private JTextField txtBusiness;
    private JTextField txtPremium;
    private JTextField txtEconomy;

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
        pnlDisplay.add(pnlChart);

        // Start Image
        lblStartTop = createLabel("Thank you for flying Air Hogie!", new Font("Arial", Font.BOLD, 15));
        lblStartBottom = createLabel("We hope you enjoy our wide selection of in-flight memes.",
                new Font("Arial", Font.BOLD, 15));
        lblStartImg = new JLabel(new ImageIcon(getClass().getResource("img/jim.png")));
        // if (this.startPage){
     //   pnlDisplay.add(pnlStart);
        // }
        // }

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
        btnRestore = createButton("Restore Defaults");

        layoutStartPanel();
        layoutButtonPanel();

        this.getContentPane().add(pnlTop, BorderLayout.NORTH);
        this.getContentPane().add(pnlLeft, BorderLayout.WEST);
        this.getContentPane().add(pnlRight, BorderLayout.EAST);
        this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);
        this.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        repaint();

        this.setVisible(true);
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

    private void layoutStartPanel() {
        pnlStart.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 20, 0);
        c.weightx = 1;
        c.weighty = 1;
        addToPanel(pnlStart, lblStartTop, c, 0, 0, 1, 1);
        addToPanel(pnlStart, lblStartImg, c, 0, 1, 1, 1);
        addToPanel(pnlStart, lblStartBottom, c, 0, 2, 1, 1);
    }

    private void layoutButtonPanel() {
        pnlBottom.setLayout(new GridBagLayout());

        // Add components to grid
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 5);

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
        addToPanel(pnlBottom, txtSeed, c, 1, 1, 2, 1);
        addToPanel(pnlBottom, txtMean, c, 1, 2, 2, 1);
        addToPanel(pnlBottom, txtQueue, c, 1, 3, 2, 1);
        addToPanel(pnlBottom, txtCancel, c, 1, 4, 2, 1);

        c.weightx = 1;
        c.weighty = 1;
        addToPanel(pnlBottom, lblFareTitle, c, 3, 0, 3, 1);
        addToPanel(pnlBottom, lblFirst, c, 3, 1, 1, 1);
        addToPanel(pnlBottom, lblBusiness, c, 3, 2, 1, 1);
        addToPanel(pnlBottom, lblPremium, c, 3, 3, 1, 1);
        addToPanel(pnlBottom, lblEconomy, c, 3, 4, 1, 1);
        c.weightx = 100;
        c.weighty = 100;
        addToPanel(pnlBottom, txtFirst, c, 4, 1, 2, 1);
        addToPanel(pnlBottom, txtBusiness, c, 4, 2, 2, 1);
        addToPanel(pnlBottom, txtPremium, c, 4, 3, 2, 1);
        addToPanel(pnlBottom, txtEconomy, c, 4, 4, 2, 1);

        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.BOTH;
        addToPanel(pnlBottom, btnRun, c, 6, 1, 2, 2);
        addToPanel(pnlBottom, btnSwitch, c, 6, 3, 2, 1);
        addToPanel(pnlBottom, btnRestore, c, 6, 4, 2, 1);
    }

    /**
     * 
     * A convenience method to add a component to given grid bag layout
     * locations. Code due to Cay Horstmann
     *
     * @param c
     *            the component to add
     * @param constraints
     *            the grid bag constraints to use
     * @param x
     *            the x grid position
     * @param y
     *            the y grid position
     * @param w
     *            the grid width
     * @param h
     *            the grid height
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

            startPage = false;
            complete_sim();
        } else if (src == btnSwitch) {
            JOptionPane.showMessageDialog(this, "A Warning Message", "Wiring Class: Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else if (src == btnRestore) {
            txtSeed.setText(String.valueOf(Constants.DEFAULT_SEED));
            txtMean.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
            txtQueue.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
            txtCancel.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
            txtFirst.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
            txtBusiness.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
            txtPremium.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
            txtEconomy.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
        }
    }

    private void createErrorMessage(String errorBody) {
        JOptionPane.showMessageDialog(this, "Please Input a valid number for the " + errorBody, "Input Value Error",
                JOptionPane.WARNING_MESSAGE);
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

    private Integer strToInt(String input) {
        Integer value;
        try {
            if ((value = Integer.parseInt(input)) > 0) {
                return value;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }

    private Double strToDouble(String input, Double max) {
        Double value;
        try {
            if ((value = Double.parseDouble(input)) > 0 && (max == null || value <= max)) {
                return value;
            }
            return null;
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }

    private boolean prepareSim() throws IOException, SimulationException {
        Integer seed, queueSize;
        Double dailyMean, cancel, first, business, premium, economy, sdBooking;
        // Check everything for errors, return false if there is a problem,
        // otherwise fall through and return true
        if ((seed = strToInt(txtSeed.getText())) == null) {
            createErrorMessage("Seed value");
            return false;
        }
        if ((dailyMean = strToDouble(txtMean.getText(), null)) == null) {
            createErrorMessage("Daily Mean");
            return false;
        }
        if ((queueSize = strToInt(txtQueue.getText())) == null) {
            createErrorMessage("Max Queue Size");
            return false;
        }
        if ((cancel = strToDouble(txtCancel.getText(), 1.0)) == null) {
            createErrorMessage("Cancel Value");
            return false;
        }
        if ((first = strToDouble(txtFirst.getText(), 1.0)) == null) {
            createErrorMessage("First Percent");
            return false;
        }
        if ((business = strToDouble(txtBusiness.getText(), 1.0)) == null) {
            createErrorMessage("Business Percent");
            return false;
        }
        if ((premium = strToDouble(txtPremium.getText(), 1.0)) == null) {
            createErrorMessage("Premium Percent");
            return false;
        }
        if ((economy = strToDouble(txtEconomy.getText(), 1.0)) == null) {
            createErrorMessage("Economy Percent");
            return false;
        }
        if ((first + business + premium + economy) != 1) {
            createErrorMessage("Passenger split");
            return false;
        }
        // overwriting the values
        sdBooking = 0.33 * dailyMean;
        sdBooking = 429.0;
        l = new Log();
        sim = new Simulator(seed, queueSize, dailyMean, sdBooking, first, business, premium, economy, cancel);
        return true;
    }

    private void runSim() throws AircraftException, PassengerException, SimulationException, IOException {
        // TODO Remove pnlStart from pnlDisplay
        // Add chart to pnlDisplay

        System.out.println("Running the main sim");
        this.sim.createSchedule();
        this.l.initialEntry(this.sim);
        // Main simulation loop

        int cumulativeBusness = 0, cumulativeEconomy = 0, cumulativeFirst = 0, cumulativePremium = 0;// ,
                                                                                                     // cumulativeFlown
                                                                                                     // =
                                                                                                     // 0
                                                                                                     // ;

        TimeSeries tmsBooking = new TimeSeries("Bookings");
        TimeSeries tmsFirst = new TimeSeries("First");
        TimeSeries tmsBusiness = new TimeSeries("Business");
        TimeSeries tmsPremium = new TimeSeries("Premium");
        TimeSeries tmsEconomy = new TimeSeries("Economy");

        Calendar cal = GregorianCalendar.getInstance();

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
            cumulativeFirst = sim.getTotalFirst();

            int dailyPremium = sim.getTotalPremium() - cumulativePremium;

            cumulativePremium = sim.getTotalPremium();

            cal.set(2016, 0, time, 6, 0);
            Date timePoint = cal.getTime();

            tmsBooking.add(new Day(timePoint), time);

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

        
        
    
        
   //     pnlDisplay.remove(pnlStart); 
   //     pnlChartController = new ChartPanel();
   //     pnlChart = pnlChartController.getChartPanel();  
   //     pnlDisplay.setLayout(new BorderLayout());
   //     pnlDisplay.setBackground(Color.GREEN);
   //     pnlDisplay.add(pnlChart);     
   
        JFreeChart chart = pnlChartController.createChart(data_points);
        pnlChart.setChart(chart);
        pnlChart.repaint();
 
        
        
        
        
        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.l.logQREntries(Constants.DURATION, sim);
        this.l.finalise(this.sim);
    }
}
