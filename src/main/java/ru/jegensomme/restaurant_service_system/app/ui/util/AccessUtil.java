package ru.jegensomme.restaurant_service_system.app.ui.util;

import javafx.scene.control.Alert;
import ru.jegensomme.restaurant_service_system.app.ui.util.modal.AlertUtil;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;

public class AccessUtil {

    public static void performIfAccess(ThrowablePerformer<AccessException> performer, Performer catchPerformer) {
        try {
            performer.perform();
        } catch (AccessException e) {
            catchPerformer.perform();
        }
    }

    public interface ThrowablePerformer <E extends Exception> {
        void perform() throws E;
    }

    public interface Performer {
        void perform();
    }
}
