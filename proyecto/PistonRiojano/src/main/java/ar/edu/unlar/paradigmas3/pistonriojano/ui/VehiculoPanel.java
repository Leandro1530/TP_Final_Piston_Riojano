/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.ui;

import ar.edu.unlar.paradigmas3.pistonriojano.dao.ClienteDAO;
import ar.edu.unlar.paradigmas3.pistonriojano.dao.ClienteDAOJdbc;
import ar.edu.unlar.paradigmas3.pistonriojano.dao.VehiculoDAO;
import ar.edu.unlar.paradigmas3.pistonriojano.dao.VehiculoDAOJdbc;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Auto;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Cliente;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Moto;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.TipoCombustible;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Vehiculo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gustavo
 */
public class VehiculoPanel extends javax.swing.JPanel {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAOJdbc();
    private final ClienteDAO clienteDAO = new ClienteDAOJdbc();

    private List<Vehiculo> vehiculosCargados = new ArrayList<>();
    private DefaultTableModel modeloVehiculos;
    
    public VehiculoPanel() {
        initComponents();
        configurarTabla();
        configurarCombos();
        cargarClientes();
        actualizarCamposPorTipo();
        cargarTablaVehiculos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSuperior = new javax.swing.JPanel();
        jLabelCliente = new javax.swing.JLabel();
        jcbCliente = new javax.swing.JComboBox<>();
        jLabelVehiculo = new javax.swing.JLabel();
        jcbTipoVehiculo = new javax.swing.JComboBox<>();
        jLabelPatente = new javax.swing.JLabel();
        jtfPatente = new javax.swing.JTextField();
        jLabelMarca = new javax.swing.JLabel();
        jtfMarca = new javax.swing.JTextField();
        jLabelModelo = new javax.swing.JLabel();
        jtfModelo = new javax.swing.JTextField();
        jLabelAnio = new javax.swing.JLabel();
        jtfAnio = new javax.swing.JTextField();
        jLabelCantPuertas = new javax.swing.JLabel();
        jtfCantPuertas = new javax.swing.JTextField();
        jLabelCombustible = new javax.swing.JLabel();
        jcbCombustible = new javax.swing.JComboBox<>();
        jLabelCilindrada = new javax.swing.JLabel();
        jtfCilindrada = new javax.swing.JTextField();
        jPanelCentro = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        jtVehiculos = new javax.swing.JTable();
        jPanelBotones = new javax.swing.JPanel();
        jbGuardar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jbFiltrarCliente = new javax.swing.JButton();
        jbVerTodos = new javax.swing.JButton();
        jbLimpiar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanelSuperior.setLayout(new java.awt.GridLayout(0, 4, 6, 4));

        jLabelCliente.setText("Cliente:");
        jPanelSuperior.add(jLabelCliente);
        jPanelSuperior.add(jcbCliente);

        jLabelVehiculo.setText("Tipo de vehículo:");
        jPanelSuperior.add(jLabelVehiculo);

        jcbTipoVehiculo.addActionListener(this::jcbTipoVehiculoActionPerformed);
        jPanelSuperior.add(jcbTipoVehiculo);

        jLabelPatente.setText("Patente:");
        jPanelSuperior.add(jLabelPatente);
        jPanelSuperior.add(jtfPatente);

        jLabelMarca.setText("Marca:");
        jPanelSuperior.add(jLabelMarca);
        jPanelSuperior.add(jtfMarca);

        jLabelModelo.setText("Modelo:");
        jPanelSuperior.add(jLabelModelo);
        jPanelSuperior.add(jtfModelo);

        jLabelAnio.setText("Año:");
        jPanelSuperior.add(jLabelAnio);
        jPanelSuperior.add(jtfAnio);

        jLabelCantPuertas.setText("Cantidad de puertas:");
        jPanelSuperior.add(jLabelCantPuertas);
        jPanelSuperior.add(jtfCantPuertas);

        jLabelCombustible.setText("Tipo de combustible:");
        jPanelSuperior.add(jLabelCombustible);
        jPanelSuperior.add(jcbCombustible);

        jLabelCilindrada.setText("Cilindrada:");
        jPanelSuperior.add(jLabelCilindrada);
        jPanelSuperior.add(jtfCilindrada);

        add(jPanelSuperior, java.awt.BorderLayout.NORTH);

        jtVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane.setViewportView(jtVehiculos);

        javax.swing.GroupLayout jPanelCentroLayout = new javax.swing.GroupLayout(jPanelCentro);
        jPanelCentro.setLayout(jPanelCentroLayout);
        jPanelCentroLayout.setHorizontalGroup(
            jPanelCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCentroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelCentroLayout.setVerticalGroup(
            jPanelCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCentroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanelCentro, java.awt.BorderLayout.CENTER);

        jbGuardar.setText("Registrar vehículo");
        jbGuardar.addActionListener(this::jbGuardarActionPerformed);
        jPanelBotones.add(jbGuardar);

        jbEliminar.setText("Eliminar seleccionado");
        jbEliminar.addActionListener(this::jbEliminarActionPerformed);
        jPanelBotones.add(jbEliminar);

        jbFiltrarCliente.setText("Listar del cliente");
        jbFiltrarCliente.addActionListener(this::jbFiltrarClienteActionPerformed);
        jPanelBotones.add(jbFiltrarCliente);

        jbVerTodos.setText("Ver todos");
        jbVerTodos.addActionListener(this::jbVerTodosActionPerformed);
        jPanelBotones.add(jbVerTodos);

        jbLimpiar.setText("Limpiar");
        jbLimpiar.addActionListener(this::jbLimpiarActionPerformed);
        jPanelBotones.add(jbLimpiar);

        add(jPanelBotones, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jcbTipoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTipoVehiculoActionPerformed
        actualizarCamposPorTipo();
    }//GEN-LAST:event_jcbTipoVehiculoActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        guardarVehiculo();
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        eliminarVehiculo();
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jbFiltrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFiltrarClienteActionPerformed
        listarPorClienteSeleccionado();
    }//GEN-LAST:event_jbFiltrarClienteActionPerformed

    private void jbVerTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVerTodosActionPerformed
        cargarTablaVehiculos();
    }//GEN-LAST:event_jbVerTodosActionPerformed

    private void jbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jbLimpiarActionPerformed
    
  private void configurarTabla() {
        modeloVehiculos = new DefaultTableModel(
                new Object[]{"Patente", "Tipo", "Marca", "Modelo", "Año", "Detalle específico", "Propietario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtVehiculos.setModel(modeloVehiculos);
    }
  
  private void configurarCombos() {
        jcbTipoVehiculo.setModel(new DefaultComboBoxModel<>(new String[]{"Auto", "Moto"}));
        jcbCombustible.setModel(new DefaultComboBoxModel<>(TipoCombustible.values()));
    }
  
  public void refrescarDatos() {
        cargarClientes();
        cargarTablaVehiculos();
    }

  private void cargarClientes() {
        Cliente seleccionado = (Cliente) jcbCliente.getSelectedItem();
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            jcbCliente.setModel(new DefaultComboBoxModel<>(clientes.toArray(new Cliente[0])));
            if (seleccionado != null) {
                for (Cliente c : clientes) {
                    if (seleccionado.equals(c)) {
                        jcbCliente.setSelectedItem(c);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            mostrarError("No se pudieron cargar los clientes.", e);
        }
    }
  
  private void actualizarCamposPorTipo() {
        boolean esAuto = "Auto".equals(jcbTipoVehiculo.getSelectedItem());
        jtfCantPuertas.setEnabled(esAuto);
        jcbCombustible.setEnabled(esAuto);
        jtfCilindrada.setEnabled(!esAuto);
        if (esAuto) {
            jtfCilindrada.setText("");
        } else {
            jtfCantPuertas.setText("");
        }
    }
  
  private void cargarTablaVehiculos() {
        try {
            vehiculosCargados = vehiculoDAO.listarTodos();
            refrescarTabla(vehiculosCargados);
        } catch (SQLException e) {
            mostrarError("No se pudo cargar la lista de vehículos.", e);
        }
    }
  
  private void listarPorClienteSeleccionado() {
        Cliente cliente = (Cliente) jcbCliente.getSelectedItem();
        if (cliente == null || cliente.getId() == null) {
            mostrarAviso("Seleccioná un cliente válido.");
            return;
        }
        try {
            vehiculosCargados = vehiculoDAO.listarPorCliente(cliente.getId());
            refrescarTabla(vehiculosCargados);
        } catch (SQLException e) {
            mostrarError("No se pudieron filtrar los vehículos del cliente.", e);
        }
    }
  
  private void refrescarTabla(List<Vehiculo> lista) {
        modeloVehiculos.setRowCount(0);
        for (Vehiculo v : lista) {
            String tipo;
            String detalle;
            if (v instanceof Auto) {
                Auto a = (Auto) v;
                tipo = "Auto";
                detalle = a.getCantidadPuertas() + " puertas - " + a.getTipoCombustible();
            } else {
                Moto m = (Moto) v;
                tipo = "Moto";
                detalle = m.getCilindrada() + " cc";
            }
            String propietario = v.getPropietario() != null
                    ? v.getPropietario().getNombre() + " " + v.getPropietario().getApellido() + " (" + v.getPropietario().getDni() + ")"
                    : "Sin asignar";

            modeloVehiculos.addRow(new Object[]{
                v.getPatente(),
                tipo,
                v.getMarca(),
                v.getModelo(),
                v.getAnio(),
                detalle,
                propietario
            });
        }
    }

    private void guardarVehiculo() {
        Cliente cliente = (Cliente) jcbCliente.getSelectedItem();
        if (cliente == null) {
            mostrarAviso("Seleccioná un cliente propietario.");
            return;
        }

        String patente = jtfPatente.getText().trim().toUpperCase();
        String marca = jtfMarca.getText().trim();
        String modelo = jtfModelo.getText().trim();
        String textoAnio = jtfAnio.getText().trim();

        if (patente.isEmpty() || marca.isEmpty() || modelo.isEmpty() || textoAnio.isEmpty()) {
            mostrarAviso("Completá patente, marca, modelo y año.");
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(textoAnio);
            if (anio < 1900 || anio > 2100) {
                mostrarAviso("Ingresá un año válido.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAviso("El año debe ser un número entero.");
            return;
        }

        try {
            if (vehiculoDAO.buscarPorPatente(patente) != null) {
                mostrarAviso("Ya existe un vehículo registrado con la patente " + patente + ".");
                return;
            }

            Vehiculo nuevoVehiculo;
            if ("Auto".equals(jcbTipoVehiculo.getSelectedItem())) {
                int puertas;
                try {
                    puertas = Integer.parseInt(jtfCantPuertas.getText().trim());
                    if (puertas <= 0) {
                        mostrarAviso("La cantidad de puertas debe ser mayor a cero.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    mostrarAviso("La cantidad de puertas debe ser un número entero.");
                    return;
                }
                TipoCombustible combustible = (TipoCombustible) jcbCombustible.getSelectedItem();
                if (combustible == null) {
                    mostrarAviso("Seleccioná un tipo de combustible.");
                    return;
                }
                nuevoVehiculo = new Auto(patente, marca, modelo, anio, cliente, puertas, combustible);
            } else {
                int cilindrada;
                try {
                    cilindrada = Integer.parseInt(jtfCilindrada.getText().trim());
                    if (cilindrada <= 0) {
                        mostrarAviso("La cilindrada debe ser mayor a cero.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    mostrarAviso("La cilindrada debe ser un número entero.");
                    return;
                }
                nuevoVehiculo = new Moto(patente, marca, modelo, anio, cliente, cilindrada);
            }

            vehiculoDAO.insertar(nuevoVehiculo);
            JOptionPane.showMessageDialog(this,
                    "Vehículo " + patente + " registrado correctamente.",
                    "Vehículo guardado", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTablaVehiculos();
        } catch (SQLException e) {
            mostrarError("No se pudo guardar el vehículo.", e);
        }
    }

    private void eliminarVehiculo() {
        int fila = jtVehiculos.getSelectedRow();
        if (fila < 0) {
            mostrarAviso("Seleccioná un vehículo de la tabla para eliminar.");
            return;
        }
        Vehiculo seleccionado = vehiculosCargados.get(fila);
        try {
            if (vehiculoDAO.tieneOrdenesTrabajo(seleccionado.getPatente())) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar el vehículo porque tiene órdenes de trabajo asociadas.",
                        "Operación no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseás eliminar el vehículo con patente " + seleccionado.getPatente() + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
            vehiculoDAO.eliminar(seleccionado.getPatente());
            JOptionPane.showMessageDialog(this,
                    "Vehículo eliminado correctamente.",
                    "Vehículo eliminado", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaVehiculos();
        } catch (SQLException e) {
            mostrarError("No se pudo eliminar el vehículo.", e);
        }
    }

    private void limpiarCampos() {
        jtfPatente.setText("");
        jtfMarca.setText("");
        jtfModelo.setText("");
        jtfAnio.setText("");
        jtfCantPuertas.setText("");
        jtfCilindrada.setText("");
        jcbTipoVehiculo.setSelectedIndex(0);
        if (jcbCombustible.getItemCount() > 0) {
            jcbCombustible.setSelectedIndex(0);
        }
        actualizarCamposPorTipo();
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(this, mensaje + "\n" + e.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelAnio;
    private javax.swing.JLabel jLabelCantPuertas;
    private javax.swing.JLabel jLabelCilindrada;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelCombustible;
    private javax.swing.JLabel jLabelMarca;
    private javax.swing.JLabel jLabelModelo;
    private javax.swing.JLabel jLabelPatente;
    private javax.swing.JLabel jLabelVehiculo;
    private javax.swing.JPanel jPanelBotones;
    private javax.swing.JPanel jPanelCentro;
    private javax.swing.JPanel jPanelSuperior;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbFiltrarCliente;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbLimpiar;
    private javax.swing.JButton jbVerTodos;
    private javax.swing.JComboBox<Cliente> jcbCliente;
    private javax.swing.JComboBox<TipoCombustible> jcbCombustible;
    private javax.swing.JComboBox<String> jcbTipoVehiculo;
    private javax.swing.JTable jtVehiculos;
    private javax.swing.JTextField jtfAnio;
    private javax.swing.JTextField jtfCantPuertas;
    private javax.swing.JTextField jtfCilindrada;
    private javax.swing.JTextField jtfMarca;
    private javax.swing.JTextField jtfModelo;
    private javax.swing.JTextField jtfPatente;
    // End of variables declaration//GEN-END:variables
}
