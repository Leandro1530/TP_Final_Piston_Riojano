/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Leandro
 */
@DisplayName("Pruebas unitarias del modelo OrdenTrabajo y entidades relacionadas")
public class OrdenTrabajoTest {

    private Mecanico mecanico;
    private Auto autoDiesel;
    private Moto motoChica;
    private Repuesto filtro;
    private Repuesto pastillas;
    private OrdenTrabajo orden;

    @BeforeAll
    static void iniciarSuite() {
        System.out.println("Iniciando suite de pruebas unitarias de Pistón Riojano...");
    }

    @AfterAll
    static void finalizarSuite() {
        System.out.println("Finalizando suite de pruebas unitarias de Pistón Riojano.");
    }

    @BeforeEach
    void configurarEscenario() {
        mecanico = new Mecanico(101, Especialidad.MOTOR, 5000.0, 1, "Carlos", "Gómez");
        autoDiesel = new Auto("AB123CD", "Toyota", "Corolla", 2018, 4, TipoCombustible.DIESEL);
        motoChica = new Moto("123ABC", "Honda", "CB125", 2020, 125);
        filtro = new Repuesto("R001", "Filtro de aceite", 3500.0, 10);
        pastillas = new Repuesto("R002", "Pastillas de freno", 12000.0, 4);
        orden = new OrdenTrabajo(1, "Service general", autoDiesel, mecanico);
    }

    @Test
    @DisplayName("Polimorfismo: Auto DIESEL devuelve 1.20 y NAFTA/GNC devuelven 1.00")
    void testCoeficienteAuto() {
        Vehiculo autoNafta = new Auto("AA111AA", "Fiat", "Cronos", 2022, 4, TipoCombustible.NAFTA);
        Vehiculo autoGnc = new Auto("BB222BB", "Renault", "Logan", 2021, 4, TipoCombustible.GNC);

        Assertions.assertEquals(1.20, autoDiesel.calcularCoeficiente(), 0.001);
        Assertions.assertEquals(1.00, autoNafta.calcularCoeficiente(), 0.001);
        Assertions.assertEquals(1.00, autoGnc.calcularCoeficiente(), 0.001);
    }

    @Test
    @DisplayName("Polimorfismo: Moto <= 250cc devuelve 0.80 y > 250cc devuelve 1.00")
    void testCoeficienteMoto() {
        Vehiculo motoLimite = new Moto("250AAA", "Yamaha", "FZ25", 2023, 250);
        Vehiculo motoGrande = new Moto("600BBB", "Kawasaki", "Z650", 2024, 650);

        Assertions.assertEquals(0.80, motoChica.calcularCoeficiente(), 0.001);
        Assertions.assertEquals(0.80, motoLimite.calcularCoeficiente(), 0.001);
        Assertions.assertEquals(1.00, motoGrande.calcularCoeficiente(), 0.001);
    }

    @Test
    @DisplayName("agregarRepuesto descuenta stock, crea DetalleOrden y congela precioAplicado")
    void testAgregarRepuestoDescuentaYCongelaPrecio() throws StockInsuficienteException {
        orden.agregarRepuesto(filtro, 2);

        Assertions.assertEquals(8, filtro.getStockDisponible());
        Assertions.assertEquals(1, orden.getDetalles().size());

        DetalleOrden detalle = orden.getDetalles().get(0);
        Assertions.assertNotNull(detalle);
        Assertions.assertEquals(3500.0, detalle.getPrecioAplicado(), 0.001);
        Assertions.assertEquals(7000.0, detalle.calcularSubtotal(), 0.001);

        // Si el precio del repuesto sube luego, el detalle conserva el precio congelado
        filtro.setPrecioUnitarioActual(9999.0);
        Assertions.assertEquals(3500.0, detalle.getPrecioAplicado(), 0.001);
        Assertions.assertEquals(7000.0, orden.calcularTotalRepuestos(), 0.001);
    }

    @Test
    @DisplayName("agregarRepuesto lanza StockInsuficienteException sin modificar stock ni detalles")
    void testAgregarRepuestoStockInsuficiente() {
        StockInsuficienteException ex = Assertions.assertThrows(
                StockInsuficienteException.class,
                () -> orden.agregarRepuesto(pastillas, 10)
        );

        Assertions.assertNotNull(ex.getMessage());
        Assertions.assertEquals(4, pastillas.getStockDisponible());
        Assertions.assertTrue(orden.getDetalles().isEmpty());
    }

    @Test
    @DisplayName("Cálculo de mano de obra, total de repuestos y total general de la orden")
    void testCalculoTotalesOrden() throws StockInsuficienteException {
        orden.setHorasManoObra(3.0);
        orden.agregarRepuesto(filtro, 2);     // 2 * 3500 = 7000
        orden.agregarRepuesto(pastillas, 1);  // 1 * 12000 = 12000

        // Mano de obra: 3 horas * $5000 * 1.20 (auto DIESEL) = $18000
        Assertions.assertEquals(18000.0, orden.calcularManoObra(), 0.001);
        Assertions.assertEquals(19000.0, orden.calcularTotalRepuestos(), 0.001);
        Assertions.assertEquals(37000.0, orden.calcularTotal(), 0.001);

        // Con moto <= 250cc: 2 horas * $5000 * 0.80 = $8000
        OrdenTrabajo ordenMoto = new OrdenTrabajo(2, "Cambio de aceite", motoChica, mecanico);
        ordenMoto.setHorasManoObra(2.0);
        Assertions.assertEquals(8000.0, ordenMoto.calcularManoObra(), 0.001);
        Assertions.assertEquals(8000.0, ordenMoto.calcularTotal(), 0.001);
    }

    @Test
    @DisplayName("Igualdad lógica (equals y hashCode) en Cliente, Vehiculo, Repuesto y OrdenTrabajo")
    void testIgualdadLogicaModelo() {
        Cliente c1 = new Cliente("30123456", "3804-111111", "a@mail.com", new ArrayList<>(), 1, "Juan", "Pérez");
        Cliente c2 = new Cliente("30123456", "3804-999999", "b@mail.com", new ArrayList<>(), 2, "Otro", "Nombre");
        Cliente c3 = new Cliente("40999888", "3804-111111", "a@mail.com", new ArrayList<>(), 3, "Juan", "Pérez");
        Assertions.assertTrue(c1.equals(c2));
        Assertions.assertEquals(c1.hashCode(), c2.hashCode());
        Assertions.assertFalse(c1.equals(c3));

        Auto a1 = new Auto("AB123CD", "Toyota", "Corolla", 2018, 4, TipoCombustible.DIESEL);
        Auto a2 = new Auto("AB123CD", "Ford", "Focus", 2020, 5, TipoCombustible.NAFTA);
        Auto a3 = new Auto("ZZ999ZZ", "Toyota", "Corolla", 2018, 4, TipoCombustible.DIESEL);
        Assertions.assertTrue(a1.equals(a2));
        Assertions.assertEquals(a1.hashCode(), a2.hashCode());
        Assertions.assertFalse(a1.equals(a3));

        Repuesto r1 = new Repuesto("R001", "Filtro", 3500.0, 10);
        Repuesto r2 = new Repuesto("R001", "Filtro alternativo", 4000.0, 5);
        Repuesto r3 = new Repuesto("R999", "Filtro", 3500.0, 10);
        Assertions.assertTrue(r1.equals(r2));
        Assertions.assertEquals(r1.hashCode(), r2.hashCode());
        Assertions.assertFalse(r1.equals(r3));

        OrdenTrabajo o1 = new OrdenTrabajo(10, "Problema A", autoDiesel, mecanico);
        OrdenTrabajo o2 = new OrdenTrabajo(10, "Problema B", motoChica, mecanico);
        OrdenTrabajo o3 = new OrdenTrabajo(11, "Problema A", autoDiesel, mecanico);
        Assertions.assertTrue(o1.equals(o2));
        Assertions.assertEquals(o1.hashCode(), o2.hashCode());
        Assertions.assertFalse(o1.equals(o3));
        Assertions.assertFalse(o1.equals(null));
    }
}
