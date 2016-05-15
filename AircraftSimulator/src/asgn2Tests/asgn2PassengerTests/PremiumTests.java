/**
 * 
 */
package asgn2Tests.asgn2PassengerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	public static Premium prem_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		prem_pass = new Premium(10, 102);
	}

	@Test
	public void checkpass_id() {
		assertEquals("P:0", prem_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in Premium", prem_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = prem_pass.upgrade();
		assertTrue(upgraded instanceof Business);
		// NEED TO READ SPECS AS TO WHAT TO DO WITH THE PASSENGER ID HERE
		assertEquals("J:0", upgraded.getPassID());
	}
}
