package br.com.centralit.citcorpore.batch;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citcorpore.bean.ControleImportarDadosDTO;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.ControleImportarDadosDao;
import br.com.centralit.citcorpore.integracao.ImportarDadosDao;
import br.com.centralit.citcorpore.negocio.ControleImportarDadosService;
import br.com.centralit.citcorpore.negocio.ExternalConnectionService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"unchecked", "rawtypes"})
public class MonitoraImportacaoDeDados implements Runnable {

	private final ControleImportarDadosDao controleDao = new ControleImportarDadosDao();
	private final ImportarDadosDao importarDadosDao = new ImportarDadosDao();

	@Override
	public void run() {

		while (true) {
			Collection<ImportarDadosDTO> listaDeImportacoes;
			Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();
			Date dataAtual = UtilDatas.getDataAtual();
			Date dataHoraExecutar;

			try {

				//Consulta todas as rotinas ativas e com execução agendada
				listaDeImportacoes = importarDadosDao.listAtivosEComRotinaAgendada();

				if(listaDeImportacoes == null || listaDeImportacoes.isEmpty())
					return;

				ControleImportarDadosDTO controle;

				for (ImportarDadosDTO importarDadosDTO : listaDeImportacoes) {

					//Consulta a ultima execução da rotina
					controle = controleDao.consultarControleImportarDados(importarDadosDTO);

					//Se não houve nenhuma execução da rotina ela é executada
					if(controle == null){
						executarScript(importarDadosDTO);
						continue;
					}


					//Por periodo
					if(importarDadosDTO.getExecutarPor().equalsIgnoreCase("P")){

						dataHoraExecutar = UtilDatas.alteraData(controle.getDataExecucao(), importarDadosDTO.getPeriodoHora(), Calendar.HOUR_OF_DAY);

						if(dataHoraAtual.compareTo(dataHoraExecutar) > 0)
							executarScript(importarDadosDTO);

					} else {

						//Por hora marcada

						dataAtual = UtilDatas.getDataAtual();
						dataHoraExecutar = dataAtual;
						dataHoraExecutar = UtilDatas.alteraData(dataAtual, Integer.parseInt(importarDadosDTO.getHoraExecucao().substring(0, 2)), Calendar.HOUR_OF_DAY);
						dataHoraExecutar = UtilDatas.alteraData(dataHoraExecutar, Integer.parseInt(importarDadosDTO.getHoraExecucao().substring(3, 5)), Calendar.MINUTE);

						dataAtual = new Date(dataHoraAtual.getTime());


						//Validar se já não foi executado antes
						if(controle.getDataExecucao().compareTo(dataHoraExecutar) < 0)
							//Validar se a hora marcada para execução é maior que a data atual
							if(dataHoraExecutar.compareTo(dataAtual) < 0)
								executarScript(importarDadosDTO);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {

			}

		}
	}



	/**
	 * Executa os scripts ou os jars vinculados ao parametro
	 * @param importarDadosDTO
	 */
	@SuppressWarnings("deprecation")
	public void executarScript(ImportarDadosDTO importarDadosDTO){

		salvarDadosDaExecucao(importarDadosDTO);

		try {

			if(importarDadosDTO.getImportarPor().equalsIgnoreCase("S")){

				//Recuperar campos da conexão (ExternalConnectionDTO)
				ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
				ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
				externalConnectionDTO.setIdExternalConnection(importarDadosDTO.getIdExternalConnection());
				externalConnectionDTO = (ExternalConnectionDTO) externalConnectionService.restore(externalConnectionDTO);

				Context cx = Context.enter();
				Scriptable scope = cx.initStandardObjects();

				String conteudoScript = importarDadosDTO.getScript();

				JdbcEngine jdbcEngine = new JdbcEngine("jdbc/citsmart", null);

				scope.put("jdbcEngine", scope, jdbcEngine);

				if(externalConnectionDTO == null || externalConnectionDTO.getIdExternalConnection() == null)
					System.out.println("Sem dados da conexão externa.");
				else {

					scope.put("url", scope, externalConnectionDTO.getUrlJdbc());
					scope.put("dbName", scope, externalConnectionDTO.getJdbcDbName());
					scope.put("driver", scope, externalConnectionDTO.getJdbcDriver());
					scope.put("password", scope, externalConnectionDTO.getJdbcPassword());
					scope.put("user", scope, externalConnectionDTO.getJdbcUser());
					scope.put("schema", scope, externalConnectionDTO.getSchemaDb());

				}

				scope.put("dataAtualFormatada", scope, UtilDatas.getDataAtual().toLocaleString().replace("/", "-").replace(" 00:00:00", ""));
				scope.put("dataAtual", scope, UtilDatas.getDataAtual());

		        cx.evaluateString(scope, conteudoScript, "JavaScript", 0, null);

		        System.out.println("Script de importação executado com sucesso: ID:" + importarDadosDTO.getIdImportarDados() + " as " + UtilDatas.getDataHoraAtual());

			} else if(importarDadosDTO.getImportarPor().equalsIgnoreCase("E")){

				ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
				Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_IMPORTARDADOS, importarDadosDTO.getIdImportarDados());
	            Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

	            ProcessBuilder pb;
	            UploadDTO upload;
	            String caminho = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

	            for (Iterator<UploadDTO> it = colAnexosUploadDTO.iterator(); it.hasNext();){

	            	upload = (UploadDTO)it.next();

	            	pb = new ProcessBuilder("java", "-jar", upload.getNameFile());

					pb.directory(new File(caminho));

				}

			}

		} catch (NullPointerException e) {
			System.out.println("Script de importação executado com erro: Objeto nulo. As " + UtilDatas.getDataHoraAtual());
		} catch (Exception e) {
			System.out.println("Script de importação executado com erro: ID:" + importarDadosDTO.getIdImportarDados() + " as " + UtilDatas.getDataHoraAtual());
		}

	}


	/**
	 * Salva a execução da rotina
	 *
	 * @param importar
	 */
	public void salvarDadosDaExecucao(ImportarDadosDTO importar) {

		ControleImportarDadosDTO controle = new ControleImportarDadosDTO();

		controle.setDataExecucao(UtilDatas.getDataHoraAtual());
		controle.setIdImportarDados(importar.getIdImportarDados());

  		ControleImportarDadosService controleService;

		try {

			controleService = (ControleImportarDadosService) ServiceLocator.getInstance().getService(ControleImportarDadosService.class, null);
			controle = (ControleImportarDadosDTO) controleService.create(controle);

		} catch (Exception e) {
		}

	}

}