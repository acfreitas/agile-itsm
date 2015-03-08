package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.DescricaoCargoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class TemplatePesquisaCurriculo extends RequisicaoPessoal {

	public String getAcao() {
		 return RequisicaoPessoalDTO.ACAO_TRIAGEM; 
	}

    public void sugereCurriculos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        String teste = (String) request.getParameter("idSolicitacaoServico");
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        String certificacao = (requisicaoPessoalDto.getChkCertificacao()!=null)?requisicaoPessoalDto.getChkCertificacao():"";
        String formacao = (requisicaoPessoalDto.getChkFormacao()!=null)?requisicaoPessoalDto.getChkFormacao():"";
        String idioma = (requisicaoPessoalDto.getChkIdioma()!=null)?requisicaoPessoalDto.getChkIdioma():"";
        
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
	            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
	            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
        }
        
        requisicaoPessoalDto.setChkCertificacao(certificacao);
        requisicaoPessoalDto.setChkFormacao(formacao);
        requisicaoPessoalDto.setChkIdioma(idioma);
        CargosDTO cargosDto = new CargosDTO();
        cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
        
        CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
        cargosDto = (CargosDTO) cargosService.restore(cargosDto);
        
        
        
        DescricaoCargoDTO descricaoCargoDto = new DescricaoCargoDTO();
        descricaoCargoDto.setIdDescricaoCargo(cargosDto.getIdDescricaoCargo());
        
        DescricaoCargoService descricaoCargoService = (DescricaoCargoService) ServiceLocator.getInstance().getService(DescricaoCargoService.class, null);
        descricaoCargoDto = (DescricaoCargoDTO) descricaoCargoService.restore(descricaoCargoDto);
        HTMLTable tblCurriculos = document.getTableById("tblCurriculos");
        tblCurriculos.deleteAllRows();
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        Collection<CurriculoDTO> curriculos = triagemRequisicaoPessoalService.sugereCurriculos(requisicaoPessoalDto);
        
        if (curriculos != null && !curriculos.isEmpty()) {
            tblCurriculos.addRowsByCollection(curriculos, 
                                                new String[] {"","","nome"}, 
                                                null, 
                                                "",
                                                new String[] {"gerarSelecaoCurriculo"}, 
                                                null, 
                                                null);       
        }
        document.executeScript("$(\"#POPUP_SUGESTAO_CURRICULOS\").dialog(\"open\");");
    }
    public void triagemManual(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        HTMLTable tblCurriculos = document.getTableById("tblCurriculosManuais");
        tblCurriculos.deleteAllRows();
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        Collection<CurriculoDTO> curriculos = triagemRequisicaoPessoalService.triagemManual();
        
        if (curriculos != null && !curriculos.isEmpty()) {
            tblCurriculos.addRowsByCollection(curriculos, 
                                                new String[] {"","","nome",""}, 
                                                null, 
                                                "",
                                                new String[] {"gerarColecao"}, 
                                                null, 
                                                null);       
        }
        document.executeScript("$(\"#POPUP_TRIAGEM_MANUAL\").dialog(\"open\");");
    }
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null){
              document.alert("Sessão expirada! Favor efetuar logon novamente!");
              return;
        }
        
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
	            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
	            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
        }
        
        requisicaoPessoalDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.setValues(requisicaoPessoalDto);
        
   } 
    public void adicionaCurriculos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        Collection<CurriculoDTO> colCurriculos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CurriculoDTO.class, "curriculos_serialize", request);
        adicionaCurriculos(document,request,colCurriculos);
     }     

    public void adicionaCurriculos(DocumentHTML document, HttpServletRequest request, Collection<CurriculoDTO> colCurriculos) throws Exception {
        if (colCurriculos != null) {
    		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
            CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        	for (CurriculoDTO curriculoDto : colCurriculos) {
        		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
        		if (curriculoDto != null) {
        			Collection<ControleGEDDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, curriculoDto.getIdCurriculo());
        			String caminhoFoto = "";
        			if (colAnexos != null && colAnexos.size() > 0) {
        				List<UploadDTO> colAnexosUploadDTO = (List<UploadDTO>) controleGedService.convertListControleGEDToUploadDTO(colAnexos);
        				caminhoFoto = colAnexosUploadDTO.get(0).getCaminhoRelativo();
        			}
        			curriculoDto.setCaminhoFoto(caminhoFoto);
        			document.executeScript("incluirCurriculo('" + br.com.citframework.util.WebUtil.serializeObject(curriculoDto, WebUtil.getLanguage(request)) + "');");
        		}
			}
        }
     }  
    
	public void adicionarCurriculosPorColecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Collection<CurriculoDTO> col) throws Exception{
		 UsuarioDTO usuario = WebUtil.getUsuario(request);
	        if (usuario == null) {
	            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
	            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	            return;
	        }
	        document.executeScript("limparDadostableCurriculo()");
	        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
	        for (CurriculoDTO curriculoDto : col) {
        		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
        		if (curriculoDto != null) {
        			   document.executeScript("incluirColecaoTable('" + br.com.citframework.util.WebUtil.serializeObject(curriculoDto, WebUtil.getLanguage(request)) + "');");
        			
        		}
			}
	     

	      /*  HTMLTable tblCurriculos = document.getTableById("tblCurriculos");
	        tblCurriculos.deleteAllRows();
	        
	        if (col != null && !col.isEmpty()) {
	        	 tblCurriculos.addRowsByCollection(col, 
	                     new String[] {"","","nome", "",""}, 
	                     null, 
	                     null,
	                     new String[] {"gerarSelecaoCurriculo"}, 
	                     null, 
	                     null); */       
	        
	}
    
}
