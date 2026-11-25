/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Mecanico;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author river
 */
public interface MecanicoDAO {void insertar(Mecanico mecanico) throws SQLException;

    void actualizar(Mecanico mecanico) throws SQLException;

    void eliminar(int id) throws SQLException;

    Mecanico buscarPorId(int id) throws SQLException;

    Mecanico buscarPorLegajo(String numeroLegajo) throws SQLException;

    List<Mecanico> listarTodos() throws SQLException;

    boolean tieneOrdenesTrabajo(int idMecanico) throws SQLException;
}
