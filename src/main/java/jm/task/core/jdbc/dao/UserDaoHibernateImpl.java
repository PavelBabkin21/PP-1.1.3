package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(45) NULL," +
                    "  `lastName` VARCHAR(45) NULL," +
                    "  `age` INT NULL," +
                    "  PRIMARY KEY (`id`))";
            session.createSQLQuery(sql).executeUpdate();

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);

        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);

        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            String stringQuery = "from User";
            users = session.createQuery(stringQuery).list();


        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            String stringQuery = "DELETE FROM User";
            Query query = session.createQuery(stringQuery);
            query.executeUpdate();

        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }
}
