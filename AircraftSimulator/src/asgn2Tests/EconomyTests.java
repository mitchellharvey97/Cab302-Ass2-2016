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

import asgn2Passengers.Economy;
import asgn2Passengers.Premium;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * The EconomyTests class provides various JUnit tests designed to guarantee
 * correct functionality of the Economy passenger object.
 * 
 * @author Mitchell Harvey (N9453270)
 * @version 1.0
 *
 */
public class EconomyTests {

    /**
     * @throws java.lang.Exception
     */
    public static int id;
    public static Economy p;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        p = new Economy(1, 2);

        // Get the passenger ID number, exclude the class identifier
        // Prevents other test files interfering with our current passenger ID
        String[] splitID = p.getPassID().split(":");
        id = Integer.parseInt(splitID[1]);
    }

    /* Valid Constructors */
    @Test
    public void testValidConstructor() throws PassengerException {
        new Economy(1, 2);
    }

    @Test
    public void testValidConstructorBookingTimeEqualDepartureTime() throws PassengerException {
        new Economy(2, 2);
    }

    @Test
    public void testValidConstructorBookingTime0() throws PassengerException {
        new Economy(0, 1);
    }

    /* Invalid Constructors */
    @Test(expected = PassengerException.class)
    public void testInvalidConstructorBookingTimeLessThanZero() throws PassengerException {
        new Economy(-1, 1);
    }

    @Test(expected = PassengerException.class)
    public void testInvalidConstructorZeroDepartureTime() throws PassengerException {
        new Economy(1, 0);
    }

    @Test(expected = PassengerException.class)
    public void testInvalidConstructorDepartureTimeLessThanZero() throws PassengerException {
        new Economy(1, -1);
    }

    @Test(expected = PassengerException.class)
    public void testInvalidConstructorDepartureTimeLessThanBookingTime() throws PassengerException {
        new Economy(2, 1);
    }

    /* Variable Tests */
    @Test
    public void testConstructorSetVariables() {
        assertEquals("Y:" + id, p.getPassID());
        assertEquals(1, p.getBookingTime());
        assertEquals(2, p.getDepartureTime());
    }

    /* Class-Specific Tests */
    @Test
    public void testNoSeatMessage() {
        assertEquals("No seats available in Economy", p.noSeatsMsg());
    }

    // TODO Do we need to test if P and U are independent of each other?
    @Test
    public void testPassengerUpgrade() {
        assertTrue(p instanceof Economy);
        Passenger u = p.upgrade();
        assertTrue(u instanceof Premium);
        assertEquals("P(U)Y:" + id, u.getPassID());

        // Check passenger state
        assertEquals(p.isNew(), u.isNew());
        assertEquals(p.isConfirmed(), u.isConfirmed());
        assertEquals(p.isQueued(), u.isQueued());
        assertEquals(p.isFlown(), u.isFlown());
        assertEquals(p.isRefused(), u.isRefused());
        assertEquals(p.wasConfirmed(), u.wasConfirmed());
        assertEquals(p.wasQueued(), u.wasQueued());

        // Check passenger properties
        assertEquals(p.getBookingTime(), u.getBookingTime());
        assertEquals(p.getEnterQueueTime(), u.getEnterQueueTime());
        assertEquals(p.getExitQueueTime(), u.getExitQueueTime());
        assertEquals(p.getConfirmationTime(), u.getConfirmationTime());
        assertEquals(p.getDepartureTime(), u.getDepartureTime());

        // Double Upgrade
        Passenger u2 = u.upgrade();
        assertEquals("J(U)P(U)Y:0", u2.getPassID());
    }

}