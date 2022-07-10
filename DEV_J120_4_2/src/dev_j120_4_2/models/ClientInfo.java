/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import javax.swing.*;

import dev_j120_4_2.Utils;

/**
 * Keeps information about a company client.
 */
public class ClientInfo {
    /**
     * Phone number.
     */
    private final PhoneNumber phoneNumber;
    /**
     * Registration date. The field is filled by {@linkplain com.company.models.ClientInfo#ClientInfo constructor}.
     */
    private final String regDate;
    /**
     * Client name.
     */
    private String name;
    /**
     * Client address.
     */
    private String address;

    private String dateBirth;
    
    private Integer personAge;

    /**
     * Initializes instance attributes with values from corresponding constructor parameters.
     * Sets client registration date to current (today) date.
     *
     * @exception IllegalArgumentException If either {@code phoneNumber}, or {@code name},
     * 		or {@code address} is {@code null}.
     */
    public ClientInfo(PhoneNumber phoneNumber, String name, String address, String dateBirth) {
        if(phoneNumber == null)
            throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        setName(name);
        setAddress(address);
        setDateBirth(dateBirth);
        this.personAge = ageCalculator(this.dateBirth);
    }
    
    public ClientInfo(PhoneNumber phoneNumber, String name, String address, String dateBirth, String regDate) {
        if(phoneNumber == null)
            throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = regDate;
        setName(name);
        setAddress(address);
        setDateBirth(dateBirth);
        this.personAge = ageCalculator(this.dateBirth);
    }

    /**
     * Returns client name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets client name.
     *
     * @exception IllegalArgumentException if {@code address} is {@code null}.
     */
    public void setName(String name) {
        if(name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name can't be null.");
        this.name = name;
    }

    /**
     * Returns client address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets client address.
     *
     * @exception IllegalArgumentException if {@code address} is {@code null}.
     */
    public void setAddress(String address) {
        if(address == null)
            throw new IllegalArgumentException("address can't be null.");
        this.address = address;
    }

    /**
     * Returns client phone number.
     * This attribute has no setter.
     */
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns registration date.
     * This attribute has no setter.
     */
    public String getRegDate() {
        return regDate;
    }
public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        if(dateBirth == null || dateBirth.trim().isEmpty())
            throw new IllegalArgumentException("date of birth can't be null.");
        int age = ageCalculator(dateBirth);
        checkAge(age);
        this.dateBirth = dateBirth;
    }
    public Integer getPersonAge(){
        return personAge;
    }
    public void setPersonAge(Integer personAge) {

        this.personAge = personAge;
    }

    public final Integer ageCalculator (String dateBirth){

        LocalDate parsedDate = null;
        try {
            parsedDate = LocalDate.parse(dateBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        catch(DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(Utils.findLatestWindow(),
                "\"Date of birth\" field must be filled. Enter date of "
                        + "birth in this format dd.MM.yyyy",
                "Error.", JOptionPane.ERROR_MESSAGE);}
        Integer age = (int) ChronoUnit.YEARS.between(parsedDate, LocalDate.now());
        checkAge(age);
        return age;
    }

    public void checkAge(int age){
        if(age < 2 || age > 130)
            throw new IllegalArgumentException("The person has already died or has not "
                    + "yet been born, and if it was born, it does not know how to talk. "
                    + "Correct the person's date of birth.");
    }

    @Override
    public String toString() {
        final String CS = "\u0009";
        return phoneNumber.getAreaCode()+ CS + phoneNumber.getLocalNum() +CS+
                name + CS + address + CS + dateBirth + CS + regDate;
    }

}