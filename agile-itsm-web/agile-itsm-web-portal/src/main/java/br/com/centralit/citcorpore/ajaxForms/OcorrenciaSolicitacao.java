/**
 * @author breno.guimaraes
 *
 */

package br.com.centralit.citcorpore.ajaxForms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.DadosEmailRegOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.AprovacaoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.CategoriaOcorrenciaService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OrigemOcorrenciaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class OcorrenciaSolicitacao  extends AjaxFormAction {    
   
	@Override
    public Class getBeanClass() {
    	return OcorrenciaSolicitacaoDTO.class;
    }
    
    private OcorrenciaSolicitacaoService getService() throws ServiceException, Exception {	
    	OcorrenciaSolicitacaoService ocorrenciaService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
    	return ocorrenciaService;
    }
    
    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
    	    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	Integer idSolicitacao = null;
    	String apenasVisualizar = "";
    	boolean resgistrarOcorrencia = false;
    	try {
    		idSolicitacao = Integer.parseInt(request.getParameter("idSolicitacaoServico"));
    		apenasVisualizar = request.getParameter("visualizar");
    		resgistrarOcorrencia = Boolean.valueOf(request.getParameter("resgistrarOcorrencia"));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	  
    	request.getSession().setAttribute("resgistrarOcorrenciaPortal", resgistrarOcorrencia);
    	
    	request.setAttribute("idSolicitacaoServico", idSolicitacao);
    	
    	if(apenasVisualizar.equalsIgnoreCase("false")){
    		document.executeScript("$('#tabCadastroOcorrencia').hide();");
    	}
    	
    	document.executeScript("document.getElementById('divRelacaoOcorrencias').innerHTML = '" + UtilI18N.internacionaliza(request, "citcorpore.comum.aguardecarregando")+"'");
    	
    	listOcorrenciasSituacao(document, request, idSolicitacao);
    	
    	geraComboCategoria(document, request);
    	
    	geraComboOrigem(document, request);
    	
    	this.configuraCheckNotificaSolicitante(document);    	

    	SolicitacaoServicoDTO solicitacaoServicoDTO = solicitacaoServicoService.restoreAll(idSolicitacao);
    	
    	document.getElementById("registradopor").setValue(usuario.getNomeUsuario());
    	
    	desabilitarCamposRegistrarOcorrencia(document, resgistrarOcorrencia,solicitacaoServicoDTO, request);
    	
    }
	
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada")	);
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = (OcorrenciaSolicitacaoDTO) document.getBean();
    	ocorrenciaSolicitacaoDTO.setDataInicio(UtilDatas.getDataAtual() ); 	
    	ocorrenciaSolicitacaoDTO.setRegistradopor(usuario.getNomeUsuario() );
    	ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual() );
    	ocorrenciaSolicitacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual() ) );
    	ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(ocorrenciaSolicitacaoDTO.getIdSolicitacaoOcorrencia() );
    	    	
    	getService().create(ocorrenciaSolicitacaoDTO);

    	//Realimentando os valores não restaurados
    	CategoriaOcorrenciaService categoriaOcorrenciaService = (CategoriaOcorrenciaService) ServiceLocator.getInstance().getService(CategoriaOcorrenciaService.class, null);
    	CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
    	categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaSolicitacaoDTO.getIdCategoriaOcorrencia());
    	categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaService.restore(categoriaOcorrenciaDTO);
    	
    	OrigemOcorrenciaService origemOcorrenciaService = (OrigemOcorrenciaService) ServiceLocator.getInstance().getService(OrigemOcorrenciaService.class, null);
    	OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();
    	origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaSolicitacaoDTO.getIdOrigemOcorrencia());
    	origemOcorrenciaDTO = (OrigemOcorrenciaDTO) origemOcorrenciaService.restore(origemOcorrenciaDTO);

    	ocorrenciaSolicitacaoDTO.setCategoria(categoriaOcorrenciaDTO.getNome()); 
    	ocorrenciaSolicitacaoDTO.setOrigem(origemOcorrenciaDTO.getNome());

    	// euler.ramos
    	// Tratamento para o envio de e-mails notificando o solicitante sobre o lançamento de ocorrências.
    	if ((ocorrenciaSolicitacaoDTO.getNotificarSolicitante()!=null)&&(ocorrenciaSolicitacaoDTO.getNotificarSolicitante().equalsIgnoreCase("S"))){
    		try {
    			this.enviaEmailSolicitante(this.obterIdModeloEmailNotificacaoSolicitante(),ocorrenciaSolicitacaoDTO,request);
    		} catch (Exception e) {
    			System.out.println("Problema no envio do e-mail de notificação de ocorrência ao solicitante.");
    		}
    	}
    	
    	Integer idEmailRegistroOcorrenciaPortal = obterIDModeloEmailNotificacaoResponsavel(); 
    	
    	if(ocorrenciaSolicitacaoDTO.getIsPortal() != null && ocorrenciaSolicitacaoDTO.getIsPortal().equals("true")){
	    	if (idEmailRegistroOcorrenciaPortal == null || idEmailRegistroOcorrenciaPortal == 0){
	    		
	    		document.alert(UtilI18N.internacionaliza(request, "idemailocorrenciaportal.incorreto.vazio"));
	    		
	    		return;
	    	}
    	}
    	
    	if ("S".equalsIgnoreCase(ocorrenciaSolicitacaoDTO.getNotificarResponsavel())){
    		
    		enviaEmailResponsavel(idEmailRegistroOcorrenciaPortal, ocorrenciaSolicitacaoDTO, request);
    	}    	
    	
    	document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    	
    	listOcorrenciasSituacao(document, request, ocorrenciaSolicitacaoDTO.getIdSolicitacaoOcorrencia());
    	document.executeScript("limparCamposOcorrencia()");
    	document.executeScript("parent.$('#modal_ocorrencia').modal('hide');");
    }
    
    /**
     * Metodo para recupera o idsolicitacaoOcorrencia para assim  listar as ocorrencias relativas aquela solicitacao.
     * @param document
     * @param request
     * @param response
     * @throws Exception\
     * @author thays.araujo
     */
    public void recuperaIdSolicitacaoOcorrencia(DocumentHTML document, HttpServletRequest request,HttpServletResponse response) throws Exception{
    	OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDto = (OcorrenciaSolicitacaoDTO)document.getBean();
    	if(ocorrenciaSolicitacaoDto.getIdSolicitacaoOcorrencia()!=null){
    		listOcorrenciasSituacao(document, request, ocorrenciaSolicitacaoDto.getIdSolicitacaoOcorrencia());
    	}
    }
    
    
    public void listOcorrenciasSituacao(DocumentHTML document, HttpServletRequest request, Integer idSolicitacaoServico) throws Exception {    	
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	final boolean registraOcorrenciaPortal = request.getSession().getAttribute("resgistrarOcorrenciaPortal") != null ? (boolean) request.getSession().getAttribute("resgistrarOcorrenciaPortal") : Boolean.FALSE;
    	
    	String language = (String)request.getSession().getAttribute("locale");
    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
    	OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();
    	
    	CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.getBean().newInstance();
    	OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) origemOcorrenciaDAO.getBean().newInstance();
    	
    	OcorrenciaSolicitacaoService ocorrenciaService = getService();
    	
    	Collection col = (idSolicitacaoServico != null ? ocorrenciaService.findByIdSolicitacaoServico(idSolicitacaoServico) : null);
    	
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	stringBuilder.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: auto;'>");
    	stringBuilder.append("<tr>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.codigoocorrencia"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.informacaoocorrencia"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.categoria"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.origem"));
    	stringBuilder.append("</td>");   
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.tempoGasto"));
    	stringBuilder.append("</td>");
    	if (!registraOcorrenciaPortal){
    		stringBuilder.append("<td class=''>");
    		stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.emailPSolicitante"));
    		stringBuilder.append("</td>");
    	}else{    		
    		stringBuilder.append("<td class=''>");
    		stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.emailPResponsavel"));
    		stringBuilder.append("</td>");
    	}
    	stringBuilder.append("</tr>");
		
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
				
		if (col != null && col.size() > 0) {			
			JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
            AprovacaoSolicitacaoServicoService aprovacaoService = (AprovacaoSolicitacaoServicoService) ServiceLocator.getInstance().getService(AprovacaoSolicitacaoServicoService.class, null);
            EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

            for (Iterator it = col.iterator(); it.hasNext(); ) {
				OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = (OcorrenciaSolicitacaoDTO) it.next();				
				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getOcorrencia() );
		
				if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) {
					JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
					justificativaDto.setIdJustificativa(ocorrenciaSolicitacaoAux.getIdJustificativa() );
					justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
					if (justificativaDto != null) 
						ocorrencia += "<br/>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa() + "<br/>";
				}
				
				if (ocorrenciaSolicitacaoAux.getComplementoJustificativa() != null) 
					ocorrencia += "<br/>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
					+ ": <b>" + ocorrenciaSolicitacaoAux.getComplementoJustificativa() + "<br/>";
		
				String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDadosSolicitacao() );
		
				SolicitacaoServicoDTO solicitacaoDto = null;
				if (dadosSolicitacao.length() > 0) {					
					try {
						solicitacaoDto = new Gson().fromJson(dadosSolicitacao,SolicitacaoServicoDTO.class);
						
						solicitacaoDto.setDataHoraSolicitacaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solicitacaoDto.getDataHoraSolicitacao(), language));
						solicitacaoDto.setDataHoraLimiteStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solicitacaoDto.getDataHoraLimite(), language));
						
						if (solicitacaoDto != null)
							dadosSolicitacao = solicitacaoDto.getDadosStr();
					} catch (Exception e) {
						dadosSolicitacao = "";
					}
				}
				
				String descricao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDescricao() ) ;
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getInformacoesContato() );
		
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
				if (solicitacaoDto != null && solicitacaoDto.getIdUltimaAprovacao() != null && ocorrenciaSolicitacaoAux.getIdItemTrabalho() != null) {
				    AprovacaoSolicitacaoServicoDTO aprovacaoDto = new AprovacaoSolicitacaoServicoDTO();
				    aprovacaoDto.setIdAprovacaoSolicitacaoServico(solicitacaoDto.getIdUltimaAprovacao());
				    aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) aprovacaoService.restore(aprovacaoDto);
				    if (aprovacaoDto.getIdTarefa() != null && aprovacaoDto.getIdTarefa().intValue() == ocorrenciaSolicitacaoAux.getIdItemTrabalho().intValue()) {
				        EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(aprovacaoDto.getIdResponsavel());
				        if (empregadoDto != null)
	                        aprovacao += UtilI18N.internacionaliza(request, "citcorpore.comum.aprovador") + ": " + empregadoDto.getNome() + "<br/>";
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
		                        aprovacao += "<br/>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa();
				        }
		                if (aprovacaoDto.getComplementoJustificativa() != null && aprovacaoDto.getComplementoJustificativa().trim().length() > 0) {
		                    aprovacao += "<br/>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
		                    + ": " + aprovacaoDto.getComplementoJustificativa();
		                }
                        if (aprovacaoDto.getObservacoes() != null && aprovacaoDto.getObservacoes().trim().length() > 0) {
                            aprovacao += "<br/>" + UtilI18N.internacionaliza(request, "citcorpore.comum.observacoes") 
                            + ": " + StringEscapeUtils.unescapeJavaScript(aprovacaoDto.getObservacoes());
                        }
				    }
				}
				
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br/>");
				descricao = descricao.replaceAll("\n", "<br/>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br/>");
				dadosSolicitacao = dadosSolicitacao.replaceAll("\n", "<br/>");		
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", "") );
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", "") );				
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", "") );
				
				stringBuilder.append("<tr>");
				stringBuilder.append("<td rowspan='4'>");
				stringBuilder.append("<b>" + ocorrenciaSolicitacaoAux.getIdOcorrencia() + "</b>");
				stringBuilder.append("</td>");
				stringBuilder.append("<td >");
				stringBuilder.append("<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,ocorrenciaSolicitacaoAux.getDataregistro(), language) + " - " + ocorrenciaSolicitacaoAux.getHoraregistro());
	        	String strRegPor = ocorrenciaSolicitacaoAux.getRegistradopor();
	        	try{
	        		if (ocorrenciaSolicitacaoAux.getRegistradopor() != null && !ocorrenciaSolicitacaoAux.getRegistradopor().trim().equalsIgnoreCase("Automático")){
		        		UsuarioDTO usuarioDto = usuarioService.restoreByLogin(ocorrenciaSolicitacaoAux.getRegistradopor());
		        		if (usuarioDto != null){
		        			EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
		        			strRegPor = strRegPor + " - " + empregadoDto.getNome();
		        		}
	        		}
	        	}catch(Exception e){}
	        	if (ocorrenciaSolicitacaoAux.getRegistradopor() != null)
	        		stringBuilder.append( " </b> - " + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <br/><b>" + strRegPor + "</b>");
	        	stringBuilder.append("</td>");
	        	
	        	// Categoria Ocorrência
	        	stringBuilder.append("<td >");
	        	if (ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia() != null && ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia() != 0) {
	        		categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia() );
	        		categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
	        		stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <br/><b>" + categoriaOcorrenciaDTO.getNome() + "</b>");
	        	} else {	        		
	        		stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <br/>");
	        	}				
	        	stringBuilder.append("</td>");
				
	        	// Origem Ocorrência
	        	stringBuilder.append( "<td >");
				if (ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia() != null && ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia() );
					origemOcorrenciaDTO = (OrigemOcorrenciaDTO)	origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
					stringBuilder.append( UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <br/><b>" + origemOcorrenciaDTO.getNome() + "</b>");
				} else {
					stringBuilder.append( UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <br/>");
				}
				stringBuilder.append("</td>");
				
				stringBuilder.append("<td >");
				stringBuilder.append( UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.tempoGasto") + ": <br/><b>" + (ocorrenciaSolicitacaoAux.getTempoGasto() != null ? "" 
				+ ocorrenciaSolicitacaoAux.getTempoGasto() + " min" : "--") + "</b>");
				stringBuilder.append("</td>");			
				
				if(!registraOcorrenciaPortal){
					stringBuilder.append("<td >");
					stringBuilder.append( UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.emailPSolicitante") + ": <br/>");
					if ((ocorrenciaSolicitacaoAux.getNotificarSolicitante()!=null)&&(ocorrenciaSolicitacaoAux.getNotificarSolicitante().equalsIgnoreCase("S"))){
						stringBuilder.append("<b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.enviado") + "</b>");
					} else {
						stringBuilder.append("<b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.naoenviado") + "</b>");
					}
					stringBuilder.append("</td>");			
				}else{					
					stringBuilder.append("<td >");
					stringBuilder.append( UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.emailPResponsavel") + ": <br/>");
					if ((ocorrenciaSolicitacaoAux.getNotificarResponsavel()!=null)&&(ocorrenciaSolicitacaoAux.getNotificarResponsavel().equalsIgnoreCase("S"))){
						stringBuilder.append("<b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.enviado") + "</b>");
					} else {
						stringBuilder.append("<b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.naoenviado") + "</b>");
					}
					stringBuilder.append("</td>");
				}
				
				
				stringBuilder.append("</tr>");
		
				if (dadosSolicitacao == null || dadosSolicitacao.trim().equalsIgnoreCase("") ) {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='5';>");
					stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <p class='ocorrenciaSolicitacaoTextoLongo'>" +  StringEscapeUtils.unescapeJavaScript(descricao) + "</p>");
					stringBuilder.append("</td>");		
					stringBuilder.append("</tr>");
				} else {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='5';>");
    				
					stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <p class='ocorrenciaSolicitacaoTextoLongo'>" + StringEscapeUtils.unescapeJavaScript(descricao) + "</p>");

                    if (dadosSolicitacao != null && !dadosSolicitacao.trim().equalsIgnoreCase("") ) {
                    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.dadosolicitacao") + ": <b><br/>" + StringEscapeUtils.unescapeJavaScript(dadosSolicitacao) + "<br/><br/></b>");
    				}
    				
                    stringBuilder.append("</td>");
                    stringBuilder.append("</tr>");
				}
			
			
				if (ocorrencia.length() > 0) {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='5';>");
					stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.ocorrencia") + ": <p class='ocorrenciaSolicitacaoTextoLongo'>" +  StringEscapeUtils.unescapeJavaScript(ocorrencia) + "</p>");
					if (aprovacao.length() > 0)
						stringBuilder.append("<b>" + aprovacao + "<br/><br/></b>");
						stringBuilder.append("</td>");		
						stringBuilder.append("</tr>");
				}else{
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='5' style='word-wrap: break-word;overflow:hidden;' >");
					stringBuilder.append("&nbsp;");
					stringBuilder.append("</td>");       
					stringBuilder.append("</tr>");
				}

                if (informacoesContato == null || informacoesContato.trim().equalsIgnoreCase("") ) {
                	stringBuilder.append("<tr>");
                	stringBuilder.append("<td colspan='5' style='word-wrap: break-word;overflow:hidden;' >");
        			if (ocorrenciaSolicitacaoAux.getInformacoesContato() != null && ocorrenciaSolicitacaoAux.getInformacoesContato().length() > 0) 
        				stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br/><b>" + StringEscapeUtils.unescapeJavaScript(ocorrenciaSolicitacaoAux.getInformacoesContato()) + "<br/><br/></b>");
        			else
        				stringBuilder.append("&nbsp;");
        				stringBuilder.append("</td>");
        				stringBuilder.append("</tr>");
				} else {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='5' style='word-wrap: break-word;overflow:hidden;' >");
        			if (informacoesContato.length() > 0) 
        				stringBuilder.append( UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br/><b>" + StringEscapeUtils.unescapeJavaScript(informacoesContato) + "<br/><br/></b>");
        			else
        				stringBuilder.append("&nbsp;");
        				stringBuilder.append("</td>");
        				stringBuilder.append("</tr>");
				}
			}
		} else {
			stringBuilder.append("<table width='100%' class='dynamicTable table table-striped table-bordered table-condensed dataTable' >");
			stringBuilder.append("<tr>");
			stringBuilder.append("<td colspan='5' >");
			stringBuilder.append("<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoinformacao") + "</b>");
			stringBuilder.append("</td>");		
			stringBuilder.append("</tr>");	    
		}
		stringBuilder.append( "</table>");	
		document.getElementById("divRelacaoOcorrencias").setInnerHTML(stringBuilder.toString());
    }
    
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OcorrenciaSolicitacaoDTO ocorrencia = (OcorrenciaSolicitacaoDTO) document.getBean();
		ocorrencia = (OcorrenciaSolicitacaoDTO) getService().restore(ocorrencia);
		HTMLForm form = document.getForm("formOcorrenciaSolicitacao");
		form.clear();
		form.setValues(ocorrencia);
		this.configuraCheckNotificaSolicitante(document);
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
    
	public void enviaEmailSolicitante(Integer idModeloEmail, OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO, HttpServletRequest request) throws Exception {
		MensagemEmail mensagem = null;

		if ((idModeloEmail == null) || (idModeloEmail.intValue() == 0)) {
			return;
		}

		// Buscando os dados da Solicitação de Serviço
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		SolicitacaoServicoDTO solicitacaoServicoDTO = solicitacaoServicoService.restoreAll(ocorrenciaSolicitacaoDTO.getIdSolicitacaoServico());
		if (solicitacaoServicoDTO == null) {
			return;
		}

		// Buscando os dados do Solicitante
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(solicitacaoServicoDTO.getIdSolicitante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
		if (empregadoDto == null) {
			return;
		}

		// Alimentando os parâmetros possíveis
		Map<String, String> mapParametros = new HashMap<String, String>();
		mapParametros.put("NOMECONTATO",solicitacaoServicoDTO.getNomecontato());
		mapParametros.put("REGISTRADOPOR",ocorrenciaSolicitacaoDTO.getRegistradopor());
		mapParametros.put("DESCRICAO",ocorrenciaSolicitacaoDTO.getDescricao());
		mapParametros.put("OCORRENCIA",ocorrenciaSolicitacaoDTO.getOcorrencia());
		mapParametros.put("IDSOLICITACAOSERVICO",solicitacaoServicoDTO.getIdSolicitacaoServico().toString());
		mapParametros.put("DEMANDA",solicitacaoServicoDTO.getDemanda());
		mapParametros.put("SERVICO",solicitacaoServicoDTO.getServico());
		mapParametros.put("INFORMACOESCONTATO",ocorrenciaSolicitacaoDTO.getInformacoesContato());
		mapParametros.put("CATEGORIA",ocorrenciaSolicitacaoDTO.getCategoria());
		mapParametros.put("ORIGEM",ocorrenciaSolicitacaoDTO.getOrigem());
		mapParametros.put("TEMPOGASTO",ocorrenciaSolicitacaoDTO.getTempoGasto().toString()+" "+UtilI18N.internacionaliza(request, "citcorpore.texto.tempo.minutoS"));

		
		String email = "";
		try {
			email = empregadoDto.getEmail();
		} catch (Exception e) {
			return;
		}

		if (email == null || email.isEmpty() || email.equalsIgnoreCase("")) {
			return;
		}

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		if (remetente == null) {
			return;
		}

		try {
			try {
				mensagem = new MensagemEmail(idModeloEmail,mapParametros);
				mensagem.envia(email, remetente, remetente);
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * @author euler.ramos Obtém o id do modelo de email referente à notificação
	 *         de ocorrências ao solicitante
	 * @return
	 */
	private Integer obterIdModeloEmailNotificacaoSolicitante() {
		Integer idModeloEmail = 0;
		try {
			String idModeloEmailNotificarSolicitante = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_NOTIFICAR_SOLICITANTE, "0");
			idModeloEmail = Integer.parseInt(idModeloEmailNotificarSolicitante.trim());
		} catch (NumberFormatException e) {
			System.out.println("Modelo de e-mail de notificação de ocorrências não definido.");
			idModeloEmail = 0;
		}
		return idModeloEmail;
	}
	
	private void configuraCheckNotificaSolicitante(DocumentHTML document) {
		try {
			if (this.obterIdModeloEmailNotificacaoSolicitante() > 0) {
				document.getCheckboxById("checkNotificarSolicitante").setChecked(true);
				document.getCheckboxById("checkNotificarSolicitante").setDisabled(false);
			} else {
				document.getCheckboxById("checkNotificarSolicitante").setChecked(false);
				document.getCheckboxById("checkNotificarSolicitante").setDisabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo responsavel por obter o paramentro de envio de email pelo portal obter o parametro de modelo de envio de email da ocorrencias
	 * realizadas no portal.
	 * 
	 * @return
	 * @author Ezequiel
	 */
	private Integer obterIDModeloEmailNotificacaoResponsavel(){
		
		try{
			
			return Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_EMAIL_REGISTRO_OCORRENCIA_PELO_PORTAL, "0"));			
		}catch(NumberFormatException e){
			
			return null;
		}
	}
	
	
	/**
	 * Metodo responsavel por enivar o email para o responsavel pela solicitação
	 * 
	 * @param idModeloEmail
	 * @param ocorrenciaSolicitacaoDTO
	 * @param request
	 * @throws Exception
	 * 
	 * @author Ezequiel
	 */
	private void enviaEmailResponsavel(Integer idModeloEmail, OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO, HttpServletRequest request) throws Exception {
		
		MensagemEmail mensagem = null;

		if ((idModeloEmail == null) || (idModeloEmail.intValue() == 0)) {
			System.out.println("ID Modelo de Email não configurado");			
			return;
		}

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		
		SolicitacaoServicoDTO solicitacaoServicoDTO = solicitacaoServicoService.restoreAll(ocorrenciaSolicitacaoDTO.getIdSolicitacaoServico());
		
		if (solicitacaoServicoDTO == null) {
			System.out.println("Solicitação não existe");			
			return;
		}	

		Collection<String> emails = obteDestinatariosOcorrencia(ocorrenciaSolicitacaoDTO);
		
		Map<String, String> mapParametros = new HashMap<String, String>();
		
		mapParametros.put("IDSOLICITACAOSERVICO", String.valueOf(ocorrenciaSolicitacaoDTO.getIdSolicitacaoServico()));
		
		mapParametros.put("DATAHORA",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
		
		mapParametros.put("REGISTRADOPOR",ocorrenciaSolicitacaoDTO.getRegistradopor());	
		
		mapParametros.put("CATEGORIA",ocorrenciaSolicitacaoDTO.getCategoria());
		
		mapParametros.put("ORIGEM",ocorrenciaSolicitacaoDTO.getOrigem());
		
		mapParametros.put("OCORRENCIAS",ocorrenciaSolicitacaoDTO.getOcorrencia());
		
		ocorrenciaSolicitacaoDTO.setInformacoesContato(ocorrenciaSolicitacaoDTO.getInformacoesContato().replace("\n", "<br>"));
		
		ocorrenciaSolicitacaoDTO.setInformacoesContato("<br>" + ocorrenciaSolicitacaoDTO.getInformacoesContato());
		
		mapParametros.put("INFORMACOESCONTATO",ocorrenciaSolicitacaoDTO.getInformacoesContato());		
		
		mapParametros.put("DESCRICAO",ocorrenciaSolicitacaoDTO.getDescricao());

				
		try {			
			
			for (String email : emails){
			
				String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
				
				if (remetente == null) {
					System.out.println("Remetente não definido.");
					return;
				}		
				
				mensagem = new MensagemEmail(idModeloEmail,mapParametros);
				
				mensagem.envia(email, null, remetente);
			}
			
		} catch (Exception e) {
			
			System.out.println("Erro ao envia o email: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo responsavel por obter os emails referentes aos destinatarios de uma ocorrencia, implementação referente a inicitiva 481.
	 * 
	 * @param ocorrencia
	 * @throws ServiceException
	 * @throws Exception
	 * @return Collection
	 * 
	 * @author Ezequiel Bispo Nunes
	 */
	private Collection<String> obteDestinatariosOcorrencia(final OcorrenciaSolicitacaoDTO ocorrencia) throws ServiceException, Exception{
		
		OcorrenciaService ocorrenciaService = (OcorrenciaService) ServiceLocator.getInstance().getService(OcorrenciaService.class, null);
		
		GrupoService grupoEmailService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		
		DadosEmailRegOcorrenciaDTO dadosEmail = ocorrenciaService.obterDadosResponsavelEmailRegOcorrencia(ocorrencia.getIdSolicitacaoServico());		
					
		Collection<String> destinatarios = new ArrayList<String>();
		
		if (dadosEmail != null){	
			
			/*
			 * Verificar se a solicitação possui "Responsável", se sim, enviar e-mail ao Responsável pela solicitação;
			 */
			if (dadosEmail.getIdResponsavelAtual() != null){
			
				destinatarios.add(dadosEmail.getEmail());
			}
			
			/*
			 * Verificar se a solicitação foi direcionada para um grupo, se sim, enviar e-mail para o grupo que foi vinculado na solicitação;
			 *
			 */
			if (dadosEmail.getIdGrupoAtual() != null){	
				
				Collection<String> emailsGrupo = grupoEmailService.listarEmailsPorGrupo(dadosEmail.getIdGrupoAtual());
				
				destinatarios.addAll(emailsGrupo);	
			}			
		}	
		
		
		if (destinatarios == null || destinatarios.isEmpty()){
			
			ServicoContratoDTO servicoContratoDTO = servicoContratoService.findByIdSolicitacaoServico(ocorrencia.getIdSolicitacaoServico());			
			
			/*
			 *  O sistema deverá verificar se para o serviço solicitado possui um "Grupo para escalação do atendimento 1.o Nível" pa-rametrizado, se sim, enviar e-mail ao referido grupo pela solicitação
			 */			
			if (servicoContratoDTO.getIdGrupoNivel1() != null){
		
				Collection<String> emailNivel1 = grupoEmailService.listarEmailsPorGrupo(servicoContratoDTO.getIdGrupoNivel1());
				
				destinatarios.clear();
				
				destinatarios.addAll(emailNivel1);				
			}
			
			/*
			 *  O sistema deverá verificar se para o serviço solicitado possui um "Grupo Executor" parametrizado, se sim, enviar e-mail ao referido grupo pela solicitação
			 */
			if (servicoContratoDTO.getIdGrupoNivel1() == null && servicoContratoDTO.getIdGrupoExecutor() != null){
				
				Collection<String> emailGrupoExecutor = grupoEmailService.listarEmailsPorGrupo(servicoContratoDTO.getIdGrupoExecutor());
				
				destinatarios.clear();
				
				destinatarios.addAll(emailGrupoExecutor);				
			}	
			
			/*
			 * Em último, caso a condição 5 não seja satisfeita, o sistema deverá verificar o parâmetro 9 "ID Grupo Nível 1" e enviar a notificação ao e-mail vinculado à esse grupo definido por parâmetro.
			 */
			if (servicoContratoDTO.getIdGrupoNivel1() == null && servicoContratoDTO.getIdGrupoExecutor() == null){
				
				Integer idGrupoPadraoNivel1 = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, "0"));				
				
				Collection<String> emailPadraoNivel1 = grupoEmailService.listarEmailsPorGrupo(idGrupoPadraoNivel1);
				
				destinatarios.clear();
				
				destinatarios.addAll(emailPadraoNivel1);				
			}
			
		}
		
		return destinatarios;
		
	}
	
	/**
	 * Metodo responsavel por desabilitar os ocultar o campo "CheckNotificarSolicitante" que não faz parte dessa funcionalidade, e deixar os campos registradopor e tempoGasto
	 * readOnly.
	 * <p>
	 * O campo tempoGasto tera o valor default igual a ZERO.
	 * <p>
	 * Metodo possui as chamdas dos demais metodos de regra de apresentação
	 * <p>
	 * No fim é executada uma função javascript para quebra as linhas dentro da textarea.
	 * 
	 * @param document
	 * @param resgistrarOcorrencia
	 * @param solicitacaoServicoDTO
	 * @throws Exception
	 * 
	 * @author Ezequiel
	 */
	private void desabilitarCamposRegistrarOcorrencia(DocumentHTML document, Boolean resgistrarOcorrencia,	SolicitacaoServicoDTO solicitacaoServicoDTO, HttpServletRequest request) throws Exception {
		
		document.getElementById("divCheckNotificarResponsavel").setVisible(Boolean.FALSE);
		
		if (resgistrarOcorrencia){
    		
    		document.getElementById("divCheckNotificarSolicitante").setVisible(Boolean.FALSE);
    		
    		document.getElementById("divCheckNotificarResponsavel").setVisible(Boolean.TRUE);
    		
    		document.getElementById("registradopor").setReadonly(Boolean.TRUE);
    		
    		document.getElementById("tempoGasto").setValue("0");
    		
    		document.getElementById("tempoGasto").setReadonly(Boolean.TRUE);    		
    		
    		preencherCampoInfoContato(document, solicitacaoServicoDTO);    	  
    		
    		disableCamposCategoria(document);
    		
    		disableCamposOrigem(document);
    		
    		/**
    		 * Cristian: solicitação 165508
    		 * O método abaixo não funciona no IE. Então, o que eu fiz foi comentar a linha abaixo e chamar este método antes de abrir a modal.
    		 */
    		//document.executeScript("parent.escapeBrTextArea();");
    		document.executeScript("escapeBrTextArea();");
    	}
		
		validaParametrosOcorrencia(document);
	}

	/**
	 * Metodo responsavel por popular na tela a textarea de informações do contato, valores obtidos através do objeto solicitacaoServicoDTO
	 * 
	 * @param document
	 * @param solicitacaoServicoDTO
	 * @throws Exception
	 * @author Ezequiel
	 */
	private void preencherCampoInfoContato(DocumentHTML document,SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
		
		StringBuilder informacoesContato = new StringBuilder();
		
		informacoesContato.append("Nome: ").append(solicitacaoServicoDTO.getSolicitante());
		
		informacoesContato.append("<br>");
		
		informacoesContato.append("Telefone: ").append(solicitacaoServicoDTO.getTelefonecontato());
		
		informacoesContato.append("<br>");
		
		informacoesContato.append("E-mail: ").append(solicitacaoServicoDTO.getEmailResponsavel());

		document.getElementById("informacoesContato").setValue(informacoesContato.toString());
	}
	
	/**
	 * Metodo responsavel por desabilitar os campos vinculados ao campo "CATEGORIA" deixando com um valor default obtido por parametro do sistema ID_CATEGORIA_REGISTRA_OCORRENCIA_PORTAL.
	 * 
	 * @param document
	 * @throws Exception
	 * @author Ezequiel
	 */
	private void disableCamposCategoria(DocumentHTML document) throws Exception{
		
		CategoriaOcorrenciaService categoriaService = (CategoriaOcorrenciaService) ServiceLocator.getInstance().getService(CategoriaOcorrenciaService.class, null);
				
		Integer idCategoria = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_CATEGORIA_REGISTRA_OCORRENCIA_PORTAL, "0"));
				
		if (idCategoria != null && idCategoria > 0){
		
			CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaService.restoreAll(idCategoria);
		
			if (categoriaOcorrenciaDTO != null && categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != null){
			
				document.getElementById("nomeCategoriaOcorrencia").setValue(categoriaOcorrenciaDTO.getNome());
				
				document.getElementById("idCategoriaOcorrencia").setValue(String.valueOf(categoriaOcorrenciaDTO.getIdCategoriaOcorrencia()));
				
				document.getElementById("nomeCategoriaOcorrencia").setReadonly(Boolean.TRUE);		
			
				document.executeScript("disabledBtnsCategoria();");
			}
		}
	}
	
	
	/**
	 * Metodo responsavel por desabilitar os campos vinculados ao campo "ORIGEM" deixando com um valor default, obtido por parametro do sistema ID_ORIGEM_REGISTRA_OCORRENCIA_PORTAL.
	 *
	 * @param document
	 * @throws Exception
	 * @author Ezequiel
	 */
	private void disableCamposOrigem(DocumentHTML document) throws Exception{
		
		OrigemOcorrenciaService origemOcorrenciaService = (OrigemOcorrenciaService) ServiceLocator.getInstance().getService(OrigemOcorrenciaService.class, null);		
		
		Integer idOrigem = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_ORIGEM_REGISTRA_OCORRENCIA_PORTAL, "0"));
		
		if (idOrigem != null && idOrigem > 0){
			
			OrigemOcorrenciaDTO origemOcorrenciaDTO = origemOcorrenciaService.restoreAll(idOrigem);
			
			if (origemOcorrenciaDTO != null && origemOcorrenciaDTO.getIdOrigemOcorrencia() != null){
			
				document.getElementById("nomeOrigemOcorrencia").setReadonly(Boolean.TRUE);
				
				document.getElementById("nomeOrigemOcorrencia").setValue(origemOcorrenciaDTO.getNome());
				
				document.getElementById("idOrigemOcorrencia").setValue(String.valueOf(origemOcorrenciaDTO.getIdOrigemOcorrencia()));
				
				document.executeScript("disabledBtnsOrigem();");
				
			}
			
		}
	}
	
	/**
	 * Valida os parâmetros 262 e 263
	 * @param document
	 * @author thyen.chang
	 * @throws Exception 
	 */
	public void validaParametrosOcorrencia(DocumentHTML document) throws Exception{
		String idCategoria = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_CATEGORIA_REGISTRA_OCORRENCIA_PORTAL, "-1");
		String idOrigem = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_ORIGEM_REGISTRA_OCORRENCIA_PORTAL, "-1");
		if(!idCategoria.equals("-1")){
			CategoriaOcorrenciaService categoriaOcorrenciaService = (CategoriaOcorrenciaService) ServiceLocator.getInstance().getService(CategoriaOcorrenciaService.class, null);
			CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaService.restoreAll(Integer.parseInt(idCategoria));
			if(categoriaOcorrenciaDTO != null){
				document.executeScript("preencheCampoCategoria('" + categoriaOcorrenciaDTO.getNome() + "', " + idCategoria + ");");
			}

		}
		if(!idOrigem.equals("-1"));{
			OrigemOcorrenciaService origemOcorrenciaService = (OrigemOcorrenciaService) ServiceLocator.getInstance().getService(OrigemOcorrenciaService.class, null);
			OrigemOcorrenciaDTO origemOcorrenciaDTO = origemOcorrenciaService.restoreAll(Integer.parseInt(idOrigem));
			if(origemOcorrenciaDTO != null){
				document.executeScript("preencheCampoOrigem('" + origemOcorrenciaDTO.getNome() + "', " + idOrigem + ");");
			}
		}
	}
	
}