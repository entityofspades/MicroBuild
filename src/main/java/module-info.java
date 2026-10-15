module org.example.microbuild {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.microbuild to javafx.fxml;
    exports org.example.microbuild;
}