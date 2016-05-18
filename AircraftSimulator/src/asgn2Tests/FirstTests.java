/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
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
	public static int startingIndex;
	public static First first_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		first_pass = new First(10, 102);
		
		// Get the passenger ID number, exclude the class identifier
		String[] splitID = first_pass.getPassID().split(":");
		startingIndex = Integer.parseInt(splitID[1]);
	}

	@Test
	public void checkpass_id() {
		assertEquals("F:" + startingIndex, first_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in First", first_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = first_pass.upgrade();
		assertTrue(upgraded instanceof First);
		assertEquals("F:" + startingIndex, upgraded.getPassID());
	}

}
