/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.PassengerException;

/**
 * @author Mitchell
 *
 */
public class PassengerTests {

	/**
	 * @throws java.lang.Exception
	 */
	public static First first_pass;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		first_pass = new First(10, 20);
	}

	// General Passenger Class Tests
	@Test
	public void check_pass_index_incriments() throws PassengerException {
		assertEquals("F:0", first_pass.getPassID());
		// Create second passenger
		First second_pass = new First(10, 21);
		assertEquals("F:1", second_pass.getPassID());
	}

	@Test(expected = PassengerException.class)
	public void invalid_booking_time() throws PassengerException {
		First bad_pass = new First(-3, 6);
	}

	@Test(expected = PassengerException.class)
	public void invalid_depart_time() throws PassengerException {
		First bad_pass = new First(5, -6);
	}

	@Test(expected = PassengerException.class)
	public void depart_time_before_booking() throws PassengerException {
		First bad_pass = new First(10, 4);
	}

	@Test
	public void confirm_seat() throws PassengerException {
		assertFalse(first_pass.isConfirmed());
		first_pass.confirmSeat(10, 12);
		assertTrue(first_pass.isConfirmed());
	}

}
