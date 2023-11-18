package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Profile {

    private final String name;
    private final String surname;
    private double balance;

    public String showAmount() {
        return name + " " + surname + " amount is: " + balance;
    }
}