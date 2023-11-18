package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("Andrej", "Ivanov", 862.8d));
        profiles.add(new Profile("Dima", "Ivanov", 586.0));
        Bank bank = new Bank();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name");
        String name = scanner.nextLine();

        Profile user = null;
        for (Profile clients: profiles) {
            if (clients.getName().equals(name)) {
                user = clients;
                break;
            }
        }

        System.out.println("Choose the action");
        System.out.println("1. My balance");
        System.out.println("2. Send money");
        System.out.println("3. Withdrawal");

        int choice = scanner.nextInt();

        if (choice == 1) {
            assert user != null;
            System.out.println(user.showAmount());
        } else if (choice == 2) {
            scanner.nextLine();
            System.out.println("Enter the person you want to send money: ");
            String enteredName = scanner.nextLine();
            Profile person = null;
            for (Profile clients : profiles) {
                if (clients.getName().equals(enteredName)) {
                    person = clients;
                    break;
                }
            }
            bank.sendMoney(user, person);
        } else if (choice == 3) {
            assert user != null;
            bank.withdrawal(user);
            System.out.println("Your balance is: " + user.getBalance());
        }
    }
}