/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class JPHojaCostos extends javax.swing.JPanel {
    ConexionSQL connect = new ConexionSQL();
    Connection con;
    Statement st;
    ResultSet rs;
    String iniciarT = "BEGIN";
    /**
     * Creates new form JPRequisicion
     */
    public JPHojaCostos() throws SQLException {
        initComponents();
        consultaCliente();
        manoDeObraDirecta();
        materiales();
        tasaGIF();
        updatesHojaC();
        resumen();
    }
    
    private void consultaCliente() throws SQLException {
        String queryEmpleado = "SELECT nombre, numeroOrden, descripcion, cantidad, fechaInicio, fechaTermino FROM cliente";
        
        con = connect.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(queryEmpleado);
        try {
            while (rs.next()) {
                String cliente = rs.getString("nombre");
                String numeroO = rs.getString("numeroOrden");
                String descripcion = rs.getString("descripcion");
                String cantidad = rs.getString("cantidad");
                String fechaI = rs.getString("fechaInicio");
                String fechaT = rs.getString("fechaTermino");
                
                // Asignar los valores a los JLabels correspondientes
                jLCliente.setText(cliente);
                jLNumeroO.setText(numeroO);
                jLDescripcion.setText(descripcion);
                jLCantidad.setText(cantidad);
                jLFechaI.setText(fechaI);
                jLFechaT.setText(fechaT);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    
    private void limpiarjTFieldCliente() {
        jTFCliente.setText("Cliente");
        jTFNumeroO.setText("N° orden");
        jTFDescripcion.setText("Descripcion");
        jTFCantidad.setText("Cantidad");
        jTFFechaI.setText("Fecha inicio Año/Mes/Dia");
        jTFFechaT.setText("Fecha termino Año/Mes/Dia");
    }
    
    private void manoDeObraDirecta() throws SQLException {
        String queryMOD = "SELECT CURDATE() AS dia, SUM(horasT) AS horasT, pagoH, SUM(horasT * pagoH) AS costoT FROM horario GROUP BY pagoH";
        con = connect.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(queryMOD);
        try {
            while (rs.next()) {
                String dia = rs.getString("dia");
                String horasT = rs.getString("horasT");
                String pagoH = rs.getString("pagoH");
                String costoT = rs.getString("costoT");

                jLFechaMOD.setText(dia);
                jLHorasMOD.setText(horasT);
                jLPagoHMOD.setText(pagoH);
                jLCostoMOD.setText(costoT);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    private void materiales() throws SQLException{
        String queryMateriales = "SELECT CURDATE() as fecha, (SELECT numeroR FROM requisicion) AS numeroR, SUM(cantidad * precioU) AS costoT FROM materiales";
        con = connect.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(queryMateriales);
        try {
            while (rs.next()) {
                String fecha = rs.getString("fecha");
                String numeroR = rs.getString("numeroR");
                String costoT = rs.getString("costoT");

                jLFechaMateriales.setText(fecha);
                jLNumeroRMateriales.setText(numeroR);
                jLCostoMateriales.setText(costoT);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    
    private void tasaGIF() throws SQLException{
        String queryGIF = "SELECT CURDATE() as fecha,"
                + " (SELECT SUM(HORASt) FROM horario) AS horasT, "
                + "(SELECT ROUND((presupuesto / horasMOD), 2) AS costoT FROM tasaGIF) AS tasaGIF,"
                + " (SELECT SUM(HORASt) FROM horario) * (SELECT ROUND((presupuesto / horasMOD), 2) AS costoT FROM tasaGIF) AS costoTGIF "
                + "FROM tasaGIF";
        con = connect.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(queryGIF);
        try {
            while (rs.next()) {
                String fecha = rs.getString("fecha");
                String horasT = rs.getString("horasT");
                String tazaGIF = rs.getString("tasaGIF");
                String tasaGIF = rs.getString("costoTGIF");

                jLFechaGIF.setText(fecha);
                jLHorasGIF.setText(horasT);
                jLTasaGIF.setText(tazaGIF);
                jLCostoGIF.setText(tasaGIF);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    
    private void resumen() throws SQLException{
        String queryMOD = "SELECT manoOD, materiales, gif, costoT, costoU FROM hojaC";
        con = connect.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(queryMOD);
        try {
            while (rs.next()) {
                String manoOD = rs.getString("manoOD");
                String materiales = rs.getString("materiales");
                String gif = rs.getString("gif");
                String costoT = rs.getString("costoT");
                String costoU = rs.getString("costoU");

                jLResumenMOD.setText(manoOD);
                jLResumenMateriales.setText(materiales);
                jLResumenGIF.setText(gif);
                jLResumenCostoT.setText(costoT);
                jLResumenCostoU.setText(costoU);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    
    private void updatesHojaC(){
        String updateCantidades = "UPDATE hojaC SET manoOD = (SELECT SUM(horasT * pagoH) AS costoT FROM horario), materiales = (SELECT SUM(cantidad * precioU) AS costoT FROM materiales), gif = (SELECT ROUND((presupuesto / horasMOD), 2) AS costoT FROM tasaGIF) WHERE id = 1;";
        String updateCostoT = "UPDATE hojaC SET costoT = (manoOD + materiales + gif) WHERE id = 1";
        String updateCostoU = "UPDATE hojac SET costoU = costoT / (SELECT cantidad FROM cliente) WHERE id = 1";
        
        try {
            con = connect.getConnection();
            st = con.createStatement();
            st.execute(iniciarT);
            st.execute(updateCantidades);
            st.execute(updateCostoT);
            st.execute(updateCostoU);
            con.commit();
        } catch (Exception e) {
            System.out.println("El error fue: " + e);
            if (con != null) {
                try {
                    JOptionPane.showMessageDialog(null, "Deshaciendo Cambios");
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex);
                }
            }
        } finally {
            try {
                if (st != null && con != null) {
                    con.setAutoCommit(true);
                    st.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar " + e);
            }
        }
    }
    
    private void limpiarLabelsHojaC(){
        jLCliente.setText("");
        jLNumeroO.setText("");
        jLCantidad.setText("");
        jLDescripcion.setText("");
        jLFechaI.setText("");
        jLFechaT.setText("");
        jLResumenMOD.setText("");
        jLResumenMateriales.setText("");
        jLResumenGIF.setText("");
        jLResumenCostoT.setText("");
        jLResumenCostoU.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPBackground = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jTFCliente = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTFNumeroO = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jTFDescripcion = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jTFCantidad = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLCliente = new javax.swing.JLabel();
        jLNumeroO = new javax.swing.JLabel();
        jLCantidad = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLFechaI = new javax.swing.JLabel();
        jLFechaT = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLDescripcion = new javax.swing.JLabel();
        jTFFechaI = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jTFFechaT = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLFechaMOD = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLHorasMOD = new javax.swing.JLabel();
        jLPagoHMOD = new javax.swing.JLabel();
        jLCostoMOD = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLFechaMateriales = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLCostoMateriales = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLNumeroRMateriales = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLFechaGIF = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLTasaGIF = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jLHorasGIF = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLCostoGIF = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLResumenGIF = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLResumenMateriales = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLResumenCostoT = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLResumenCostoU = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLResumenMOD = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jBAgregarInfo = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(246, 246, 246));

        jPBackground.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator1.setForeground(new java.awt.Color(175, 175, 175));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTFCliente.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTFCliente.setText("Cliente");
        jTFCliente.setBorder(null);
        jTFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFClienteMousePressed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel1.setText("Información");

        jTFNumeroO.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTFNumeroO.setText("N° orden");
        jTFNumeroO.setBorder(null);
        jTFNumeroO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFNumeroOMousePressed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jTFDescripcion.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTFDescripcion.setText("Descripcion");
        jTFDescripcion.setBorder(null);
        jTFDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFDescripcionMousePressed(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jTFCantidad.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTFCantidad.setText("Cantidad");
        jTFCantidad.setBorder(null);
        jTFCantidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFCantidadMousePressed(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel3.setText("Cliente:");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel4.setText("Numero de orden:");

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel6.setText("Cantidad:");

        jLCliente.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLNumeroO.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLCantidad.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jSeparator9.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel8.setText("Fecha inicio:");

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel9.setText("Fecha termino:");

        jLFechaI.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLFechaT.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel5.setText("Descripcion:");

        jLDescripcion.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLFechaT, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addComponent(jLNumeroO, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLNumeroO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLCantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLFechaI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLFechaT)))
                .addGap(0, 21, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator9)
                .addContainerGap())
        );

        jTFFechaI.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTFFechaI.setText("Fecha inicio Año/Mes/Dia");
        jTFFechaI.setBorder(null);
        jTFFechaI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFFechaIMousePressed(evt);
            }
        });

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jTFFechaT.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTFFechaT.setText("Fecha termino Año/Mes/Dia");
        jTFFechaT.setBorder(null);
        jTFFechaT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFFechaTMousePressed(evt);
            }
        });

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel2.setText("MOD");

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel13.setText("Fecha:");

        jLFechaMOD.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jSeparator13.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel14.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel14.setText("Horas:");

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel15.setText("Pago por hora:");

        jLabel16.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel16.setText("Costo:");

        jLHorasMOD.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLPagoHMOD.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLCostoMOD.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLHorasMOD, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(jLPagoHMOD, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(jLCostoMOD, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(jLFechaMOD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(53, 53, 53))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLFechaMOD)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLHorasMOD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLPagoHMOD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLCostoMOD))))
                    .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel7.setText("Materiales");

        jLFechaMateriales.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel12.setText("Costo:");

        jLCostoMateriales.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel10.setText("Fecha:");

        jSeparator12.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel11.setText("Numero de requisición:");

        jLNumeroRMateriales.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLNumeroRMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                        .addComponent(jLFechaMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLCostoMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLFechaMateriales)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLNumeroRMateriales))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLCostoMateriales)
                            .addComponent(jLabel12)))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel17.setText("GIF");

        jLFechaGIF.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel18.setText("Tasa GIF:");

        jLTasaGIF.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel19.setText("Fecha:");

        jSeparator14.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel20.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel20.setText("Horas:");

        jLHorasGIF.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel21.setText("Costo:");

        jLCostoGIF.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLHorasGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLFechaGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLTasaGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLCostoGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLFechaGIF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLHorasGIF)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLTasaGIF, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLCostoGIF)))
                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel22.setText("Resumen");

        jLabel23.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel23.setText("GIF:");

        jLResumenGIF.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jSeparator15.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel25.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel25.setText("Materiales:");

        jLResumenMateriales.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel26.setText("Costo Total:");

        jLResumenCostoT.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel27.setText("Costo unitario:");

        jLResumenCostoU.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel24.setText("MOD:");

        jLResumenMOD.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel22))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLResumenCostoU, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLResumenMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLResumenGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLResumenCostoT, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLResumenMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel24))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLResumenMateriales)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLResumenMOD)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLResumenGIF)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLResumenCostoT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLResumenCostoU)))
                    .addComponent(jSeparator15)))
        );

        jSeparator6.setForeground(new java.awt.Color(175, 175, 175));

        jBAgregarInfo.setBackground(new java.awt.Color(12, 147, 81));
        jBAgregarInfo.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jBAgregarInfo.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregarInfo.setText("AGREGAR");
        jBAgregarInfo.setBorderPainted(false);
        jBAgregarInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregarInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarInfoActionPerformed(evt);
            }
        });

        jBEliminar.setBackground(new java.awt.Color(221, 66, 62));
        jBEliminar.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jBEliminar.setForeground(new java.awt.Color(255, 255, 255));
        jBEliminar.setText("ELIMINAR");
        jBEliminar.setBorderPainted(false);
        jBEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPBackgroundLayout = new javax.swing.GroupLayout(jPBackground);
        jPBackground.setLayout(jPBackgroundLayout);
        jPBackgroundLayout.setHorizontalGroup(
            jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBackgroundLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFCliente)
                    .addComponent(jSeparator2)
                    .addComponent(jLabel1)
                    .addComponent(jTFNumeroO)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFCantidad)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFFechaI)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFFechaT)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPBackgroundLayout.createSequentialGroup()
                        .addComponent(jBAgregarInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPBackgroundLayout.createSequentialGroup()
                        .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator6))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPBackgroundLayout.setVerticalGroup(
            jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBackgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPBackgroundLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPBackgroundLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPBackgroundLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPBackgroundLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFNumeroO, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFFechaT, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAgregarInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBAgregarInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarInfoActionPerformed
        String nombre = jTFCliente.getText();
        String numeroOrden = jTFNumeroO.getText();
        String descripcion = jTFDescripcion.getText();
        String cantidad = jTFCantidad.getText();
        String fechaI = jTFFechaI.getText();
        String fechaT = jTFFechaT.getText();
        
        String queryInsert = "INSERT INTO cliente (id, nombre, numeroOrden, descripcion, cantidad, fechaInicio, fechaTermino) VALUES (1, '"+nombre+"', "+numeroOrden+", '"+descripcion+"', "+cantidad+", '"+fechaI+"', '"+fechaT+"')";
        String queryInsertHojaC = "INSERT INTO hojaC (id, manoOD, materiales, gif, costoT, costoU) VALUES (1, NULL, NULL, NULL, NULL, NULL)";
        
        if (nombre.equals("Cliente") && numeroOrden.equals("N° orden") && descripcion.equals("Descripcion") && cantidad.equals("Cantidad") && fechaI.equals("Fecha inicio Año/Mes/Dia") && fechaT.equals("Fecha termino Año/Mes/Dia")) {
            JOptionPane.showMessageDialog(null, "Ingrese datos, porfavor");
        } else if (nombre.equals("Cliente") || numeroOrden.equals("N° orden") || descripcion.equals("Descripcion") || cantidad.equals("Cantidad") || fechaI.equals("Fecha inicio Año/Mes/Dia") || fechaT.equals("Fecha termino Año/Mes/Dia")) {
            JOptionPane.showMessageDialog(null, "Ingrese los datos faltantes, por favor");
        } else {
            try {
                con = connect.getConnection();
                st = con.createStatement();
                st.execute(iniciarT);
                st.executeUpdate(queryInsert);
                st.executeUpdate(queryInsertHojaC);
                JOptionPane.showMessageDialog(null, "Registro agregado");
                con.commit();
                updatesHojaC();
                resumen();
                limpiarjTFieldCliente();
                consultaCliente();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
                if(con != null){
                    try {
                        JOptionPane.showMessageDialog(null, "Deshaciendo Cambios, usuarios maximos alcanzados");
                        con.rollback();
                        limpiarjTFieldCliente();
                        consultaCliente();
                    } catch (SQLException ex) {
                        System.out.println("Error: "+ex);
                    }
                }
            } finally {
                try {
                    if (st != null && con != null) {
                        con.setAutoCommit(true);
                        st.close();
                        con.close();
                    }   
                } catch (SQLException e) {
                    System.out.println("Error al cerrar "+e);
                }
            }
        }
    }//GEN-LAST:event_jBAgregarInfoActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        String sqlDeleteHojaC = "DELETE FROM hojaC";
        String sqlDeleteM = "DELETE FROM cliente";

        try {
            con = connect.getConnection();
            st = con.createStatement();
            st.execute(iniciarT);
            st.execute(sqlDeleteHojaC);
            st.execute(sqlDeleteM);
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
            con.commit();
            
            limpiarLabelsHojaC();
            limpiarjTFieldCliente();
            consultaCliente();
        } catch (Exception e) {
            System.out.println("El error fue: " + e);
            if (con != null) {
                try {
                    JOptionPane.showMessageDialog(null, "Deshaciendo Cambios");
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex);
                }
            }
        } finally {
            try {
                if (st != null && con != null) {
                    con.setAutoCommit(true);
                    st.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar " + e);
            }
        }
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jTFClienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFClienteMousePressed
        if (jTFCliente.getText().equals("Cliente")) {
            jTFCliente.setText("");
        }
        if (jTFNumeroO.getText().isEmpty()) {
            jTFNumeroO.setText("N° orden");   
        }

        if (jTFDescripcion.getText().isEmpty()) {
            jTFDescripcion.setText("Descripcion");  
        }

        if (jTFCantidad.getText().isEmpty()) {
            jTFCantidad.setText("Cantidad");  
        }
        if (jTFFechaI.getText().isEmpty()) {
            jTFFechaI.setText("Fecha inicio Año/Mes/Dia");  
        }

        if (jTFFechaT.getText().isEmpty()) {
            jTFFechaT.setText("Fecha termino Año/Mes/Dia");  
        }
    }//GEN-LAST:event_jTFClienteMousePressed

    private void jTFNumeroOMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFNumeroOMousePressed
        if (jTFCliente.getText().isEmpty()) {
            jTFCliente.setText("Cliente");
        }
        if (jTFNumeroO.getText().equals("N° orden")) {
            jTFNumeroO.setText("");   
        }

        if (jTFDescripcion.getText().isEmpty()) {
            jTFDescripcion.setText("Descripcion");  
        }

        if (jTFCantidad.getText().isEmpty()) {
            jTFCantidad.setText("Cantidad");  
        }
        if (jTFFechaI.getText().isEmpty()) {
            jTFFechaI.setText("Fecha inicio Año/Mes/Dia");  
        }

        if (jTFFechaT.getText().isEmpty()) {
            jTFFechaT.setText("Fecha termino Año/Mes/Dia");  
        }
    }//GEN-LAST:event_jTFNumeroOMousePressed

    private void jTFDescripcionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFDescripcionMousePressed
        if (jTFCliente.getText().isEmpty()) {
            jTFCliente.setText("Cliente");
        }
        if (jTFNumeroO.getText().isEmpty()) {
            jTFNumeroO.setText("N° orden");   
        }

        if (jTFDescripcion.getText().equals("Descripcion")) {
            jTFDescripcion.setText("");  
        }

        if (jTFCantidad.getText().isEmpty()) {
            jTFCantidad.setText("Cantidad");  
        }
        if (jTFFechaI.getText().isEmpty()) {
            jTFFechaI.setText("Fecha inicio Año/Mes/Dia");  
        }

        if (jTFFechaT.getText().isEmpty()) {
            jTFFechaT.setText("Fecha termino Año/Mes/Dia");  
        }
    }//GEN-LAST:event_jTFDescripcionMousePressed

    private void jTFCantidadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFCantidadMousePressed
        if (jTFCliente.getText().isEmpty()) {
            jTFCliente.setText("Cliente");
        }
        if (jTFNumeroO.getText().isEmpty()) {
            jTFNumeroO.setText("N° orden");   
        }

        if (jTFDescripcion.getText().isEmpty()) {
            jTFDescripcion.setText("Descripcion");  
        }

        if (jTFCantidad.getText().equals("Cantidad")) {
            jTFCantidad.setText("");  
        }
        if (jTFFechaI.getText().isEmpty()) {
            jTFFechaI.setText("Fecha inicio Año/Mes/Dia");  
        }

        if (jTFFechaT.getText().isEmpty()) {
            jTFFechaT.setText("Fecha termino Año/Mes/Dia");  
        }
    }//GEN-LAST:event_jTFCantidadMousePressed

    private void jTFFechaIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFFechaIMousePressed
        if (jTFCliente.getText().isEmpty()) {
            jTFCliente.setText("Cliente");
        }
        if (jTFNumeroO.getText().isEmpty()) {
            jTFNumeroO.setText("N° orden");   
        }

        if (jTFDescripcion.getText().isEmpty()) {
            jTFDescripcion.setText("Descripcion");  
        }

        if (jTFCantidad.getText().isEmpty()) {
            jTFCantidad.setText("Cantidad");  
        }
        if (jTFFechaI.getText().equals("Fecha inicio Año/Mes/Dia")) {
            jTFFechaI.setText("");  
        }

        if (jTFFechaT.getText().isEmpty()) {
            jTFFechaT.setText("Fecha termino Año/Mes/Dia");  
        }
    }//GEN-LAST:event_jTFFechaIMousePressed

    private void jTFFechaTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFFechaTMousePressed
        if (jTFCliente.getText().isEmpty()) {
            jTFCliente.setText("Cliente");
        }
        if (jTFNumeroO.getText().isEmpty()) {
            jTFNumeroO.setText("N° orden");   
        }

        if (jTFDescripcion.getText().isEmpty()) {
            jTFDescripcion.setText("Descripcion");  
        }

        if (jTFCantidad.getText().isEmpty()) {
            jTFCantidad.setText("Cantidad");  
        }
        if (jTFFechaI.getText().isEmpty()) {
            jTFFechaI.setText("Fecha inicio Año/Mes/Dia");  
        }

        if (jTFFechaT.getText().equals("Fecha termino Año/Mes/Dia")) {
            jTFFechaT.setText("");  
        }
    }//GEN-LAST:event_jTFFechaTMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregarInfo;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JLabel jLCantidad;
    private javax.swing.JLabel jLCliente;
    private javax.swing.JLabel jLCostoGIF;
    private javax.swing.JLabel jLCostoMOD;
    private javax.swing.JLabel jLCostoMateriales;
    private javax.swing.JLabel jLDescripcion;
    private javax.swing.JLabel jLFechaGIF;
    private javax.swing.JLabel jLFechaI;
    private javax.swing.JLabel jLFechaMOD;
    private javax.swing.JLabel jLFechaMateriales;
    private javax.swing.JLabel jLFechaT;
    private javax.swing.JLabel jLHorasGIF;
    private javax.swing.JLabel jLHorasMOD;
    private javax.swing.JLabel jLNumeroO;
    private javax.swing.JLabel jLNumeroRMateriales;
    private javax.swing.JLabel jLPagoHMOD;
    private javax.swing.JLabel jLResumenCostoT;
    private javax.swing.JLabel jLResumenCostoU;
    private javax.swing.JLabel jLResumenGIF;
    private javax.swing.JLabel jLResumenMOD;
    private javax.swing.JLabel jLResumenMateriales;
    private javax.swing.JLabel jLTasaGIF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPBackground;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCliente;
    private javax.swing.JTextField jTFDescripcion;
    private javax.swing.JTextField jTFFechaI;
    private javax.swing.JTextField jTFFechaT;
    private javax.swing.JTextField jTFNumeroO;
    // End of variables declaration//GEN-END:variables
}
