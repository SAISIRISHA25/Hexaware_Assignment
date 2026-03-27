package com.example;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class AccountTest {

    static String dbStatus;
    Account myAccount;

    @BeforeAll
    static void initDatabase() {
        dbStatus = "Connected to BankDB";
        System.out.println("--- @BeforeAll: Database Connection Opened ---");
    }

    @BeforeEach
    void setup() {
        myAccount = new Account(1500, "John Doe", "john@example.com");
        System.out.println("--- @BeforeEach: New Account Opened with 1500 ---");
    }

    @Test
    @DisplayName("Verify Opening Balance")
    void testInitialBalance() {
        assertEquals(1500, myAccount.getBalance(), "Opening balance should be 1500");
    }

    @Test
    @DisplayName("Test Deposit Logic")
    void testDeposit() {
        myAccount.deposit(500);
        assertEquals(2000, myAccount.getBalance());
    }

    @Test
    @DisplayName("Test Withdrawal with Min Balance Rule")
    void testWithdrawal() {
        assertThrows(IllegalArgumentException.class, () -> {
            myAccount.withdraw(100); 
        }, "Should throw error if balance drops below 1500");
    }

    @AfterEach
    void cleanup() {
        myAccount = null;
        System.out.println("--- @AfterEach: Account Data Cleared ---");
    }

    @AfterAll
    static void tearDown() {
        dbStatus = "Disconnected";
        System.out.println("--- @AfterAll: Database Connection Closed ---");
    }
}
