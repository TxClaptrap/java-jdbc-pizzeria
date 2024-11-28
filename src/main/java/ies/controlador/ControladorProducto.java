package ies.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import ies.controlador.dao.ClienteDao;
import ies.controlador.dao.ProductoDao;
import ies.controlador.dao.impl.JdbcClienteDao;
import ies.controlador.dao.impl.JdbcProductoDao;
import ies.modelo.Ingrediente;
import ies.modelo.Producto;

public class ControladorProducto {

ProductoDao productoDao = new JdbcProductoDao();

    public void registrarProducto(Producto producto) throws SQLException {
        productoDao.insertProducto(producto);
    }

    public void actualizarProducto(int productoId) throws SQLException {
        productoDao.updateProducto(productoId);
    }

    public Producto enontrarProductoById(int idProducto) throws SQLException {
        return productoDao.findProductoById(idProducto);
    }

    /* 
    GestorFicheros gestorFicheros;

    public ControladorProducto() {
        gestorFicheros = new GestorFicheros();
    }

    //Métodos de encapsulado, para no hacerlo todo desde GestorFicheros
    public boolean exportarIngredientesCSV(List<Ingrediente> ingredientes)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, FileNotFoundException {
        return gestorFicheros.exportarCSV(ingredientes);
    }

    public List<Ingrediente> importarIngredientesCSV() throws FileNotFoundException, IOException {
        return gestorFicheros.importarCSV();
    }
        */

}
