package com.example.guiwithconsole;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FoodCenterGUI {
    private static final int MAX_QUEUES = 3;

    private FoodQueue[] queues;

    public FoodCenterGUI(FoodQueue[] queues) {
        this.queues = queues;
    }

    public void runGUI(Stage primaryStage) {

        GridPane gridPane = createGridPane();
        populateGridPane(gridPane);

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("Food Center Queue Status");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create UI components
        Label titleLabel = new Label("Food Center Queue Status");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Customer> queueTableView = createQueueTableView();
        TableView<Customer> waitingListTableView = createWaitingListTableView();

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> {
            refreshQueueTableView(queueTableView);
            refreshWaitingListTableView(waitingListTableView);
        });

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Enter customer name");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> searchCustomer(queueTableView, waitingListTableView, searchTextField.getText()));

        // Add UI components to gridPane
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(new Label("Food Queue"), 0, 1);
        gridPane.add(queueTableView, 0, 2);
        gridPane.add(new Label("Waiting List"), 1, 1);
        gridPane.add(waitingListTableView, 1, 2);
        gridPane.add(refreshButton, 0, 3);
        gridPane.add(searchTextField, 0, 4);
        gridPane.add(searchButton, 1, 4);

        return gridPane;
    }

    private TableView<Customer> createQueueTableView() {
        TableView<Customer> tableView = new TableView<>();

        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(data -> data.getValue().firstNameProperty());

        TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(data -> data.getValue().lastNameProperty());

        TableColumn<Customer, Integer> burgersRequiredColumn = new TableColumn<>("Burgers Required");
        burgersRequiredColumn.setCellValueFactory(data -> data.getValue().burgersRequiredProperty().asObject());

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, burgersRequiredColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        refreshQueueTableView(tableView);

        return tableView;
    }

    private TableView<Customer> createWaitingListTableView() {
        TableView<Customer> tableView = new TableView<>();
        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));

        TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));

        TableColumn<Customer, Integer> burgersRequiredColumn = new TableColumn<>("Burgers Required");
        burgersRequiredColumn.setCellValueFactory(new PropertyValueFactory("burgersRequired"));

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, burgersRequiredColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        refreshWaitingListTableView(tableView);

        return tableView;
    }

    private void populateGridPane(GridPane gridPane) {
        gridPane.add(new Label("Food Queue"), 0, 1);
        gridPane.add(new Label("Waiting List"), 1, 1);
    }

    private void refreshQueueTableView(TableView<Customer> tableView) {
        List<Customer> allCustomers = new ArrayList<>();
        for (FoodQueue queue : queues) {
            allCustomers.addAll(queue.getCustomers());
        }
        tableView.getItems().setAll(allCustomers);
    }

    private void refreshWaitingListTableView(TableView<Customer> tableView) {
        List<Customer> waitingListCustomers = queues[0].getWaitingListCustomers();
        tableView.getItems().setAll(waitingListCustomers);
    }

    private void searchCustomer(TableView<Customer> queueTableView, TableView<Customer> waitingListTableView, String searchName) {
        List<Customer> matchedCustomers = new ArrayList<>();
        for (FoodQueue queue : queues) {
            for (Customer customer : queue.getCustomers()) {
                if (customer.getFullName().toLowerCase().contains(searchName.toLowerCase())) {
                    matchedCustomers.add(customer);
                }
            }
        }
        queueTableView.getItems().setAll(matchedCustomers);

        List<Customer> waitingListCustomers = queues[0].getWaitingListCustomers();
        List<Customer> matchedWaitingListCustomers = new ArrayList<>();
        for (Customer customer : waitingListCustomers) {
            if (customer.getFullName().toLowerCase().contains(searchName.toLowerCase())) {
                matchedWaitingListCustomers.add(customer);
            }
        }
        waitingListTableView.getItems().setAll(matchedWaitingListCustomers);
    }

//    private void initializeQueues() {
//        queues = new FoodQueue[MAX_QUEUES];
//        queues[0] = new FoodQueue("Cashier 1");
//        queues[1] = new FoodQueue("Cashier 2");
//        queues[2] = new FoodQueue("Cashier 3");
//    }

    public void setQueues(FoodQueue[] queues) {
        this.queues = queues;
    }
}
