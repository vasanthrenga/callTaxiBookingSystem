package callTaxiBookingSystem;

import java.util.ArrayList;

public class Taxi extends Thread {
	int id;// taxi number
	char currentPoint = 'A';
	double totalEarn = 0.0;
	ArrayList<Integer> bookingId = new ArrayList<Integer>();
	ArrayList<Integer> custId = new ArrayList<Integer>();
	ArrayList<Character> from = new ArrayList<Character>();
	ArrayList<Character> to = new ArrayList<Character>();
	ArrayList<Integer> pickUpTime = new ArrayList<Integer>();
	ArrayList<Integer> dropTime = new ArrayList<Integer>();
	ArrayList<Double> amount = new ArrayList<Double>();
	boolean isFree = true;

	Taxi(int id) {
		this.id = id;
	}

	void assign(int bookid, int cid, char pp, char dp, int pt) {
		bookingId.add(bookid);
		custId.add(cid);
		from.add(pp);
		to.add(dp);
		pickUpTime.add(pt);
		dropTime.add(pt + Math.abs(pp - dp));
		amount.add((double) 100 + ((15 * Math.abs(pp - dp)) - 5) * 10);// get distance
		totalEarn += (double) 100 + ((15 * Math.abs(pp - dp)) - 5) * 10;
		System.out.println("amount to pay : " + (100 + ((15 * Math.abs(pp - dp)) - 5) * 10));
		this.currentPoint = dp;// update current point with drop point

	}
}
