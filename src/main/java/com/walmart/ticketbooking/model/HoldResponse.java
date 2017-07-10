package com.walmart.ticketbooking.model;

import java.util.List;

public class HoldResponse {
	private String venue;
	private String refId;
	private List<Seat> heldSeats;

	@Override
	public String toString() {
		return venue + "<br/>" + ", refId=" + refId + "<br/>" + ", heldSeats=" + heldSeats;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public List<Seat> getHeldSeats() {
		return heldSeats;
	}

	public void setHeldSeats(List<Seat> heldSeats) {
		this.heldSeats = heldSeats;
	}
}
