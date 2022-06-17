package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(45) NULL," +
                    "  `lastName` VARCHAR(45) NULL," +
                    "  `age` INT NULL," +
                    "  PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE mydbtest.users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String LastName, byte age) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO mydbtest.users (users.name, LastName, age) VALUES(?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, LastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM mydbtest.users WHERE id = ?");
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM mydbtest.users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));

                users.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
        public void cleanUsersTable () {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM mydbtest.users");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}