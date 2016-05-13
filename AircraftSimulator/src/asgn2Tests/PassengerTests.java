/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Premium;

/**
 * @author Mitchell
 *
 */
public class PassengerTests {

	public static Economy y;
	public static Premium p;
	public static Business j;
	public static First f;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		y = new Economy(0, 0);
		p = new Premium(0, 0);
		j = new Business(0, 0);
		f = new First(0, 0);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
