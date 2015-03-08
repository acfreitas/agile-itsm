package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class AgendamentoExecucaoBI extends AjaxFormAction {

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchBean = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);

		if (processamentoBatchBean.getIdProcessamentoBatch() == null || processamentoBatchBean.getIdProcessamentoBatch().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.necessarioSelecionarRegistro"));
			return;
		} else {
			processamentoBatchBean.setSituacao("I");
			processamentoBatchService.update(processamentoBatchBean);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("document.location.reload()");
	}

	public Class getBeanClass() {
		return ProcessamentoBatchDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessamentoBatchDTO agendamentoProcessBatchDto = (ProcessamentoBatchDTO) document.getBean();
		/**
		 * setando atributos de agendamentoExecucaoBI.jsp, por que no load é enxergado o idConexaoBI e abriuAgendamentoExcecao do formGerenciamento, mas quando entrar no save o getBean enxerga o
		 * agendamentoExecucaoBI.jsp e com isso é necessario setar conforme esta abaixo para que os valores que estavam no formGerenciamento sejam setados no agendamentoExecucaoBI.
		 */
		document.getElementById("idConexaoBI").setValue(agendamentoProcessBatchDto.getIdConexaoBI().toString());
		document.getElementById("abriuAgendamentoExcecao").setValue(agendamentoProcessBatchDto.getAbriuAgendamentoExcecao().toString());

		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		processamentoBatchService.populaSelects(document, request, response);

		ConexaoBIDTO conexaoBIDTO = new ConexaoBIDTO();
		ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
		conexaoBIDTO.setIdConexaoBI(agendamentoProcessBatchDto.getIdConexaoBI());
		conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.restore(conexaoBIDTO);
		/**
		 * condição para verificar se o agendamento chamado é Padrão ou de Exceção
		 */
		if (agendamentoProcessBatchDto.getAbriuAgendamentoExcecao().equals("false")){
			if (conexaoBIDTO.getIdProcessamentoBatchEspecifico() != null){
				agendamentoProcessBatchDto.setIdProcessamentoBatch(conexaoBIDTO.getIdProcessamentoBatchEspecifico());
				restore(document, request, response);
			}
		} else {
			if (conexaoBIDTO.getIdProcessamentoBatchExcecao() != null){
				agendamentoProcessBatchDto.setIdProcessamentoBatch(conexaoBIDTO.getIdProcessamentoBatchExcecao());
				restore(document, request, response);
			}
		}
		if (agendamentoProcessBatchDto.getAbriuAgendamentoExcecao()!= null && agendamentoProcessBatchDto.getAbriuAgendamentoExcecao().equals("true")){
			document.executeScript("parent.setTituloModalAgendamento('excecao')");
		}else {
			document.executeScript("parent.setTituloModalAgendamento('especifico')");
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		Integer idConexaoBI = 0;
		ProcessamentoBatchDTO processamentoBatchDTO = (ProcessamentoBatchDTO) document.getBean();
		idConexaoBI = processamentoBatchDTO.getIdConexaoBI();

		String abriuAgendamentoExcecao = processamentoBatchDTO.getAbriuAgendamentoExcecao();

		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.restore(processamentoBatchDTO);
		if (processamentoBatchDTO!=null){
			processamentoBatchDTO.setIdConexaoBI(idConexaoBI);
			processamentoBatchDTO.setAbriuAgendamentoExcecao(abriuAgendamentoExcecao);
			processamentoBatchService.setaPropriedadesExpressaoCron(processamentoBatchDTO);
			HTMLForm form = document.getForm("formAgendamento");
			form.clear();
			form.setValues(processamentoBatchDTO);
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchDTO = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		ConexaoBIDTO conexaoBIDTO = new ConexaoBIDTO();
		ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, null);

		conexaoBIDTO.setIdConexaoBI(processamentoBatchDTO.getIdConexaoBI());
		conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.restore(conexaoBIDTO);

		//processamentoBatchDTO.setAbriuAgendamentoExcecao(document.getElementById("abriuAgendamentoExcecao").getValue());

		processamentoBatchDTO.setTipo("C");
		processamentoBatchDTO.setConteudo("br.com.centralit.citcorpore.quartz.job.ImportacaoAutoBiCitsmart");
		if (!processamentoBatchDTO.getAbriuAgendamentoExcecao().equalsIgnoreCase("") && processamentoBatchDTO.getAbriuAgendamentoExcecao().equalsIgnoreCase("false")){
			processamentoBatchDTO.setDescricao("Importação automática BI Citsmart - Conexão: "+conexaoBIDTO.getNome()+ " - Específico");
		} else if (!processamentoBatchDTO.getAbriuAgendamentoExcecao().equalsIgnoreCase("") && processamentoBatchDTO.getAbriuAgendamentoExcecao().equalsIgnoreCase("true")){
			processamentoBatchDTO.setDescricao("Importação automática BI Citsmart - Conexão: "+conexaoBIDTO.getNome()+ " - Exceção");
		}

		processamentoBatchService.montaExpressaoCron(processamentoBatchDTO);
		if (processamentoBatchService.validaExpressaoCron(document,request,processamentoBatchDTO)){
			/**
			 * condição para verificar se está sendo tratado o agendamentoEspecifico ou o agendamentoPadrao
			 */
			if (processamentoBatchDTO.getAbriuAgendamentoExcecao() != null && processamentoBatchDTO.getAbriuAgendamentoExcecao().equals("false")){
					//setando o IdProcessamentoBatch caso já exista para a conexão tratada
					if (conexaoBIDTO.getIdProcessamentoBatchEspecifico() != null){
						processamentoBatchDTO.setIdProcessamentoBatch(conexaoBIDTO.getIdProcessamentoBatchEspecifico());
					}
					// se o idProcessamento existir apenas atualiza as informações caso contrario entra no else e chama o create
					if (processamentoBatchDTO.getIdProcessamentoBatch() != null && processamentoBatchDTO.getIdProcessamentoBatch().intValue() > 0) {
						processamentoBatchService.update(processamentoBatchDTO);
						if ((processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
							processamentoBatchService.agendaJob(processamentoBatchDTO, document, request);
						}
						document.alert(UtilI18N.internacionaliza(request, "MSG06"));
						document.executeScript("parent.fecharModalAgendamento();");
					} else {
							processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.create(processamentoBatchDTO);
							if ((processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
								processamentoBatchService.agendaJob(processamentoBatchDTO, document, request);
							}
							conexaoBIDTO.setIdProcessamentoBatchEspecifico(processamentoBatchDTO.getIdProcessamentoBatch());
							conexaoBIService.update(conexaoBIDTO);
							document.alert(UtilI18N.internacionaliza(request, "MSG05"));
							document.executeScript("parent.fecharModalAgendamento();");
					}
			} else {
					//Validação se o agendamento de excecao a ser gravado é superior a data/hora atual e se é uma hora inferior a data/hora da proxima execucao Padrao/especifica
						BICitsmartResultRotinaDTO responseValidacaoAgendExcecao = conexaoBIService.validaAgendamentoExcecao(conexaoBIDTO, processamentoBatchDTO);
						if (responseValidacaoAgendExcecao.isResultado()){
							if (conexaoBIDTO.getIdProcessamentoBatchExcecao() != null){
								processamentoBatchDTO.setIdProcessamentoBatch(conexaoBIDTO.getIdProcessamentoBatchExcecao());
							}
							if (processamentoBatchDTO.getIdProcessamentoBatch() != null && processamentoBatchDTO.getIdProcessamentoBatch().intValue() > 0){
								processamentoBatchService.update(processamentoBatchDTO);
								if ((processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
									processamentoBatchService.agendaJob(processamentoBatchDTO, document, request);
								}
								document.alert(UtilI18N.internacionaliza(request, "MSG06"));
								document.executeScript("parent.fecharModalAgendamento();");
							}else {
								processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.create(processamentoBatchDTO);
								if ((processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
									processamentoBatchService.agendaJob(processamentoBatchDTO, document, request);
								}
								conexaoBIDTO.setIdProcessamentoBatchExcecao(processamentoBatchDTO.getIdProcessamentoBatch());
								conexaoBIService.update(conexaoBIDTO);
								document.alert(UtilI18N.internacionaliza(request, "MSG05"));
								document.executeScript("parent.fecharModalAgendamento();");
							}
						} else {
							document.alert(responseValidacaoAgendExcecao.getMensagem());
						}
			}
			HTMLForm form = document.getForm("formAgendamento");
			form.clear();
		}
	}

}
