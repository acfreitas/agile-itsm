package br.com.centralit.citcorpore.comm.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
  
public class Client {  
  
    public static void main(String[] args) {  
          
        //Declaro o socket cliente  
        Socket s = null;  
          
        //Declaro a Stream de saida de dados  
        PrintStream ps = null;  
          
        try{  
              
            //Cria o socket com o recurso desejado na porta especificada  
            s = new Socket("192.168.1.117",7000);  
              
            //Cria a Stream de saida de dados  
            ps = new PrintStream(s.getOutputStream());  
            
            Collection<String> col = new ArrayList<String>();
            col.add("GET-INVENTORY");
            
            boolean running = true;
            while(running){
            	String dadoRecebido = "";
        		String msg = "GET-INVENTORY";
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
				
				String dadoRecebidoAux = "";
				try{
					dadoRecebidoAux = entrada.readLine();
				}catch (Exception e) {
					//Deixa passar!!
				}
				if (dadoRecebidoAux == null){
					dadoRecebidoAux = "";
				}
				dadoRecebido = new String(dadoRecebidoAux.getBytes());
				if (dadoRecebido != null){
					System.out.println("Resultado recebido!");
					System.out.println(dadoRecebido);
					
					//dadoRecebido = dadoRecebido.replaceAll("INVENTORYRESPONSE", "");
					
					BufferedWriter br = new BufferedWriter(new FileWriter(new File("C:\\XML_DA_MAQUINA_EMAURI.xml")));
		            br.write(dadoRecebido);  
		            br.close();
					
					running = false;
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
