package dev.crashbandicootfm.service.profile;

import dev.crashbandicootfm.exception.TransactionException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;


@Setter
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Profile {

  @NotNull
  UUID uuid;

  @NotNull
  String name;

  @NonFinal
  float balance = 0;

  @NonFinal
  int id;

  @NonFinal
  int pin;

  public Profile(@NotNull UUID uuid, int id, @NotNull String name, int pin) {
    this.uuid = uuid;
    this.name = name;
    this.id = id;
    this.pin = pin;
  }

  public  Profile(@NotNull UUID uuid, @NotNull String name, int pin) {
    this.uuid = uuid;
    this.name = name;
    this.pin = pin;
  }
  public void deposit(float amount) {
    if (amount > 0) {
      balance += amount;
      System.out.println("Success");
    }
    else System.out.println("Amount can not be 0");
  }

  public void withdraw(float amount) throws TransactionException {
    if (balance < amount) {
      throw new TransactionException("Not enough money");
    }
    balance -= amount;
  }

}