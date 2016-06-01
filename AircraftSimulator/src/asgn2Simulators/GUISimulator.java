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
    
    private JLabel lblSimulation;
    private JLabel lblFareClasses;
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
        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));
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

        // Text Area
        txtDisplay = createTextArea();
        pnlDisplay.setLayout(new BorderLayout());
        pnlDisplay.add(txtDisplay, BorderLayout.CENTER);
        
        // Labels
        lblSimulation = createLabel("Simulation", new Font("Arial", Font.BOLD, 24));
        lblFareClasses = createLabel("Fare Classes", new Font("Arial", Font.BOLD, 24));
        lblSeed = createLabel("Seed");
        lblMean = createLabel("Mean");
        lblQueue = createLabel("Queue Size");
        lblCancel = createLabel("Pr(Cancel)");
        lblFirst = createLabel("Pr(First)");
        lblBusiness = createLabel("Pr(Business)");
        lblPremium = createLabel("Pr(Premium)");
        lblEconomy = createLabel("Pr(Economy)");
        
        // Text Fields
        txtSeed = createTextField();
        txtMean = createTextField();
        txtQueue = createTextField();
        txtCancel = createTextField();
        txtFirst = createTextField();
        txtBusiness = createTextField();
        txtPremium = createTextField();
        txtEconomy = createTextField();

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

    private JTextField createTextField() {
        JTextField jtf = new JTextField();
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
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        return jl;
    }
    

    private void layoutButtonPanel() {
        pnlBottom.setLayout(new GridBagLayout());

        // Add components to grid
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(1, 2, 1, 2);
        c.weightx = 1;
        c.weighty = 1;

        c.anchor = GridBagConstraints.WEST;
        addToPanel(pnlBottom, lblSimulation, c, 0, 0, 2, 1);
        addToPanel(pnlBottom, lblSeed, c, 0, 1, 1, 1);
        addToPanel(pnlBottom, lblMean, c, 0, 2, 1, 1);
        addToPanel(pnlBottom, lblQueue, c, 0, 3, 1, 1);
        addToPanel(pnlBottom, lblCancel, c, 0, 4, 1, 1);
        addToPanel(pnlBottom, txtSeed, c, 1, 1, 1, 1);
        addToPanel(pnlBottom, txtMean, c, 1, 2, 1, 1);
        addToPanel(pnlBottom, txtQueue, c, 1, 3, 1, 1);
        addToPanel(pnlBottom, txtCancel, c, 1, 4, 1, 1);

        addToPanel(pnlBottom, lblFareClasses, c, 2, 0, 2, 1);
        addToPanel(pnlBottom, lblFirst, c, 2, 1, 1, 1);
        addToPanel(pnlBottom, lblBusiness, c, 2, 2, 1, 1);
        addToPanel(pnlBottom, lblPremium, c, 2, 3, 1, 1);
        addToPanel(pnlBottom, lblEconomy, c, 2, 4, 1, 1);
        addToPanel(pnlBottom, txtFirst, c, 3, 1, 1, 1);
        addToPanel(pnlBottom, txtBusiness, c, 3, 2, 1, 1);
        addToPanel(pnlBottom, txtPremium, c, 3, 3, 1, 1);
        addToPanel(pnlBottom, txtEconomy, c, 3, 4, 1, 1);
        
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.BOTH;
        addToPanel(pnlBottom, btnRun, c, 4, 1, 2, 2);
        addToPanel(pnlBottom, btnSwitch, c, 4, 3, 2, 2);
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
        JOptionPane.showMessageDialog(this, "Please Input a valid number for the" + errorBody, "Input Value Error", JOptionPane.WARNING_MESSAGE);

    }

    // Simulation Running Code
    private Log l;
    private Simulator sim;

    private void complete_sim() {
        try {
            if (prepare_sim()) {
                run_sim();
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
            if ((value = Double.parseDouble(input)) > 0){
                if (max == null || value <= max){
                return value;
                }
            }
            
                return null;
            
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }
    
    
    
    private boolean prepare_sim() throws IOException, SimulationException {
        Integer seed,queue_size;
        
        Double daily_mean, cancel, first, business, premium ,economy ,sd_booking;

         
        if ((seed = str_to_int(txtSeed.getText())) == null) {
            
            createErrorMessage("Seed value");
            return false;
        }
        if ((daily_mean = str_to_dbl(txtMean.getText(), null)) == null) {
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
        
        sd_booking = 0.33 * daily_mean;
        
        
        l = new Log();

        sim = new Simulator(seed, queue_size, daily_mean, sd_booking, first, business, premium, economy, cancel);

        return true;
    }

    private void run_sim() throws AircraftException, PassengerException, SimulationException, IOException {
        System.out.println("Running the main sim");
        this.sim.createSchedule();
        this.l.initialEntry(this.sim);

        // Main simulation loop
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
            // Log progress

            this.l.logQREntries(time, sim);
            this.l.logEntry(time, this.sim);
        }
        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.l.logQREntries(Constants.DURATION, sim);
        this.l.finalise(this.sim);

    }

}
