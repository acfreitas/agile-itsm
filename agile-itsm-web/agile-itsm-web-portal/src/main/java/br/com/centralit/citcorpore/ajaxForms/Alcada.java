package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.LimiteAlcadaDTO;
import br.com.centralit.citcorpore.negocio.AlcadaService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.LimiteAlcadaService;
import br.com.centralit.citcorpore.util.Enumerados.TipoAlcada;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

/**
 * @author rodrigo.oliveira
 *
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class Alcada extends AjaxFormAction {
	
	private AlcadaService alcadaService;
	
	@Override
	public Class getBeanClass() {
		return AlcadaDTO.class;
	}
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		preencheComboTipoAlcada(document, request);
		
	}
	
	private void preencheComboTipoAlcada(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("tipoAlcada");
		inicializaCombo(combo, request);
			  
		for (TipoAlcada c : TipoAlcada.values()){
			if (c.getDescricao() != null) {
				combo.addOption(c.name(), c.getDescricao());
			}
		}
		
	}

	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AlcadaDTO alcada = (AlcadaDTO) document.getBean();
		LimiteAlcadaService limiteService  = (LimiteAlcadaService) ServiceLocator.getInstance().getService(LimiteAlcadaService.class, WebUtil.getUsuarioSistema(request));
		
		ArrayList<LimiteAlcadaDTO> listaDeLimites = (ArrayList<LimiteAlcadaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LimiteAlcadaDTO.class, "listLimites", request);
		
		boolean existeIgual = getAlcadaService(request).existeIgual(alcada);
		
			if (alcada.getIdAlcada() == null || alcada.getIdAlcada() == 0) {
				if(!existeIgual){
					alcada = (AlcadaDTO) getAlcadaService(request).create(alcada);
					
					limiteService.removerPorIdAlcada(alcada.getIdAlcada());
					
					if (listaDeLimites != null && !listaDeLimites.isEmpty()) {
		
						for (LimiteAlcadaDTO limiteAlcadaDTO : listaDeLimites) {
							limiteAlcadaDTO.setIdAlcada(alcada.getIdAlcada());
							limiteService.create(limiteAlcadaDTO);
						}
					}
					document.alert(UtilI18N.internacionaliza(request, "MSG05"));
				} else {
					document.alert(UtilI18N.internacionaliza(request, "MSE01"));
					return;
				}
			} else {
				getAlcadaService(request).update(alcada);
				
				limiteService.removerPorIdAlcada(alcada.getIdAlcada());
				
				if (listaDeLimites != null && !listaDeLimites.isEmpty()) {
	
					for (LimiteAlcadaDTO limiteAlcadaDTO : listaDeLimites) {
						limiteAlcadaDTO.setIdAlcada(alcada.getIdAlcada());
						limiteService.create(limiteAlcadaDTO);
					}
				}
				
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
			
			HTMLForm form = document.getForm("form");
			form.clear();
			document.executeScript("setSituacao()");
			HTMLForm formLimite = document.getForm("formLimite");
			formLimite.clear();
			document.executeScript("setSituacaoLimite()");
			document.executeScript("deleteAllRows()");
		
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AlcadaDTO alcadaDTO = (AlcadaDTO) document.getBean();
		LimiteAlcadaService limiteService  = (LimiteAlcadaService) ServiceLocator.getInstance().getService(LimiteAlcadaService.class, WebUtil.getUsuarioSistema(request) );
		
		// Verificando a existência do DTO e do serviço de limite.
		if (alcadaDTO != null && limiteService != null) {
			
			if (alcadaDTO.getIdAlcada() != null && alcadaDTO.getIdAlcada().intValue() > 0) {
				CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request) );
				
				// Verificando se a alçada a ser excluída está associada a alguma alçada de centro de resultado.
				Collection centrosResultadoDTO = centroResultadoService.findByIdAlcada(alcadaDTO.getIdAlcada() );
				
				if (centrosResultadoDTO != null && !centrosResultadoDTO.isEmpty() ) {
					// Se existe a associação, notifica o usuário que a exclusão foi cancelada.
					document.alert(UtilI18N.internacionaliza(request, "MSG08") );
				} else {
					// Senão efetua a exclusão.
					limiteService.removerPorIdAlcada(alcadaDTO.getIdAlcada() );
					getAlcadaService(request).delete(alcadaDTO);
				}
			}
			
			HTMLForm form = document.getForm("form");
			document.executeScript("deleteAllRows()");
			form.clear();
		}
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AlcadaDTO alcada = (AlcadaDTO) document.getBean();
		alcada = (AlcadaDTO) getAlcadaService(request).restore(alcada);
		LimiteAlcadaService limiteService  = (LimiteAlcadaService) ServiceLocator.getInstance().getService(LimiteAlcadaService.class, WebUtil.getUsuarioSistema(request));
		Collection<LimiteAlcadaDTO> colLimites = new ArrayList();
		colLimites = limiteService.findByIdAlcada(alcada.getIdAlcada());
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.executeScript("deleteAllRows()");
		
		if (colLimites != null && !colLimites.isEmpty()){
			StringBuilder scriptAddLinha = new StringBuilder();
			GrupoService grupoService  = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, WebUtil.getUsuarioSistema(request));
			GrupoDTO grupoDTO = new GrupoDTO();
			String valor = "";
			
			for (LimiteAlcadaDTO limiteAlcada : colLimites){
				scriptAddLinha.append("addLinhaTabelaLimite(");
				scriptAddLinha.append(limiteAlcada.getIdGrupo() +",");
				
				grupoDTO.setIdGrupo(limiteAlcada.getIdGrupo());
				grupoDTO = (GrupoDTO) grupoService.restore(grupoDTO);
				if(grupoDTO != null){
					scriptAddLinha.append("'" + grupoDTO.getNome() + "',");
				}
				scriptAddLinha.append("'" + limiteAlcada.getTipoLimite() + "',");
				scriptAddLinha.append("'" + limiteAlcada.getAbrangenciaCentroCusto() + "',");
				
				valor = UtilFormatacao.formatDouble(limiteAlcada.getLimiteItemUsoInterno(),2);
				scriptAddLinha.append("'" + valor + "',");
				valor = UtilFormatacao.formatDouble(limiteAlcada.getLimiteMensalUsoInterno(),2);
				scriptAddLinha.append("'" + valor + "',");
				
                valor = UtilFormatacao.formatDouble(limiteAlcada.getLimiteItemAtendCliente(),2);
                scriptAddLinha.append("'" + valor + "',");
                valor = UtilFormatacao.formatDouble(limiteAlcada.getLimiteMensalAtendCliente(),2);
                scriptAddLinha.append("'" + valor + "',");

				scriptAddLinha.append("'" + limiteAlcada.getSituacao() + "')");
				
				document.executeScript(scriptAddLinha.toString());
				scriptAddLinha = new StringBuilder();
			}
		}
		
		form.setValues(alcada);		
	}
	
	public AlcadaService getAlcadaService(HttpServletRequest request) throws ServiceException, Exception {
		if(alcadaService == null){
			alcadaService = (AlcadaService) ServiceLocator.getInstance().getService(AlcadaService.class, WebUtil.getUsuarioSistema(request));
		}
		return alcadaService;
	}

}
