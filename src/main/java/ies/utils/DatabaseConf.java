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

    public static final String CREATE_TABLE_PRODUCTOS = "CREATE TABLE IF NOT EXISTS productos (\r\n" +
            "    id INT PRIMARY KEY AUTO_INCREMENT,\r\n" +
            "    nombre VARCHAR(255) NOT NULL UNIQUE,\r\n" +
            "    precio DOUBLE NOT NULL,\r\n" +
            "    tipo VARCHAR(255) NOT NULL,\r\n" +
            "    size VARCHAR(255) DEFAULT NULL\r\n" +
            ");\r\n";

    public static final String CREATE_TABLE_PRODUCTO_INGREDIENTE = "CREATE TABLE IF NOT EXISTS producto_ingrediente (\r\n"
            +
            "    producto_id INT,\r\n" +
            "    ingrediente_id INT,\r\n" +
            "    PRIMARY KEY (producto_id, ingrediente_id),\r\n" + // Clave primaria compuesta
            "    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" +
            "    FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n" +
            ");\r\n";

    public static final String CREATE_TABLE_INGREDIENTES = "CREATE TABLE IF NOT EXISTS ingredientes (\r\n" +
            "    id INT PRIMARY KEY AUTO_INCREMENT,\r\n" +
            "    nombre VARCHAR(255) NOT NULL UNIQUE\r\n" +
            ");\r\n";

    public static final String CREATE_TABLE_ALERGENOS = "CREATE TABLE IF NOT EXISTS alergenos (\r\n" +
            "    id INT PRIMARY KEY AUTO_INCREMENT,\r\n" +
            "    nombre VARCHAR(255) NOT NULL UNIQUE\r\n" +
            ");\r\n";

    public static final String CREATE_TABLE_INGREDIENTE_ALERGENO = "CREATE TABLE IF NOT EXISTS ingrediente_alergeno (\r\n"
            +
            "    ingrediente_id INT,\r\n" +
            "    alergeno_id INT,\r\n" +
            "    PRIMARY KEY (ingrediente_id, alergeno_id),\r\n" +
            "    FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" +
            "    FOREIGN KEY (alergeno_id) REFERENCES alergenos(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n" +
            ");\r\n";

    private static final String DROP_TABLE_PRODUCTO_INGREDIENTE = "DROP TABLE IF EXISTS producto_ingrediente";
    private static final String DROP_TABLE_INGREDIENTE_ALERGENO = "DROP TABLE IF EXISTS ingrediente_alergeno";
    private static final String DROP_TABLE_CLIENTES = "DROP TABLE IF EXISTS clientes";
    private static final String DROP_TABLE_PRODUCTOS = "DROP TABLE IF EXISTS productos";
    private static final String DROP_TABLE_INGREDIENTES = "DROP TABLE IF EXISTS ingredientes";
    private static final String DROP_TABLE_ALERGENOS = "DROP TABLE IF EXISTS alergenos";

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

    public static void dropAndCreateTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
    
            // Drops en orden 
            stmt.execute(DROP_TABLE_PRODUCTO_INGREDIENTE);
            stmt.execute(DROP_TABLE_INGREDIENTE_ALERGENO);
            stmt.execute(DROP_TABLE_CLIENTES);
            stmt.execute(DROP_TABLE_PRODUCTOS);
            stmt.execute(DROP_TABLE_INGREDIENTES);
            stmt.execute(DROP_TABLE_ALERGENOS);
            System.out.println("Tablas eliminadas correctamente");
    
            // Creaciones en orden 
            stmt.execute(CREATE_TABLE_PRODUCTOS);
            stmt.execute(CREATE_TABLE_INGREDIENTES);
            stmt.execute(CREATE_TABLE_ALERGENOS);
            stmt.execute(CREATE_TABLE_CLIENTE);
            stmt.execute(CREATE_TABLE_PRODUCTO_INGREDIENTE);
            stmt.execute(CREATE_TABLE_INGREDIENTE_ALERGENO);
            System.out.println("Tablas creadas correctamente");
        }
    }
    
}