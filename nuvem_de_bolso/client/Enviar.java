package nuvem_de_bolso.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Werlan
 */
public class Enviar extends javax.swing.JFrame {

    private static Socket cliente;
    private File arq;
    private File arqSaida;
    private String arquivoSelecionado = "esperando";
    private String saida = "esperando";
    private String prontoEnviar = "esperando";
    private DiretorioThread t;
    
    /**
     * Creates new form enviar

     */
 
    public Enviar() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        diretorio = new javax.swing.JTextField();
        enviarBotao = new javax.swing.JButton();
        arquivos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        diretorioSaida = new javax.swing.JTextField();
        Dsaida = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        diretorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diretorioActionPerformed(evt);
            }
        });

        enviarBotao.setText("enviar");
        enviarBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarBotaoActionPerformed(evt);
            }
        });

        arquivos.setText("Arquivo");
        arquivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arquivosActionPerformed(evt);
            }
        });

        jLabel1.setText("Escolha um arquivo");

        diretorioSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diretorioSaidaActionPerformed(evt);
            }
        });

        Dsaida.setText("Diretorio");
        Dsaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DsaidaActionPerformed(evt);
            }
        });

        jLabel2.setText("Escolha o diretorio de saida");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Dsaida)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(diretorioSaida)
                            .addComponent(jLabel1)
                            .addComponent(arquivos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(diretorio, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                            .addComponent(enviarBotao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diretorio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(arquivos)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diretorioSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dsaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(enviarBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(416, 316));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void enviarBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarBotaoActionPerformed
        try {
            if (saida.equals("ok")){
                DataOutputStream out  = new DataOutputStream(cliente.getOutputStream());
                out.writeUTF("2");
                
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                prontoEnviar = (String) entrada.readUTF();

                if (prontoEnviar.equals("ok")){
                    EnviarThread en = new EnviarThread(cliente,arq,this);
                    en.start();
                }
            }
        } 
        catch (IOException ex) {Logger.getLogger(Enviar.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_enviarBotaoActionPerformed

    private void arquivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arquivosActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser(new File("C:\\"));
        chooser.setDialogTitle("Procurar arquivo");

        int retorno = chooser.showOpenDialog(this);
        
        if(retorno == JFileChooser.APPROVE_OPTION){
            this.arq = chooser.getSelectedFile();
            diretorio.setText(arq.getPath());
            arquivoSelecionado = "ok";
        }
    }//GEN-LAST:event_arquivosActionPerformed

    private void diretorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diretorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diretorioActionPerformed

    private void DsaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DsaidaActionPerformed
        // TODO add your handling code here:
        if (arquivoSelecionado.equals("ok")){
            try {
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());     
                DataOutputStream out  = new DataOutputStream(cliente.getOutputStream());
                
                out.writeUTF("1");
                
                Diretorio dir = new Diretorio();
                dir.setBotaoSalvar();
                dir.setVisible(true);
                dir.setArquivo(arq.getName());
                
                DiretorioThread thread = new DiretorioThread(dir,entrada,out,diretorioSaida);
                thread.start();
                t = thread;
      
                this.saida = "ok"; 
                System.out.println(arq.length());
            } 
            catch (IOException ex) {Logger.getLogger(DiretorioThread.class.getName()).log(Level.SEVERE, null, ex);}
           
        }
    }//GEN-LAST:event_DsaidaActionPerformed

    private void diretorioSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diretorioSaidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diretorioSaidaActionPerformed

    /**
     */
    public static void main() {
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
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Enviar().setVisible(true);
            }
        });
    }
    
    public void setCliente(Socket cliente){
         Enviar.cliente = cliente;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dsaida;
    private javax.swing.JButton arquivos;
    private javax.swing.JTextField diretorio;
    private javax.swing.JTextField diretorioSaida;
    private javax.swing.JButton enviarBotao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
