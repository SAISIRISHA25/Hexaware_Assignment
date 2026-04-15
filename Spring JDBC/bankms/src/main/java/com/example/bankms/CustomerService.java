package com.example.bankms;


public class CustomerService {

    private CustomerDAO customerDAO;

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void addCustomer(Customer c) {
        customerDAO.addCustomer(c);
    }

    public void depositAmount(long accountNumber, double amount) {
        customerDAO.depositAmount(accountNumber, amount);
    }

    public void withdrawAmount(long accountNumber, double amount) {
        customerDAO.withdrawAmount(accountNumber, amount);
    }

    public void transferAmount(long fromAccount, long toAccount, double amount) {
        customerDAO.transferAmount(fromAccount, toAccount, amount);
    }

    public void showAllCustomers() {
        customerDAO.showAllCustomers();
    }

    public void enquireCustomer(long accountNumber) {
        customerDAO.enquireCustomer(accountNumber);
    }
}
