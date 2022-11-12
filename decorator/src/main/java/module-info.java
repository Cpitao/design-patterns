module agh.dp.decorator {
    requires javafx.controls;
    requires javafx.fxml;


    opens agh.dp.decorator to javafx.fxml;
    exports agh.dp.decorator;
}