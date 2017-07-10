package com.walmart.ticketbooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.ticketbooking.model.HoldResponse;
import com.walmart.ticketbooking.model.Seat;
import com.walmart.ticketbooking.service.SeatsService;
import com.walmart.ticketbooking.util.SeatUtility;

@Service
public class SeatsImpl implements SeatsService{
	private List<Seat> seatList;
	
	
	@Autowired
	SeatUtility seatutil;
	
	SeatsImpl() {
		this.seatList = new ArrayList<Seat>();		
	}
	
	
	@Override
	public List<Seat> buildSeats(int rows, int columns){
		seatList =  seatutil.buildSeats(rows, columns);			
		return seatList;
	}

	@Override
	public String showSeats() {
		return seatutil.showSeats();
	}
	
	
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
	
	
/***
 * find in Held Seats, if the seats are held for Customer
 * if yes then confirm booking else, return message to hold seats
 * @throws Exception 
 */
	@Override
	public boolean bookSeatsForCustomer(String refId) throws Exception {
		return seatutil.bookSeatsForCustomer(refId);		
	}
	
	
	/*private Instant calculateExpirytime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minusExpiredSeconds = now.minusSeconds(expirylimit);
        return minusExpiredSeconds.atZone(ZoneId.systemDefault()).toInstant();
    }
	*/
	public List<Seat> getSeatList() {
		return seatList;
	}

	public void setSeatList(List<Seat> seatList) {
		this.seatList = seatList;
	}


	@Override
	public int getAvailableSeats() {
		return seatutil.getAvailableSeat().size();
	}
	
}
