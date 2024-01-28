package dev.crashbandicootfm.connection;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection(@NotNull String url, @NotNull String user, @NotNull String password);
}
