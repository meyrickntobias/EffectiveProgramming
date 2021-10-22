import java.util.*;
import java.lang.*;

public class PrimeDays {
	
	/**
	 * Check if a given number, num, is a prime number
	 * @param num the number to check for prime
	 * @return true if number is prime
	 */
	private static boolean is_prime(int num) {
		if (num == 1) return false;
		if (num == 2) return true;
		int upper_limit = (int) Math.sqrt(num);
		for (int x = 2; x <= upper_limit; x++) {
			if (num % x == 0) return false;
		}
		return true;
	}
	
	/**
	 * Given a day of the year and an ArrayList of lengths of months,
	 * give the day of the month and the month number
	 * @param day the day of the year
	 * @param months the ArrayList of the lengths of all the months
	 * @return the day of the month and the month number
	 */
	private static int[] return_date(int day, ArrayList<Integer> months) {
		int days_overall = 0;
		int days_before = 0;
		int month_index = 1;
		for (int length_of_month : months) {
			days_overall += length_of_month;
			if ((double) days_overall / day >= 1) {
				int[] date = {(day - days_before), month_index};
				return date;
			}
			month_index++;
			days_before = days_overall;
		}
		int[] a = {0, 0};
		return a;
	}
	
	/**
	 * Given a month, spit out the day of the year that corresponds to the end of that month
	 * @param month month of year
	 * @param months the ArrayList of the lengths of all the months
	 * @return the day of the year
	 */
	private static int endOfMonth(int month, ArrayList<Integer> months) {
		int total = 0;
		int mCount = 0;
		while (mCount < month) {
			total += months.get(mCount);
			mCount++;
		}
		return total;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> months = new ArrayList<Integer>();
		for (String s : args) {
			int length_of_month = Integer.parseInt(s);
			months.add(length_of_month);
		}
		int sum = 0;
		for (int m : months) {
			sum += m;
		}
		
		// Loop through days and months
		int day = 2;
		while (day <= sum) {
			int[] date = return_date(day, months);
			int month = date[1];
			int day_of_month = date[0];
			if (is_prime(month)) {
				if (is_prime(day)) {
					if(is_prime(day_of_month)) {
						System.out.println(day + ": " + month + " " + day_of_month);
					}
				}
			} else {
				day = endOfMonth(month, months);
			}
			day++;
		}
	}
	
}