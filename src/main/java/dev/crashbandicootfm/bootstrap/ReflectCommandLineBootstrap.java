package dev.crashbandicootfm.bootstrap;

import dev.crashbandicootfm.annotation.ActionHandler;
import dev.crashbandicootfm.profile.Profile;
import dev.crashbandicootfm.service.CommandLineService;
import dev.crashbandicootfm.service.ProfileService;
import dev.crashbandicootfm.service.TransactionService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;


@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public final class ReflectCommandLineBootstrap implements CommandLineBootstrap {


  @NotNull
  final ReflectActionHandlerService actionHandler = new ReflectActionHandlerServiceImpl();

  @NotNull
  final Scanner scanner = new Scanner(System.in);

  @NotNull
  final CommandLineService commandLineService = new CommandLineService();

  @NotNull
  final ProfileService profileService = new ProfileService();

  final TransactionService service = new TransactionService();

  @ActionHandler(
          value = "exit",
          description = "Shutdowns application"
  )
  private void exit() {
    System.exit(0);
  }

  @ActionHandler(
          value = "balance",
          description = "Shows your current balance"
  )
  private void balance(Profile profile) {
    System.out.println("Your balance: " + profile.getBalanceFormatted());
  }

  @ActionHandler(
          value = "send",
          description = "Send money"
  )
  private void send(Profile profile, String recipientName) {
    System.out.println("Sending money to: " + recipientName);
    Profile recipient = profileService.getProfile(recipientName);

    if (recipient == null || recipient == profile) return;
    else {
      System.out.print("Enter the amount for " + recipientName + ": ");
      float amount = scanner.nextFloat();
      scanner.nextLine();
      System.out.println("Sending money...");
      service.sendMoney(amount, profile, recipient);
    }
  }

  @ActionHandler(
          value = "help",
          description = "Commands you can use"
  )
  private void help() {
    System.out.println("\"balance\" - shows your current balance");
    System.out.println("\"send\" - send money");
    System.out.println("\"put\" - put money on your account");
    System.out.println("\"withdraw\" - take money from your account");
    System.out.println("\"uuid\" - shows your uuid");
  }

  @ActionHandler(
          value = "put",
          description = "Put money on your account"
  )
  private void put(Profile profile, Float... amount) {
    if (amount != null && amount.length > 0) {
      profile.deposit(amount[0]);
    } else {
      System.out.print("Enter the amount: ");
      float sum = scanner.nextFloat();
      scanner.nextLine();
      profile.deposit(sum);
    }
  }

  @ActionHandler(
          value = "withdraw",
          description = "Take money from your account"
  )
  private void withdraw(Profile profile, Float... amount) {
    if (amount != null && amount.length > 0) {
      profile.withdraw(amount[0]); // Assuming only one amount is provided
    } else {
      System.out.println("Enter the amount: ");
      float sum = scanner.nextFloat();
      scanner.nextLine();
      withdraw(profile, sum);
    }
  }

  @ActionHandler(
          value = "my uuid",
          description = "Shows your uuid"
  )
  private void uuid(Profile profile) {
    System.out.println(profile.getUuid());
  }

  @Override
  @SuppressWarnings("InfiniteLoopStatement")
  public void bootstrap(Profile profile) {
    profileService.addProfile(new Profile("Andrej", "Ivanov", 862.8F));
    profileService.addProfile(new Profile("Dima", "Ivanov", 762.0F));
    System.out.print("Enter your name: ");
    actionHandler.discoverHandlerMethods(this);
    String name = scanner.nextLine();
    profile = Objects.requireNonNull(profileService.getProfile(name));

    while (true) {
      commandLineService.printCommandLinePrompt();
      String actionLine = scanner.nextLine();
      String[] parts = actionLine.split("\\s+", 2);

      String action = parts[0];
      String argument = parts.length > 1 ? parts[1] : "";

      List<Class<?>> parameterTypes = actionHandler.getParameterTypes(action);
      Object[] args = new Object[parameterTypes.size()];

      for (int i = 0; i < parameterTypes.size(); i++) {
        Class<?> parameterType = parameterTypes.get(i);

        if (parameterType == Profile.class) {
          args[i] = profile;
        } else if (parameterType == String.class) {
          args[i] = argument;
        } else if (parameterType == Float.class || parameterType == float.class) {
          args[i] = scanner.nextFloat();
          scanner.nextLine();
        }
      }

      switch (action) {
        case "balance" -> actionHandler.handle(action, profile, args);
        case "send" -> actionHandler.handle(action, profile, args);
        case "help" -> actionHandler.handle(action, profile, args);
        case "put" -> actionHandler.handle(action, profile, args);
        case "withdraw" -> actionHandler.handle(action, profile, args);
        case "uuid" -> actionHandler.handle(action, profile, args);
        default -> actionHandler.handle(action, profile, args);
      }
    }
  }
}