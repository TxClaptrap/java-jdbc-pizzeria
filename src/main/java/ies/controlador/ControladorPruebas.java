package ies.controlador;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import ies.modelo.Cliente;
import ies.modelo.Pizza;
import ies.modelo.Producto;
import ies.modelo.ProductosSeparados;

public class ControladorPruebas {
    Varios gestorFicheros = new Varios();

    public boolean exportarAdministradoresTXT(List<Cliente> administradores) throws IOException {
        return gestorFicheros.exportarAdministradoresTXT(administradores);
    }

    public ProductosSeparados separarProductos(List<Producto> productos) throws Exception {
        return gestorFicheros.separarProductosEnListas(productos);
    }

    public boolean exportarPizzaXML(List<Pizza> pizzas) throws JAXBException {
        return gestorFicheros.exportarPizzaXML(pizzas);
    }

    public List<Pizza> importarPizzasXML() throws JAXBException {
        return gestorFicheros.importarPizzaXML();
    }
}
