package dev.crashbandicootfm.service.registration;

import dev.crashbandicootfm.service.profile.Profile;
import dev.crashbandicootfm.service.profile.ProfileService;
import java.util.Scanner;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegistrationServiceImpl implements RegistrationService {

  @NotNull ProfileService profileService;

  @Override
  public void performRegistration() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Registration for new profile");
    UUID id = UUID.randomUUID();
    System.out.println("Your id is: " + id);
    System.out.print("Enter your name for adding to database: ");
    String name = scanner.nextLine();
    System.out.print("Enter your pin for adding to database: ");
    int pin = scanner.nextInt();
    Profile newProfile = new Profile(id, name, pin);
    profileService.addProfile(newProfile);
  }
}
