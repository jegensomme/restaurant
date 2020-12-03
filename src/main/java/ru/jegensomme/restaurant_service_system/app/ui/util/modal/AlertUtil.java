package ru.jegensomme.restaurant_service_system.app.ui.util.modal;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {

    private AlertUtil() {
    }

    public static void inform(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, msg);
        alert.setTitle("Внимание");
        switch (alertType) {
            case INFORMATION -> inform(alert);
            case ERROR -> informError(alert);
            case WARNING -> informWarning(alert);
        }
    }

    public static boolean confirm(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Требуется подтвердить действие");
        alert.setContentText(msg);
        Optional<ButtonType> optional = alert.showAndWait();
        return optional.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }

    private static void inform(Alert alert) {
        alert.setHeaderText("Информация");
        alert.show();
    }
    private static void informError(Alert alert) {
        alert.setHeaderText("Ошибка");
        alert.show();
    }

    private static void informWarning(Alert alert) {
        alert.setHeaderText("Предупреждение");
        alert.show();
    }
}
