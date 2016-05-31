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

import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * The A380Tests class provides various JUnit tests designed to guarantee
 * correct functionality of the A380 object.
 * 
 * @author Andrew Carr (N9172190)
 * @version 1.0
 *
 */
public class A380Tests {
    public A380 a;
    public Economy p;
    
    int maxBusiness = 64;
    int maxFirst = 14;
    int maxPremium = 35;
    int maxEconomy = 371;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUpBefore() throws Exception {
        a = new A380("QF11", 1, 0, 0, 1, 1);
        p = new Economy(0, 1);
    }

    /* Valid Constructors */
    @Test
    public void testDefaultConstructorValid() throws AircraftException {
        new A380("QF11", 1);
    }

    @Test
    public void testFullConstructorValid() throws AircraftException {
        new A380("", 1, 0, 0, 0, 0);
    }

    /* Invalid Constructors */
    @Test (expected = AircraftException.class)
    public void testDefaultConstructorNullFlightCode() throws AircraftException {
        new A380(null, 1);
    }

    @Test (expected = AircraftException.class)
    public void testDefaultConstructorZeroDepartureTime() throws AircraftException {
        new A380("", 0);
    }

    @Test (expected = AircraftException.class)
    public void testDefaultConstructorNegativeDepartureTime() throws AircraftException {
        new A380("", -1);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNullFlightCode() throws AircraftException {
        new A380(null, 1, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorZeroDepartureTime() throws AircraftException {
        new A380("", 0, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeDepartureTime() throws AircraftException {
        new A380("", -1, 0, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeFirstCapacity() throws AircraftException {
        new A380("", 1, -1, 0, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeBusinessCapacity() throws AircraftException {
        new A380("", 1, 0, -1, 0, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativePremiumCapacity() throws AircraftException {
        new A380("", 1, 0, 0, -1, 0);
    }

    @Test (expected = AircraftException.class)
    public void testFullConstructorNegativeEconomyCapacity() throws AircraftException {
        new A380("", 1, 0, 0, 0, -1);
    }

    /* Variable Tests */
    @Test
    public void testSimpleConstructorFlightCode() throws AircraftException {
        a = new A380("QF11", 1);
        assertTrue(a.toString().indexOf("QF11") >= 0);
    }
    
    // TODO add some way of testing the capacity is set correctly by default
    // (Probably via a loop)

    private void fillPlaneWithProvidedValues(int first, int business, int premium, int economy)
            throws AircraftException, PassengerException {
        a = new A380("", 3);

        for (int x = 0; x < first; x++) {
            Passenger p = new First(1, 1);
            a.confirmBooking(p, 2);
        }

        for (int x = 0; x < business; x++) {
            Passenger p = new Business(1, 1);
            a.confirmBooking(p, 2);
        }

        for (int x = 0; x < premium; x++) {
            Passenger p = new Premium(1, 1);
            a.confirmBooking(p, 2);
        }

        for (int x = 0; x < economy; x++) {
            Passenger p = new Economy(1, 1);
            a.confirmBooking(p, 2);
        }
    }

    @Test
    public void testSimpleConstructorFillDefaultPlane() throws AircraftException, PassengerException {
        int maxBusiness = 64;
        int maxFirst = 14;
        int maxPremium = 35;
        int maxEconomy = 371;

        fillPlaneWithProvidedValues(maxFirst, maxBusiness, maxPremium, maxEconomy);

        assertTrue(a.flightFull());
        assertEquals(maxBusiness, a.getNumBusiness());
        assertEquals(maxFirst, a.getNumFirst());
        assertEquals(maxPremium, a.getNumPremium());
        assertEquals(maxEconomy, a.getNumEconomy());
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlaneFirst() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(maxFirst + 1 , maxBusiness, maxPremium, maxEconomy);
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlaneBusiness() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(maxFirst, maxBusiness + 1, maxPremium, maxEconomy);
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlanePremium() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(maxFirst, maxBusiness, maxPremium + 1, maxEconomy);
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlaneEconomy() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(maxFirst, maxBusiness, maxPremium, maxEconomy + 1);
    }

    @Test
    public void testFullConstructorFlightCode() {
        assertTrue(a.toString().indexOf("QF11") >= 0);
    }

    @Test
    public void testEconomyClassCapacityIsSet() throws AircraftException, PassengerException {
        a = new A380("", 1, 0, 0, 0, 1);
        assertTrue(a.seatsAvailable(p));
        a.confirmBooking(p, 1);
        assertFalse(a.seatsAvailable(p));
    }

    @Test
    public void testPremiumClassCapacityIsSet() throws AircraftException, PassengerException {
        a = new A380("", 1, 0, 0, 1, 0);
        Premium p2 = new Premium(1, 1);
        assertTrue(a.seatsAvailable(p2));
        a.confirmBooking(p2, 1);
        assertFalse(a.seatsAvailable(p2));
    }

    @Test
    public void testBusinessClassCapacityIsSet() throws AircraftException, PassengerException {
        a = new A380("", 1, 0, 1, 0, 0);
        Business p2 = new Business(1, 1);
        assertTrue(a.seatsAvailable(p2));
        a.confirmBooking(p2, 1);
        assertFalse(a.seatsAvailable(p2));
    }

    @Test
    public void testFirstClassCapacityIsSet() throws AircraftException, PassengerException {
        a = new A380("", 1, 1, 0, 0, 0);
        First p2 = new First(1, 1);
        assertTrue(a.seatsAvailable(p2));
        a.confirmBooking(p2, 1);
        assertFalse(a.seatsAvailable(p2));
    }

}
