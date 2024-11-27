package ies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import ies.controlador.ControladorCliente;
import ies.modelo.Cliente;
import ies.utils.DatabaseConf;

import static org.junit.jupiter.api.Assertions.*;

class ControladorClienteTest {

    ControladorCliente controladorCliente;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConf.dropAndCreateTables();
        controladorCliente = new ControladorCliente();
    }

    @Test
    void testRegistrarCliente() throws SQLException {
        Cliente cliente = new Cliente("12345678A", "Juan", "Calle 1", "600111222", "juan@correo.com", "password");
        controladorCliente.registrarCliente(cliente);

        Cliente clienteEncontrado = controladorCliente.encontrarPorEmail("juan@correo.com");
        assertNotNull(clienteEncontrado, "El cliente debería haberse registrado.");
        assertEquals("Juan", clienteEncontrado.getNombre(), "El nombre del cliente no coincide.");
    }

    @Test
    void testRegistrarClienteDuplicado() throws SQLException {
        Cliente cliente = new Cliente("12345678A", "Juan", "Calle 1", "600111222", "juan@correo.com", "password");
        controladorCliente.registrarCliente(cliente);

        assertThrows(IllegalArgumentException.class, () -> {
            controladorCliente.registrarCliente(cliente);
        }, "Registrar un cliente duplicado debería lanzar una excepción.");
    }

    @Test
    void testEncontrarTodosLosClientes() throws SQLException {
        Cliente cliente1 = new Cliente("12345678A", "Juan", "Calle 1", "600111222", "juan@correo.com", "password");
        Cliente cliente2 = new Cliente("87654321B", "Ana", "Calle 2", "700333444", "ana@correo.com", "password");
        controladorCliente.registrarCliente(cliente1);
        controladorCliente.registrarCliente(cliente2);

        List<Cliente> clientes = controladorCliente.encontrarTodos();
        assertEquals(2, clientes.size(), "Debería haber dos clientes registrados.");
    }
}
