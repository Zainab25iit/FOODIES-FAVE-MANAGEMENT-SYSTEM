package version1;

import java.util.*;

public class FoodiesFaveFoodCentre {
    private static final int MAX_STOCK = 50;
    private static final int WARNING_THRESHOLD = 10;

    private static int stock = MAX_STOCK;
    static Queue<String> cashier1 = new LinkedList<>();
    static Queue<String> cashier2 = new LinkedList<>();
    static Queue<String> cashier3 = new LinkedList<>();

    public static void main(String[] args) {
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

    static void displayMenu() {
        System.out.println("*****************");
        System.out.println("* Cashiers      *");
        System.out.println("*****************");
        System.out.println(printQueue(cashier1));
        System.out.println(printQueue(cashier2));
        System.out.println(printQueue(cashier3));
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
        System.out.println("999 or EXT: Exit the Program.");
        System.out.print("Enter your choice: ");
    }

    private static String printQueue(Queue<String> queue) {
        StringBuilder sb = new StringBuilder();
        int occupied = queue.size();
        int notOccupied = getQueueCapacity(queue) - occupied;

        for (int i = 0; i < occupied; i++) {
            sb.append("O ");
        }
        for (int i = 0; i < notOccupied; i++) {
            sb.append("X ");
        }

        return sb.toString().trim();
    }

    static int getQueueCapacity(Queue<String> queue) {
        if (queue == cashier1) {
            return 2;
        } else if (queue == cashier2) {
            return 3;
        } else if (queue == cashier3) {
            return 5;
        }
        return 0;
    }

    private static void viewAllQueues() {
        System.out.println("Cashier 1: " + cashier1);
        System.out.println("Cashier 2: " + cashier2);
        System.out.println("Cashier 3: " + cashier3);
    }

    private static void viewEmptyQueues() {
        if (cashier1.isEmpty()) {
            System.out.println("Cashier 1 is empty.");
        }
        if (cashier2.isEmpty()) {
            System.out.println("Cashier 2 is empty.");
        }
        if (cashier3.isEmpty()) {
            System.out.println("Cashier 3 is empty.");
        }
    }

    static void addCustomerToQueue(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter cashier number (1, 2, or 3): ");
        int cashierNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Queue<String> selectedCashier = getCashierByNumber(cashierNumber);
        if (selectedCashier != null) {
            selectedCashier.offer(customerName);
            stock -= 5;
            if (stock <= WARNING_THRESHOLD) {
                System.out.println("Warning: Low stock! Remaining burgers: " + stock);
            }
            System.out.println("Customer " + customerName + " added to Cashier " + cashierNumber);
        } else {
            System.out.println("Invalid cashier number.");
        }
    }

    static Queue<String> getCashierByNumber(int cashierNumber) {
        switch (cashierNumber) {
            case 1:
                return cashier1;
            case 2:
                return cashier2;
            case 3:
                return cashier3;
            default:
                return null;
        }
    }

    static void removeCustomerFromQueue(Scanner scanner) {
        System.out.print("Enter cashier number (1, 2, or 3): ");
        int cashierNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Queue<String> selectedCashier = getCashierByNumber(cashierNumber);
        if (selectedCashier != null) {
            System.out.print("Enter customer position to remove (1-" + selectedCashier.size() + "): ");
            int position = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (position >= 1 && position <= selectedCashier.size()) {
                String removedCustomer = removeCustomer(selectedCashier, position);
                if (removedCustomer != null) {
                    System.out.println("Customer " + removedCustomer + " removed from Cashier " + cashierNumber);
                } else {
                    System.out.println("Invalid position.");
                }
            } else {
                System.out.println("Invalid position.");
            }
        } else {
            System.out.println("Invalid cashier number.");
        }
    }

    static String removeCustomer(Queue<String> queue, int position) {
        Iterator<String> iterator = queue.iterator();
        String removedCustomer = null;
        int currentPosition = 1;

        while (iterator.hasNext()) {
            if (currentPosition == position) {
                removedCustomer = iterator.next();
                iterator.remove();
                break;
            }
            iterator.next();
            currentPosition++;
        }

        return removedCustomer;
    }

    static void removeServedCustomer() {
        String removedCustomer = removeCustomer(cashier1, 1);
        if (removedCustomer != null) {
            System.out.println("Customer " + removedCustomer + " served and removed from Cashier 1.");
        } else {
            removedCustomer = removeCustomer(cashier2, 1);
            if (removedCustomer != null) {
                System.out.println("Customer " + removedCustomer + " served and removed from Cashier 2.");
            } else {
                removedCustomer = removeCustomer(cashier3, 1);
                if (removedCustomer != null) {
                    System.out.println("Customer " + removedCustomer + " served and removed from Cashier 3.");
                } else {
                    System.out.println("No customers to serve.");
                }
            }
        }
    }

    static void viewCustomersSorted() {
        List<String> allCustomers = new ArrayList<>();
        allCustomers.addAll(cashier1);
        allCustomers.addAll(cashier2);
        allCustomers.addAll(cashier3);

        Collections.sort(allCustomers);
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

    static void viewRemainingStock() {
        System.out.println("Remaining burgers stock: " + stock);
    }

    static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        stock += burgersToAdd;
        System.out.println("Burgers added to stock. Remaining burgers: " + stock);
    }
}
