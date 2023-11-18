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
    return String.format("%f0.2", profile.getBalance());
  }

  public void sendMoney(
      float amount,
      @NotNull Profile sender,
      @NotNull Profile recipient
  ) throws TransactionException {
    sender.withdraw(amount);
    recipient.deposit(amount);
  }

  public void withdraw(float amount, @NotNull Profile profile) {
    profile.withdraw(amount);
  }
}
