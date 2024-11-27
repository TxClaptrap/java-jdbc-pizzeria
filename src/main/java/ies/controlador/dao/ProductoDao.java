package ies.controlador.dao;

import java.sql.SQLException;
import java.util.List;

import ies.modelo.Ingrediente;
import ies.modelo.Producto;

public interface ProductoDao {

    public void insertProducto(Producto producto) throws SQLException;
    public void insertIngrediente(Ingrediente ingrediente) throws SQLException;
    public void insertAlergeno(String alergeno) throws SQLException;
    public void insertRelacionIngredienteAlergeno(int ingredienteId, int alergenoId) throws SQLException;
    public void update(Producto producto) throws SQLException;
    public void delete(Producto producto) throws SQLException;
    public List <Producto> findAllProductos() throws SQLException;
    public List <Ingrediente> findIngredientesByProducto(int idProducto) throws SQLException;
    public List <String> findAlergenosByIngrediente(int idIngrediente) throws SQLException;
    


}
