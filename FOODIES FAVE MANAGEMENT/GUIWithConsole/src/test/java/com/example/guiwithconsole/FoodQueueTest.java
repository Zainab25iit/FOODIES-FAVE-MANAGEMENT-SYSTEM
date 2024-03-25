package com.example.guiwithconsole;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FoodQueueTest {
    private FoodQueue foodQueue;

    @BeforeEach
    public void setUp() {
        foodQueue = new FoodQueue("Cashier 1");
    }

    @Test
    public void testGetQueueName() {
        String queueName = foodQueue.getQueueName();
        Assertions.assertEquals("Cashier 1", queueName);
    }

    @Test
    public void testGetQueueLength() {
        int queueLength = foodQueue.getQueueLength();
        Assertions.assertEquals(0, queueLength);
    }

    @Test
    public void testGetIncome() {
        int income = foodQueue.getIncome();
        Assertions.assertEquals(0, income);
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer("Jane", "Doe", 3);
        foodQueue.addCustomer(customer);

        int queueLength = foodQueue.getQueueLength();
        Assertions.assertEquals(1, queueLength);

        int income = foodQueue.getIncome();
        Assertions.assertEquals(3 * 650, income);
    }

    @Test
    public void testServeCustomer() {
        Customer customer = new Customer("Jane", "Doe", 3);
        foodQueue.addCustomer(customer);

        Customer servedCustomer = foodQueue.serveCustomer();

        Assertions.assertNotNull(servedCustomer);
        Assertions.assertEquals("Jane Doe", servedCustomer.getFullName());

        int queueLength = foodQueue.getQueueLength();
        Assertions.assertEquals(0, queueLength);
    }

    @Test
    public void testGetWaitingListCustomers() {
        Customer customer1 = new Customer("Jane", "Doe", 3);
        Customer customer2 = new Customer("John", "Smith", 2);

        foodQueue.addCustomer(customer1);
        foodQueue.addCustomer(customer2);

        List<Customer> waitingListCustomers = foodQueue.getWaitingListCustomers();

        Assertions.assertEquals(10, waitingListCustomers.size());
    }
}
