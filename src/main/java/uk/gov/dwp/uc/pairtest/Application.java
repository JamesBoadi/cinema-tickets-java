package uk.gov.dwp.uc.pairtest;

import java.util.Scanner;

import uk.gov.dwp.uc.pairtest.entities.Account;
import uk.gov.dwp.uc.pairtest.entities.User;
import uk.gov.dwp.uc.pairtest.helpers.Helpers;

public class Application {

	static Scanner sc = new Scanner(System.in);
	static boolean plug = false;

	public static void main(String[] args) {
		init();
	}

	static void init() {
		Helpers helpers = new Helpers();

		System.out.println("Welcome, type 0 to create a new account \n");

		String next = sc.nextLine();
		int entry = helpers.checkIfInteger(next.trim());

		switch (entry) {
			case 0: {
				plug = true;
				break;
			}
		}

		while (!plug) {
			System.out.println(
					"Invalid entry, type 0 to create a new account or type 1 to get an id for an existing account ");

			if (entry == 0 || entry == 1)
				break;

			next = sc.nextLine();
			entry = helpers.checkIfInteger(next.trim());
		}

		processEntry(entry);
	}

	static void processEntry(int entry) {
		if (entry == 0) {
			Account account = Account.createAccount();
			final long accountId = account.getId();
			User user = new User(accountId);
			System.out.println("Your account id is " + accountId);
			user.purchaseTicket();
		}

		process();
	}

	static void process() {
		System.out.println("Buy more tickets? Type yes to buy more tickets or any key to exit \n");
		String next = sc.nextLine().toLowerCase();

		if (next.equals("yes"))
			processEntry(0);
		else {
			System.out.println("Have a good day");
			System.exit(1);
		}
	}

}
