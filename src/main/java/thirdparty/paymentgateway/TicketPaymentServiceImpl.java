package thirdparty.paymentgateway;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class TicketPaymentServiceImpl implements TicketPaymentService {

    @Override
    public void makePayment(long accountId, int totalAmountToPay) {
        // Real implementation omitted, assume working code will take the payment using a card pre linked to the account.
    }
    

	

}
