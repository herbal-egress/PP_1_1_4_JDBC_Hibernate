package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import static jm.task.core.jdbc.util.Util.sessionFactory; // импортировал настроенное подключение
// класс общается с БД, извлекает людей из списка, находит по id и т.д.

public class UserDaoHibernateImpl implements UserDao {

    public void createUsersTable() {
        Transaction transaction = null; //
        try (Session session = sessionFactory.getCurrentSession()) { // создал сессию, если её нет
            transaction = session.beginTransaction(); // создалась транзакция
            // запрос на SQL языке:
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `kata_pp_db`.`users` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(100) NOT NULL," +
                    "`lastName` VARCHAR(100) NOT NULL," +
                    "`age` INT(3) NOT NULL," +
                    "PRIMARY KEY (`id`))" +
                    "DEFAULT CHARACTER SET = utf8;").executeUpdate();
            transaction.commit(); // передал запрос в БД на SQL
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        // try с ресурсом, сам закроет сессию Hibernate!!!
    }

    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) { // создал сессию, если её нет
            transaction = session.beginTransaction(); // создалась транзакция
            // юзаем запрос на SQL языке:
            session.createSQLQuery("DROP TABLE IF EXISTS `kata_pp_db`.`users`;").executeUpdate();
            transaction.commit(); // передал запрос в БД на SQL
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        // try с ресурсом, сам закроет сессию Hibernate!!!
    }

    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null; //
        try (Session session = sessionFactory.getCurrentSession()) { // получил текущую сессию
            transaction = session.beginTransaction(); // создалась транзакция
            session.save(new User(name, lastName, age));  // создал нового юзера в сессии
            transaction.commit(); // передал в БД SQL, который Hibernate сам создал!!!
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        // try с ресурсом, сам закроет сессию Hibernate!!!
    }

    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) { // создал сессию, если её нет
            transaction = session.beginTransaction(); // создалась транзакция
            session.delete(session.load(User.class, id)); // из сессии загрузил юзера по id и удалил
            transaction.commit(); // передал в БД SQL, который был сформирован Хайбернейтом автоматически
            System.out.println("(UserDaoHibernateImpl) Remove user - OK");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        // try с ресурсом, сам закроет сессию Hibernate!!!
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) { // создал сессию
            return session.createQuery("from User", User.class).getResultList(); // HQL-запрос и возвращаем List
            // из всех юзеров сразу приводим к листу
        }
        // try с ресурсом, сам закроет сессию Hibernate!!!
    }

    public void cleanUsersTable() {
        Transaction transaction = null; //
        try (Session session = sessionFactory.getCurrentSession()) { // создал сессию, если её нет
            transaction = session.beginTransaction(); // создалась транзакция
            // юзаем запросы на SQL языке:
            session.createSQLQuery("TRUNCATE TABLE `kata_pp_db`.`users`;").executeUpdate();
            transaction.commit(); // передал запрос в БД SQL!!!
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        // try с ресурсом, сам закроет сессию Hibernate!!!
    }
}