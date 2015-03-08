package br.com.citframework.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.TilesRequestProcessor;

public class CustomRequestProcessor extends TilesRequestProcessor {

	/*private static final Logger LOGGER = Logger.getLogger(CustomRequestProcessor.class);
	private static boolean cargaEmpregadoIniciada = false;
	
	private class CargaEmpregadoTask implements Runnable {
		
		DataLoadService service;
		
		public CargaEmpregadoTask(DataLoadService service) {
			this.service = service;
		}
		
		public void run() {
			try {
				service.loadEmpregados("resource/empregados.txt");
			} catch (Exception e) {
				LOGGER.error("Erro na carga de empregados.", e);
			}
		}
	}*/
	
	protected boolean processPreprocess(HttpServletRequest arg0, HttpServletResponse arg1) {
		/*if (!cargaEmpregadoIniciada) {
			cargaEmpregadoIniciada = true;
			try {
				DataLoadService service = ServiceLocator.getInstance().getDataLoadService();
				Thread thread = new Thread(new CargaEmpregadoTask(service));
				thread.start();
			} catch (Exception e) {
				LOGGER.error("Erro na execucao da thread da carga de empregados.", e);
			}
		}*/
		return super.processPreprocess(arg0, arg1);
	}
}
