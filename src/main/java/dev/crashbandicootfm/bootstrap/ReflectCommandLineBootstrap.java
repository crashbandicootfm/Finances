package dev.crashbandicootfm.bootstrap;

import dev.crashbandicootfm.annotation.ActionHandler;
import dev.crashbandicootfm.authorization.AuthorizationService;
import dev.crashbandicootfm.authorization.AuthorizationServiceImpl;
import dev.crashbandicootfm.connection.ConnectionFactory;
import dev.crashbandicootfm.connection.ConnectionFactoryImpl;
import dev.crashbandicootfm.profile.Profile;
import dev.crashbandicootfm.repository.ProfileRepository;
import dev.crashbandicootfm.repository.ProfileRepositoryImpl;
import dev.crashbandicootfm.service.action.ReflectActionHandlerService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerServiceImpl;
import dev.crashbandicootfm.service.profile.ProfileService;
import dev.crashbandicootfm.service.profile.ProfileServiceImpl;
import dev.crashbandicootfm.service.transaction.TransactionService;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class ReflectCommandLineBootstrap implements CommandLineBootstrap {

  public static final String URL = "";
  public static final String USERNAME = "";
  public static final String PASSWORD = "";

  @NotNull
  ReflectActionHandlerService actionHandler = new ReflectActionHandlerServiceImpl();

  @NotNull
  Scanner scanner = new Scanner(System.in);

  @NotNull
  ConnectionFactory connectionFactory = new ConnectionFactoryImpl();

  @NotNull Connection connection = connectionFactory.createConnection(URL, USERNAME, PASSWORD);

  @NotNull
  ProfileRepository profileRepository = new ProfileRepositoryImpl(connection);

  @NotNull
  ProfileService profileService = new ProfileServiceImpl(profileRepository);

  @NotNull
  TransactionService service = new TransactionService();

  @NotNull
  AuthorizationService authorizationService = new AuthorizationServiceImpl();

  @ActionHandler(
      value = "exit",
      description = "Shutdowns application"
  )
  private void exit(Profile profile, @NotNull List<String> args) {
    System.exit(0);
  }

  @ActionHandler(
      value = "balance",
      description = "Shows your current balance"
  )
  private void balance(Profile profile, @NotNull List<String> args) {
    System.out.printf("Your balance: %.2f", profile.getBalance());
    System.out.println();
  }

  @ActionHandler(
      value = "send",
      description = "Send money"
  )
  private void send(Profile profile, @NotNull List<String> recipientName) {
    System.out.println("Sending money to: " + recipientName);

    if (!recipientName.isEmpty()) {
      String recipient = recipientName.get(0);
      Profile recipientProfile = profileService.getProfile(recipient);

      if (recipientProfile != null && !recipientProfile.equals(profile)) {
        System.out.print("Enter the amount for " + recipient + ": ");
        float amount = scanner.nextFloat();
        scanner.nextLine();
        System.out.println("Sending money...");
        service.sendMoney(amount, profile, recipientProfile);
      } else {
        System.out.println("Invalid recipient");
      }
    } else {
      System.out.println("No recipient specified");
    }
  }

  @ActionHandler(
      value = "help",
      description = "Commands you can use"
  )
  private void help(Profile profile, @NotNull List<String> args) {
    actionHandler.getHelpMessage()
        .forEach(System.out::println);
  }

  @ActionHandler(
      value = "put",
      description = "Put money on your account"
  )
  private void put(Profile profile, @NotNull List<String> args) {
    if (!args.isEmpty()) {
      String amountString = args.get(0);
      float amount = Float.parseFloat(amountString);
      profile.deposit(amount);
    }
  }

  @ActionHandler(
      value = "withdraw",
      description = "Take money from your account"
  )
  private void withdraw(Profile profile, @NotNull List<String> args) {
    if (!args.isEmpty()) {
      String amountString = args.get(0);
      float amount = Float.parseFloat(amountString);
      profile.withdraw(amount);
    }
  }

  @ActionHandler(
      value = "uuid",
      description = "Shows your uuid"
  )
  private void uuid(@NotNull Profile profile, @NotNull List<String> args) {
    System.out.printf("Your UUID: %s", profile.getUuid());
  }

  @Override
  @SuppressWarnings("InfiniteLoopStatement")
  public void bootstrap() {
    profileService.enable();
    System.out.print("Enter your name: ");
    actionHandler.discoverHandlerMethods(this);
    String name = scanner.nextLine();

    Profile profile = profileService.getProfile(name);
    if (profile == null) {
      System.out.println("Profile not found for name: " + name);
      return;
    }
    authorizationService.authorize(profile);

    while (true) {
      System.out.print("fin > ");
      String line = scanner.nextLine();
      String[] params = line.split(" ");
      String action = params[0];

      actionHandler.handle(
          action,
          profile,
          Arrays.stream(params)
              .skip(1)
              .toList()
      );
    }
  }
}