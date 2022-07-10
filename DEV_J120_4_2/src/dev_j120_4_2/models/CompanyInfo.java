/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompanyInfo {

    private final PhoneNumber phoneNumber;
    private final String regDate;

    private String name;

    private String address;

    private String nameDirector;

    private String contactName;


    public CompanyInfo(PhoneNumber phoneNumber, String name, String address, String nameDirector, String contactName) {
        if(phoneNumber == null)
            throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        setName(name);
        setAddress(address);
        setNameDirector(nameDirector);
        setContactName(contactName);
    }
    public CompanyInfo(PhoneNumber phoneNumber, String name, String address, String nameDirector, String contactName, String regDate) {
        if(phoneNumber == null)
            throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = regDate;
        setName(name);
        setAddress(address);
        setNameDirector(nameDirector);
        setContactName(contactName);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name can't be null.");
        this.name = name;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        if(address == null || address.trim().isEmpty())
            throw new IllegalArgumentException("address can't be null.");
        this.address = address;
    }


    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getNameDirector() {
        return nameDirector;
    }

    public void setNameDirector(String nameDirector) {
        if(nameDirector == null || nameDirector.trim().isEmpty())
            throw new IllegalArgumentException("name director can't be null.");
        this.nameDirector = nameDirector;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        if(contactName == null || contactName.trim().isEmpty())
            throw new IllegalArgumentException("contact name can't be null.");
        this.contactName = contactName;
    }
    public String getRegDate(){
        return regDate;
    }

    @Override
    public String toString() {
        final String CS = "\u0009";
        return phoneNumber.getAreaCode()+ CS + phoneNumber.getLocalNum() +CS+
                name + CS + address + CS + nameDirector + CS + contactName + CS + regDate;
    }
}
