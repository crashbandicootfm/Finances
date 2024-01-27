package dev.crashbandicootfm.service;

import java.util.Objects;
import java.util.Scanner;

public interface Authorization {
    default void authorization(String name) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your pin: ");

        int pin = scanner.nextInt();

        if (pin == 1719 && Objects.equals(name, "Dima")) {
            System.out.println("Authorization passed");
        } else if (pin == 1713 && Objects.equals(name, "Andrej")) {
            System.out.println("Authorization passed");
        } else {
            System.out.println("Authorization failed");
            authorization(name);
        }
    }
}