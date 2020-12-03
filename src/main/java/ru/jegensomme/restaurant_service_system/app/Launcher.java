package ru.jegensomme.restaurant_service_system.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jegensomme.restaurant_service_system.app.util.SpringStageLoader;

public class Launcher extends Application {

    private static ClassPathXmlApplicationContext context;

    private static SpringStageLoader stageLoader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        context = new ClassPathXmlApplicationContext("classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml");
        stageLoader = context.getBean(SpringStageLoader.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stageLoader.loadMainStage().show();
    }

    @Override
    public void stop() {
        context.close();
    }
}
