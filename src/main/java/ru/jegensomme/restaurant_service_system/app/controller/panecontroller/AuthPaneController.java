package ru.jegensomme.restaurant_service_system.app.controller.panecontroller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.controller.user.UserController;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.app.util.SpringStageLoader;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.io.IOException;

@Component
public class AuthPaneController extends AbstractPaneController {

    private UserController userController;

    private SpringStageLoader stageLoader;

    @Autowired
    private HomePaneController homePaneController;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public AuthPaneController(UserController userController, SpringStageLoader stageLoader) {
        this.userController = userController;
        this.stageLoader = stageLoader;
    }

    @FXML
    private PasswordField passwordField;

    @FXML
    public void pressButton(ActionEvent event) {
        String pressed = ((Button) event.getSource()).getText();
        if ("x".equals(pressed)) {
            passwordField.setText(passwordField.getText().
                            substring(0, passwordField.getText().length() - 1));
        } else {
            passwordField.setText(passwordField.getText() + ((Button) event.getSource()).getText());
        }
    }

    @FXML
    public void enter(ActionEvent event) {
        String key = passwordField.getText();
        try {
            User user = userController.getByKey(key);
            SecurityUtil.setAuthUserId(user.getId());
            SecurityUtil.setAuthUserRoles(user.getRoles());
            logger.info("authorised {}", user.getId());
            stageLoader.setScene("home");
            homePaneController.init(user);
        } catch (IllegalArgumentException e) {

        } catch (NotFoundException e) {

        } catch (IOException e) {

        }
    }

    @FXML
    public void turnOff(ActionEvent event) {
        Platform.exit();
    }
}
