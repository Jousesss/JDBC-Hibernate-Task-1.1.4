package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user_list (" +
                    "user_id BIGSERIAL PRIMARY KEY NOT NULL," +
                    "user_name TEXT NOT NULL," +
                    "user_lastname TEXT NOT NULL," +
                    "user_age SMALLINT NOT NULL)");
            System.out.println("[вызов метода - createUsersTable]   Если таблицы не было, то сейчас она создана!");
        } catch (Exception e) {
            System.err.println("Ошибка создания таблицы пользователей: ");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user_list");
            System.out.println("[вызов метода - dropUsersTable]   Если таблица существовала, то сейчас она удалена!");
        } catch (Exception e) {
            System.err.println("Ошибка удаления таблицы пользователей: ");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO user_list (user_name,user_lastname,user_age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("[вызов метода - saveUser]   User с именем – " + name + " добавлен в базу данных!");
        } catch (Exception e) {
            System.err.println("Ошибка добавления пользователя в таблицу: ");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM user_list WHERE user_id = ?";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("[вызов метода - removeUserById]   User с id - " + id + " был удалён из базы данных!");
        } catch (Exception e) {
            System.err.println("Ошибка удаления пользователя из таблицу: ");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_list");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("user_name"),resultSet.getString("user_lastname"),resultSet.getByte("user_age"));
                user.setId(resultSet.getLong("user_id"));
                users.add(user);
            }
            System.out.println("[вызов метода - getAllUsers]   Список пользователей был получен!");
        } catch (Exception e) {
            System.err.println("Ошибка получения таблицы пользователей: ");
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM user_list");
            System.out.println("[вызов метода - cleanUsersTable]   Удалены все записи из таблицы!");
        } catch (Exception e) {
            System.err.println("Ошибка очистки таблицы пользователей: ");
            e.printStackTrace();
        }
    }
}
