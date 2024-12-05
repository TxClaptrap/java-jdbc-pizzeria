package ies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ies.controlador.dao.impl.JdbcClienteDao;
import ies.controlador.dao.impl.JdbcPedidoDao;
import ies.controlador.dao.impl.JdbcProductoDao;
import ies.modelo.Bebida;
import ies.modelo.Cliente;
import ies.modelo.EstadoPedido;
import ies.modelo.LineaPedido;
import ies.modelo.Pagar_Efectivo;
import ies.modelo.Pagar_Tarjeta;
import ies.modelo.Pedido;
import ies.modelo.Pizza;
import ies.modelo.Producto;
import ies.modelo.SIZE;
import ies.utils.DatabaseConf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorPedidoTest {

    private JdbcPedidoDao pedidoDao;
    private JdbcClienteDao clienteDao;
    private JdbcProductoDao productoDao;

    @BeforeEach
    public void prepararTest() throws SQLException {
        DatabaseConf.dropAndCreateTables();
        pedidoDao = new JdbcPedidoDao();
        clienteDao = new JdbcClienteDao();
        productoDao = new JdbcProductoDao();
        Cliente cliente = new Cliente("111111", "Pepe", "C/Falsa", "45353454", "fsd@nj.com", "patata");
        clienteDao.insertCliente(cliente);
        Producto producto1 = new Pizza("Pizza Margarita", 9, SIZE.MEDIANO, new ArrayList<>());
        Producto producto2 = new Bebida("Coca Cola", 2, SIZE.PEQUENO);
        productoDao.insertProducto(producto1);
        productoDao.insertProducto(producto2);
    }

    @Test
    public void testInsertPedido() throws SQLException {
        Cliente cliente = clienteDao.findClienteById(1);
        Pedido pedido = new Pedido(cliente);
        LineaPedido linea1 = new LineaPedido(2, productoDao.findProductoById(1));
        LineaPedido linea2 = new LineaPedido(3, productoDao.findProductoById(2));
        pedido.getListaLineaPedidos().add(linea1);
        pedido.getListaLineaPedidos().add(linea2);
        pedido.setMetodoPago(new Pagar_Tarjeta());
        pedidoDao.insertPedido(pedido);
        Pedido dbPedido = pedidoDao.findPedidoById(pedido.getId());
        assertNotNull(dbPedido);
        assertEquals(pedido.getId(), dbPedido.getId());
        assertEquals(cliente.getId(), dbPedido.getCliente().getId());
        assertEquals(2, dbPedido.getListaLineaPedidos().size());
    }

    @Test
    public void testUpdatePedido() throws SQLException {
        Cliente cliente = clienteDao.findClienteById(1);
        Pedido pedido = new Pedido(cliente);
        LineaPedido linea1 = new LineaPedido(2, productoDao.findProductoById(1));
        pedido.getListaLineaPedidos().add(linea1);
        pedido.setMetodoPago(new Pagar_Efectivo());
        pedidoDao.insertPedido(pedido);
        pedido.setEstado(EstadoPedido.FINALIZADO);
        LineaPedido linea2 = new LineaPedido(1, productoDao.findProductoById(2));
        pedido.getListaLineaPedidos().add(linea2);
        pedidoDao.updatePedido(pedido);
        Pedido updatedPedido = pedidoDao.findPedidoById(pedido.getId());
        assertEquals(EstadoPedido.FINALIZADO, updatedPedido.getEstado());
        assertEquals(2, updatedPedido.getListaLineaPedidos().size());
    }

    @Test
    public void testDeletePedido() throws SQLException {
        Cliente cliente = clienteDao.findClienteById(1);
        Pedido pedido = new Pedido(cliente);
        LineaPedido linea1 = new LineaPedido(2, productoDao.findProductoById(1));
        pedido.getListaLineaPedidos().add(linea1);
        pedido.setMetodoPago(new Pagar_Tarjeta());
        pedidoDao.insertPedido(pedido);
        pedidoDao.deletePedido(pedido);
        Pedido deletedPedido = pedidoDao.findPedidoById(pedido.getId());
        assertNull(deletedPedido);
    }

    @Test
    public void testFindPedidoById() throws SQLException {
        Cliente cliente = clienteDao.findClienteById(1);
        Pedido pedido = new Pedido(cliente);
        LineaPedido linea1 = new LineaPedido(2, productoDao.findProductoById(1));
        pedido.getListaLineaPedidos().add(linea1);
        pedido.setMetodoPago(new Pagar_Efectivo());
        pedidoDao.insertPedido(pedido);
        Pedido dbPedido = pedidoDao.findPedidoById(pedido.getId());
        assertNotNull(dbPedido);
        assertEquals(cliente.getId(), dbPedido.getCliente().getId());
        assertEquals(1, dbPedido.getListaLineaPedidos().size());
    }

    @Test
    public void testFindPedidosByClienteId() throws SQLException {
        Cliente cliente = clienteDao.findClienteById(1);
        Pedido pedido1 = new Pedido(cliente);
        Pedido pedido2 = new Pedido(cliente);
        pedidoDao.insertPedido(pedido1);
        pedidoDao.insertPedido(pedido2);
        List<Pedido> pedidos = pedidoDao.findPedidosByClienteId(cliente.getId());
        assertEquals(2, pedidos.size());
    }

    @Test
    public void testFindPedidosByEstado() throws SQLException {
        Cliente cliente = clienteDao.findClienteById(1);
        Pedido pedido = new Pedido(cliente);
        pedido.setEstado(EstadoPedido.ENTREGADO);
        pedidoDao.insertPedido(pedido);
        List<Pedido> pedidos = pedidoDao.findPedidosByEstado(EstadoPedido.ENTREGADO);
        assertEquals(1, pedidos.size());
        assertEquals(EstadoPedido.ENTREGADO, pedidos.get(0).getEstado());
    }
}
