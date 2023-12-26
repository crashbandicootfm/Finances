package dev.crashbandicootfm.profile;

import dev.crashbandicootfm.exception.TransactionException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;


@Setter
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Profile {

  @NotNull
  UUID uuid = UUID.randomUUID();

  @NotNull
  String name;

  @NotNull
  String surname;

  @NonFinal
  float balance = 0;

  public Profile(@NotNull String name, @NotNull String surname) {
    this.name = name;
    this.surname = surname;
  }

  public Profile(@NotNull String name, @NotNull String surname, float balance) {
    this.name = name;
    this.surname = surname;
    this.balance = balance;
  }

  public void deposit(float amount) {
    balance += amount;
  }

  public void withdraw(float amount) throws TransactionException {
    if (balance < amount) {
      throw new TransactionException("Not enough money");
    }
    balance -= amount;
  }

  public @NotNull String getBalanceFormatted() {
    return String.format("%.2f", balance);
  }
}