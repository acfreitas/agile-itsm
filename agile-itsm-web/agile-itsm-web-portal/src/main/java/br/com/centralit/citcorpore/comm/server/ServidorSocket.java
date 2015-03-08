package br.com.centralit.citcorpore.comm.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;

import br.com.centralit.citcorpore.ajaxForms.Inventario;
import br.com.centralit.citcorpore.negocio.InventarioXMLService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class ServidorSocket extends Thread implements ClientServer {
	
	@Override
	public void run() {
		ServerSocket server = null;

		try {
			server = new ServerSocket(CLIENT_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			while (true) {
				Socket socket = null;
				socket = server.accept();
				
				String ipCliente = socket.getRemoteSocketAddress().toString();
				System.out.println(socket.getRemoteSocketAddress().toString());
				System.out.println(socket.getLocalAddress());
				System.out.println(socket.getLocalSocketAddress());
				String ipParaBusca = "";
				
				
				/* Trata o IP. */
				ipCliente = ipCliente.replaceAll("/", "");
				String[] array = ipCliente.split(":");
				ipCliente = array[0];
				ipParaBusca = ipCliente.replace(".", "-");
				
				InventarioXMLService inventarioXMLService = (InventarioXMLService) ServiceLocator.getInstance().getService(InventarioXMLService.class, null);
				/*ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
				ParametroCorporeDTO paramentroDTO = null;
				
				List<ParametroCorporeDTO> listDiasInventario = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.DiasInventario.id(), Enumerados.ParametroSistema.DiasInventario.campo());
		
				String diasInventario = "";
				
				if ((paramentroDTO = (ParametroCorporeDTO) listDiasInventario.get(0)).getValor() != null) {
					diasInventario = (paramentroDTO = (ParametroCorporeDTO) listDiasInventario.get(0)).getValor();
				}*/
				
				//Date dataInventario = UtilDatas.getSqlDate(UtilDatas.incrementaDiasEmData(Util.getDataAtual(), -(new Integer(diasInventario.trim()))));
				Date dataInventario = UtilDatas.getSqlDate(new java.util.Date());
						
				boolean inventarioAtualizado = inventarioXMLService.inventarioAtualizado(ipParaBusca,dataInventario);
				
				if(!inventarioAtualizado){
					Inventario inventario = new Inventario();
					inventario.InventarioAutomatico(ipCliente);
				}
			}
		} catch (ServiceException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}