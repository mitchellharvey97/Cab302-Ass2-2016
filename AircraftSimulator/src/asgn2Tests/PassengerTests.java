/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public static int startingIndex;
	public static First first_pass;
	public static First bad_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		first_pass = new First(10, 20);

		// Get the passenger ID number, exclude the class identifier
		String[] splitID = first_pass.getPassID().split(":");
		startingIndex = Integer.parseInt(splitID[1]);
	}

	// General Passenger Class Tests
	@Test
	public void check_pass_index_incriments() throws PassengerException {
		assertEquals("F:" + startingIndex, first_pass.getPassID());
		// Create second passenger
		First second_pass = new First(10, 21);
		assertEquals("F:" + (startingIndex + 1), second_pass.getPassID());
	}

	// Booking less than 0
	@Test(expected = PassengerException.class)
	public void invalid_booking_time() throws PassengerException {
		bad_pass = new First(-3, 6);
	}

	// Departure less than 0
	@Test(expected = PassengerException.class)
	public void invalid_depart_time() throws PassengerException {
		bad_pass = new First(5, -6);
	}

	// Departure equal to 0
	@Test(expected = PassengerException.class)
	public void invalid_depart_time_0() throws PassengerException {
		bad_pass = new First(5, 0);
	}

	// Departure less than booking
	@Test(expected = PassengerException.class)
	public void depart_time_before_booking() throws PassengerException {
		bad_pass = new First(10, 4);
	}

	//Successful Cancel Seat
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
	
	
	public void cancel_seat_new_pas(){
		
		
	}
	
	
	

	@Test
	public void confirm_seat() throws PassengerException {
		assertFalse(first_pass.isConfirmed());
		first_pass.confirmSeat(10, 12);
		assertTrue(first_pass.isConfirmed());
	}

}
