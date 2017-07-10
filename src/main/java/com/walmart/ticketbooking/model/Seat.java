package com.walmart.ticketbooking.model;

import java.time.Instant;
import com.walmart.ticketbooking.util.SeatStatus;

/**
 * 
 * @author Priyanka
 * @Created on 7/7/2017
 * Entity that holds seat details
 */
public class Seat implements Comparable<Seat> {
	private int number;
	private SeatStatus status;
	private Instant holdTime;

	public Seat(int number) {
		this.number = number;
		this.status = SeatStatus.AVAILABLE;
	}

	@Override
	public int compareTo(Seat o) {
		return this.number - o.number;
	}

	public Instant getHoldTime() {
		return holdTime;
	}

	public int getNumber() {
		return number;
	}

	public SeatStatus getStatus() {
		return status;
	}

	public void setHoldTime(Instant holdTime) {
		this.holdTime = holdTime;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Seat [number=" + number + "]";
	}

}
