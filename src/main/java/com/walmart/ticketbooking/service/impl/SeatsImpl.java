package com.walmart.ticketbooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.ticketbooking.model.HoldResponse;
import com.walmart.ticketbooking.model.Seat;
import com.walmart.ticketbooking.service.SeatsService;
import com.walmart.ticketbooking.util.SeatUtility;
/**
 * 
 * @author Priyanka
 * @Created on 7/7/2017
 *
 */
@Service
public class SeatsImpl implements SeatsService{
	private List<Seat> seatList;

	@Autowired
	SeatUtility seatutil;

	SeatsImpl() {
		this.seatList = new ArrayList<Seat>();		
	}

	/**
	 * 
	 * @param refId
	 * 		holdRefId is used to book the held seats
	 * @return
	 * 		if seats are booked the function return true , else false
	 */
	@Override
	public boolean bookSeatsForCustomer(String refId){
		return seatutil.bookSeatsForCustomer(refId);		
	}

	/**
	 * 
	 * @param rows
	 * 		This parameter represents number of rows in the Venue
	 * @param columns
	 * 		This parameter represents number of columns in the Venue
	 * @return List<Seat>
	 * 		The function returns list of seats built on basis of rows and columns 		
	 */
	@Override
	public List<Seat> buildSeats(int rows, int columns){
		seatList =  seatutil.buildSeats(rows, columns);			
		return seatList;
	}

	@Override
	public int getAvailableSeats() {
		return seatutil.getAvailableSeat().size();
	}

	public List<Seat> getSeatList() {
		return seatList;
	}

	/**
	 * 
	 * @param noOfSeats
	 * 		Total Number of seats to be held
	 * @return HoldResponse
	 * 		Return the HoldResponse object after holding the seats
	 * 		HoldResponse object contains holdRefId, link for booking held seats
	 */
	@Override
	public HoldResponse holdSeatsForCustomer(int nosOfSeats) {
		
		String refId = seatutil.holdSeatsForCustomer(nosOfSeats);
		HoldResponse response = new HoldResponse();
		response.setVenue(seatutil.showSeats());
		if(refId==null){
			response.setHeldSeats(null);
			response.setRefId("Seats are Not Available At This Time");

		}else{
			response.setRefId("Use this url to confirm booking http://localhost:8080/bookSeats/"+refId);
			response.setHeldSeats(seatutil.getHeldSeat().get(refId));
		}
		return response;
	}

	public void setSeatList(List<Seat> seatList) {
		this.seatList = seatList;
	}

	/**
	 * 
	 * @return
	 * 		This function returns the String that represents the seating arrangement
	 */
	@Override
	public String showSeats() {
		return seatutil.showSeats();
	}

}
