module com.learn.clocks {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.learn.clocks to javafx.fxml;
    exports com.learn.clocks;
}