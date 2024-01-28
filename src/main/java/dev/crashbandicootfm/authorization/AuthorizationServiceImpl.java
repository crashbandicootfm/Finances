package dev.crashbandicootfm.authorization;

import dev.crashbandicootfm.profile.Profile;
import dev.crashbandicootfm.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Scanner;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    ProfileRepository profileRepository;

    @Override
    public void authorize(String name) {

        System.out.print("Enter your pin: ");
        Scanner scanner = new Scanner(System.in);
        int password = scanner.nextInt();

        if (check(name, password)) {
            System.out.println("Verification passed");
        }
        else {
            System.out.println("Verification failed");
            authorize(name);
        }
    }

    private boolean check(String name, int pin) {
        Profile profile = profileRepository.getAll()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);

        return profile != null && profile.getPin() == pin;
    }
}
