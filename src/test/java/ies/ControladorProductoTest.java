package ies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import ies.controlador.ControladorProducto;
import ies.modelo.Pizza;
import ies.modelo.SIZE;
import ies.utils.DatabaseConf;

import static org.junit.jupiter.api.Assertions.*;

class ControladorProductoTest {

    ControladorProducto controladorProducto;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConf.dropAndCreateTables();
        controladorProducto = new ControladorProducto();
    }

    @Test
    void testRegistrarProductoPizza() throws SQLException {
        Pizza pizza = new Pizza("Carbonara", 12.5, SIZE.MEDIANO, null);
        controladorProducto.registrarProducto(pizza);

        // No se especifica método para encontrar productos en el código inicial
        // Supongamos que hay una implementación para validar.
        assertNotNull(pizza.getId(), "El ID de la pizza debería haberse generado.");
    }
}
