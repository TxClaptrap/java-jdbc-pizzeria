package ies.controlador.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ies.controlador.dao.ProductoDao;
import ies.modelo.Bebida;
import ies.modelo.Ingrediente;
import ies.modelo.Pasta;
import ies.modelo.Pizza;
import ies.modelo.Producto;
import ies.utils.DatabaseConf;

public class JdbcProductoDao implements ProductoDao {

    final String INSERT_PRODUCTO = "INSERT INTO  productos (nombre, precio, tipo, size) VALUES(?, ?, ?, ?)";

    @Override
    public void insert(Producto producto) throws SQLException {
        try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtCliente = conexion.prepareStatement(INSERT_PRODUCTO,
                        Statement.RETURN_GENERATED_KEYS)) {
            {
                // Insertar el Producto
                pstmtCliente.setString(1, producto.getNombre());
                pstmtCliente.setDouble(2, producto.getPrecio());
                if (producto instanceof Pizza) {
                    Pizza pizza = (Pizza) producto;
                    pstmtCliente.setString(3, "Pizza");
                    pstmtCliente.setString(4, String.valueOf(pizza.getTamano()));
                } else if (producto instanceof Pasta) {
                    pstmtCliente.setString(3, "Pasta");
                    pstmtCliente.setString(4, null);
                } else if (producto instanceof Bebida) {
                    Bebida bebida = (Bebida) producto;
                    pstmtCliente.setString(3, "Bebida");
                    pstmtCliente.setString(4, String.valueOf(bebida.getTamano()));
                } else {
                    pstmtCliente.setString(3, "Desconocido");
                    pstmtCliente.setString(4, null);

                }
                pstmtCliente.executeUpdate();

                System.out.println("Producto insertado correctamente.");
                // Obtiene el ID generado por el AUTO_INCREMENT
                try (ResultSet generatedKeys = pstmtCliente.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void update(Producto Producto) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Producto Producto) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
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
    public List<String> findAlergenoByIngrediente(int idIngrediente) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAlergenoByIngrediente'");
    }

}
