/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * The PassengerTests class provides various JUnit tests designed to guarantee correct
 * functionality of any extending object (i.e. First, Business, Premium and Economy).
 * 
 * @author Mitchell Harvey (N9453270)
 * @version 1.0
 *
 */
public class PassengerTests {

    /**
     * @throws java.lang.Exception
     */
    public int id;
    public First p;

    @Before
    public void setUpBefore() throws Exception {
        p = new First(1, 1);
        id = extractPassengerID(p);
    }

    private int extractPassengerID(Passenger pas) {
        // Get the passenger ID number, exclude the class identifier
        String[] splitID = pas.getPassID().split(":");
        int pass_id = Integer.parseInt(splitID[1]);
        return pass_id;
    }

    /* VALID CONSTRUCTORS */
    @Test
    public void testValidConstructorZeroBookingTime() throws PassengerException {
        p = new First(0, 1);
        assertEquals(0, p.getBookingTime());
        assertEquals(1, p.getDepartureTime());
    }

    @Test
    public void testValidConstructorDepartureTimeEqualToBookingTime() throws PassengerException {
        assertEquals(1, p.getBookingTime());
        assertEquals(1, p.getDepartureTime());
    }

    /* INVALID CONSTRUCTORS */
    @Test(expected = PassengerException.class)
    public void testInvalidConstructorBookingTimeLessThanZero() throws PassengerException {
        new First(-1, 1);
    }

    @Test(expected = PassengerException.class)
    public void testInvalidConstructorDepartureTimeLessThanZero() throws PassengerException {
        new First(1, -1);
    }

    @Test(expected = PassengerException.class)
    public void testInvalidConstructorZeroDepartureTime() throws PassengerException {
        new First(1, 0);
    }

    @Test(expected = PassengerException.class)
    public void testInvalidConstructorDepartureTimeLessThanBookingTime() throws PassengerException {
        new First(2, 1);
    }

    /* Passenger.cancelSeat() Tests */
    @Test
    public void testCancelSeat() throws PassengerException {
        // Need to confirm to cancel
        p = new First(0, 1);
        p.confirmSeat(1, 1);
        assertTrue(p.isConfirmed());
        assertFalse(p.isNew());
        assertEquals(0, p.getBookingTime());

        // Ensure state is reset
        p.cancelSeat(1);
        assertFalse(p.isConfirmed());
        assertTrue(p.isNew());
        assertEquals(1, p.getBookingTime());
    }

    @Test(expected = PassengerException.class)
    public void testCancelSeatPassengerNew() throws PassengerException {
        p.cancelSeat(1);
    }

    @Test(expected = PassengerException.class)
    public void testCancelSeatPassengerQueued() throws PassengerException {
        p.queuePassenger(1, 1);
        p.cancelSeat(1);
    }

    @Test(expected = PassengerException.class)
    public void testCancelSeatPassengerRefused() throws PassengerException {
        p.refusePassenger(1);
        p.cancelSeat(1);
    }

    @Test(expected = PassengerException.class)
    public void testCancelSeatPassengerFlown() throws PassengerException {
        p.flyPassenger(1);
        p.cancelSeat(1);
    }

    @Test(expected = PassengerException.class)
    public void testCancelSeatCancellationTimeLessThanZero() throws PassengerException {
        p.confirmSeat(1, 1);
        p.cancelSeat(-1);
    }

    @Test(expected = PassengerException.class)
    public void testCancelSeatDepartureTimeLessThanCancellationTime() throws PassengerException {
        p.confirmSeat(1, 1);
        p.cancelSeat(2);
    }

    /* Passenger.confirmSeat() Tests */
    @Test
    public void testConfirmSeatPassengerNew() throws PassengerException {
        assertTrue(p.isNew());
        assertFalse(p.isConfirmed());

        // New -> Confirmed
        p.confirmSeat(1, 2);
        assertFalse(p.isNew());
        assertTrue(p.isConfirmed());
        assertEquals(1, p.getConfirmationTime());
        assertEquals(2, p.getDepartureTime());
        assertEquals(0, p.getExitQueueTime());
    }

    @Test
    public void testConfirmSeatPassengerQueued() throws PassengerException {
        assertTrue(p.isNew());
        assertFalse(p.isConfirmed());
        assertEquals(0, p.getExitQueueTime());

        // Queued -> Confirmed
        p.queuePassenger(1, 2);
        p.confirmSeat(1, 2);
        assertFalse(p.isNew());
        assertTrue(p.isConfirmed());
        assertEquals(1, p.getConfirmationTime());
        assertEquals(2, p.getDepartureTime());
        assertEquals(1, p.getExitQueueTime());
    }

    @Test (expected = PassengerException.class)
    public void testConfirmSeatPassengerConfirmed() throws PassengerException {
        p.confirmSeat(1, 1);
        p.confirmSeat(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmSeatPassengerRefused() throws PassengerException {
        p.refusePassenger(1);
        p.confirmSeat(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmSeatPassengerFlown() throws PassengerException {
        p.flyPassenger(1);
        p.confirmSeat(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmSeatConfirmationTimeLessThanZero() throws PassengerException {
        p.confirmSeat(-1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testConfirmSeatDepartureTimeLessThanConfirmationTime() throws PassengerException {
        p.confirmSeat(2, 1);
    }

    /* Passenger.flyPassenger() Tests */
    @Test
    public void testFlyPassenger() throws PassengerException {
        p.confirmSeat(1, 1);
        assertFalse(p.isNew());
        assertTrue(p.isConfirmed());
        assertEquals(1, p.getDepartureTime());

        p.flyPassenger(2);
        assertFalse(p.isConfirmed());
        assertTrue(p.isFlown());
        assertEquals(2, p.getDepartureTime());
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerPassengerNew() throws PassengerException {
        p.flyPassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerPassengerQueued() throws PassengerException {
        p.queuePassenger(1, 1);
        p.flyPassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerPassengerRefused() throws PassengerException {
        p.refusePassenger(1);
        p.flyPassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerPassengerFlown() throws PassengerException {
        p.confirmSeat(1, 1);
        p.flyPassenger(1);
        p.flyPassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerZeroDepartureTime() throws PassengerException {
        p.confirmSeat(1, 1);
        p.flyPassenger(0);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerDepartureTimeLessThanZero() throws PassengerException {
        p.confirmSeat(1, 1);
        p.flyPassenger(-1);
    }

    /* Passenger.getPassID() Tests */
    @Test
    public void testPassIDIncrements() throws PassengerException {
        // Find current passenger index since tests are run out of order
        p = new First(1, 1);
        int index = extractPassengerID(p) + 1;

        //Create 5 new passengers to make sure ID increments correctly
        for (int i = index; i < index + 5; i++) {
            p = new First(1, 1);
            assertEquals(("F:" + i), p.getPassID());
        }
    }

    /* Passenger.queuePassenger() Tests */
    @Test
    public void testQueuePassenger() throws PassengerException {
        assertTrue(p.isNew());

        p.queuePassenger(0, 1);
        assertFalse(p.isNew());
        assertTrue(p.isQueued());
        assertEquals(0, p.getEnterQueueTime());
        assertEquals(1, p.getDepartureTime());
    }

    @Test (expected = PassengerException.class)
    public void testQueuePassengerPassengerQueued() throws PassengerException {
        p.queuePassenger(1, 1);
        p.queuePassenger(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testQueuePassengerPassengerConfirmed() throws PassengerException {
        p.confirmSeat(1, 1);
        p.queuePassenger(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testQueuePassengerPassengerRefused() throws PassengerException {
        p.refusePassenger(1);
        p.queuePassenger(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testQueuePassengerPassengerFlown() throws PassengerException {
        p.flyPassenger(1);
        p.queuePassenger(1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testQueuePassengerQueueTimeLessThanZero() throws PassengerException {
        p.queuePassenger(-1, 1);
    }

    @Test (expected = PassengerException.class)
    public void testQueuePassengerDepartureTimeLessThanQueueTime() throws PassengerException {
        p.queuePassenger(2, 1);
    }

    /* Passenger.refusePassenger() Tests */
    @Test
    public void testRefusePassengerPassengerNew() throws PassengerException {
        assertTrue(p.isNew());

        p.refusePassenger(1);
        assertEquals(0, p.getExitQueueTime());
    }

    @Test
    public void testRefusePassengerPassengerQueued() throws PassengerException {
        // Test Queued -> Refused
        p.queuePassenger(1, 1);
        assertTrue(p.isQueued());
        p.refusePassenger(1);
        assertEquals(1, p.getExitQueueTime());

        // Test valid when refusalTime = 0
        First p2 = new First(0, 1);
        p2.refusePassenger(0);
        assertEquals(0, p2.getExitQueueTime());
    }

    @Test (expected = PassengerException.class)
    public void testRefusePassengerPassengerConfirmed() throws PassengerException {
        p.confirmSeat(1, 1);
        p.refusePassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testRefusePassengerPassengerRefused() throws PassengerException {
        p.refusePassenger(1);
        p.refusePassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testRefusePassengerPassengerFlown() throws PassengerException {
        p.flyPassenger(1);
        p.refusePassenger(1);
    }

    @Test (expected = PassengerException.class)
    public void testRefusePassengerRefusalTimeLessThanZero() throws PassengerException {
        p.refusePassenger(-1);
    }

    @Test (expected = PassengerException.class)
    public void testRefusePassengerRefusalTimeLessThanBookingTime() throws PassengerException {
        p.refusePassenger(0);
    }

    /* Passenger.wasConfirmed() Tests */
    @Test
    public void testWasConfirmedPassengerRefused() throws PassengerException {
        // N -> C -> N -> Q -> R
        assertFalse(p.wasConfirmed());
        p.confirmSeat(0, 1);
        assertTrue(p.wasConfirmed());
        p.cancelSeat(1);
        assertTrue(p.wasConfirmed());
        p.queuePassenger(1, 1);
        assertTrue(p.wasConfirmed());
        p.refusePassenger(1);
        assertTrue(p.wasConfirmed());
    }
    
    @Test
    public void testWasConfirmedPassengerFlown() throws PassengerException {
        // N -> C -> F
        assertFalse(p.wasConfirmed());
        p.confirmSeat(1, 1);
        assertTrue(p.wasConfirmed());
        p.flyPassenger(1);
        assertTrue(p.wasConfirmed());
    }
    
    @Test
    public void testWasConfirmedPassengerUpgraded() throws PassengerException {
        p.confirmSeat(1, 1);
        First u = (First) p.upgrade();
        assertTrue(u.wasConfirmed());
    }
    
    @Test
    public void testWasQueuedPassengerUpgraded() throws PassengerException {
        p.queuePassenger(1, 1);
        First u = (First) p.upgrade();
        assertTrue(u.wasQueued());
    }

    /* Passenger.wasQueued() Tests */
    @Test
    public void testWasQueuedPassengerRefused() throws PassengerException {
        // N -> Q -> R
        assertFalse(p.wasQueued());
        p.queuePassenger(1, 1);
        assertTrue(p.wasQueued());
        p.refusePassenger(1);
        assertTrue(p.wasQueued());
    }
    @Test
    public void testWasQueuedPassengerFlown() throws PassengerException {
        // N -> Q -> C -> F
        assertFalse(p.wasQueued());
        p.queuePassenger(1, 1);
        assertTrue(p.wasQueued());
        p.confirmSeat(1, 1);
        assertTrue(p.wasQueued());
        p.flyPassenger(1);
        assertTrue(p.wasQueued());
    }
}
