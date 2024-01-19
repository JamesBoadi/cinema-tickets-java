package uk.gov.dwp.uc.pairtest.entities;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.helpers.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* User class for performing transactions */
public class User extends Account {

	private int noOfTickets = 0;
	private Type type;
	
	Helpers helpers = new Helpers();
	Person person = new Person();
	HashMap<TicketTypeRequest.Type, Integer> tickets = new HashMap<>();

	public User(Long id) {
		super(id);
	}
	
	public Account getAccountById(Long id) {
		return super.getAccountById(id) ;
	}

	public long getId() {
		return super.getId();
	}

	public void purchaseTicket() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of tickets you wish to purchase");

		String next = sc.nextLine();
		int ticketsToPurchase = helpers.checkIfInteger(next);
	
		while (ticketsToPurchase < 0) {
			System.out.println("Invalid entry, please try again");
			next = sc.nextLine();
			ticketsToPurchase = helpers.checkIfInteger(next);
		}

		System.out.println("Enter the name and age of each person you wish to buy a ticket");
		System.out.println("Age 0 - 5: Infant Ticket | Age: 5 - 17: Child Ticket | Age: 18+ Adult Ticket \n");
		
		String name = "";
		int age = 0;
		int counter = 0;

		while (counter < ticketsToPurchase) {
			System.out.println("Name of person " + counter);
			next = sc.nextLine();
			name = next;

			System.out.println("Age of " + name);
			next = sc.nextLine();
			age = helpers.checkIfInteger(next);
			
			while(age < 0)
			{
				System.out.println("Please enter a number greater than 0");
				next = sc.nextLine();
				age = helpers.checkIfInteger(next);
			}

			person.registerPerson(name, age);
			
			counter++;
		}
		
		List<Person> persons = person.getPersons();

		for (int noOfPersons = 0; noOfPersons < persons.size(); noOfPersons++) {
			Person person = persons.get(noOfPersons);
			age = person.getAge();
			if (age <= 5) {
				tickets.put(TicketTypeRequest.Type.INFANT, tickets.getOrDefault(TicketTypeRequest.Type.INFANT, 0) + 1);
			} else if (age < 18) {
				tickets.put(TicketTypeRequest.Type.CHILD, tickets.getOrDefault(TicketTypeRequest.Type.CHILD, 0) + 1);
			} else {
				tickets.put(TicketTypeRequest.Type.ADULT, tickets.getOrDefault(TicketTypeRequest.Type.ADULT, 0) + 1);
			}
		}
		
		System.out.println("For confirmation, your account id is " + this.getId() + "\n");
		TicketTypeRequest[] ticketTypeRequests = requestPayment(this.getId(), tickets);
		
		System.out.println("Your tickets are: \n");
		
		for (Map.Entry<TicketTypeRequest.Type, Integer> map : tickets.entrySet()) {
			System.out.print(map.getValue() + "X " + map.getKey().name() + " Tickets \n");
		}
		
		System.out.println("\n");
		System.out.println("Proceed with purchase? Type yes to proceed, type any key to start again");
		
		next = sc.nextLine().toLowerCase();
		
		if(next.equals("yes"))
			ticketPaymentService.purchaseTickets(this.getId(), ticketTypeRequests);
		else
			purchaseTicket();
	}
	
	
	
	
	


}
