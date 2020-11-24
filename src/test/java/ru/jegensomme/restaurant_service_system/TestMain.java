package ru.jegensomme.restaurant_service_system;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml");
    }
}
