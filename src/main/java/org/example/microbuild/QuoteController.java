package org.example.microbuild;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class QuoteController {

    @FXML private TextArea quoteTextArea;

    public void setQuoteText(String quote) {
        if (quoteTextArea != null) {
            quoteTextArea.setText(quote);
        }
    }

    @FXML
    private void handleBackToHome(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}