package org.cashforward.ui.payment;

import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import org.cashforward.model.Payment;
import org.cashforward.model.Payment.Occurence;
import org.cashforward.ui.adapter.PaymentServiceAdapter;
import org.cashforward.util.DateUtilities;

/**
 * Interface for setting scheduling information for a Payment.
 *
 * @author  Bill
 */
public class PaymentScheduleForm extends javax.swing.JPanel {

    private Payment payment;
    private Payment.Occurence occurence;
    private PaymentServiceAdapter paymentService;

    public PaymentScheduleForm() {
        initComponents();
        occurenceCombo.setModel(new OccurenceComboModel());
        cboEnds.setEnabled(false);
        stopsOnRadio.setEnabled(false);
        untilDateCombo.setEnabled(false);
        stopsAfterRadio.setEnabled(false);
        valueSpinner.setEnabled(false);
    }

    public void setPayment(Payment payment) {
        if (paymentService == null) {
            paymentService = new PaymentServiceAdapter();
        }

        this.payment = payment;
        if (payment.getOccurence() != null) {
            occurence = Payment.Occurence.valueOf(payment.getOccurence());
            occurenceCombo.setSelectedItem(occurence.getLabel());
        }

        if (Payment.Occurence.NONE == occurence) {
            setScheduleInterfaceEnabled(false);
        } else if (payment.getEndDate() != null) {
            untilDateCombo.setDate(payment.getEndDate());
            stopsButtonGroup.setSelected(stopsOnRadio.getModel(), true);
            //set value spinner appropriate
            List<Payment> paymentsRemaining =
                    paymentService.getScheduledPayments(payment,
                    new Date(), payment.getEndDate());
            if (paymentsRemaining != null) {
                valueSpinner.setValue(paymentsRemaining.size());
            }
            //valueSpinner.setEnabled(false);
            cboEnds.setSelected(true);
            untilDateCombo.setEnabled(true);
            setScheduleInterfaceEnabled(true);
        } else {
            untilDateCombo.setSelectedItem(null);
            untilDateCombo.setEnabled(false);
            valueSpinner.setEnabled(false);
            valueSpinner.setValue(0);
        }
    }

    private void setScheduleInterfaceEnabled(boolean state) {
        cboEnds.setSelected(state);
        stopsAfterRadio.setEnabled(state);
        stopsOnRadio.setEnabled(state);
    }

    /**
     * This is really the *commit* method for this form.
     *
     * @return the selected Occurence
     */
    public void updatePaymentOccurence() {
        Payment.Occurence newOccurence =
                getOccurence();

        if (!cboEnds.isSelected()) {
            payment.setEndDate(null);
            return;
        }

        if (stopsAfterRadio.isSelected()) {
            int period = newOccurence.period();
            int timesRemaining =
                    getOccurencesRemaining();
            if (timesRemaining > 0) {
                Date endDate = DateUtilities.getDateAfterPeriod(
                        payment.getStartDate(),
                        period, timesRemaining);
                payment.setEndDate(endDate);
            }
        } else if (stopsOnRadio.isSelected()) {
            payment.setEndDate(this.untilDateCombo.getDate());
        }
    }

    public Payment.Occurence getOccurence() {
        Payment.Occurence newOccurence =
                Occurence.values()[occurenceCombo.getSelectedIndex()];

        return newOccurence;
    }

    public Date getEndDate() {
        return this.untilDateCombo.getDate();
    }
    //keep this in sync with end date

    public int getOccurencesRemaining() {
        return Integer.parseInt(valueSpinner.getValue().toString());
    }

    private class OccurenceComboModel extends DefaultComboBoxModel {

        public OccurenceComboModel() {
            super(Payment.Occurence.values());
        }

        @Override
        public Object getElementAt(int index) {
            Payment.Occurence occurence =
                    (Payment.Occurence) super.getElementAt(index);
            return occurence.getLabel();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stopsButtonGroup = new javax.swing.ButtonGroup();
        untilDateCombo = new com.jidesoft.combobox.DateComboBox();
        occurenceCombo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        valueSpinner = new javax.swing.JSpinner();
        cboEnds = new javax.swing.JCheckBox();
        stopsAfterRadio = new javax.swing.JRadioButton();
        stopsOnRadio = new javax.swing.JRadioButton();

        untilDateCombo.setPreferredSize(new java.awt.Dimension(92, 20));

        occurenceCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                occurenceComboActionPerformed(evt);
            }
        });

        jLabel4.setText(org.openide.util.NbBundle.getMessage(PaymentScheduleForm.class, "PaymentScheduleForm.jLabel4.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(PaymentScheduleForm.class, "PaymentScheduleForm.jLabel1.text")); // NOI18N

        valueSpinner.setPreferredSize(new java.awt.Dimension(30, 20));

        cboEnds.setText(org.openide.util.NbBundle.getMessage(PaymentScheduleForm.class, "PaymentScheduleForm.cboEnds.text")); // NOI18N
        cboEnds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEndsActionPerformed(evt);
            }
        });

        stopsButtonGroup.add(stopsAfterRadio);
        stopsAfterRadio.setText(org.openide.util.NbBundle.getMessage(PaymentScheduleForm.class, "PaymentScheduleForm.stopsAfterRadio.text")); // NOI18N
        stopsAfterRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopsAfterRadioActionPerformed(evt);
            }
        });

        stopsButtonGroup.add(stopsOnRadio);
        stopsOnRadio.setText(org.openide.util.NbBundle.getMessage(PaymentScheduleForm.class, "PaymentScheduleForm.stopsOnRadio.text")); // NOI18N
        stopsOnRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopsOnRadioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cboEnds)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(21, 21, 21)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(stopsAfterRadio)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(valueSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                        .add(stopsOnRadio)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(untilDateCombo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                        .add(6, 6, 6)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .add(4, 4, 4))
                    .add(layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(occurenceCombo, 0, 153, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(occurenceCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cboEnds)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(stopsOnRadio)
                    .add(untilDateCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(stopsAfterRadio)
                    .add(valueSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1)))
        );
    }// </editor-fold>//GEN-END:initComponents

private void occurenceComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_occurenceComboActionPerformed
    cboEnds.setEnabled(occurenceCombo.getSelectedIndex() > 0);
}//GEN-LAST:event_occurenceComboActionPerformed

private void cboEndsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEndsActionPerformed
    setScheduleInterfaceEnabled(cboEnds.isSelected());
}//GEN-LAST:event_cboEndsActionPerformed

private void stopsOnRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopsOnRadioActionPerformed
// TODO add your handling code here:
    untilDateCombo.setEnabled(stopsOnRadio.isSelected());
}//GEN-LAST:event_stopsOnRadioActionPerformed

private void stopsAfterRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopsAfterRadioActionPerformed
// TODO add your handling code here:
    valueSpinner.setEnabled(stopsAfterRadio.isSelected());
}//GEN-LAST:event_stopsAfterRadioActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboEnds;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox occurenceCombo;
    private javax.swing.JRadioButton stopsAfterRadio;
    private javax.swing.ButtonGroup stopsButtonGroup;
    private javax.swing.JRadioButton stopsOnRadio;
    private com.jidesoft.combobox.DateComboBox untilDateCombo;
    private javax.swing.JSpinner valueSpinner;
    // End of variables declaration//GEN-END:variables
}