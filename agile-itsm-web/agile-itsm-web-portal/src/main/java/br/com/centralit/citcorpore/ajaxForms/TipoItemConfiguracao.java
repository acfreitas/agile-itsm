/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.centralit.citcorpore.negocio.CaracteristicaTipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

/**
 * Action de TipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * @author flavio.santana
 * @since 15/05/2013
 * 
 */
public class TipoItemConfiguracao extends AjaxFormAction {

    /** Bean de TipoItemConfiguracao. */
    private TipoItemConfiguracaoDTO tipoItemConfiguracaoBean;

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	/*Combo de status*/
		HTMLSelect comboCategoria = (HTMLSelect) document.getSelectById("categoria");
		comboCategoria.removeAllOptions();
		for (Enumerados.CategoriaTipoItemConfiguracao st : Enumerados.CategoriaTipoItemConfiguracao.values()) {
			comboCategoria.addOption(st.getItem().toString(), st.getDescricao());
		}
		List<String> listaImagens = listDirectorioImagens(new File(CITCorporeUtil.CAMINHO_REAL_APP +"/pages/tipoItemConfiguracao/imagens/48/"));
		StringBuilder sb = new StringBuilder();
		for (String imagem : listaImagens) {
			sb.append(
			"<label>" +
				"<img style='vertical-align: top;' src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/tipoItemConfiguracao/imagens/48/"+imagem+"' />" +
			"<input class='' type='radio' name='imagem' value='"+imagem+"' /></label>" +
			"");
		}
		document.getElementById("imagens").setInnerHTML(sb.toString());
		
    }

    /**
     * Inclui Novo Item de Configuração.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     */
    @SuppressWarnings("rawtypes")
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setTipoItemConfiguracaoBean((TipoItemConfiguracaoDTO) document.getBean());
	
		this.getTipoItemConfiguracaoBean().setCaracteristicas(
			(List) WebUtil.deserializeCollectionFromRequest(CaracteristicaDTO.class, "caracteristicasSerializadas", request));
	
		if (this.getTipoItemConfiguracaoBean().getId() == null || this.getTipoItemConfiguracaoBean().getId().intValue() == 0) {
			if(getTipoItemConfiguracaoService().verificarSeTipoItemConfiguracaoExiste(getTipoItemConfiguracaoBean())){				
				document.alert(UtilI18N.internacionaliza(request, "MSE01") );				
				return;
			}
		    this.getTipoItemConfiguracaoService().create(this.getTipoItemConfiguracaoBean(), request);		    
		    document.alert(UtilI18N.internacionaliza(request, "MSG05") );
		} else {
			if(getTipoItemConfiguracaoService().verificarSeTipoItemConfiguracaoExiste(getTipoItemConfiguracaoBean())){
				document.alert(UtilI18N.internacionaliza(request, "MSE01") );				
				return;
			}
		    this.getTipoItemConfiguracaoService().update(this.getTipoItemConfiguracaoBean());
		    document.alert(UtilI18N.internacionaliza(request, "MSG06") );
		}
	
		CITCorporeUtil.limparFormulario(document);
		document.executeScript("ocultaGrid()");
		HTMLTable tabelaCaracteristica = document.getTableById("tabelaCaracteristica");
		tabelaCaracteristica.deleteAllRows();
    }

    /**
     * Restaura Tela de Tipo Item Configuração.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setTipoItemConfiguracaoBean((TipoItemConfiguracaoDTO) document.getBean());
		this.setTipoItemConfiguracaoBean(this.getTipoItemConfiguracaoService().restore(this.getTipoItemConfiguracaoBean()));
		document.executeScript("deleteAllRows()");
		HTMLForm form = CITCorporeUtil.limparFormulario(document);
		form.setValues(this.getTipoItemConfiguracaoBean());
		this.bloquearDesbloquearTag(document);
		if (this.getTipoItemConfiguracaoBean() != null) {
		    this.getTipoItemConfiguracaoService().restaurarGridCaracteristicas(document,
			    this.getCaracteristicaService().consultarCaracteristicasAtivas(this.getTipoItemConfiguracaoBean().getId()));
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
		
    }
    
    /**
     * Bloquea ou desbloqueia TAG.
     * 
     * @param document
     * @author valdoilo.damasceno
     */
    private void bloquearDesbloquearTag(DocumentHTML document) {
		if (this.getTipoItemConfiguracaoBean().getSistema() != "N") {
		    document.executeScript("bloquearTag(+" + true + ")");
		} else {
		    document.executeScript("bloquearTag(+" + false + ")");
		}
    }

    /**
     * Exclui Tipo Item Configuração e suas características.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void excluirTipoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setTipoItemConfiguracaoBean((TipoItemConfiguracaoDTO) document.getBean());
	
		if (!this.verificaAssociacaoItemConfiguracao()){
			if (this.getTipoItemConfiguracaoBean().getId() != null && this.getTipoItemConfiguracaoBean().getId() != 0) {
			    this.getTipoItemConfiguracaoBean().setDataFim(UtilDatas.getDataAtual());
			    this.getTipoItemConfiguracaoService().update(this.getTipoItemConfiguracaoBean());
		
			    this.getCaracteristicaTipoItemConfiguracaoService().excluirAssociacaoCaracteristicaTipoItemConfiguracao(
				    this.getTipoItemConfiguracaoBean().getId(), null);
		
			    CITCorporeUtil.limparFormulario(document);
			    document.executeScript("ocultaGrid()");
			    document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "grupo.deletar.tipoItemConfiguracao"));
		}
    }

    /**
     * Exclui característica do Tipo Item Configuração.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void excluirAssociacaoCaracteristicaTipoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
		Integer idCaracteristica = !request.getParameter("caracteristicaSerializada").isEmpty() ? Integer.parseInt(request
			.getParameter("caracteristicaSerializada")) : null;
		
		CaracteristicaDTO caracteristica = new CaracteristicaDTO();
		if(idCaracteristica != null) {
			caracteristica.setIdCaracteristica(idCaracteristica);
			caracteristica = (CaracteristicaDTO) this.getCaracteristicaService().restore(caracteristica);	
			
			/*
			 * Retirado da rotina para validação da pink elephant
			 * && caracteristica.getSistema() == "N";
			 * */
			if (caracteristica != null ){
				this.setTipoItemConfiguracaoBean((TipoItemConfiguracaoDTO) document.getBean());
				this.getCaracteristicaTipoItemConfiguracaoService().excluirAssociacaoCaracteristicaTipoItemConfiguracao(this.getTipoItemConfiguracaoBean().getId(), idCaracteristica);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}
		}
		
		
    }

    /**
     * Retorna Service de TipoItemConfiguracao.
     * 
     * @return TipoItemConfiguracaoService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public TipoItemConfiguracaoService getTipoItemConfiguracaoService() throws ServiceException, Exception {
	return (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
    }

    /**
     * Retorna Service de Característica.
     * 
     * @return CaracteristicaService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public CaracteristicaService getCaracteristicaService() throws ServiceException, Exception {
	return (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
    }

    /**
     * Retorna Service de CaracteristicaTipoItemConfiguracao.
     * 
     * @return CaracteristicaTipoItemConfiguracaoService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public CaracteristicaTipoItemConfiguracaoService getCaracteristicaTipoItemConfiguracaoService() throws ServiceException, Exception {
	return (CaracteristicaTipoItemConfiguracaoService) ServiceLocator.getInstance().getService(CaracteristicaTipoItemConfiguracaoService.class,
		null);
    }

    /**
     * Retorna Tipo Item Configuração.
     * 
     * @return TipoItemConfiguracaoDTO
     * @author valdoilo.damasceno
     */
    public TipoItemConfiguracaoDTO getTipoItemConfiguracaoBean() {
	return this.tipoItemConfiguracaoBean;
    }

    /**
     * Configura Tipo Item Configuração.
     * 
     * @param tipoItemConfiguracao
     *            - IDto
     * @author valdoilo.damasceno
     */
    public void setTipoItemConfiguracaoBean(IDto tipoItemConfiguracao) {
	this.tipoItemConfiguracaoBean = (TipoItemConfiguracaoDTO) tipoItemConfiguracao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.centralit.citajax.html.AjaxFormAction#getBeanClass()
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Class getBeanClass() {
	return TipoItemConfiguracaoDTO.class;
    }
    
    public static java.util.List<String> listDirectorioImagens(File dir) {  
	    List<String> lista = new ArrayList<String>();
		if (dir.isDirectory()) {  
	        String[] filhos = dir.list();  
	        for (int i = 0; i < filhos.length; i++) {
	            File nome = new File(dir, filhos[i]);  
	            if (nome.isFile()) {  
	                if (nome.getName().endsWith(".png")) {  
	                    lista.add(nome.getName());  
	                }  
	            }
	        }  
	    } 
	    return lista;  
	}
    
    /**
     * Verifica se o Tipo Item Configuração está associado a algum Item Configuração
     * @return
     * @throws Exception 
     * @throws ServiceException 
     * @author thyen.chang
     */
    public boolean verificaAssociacaoItemConfiguracao() throws ServiceException, Exception{
    	return this.getTipoItemConfiguracaoService().verificaAssociacaoItemConfiguracao(this.getTipoItemConfiguracaoBean());
    }

}
