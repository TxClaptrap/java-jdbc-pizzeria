package ies.controlador.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ies.controlador.dao.ClienteDao;
import ies.controlador.dao.PedidoDao;
import ies.controlador.dao.ProductoDao;
import ies.modelo.Cliente;
import ies.modelo.EstadoPedido;
import ies.modelo.LineaPedido;
import ies.modelo.Pedido;
import ies.utils.DatabaseConf;

public class JdbcPedido implements PedidoDao {

    ClienteDao clienteDao = new JdbcClienteDao();
    ProductoDao 

    @Override
    public void insertPedido(Pedido pedido) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertPedido'");
    }

    @Override
    public void updatePedido(Pedido pedido) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePedido'");
    }

    @Override
    public void deletePedido(Pedido pedido) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePedido'");
    }

    @Override
    public Pedido findPedidoById(int idPedido) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPedidoById'");
    }

    @Override
    public List<Pedido> findPedidoByClienteId(int ClienteId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPedidoByClienteId'");
    }

    @Override
    public List<Pedido> findPedidosByEstado(EstadoPedido estado) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT pedidos.id, pedidos.fecha, pedidos.precio_total, pedidos.cliente_id " + "FROM pedidos " + "WHERE pedidos.estado = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER, DatabaseConf.PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, estado.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                double precioTotal = rs.getDouble("precio_total");
                int clienteId = rs.getInt("cliente_id");

                // Recuperar el cliente por id
                Cliente cliente = clienteDao.findClienteById(clienteId);

                // Crear el pedido
                Pedido pedido = new Pedido(id, cliente);
                pedido.setFecha(fecha);
                pedido.setPrecioTotal(precioTotal);
                pedido.setEstado(estado);

                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    @Override
    public List<LineaPedido> findLineasPedidoByPedidoId(int PedidpId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findLineasPedidoByPedidoId'");
    }

}
