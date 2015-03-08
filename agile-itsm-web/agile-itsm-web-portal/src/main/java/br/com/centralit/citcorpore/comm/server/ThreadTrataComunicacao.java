package br.com.centralit.citcorpore.comm.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import br.com.citframework.util.UtilTratamentoArquivos;

public class ThreadTrataComunicacao extends Thread {
	private Socket sockClient = null;
	
	public ThreadTrataComunicacao(Socket sockClientParm){
		this.sockClient = sockClientParm;
	}
	
	@Override
	public void run() {
		if (sockClient == null){
			System.out.println("Não há nada pra fazer! Comunicação fechada!");
			System.out.println("Comunicação encerrada!");
			return;
		}
		
		while(true){
			String dadoRecebido = "";
			// Aguarda por algum dado e imprime a linha recebida quando recebe
			try {
				if (sockClient == null || sockClient.isClosed() || !sockClient.isConnected()){
					System.out.println("Comunicação encerrada!");
					return;
				}
				
				// Declaro o leitor para a entrada de dados
				BufferedReader entrada = null;
				
				// Cria um BufferedReader para o canal da stream de entrada de dados
				// do socket s
				try {
					entrada = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
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
				System.out.println(dadoRecebido);
				
				try{
					PrintStream ps = null;
					ps = new PrintStream(sockClient.getOutputStream());  
				}catch (Exception e) {
					e.printStackTrace();
				}				
			} catch (IOException e) {
				System.out.println("Algum problema ocorreu na hora de imprimir os dados do socket.");
				System.out.println("Comunicação encerrada!");
				e.printStackTrace();
				break;
			}	
			
			if (dadoRecebido != null && dadoRecebido.equalsIgnoreCase("GET-INVENTORY")){
				System.out.println("Solicitação de inventario!");
				
				try{
					PrintStream ps = null;
					ps = new PrintStream(sockClient.getOutputStream());  
					
					File diretorio = new File("C:\\");
					if (diretorio != null){
						for ( int i = 0; i < diretorio.listFiles().length ; i++ ){ 
							if (diretorio.listFiles()[i].getName().endsWith(".xml") ||
									diretorio.listFiles()[i].getName().endsWith(".XML") ||
									diretorio.listFiles()[i].getName().endsWith(".ocs")){
								File f = new File(diretorio.listFiles()[i].getAbsolutePath());
								f.delete();
							}
						}
					}
					
					try {
						Runtime.getRuntime().exec("D:/CentralIT/ocs/OCSInventory.exe /local=c:\\ /xml=c:\\");
					} catch (IOException e) {
						e.printStackTrace();
					} 
					
					boolean x = true;
					while(x){
						diretorio = new File("C:\\");
						if (diretorio != null){
							for ( int i = 0; i < diretorio.listFiles().length ; i++ ){ 
								if (diretorio.listFiles()[i].getName().endsWith(".xml") ||
										diretorio.listFiles()[i].getName().endsWith(".XML")){
									String textoXML = UtilTratamentoArquivos.getStringTextFromFileTxt(diretorio.listFiles()[i].getAbsolutePath());
									textoXML = textoXML.replaceAll("\n", "");
									textoXML = textoXML.replaceAll("\r", "");
									ps.println(textoXML);
									x = false;
									break;
								}
							}
							sleep(5000);
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sleep(5000); //Aguarda um tempo antes de morrer! Isto eh necessario pra garantir que os dados cheguem ao client socket do outro lado!
				} catch (InterruptedException e) {
				}
				break;
			}
		}
	}

}
