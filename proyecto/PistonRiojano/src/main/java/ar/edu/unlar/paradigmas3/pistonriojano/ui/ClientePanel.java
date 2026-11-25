/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.ui;

import ar.edu.unlar.paradigmas3.pistonriojano.dao.ClienteDAO;
import ar.edu.unlar.paradigmas3.pistonriojano.dao.ClienteDAOJdbc;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author river
 */
public class ClientePanel extends javax.swing.JPanel {
    private final ClienteDAO clienteDAO;
    private DefaultTableModel modeloTabla;
    private List<Cliente> clientesActuales = new ArrayList<>();
    private Integer idSeleccionado = null;
    private String dniSeleccionado = null;

    /**
     * Creates new form ClientePanel
     */
    public ClientePanel() {
        
        initComponents();
        this.clienteDAO = new ClienteDAOJdbc();
        configurarTabla();
        configurarEventosExtra();
        cargarTablaClientes();
        limpiarCampos();
    }
    
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[][]{},
                new String[]{"DNI", "Nombre", "Apellido", "Teléfono", "Email", "Vehículos"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tClientes.setModel(modeloTabla);
    }

    private void configurarEventosExtra() {
        btnEliminar.addActionListener(evt -> eliminarCliente());
        btnLimpiar.addActionListener(evt -> limpiarCampos());
    }

    private void cargarTablaClientes() {
        modeloTabla.setRowCount(0);
        clientesActuales.clear();
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            clientesActuales.addAll(clientes);
            for (Cliente c : clientes) {
                int cantVehiculos = (c.getVehiculos() != null) ? c.getVehiculos().size() : 0;
                modeloTabla.addRow(new Object[]{
                    c.getDni(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getTelefono(),
                    c.getEmail(),
                    cantVehiculos
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los clientes: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean validarCampos() {
        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "DNI, Nombre y Apellido son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }

    private Cliente construirClienteDesdeFormulario(Integer id) {
        Cliente c = new Cliente();
        c.setId(id);
        c.setDni(txtDni.getText().trim());
        c.setNombre(txtNombre.getText().trim());
        c.setApellido(txtApellido.getText().trim());
        c.setTelefono(txtTelefono.getText().trim());
        c.setEmail(txtMail.getText().trim());
        return c;
    }

    private void guardarCliente() {
        if (!validarCampos()) {
            return;
        }
        try {
            String dni = txtDni.getText().trim();
            if (clienteDAO.buscarPorDni(dni) != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe un cliente registrado con el DNI " + dni + ".",
                        "DNI duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Cliente nuevo = construirClienteDesdeFormulario(null);
            clienteDAO.insertar(nuevo);
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            cargarTablaClientes();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el cliente: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void modificarCliente() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un cliente de la tabla para modificarlo.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        if (!validarCampos()) {
            return;
        }

        try {
            Cliente editado = construirClienteDesdeFormulario(idSeleccionado);
            clienteDAO.actualizar(editado);
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            cargarTablaClientes();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al modificar el cliente: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarCliente() {
        if (idSeleccionado == null || dniSeleccionado == null || dniSeleccionado.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un cliente de la tabla para eliminar.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        String dni = dniSeleccionado;
        try {
            if (clienteDAO.tieneVehiculos(dni)) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se puede eliminar el cliente porque tiene vehículos asociados.",
                        "Operación no permitida",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de eliminar al cliente con DNI " + dni + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                clienteDAO.eliminar(dni);
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                cargarTablaClientes();
                limpiarCampos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el cliente: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void seleccionarFilaTabla() {
        int fila = tClientes.getSelectedRow();
        if (fila >= 0 && fila < clientesActuales.size()) {
            Cliente seleccionado = clientesActuales.get(fila);
            idSeleccionado = seleccionado.getId();
            dniSeleccionado = seleccionado.getDni();
            
            txtID.setText(idSeleccionado != null ? String.valueOf(idSeleccionado) : "");
            txtDni.setText(valorSeguro(seleccionado.getDni()));
            txtNombre.setText(valorSeguro(seleccionado.getNombre()));
            txtApellido.setText(valorSeguro(seleccionado.getApellido()));
            txtTelefono.setText(valorSeguro(seleccionado.getTelefono()));
            txtMail.setText(valorSeguro(seleccionado.getEmail()));

            btnGuardar.setEnabled(false);
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }
    }

    private void limpiarCampos() {
        idSeleccionado = null;
        dniSeleccionado = null;
        txtDni.setText("");
        txtID.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtMail.setText("");

        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tClientes.clearSelection();
        txtNombre.requestFocus();
    }

    private String valorSeguro(Object valor) {
        return valor != null ? valor.toString() : "";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtMail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();

        jLabel1.setText("Nombre:");

        jLabel2.setText("Apellido");

        jLabel3.setText("DNI:");

        jLabel4.setText("Telefono:");

        jLabel5.setText("mail:");

        tClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tClientes);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnEliminar.setText("Eliminar");

        btnLimpiar.setText("Limpiar");

        jLabel6.setText("ID");

        txtID.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDni, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefono)
                            .addComponent(txtMail)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 213, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarCliente();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        modificarCliente();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked
        // TODO add your handling code here:
        seleccionarFilaTabla();
    }//GEN-LAST:event_tClientesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tClientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
