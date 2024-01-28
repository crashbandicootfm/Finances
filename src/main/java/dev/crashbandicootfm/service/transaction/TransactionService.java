package dev.crashbandicootfm.service.transaction;

import dev.crashbandicootfm.exception.TransactionException;
import dev.crashbandicootfm.profile.Profile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TransactionService {

  public void sendMoney(float amount, @NotNull Profile sender, @NotNull Profile recipient) {
    try {
      sender.withdraw(amount);
      recipient.deposit(amount);
      System.out.println("Transaction successful");
    } catch (TransactionException e) {
      System.out.println("Failed: " + e.getMessage());
    }
  }
}
