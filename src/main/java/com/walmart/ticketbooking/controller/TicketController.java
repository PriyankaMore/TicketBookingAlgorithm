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

	@RequestMapping("/getAllSeats")
	public String getAvailableSeats() {
		return seatService.showSeats() + "<br/>" + seatService.getAvailableSeats();
	}

	@RequestMapping("/generate/{rows}/{columns}")
	public String generate(@PathVariable("rows") int rows, @PathVariable("columns") int columns) {
		seatService.buildSeats(rows, columns);
		return seatService.showSeats();
	}

	@RequestMapping("/display")
	public String display() {
		return seatService.showSeats();

	}

	@RequestMapping("/holdSeats/{nosOfSeats}")
	public String holdSeats(@PathVariable("nosOfSeats") int nosOfSeats) {
		return seatService.holdSeatsForCustomer(nosOfSeats).toString();
	}

	@RequestMapping("/bookSeats/{refId}")
	public String bookSeats(@PathVariable("refId") String refId) throws Exception {
		boolean booked = seatService.bookSeatsForCustomer(refId);
		if (booked) {
			return seatService.showSeats() + "<br/> Seats successfully booked";
		} else {
			return "Either your booking has expired or the refId does not exist";
		}

	}

}
