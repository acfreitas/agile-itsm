package br.com.centralit.citcorpore.comm.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
	// Declaro o ServerSocket (ele que trata todos os pedidos de conexao)
	ServerSocket serv = null;
	try {
	    // Cria o ServerSocket na porta 7000 se estiver dispon�vel (veja na
	    // lista de portas em execucao da maquina servidora)
	    serv = new ServerSocket(7000);

	    while (true) {
		// Declaro o Socket de comunica��o
		Socket s = null;
		// Aguarda uma conex�o na porta especificada e cria retorna o
		// socket
		// que ir� comunicar com o cliente
		s = serv.accept();

		// Cria a tread que vai tratar a comunica��o e deixa a conversa
		// com ela!
		ThreadTrataComunicacao threadTratarComm = new ThreadTrataComunicacao(s);
		threadTratarComm.start();

		// Apos o start da thread, esta apto para receber novos pedidos
		// de conexao!
	    }
	} catch (IOException e) {
	    // Imprime uma notifica��o na sa�da padr�o caso haja algo errado.
	    System.out.println("Algum problema ocorreu para criar ou receber o socket.");

	} finally {
	    try {
		// Encerra o ServerSocket
	    	if(serv != null){
	    		serv.close();
	    	}
	    } catch (IOException e) {
	    }
	}
    }
}