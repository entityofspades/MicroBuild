module org.example.microbuild {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.microbuild to javafx.fxml;
    exports org.example.microbuild;
}