/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
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
		a = new A380("QF11", 1);
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
	
	/* VARIABLE TESTS */
	@Test
	public void testSimpleConstructorFlightCode() throws AircraftException {
		a = new A380("QF11", 1);
		assertTrue(a.toString().indexOf("QF11") >= 0);
	}
	
	@Test
	public void testFullConstructorFlightCode() {
		assertTrue(a.toString().indexOf("QF11") >= 0);
	}
	
	@Test
	public void testEconomyClassCapacityIsSet() throws AircraftException, PassengerException {
		a = new A380("", 1, 0, 0, 0, 1);
		assertTrue(a.seatsAvailable(p));
		a.confirmBooking(p, 1);
		assertFalse(a.seatsAvailable(p));
	}
	
	@Test
	public void testPremiumClassCapacityIsSet() throws AircraftException, PassengerException {
		a = new A380("", 1, 0, 0, 1, 0);
		Premium p2 = new Premium(1, 1);
		assertTrue(a.seatsAvailable(p2));
		a.confirmBooking(p2, 1);
		assertFalse(a.seatsAvailable(p2));
	}
	
	@Test
	public void testBusinessClassCapacityIsSet() throws AircraftException, PassengerException {
		a = new A380("", 1, 0, 1, 0, 0);
		Business p2 = new Business(1, 1);
		assertTrue(a.seatsAvailable(p2));
		a.confirmBooking(p2, 1);
		assertFalse(a.seatsAvailable(p2));
	}
	
	@Test
	public void testFirstClassCapacityIsSet() throws AircraftException, PassengerException {
		a = new A380("", 1, 1, 0, 0, 0);
		First p2 = new First(1, 1);
		assertTrue(a.seatsAvailable(p2));
		a.confirmBooking(p2, 1);
		assertFalse(a.seatsAvailable(p2));
	}

}
