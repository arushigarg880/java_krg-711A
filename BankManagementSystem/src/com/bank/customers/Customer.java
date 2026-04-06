package com.bank.customers;

import com.bank.accounts.Account;

public class Customer {

    private String customerId;
    private String name;
    private Account account;

\
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public void linkAccount(Account account) {
        this.account = account;
    }
    public void displayCustomerDetails() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);

        if (account != null) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("No account linked.");
        }
    }
}
