package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.controller.OrderController;
import ru.jegensomme.restaurant_service_system.app.controller.UserController;
import ru.jegensomme.restaurant_service_system.app.controller.UserShiftController;
import ru.jegensomme.restaurant_service_system.app.ui.panecontroller.view.OrderView;
import ru.jegensomme.restaurant_service_system.app.ui.panecontroller.view.UserShiftView;
import ru.jegensomme.restaurant_service_system.app.ui.util.AccessUtil;
import ru.jegensomme.restaurant_service_system.app.ui.util.EventUtil;
import ru.jegensomme.restaurant_service_system.app.ui.util.modal.AlertUtil;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.app.util.to.UserTo;
import ru.jegensomme.restaurant_service_system.app.util.to.util.UserUtil;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HomePaneController extends AuthorisedPaneController {

    @Autowired
    private UserShiftController userShiftController;

    @Autowired
    private OrderController orderController;

    @Autowired
    private UserController userController;

    @Autowired
    private OrdersPaneController ordersPaneController;

    @FXML
    private Button endShiftButton;

    @FXML
    private Button startShiftButton;

    @FXML
    private ComboBox<FilterUnit> filterBox;

    @FXML
    private TextField totalSalesField;

    @FXML
    private CheckBox customFilterCheckBox;

    private boolean customFilter;

    @FXML
    private HBox customFilterBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private LocalDate startDateFilter;

    private LocalDate endDateFilter;

    private UserShift currentShift;

    private enum FilterUnit {
        CURRENT_DAY {
            @Override
            public String toString() {
                return "За текущий день";
            }
        },
        WEEK {
            @Override
            public String toString() {
                return "За неделю";
            }
        },
        MONTH {
            @Override
            public String toString() {
                return "За месяц";
            }
        },
        YEAR {
            @Override
            public String toString() {
                return "За год";
            }
        }
    }

    @FXML
    private TableView<OrderView> orderInfoTable;

    @FXML
    private TableColumn<OrderView, LocalDateTime> orderIdColumn;

    @FXML
    private TableColumn<OrderView, LocalDateTime> orderDateTimeColumn;

    @FXML
    private TableColumn<OrderView, Integer> orderTableColumn;

    @FXML
    private TableColumn<OrderView, Integer> orderCostColumn;

    @FXML
    private TableColumn<OrderView, Integer> orderStatusColumn;

    @FXML
    private TableView<UserShiftView> shiftInfoTable;

    @FXML
    private TableColumn<UserShiftView, Integer> shiftIdColumn;

    @FXML
    private TableColumn<UserShiftView, LocalDate> shiftDateColumn;

    @FXML
    private TableColumn<UserShiftView, LocalTime> shiftStartTimeColumn;

    @FXML
    private TableColumn<UserShiftView, LocalTime> shiftEndTimeColumn;

    @FXML
    private TableColumn<UserShiftView, Integer> shiftTotalColumn;

    @FXML
    private BorderPane waitersPane;

    @FXML
    private ListView<UserTo> waitersListView;

    private UserTo selectedUser;

    @Override
    public void init(User user) {
        super.init(user);
        initFilterBox();
        initShiftInfoTable();
        initOrderInfoTable();
        customFilter = false;
        initWaitersListView();
        updateCurrentShift();
        updateShiftInfoTable();
    }

    private void initWaitersListView() {
        if (SecurityUtil.authUserRole() == Role.MANAGER) {
            waitersPane.setDisable(false);
            List<UserTo> userTos = UserUtil.getTos(userController.getAllByRole(Role.WAITER));
            waitersListView.setItems(FXCollections.observableList(userTos));
            selectedUser = null;
        } else {
            selectedUser = UserUtil.createTo(user);
            waitersListView.setItems(FXCollections.observableArrayList(selectedUser));
        }
    }

    private void initFilterBox() {
        filterBox.getItems().setAll(List.of(
                FilterUnit.CURRENT_DAY,
                FilterUnit.WEEK,
                FilterUnit.MONTH,
                FilterUnit.YEAR
        ));
        filterBox.setValue(FilterUnit.CURRENT_DAY);
    }

    private void initShiftInfoTable() {
        shiftIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        shiftDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        shiftStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        shiftEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        shiftTotalColumn.setCellValueFactory(new PropertyValueFactory<>("sales"));
    }

    private void initOrderInfoTable() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        orderTableColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        orderCostColumn.setCellValueFactory(new PropertyValueFactory<>("checkAmount"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void onAcceptFilter() {
        setFilter();
    }

    private void setFilter() {
        if (customFilter) {
            startDateFilter = startDatePicker.getValue() == null ? DateTimeUtil.MIN : startDatePicker.getValue();
            endDateFilter = endDatePicker.getValue() == null ? DateTimeUtil.MAX : endDatePicker.getValue();
            if (startDateFilter.isAfter(endDateFilter)) {
                AlertUtil.inform("Не корректно выбраны начало и конец даты!", Alert.AlertType.WARNING);
                startDateFilter = null;
                endDateFilter = null;
                return;
            }
        }
        updateShiftInfoTable();
    }

    @FXML
    private void waitersOnClick(MouseEvent mouseEvent) {
        if (!EventUtil.MouseEventUtil.isDoubleClick(mouseEvent)) {
            return;
        }
        selectedUser = waitersListView.getSelectionModel().getSelectedItem();
        setFilter();
    }

    @FXML
    private void onForAllClick() {
        selectedUser = null;
        setFilter();
    }

    @FXML
    private void onCustomFilterCheckBox() {
        customFilter = customFilterCheckBox.isSelected();
        filterBox.setDisable(customFilter);
        customFilterBox.setDisable(!customFilter);
    }

    @FXML
    private void onShiftInfoClicked(MouseEvent mouseEvent) {
        if (EventUtil.MouseEventUtil.isDoubleClick(mouseEvent)) {
            updateOrderInfoTable();
        }
    }

    private void updateShiftInfoTable() {
        AccessUtil.performIfAccess(() -> {
            List<UserShiftView> shiftViews = new ArrayList<>();
            List<UserShift> shifts;
            double totalSales = 0;
            if (!customFilter) {
                LocalDate now = LocalDate.now();
                endDateFilter = DateTimeUtil.MAX;
                switch (filterBox.getValue()) {
                    case WEEK -> startDateFilter = now.minusDays(now.getDayOfWeek().getValue() - 1);
                    case MONTH -> startDateFilter = LocalDate.of(now.getYear(), now.getMonth(), 1);
                    case YEAR -> startDateFilter = LocalDate.of(now.getYear(), Month.JANUARY, 1);
                    default -> startDateFilter = LocalDate.now();
                }
            }
            shifts = selectedUser == null ?
                    userShiftController.getBetweenInclusive(startDateFilter, endDateFilter) :
                    userShiftController.getBetweenInclusiveByUser(selectedUser.getId(), startDateFilter, endDateFilter);
            for (UserShift shift : shifts) {
                Float sales = userShiftController.getTotalSalesByUserShift(shift.id(), shift.getUser().id());
                shiftViews.add(new UserShiftView(shift.id(), shift.getDate(),
                        shift.getStartTime(), shift.getEndTime(), sales));
                totalSales += sales;
            }
            shiftInfoTable.setItems(FXCollections.observableList(shiftViews));
            totalSalesField.setText(Double.toString(totalSales));
            orderInfoTable.getItems().clear();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    private void updateOrderInfoTable() {
        UserShiftView selected = shiftInfoTable.getSelectionModel().getSelectedItem();
        List<Order> orders = orderController.getAllByUserShift(selected.getId());
        orderInfoTable.setItems(FXCollections.observableList(orders.stream().
                map(order -> new OrderView(order.id(), order.getDateTime(),
                        order.getTable().getNumber(), order.getCheckAmount(), order.getStatus().toStringRus())).
                collect(Collectors.toList())));
    }

    private void updateCurrentShift() {
        AccessUtil.performIfAccess(() -> {
            List<UserShift> openedShifts = userShiftController.getOpenedByUser(SecurityUtil.authUserId());
            currentShift = openedShifts.isEmpty() ? null : openedShifts.get(0);
            startShiftButton.setDisable(currentShift != null);
            endShiftButton.setDisable(currentShift == null);
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    @FXML
    private void onStartShiftClick() {
        startShift();
    }

    private void startShift() {
        try {
            LocalDateTime startDateTime = LocalDateTime.now();
            if (userShiftController.create(new UserShift(null, startDateTime.toLocalDate(),
                    startDateTime.toLocalTime(), null)) == null) {
                AlertUtil.inform("Не удалось открыть смену!", Alert.AlertType.ERROR);
                return;
            }
            updateCurrentShift();
            AlertUtil.inform("Личная смена открыта: " + DateTimeUtil.toString(startDateTime),
                    Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onEndShiftClick() {
        endShift();
    }

    private void endShift() {
        AccessUtil.performIfAccess(() -> {
            LocalDateTime endDateTime = LocalDateTime.now();
            currentShift.setEndTime(endDateTime.toLocalTime());
            userShiftController.update(currentShift, SecurityUtil.authUserId());
            AlertUtil.inform("Личная смена закрыта: " + DateTimeUtil.toString(endDateTime),
                    Alert.AlertType.INFORMATION);
            updateCurrentShift();
            }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    @FXML
    private void onOpenOrdersClick() {
        openOrdersPane();
    }

    private void openOrdersPane() {
        if (currentShift == null) {
            AlertUtil.inform("Личная смена не открыта!", Alert.AlertType.WARNING);
            return;
        }
        try {
            stageLoader.setPane("orders");
            ordersPaneController.init(user);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLockClick() {
        lock();
    }

    private void lock() {
        try {
            stageLoader.setPane("auth");
        } catch (IOException e) {
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }
}
