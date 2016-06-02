/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import asgn2Aircraft.AircraftException;
import asgn2Passengers.PassengerException;

/**
 * Class to operate the simulation, taking parameters and utility methods from the Simulator
 * to control the available resources, and using Log to provide a record of operation. 
 * 
 * @author hogan
 *
 */
public class SimulationRunner {
    /**
     * Main program for the simulation 
     * 
     * @param args Arguments to the simulation - 
     * see {@link asgn2Simulators.SimulationRunner#printErrorAndExit()}
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        final int NUM_ARGS = 10;
        GUISimulator g = null;
        Simulator s = null;
        Log l = null;

        try {
            switch (args.length) {
                case NUM_ARGS: {
                    // Check if GUI was called
                    if (args[0].equals("gui")) {
                        System.out.println("Starting GUI with custom params");
                        g = createGUIUsingArgs(args);
                        GUISimulator.startGUI(g);
                    } else if (args[0].equals("nogui")) {
                        // TODO add 'start simulation' code
                        System.out.println("Starting simulation with default params");
                    }

                    s = createSimulatorUsingArgs(args);
                    break;
                }
                case 1: {
                    if (args[0].equals("gui")) {
                        System.out.println("Starting GUI with default params");
                        g = createGUIUsingArgs(args);
                        GUISimulator.startGUI(g);
                    } else if (args[0].equals("nogui")) {
                        // TODO add 'start simulation' code
                        System.out.println("Starting simulation with default params");
                    }

                    s = createSimulatorUsingArgs(args);

                    break;
                }
                case 0: {
                    System.out.println("Zero arguments. Starting GUI with default params");
                    g = new GUISimulator();
                    GUISimulator.startGUI(g);
                    s = new Simulator();
                    break;
                }
                default: {
                    printErrorAndExit(args.length);
                }
            }
            l = new Log();
        } catch (SimulationException | IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }

        // Run the simulation
        SimulationRunner sr = new SimulationRunner(s, l);
        try {
            sr.runSimulation();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Helper to process args for Simulator  
     * 
     * @param args Command line arguments (see usage message) 
     * @return new <code>Simulator</code> from the arguments 
     * @throws SimulationException if invalid arguments. 
     * See {@link asgn2Simulators.Simulator#Simulator(int, int, double, double, double, double, double, double, double)}
     */
    private static Simulator createSimulatorUsingArgs(String[] args) throws SimulationException {
        int seed = Integer.parseInt(args[1]);
        int maxQueueSize = Integer.parseInt(args[2]);
        double meanBookings = Double.parseDouble(args[3]);
        double sdBookings = Double.parseDouble(args[4]);
        double firstProb = Double.parseDouble(args[5]);
        double businessProb = Double.parseDouble(args[6]);
        double premiumProb = Double.parseDouble(args[7]);
        double economyProb = Double.parseDouble(args[8]);
        double cancelProb = Double.parseDouble(args[9]);

        return new Simulator(seed, maxQueueSize, meanBookings, sdBookings, firstProb, businessProb, premiumProb,
                economyProb, cancelProb);
    }

    /**
     * Helper to process args for GUISimulator  
     * 
     * @param args Command line arguments (see usage message) 
     * @return new <code>Simulator</code> from the arguments 
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException
     */
    private static GUISimulator createGUIUsingArgs(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        int seed = Integer.parseInt(args[1]);
        int maxQueueSize = Integer.parseInt(args[2]);
        double meanBookings = Double.parseDouble(args[3]);
        double sdBookings = Double.parseDouble(args[4]);
        double firstProb = Double.parseDouble(args[5]);
        double businessProb = Double.parseDouble(args[6]);
        double premiumProb = Double.parseDouble(args[7]);
        double economyProb = Double.parseDouble(args[8]);
        double cancelProb = Double.parseDouble(args[9]);

        return new GUISimulator(seed, maxQueueSize, meanBookings, sdBookings, firstProb, businessProb, premiumProb,
                economyProb, cancelProb);
    }

    /**
     *  Helper to generate usage message 
     */
    private static void printErrorAndExit(int args) {
        String str = "Expected 0, 1 or 10 args. Got " + String.valueOf(args) + "\n";
        str += "Usage: java asgn2Simulators.SimulationRunner [SIM Args]\n";
        str += "SIM Args: [no]gui seed maxQueueSize meanBookings sdBookings ";
        str += "firstProb businessProb premiumProb economyProb cancelProb\n";
        str += "If no arguments, default values are used\n";
        System.err.println(str);
        System.exit(-1);
    }

    private Simulator sim;
    private Log log;

    /**
     * Constructor just does initialisation 
     * 
     * @param sim <code>Simulator</code> containing simulation parameters
     * @param log <code>Log</code> object supporting record of operation 
     */
    public SimulationRunner(Simulator sim, Log log) {
        this.sim = sim;
        this.log = log;
    }

    /**
     * Method to run the simulation from start to finish.
     * Exceptions are propagated upwards as necessary
     * 
     * @throws AircraftException See methods from {@link asgn2Simulators.Simulator}
     * @throws PassengerException See methods from {@link asgn2Simulators.Simulator}
     * @throws SimulationException See methods from {@link asgn2Simulators.Simulator}
     * @throws IOException on logging failures See methods from {@link asgn2Simulators.Log}
     */
    public void runSimulation() throws AircraftException, PassengerException, SimulationException, IOException {
        this.sim.createSchedule();
        this.log.initialEntry(this.sim);

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
                this.log.logFlightEntries(time, sim);
            } else {
                this.sim.processQueue(time);
            }
            // Log progress
            this.log.logQREntries(time, sim);
            this.log.logEntry(time, this.sim);
        }
        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.log.logQREntries(Constants.DURATION, sim);
        this.log.finalise(this.sim);
    }
}
