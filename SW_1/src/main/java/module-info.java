module com.example.sw_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sw_1 to javafx.fxml;
    exports com.example.sw_1;
}