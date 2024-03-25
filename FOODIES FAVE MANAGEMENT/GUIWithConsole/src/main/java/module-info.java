module com.example.guiwithconsole {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guiwithconsole to javafx.fxml;
    exports com.example.guiwithconsole;
}