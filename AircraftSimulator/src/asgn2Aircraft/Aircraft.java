/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Aircraft;

import java.util.ArrayList;
import java.util.List;

import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;
import asgn2Simulators.Log;

/**
 * The <code>Aircraft</code> class provides facilities for modelling a
 * commercial jet aircraft with multiple travel classes. New aircraft types are
 * created by explicitly extending this class and providing the necessary
 * configuration information.
 * 
 * In particular, <code>Aircraft</code> maintains a collection of currently
 * booked passengers, those with a Confirmed seat on the flight. Queueing and
 * Refused bookings are handled by the main {@link asgn2Simulators.Simulator}
 * class.
 * 
 * The class maintains a variety of constraints on passengers, bookings and
 * movement between travel classes, and relies heavily on the asgn2Passengers
 * hierarchy. Reports are also provided for logging and graphical display.
 * 
 * @author hogan
 *
 */
public abstract class Aircraft {

	protected int firstCapacity;
	protected int businessCapacity;
	protected int premiumCapacity;
	protected int economyCapacity;
	protected int capacity;

	protected int numFirst;
	protected int numBusiness;
	protected int numPremium;
	protected int numEconomy;

	protected String flightCode;
	protected String type;
	protected int departureTime;
	protected String status;
	protected List<Passenger> seats;

	/**
	 * Constructor sets flight info and the basic size parameters.
	 * 
	 * @param flightCode
	 *            <code>String</code> containing flight ID
	 * @param departureTime
	 *            <code>int</code> scheduled departure time
	 * @param first
	 *            <code>int</code> capacity of First Class
	 * @param business
	 *            <code>int</code> capacity of Business Class
	 * @param premium
	 *            <code>int</code> capacity of Premium Economy Class
	 * @param economy
	 *            <code>int</code> capacity of Economy Class
	 * @throws AircraftException
	 *             if isNull(flightCode) OR (departureTime <=0) OR
	 *             ({first,business,premium,economy} <0)
	 */
	public Aircraft(String flightCode, int departureTime, int first, int business, int premium, int economy)
			throws AircraftException {
		
		// Check input for errors
		if (flightCode == null) {
			throw new AircraftException("Flight code cannot be null.");
		} else if (departureTime <= 0) {
			throw new AircraftException("Departure time must be greater than zero.");
		} else if (first < 0) {
			throw new AircraftException("First class capacity cannot be less than zero.");
		} else if (business < 0) {
			throw new AircraftException("Business class capacity cannot be less than zero.");
		} else if (premium < 0) {
			throw new AircraftException("Premium class capacity cannot be less than zero.");
		} else if (economy < 0) {
			throw new AircraftException("Economy class capacity cannot be less than zero.");
		}

		// Modify the aircraft
		this.flightCode = flightCode;
		this.departureTime = departureTime;
		this.firstCapacity = first;
		this.businessCapacity = business;
		this.premiumCapacity = premium;
		this.economyCapacity = economy;
		
		// Initialise variables
		this.status = "";
		this.seats = new ArrayList<Passenger>();
	}

	/**
	 * Method to remove passenger from the aircraft - passenger must have a
	 * confirmed seat prior to entry to this method.
	 *
	 * @param p
	 *            <code>Passenger</code> to be removed from the aircraft
	 * @param cancellationTime
	 *            <code>int</code> time operation performed
	 * @throws PassengerException
	 *             if <code>Passenger</code> is not Confirmed OR
	 *             cancellationTime is invalid. See
	 *             {@link asgn2Passengers.Passenger#cancelSeat(int)}
	 * @throws AircraftException
	 *             if <code>Passenger</code> is not recorded in aircraft seating
	 */
	public void cancelBooking(Passenger p, int cancellationTime) throws PassengerException, AircraftException {
		// Check passenger's booking
		if (!seats.contains(p)) {
			throw new AircraftException("Passenger not recorded in aircraft seating. Cannot cancel booking.");
		}
		
		// Cancel passenger's booking
		p.cancelSeat(cancellationTime);
		this.seats.remove(p);
		this.status += Log.setPassengerMsg(p, "C", "N");
	}

	/**
	 * Method to add a Passenger to the aircraft seating. Precondition is a test
	 * that a seat is available in the required fare class
	 * 
	 * @param p
	 *            <code>Passenger</code> to be added to the aircraft
	 * @param confirmationTime
	 *            <code>int</code> time operation performed
	 * @throws PassengerException
	 *             if <code>Passenger</code> is in incorrect state OR
	 *             confirmationTime OR departureTime is invalid. See
	 *             {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException
	 *             if no seats available in <code>Passenger</code> fare class.
	 */
	public void confirmBooking(Passenger p, int confirmationTime) throws AircraftException, PassengerException {
		// Check seat availability
		if (p instanceof First) { 
			if (numFirst >= firstCapacity) {
				throw new AircraftException("No seats available in First class. Cannot confirm booking.");
			}
		} else if (p instanceof Business) {
			if (numBusiness >= businessCapacity) {
				throw new AircraftException("No seats available in Business class. Cannot confirm booking.");
			}
		} else if (p instanceof Premium) {
			if (numPremium >= premiumCapacity) {
				throw new AircraftException("No seats available in Premium class. Cannot confirm booking.");
			}
		} else if (p instanceof Economy) {
			if (numEconomy >= economyCapacity) {
				throw new AircraftException("No seats available in Economy class. Cannot confirm booking.");
			}
		}
		
		// Confirm passenger's booking
		p.confirmSeat(confirmationTime, this.departureTime);
		this.seats.add(p);
		this.status += Log.setPassengerMsg(p, "N/Q", "C");
	}

	/**
	 * State dump intended for use in logging the final state of the aircraft.
	 * (Supplied)
	 * 
	 * @return <code>String</code> containing dump of final aircraft state
	 */
	public String finalState() {
		String str = aircraftIDString() + " Pass: " + this.seats.size() + "\n";
		for (Passenger p : this.seats) {
			str += p.toString() + "\n";
		}
		return str + "\n";
	}

	/**
	 * Simple status showing whether aircraft is empty
	 * 
	 * @return <code>boolean</code> true if aircraft empty; false otherwise
	 */
	public boolean flightEmpty() {
		return (this.numFirst + this.numBusiness + this.numPremium + this.numEconomy > 0);
	}

	/**
	 * Simple status showing whether aircraft is full
	 * 
	 * @return <code>boolean</code> true if aircraft full; false otherwise
	 */
	public boolean flightFull() {
		// Changed to greater than or equal to in case booking errors over book a
		// flight it will still come up as full
		return (this.numFirst >= this.firstCapacity &&
				this.numBusiness >= this.businessCapacity &&
				this.numPremium >= this.premiumCapacity &&
				this.numEconomy >= this.economyCapacity);
	}

	/**
	 * Method to finalise the aircraft seating on departure. Effect is to change
	 * the state of each passenger to Flown. departureTime parameter allows for
	 * rescheduling
	 * 
	 * @param departureTime
	 *            <code>int</code> actual departureTime from simulation
	 * @throws PassengerException
	 *             if <code>Passenger</code> is in incorrect state See
	 *             {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 */
	public void flyPassengers(int departureTime) throws PassengerException {
		for (Passenger pas : this.seats) {
			pas.flyPassenger(departureTime);
		}
	}

	/**
	 * Method to return an {@link asgn2Aircraft.Bookings} object containing the
	 * Confirmed booking status for this aircraft.
	 * 
	 * @return <code>Bookings</code> object containing the status.
	 */
	public Bookings getBookings() {
		Bookings aircraft_status = new Bookings(this.numFirst, this.numBusiness, this.numPremium, this.numEconomy,
				this.seats.size(), this.capacity);
		return aircraft_status;
	}

	/**
	 * Simple getter for number of confirmed Business Class passengers
	 * 
	 * @return <code>int</code> number of Business Class passengers
	 */
	public int getNumBusiness() {
		return numBusiness;
		
		//int num_confirm_business = 0;
		//for (Passenger pas : this.seats) {
		//	if (pas.isConfirmed() && pas instanceof Business) {
		//		num_confirm_business++;
		//	}
		//}
		//return num_confirm_business;
	}

	/**
	 * Simple getter for number of confirmed Economy passengers
	 * 
	 * @return <code>int</code> number of Economy Class passengers
	 */
	public int getNumEconomy() {
		return numEconomy;
		
		//int num_confirm_eco = 0;
		//for (Passenger pas : this.seats) {
		//	if (pas.isConfirmed() && pas instanceof Economy) {
		//		num_confirm_eco++;
		//	}
		//}
		//return num_confirm_eco;
	}

	/**
	 * Simple getter for number of confirmed First Class passengers
	 * 
	 * @return <code>int</code> number of First Class passengers
	 */
	public int getNumFirst() {
		return numFirst;
		
		//int num_confirm_first = 0;
		//for (Passenger pas : this.seats) {
		//	if (pas.isConfirmed() && pas instanceof First) {
		//		num_confirm_first++;
		//	}
		//}
		//return num_confirm_first;
	}

	/**
	 * Simple getter for the total number of confirmed passengers
	 * 
	 * @return <code>int</code> number of Confirmed passengers
	 */
	public int getNumPassengers() {
		return numFirst + numBusiness + numPremium + numEconomy;
		
		//int num_confirm = 0;
		//for (Passenger pas : this.seats) {
		//	if (pas.isConfirmed()) {
		//		num_confirm++;
		//	}
		//}
		//return num_confirm;
	}

	/**
	 * Simple getter for number of confirmed Premium Economy passengers
	 * 
	 * @return <code>int</code> number of Premium Economy Class passengers
	 */
	public int getNumPremium() {
		return numPremium;
		
		//int num_confirm_prem = 0;
		//for (Passenger pas : this.seats) {
		//	if (pas.isConfirmed() && pas instanceof Premium) {
		//		num_confirm_prem++;
		//	}
		//}
		//return num_confirm_prem;
	}

	/**
	 * Method to return an {@link java.util.List} object containing a copy of
	 * the list of passengers on this aircraft.
	 * 
	 * @return <code>List<Passenger></code> object containing the passengers.
	 */
	public List<Passenger> getPassengers() {
		return this.seats;
	}

	/**
	 * Method used to provide the current status of the aircraft for logging.
	 * (Supplied) Uses private status <code>String</code>, set whenever a
	 * transition occurs.
	 * 
	 * @return <code>String</code> containing current aircraft state
	 */
	public String getStatus(int time) {
		String str = time + "::" + this.seats.size() + "::" + "F:" + this.numFirst + "::J:" + this.numBusiness + "::P:"
				+ this.numPremium + "::Y:" + this.numEconomy;
		str += this.status;
		this.status = "";
		return str + "\n";
	}

	/**
	 * Simple boolean to check whether a passenger is included on the aircraft
	 * 
	 * @param p
	 *            <code>Passenger</code> whose presence we are checking
	 * @return <code>boolean</code> true if isConfirmed(p); false otherwise
	 */
	public boolean hasPassenger(Passenger p) {
		return seats.contains(p);
		//return p.isConfirmed();
		
		//for (Passenger pas : this.seats) {
		//	if (pas.getPassID() == p.getPassID()) {
		//		return true;
		//	}
		//}
		//return false;
	}

	/**
	 * State dump intended for logging the aircraft parameters (Supplied)
	 * 
	 * @return <code>String</code> containing dump of initial aircraft
	 *         parameters
	 */
	public String initialState() {
		return aircraftIDString() + " Capacity: " + this.capacity + " [F: " + this.firstCapacity + " J: "
				+ this.businessCapacity + " P: " + this.premiumCapacity + " Y: " + this.economyCapacity + "]";
	}

	/**
	 * Given a Passenger, method determines whether there are seats available in
	 * that fare class.
	 * 
	 * @param p
	 *            <code>Passenger</code> to be Confirmed
	 * @return <code>boolean</code> true if seats in Class(p); false otherwise
	 */
	public boolean seatsAvailable(Passenger p) {
		if (p instanceof First) {
			return (this.numFirst < this.firstCapacity);
		} else if (p instanceof Business) {
			return (this.numBusiness < this.businessCapacity);
		} else if (p instanceof Premium) {
			return (this.numPremium < this.premiumCapacity);
		} else if (p instanceof Economy) {
			return (this.numEconomy < this.economyCapacity);
		} else { // Fall Back error case
			return false;
		}
	}

	/*
	 * (non-Javadoc) (Supplied)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return aircraftIDString() + " Count: " + this.seats.size() + " [F: " + numFirst + " J: " + numBusiness + " P: "
				+ numPremium + " Y: " + numEconomy + "]";
	}

	/**
	 * Method to upgrade Passengers to try to fill the aircraft seating. Called
	 * at departureTime. Works through the aircraft fare classes in descending
	 * order of status. No upgrades are possible from First, so we consider
	 * Business passengers (upgrading if there is space in First), then Premium,
	 * upgrading to fill spaces already available and those created by upgrades
	 * to First), and then finally, we do the same for Economy, upgrading where
	 * possible to Premium.
	 */
	public void upgradeBookings() {
		// Removed exception from this method as per email from Jim. - Andrew
		
		// Upgrading Business to First
		while (this.numFirst < this.firstCapacity && this.numBusiness > 0) {
			for (Passenger pas : this.seats) {
				if (pas instanceof Business) {
					pas = pas.upgrade();
					this.numBusiness--;
				}
				if (this.numFirst >= this.firstCapacity) {
					break;
				}
			}
		}
		
		// Upgrading Premium to Business
		while (this.numBusiness < this.businessCapacity && this.numPremium > 0) {
			for (Passenger pas : this.seats) {
				if (pas instanceof Premium) {
					pas = pas.upgrade();
					this.numPremium--;
				}
				if (this.numBusiness >= this.businessCapacity) {
					break;
				}
			}
		}

		// Upgrading Economy to Premium
		while (this.numPremium < this.premiumCapacity && this.numEconomy > 0) {
			for (Passenger pas : this.seats) {
				if (pas instanceof Economy) {
					pas = pas.upgrade();
					this.numEconomy--;
				}
				if (this.numPremium >= this.premiumCapacity) {
					break;
				}
			}
		}

	}

	/**
	 * Simple String method for the Aircraft ID
	 * 
	 * @return <code>String</code> containing the Aircraft ID
	 */
	private String aircraftIDString() {
		return this.type + ":" + this.flightCode + ":" + this.departureTime;
	}

	// Various private helper methods to check arguments and throw exceptions,
	// to increment
	// or decrement counts based on the class of the Passenger, and to get the
	// number of seats
	// available in a particular class

	// Used in the exception thrown when we can't confirm a passenger
	/**
	 * Helper method with error messages for failed bookings
	 * 
	 * @param p
	 *            Passenger seeking a confirmed seat
	 * @return msg string failure reason
	 */
//	private String noSeatsAvailableMsg(Passenger p) {
//		String msg = "";
//		return msg + p.noSeatsMsg();
//	}
}
