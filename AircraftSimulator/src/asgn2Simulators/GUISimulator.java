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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
    private JPanel pnlTwo;
    private JPanel pnlBtn;
    private JPanel pnlFour;
    private JPanel pnlFive;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnSwitch;

    private JTextArea areDisplay;

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

        pnlDisplay = createPanel();// Color.WHITE);
        pnlTwo = createPanel();// Color.BLACK);
        pnlBtn = createPanel();// Color.BLUE);
        pnlFour = createPanel();// Color.RED);
        pnlFive = createPanel();// Color.YELLOW);

        btnLoad = createButton("Load");
        btnUnload = createButton("Unload");
        btnFind = createButton("Find");
        btnSwitch = createButton("Switch");

        areDisplay = createTextArea();

        pnlDisplay.setLayout(new BorderLayout());
        pnlDisplay.add(areDisplay, BorderLayout.CENTER);

        layoutButtonPanel();

        this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);
        this.getContentPane().add(pnlTwo, BorderLayout.NORTH);
        this.getContentPane().add(pnlBtn, BorderLayout.SOUTH);
        this.getContentPane().add(pnlFour, BorderLayout.EAST);
        this.getContentPane().add(pnlFive, BorderLayout.WEST);
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

    private JTextArea createTextArea() {
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setFont(new Font("Arial", Font.BOLD, 24));
        jta.setBorder(BorderFactory.createEtchedBorder());
        return jta;
    }

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlBtn.setLayout(layout);

        // add components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;

        addToPanel(pnlBtn, btnLoad, constraints, 0, 0, 2, 1);
        addToPanel(pnlBtn, btnUnload, constraints, 3, 0, 2, 1);
        addToPanel(pnlBtn, btnFind, constraints, 0, 2, 2, 1);
        addToPanel(pnlBtn, btnSwitch, constraints, 3, 2, 2, 1);
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

        // Consider the alternatives - not all active at once.
        if (src == btnLoad) {
            JButton btn = ((JButton) src);
            areDisplay.setText(btn.getText().trim());
        } else if (src == btnUnload) {
            JButton btn = ((JButton) src);
            areDisplay.setText(btn.getText().trim());
        } else if (src == btnSwitch) {
            JOptionPane.showMessageDialog(this, "A Warning Message", "Wiring Class: Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else if (src == btnFind) {
            // JOptionPane.showMessageDialog(this, "An Error Message", "Wiring
            // Class: Error", JOptionPane.ERROR_MESSAGE);
            complete_sim();
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
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }

    private Double str_to_dbl(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("FAIL");
            return null;
        }
    }
    
    
    
    private boolean prepare_sim() throws IOException, SimulationException {
        Integer seed = 100;
        Integer daily_mean = 1300;
        Integer queue_size = 0;
        Double cancel = 0.1;
        Double first = 0.14;
        Double business = 0.13;
        Double premium = 0.7;
        Double economy = 0.7;
        Double sd_booking = 2.0;

        String text_input = "10a";
//Going to add some more testing in each here
        if ((seed = str_to_int(text_input)) == null) {
            createErrorMessage("Seed value");
            return false;
        }
        if ((daily_mean = str_to_int(text_input)) == null) {
            createErrorMessage("Daily Mean");
            return false;
        }

        if ((queue_size = str_to_int(text_input)) == null) {
            createErrorMessage("Max Queue Size");
            return false;
        }

        if ((cancel = str_to_dbl(text_input)) == null) {
            createErrorMessage("Cancel Value");
            return false;
        }

        if ((first = str_to_dbl(text_input)) == null) {
            createErrorMessage("First Percent");
            return false;
        }
        if ((business = str_to_dbl(text_input)) == null) {
            createErrorMessage("Business Percent");
            return false;
        }  
        
        if ((business = str_to_dbl(text_input)) == null) {
            createErrorMessage("Business Percent");
            return false;
        }        
        
        if ((premium = str_to_dbl(text_input)) == null) {
            createErrorMessage("Premium Percent");
            return false;
        }        
        if ((economy = str_to_dbl(text_input)) == null) {
            createErrorMessage("Economy Percent");
            return false;
        }        
       
        
        
        
        
        
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
            // System.out.println("TEST??");
            // Log progress

            this.l.logQREntries(time, sim);
            this.l.logEntry(time, this.sim);
        }
        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.l.logQREntries(Constants.DURATION, sim);
        this.l.finalise(this.sim);

    }

}
