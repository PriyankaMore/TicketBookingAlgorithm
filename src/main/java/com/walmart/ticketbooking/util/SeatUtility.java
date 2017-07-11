package com.walmart.ticketbooking.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.walmart.ticketbooking.model.Seat;

/**
 * @author Priyanka 
 * @Created on 7/7/2017
 * Since there is no repository, Seats are held in a list
 * This class is replacement for Repository
 * All seats are stored in List
 * Available seats are stored in Set(Tree set)so collection remains sorted at all times
 * Held seats are stored in Map, key as holdRefId and value as List of seats
 */
@Service
public class SeatUtility {
	private List<Seat> seatList;
	private Map<String, List<Seat>> heldSeatMap;
	private Set<Seat> availableSeat;	
	private int columns;

	public SeatUtility() {
		init();
	}

	/**
	 * 
	 * @param Strig refId 
	 * 		holdRefId is used to retrieve seats from heldSeats collection and book the seats 
	 * 		Each booking request has unique holdRefId
	 * @return boolean
	 * 		if seats are booked the function returns true else false
	 * 
	 */
	public boolean bookSeatsForCustomer(String refId){
		List<Seat> seatsToBook = heldSeatMap.get(refId);
		if (seatsToBook == null || seatsToBook.isEmpty()) {
			return false;
		} else {
			heldSeatMap.remove(refId);
			for (Seat seats : seatsToBook) {
				seatList.get(seats.getNumber()).setStatus(SeatStatus.BOOKED);
			}
			return true;
		}
	}

	/**
	 * 
	 * @param rows
	 * 		This parameter represents number of rows in the Venue
	 * @param columns
	 * 		This parameter represents number of columns in the Venue
	 * @return List<Seat>
	 * 		The function returns list of seats built on basis of rows and columns
	 * 
	 */
	public List<Seat> buildSeats(int rows, int columns) {
		if (seatList.size() > 0) {
			init();
		}
		this.columns = columns;
		int numberOfSeats = rows * columns;
		for (int i = 0; i < numberOfSeats; i++) {
			seatList.add(new Seat(i));
		}
		this.availableSeat = new TreeSet<Seat>(seatList);
		return seatList;
	}

	/**
	 * 
	 * @param noOfSeats
	 * 		Number of seats to be held next to each other
	 * @return List<Seat>
	 * 		Returns collection of held seats
	 */

	private List<Seat> findSeatsNextToEachOther(int noOfSeats) {
		List<Seat> heldSeats = new ArrayList<Seat>();
		int seatFound = 0;
		int lastSeatNumber = -1;
		for (Seat seat : availableSeat) {
			if (lastSeatNumber == -1) {
				lastSeatNumber = seat.getNumber();
				seatFound++;
			} else if (lastSeatNumber == seat.getNumber() - 1) {
				lastSeatNumber++;
				seatFound++;
			} else {
				lastSeatNumber = seat.getNumber();
				seatFound = 1;
			}
			if (seatFound >= noOfSeats)
				break;
		}
		if (seatFound < noOfSeats) {
			return null;
		}
		Iterator<Seat> it = availableSeat.iterator();
		boolean startHolding = false;
		int seatCount = noOfSeats;
		while (it.hasNext() && seatCount > 0) {
			Seat seat = (Seat) it.next();
			if (startHolding || seat.getNumber() == lastSeatNumber - noOfSeats + 1) {
				startHolding = true;
				heldSeats.add(seat);
				seat.setHoldTime(Instant.now());
				it.remove();
				seatList.get(seat.getNumber()).setStatus(SeatStatus.HOLD);
				seatCount--;
			}
		}
		return heldSeats;

	}
	

	public Set<Seat> getAvailableSeat() {
		return availableSeat;
	}

	public int getColumns() {
		return columns;
	}

	public Map<String, List<Seat>> getHeldSeat() {
		return heldSeatMap;
	}

	public List<Seat> getSeatList() {
		return seatList;
	}

	/**
	 * 
	 * @param noOfSeats
	 * 		Total Number of seats to be held
	 * @return String
	 * 		Return the holdRefId after holding the seats
	 * 		holdRefId is used for booking the held seats
	 */
	public String holdSeatsForCustomer(int noOfSeats) {
		if (availableSeat.size() < noOfSeats) {
			return null;
		}
		UUID holdRefId = UUID.randomUUID(); 				// Same holdReference number for
															// bookings done in one request
		List<Seat> holdSeats = null;
		holdSeats = findSeatsNextToEachOther(noOfSeats);	//first find seats next to each other
		if (holdSeats == null) {							//if no seats are available next to each other
			holdSeats = holdSeatsRandomly(noOfSeats);		//find randomly available seats
		}
		heldSeatMap.put(holdRefId.toString(), holdSeats);	//Collection with heldRefId and List of seats held
		return holdRefId.toString();
	}

	/**
	 * 
	 * @param noOfSeats
	 * 		Number of seats to be held
	 * @return
	 * 		Returns collection of held seats
	 */
	private List<Seat> holdSeatsRandomly(int noOfSeats) {
		List<Seat> heldSeat = new ArrayList<Seat>();
		Iterator<Seat> iterator = availableSeat.iterator();
		int seatsBooked = 0;
		while (iterator.hasNext() && seatsBooked < noOfSeats) {
			Seat seat = (Seat) iterator.next();
			seatsBooked++;
			seat.setHoldTime(Instant.now());
			heldSeat.add(seat);
			iterator.remove();
			seatList.get(seat.getNumber()).setStatus(SeatStatus.HOLD);
		}
		return heldSeat;
	}

	private void init() {
		this.seatList = Collections.synchronizedList(new ArrayList<Seat>());
		this.heldSeatMap = new ConcurrentHashMap<String, List<Seat>>();		
		this.availableSeat = Collections.synchronizedSet(new TreeSet<Seat>());
	}

	/**
	 * 
	 * @param bookingToRemove
	 * 		List of holdRefId, this Id is used to remove the expired seats from
	 * 		heldSeats Collection and make them available
	 */
	public void removeHeldSeat(List<String> bookingToRemove) {
		for (String refId : bookingToRemove) {
			List<Seat> heldSeats = heldSeatMap.get(refId);
			for (Seat seat : heldSeats) {
				seatList.get(seat.getNumber()).setStatus(SeatStatus.AVAILABLE);
				seat.setHoldTime(null);
				availableSeat.add(seat);
			}
			heldSeatMap.remove(refId);
		}

	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setSeatList(List<Seat> seatList) {
		this.seatList = seatList;
	}

	/**
	 * 
	 * @return
	 * 		This function returns the String that represents the seating arrangement
	 */

	public String showSeats() {

		String display = "----------[[ STAGE ]]----------" + "\n";
		for (int i = 0; i < seatList.size(); i++) {
			display = display + seatList.get(i).getStatus().getValue();
			if ((i + 1) % columns == 0) {
				display = display + "\n";
				//<br/>
			}
		}

		return display;
	}

}
