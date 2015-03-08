package br.com.centralit.citcorpore.comm.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
  
public class SampleClient {  
  
    public static void main(String[] args) {  
          
        //Declaro o socket cliente  
        Socket s = null;  
          
        //Declaro a Stream de saida de dados  
        PrintStream ps = null;  
          
        try{  
              
            //Cria o socket com o recurso desejado na porta especificada  
            s = new Socket("192.168.1.101",7000);  
              
            //Cria a Stream de saida de dados  
            ps = new PrintStream(s.getOutputStream());  
            
            Collection<String> col = new ArrayList<String>();
            col.add("Estou enviando dados para o servidor");
            col.add("DE NOVO!");
            col.add("ISTO EH UM TESTE!");
            col.add("STOP");
            
            boolean running = true;
            while(running){
            	String dadoRecebido = "";
            	for (Iterator<String> it = col.iterator(); it.hasNext();){
            		String msg = (String)it.next();
            		//Imprime uma linha para a stream de saída de dados  
            		ps.println(msg);
            		
    				// Declaro o leitor para a entrada de dados
    				BufferedReader entrada = null;
    				
    				// Cria um BufferedReader para o canal da stream de entrada de dados
    				// do socket s
    				try {
    					entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
    				} catch (IOException e) {
    					System.out.println("Algum problema ocorreu para receber dados do socket.");
    					System.out.println("Comunicação encerrada!");
    					e.printStackTrace();
    					break;
    				}  
    				
    				String dadoRecebidoAux = entrada.readLine();
    				if(dadoRecebidoAux != null){
    					dadoRecebido = new String(dadoRecebidoAux.getBytes());
    				}
    				if (dadoRecebido != null){
    					if (dadoRecebido.equalsIgnoreCase("OK-REC")){
    						System.out.println("O servidor recebeu a mensagem!");
    					}
    					if (dadoRecebido.equalsIgnoreCase("OK-STOP")){
    						System.out.println("O servidor recebeu o comando de encerramento!");
    						running = false;
    						break; //Entao encerra!
    					}
    				}
            	}
            }
            
        //Trata possíveis exceções  
        }catch(IOException e){  
              
            System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");  
            System.out.println("Comunicação encerrada!");
          
        }finally{  
              
            try{  
                //Encerra o socket cliente  
            	if(s != null){
            		s.close();  
            	}
            }catch(IOException e){}  
          
        }  
  
    }  
}  
