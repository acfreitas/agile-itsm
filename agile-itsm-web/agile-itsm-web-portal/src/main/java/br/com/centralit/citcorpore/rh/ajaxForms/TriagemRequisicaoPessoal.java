
package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.DescricaoCargoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

import com.google.gson.Gson;

public class TriagemRequisicaoPessoal extends RequisicaoPessoal {

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

        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        String certificacao = (requisicaoPessoalDto.getChkCertificacao()!=null)?requisicaoPessoalDto.getChkCertificacao():"";
        String formacao = (requisicaoPessoalDto.getChkFormacao()!=null)?requisicaoPessoalDto.getChkFormacao():"";
        String idioma = (requisicaoPessoalDto.getChkIdioma()!=null)?requisicaoPessoalDto.getChkIdioma():"";
        
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
	            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
	            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
        }
        
        requisicaoPessoalDto.setChkFormacao(formacao);
        requisicaoPessoalDto.setChkCertificacao(certificacao);
        requisicaoPessoalDto.setChkIdioma(idioma);
        
        if (requisicaoPessoalDto != null && requisicaoPessoalDto.getIdCargo() != null) {
            CargosDTO cargosDto = new CargosDTO();
            cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
            
            CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
            cargosDto = (CargosDTO) cargosService.restore(cargosDto);
            
            DescricaoCargoDTO descricaoCargoDto = new DescricaoCargoDTO();
            descricaoCargoDto.setIdDescricaoCargo(cargosDto.getIdDescricaoCargo());
            
            DescricaoCargoService descricaoCargoService = (DescricaoCargoService) ServiceLocator.getInstance().getService(DescricaoCargoService.class, null);
            descricaoCargoDto = (DescricaoCargoDTO) descricaoCargoService.restore(descricaoCargoDto);
        }
        
        HTMLTable tblCurriculos = document.getTableById("tblCurriculos");
        tblCurriculos.deleteAllRows();
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        Collection<CurriculoDTO> curriculos = triagemRequisicaoPessoalService.sugereCurriculos(requisicaoPessoalDto);
        
        if (curriculos != null && !curriculos.isEmpty()) {
            PesquisaCurriculo template = new PesquisaCurriculo();
            template.adicionarCurriculosPorColecao(document, request, response, curriculos);
        }
        
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }
    
    /**
     * Triagem manual dos currículos.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void triagemManual(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();
        
        if(requisicaoPessoalDTO != null){
        	requisicaoPessoalDTO = retirarEspacos(requisicaoPessoalDTO);
        }
        
        String idsCurTriados = request.getParameter("idsCurTriados");
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        
        //Seta a quantidade de itens que sera exibido por paginha 
        Integer itensPorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));
        
        Integer totalPaginas = 0;
        Integer paginaSelecionada = requisicaoPessoalDTO.getPaginaSelecionada();
        
        if (paginaSelecionada == null) {
        	paginaSelecionada = 1;
        }
        
        Collection<CurriculoDTO> curriculosTriados = new ArrayList<CurriculoDTO>();
        Collection<CurriculoDTO> curriculos = triagemRequisicaoPessoalService.triagemManualPorCriterios(requisicaoPessoalDTO,idsCurTriados, paginaSelecionada, itensPorPagina);
        
        if(requisicaoPessoalDTO.getPesquisa_chave() != null && !requisicaoPessoalDTO.getPesquisa_chave().equalsIgnoreCase("")){
        	Gson gson = new Gson();
        	for (CurriculoDTO curriculoDTO : curriculos) {
        		curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
        		String curriculo = gson.toJson(curriculoDTO);
        		String termo = requisicaoPessoalDTO.getPesquisa_chave().toString();
        		boolean valido = StringUtils.contains(curriculo.toLowerCase(), termo.toLowerCase());
        		if(valido){
        			curriculosTriados.add(curriculoDTO);
        		}
        	}
        }else{
        	if(curriculos != null && curriculos.size() > 0){
        		curriculosTriados.addAll(curriculos);
        	}
        }
        
        //caso o usuario optar busca por palavra chave esse laço trata a paginação
        if(requisicaoPessoalDTO.getPesquisa_chave() != null && !requisicaoPessoalDTO.getPesquisa_chave().equalsIgnoreCase("")){
        	if (curriculosTriados.size() > 0 && totalPaginas > 0) {
            	totalPaginas = (totalPaginas / itensPorPagina);
            	if(totalPaginas % itensPorPagina != 0){
            		totalPaginas = totalPaginas + 1;
            	}
            }
        }else{
        	totalPaginas = curriculoService.calculaTotalPaginas(requisicaoPessoalDTO, idsCurTriados, itensPorPagina);
        }
        
        //se a busca conter menos curriculos que o valor do parametro de paginação esse laço elimina a paginação
        if (totalPaginas > 1) 
        	paginacaoGerenciamento(totalPaginas,paginaSelecionada,request, document);
        else
        	document.executeScript("limpaPaginacao()");
        
        PesquisaCurriculo template = new PesquisaCurriculo();
        template.adicionarCurriculosPorColecao(document, request, response, curriculosTriados);
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null){
        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));  
            return;
        }
        
        List<CurriculoDTO> lstCurriculoAux = new ArrayList<CurriculoDTO>();
        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
        EntrevistaCandidatoDTO entrevistaCandidatoDTO = new EntrevistaCandidatoDTO();
        Boolean aprovado = false;
        
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
        	
            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
            if(requisicaoPessoalDto.getColTriagem() != null ){
            	for (TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO : requisicaoPessoalDto.getColTriagem()) {
            		entrevistaCandidatoDTO.setIdTriagem(triagemRequisicaoPessoalDTO.getIdTriagem());
            		aprovado = entrevistaCandidatoService.seCandidatoAprovado(triagemRequisicaoPessoalDTO);
            		if (aprovado != null && aprovado == false) {
            			CurriculoDTO curriculoDTO = new CurriculoDTO();
	            		curriculoDTO.setIdCurriculo(triagemRequisicaoPessoalDTO.getIdCurriculo());
	            		curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
						lstCurriculoAux.add(curriculoDTO);
            		}
				}
            }
            
            this.preencherComboUfs(document, request, requisicaoPessoalDto);
            this.preencherComboCidade(document, request, requisicaoPessoalDto);
            if(lstCurriculoAux != null ){
            	//Restore de curriculos já triados e adiciona na table caso tenha selecionado gravar e manter tarefa atual
            	this.adicionaCurriculos(document, request, lstCurriculoAux, "restore");
            }
        }
        
        requisicaoPessoalDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        /*
         * dando um trim na entidade tipo de contratacao, pois na base de dados postgree estar gravando com espaço
         * @thays.araujo 09/05/2014
         */
        if(requisicaoPessoalDto.getTipoContratacao()!=null){
        	  requisicaoPessoalDto.setTipoContratacao(requisicaoPessoalDto.getTipoContratacao().trim());
        }
      
        form.setValues(requisicaoPessoalDto);
    } 
    
    /**
     * Método de tratamento de coleção de triagem para não permitir que um mesmo currículo não participe de varios triagens.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void tratarColecaoTriagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        Collection<CurriculoDTO> colCurriculos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CurriculoDTO.class, "curriculos_serialize", request);
        
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        Integer idSolicitacaoServico = 0;
        
        if ((requisicaoPessoalDto!=null)&&(requisicaoPessoalDto.getIdSolicitacaoServico()!=null)){
        	idSolicitacaoServico = requisicaoPessoalDto.getIdSolicitacaoServico();
        }
        
       	if(idSolicitacaoServico == 0){
       		idSolicitacaoServico = (Integer) request.getSession().getAttribute("IdSolicitacaoServico");
       	}
       	
        //Emitir alerta quando o usuário selecionou um currículo que já participa de um processo de seleção
       	if ((colCurriculos!=null)&&(colCurriculos.size()==1)){
        	CurriculoDTO curriculoDTO = colCurriculos.iterator().next();
        	TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, null);
        	if (triagemRequisicaoPessoalService.candidatoParticipaProcessoSelecao(curriculoDTO.getIdCurriculo(),idSolicitacaoServico)){
        		document.alert(UtilI18N.internacionaliza(request, "rh.candidatoParticipaDeOutroProcessoSeletivo"));
        	}else{
        		adicionaCurriculos(document,request,colCurriculos, "pesquisa");
        	}
        }else{
        	 adicionaCurriculos(document,request,colCurriculos, "pesquisa");
        }
       	
       	//Metodo comentado abaixo, descomentar somente para teste com ele descomentado o usuario pode triar o mesmo curriculo mais de uma vez
       	//adicionaCurriculos(document,request,colCurriculos, "pesquisa");
        //Adiciona os curriculos através da Pesquisa de curriculos
       
    }     

    public void adicionaCurriculos(DocumentHTML document, HttpServletRequest request, Collection<CurriculoDTO> colCurriculos, String acao) throws Exception {
    	
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
        			//Apenas inclui o curriculo se ele não estiver na lista negra
        			if(curriculoDto.getListaNegra() != null && curriculoDto.getListaNegra().equalsIgnoreCase("N") || curriculoDto.getListaNegra() == null){
	        			if(acao.equalsIgnoreCase("pesquisa")){
	        				document.executeScript("parent.incluirCurriculo('" + br.com.citframework.util.WebUtil.serializeObject(curriculoDto, WebUtil.getLanguage(request)) + "');");
	        			}else{
	        				document.executeScript("incluirCurriculo('" + br.com.citframework.util.WebUtil.serializeObject(curriculoDto, WebUtil.getLanguage(request)) + "');");
	        			}
        			}
        		}
			}
        }
    }  
    
    /**
	 * @author Thiago.borges
	 * Geração da view dos elementos da paginação
	 * 
	 */
	public void paginacaoGerenciamento(Integer totalPaginas, Integer paginaSelecionada, HttpServletRequest request, DocumentHTML document) throws Exception {
		HTMLElement divPrincipal = document.getElementById("paginas");
		StringBuilder sb = new StringBuilder();
		final Integer adjacentes = 2;
		
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		
		sb.append(" <div id='itenPaginacaoGerenciamento' class='pagination pagination-right margin-none' > ");
		sb.append(" <ul>");
		sb.append(" <li " + (paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='paginarItens(this.value);'") + " ><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.primeiro")+"</a></li></font> ");
		sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='"+(paginaSelecionada-1)+"' onclick='paginarItens(this.value);'") + "><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.anterior")+"</a></li></font> ");
		
		if(totalPaginas <= 5) {
			for (int i = 1; i <= totalPaginas; i++) {
				if (i == paginaSelecionada) {
					sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
				} else {
					sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> " );
				}
			}
		} else {
			if (totalPaginas > 5) {
				if (paginaSelecionada < 1 + (2 * adjacentes)) {
					for (int i=1; i< 2 + (2 * adjacentes); i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						} else {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> " );
						}
					}
				} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
					for (int i = paginaSelecionada-adjacentes; i<= paginaSelecionada + adjacentes; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						} else {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						}
					}
				} else {
					for (int i = totalPaginas - (0 + (2 * adjacentes)); i <= totalPaginas; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						} else {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						}
					}
				}
			}
		}
		
		sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='"+(paginaSelecionada+1)+"' onclick='paginarItens(this.value);'") + " ><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.proximo")+"</a></li></font>");
		sb.append(" <li id='"+totalPaginas+"' value='"+totalPaginas+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.ultimo")+"</a></li></font>");
		sb.append(" </ul>");
		sb.append(" </div>");
		
		divPrincipal.setInnerHTML(sb.toString());
		
	}
    
	public RequisicaoPessoalDTO retirarEspacos(RequisicaoPessoalDTO requisicaoPessoalDTO){
		if(requisicaoPessoalDTO.getPesquisa_chave() != null && !requisicaoPessoalDTO.getPesquisa_chave().equalsIgnoreCase(""))
        	requisicaoPessoalDTO.setPesquisa_chave(requisicaoPessoalDTO.getPesquisa_chave().trim());
		
		if(requisicaoPessoalDTO.getPesquisa_formacao() != null && !requisicaoPessoalDTO.getPesquisa_formacao().equalsIgnoreCase(""))
        	requisicaoPessoalDTO.setPesquisa_formacao(requisicaoPessoalDTO.getPesquisa_formacao().trim());
		
		if(requisicaoPessoalDTO.getPesquisa_certificacao() != null && !requisicaoPessoalDTO.getPesquisa_certificacao().equalsIgnoreCase(""))
        	requisicaoPessoalDTO.setPesquisa_certificacao(requisicaoPessoalDTO.getPesquisa_certificacao().trim());

		if(requisicaoPessoalDTO.getPesquisa_idiomas() != null && !requisicaoPessoalDTO.getPesquisa_idiomas().equalsIgnoreCase(""))
        	requisicaoPessoalDTO.setPesquisa_idiomas(requisicaoPessoalDTO.getPesquisa_idiomas().trim());
		
		if(requisicaoPessoalDTO.getPesquisa_cidade() != null && !requisicaoPessoalDTO.getPesquisa_cidade().equalsIgnoreCase(""))
        	requisicaoPessoalDTO.setPesquisa_cidade(requisicaoPessoalDTO.getPesquisa_cidade().trim());
		
        return requisicaoPessoalDTO;	
	}
}
