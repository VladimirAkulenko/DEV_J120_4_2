/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.ui;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import dev_j120_4_2.lists.CompanyList;
import dev_j120_4_2.models.CompanyInfo;
import dev_j120_4_2.models.PhoneNumber;

/**
 *
 * @author vlaaku
 */
public class CompanyListTableModel implements TableModel{
    private static final String[] COLUMN_HEADERS = new String[]{
            "Phone number",
            "Company name",
            "Company address",
            "Director name",
            "Contact name",
            "Registration date"
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
            case 4:
            case 5:
                return String.class;
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public int getRowCount() {
        return CompanyList.getInstance().getCompanyClientCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CompanyInfo ci = CompanyList.getInstance().getCompanyClientInfo(rowIndex);
        switch(columnIndex) {
            case 0: return ci.getPhoneNumber();
            case 1: return ci.getName();
            case 2: return ci.getAddress();
            case 3: return ci.getNameDirector();
            case 4: return ci.getContactName();
            case 5: return ci.getRegDate();
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


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

    public CompanyInfo getCompanyClient(int rowNdx) {
        return CompanyList.getInstance().getCompanyClientInfo(rowNdx);
    }


    public void addCompany(PhoneNumber number, String name, String address, String nameDirector, String contactName) {
        CompanyList.getInstance().addCompany(number, name, address, nameDirector, contactName);
        int rowNdx = CompanyList.getInstance().getCompanyClientCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }

    public void addCompany(PhoneNumber number, String name, String address, String nameDirector, String contactName, String regDate) {
        CompanyList.getInstance().addCompany(number, name, address, nameDirector, contactName, regDate);
        int rowNdx = CompanyList.getInstance().getCompanyClientCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }


    public void companyChanged(int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }


    public void dropCompany(int index) {
        CompanyList.getInstance().removeCompany(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }


    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }
}