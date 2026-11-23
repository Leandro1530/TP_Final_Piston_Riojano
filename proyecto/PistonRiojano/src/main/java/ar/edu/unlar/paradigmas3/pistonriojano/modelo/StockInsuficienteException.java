/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

/**
 *
 * @author Leandro
 */
public class StockInsuficienteException extends Exception {

    public StockInsuficienteException() {
        super("Stock insuficiente para realizar la operación.");
    }

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
