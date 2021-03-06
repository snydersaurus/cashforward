package org.cashforward.ui.payment;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.cashforward.model.Payee;
import org.cashforward.model.Payment;
import org.cashforward.ui.UIContext;
import org.cashforward.ui.internal.filter.MatcherFactory;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;

/**
 * Shows the current filtered/selected group of Payments.
 *
 * @author  Bill
 */
public class PaymentListPanel extends TopComponent {

    private EventList<Payment> payments;
    private SortedList<Payment> sortedList;
    private FilterList<Payment> filteredList;
    private EventTableModel tableModel;
    private EventSelectionModel selectionModel;
    
    private Lookup.Result paymentNotifier =
            UIContext.getDefault().lookupResult(Payment.class);
    
    private MatcherFactory matcherFactory =
            MatcherFactory.getInstance();

    private NumberFormat f = NumberFormat.getCurrencyInstance();
    
    protected static final Color BACKGROUND1 = new Color(253, 253, 244);
    protected static final Color BACKGROUND2 = new Color(230, 230, 255);
    protected static final Color BACKGROUND3 = new Color(210, 255, 210);
    protected static final Color FOREGROUND1 = new Color(0, 0, 10);
    protected static final Color BACKGROUND4 = new Color(0, 128, 0);
    protected static final Color FOREGROUND4 = new Color(255, 255, 255);

    public PaymentListPanel() {
        initComponents();

        paymentNotifier.addLookupListener(new LookupListener() {

            public void resultChanged(LookupEvent event) {
                Lookup.Result r = (Lookup.Result) event.getSource();
                Collection c = r.allInstances();
                if (!c.isEmpty()) {
                    Payment payment = (Payment) c.iterator().next();
                    int index = filteredList.indexOf(payment);
                    if (!selectionModel.getValueIsAdjusting()) {
                        if (index == selectionModel.getAnchorSelectionIndex()) {
                            tableModel.fireTableRowsUpdated(index, index);
                        } else {
                            paymentTable.scrollRectToVisible(
                                    paymentTable.getCellRect(index, 0, true));
                            selectionModel.setSelectionInterval(index, index);
                        }
                    }
                }
            }
        });

    }

    public void setPayments(final EventList payments) {
        this.payments = payments;

        sortedList =
                new SortedList(payments, new PaymentComparator());
        
        filteredList = new FilterList(sortedList,
                matcherFactory.createLabelAndScenarioMatcher());

        selectionModel = new EventSelectionModel(filteredList);
        selectionModel.setSelectionMode(EventSelectionModel.SINGLE_SELECTION);
        
        tableModel = new EventTableModel(filteredList, new PaymentTableFormat());
        paymentTable.setModel(tableModel);
        paymentTable.setSelectionModel(selectionModel);

        PaymentCellRenderer pcr = new PaymentCellRenderer();
        paymentTable.getColumnModel().getColumn(2).setCellRenderer(pcr);
        paymentTable.getColumnModel().getColumn(3).setCellRenderer(pcr);

        //not supporting table sorting at the momemt
        TableComparatorChooser tableSorter =
                new TableComparatorChooser(paymentTable, sortedList, false);
       

        paymentTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) {
                            return;
                        } else if (paymentTable.getSelectedRow() < 0 ||
                                filteredList.size() <
                                paymentTable.getSelectedRow()) {
                            UIContext.getDefault().clearPayment();
                            return;
                        }
                        Payment payment = (Payment) filteredList.get(paymentTable.getSelectedRow());
                        //content.set(Collections.singleton (payment), null);
                        UIContext.getDefault().setPayment(payment);
                    }
                });

    }

    JTable getTableComponent() {
        return this.paymentTable;
    }

    private float getBalance(int toIndex) {
        float balance = 0f;

        for (int i = 0; i <= toIndex; i++) {
            balance += filteredList.get(i).getAmount();
        }

        return balance;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rowStripeCellStyleProvider1 = new com.jidesoft.grid.RowStripeCellStyleProvider();
        jScrollPane1 = new javax.swing.JScrollPane();
        paymentTable = new com.jidesoft.grid.CellStyleTable();

        rowStripeCellStyleProvider1.setAlternativeBackgroundColors(new Color[]{BACKGROUND1,BACKGROUND2});

        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        paymentTable.setCellStyleProvider(rowStripeCellStyleProvider1);
        jScrollPane1.setViewportView(paymentTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private com.jidesoft.grid.CellStyleTable paymentTable;
    private com.jidesoft.grid.RowStripeCellStyleProvider rowStripeCellStyleProvider1;
    // End of variables declaration//GEN-END:variables

    class PaymentComparator implements Comparator {

        public int compare(Object a, Object b) {
            Payment itemA = (Payment) a;
            Payment itemB = (Payment) b;

            //initially sort by date, earliest is more important
            Date itemADate = itemA.getStartDate();
            Date itemBDate = itemB.getStartDate();
            if (itemADate == null) {
                return itemBDate == null ? 0 : 1;
            } else if (itemADate != null) {
                return itemBDate == null ? 1 : 0;
            }
            return itemADate.compareTo(itemBDate);
        }
    }

    class DateComparator implements Comparator {

        public int compare(Object a, Object b) {
            Date itemADate = (Date) a;
            Date itemBDate = (Date) b;

            //initially sort by date, earliest is more important
            if (itemADate == null) {
                return itemBDate == null ? 0 : 1;
            } else if (itemADate != null) {
                return itemBDate == null ? 1 : 0;
            }
            return itemADate.compareTo(itemBDate);
        }
    }

    class AlwaysTheSameComparator implements Comparator {

        public int compare(Object a, Object b) {
            return 0;
        }
    }

    class PaymentTableFormat implements AdvancedTableFormat, WritableTableFormat {

        final String[] colNames = new String[]{
            "Date", "Payee", "Amount", "Balance"
        };

        public int getColumnCount() {
            return colNames.length;
        }

        public String getColumnName(int column) {
            return colNames[column];
        }

        public Object getColumnValue(Object baseObject, int column) {
            Payment payment = (Payment) baseObject;
            if (payment == null) {
                return null;
            }
            Payee payee = payment.getPayee();

            if (payee == null) {
                return null;
            }
            Date paymentDate = payment.getStartDate();
            float amount = payment.getAmount();

            if (column == 0) {
                return paymentDate;
            } else if (column == 1) {
                return payee.getName();
            } else if (column == 2) {
                return Float.valueOf(amount);
            } else if (column == 3) {
                try { //there is a bug here
                    int toIndex = filteredList.indexOf(baseObject);
                    return getBalance(toIndex);
                } catch (AssertionError e){return 0;}
            } else {
                return "";
            }
        }

        public Class getColumnClass(int i) {
            if (i == 0) {
                return Date.class;
            } else if (i == 2 || i == 3) {
                return Float.class;
            } else {
                return String.class;
            }
        }

        public Comparator getColumnComparator(int column) {
            if (column == 0) {
                return GlazedLists.comparableComparator();
            } else if (column == 1) {
                return GlazedLists.caseInsensitiveComparator();
            } else if (column == 2) {
                return GlazedLists.comparableComparator();
            } else {
                return null;
            }
        }

        public boolean isEditable(Object o, int i) {
            return false;
        }

        public Object setColumnValue(Object baseObject, Object editedObject, int i) {
            return baseObject;
        }
    }

    class PaymentCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component r = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            JLabel cell = (JLabel) r;
            cell.setHorizontalAlignment(JLabel.RIGHT);

            if (column == 2 || column == 3) {
                cell.setText(f.format(value));
                if ((Float) value < 0) {
                    cell.setForeground(Color.RED);
                } else {
                    cell.setForeground(Color.BLACK);
                }
            }

            return cell;
        }
    }

}
