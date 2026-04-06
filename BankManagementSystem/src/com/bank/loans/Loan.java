package com.bank.loans;

public class Loan {

    private double loanAmount;
    private double interestRate;
    private int tenure;


    public Loan(double loanAmount, double interestRate, int tenure) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
    }


    public double calculateEMI() {
        return (loanAmount * interestRate * tenure) / 100;
    }
}