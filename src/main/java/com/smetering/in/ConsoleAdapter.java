package com.smetering.in;

import com.smetering.domain.entities.User;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleAdapter {
    private final ConsoleActions actions;
    private User activeUser;

    public ConsoleAdapter(ConsoleActions actions) {
        this.actions = actions;
    }

    public void serve() {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1. Регистрация");
                System.out.println("2. Авторизация");
                System.out.println("0. Завершить работу приложения");

                String choice = scanner.next();
                scanner.nextLine();  // очистка буфера

                switch (choice) {
                    case "1":
                        activeUser = actions.register();
                        basicUserActions();
                        break;
                    case "2":
                        activeUser = actions.login();
                        if (activeUser.getName().equals("admin")) {
                            superUserActions();
                            break;
                        } else if (activeUser != null) {
                            basicUserActions();
                            break;
                        }
                    case "0":
                        System.exit(0);
                    default:
                        System.out.println("Неверный выбор. Повторите.");
                }
            }
        }
    }

    private void superUserActions() {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {

            }
        }
    }

    private void basicUserActions() {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {

            }
        }
    }

}
