/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.B747;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * The B747Tests class provides various JUnit tests designed to guarantee correct
 * functionality of the B747 object.
 * 
 * @author Andrew Carr (N9172190)
 * @version 1.0
 *
 */
public class B747Tests {

    public B747 b;
    public Economy p;

    /**
     * @throws javb.lang.Exception
     */
    @Before
    public void setUpBefore() throws Exception {
        b = new B747("QF15", 1, 0, 0, 1, 1);
        p = new Economy(0, 1);
    }

    /* Valid Constructors */
    @Test
    public void testDefaultConstructorValid() throws AircraftException {
        new B747("QF15", 1);
    }

    @Test
    public void testFullConstructorValid() throws AircraftException {
        new B747("", 1, 0, 0, 0, 0);
    }

    /* Invalid Constructors */
    @Test (expected = AircraftException.class)
    public void testDefaultConstructorNullFlightCode() throws AircraftException {
        new B747(null, 1);
    }

    @Test (expected = AircraftException.class)
    public void testDefaultConstructorZeroDepartureTime() throws AircraftException {
        new B747("", 0);
    }

    @Test (expected = AircraftException.class)
    public void testDefaultConstructorNegativeDepartureTime() throws AircraftException {
        new B747("", -1);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNullFlightCode() throws AircraftException {
        new B747(null, 1, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorZeroDepartureTime() throws AircraftException {
        new B747("", 0, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeDepartureTime() throws AircraftException {
        new B747("", -1, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeFirstCapacity() throws AircraftException {
        new B747("", 1, -1, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeBusinessCapacity() throws AircraftException {
        new B747("", 1, 0, -1, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativePremiumCapacity() throws AircraftException {
        new B747("", 1, 0, 0, -1, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeEconomyCapacity() throws AircraftException {
        new B747("", 1, 0, 0, 0, -1);
    }

    /* Variable Tests */
    @Test
    public void testSimpleConstructorFlightCode() throws AircraftException {
        b = new B747("QF15", 1);
        assertTrue(b.toString().indexOf("QF15") >= 0);
    }
    
  //TODO add some way of testing the capacity is set correctly by default (Probably via a loop)

    @Test
    public void testFullConstructorFlightCode() {
        assertTrue(b.toString().indexOf("QF15") >= 0);
    }

    @Test
    public void testEconomyClassCapacityIsSet() throws AircraftException, PassengerException {
        b = new B747("", 1, 0, 0, 0, 1);
        assertTrue(b.seatsAvailable(p));
        b.confirmBooking(p, 1);
        assertFalse(b.seatsAvailable(p));
    }

    @Test
    public void testPremiumClassCapacityIsSet() throws AircraftException, PassengerException {
        b = new B747("", 1, 0, 0, 1, 0);
        Premium p2 = new Premium(1, 1);
        assertTrue(b.seatsAvailable(p2));
        b.confirmBooking(p2, 1);
        assertFalse(b.seatsAvailable(p2));
    }

    @Test
    public void testBusinessClassCapacityIsSet() throws AircraftException, PassengerException {
        b = new B747("", 1, 0, 1, 0, 0);
        Business p2 = new Business(1, 1);
        assertTrue(b.seatsAvailable(p2));
        b.confirmBooking(p2, 1);
        assertFalse(b.seatsAvailable(p2));
    }

    @Test
    public void testFirstClassCapacityIsSet() throws AircraftException, PassengerException {
        b = new B747("", 1, 1, 0, 0, 0);
        First p2 = new First(1, 1);
        assertTrue(b.seatsAvailable(p2));
        b.confirmBooking(p2, 1);
        assertFalse(b.seatsAvailable(p2));
    }

}
