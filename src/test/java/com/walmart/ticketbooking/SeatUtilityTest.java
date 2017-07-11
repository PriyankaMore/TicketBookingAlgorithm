package com.walmart.ticketbooking;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.walmart.ticketbooking.model.Seat;
import com.walmart.ticketbooking.util.SeatUtility;

public class SeatUtilityTest {
	
	SeatUtility seatUtil;
	@Test
	public void bookHoldSeatsTest(){
		String ref1 = seatUtil.holdSeatsForCustomer(5);
		seatUtil.bookSeatsForCustomer(ref1);
		assertEquals(20, seatUtil.getAvailableSeat().size());
		assertEquals(0, seatUtil.getHeldSeat().size());

	}
	
	@Test
	public void bookHoldSeatsTest2(){
		String ref1 = seatUtil.holdSeatsForCustomer(3);
		String ref2 = seatUtil.holdSeatsForCustomer(3);
		seatUtil.bookSeatsForCustomer(ref2);
		assertEquals(19, seatUtil.getAvailableSeat().size());
		assertEquals(1, seatUtil.getHeldSeat().size());
		assertEquals(3, seatUtil.getHeldSeat().get(ref1).size());

	}
	
	@Test
	public void buildTest(){
		List<Seat> seatList = seatUtil.getSeatList();
		assertEquals(25, seatList.size());
		assertEquals(0,seatList.get(0).getNumber());
		assertEquals(24,seatList.get(24).getNumber());
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
		seatUtil.holdSeatsForCustomer(3);
		List<String> strList = new ArrayList<String>();
		strList.add(ref1);
		seatUtil.removeHeldSeat(strList);
		String ref3=seatUtil.holdSeatsForCustomer(4);
		assertEquals(6, seatUtil.getHeldSeat().get(ref3).get(0).getNumber());

	}
	
	@Test
	public void holdSeatTest(){
		String refId = seatUtil.holdSeatsForCustomer(5);
		assertEquals(20, seatUtil.getAvailableSeat().size());
		assertEquals(1, seatUtil.getHeldSeat().size());
		assertEquals(5, seatUtil.getHeldSeat().get(refId).size());
	}
	
	@Test
	public void removeHoldSeatTest(){
		String refId = seatUtil.holdSeatsForCustomer(5);
		seatUtil.holdSeatsForCustomer(5);
		List<String> strList = new ArrayList<String>();
		strList.add(refId);		
		assertEquals(15, seatUtil.getAvailableSeat().size());
		assertEquals(2, seatUtil.getHeldSeat().size());
		seatUtil.removeHeldSeat(strList);
		assertEquals(20, seatUtil.getAvailableSeat().size());
		assertEquals(1, seatUtil.getHeldSeat().size());
		
	}
	
	
	@Before
	public void init(){
		seatUtil = new SeatUtility();
		seatUtil.buildSeats(5,5);
	}
	
	@Test
	public void showSeatTest(){
		String expectedStr = "----------[[ STAGE ]]----------\nAAAAA\nAAAAA\nAAAAA\nAAAAA\nAAAAA\n";
		assertEquals(expectedStr, seatUtil.showSeats());
	}
	
}
