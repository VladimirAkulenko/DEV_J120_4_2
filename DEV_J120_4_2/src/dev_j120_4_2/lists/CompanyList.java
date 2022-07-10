/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev_j120_4_2.models.CompanyInfo;
import dev_j120_4_2.models.PhoneNumber;

public class CompanyList {

    private static final dev_j120_4_2.lists.CompanyList companyInstance = new dev_j120_4_2.lists.CompanyList();


    private List<CompanyInfo> companyClient = new ArrayList<>();


    private Set <PhoneNumber> companyNumbers = new HashSet<>();


    private CompanyList() {
    }


    public void addCompany(PhoneNumber number, String name, String address, String nameDirector, String contactName) {
        if(companyNumbers.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        companyClient.add(new CompanyInfo(number, name, address, nameDirector, contactName));
        companyNumbers.add(number);
    }

    public void addCompany(PhoneNumber number, String name, String address, String nameDirector, String contactName, String regDate) {
        if(companyNumbers.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        companyClient.add(new CompanyInfo(number, name, address, nameDirector, contactName, regDate));
        companyNumbers.add(number);
    }


    public void removeCompany(int index) {
        CompanyInfo companyClientInfo = companyClient.get(index);
        companyNumbers.remove(companyClientInfo.getPhoneNumber());
        companyClient.remove(index);
    }


    public int getCompanyClientCount() {
        return companyClient.size();
    }


    public CompanyInfo getCompanyClientInfo(int index) {
        return companyClient.get(index);
    }


    public static dev_j120_4_2.lists.CompanyList getInstance() {
        return companyInstance;
    }
    public List<CompanyInfo> getCompanyClientsList() {
        return companyClient;
    }
}
