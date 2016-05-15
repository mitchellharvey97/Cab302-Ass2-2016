/**
 * 
 */
package asgn2Tests.asgn2PassengerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.Premium;

/**
 * @author Mitchell
 *
 */
public class EconomyTests {

	/**
	 * @throws java.lang.Exception
	 */
	public static Economy eco_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		eco_pass = new Economy(10, 102);
	}

	@Test
	public void checkpass_id() {
		assertEquals("Y:0", eco_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in Economy", eco_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = eco_pass.upgrade();
		assertTrue(upgraded instanceof Premium);
		// NEED TO READ SPECS AS TO WHAT TO DO WITH THE PASSENGER ID HERE
		assertEquals("P:0", upgraded.getPassID());
	}

}
