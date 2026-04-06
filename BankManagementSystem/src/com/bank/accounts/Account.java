package com.bank.accounts;

import com.bank.exceptions.InsufficientBalanceException;
import com.bank.util.BankUtil;

public class Account {

    protected String accountNumber;
    protected double balance;


    public Account(String accountNumber, double balance) throws Exception {
        BankUtil.validateMinimumBalance(balance);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }


    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }


    public void withdraw(double amount) throws InsufficientBalanceException {


        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        if (balance - amount < 1000) {
            System.out.println("Minimum balance of ₹1000 must be maintained!");
            return;
        }

        balance -= amount;
        System.out.println("Withdrawn: " + amount);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}