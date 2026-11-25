/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.ui;
        
import ar.edu.unlar.paradigmas3.pistonriojano.dao.MecanicoDAO;
import ar.edu.unlar.paradigmas3.pistonriojano.dao.MecanicoDAOJdbc;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Especialidad;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Mecanico;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author river
 */
public class MecanicoPanel extends javax.swing.JPanel {
    private final MecanicoDAO mecanicoDAO;
    private DefaultTableModel modeloTabla;
    private List<Mecanico> mecanicosActuales = new ArrayList<>();
    private Integer idSeleccionado = null;

    /**
     * Creates new form MecanicoPanel
     */
    public MecanicoPanel() {
        initComponents();
        this.mecanicoDAO = new MecanicoDAOJdbc();
        configurarComboEspecialidad();
        configurarTabla();
        cargarTablaMecanicos();
        limpiarCampos();
    }
    
    private void configurarComboEspecialidad() {
        cbEspecialidad.setModel(new DefaultComboBoxModel<>(Especialidad.values()));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "N° Legajo", "Nombre", "Apellido", "Especialidad", "Valor Hora"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbMecanicos.setModel(modeloTabla);
    }

    private void cargarTablaMecanicos() {
        modeloTabla.setRowCount(0);
        mecanicosActuales.clear();
        try {
            List<Mecanico> lista = mecanicoDAO.listarTodos();
            mecanicosActuales.addAll(lista);
            for (Mecanico m : lista) {
                modeloTabla.addRow(new Object[]{
                    m.getId(),
                    m.getLegajo(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getEspecialidad(),
                    m.getValorHora()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los mecánicos: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Integer parsearLegajo() {
        String texto = txtLegajo.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El N° de legajo es obligatorio.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtLegajo.requestFocus();
            return null;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El N° de legajo debe ser un número entero válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtLegajo.requestFocus();
            return null;
        }
    }

    private Double parsearValorHora() {
        String texto = txtValorHora.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El valor hora es obligatorio.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtValorHora.requestFocus();
            return null;
        }
        try {
            double valor = Double.parseDouble(texto);
            if (valor < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El valor hora no puede ser negativo.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtValorHora.requestFocus();
                return null;
            }
            return valor;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El valor hora debe ser un número válido (ej: 15000 o 15000.50).",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtValorHora.requestFocus();
            return null;
        }
    }

    private boolean validarCamposBasicos() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        Especialidad especialidad = (Especialidad) cbEspecialidad.getSelectedItem();

        if (nombre.isEmpty() || apellido.isEmpty() || especialidad == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nombre, Apellido y Especialidad son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }

    private Mecanico construirMecanicoDesdeFormulario(Integer id, int legajo, double valorHora) {
        Mecanico m = new Mecanico();
        m.setId(id);
        m.setLegajo(legajo);
        m.setNombre(txtNombre.getText().trim());
        m.setApellido(txtApellido.getText().trim());
        m.setEspecialidad((Especialidad) cbEspecialidad.getSelectedItem());
        m.setValorHora(valorHora);
        return m;
    }

    private void guardarMecanico() {
        if (!validarCamposBasicos()) {
            return;
        }
        Integer legajo = parsearLegajo();
        if (legajo == null) {
            return;
        }
        Double valorHora = parsearValorHora();
        if (valorHora == null) {
            return;
        }

        try {
            Mecanico existente = mecanicoDAO.buscarPorLegajo(String.valueOf(legajo));
            if (existente != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe un mecánico registrado con el legajo " + legajo + ".",
                        "Legajo duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Mecanico nuevo = construirMecanicoDesdeFormulario(null, legajo, valorHora);
            mecanicoDAO.insertar(nuevo);
            JOptionPane.showMessageDialog(this, "Mecánico guardado correctamente.");
            cargarTablaMecanicos();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el mecánico: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void modificarMecanico() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un mecánico de la tabla para modificarlo.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        if (!validarCamposBasicos()) {
            return;
        }
        Integer legajo = parsearLegajo();
        if (legajo == null) {
            return;
        }
        Double valorHora = parsearValorHora();
        if (valorHora == null) {
            return;
        }

        try {
            Mecanico existenteConLegajo = mecanicoDAO.buscarPorLegajo(String.valueOf(legajo));
            if (existenteConLegajo != null
                    && existenteConLegajo.getId() != null
                    && !existenteConLegajo.getId().equals(idSeleccionado)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe otro mecánico registrado con el legajo " + legajo + ".",
                        "Legajo duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Mecanico editado = construirMecanicoDesdeFormulario(idSeleccionado, legajo, valorHora);
            mecanicoDAO.actualizar(editado);
            JOptionPane.showMessageDialog(this, "Mecánico actualizado correctamente.");
            cargarTablaMecanicos();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al modificar el mecánico: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarMecanico() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un mecánico de la tabla para eliminar.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        try {
            if (mecanicoDAO.tieneOrdenesTrabajo(idSeleccionado)) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se puede eliminar el mecánico porque tiene órdenes de trabajo asociadas.",
                        "Operación no permitida",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de eliminar al mecánico con ID " + idSeleccionado + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                mecanicoDAO.eliminar(idSeleccionado);
                JOptionPane.showMessageDialog(this, "Mecánico eliminado correctamente.");
                cargarTablaMecanicos();
                limpiarCampos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el mecánico: " + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void seleccionarFilaTabla() {
        int fila = tbMecanicos.getSelectedRow();
        if (fila >= 0 && fila < mecanicosActuales.size()) {
            Mecanico seleccionado = mecanicosActuales.get(fila);
            idSeleccionado = seleccionado.getId();

            txtID.setText(idSeleccionado != null ? String.valueOf(idSeleccionado) : "");
            txtLegajo.setText(String.valueOf(seleccionado.getLegajo()));
            txtNombre.setText(valorSeguro(seleccionado.getNombre()));
            txtApellido.setText(valorSeguro(seleccionado.getApellido()));
            if (seleccionado.getEspecialidad() != null) {
                cbEspecialidad.setSelectedItem(seleccionado.getEspecialidad());
            } else if (cbEspecialidad.getItemCount() > 0) {
                cbEspecialidad.setSelectedIndex(0);
            }
            txtValorHora.setText(String.valueOf(seleccionado.getValorHora()));

            btnGuardar.setEnabled(false);
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }
    }

    private void limpiarCampos() {
        idSeleccionado = null;

        txtID.setText("");
        txtLegajo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        if (cbEspecialidad.getItemCount() > 0) {
            cbEspecialidad.setSelectedIndex(0);
        }
        txtValorHora.setText("");

        txtID.setEditable(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tbMecanicos.clearSelection();
        txtLegajo.requestFocus();
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
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtLegajo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        cbEspecialidad = new javax.swing.JComboBox<>();
        txtValorHora = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMecanicos = new javax.swing.JTable();

        jLabel1.setText("ID");

        jLabel2.setText("N° legajo");

        jLabel3.setText("Nombre");

        jLabel4.setText("Apellido");

        jLabel5.setText("Especialidad");

        jLabel6.setText("Valor hora");

        txtID.addActionListener(this::txtIDActionPerformed);

        cbEspecialidad.addActionListener(this::cbEspecialidadActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        tbMecanicos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbMecanicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMecanicosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbMecanicos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(58, 58, 58)
                        .addComponent(txtID))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtLegajo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(24, 24, 24)
                        .addComponent(txtNombre))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(22, 22, 22)
                        .addComponent(txtApellido))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbEspecialidad, 0, 171, Short.MAX_VALUE)
                            .addComponent(txtValorHora))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtValorHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void cbEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEspecialidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEspecialidadActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarMecanico();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        modificarMecanico();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarMecanico();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tbMecanicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMecanicosMouseClicked
        // TODO add your handling code here:
    seleccionarFilaTabla();
    }//GEN-LAST:event_tbMecanicosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<Especialidad> cbEspecialidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbMecanicos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLegajo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtValorHora;
    // End of variables declaration//GEN-END:variables
}
