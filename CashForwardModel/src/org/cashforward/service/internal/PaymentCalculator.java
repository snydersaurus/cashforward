package org.cashforward.service.internal;

import org.cashforward.util.DateUtilities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.cashforward.model.Payment;
import org.cashforward.model.Payment.Occurence;

/**
 * 
 * Utility class for calculating Payments for scheduled Payments.
 *
 * @author Bill 
 */
public class PaymentCalculator {
    
    public List<Payment> calculatePayments(Payment payment, Date start, Date end)
            throws Exception {
        List<Payment> payments = new ArrayList();
        ServicesLogger.LOG.finest(payment.toString());
        Date paymentStart = payment.getStartDate();
        Date paymentEnd = payment.getEndDate();
        Date nextPaymentDate = null;

        Occurence occurence = Occurence.valueOf(payment.getOccurence());

        //if ("weekly".equals(occurence)) {//enum - every 7 days
            //when the range start is after the payment start
            //figure out what the earliest payment date is in the range

            //get day difference between range start and payment start
            int offsetStart = DateUtilities.daysBetween(start,paymentStart);
            ServicesLogger.LOG.finest("offsetStart:"+offsetStart);
            //if positive, range start is before payment
            //so, nextPaymentDate = start + (offset / 7 days)
            if (offsetStart > -1 )
                nextPaymentDate = paymentStart;
            else if (occurence != Occurence.ONCE)
                nextPaymentDate = 
                        DateUtilities.getDateAfterPeriod(start, 
                            occurence.period(), offsetStart % occurence.unit());

            
            //after we have the nextPaymentDate, 
            //keep adding the time period while still before the range and payment end
            while (nextPaymentDate != null && nextPaymentDate.before(end) && 
                    (paymentEnd == null || nextPaymentDate.before(paymentEnd) ) ) {
                
                payments.add(createPayment(payment, nextPaymentDate));
                
                if (occurence != Occurence.ONCE)
                    nextPaymentDate = 
                        DateUtilities.getDateAfterPeriod(nextPaymentDate,
                            occurence.period(), occurence.unit());
                else
                    nextPaymentDate = null;
                
            }


        return payments;

    }
    
    public Date getNextPaymentDate(Payment payment, Date start){
        Date paymentStart = payment.getStartDate();
        Date nextPaymentDate = null;

        Occurence occurence = Occurence.valueOf(payment.getOccurence());
        int offsetStart = DateUtilities.daysBetween(start,paymentStart);
        ServicesLogger.LOG.finest("offsetStart:"+offsetStart);
        if (offsetStart > -1 )
            nextPaymentDate = paymentStart;
        else if (occurence != Occurence.ONCE)
            nextPaymentDate = 
                    DateUtilities.getDateAfterPeriod(start, 
                        occurence.period(), offsetStart % occurence.unit());
                            
        if (occurence != Occurence.ONCE)
                    nextPaymentDate = 
                        DateUtilities.getDateAfterPeriod(nextPaymentDate,
                            occurence.period(), occurence.unit());
        
        return nextPaymentDate;
    }

    private Payment createPayment(Payment base, Date nextDate) {
        Payment newPayment = null;
        newPayment = new Payment(base.getAmount(), base.getPayee(), nextDate);
        newPayment.addLabels(base.getLabels());
        //newPayment.setOccurence(Occurence.ONCE.getLabel());
        newPayment.setEndDate(nextDate);
        return newPayment;

    }

}

