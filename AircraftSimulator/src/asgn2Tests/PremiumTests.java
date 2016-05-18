/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.Passenger;
import asgn2Passengers.Premium;

/**
 * @author Mitchell
 *
 */
public class PremiumTests {

	/**
	 * @throws java.lang.Exception
	 */
	public static int startingIndex;
	public static Premium prem_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		prem_pass = new Premium(10, 102);
		
		// Get the passenger ID number, exclude the class identifier
		String[] splitID = prem_pass.getPassID().split(":");
		startingIndex = Integer.parseInt(splitID[1]);
	}

	@Test
	public void checkpass_id() {
		assertEquals("P:" + startingIndex, prem_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in Premium", prem_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = prem_pass.upgrade();
		assertTrue(upgraded instanceof Business);
		assertEquals("J(U)P:" + startingIndex, upgraded.getPassID());
	}
}
