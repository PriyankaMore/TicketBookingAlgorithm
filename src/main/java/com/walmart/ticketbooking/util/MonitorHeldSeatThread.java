package com.walmart.ticketbooking.util;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.walmart.ticketbooking.model.Seat;
@Component
public class MonitorHeldSeatThread{
	@Autowired
	SeatUtility seatUtil;
	
	private static final Logger log = LoggerFactory.getLogger(MonitorHeldSeatThread.class);
	
	@Scheduled(fixedRate = 5000)
	public void cleanUpHeldSeats() {
				log.info("Scheduled Threaad running to clean up Held seats greater than 1 minute");
				Map<String,List<Seat>> heldSeatMap = seatUtil.getHeldSeat();
				if(heldSeatMap==null || heldSeatMap.isEmpty()){
					return;
				}
				List<String>bookingToRemove = new ArrayList<String>();
				for (String refId : heldSeatMap.keySet()) {
					Instant heldTime = heldSeatMap.get(refId).get(0).getHoldTime();
					Duration timeElapsed = Duration.between(heldTime,Instant.now());
					log.info(timeElapsed.toString());
					if(timeElapsed.toMillis()>60000){
						bookingToRemove.add(refId);
					}	
				}
				seatUtil.removeHeldSeat(bookingToRemove);	
	}
}
