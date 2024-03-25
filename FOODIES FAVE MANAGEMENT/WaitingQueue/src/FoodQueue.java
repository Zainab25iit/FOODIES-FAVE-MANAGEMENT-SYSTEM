import java.util.LinkedList;
import java.util.Queue;

public class FoodQueue {
    public static final int MAX_STOCK = 50;
    private static final int WARNING_THRESHOLD = 10;
    private static final int BURGER_PRICE = 650;
    private static final int MAX_WAITING_LIST = 10;

    private Queue<Customer> customers;
    private String queueName;
    private int income;

    private CircularQueue<Customer> waitingList;

    public FoodQueue(String queueName) {
        this.queueName = queueName;
        this.customers = new LinkedList<>();
        this.income = 0;

        this.waitingList = new CircularQueue<>(MAX_WAITING_LIST);
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
        if (customers.size() < getMaxQueueCapacity()) {
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
        } else {
            addToWaitingList(customer);
        }
    }

    public Customer serveCustomer() {
        Customer servedCustomer = customers.poll();
        if (servedCustomer != null) {
            System.out.println("Customer " + servedCustomer.getFullName() + " served and removed from " + queueName + ".");
            serveNextCustomerFromWaitingList();
        } else {
            System.out.println("No customers to serve in " + queueName + ".");
        }
        return servedCustomer;
    }

    private void addToWaitingList(Customer customer) {
        if (waitingList.isFull()) {
            System.out.println("Waiting list is full. Customer " + customer.getFullName() + " cannot be added.");
        } else {
            waitingList.offer(customer);
            System.out.println("Customer " + customer.getFullName() + " added to the waiting list.");
        }
    }

    private void serveNextCustomerFromWaitingList() {
        Customer nextCustomer = waitingList.poll();
        if (nextCustomer != null) {
            addCustomer(nextCustomer);
        }
    }

    private int getTotalBurgersSold() {
        int totalBurgersSold = 0;
        for (Customer customer : customers) {
            totalBurgersSold += customer.getBurgersRequired();
        }
        return totalBurgersSold;
    }

    private int getMaxQueueCapacity() {
        if (queueName.equals("Cashier 1")) {
            return 2;
        } else if (queueName.equals("Cashier 2")) {
            return 3;
        } else if (queueName.equals("Cashier 3")) {
            return 5;
        }
        return 0;
    }

    private static class CircularQueue<E> {
        private E[] elements;
        private int maxSize;
        private int front;
        private int rear;
        private int size;

        public CircularQueue(int maxSize) {
            this.maxSize = maxSize;
            elements = (E[]) new Object[maxSize];
            front = 0;
            rear = -1;
            size = 0;
        }

        public boolean isFull() {
            return size == maxSize;
        }

        public boolean offer(E element) {
            if (isFull()) {
                return false;
            }
            rear = (rear + 1) % maxSize;
            elements[rear] = element;
            size++;
            return true;
        }

        public E poll() {
            if (isEmpty()) {
                return null;
            }
            E element = elements[front];
            front = (front + 1) % maxSize;
            size--;
            return element;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    public Queue<Customer> getCustomers() {
        return customers;
    }

    public CircularQueue<Customer> getWaitingList() {
        return waitingList;
    }
}
