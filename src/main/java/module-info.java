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
    requires com.sun.xml.bind;

    requires java.naming;

    exports ru.jegensomme.restaurant_service_system.app;

    exports ru.jegensomme.restaurant_service_system.app.controller;
    exports ru.jegensomme.restaurant_service_system.app.ui.panecontroller;
    exports ru.jegensomme.restaurant_service_system.app.ui.util;
    exports ru.jegensomme.restaurant_service_system.app.ui.util.modal;
    exports ru.jegensomme.restaurant_service_system.app.ui.util.wraper;
    exports ru.jegensomme.restaurant_service_system.app.util;

    exports ru.jegensomme.restaurant_service_system.util.exception;

    exports ru.jegensomme.restaurant_service_system.app.util.to;

    exports ru.jegensomme.restaurant_service_system.model;
    exports ru.jegensomme.restaurant_service_system.service;
    exports ru.jegensomme.restaurant_service_system.repository;
    exports ru.jegensomme.restaurant_service_system.repository.jpa;
    exports ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;
}
