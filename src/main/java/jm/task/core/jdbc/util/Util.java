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
     // id автоматически назначается БД при создании таблицы (задано в Модели и в SQL на создание таблицы)!
    // поля для работы с БД:
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kata_pp_db"; // это URL моей БД на MySQL
    private static final String DB_USERNAME = "divan";
    private static final String DB_PASSWORD = "divan";

    public static Connection connection; // соединение к БД будет хранится в этом
    // статическом поле. Сразу задаём значение этому полю ниже без объявления метода (так удобнее)

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // подгрузка класса с JDBC драйвером в оперативную память.
            // В последних версиях это не обязательно
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("(Util) Connection to DB - OK");
        } catch (ClassNotFoundException |
                 SQLException e) { // один из недостатков JDBC - только одна ошибка, не понять что произошло конкретно
            e.printStackTrace();
            System.out.println("(Util) Connection to DB - NOT ok");
        }
    }
 //------------------------ Настройки HIBERNATE--------------------------------------------------//
 // здесь все настройки Hibernate. Можно их так же делать через отдельный файл hibernate.cfg.xml's или hibernate.properties
    public static SessionFactory sessionFactory; // соединение к БД будет храниться в этом
    // статическом поле. Сразу задаём значение этому полю ниже без объявления метода (так удобнее потом его использовать)

    static {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // ниже настройки Hibernate
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/kata_pp_db");
                settings.put(Environment.USER, "divan");
                settings.put(Environment.PASS, "divan");

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true"); // показать сформированный SQL запрос в консоли
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread"); // thread - каждая сессия будет автоматически
                // закрываться при завершении транзакции только о сессиях, полученных посредством getCurrentSession().
                // Если вы в своем коде используете openSession() и управляете сессией самостоятельно,
                // то вам необходимо явно вызывать методы flush() и close().

                settings.put(Environment.HBM2DDL_AUTO, ""); // create-drop автоматически обновлять схему базы данных при запуске
                // приложения. Например, если в классе-сущности было добавлено новое поле, Hibernate добавит соответствующую
                // колонку в таблицу базы данных. Пустые "" - схема создаётся автоматически

                configuration.setProperties(settings); // записали настройки Hibernate в configuration

                configuration.addAnnotatedClass(User.class); // указали класс Модели (оно же название таблицы в БД)

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build(); // сформировали сущность подключения

                sessionFactory = configuration.buildSessionFactory(serviceRegistry); // формируем готовый sessionFactory
                System.out.println("(Util) sessionFactory to DB - OK");
            } catch (Exception e) {
                System.out.println("(Util) sessionFactory to DB - NOT ok");
                e.printStackTrace();
            }
        }
    }
}