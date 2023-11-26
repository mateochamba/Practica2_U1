package vista;

import controlador.CasaControl;
import controlador.VendedorControl;
import controlador.ed.listas.DynamicList;
import javax.swing.JOptionPane;
import modelo.Casa;
import modelo.Vendedor;
import vista.modelo.ModeloTablaCasa;
import vista.modelo.ModeloTablaVendedorr;

/**
 *
 * @author FA506ICB-HN114W
 */
public class FrmVentas extends javax.swing.JDialog {

    private ModeloTablaVendedorr mv = new ModeloTablaVendedorr();
    private ModeloTablaCasa mc = new ModeloTablaCasa();
    private VendedorControl vc = new VendedorControl();
    private CasaControl cc = new CasaControl();
    private Integer fila = -1;

    /**
     * Creates new form FrmVentas
     */
    public FrmVentas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiar();
    }

    public void cargarTabla() {

        mv.setLista(vc.getVendedorList());
        mc.setLista(cc.getCasaList());

        tblTableC.setModel(mc);
        tblTableV.setModel(mv);

        tblTableC.updateUI();
        tblTableV.updateUI();
    }

    public void limpiar() {
        txtApellido.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtDni.setText("");
        txtDireccion.setText("");
        txtDimensiones.setText("");
        txtArea.setText("");
        cbxTipoCasa.setSelectedIndex(0);
        vc.setVendedor(null);
        cc.setCasa(null);
        fila = -1;
        cargarTabla();

    }

    private Boolean validar() {
        return (!txtNombre.getText().trim().isEmpty() && !txtApellido.getText().trim().isEmpty()
                && !txtCorreo.getText().trim().isEmpty()
                && !txtTelefono.getText().trim().isEmpty()
                && !txtDni.getText().trim().isEmpty()
                && !txtDimensiones.getText().trim().isEmpty()
                && !txtArea.getText().trim().isEmpty()
                && !txtDireccion.getText().trim().isEmpty()
                && !cbxTipoCasa.getSelectedItem().toString().isEmpty());
    }

    private void adjudicarVenta() {
        int filaVendedor = tblTableV.getSelectedRow();
        int filaCasa = tblTableC.getSelectedRow();

        if (filaVendedor == -1 || filaCasa == -1) {
            txtNotificacion.setText("Selecciona un Vendedor y una Casa para poder adjudicar una venta.");
            return;
        }

        try {
            String nombreVendedor = (String) tblTableV.getValueAt(filaVendedor, 0);
            String apellidoVendedor = (String) tblTableV.getValueAt(filaVendedor, 1);
            int dimensionCasa = Integer.parseInt((String) tblTableC.getValueAt(filaCasa, 1));
            String direccionCasa = (String) tblTableC.getValueAt(filaCasa, 2);

            //Aqui verifico si la casa ya ha sido vendido
            if (casaVendida(direccionCasa)) {
                txtNotificacion.setText("Error: La casa en " + direccionCasa + "ya ha sido vendida");
                return;
            }

            CasaControl casaControl = new CasaControl();
            casaControl.modificarEstadoCasa(direccionCasa, true);

            // Usar StringBuilder para mejorar la concatenación
            StringBuilder notificacion = new StringBuilder();
            notificacion.append("El vendedor ").append(nombreVendedor).append(" ").append(apellidoVendedor)
                    .append(" ha vendido la propiedad en ").append(direccionCasa).append(" con una dimensión de ")
                    .append(dimensionCasa).append(".");

            // Mostrar la notificación en el cuadro de texto
            txtNotificacion.setText(notificacion.toString());

        } catch (NumberFormatException e) {
            txtNotificacion.setText("Error: La dimensión de la casa no es un número válido.");
        }
    }

    private boolean casaVendida(String direccionCasa) {
        CasaControl casaControl = new CasaControl();
        DynamicList<Casa> listaCasa = casaControl.getCasaList();

        for (int i = 0; i < listaCasa.getLength(); i++) {
            try {
                Casa casa = listaCasa.getInfo(i);

                if (casa.getDireccion().equals(direccionCasa) && casa.isVendida()) {
                    // devuelve una casa ya vendida
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void modificarVendedor() {
        fila = tblTableV.getSelectedRow();

        if (fila >= 0) {
            try {
                Integer id = mv.getLista().getInfo(fila).getId();
                vc.setVendedor(vc.get(id));

                txtNombre.setText(vc.getVendedor().getNombre());
                txtApellido.setText(vc.getVendedor().getApellido());
                txtCorreo.setText(vc.getVendedor().getCorreo());
                txtTelefono.setText(vc.getVendedor().getTelf());
                txtDni.setText(vc.getVendedor().getDni());

            } catch (Exception e) {
                System.out.println("ERROR EN MODIFICAR FRM: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }
    }

    private void modificarCasa() {
        fila = tblTableC.getSelectedRow();

        if (fila >= 0) {
            try {
                Integer id = mc.getLista().getInfo(fila).getId();
                cc.setCasa(cc.get(id));

                // Cargar los datos de la casa en los campos correspondientes
                txtDimensiones.setText(cc.getCasa().getDimensiones());
                txtArea.setText(cc.getCasa().getArea());
                txtDireccion.setText(cc.getCasa().getDireccion());
                cbxTipoCasa.setSelectedItem(cc.getCasa().getTipoCasa());

                // ... (otros campos según sea necesario)
            } catch (Exception e) {
                System.out.println("ERROR EN MODIFICAR FRM: " + e);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }
    }

    private void guardar() {
        if (validar()) {
            try {
                vc.getVendedor().setNombre(txtNombre.getText());
                vc.getVendedor().setApellido(txtApellido.getText());
                vc.getVendedor().setCorreo(txtCorreo.getText());
                vc.getVendedor().setTelf(txtTelefono.getText());
                vc.getVendedor().setDni(txtDni.getText());

                if (vc.getVendedor().getId() != null) {
                    vc.modificar(fila);
                } else {
                    vc.persit();
                }

                cc.getCasa().setDimensiones(txtDimensiones.getText());
                cc.getCasa().setArea(txtArea.getText());
                cc.getCasa().setDireccion(txtDireccion.getText());
                cc.getCasa().setTipoCasa(cbxTipoCasa.getSelectedItem().toString());

                cc.persit();
                
                
                limpiar();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "NO SE PUDO GUARDAR: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtDni = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTableV = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTableC = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDimensiones = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtArea = new javax.swing.JTextField();
        cbxTipoCasa = new javax.swing.JComboBox<>();
        btnModificarVendedor = new javax.swing.JButton();
        btnModificarCasa = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        btnAdjudicarVenta = new javax.swing.JButton();
        txtNotificacion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(38, 121, 142));

        jPanel2.setBackground(new java.awt.Color(99, 202, 167));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Vendedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SimSun", 0, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel2.setText("Apellido:");

        jLabel3.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel3.setText("Telefono:");

        jLabel4.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel4.setText("Correo:");

        jLabel5.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel5.setText("Dni:");

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        txtDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(txtNombre))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDni, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(txtCorreo)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addComponent(txtTelefono)))))
                .addGap(0, 183, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(38, 121, 142));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tblTableV.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTableV);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(38, 121, 142));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tblTableC.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblTableC);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(99, 202, 167));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Casa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SimSun", 0, 18))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel6.setText("Dimensiones:");

        jLabel7.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel7.setText("Area:");

        jLabel8.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel8.setText("Dirección:");

        jLabel9.setFont(new java.awt.Font("Evogria", 0, 12)); // NOI18N
        jLabel9.setText("Tipo Casa:");

        cbxTipoCasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Edificio", "Departamento", "Casa", "Oficina" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtDimensiones))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(61, 61, 61)
                        .addComponent(txtArea))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccion)
                            .addComponent(cbxTipoCasa, 0, 276, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDimensiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbxTipoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnModificarVendedor.setText("Modificar Vendedor");
        btnModificarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarVendedorActionPerformed(evt);
            }
        });

        btnModificarCasa.setText("Modificar Casa");
        btnModificarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCasaActionPerformed(evt);
            }
        });

        btnGuardar1.setText("Guardar");
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });

        btnAdjudicarVenta.setText("Adjudicar Venta");
        btnAdjudicarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjudicarVentaActionPerformed(evt);
            }
        });

        txtNotificacion.setFont(new java.awt.Font("Evogria", 0, 10)); // NOI18N
        txtNotificacion.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnModificarVendedor)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnModificarCasa)
                        .addGap(160, 160, 160))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 16, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnAdjudicarVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificarVendedor)
                            .addComponent(btnGuardar1))
                        .addGap(12, 12, 12)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnModificarCasa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdjudicarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNotificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnModificarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarVendedorActionPerformed
        modificarVendedor();
    }//GEN-LAST:event_btnModificarVendedorActionPerformed

    private void btnModificarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCasaActionPerformed
        modificarCasa();
    }//GEN-LAST:event_btnModificarCasaActionPerformed

    private void btnAdjudicarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdjudicarVentaActionPerformed
        adjudicarVenta();
    }//GEN-LAST:event_btnAdjudicarVentaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmVentas dialog = new FrmVentas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdjudicarVenta;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnModificarCasa;
    private javax.swing.JButton btnModificarVendedor;
    private javax.swing.JComboBox<String> cbxTipoCasa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblTableC;
    private javax.swing.JTable tblTableV;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDimensiones;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JLabel txtNotificacion;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
