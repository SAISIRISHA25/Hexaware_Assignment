package com.example.bankms;


import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCustomer(Customer c) {
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                c.getCustomerId(),
                c.getCustomerName(),
                c.getAccountNumber(),
                c.getBalance());

        if (rows > 0) {
            System.out.println("Customer added successfully.");
        }
    }

    public Customer findByAccountNumber(long accountNumber) {
        String sql = "SELECT * FROM customer WHERE account_number=?";

        List<Customer> list = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getLong("account_number"),
                        rs.getDouble("balance")
                ),
                accountNumber);

        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void depositAmount(long accountNumber, double amount) {
        String sql = "UPDATE customer SET balance = balance + ? WHERE account_number=?";
        int rows = jdbcTemplate.update(sql, amount, accountNumber);

        if (rows > 0) {
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Customer account not found.");
        }
    }

    public void withdrawAmount(long accountNumber, double amount) {
        Customer c = findByAccountNumber(accountNumber);

        if (c == null) {
            System.out.println("Customer account not found.");
            return;
        }

        if (c.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return;
        }

        String sql = "UPDATE customer SET balance = balance - ? WHERE account_number=?";
        jdbcTemplate.update(sql, amount, accountNumber);
        System.out.println("Amount withdrawn successfully.");
    }

    public void transferAmount(long fromAccount, long toAccount, double amount) {
        Customer sender = findByAccountNumber(fromAccount);
        Customer receiver = findByAccountNumber(toAccount);

        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (sender.getBalance() < amount) {
            System.out.println("Insufficient balance in sender account.");
            return;
        }

        String withdrawSql = "UPDATE customer SET balance = balance - ? WHERE account_number=?";
        String depositSql = "UPDATE customer SET balance = balance + ? WHERE account_number=?";

        jdbcTemplate.update(withdrawSql, amount, fromAccount);
        jdbcTemplate.update(depositSql, amount, toAccount);

        System.out.println("Amount transferred successfully.");
    }

    public void showAllCustomers() {
        String sql = "SELECT * FROM customer";

        List<Customer> list = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getLong("account_number"),
                        rs.getDouble("balance")
                ));

        if (list.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer c : list) {
                System.out.println(c);
            }
        }
    }

    public void enquireCustomer(long accountNumber) {
        Customer c = findByAccountNumber(accountNumber);

        if (c == null) {
            System.out.println("Customer not found.");
        } else {
            System.out.println("Customer Details:");
            System.out.println(c);
        }
    }
}
