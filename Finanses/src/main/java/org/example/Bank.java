package org.example;

import java.util.Scanner;

public class Bank {

    public void sendMoney(Profile profile1, Profile profile2) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        if (profile1 == null || profile2 == null)
            return;

        profile1.setBalance(profile1.getBalance() - amount);
        profile2.setBalance(profile2.getBalance() + amount);

        System.out.println(profile1.showAmount());
        System.out.println(profile2.showAmount());
    }

    public void withdrawal (Profile profile) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount: ");
        double sum = scanner.nextDouble();
        profile.setBalance(profile.getBalance() - sum);
    }
}
