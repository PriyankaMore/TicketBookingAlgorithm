package com.walmart.ticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 
 * @author Priyanka
 * @Created on  7/7/2017
 *
 */
@SpringBootApplication
@EnableScheduling
public class TicketBookingAlgorithmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingAlgorithmApplication.class, args);
	}
}
