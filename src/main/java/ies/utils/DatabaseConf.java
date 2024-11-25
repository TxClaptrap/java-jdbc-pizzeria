package ies.utils;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConf {
    public static final String URL = "jdbc:mysql://localhost:3306/pizzeria";
    public static final String USER = "root";
    public static final String PASSWORD = "admin";
    public static final String CREATE_TABLE_CLIENTE = "CREATE TABLE IF NOT EXISTS clientes (\r\n" +
            "    id INT PRIMARY KEY AUTO_INCREMENT,\r\n" +
            "    dni VARCHAR(255) NOT NULL UNIQUE,\r\n" +
            "    nombre VARCHAR(255) NOT NULL,\r\n" +
            "    direccion VARCHAR(255) NOT NULL,\r\n" +
            "    telefono VARCHAR(255) NULL UNIQUE,\r\n" +
            "    email VARCHAR(255) NOT NULL UNIQUE,\r\n" +
            "    password VARCHAR(255) NOT NULL,\r\n" +
            "    administrador BOOL DEFAULT false\r\n" +
            ");\r\n";

    private static final String DROP_TABLE_CLIENTES = "DROP TABLE IF EXISTS clientes";

    public static void createTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_CLIENTE);
            System.out.println("Tabla creada correctamente");
        }
    }

    public static void dropTableClientes() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.execute(DROP_TABLE_CLIENTES);
            System.out.println("Tablas borradas correctamente");
        }
    }

    public static void dropAndCreateTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.execute(DROP_TABLE_CLIENTES);
            System.out.println("Tablas borradas correctamente");
            stmt.execute(CREATE_TABLE_CLIENTE);
            System.out.println("Tablas creadas correctamente");
        }
    }
}