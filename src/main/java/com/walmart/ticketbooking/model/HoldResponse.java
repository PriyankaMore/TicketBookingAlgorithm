package com.walmart.ticketbooking.model;

import java.util.List;

/**
 * 
 * @author Priyanka
 * @Created on 7/7/2017
 * 
 * This Entity that contains held seats details 
 *
 */
public class HoldResponse {
	private String venue;
	private String refId;
	private List<Seat> heldSeats;

	public List<Seat> getHeldSeats() {
		return heldSeats;
	}

	public String getRefId() {
		return refId;
	}

	public String getVenue() {
		return venue;
	}

	public void setHeldSeats(List<Seat> heldSeats) {
		this.heldSeats = heldSeats;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	@Override
	public String toString() {
		return venue + "<br/>" + ", refId=" + refId + "<br/>" + ", heldSeats=" + heldSeats;
	}
}
