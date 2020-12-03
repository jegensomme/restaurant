package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.controller.UserController;
import ru.jegensomme.restaurant_service_system.app.ui.util.modal.AlertUtil;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;

import java.io.IOException;

@Component
public class AuthPaneController extends AbstractPaneController {

    @Autowired
    private UserController userController;

    @Autowired
    private HomePaneController homePaneController;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    private PasswordField passwordField;

    @FXML
    public void onPressKeyBoard(ActionEvent event) {
        String pressed = ((Button) event.getSource()).getText();
        if ("x".equals(pressed)) {
            passwordField.setText(passwordField.getText().
                            substring(0, passwordField.getText().length() - 1));
        } else {
            passwordField.setText(passwordField.getText() + ((Button) event.getSource()).getText());
        }
    }

    @FXML
    public void onAuth() {
        String key = passwordField.getText();
        try {
            User user = userController.getByKey(key);
            if (user == null) {
                AlertUtil.inform("Пользователь с таким паролем не найдет!", Alert.AlertType.WARNING);
                return;
            }
            SecurityUtil.setAuthUserId(user.getId());
            if (user.getRoles().contains(Role.MANAGER) && user.getRoles().contains(Role.WAITER)) {
                SecurityUtil.setAuthUserRole(AlertUtil.confirm("Зайти как менеджер?") ?
                        Role.MANAGER : Role.WAITER);
            } else {
                SecurityUtil.setAuthUserRole(user.getRoles().contains(Role.MANAGER) ? Role.MANAGER : Role.WAITER);
            }
            userController.authUser();
            logger.info("authorised {}", user.getId());
            stageLoader.setPane("home");
            homePaneController.init(user);
        } catch (IllegalArgumentException e) {
            AlertUtil.inform("Введен некорректный пароль", Alert.AlertType.WARNING);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onTurnOff() {
        Platform.exit();
    }
}
