/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Passengers;

/**
 * Specialization of the {@link asgn2Passengers.Passenger} class for the 'premium-class' passengers.
 * 
 * @author Andrew Carr (N9172190)
 * @version 1.0
 *
 */
public class Premium extends Passenger {

    /**
     * Premium Constructor (Partially Supplied)
     * Passenger is created in New state, later given a Confirmed Premium Class reservation, 
     * Queued, or Refused booking if waiting list is full. 
     * 
     * @param bookingTime <code>int</code> day of the original booking. 
     * @param departureTime <code>int</code> day of the intended flight.  
     * @throws PassengerException if invalid bookingTime or departureTime 
     * @see asgnPassengers.Passenger#Passenger(int,int)
     */
    public Premium(int bookingTime,int departureTime) throws PassengerException {
        super(bookingTime, departureTime);
        this.passID = "P:" + this.passID;
    }

    /**
     * Simple constructor to support {@link asgn2Passengers.Passenger#upgrade()} in other subclasses
     */
    protected Premium() {
        // Set the prefix for the new (upgraded) passenger
        // The original passID will be appended in copyPassengerState()
        this.passID = "P(U)";
    }

    @Override
    public Passenger upgrade() {
        // Upgrade Premium (P) to Business (J)
        Business p = new Business();
        p.copyPassengerState(this);
        return p;
    }

    @Override
    public String noSeatsMsg() {
        return "No seats available in Premium";
    }
}
