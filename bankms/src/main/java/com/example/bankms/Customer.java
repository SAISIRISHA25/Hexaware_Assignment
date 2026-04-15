package com.example.bankms;


public class Customer {
    private int customerId;
    private String customerName;
    private long accountNumber;
    private double balance;

    public Customer() {
    }

    public Customer(int customerId, String customerName, long accountNumber, double balance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId +
                ", customerName=" + customerName +
                ", accountNumber=" + accountNumber +
                ", balance=" + balance + "]";
    }
}
