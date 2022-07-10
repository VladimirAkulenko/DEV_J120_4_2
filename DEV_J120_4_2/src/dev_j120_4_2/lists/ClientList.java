/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.lists;

import java.util.*;

import dev_j120_4_2.models.ClientInfo;
import dev_j120_4_2.models.PersonsInfo;
import dev_j120_4_2.models.PhoneNumber;

/**
 * Manages list of clients.
 */
public class ClientList {
    /**
     * The only instance of this class.
     *
     * @see #getInstance
     */
    private static final dev_j120_4_2.lists.ClientList instance = new dev_j120_4_2.lists.ClientList();

    /**
     * Keeps the list of of clients. Clients are stored in the same order,
     * which they have been registered in.
     */
    private List<ClientInfo> clients = new ArrayList<>();

    /**
     * List of client phone numbers. The list is used by {@link #addClient} method to check,
     * that specified phone number is not used.
     * This set is updated simultaneously with {@link #clients} list, when
     * clients are {@linkplain #addClient added} or {@linkplain #remove removed}.
     */
    private Set <PhoneNumber> numbers = new HashSet<>();

    /**
     * Prevents instance creation out of the class.
     */
    private ClientList() {
    }

    /**
     * Adds client with specified attributes. Creates instance of {@code ClientInfo}
     * (see {@link ClientInfo#ClientInfo(PhoneNumber, String, String)}) while adding new client.
     *
     * @param number client phone number
     * @param name client name
     * @param address client address
     *
     * @exception IllegalArgumentException If some client with specified phone number
     * 		has already been registered.
     */
    public void addClient(PhoneNumber number, String name, String address, String dataBirth) {
        if(numbers.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        clients.add(new ClientInfo(number, name, address, dataBirth));
        numbers.add(number);
    }
    
    public void addClient(PhoneNumber number, String name, String address, String dataBirth, String regDate) {
        if(numbers.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        clients.add(new ClientInfo(number, name, address, dataBirth, regDate));
        numbers.add(number);
    }

    /**
     * Removes client with the specified index.
     *
     * @param index index of a client to be removed
     *
     * @exception IndexOutOfBoundsException
     * 		If the index is out of range (index < 0 || index >= {@link #getClientsCount}).
     */
    public void remove(int index) {
        ClientInfo clientInfo = clients.get(index);
        numbers.remove(clientInfo.getPhoneNumber());
        clients.remove(index);
    }

    /**
     * Returns amount of clients, kept by the list.
     *
     * @return Number of clients, kept by the list.
     */
    public int getClientsCount() {
        return clients.size();
    }

    /**
     * Returns information about a client with specified index.
     *
     * @param index client index, which data is retrieved
     *
     * @return {@code ClientInfo}
     *
     * @exception IndexOutOfBoundsException
     * 		If the index is out of range (index < 0 || index >= {@link #getClientsCount}).
     */
    public ClientInfo getClientInfo(int index) {
        return clients.get(index);
    }

    /**
     * Returns the only instance of this class.
     * @return 
     */
    public static dev_j120_4_2.lists.ClientList getInstance() {
        return instance;
    }
    
    public List<ClientInfo> getClientsList() {
        return clients;
    }
}