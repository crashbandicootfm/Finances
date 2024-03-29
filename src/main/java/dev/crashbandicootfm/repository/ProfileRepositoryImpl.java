package dev.crashbandicootfm.repository;

import dev.crashbandicootfm.service.profile.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileRepositoryImpl implements ProfileRepository {

  @NotNull
  Connection connection;

  @Override
  @SneakyThrows(SQLException.class)
  public @NotNull @Unmodifiable List<Profile> getAll() {
    List<Profile> profiles = new ArrayList<>();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

    while (resultSet.next()) {
      UUID id = UUID.fromString(resultSet.getString("id"));
      String name = resultSet.getString("name");
      int pin = resultSet.getInt("pin");

      Profile profile = new Profile(id, name, pin);
      profiles.add(profile);
    }

    return List.copyOf(profiles);
  }

  @Override
  @SneakyThrows
  public Profile getProfileByName(@NotNull String name) {
    String sql1 = "SELECT * FROM users WHERE name = ?";
    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
    preparedStatement1.setString(1, name);
    ResultSet resultSet1 = preparedStatement1.executeQuery();
    if (resultSet1.next()) {
      UUID id = UUID.fromString(resultSet1.getString("id"));
      int pin = resultSet1.getInt("pin");

      return new Profile(id, name, pin);
    }
    return null;
  }

  @Override
  @SneakyThrows
  public void save(@NotNull Profile profile) {
    String sql = "INSERT INTO users (id, name, pin) VALUES (?, ?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    UUID uuid = UUID.fromString(profile.getUuid().toString());
    preparedStatement.setString(1, String.valueOf(uuid));
    preparedStatement.setString(2, profile.getName());
    preparedStatement.setInt(3, profile.getPin());
    preparedStatement.executeUpdate();
  }

  @Override
  @SneakyThrows
  public Set<Integer> getUserIds() {
    Set<Integer> existingIDs = new HashSet<>();

    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT id FROM users");

    while(resultSet.next()) {
      existingIDs.add(resultSet.getInt("id"));
    }

    return existingIDs;
  }
}
