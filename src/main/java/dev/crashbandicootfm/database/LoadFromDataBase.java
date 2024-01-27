package dev.crashbandicootfm.database;

import dev.crashbandicootfm.profile.Profile;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface LoadFromDataBase {
    default List<Profile> fetchProfilesFromDatabase(Connection connection) throws SQLException {
        @NotNull List<Profile> profiles = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int pin = resultSet.getInt("pin");

            Profile profile = new Profile(id, name, pin);
            profiles.add(profile);
        }

        return profiles;
    }
}
