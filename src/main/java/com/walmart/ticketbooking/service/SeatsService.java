package com.walmart.ticketbooking.service;

import java.util.List;

import com.walmart.ticketbooking.model.HoldResponse;
import com.walmart.ticketbooking.model.Seat;

/**
 * 
 * @author Priyanka
 * @Created on 7/7/2017
 */
public interface SeatsService {

	public boolean bookSeatsForCustomer(String refId);
	public List<Seat> buildSeats(int rows, int columns);		
	public int getAvailableSeats();
	public HoldResponse holdSeatsForCustomer(int nosOfSeats);
	public String showSeats();


}
