package jm.task.core.jdbc.dao;
// здесь расписываем основной функционал (CRUD методы взаимодействия с SQL)

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() { } // пустой конструктор

    @Override
    public void createUsersTable() {
        // try с ресурсом, сам закроет connection!!!
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("CREATE TABLE IF NOT EXISTS `kata_pp_db`.`users` (" +
                        "`id` INT NOT NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(100) NOT NULL," +
                        "`lastName` VARCHAR(100) NOT NULL," +
                        "`age` INT(3) NOT NULL," +
                        "PRIMARY KEY (`id`))" +
                        "DEFAULT CHARACTER SET = utf8;")) {
            preparedStatement.executeUpdate(); // закоммитил SQL в БД
            // sql запрос создания таблицы взят из MySQL WorkBench:
            System.out.println("(UserDaoJDBCImpl) Table create - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        // try с ресурсом, сам закроет connection!!!
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DROP TABLE IF EXISTS `kata_pp_db`.`users`;")) {
            // sql запрос удаления взят из MySQL WorkBench:
            preparedStatement.executeUpdate(); // отправил SQL в БД
            System.out.println("(UserDaoJDBCImpl) Table drop - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
// try с ресурсом, сам закроет connection!!!
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO users Values(id, ?,?,?)")) { // создаём SQL запрос и присваиваем полям нового юзера значения
            preparedStatement.setString(1, name); // вместо ? в запрос помещаем конкретные значения,
            preparedStatement.setString(2, lastName); // которые пришли в метод
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate(); // сохранили нового юзера в БД
            System.out.println("(UserDaoJDBCImpl) Save new user - OK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        // try с ресурсом, сам закроет connection!!! создаём SQL запрос и передаём в него id для удаления
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate(); // зафиксировали удаление юзера в БД
            System.out.println("(UserDaoJDBCImpl) Remove user - OK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() { // считываем из БД всех людей
        List<User> allUsers = new ArrayList<>(); // в этот лист будем записывать людей, которых достанем из БД
        // try с ресурсом, сам закроет connection!!!
        try (Statement statement = connection.createStatement()) // объект, который содержит в себе SQL-запрос к БД, и содержит соединение к БД
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kata_pp_db.users;");// на statement выполняем
            // SQL-запрос, вернутся строчки из БД, присваиваем результат объекту ResultSet (коллекция)
            while (resultSet.next()) { // проходимся по каждой строке возвращенного запроса и каждую строку присваиваем в java-объект:
                User user = new User(); // создаём каждый раз нового юзера, пока есть элементы в коллекции resultSet.
                // для каждой колонки получаем значение из БД и присваиваем его новому юзеру:
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user); // добавляем каждого нового юзера с заполненными полями в наш List
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("(UserDaoJDBCImpl) allUsers - OK: " + "\n" + allUsers);
        return allUsers; // возвращаем список всех юзеров, полученных из БД
    }

    @Override
    public void cleanUsersTable() {
        // try с ресурсом, сам закроет connection!!!
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("TRUNCATE TABLE `kata_pp_db`.`users`;")) {
            preparedStatement.executeUpdate(); // отправил SQL в БД
            System.out.println("(UserDaoJDBCImpl) Table clean - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}