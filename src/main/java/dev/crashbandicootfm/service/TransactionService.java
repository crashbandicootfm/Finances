package dev.crashbandicootfm.service;

import dev.crashbandicootfm.exception.TransactionException;
import dev.crashbandicootfm.profile.Profile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TransactionService {

  public String getBalance(@NotNull Profile profile) {
    return String.format("%f0", profile.getBalance());
  }

  public void sendMoney(float amount, @NotNull Profile sender, @NotNull Profile recipient) {
    try {
      sender.withdraw(amount);
      recipient.deposit(amount);
      System.out.println("Transaction successful");
    } catch (TransactionException e) {
      System.out.println("Failed: " + e.getMessage());
    }
  }

  public void withdraw(float amount, @NotNull Profile profile) {
    try {
      profile.withdraw(amount);
      System.out.println("Success");
    } catch (TransactionException e) {
      System.out.println("Failed: " + e.getMessage());
    }
  }
}
