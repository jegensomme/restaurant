package ru.jegensomme.restaurant_service_system.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.jegensomme.restaurant_service_system.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaUtil {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DriverManagerDataSource dataSource;

    public void clear2ndLevelHibernateCache() {
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }

    public void authUser(Role role) {
        dataSource.setUsername(role == Role.MANAGER ? "manager" : "waiter");
        dataSource.setPassword("password");
    }
}