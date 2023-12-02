package Database.Managers;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Провайдер подключения к базе данных SQLite.
 *
 * @author Будчанин В.А.
 * @version  1.0
 */
public class SQLiteConnectionProvider {
    /**
     * URL базы данных.
     */
    private static final String DB_URL = "jdbc:sqlite:D:\\3COURSE\\Java\\CourseWork\\CourseWorkApp\\src\\Database\\DatabaseFiles\\StudentGroupDB.sqlite";

    /**
     * Соединение с базой данных.
     */
    private Connection connection;

    /**
     * Создает новый провайдер подключения к базе данных SQLite.
     */
    public SQLiteConnectionProvider() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает подключение к базе данных.
     *
     * @return объект Connection для подключения к базе данных
     */
    public Connection getConnection() {
        if (connection == null) {
            try {
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);
                connection = DriverManager.getConnection(DB_URL, config.toProperties());
                System.out.println("Connected to SQLite database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Закрывает подключение к базе данных.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to SQLite database closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}