package ru.jegensomme.restaurant_service_system.app.util;

import javafx.scene.control.Alert;

public class DialogUtil {

    public static void informError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.show();
    }
}
