//1) Design a Call taxi booking application
//        -There are n number of taxi�s. For simplicity, assume 4.
//        (But it should work for any number of taxi�s)
//        -The are 6 points(A,B,C,D,E,F)
//        -All the points are in a straight line.
//        -each point is 15kms away from the adjacent points.
//        -It takes 60 mins to travel from one point to another(15km in 60 mins)
//        -Each taxi charges Rs.100 minimum for the first 5 kilometers
//        -Rs.10 for the subsequent kilometers.
//        -For simplicity, time can be entered as absolute time. Eg: 9hrs, 15hrs etc.
//        -All taxi�s are initially stationed at A.
//        -When a customer books a Taxi, a free taxi at that point is allocated
//        -If no free taxi is available at that point, a free taxi at the nearest point is allocated.
//        -If two taxi�s are free at the same point, one with lower earning is allocated
//        -Note that the taxi only charges the customer from the pickup point to the drop point.
//        Not the distance it travels from an adjacent point to pickup the customer.
//        -If no taxi is free at that time, booking is rejected
//
//        Design modules for
//
//        1)    Call taxi booking
//        Input 1:
//        Customer ID: 1
//        Pickup Point: A
//        Drop Point: B
//        Pickup Time: 9
//
//        Output 1:
//        Taxi can be allotted.
//        Taxi-1 is allotted
//
//        Input 2:
//        Customer ID: 2
//        Pickup Point: B
//        Drop Point: D
//        Pickup Time: 9
//
//        Output 1:
//        Taxi can be allotted.
//        Taxi-2 is allotted
//        (Note: Since Taxi-1 would have completed its journey when second booking is done, so Taxi-2 from nearest point A which is free is allocated)
//
//
//        Input 3:
//        Customer ID: 3
//        Pickup Point: B
//        Drop Point: C
//        Pickup Time: 12
//
//        Output 1:
//        Taxi can be allotted.
//        Taxi-1 is allotted
//        2) Display the Taxi details
//
//        Taxi No:    Total Earnings:
//        BookingID    CustomerID    From    To    PickupTime    DropTime    Amount
//
//        Output:
//        Taxi-1    Total Earnings: Rs. 400
//
//        1    1    A    B     9    10    200
//        3    3    B    C    12    13    200
//
//        Taxi-2 Total Earnings: Rs. 350
//        2    2    B    D    9    11    350
//        These were just sample inputs.
//        It should work for any input that they give.
//        Those who finished both the modules within 3 hours and if it worked for all the inputs they give,
//        those candidates were given extra modules to work with.

package callTaxiBookingSystem;

import java.util.*;

public class Main {
	static Scanner in = new Scanner(System.in);
	static int bookid = 1;
	static int numberOfTaxies = 4;
	static ArrayList<Taxi> taxies = new ArrayList<Taxi>();

	// constructor
	Main() {
		for (int i = 1; i <= numberOfTaxies; i++) {
			Taxi t = new Taxi(i);
			taxies.add(t);
		}
	}

	static void bookTaxi() {// get details for booking and allot taxi
		System.out.print("Enter Customer ID: ");
		int cid = in.nextInt();
		System.out.print("Enter Pickup Point: ");
		char pp = in.next().toUpperCase().charAt(0);// from
		System.out.print("Enter Drop Point: ");
		char dp = in.next().toUpperCase().charAt(0);// to
		System.out.print("Enter Pickup Time: ");
		int pt = in.nextInt();
		int rej = 1;// for reject a booking

		for (Taxi t : taxies) {
			if (t.isFree == true)
				rej = 0;// means atleast one taxi is free
		}
		if (rej == 1) {// means no taxi is free, so reject the booking
			System.out.println(" sorry No taxi is Available Now :-( ");
			System.out.println("sorry your booking is rejected ");
			return;
		}

		ArrayList<Taxi> nearestTaxies = new ArrayList<>();
		for (Taxi t1 : taxies) {// get nearest taxies in another array list
			if (t1.isFree == true) {
				nearestTaxies.add(t1);
			}
		}
		if (nearestTaxies.size() == 1) {// if we have only one nearest taxi
			nearestTaxies.get(0).assign(bookid, cid, pp, dp, pt);
			bookid++;
			System.out.println("Taxi id " + nearestTaxies.get(0).id + " is Assigned ");
			TaxiHold th = new TaxiHold(nearestTaxies.get(0));
			th.start();
		} else {
			int minDis = Math.abs(nearestTaxies.get(0).currentPoint - pp);
			int countMinDis = 0;
			for (int i = 1; i < nearestTaxies.size(); i++) {// getting minimum distance
				if (minDis > Math.abs(nearestTaxies.get(i).currentPoint - pp)) {
					minDis = Math.abs(nearestTaxies.get(i).currentPoint - pp);
				}
			}
			for (int i = 0; i < nearestTaxies.size(); i++) {
				if (minDis == Math.abs(nearestTaxies.get(i).currentPoint - pp))
					countMinDis++;
				else {
					nearestTaxies.remove(i);
				}
			}
			if (countMinDis == 1) {
				nearestTaxies.get(0).assign(bookid, cid, pp, dp, pt);
				bookid++;
				System.out.println("Taxi id " + nearestTaxies.get(0).id + " is Assigned ");
				TaxiHold th = new TaxiHold(nearestTaxies.get(0));
				th.start();
			} else {
				double minEar = nearestTaxies.get(0).totalEarn;
				int countMinEar = 0;
				for (int i = 1; i < nearestTaxies.size(); i++) {// get minimum earning taxi from minimum distance taxies
					if (minEar > nearestTaxies.get(i).totalEarn) {
						minEar = nearestTaxies.get(i).totalEarn;
					}
				}
				for (int i = 0; i < nearestTaxies.size(); i++) {
					if (minEar == nearestTaxies.get(i).totalEarn)
						countMinEar++;
					else
						nearestTaxies.remove(i);
				}
				nearestTaxies.get(0).assign(bookid, cid, pp, dp, pt);
				bookid++;
				System.out.println("Taxi id " + nearestTaxies.get(0).id + " is Assigned ");
				TaxiHold th = new TaxiHold(nearestTaxies.get(0));
				th.start();
			}

		}
	}

	static void taxiDetails() {
		for (Taxi t : taxies) {
			if (t.totalEarn != 0.0) {
				System.out.println(
						"---------------------------------------------------------------------------------------");
				System.out.print("Taxi id - " + t.id);
				System.out.print("\t\t Total Earnings - " + t.totalEarn);
				System.out.println(
						"\n---------------------------------------------------------------------------------------");
				System.out.println("BookingID\tCustomerID\tFrom\tTo\tPickupTime\tDropTime\tAmount");
				for (int i = 0; i < t.bookingId.size(); i++) {
					System.out.print(t.bookingId.get(i));
					System.out.print("\t\t" + t.custId.get(i));
					System.out.print("\t\t" + t.from.get(i));
					System.out.print("\t" + t.to.get(i));
					System.out.print("\t" + t.pickUpTime.get(i));
					System.out.print("\t\t" + t.dropTime.get(i));
					System.out.print("\t\t" + t.amount.get(i));
				}
				System.out.println(
						"\n---------------------------------------------------------------------------------------\n");
			}
		}
	}

	static void taxiStatus() {
		for (Taxi t : taxies) {
			if (t.isFree == true)
				System.out.println("Taxi id " + t.id + " is free( Available Now)");
			else
				System.out.println("Taxi id " + t.id + " is busy ( Not Available Now ) ");
		}
	}

	public static void main(String args[]) {
		Main tb = new Main();
		while (true) {
			System.out.println("______Taxi Booking______");
			System.out.println("1.Book Taxi  ");
			System.out.println("2.Taxi Details  ");
			System.out.println("3.Taxies Status ");
			System.out.println("4.Exit ");
			System.out.print("Enter your choice : ");
			int ch = in.nextInt();
			switch (ch) {
				case 1: {
					bookTaxi();
					System.out.println();
					break;
				}
				case 2: {
					taxiDetails();
					System.out.println();
					break;
				}
				case 3: {
					taxiStatus();
					System.out.println();
					break;
				}
				case 4:
					System.exit(0);
	
				default: {
					System.out.println("Enter valid choice :(\n");
					break;
				}
			}
		}
	}
}