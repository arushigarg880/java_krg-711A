package com.bank;
import com.bank.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.bank.accounts.SavingsAccount;
import com.bank.customers.Customer;
import com.bank.exceptions.InsufficientBalanceException;
import com.bank.loans.Loan;

import static com.bank.util.BankUtil.generateAccountNumber;

public class BankApplication {

    public static void main(String[] args) {

        try {
            Customer customer = new Customer("C101", "Arushi");

            Connection conn = DBConnection.getConnection();


            String insertCustomer = "INSERT INTO customers (customer_id, name) VALUES (?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(insertCustomer);

            ps1.setString(1, "C101");
            ps1.setString(2, "Arushi");

            ps1.executeUpdate();
            System.out.println("Customer inserted into DB!");

            String accNo = generateAccountNumber();


            SavingsAccount account = new SavingsAccount(accNo, 5000, 5);

            String insertAccount = "INSERT INTO accounts (account_number, balance, customer_id) VALUES (?, ?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(insertAccount);

            ps2.setString(1, accNo);
            ps2.setDouble(2, account.getBalance());
            ps2.setString(3, "C101");

            ps2.executeUpdate();
            System.out.println("Account inserted into DB!");

            customer.linkAccount(account);


            account.deposit(2000);


            try {
                account.withdraw(7000);
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }


            double interest = account.calculateInterest();
            System.out.println("Interest: " + interest);

            Loan loan = new Loan(100000, 10, 2);
            System.out.println("Loan EMI: " + loan.calculateEMI());


            customer.displayCustomerDetails();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}