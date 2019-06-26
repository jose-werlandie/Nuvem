/*
 * 
 */
package nuvem_de_bolso.client;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lucas
 */
public class ReceberThread extends Thread{
    private final Socket cliente;
    private final String saida;
    private int len = 1;
    private Receber r;
    
    public ReceberThread(Socket cliente, String saida, int len, Receber r){
        this.r = r;
        this.cliente = cliente;
        this.saida = saida;
        if (len > 0){this.len = len;}
    }
    
    @Override
    public void run() {
        ////////////////////////////////////////////////////////////////////
        try {   
            //FileOuputStream para salvar o arquivo recebido
            FileOutputStream direSaida;
        
            direSaida = new FileOutputStream(saida);
        
            Carregando c = new Carregando();
            c.setVisible(true);

            int tamanhoArray = 1000000;
            int begin = 0;
            int cont = 0;
            int cont2 = 0;
            
            String t = String.valueOf(len);
            int div = 1;
            if (len > 2000000){div = (int) Math.pow(10,(t.length() - 6));}
            int total = len/div;

            byte[] br = new byte[tamanhoArray];

            // DataInputStream para processar os bytes recebidos 
            DataInputStream in = new DataInputStream(cliente.getInputStream());
           
            
            int leitura = in.read(br);

            while(leitura != 1) {
                cont += leitura/div;
                cont2 += leitura;
                c.setValorBarra((int)(cont * 100 / total));
                if(leitura != -2) {
                    direSaida.write(br,begin,leitura);
                    direSaida.flush(); 
                }
                if(cont2 == this.len){
                    break;
                }

                leitura = in.read(br);              
            }
            direSaida.close();
            c.dispose();
            r.dispose();
            JOptionPane.showMessageDialog(c,"arquivo baixado" );
        } 
        catch (FileNotFoundException ex){Logger.getLogger(ReceberThread.class.getName()).log(Level.SEVERE, null, ex);} catch (IOException ex) {          
            Logger.getLogger(ReceberThread.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }
    
}
