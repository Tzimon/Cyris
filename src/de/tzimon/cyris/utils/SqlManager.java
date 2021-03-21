package de.tzimon.cyris.utils;

import de.tzimon.cyris.Cyris;

import java.sql.*;

public class SqlManager {

    private Cyris plugin;

    private String hostName;
    private int port;
    private String username;
    private String password;

    public SqlManager() {
        plugin = Cyris.getPlugin();

        hostName = plugin.getConfig().getString("sql.hostname");
        port = plugin.getConfig().getInt("sql.port");
        username = plugin.getConfig().getString("sql.username");
        password = plugin.getConfig().getString("sql.password");
    }

    public Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + hostName + ":" + port, username, password);
        } catch (SQLException ignored) {
            Logger.error("Unable to connect to mysql server");
            return null;
        }
    }

    public ResultSet executeQuery(Connection connection, String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (NullPointerException | SQLException ignored) {
            Logger.error("SQL Exception");
            return null;
        }
    }

    public void executeUpdate(Connection connection, String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (NullPointerException | SQLException ignored) {
            Logger.error("SQL Exception");
        }
    }

}
