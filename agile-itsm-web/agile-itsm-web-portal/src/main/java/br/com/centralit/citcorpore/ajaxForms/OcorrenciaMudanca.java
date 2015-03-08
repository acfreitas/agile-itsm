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
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaMudancaService;
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

public class OcorrenciaMudanca extends AjaxFormAction  {
	
	private RequisicaoMudancaDTO requisicaoMudancaDto;
	
	private OcorrenciaMudancaDTO ocorrenciaMudancaDto;
	
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	ocorrenciaMudancaDto = (OcorrenciaMudancaDTO) document.getBean();
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	geraComboCategoria(document, request);
    	geraComboOrigem(document, request);
    	document.getElementById("registradopor").setValue(usuario.getNomeUsuario() );
    	
    	if (ocorrenciaMudancaDto != null){
    		this.listInfoRegExecucaoRequisicaoMudanca(document, request, response);
    	}
    	
    	if (this.listInfoRegExecucaoRequisicaoMudanca(document, request, response) != null) {
			document.getElementById("divRelacaoOcorrencias").setInnerHTML(this.listInfoRegExecucaoRequisicaoMudanca(document, request, response));
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
	public String listInfoRegExecucaoRequisicaoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);

		OcorrenciaMudancaService ocorrenciaMudancaService = (OcorrenciaMudancaService) ServiceLocator.getInstance().getService(OcorrenciaMudancaService.class, null);

		Collection<OcorrenciaMudancaDTO> col = ocorrenciaMudancaService.findByIdRequisicaoMudanca(ocorrenciaMudancaDto.getIdRequisicaoMudanca());

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

			for (OcorrenciaMudancaDTO ocorrenciaMudancaDto : col) {
				
				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getOcorrencia());
				
				if (ocorrenciaMudancaDto.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaMudancaDto.getOcorrencia());
					ocorrenciaMudancaDto.setOcorrencia(source.getTextExtractor().toString());
				}
		
				if (ocorrenciaMudancaDto.getIdJustificativa() != null) {
					JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
					justificativaDto.setIdJustificativa(ocorrenciaMudancaDto.getIdJustificativa() );
					justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
					if (justificativaDto != null) 
						ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa() + "<br>";
				}
				
				if (ocorrenciaMudancaDto.getComplementoJustificativa() != null) 
					ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
					+ ": <b>" + ocorrenciaMudancaDto.getComplementoJustificativa() + "<br>";
		
				String dadosMudanca = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getDadosMudanca());
		
				if (dadosMudanca.length() > 0) {					
					try {
						RequisicaoMudancaDTO solicitacaoDto = new Gson().fromJson(dadosMudanca,RequisicaoMudancaDTO.class);
						
						if (solicitacaoDto != null)
							dadosMudanca = solicitacaoDto.getDadosStr();
					} catch (Exception e) {
						dadosMudanca = "";
					}
				}
				
				String descricao = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getDescricao() ) ;
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getInformacoesContato() );
		
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
				dadosMudanca = dadosMudanca.replaceAll("\n", "<br>");		
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", "") );
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", "") );				
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", "") );
				
				strBuffer += "<tr>";
				strBuffer += "<td rowspan='4'>";
				strBuffer += "<b>" + ocorrenciaMudancaDto.getIdOcorrencia() + "</b>";
				strBuffer += "</td>";
	        	strBuffer += "<td>";
	        	strBuffer += "<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ocorrenciaMudancaDto.getDataregistro(), WebUtil.getLanguage(request)) + " - " + ocorrenciaMudancaDto.getHoraregistro();
	        	strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaMudanca.registradopor") + ": <b>" + ocorrenciaMudancaDto.getRegistradopor() + "</b>";
	        	strBuffer += "</td>";
	        	
	        	// Categoria Ocorrência
	        	strBuffer += "<td>";
	        	if (ocorrenciaMudancaDto.getIdCategoriaOcorrencia() != null && ocorrenciaMudancaDto.getIdCategoriaOcorrencia() != 0) {
	        		categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaMudancaDto.getIdCategoriaOcorrencia() );
	        		categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
	        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <b>" + categoriaOcorrenciaDTO.getNome() + "</b>";
	        	} else {	        		
	        		strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": ";
	        	}				
	        	strBuffer += "</td>";
				
	        	// Origem Ocorrência
				strBuffer += "<td>";
				if (ocorrenciaMudancaDto.getIdOrigemOcorrencia() != null && ocorrenciaMudancaDto.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaMudancaDto.getIdOrigemOcorrencia() );
					origemOcorrenciaDTO = (OrigemOcorrenciaDTO)	origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
					strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <b>" + origemOcorrenciaDTO.getNome() + "</b>";
				} else {
					strBuffer += UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": ";
				}
				strBuffer += "</td>";
				
				strBuffer += "<td>";
				strBuffer += UtilI18N.internacionaliza(request, "ocorrenciaMudanca.tempoGasto") + ": <b><br />" + (ocorrenciaMudancaDto.getTempoGasto() != null ? "" 
				+ ocorrenciaMudancaDto.getTempoGasto() + " min" : "--") + "</b>";
				strBuffer += "</td>";			
				strBuffer += "</tr>";
		
				if (dadosMudanca == null || dadosMudanca.trim().equalsIgnoreCase("") ) {
					strBuffer += "<tr>";
        			strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
    				strBuffer +=  UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" + descricao + "<br><br></b>";
        			strBuffer += "</td>";		
        			strBuffer += "</tr>";
				} else {
					strBuffer += "<tr>";
    				strBuffer += "<td colspan='4' style='word-wrap: break-word;overflow:hidden;'>";
    				
    				if (dadosMudanca != null && !dadosMudanca.trim().equalsIgnoreCase("") ) {
    					strBuffer += "<br>" + UtilI18N.internacionaliza(request, "ocorrenciaMudanca.dadosrequisicaomudanca") + ": <b><br>" + dadosMudanca + "<br><br></b>";
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
        			if (ocorrenciaMudancaDto.getInformacoesContato() != null && ocorrenciaMudancaDto.getInformacoesContato().length() > 0) 
        				strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + ocorrenciaMudancaDto.getInformacoesContato() + "<br><br></b>";
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
    	
    	ocorrenciaMudancaDto = (OcorrenciaMudancaDTO) document.getBean();
    	
    	if (ocorrenciaMudancaDto.getIdCategoriaOcorrencia() == null) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE03") );
    		return;
    	}
    	
    	if (ocorrenciaMudancaDto.getIdOrigemOcorrencia().equals("")) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE4") );
    		return;
    	}
    	
    	ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual() ); 	
    	ocorrenciaMudancaDto.setRegistradopor(usuario.getNomeUsuario() );
    	ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual() );
    	ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual() ) );
    	ocorrenciaMudancaDto.setIdRequisicaoMudanca(ocorrenciaMudancaDto.getIdRequisicaoMudanca() );
    	getService().create(ocorrenciaMudancaDto);
    	document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    	HTMLForm form = document.getForm("formOcorrenciaMudanca");
    	form.clear();
    	document.executeScript("$('#POPUP_menuOcorrencias').dialog('close');");
    }
	
	 private OcorrenciaMudancaService getService() throws ServiceException, Exception {	
		 OcorrenciaMudancaService ocorrenciaMudancaService = (OcorrenciaMudancaService) ServiceLocator.getInstance().getService(OcorrenciaMudancaService.class, null);
	    	return ocorrenciaMudancaService;
	    }

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return OcorrenciaMudancaDTO.class;
	}
	
	public OcorrenciaMudancaDTO getOcorrenciaMudancaDto() {
		return ocorrenciaMudancaDto;
	}

	public void setOcorrenciaMudancaDto(OcorrenciaMudancaDTO ocorrenciaMudancaDto) {
		this.ocorrenciaMudancaDto = ocorrenciaMudancaDto;
	}
    
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OcorrenciaMudancaDTO ocorrencia = (OcorrenciaMudancaDTO) document.getBean();
		ocorrencia = (OcorrenciaMudancaDTO) getService().restore(ocorrencia);
		HTMLForm form = document.getForm("formOcorrenciaMudanca");

		form.clear();
		form.setValues(ocorrencia);
    }
}
