/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cashforward.ui.action;

import ca.odell.glazedlists.EventList;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.cashforward.model.Payment;
import org.cashforward.ui.UIContext;
import org.cashforward.ui.adapter.PaymentServiceAdapter;
import org.cashforward.ui.task.PaymentFilter;

/**
 * Responsible for making sure the scheduled payments are loaded
 * into the application.
 *
 * @author Bill
 */
public class LoadScheduledPaymentsAction extends AbstractAction {
    
    PaymentServiceAdapter serviceAdapter;
    
    public LoadScheduledPaymentsAction(){
        super("Scheduled");
        putValue(Action.SHORT_DESCRIPTION, "Scheduled");
    }
    
    //TODO threading
    public void actionPerformed(ActionEvent e) {
        if (serviceAdapter == null)
            serviceAdapter = new PaymentServiceAdapter();
        
        EventList<Payment> paymentList = 
                UIContext.getDefault().getPayments();
        
        List allPayments = serviceAdapter.getAllPayments();
        if (allPayments == null)
            return;
        
        paymentList.getReadWriteLock().writeLock().lock();
        paymentList.clear();
        paymentList.addAll(allPayments);
        paymentList.getReadWriteLock().writeLock().unlock();
        
        PaymentFilter filter = UIContext.getDefault().getPaymentFilter();
        filter.setPaymentType(PaymentFilter.TYPE_SCHEDULED);
        UIContext.getDefault().setPaymentFilter(filter);
    }
    
}
