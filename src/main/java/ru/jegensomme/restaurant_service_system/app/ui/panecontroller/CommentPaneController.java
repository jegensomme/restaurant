package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class CommentPaneController {

    private Stage stage;

    @FXML
    private TextArea commentTextArea;

    private String comment;

    public void init(Stage stage) {
        this.stage = stage;
        commentTextArea.clear();
        comment = null;
    }

    @FXML
    private void accept() {
        comment = commentTextArea.getText();
        stage.close();
    }

    @FXML
    private void cancel() {
        comment = null;
        stage.close();
    }

    public String getComment() {
        return comment;
    }
}
