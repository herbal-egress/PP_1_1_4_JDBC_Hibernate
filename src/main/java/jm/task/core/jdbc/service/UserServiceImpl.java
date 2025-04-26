package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
   // UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl(); // экземпляр UserDaoHibernateImpl, что бы использовать его
    // реализацию методов - так нельзя делать!!!
    private final UserDao userDao;  // использую интерфейс UserDao. Так добиваюсь инкапсуляции, и не обращаюсь
// к методам UserDaoHibernateImpl или UserDaoJDBCImpl напрямую!
public UserServiceImpl(UserDao userDao) { // внедрение зависимости от UserDao через конструктор
    this.userDao = userDao;
}
// Теперь при использовании этого класса, можно передавать любую реализацию интерфейса UserDao через создание одного из двух объектов:
// UserDao userDao = new UserDaoJDBCImpl();
// или другая реализация:
// UserDao userDao = new UserDaoHibernateImpl();
// И теперь все методы юзаем через интерфейс userDao:
public void createUsersTable() {
    userDao.createUsersTable();
}
    public void dropUsersTable()        {
        userDao.dropUsersTable();
    }
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}