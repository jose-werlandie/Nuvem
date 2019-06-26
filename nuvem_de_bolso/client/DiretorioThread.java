package nuvem_de_bolso.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

/**
 *
 * @author Werlan
 */
public class DiretorioThread extends Thread{
    private final Diretorio dir;
    private final DataInputStream entrada;
    private final DataOutputStream saida;
    private String diretorioSaida;
    private int finaliza = 0;
    private final JTextField SAIDA;
    private String ultimo;
    
    public DiretorioThread(Diretorio dir, DataInputStream entrada, DataOutputStream saida, JTextField diretorioSaida){
        this.dir = dir;
        this.entrada = entrada;
        this.saida = saida;
        this.SAIDA = diretorioSaida;
    }
    
    public String getExtensao (String arquivo){
        char[] palavra = arquivo.toCharArray();
        String extensao = "";
        
        for (int i = (palavra.length - 1); i >= 0; i--){
            if(String.valueOf(palavra[i]).equals(".")){
                for(int j = i; j < palavra.length ; j++){
                    extensao += palavra[j];
                }
                break; 
            }
        }
        
        return extensao;
    }
    
    public void run(){
        
        try {
            while(true){
                int action = dir.getAction();
            
                saida.writeUTF(String.valueOf(action));
                saida.flush();

                Thread.sleep(100);

                if (action == -1){dir.setAction(0);dir.dispose();finaliza = 1;return;}

                if (action == 0){
                    String diretorio = entrada.readUTF();
                    int len = Integer.valueOf(entrada.readUTF());
                    DefaultListModel arq = new DefaultListModel();

                    for (int i = 0; i < len; i++){
                        String arquivo = entrada.readUTF();
                        String ehPasta = entrada.readUTF();
                        if (ehPasta.equals("false")){
                            arq.addElement(new ImgsNText(arquivo,new ImageIcon("C:\\POO\\teste_POO\\folha.png")));
                        }
                        else{
                            arq.addElement(new ImgsNText(arquivo,new ImageIcon("C:\\POO\\teste_POO\\pasta.png")));
                        }
                    }
                    dir.setList(arq);
                    dir.setDiretorio(diretorio);
                    dir.setAction(-10);
                    dir.setClickou(false);
                }

                if (action == 1){
                    String pasta = dir.getSelecionado();
                    saida.writeUTF(pasta);
                    saida.flush();
                    ultimo = pasta;

                    String diretorio = entrada.readUTF();
                    int len = Integer.valueOf(entrada.readUTF());

                    DefaultListModel arq = new DefaultListModel();

                    for (int i = 0; i < len; i++){
                        String arquivo = entrada.readUTF();
                        String ehPasta = entrada.readUTF();
                        if (ehPasta.equals("false")){
                            arq.addElement(new ImgsNText(arquivo,new ImageIcon("C:\\POO\\teste_POO\\folha.png")));
                        }
                        else{
                            arq.addElement(new ImgsNText(arquivo,new ImageIcon("C:\\POO\\teste_POO\\pasta.png")));
                        }
                    }
                    dir.setList(arq);
                    dir.setDiretorio(diretorio);
                    dir.setAction(-10);
                }

                if (action == 2){
                    try{
                        String diretorio = entrada.readUTF();

                        int len = Integer.valueOf(entrada.readUTF());

                        DefaultListModel arq = new DefaultListModel();

                        for (int i = 0; i < len; i++){
                            String arquivo = entrada.readUTF();
                            String ehPasta = entrada.readUTF();
                            if (ehPasta.equals("false")){
                                arq.addElement(new ImgsNText(arquivo,new ImageIcon("C:\\POO\\teste_POO\\folha.png")));
                            }
                            else{
                                arq.addElement(new ImgsNText(arquivo,new ImageIcon("C:\\POO\\teste_POO\\pasta.png")));
                            }
                        }
                        dir.setList(arq);
                        dir.setDiretorio(diretorio);
                        dir.setAction(-10);
                    }
                    catch(Exception e){dir.setAction(-10);}
                }

                if (action == 3){
                    String d = dir.getDiretorio();
                    ultimo = dir.getSelecionado();
                    saida.writeUTF(d);
                    saida.flush();
                    diretorioSaida = d ;
                    dir.dispose();
                    finaliza = 1;
                    SAIDA.setText(diretorioSaida);
                    return;
                }

                if(action == 4){
                    String pasta = dir.getPasta() + File.separator + dir.getNomePasta();
                    saida.writeUTF(pasta);
                    saida.flush();
                    
                    dir.setNomePasta(null);
                    dir.setAction(0);
                    action = 0;
                }
                
                if (action == 5){
                    String elemento = dir.getPasta() + File.separator + dir.getSelecionado();
                    saida.writeUTF(elemento);
                    saida.flush();
                    dir.setAction(0);
                }
                
                if (action == 6){
                    String copiado = dir.getSelecionado();
                    String caminho = dir.getPasta() + File.separator + dir.getSelecionado();
                    saida.writeUTF(copiado);
                    saida.writeUTF(caminho);
                    saida.flush();
                    dir.setAction(-10);
                }
                
                if (action == 7){
                    String colar = dir.getPasta();
                    saida.writeUTF(colar);
                    saida.flush();
                    dir.setAction(0);
                }
                
                if (action == 8){ 
                    String select = dir.getSelecionado();
                    String novo = "";
                    
                    if (dir.getIcone().equals(dir.getIconeFolha())){
                        String extensao = this.getExtensao(select);
                        novo = dir.getNovoNome() +  extensao;
                    }
                    if (dir.getIcone().equals(dir.getIconePasta())){
                        novo = dir.getNovoNome();
                    }
                    
                    String novoNome = dir.getPasta() + File.separator + novo;
                    saida.writeUTF(novoNome);
                    saida.writeUTF(dir.getPasta() + File.separator + dir.getSelecionado());
                    saida.flush();
                    
                    dir.SetNovoNome(null);
                    dir.setAction(0);
                }
            } 
        } catch (IOException | InterruptedException ex) {Logger.getLogger(DiretorioThread.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    public String getSaida(){
        return this.diretorioSaida;
    }
    
    public String getUltimo(){
        return this.ultimo;
    }
    
    public int getFinaliza(){
        return this.finaliza;
    }
}
