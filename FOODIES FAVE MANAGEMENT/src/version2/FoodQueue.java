package version2;

import java.util.LinkedList;
import java.util.Queue;

public class FoodQueue {
    public static final int MAX_STOCK = 50;
    private static final int WARNING_THRESHOLD = 10;
    private static final int BURGER_PRICE = 650;

    private Queue<Customer> customers;
    private String queueName;
    private int income;

    public FoodQueue(String queueName) {
        this.queueName = queueName;
        this.customers = new LinkedList<>();
        this.income = 0;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getQueueLength() {
        return customers.size();
    }

    public int getIncome() {
        return income;
    }

    public void addCustomer(Customer customer) {
        customers.offer(customer);
        int burgersSold = customer.getBurgersRequired();
        int totalPrice = burgersSold * BURGER_PRICE;
        income += totalPrice;
        System.out.println("Customer " + customer.getFullName() + " added to " + queueName + ".");
        System.out.println("Burgers sold: " + burgersSold);
        System.out.println("Total price: $" + totalPrice);

        int remainingStock = MAX_STOCK - (getTotalBurgersSold() * BURGER_PRICE);
        if (remainingStock <= WARNING_THRESHOLD) {
            System.out.println("Warning: Low stock! Remaining burgers: " + remainingStock);
        }
    }

    public Customer serveCustomer() {
        Customer servedCustomer = customers.poll();
        if (servedCustomer != null) {
            System.out.println("Customer " + servedCustomer.getFullName() + " served and removed from " + queueName + ".");
        } else {
            System.out.println("No customers to serve in " + queueName + ".");
        }
        return servedCustomer;
    }

    private int getTotalBurgersSold() {
        int totalBurgersSold = 0;
        for (Customer customer : customers) {
            totalBurgersSold += customer.getBurgersRequired();
        }
        return totalBurgersSold;
    }

    public Queue<Customer> getCustomers() {
        return customers;
    }
}
