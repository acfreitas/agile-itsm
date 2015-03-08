package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

public class ListaServicosContrato extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return ServicoContratoDTO.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String pathInfo = request.getRequestURI();
		String ext = "";
		String paginacao = "";
		ext = getObjectExt(pathInfo);
		ext = ext.replaceAll("#", "");
		Integer totalPag = 1;
		Integer pagAtual = 0;
		Integer pagAtualAux = 0;
		Integer idContrato;
		String campoPesquisa="";
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO)document.getBean();
		
		if ("load".equalsIgnoreCase(ext)) {
			if (request.getParameter("paginacao") != null) {
				paginacao = UtilStrings.decodeCaracteresEspeciais(request.getParameter("paginacao"));
				idContrato = Integer.parseInt(UtilStrings.decodeCaracteresEspeciais(request.getParameter("idContrato")));
				servicoContratoDTO.setIdContrato(idContrato);
			}
			if (request.getParameter("paginacao") == null || request.getParameter("paginacao") == "") {
				paginacao = "0";
			}
			if (request.getParameter("paginacao") == null) {
				paginacao = "0";
			}
			if (request.getParameter("pesquisa") != null && request.getParameter("pesquisa") != "") {
				campoPesquisa = UtilStrings.decodeCaracteresEspeciais(request.getParameter("pesquisa"));
				idContrato = Integer.parseInt(UtilStrings.decodeCaracteresEspeciais(request.getParameter("idContrato")));
				servicoContratoDTO.setIdContrato(idContrato);
			}
		}
		
		Integer quantidadePaginator = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));
		if(paginacao.equalsIgnoreCase(quantidadePaginator.toString())){
			pagAtual = quantidadePaginator;
		}else if(new Integer(paginacao) == 1){
			pagAtual = (new Integer(request.getSession(true).getAttribute("pagAtual").toString()) + quantidadePaginator);
			pagAtualAux = (new Integer(request.getSession(true).getAttribute("pagAtualAux").toString())+1);
			if(pagAtual >= new Integer(request.getSession(true).getAttribute("totalItens").toString())){
				pagAtual = new Integer(request.getSession(true).getAttribute("pagAtual").toString());
			}
			if(pagAtualAux >= new Integer(request.getSession(true).getAttribute("totalPag").toString())){
				pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag").toString());
			}			
		}else if(new Integer(paginacao) < 0){
			pagAtual = (new Integer(request.getSession(true).getAttribute("pagAtual").toString()) - quantidadePaginator);
			pagAtualAux = (new Integer(request.getSession(true).getAttribute("pagAtualAux").toString()) - 1);
			if(pagAtual < 1){
				pagAtual = 0;
				pagAtualAux = 1;
			}			
		}else if(new Integer(paginacao) == 0){
			pagAtual = 0;
			pagAtualAux = 1;
		}else{
			pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag").toString()) + 1;
			Integer modulo = (new Integer(request.getSession(true).getAttribute("totalItens").toString()) % quantidadePaginator);
			if(modulo.intValue() == quantidadePaginator.intValue() || modulo.intValue() == 0){
				pagAtual = new Integer(paginacao) - quantidadePaginator;
			}else{
				pagAtual = new Integer(paginacao) - modulo;
			}			
			if(pagAtualAux > new Integer(request.getSession(true).getAttribute("totalPag").toString())){
				pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag").toString());
			}			
		}
		request.getSession(true).setAttribute("pagAtual", pagAtual);
		request.getSession(true).setAttribute("pagAtualAux", pagAtualAux);
		
		ServicoContratoService serviceContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
		Collection colServicosContrato = serviceContratoService.findByIdContratoPaginada(servicoContratoDTO, paginacao, pagAtual, pagAtualAux, totalPag, quantidadePaginator,campoPesquisa);
		
		request.getSession(true).setAttribute("totalPag", servicoContratoDTO.getTotalPagina());
		request.getSession(true).setAttribute("totalItens", (servicoContratoDTO.getTotalItens() ));
		
		List colFinal = new ArrayList();
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
						Collection col = acordoNivelServicoService.findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());
						Collection colVincs = acordoServicoContratoService.findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());		
						if ((col != null && col.size() > 0) || (colVincs != null && colVincs.size() > 0)){
							servicoContratoAux.setTemSLA(true);	
						}
						colFinal.add(servicoContratoAux);
					}
				}
			}
		}
		Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		request.setAttribute("listaServicos", colFinal);
	}
	
	public String getObjectExt(String path) {
		String strResult = "";
		for (int i = path.length() - 1; i >= 0; i--) {
			if (path.charAt(i) == '.') {
				return strResult;
			} else {
				strResult = path.charAt(i) + strResult;
			}
		}
		return strResult;
	}

}
