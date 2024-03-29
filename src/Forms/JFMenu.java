/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Forms;

import Views.ConexionSQL;
import Views.JPHojaCostos;
import Views.JPInicio;
import Views.JPRequisicionM;
import Views.JPTasaGIF;
import Views.JPTrabajador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class JFMenu extends javax.swing.JFrame {
    int xMouse, yMouse; //Variables para mover la barra
    ConexionSQL connect = new ConexionSQL();
    Connection con;
    Statement st;
    ResultSet rs;
    String iniciarT = "BEGIN";
    /**
     * Creates new form JFMenuu
     */
    public JFMenu() {
        initComponents();
        initContent();
    }
    
    private void initContent(){
        JPInicio vinicio = new JPInicio();
            vinicio.setSize(1012, 543);
            vinicio.setLocation(0, 0);

            jPViews.removeAll();
            jPViews.add(vinicio, BorderLayout.CENTER);
            jPViews.revalidate();
            jPViews.repaint();
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
        jPBarra = new javax.swing.JPanel();
        jPExit = new javax.swing.JPanel();
        jLExit = new javax.swing.JLabel();
        jPMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLRequisicionM = new javax.swing.JLabel();
        jLHojaT = new javax.swing.JLabel();
        jLTasaGIF = new javax.swing.JLabel();
        jLHojaC = new javax.swing.JLabel();
        jPViews = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPBackground.setBackground(new java.awt.Color(255, 255, 255));

        jPBarra.setBackground(new java.awt.Color(255, 255, 255));
        jPBarra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPBarraMouseDragged(evt);
            }
        });
        jPBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPBarraMousePressed(evt);
            }
        });

        jPExit.setBackground(new java.awt.Color(255, 255, 255));

        jLExit.setBackground(new java.awt.Color(236, 236, 236));
        jLExit.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLExit.setText("X");
        jLExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPExitLayout = new javax.swing.GroupLayout(jPExit);
        jPExit.setLayout(jPExitLayout);
        jPExitLayout.setHorizontalGroup(
            jPExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );
        jPExitLayout.setVerticalGroup(
            jPExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLExit, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPBarraLayout = new javax.swing.GroupLayout(jPBarra);
        jPBarra.setLayout(jPBarraLayout);
        jPBarraLayout.setHorizontalGroup(
            jPBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBarraLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPBarraLayout.setVerticalGroup(
            jPBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPMenu.setBackground(new java.awt.Color(246, 246, 246));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLRequisicionM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLRequisicionM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/RequisicionM.png"))); // NOI18N
        jLRequisicionM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLRequisicionM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLRequisicionMMouseClicked(evt);
            }
        });

        jLHojaT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLHojaT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/TiempoT.png"))); // NOI18N
        jLHojaT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLHojaT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLHojaTMouseClicked(evt);
            }
        });

        jLTasaGIF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLTasaGIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/TazaGIF.png"))); // NOI18N
        jLTasaGIF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLTasaGIF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLTasaGIFMouseClicked(evt);
            }
        });

        jLHojaC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLHojaC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/HojaC.png"))); // NOI18N
        jLHojaC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLHojaC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLHojaCMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPMenuLayout = new javax.swing.GroupLayout(jPMenu);
        jPMenu.setLayout(jPMenuLayout);
        jPMenuLayout.setHorizontalGroup(
            jPMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLRequisicionM, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLHojaT, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLTasaGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLHojaC, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPMenuLayout.setVerticalGroup(
            jPMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMenuLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLRequisicionM, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLHojaT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLTasaGIF, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLHojaC, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        jPViews.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPViewsLayout = new javax.swing.GroupLayout(jPViews);
        jPViews.setLayout(jPViewsLayout);
        jPViewsLayout.setHorizontalGroup(
            jPViewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1021, Short.MAX_VALUE)
        );
        jPViewsLayout.setVerticalGroup(
            jPViewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPBackgroundLayout = new javax.swing.GroupLayout(jPBackground);
        jPBackground.setLayout(jPBackgroundLayout);
        jPBackgroundLayout.setHorizontalGroup(
            jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPBarra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPBackgroundLayout.createSequentialGroup()
                .addComponent(jPMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPViews, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPBackgroundLayout.setVerticalGroup(
            jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBackgroundLayout.createSequentialGroup()
                .addComponent(jPBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPViews, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLExitMouseClicked
        String sqlDeleteHojaC = "DELETE FROM hojaC";
        String sqlDeleteM = "DELETE FROM cliente";
        String sqlDeleteTG = "DELETE FROM tasaGIF";
        String sqlDeleteH = "DELETE FROM horario";
        String sqlDeleteE = "DELETE FROM Empleado";
        String sqlDeleteMateriales = "DELETE FROM materiales";
        String sqlDeleteR = "DELETE FROM requisicion";

        try {
            con = connect.getConnection();
            st = con.createStatement();
            st.execute(iniciarT);
            st.execute(sqlDeleteHojaC);
            st.execute(sqlDeleteM);
            st.execute(sqlDeleteTG);
            st.execute(sqlDeleteH);
            st.execute(sqlDeleteE);
            st.execute(sqlDeleteMateriales);
            st.execute(sqlDeleteR);
            JOptionPane.showMessageDialog(null, "Saliendo");
            con.commit();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
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
                    JFLogin vLogin = new JFLogin();
                    vLogin.setVisible(true);
                    dispose();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar " + e);
            }
        }
    }//GEN-LAST:event_jLExitMouseClicked

    private void jPBarraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBarraMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse,y - yMouse);
    }//GEN-LAST:event_jPBarraMouseDragged

    private void jPBarraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBarraMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPBarraMousePressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        initContent();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLExitMouseEntered
        jPExit.setBackground(Color.red);
        jLExit.setForeground(Color.white);
    }//GEN-LAST:event_jLExitMouseEntered

    private void jLExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLExitMouseExited
        jPExit.setBackground(Color.white);
        jLExit.setForeground(Color.black);
    }//GEN-LAST:event_jLExitMouseExited

    private void jLRequisicionMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLRequisicionMMouseClicked
        try {
            JPRequisicionM vRequisicion = new JPRequisicionM();
            vRequisicion.setSize(1012, 543);
            vRequisicion.setLocation(0, 0);

            jPViews.removeAll();
            jPViews.add(vRequisicion, BorderLayout.CENTER);
            jPViews.revalidate();
            jPViews.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(JFMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLRequisicionMMouseClicked

    private void jLHojaTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLHojaTMouseClicked
        try {
            JPTrabajador vTiempoT = new JPTrabajador();
            vTiempoT.setSize(1012, 543);
            vTiempoT.setLocation(0,0);

            jPViews.removeAll();
            jPViews.add(vTiempoT, BorderLayout.CENTER);
            jPViews.revalidate();
            jPViews.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(JFMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLHojaTMouseClicked

    private void jLTasaGIFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLTasaGIFMouseClicked
        try {
            JPTasaGIF vTasaGIF = new JPTasaGIF();

            vTasaGIF.setSize(1012, 543);
            vTasaGIF.setLocation(0, 0);

            jPViews.removeAll();
            jPViews.add(vTasaGIF, BorderLayout.CENTER);
            jPViews.revalidate();
            jPViews.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(JFMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLTasaGIFMouseClicked

    private void jLHojaCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLHojaCMouseClicked
        try {
            JPHojaCostos vHojaC = new JPHojaCostos();
            vHojaC.setSize(1012, 590);
            vHojaC.setLocation(0, 0);

            jPViews.removeAll();
            jPViews.add(vHojaC, BorderLayout.CENTER);
            jPViews.revalidate();
            jPViews.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(JFMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLHojaCMouseClicked

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
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLExit;
    private javax.swing.JLabel jLHojaC;
    private javax.swing.JLabel jLHojaT;
    private javax.swing.JLabel jLRequisicionM;
    private javax.swing.JLabel jLTasaGIF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPBackground;
    private javax.swing.JPanel jPBarra;
    private javax.swing.JPanel jPExit;
    private javax.swing.JPanel jPMenu;
    private javax.swing.JPanel jPViews;
    // End of variables declaration//GEN-END:variables
}
