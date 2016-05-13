/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.B747;

/**
 * @author Andrew
 *
 */
public class AircraftTests {

	public static A380 a;
	public static B747 b;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		a = new A380("QF11", 0);
		b = new B747("QF15", 0);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
