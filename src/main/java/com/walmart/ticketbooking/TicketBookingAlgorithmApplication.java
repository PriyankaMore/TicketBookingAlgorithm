package com.walmart.ticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
class TicketBookingAlgorithmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingAlgorithmApplication.class, args);
	}
}
