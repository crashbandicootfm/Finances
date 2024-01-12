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


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class ReflectCommandLineBootstrap implements CommandLineBootstrap {

  @NotNull
  ReflectActionHandlerService actionHandler = new ReflectActionHandlerServiceImpl();

  @NotNull
  Scanner scanner = new Scanner(System.in);

  @NotNull
  ProfileService profileService = new ProfileService();

  @NotNull
  TransactionService service = new TransactionService();

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
  }

  @ActionHandler(
          value = "send",
          description = "Send money"
  )
  private void send(Profile profile, @NotNull List<String> recipientName) {
//    System.out.println("Sending money to: " + recipientName);
//    Profile recipient = profileService.getProfile(recipientName);
//
//    if (recipient == null || recipient == profile) return;
//    else {
//      System.out.print("Enter the amount for " + recipientName + ": ");
//      float amount = scanner.nextFloat();
//      scanner.nextLine();
//      System.out.println("Sending money...");
//      service.sendMoney(amount, profile, recipient);
//    }
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
//    if (amount != null && amount.length > 0) {
//      profile.deposit(amount[0]);
//    } else {
//      System.out.print("Enter the amount: ");
//      float sum = scanner.nextFloat();
//      scanner.nextLine();
//      profile.deposit(sum);
//    }
  }

  @ActionHandler(
          value = "withdraw",
          description = "Take money from your account"
  )
  private void withdraw(Profile profile, @NotNull List<String> args) {
//    if (amount != null && amount.length > 0) {
//      profile.withdraw(amount[0]); // Assuming only one amount is provided
//    } else {
//      System.out.println("Enter the amount: ");
//      float sum = scanner.nextFloat();
//      scanner.nextLine();
//      withdraw(profile, sum);
//    }
  }

  @ActionHandler(
          value = "uuid",
          description = "Shows your uuid"
  )
  private void uuid(Profile profile, @NotNull List<String> args) {
    System.out.printf("Your UUID: %s", profile.getUuid());
  }

  @Override
  @SuppressWarnings("InfiniteLoopStatement")
  public void bootstrap() {
    profileService.addProfile(new Profile("Andrej", "Ivanov", 862.8F));
    profileService.addProfile(new Profile("Dima", "Ivanov", 762.0F));
    System.out.print("Enter your name: ");
    actionHandler.discoverHandlerMethods(this);
    String name = scanner.nextLine();
    Profile profile = Objects.requireNonNull(profileService.getProfile(name));

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