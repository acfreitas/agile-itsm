package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.ItemHistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.VisualizarHistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.rh.negocio.ItemHistoricoFuncionalService;
import br.com.centralit.citcorpore.rh.negocio.VisualizarHistoricoFuncionalService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author david.silva
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class VisualizarHistoricoFuncional extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		if(!(isUserInGroup(request, "RH"))){
			document.executeScript("alert('Voce não tem permição para usar essa Funcionalidade. Apenas Participantes do Grupo RH');");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'"); 
			return; 
		}
		
		String idHistorico = request.getParameter("idHistoricoFuncional");
		String idCandidato = request.getParameter("idCandidato");
		
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
		ItemHistoricoFuncionalService itemHistoricoFuncionalService = (ItemHistoricoFuncionalService) ServiceLocator.getInstance().getService(ItemHistoricoFuncionalService.class, null);
		
		Collection<CandidatoDTO> listCandidato = null;
		HistoricoFuncionalDTO historicoFuncionalDTO = new HistoricoFuncionalDTO();
		listCandidato = candidatoService.findListTodosCandidatos();
		
		for(CandidatoDTO candidato: listCandidato){
			if(candidato.getIdCandidato().equals(Integer.parseInt(idCandidato))){
				historicoFuncionalDTO.setNome(candidato.getNome());
				break;
			}
		}
		
		/**
		 * Lista com todos os itens do Historico funcional
		 * com idHistoricoFuncional daquele candidato.
		 */
		List list = new ArrayList<>();
		list = (List) itemHistoricoFuncionalService.findByIdItemHistorico(Integer.parseInt(idHistorico));
		
		historicoFuncionalDTO.setListaItemHistoricoFuncional(list);
		
		String itemHistoricoHTML = this.geraTabelaItem(request, historicoFuncionalDTO).toString();
		document.getElementById("divItens").setInnerHTML(itemHistoricoHTML);
				
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(historicoFuncionalDTO);
	}
	
	
	/**
	 * @param request
	 * @param historicoFuncionalDTO
	 * @return
	 * @throws Exception
	 * 
	 * Metodo para gerar tabela com itens de Historico Funcional
	 * Recebe como parametro um historicoFuncionalDTO populado
	 * com itens de historico funcional
	 */
	private StringBuilder geraTabelaItem(HttpServletRequest request, HistoricoFuncionalDTO historicoFuncionalDTO) throws Exception{
		VisualizarHistoricoFuncionalService visualizarHistoricoFuncionalService = (VisualizarHistoricoFuncionalService) ServiceLocator.getInstance().getService(VisualizarHistoricoFuncionalService.class, null);
		ItemHistoricoFuncionalDTO itemHistoricoFuncionalDto = new ItemHistoricoFuncionalDTO();
		
		Collection<ItemHistoricoFuncionalDTO> itensHistoricoFuncional = (Collection<ItemHistoricoFuncionalDTO>) historicoFuncionalDTO.getListaItemHistoricoFuncional();
		
		StringBuilder sb = new StringBuilder();
		
		if(itensHistoricoFuncional != null && itensHistoricoFuncional.size() > 0){
			for(ItemHistoricoFuncionalDTO itemHistoricoFuncionalDtoAux : itensHistoricoFuncional){
				
				itemHistoricoFuncionalDto =	visualizarHistoricoFuncionalService.restoreUsuario(itemHistoricoFuncionalDtoAux.getIdResponsavel());
				if(itemHistoricoFuncionalDto == null)
					continue;
				itemHistoricoFuncionalDtoAux.setNomeResponsavel(itemHistoricoFuncionalDto.getNomeResponsavel());
				
				sb.append("<div class='row-fluid'>");
				sb.append("		<div class='span12 divTotal'>");
				
				sb.append("<div class='row-fluid'>");
				sb.append("			<div class='span3 divlateral'>");
				sb.append("				<div class='row-fluid'>");
				sb.append("					<div class='span12 '>");
				sb.append("				    	<label class='strong'>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalTipo") + "</label>");
				
				if(itemHistoricoFuncionalDtoAux != null && itemHistoricoFuncionalDtoAux.getTipo().equalsIgnoreCase("A")){
					sb.append("						<label>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalAutomatico") + "</label>");	
				}
				if(itemHistoricoFuncionalDtoAux != null && itemHistoricoFuncionalDtoAux.getTipo().equalsIgnoreCase("M")){
					sb.append("						<label>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalManual") + "</label>");
				}
				
				sb.append("					</div>");													
				sb.append("				</div>");
				sb.append("				<div class='row-fluid'>");
				sb.append("					<div class='span12 '>");
				sb.append("						<label class='strong'>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalData") + "</label>");
				sb.append("						<label>"+UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, itemHistoricoFuncionalDtoAux.getDtCriacao(), UtilI18N.getLocale())+"</label>");
				sb.append("					</div>");													
				sb.append("				</div>");
				sb.append("				<div class='row-fluid'>");
				sb.append("					<div class='span12 '>");
				sb.append("						<label class='strong'>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalResponsavel") + "</label>");
				sb.append("						<label >"+itemHistoricoFuncionalDtoAux.getNomeResponsavel()+"</label>");
				sb.append("					</div>");													
				sb.append("				</div>");
				sb.append("			</div>");
				sb.append("			<div class='span9'>");
				sb.append("				<div class='row-fluid'>");
				sb.append("					<div class='span12'>");
				sb.append("						<label class='strong'>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalTitulo") + "</label>");
				sb.append("						<label>"+ itemHistoricoFuncionalDtoAux.getTitulo() +"</label>");
				sb.append("					</div>");													
				sb.append("				</div>");
				sb.append("				<div class='row-fluid'>");
				sb.append("					<div class='span12'>");
				sb.append("						<label class='strong'>" + UtilI18N.internacionaliza(request, "rh.historicoFuncionalDescricao") + "</label>");
				sb.append("						<label style='display: block;'>"+ itemHistoricoFuncionalDtoAux.getDescricao() +"</label>");
				sb.append("					</div>");
				sb.append("				</div>");
				sb.append("			</div>");
				sb.append("		</div>");
				
				sb.append("		</div>");
				sb.append("</div>");
				sb.append("				</br>");
			}
		}
		return sb;
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
		return VisualizarHistoricoFuncionalDTO.class;
	}

}
