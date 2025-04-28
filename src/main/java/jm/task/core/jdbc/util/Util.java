package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kata_pp_db";
    private static final String DB_USERNAME = "divan";
    private static final String DB_PASSWORD = "divan";

    public static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("(Util) Connection to DB - OK");
        } catch (ClassNotFoundException |
                 SQLException e) {
            e.printStackTrace();
            System.out.println("(Util) Connection to DB - NOT ok");
        }
    }

    //------------------------ Настройки HIBERNATE------------------------------------//
    public static SessionFactory sessionFactory;

    static {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/kata_pp_db");
                settings.put(Environment.USER, "divan");
                settings.put(Environment.PASS, "divan");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("(Util) sessionFactory to DB - OK");
            } catch (Exception e) {
                System.out.println("(Util) sessionFactory to DB - NOT ok");
                e.printStackTrace();
            }
        }
    }
}