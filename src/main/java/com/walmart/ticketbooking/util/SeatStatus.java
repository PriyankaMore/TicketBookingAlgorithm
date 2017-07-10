package com.walmart.ticketbooking.util;
/**
 * 
 * @author Priyanka
 * This enum contains list of status a seat can have
 *
 */

public enum SeatStatus {
	AVAILABLE('A'), HOLD('H'), BOOKED('B');

	private char value;

	private SeatStatus(char value) {
		this.value = value;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}
}
