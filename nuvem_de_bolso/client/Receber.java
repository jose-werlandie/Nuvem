package nuvem_de_bolso.client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author lucas
 */
public class Receber extends javax.swing.JFrame {
    private static Socket cliente;
    public File arquivo;
    public String arquivoBaixar = "esperando";
    public String diretorioSelecionado = "esperando";
    DiretorioThread thread;
    
    public int len;

    
    public Receber() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        escolherDiretorio = new javax.swing.JTextField();
        diretorio = new javax.swing.JButton();
        baixarBotao = new javax.swing.JButton();
        diretorioArquivo = new javax.swing.JTextField();
        ArquivoDoServidor = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Escolha o diretorio de saida");

        escolherDiretorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escolherDiretorioActionPerformed(evt);
            }
        });

        diretorio.setText("Diretorio");
        diretorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diretorioActionPerformed(evt);
            }
        });

        baixarBotao.setText("baixar");
        baixarBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baixarBotaoActionPerformed(evt);
            }
        });

        ArquivoDoServidor.setText("Arquivo");
        ArquivoDoServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArquivoDoServidorActionPerformed(evt);
            }
        });

        jLabel2.setText("Escolha o arquivo a ser baixado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(escolherDiretorio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(diretorio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(192, 192, 192))
                    .addComponent(ArquivoDoServidor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(baixarBotao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diretorioArquivo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diretorioArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ArquivoDoServidor)
                .addGap(3, 3, 3)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(escolherDiretorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diretorio)
                .addGap(30, 30, 30)
                .addComponent(baixarBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(416, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void diretorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diretorioActionPerformed
        // TODO add your handling code here:
        try {
            if (arquivoBaixar.equals("ok")){
            
                DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
           
                JFileChooser chooser = new JFileChooser(new File("C:\\POO\\teste_POO"));
                chooser.setDialogTitle("Procurar diretorio");
                
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int retorno = chooser.showSaveDialog(this);
                
                if(retorno == JFileChooser.APPROVE_OPTION){
                    this.arquivo = new File(chooser.getSelectedFile().toString() + File.separator + thread.getUltimo());
                    escolherDiretorio.setText(arquivo.getPath());
                    diretorioSelecionado = "ok";
                }
            } 
        } catch (IOException ex) {Logger.getLogger(Receber.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_diretorioActionPerformed

    private void escolherDiretorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escolherDiretorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_escolherDiretorioActionPerformed

    private void ArquivoDoServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArquivoDoServidorActionPerformed
        // TODO add your handling code here:
        try {
            DataInputStream entrada = new DataInputStream(cliente.getInputStream()); 
            DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
            
            out.writeUTF("1");

            Diretorio dir = new Diretorio();
            dir.setBotaoAbrir();
            dir.setVisible(true);

            thread = new DiretorioThread(dir,entrada,out,diretorioArquivo);
            thread.start();

            arquivoBaixar = "ok";
            
            } catch (IOException ex) {Logger.getLogger(Receber.class.getName()).log(Level.SEVERE, null, ex);}        
        
    }//GEN-LAST:event_ArquivoDoServidorActionPerformed

    private void baixarBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baixarBotaoActionPerformed
        // TODO add your handling code here:
        try {
            if (diretorioSelecionado.equals("ok")){
                DataInputStream in = new DataInputStream(cliente.getInputStream());
                DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
                
                out.writeUTF("2");     // manda "2" permitindo q o arquivo seja enviado. 
                
                out.writeUTF(diretorioArquivo.getText());
                
                out.writeUTF("ok");
                
                String str = in.readUTF();
                int len = Integer.valueOf(str);
                
                ReceberThread receber = new ReceberThread(cliente,escolherDiretorio.getText(),len,this);
                receber.start();
            }
        } 
        catch (IOException ex) {Logger.getLogger(Receber.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_baixarBotaoActionPerformed
    
    
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
            java.util.logging.Logger.getLogger(Receber.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receber.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receber.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receber.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receber().setVisible(true);
            }
        });
    }
    
    
    public void setCliente(Socket cliente){
         Receber.cliente = cliente;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ArquivoDoServidor;
    private javax.swing.JButton baixarBotao;
    private javax.swing.JButton diretorio;
    private javax.swing.JTextField diretorioArquivo;
    private javax.swing.JTextField escolherDiretorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
