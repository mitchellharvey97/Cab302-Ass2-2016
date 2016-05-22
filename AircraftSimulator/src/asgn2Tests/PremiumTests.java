/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.Premium;
import asgn2Passengers.Business;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * The PremiumTests class provides various JUnit tests designed to guarantee correct
 * functionality of the Premium passenger object.
 * 
 * @author Mitchell Harvey (N9453270)
 * @version 1.0
 *
 */
public class PremiumTests {

    /**
     * @throws java.lang.Exception
     */
    public static int id;
    public static Premium p;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        p = new Premium(1, 2);

        // Get the passenger ID number, exclude the class identifier
        // Prevents other test files interfering with our current passenger ID
        String[] splitID = p.getPassID().split(":");
        id = Integer.parseInt(splitID[1]);
    }

    /* Valid Constructors */
    @Test
    public void testValidConstructor() throws PassengerException {
        new Premium(1, 1);
    }

    /* Invalid Constructors */
    @Test (expected = PassengerException.class)
    public void testInvalidConstructorBookingTimeLessThanZero() throws PassengerException {
        new Premium(-1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testInvalidConstructorZeroDepartureTime() throws PassengerException {
        new Premium(1, 0);
    }

    @Test (expected = PassengerException.class)
    public void testInvalidConstructorDepartureTimeLessThanZero() throws PassengerException {
        new Premium(1, -1);
    }

    @Test (expected = PassengerException.class)
    public void testInvalidConstructorDepartureTimeLessThanBookingTime() throws PassengerException {
        new Premium(2, 1);
    }

    /* Variable Tests */
    @Test
    public void testConstructorSetVariables() {
        assertEquals("P:" + id, p.getPassID());
        assertEquals(1, p.getBookingTime());
        assertEquals(2, p.getDepartureTime());
    }

    /* Class-Specific Tests */
    @Test
    public void testNoSeatMessage() {
        assertEquals("No seats available in Premium", p.noSeatsMsg());
    }

    @Test
    public void testPassengerUpgrade() {
        Passenger u = p.upgrade();
        assertTrue(u instanceof Business);
        assertEquals("J(U)P:" + id, u.getPassID());
    }

}
