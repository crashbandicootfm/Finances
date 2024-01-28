package dev.crashbandicootfm.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class ConnectionFactoryImpl implements ConnectionFactory {

    @Override
    @SneakyThrows(SQLException.class)
    public Connection createConnection(
            @NotNull String url,
            @NotNull String user,
            @NotNull String password
    ) {
        return DriverManager.getConnection(url, user, password);
    }
}
