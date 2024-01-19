package uk.gov.dwp.uc.pairtest.entities;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.helpers.Helpers;
import uk.gov.dwp.uc.pairtest.service.ticket.TicketServiceImpl;

import java.util.HashMap;
import java.util.Map;

/* Holds Account information for user */
public class Account {

	TicketServiceImpl ticketPaymentService = new TicketServiceImpl();
	HashMap<Long, Account> accounts = new HashMap<>();
	static Helpers helpers = new Helpers();

	private long id;
	private int costOfLastTransaction = 0;
	private long totalTransactions = 0;

	public Account(Long id) {
		this.id = id;
	}

	public static Account createAccount() {
		long id = helpers.generateId();
		return new Account(id);
	}

	public Account getAccountById(long id) {
		return accounts.get(id);
	}

	public long getId() {
		return this.id;
	}

	public long getTotalTransactions() {
		return this.totalTransactions;
	}

	public TicketTypeRequest[] requestTicketType(HashMap<TicketTypeRequest.Type, Integer> tickets) {
		int count = 0;
		TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[tickets.size()];

		for (Map.Entry<TicketTypeRequest.Type, Integer> map : tickets.entrySet()) {
			ticketTypeRequest[count] = new TicketTypeRequest(map.getKey(), map.getValue());
			count++;
		}

		return ticketTypeRequest;
	}

	public TicketTypeRequest[] requestPayment(Long id, HashMap<TicketTypeRequest.Type, Integer> tickets) {
		TicketTypeRequest[] ticketTypeRequests = requestTicketType(tickets);
		return ticketTypeRequests;
	}

	public int calculateNoOfSeats(TicketTypeRequest[] ticketTypeRequests) {
		int seats = 0;

		for (int count = 0; count < ticketTypeRequests.length; count++) {
			TicketTypeRequest ticketTypeRequest = ticketTypeRequests[count];

			if (ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.CHILD
					|| ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.ADULT)
				seats += ticketTypeRequest.getNoOfTickets();
		}

		return seats;
	}

	public int calculateCost(TicketTypeRequest[] ticketTypeRequests) {
		int cost = 0;

		for (int count = 0; count < ticketTypeRequests.length; count++) {
			TicketTypeRequest ticketTypeRequest = ticketTypeRequests[count];
			int noOfTickets = ticketTypeRequest.getNoOfTickets();

			if (ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.CHILD)
				cost += (10 * noOfTickets);
			else if (ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.ADULT)
				cost += (20 * noOfTickets);

			this.costOfLastTransaction = cost;
		}

		return cost;
	}

}
