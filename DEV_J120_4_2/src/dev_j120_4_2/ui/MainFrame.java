/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dev_j120_4_2.lists.CompanyList;
import dev_j120_4_2.lists.ClientList;
import dev_j120_4_2.lists.File;
import dev_j120_4_2.models.ClientInfo;
import dev_j120_4_2.models.CompanyInfo;
import dev_j120_4_2.models.PhoneNumber;
import dev_j120_4_2.Utils;

/**
 * Application main window.
 */
public class MainFrame extends JFrame {

    private final ClientListTableModel clientTableModel = new ClientListTableModel();
    private final JTable personsTable = new JTable();
    private final ClientDialog clientDialog = new ClientDialog(this);

    private final CompanyListTableModel companyTableModel = new CompanyListTableModel();
    private final JTable companyTable = new JTable();
    private final CompanyDioalog companyDioalog = new CompanyDioalog(this);

    private final File file = new File();

    public MainFrame() {
        super("AvalonTelecom Ltd. clients list");
        initTabbedPanel();
        startApp();
        closeApp();
        setBounds(50, 100, 880, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void tuningTables(){
        personsTable.setModel(clientTableModel);
        companyTable.setModel(companyTableModel);
        personsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        companyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personsTable.setRowHeight(27);
        companyTable.setRowHeight(27);
        Utils.alignCenter(personsTable, 0);
        Utils.alignCenter(personsTable, 3);
        Utils.alignCenter(personsTable, 4);
        Utils.alignCenter(companyTable, 0);
        Utils.alignCenter(companyTable, 5);
        Utils.setJTableColumnsWidth(personsTable, 900, 25, 40, 75, 25, 20);
        Utils.setJTableColumnsWidth(companyTable, 900, 19, 25, 30, 30, 30, 21);
    }

    private void initTabbedPanel(){
        tuningTables();

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        JPanel[] panels = new JPanel[2];
        for(int i =0; i<panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new BorderLayout());
            tabbedPane.addTab((i%2!=1)? "Persons": "Сompanies", panels[i]);
            tabbedPane.setMnemonicAt(i, (i%2!=1)? KeyEvent.VK_P : KeyEvent.VK_C);
            add(tabbedPane);
        }
        initPersonMenu(panels[0]);
        initCompanyMenu(panels[1]);
        panels[0].add(new JScrollPane(personsTable), BorderLayout.CENTER);
        panels[1].add(new JScrollPane(companyTable), BorderLayout.CENTER);
    }

    private void initPersonMenu(Container container) {

        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations for persons");
        operations.setBorder(BorderFactory.createEtchedBorder());
        operations.setMnemonic('O');
        menuBar.add(operations);
        addMenuItemTo(operations, "Add", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> addPersonClient());

        addMenuItemTo(operations, "Change", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changePersonClient());

        addMenuItemTo(operations, "Delete", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delPersonClient());

        container.add(menuBar, BorderLayout.NORTH);
    }

    private void initCompanyMenu(Container container) {

        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations for companies");
        operations.setBorder(BorderFactory.createEtchedBorder());
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Add", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> addCompanyClient());

        addMenuItemTo(operations, "Change", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changeCompanyClient());

        addMenuItemTo(operations, "Delete", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delCompanyClient());

        container.add(menuBar, BorderLayout.NORTH);
    }

    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
                               KeyStroke accelerator, ActionListener al) {

        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }

    private void startApp(){

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                Map<String, List<String>> sourceMap;
                List<String> sourcePersonList;
                List<String> sourceCompanyList;
                try{
                    sourceMap = file.extractClientsFromFile();

                    sourcePersonList = sourceMap.get(File.getURL_CLIENT());
                    sourceCompanyList = sourceMap.get(File.getURL_COMPANY());

                    sourcePersonList.forEach(x -> {
                        String[] temp = x.split("\u0009");
                        clientTableModel.addClient(new PhoneNumber(temp[0], temp[1]),
                                temp[2], temp[3], temp[4], temp[5]);
                    });
                    sourceCompanyList.forEach(x -> {
                        String[] temp = x.split("\u0009");
                        companyTableModel.addCompany(new PhoneNumber(temp[0], temp[1]), temp[2],
                                temp[3], temp[4], temp[5], temp[6]);
                    });
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(e.getWindow(),
                            "The data source file is missing or reading from it is impossible. The data will not be uploaded",
                            "Error. An error occurred while reading the file.", JOptionPane.ERROR_MESSAGE);
                }
                catch(NullPointerException npe){}
            }
        });
    }

    private void closeApp(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane
                        .showConfirmDialog(e.getWindow(), "Closing the app? Are you sure?",
                                "Confirmation of closing", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                if (n == 0) {
                    List<ClientInfo> personList = ClientList.getInstance().getClientsList();
                    List<CompanyInfo> companyList = CompanyList.getInstance().getCompanyClientsList();
                    try {
                        file.saveClientsToFile(personList, companyList);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(e.getWindow(),
                                "An error occurred while writing the file. Table Data may be lost.", "Error. The application will be stopped.",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
                else
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        });
    }

    private void addPersonClient() {
        clientDialog.prepareForAdd();
        while(clientDialog.showModal()) {
            try {
                PhoneNumber pn = new PhoneNumber(clientDialog.getAreaCode(), clientDialog.getPhoneNum());
                clientTableModel.addClient(pn, clientDialog.getClientName(), clientDialog.getClientAddr(),
                        clientDialog.getClientDateBirth());
                return;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Person registration error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void addCompanyClient() {
        companyDioalog.prepareForAdd();
        while(companyDioalog.showModal()) {
            try {
                PhoneNumber pn = new PhoneNumber(companyDioalog.getAreaCode(), companyDioalog.getPhoneNum());
                companyTableModel.addCompany(pn, companyDioalog.getCompanyName(), companyDioalog.getCompanyAddr(),
                         companyDioalog.getNameDirector(), companyDioalog.getContactName());
                return;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Company registration error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changePersonClient() {
        int seldRow = personsTable.getSelectedRow();
        if(seldRow == -1)
            return;

        ClientInfo ci = clientTableModel.getClient(seldRow);
        clientDialog.prepareForChange(ci);
        try {
            if(clientDialog.showModal()) {
                ci.setName(clientDialog.getClientName());
                ci.setAddress(clientDialog.getClientAddr());
                ci.setDateBirth(clientDialog.getClientDateBirth());
                ci.setPersonAge(ci.ageCalculator(ci.getDateBirth()));
                clientTableModel.clientChanged(seldRow);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error changing person data.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void changeCompanyClient() {
        int seldRow = companyTable.getSelectedRow();
        if(seldRow == -1)
            return;

        CompanyInfo ci = companyTableModel.getCompanyClient(seldRow);
        companyDioalog.prepareForChange(ci);
        try {
            if(companyDioalog.showModal()) {
                ci.setName(companyDioalog.getCompanyName());
                ci.setAddress(companyDioalog.getCompanyAddr());
                ci.setNameDirector(companyDioalog.getCompanyName());
                ci.setContactName(companyDioalog.getCompanyName());
                companyTableModel.companyChanged(seldRow);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error changing company data.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delPersonClient() {
        int seldRow = personsTable.getSelectedRow();
        if(seldRow == -1)
            return;

        ClientInfo ci = clientTableModel.getClient(seldRow);
        if(JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete Person\n"
                        + "with phone number " + ci.getPhoneNumber() + "?",
                "Delete confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            clientTableModel.dropClient(seldRow);
        }
    }
    private void delCompanyClient() {
        int seldRow = companyTable.getSelectedRow();
        if(seldRow == -1)
            return;

        CompanyInfo ci = companyTableModel.getCompanyClient(seldRow);
        if(JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete Company\n"
                        + "with phone number " + ci.getPhoneNumber() + "?",
                "Delete confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            companyTableModel.dropCompany(seldRow);
        }
    }
}