
// import static org.junit.Assert.assertEquals;
import org.junit.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class UserTest {

	// Out of time, but some test cases...
	@Test(expected = InvalidPurchaseException.class)
	public void testExceptionIsThrown() {
		// TicketTypeRequest ticketTypeRequest = ticketTypeRequests[21];
		// ticketPaymentService.purchaseTickets(this.getId(), ticketTypeRequests);
	}

}
