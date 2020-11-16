package ru.jegensomme.restaurant_service_system.app.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("singleton")
public class SpringStageLoader implements ApplicationContextAware {

    private static ApplicationContext staticContext;
    @Value("${title}")
    private String appTitle;

    private Stage stage;

    private static final String FXML_DIR = "/view/fxml/";
    private static final String AUTH = "auth";

    private static Parent load(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SpringStageLoader.class.getResource(FXML_DIR + fxmlName + ".fxml"));
        loader.setClassLoader(SpringStageLoader.class.getClassLoader());
        loader.setControllerFactory(staticContext::getBean);
        return loader.load(SpringStageLoader.class.getResourceAsStream(FXML_DIR + fxmlName + ".fxml"));
    }

    public Stage loadStage() throws IOException {
        stage = new Stage();
        stage.setScene(new Scene(load(AUTH)));
        stage.setOnHidden(event -> Platform.exit());
        stage.setTitle(appTitle);
        return stage;
    }

    public void setScene(String fxmlName) throws IOException {
        stage.setScene(new Scene(load(fxmlName)));
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringStageLoader.staticContext = context;
    }
}
