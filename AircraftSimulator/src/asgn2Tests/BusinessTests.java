/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	public static int startingIndex;
	public static Business bus_pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bus_pass = new Business(10, 102);
		
		// Get the passenger ID number, exclude the class identifier
		String[] splitID = bus_pass.getPassID().split(":");
		startingIndex = Integer.parseInt(splitID[1]);
	}

	@Test
	public void checkpass_id() {
		assertEquals("J:" + startingIndex, bus_pass.getPassID());
	}

	@Test
	public void check_noSeatMsg() {
		assertEquals("No seats available in Business", bus_pass.noSeatsMsg());
	}

	@Test
	public void upgrade_pas() {
		Passenger upgraded = bus_pass.upgrade();
		assertTrue(upgraded instanceof First);
		assertEquals("F(U)J:" + startingIndex, upgraded.getPassID());
	}

}
