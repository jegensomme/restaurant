open module ru.jegensomme.restaurant_service_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.beans;
    requires spring.context;
    requires org.slf4j;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.core;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.validation;
    requires org.hibernate.validator;

    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    exports ru.jegensomme.restaurant_service_system.app;

    exports ru.jegensomme.restaurant_service_system.app.controller;
    exports ru.jegensomme.restaurant_service_system.app.controller.user;
    exports ru.jegensomme.restaurant_service_system.app.controller.panecontroller;

    exports ru.jegensomme.restaurant_service_system.app.util;

    exports ru.jegensomme.restaurant_service_system.model;
    exports ru.jegensomme.restaurant_service_system.service;
    exports ru.jegensomme.restaurant_service_system.repository.jdbc;
    exports ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;
    exports ru.jegensomme.restaurant_service_system.repository.jdbc.mapper;
}
