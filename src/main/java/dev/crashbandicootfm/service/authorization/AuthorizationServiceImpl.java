package dev.crashbandicootfm.service.authorization;

import dev.crashbandicootfm.service.profile.Profile;
import java.util.Scanner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

  @Override
  public void authorize(@NotNull Profile profile) {
    System.out.print("Enter your pin: ");
    Scanner scanner = new Scanner(System.in);
    int password = scanner.nextInt();

    if (profile.getPin() == password) {
      System.out.println("Verification passed");
      return;
    }

    System.out.println("Verification failed");
    authorize(profile);
  }
}
