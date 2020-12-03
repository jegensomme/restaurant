package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;

import java.util.Set;

public abstract class AuthorisedPaneController extends AbstractPaneController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField roleTextField;

    protected User user;

    protected void init(User user) {
        this.user = user;
        initNameField(user.getName());
        initRoleField(user.getRoles());
    }

    private void initRoleField(Set<Role> roles) {
        roleTextField.clear();
        roles.forEach(role -> {
            String text = roleTextField.getText();
            roleTextField.setText(text + (text.isEmpty() ? "" : ", ") + role.toStringRus());
        });
    }

    private void initNameField(String name) {
        nameTextField.setText(name);
    }
}
