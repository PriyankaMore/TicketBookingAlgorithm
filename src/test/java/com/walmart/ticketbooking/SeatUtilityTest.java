package com.walmart.ticketbooking;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.walmart.ticketbooking.model.Seat;
import com.walmart.ticketbooking.util.SeatUtility;

public class SeatUtilityTest {
	
	SeatUtility seatUtil;
	@Before
	public void init(){
		seatUtil = new SeatUtility();
		seatUtil.buildSeats(5,5);
	}
	
	@Test
	public void buildTest(){
		List<Seat> seatList = seatUtil.getSeatList();
		assertEquals(25, seatList.size());
		assertEquals(0,seatList.get(0).getNumber());
		assertEquals(24,seatList.get(24).getNumber());
	}
	
	@Test
	public void showSeatTest(){
		String expectedStr = "----------[[ STAGE ]]----------<br/>AAAAA<br/>AAAAA<br/>AAAAA<br/>AAAAA<br/>AAAAA<br/>";
		assertEquals(expectedStr, seatUtil.showSeats());
	}
	
	@Test
	public void holdSeatTest(){
		String refId = seatUtil.holdSeatsForCustomer(5);
		assertEquals(20, seatUtil.getAvailableSeat().size());
		assertEquals(1, seatUtil.getHeldSeat().size());
		assertEquals(5, seatUtil.getHeldSeat().get(refId).size());
	}
	
	@Test
	public void holdNextInLineTest(){
		String ref1 = seatUtil.holdSeatsForCustomer(3);
		List<String> strList = new ArrayList<String>();
		strList.add(ref1);
		seatUtil.removeHeldSeat(strList);
		String ref3=seatUtil.holdSeatsForCustomer(2);
		assertEquals(0, seatUtil.getHeldSeat().get(ref3).get(0).getNumber());

	}
	
	@Test
	public void holdNextInLineTest2(){
		seatUtil.holdSeatsForCustomer(3);
		String ref2 = seatUtil.holdSeatsForCustomer(3);
		List<String> strList = new ArrayList<String>();
		strList.add(ref2);
		seatUtil.removeHeldSeat(strList);
		String ref3=seatUtil.holdSeatsForCustomer(2);
		assertEquals(3, seatUtil.getHeldSeat().get(ref3).get(0).getNumber());

	}
	
	@Test
	public void holdNextInLineTest3(){
		String ref1 = seatUtil.holdSeatsForCustomer(3);
		String ref2 = seatUtil.holdSeatsForCustomer(3);
		List<String> strList = new ArrayList<String>();
		strList.add(ref1);
		seatUtil.removeHeldSeat(strList);
		String ref3=seatUtil.holdSeatsForCustomer(4);
		assertEquals(6, seatUtil.getHeldSeat().get(ref3).get(0).getNumber());

	}
	
	
}
