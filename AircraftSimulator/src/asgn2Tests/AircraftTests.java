/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * The AircraftTests class provides various JUnit tests designed to guarantee correct
 * functionality of any extending object (i.e. A380 and B747).
 * 
 * @author Andrew Carr (N9172190)
 * @version 1.0
 *
 */
public class AircraftTests {

    public A380 a;
    public Economy p;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUpBefore() throws Exception {
        a = new A380("QF11", 1, 0, 0, 1, 2);
        p = new Economy(0, 1);
    }

    /* Valid Constructors */
    @Test
    public void testDefaultConstructorValid() throws AircraftException {
        a = new A380("", 1);
    }

    @Test
    public void testFullConstructorValid() throws AircraftException {
        a = new A380("", 1, 0, 0, 0, 0);
    }

    /* Invalid Constructors */
    @Test (expected = AircraftException.class)
    public void testDefaultConstructorNullFlightCode() throws AircraftException {
        a = new A380(null, 1);
    }

    @Test (expected = AircraftException.class)
    public void testDefaultConstructorZeroDepartureTime() throws AircraftException {
        a = new A380("", 0);
    }

    @Test (expected = AircraftException.class)
    public void testDefaultConstructorNegativeDepartureTime() throws AircraftException {
        a = new A380("", -1);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNullFlightCode() throws AircraftException {
        a = new A380(null, 1, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorZeroDepartureTime() throws AircraftException {
        a = new A380("", 0, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeDepartureTime() throws AircraftException {
        a = new A380("", -1, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeFirstCapacity() throws AircraftException {
        a = new A380("", 1, -1, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeBusinessCapacity() throws AircraftException {
        a = new A380("", 1, 0, -1, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativePremiumCapacity() throws AircraftException {
        a = new A380("", 1, 0, 0, -1, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeEconomyCapacity() throws AircraftException {
        a = new A380("", 1, 0, 0, 0, -1);
    }

    /* Aircraft.cancelBooking() Tests */
    @Test
    public void testCancelBookingValid() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        assertTrue(p.isConfirmed());
        a.cancelBooking(p, 1);
        assertTrue(p.isNew());
        assertEquals(1, p.getBookingTime());
    }

    @Test (expected = PassengerException.class)
    public void testCancelBookingNewPassenger() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        p.cancelSeat(1);
        a.cancelBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testCancelBookingQueuedPassenger() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        p.queuePassenger(1, 1);
        a.cancelBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testCancelBookingRefusedPassenger() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        p.refusePassenger(1);
        a.cancelBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testCancelBookingFlownPassenger() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        p.flyPassenger(1);
        a.cancelBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testCancelBookingCancellationTimeLessThanZero() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        a.cancelBooking(p, -1);
    }

    @Test (expected = PassengerException.class)
    public void testCancelBookingCancellationTimeGreaterThanDepartureTime() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        a.cancelBooking(p, 2);
    }

    @Test (expected = AircraftException.class)
    public void testCancelBookingPassengerNotConfirmed() throws PassengerException, AircraftException {
        a.cancelBooking(p, 1);
    }

    /* Aircraft.confirmBooking() Tests */
    @Test
    public void testConfirmBookingValid() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        assertTrue(p.isConfirmed());
        assertEquals(1, p.getConfirmationTime());
        assertEquals(1, p.getDepartureTime());

        // Check alternative POST condition
        Economy p2 = new Economy(1, 1);
        p2.queuePassenger(1, 1);
        a.confirmBooking(p2, 1);
        assertEquals(1, p2.getExitQueueTime());
    }

    @Test (expected = PassengerException.class)
    public void testConfirmBookingConfirmedPassenger() throws AircraftException, PassengerException {
        p.confirmSeat(1, 1);
        a.confirmBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmBookingRefusedPassenger() throws PassengerException, AircraftException {
        p.refusePassenger(1);
        a.confirmBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmBookingFlownPassenger() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        p.flyPassenger(1);
        a.confirmBooking(p, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmBookingConfirmationTimeLessThanZero() throws PassengerException, AircraftException {
        a.confirmBooking(p, -1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmBookingConfirmationTimeGreaterThanDepartureTime() throws PassengerException, AircraftException {
        a.confirmBooking(p, 2);
    }

    /* Aircraft.finalState() Tests */
    @Test
    public void testFinalState() {
        String finalState = a.finalState();

        // Check the contents of finalState
        boolean type = finalState.indexOf("A380") > -1;
        boolean flightCode = finalState.indexOf("QF11") > -1;
        boolean passengers = finalState.indexOf("Pass: 0") > -1;
        assertTrue(type && flightCode && passengers);
    }

    /* Aircraft.flightEmpty() Tests */
    @Test
    public void testFlightEmpty() throws AircraftException, PassengerException {
        assertEquals(0, a.getNumPassengers());
        assertTrue(a.flightEmpty());

        // Add a passenger
        a.confirmBooking(p, 1);
        assertEquals(1, a.getNumPassengers());
        assertFalse(a.flightEmpty());
    }

    /* Aircraft.flightFull() Tests */
    @Test
    public void testFlightFull() throws AircraftException, PassengerException {
        Economy p2 = new Economy(1, 1);
        Premium p3 = new Premium(1, 1);

        // Fill all available seats
        assertEquals(0, a.getNumPassengers());
        assertFalse(a.flightFull());
        a.confirmBooking(p, 1);
        a.confirmBooking(p2, 1);
        a.confirmBooking(p3, 1);
        assertEquals(3, a.getNumPassengers());
        assertTrue(a.flightFull());
    }

    /* Aircraft.flyPassengers() Tests */
    @Test
    public void testFlyPassengersValid() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        a.flyPassengers(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengersNewPassenger() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        p.cancelSeat(1);
        a.flyPassengers(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengersQueuedPassenger() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        p.queuePassenger(1, 1);
        a.flyPassengers(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengersRefusedPassenger() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        p.refusePassenger(1);
        a.flyPassengers(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengersFlownPassenger() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        p.flyPassenger(1);
        a.flyPassengers(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengersZeroDepartureTime() throws PassengerException, AircraftException {
        a.confirmBooking(p, 1);
        a.flyPassengers(0);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengersDepartureTimeLessThanZero() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        a.flyPassengers(-1);
    }

    /* Aircraft.getBookings() Tests */
    @Test
    public void testGetBookings() throws AircraftException, PassengerException {
        a = new A380("", 1, 1, 1, 1, 1);

        // Test initial values
        Bookings book = a.getBookings();
        assertEquals(0, book.getTotal());
        assertEquals(0, book.getNumFirst());
        assertEquals(0, book.getNumBusiness());
        assertEquals(0, book.getNumPremium());
        assertEquals(0, book.getNumEconomy());
        assertEquals(4, book.getAvailable());

        // Add passengers
        Premium p2 = new Premium(0, 1);
        Business p3 = new Business(0, 1);
        First p4 = new First(0, 1);
        a.confirmBooking(p, 1);
        a.confirmBooking(p2, 1);
        a.confirmBooking(p3, 1);
        a.confirmBooking(p4, 1);

        // Test modified values
        book = a.getBookings();
        assertEquals(4, book.getTotal());
        assertEquals(1, book.getNumFirst());
        assertEquals(1, book.getNumBusiness());
        assertEquals(1, book.getNumPremium());
        assertEquals(1, book.getNumEconomy());
        assertEquals(0, book.getAvailable());
    }

    /* Aircraft.getPassengers() Tests */
    @Test
    public void testGetPassengers() throws AircraftException, PassengerException {
        List<Passenger> passengers = a.getPassengers();
        assertTrue(passengers.isEmpty());

        // Test passenger has been added
        a.confirmBooking(p, 1);
        passengers = a.getPassengers();
        assertEquals(1, passengers.size());

        // Test passenger is stored correctly
        assertEquals(p.getPassID(), passengers.get(0).getPassID());
    }

    /* Aircraft.hasPassenger() Tests */
    @Test
    public void testHasPassengerValidConfirmation() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);
        assertTrue(a.hasPassenger(p));
    }

    @Test
    public void testHasPassengerInvalidConfirmation() throws PassengerException {
        assertFalse(a.hasPassenger(p));
        p.confirmSeat(1, 1);
        assertFalse(a.hasPassenger(p));
    }

    /* Aircraft.seatsAvailable() Tests */
    @Test
    public void testSeatsAvailableEconomy() throws AircraftException, PassengerException {
        a = new A380("", 1, 0, 0, 0, 1);
        assertTrue(a.seatsAvailable(p));
        a.confirmBooking(p, 1);
        assertFalse(a.seatsAvailable(p));
    }

    @Test
    public void testSeatsAvailablePremium() throws AircraftException, PassengerException {
        a = new A380("", 1, 0, 0, 1, 0);
        Premium p2 = new Premium(1, 1);
        assertTrue(a.seatsAvailable(p2));
        a.confirmBooking(p2, 1);
        assertFalse(a.seatsAvailable(p2));
    }

    @Test
    public void testSeatsAvailableBusiness() throws AircraftException, PassengerException {
        a = new A380("", 1, 0, 1, 0, 0);
        Business p2 = new Business(1, 1);
        assertTrue(a.seatsAvailable(p2));
        a.confirmBooking(p2, 1);
        assertFalse(a.seatsAvailable(p2));
    }

    @Test
    public void testSeatsAvailableFirst() throws AircraftException, PassengerException {
        a = new A380("", 1, 1, 0, 0, 0);
        First p2 = new First(1, 1);
        assertTrue(a.seatsAvailable(p2));
        a.confirmBooking(p2, 1);
        assertFalse(a.seatsAvailable(p2));
    }

    /* Aircraft.upgradeBookings() Tests */
    @Test
    public void testUpgradeBookingsFull() throws AircraftException, PassengerException {
        a.confirmBooking(p, 1);

        // Test valid upgrade (Economy -> Premium)
        a.upgradeBookings();
        List<Passenger> passengers = a.getPassengers();
        assertTrue(passengers.get(0) instanceof Premium);

        // Test class full upgrade (Premium -> Premium)
        a.upgradeBookings();
        passengers = a.getPassengers();
        assertTrue(passengers.get(0) instanceof Premium);
    }

    @Test
    public void testUpgradeBookingsOrderSpecific() throws PassengerException, AircraftException {
        // Initialise variables
        a = new A380("", 1, 2, 1, 2, 1);
        Premium p2 = new Premium(1, 1);
        Business p3 = new Business(1, 1);
        First p4 = new First(1, 1);

        // Confirm all passengers (order specific for test)
        a.confirmBooking(p, 1);
        a.confirmBooking(p2, 1);
        a.confirmBooking(p3, 1);
        a.confirmBooking(p4, 1);

        // This will cause an error if method does not loop in correct order
        // i.e. Business, then Premium, then Economy passengers
        a.upgradeBookings();

        // Ensure all passengers were upgraded correctly
        List<Passenger> passengers = a.getPassengers();
        assertTrue(passengers.get(0) instanceof Premium);
        assertTrue(passengers.get(1) instanceof Business);
        assertTrue(passengers.get(2) instanceof First);
        assertTrue(passengers.get(3) instanceof First);
    }

}
