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
import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
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
  
    static final int FIRST = 14;
    static final int BUSINESS = 52;
    static final int PREMIUM = 32;
    static final int ECONOMY = 255;
    
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
    
    private void fillPlaneWithProvidedValues(int first, int business, int premium, int economy)
            throws AircraftException, PassengerException {
        b = new B747("", 3);

        for (int x = 0; x < first; x++) {
            Passenger p = new First(1, 1);
            b.confirmBooking(p, 2);
        }

        for (int x = 0; x < business; x++) {
            Passenger p = new Business(1, 1);
            b.confirmBooking(p, 2);
        }

        for (int x = 0; x < premium; x++) {
            Passenger p = new Premium(1, 1);
            b.confirmBooking(p, 2);
        }

        for (int x = 0; x < economy; x++) {
            Passenger p = new Economy(1, 1);
            b.confirmBooking(p, 2);
        }
    }

    @Test
    public void testSimpleConstructorFillDefaultPlane() throws AircraftException, PassengerException {
       fillPlaneWithProvidedValues(FIRST, BUSINESS, PREMIUM, ECONOMY);
        assertTrue(b.flightFull());
        assertEquals(BUSINESS, b.getNumBusiness());
        assertEquals(FIRST, b.getNumFirst());
        assertEquals(PREMIUM, b.getNumPremium());
        assertEquals(ECONOMY, b.getNumEconomy());
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlaneFirst() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(FIRST + 1 , BUSINESS, PREMIUM, ECONOMY);
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlaneBusiness() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(FIRST, BUSINESS + 1, PREMIUM, ECONOMY);
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlanePremium() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(FIRST, BUSINESS, PREMIUM + 1, ECONOMY);
    }
    
    @Test (expected = AircraftException.class)
    public void testSimpleConstructorOverFillDefaultPlaneEconomy() throws AircraftException, PassengerException {
        fillPlaneWithProvidedValues(FIRST, BUSINESS, PREMIUM, ECONOMY + 1);
    }

    
    
    
    

}
