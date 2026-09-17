module com.clase.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.clase.blackjack to javafx.fxml;
    exports com.clase.blackjack;
}