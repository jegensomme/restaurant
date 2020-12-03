package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.controller.DishCategoryController;
import ru.jegensomme.restaurant_service_system.app.controller.DishController;
import ru.jegensomme.restaurant_service_system.app.controller.OrderController;
import ru.jegensomme.restaurant_service_system.app.controller.OrderDishController;
import ru.jegensomme.restaurant_service_system.app.ui.util.AccessUtil;
import ru.jegensomme.restaurant_service_system.app.ui.util.modal.AlertUtil;
import ru.jegensomme.restaurant_service_system.app.ui.util.wraper.ContentButtonGridPane;
import ru.jegensomme.restaurant_service_system.app.ui.util.wraper.ContentButton;
import ru.jegensomme.restaurant_service_system.app.util.to.util.OrderUtil;
import ru.jegensomme.restaurant_service_system.model.*;
import ru.jegensomme.restaurant_service_system.app.util.to.DishTo;
import ru.jegensomme.restaurant_service_system.app.util.to.OrderDishTo;
import ru.jegensomme.restaurant_service_system.app.util.to.util.DishUtil;
import ru.jegensomme.restaurant_service_system.app.util.to.util.OrderDishUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class OrderEditPaneController extends AuthorisedPaneController {

    @Autowired
    private DishCategoryController dishCategoryController;

    @Autowired
    private OrderController orderController;

    @Autowired
    private DishController dishController;

    @Autowired
    private OrderDishController orderDishController;

    @Autowired
    private OrdersPaneController ordersPaneController;

    @Autowired
    private CommentPaneController commentPaneController;

    @Autowired
    private DiscountPaneController discountPaneController;

    private Order order;

    @FXML
    private BorderPane orderDishesPane;

    @FXML
    private BorderPane controlPane;

    @FXML
    private ListView<OrderDishTo> orderDishesListView;

    @FXML
    private Button setDiscountButton;

    @FXML
    private TextField discountField;

    @FXML
    private TextField checkAmountField;

    @FXML
    private Button billButton;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private Button backMenuButton;

    private DishCategory currentDishCategory;

    private ContentButtonGridPane<DishButton, DishTo> buttonMenuGridPane;

    private final Consumer<DishTo> menuActionPerformer = new Consumer<>() {
        @Override
        public void accept(DishTo dishTo) {
            if (dishTo.isCategory()) {
                backMenuButton.setDisable(false);
                currentDishCategory = dishCategoryController.get(dishTo.getId());
                updateMenuGridPane();
            } else {
                Dish dish = dishController.get(dishTo.getId());
                if (dish == null) {
                    AlertUtil.inform("Блюдо не найдено!", Alert.AlertType.ERROR);
                    return;
                }
                double dishCost = OrderUtil.getCostWithDiscount(dish.getCost(), order.getDiscount());
                orderDishesListView.getItems().add(new OrderDishTo(null, dish, 1, null, dishCost));
                order.setCheckAmount(order.getCheckAmount() + dishCost);
                checkAmountField.setText(Double.toString(order.getCheckAmount()));
                if (orderDishesListView.getItems().size() == 1) {
                    orderDishesPane.setDisable(false);
                }
            }
        }
    };

    public void init(User user, Order order) {
        this.order = order;
        init(user);
    }

    @Override
    protected void init(User user) {
        super.init(user);
        currentDishCategory = null;
        if (order.isNew()) {
            billButton.setDisable(true);
            setDiscountButton.setDisable(true);
            setDiscountButton.setDisable(true);
        }
        if (order.getStatus() == OrderStatus.ON_BILL){
            orderDishesPane.setDisable(true);
            controlPane.setDisable(true);
            setDiscountButton.setDisable(true);
        }
        checkAmountField.setText(order.isNew() ? "0" : Double.toString(order.getCheckAmount()));
        discountField.setText(order.isNew() ? "0" : Integer.toString(order.getDiscount()));
        buttonMenuGridPane = new ContentButtonGridPane<>(menuGridPane, new DishButton.DishButtonFactory(), 178);
        updateMenuGridPane();
        updateOrderDishesListView();
    }

    private void updateOrderDishesListView() {
        @SuppressWarnings("unchecked")
        List<OrderDish> orderDishes = order.isNew() ?
                Collections.EMPTY_LIST : orderDishController.getAll(order.id());
        orderDishesListView.setItems(FXCollections.observableList(OrderDishUtil.getTos(orderDishes)));
        if (orderDishesListView.getItems().isEmpty()) {
            orderDishesPane.setDisable(true);
        }
    }

    private OrderDishTo getSelectedDish() {
        ObservableList<OrderDishTo> selectedItems = orderDishesListView.getSelectionModel().getSelectedItems();
        return selectedItems.isEmpty() ? null : selectedItems.get(0);
    }

    @FXML void onCommentClick() {
        addComment();
    }

    private void addComment() {
        OrderDishTo selected = getSelectedDish();
        if (selected == null) {
            return;
        }
        if (selected.getId() != null) {
            AlertUtil.inform("Блюдо уже сохранено!", Alert.AlertType.WARNING);
            return;
        }
        String comment = null;
        try {
            Stage commentStage = stageLoader.loadModalStage("comment", "Комментарий к блюду");
            commentPaneController.init(commentStage);
            commentStage.showAndWait();
            comment = commentPaneController.getComment();
        } catch (IOException e) {
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
        if (comment != null) {
            selected.setComment(comment);
            orderDishesListView.refresh();
        }
    }

    @FXML
    void onPlusClick() {
        changeAmount(1);
    }

    @FXML
    void onMinusClick() {
        changeAmount(-1);
    }

    private void changeAmount(int amount) {
        OrderDishTo selected = getSelectedDish();
        if (selected == null) {
            return;
        }
        if (selected.getId() != null) {
            AlertUtil.inform("Блюдо уже сохранено!", Alert.AlertType.WARNING);
            return;
        }
        int result = selected.getAmount() + amount;
        selected.setAmount(result >= 1 ? result : selected.getAmount());
        selected.setCost(OrderUtil.getCostWithDiscount(
                selected.getAmount()*selected.getDish().getCost(), order.getDiscount()));
        orderDishesListView.refresh();
        updateOrderCheckAmount();
    }

    @FXML
    void onDeleteClick() {
        deleteDish();
    }

    private void deleteDish() {
        OrderDishTo selected = getSelectedDish();
        if (selected == null) {
            return;
        }
        if (selected.getId() == null) {
            orderDishesListView.getItems().remove(selected);
            if (orderDishesListView.getItems().isEmpty()) {
                orderDishesPane.setDisable(true);
            }
            order.setCheckAmount(order.getCheckAmount() - selected.getCost());
            orderDishesListView.refresh();
            updateOrderCheckAmount();
            return;
        }
        if (!AlertUtil.confirm("Подтвердите удаление блюда")) {
            return;
        }
        AccessUtil.performIfAccess(() -> {
            orderDishController.delete(selected.getId());
            updateOrderDishesListView();
            updateOrderCheckAmount();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    @FXML
    private void onPrintClick() {
        if (order.isNew()) {
            createOrder();
        } else {
            updateOrder();
        }
    }

    private void createOrder() {
        order.setStatus(OrderStatus.PROCESSING);
        order.setDateTime(LocalDateTime.now().withNano(0));
        order.setDiscount(0);
        order.setDishes(orderDishesListView.getItems().stream()
                .map(orderDishTo -> new OrderDish(null, order, orderDishTo.getDish(),
                    orderDishTo.getAmount(), orderDishTo.getComment())).collect(Collectors.toList()));
        AccessUtil.performIfAccess(() -> {
            if (orderController.create(order) == null) {
                AlertUtil.inform("Не удалось создать заказ!", Alert.AlertType.WARNING);
            }
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));

        openOrdersPane();
    }

    private void updateOrder() {
        order.setDishes(orderDishesListView.getItems().stream()
                .filter(orderDishTo -> orderDishTo.getId() == null)
                .map(orderDishTo -> new OrderDish(null, order, orderDishTo.getDish(),
                        orderDishTo.getAmount(), orderDishTo.getComment())).collect(Collectors.toList()));
        AccessUtil.performIfAccess(() -> {
            orderController.updateWithContent(order, order.getUser().id());
            openOrdersPane();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    @FXML
    private void onBillClick() {
        setOnBill();
    }

    private void setOnBill() {
        order.setStatus(OrderStatus.ON_BILL);
        AccessUtil.performIfAccess(() -> {
            orderController.update(order, order.getUser().id());
            openOrdersPane();
        }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
    }

    private void updateOrderCheckAmount() {
        order.setCheckAmount(0);
        orderDishesListView.getItems().forEach(orderDishTo ->
                order.setCheckAmount(order.getCheckAmount() + orderDishTo.getCost()));
        order.setCheckAmount(OrderUtil.getCostWithDiscount(order.getCheckAmount(), order.getDiscount()));
        checkAmountField.setText(Double.toString(order.getCheckAmount()));
    }

    @FXML
    private void onSetDiscountClick() {
        if (order.isNew()) {
            return;
        }
        Integer discount = null;
        try {
            Stage discountStage = stageLoader.loadModalStage("discount", "Комментарий к блюду");
            discountPaneController.init(discountStage);
            discountStage.showAndWait();
            discount = discountPaneController.getDiscount();
        } catch (IOException e) {
            AlertUtil.inform("Произошла ошибка!", Alert.AlertType.ERROR);
        }
        if (discount != null) {
            order.setDiscount(discount);
            AccessUtil.performIfAccess(() -> {
                updateOrderCheckAmount();
                orderController.update(order, order.getUser().id());
                discountField.setText(Integer.toString(order.getDiscount()));
            }, () -> AlertUtil.inform("Отсутствует доступ!", Alert.AlertType.WARNING));
        }
        order = orderController.get(order.id());
        updateOrderCheckAmount();
    }

    @FXML
    private void onBackMenuClick() {
        currentDishCategory = currentDishCategory.getCategory();
        if (currentDishCategory == null) {
            backMenuButton.setDisable(true);
        }
        updateMenuGridPane();
    }

    @FXML
    private void onSearchClick() {

    }

    private void updateMenuGridPane() {
        List<DishCategory> dishCategories = currentDishCategory == null ?
                dishCategoryController.getAllTop() : dishCategoryController.getAllByCategory(currentDishCategory.id());
        List<Dish> dishes = currentDishCategory == null ?
                dishController.getAllTop() : dishController.getAllByCategory(currentDishCategory.id());
        List<DishTo> dishTos = DishUtil.getTos(dishCategories);
        dishTos.addAll(DishUtil.getTos(dishes));
        buttonMenuGridPane.fillGridPane(dishTos, menuActionPerformer);
    }

    private static class DishButton extends ContentButton<DishTo> {

        static final String DISH_CATEGORY_BUTTON_STYLE =
                "-fx-background-color: #CD853F;" +
                        " -fx-border-width: 3;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static final String DISH_BUTTON_STYLE =
                "-fx-background-color: #F5DEB3;" +
                        " -fx-border-width: 3;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        DishButton(Button button, DishTo dishTo) {
            super(button, dishTo);
        }

        static class DishButtonFactory extends ContentButtonFactory<DishButton, DishTo> {
            @Override
            public DishButton create(DishTo dishTo, Consumer<DishTo> performer) {
                Button button = new Button(dishTo.getName());
                button.setOnAction(event -> performer.accept(dishTo));
                button.setStyle(dishTo.isCategory() ? DISH_CATEGORY_BUTTON_STYLE : DISH_BUTTON_STYLE);
                setButtonDefaultProperties(button);
                return new DishButton(button, dishTo);
            }
        }
    }

    @FXML
    private void onBackClick() {
        openOrdersPane();
    }

    private void openOrdersPane() {
        try {
            stageLoader.setPane("orders");
            ordersPaneController.init(user);
        } catch (IOException e) {
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
