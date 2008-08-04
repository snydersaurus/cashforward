/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cashforward.ui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.cashforward.ui.internal.options.UIOptions;

final class SystemSettingsPanel extends javax.swing.JPanel {

    private final EntryOptionsPanelController controller;

    SystemSettingsPanel(final EntryOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        cbDebugOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changed();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbDebugOn = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(cbDebugOn, org.openide.util.NbBundle.getMessage(SystemSettingsPanel.class, "SystemSettingsPanel.cbDebugOn.text")); // NOI18N
        cbDebugOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDebugOnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(cbDebugOn)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(cbDebugOn)
                .addContainerGap(265, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbDebugOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDebugOnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cbDebugOnActionPerformed

    void load() {
        cbDebugOn.setSelected(UIOptions.isDebuggingOn());
    }

    void store() {
        UIOptions.setDebuggingOn(cbDebugOn.isSelected());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbDebugOn;
    // End of variables declaration//GEN-END:variables
}
