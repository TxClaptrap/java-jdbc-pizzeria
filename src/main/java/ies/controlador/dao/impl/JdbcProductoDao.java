package ies.controlador.dao.impl;

import java.sql.SQLException;
import java.util.List;

import ies.controlador.dao.ProductoDao;
import ies.modelo.Ingrediente;
import ies.modelo.Producto;

public class JdbcProductoDao implements ProductoDao {

    final String INSERT_PRODUCTO = "INSERT INTO  (dni, nombre, direccion, telefono, email, password) VALUES(?, ?, ?, ?, ?, ?)";

    @Override
    public void insert(Producto Producto) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
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
