module client.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens client.client to javafx.fxml;
    exports client.client;
    exports client.client.Controller;
    opens client.client.Controller to javafx.fxml;
    exports client.client.Backend;
    opens client.client.Backend to javafx.fxml;

    //opens client.client.Controller to javafx.fxml;
}