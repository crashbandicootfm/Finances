package dev.crashbandicootfm.repository;

import dev.crashbandicootfm.connection.ConnectionFactory;
import dev.crashbandicootfm.profile.Profile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileRepositoryImpl implements ProfileRepository {

    @NotNull
    ConnectionFactory connectionFactory;

    @Override
    @SneakyThrows(SQLException.class)
    public @NotNull @Unmodifiable List<Profile> getAll() {
        List<Profile> profiles = new ArrayList<>();
        Connection connection = connectionFactory.createConnection(
                "jdbc:mysql://localhost:3306/dataBase",
                "user",
                "pass");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int pin = resultSet.getInt("pin");

            Profile profile = new Profile(id, name, pin);
            profiles.add(profile);
        }

        return List.copyOf(profiles);
    }
}
