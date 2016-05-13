/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Aircraft.B747;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.PassengerException;

/**
 * @author Andrew
 *
 */
public class AircraftTests {

	public static A380 a;
	public static B747 b;
	public static Economy p;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		a = new A380("QF11", 1);
		b = new B747("QF15", 1);
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
		//@todo Add passenger to aircraft seating, set passenger to confirmed
		a.cancelBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingPassengerNotConfirmed() throws PassengerException, AircraftException {
		//@todo Set passenger to a state that's not confirmed
		a.cancelBooking(p, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingNegativeCancellationTime() throws PassengerException, AircraftException {
		a.cancelBooking(p, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingLargeCancellationTime() throws PassengerException, AircraftException {
		// Test that cancellationTime > departureTime throws an exception
		a.cancelBooking(p, 999);
	}
	
	@Test (expected = AircraftException.class)
	public void testCancelBookingInvalidPassenger() throws PassengerException, AircraftException {
		// Passenger not recorded in aircraft seating
		a.cancelBooking(p, 1);
	}
	
	/* Aircraft.confirmBooking() Tests */
	@Test
	public void testConfirmBookingValid() {
		//@todo 
		a.confirmBooking(p, 1);
	}

	@Test (expected = PassengerException.class)
	public void testConfirmBookingConfirmedPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingRefusedPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingFlownPassenger() {
		
	}

	@Test (expected = PassengerException.class)
	public void testConfirmBookingNegativeConfirmationTime() throws PassengerException, AircraftException {
		a.cancelBooking(p, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingLargeConfirmationTime() throws PassengerException, AircraftException {
		// Test that confirmationTime > departureTime throws an exception
		a.cancelBooking(p, 999);
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
	public void testFlyPassengersFlownPassenger() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersZeroDepartureTime() {
		
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersNegativeDepartureTime() {
		
	}
	
	/* Aircraft.getBookings() Tests */
	@Test
	public void testGetBookings() {
		
	}
		
	/* Aircraft.getPassengers() Tests */
	@Test
	public void testGetPassengers() {
		
	}
	
	/* Aircraft.hasPassenger() Tests */
	@Test
	public void testHasPassenger() {
		
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
