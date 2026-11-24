/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanelSuperior = new javax.swing.JPanel();
        jPanelFormulario = new javax.swing.JPanel();
        jLabelCliente = new javax.swing.JLabel();
        jcbCliente = new javax.swing.JComboBox<>();
        jLabelTipo = new javax.swing.JLabel();
        jcbTipoVehiculo = new javax.swing.JComboBox<>();
        jLabelPatente = new javax.swing.JLabel();
        jtfPatente = new javax.swing.JTextField();
        jLabelMarca = new javax.swing.JLabel();
        jtfMarca = new javax.swing.JTextField();
        jLabelModelo = new javax.swing.JLabel();
        jtfModelo = new javax.swing.JTextField();
        jLabelAnio = new javax.swing.JLabel();
        jtfAnio = new javax.swing.JTextField();
        jLabelPuertas = new javax.swing.JLabel();
        jtfCantidadPuertas = new javax.swing.JTextField();
        jLabelCombustible = new javax.swing.JLabel();
        jcbCombustible = new javax.swing.JComboBox<>();
        jLabelCilindrada = new javax.swing.JLabel();
        jtfCilindrada = new javax.swing.JTextField();
        jPanelBotones = new javax.swing.JPanel();
        jbGuardar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jbFiltrarCliente = new javax.swing.JButton();
        jbVerTodos = new javax.swing.JButton();
        jbLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtVehiculos = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new java.awt.BorderLayout());

        jPanelSuperior.setLayout(new java.awt.BorderLayout());

        jPanelFormulario.setLayout(new java.awt.GridLayout(0, 4, 6, 4));

        jLabelCliente.setText("Cliente:");
        jPanelFormulario.add(jLabelCliente);
        jPanelFormulario.add(jcbCliente);

        jLabelTipo.setText("Tipo de vehículo:");
        jPanelFormulario.add(jLabelTipo);

        jcbTipoVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTipoVehiculoActionPerformed(evt);
            }
        });
        jPanelFormulario.add(jcbTipoVehiculo);

        jLabelPatente.setText("Patente:");
        jPanelFormulario.add(jLabelPatente);
        jPanelFormulario.add(jtfPatente);

        jLabelMarca.setText("Marca:");
        jPanelFormulario.add(jLabelMarca);
        jPanelFormulario.add(jtfMarca);

        jLabelModelo.setText("Modelo:");
        jPanelFormulario.add(jLabelModelo);
        jPanelFormulario.add(jtfModelo);

        jLabelAnio.setText("Año:");
        jPanelFormulario.add(jLabelAnio);
        jPanelFormulario.add(jtfAnio);

        jLabelPuertas.setText("Cantidad de puertas:");
        jPanelFormulario.add(jLabelPuertas);
        jPanelFormulario.add(jtfCantidadPuertas);

        jLabelCombustible.setText("Tipo de combustible:");
        jPanelFormulario.add(jLabelCombustible);
        jPanelFormulario.add(jcbCombustible);

        jLabelCilindrada.setText("Cilindrada (cc):");
        jPanelFormulario.add(jLabelCilindrada);
        jPanelFormulario.add(jtfCilindrada);

        jPanelSuperior.add(jPanelFormulario, java.awt.BorderLayout.CENTER);

        jbGuardar.setText("Registrar vehículo");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });
        jPanelBotones.add(jbGuardar);

        jbEliminar.setText("Eliminar seleccionado");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });
        jPanelBotones.add(jbEliminar);

        jbFiltrarCliente.setText("Listar del cliente");
        jbFiltrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFiltrarClienteActionPerformed(evt);
            }
        });
        jPanelBotones.add(jbFiltrarCliente);

        jbVerTodos.setText("Ver todos");
        jbVerTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbVerTodosActionPerformed(evt);
            }
        });
        jPanelBotones.add(jbVerTodos);

        jbLimpiar.setText("Limpiar");
        jbLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimpiarActionPerformed(evt);
            }
        });
        jPanelBotones.add(jbLimpiar);

        jPanelSuperior.add(jPanelBotones, java.awt.BorderLayout.SOUTH);

        add(jPanelSuperior, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(jtVehiculos);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>

    private void jcbTipoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {
        actualizarCamposPorTipo();
    }

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        guardarVehiculo();
    }

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        eliminarVehiculo();
    }

    private void jbFiltrarClienteActionPerformed(java.awt.event.ActionEvent evt) {
        listarPorClienteSeleccionado();
    }

    private void jbVerTodosActionPerformed(java.awt.event.ActionEvent evt) {
        cargarTablaVehiculos();
    }

    private void jbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
    }

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

    private void cargarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            jcbCliente.setModel(new DefaultComboBoxModel<>(clientes.toArray(new Cliente[0])));
        } catch (SQLException e) {
            mostrarError("No se pudieron cargar los clientes.", e);
        }
    }

    private void actualizarCamposPorTipo() {
        boolean esAuto = "Auto".equals(jcbTipoVehiculo.getSelectedItem());
        jtfCantidadPuertas.setEnabled(esAuto);
        jcbCombustible.setEnabled(esAuto);
        jtfCilindrada.setEnabled(!esAuto);
        if (esAuto) {
            jtfCilindrada.setText("");
        } else {
            jtfCantidadPuertas.setText("");
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
                    puertas = Integer.parseInt(jtfCantidadPuertas.getText().trim());
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
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseás eliminar el vehículo con patente " + seleccionado.getPatente() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
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
        jtfCantidadPuertas.setText("");
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

    // Variables declaration - do not modify
    private javax.swing.JLabel jLabelAnio;
    private javax.swing.JLabel jLabelCilindrada;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelCombustible;
    private javax.swing.JLabel jLabelMarca;
    private javax.swing.JLabel jLabelModelo;
    private javax.swing.JLabel jLabelPatente;
    private javax.swing.JLabel jLabelPuertas;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JPanel jPanelBotones;
    private javax.swing.JPanel jPanelFormulario;
    private javax.swing.JPanel jPanelSuperior;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTextField jtfCilindrada;
    private javax.swing.JTextField jtfMarca;
    private javax.swing.JTextField jtfModelo;
    private javax.swing.JTextField jtfPatente;
    private javax.swing.JTextField jtfCantidadPuertas;
    // End of variables declaration
}
