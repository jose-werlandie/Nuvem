package nuvem_de_bolso.servidor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * 
 * @author Werlan D
 */
public class EnviarServidor extends Thread {
    
    private final Socket cliente;
    private String copiado;
    private String copiadoCaminho;
    
    public EnviarServidor(Socket cliente)       {this.cliente = cliente; }
    
    public void enviarDiretorio(File arquivo) throws IOException{               //essa função pegará os dados da pasta
      DataInputStream entrada = new DataInputStream(cliente.getInputStream());  //escolhida e envia pra interface diretorio
      DataOutputStream saida  = new DataOutputStream(cliente.getOutputStream());

      System.out.println(arquivo);                                              

      int lenPasta            = arquivo.list().length;                          //primeiro ele cria um array com todos
      String[] lista          = arquivo.list();                                 //os dados dentro da pasta, em seguida 
                                                                                //ele envia o caminho da pasta e o len,
      saida.writeUTF(arquivo.getPath());                                        //assim ele agora percorre e envia todos
                                                                                //os dados da pasta para a interface
      saida.writeUTF(String.valueOf(lenPasta));                                 //diretorio que serão exibidos

      for (int i = 0; i < lenPasta; i++){
          saida.writeUTF(lista[i]);
          File a = new File(arquivo.getPath() + File.separator + lista[i]);
          saida.writeUTF(String.valueOf(a.isDirectory()));
      }
    }
    
     
    public void colarArquivo(File file,String copiado, String copiadoCaminho, String colar) throws IOException{
        String[] arquivos = file.list();

        File caminhoColar = new File(colar + File.separator + copiado);
        try{
            caminhoColar.mkdir();
        }catch(Exception e){System.out.println("falha ao cria pasta");}

        for (String arquivo : arquivos) {
            File arq = new File(copiadoCaminho + File.separator + arquivo);
            if (arq.isDirectory()) {
                this.colarArquivo(arq, arquivo, file + File.separator + arquivo, caminhoColar.getPath());
            }
            if (arq.isFile()) {
                FileOutputStream caminhoSaida;
                try (final FileInputStream copia = new FileInputStream(arq)) {
                    caminhoSaida = new FileOutputStream(caminhoColar + File.separator + arquivo);
                    try{
                        
                        int begin = 0;
                        int len = (int)arq.length();
                        
                        while (true){
                            
                            byte[] bt = new byte[50];
                            
                            // DataInputStream para processar os bytes recebidos
                            
                            
                            int leitura = copia.read(bt);
                            
                            while(leitura != -1)
                            {
                                if(leitura != -2) {
                                    caminhoSaida.write(bt,begin,leitura);
                                    caminhoSaida.flush();
                                }
                                leitura = copia.read(bt);
                            }
                            
                            begin += 50;
                            
                            if (begin >= len)  { break; }
                        }
                    }
                    catch(IOException e){System.out.println("impossivel colar");}
                }
                caminhoSaida.close();
            }
        }                            
    }
    
    public void excluiArquivo(File excluir){
        String[] pasta = excluir.list();
        
        for (String pasta1 : pasta) {
            File arq = new File(excluir + File.separator + pasta1);
            if (arq.isFile()){
                try{
                    arq.delete();
                }catch(Exception e){System.out.println("impossivel excluir arquivo");}
            }
            if (arq.isDirectory()){
                this.excluiArquivo(arq);
                try{
                    arq.delete();
                }catch(Exception e){System.out.println("impossivel excluir arquivo");}
            }
        }
    }
    
    public String pegaDadosDiretorio(ArrayList<String> historico, File arquivo, 
                                   File home,EnviarServidor e,String diretorioSaida) throws IOException{
        
        DataInputStream entrada = new DataInputStream(cliente.getInputStream());
        DataOutputStream saida  = new DataOutputStream(cliente.getOutputStream());
        
        while (true)                                                            //pega as info de disco rigido e coloca
        {                                                                       //num array para enviar pro cliente
            String action = entrada.readUTF();
            
            if (action.equals("-1")){                               //cancela
                break;
            }
            
            if (action.equals("0")){                               //inicializa                      
                historico.add(arquivo.getPath());                  //adiciona num array de caminhos o caminho (C:\\POO...) inicio
                                                                   //que é enviado para o cliente escolher o diretorio onde salvar.
                e.enviarDiretorio(arquivo);
            }
            
            if (action.equals("1")){                               //seleciona pasta
                String pasta = entrada.readUTF();                       //qnd o usuario seleciona uma pasta ele manda o nome da pasta 
                                                                        //assim ele coloca esse caminho no historico e pega o caminho
                arquivo = new File(arquivo + File.separator + pasta);   //anterior e add essa nova pasta ex.: a pasta ecolhida foi
                                                                        //"teste" suponha q o caminho era "C:\\POO" então o caminho
                historico.add(arquivo.getPath());                       //ficara "C:\\POO\\teste" e as info dessa pasta serão mandadas
                                                                        //pra interface diretorio
                e.enviarDiretorio(arquivo);
            }
            
            if (action.equals("2")){                               //voltar
                if (historico.size() > 1){                                      //primeiramente verifica se o usuario esta no caminho inicial,
                    String ultimo = historico.get(historico.size() - 1);        //estando ele ñ fará nada, não estando, ele irá 
                    String ant = historico.get(historico.size() - 2);           //pegar o ultimo e o penultimo caminho,
                                                                                //o ultimo ele remove do historico e o penultimo é selecionado
                    arquivo = new File(ant);
                    historico.remove(ultimo);
                }
                e.enviarDiretorio(arquivo);
            }
            
            if (action.equals("3")){                               //salvar
                diretorioSaida = entrada.readUTF();
                break;
            }
            
             if (action.equals("4")){                                //criar pasta
                String pasta = entrada.readUTF();
                System.out.println(pasta);
                File newPasta = new File (pasta);
                try{
                    newPasta.mkdir();
                }
                catch(Exception i){System.out.println("impossivel criar pasta");}
            }
            
            if (action.equals("5")){                                //excluir elemento
                String elemento = entrada.readUTF();
                File arquivoExcluir = new File(elemento);
                
                if (arquivoExcluir.isFile()){
                    try{
                        arquivoExcluir.delete();
                    }
                    catch(Exception i){System.out.println("impossivel deletar elemento");}
                }
                if (arquivoExcluir.isDirectory()){
                    e.excluiArquivo(arquivoExcluir);
                    try{
                        arquivoExcluir.delete();
                    }catch(Exception i){System.out.println("impossivel excluir arquivo");}
                }
            }
            
            if (action.equals("6")){                                //copiar elemento
                this.copiado = entrada.readUTF();
                this.copiadoCaminho = entrada.readUTF();
            }
            
            if (action.equals("7")){                                //colar
                String colar = entrada.readUTF();
                System.out.println("vai ser aki " + colar + File.separator + copiado);
                
                File file = new File(copiadoCaminho);
                
                if (file.isFile()){
                    
                    FileOutputStream caminhoSaida;
                    try(FileInputStream copia = new FileInputStream(file)) {

                        caminhoSaida = new FileOutputStream(colar + File.separator + copiado);
                        try{
                            
                            int begin = 0;
                            int len = (int)file.length();
                            
                            while (true){
                                
                                byte[] bt = new byte[50];
                                
                                // DataInputStream para processar os bytes recebidos
                                
                                
                                int leitura = copia.read(bt);
                                
                                while(leitura != -1)
                                {
                                    if(leitura != -2) {
                                        caminhoSaida.write(bt,begin,leitura);
                                        caminhoSaida.flush();
                                    }
                                    leitura = copia.read(bt);
                                }
                                
                                begin += 50;
                                
                                if (begin >= len)  { break; }
                            }
                        }
                        catch(IOException i){System.out.println("impossivel colar");}
                    }
                    caminhoSaida.close();
                }
                
                if (file.isDirectory()){
                    e.colarArquivo(file, copiado, copiadoCaminho, colar);
                }
                    
            }
            
            
            if (action.equals("8")){
                String novoNome = entrada.readUTF();
                String nomeAntigo = entrada.readUTF();
                
                System.out.println(novoNome);
                File novoFile = new File(novoNome);
                boolean certo = new File(nomeAntigo).renameTo(novoFile);
            }
            
        }
        return diretorioSaida;    
    }
    
    @Override
    public void run()
    {
      try{
        while(true)
        {
          File home               = new File("C:\\POO");                        //criação do caminho inicial.
          File arquivo            = new File(home.getPath());                   //inicialização do caminho q será continuado pelo usuario

          ArrayList<String> historico = new ArrayList<>();

          String diretorioSaida = null;
          EnviarServidor e = new EnviarServidor(cliente);   

          DataInputStream in = new DataInputStream(cliente.getInputStream());


          String selecao = in.readUTF();

          if (selecao.equals("1")){
              e.pegaDadosDiretorio(historico,arquivo,home,e,diretorioSaida);
          }


          if (selecao.equals("2"))
          {
            String diretorioArquivo = (String) in.readUTF();                          //(Recebe diretorio do arquivo 
                                                                                        // a ser baixado).
            File arq       = new File (diretorioArquivo);                         //(Pega o arquivo escolhido pelo cliente).

            String confirmacao = (String) in.readUTF();                               //(Recebe "ok" para receber arquivo).
            //------------------------------------------------------------------------\\     
             if (confirmacao.equals("ok"))
             {
              DataInputStream entrada = new DataInputStream(cliente.getInputStream());
              DataOutputStream saida  = new DataOutputStream(cliente.getOutputStream());

              String len = String.valueOf(arq.length());
              System.out.println(len);

              saida.writeUTF(len);
            //------------------------------------------------------------------------\\   
              DataOutputStream out  = new DataOutputStream(cliente.getOutputStream());

              FileInputStream file = new FileInputStream(arq);                     //Leitura do arquivo solicitado.

              DataInputStream a = new DataInputStream(file);                           //(DataInputStream para processar 
                                                                                        // o arquivo solicitado).
              int begin = 0;
              int tamanhoArray = 1000000;
            //------------------------------------------------------------------------\\   


              byte buffer[] = new byte[tamanhoArray];                                       //Buffer de leitura dos bytes do arquivo.

              int leitura = a.read(buffer);
              int cont = 0; 

              while(leitura != - 1)                                               //(Lendo os bytes do arquivo e enviando
              {                                                                   //  para o socket).
                  cont += leitura;
                  if(leitura != - 2) 
                  {
                      out.write(buffer,begin,leitura);
                      out.flush();   
                  }
                  leitura = a.read(buffer);
              }
              a.close();
              break;
             
            }
          }
        }
      }
      catch(IOException e){System.out.println("merda");}
    
    }
}
