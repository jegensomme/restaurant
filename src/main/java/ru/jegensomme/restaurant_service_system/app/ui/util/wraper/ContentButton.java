package ru.jegensomme.restaurant_service_system.app.ui.util.wraper;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ContentButton<T> {

    protected final Button button;
    protected T object;

    protected ContentButton(Button button, T object) {
        this.button = button;
        this.object = object;
    }

    public static void setButtonDefaultProperties(Button button) {
        button.setFont(Font.font("System Bold", 18));
        button.setTextAlignment(TextAlignment.CENTER);
        button.setMaxSize(1.7976931348623157E308, 1.7976931348623157E308);
    }

    public Button getButton() {
        return button;
    }

    public T getObject() {
        return object;
    }

    public abstract static class ContentButtonFactory<B extends ContentButton<T>, T> {

        public abstract B create(T object, Consumer<T> performer);

        public List<ContentButton<T>> of(List<T> objects, Consumer<T> performer) {
            return objects.stream().map(object -> create(object, performer)).collect(Collectors.toList());
        }
    }
}
