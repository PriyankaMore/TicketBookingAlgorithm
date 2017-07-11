package com.walmart.ticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.ticketbooking.service.SeatsService;

/****
 * Created on 7/8/2017
 * 
 * @author Priyanka
 * 
 *         This Controller is used for booking the tickets/ Checking
 *         Availability/ Hold the reservations
 *
 *         Assuming lower seat numbers are the best available seats
 */

@RestController
public class TicketController {

	@Autowired
	private SeatsService seatService;

	/**
	 * 
	 * @param refId 
	 * 		 holdRefId is used to book the held seats
	 * @return String 
	 * 		 Seating arrangements in form of String
	 * @throws Exception 
	 * 		 To check if reference id is not null
	 */
	@RequestMapping("/bookSeats/{refId}")
	public String bookSeats(@PathVariable("refId") String refId) throws Exception {
		if(refId !=null){
			boolean booked = seatService.bookSeatsForCustomer(refId);
			if (booked) {
				return seatService.showSeats() + "\n Seats successfully booked";
			} else {
				return "Either your booking has expired or the refId does not exist";
			}
		}else
			throw new Exception("Enter Reference number");
	}

	/**
	 * 
	 * @return String - Seating arrangements in form of String
	 */
	@RequestMapping("/display")
	public String display() {
		return seatService.showSeats();
	}

	/**
	 * 
	 * @param rows
	 * 		This parameter represents number of rows in the Venue
	 * @param columns
	 * 		This parameter represents number of columns in the Venue
	 * @return String
	 * 		The function returns Seating arrangements in form of String
	 * @throws Exception 
	 * 		To check rows & columns are always greater than 0
	 */
	@RequestMapping("/generate/{rows}/{columns}")
	public String generate(@PathVariable("rows") int rows, @PathVariable("columns") int columns) throws Exception {
		if(rows>0 && columns >0){
			seatService.buildSeats(rows, columns);
			return seatService.showSeats();
		}else
			throw new Exception("Enter Valid rows and Columns");
	}

	/**
	 * 
	 * @return String 
	 * 		The function returns Seating arrangements in form of String &
	 * 		Number of seats available
	 */
	@RequestMapping("/getAllSeats")
	public String getAvailableSeats() {
		return seatService.showSeats() + "\n" + seatService.getAvailableSeats();
	}

	/**
	 * 
	 * @param noOfSeats
	 * 		Total Number of seats to be held
	 * @return String
	 * 		The function returns Seating arrangements in form of String &
	 * 		Return the holdRefId after holding the seats 		
	 * @throws Exception 
	 * 		To check number of seats are always greater than 0
	 */
	@RequestMapping("/holdSeats/{nosOfSeats}")
	public String holdSeats(@PathVariable("nosOfSeats") int nosOfSeats) throws Exception {
		if(nosOfSeats > 0)
			return seatService.holdSeatsForCustomer(nosOfSeats).toString();
		else
			throw new Exception("Number of Seats shoould be greater than 0");
	}

}
