package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AprovacaoRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaLiberacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.negocio.AprovacaoRequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaLiberacaoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

public class OcorrenciaLiberacao extends AjaxFormAction  {
	
	private RequisicaoLiberacaoDTO requisicaoLiberacaoDTO;
	
	private OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDTO;
	
	
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	ocorrenciaLiberacaoDTO = (OcorrenciaLiberacaoDTO) document.getBean();
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	geraComboCategoria(document, request);
    	geraComboOrigem(document, request);
    	document.getElementById("registradopor").setValue(usuario.getNomeUsuario() );
    	
    	if (ocorrenciaLiberacaoDTO != null){
    		this.listInfoRegExecucaoRequisicaoLiberacao(document, request, response);
    	}
    	
    	if (this.listInfoRegExecucaoRequisicaoLiberacao(document, request, response) != null) {
			document.getElementById("divRelacaoOcorrencias").setInnerHTML(this.listInfoRegExecucaoRequisicaoLiberacao(document, request, response));
		}
		
    	
    }
 
    private void geraComboCategoria(DocumentHTML document, HttpServletRequest request) throws Exception {
    	HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById("categoria");
    	comboTipoDemanda.removeAllOptions();
    	comboTipoDemanda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") );
    	
    	for (Enumerados.CategoriaOcorrencia c : Enumerados.CategoriaOcorrencia.values() ) {
    		if (!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.getSigla().toString() ) && 
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Reclassificacao.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Agendamento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Reativacao.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Encerramento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Reabertura.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Direcionamento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Compartilhamento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Criacao.getSigla().toString() ) ) {
    			comboTipoDemanda.addOption(c.getSigla().toString(), c.getDescricao());
    		}
    	}
    }
    
    private void geraComboOrigem(DocumentHTML document, HttpServletRequest request) throws Exception {
    	HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById("origem");
    	comboTipoDemanda.removeAllOptions();
    	comboTipoDemanda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") );
	
    	for(Enumerados.OrigemOcorrencia c : Enumerados.OrigemOcorrencia.values() ) {
    		comboTipoDemanda.addOption(c.getSigla().toString(), c.getDescricao() );
    	}
    }
    
    
    
    /**
	 * Retorna uma lista de informações da entidade ocorrencia
	 * @param requisicaoMudancaDto
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa
	 */
	public String listInfoRegExecucaoRequisicaoLiberacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);

		OcorrenciaLiberacaoService ocorrenciaLiberacaoService = (OcorrenciaLiberacaoService) ServiceLocator.getInstance().getService(OcorrenciaLiberacaoService.class, null);

		Collection<OcorrenciaLiberacaoDTO> col = ocorrenciaLiberacaoService.findByIdRequisicaoLiberacao(ocorrenciaLiberacaoDTO.getIdRequisicaoLiberacao());
		
		CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();

		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
		OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();
		
		//
		String strBuffer = "<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>";
    	strBuffer += "<tr>";
    	strBuffer += "<td class='linhaSubtituloGridOcorr'>";
		strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaMudanca.codigoocorrencia");
		strBuffer += "</td>";
		strBuffer += "<td class='linhaSubtituloGridOcorr'>";
		strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaMudanca.informacaoocorrencia");
		strBuffer += "</td>";
		strBuffer += "<td class='linhaSubtituloGridOcorr'>";
		strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.categoria");
		strBuffer += "</td>";
		strBuffer += "<td class='linhaSubtituloGridOcorr'>";
		strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.origem");
		strBuffer += "</td>";   
		strBuffer += "<td class='linhaSubtituloGridOcorr'>";
		strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaMudanca.tempoGasto");
		strBuffer += "</td>";
		strBuffer += "</tr>";
		//
		
		if (col != null) {

			for (OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto : col) {
				
				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaLiberacaoDto.getOcorrencia());
				
				if (ocorrenciaLiberacaoDto.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaLiberacaoDto.getOcorrencia());
					ocorrenciaLiberacaoDto.setOcorrencia(source.getTextExtractor().toString());
				}
		
				if (ocorrenciaLiberacaoDto.getIdJustificativa() != null) {
					JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
					justificativaDto.setIdJustificativa(ocorrenciaLiberacaoDto.getIdJustificativa() );
					justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
					if (justificativaDto != null) 
						ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa() + "<br>";
				}
				
				if (ocorrenciaLiberacaoDto.getComplementoJustificativa() != null) 
					ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
					+ ": <b>" + ocorrenciaLiberacaoDto.getComplementoJustificativa() + "<br>";
		
				String dadosLiberacao = UtilStrings.nullToVazio(ocorrenciaLiberacaoDto.getDadosLiberacao());
		
				if (dadosLiberacao.length() > 0) {					
					try {
						RequisicaoLiberacaoDTO solicitacaoDto = new Gson().fromJson(dadosLiberacao,RequisicaoLiberacaoDTO.class);
						
						if (solicitacaoDto != null){
							dadosLiberacao = solicitacaoDto.getDadosStr();
						}
					} catch (Exception e) {
						dadosLiberacao = "";
					}
				}
				
				String descricao = UtilStrings.nullToVazio(ocorrenciaLiberacaoDto.getDescricao() ) ;
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaLiberacaoDto.getInformacoesContato() );
		
				if (informacoesContato.length() > 0) {
					try {
						ContatoRequisicaoMudancaDTO contatoDto = new Gson().fromJson(informacoesContato, ContatoRequisicaoMudancaDTO.class);
						if (contatoDto != null)
							informacoesContato = contatoDto.getDadosStr();
					} catch (Exception e) {
						informacoesContato = "";
					}	
				}
				
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br>");
				descricao = descricao.replaceAll("\n", "<br>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br>");
				dadosLiberacao = dadosLiberacao.replaceAll("\n", "<br>");		
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", "") );
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", "") );				
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", "") );
				
				strBuffer += "<tr>";
				strBuffer += "<td rowspan='4'>";
				strBuffer += "<b>" + ocorrenciaLiberacaoDto.getIdOcorrencia() + "</b>";
				strBuffer += "</td>";
	        	strBuffer += "<td>";
	        	strBuffer += "<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ocorrenciaLiberacaoDto.getDataregistro(), WebUtil.getLanguage(request)) + " - " + ocorrenciaLiberacaoDto.getHoraregistro();
	        	strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaMudanca.registradopor") + ": <b>" + ocorrenciaLiberacaoDto.getRegistradopor() + "</b>";
	        	strBuffer += "</td>";
	        	
	        	// Categoria Ocorrência
	        	strBuffer += "<td>";
	        	if (ocorrenciaLiberacaoDto.getIdCategoriaOcorrencia() != null && ocorrenciaLiberacaoDto.getIdCategoriaOcorrencia() != 0) {
	        		categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaLiberacaoDto.getIdCategoriaOcorrencia() );
	        		categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
	        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <b>" + categoriaOcorrenciaDTO.getNome() + "</b>";
	        	} else {	        		
	        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": ";
	        	}				
	        	strBuffer += "</td>";
				
	        	// Origem Ocorrência
				strBuffer += "<td>";
				if (ocorrenciaLiberacaoDto.getIdOrigemOcorrencia() != null && ocorrenciaLiberacaoDto.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaLiberacaoDto.getIdOrigemOcorrencia() );
					origemOcorrenciaDTO = (OrigemOcorrenciaDTO)	origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
					strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <b>" + origemOcorrenciaDTO.getNome() + "</b>";
				} else {
					strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": ";
				}
				strBuffer += "</td>";
				
				strBuffer += "<td>";
				strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaMudanca.tempoGasto") + ": <b><br />" + (ocorrenciaLiberacaoDto.getTempoGasto() != null ? "" 
				+ ocorrenciaLiberacaoDto.getTempoGasto() + " min" : "--") + "</b>";
				strBuffer += "</td>";			
				strBuffer += "</tr>";
		
				if (dadosLiberacao == null || dadosLiberacao.trim().equalsIgnoreCase("") ) {
					strBuffer += "<tr>";
        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
    				strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" + descricao + "<br><br></b>";
        			strBuffer += "</td>";		
        			strBuffer += "</tr>";
				} else {
					strBuffer += "<tr>";
    				strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
    				
    				if (dadosLiberacao != null && !dadosLiberacao.trim().equalsIgnoreCase("") ) {
    					strBuffer += "<br>" + UtilI18N.internacionaliza(request, "ocorrenciaMudanca.dadosrequisicaomudanca") + ": <b><br>" + dadosLiberacao + "<br><br></b>";
    				}
    				
    				strBuffer += "</td>";
        			strBuffer += "</tr>";
				}
			
				strBuffer += "<tr>";
				strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
			
				if (ocorrencia.length() > 0) 
					strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.ocorrencia") + ": <br><b>" + ocorrencia + "<br><br></b>";
				else
					strBuffer += "&nbsp;";
				strBuffer += "</td>";		
				strBuffer += "</tr>";
		
				if (informacoesContato == null || informacoesContato.trim().equalsIgnoreCase("") ) {
					strBuffer += "<tr>";
        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
        			if (ocorrenciaLiberacaoDto.getInformacoesContato() != null && ocorrenciaLiberacaoDto.getInformacoesContato().length() > 0) 
        				strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + ocorrenciaLiberacaoDto.getInformacoesContato() + "<br><br></b>";
        			else
        				strBuffer += "&nbsp;";
        			strBuffer += "</td>";
        			strBuffer += "</tr>";
				} else {
					strBuffer += "<tr>";
        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
        			if (informacoesContato.length() > 0) 
        				strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + informacoesContato + "<br><br></b>";
        			else
        				strBuffer += "&nbsp;";
    				strBuffer += "</td>";
					strBuffer += "</tr>";
				}
			}
		}
		strBuffer += "</table>";

		categoriaOcorrenciaDTO = null;
		origemOcorrenciaDTO = null;
		
		return strBuffer;
	}
    
    
    
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada")	);
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	ocorrenciaLiberacaoDTO = (OcorrenciaLiberacaoDTO) document.getBean();
    	
    	if (ocorrenciaLiberacaoDTO.getIdCategoriaOcorrencia() == null) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE03") );
    		return;
    	}
    	
    	if (ocorrenciaLiberacaoDTO.getIdOrigemOcorrencia().equals("")) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE4") );
    		return;
    	}
    	ocorrenciaLiberacaoDTO.setDataInicio(UtilDatas.getDataAtual() ); 	
    	ocorrenciaLiberacaoDTO.setRegistradopor(usuario.getNomeUsuario() );
    	ocorrenciaLiberacaoDTO.setDataregistro(UtilDatas.getDataAtual() );
    	ocorrenciaLiberacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual() ) );
    	ocorrenciaLiberacaoDTO.setIdRequisicaoLiberacao(ocorrenciaLiberacaoDTO.getIdRequisicaoLiberacao() ); 
    	getService().create(ocorrenciaLiberacaoDTO);
    	document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    	HTMLForm form = document.getForm("formOcorrenciaLiberacao");
    	form.clear();
    	document.executeScript("$('#POPUP_menuOcorrencias').dialog('close');");
    }
	
	 private OcorrenciaLiberacaoService getService() throws ServiceException, Exception {	
		 OcorrenciaLiberacaoService ocorrenciaLiberacaoService = (OcorrenciaLiberacaoService) ServiceLocator.getInstance().getService(OcorrenciaLiberacaoService.class, null);
	    	return ocorrenciaLiberacaoService;
	    }

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return OcorrenciaLiberacaoDTO.class;
	}
	
	public OcorrenciaLiberacaoDTO getOcorrenciaLiberacaoDTO() {
		return ocorrenciaLiberacaoDTO;
	}

	public void setOcorrenciaLiberacaoDTO(OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDTO) {
		this.ocorrenciaLiberacaoDTO = ocorrenciaLiberacaoDTO;
	}
    
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OcorrenciaLiberacaoDTO ocorrencia = (OcorrenciaLiberacaoDTO) document.getBean();
		ocorrencia = (OcorrenciaLiberacaoDTO) getService().restore(ocorrencia);
		HTMLForm form = document.getForm("formOcorrenciaLiberacao");

		form.clear();
		form.setValues(ocorrencia);
    }
	
	public void listOcorrenciasSituacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
	    	
		UsuarioDTO usuario = WebUtil.getUsuario(request);
	    	
	    	if (usuario == null) {
	    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
	    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	    		return;
	    	}
	    	
	    	CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
	    	OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();
	    	
	    	CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.getBean().newInstance();
	    	OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) origemOcorrenciaDAO.getBean().newInstance();
	    	
	    	OcorrenciaLiberacaoDTO bean = (OcorrenciaLiberacaoDTO)document.getBean();
	    	bean.setIdRequisicaoLiberacao(bean.getIdOcorrencia());
	        OcorrenciaLiberacaoService ocorrenciaLiberacao = (OcorrenciaLiberacaoService) ServiceLocator.getInstance().getService(OcorrenciaLiberacaoService.class, null);

//	    	OcorrenciaSolicitacaoService ocorrenciaService = getService();
	    	Collection col = ocorrenciaLiberacao.findByIdRequisicaoLiberacao(bean.getIdRequisicaoLiberacao() );
	    	
	    	String strBuffer = "<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>";
	    	strBuffer += "<tr>";
	    	strBuffer += "<td class='linhaSubtituloGridOcorr'>";
			strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaLiberacao.codigoocorrencia");
			strBuffer += "</td>";
			strBuffer += "<td class='linhaSubtituloGridOcorr'>";
			strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaLiberacao.informacaoocorrencia");
			strBuffer += "</td>";
			strBuffer += "<td class='linhaSubtituloGridOcorr'>";
			strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.categoria");
			strBuffer += "</td>";
			strBuffer += "<td class='linhaSubtituloGridOcorr'>";
			strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.origem");
			strBuffer += "</td>";   
			strBuffer += "<td class='linhaSubtituloGridOcorr'>";
			strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaLiberacao.tempoGasto");
			strBuffer += "</td>";
			strBuffer += "</tr>";
			
			if (col != null && col.size() > 0) {				
				JustificativaRequisicaoLiberacaoService justificativaService = (JustificativaRequisicaoLiberacaoService) ServiceLocator.getInstance().getService(JustificativaRequisicaoLiberacaoService.class, null);
	            AprovacaoRequisicaoLiberacaoService aprovacaoService = (AprovacaoRequisicaoLiberacaoService) ServiceLocator.getInstance().getService(AprovacaoRequisicaoLiberacaoService.class, null);
	            EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

	            for (Iterator it = col.iterator(); it.hasNext(); ) {
					OcorrenciaLiberacaoDTO ocorrenciaLiberacaoAux = (OcorrenciaLiberacaoDTO) it.next();				
					String ocorrencia = UtilStrings.nullToVazio(ocorrenciaLiberacaoAux.getOcorrencia() );
			
					if (ocorrenciaLiberacaoAux.getIdJustificativa() != null) {
						JustificativaRequisicaoLiberacaoDTO justificativaDto = new JustificativaRequisicaoLiberacaoDTO();
						justificativaDto.setIdJustificativaLiberacao(ocorrenciaLiberacaoAux.getIdJustificativa());
						justificativaDto = (JustificativaRequisicaoLiberacaoDTO) justificativaService.restore(justificativaDto);
						if (justificativaDto != null) 
							ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa() + "<br>";
					}
					
					if (ocorrenciaLiberacaoAux.getComplementoJustificativa() != null) 
						ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
						+ ": <b>" + ocorrenciaLiberacaoAux.getComplementoJustificativa() + "<br>";
			
					String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaLiberacaoAux.getDadosLiberacao() );
			
					RequisicaoLiberacaoDTO liberacaoDto = null;
					if (dadosSolicitacao.length() > 0) {					
						try {
							liberacaoDto = new Gson().fromJson(dadosSolicitacao,RequisicaoLiberacaoDTO.class);
							
							if (liberacaoDto != null)
								dadosSolicitacao = liberacaoDto.getDadosStr();
						} catch (Exception e) {
							dadosSolicitacao = "";
						}
					}
					
					String descricao = UtilStrings.nullToVazio(ocorrenciaLiberacaoAux.getDescricao() ) ;
					String informacoesContato = UtilStrings.nullToVazio(ocorrenciaLiberacaoAux.getInformacoesContato() );
			
					if (informacoesContato.length() > 0) {
						try {
							ContatoSolicitacaoServicoDTO contatoDto = new Gson().fromJson(informacoesContato, ContatoSolicitacaoServicoDTO.class);
							if (contatoDto != null)
								informacoesContato = contatoDto.getDadosStr();
						} catch (Exception e) {
							informacoesContato = "";
						}	
					}
					
					String aprovacao = "";
					if (liberacaoDto != null && liberacaoDto.getIdUltimaAprovacao() != null && ocorrenciaLiberacaoAux.getIdItemTrabalho() != null) {
					    AprovacaoRequisicaoLiberacaoDTO aprovacaoDto = new AprovacaoRequisicaoLiberacaoDTO();
					    aprovacaoDto.setIdAprovacaoRequisicaoLiberacao(liberacaoDto.getIdUltimaAprovacao());
					    aprovacaoDto = (AprovacaoRequisicaoLiberacaoDTO) aprovacaoService.restore(aprovacaoDto);
					    if (aprovacaoDto.getIdTarefa() != null && aprovacaoDto.getIdTarefa().intValue() == ocorrenciaLiberacaoAux.getIdItemTrabalho().intValue()) {
					        EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(aprovacaoDto.getIdResponsavel());
					        if (empregadoDto != null)
		                        aprovacao += UtilI18N.internacionaliza(request, "citcorpore.comum.aprovador") + ": " + empregadoDto.getNome() + "<br>";
					        aprovacao += UtilI18N.internacionaliza(request, "citcorpore.comum.aprovada") + ": ";
					        if (aprovacaoDto.getAprovacao().equalsIgnoreCase("A"))
					            aprovacao += "Sim";
					        else
					            aprovacao += "Não";
					        if (aprovacaoDto.getIdJustificativa() != null) {
			                    JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
			                    justificativaDto.setIdJustificativa(aprovacaoDto.getIdJustificativa() );
			                    justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
			                    if (justificativaDto != null) 
			                        aprovacao += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa();
					        }
			                if (aprovacaoDto.getComplementoJustificativa() != null && aprovacaoDto.getComplementoJustificativa().trim().length() > 0) {
			                    aprovacao += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
			                    + ": " + aprovacaoDto.getComplementoJustificativa();
			                }
	                        if (aprovacaoDto.getObservacoes() != null && aprovacaoDto.getObservacoes().trim().length() > 0) {
	                            aprovacao += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.observacoes") 
	                            + ": " + aprovacaoDto.getObservacoes();
	                        }
					    }
					}
					
					ocorrencia = ocorrencia.replaceAll("\"", "");
					descricao = descricao.replaceAll("\"", "");
					informacoesContato = informacoesContato.replaceAll("\"", "");
					ocorrencia = ocorrencia.replaceAll("\n", "<br>");
					descricao = descricao.replaceAll("\n", "<br>");
					informacoesContato = informacoesContato.replaceAll("\n", "<br>");
					dadosSolicitacao = dadosSolicitacao.replaceAll("\n", "<br>");		
					ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", "") );
					descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", "") );				
					informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", "") );
					
					strBuffer += "<tr>";
					strBuffer += "<td rowspan='4' style='border:1px solid black'>";
					strBuffer += "<b>" + ocorrenciaLiberacaoAux.getIdOcorrencia() + "</b>";
					strBuffer += "</td>";
		        	strBuffer += "<td style='border:1px solid black'>";
		        	strBuffer += "<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ocorrenciaLiberacaoAux.getDataregistro(), WebUtil.getLanguage(request)) + " - " + ocorrenciaLiberacaoAux.getHoraregistro();
		        	if (ocorrenciaLiberacaoAux.getRegistradopor() != null)
		        	    strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + ocorrenciaLiberacaoAux.getRegistradopor() + "</b>";
		        	strBuffer += "</td>";
		        	
		        	// Categoria Ocorrência
		        	strBuffer += "<td style='border:1px solid black'>";
		        	if (ocorrenciaLiberacaoAux.getIdCategoriaOcorrencia() != null && ocorrenciaLiberacaoAux.getIdCategoriaOcorrencia() != 0) {
		        		categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaLiberacaoAux.getIdCategoriaOcorrencia() );
		        		categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
		        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <b>" + categoriaOcorrenciaDTO.getNome() + "</b>";
		        	} else {	        		
		        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": ";
		        	}				
		        	strBuffer += "</td>";
					
		        	// Origem Ocorrência
					strBuffer += "<td style='border:1px solid black'>";
					if (ocorrenciaLiberacaoAux.getIdOrigemOcorrencia() != null && ocorrenciaLiberacaoAux.getIdOrigemOcorrencia() != 0) {
						origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaLiberacaoAux.getIdOrigemOcorrencia() );
						origemOcorrenciaDTO = (OrigemOcorrenciaDTO)	origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
						strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <b>" + origemOcorrenciaDTO.getNome() + "</b>";
					} else {
						strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": ";
					}
					strBuffer += "</td>";
					
					strBuffer += "<td style='border:1px solid black'>";
					strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.tempoGasto") + ": <b><br />" + (ocorrenciaLiberacaoAux.getTempoGasto() != null ? "" 
					+ ocorrenciaLiberacaoAux.getTempoGasto() + " min" : "--") + "</b>";
					strBuffer += "</td>";			
					strBuffer += "</tr>";
			
					if (dadosSolicitacao == null || dadosSolicitacao.trim().equalsIgnoreCase("") ) {
						strBuffer += "<tr>";
	        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
	    				strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" + descricao + "<br><br></b>";
	        			strBuffer += "</td>";		
	        			strBuffer += "</tr>";
					} else {
						strBuffer += "<tr>";
	    				strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
	    				
	                    strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" + descricao + "<br><br></b>";

	                    if (dadosSolicitacao != null && !dadosSolicitacao.trim().equalsIgnoreCase("") ) {
	    					strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.dadosolicitacao") + ": <b><br>" + dadosSolicitacao + "<br><br></b>";
	    				}
	    				
	    				strBuffer += "</td>";
	        			strBuffer += "</tr>";
					}
				
				
					if (ocorrencia.length() > 0) {
		                strBuffer += "<tr>";
		                strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
						strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.ocorrencia") + ": <br><b>" + ocorrencia + "<br><br></b>";
						if (aprovacao.length() > 0)
		                    strBuffer += "<b>" + aprovacao + "<br><br></b>";
	    				strBuffer += "</td>";		
	    				strBuffer += "</tr>";
					}else{
	                    strBuffer += "<tr>";
	                    strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
	                    strBuffer += "&nbsp;";
	                    strBuffer += "</td>";       
	                    strBuffer += "</tr>";
					}

	                if (informacoesContato == null || informacoesContato.trim().equalsIgnoreCase("") ) {
						strBuffer += "<tr>";
	        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
	        			if (ocorrenciaLiberacaoAux.getInformacoesContato() != null && ocorrenciaLiberacaoAux.getInformacoesContato().length() > 0) 
	        				strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + ocorrenciaLiberacaoAux.getInformacoesContato() + "<br><br></b>";
	        			else
	        				strBuffer += "&nbsp;";
	        			strBuffer += "</td>";
	        			strBuffer += "</tr>";
					} else {
						strBuffer += "<tr>";
	        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
	        			if (informacoesContato.length() > 0) 
	        				strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + informacoesContato + "<br><br></b>";
	        			else
	        				strBuffer += "&nbsp;";
	    				strBuffer += "</td>";
						strBuffer += "</tr>";
					}
				}
			} else {
				strBuffer = "<table width='100%' border='1'>";
				strBuffer += "<tr>";
				strBuffer += "<td colspan='4' style='border:1px solid black'>";
				strBuffer += "<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoinformacao") + "</b>";
				strBuffer += "</td>";		
				strBuffer += "</tr>";	    
			}
			strBuffer += "</table>";	
			document.getElementById("divRelacaoOcorrencias").setInnerHTML(strBuffer);
	    }
	    

}

