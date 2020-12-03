package ru.jegensomme.restaurant_service_system.app.ui.panecontroller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.jegensomme.restaurant_service_system.app.util.SpringStageLoader;

public abstract class AbstractPaneController implements ApplicationContextAware {

    @Autowired
    protected SpringStageLoader stageLoader;

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}