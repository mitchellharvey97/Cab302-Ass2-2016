/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.B747;
import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Andrew
 *
 */
public class B747Tests {

	public B747 b;
	public Economy p;
	
	/**
	 * @throws javb.lang.Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		b = new B747("QF15", 1, 0, 0, 1, 1);
		p = new Economy(0, 1);
	}

	/* VALID CONSTRUCTORS */
	@Test
	public void testDefaultConstructorValid() throws AircraftException {
		b = new B747("", 1);
	}

	@Test
	public void testFullConstructorValid() throws AircraftException {
		b = new B747("", 1, 0, 0, 0, 0);
	}
	
	/* INVALID CONSTRUCTORS */
	@Test (expected = AircraftException.class)
	public void testDefaultConstructorNullFlightCode() throws AircraftException {
		b = new B747(null, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testDefaultConstructorZeroDepartureTime() throws AircraftException {
		b = new B747("", 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testDefaultConstructorNegativeDepartureTime() throws AircraftException {
		b = new B747("", -1);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorNullFlightCode() throws AircraftException {
		b = new B747(null, 1, 0, 0, 0, 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorZeroDepartureTime() throws AircraftException {
		b = new B747("", 0, 0, 0, 0, 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorNegativeDepartureTime() throws AircraftException {
		b = new B747("", -1, 0, 0, 0, 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorNegativeFirstCapacity() throws AircraftException {
		b = new B747("", 1, -1, 0, 0, 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorNegativeBusinessCapacity() throws AircraftException {
		b = new B747("", 1, 0, -1, 0, 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorNegativePremiumCapacity() throws AircraftException {
		b = new B747("", 1, 0, 0, -1, 0);
	}
	
	@Test (expected = AircraftException.class)
	public void testFullConstructorNegativeEconomyCapacity() throws AircraftException {
		b = new B747("", 1, 0, 0, 0, -1);
	}
	
	/* Aircraft.cancelBooking() Tests */
	@Test
	public void testCancelBookingValid() throws PassengerException, AircraftException {
		// Cancel confirmed booking on the same day
		b.confirmBooking(p, 1);
		b.cancelBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingLowerBoundCancellationTime() throws PassengerException, AircraftException {
		// Test that cancellationTime < 0 throws an exception
		b.confirmBooking(p, 1);
		b.cancelBooking(p, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingUpperBoundCancellationTime() throws PassengerException, AircraftException {
		// Test that cancellationTime > departureTime throws an exception
		b.confirmBooking(p, 1);
		b.cancelBooking(p, 2);
	}
	
	@Test (expected = AircraftException.class)
	public void testCancelBookingPassengerNotConfirmed() throws PassengerException, AircraftException {
		b.cancelBooking(p, 1);
	}
	
	/* Aircraft.confirmBooking() Tests */
	@Test
	public void testConfirmBookingValid() throws AircraftException, PassengerException {
		b.confirmBooking(p, 1);
	}

	@Test (expected = PassengerException.class)
	public void testConfirmBookingConfirmedPassenger() throws AircraftException, PassengerException {
		p.confirmSeat(1, 1);
		b.confirmBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingRefusedPassenger() throws PassengerException, AircraftException {
		p.refusePassenger(1);
		b.confirmBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingFlownPassenger() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		b.flyPassengers(1);
		b.confirmBooking(p, 1);
	}

	@Test (expected = PassengerException.class)
	public void testConfirmBookingNegativeConfirmationTime() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		b.cancelBooking(p, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingLargeConfirmationTime() throws PassengerException, AircraftException {
		// Test that confirmationTime > departureTime throws an exception
		b.confirmBooking(p, 1);
		b.cancelBooking(p, 2);
	}
	
	/* Aircraft.finalState() Tests */
	@Test
	public void testFinalState() {
		
	}
	
	/* Aircraft.flightEmpty() Tests */
	@Test
	public void testFlightEmpty() throws AircraftException, PassengerException {
		assertTrue(b.flightEmpty());
		b.confirmBooking(p, 1);
		assertFalse(b.flightEmpty());
	}
	
	/* Aircraft.flightFull() Tests */
	@Test
	public void testFlightFull() throws AircraftException, PassengerException {
		Premium p2 = new Premium(1, 1);
		
		assertFalse(b.flightFull());
		b.confirmBooking(p, 1);
		b.confirmBooking(p2, 1);
		assertTrue(b.flightFull());
	}
	
	/* Aircraft.flyPassengers() Tests */
	@Test
	public void testFlyPassengersValid() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		b.flyPassengers(1);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersNewPassenger() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		p.cancelSeat(1);
		b.flyPassengers(1);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersQueuedPassenger() throws AircraftException, PassengerException {
		b.confirmBooking(p, 1);
		p.queuePassenger(1, 1);
		b.flyPassengers(1);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersRefusedPassenger() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		p.refusePassenger(1);
		b.flyPassengers(1);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersFlownPassenger() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		b.flyPassengers(1);
		b.flyPassengers(1);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersZeroDepartureTime() throws PassengerException, AircraftException {
		b.confirmBooking(p, 1);
		b.flyPassengers(0);
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersNegativeDepartureTime() throws AircraftException, PassengerException {
		b.confirmBooking(p, 1);
		b.flyPassengers(-1);
	}
	
	/* Aircraft.getBookings() Tests */
	@Test
	public void testGetBookings() {
		Bookings book = b.getBookings();
		
		fail("Not yet implemented.");
	}
		
	/* Aircraft.getPassengers() Tests */
	@Test
	public void testGetPassengers() {
		List<Passenger> passengers = b.getPassengers();
		
		fail("Not yet implemeted.");
	}
	
	/* Aircraft.hasPassenger() Tests */
	@Test
	public void testHasPassenger() throws AircraftException, PassengerException {
		assertFalse(b.hasPassenger(p));
		b.confirmBooking(p, 1);
		assertTrue(b.hasPassenger(p));
	}
	
	/* Aircraft.seatsAvailable() Tests */
	@Test
	public void testSeatsAvailable() {
		fail("Not yet implemented.");
	}
	
	/* Aircraft.upgradeBookings() Tests */
	@Test
	public void testUpgradeBookings() {
		fail("Not yet implemented.");
	}

}
