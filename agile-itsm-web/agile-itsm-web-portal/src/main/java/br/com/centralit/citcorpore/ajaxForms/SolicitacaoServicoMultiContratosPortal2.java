package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class SolicitacaoServicoMultiContratosPortal2 extends SolicitacaoServicoMultiContratos {

	@SuppressWarnings("unchecked")
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		GrupoEmpregadoService grupoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		
		List<GrupoEmpregadoDTO> listGrupos = (ArrayList<GrupoEmpregadoDTO>) grupoService.findByIdEmpregado(usuario.getIdEmpregado());

		String contratoTxt = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "0").trim();		
		String origemTxt = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "2");
		
		Integer idContrato = null;
		Integer idOrigem = null;
		ContratoDTO contrato = null;
		OrigemAtendimentoDTO origemAtendimentoDTO = null;
		try {
			idContrato = (contratoTxt.equalsIgnoreCase("") ?  0 : Integer.valueOf(contratoTxt));
			contrato = new ContratoDTO();
			contrato.setIdContrato(idContrato);
			contrato = (ContratoDTO) contratoService.restore(contrato);
			
			idOrigem = (origemTxt.equalsIgnoreCase("") ?  0 : Integer.valueOf(origemTxt));
			origemAtendimentoDTO = new OrigemAtendimentoDTO();
			origemAtendimentoDTO.setIdOrigem(idOrigem);
			origemAtendimentoDTO = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemAtendimentoDTO);
		} catch(Exception e) {
			e.printStackTrace();
		}

		/*Realiza a listagem de grupos empregados*/
		if (listGrupos!=null && contrato!=null) {		
			if(origemAtendimentoDTO != null) {
				String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

				if (COLABORADORES_VINC_CONTRATOS == null) {
					COLABORADORES_VINC_CONTRATOS = "N";
				}
				if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("N")) {
					restaurarInformacoes(document, request, contrato);
				} else {
//					if(contratosGruposService.hasContrato(listGrupos, contrato))  {
						restaurarInformacoes(document, request, contrato);
//					} else {
						//Função específica para exibir somente um popup no IE
//						document.executeScript("cancelarUsuarioContrato()");	
						//document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.usuarioContratoNaoLocalizado"));
//					}
				}
			} else {
				document.executeScript("cancelar()");	
				document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.origemSolicitacaoNaoLocalizada"));	
			}
		} else {			
			document.executeScript("cancelar()");
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.contratoNaoLocalizado"));			
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		if(solicitacaoServicoDto.getIdUnidade() == null) {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.solicitanteunidade"));
			document.executeScript("cancelar()");
			return;
		}
		
		ServicoContratoService servicoContratoServico = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		ServicoContratoDTO servicoContratoDto= null;
		
		//Insere grupo Executor padrão
		if(solicitacaoServicoDto.getIdContrato() != null && solicitacaoServicoDto.getIdServico() != null ){
			servicoContratoDto = servicoContratoServico.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
			
			if(servicoContratoDto!= null && servicoContratoDto.getIdGrupoExecutor() != null){
				solicitacaoServicoDto.setIdGrupoAtual(servicoContratoDto.getIdGrupoExecutor());
			}
		}
		
		if(solicitacaoServicoDto.getIdServico() != null)
		{
			if (validaServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico())) {	
				
				solicitacaoServicoDto.setUsuarioDto(usuario);
				solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());
				solicitacaoServicoDto.setEnviaEmailCriacao("S");
				try {
					if (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
 						solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto);
						// document.alert("Registro efetuado com sucesso. Solicitação N.o: " + solicitacaoServicoDto.getIdSolicitacaoServico() + " criada.");
						String comando = "mostraMensagemInsercao('" +"<H3>"+ UtilI18N.internacionaliza(request, "MSG05") + ".<br>"
								+ UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + " <b><u>" + solicitacaoServicoDto.getIdSolicitacaoServico() + "</u></b> "
								+ UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>" + UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": "
								+ solicitacaoServicoDto.getIdPrioridade();
						if (solicitacaoServicoDto.getPrazoHH() > 0 || solicitacaoServicoDto.getPrazoMM() > 0) {
							comando = comando + " - SLA: " + solicitacaoServicoDto.getSLAStr() + "";
						}
						comando = comando +"</H3>"+ "')";
						document.executeScript(comando);
						document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
						document.executeScript("parent.atualizarLista();");	
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
					document.executeScript("parent.atualizarLista();");
					return;
				}
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				document.executeScript("fechar();");	
				document.executeScript("parent.atualizarLista();");
			}else {
				document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaolocalizado"));
				document.executeScript("cancelar()");
			}
		}
		else {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaoconfigurado"));
			document.executeScript("cancelar()");
		}
	}
	
	public void restaurarInformacoes(DocumentHTML document, HttpServletRequest request, ContratoDTO contrato) throws Exception {		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		HTMLForm form = document.getForm("form");
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		
		EmpregadoDTO empregadoDTO = this.getEmpregadoService().restoreByIdEmpregado(WebUtil.getUsuario(request).getIdEmpregado());
		if(empregadoDTO != null) {
			solicitacaoServicoDto.setIdSolicitante(empregadoDTO.getIdEmpregado());
			solicitacaoServicoDto.setSolicitante(empregadoDTO.getNome());
			solicitacaoServicoDto.setNomecontato(empregadoDTO.getNome());
			solicitacaoServicoDto.setEmailcontato(empregadoDTO.getEmail());
			solicitacaoServicoDto.setTelefonecontato(empregadoDTO.getTelefone());
			solicitacaoServicoDto.setIdUnidade(empregadoDTO.getIdUnidade());
			
			document.executeScript(
					"$('#nomecontatotxt').text(\"" + empregadoDTO.getNome() + "\");"
					+ "	$('#emailcontatotxt').text(\"" + empregadoDTO.getEmail() + "\");"
						+ "	$('#telefonecontatotxt').text(\"" + empregadoDTO.getTelefone() + "\");");
		}
		
		String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "2");
		Integer idOrigem = (ORIGEM.trim().equalsIgnoreCase("") ?  0 : Integer.valueOf(ORIGEM.trim()));
		if(idOrigem != 0){
			OrigemAtendimentoDTO  atendimentoDTO = new OrigemAtendimentoDTO();
			atendimentoDTO.setIdOrigem(idOrigem);
			atendimentoDTO =  (OrigemAtendimentoDTO) origemAtendimentoService.restore(atendimentoDTO);
			if(atendimentoDTO != null) {
				solicitacaoServicoDto.setIdOrigem(atendimentoDTO.getIdOrigem());
				solicitacaoServicoDto.setOrigem(atendimentoDTO.getDescricao());
			}
		}
		
		String SERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "");
		Integer idServico = (SERVICO.trim().equalsIgnoreCase("") ?  0 : Integer.valueOf(SERVICO.trim()));
		if(idServico != 0){
			if(validaServico(contrato.getIdContrato(), idServico)) {
				solicitacaoServicoDto.setIdServico(idServico);	
				solicitacaoServicoDto.setIdContrato(contrato.getIdContrato());
				
				ServicoDTO servicoDto = new ServicoDTO();
				servicoDto.setIdServico(idServico);
				servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
				/*Setando o tipo de demanda de serviço*/
				if(servicoDto!=null) {
					solicitacaoServicoDto.setNomeServico(servicoDto.getNomeServico());
					
					TipoDemandaServicoDTO demandaServicoDTO = new TipoDemandaServicoDTO();
					demandaServicoDTO.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());
					demandaServicoDTO = (TipoDemandaServicoDTO)tipoDemandaServicoService.restore(demandaServicoDTO);
					if(demandaServicoDTO != null) {
						solicitacaoServicoDto.setIdTipoDemandaServico(demandaServicoDTO.getIdTipoDemandaServico());
						solicitacaoServicoDto.setNomeTipoDemandaServico(demandaServicoDTO.getNomeTipoDemandaServico());
					}
				}
				/*
				Rodrigo Pecci Acorse - 08/11/2013 10h05 - #123390
				Linha comentada pois estava tentando abrir o modal 2 vezes e causava problemas no ie.
				*/
				//document.executeScript("abrir();");	
			} else {
				document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaolocalizado"));
				document.executeScript("cancelar()");
				
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicoPadraoNaoconfigurado"));
			document.executeScript("cancelar()");
		}
		
		setaNomeContrato(solicitacaoServicoDto, contrato);
		
		form.setValues(solicitacaoServicoDto);
	}
	
	
	private void setaNomeContrato(SolicitacaoServicoDTO solicitacaoServicoDTO, ContratoDTO contrato) throws Exception{
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		String nomeCliente = "";
		String nomeForn = "";
		ClienteDTO clienteDto = new ClienteDTO();
		clienteDto.setIdCliente(contrato.getIdCliente());
		clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
		if (clienteDto != null) {
			nomeCliente = clienteDto.getNomeRazaoSocial();
		}
		FornecedorDTO fornecedorDto = new FornecedorDTO();
		fornecedorDto.setIdFornecedor(contrato.getIdFornecedor());
		fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
		if (fornecedorDto != null) {
			nomeForn = fornecedorDto.getRazaoSocial();
		}
		if (contrato.getSituacao().equalsIgnoreCase("A")) {
			String nomeContrato = "" + contrato.getNumero() + " de " + UtilDatas.dateToSTR(contrato.getDataContrato()) + " (" + nomeCliente + " - " + nomeForn + ")";
			solicitacaoServicoDTO.setNomeContrato(nomeContrato);
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
