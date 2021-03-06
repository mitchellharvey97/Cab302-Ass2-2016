/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Passengers;

/**
 * Specialization of the {@link asgn2Passengers.Passenger} class for the 'economy-class' passengers.
 * 
 * @author Andrew Carr (N9172190)
 * @version 1.0
 *
 */
public class Economy extends Passenger {

    /**
     * Economy Constructor (Partially Supplied)
     * Passenger is created in New state, later given a Confirmed Economy Class reservation, 
     * Queued, or Refused booking if waiting list is full. 
     * 
     * @param bookingTime <code>int</code> day of the original booking. 
     * @param departureTime <code>int</code> day of the intended flight.  
     * @throws PassengerException if invalid bookingTime or departureTime 
     * @see asgnPassengers.Passenger#Passenger(int,int)
     */
    public Economy(int bookingTime,int departureTime) throws PassengerException {
        super(bookingTime, departureTime);
        this.passID = "Y:" + this.passID;
    }

    @Override
    public String noSeatsMsg() {
        return "No seats available in Economy";
    }

    @Override
    public Passenger upgrade() {
        // Upgrade Economy (Y) to Premium (P)
        Premium p = new Premium();
        p.copyPassengerState(this);
        return p;
    }
}
