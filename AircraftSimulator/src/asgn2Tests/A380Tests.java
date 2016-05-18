/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Before;
import org.junit.Test;


import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Andrew
 *
 */
public class A380Tests {

	public A380 a;
	public Economy p;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		a = new A380("QF11", 1, 0, 0, 1, 1);
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
	
	@Test (expected = AircraftException.class)
	public void testCancelBookingPassengerNotConfirmed() throws PassengerException, AircraftException {
		a.cancelBooking(p, 1);
	}
	
	/* Aircraft.confirmBooking() Tests */
	@Test
	public void testConfirmBookingValid() throws AircraftException, PassengerException {
		a.confirmBooking(p, 1);
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
	public void testFlightEmpty() throws AircraftException, PassengerException {
		assertEquals(0, a.getNumPassengers());
		assertTrue(a.flightEmpty());
		a.confirmBooking(p, 1);
		assertFalse(a.flightEmpty());
	}
	
	/* Aircraft.flightFull() Tests */
	@Test
	public void testFlightFull() throws AircraftException, PassengerException {
		Premium p2 = new Premium(1, 1);
		
		assertFalse(a.flightFull());
		a.confirmBooking(p, 1);
		a.confirmBooking(p2, 1);
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
	public void testGetBookings() throws AircraftException, PassengerException {
		// Test initial values
		Bookings book = a.getBookings();
		assertEquals(0, book.getTotal());
		assertEquals(0, book.getNumFirst());
		assertEquals(0, book.getNumBusiness());
		assertEquals(0, book.getNumPremium());
		assertEquals(0, book.getNumEconomy());
		assertEquals(2, book.getAvailable());
		
		// Test values with new passenger
		Premium p2 = new Premium(0, 1);
		a.confirmBooking(p, 1);
		a.confirmBooking(p2, 1);
		book = a.getBookings();
		assertEquals(2, book.getTotal());
		assertEquals(0, book.getNumFirst());
		assertEquals(0, book.getNumBusiness());
		assertEquals(1, book.getNumPremium());
		assertEquals(1, book.getNumEconomy());
		assertEquals(0, book.getAvailable());
	}
		
	/* Aircraft.getPassengers() Tests */
	@Test
	public void testGetPassengers() throws AircraftException, PassengerException {
		List<Passenger> passengers = a.getPassengers();
		assertTrue(passengers.isEmpty());
		
		// Add a passenger to the aircraft
		a.confirmBooking(p, 1);
		
		// Test passenger has been added
		passengers = a.getPassengers();
		assertEquals(1, passengers.size());
		
		// Test passenger is stored correctly
		assertEquals(p.getPassID(), passengers.get(0).getPassID());
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
	public void testSeatsAvailable() throws AircraftException, PassengerException {
		assertTrue(a.seatsAvailable(p));
		a.confirmBooking(p, 1);
		assertFalse(a.seatsAvailable(p));
	}
	
	/* Aircraft.upgradeBookings() Tests */
	@Test
	public void testUpgradeBookings() throws AircraftException, PassengerException {
		a.confirmBooking(p, 1);
		
		// Test valid upgrade (Economy -> Premium)
		a.upgradeBookings();
		List<Passenger> passengers = a.getPassengers();
		assertThat(passengers.get(0), instanceOf(Premium.class));
		
		// Test class full upgrade (Premium -> Premium)
		a.upgradeBookings();
		passengers = a.getPassengers();
		assertThat(passengers.get(0), instanceOf(Premium.class));
	}

}
