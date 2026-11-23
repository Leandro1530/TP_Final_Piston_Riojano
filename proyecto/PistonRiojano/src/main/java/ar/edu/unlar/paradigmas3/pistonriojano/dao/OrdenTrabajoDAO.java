/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.OrdenTrabajo;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.StockInsuficienteException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Leandro
 */
public interface OrdenTrabajoDAO {

    void insertar(OrdenTrabajo orden) throws SQLException, StockInsuficienteException;

    void actualizar(OrdenTrabajo orden) throws SQLException;

    boolean eliminar(int id) throws SQLException;

    OrdenTrabajo buscarPorId(int id) throws SQLException;

    OrdenTrabajo buscarPorNumero(int numero) throws SQLException;

    List<OrdenTrabajo> listarTodos() throws SQLException;
}
