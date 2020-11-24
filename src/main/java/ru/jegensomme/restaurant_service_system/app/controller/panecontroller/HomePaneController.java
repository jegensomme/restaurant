package ru.jegensomme.restaurant_service_system.app.controller.panecontroller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.controller.UserShiftController;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.app.util.SpringStageLoader;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.to.UserShiftTo;
import ru.jegensomme.restaurant_service_system.util.UserShiftUtil;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
public class HomePaneController extends AbstractPaneController {

    private UserShiftController userShiftController;

    private SpringStageLoader stageLoader;

    private User user;

    private UserShift currentShift = null;

    private List<UserShift> userShifts;

    @Autowired
    public HomePaneController(UserShiftController userShiftController, SpringStageLoader stageLoader) {
        this.userShiftController = userShiftController;
        this.stageLoader = stageLoader;
    }

    void init(User user) {
        this.user = user;
        nameTextField.setText(user.getName());
        initRoleField(user.getRoles());
        initShiftList();
        initCurrentShift(userShifts);
    }

    @FXML
    private ListView<UserShiftTo> shiftListView;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField roleTextField;

    @FXML
    private Button startShiftButton;

    @FXML
    private Button endShiftButton;

    private void initRoleField(Set<Role> roles) {
        roles.forEach(role -> {
            String text = roleTextField.getText();
            roleTextField.setText(text + (text.isEmpty() ? "" : ", ") + role.toStringRus());
        });
    }

    private void initShiftList() {
        try {
            userShifts = userShiftController.getAllByUser(SecurityUtil.authUserId());
            List<UserShiftTo> userShiftTos = UserShiftUtil.getTos(userShifts);
            shiftListView.setItems(FXCollections.observableList(userShiftTos));
        } catch (AccessException e) {
            //обработать
        }
    }

    private void initCurrentShift(List<UserShift> userShifts) {
        UserShift last = userShifts.get(userShifts.size() - 1);
        if (last.getEndDateTime() == null) {
            currentShift = last;
            setButtonEnabling();
        } else {
            currentShift = null;
        }
    }


    @FXML
    public void startShift(ActionEvent event) {
        try {
            currentShift = new UserShift(null, LocalDateTime.now(), null);
            userShiftController.create(currentShift);
            initShiftList();
            setButtonEnabling();
        } catch (IllegalArgumentException e) {

        } catch (NotFoundException e) {
            //обработать
        }
    }

    @FXML
    public void endShift(ActionEvent event) {
        try {
            currentShift.setEndDateTime(LocalDateTime.now());
            userShiftController.update(currentShift, SecurityUtil.authUserId());
            initShiftList();
            setButtonEnabling();
        } catch (IllegalArgumentException e) {

        } catch (NotFoundException e) {

        }
    }

    @FXML
    public void toOrders(ActionEvent event) {
        try {
            stageLoader.setScene("orders");
        } catch (IOException e) {

        }
    }

    @FXML
    public void lock(ActionEvent event) {
        try {
            stageLoader.setScene("auth");
        } catch (IOException e) {

        }
    }

    private void setButtonEnabling() {
        startShiftButton.setDisable(!startShiftButton.isDisabled());
        endShiftButton.setDisable(!endShiftButton.isDisabled());
    }
}
