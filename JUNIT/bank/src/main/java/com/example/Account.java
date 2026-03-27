package com.example;


public class Account {
    private int balance;
    private String accountName;
    private String email;

    public Account(int balance, String accountName, String email) {
        this.balance = balance;
        this.accountName = accountName;
        this.email = email;
    }

    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        if (balance - amount >= 1500) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds: Must maintain 1500 minimum");
        }
    }

    @Override
    public String toString() {
        return "Account{name='" + accountName + "', balance=" + balance + "}";
    }
}