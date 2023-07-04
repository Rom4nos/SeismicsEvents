module com.example.databasetest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.databasetest to javafx.fxml;
    exports com.example.databasetest;
}