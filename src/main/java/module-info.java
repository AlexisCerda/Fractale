module figure {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens figure to javafx.fxml;
    exports figure;
    exports figure.Controler;
    opens figure.Controler to javafx.fxml;
    exports figure.Fractales;
    opens figure.Fractales to javafx.fxml;
}