/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;

/**
 * @author Mitchell
 *
 */
public class BusinessTests {

	/**
	 * @throws java.lang.Exception
	 */
	public static Business bus_pass;

	@Before
	public void setUpBefore() throws Exception {
		bus_pass = new Business(10, 102);
	}

	@Test
	public void checkpass_id() {
		assertEquals("J:0", bus_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in Business", bus_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = bus_pass.upgrade();
		assertTrue(upgraded instanceof First);
		// NEED TO READ SPECS AS TO WHAT TO DO WITH THE PASSENGER ID HERE
		assertEquals("F:0", upgraded.getPassID());
	}

}
