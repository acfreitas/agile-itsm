package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.MatrizPrioridadeDTO;
import br.com.centralit.citcorpore.bean.PrioridadeSolicitacoesDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.PrioridadeSolicitacoesService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * Classe que representa o form do Cadastro de Prioridade
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PrioridadeSolicitacoes extends AjaxFormAction {
	
	private PrioridadeSolicitacoesService prioridadeSolicitacoesService; 
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		carregaValoresImpacto(document, request);
		carregaValoresUrgencia(document, request);
		carregaValoresMatriz(document, request);
		
	}

	@Override
	public Class getBeanClass() {
		return PrioridadeSolicitacoesDTO.class;
	}
	
	private void carregaValoresImpacto(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception{
		document.getElementById("NIVELIMPACTOSERELIALIZADO").setValue("");
		List<ImpactoDTO> lista = new ArrayList(getPrioridadeSolicitacoesService(request).consultaImpacto());
		for(ImpactoDTO impacto : lista){
			if(impacto != null && impacto.getSiglaImpacto() != null && impacto.getSiglaImpacto().length() > 1)
				impacto.setSiglaImpacto(impacto.getSiglaImpacto().substring(0, 1));
		}
		String impactoSerelializado = br.com.citframework.util.WebUtil.serializeObjects(lista, WebUtil.getLanguage(request));
		document.getElementById("NIVELIMPACTOSERELIALIZADO").setValue(impactoSerelializado);
		document.executeScript("carregaImpacto();");
	}
	
	private void carregaValoresUrgencia(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception{
		document.getElementById("NIVELURGENCIASERELIALIZADO").setValue("");
		List<UrgenciaDTO> lista = new ArrayList(getPrioridadeSolicitacoesService(request).consultaUrgencia());
		for(UrgenciaDTO urgencia : lista){
			if(urgencia != null && urgencia.getSiglaUrgencia() != null && urgencia.getSiglaUrgencia().length() > 1)
				urgencia.setSiglaUrgencia(urgencia.getSiglaUrgencia().substring(0, 1));
		}
		String urgenciaSerelializado = br.com.citframework.util.WebUtil.serializeObjects(lista, WebUtil.getLanguage(request));
		document.getElementById("NIVELURGENCIASERELIALIZADO").setValue(urgenciaSerelializado);
		document.executeScript("carregaUrgencia();");
	}
	
	private void carregaValoresMatriz(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception{
		
		Collection<MatrizPrioridadeDTO> listaMatriz = getPrioridadeSolicitacoesService(request).consultaMatrizPrioridade();
		if(listaMatriz != null && listaMatriz.size() > 0){
			document.executeScript("deleteAllRows()");
			document.executeScript("countMatriz = 0");
			for (MatrizPrioridadeDTO matrizPrioridadeDTO : listaMatriz) {
				ImpactoDTO impacto = new ImpactoDTO();
				if(matrizPrioridadeDTO != null){
					impacto.setSiglaImpacto(matrizPrioridadeDTO.getSiglaImpacto());
				}
				impacto = (ImpactoDTO) getPrioridadeSolicitacoesService(request).restoreImpactoBySigla(impacto);
				UrgenciaDTO urgencia = new UrgenciaDTO();
				if(matrizPrioridadeDTO != null){
					urgencia.setSiglaUrgencia(matrizPrioridadeDTO.getSiglaUrgencia());
				}
				urgencia = (UrgenciaDTO) getPrioridadeSolicitacoesService(request).restoreUrgenciaBySigla(urgencia);
				if(matrizPrioridadeDTO != null && impacto != null && urgencia != null){
					document.executeScript("insereRow('"+impacto.getSiglaImpacto()+"', '"+impacto.getNivelImpacto()+"', " +
							"'"+urgencia.getSiglaUrgencia()+"', '"+urgencia.getNivelUrgencia()+"', "+matrizPrioridadeDTO.getValorPrioridade()+");");
				}
			}
			document.executeScript("exibeCadastroMatriz()");
			document.executeScript("exibeGrid()");
			geraComboImpacto(document, request);
			geraComboUrgencia(document, request);
			document.executeScript("$('#divBotaoMatrizPrioridade').remove();");
		}
		
	}
	
	public void saveImpacto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<ImpactoDTO> listaImpacto = ((ArrayList<ImpactoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ImpactoDTO.class, "NIVELIMPACTOSERELIALIZADO", request));
		int a = verificaImpacto(listaImpacto);
		if(a != -1){
			document.alert(UtilI18N.internacionaliza(request, "prioridadesolicitacao.registrosiguais"));
			document.executeScript("removeLinha('tabelaImpacto',"+a+")");
			return;
		}
		
		
		if(listaImpacto != null){
//			listaImpacto = verificaImpactoExiste(request, listaImpacto);
//			if(listaImpacto == null){
//				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
//				return;
//			}
			getPrioridadeSolicitacoesService(request).deleteImpacto();
			for (ImpactoDTO impacto : listaImpacto) {
				String nivel = impacto.getNivelImpacto().trim();
				String sigla = impacto.getSiglaImpacto().trim();
				if((nivel == null || nivel.isEmpty()) || (sigla == null || sigla.isEmpty())){
					document.alert(UtilI18N.internacionaliza(request, "prioridade.cadastro.preenchimento"));
					return;
				}
				getPrioridadeSolicitacoesService(request).createImpacto(impacto);
			}
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			geraComboImpacto(document, request);
		}else{
			getPrioridadeSolicitacoesService(request).deleteImpacto();
			document.alert(UtilI18N.internacionaliza(request, "MSG11"));
		}
		
	}
	
	
	public void saveUrgencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<UrgenciaDTO> listaUrgencia = ((ArrayList<UrgenciaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(UrgenciaDTO.class, "NIVELURGENCIASERELIALIZADO", request));
		
		if(listaUrgencia != null){
			int a = verificaUrgencia(listaUrgencia);
			if(a != -1){
				document.alert(UtilI18N.internacionaliza(request, "prioridadesolicitacao.registrosiguais"));
				document.executeScript("removeLinha('tabelaUrgencia',"+a+")");
				return;
			}
			
//			listaUrgencia = verificaUrgenciaExiste(request, listaUrgencia);
//			if(listaUrgencia == null){
//				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
//				return;
//			}
			
			getPrioridadeSolicitacoesService(request).deleteUrgencia();
			for (UrgenciaDTO urgencia : listaUrgencia) {
				String nivel = urgencia.getNivelUrgencia().trim();
				String sigla = urgencia.getSiglaUrgencia().trim();
				if((nivel == null || nivel.isEmpty()) || (sigla == null || sigla.isEmpty())){
					document.alert(UtilI18N.internacionaliza(request, "prioridade.cadastro.preenchimento"));
					return;
				}
				getPrioridadeSolicitacoesService(request).createUrgencia(urgencia);
			}
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			geraComboUrgencia(document, request);
		}else{
			getPrioridadeSolicitacoesService(request).deleteUrgencia();
			document.alert(UtilI18N.internacionaliza(request, "MSG11"));
		}
		
	}
	
	public void saveMatrizPrioridade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<MatrizPrioridadeDTO> listaMatriz = (ArrayList<MatrizPrioridadeDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(MatrizPrioridadeDTO.class, "MATRIZPRIORIDADESERELIALIZADO", request);
		
		if(listaMatriz != null){
			getPrioridadeSolicitacoesService(request).deleteMatrizPrioridade();
			for (MatrizPrioridadeDTO matrizPrioridade : listaMatriz) {
				getPrioridadeSolicitacoesService(request).createMatrizPrioridade(matrizPrioridade);
			}
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}else{
			getPrioridadeSolicitacoesService(request).deleteMatrizPrioridade();
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}
		
	}
	
	public void cadastrarMatriz(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean flag = getPrioridadeSolicitacoesService(request).consultaCadastros();
		if(flag){
			geraComboImpacto(document, request);
			geraComboUrgencia(document, request);
			document.executeScript("exibeCadastroMatriz();");
			document.executeScript("$('#divBotaoMatrizPrioridade').remove();");
		}else{
			document.alert(UtilI18N.internacionaliza(request, "prioridade.matrizprioridade.alert.erro"));
		}
		
	}
	
	private void geraComboImpacto(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("IDIMPACTOSELECT");
		combo.removeAllOptions();
		combo.addOption(String.valueOf(0), UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		Collection<ImpactoDTO> lista = getPrioridadeSolicitacoesService(request).consultaImpacto();
		
		if (lista != null) {
		    for (ImpactoDTO j : lista) {
		    	combo.addOption(j.getSiglaImpacto().toString(), j.getNivelImpacto());
		    }
		}
    }
	
	private void geraComboUrgencia(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("IDURGENCIASELECT");
		combo.removeAllOptions();
		combo.addOption(String.valueOf(0), UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		Collection<UrgenciaDTO> lista = getPrioridadeSolicitacoesService(request).consultaUrgencia();
		
		if (lista != null) {
		    for (UrgenciaDTO j : lista) {
		    	combo.addOption(j.getSiglaUrgencia().toString(), j.getNivelUrgencia());
		    }
		}
    }

	/**
	 * @return the prioridadeSolicitacoesService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public PrioridadeSolicitacoesService getPrioridadeSolicitacoesService(HttpServletRequest request) throws ServiceException, Exception {
		if(prioridadeSolicitacoesService == null){
			prioridadeSolicitacoesService = (PrioridadeSolicitacoesService) ServiceLocator.getInstance().getService(PrioridadeSolicitacoesService.class, WebUtil.getUsuarioSistema(request));
		}
		return prioridadeSolicitacoesService;
	}
	
	private Integer verificaImpacto(Collection<ImpactoDTO> listaImpacto){
		int auxa = 0;
		
		if(listaImpacto != null){
			for (ImpactoDTO impacto : listaImpacto) {
				
				String nivel = impacto.getNivelImpacto().trim();
				String sigla = impacto.getSiglaImpacto().trim();
				int auxb = 0;
				for (ImpactoDTO imp : listaImpacto) {
					if(auxa == auxb){
						auxb++;
						continue;
					}
					String nvl = imp.getNivelImpacto().trim();
					String sga = imp.getSiglaImpacto().trim();
					if(nvl.equalsIgnoreCase(nivel) || sga.equalsIgnoreCase(sigla)){
						return auxb;
					}
					auxb++;
					
					
				}
				auxa++;
			}
			return -1;
		}
		
		
		return null;
		
	}
	private Integer verificaUrgencia(Collection<UrgenciaDTO> listaUrgencia){
		int auxa = 0;
		
		if(listaUrgencia != null){
			for (UrgenciaDTO impacto : listaUrgencia) {
				
				String nivel = impacto.getNivelUrgencia().trim();
				String sigla = impacto.getSiglaUrgencia().trim();
				int auxb = 0;
				for (UrgenciaDTO imp : listaUrgencia) {
					if(auxa == auxb){
						auxb++;
						continue;
					}
					String nvl = imp.getNivelUrgencia().trim();
					String sga = imp.getSiglaUrgencia().trim();
					if(nvl.equalsIgnoreCase(nivel) || sga.equalsIgnoreCase(sigla)){
						return auxb;
					}
					auxb++;
					
					
				}
				auxa++;
			}
			return -1;
		}
		
		
		return null;
		
	}
	
}
	

