/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Cliente;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author river
 */
public interface ClienteDAO {
    
    void insertar(Cliente cliente) throws SQLException;

    void actualizar(Cliente cliente) throws SQLException;

    void eliminar(String dni) throws SQLException;

    Cliente buscarPorDni(String dni) throws SQLException;

    List<Cliente> listarTodos() throws SQLException;

    boolean tieneVehiculos(String dni) throws SQLException;
    
}
