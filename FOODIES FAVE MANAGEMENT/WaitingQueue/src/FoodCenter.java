import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class FoodCenter {
    private static final int MAX_QUEUES = 3;

    private static FoodQueue[] queues;
    private static int stock = FoodQueue.MAX_STOCK;

    public static void main(String[] args) {

        initializeQueues();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 100:
                    viewAllQueues();
                    break;
                case 101:
                    viewEmptyQueues();
                    break;
                case 102:
                    addCustomerToQueue(scanner);
                    break;
                case 103:
                    removeCustomerFromQueue(scanner);
                    break;
                case 104:
                    removeServedCustomer();
                    break;
                case 105:
                    viewCustomersSorted();
                    break;
                case 106:
                    storeProgramData();
                    break;
                case 107:
                    loadProgramData();
                    break;
                case 108:
                    viewRemainingStock();
                    break;
                case 109:
                    addBurgersToStock(scanner);
                    break;
                case 110:
                    printIncomeOfEachQueue();
                    break;
                case 999:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 999);

        scanner.close();
    }

    private static void initializeQueues() {
        queues = new FoodQueue[MAX_QUEUES];
        queues[0] = new FoodQueue("Cashier 1");
        queues[1] = new FoodQueue("Cashier 2");
        queues[2] = new FoodQueue("Cashier 3");
    }

    private static void displayMenu() {
        System.out.println("*****************");
        System.out.println("* Cashiers      *");
        System.out.println("*****************");
        for (FoodQueue queue : queues) {
            System.out.println(printQueue(queue));
        }
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue.");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order.");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("110 or IFQ: Print the income of each queue.");
        System.out.println("999 or EXT: Exit the Program.");
        System.out.print("Enter your choice: ");
    }

    private static String printQueue(FoodQueue queue) {
        StringBuilder sb = new StringBuilder();
        int occupied = queue.getQueueLength();
        int notOccupied = getQueueCapacity(queue) - occupied;

        for (int i = 0; i < occupied; i++) {
            sb.append("O ");
        }
        for (int i = 0; i < notOccupied; i++) {
            sb.append("X ");
        }

        return queue.getQueueName() + ": " + sb.toString().trim();
    }

    private static int getQueueCapacity(FoodQueue queue) {
        if (queue.getQueueName().equals("Cashier 1")) {
            return 2;
        } else if (queue.getQueueName().equals("Cashier 2")) {
            return 3;
        } else if (queue.getQueueName().equals("Cashier 3")) {
            return 5;
        }
        return 0;
    }

    private static void viewAllQueues() {
        for (FoodQueue queue : queues) {
            System.out.println(queue.getQueueName() + ": " + queue);
        }
    }

    private static void viewEmptyQueues() {
        for (FoodQueue queue : queues) {
            if (queue.getQueueLength() == 0) {
                System.out.println(queue.getQueueName() + " is empty.");
            }
        }
    }

    private static void addCustomerToQueue(Scanner scanner) {
        System.out.print("Enter customer's first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter customer's last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter the number of burgers required: ");
        int burgersRequired = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        FoodQueue selectedQueue = getQueueWithMinimumLength();
        if (selectedQueue != null) {
            Customer customer = new Customer(firstName, lastName, burgersRequired);
            selectedQueue.addCustomer(customer);
            stock -= burgersRequired;
            System.out.println("Customer " + customer.getFullName() + " added to " + selectedQueue.getQueueName());
        } else {
            System.out.println("All queues are full. Customer " + firstName + " " + lastName + " added to the waiting list.");
        }
    }

    private static FoodQueue getQueueWithMinimumLength() {
        FoodQueue minQueue = null;
        int minLength = Integer.MAX_VALUE;

        for (FoodQueue queue : queues) {
            if (queue.getQueueLength() < minLength) {
                minQueue = queue;
                minLength = queue.getQueueLength();
            }
        }

        return minQueue;
    }

    private static void removeCustomerFromQueue(Scanner scanner) {
        System.out.print("Enter cashier number (1, 2, or 3): ");
        int cashierNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (cashierNumber >= 1 && cashierNumber <= MAX_QUEUES) {
            FoodQueue selectedQueue = queues[cashierNumber - 1];
            if (selectedQueue.getQueueLength() > 0) {
                System.out.print("Enter customer position to remove (1-" + selectedQueue.getQueueLength() + "): ");
                int position = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (position >= 1 && position <= selectedQueue.getQueueLength()) {
                    selectedQueue.serveCustomer();
                } else {
                    System.out.println("Invalid position.");
                }
            } else {
                System.out.println(selectedQueue.getQueueName() + " is empty.");
            }
        } else {
            System.out.println("Invalid cashier number.");
        }
    }

    private static void removeServedCustomer() {
        for (FoodQueue queue : queues) {
            Customer servedCustomer = queue.serveCustomer();
            if (servedCustomer != null) {
                break;
            }
        }
    }

    private static void viewCustomersSorted() {
        List<Customer> allCustomers = new ArrayList<>();
        for (FoodQueue queue : queues) {
            allCustomers.addAll(queue.getCustomers());
        }

        allCustomers.sort(Comparator.comparing(Customer::getFullName));
        System.out.println("Customers Sorted in alphabetical order: " + allCustomers);
    }

    private static void storeProgramData() {
        // Implement code to store program data into a file (not included in this example)
        System.out.println("Program data stored successfully.");
    }

    private static void loadProgramData() {
        // Implement code to load program data from a file (not included in this example)
        System.out.println("Program data loaded successfully.");
    }

    private static void viewRemainingStock() {
        System.out.println("Remaining burgers stock: " + stock);
    }

    private static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        stock += burgersToAdd;
        System.out.println("Burgers added to stock. Remaining burgers: " + stock);
    }

    private static void printIncomeOfEachQueue() {
        for (FoodQueue queue : queues) {
            System.out.println(queue.getQueueName() + " Income: $" + queue.getIncome());
        }
    }
}

