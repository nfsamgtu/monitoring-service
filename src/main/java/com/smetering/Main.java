package com.smetering;

import com.smetering.domain.usecases.*;
import com.smetering.in.ConsoleActions;
import com.smetering.in.ConsoleAdapter;
import com.smetering.out.storages.UserStorage;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Главный класс прилоежения.
 * При запуске приложение создаётся учётная запись суперпользователя "admin".
 * Пароль учётной записи суперпользователя следует указать в первом аргументе командной строки при запуске приложения.
 * Если пароль суперпользователя не задан - пароль по умолчанию "admin".
 */
public class Main {
    public static void main(String[] args) {
        UserStorage storage = new UserStorage();

        ActivityRecordsDisplay activityRecordsDisplay = new ActivityRecordsDisplay(storage);
        ActualReadingsDisplay actualReadingsDisplay = new ActualReadingsDisplay(storage);
        AllReadingsDisplay allReadingsDisplay = new AllReadingsDisplay(storage);
        AuthService authService = new AuthService(storage);
        MeterService meterService = new MeterService(storage);
        ReadingSubmitterService readingSubmitterService = new ReadingSubmitterService(storage);
        RegisterService registerService = new RegisterService(storage);
        SpecificReadingsDisplay specificReadingsDisplay = new SpecificReadingsDisplay(storage);
        UsersDisplay usersDisplay = new UsersDisplay(storage);

        String adminPassword = args.length == 0 ? "admin" : args[0];

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        try (scanner) {
            ConsoleActions actions = new ConsoleActions(authService,
                    registerService,
                    adminPassword,
                    activityRecordsDisplay,
                    actualReadingsDisplay,
                    allReadingsDisplay,
                    meterService,
                    specificReadingsDisplay,
                    usersDisplay,
                    readingSubmitterService,
                    scanner);

            ConsoleAdapter consoleAdapter = new ConsoleAdapter(actions, scanner);

            consoleAdapter.serve();
        }
    }

}
