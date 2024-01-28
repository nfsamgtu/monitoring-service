package com.smetering.in;

import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;
import com.smetering.domain.entities.enums.Role;

import java.util.List;
import java.util.Scanner;

public class ConsoleAdapter {
    private final ConsoleActions actions;
    private final Scanner scanner;
    private User activeUser;

    public ConsoleAdapter(ConsoleActions actions, Scanner scanner) {
        this.actions = actions;
        this.scanner = scanner;
    }

    public void serve() {
            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1. Регистрация");
                System.out.println("2. Авторизация");
                System.out.println("0. Завершить работу приложения");

                String choice = scanner.next();
                scanner.nextLine();

                switch (choice) {
                    case "1":
                        activeUser = actions.register();
                        break;
                    case "2":
                        activeUser = actions.login();
                        if (activeUser == null) {
                            break;
                        } else if (activeUser.getRole().equals(Role.ROLE_USER)) {
                            basicUserActions(scanner);
                            break;
                        } else {
                            superUserActions(scanner);
                            break;
                        }
                    case "0":
                        System.exit(0);
                    default:
                        System.out.println("Неверный выбор. Повторите.");
                }
            }
    }

    private void superUserActions(Scanner scanner) {
            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1. Показать всех пользователей");
                System.out.println("2. Показать активность всех пользователей");
                System.out.println("3. Показать активность одного пользователя");
                System.out.println("4. Показать актуальные показания одного пользователя");
                System.out.println("5. Показать все показания одного пользователя");
                System.out.println("6. Показать показания одного пользователя за конкретный месяц");
                System.out.println("0. Выйти");

                String choice = scanner.next();
                scanner.nextLine();

                switch (choice) {
                    case "1":
                        actions.showAllUsers();
                        break;
                    case "2":
                        actions.showActivityRecords();
                        break;
                    case "3":
                        List<User> userList = actions.showAllUsers();
                        System.out.println("Введите номер пользователя для отображения активности:");
                        int userIndex = scanner.nextInt();
                        User selectedUser = userList.get(userIndex);
                        actions.showActivityRecords(selectedUser);
                        break;
                    case "4":
                        List<User> users = actions.showAllUsers();
                        System.out.println("Введите номер пользователя для отображения актуальных показаний:");
                        int index = scanner.nextInt();
                        User userToShowReadings = users.get(index);
                        actions.showActualReadings(userToShowReadings);
                        break;
                    case "5":
                        List<User> allUsers = actions.showAllUsers();
                        System.out.println("Введите номер пользователя для отображения всех показаний:");
                        int ind = scanner.nextInt();
                        User userToShowAllReadings = allUsers.get(ind);
                        actions.showAllReadings(userToShowAllReadings);
                        break;
                    case "6":
                        List<User> allUsersToShow = actions.showAllUsers();
                        System.out.println("Введите номер пользователя для отображения показаний за месяц:");
                        int in = scanner.nextInt();
                        User userToShowSpecificReading = allUsersToShow.get(in);
                        actions.showSpecificValues(userToShowSpecificReading);
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неверный выбор. Повторите.");
                }
            }

    }

    private void basicUserActions(Scanner scanner) {
            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1. Добавить прибор учёта");
                System.out.println("2. Добавить показания прибора учёта");
                System.out.println("3. Показать актуальные показания");
                System.out.println("4. Показать все показания");
                System.out.println("5. Показать показания за конкретный месяц");
                System.out.println("0. Выйти");

                String choice = scanner.next();
                scanner.nextLine();

                switch (choice) {
                    case "1":
                        actions.registerMeter(activeUser);
                        break;
                    case "2":
                        List<Meter> meters = activeUser.getCustomer().getMeters();
                        if (meters.isEmpty()) {
                            System.out.println("У пользователя нет приборов учёта.");
                            break;
                        }
                        System.out.println("Выберите прибор учёта:");
                        for (int i = 0; i < meters.size(); i++) {
                            System.out.println((i + 1) + ". " + meters.get(i).getSerialNumber());
                        }
                        int meterIndex = scanner.nextInt();
                        Meter selectedMeter = meters.get(meterIndex - 1);
                        actions.submitReading(activeUser, selectedMeter);
                        break;
                    case "3":
                        actions.showActualReadings(activeUser);
                        break;
                    case "4":
                        actions.showAllReadings(activeUser);
                        break;
                    case "5":
                        actions.showSpecificValues(activeUser);
                        break;
                    case "0":
                        return; // Выход из цикла
                    default:
                        System.out.println("Неверный выбор. Повторите.");
                }
            }
    }

}
