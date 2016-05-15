/**
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
import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * @author Andrew
 *
 */
public class A380Tests {

	public static A380 a;
	public static Economy p;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		a = new A380("QF11", 1);
		p = new Economy(0, 1);
	}

	/* VALID CONSTRUCTORS */
	@Test
	public void testDefaultConstructorValid() throws AircraftException {
		a = new A380("", 1);
	}

	@Test
	public void testFullConstructorValid() throws AircraftException {
		a = new A380("", 1, 0, 0, 0, 0);
	}
	
	/* INVALID CONSTRUCTORS */
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
		// Cancel confirmed booking on the same day
		a.confirmBooking(p, 1);
		a.cancelBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingPassengerNotConfirmed() throws PassengerException, AircraftException {
		a.cancelBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingLowerBoundCancellationTime() throws PassengerException, AircraftException {
		// Test that cancellationTime < 0 throws an exception
		a.confirmBooking(p, 1);
		a.cancelBooking(p, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingUpperBoundCancellationTime() throws PassengerException, AircraftException {
		// Test that cancellationTime > departureTime throws an exception
		a.confirmBooking(p, 1);
		a.cancelBooking(p, 2);
	}
	
//	@Test (expected = AircraftException.class)
//	public void testCancelBookingInvalidPassenger() throws PassengerException, AircraftException {
//		// Passenger not recorded in aircraft seating
//		a.cancelBooking(p, 1);
//	}
	
	/* Aircraft.confirmBooking() Tests */
	@Test
	public void testConfirmBookingValid() throws AircraftException, PassengerException {
		a.confirmBooking(p, 1);
	}

	@Test (expected = PassengerException.class)
	public void testConfirmBookingConfirmedPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingRefusedPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingFlownPassenger() throws PassengerException, AircraftException {
		a.confirmBooking(p, 1);
		a.flyPassengers(1);
		a.confirmBooking(p, 1);
	}

	@Test (expected = PassengerException.class)
	public void testConfirmBookingNegativeConfirmationTime() throws PassengerException, AircraftException {
		a.confirmBooking(p, 1);
		a.cancelBooking(p, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingLargeConfirmationTime() throws PassengerException, AircraftException {
		// Test that confirmationTime > departureTime throws an exception
		a.confirmBooking(p, 1);
		a.cancelBooking(p, 2);
	}
	
	/* Aircraft.finalState() Tests */
	@Test
	public void testFinalState() {
		
	}
	
	/* Aircraft.flightEmpty() Tests */
	@Test
	public void testFlightEmpty() {
		
	}
	
	/* Aircraft.flightFull() Tests */
	@Test
	public void testFlightFull() {
		
	}
	
	/* Aircraft.flyPassengers() Tests */
	@Test
	public void testFlyPassengersValid() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersNewPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersQueuedPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersRefusedPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersFlownPassenger() throws PassengerException, AircraftException {
		a.confirmBooking(p, 1);
		a.flyPassengers(1);
		a.flyPassengers(1);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersZeroDepartureTime() throws PassengerException, AircraftException {
		a.confirmBooking(p, 1);
		a.flyPassengers(0);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersNegativeDepartureTime() throws AircraftException, PassengerException {
		a.confirmBooking(p, 1);
		a.flyPassengers(-1);
	}
	
	/* Aircraft.getBookings() Tests */
	@Test
	public void testGetBookings() {
		Bookings book = a.getBookings();
		
	}
		
	/* Aircraft.getPassengers() Tests */
	@Test
	public void testGetPassengers() {
		List<Passenger> passengers = a.getPassengers();
		
	}
	
	/* Aircraft.hasPassenger() Tests */
	@Test
	public void testHasPassenger() throws AircraftException, PassengerException {
		assertFalse(a.hasPassenger(p));
		a.confirmBooking(p, 1);
		assertTrue(a.hasPassenger(p));
	}
	
	/* Aircraft.seatsAvailable() Tests */
	@Test
	public void testSeatsAvailable() {
		
	}
	
	/* Aircraft.upgradeBookings() Tests */
	@Test
	public void testUpgradeBookings() {
		
	}

}
