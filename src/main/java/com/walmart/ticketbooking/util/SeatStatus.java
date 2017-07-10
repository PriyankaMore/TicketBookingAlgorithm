package com.walmart.ticketbooking.util;

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
