module com.example.dp {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;

    opens com.example.dp to javafx.fxml;

    opens com.example.dp.controller to javafx.fxml;
    opens com.example.dp.model to javafx.base;

    exports com.example.dp;
}