package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.ui.util.modal.AlertUtil;
import ru.jegensomme.restaurant_service_system.util.StringUtil;

@Component
public class DiscountPaneController {

    private Stage stage;

    @FXML
    private TextField discountField;

    private Integer discount;

    public void init (Stage stage) {
        this.stage = stage;
        discount = null;
    }

    @FXML
    private void accept() {
        if (!StringUtil.isNumber(discountField.getText())) {
            AlertUtil.inform("Некорректный ввод!", Alert.AlertType.WARNING);
            return;
        }
        int number = Integer.parseInt(discountField.getText());
        if (!(number >= 0 && number <= 100)) {
            AlertUtil.inform("Требуется ввести число от 0 до 100!", Alert.AlertType.WARNING);
            return;
        }
        discount = number;
        stage.close();
    }

    @FXML
    private void cancel() {
        discount = null;
        stage.close();
    }

    public Integer getDiscount() {
        return discount;
    }
}
