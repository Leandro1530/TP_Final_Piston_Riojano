/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Repuesto;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author gustavo
 */
public interface RepuestoDAO {

    void insertar(Repuesto repuesto) throws SQLException;

    void actualizar(Repuesto repuesto) throws SQLException;

    void actualizarPrecioYStock(String codigo, double nuevoPrecio, int nuevoStock) throws SQLException;

    void eliminar(String codigo) throws SQLException;

    Repuesto buscarPorCodigo(String codigo) throws SQLException;

    List<Repuesto> listarTodos() throws SQLException;

}