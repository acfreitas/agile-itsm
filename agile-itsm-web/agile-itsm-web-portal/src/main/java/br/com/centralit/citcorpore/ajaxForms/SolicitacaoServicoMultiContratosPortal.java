package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class SolicitacaoServicoMultiContratosPortal extends SolicitacaoServicoMultiContratos {

	@SuppressWarnings("unchecked")
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		//Limpa sessão dos anexos
		request.getSession(true).setAttribute("colUploadsGED", null);
		
		//Imprime os dados do solicitante
		informaDadosSolicitacao(document, request, response);
		
		GrupoEmpregadoService grupoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		
		List<GrupoEmpregadoDTO> listGrupos = (ArrayList<GrupoEmpregadoDTO>) grupoService.findByIdEmpregado(usuario.getIdEmpregado());
		/*Set<ContratosGruposDTO> listContratosGrupos 	= new HashSet <ContratosGruposDTO>();
		Set colContratos = new HashSet<ContratoDTO>();*/

		String CONTRATO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "0").trim();		
		Integer idContrato = (CONTRATO.equalsIgnoreCase("") ?  0 : Integer.valueOf(CONTRATO));
		ContratoDTO contrato = new ContratoDTO();
		contrato.setIdContrato(idContrato);
		contrato = (ContratoDTO) contratoService.restore(contrato);
		
		/*Realiza a listagem de grupos empregados*/
		if (listGrupos!=null && contrato!=null) {
			if(contratosGruposService.hasContrato(listGrupos, contrato)) 
				carregaServico(document, request, contrato);
		} else { 
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.executeScript("fechar();");
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.contratoNaoLocalizado"));
		}
	}

    /**
	 * informaDadosSolicitação - Informa dados iniciais da solicitação no momento da abertura
	 * 
	 * @author Flavio.Junior
	 */
	public void informaDadosSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		/*Setando o id do Solicitante diretamente d sessão*/
    	document.getElementById("idSolicitante").setValue(usuario.getIdEmpregado().toString());
		
		EmpregadoDTO eb = this.getEmpregadoService().restoreByIdEmpregado(usuario.getIdEmpregado());
		if (eb != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(eb.getNome() + ";" + eb.getEmail() + ";" + eb.getTelefone() + ";");
			
			document.executeScript("informaDadosSolicitacao(\"" + sb.toString() + "\")");	
		}

	}

	public void salvar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, WebUtil.getUsuarioSistema(request));						
		String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");
		Integer idOrigem = (ORIGEM.trim().equalsIgnoreCase("") ?  0 : Integer.valueOf(ORIGEM.trim()));
		if(idOrigem != 0){
			solicitacaoServicoDto.setIdServico(idOrigem);
		}
		String CONTRATO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "0").trim();		
		Integer idContrato = (CONTRATO.equalsIgnoreCase("") ?  0 : Integer.valueOf(CONTRATO));		
		if(idContrato != 0){
			solicitacaoServicoDto.setIdContrato(idContrato);
		}
		if(solicitacaoServicoDto.getIdServico() != null)
		{
			if (validaServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico())) {
				solicitacaoServicoDto.setIdOrigem(idOrigem);
				
				solicitacaoServicoDto.setUsuarioDto(usuario);
				solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());
				solicitacaoServicoDto.setEnviaEmailCriacao("S");
				EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuario.getIdEmpregado());
				solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
				try {
					if (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
						solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto);
						// document.alert("Registro efetuado com sucesso. Solicitação N.o: " + solicitacaoServicoDto.getIdSolicitacaoServico() + " criada.");
						String comando = "mostraMensagemInsercao('" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>"
								+ UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + " <b><u>" + solicitacaoServicoDto.getIdSolicitacaoServico() + "</u></b> "
								+ UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>" + UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": "
								+ solicitacaoServicoDto.getIdPrioridade();
						if (solicitacaoServicoDto.getPrazoHH() > 0 || solicitacaoServicoDto.getPrazoMM() > 0) {
							comando = comando + " - SLA: " + solicitacaoServicoDto.getSLAStr() + "";
						}
						comando = comando + "')";
						document.executeScript(comando);
						document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
						
						Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
						solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);
						// Rotina para gravar no banco
						if (solicitacaoServicoDto.getColArquivosUpload() != null && solicitacaoServicoDto.getColArquivosUpload().size() > 0) {
							Integer idEmpresa = WebUtil.getIdEmpresa(request);
							if (idEmpresa == null)
								idEmpresa = 1;
							solicitacaoServicoService.gravaInformacoesGED(solicitacaoServicoDto.getColArquivosUpload(), idEmpresa, solicitacaoServicoDto, null);
						
						}
						
						return;
					} else {
						solicitacaoServicoService.updateInfo(solicitacaoServicoDto);
						document.alert(UtilI18N.internacionaliza(request, "MSG06"));
					}

					
				} catch (Exception e) {
					String msgErro = e.getMessage();
					msgErro = msgErro.replaceAll("java.lang.Exception:", "");
					msgErro = msgErro.replaceAll("br.com.citframework.excecao.ServiceException:", "");
					msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
					document.alert(msgErro);
					document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
					return;
				}
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				document.executeScript("fechar();");		
			}else {
				document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaolocalizado"));
			}
		}
		else {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaoconfigurado"));
		}
	}
	
	public void carregaServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "");
		Integer idServico = (ORIGEM.trim().equalsIgnoreCase("") ?  0 : Integer.valueOf(ORIGEM.trim()));
		if(idServico != 0){
			solicitacaoServicoDto.setIdServico(idServico);
		}
		String CONTRATO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "0").trim();		
		Integer idContrato = (CONTRATO.equalsIgnoreCase("") ?  0 : Integer.valueOf(CONTRATO));		
		if(idContrato != 0){
			solicitacaoServicoDto.setIdContrato(idContrato);
		}
		ServicoDTO servicoDto = new ServicoDTO();
		servicoDto.setIdServico(idServico);
		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
		/*Setando o tipo de demanda de serviço*/
		
		if(servicoDto!=null) {
			document.getElementById("idTipoDemandaServico").setValue(servicoDto.getIdTipoDemandaServico().toString());
		}
		else {
			document.getElementById("idTipoDemandaServico").setValue("");
		}

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO temp = null;
		temp = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), idServico);
		
		if(temp != null) {
			/*Setando o id do Solicitante*/
	    	document.getElementById("idServico").setValue(temp.getIdServico().toString());
		}else {
			document.getElementById("idServico").setValue("");
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaoconfigurado"));
		}
				
	}
	
	public void carregaServico(DocumentHTML document, HttpServletRequest request, ContratoDTO contrato) throws Exception {
		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");
		Integer idServico = (ORIGEM.trim().equalsIgnoreCase("") ?  0 : Integer.valueOf(ORIGEM.trim()));
		if(idServico != 0){
			solicitacaoServicoDto.setIdServico(idServico);
		}
		String CONTRATO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "0").trim();		
		Integer idContrato = (CONTRATO.equalsIgnoreCase("") ?  0 : Integer.valueOf(CONTRATO));		
		if(idContrato != 0){
			solicitacaoServicoDto.setIdContrato(idContrato);
		}
		ServicoDTO servicoDto = new ServicoDTO();
		servicoDto.setIdServico(idServico);
		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
		/*Setando o tipo de demanda de serviço*/
		if(servicoDto!=null) {
			document.getElementById("idTipoDemandaServico").setValue(servicoDto.getIdTipoDemandaServico().toString());
			if(validaServico(idContrato, servicoDto.getIdServico())) {
				document.getElementById("idServico").setValue(idServico.toString());
				document.getElementById("idContrato").setValue(idContrato.toString());
			}else {
				document.getElementById("idServico").setValue("");
				document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaolocalizado"));
			}
		}
		else {
			document.getElementById("idTipoDemandaServico").setValue("");
			document.getElementById("idServico").setValue("");
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaoconfigurado"));
		}
		
	}
	
	public boolean validaServico(Integer idContrato, Integer idServico) throws Exception {
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO temp = null;
		temp = servicoContratoService.findByIdContratoAndIdServico(idContrato, idServico);
		if(temp != null) {
			return true;
		}
		return false;
	}
}
