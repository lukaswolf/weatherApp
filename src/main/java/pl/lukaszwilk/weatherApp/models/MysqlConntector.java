package pl.lukaszwilk.weatherApp.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConntector {
    private static final String SQL_LINK = "jdbc:mysql://5.135.218.27:3306/wilklukasz?useUnicode=yes&characterEncoding=UTF-8";
    private static final String SQL_USER = "oskar";
    private static final String SQL_PASSWORD = "10135886";
    private static final String SQL_CLASS = "com.mysql.jdbc.Driver";

    private static MysqlConntector connector = new MysqlConntector();

    public static MysqlConntector getInstace() {
        return connector;
    }

    private Connection connection;

    private MysqlConntector() {
        connect();

    }

    private void connect() {
        try {
            Class.forName(SQL_CLASS);
            connection = DriverManager.getConnection(SQL_LINK, SQL_USER, SQL_PASSWORD);
            System.out.println("połaczono");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

}
