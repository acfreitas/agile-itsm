package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaProblemaService;
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

/**
 * 
 * @author geber.costa
 * 
 */

@SuppressWarnings({"unchecked","rawtypes","unused"})
public class OcorrenciaProblema extends AjaxFormAction {

	private ProblemaDTO problemaDto;
	private OcorrenciaProblemaDTO ocorrenciaProblemaDto;
	

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		ocorrenciaProblemaDto = (OcorrenciaProblemaDTO) document.getBean();
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	geraComboCategoria(document, request);
    	geraComboOrigem(document, request);
    	document.getElementById("registradopor").setValue(usuario.getNomeUsuario() );
    	
    	if (ocorrenciaProblemaDto != null){
    		this.listInfoRegExecucaoProblema(document, request, response);
    	}
    	
    	if (this.listInfoRegExecucaoProblema(document, request, response) != null) {
			document.getElementById("divRelacaoOcorrencias").setInnerHTML(this.listInfoRegExecucaoProblema(document, request, response));
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
	 * @param ocorrenciaProblema
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa
	 */
	
    /* Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 17:33 - ID Citsmart: 120948 - 
     * Motivo/Comentário: Table no padrão antigo / Tabela no novo layout */
	public String listInfoRegExecucaoProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);

		OcorrenciaProblemaService ocorrenciaProblemaService = (OcorrenciaProblemaService) ServiceLocator.getInstance().getService(OcorrenciaProblemaService.class, null);
		
		Collection<OcorrenciaProblemaDTO> col = ocorrenciaProblemaService.findByIdProblema(ocorrenciaProblemaDto.getIdProblema());

		CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();

		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
		OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();
		
		String strBuffer = "<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>";
    	strBuffer += "<tr>";
    	strBuffer += "<th >";
		strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaProblema.codigoocorrencia");
		strBuffer += "</th>";
		strBuffer += "<th >";
		strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaProblema.informacaoocorrencia");
		strBuffer += "</th>";
		strBuffer += "<th >";
		strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.categoria");
		strBuffer += "</th>";
		strBuffer += "<th >";
		strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.origem");
		strBuffer += "</th>";   
		strBuffer += "<th >";
		strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaProblema.tempoGasto");
		strBuffer += "</th>";
		strBuffer += "</th>";
		
		if (col != null) {

			for (OcorrenciaProblemaDTO ocorrenciaProblemaDto : col) {
				
				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getOcorrencia());
				
				if (ocorrenciaProblemaDto.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaProblemaDto.getOcorrencia());
					ocorrenciaProblemaDto.setOcorrencia(source.getTextExtractor().toString());
				}
		
				if (ocorrenciaProblemaDto.getIdJustificativa() != null) {
					JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
					justificativaDto.setIdJustificativa(ocorrenciaProblemaDto.getIdJustificativa() );
					justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
					if (justificativaDto != null) 
						ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa() + "<br>";
				}
				
				if (ocorrenciaProblemaDto.getComplementoJustificativa() != null) 
					ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
					+ ": <b>" + ocorrenciaProblemaDto.getComplementoJustificativa() + "<br>";
		
				String dadosProblema = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getDadosProblema());
		
				if (dadosProblema.length() > 0) {					
					try {
						ProblemaDTO problemaDTO = new Gson().fromJson(dadosProblema,ProblemaDTO.class);
						
						if (problemaDTO != null)
							dadosProblema = problemaDTO.getDadosStr();
					} catch (Exception e) {
						dadosProblema = "";
					}
				}
				
				String descricao = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getDescricao() ) ;
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getInformacoesContato() );
		
				if (informacoesContato.length() > 0) {
					try {
						ContatoProblemaDTO contatoProblemaDto = new Gson().fromJson(informacoesContato, ContatoProblemaDTO.class);
						if (contatoProblemaDto != null)
							informacoesContato = contatoProblemaDto.getDadosStr();
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
				dadosProblema = dadosProblema.replaceAll("\n", "<br>");		
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", "") );
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", "") );				
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", "") );
				
				strBuffer += "<tr>";
				strBuffer += "<td rowspan='4'>";
				strBuffer += "<b>" + ocorrenciaProblemaDto.getIdOcorrencia() + "</b>";
				strBuffer += "</td>";
	        	strBuffer += "<td >";
	        	strBuffer += "<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ocorrenciaProblemaDto.getDataregistro(), WebUtil.getLanguage(request)) + " - " + ocorrenciaProblemaDto.getHoraregistro();
	        	strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaProblema.registradopor") + ": <b>" + ocorrenciaProblemaDto.getRegistradopor() + "</b>";
	        	strBuffer += "</td>";
	        	
	        	// Categoria Ocorrência
	        	strBuffer += "<td >";
	        	if (ocorrenciaProblemaDto.getIdCategoriaOcorrencia() != null && ocorrenciaProblemaDto.getIdCategoriaOcorrencia() != 0) {
	        		categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaProblemaDto.getIdCategoriaOcorrencia() );
	        		categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
	        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <b>" + categoriaOcorrenciaDTO.getNome() + "</b>";
	        	} else {	        		
	        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": ";
	        	}				
	        	strBuffer += "</td>";
				
	        	// Origem Ocorrência
				strBuffer += "<td >";
				if (ocorrenciaProblemaDto.getIdOrigemOcorrencia() != null && ocorrenciaProblemaDto.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaProblemaDto.getIdOrigemOcorrencia() );
					origemOcorrenciaDTO = (OrigemOcorrenciaDTO)	origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
					strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <b>" + origemOcorrenciaDTO.getNome() + "</b>";
				} else {
					strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": ";
				}
				strBuffer += "</td>";
				
				strBuffer += "<td >";
				strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaProblema.tempoGasto") + ": <b><br />" + (ocorrenciaProblemaDto.getTempoGasto() != null ? "" 
				+ ocorrenciaProblemaDto.getTempoGasto() + " min" : "--") + "</b>";
				strBuffer += "</td>";			
				strBuffer += "</tr>";
		
				if (dadosProblema == null || dadosProblema.trim().equalsIgnoreCase("") ) {
					strBuffer += "<tr>";
        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
    				strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" + descricao + "<br><br></b>";
        			strBuffer += "</td>";		
        			strBuffer += "</tr>";
				} else {
					strBuffer += "<tr>";
    				strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
    				
    				if (dadosProblema != null && !dadosProblema.trim().equalsIgnoreCase("") ) {
    					strBuffer += "<br>" + UtilI18N.internacionaliza(request, "ocorrenciaProblema.dadosproblema") + ": <b><br>" + dadosProblema + "<br><br></b>";
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
        			if (ocorrenciaProblemaDto.getInformacoesContato() != null && ocorrenciaProblemaDto.getInformacoesContato().length() > 0) 
        				strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + ocorrenciaProblemaDto.getInformacoesContato() + "<br><br></b>";
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
    	
    	ocorrenciaProblemaDto = (OcorrenciaProblemaDTO) document.getBean();
    	
    	if (ocorrenciaProblemaDto.getIdCategoriaOcorrencia() == null) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE03") );
    		return;
    	}
    	
    	if (ocorrenciaProblemaDto.getIdOrigemOcorrencia().equals("")) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE4") );
    		return;
    	}
    	
    	ocorrenciaProblemaDto.setDataInicio(UtilDatas.getDataAtual() ); 	
    	ocorrenciaProblemaDto.setRegistradopor(usuario.getNomeUsuario() );
    	ocorrenciaProblemaDto.setDataregistro(UtilDatas.getDataAtual() );
    	ocorrenciaProblemaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual() ) );
    	ocorrenciaProblemaDto.setIdProblema(ocorrenciaProblemaDto.getIdProblema());
    	getService().create(ocorrenciaProblemaDto);
    	document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    	HTMLForm form = document.getForm("formOcorrenciaProblema");
    	form.clear();
    	document.executeScript("$('#POPUP_menuOcorrencias').dialog('close');");
    }
	
	 private OcorrenciaProblemaService getService() throws ServiceException, Exception {	
		 OcorrenciaProblemaService ocorrenciaProblemaService = (OcorrenciaProblemaService) ServiceLocator.getInstance().getService(OcorrenciaProblemaService.class, null);
	    	return ocorrenciaProblemaService;
	    }

	
	@Override
	public Class getBeanClass() {

		return OcorrenciaProblemaDTO.class;
	}
	
	public OcorrenciaProblemaDTO getOcorrenciaProblemaDto() {
		return ocorrenciaProblemaDto;
	}

	public void setOcorrenciaProblemaDto(OcorrenciaProblemaDTO ocorrenciaProblemaDto) {
		this.ocorrenciaProblemaDto = ocorrenciaProblemaDto;
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OcorrenciaProblemaDTO ocorrencia = (OcorrenciaProblemaDTO) document.getBean();
		ocorrencia = (OcorrenciaProblemaDTO) getService().restore(ocorrencia);
		HTMLForm form = document.getForm("formOcorrenciaProblema");

		form.clear();
		form.setValues(ocorrencia);
    }
}
