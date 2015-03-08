package br.com.centralit.citcorpore.quartz.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.bi.operation.BICitsmartOperation;
import br.com.centralit.citcorpore.bi.utils.BICitsmartEmailNotificacao;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

/**
 * @author euler.ramos
 * Job que ir� executar a importa��o autom�tica do banco de dados dos clientes Citsmart
 */
public class ImportacaoAutoBiCitsmart implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		BICitsmartEmailNotificacao bICitsmartEmailNotificacao = new BICitsmartEmailNotificacao();
		Map<String, String> map;
		try {
			BICitsmartResultRotinaDTO bICitsmartResultRotinaDTO;
			Integer idProcessamentoBatch = new Integer(UtilStrings.apenasNumeros(jobExecutionContext.getJobDetail().getName()));
			ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
			ProcessamentoBatchDTO processamentoBatchDTO = processamentoBatchService.findById(idProcessamentoBatch);
			ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
			//Tem que estar ativo o Processamento
			if ((processamentoBatchDTO!=null)&&(processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
				if (ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_EXECUTAR_ROTINA_AUTOMATICA, "N").equalsIgnoreCase("S")) {
					ConexaoBIDTO conexaoBIDTO = conexaoBIService.findByIdProcessBatch(processamentoBatchDTO.getIdProcessamentoBatch());
					//Agendamento Espec�fico ou de Exce��o
					if((conexaoBIDTO!=null)&&(conexaoBIDTO.getIdConexaoBI()!=null)&&(conexaoBIDTO.getIdConexaoBI()>0)){
						//Verificando se a conex�o est� ativa: status=Null significa conex�o ativa, "S" significa sucesso, "F" significa falha, "I" significa Inativa!
						if ((conexaoBIDTO.getStatus()==null)||((conexaoBIDTO.getStatus()!=null)&&(!conexaoBIDTO.getStatus().equalsIgnoreCase("I")))){
							//Filtrando somente as Conex�es Autom�ticas!
							if ((conexaoBIDTO.getTipoImportacao()!=null)&&(conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A"))){
								BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
								bICitsmartResultRotinaDTO = biCitsmartOperation.execucaoRotinaAutomatica(conexaoBIDTO, processamentoBatchDTO.getIdProcessamentoBatch());
								//Se for agendamento de exce��o deve-se Inativ�-lo! (Executa-se apenas uma vez!)
								if((conexaoBIDTO.getIdProcessamentoBatchExcecao()!=null)&&(conexaoBIDTO.getIdProcessamentoBatchExcecao().equals(processamentoBatchDTO.getIdProcessamentoBatch()))){
									processamentoBatchDTO.setSituacao("I");
									processamentoBatchService.update(processamentoBatchDTO);
									//Se a rotina de importa��o retornou erro!
									if (!bICitsmartResultRotinaDTO.isResultado()){
										//Notifica��o erro rotina de Exce��o
										bICitsmartEmailNotificacao.setEmailConexao(conexaoBIDTO.getEmailNotificacao());
										bICitsmartEmailNotificacao.setModeloEmail("Exce��o");
										map = new HashMap<String, String>();
										map.put("idConexao", conexaoBIDTO.getIdConexaoBI().toString());
										map.put("nomeConexao", conexaoBIDTO.getNome());
										map.put("linkConexao", conexaoBIDTO.getLink());
										map.put("idProcessamento",processamentoBatchDTO.getIdProcessamentoBatch().toString());
										map.put("descrProcessamento",processamentoBatchDTO.getDescricao().toString());
										bICitsmartEmailNotificacao.setMap(map);
										bICitsmartEmailNotificacao.envia();
									}
								} else {
									//Se a rotina de importa��o retornou erro!
									if (!bICitsmartResultRotinaDTO.isResultado()){
										//Notifica��o erro rotina agendamento espec�fico
										bICitsmartEmailNotificacao.setEmailConexao(conexaoBIDTO.getEmailNotificacao());
										bICitsmartEmailNotificacao.setModeloEmail("Espec�fico");
										map = new HashMap<String, String>();
										map.put("idConexao", conexaoBIDTO.getIdConexaoBI().toString());
										map.put("nomeConexao", conexaoBIDTO.getNome());
										map.put("linkConexao", conexaoBIDTO.getLink());
										map.put("idProcessamento",processamentoBatchDTO.getIdProcessamentoBatch().toString());
										map.put("descrProcessamento",processamentoBatchDTO.getDescricao().toString());
										bICitsmartEmailNotificacao.setMap(map);
										bICitsmartEmailNotificacao.envia();
									}
								}
							}
						}
					} else {
						//Se n�o encontrou agendamento correspondente a conex�o, ent�o, este � um Agendamento Padr�o! O sistema deve executar a atualiza��o de todas as conex�es:
						//Autom�ticas, Ativas e Sem Agendamento Espec�fico ou de Exce��o!
						BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
						ArrayList<ConexaoBIDTO> listaCnxsAgPadrao = (ArrayList<ConexaoBIDTO>) conexaoBIService.listarConexoesAutomaticasSemAgendEspOuExcecao();
						for (ConexaoBIDTO conexaoBI : listaCnxsAgPadrao) {
							bICitsmartResultRotinaDTO = biCitsmartOperation.execucaoRotinaAutomatica(conexaoBI, processamentoBatchDTO.getIdProcessamentoBatch());
							//Se a rotina de importa��o retornou erro!
							if (!bICitsmartResultRotinaDTO.isResultado()){
								//Notifica��o erro rotina agendamento padr�o
								bICitsmartEmailNotificacao.setEmailConexao(conexaoBI.getEmailNotificacao());
								bICitsmartEmailNotificacao.setModeloEmail("Padr�o");
								map = new HashMap<String, String>();
								map.put("idConexao", conexaoBI.getIdConexaoBI().toString());
								map.put("nomeConexao", conexaoBI.getNome());
								map.put("linkConexao", conexaoBI.getLink());
								map.put("idProcessamento",processamentoBatchDTO.getIdProcessamentoBatch().toString());
								map.put("descrProcessamento",processamentoBatchDTO.getDescricao().toString());
								//map.put("loginConexao", conexaoBI.getLogin());
								bICitsmartEmailNotificacao.setMap(map);
								bICitsmartEmailNotificacao.envia();
							}
						}
					}
				} else {
					System.out.println("Importa��o autom�tica n�o executada. Par�metro BICITSMART_EXECUTAR_ROTINA_AUTOMATICA inativado!");
					//Notifica��o erro par�metro n�o ativado
					bICitsmartEmailNotificacao.setEmailConexao(null);
					bICitsmartEmailNotificacao.setModeloEmail("Par�metro");
					map = new HashMap<String, String>();
					map.put("parametro", Enumerados.ParametroSistema.BICITSMART_EXECUTAR_ROTINA_AUTOMATICA.toString());
					bICitsmartEmailNotificacao.setMap(map);
					bICitsmartEmailNotificacao.envia();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problema na importa��o autom�tica BICitsmart!");
			//Notifica��o erro rotina
			bICitsmartEmailNotificacao.setEmailConexao(null);
			bICitsmartEmailNotificacao.setModeloEmail("Problema");
			map = new HashMap<String, String>();
			bICitsmartEmailNotificacao.setMap(map);			
			bICitsmartEmailNotificacao.envia();
		}
	}

}