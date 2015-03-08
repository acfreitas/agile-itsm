package br.com.centralit.citcorpore.comm.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
	// Declaro o ServerSocket (ele que trata todos os pedidos de conexao)
	ServerSocket serv = null;
	try {
	    // Cria o ServerSocket na porta 7000 se estiver disponível (veja na
	    // lista de portas em execucao da maquina servidora)
	    serv = new ServerSocket(7000);

	    while (true) {
		// Declaro o Socket de comunicação
		Socket s = null;
		// Aguarda uma conexão na porta especificada e cria retorna o
		// socket
		// que irá comunicar com o cliente
		s = serv.accept();

		// Cria a tread que vai tratar a comunicação e deixa a conversa
		// com ela!
		ThreadTrataComunicacao threadTratarComm = new ThreadTrataComunicacao(s);
		threadTratarComm.start();

		// Apos o start da thread, esta apto para receber novos pedidos
		// de conexao!
	    }
	} catch (IOException e) {
	    // Imprime uma notificação na saída padrão caso haja algo errado.
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