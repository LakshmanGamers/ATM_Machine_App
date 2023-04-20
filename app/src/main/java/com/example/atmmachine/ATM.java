package com.example.atmmachine;


public class ATM {

    private double balance = 100.0;

    public String printBalance() {
       return "Current balance: " + balance;
    }

    public String deposit(double amount) throws ATMException {
        if (amount <= 0) {
            throw new ATMException("Invalid deposit amount");
        }
        balance += amount;
        return "Deposit successful.\n\n "+printBalance();
    }


    public String withdraw(double amount) throws ATMException {
        if (amount <= 0) {
            throw new ATMException("Invalid withdrawal amount");
        }
        if (amount > balance) {
            throw new ATMException("Insufficient funds");
        }
        balance -= amount;
        return "Withdrawal successful.\n\n "+printBalance();
    }

}
