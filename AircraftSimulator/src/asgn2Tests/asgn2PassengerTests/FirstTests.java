/**
 * 
 */
package asgn2Tests.asgn2PassengerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;

/**
 * @author Mitchell
 *
 */
public class FirstTests {

	/**
	 * @throws java.lang.Exception
	 */
	public static First first_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		first_pass = new First(10, 102);
	}

	@Test
	public void checkpass_id() {
		assertEquals("F:0", first_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in First", first_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = first_pass.upgrade();
		assertTrue(upgraded instanceof First);
		// NEED TO READ SPECS AS TO WHAT TO DO WITH THE PASSENGER ID HERE
		assertEquals("F:0", upgraded.getPassID());
	}

}
