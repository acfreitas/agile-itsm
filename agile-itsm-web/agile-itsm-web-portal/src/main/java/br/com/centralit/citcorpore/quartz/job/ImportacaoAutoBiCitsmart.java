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
 * Job que irá executar a importação automática do banco de dados dos clientes Citsmart
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
					//Agendamento Específico ou de Exceção
					if((conexaoBIDTO!=null)&&(conexaoBIDTO.getIdConexaoBI()!=null)&&(conexaoBIDTO.getIdConexaoBI()>0)){
						//Verificando se a conexão está ativa: status=Null significa conexão ativa, "S" significa sucesso, "F" significa falha, "I" significa Inativa!
						if ((conexaoBIDTO.getStatus()==null)||((conexaoBIDTO.getStatus()!=null)&&(!conexaoBIDTO.getStatus().equalsIgnoreCase("I")))){
							//Filtrando somente as Conexões Automáticas!
							if ((conexaoBIDTO.getTipoImportacao()!=null)&&(conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A"))){
								BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
								bICitsmartResultRotinaDTO = biCitsmartOperation.execucaoRotinaAutomatica(conexaoBIDTO, processamentoBatchDTO.getIdProcessamentoBatch());
								//Se for agendamento de exceção deve-se Inativá-lo! (Executa-se apenas uma vez!)
								if((conexaoBIDTO.getIdProcessamentoBatchExcecao()!=null)&&(conexaoBIDTO.getIdProcessamentoBatchExcecao().equals(processamentoBatchDTO.getIdProcessamentoBatch()))){
									processamentoBatchDTO.setSituacao("I");
									processamentoBatchService.update(processamentoBatchDTO);
									//Se a rotina de importação retornou erro!
									if (!bICitsmartResultRotinaDTO.isResultado()){
										//Notificação erro rotina de Exceção
										bICitsmartEmailNotificacao.setEmailConexao(conexaoBIDTO.getEmailNotificacao());
										bICitsmartEmailNotificacao.setModeloEmail("Exceção");
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
									//Se a rotina de importação retornou erro!
									if (!bICitsmartResultRotinaDTO.isResultado()){
										//Notificação erro rotina agendamento específico
										bICitsmartEmailNotificacao.setEmailConexao(conexaoBIDTO.getEmailNotificacao());
										bICitsmartEmailNotificacao.setModeloEmail("Específico");
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
						//Se não encontrou agendamento correspondente a conexão, então, este é um Agendamento Padrão! O sistema deve executar a atualização de todas as conexões:
						//Automáticas, Ativas e Sem Agendamento Específico ou de Exceção!
						BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
						ArrayList<ConexaoBIDTO> listaCnxsAgPadrao = (ArrayList<ConexaoBIDTO>) conexaoBIService.listarConexoesAutomaticasSemAgendEspOuExcecao();
						for (ConexaoBIDTO conexaoBI : listaCnxsAgPadrao) {
							bICitsmartResultRotinaDTO = biCitsmartOperation.execucaoRotinaAutomatica(conexaoBI, processamentoBatchDTO.getIdProcessamentoBatch());
							//Se a rotina de importação retornou erro!
							if (!bICitsmartResultRotinaDTO.isResultado()){
								//Notificação erro rotina agendamento padrão
								bICitsmartEmailNotificacao.setEmailConexao(conexaoBI.getEmailNotificacao());
								bICitsmartEmailNotificacao.setModeloEmail("Padrão");
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
					System.out.println("Importação automática não executada. Parâmetro BICITSMART_EXECUTAR_ROTINA_AUTOMATICA inativado!");
					//Notificação erro parâmetro não ativado
					bICitsmartEmailNotificacao.setEmailConexao(null);
					bICitsmartEmailNotificacao.setModeloEmail("Parâmetro");
					map = new HashMap<String, String>();
					map.put("parametro", Enumerados.ParametroSistema.BICITSMART_EXECUTAR_ROTINA_AUTOMATICA.toString());
					bICitsmartEmailNotificacao.setMap(map);
					bICitsmartEmailNotificacao.envia();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problema na importação automática BICitsmart!");
			//Notificação erro rotina
			bICitsmartEmailNotificacao.setEmailConexao(null);
			bICitsmartEmailNotificacao.setModeloEmail("Problema");
			map = new HashMap<String, String>();
			bICitsmartEmailNotificacao.setMap(map);			
			bICitsmartEmailNotificacao.envia();
		}
	}

}