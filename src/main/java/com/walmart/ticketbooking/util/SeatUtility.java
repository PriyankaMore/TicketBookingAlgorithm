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
 * 
 * @author Priyanka Since there is no repository, Seats are held in a list
 */
@Service
public class SeatUtility {
	private List<Seat> seatList;
	private Map<String, List<Seat>> heldSeatMap;
	private Set<Seat> availableSeat;
	private int expirylimit;
	private int columns;

	public SeatUtility() {
		init();
	}

	private void init() {
		this.seatList = Collections.synchronizedList(new ArrayList<Seat>());
		this.heldSeatMap = new ConcurrentHashMap<String, List<Seat>>();
		this.expirylimit = 60;
		this.availableSeat = Collections.synchronizedSet(new TreeSet<Seat>());

	}

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

	public List<Seat> getSeatList() {
		return seatList;
	}

	public String showSeats() {

		String display = "----------[[ STAGE ]]----------" + "<br/>";
		for (int i = 0; i < seatList.size(); i++) {
			display = display + seatList.get(i).getStatus().getValue();
			if ((i + 1) % columns == 0) {
				display = display + "<br/>";
			}
		}

		return display;
	}

	public Map<String, List<Seat>> getHeldSeat() {
		return heldSeatMap;
	}

	public Set<Seat> getAvailableSeat() {
		return availableSeat;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getExpirylimit() {
		return expirylimit;
	}

	public void setSeatList(List<Seat> seatList) {
		this.seatList = seatList;
	}

	public String holdSeatsForCustomer(int noOfSeats) {
		if (availableSeat.size() < noOfSeats) {
			return null;
		}
		UUID holdRefId = UUID.randomUUID(); // Same holdReference number for
											// bookings done in one request
		List<Seat> holdSeats = null;
		holdSeats = findSeatsNextToEachOther(noOfSeats);
		if (holdSeats == null) {
			holdSeats = holdSeatsRandomly(noOfSeats);
		}
		heldSeatMap.put(holdRefId.toString(), holdSeats);
		return holdRefId.toString();
	}

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

	public boolean bookSeatsForCustomer(String refId) throws Exception {
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

}
