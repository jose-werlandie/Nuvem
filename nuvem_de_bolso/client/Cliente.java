/**
 * Classe responsavel por inciar o processo cliente.
 */
package nuvem_de_bolso.client;

/**
 *
 * @author werlan
 */
import java.io.*;
import java.net.*;

public class Cliente {
   
    public static void main(String args[]) throws ClassNotFoundException{
       
        try {
            /**
             * Criação do construtor da interface Iniciar cliente.
             */
            IniciarCliente IC = new IniciarCliente();
            IC.setVisible(true);
            
            while (true){
                /**
                 * Uma vez que um ip for valido, é criado um socket cliente
                 * até que o ip nao seja validado , uma mensagem false é exibida.
                 */
                
                IC.getVeri();
                System.out.println(IC.getVeri());
                if (IC.getVeri().equals("true")){
                //Instancia do atributo conexao do tipo Socket, 
                // conecta a IP do Servidor, Porta
                IC.setVisible(false);
                
                Socket cliente = new Socket(IC.getIndentifi(),5555);
                
                Escolha es = new Escolha();
                es.setCliente(cliente);
                es.setVisible(true);
                break;
                }
            }
        }catch(IOException e){ System.out.println("ERRO DE CONEXÃO");}   
    }
}

