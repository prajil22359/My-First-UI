module com.example.prajil {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prajil to javafx.fxml;
    exports com.example.prajil;
}