package com.smetering.in;

import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;
import com.smetering.domain.usecases.*;

import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleActions {
    private final AuthService authService;
    private final RegisterService registerService;
    private final ActivityRecordsDisplay activityRecordsDisplay;
    private final ActualReadingsDisplay actualReadingsDisplay;
    private final AllReadingsDisplay allReadingsDisplay;
    private final MeterService meterService;
    private final SpecificReadingsDisplay specificReadingsDisplay;
    private final UsersDisplay usersDisplay;
    private final ReadingSubmitterService readingSubmitterService;


    public ConsoleActions(AuthService authService,
                          RegisterService registerService,
                          String adminPassword,
                          ActivityRecordsDisplay activityRecordsDisplay,
                          ActualReadingsDisplay actualReadingsDisplay,
                          AllReadingsDisplay allReadingsDisplay,
                          MeterService meterService,
                          SpecificReadingsDisplay specificReadingsDisplay,
                          UsersDisplay usersDisplay, ReadingSubmitterService readingSubmitterService) {
        this.authService = authService;
        this.registerService = registerService;
        this.activityRecordsDisplay = activityRecordsDisplay;
        this.actualReadingsDisplay = actualReadingsDisplay;
        this.allReadingsDisplay = allReadingsDisplay;
        this.meterService = meterService;
        this.specificReadingsDisplay = specificReadingsDisplay;
        this.usersDisplay = usersDisplay;
        this.readingSubmitterService = readingSubmitterService;
        registerService.register("admin", adminPassword);
    }

    /**
     * Регистрация пользователя
     *
     * @return зарегистрированного пользователя
     */
    public User register() {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Введите имя пользователя:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();

            Optional<User> user = registerService.register(username, password);
            if (user.isPresent()) {
                System.out.println("Регистрация успешна");
                return user.get();
            } else {
                System.out.println("Регистрация неуспешна. Этот пользователь уже существует!");
                return null;
            }
        }
    }

    /**
     * Обработка логина пользователя
     *
     * @return пользователя
     */
    public User login() {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Введите имя пользователя:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();

            Optional<User> user = authService.login(username, password);
            if (user.isPresent()) {
                System.out.println("Успешная авторизация");
                return user.get();
            } else {
                System.out.println("Неверное имя пользователя или пароль.");
                return null;
            }
        }
    }

    /**
     * Обработка логаута пользователя
     *
     * @param user пользователь
     */
    public void logout(User user) {
        authService.logout(user);
    }

    /**
     * Показ записей о действиях всех пользователей
     */
    public void showActivityRecords() {
        System.out.println("История действий всех пользователей:");
        activityRecordsDisplay.get().forEach(System.out::println);
    }

    /**
     * Показ записей о действиях одного пользователя
     * @param user пользователь
     */
    public void showActivityRecords(User user) {
        System.out.println("История действий пользователя " + user.getName() + ":");
        activityRecordsDisplay.get(user).forEach(System.out::println);
    }

    /**
     * Вывод в консоль списка актуальных покааний счётчиков пользователя
     * @param user пользователь
     */
    public void showActualReadings(User user) {
        System.out.println("Актуальные показания счётчиков для пользователя " + user.getName() + ":");
        actualReadingsDisplay.showActualValues(user).forEach(System.out::println);
    }

    /**
     * Вывод в консоль всех покааний счётчиков пользователя
     * @param user пользователь
     */
    public void showAllReading(User user) {
        System.out.println("Все показания счётчиков для пользователя " + user.getName() + ":");
        allReadingsDisplay.showAllValues(user).forEach(System.out::println);
    }

    /**
     * Добавление прибора учёта к учётной записи пользователя
     * @param user пользователь
     * @return прибор учёта
     */
    public Meter registerMeter(User user) {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Введите смещение относитель GMT в минутах:");
            String inputTimeZoneOffset = scanner.nextLine();
            System.out.println("Введите серийный номер прибора учёта:");
            String serialNumber = scanner.nextLine();
            System.out.println("Введите единицу измерения:");
            String unitSymbol = scanner.nextLine();
            System.out.println("Введите множитель единицы измерения:");
            String unitMultiplier = scanner.nextLine();
            System.out.println("Введите тип измерения:");
            String measurementType = scanner.nextLine();
            scanner.nextLine();

            return meterService
                    .registerMeter(user, Double.valueOf(inputTimeZoneOffset), serialNumber, unitSymbol, unitMultiplier, measurementType)
                    .get();

        }
    }

    /**
     * Вывести показания счётчика пользователя за конкретный месяц
     * @param user пользователь
     */
    public void showSpecificValues(User user) {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Введите номер месяца:");
            String inputMonth = scanner.nextLine();
            System.out.println("Введите год");
            String inputYear = scanner.nextLine();
            scanner.nextLine();

            System.out.println("История действий пользователя " + user.getName() + ":");
            specificReadingsDisplay.showSpecificValues(user, Month.of(Integer.parseInt(inputMonth)), Integer.parseInt(inputYear)).forEach(System.out::println);
        }
    }

    /**
     * Вывести список всех зарегистрированных пользователей
     */
    public void showAllUsers() {
        System.out.println("Список всех зарегистрированных пользователей:");
        usersDisplay.get().forEach(System.out::println);
    }

    /**
     * Внести показания прибора учёта
     *
     * @param user пользователь
     * @param meter прибор учёта
     */
    public void submitReading(User user, Meter meter) {
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Введите значение показаний:");
            String value = scanner.nextLine();

            readingSubmitterService.submitMeasurementValue(user, meter, value);
        }
    }

}
