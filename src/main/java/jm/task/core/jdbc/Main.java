package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan","Ivanov",(byte) 31);
        userService.saveUser("Vova","Vladimirov",(byte) 26);
        userService.saveUser("Petya","Petrov",(byte) 14);
        userService.saveUser("Vlad","Vladislavov",(byte) 19);
        for (User user: userService.getAllUsers()) {
            System.out.println(user);
        };
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
