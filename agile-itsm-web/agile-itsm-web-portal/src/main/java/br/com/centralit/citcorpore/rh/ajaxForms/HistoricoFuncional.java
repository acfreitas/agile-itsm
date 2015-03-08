package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.negocio.BlackListService;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public class HistoricoFuncional extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		if(!(isUserInGroup(request, "RH"))){
			document.executeScript("alert('Voce não tem permição para usar essa Funcionalidade. Apenas Participantes do Grupo RH');");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'"); 
			return; 
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
	}
	
	/**
	 * @author david.silva
	 * Metodo para montar tabela retorno com uma colecao de candidatos
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void montarTabelaRetorno(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
        HistoricoFuncionalDTO historicoFuncionalDto = (HistoricoFuncionalDTO) document.getBean();
        CandidatoDTO candidatoDto  = new CandidatoDTO();
        
        String chkListaNegra = request.getParameter("chkListaNegra");
        
        candidatoDto.setCpf(historicoFuncionalDto.getCpf().replaceAll("[^0-9]*",""));
        candidatoDto.setNome(historicoFuncionalDto.getNome());
       	candidatoDto.setTipo(historicoFuncionalDto.getTipo());
       	
       	if(chkListaNegra != null && chkListaNegra.equalsIgnoreCase("B")){
       		candidatoDto.setCandidatoNaListaNegra("S");
       	}
       	
        Integer itensPorPagina = 5;
        
        HTMLTable tblCandidato = document.getTableById("tblPesquisa");
		tblCandidato.deleteAllRows();
        
        Integer totalPaginas = candidatoService.calculaTotalPaginas(itensPorPagina, candidatoDto);
        Integer paginaSelecionada = historicoFuncionalDto.getPaginaSelecionada();
        
        if (paginaSelecionada == null) {
        	paginaSelecionada = 1;
        }
        
        paginacaoGerenciamento(totalPaginas,paginaSelecionada,request, document);
        
		Collection<CandidatoDTO> colCandidatos = candidatoService.recuperaColecaoCandidatos(candidatoDto, paginaSelecionada, itensPorPagina);
       
        if (colCandidatos != null && !colCandidatos.isEmpty()) {
        	colCandidatos = gerarTabelaHistorico(request, document, colCandidatos);
        }else{
        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
        }
        
		tblCandidato.addRowsByCollection(colCandidatos, new String[]{"detalhamentoTabela01","detalhamentoTabela02","detalhamentoTabela03","detalhamentoTabela04"}, null, "", new String[]{"corLinhaItemListaNegra"}, null, null); 
	
		document.executeScript("JANELA_AGUARDE_MENU.hide();	");
//		document.executeScript("limpar();");
	}
	
	/**
	 * Metodo com filtros para pesquisa
	 * @author david.silva
	 */
	private Collection<CandidatoDTO> gerarTabelaHistorico(HttpServletRequest request, DocumentHTML document, Collection<CandidatoDTO> colCandidatos) throws Exception {
		HistoricoFuncionalDTO historicoFuncionalDto = (HistoricoFuncionalDTO) document.getBean();
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();
		
		String tipoPessoa = "";
		
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
		BlackListService blackService = (BlackListService) ServiceLocator.getInstance().getService(BlackListService.class, null);
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
			
		Collection<CandidatoDTO> colRetorno = new ArrayList<CandidatoDTO>();
		
		if(colCandidatos != null){
			for(CandidatoDTO candidatoDto : colCandidatos){
				boolean isOk = blackService.isCandidatoBlackList(candidatoDto.getIdCandidato());
				
				if (isOk){
					candidatoDto.setConstaListaNegra("S");
				}else {
					candidatoDto.setConstaListaNegra("N");
				}
					
					CurriculoDTO curriculoDTO = curriculoService.findIdByCpf(candidatoDto.getCpf());
					
					List list = new ArrayList<>();
					list = (List) candidatoService.findByIdCandidatoJoinIdHistorico(candidatoDto.getIdCandidato());
					
					if(list != null && list.size() > 0){
						CandidatoDTO candidatoAux = (CandidatoDTO) list.get(0);
						candidatoDto.setIdHistoricoFuncional(candidatoAux.getIdHistoricoFuncional());
					}
					
					String url = ParametroUtil.getValor(Enumerados.ParametroSistema.URL_Sistema);
											
					if (candidatoDto.getTipo().equalsIgnoreCase("F")){
						tipoPessoa = UtilI18N.internacionaliza(request, "candidato.candidatoExterno");
					}
					if (candidatoDto.getTipo().equalsIgnoreCase("C")){
						tipoPessoa = UtilI18N.internacionaliza(request, "colaborador.colaborador");
					}
					if (candidatoDto.getTipo().equalsIgnoreCase("E")){
						tipoPessoa = UtilI18N.internacionaliza(request, "candidato.exColaborador");
					}
					if (candidatoDto.getTipo().equalsIgnoreCase("A")){
						tipoPessoa = UtilI18N.internacionaliza(request, "candidato.colaboradorAfastado");
					}
						
					String caminhoFoto = "";
					String divFoto = "";
					
					if (curriculoDTO.getIdCurriculo() != null && curriculoDTO.getIdCurriculo() > 0 ){
						caminhoFoto = curriculoService.retornarCaminhoFoto(curriculoDTO.getIdCurriculo());
					}
					
					if(caminhoFoto != "") {
						divFoto = "<div class='span 12'><img src='"+caminhoFoto+"' border=0 width='70' height='70' /></div>";
					}else{
						divFoto = "<div class='span 12' ><img src='"+ url +"/novoLayout/common/theme/images/avatar.jpg' border=0 width='70' height='70'  /></div>";
					}
					
						/** 1ª Coluna **/
						sb.append("		<div class='row-fluid'>");
						sb.append(divFoto);
						sb.append("		</div>");
						candidatoDto.setDetalhamentoTabela01(sb.toString());
						sb.delete(0, sb.length());
						
						/** 2ª Coluna **/
						sb2.append("		<div class='row-fluid innerTB'>");
						sb2.append("			<div class='row-fluid'>");
						sb2.append("				<div class='span4'>");
						sb2.append("					<label><b>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.nome") +":</b> "+ candidatoDto.getNome() +"</label>");
						sb2.append("				</div>");
						sb2.append("				<div class='span4'>");
						sb2.append("					<label><b>"+ UtilI18N.internacionaliza(request, "colaborador.cpf") +":</b> "+candidatoDto.getCpfFormatado()+"</label>");
						sb2.append("				</div>");
						sb2.append("				<div class='span4'>");
						sb2.append("					<label><b>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.email") +":</b> "+candidatoDto.getEmail()+"</label>");
						sb2.append("				</div>");
						sb2.append("			</div>");
						sb2.append("		</div>");
						candidatoDto.setDetalhamentoTabela02(sb2.toString());
						sb2.delete(0, sb2.length());
						
						/** 3ª Coluna **/
						sb3.append("		<div class='row-fluid innerTB'>");
						sb3.append("			<div class='span12'>");
						sb3.append("<span class='strong tamanhoFonte' data-layout='top' data-type='information' data-toggle='notyfy'>"+tipoPessoa+"</span>");
						sb3.append("			</div>");
						sb3.append("		</div>");
						candidatoDto.setDetalhamentoTabela03(sb3.toString());
						sb3.delete(0, sb3.length());
						
						/** 4ª Coluna **/
						sb4.append("		<div class='row-fluid innerTB'>");  
						sb4.append("			<div class='span10'>"); 
						sb4.append("				<a href='#' class='btn-action glyphicons nameplate btn-success titulo' title='"+ UtilI18N.internacionaliza(request, "rh.visualizarHistorico") +"' onclick='visualizarHistorico("+ candidatoDto.getIdHistoricoFuncional() +","+ candidatoDto.getIdCandidato() +");'><i></i></a> ");
						sb4.append("				<a href='#' class='btn-action glyphicons circle_plus btn-success titulo' title='"+ UtilI18N.internacionaliza(request, "rh.adicionarItemHistorico") +"' onclick='addItemHistorico("+ candidatoDto.getIdHistoricoFuncional() +");'><i></i></a> |");
						sb4.append("				<a href='#' class='btn-action glyphicons thumbs_down btn-warning titulo' title='"+ UtilI18N.internacionaliza(request, "rh.addRmvListaNegra") +"' onclick='addBlackList("+ candidatoDto.getIdCandidato() +");'><i></i></a>");
						sb4.append("			</div>");
						sb4.append("		</div>");
						candidatoDto.setDetalhamentoTabela04(sb4.toString());
						sb4.delete(0, sb4.length());
						
					colRetorno.add(candidatoDto);
			}
		}
		return colRetorno;
	}

	/**
	 * @author david.silva
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
		
		//Limpa a tabela dos integrantes da viagem a cada paginação
//		document.executeScript("limparCamposDaSegundaGridDoFormularioPrincipal();");
	}
	
	public boolean isUserInGroup(HttpServletRequest req, String grupo) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		if (usuario == null) {
			return false;
		}

		String[] grupos = usuario.getGrupos();
		String grpAux = UtilStrings.nullToVazio(grupo);
		for (int i = 0; i < grupos.length; i++) {
			if (grupos[i] != null) {
				if (grupos[i].trim().indexOf(grpAux.trim()) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Class getBeanClass() {
		return HistoricoFuncionalDTO.class;
	}
}
