package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
     // id автоматически назначается БД!
    // поля для работы с БД:
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kata_pp_db"; // это URL моей БД на postgresql
    private static final String DB_USERNAME = "divan";
    private static final String DB_PASSWORD = "divan";

    public static Connection connection; // соединение к БД будет хранится в этом
    // статическом поле. Сразу задаём значение этому полю ниже без объявления метода (так удобнее)

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // подгрузка класса с JDBC драйвером в оперативную память.
            // В последних версиях это не обязательно
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("(Util.java) Connection to DB - OK");
        } catch (ClassNotFoundException |
                 SQLException e) { // один из недостатков JDBC - только одна ошибка, не понять что произошло конкретно
            e.printStackTrace();
            System.out.println("(Util) Connection to DB - NOT ok");
        }
    }
}