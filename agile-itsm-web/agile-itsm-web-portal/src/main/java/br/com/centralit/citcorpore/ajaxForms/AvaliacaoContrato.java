package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AvaliacaoContratoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.OpiniaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.OpiniaoService;
import br.com.centralit.citcorpore.negocio.PesquisaSatisfacaoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorServicoContratoPeriodo;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class AvaliacaoContrato extends AjaxFormAction {
	private Boolean acao = false;
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);		
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		Collection colContratosColab = null;
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}
		Collection colContratos = contratoService.list();
		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
		if (colContratos != null) {
			if (colContratos.size() > 1) {
				((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			} else {
				acao = true;
			}			
			for (Iterator it = colContratos.iterator(); it.hasNext();) {
				ContratoDTO contratoDto = (ContratoDTO) it.next();
				if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
					if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se parametro de colaboradores por contrato ativo, entao filtra.
						if (colContratosColab == null) {
							continue;
						}
						if (!isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
							continue;
						}
					}
					ContratoDTO contratoDtoAux = new ContratoDTO();
					String nomeCliente = "";
					String nomeForn = "";
					ClienteDTO clienteDto = new ClienteDTO();
					clienteDto.setIdCliente(contratoDto.getIdCliente());
					clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
					if (clienteDto != null) {
						nomeCliente = clienteDto.getNomeRazaoSocial();
					}
					FornecedorDTO fornecedorDto = new FornecedorDTO();
					fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
					fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
					if (fornecedorDto != null) {
						nomeForn = fornecedorDto.getRazaoSocial();
					}
					contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
					if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ")";
						((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), nomeContrato);
						contratoDto.setNome(nomeContrato);
						listaContratos.add(contratoDto);
					}
				}
			}
		}
		AvaliacaoContratoDTO avaliacaoContratoDTO = (AvaliacaoContratoDTO)document.getBean();
		if (avaliacaoContratoDTO.getIdContrato() != null){
			pesquisar(document, request, response);
			document.getForm("form").setValues(avaliacaoContratoDTO);
		}
	}
	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void pesquisar(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		String strTable = "";
		AvaliacaoContratoDTO avaliacaoContratoDTO = (AvaliacaoContratoDTO)document.getBean();
		OpiniaoService opiniaoService = (OpiniaoService) ServiceLocator.getInstance().getService(OpiniaoService.class, null);
		Collection col = opiniaoService.findByTipoAndPeriodo(OpiniaoDTO.QUEIXA, avaliacaoContratoDTO.getIdContrato(), avaliacaoContratoDTO.getDataInicio(), avaliacaoContratoDTO.getDataFim());
		if (col != null && col.size() > 0){
			strTable += "<b><u>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.queixasregistradas") + "</u>:</b>";
			strTable += "<table>";
			for (Iterator it = col.iterator(); it.hasNext();){
				OpiniaoDTO opiniaoDTO = (OpiniaoDTO)it.next();
				strTable += "<tr>";
					strTable += "<td>";
						strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, opiniaoDTO.getData(), WebUtil.getLanguage(request));
					strTable += "</td>";
					strTable += "<td>";
						strTable += "&nbsp;";
					strTable += "</td>";
					strTable += "<td>";
						strTable += opiniaoDTO.getObservacoes();
					strTable += "</td>";
				strTable += "</tr>";
			}
			strTable += "</table>";
		}else{
			strTable += "<b>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.naohaqueixas") + "</b><br>";
		}
		col = opiniaoService.findByTipoAndPeriodo(OpiniaoDTO.ELOGIO, avaliacaoContratoDTO.getIdContrato(), avaliacaoContratoDTO.getDataInicio(), avaliacaoContratoDTO.getDataFim());
		if (col != null && col.size() > 0){
			strTable += "<b><u>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.elogiosregistrados") + "</u>:</b>";
			strTable += "<table>";
			for (Iterator it = col.iterator(); it.hasNext();){
				OpiniaoDTO opiniaoDTO = (OpiniaoDTO)it.next();
				strTable += "<tr>";
					strTable += "<td>";
						strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, opiniaoDTO.getData(), WebUtil.getLanguage(request));
					strTable += "</td>";
					strTable += "<td>";
						strTable += "&nbsp;";
					strTable += "</td>";					
					strTable += "<td>";
						strTable += opiniaoDTO.getObservacoes();
					strTable += "</td>";
				strTable += "</tr>";
			}
			strTable += "</table>";
		}else{
			strTable += "<b>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.naohaelogios") + "</b><br>";
		}	
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(avaliacaoContratoDTO.getDataFim());
		/*calendar.add(GregorianCalendar.DATE, 1);*/

		java.sql.Date datafim = new java.sql.Date(calendar.getTime().getTime());		
		PesquisaSatisfacaoService pesquisaSatisfacaoService = (PesquisaSatisfacaoService) ServiceLocator.getInstance().getService(PesquisaSatisfacaoService.class, null);
		PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO = new PesquisaSatisfacaoDTO();
		pesquisaSatisfacaoDTO.setIdContrato(avaliacaoContratoDTO.getIdContrato());
		pesquisaSatisfacaoDTO.setNota(Enumerados.Nota.REGULAR.getNota());
		pesquisaSatisfacaoDTO.setDataInicio(avaliacaoContratoDTO.getDataInicio());
		pesquisaSatisfacaoDTO.setDataFim(datafim);
		Collection colPesqRegular = pesquisaSatisfacaoService.relatorioPesquisaSatisfacao(pesquisaSatisfacaoDTO);
		if (colPesqRegular != null && colPesqRegular.size() > 0){
			strTable += "<b><u>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.pesqregularregistradas") + "</u>:</b>";
			strTable += "<table>";
			for (Iterator it = colPesqRegular.iterator(); it.hasNext();){
				PesquisaSatisfacaoDTO pesquisaSatisfacaoAux = (PesquisaSatisfacaoDTO)it.next();
				strTable += "<tr>";
					strTable += "<td>";
						strTable += pesquisaSatisfacaoAux.getIdSolicitacaoServico();
					strTable += "</td>";	
					strTable += "<td>";
						strTable += "&nbsp;";
					strTable += "</td>";				
					strTable += "<td>";
						strTable += pesquisaSatisfacaoAux.getComentario();
					strTable += "</td>";
				strTable += "</tr>";
			}
			strTable += "</table>";			
		}else{
			strTable += "<b>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.naohapesqsatisfacaoregular") + "</b><br>";
		}
		
		pesquisaSatisfacaoDTO.setNota(Enumerados.Nota.RUIM.getNota());
		Collection colPesqRuim = pesquisaSatisfacaoService.relatorioPesquisaSatisfacao(pesquisaSatisfacaoDTO);
		if (colPesqRuim != null && colPesqRuim.size() > 0){		
			strTable += "<b><u>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.pesqruimregistradas") + "</u>:</b>";
			strTable += "<table>";
			for (Iterator it = colPesqRuim.iterator(); it.hasNext();){
				PesquisaSatisfacaoDTO pesquisaSatisfacaoAux = (PesquisaSatisfacaoDTO)it.next();
				strTable += "<tr>";
					strTable += "<td>";
						strTable += pesquisaSatisfacaoAux.getIdSolicitacaoServico();
					strTable += "</td>";	
					strTable += "<td>";
						strTable += "&nbsp;";
					strTable += "</td>";					
					strTable += "<td>";
						strTable += pesquisaSatisfacaoAux.getComentario();
					strTable += "</td>";
				strTable += "</tr>";
			}
			strTable += "</table>";					
		}else{
			strTable += "<b>" + UtilI18N.internacionaliza(request, "avaliacaocontrato.naohapesqsatisfacaoruim") + "</b><br>";
		}	
		
		request.setAttribute("info", strTable);
		
		ServicoContratoService serviceContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
		Collection colServicosContrato = serviceContratoService.findByIdContrato(avaliacaoContratoDTO.getIdContrato());
		List colFinal = new ArrayList();
		
		ControleGenerateSLAPorServicoContratoPeriodo controleGenerateSLAPorServicoContratoPeriodo = new ControleGenerateSLAPorServicoContratoPeriodo();
		if (colServicosContrato != null){
			for(Iterator it = colServicosContrato.iterator(); it.hasNext();){
				ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO)it.next();
				if (servicoContratoAux.getDeleted() != null && !servicoContratoAux.getDeleted().equalsIgnoreCase("N")){
				    continue;
				}
				if (servicoContratoAux.getIdServico() != null){
					ServicoDTO servicoDto = new ServicoDTO();
					servicoDto.setIdServico(servicoContratoAux.getIdServico());
					servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
					if (servicoDto != null){
						if (servicoDto.getDeleted() != null && !servicoDto.getDeleted().equalsIgnoreCase("N")){
						    continue;
						}		
						servicoContratoAux.setTemSLA(false);
						servicoContratoAux.setNomeServico(servicoDto.getNomeServico());
						servicoContratoAux.setServicoDto(servicoDto);
						servicoContratoAux.setSituacaoServico(servicoDto.getIdSituacaoServico());
						if (servicoDto.getIdTipoDemandaServico() != null){
						    TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
						    tipoDemandaServicoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());
						    tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
						    if (tipoDemandaServicoDto != null){
						    	servicoContratoAux.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
						    }
						}
						col = acordoNivelServicoService.findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());
						Collection colVincs = acordoServicoContratoService.findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());		
						if ((col != null && col.size() > 0) || (colVincs != null && colVincs.size() > 0)){
							servicoContratoAux.setTemSLA(true);	
						}
						List lst = controleGenerateSLAPorServicoContratoPeriodo.execute(servicoContratoAux.getIdServicoContrato(), avaliacaoContratoDTO.getDataInicio(), avaliacaoContratoDTO.getDataFim());
						double qtdeDentroPrazo = 0;
						double qtdeForaPrazo = 0;
						if (lst != null && lst.size() > 0){
							for (Iterator itSLA = lst.iterator(); itSLA.hasNext();){
								Object[] objs = (Object[]) itSLA.next();
								if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
									qtdeForaPrazo = (Double)objs[2];
								}else{
									qtdeDentroPrazo = (Double)objs[2];
								}
							}
						}
						double qtdeDentroPrazoPerc = qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo);
						double qtdeForaPrazoPerc = qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo);
						servicoContratoAux.setDentroPrazo((qtdeDentroPrazoPerc * 100));
						servicoContratoAux.setForaPrazo((qtdeForaPrazoPerc * 100));
						
						servicoContratoAux.setQtdeDentroPrazo((int)qtdeDentroPrazo);
						servicoContratoAux.setQtdeForaPrazo((int)qtdeForaPrazo);
						
						Integer qtde = solicitacaoServicoService.getQuantidadeByIdServicoContrato(servicoContratoAux.getIdServicoContrato());
						servicoContratoAux.setQuantidade(qtde);
						
						colFinal.add(servicoContratoAux);
					}
				}
			}
		}
		Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		request.setAttribute("listaServicos", colFinal);
		
	}
	
	@Override
	public Class getBeanClass() {
		return AvaliacaoContratoDTO.class;
	}

}
