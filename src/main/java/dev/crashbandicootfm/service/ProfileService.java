package dev.crashbandicootfm.service;

import dev.crashbandicootfm.connection.ConnectionFactory;
import dev.crashbandicootfm.connection.ConnectionFactoryImpl;
import dev.crashbandicootfm.profile.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.crashbandicootfm.repository.ProfileRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Setter
@RequiredArgsConstructor
public class ProfileService {

  @NotNull
  Map<UUID, Profile> loadedProfiles = new HashMap<>();

  ProfileRepository profileRepository;

  ConnectionFactory connectionFactory = new ConnectionFactoryImpl();

  public @Nullable Profile getProfile(@NotNull UUID uuid) {
    return loadedProfiles.get(uuid);
  }

  public @Nullable Profile getProfile(@NotNull String name) {
    return loadedProfiles.values()
        .stream()
        .filter(profile -> profile.getName().equals(name))
        .findFirst().orElse(null);
  }

  public void addProfile(@NotNull Profile profile) {
    loadedProfiles.put(profile.getUuid(), profile);
  }

  public @NotNull List<Profile> getProfiles() {
    return new ArrayList<>(loadedProfiles.values());
  }

  public @Nullable Profile getProfile(@NotNull String name, int id) {
    return loadedProfiles.values()
            .stream()
            .filter(profile -> profile.getName().equals(name) && profile.getId() == id)
            .findFirst().orElse(null);
  }
  public Profile loadAllProfiles(@NotNull String name) {
    List<Profile> profilesFromRepository = profileRepository.getAll();
    profilesFromRepository.forEach(profile -> loadedProfiles.put(profile.getUuid(), profile));

    return loadedProfiles.values()
            .stream()
            .filter(profile -> profile.getName().equals(name))
            .findFirst().orElse(null);
  }

  public void addUser(String name, int password) {
    Connection connection = connectionFactory.createConnection(
            "jdbc:mysql://localhost:3306/dataBase",
            "user",
            "pass");

    add(connection, "???", "???");
  }

  @SneakyThrows(SQLException.class)
  public void add(Connection connection, String username, String password) {
    String sql = "INSERT INTO users (name, pin) VALUES (?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      preparedStatement.executeUpdate();
      System.out.println("User added successfully.");
  }
}
