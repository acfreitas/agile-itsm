package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.HistoricoAcaoCurriculoDTO;
import br.com.centralit.citcorpore.bean.JustificativaAcaoCurriculoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.HistoricoAcaoCurriculoService;
import br.com.centralit.citcorpore.negocio.JustificativaAcaoCurriculoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings("rawtypes")
public class HistoricoAcaoCurriculo extends AjaxFormAction {
	

	public Class getBeanClass() {
	return HistoricoAcaoCurriculoDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
//    	HistoricoAcaoCurriculoDTO historicoAcaoCurriculoDTO = (HistoricoAcaoCurriculoDTO) document.getBean();
    	CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
    	JustificativaAcaoCurriculoService justificativaService = (JustificativaAcaoCurriculoService) ServiceLocator.getInstance().getService(JustificativaAcaoCurriculoService.class, null);
    	
    	HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("idJustificativaAcaoCurriculo");
    	idJustificativa.removeAllOptions();
    	idJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection listaDeJustificativas = justificativaService.list();
		if (listaDeJustificativas != null) {
			idJustificativa.addOptions(listaDeJustificativas, "idJustificativaAcaoCurriculo", "nomeJustificativaAcaoCurriculo", null);
		}
    	
    	String idCurriculo = request.getParameter("idCurriculoPesquisa");
    	String tela = request.getParameter("tela");
    	String indiceTriagem = request.getParameter("rowIndexTriagem");
    	document.getElementById("indiceTriagem").setValue(indiceTriagem);
    	document.getElementById("tela").setValue(tela);
    	
    	StringBuilder stringBuffer = new StringBuilder();
    	/*String foto = curriculoService.retornarCaminhoFoto(Integer.parseInt(idCurriculo));*/
    	if(idCurriculo != null){
    		CurriculoDTO curriculoDTO = new CurriculoDTO();
    		
    		curriculoDTO.setIdCurriculo(Integer.parseInt(idCurriculo));
    		curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
    		
    		if(curriculoDTO != null){
    			stringBuffer.append("<div class='row-fluid'>");
    				stringBuffer.append("<div class='span12'>");
	    				stringBuffer.append("<h3>"+curriculoDTO.getNome()+"</h3>");
    				stringBuffer.append("</div>");
	    			stringBuffer.append("<div class='span12 '>");
	    			/*Para mostrar a foto, retire o comentario da linha */
    					/*stringBuffer.append("<img class='media-object pull-right thumb' style='width: 150px' src='"+foto+"' alt='Logo'>");*/
	    			stringBuffer.append("<div class='span6'>CPF: "+curriculoDTO.getCpfFormatado()+"</div>");
    				stringBuffer.append("</div>");	
    				if(curriculoDTO.getListaNegra()!= null && curriculoDTO.getListaNegra().equalsIgnoreCase("S")){
    				stringBuffer.append("<div class='span8'>");
    					stringBuffer.append("<div class='alert alert-warning'><p>ATENÇÃO: O currículo selecionado consta na <b>lista Negra</b>.</p></div>");
    				stringBuffer.append("</div>");
    				}
    			stringBuffer.append("</div>");
    			document.getElementById("informacoesCandidato").setInnerHTML(stringBuffer.toString());
    		
    		if(curriculoDTO.getListaNegra() != null && curriculoDTO.getListaNegra().equalsIgnoreCase("S")){
    			document.getElementById("divRetirarListaNegra").setVisible(true);
    			document.getElementById("divInserirListaNegra").setVisible(false);
    			
    		}else if(curriculoDTO.getListaNegra() == null){
    			document.getElementById("divInserirListaNegra").setVisible(true);
    			document.getElementById("divRetirarListaNegra").setVisible(false);
    			
    		}else{
    			document.getElementById("divInserirListaNegra").setVisible(true);
    			document.getElementById("divRetirarListaNegra").setVisible(false);
    			
    		}
    		this.montarListaHistoricoAcoesCurriculo(document, request, response, curriculoDTO);
    		}
    	}
    	

    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		HistoricoAcaoCurriculoDTO historicoAcaoCurriculoDTO = (HistoricoAcaoCurriculoDTO) document.getBean();
		HistoricoAcaoCurriculoService historicoAcaoCurriculoService = (HistoricoAcaoCurriculoService) ServiceLocator.getInstance().getService(HistoricoAcaoCurriculoService.class, null);
		
		if(historicoAcaoCurriculoDTO.getIdCurriculo() != null){
			historicoAcaoCurriculoDTO.setIdUsuario(usrDto.getIdUsuario());
			historicoAcaoCurriculoService.create(historicoAcaoCurriculoDTO);
			document.alert("Alteração realizada com sucesso!");
			document.executeScript("window.location.reload()");
			//remove a linha da grid de triagem caso coloque o candidato na lista negra
			if(historicoAcaoCurriculoDTO.getTela() != null){
				if(historicoAcaoCurriculoDTO.getTela().equalsIgnoreCase("triagemRequisicaoPessoal")){
					document.executeScript("parent.atualizarGridCurriculoListaNegra('"+historicoAcaoCurriculoDTO.getIndiceTriagem()+"')");
				}
			}
		}else{
			document.alert("Curriculo não informado!");
		}
		this.load(document, request, response);
		document.executeScript("ocultaModal();");
    }
    
    @SuppressWarnings("unchecked")
	public void montarListaHistoricoAcoesCurriculo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, CurriculoDTO curriculoDTO) throws Exception{
		HistoricoAcaoCurriculoService historicoAcaoCurriculoService = (HistoricoAcaoCurriculoService) ServiceLocator.getInstance().getService(HistoricoAcaoCurriculoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
    	Collection<HistoricoAcaoCurriculoDTO> colListaHistoricoAcoesCurriculo =  historicoAcaoCurriculoService.listByIdCurriculo(curriculoDTO.getIdCurriculo());
     	JustificativaAcaoCurriculoService justificativaService = (JustificativaAcaoCurriculoService) ServiceLocator.getInstance().getService(JustificativaAcaoCurriculoService.class, null);
    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	JustificativaAcaoCurriculoDTO justificativaAcaoCurriculoDTO = new JustificativaAcaoCurriculoDTO();
    	StringBuilder stringBuffer = new StringBuilder();
    		if(colListaHistoricoAcoesCurriculo != null && colListaHistoricoAcoesCurriculo.size() >0){
    			
    			for (HistoricoAcaoCurriculoDTO historico : colListaHistoricoAcoesCurriculo) {
					usuarioDTO.setIdUsuario(historico.getIdUsuario());
					justificativaAcaoCurriculoDTO.setIdJustificativaAcaoCurriculo(historico.getIdJustificativaAcaoCurriculo());
					if(usuarioDTO.getIdUsuario() != null){
						usuarioDTO = (UsuarioDTO) usuarioService.restore(usuarioDTO);
					}
					if(justificativaAcaoCurriculoDTO.getIdJustificativaAcaoCurriculo() != null){
						justificativaAcaoCurriculoDTO = (JustificativaAcaoCurriculoDTO) justificativaService.restore(justificativaAcaoCurriculoDTO);
					}
			
					stringBuffer.append("<li class='double'>");
						stringBuffer.append("<span class='glyphicons activity-icon history'><i></i></span>");
								stringBuffer.append("<span class='ellipsis'>");
								stringBuffer.append("<span class='meta glyphicons calendar single'><i></i>"+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, historico.getDataHora(), WebUtil.getLanguage(request))+"");
								if(historico.getAcao().equalsIgnoreCase("S")){
										stringBuffer.append(" - O currículo foi <b>inserido</b> na Lista Negra Por <a href=''>"+UtilStrings.nullToNaoDisponivel(usuarioDTO.getNomeUsuario())+"</a> ");
								}else{
										stringBuffer.append(" - O currículo foi <b>removido</b> da Lista Negra Por <a href=''>"+UtilStrings.nullToNaoDisponivel(usuarioDTO.getNomeUsuario())+"</a> ");
								}
										
										stringBuffer.append("<span class='meta glyphicons calendar asterisk single'><i></i><strong> Justificativa: </strong>"+justificativaAcaoCurriculoDTO.getNomeJustificativaAcaoCurriculo()+"</span>");
										stringBuffer.append("<span class='meta glyphicons calendar more single'><i></i>"+historico.getComplementoJustificativa()+"</span>");
								stringBuffer.append("</span>");
												stringBuffer.append("<div class='clearfix'></div>");
						stringBuffer.append("</li>");
						
					
					
			document.getElementById("divHistoricoAcoesCurriculo").setInnerHTML(stringBuffer.toString());
    		}
    	
    	}

    }


}
