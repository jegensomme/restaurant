package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jegensomme.restaurant_service_system.app.controller.TableController;
import ru.jegensomme.restaurant_service_system.app.ui.util.wraper.ContentButton;
import ru.jegensomme.restaurant_service_system.app.ui.util.wraper.ContentButtonGridPane;
import ru.jegensomme.restaurant_service_system.app.util.to.TableTo;
import ru.jegensomme.restaurant_service_system.app.util.to.util.TableUtil;

import java.util.List;
import java.util.function.Consumer;

@Component
public class TablesPaneController extends AbstractPaneController {

    @Autowired
    private TableController tableController;

    private Stage stage;

    private TableTo selected;

    @FXML
    private GridPane tablesGridPane;

    private ContentButtonGridPane<TableButton, TableTo> contentButtonTablesGridPane;

    public void init(Stage stage) {
        this.stage = stage;
        this.selected = null;
        contentButtonTablesGridPane = new ContentButtonGridPane<>(tablesGridPane,
                new TableButton.TableButtonFactory(), 106);
        updateTablesGridPane();
    }

    public void updateTablesGridPane() {
        List<TableTo> tableTos = TableUtil.getTos(tableController.getAll());
        contentButtonTablesGridPane.fillGridPane(tableTos, tableButtonActionPerformer);
    }

    private final Consumer<TableTo> tableButtonActionPerformer = new Consumer<>() {
        @Override
        public void accept(TableTo tableTo) {
            selected = tableTo;
            stage.close();
        }
    };

    private static class TableButton extends ContentButton<TableTo> {

        TableButton(Button button, TableTo tableTo) {
            super(button, tableTo);
        }

        static final String TABLE_BUTTON_STYLE =
                "-fx-background-color: #DAA520;" +
                        " -fx-border-width: 3;" +
                        " -fx-border-color: #696969;" +
                        " -fx-border-radius: 10;" +
                        " -fx-background-radius: 12";

        static class TableButtonFactory extends ContentButtonFactory<TableButton, TableTo> {

            @Override
            public TableButton create(TableTo tableTo, Consumer<TableTo> performer) {
                Button button = new Button(tableTo.getNumber().toString());
                button.setStyle(TABLE_BUTTON_STYLE);
                setButtonDefaultProperties(button);
                button.setOnAction(event -> performer.accept(tableTo));
                return new TableButton(button, tableTo);
            }
        }
    }

    @FXML
    private void cancel() {
        selected = null;
        stage.close();
    }

    public TableTo getSelected() {
        return selected;
    }
}
