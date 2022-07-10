/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.ui;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.Set;

import dev_j120_4_2.lists.ClientList;
import dev_j120_4_2.models.ClientInfo;
import dev_j120_4_2.models.PhoneNumber;

/**
 * Clients list table model for the table of application main window.
 */
public class ClientListTableModel implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{
            "Phone number",
            "Person name",
            "Person address",
            "Registration date",
            "Person age"
    };

    private final Set<TableModelListener> modelListeners = new HashSet<>();

    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return PhoneNumber.class;
            case 1:
            case 2:
            case 3:   
                return String.class;
            case 4:
                return Integer.class;
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public int getRowCount() {
        return ClientList.getInstance().getClientsCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientInfo ci = ClientList.getInstance().getClientInfo(rowIndex);
        switch(columnIndex) {
            case 0: return ci.getPhoneNumber();
            case 1: return ci.getName();
            case 2: return ci.getAddress();
            case 3: return ci.getRegDate();
            case 4: return ci.getPersonAge();
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }

    /**
     * Returns {@code false}, since in-cell editing is prohibited.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * Does nothing, since in-cell editing is prohibited.
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        /* Nothing to do, since isCellEditable is always false. */
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
    }

    public ClientInfo getClient(int rowNdx) {
        return ClientList.getInstance().getClientInfo(rowNdx);
    }

    /**
     * Registers new client. Adds new client by calling of {@link ClientList#addClient}
     * and notifies model listeners about changes.
     */
    public void addClient(PhoneNumber number, String name, String address, String dateBirth) {
        ClientList.getInstance().addClient(number, name, address, dateBirth);
        int rowNdx = ClientList.getInstance().getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }

    public void addClient(PhoneNumber number, String name, String address, String dateBirth, String regDate) {
        ClientList.getInstance().addClient(number, name, address, dateBirth, regDate);
        int rowNdx = ClientList.getInstance().getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }
    /**
     * Just notifies model listeners, that data of a client with specified index has been changed.
     *
     * @param index index of a client in the client list, which data has been changed
     */
    public void clientChanged(int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }

    /**
     * Removes client with the specified index. Notifies model listeners about removal.
     *
     * @param index index of a client record to remove
     */
    public void dropClient(int index) {
        ClientList.getInstance().remove(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }

    /**
     * Creates {@code TableModelEvent} of specified type (see {@link TableModelEvent} constants),
     * and calls listeners to notify them about the change.
     *
     * @param rowNdx index of table row, which is the reason of the event
     * @param evtType event type; one of {@code TableModelEvent} constants
     * 		({@link TableModelEvent#UPDATE} for example)
     */
    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }
}