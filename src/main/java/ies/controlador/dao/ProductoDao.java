package ies.controlador.dao;

import java.sql.SQLException;
import java.util.List;

import ies.modelo.Ingrediente;
import ies.modelo.Producto;

public interface ProductoDao {

    public void insert(Producto Producto) throws SQLException;
    public void update(Producto Producto) throws SQLException;
    public void delete(Producto Producto) throws SQLException;
    public List <Producto> findAllProductos() throws SQLException;
    public List <Ingrediente> findIngredientesByProducto(int idProducto) throws SQLException;
    public List <String> findAlergenoByIngrediente(int idIngrediente) throws SQLException;
    


}
