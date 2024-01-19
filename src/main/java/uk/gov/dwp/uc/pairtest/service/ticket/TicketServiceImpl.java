package uk.gov.dwp.uc.pairtest.service.ticket;

import java.util.HashSet;
import java.util.Set;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.entities.Account;
import uk.gov.dwp.uc.pairtest.entities.User;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */
	
	TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
	SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();
	
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
    	User user = new User(accountId);
  
    	if(!checkTicketRequestIsValid(ticketTypeRequests))
    		throw new InvalidPurchaseException("A child or infant ticket cannot be purchased without an adult ticket");
    	
    	if(!checkNoOfTicketRequests(ticketTypeRequests))
    		throw new InvalidPurchaseException("Only a maximum of 20 tickets can be purchased at a time");
    	
    	final int cost = user.calculateCost(ticketTypeRequests);
    	final int totalSeatsToAllocate = user.calculateNoOfSeats(ticketTypeRequests);
    	
    	System.out.println("Cost: £" + cost + " Seats to Allocate: " + totalSeatsToAllocate);
    	
     	paymentRequest(accountId, cost);
    	// Payments that are made always go through, therefore seats can now be reserved
    	reserveSeats(accountId, totalSeatsToAllocate);
    	
    	System.out.println("Thank you, your tickets have been purchased and seats allocated \n");
    	// Update Account
    	// .......
    }
    
    private void reserveSeats(long accountId, int totalSeatsToAllocate)
    {
    	seatReservationService.reserveSeat(accountId, totalSeatsToAllocate);	
    }
    
    private void paymentRequest(long accountId, int cost)
    {
    	ticketPaymentService.makePayment(accountId, cost);	
    }
    
    private boolean checkTicketRequestIsValid(TicketTypeRequest... ticketTypeRequests)
    {
    	Set<TicketTypeRequest.Type> set = new HashSet<>();
    	
    	for (int count = 0; count < ticketTypeRequests.length; count++) {
			TicketTypeRequest ticketTypeRequest = ticketTypeRequests[count];
			set.add(ticketTypeRequest.getTicketType());
    	}
    	
    	if(!set.contains(TicketTypeRequest.Type.ADULT) && (set.contains(TicketTypeRequest.Type.INFANT)
    			|| set.contains(TicketTypeRequest.Type.CHILD)))
    		return false;
    	
    	return true;
    }
    
    private boolean checkNoOfTicketRequests(TicketTypeRequest... ticketTypeRequests)
    {
    	int noOfTickets = 0;
    	
    	for (int count = 0; count < ticketTypeRequests.length; count++) {
			TicketTypeRequest ticketTypeRequest = ticketTypeRequests[count];
			
			if(ticketTypeRequest.getNoOfTickets() > 20)
				return false;
			
			noOfTickets += ticketTypeRequest.getNoOfTickets();
    	}
    	
    	if(noOfTickets > 20)
    		return false;
    	
    	return true;
    }
    
}
