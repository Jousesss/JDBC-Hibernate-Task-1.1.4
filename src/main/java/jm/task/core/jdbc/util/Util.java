package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String dataBaseUrl = "url";
    private static final String dataBaseUser = "name";
    private static final String dataBasePassword = "password";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dataBaseUrl,dataBaseUser,dataBasePassword);
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Ошибка получения connection: ");
            e.printStackTrace();
        }
        return connection;
    }

    // Hibernate

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.url", dataBaseUrl)
                        .setProperty("hibernate.connection.username", dataBaseUser)
                        .setProperty("hibernate.connection.password", dataBasePassword)
                        .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}




