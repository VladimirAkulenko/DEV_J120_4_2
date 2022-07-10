/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import dev_j120_4_2.models.CompanyInfo;

public class CompanyDioalog extends JDialog {
    private final JTextField areaCodeField;
    private final JTextField phoneNumField;
    private final JTextField companyNameField;
    private final JTextField companyAddrField;
    private final JTextField companyNameDirectorField;
    private final JTextField companyContactNameField;


    private boolean okPressed;


    public CompanyDioalog(JFrame owner) {
        super(owner, true);

        areaCodeField = new JTextField(3);
        phoneNumField = new JTextField(5);
        companyNameField = new JTextField(34);
        companyAddrField = new JTextField(33);
        companyNameDirectorField = new JTextField(33);
        companyContactNameField = new JTextField(29);


        initLayout();

        setResizable(false);
    }


    private void initLayout() {
        initControls();
        initOkCancelButtons();
    }


    private void initControls() {
        JPanel controlsPane = new JPanel(null);
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));
        //Font f0 = new Font("Arial", 0, 16);
        //Font f1 = new Font("Arial", 1, 16);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel("Phone number (");
        //lbl.setFont(f1);
        //areaCodeField.setFont(f0);
        lbl.setLabelFor(areaCodeField);
        p.add(lbl);
        p.add(areaCodeField);
        lbl = new JLabel(")");
        //lbl.setFont(f1);
        //phoneNumField.setFont(f0);
        lbl.setLabelFor(phoneNumField);
        p.add(lbl);
        p.add(phoneNumField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Company name");
        //lbl.setFont(f1);
        //companyNameField.setFont(f0);
        lbl.setLabelFor(companyNameField);
        p.add(lbl);
        p.add(companyNameField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Company address");
        //lbl.setFont(f1);
        //companyAddrField.setFont(f0);
        lbl.setLabelFor(companyAddrField);
        p.add(lbl);
        p.add(companyAddrField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Company director");
        //lbl.setFont(f1);
        //companyNameDirectorField.setFont(f0);
        lbl.setLabelFor(companyNameDirectorField);
        p.add(lbl);
        p.add(companyNameDirectorField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Company contact person");
        //lbl.setFont(f1);
        //companyContactNameField.setFont(f0);
        lbl.setLabelFor(companyContactNameField);
        p.add(lbl);
        p.add(companyContactNameField);
        controlsPane.add(p);

        add(controlsPane, BorderLayout.CENTER);
    }


    private void initOkCancelButtons() {
        JPanel btnsPane = new JPanel();

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        okBtn.setDefaultCapable(true);
        btnsPane.add(okBtn);

        Action cancelDialogAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };

        JButton cancelBtn = new JButton(cancelDialogAction);
        btnsPane.add(cancelBtn);

        add(btnsPane, BorderLayout.SOUTH);

        final String esc = "escape";
        getRootPane()
                .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
        getRootPane()
                .getActionMap()
                .put(esc, cancelDialogAction);
    }


    public boolean showModal() {
        pack();
        setLocationRelativeTo(getOwner());
        if(areaCodeField.isEnabled())
            areaCodeField.requestFocusInWindow();
        else
            companyNameField.requestFocusInWindow();
        okPressed = false;
        setVisible(true);
        return okPressed;
    }


    public void prepareForAdd() {
        setTitle("New client registration");

        areaCodeField.setText("");
        phoneNumField.setText("");
        companyNameField.setText("");
        companyAddrField.setText("");
        companyContactNameField.setText("");
        companyNameDirectorField.setText("");

        areaCodeField.setEnabled(true);
        phoneNumField.setEnabled(true);
    }


    public void prepareForChange(CompanyInfo ci) {
        setTitle("Company properties change");

        areaCodeField.setText(ci.getPhoneNumber().getAreaCode());
        phoneNumField.setText(ci.getPhoneNumber().getLocalNum());
        companyNameField.setText(ci.getName());
        companyAddrField.setText(ci.getAddress());
        companyContactNameField.setText(ci.getContactName());
        companyNameDirectorField.setText(ci.getNameDirector());

        areaCodeField.setEnabled(false);
        phoneNumField.setEnabled(false);
    }


    public String getAreaCode() {
        return areaCodeField.getText();
    }


    public String getPhoneNum() {
        return phoneNumField.getText();
    }


    public String getCompanyName() {
        return companyNameField.getText();
    }


    public String getCompanyAddr() {
        return companyAddrField.getText();
    }

    public String getContactName() {
        return companyContactNameField.getText();
    }

    public String getNameDirector() {
        return companyNameDirectorField.getText();
    }

}