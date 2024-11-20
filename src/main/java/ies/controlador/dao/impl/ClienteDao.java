package ies.controlador.dao.impl;

import java.sql.SQLException;
import java.util.List;

import ies.modelo.Cliente;

public interface ClienteDao {

    public void insert(Cliente cliente) throws SQLException;
    public void update(Cliente cliente) throws SQLException;
    public void delete(Cliente cliente) throws SQLException;
    public Cliente findByEmail(String email) throws SQLException;
    public Cliente findById(int id) throws SQLException;
    public List<Cliente> findAll() throws SQLException;

}
