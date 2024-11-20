package ies.controlador.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ies.modelo.Cliente;
import ies.utils.DatabaseConf;

public class JdbcClienteDao implements ClienteDao {

    final String INSERT_CLIENTE = "INSERT INTO clientes (dni, nombre, direccion, telefono, email, password) VALUES(?, ?, ?, ?, ?, ?)";
    final String FIND_EMAIL= "SELECT clientes.id, clientes.dni, clientes.nombre, clientes.direccion, clientes.telefono, clientes.email, clientes.password WHERE clientes.emnail = ?";

    @Override
    public void insert(Cliente cliente) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Cliente findById(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Cliente findByEmail(String email) throws SQLException {
try (Connection conexion = DriverManager.getConnection(DatabaseConf.URL, DatabaseConf.USER,
                DatabaseConf.PASSWORD);
                PreparedStatement pstmtCliente = conexion.prepareStatement(FIND_EMAIL)) {
            pstmtCliente.setString(6, email);
            ResultSet rSet = pstmtCliente.executeQuery();

                Cliente cliente = new Cliente(rSet.getInt("id"), rSet.getString("clientes.nombre"),
                    rSet.getString("clientes.direccion"), rSet.getString("clientes.telefono"), rSet.getString("clientes.email"), rSet.getString("clientes.password"));
                
                System.out.print("\nCliente encontrado correctamente:");
                return cliente;
            }

            else {
                return null;
            }
        }
    }

    @Override
    public List<Cliente> findAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
