package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class UserServiceImpl implements UserService {
UserDaoJDBCImpl userDaoJdbcImpl = new UserDaoJDBCImpl(); // экземпляр UserDaoJDBCImpl, что бы использовать его
    // реализацию методов
    public void createUsersTable() {
        userDaoJdbcImpl.createUsersTable();
    }
    public void dropUsersTable()        {
        userDaoJdbcImpl.dropUsersTable();
    }
    public void saveUser(String name, String lastName, byte age) {
        userDaoJdbcImpl.saveUser(name, lastName, age);
    }
    public void removeUserById(long id) {
        userDaoJdbcImpl.removeUserById(id);
    }
    public List<User> getAllUsers() {
        return userDaoJdbcImpl.getAllUsers();
    }
    public void cleanUsersTable() {
        userDaoJdbcImpl.cleanUsersTable();
    }
}