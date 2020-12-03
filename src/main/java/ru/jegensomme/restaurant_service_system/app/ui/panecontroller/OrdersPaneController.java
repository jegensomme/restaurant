package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.controller.OrderController;
import ru.jegensomme.restaurant_service_system.app.controller.TableController;
import ru.jegensomme.restaurant_service_system.app.ui.util.AccessUtil;
import ru.jegensomme.restaurant_service_system.app.ui.util.wraper.ContentButtonGridPane;
import ru.jegensomme.restaurant_service_system.app.ui.util.wraper.ContentButton;
import ru.jegensomme.restaurant_service_system.app.controller.UserController;
import ru.jegensomme.restaurant_service_system.app.ui.util.modal.AlertUtil;
import ru.jegensomme.restaurant_service_system.app.ui.util.EventUtil;
import ru.jegensomme.restaurant_service_system.model.*;
import ru.jegensomme.restaurant_service_system.app.util.to.OrderTo;
import ru.jegensomme.restaurant_service_system.app.util.to.TableTo;
import ru.jegensomme.restaurant_service_system.app.util.to.UserTo;
import ru.jegensomme.restaurant_service_system.app.util.to.util.OrderUtil;
import ru.jegensomme.restaurant_service_system.app.util.to.util.UserUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Component
public class OrdersPaneController extends AuthorisedPaneController {

    @Autowired
    private OrderController orderController;

    @Autowired
    private UserController userController;

    @Autowired
    private TableController tableController;

    @Autowired
    private HomePaneController homePaneController;

    @Autowired
    private OrderEditPaneController orderEditPaneController;

    @Autowired
    private TablesPaneController tablesPaneController;

    private OrderButton.Action orderButtonAction;

    private UserTo selectedUserTo;

    @FXML
    private ListView<UserTo> waiterListView;

    @FXML
    private HBox orderControlBox;

    @FXML
    private Button cancelButton;

    @FXML
    private GridPane orderGridPane;

    private OrderButtonGridPane orderButtonGridPane;

    private final Consumer<OrderTo> orderActionPerformer = new Consumer<>() {

        @Override
        public void accept(OrderTo orderTo) {
            switch (orderButtonAction) {
                case OPEN -> {
                    if (orderTo == null) {
                        createOrder();
                    } else {
                        Order order = orderController.get(orderTo.getId());
                        if (order == null) {
                            AlertUtil.inform("Заказ не найден!", Alert.AlertType.ERROR);
                            return;
                        }
                        openOrder(order);
                    }
                }
                case DELETE -> {
                    if (!AlertUtil.confirm("Подтвердите удаление заказа")) {
                        return;
                    }
                    deleteOrder(orderTo);
                }
                case MOVE -> moveOrder(orderTo);
                case CLOSE -> {
                    if (!AlertUtil.confirm("Подтвердите закрытие заказа")) {
                        return;
                    }
                    closeOrder(orderTo);
                }
            }
        }
    };

    @Override
    public void init(User user) {
        super.init(user);
        selectedUserTo = null;
        orderButtonGridPane = new OrderButtonGridPane(orderGridPane, new OrderButton.OrderButtonFactory(), 118);
        updateWaiterListView();
        updateOrderGridPane();
        orderButtonAction = OrderButton.Action.OPEN;
    }

    private void updateWaiterListView() {
        List<User> users = userController.getAllByShiftDate(LocalDate.now());
        waiterListView.setItems(FXCollections.observableList(
                UserUtil.filterByPredicate(users, user -> user.getRoles().contains(Role.WAITER))));
    }

    @FXML
    private void onWaiterListClicked(MouseEvent mouseEvent) {
        if (!EventUtil.MouseEventUtil.isDoubleClick(mouseEvent)) {
            return;
        }
        selectedUserTo = waiterListView.getSelectionModel().getSelectedItems().get(0);
        updateOrderGridPane();
    }

    @FXML
    private void onCancelClick() {
        cancelAction();
    }

    private void cancelAction() {
        cancelButton.setDisable(true);
        orderControlBox.setDisable(false);
        orderButtonAction = OrderButton.Action.OPEN;
        orderButtonGridPane.notifyButtons();
    }

    @FXML
    private void onDeleteOrderClick() {
        cancelButton.setDisable(false);
        orderControlBox.setDisable(true);
        orderButtonAction = OrderButton.Action.DELETE;
        orderButtonGridPane.notifyButtons();
    }

    @FXML
    private void onMoveOrderClick() {
        cancelButton.setDisable(false);
        orderControlBox.setDisable(true);
        orderButtonAction = OrderButton.Action.MOVE;
        orderButtonGridPane.notifyButtons();
    }

    @FXML
    private void onCloseOrderClick() {
        cancelButton.setDisable(false);
        orderControlBox.setDisable(true);
        orderButtonAction = OrderButton.Action.CLOSE;
        orderButtonGridPane.notifyButtons();
    }

    @FXML
    private void onShowAllClick() {
        selectedUserTo = null;
        updateOrderGridPane();
    }

    @FXML
    private void onOpenHomeClick() {
        try {
            stageLoader.setPane("home");
            homePaneController.init(user);
        } catch (IOException e) {
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLockClick() {
        try {
            stageLoader.setPane("auth");
        } catch (IOException e) {
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }

    private void createOrder() {
        Table table = selectTable();
        if (table == null) {
            return;
        }
        Order order = new Order();
        order.setTable(table);
        openOrder(order);
    }

    private void openOrder(Order order) {
        try {
            stageLoader.setPane("order-edit");
            orderEditPaneController.init(user, order);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
    }

    private void deleteOrder(OrderTo orderTo) {
        cancelAction();
        AccessUtil.performIfAccess(() -> {
            orderController.delete(orderTo.getId());
            updateOrderGridPane();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    private void moveOrder(OrderTo orderTo) {
        cancelAction();
        orderButtonAction = OrderButton.Action.OPEN;
        Table table = selectTable();
        if (table == null) {
            return;
        }
        Order order = orderController.get(orderTo.getId());
        if (order == null) {
            AlertUtil.inform("Заказ не найден!", Alert.AlertType.WARNING);
            return;
        }
        order.setTable(table);
        AccessUtil.performIfAccess(() -> {
            orderController.update(order, order.getUser().id());
            updateOrderGridPane();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    private void closeOrder(OrderTo orderTo) {
        cancelAction();
        Order order = orderController.get(orderTo.getId());
        if (order == null) {
            AlertUtil.inform("Заказ не найден!", Alert.AlertType.WARNING);
            return;
        }
        order.setStatus(OrderStatus.CLOSED);
        AccessUtil.performIfAccess(() -> {
            orderController.update(order, order.getUser().id());
            updateOrderGridPane();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    private Table selectTable() {
        Table table = null;
        try {
            Stage tablesStage = stageLoader.loadModalStage("tables", "Выбор стола");
            tablesPaneController.init(tablesStage);
            tablesStage.showAndWait();
            TableTo selectedTableTo = tablesPaneController.getSelected();
            if (selectedTableTo== null) {
                return null;
            }
            table = tableController.get(selectedTableTo.getId());
            if (table == null) {
                AlertUtil.inform("Стол не найден!", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
        return table;
    }

    private class OrderButtonGridPane extends ContentButtonGridPane<OrderButton, OrderTo> {

        OrderButtonGridPane(GridPane orderGridPane, OrderButton.OrderButtonFactory orderButtonFactory, int rowPrefHeight) {
            super(orderGridPane, orderButtonFactory, rowPrefHeight);
        }

        @Override
        public void fillGridPane(List<OrderTo> orderTos, Consumer<OrderTo> performer) {
            super.fillGridPane(orderTos, performer);
            add(((OrderButton.OrderButtonFactory)contentButtonFactory).
                    createEmpty(performer));
        }

        void notifyButtons() {
            contentButtons.forEach(contentButton -> (contentButton).onAction(orderButtonAction));
        }
    }

    private void updateOrderGridPane() {
        List<Order> orders = selectedUserTo == null ?
                orderController.getAllOpened() : orderController.getAllOpenedByUser(selectedUserTo.getId());
        List<OrderTo> orderTos = OrderUtil.getTos(orders);
        orderButtonGridPane.fillGridPane(orderTos, orderActionPerformer);
    }

    private static class OrderButton extends ContentButton<OrderTo> {

        final String style;

        private OrderButton(Button button, OrderTo orderTo) {
            super(button, orderTo);
            this.style = button.getStyle();
        }

        void onAction(Action action) {
            if (action == Action.OPEN) {
                button.setStyle(style);
                button.setDisable(false);
                return;
            }
            if (object == null) {
                button.setDisable(true);
                return;
            }
            button.setStyle(action == Action.DELETE ? DELETE_ORDER_BUTTON_STYLE :
                    action == Action.MOVE ? MOVE_ORDER_BUTTON_STYLE : CLOSE_ORDER_BUTTON_STYLE);
        }

        static final String PROCESSING_ORDER_BUTTON_STYLE =
                "-fx-background-color: #9ACD32;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static final String ON_BILL_ORDER_BUTTON_STYLE =
                "-fx-background-color: #B22222;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static final String NEW_ORDER_BUTTON_STYLE =
                "-fx-background-color: #66CDAA;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static final String DELETE_ORDER_BUTTON_STYLE =
                "-fx-background-color: #F08080;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static final String MOVE_ORDER_BUTTON_STYLE =
                "-fx-background-color: #90EE90;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static final String CLOSE_ORDER_BUTTON_STYLE =
                "-fx-background-color: #FFA500;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        enum Action {
            OPEN,
            DELETE,
            MOVE,
            CLOSE
        }

        static class OrderButtonFactory extends ContentButton.ContentButtonFactory<OrderButton, OrderTo> {

            @Override
            public OrderButton create(OrderTo orderTo, Consumer<OrderTo> performer) {
                Button button = new Button("Заказ\nСтол " + orderTo.getTable().getNumber());
                button.setOnAction(event -> performer.accept(orderTo));
                button.setStyle(orderTo.getStatus() == OrderStatus.PROCESSING ?
                        PROCESSING_ORDER_BUTTON_STYLE : ON_BILL_ORDER_BUTTON_STYLE);
                setButtonDefaultProperties(button);
                return new OrderButton(button, orderTo);
            }

            OrderButton createEmpty(Consumer<OrderTo> performer) {
                Button button = new Button("Новый заказ");
                button.setStyle(NEW_ORDER_BUTTON_STYLE);
                setButtonDefaultProperties(button);
                button.setOnAction(event -> performer.accept(null));
                return new OrderButton(button, null);
            }
        }

    }

}
