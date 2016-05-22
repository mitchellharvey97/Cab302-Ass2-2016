/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * @author Mitchell
 *
 */
public class PassengerTests {

	/**
	 * @throws java.lang.Exception
	 */
	public int startingIndex;
	public First first_pass;
	public First bad_pass;

	@Before
	public void setUpBeforeClass() throws Exception {
		first_pass = new First(10, 20);
		startingIndex = extract_pass_id(first_pass);
	}

	private int extract_pass_id(Passenger pas) {
		// Get the passenger ID number, exclude the class identifier
		String[] splitID = pas.getPassID().split(":");
		int pass_id = Integer.parseInt(splitID[1]);
		return pass_id;
	}

	// General Passenger Class Tests
	@Test
	public void check_pass_index_incriments() throws PassengerException {
		assertEquals("F:" + startingIndex, first_pass.getPassID());
		// Create second passenger
		First second_pass = new First(10, 21);
		// FInd the loop start value (Tests are run out of order)
		int loop_pass_start = extract_pass_id(second_pass) + 1;
		//Create 10 first class passengers to make sure it increments correctly
		for (int x = loop_pass_start; x < loop_pass_start + 10; x++) {
			First mass_pass = new First(10, 20);
			assertEquals(("F:" + x), mass_pass.getPassID());
		}
	}

	
	//Constructor Testing 
	
	// Booking less than 0
	@Test(expected = PassengerException.class)
	public void booking_time_less_than_0() throws PassengerException {
		bad_pass = new First(-3, 6);
	}

	// Booking 0 (Allowed)
	@Test
	public void booking_time_0() throws PassengerException {
		First good_pass = new First(0, 6);
		assertEquals(0, good_pass.getBookingTime());
	}
	
	// Departure less than 0
	@Test(expected = PassengerException.class)
	public void depart_time_less_than_0() throws PassengerException {
		bad_pass = new First(5, -6);
	}

	// Departure equal to 0
	@Test(expected = PassengerException.class)
	public void depart_time_0() throws PassengerException {
		bad_pass = new First(5, 0);
	}

	// Departure less than booking
	@Test(expected = PassengerException.class)
	public void depart_time_before_booking() throws PassengerException {
		bad_pass = new First(10, 4);
	}
	
	// Booking sane as depart (Allowed)
		@Test
		public void depart_time_same_booking() throws PassengerException {
			First good_pass = new First(6, 6);
			assertEquals(6, good_pass.getBookingTime());
			assertEquals(6, good_pass.getDepartureTime());
}

	// Successful Cancel Seat
	@Test
	public void cancel_seat() throws PassengerException {
		// Need to confirm to cancel
		first_pass.confirmSeat(10, 20);
		// check staring values are correct
		assertTrue(first_pass.isConfirmed());
		assertFalse(first_pass.isNew());
		first_pass.cancelSeat(1);
		assertFalse(first_pass.isConfirmed());
	}

	//TODO finish Cancel seat throws
	@Test(expected = PassengerException.class)
	public void cancel_seat_not_confirmed() throws PassengerException {
		assertFalse(first_pass.isConfirmed());
		
		first_pass.cancelSeat(3);

	}

	@Test
	public void confirm_seat() throws PassengerException {
		assertFalse(first_pass.isConfirmed());
		first_pass.confirmSeat(10, 12);
		assertTrue(first_pass.isConfirmed());
	}

}
