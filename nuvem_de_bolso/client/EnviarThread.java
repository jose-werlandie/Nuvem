package nuvem_de_bolso.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lucas
 */
public class EnviarThread extends Thread{
    Socket cliente;
    File arquivo;
    long len = 1;
    private Enviar e;
    
    public EnviarThread(Socket cliente, File arquivo, Enviar e){
        this.e = e;
        this.cliente = cliente;
        this.arquivo = arquivo;
        if(arquivo.length() > 0){this.len = arquivo.length();}
    }
    
    /**
     *
     */
    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());

            DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());

            String len = String.valueOf(arquivo.length());
            System.out.println(len);


            saida.writeUTF(len);
            saida.writeUTF(arquivo.getName());
            
            Carregando c = new Carregando();
            c.setVisible(true);

            ////////////////////////////////////////////////////////////////////
                DataOutputStream out  = new DataOutputStream(cliente.getOutputStream());

                //Leitura do arquivo solicitado
                FileInputStream file = new FileInputStream(arquivo);
                //DataInputStream para processar o arquivo solicitado
                DataInputStream a = new DataInputStream(file);

                int tamanhoArray = 1000000;
                int begin = 0;
                int cont = 0;
                String t = String.valueOf(len);
                int div = 1;
                int int_len = Integer.valueOf(len);
                if (int_len > 2000000){div = (int) Math.pow(10,(t.length() - 6));}
                int total = int_len/div;

                
                    

                //Buffer de leitura dos bytes do arquivo
                byte buffer[] = new byte[tamanhoArray];

                int leitura = a.read(buffer);
                //Lendo os bytes do arquivo e enviando para o socket  
                while(leitura != - 1) {
                    cont += leitura/div;
                    c.setValorBarra((int)(cont * 100 / total));
                    if(leitura != - 2) {
                        out.write(buffer,begin,leitura); 
                        out.flush();
                    }
                    leitura = a.read(buffer);
                }
                a.close();
              
                c.dispose();
                e.dispose();
                JOptionPane.showMessageDialog(c,"arquivo enviado" );
        } 
        catch (IOException ex) { Logger.getLogger(EnviarThread.class.getName()).log(Level.SEVERE, null, ex);}      
    }
}
