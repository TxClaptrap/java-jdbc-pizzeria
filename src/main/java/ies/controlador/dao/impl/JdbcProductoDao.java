package ies.controlador.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ies.controlador.dao.ProductoDao;
import ies.modelo.Bebida;
import ies.modelo.Ingrediente;
import ies.modelo.Pasta;
import ies.modelo.Pizza;
import ies.modelo.Producto;
import ies.modelo.SIZE;
import ies.utils.DatabaseConf;

public class JdbcProductoDao implements ProductoDao {

    @Override
    public void insertProducto(Producto producto) throws SQLException {
        final String INSERT_PRODUCTO = "INSERT INTO  productos (nombre, precio, tipo, size) VALUES(?, ?, ?, ?)";
        //Comprobar si ya está en la base
        if (existsByName("productos", "nombre", producto.getNombre())) {
            System.out.println("El Producto ya existe: " + producto.getNombre());
            return; // Salir si existe
        }
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtProducto = conexion.prepareStatement(INSERT_PRODUCTO,
                        Statement.RETURN_GENERATED_KEYS)) {
            {
                List<Ingrediente> ingredientes = new ArrayList<>();
                // Insertar el Producto
                pstmtProducto.setString(1, producto.getNombre());
                pstmtProducto.setDouble(2, producto.getPrecio());

                ingredientes = configurarProducto(producto, pstmtProducto);

                pstmtProducto.executeUpdate();

                System.out.println("Producto insertado correctamente.");
                // Obtiene el ID generado por el AUTO_INCREMENT
                try (ResultSet generatedKeys = pstmtProducto.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getInt(1));
                    }
                }
                gestionarIngredientesYRelaciones(producto, ingredientes);
            }
        }
    }

    @Override
    public void updateProducto(int productoId) throws SQLException {
        final String UPDATE_PRODUCTO = "UPDATE productos SET nombre = ?, precio = ?, tipo = ?, size = ? WHERE id = ?";
        Producto producto = findProductoById(productoId);

        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD)) {
            List<Ingrediente> ingredientes = new ArrayList<>();
            // Actualizar las propiedades del producto
            try (PreparedStatement pstmtProducto = conexion.prepareStatement(UPDATE_PRODUCTO)) {

                pstmtProducto.setString(1, producto.getNombre());
                pstmtProducto.setDouble(2, producto.getPrecio());

                ingredientes = configurarProducto(producto, pstmtProducto);

                pstmtProducto.setInt(5, producto.getId());

                int rowsUpdated = pstmtProducto.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Producto actualizado correctamente.");
                } else {
                    System.out.println("No se encontró un producto con ese ID.");
                    return;
                }
            }

            // Actualizar las relaciones con ingredientes
            gestionarIngredientesYRelaciones(producto, ingredientes);
        }
    }

    private List<Ingrediente> configurarProducto(Producto producto, PreparedStatement pstmtProducto)
            throws SQLException {
        List<Ingrediente> ingredientes = new ArrayList<>();

        if (producto instanceof Pizza) {
            Pizza pizza = (Pizza) producto;
            pstmtProducto.setString(3, "Pizza");
            pstmtProducto.setString(4, String.valueOf(pizza.getTamano()));
            ingredientes.addAll(pizza.getListaIngredientes());
        } else if (producto instanceof Pasta) {
            Pasta pasta = (Pasta) producto;
            pstmtProducto.setString(3, "Pasta");
            pstmtProducto.setString(4, null);
            ingredientes.addAll(pasta.getListaIngredientes());
        } else if (producto instanceof Bebida) {
            Bebida bebida = (Bebida) producto;
            pstmtProducto.setString(3, "Bebida");
            pstmtProducto.setString(4, String.valueOf(bebida.getTamano()));
        } else {
            pstmtProducto.setString(3, "Desconocido");
            pstmtProducto.setString(4, null);
        }

        return ingredientes;
    }

    private void gestionarIngredientesYRelaciones(Producto producto, List<Ingrediente> ingredientes) throws SQLException {
        for (Ingrediente ingrediente : ingredientes) {
            insertIngrediente(ingrediente);
            insertRelacionProductoIngrediente(producto.getId(), ingrediente.getId());
            for (String alergeno : ingrediente.getAlergenos()) {
                insertAlergeno(alergeno);
                insertRelacionIngredienteAlergeno(ingrediente.getId(), findIdByName("alergenos", "nombre", alergeno));
            }
        }
    }
    

    @Override
    public void delete(Producto producto) throws SQLException {
        final String DELETE_PRODUCTO = "DELETE FROM productos WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtProducto = conexion.prepareStatement(DELETE_PRODUCTO)) {
            pstmtProducto.setInt(1, producto.getId());

            int rowsDeleted = pstmtProducto.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontró un producto con ese ID.");
            }
        }
    }

    /*
     * public void deleteIngrediente(Ingrediente ingrediente) throws SQLException {
     * final String DELETE_INGREDIENTE = "DELETE FROM ingredientes WHERE id = ?";
     * try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL,
     * DatabaseConf.USER,
     * DatabaseConf.PASSWORD);
     * PreparedStatement pstmtIngrediente =
     * conexion.prepareStatement(DELETE_INGREDIENTE)) {
     * pstmtIngrediente.setInt(1, ingrediente.getId());
     * 
     * int rowsDeleted = pstmtIngrediente.executeUpdate();
     * if (rowsDeleted > 0) {
     * System.out.println("Ingrediente eliminado correctamente.");
     * } else {
     * System.out.println("No se encontró un ingrediente con ese ID.");
     * }
     * }
     * }
     */

    @Override
    public Producto findProductoById(int idProducto) throws SQLException {
        final String SELECT_PRODUCTO_BY_ID = "SELECT * FROM productos WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtProducto = conexion.prepareStatement(SELECT_PRODUCTO_BY_ID)) {
            pstmtProducto.setInt(1, idProducto);

            try (ResultSet rs = pstmtProducto.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String tipo = rs.getString("tipo");
                    String size = rs.getString("size");

                    switch (tipo) {
                        case "Pizza":
                            return new Pizza(nombre, precio, SIZE.valueOf(size), null); // Asume que no hay ingredientes
                        case "Pasta":
                            return new Pasta(nombre, precio, null);
                        case "Bebida":
                            return new Bebida(nombre, precio, size != null ? SIZE.valueOf(size) : null);
                        default:
                            System.out.println("El tipo de producto no es reconocido.");
                            return null;
                    }
                } else {
                    System.out.println("No se encontró un producto con ese ID.");
                    return null;
                }
            }
        }
    }

    @Override
    public List<Producto> findAllProductos() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllProductos'");
    }

    @Override
    public List<Ingrediente> findIngredientesByProducto(int idProducto) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findIngredientesByProducto'");
    }

    @Override
    public List<String> findAlergenosByIngrediente(int idIngrediente) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAlergenoByIngrediente'");
    }

    public void insertIngrediente(Ingrediente ingrediente) throws SQLException {
        final String INSERT_INGREDIENTE = "INSERT INTO  ingredientes (nombre) VALUES(?)";
        // Comprobación si está el ingrediente en la tabla
        if (existsByName("ingredientes", "nombre", ingrediente.getNombre())) {
            System.out.println("El ingrediente ya existe: " + ingrediente.getNombre());
            return; // Salir si ya existe
        }

        // Insertarlo
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtIngrediente = conexion.prepareStatement(INSERT_INGREDIENTE,
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmtIngrediente.setString(1, ingrediente.getNombre());
            pstmtIngrediente.executeUpdate();

            System.out.println("Ingrediente insertado correctamente.");
            try (ResultSet generatedKeys = pstmtIngrediente.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ingrediente.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void insertAlergeno(String alergeno) throws SQLException {
        final String INSERT_ALERGENO = "INSERT INTO  alergenos (nombre) VALUES(?)";
        // Comprobar si está ya en la tabla
        if (existsByName("alergenos", "nombre", alergeno)) {
            System.out.println("El alérgeno ya existe: " + alergeno);
            return; // Salir si existe
        }

        // Insertar alérgeno
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtAlergeno = conexion.prepareStatement(INSERT_ALERGENO,
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmtAlergeno.setString(1, alergeno);
            pstmtAlergeno.executeUpdate();

            System.out.println("Alérgeno insertado correctamente.");
        }
    }

    public void insertRelacionProductoIngrediente(int productoId, int ingredienteId) throws SQLException {
        final String COMPROBAR_RELACION = "SELECT COUNT(*) FROM producto_ingrediente WHERE producto_id = ? AND ingrediente_id = ?";
        final String INSERT_RELACION = "INSERT INTO producto_ingrediente (producto_id, ingrediente_id) VALUES (?, ?)";

        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD)) {
            // Comprobar si la relación ya existe
            try (PreparedStatement pstmComprobar = conexion.prepareStatement(COMPROBAR_RELACION)) {
                pstmComprobar.setInt(1, productoId);
                pstmComprobar.setInt(2, ingredienteId);
                try (ResultSet rs = pstmComprobar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("La relación ya existe: producto_id = " + productoId
                                + ", ingrediente_id = " + ingredienteId);
                        return; // Salir si ya existe
                    }
                }
            }

            // Insertar la nueva relación
            try (PreparedStatement pstmtRelacion = conexion.prepareStatement(INSERT_RELACION)) {
                pstmtRelacion.setInt(1, productoId);
                pstmtRelacion.setInt(2, ingredienteId);
                pstmtRelacion.executeUpdate();
                System.out.println("Relación insertada correctamente: producto_id = " + productoId
                        + ", ingrediente_id = " + ingredienteId);
            }
        }
    }

    public void insertRelacionIngredienteAlergeno(int ingredienteId, int alergenoId) throws SQLException {
        final String COMPROBAR_RELACION = "SELECT COUNT(*) FROM ingrediente_alergeno WHERE ingrediente_id = ? AND alergeno_id = ?";
        final String INSERT_RELACION = "INSERT INTO ingrediente_alergeno (ingrediente_id, alergeno_id) VALUES (?, ?)";

        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD)) {
            // Comprobar si la relación ya existe
            try (PreparedStatement pstmComprobar = conexion.prepareStatement(COMPROBAR_RELACION)) {
                pstmComprobar.setInt(1, ingredienteId);
                pstmComprobar.setInt(2, alergenoId);
                try (ResultSet rs = pstmComprobar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("La relación ya existe: ingrediente_id = " + ingredienteId
                                + ", alergeno_id = " + alergenoId);
                        return; // Salir si la ya existe
                    }
                }
            }

            // Insertar la nueva relación
            try (PreparedStatement pstmtRelacion = conexion.prepareStatement(INSERT_RELACION)) {
                pstmtRelacion.setInt(1, ingredienteId);
                pstmtRelacion.setInt(2, alergenoId);
                pstmtRelacion.executeUpdate();
                System.out.println("Relación insertada correctamente: ingrediente_id = " + ingredienteId
                        + ", alergeno_id = " + alergenoId);
            }
        }
    }

    private boolean existsByName(String tabla, String columna, String nombre) throws SQLException {
        final String COUNT_DUPLICADOS = "SELECT COUNT(*) FROM " + tabla + " WHERE " + columna + " = ?";
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtContar = conexion.prepareStatement(COUNT_DUPLICADOS)) {
            pstmtContar.setString(1, nombre);
            try (ResultSet rs = pstmtContar.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Devuelve true si el conteo es mayor que 0
                }
            }
        }
        return false;
    }

    private int findIdByName(String tabla, String columna, String nombre) throws SQLException {
        final String SELECT_ID = "SELECT id FROM " + tabla + " WHERE " + columna + " = ?";
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmt = conexion.prepareStatement(SELECT_ID)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("No se encontró un registro con el nombre: " + nombre);
    }

    /*
     * // Tiene sentido, no? TO DO: ver cómo lo aplico
     * public void insertIngredienteAlergeno(String ingredienteNombre, String
     * alergenoNombre) throws SQLException {
     * int ingredienteId = findIdByName("ingredientes", "nombre",
     * ingredienteNombre);
     * int alergenoId = findIdByName("alergenos", "nombre", alergenoNombre);
     * insertRelacionIngredienteAlergeno(ingredienteId, alergenoId);
     * }
     */
}
