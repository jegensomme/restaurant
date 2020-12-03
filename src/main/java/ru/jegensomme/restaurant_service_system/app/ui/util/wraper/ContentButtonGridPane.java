package ru.jegensomme.restaurant_service_system.app.ui.util.wraper;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ContentButtonGridPane<B extends ContentButton<T>, T> {

    protected final GridPane gridPane;

    private final int rowPrefHeight;

    private int nextColumn = 0;

    private int nextRow = 0;

    protected final ContentButton.ContentButtonFactory<B, T> contentButtonFactory;

    protected final List<B> contentButtons = new ArrayList<>();

    public ContentButtonGridPane(GridPane gridPane, ContentButton.ContentButtonFactory<B, T> contentButtonFactory, int rowPrefHeight) {
        this.gridPane = gridPane;
        this.contentButtonFactory = contentButtonFactory;
        this.rowPrefHeight = rowPrefHeight;
    }

    public void fillGridPane(List<T> objects, Consumer<T> performer) {
        contentButtons.clear();
        gridPane.getChildren().clear();
        nextColumn = 0;
        nextRow = 0;
        for (T object : objects) {
            add(contentButtonFactory.create(object, performer));
        }
    }

    public void add(B contentButton) {
        contentButtons.add(contentButton);
        gridPane.add(contentButton.getButton(), nextColumn++, nextRow);
        if (nextColumn == gridPane.getColumnCount()) {
            if (nextRow == gridPane.getRowCount()) {
                addRow(gridPane, rowPrefHeight);
            } else {
                nextColumn = 0;
                ++nextRow;
            }
        }
    }

    public static void addRow(GridPane gridPane, int prefHeight) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(prefHeight);
        rowConstraints.setMinHeight(prefHeight);
        rowConstraints.setMaxHeight(prefHeight);
        gridPane.setPrefHeight(gridPane.getPrefHeight() + prefHeight);
        gridPane.getRowConstraints().add(rowConstraints);
    }
}
