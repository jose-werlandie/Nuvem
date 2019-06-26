/**
 * Pacote corresponde a programação do servidor.
 */
package nuvem_de_bolso.servidor;

/**
 *
 * @author werlan
 */
import java.io.*;
import java.net.*;

/**
 * Servidor main responsavel pela execuçãoo do servidor.
 * Continuará sua execução,até que seja finalizado .
 * Sempre que tiver um requerimento de novo cliente,manterá o mesmo conectado a uma thread,
 * de forma que varios clientes possam, enviar e baixar arquivos do servidor ao mesmo tempo.
 */

public class Servidor extends Thread {
    /**
     * private Socket conexão;.
     * Parte que controla as conexões por meio de threads.
     */
    private Socket conexao;
    /**
     * @param socket 
     */
    public Servidor(Socket socket) {
        this.conexao = socket;
    }
    
    public static void main(String args[]) {
        
        
        try {
            // cria um socket que fica escutando a porta 5555.
            ServerSocket cliente = new ServerSocket(5555);
            System.out.println("ServidorSocket rodando na porta 5555");
            // Loop principal.
            
            while (true) {
                /**
                 * Socket conexao = cliente.accept();.
                 * aguarda algum cliente se conectar.
                 * A execução do servidor fica bloqueada na chamada do método accept da.
                 * classe ServerSocket até que algum cliente se conecte ao servidor.
                 * O próprio método desbloqueia e retorna com um objeto da classe Socket.
                 */
                
                Socket conexao = cliente.accept();
                
                /**
                 * Thread t = new Servidor (conexao);.
                 * cria uma nova thread para tratar essa conexão.
                 */
                
                Thread t = new Servidor (conexao);
                t.start();
            }
        } catch (IOException e) {
         // caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }
  //execução da thread
    public void run(){
            try{
                    DataInputStream in = new DataInputStream(conexao.getInputStream());

                    while (true){
                        
                        String  entrada = (String)in.readUTF();

                        System.out.println(entrada);
                        if (entrada.equals("1")){                                       
                            Receber receber = new Receber(conexao);
                            receber.run(); 

                        }
                        if (entrada.equals("2")){
                            EnviarServidor enviar = new EnviarServidor(conexao);
                            enviar.run();
          
                        }
                    }
                }catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("Falha na Conexao... .. ."+" IOException: " + e);
        }
    }
}