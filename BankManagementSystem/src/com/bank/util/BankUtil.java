package com.bank.util;

import java.util.Random;

public class BankUtil {


    public static String generateAccountNumber() {
        Random random = new Random();
        int number = 10000 + random.nextInt(90000);
        return "ACC" + number;
    }


    public static void validateMinimumBalance(double balance) throws Exception {
        if (balance < 1000) {
            throw new Exception("Minimum balance must be ₹1000");
        }
    }
}