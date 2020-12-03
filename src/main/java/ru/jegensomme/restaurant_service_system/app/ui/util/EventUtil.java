package ru.jegensomme.restaurant_service_system.app.ui.util;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EventUtil {

    public static class MouseEventUtil {
        private MouseEventUtil() {
        }
        public static boolean isDoubleClick(MouseEvent mouseEvent) {
            return mouseEvent.getButton().equals(MouseButton.PRIMARY)
                    && mouseEvent.getClickCount() == 2;
        }
    }

    private EventUtil() {
    }
}
