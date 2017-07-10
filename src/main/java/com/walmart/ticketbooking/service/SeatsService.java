package com.walmart.ticketbooking.service;

import java.util.List;

import com.walmart.ticketbooking.model.HoldResponse;
import com.walmart.ticketbooking.model.Seat;


public interface SeatsService {
	
	public String showSeats();
	public List<Seat> buildSeats(int rows, int columns);		
	public HoldResponse holdSeatsForCustomer(int nosOfSeats);
	public boolean bookSeatsForCustomer(String refId) throws Exception;
	public int getAvailableSeats();
	
	
}
