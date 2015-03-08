/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.ajaxForms;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.BaseItemConfiguracaoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;


/**
 * Action de BaseItemConfiguracao.
 *
 * @author valdoilo.damasceno
 *
 */
public class BaseItemConfiguracao extends AjaxFormAction {

    /** Bean de Base Item Configuração. */
    private BaseItemConfiguracaoDTO baseItemConfiguracaoBean;

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    /**
     * Inclui Novo Base Item de Configuração.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     * @author diego.rezende
     */

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Usuario usuario = (Usuario) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
    	BaseItemConfiguracaoDTO[] vetorBaseItemConfiguracao = new BaseItemConfiguracaoDTO[3];
    	vetorBaseItemConfiguracao[0] = (BaseItemConfiguracaoDTO) document.getBean();
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
    		if (vetorBaseItemConfiguracao[0].getId() == null) {
    			document.executeScript("desabilitarChecks()");
    		}
    		return;
    	}

    	vetorBaseItemConfiguracao[1] = new BaseItemConfiguracaoDTO();
		Map<?,?> map = document.getValuesForm();

		BaseItemConfiguracaoService baseItemConfiguracaoService = (BaseItemConfiguracaoService) ServiceLocator.getInstance().getService(BaseItemConfiguracaoService.class, null);

		if (map.get("TIPOEXECUCAOINSTALACAO") != null && map.get("TIPOEXECUCAOINSTALACAO").equals("on")) {
			vetorBaseItemConfiguracao[1].setTipoexecucao("I");
			vetorBaseItemConfiguracao[1].setExecutavel(String.valueOf(map.get("EXECUTAVELINSTALACAO")));
			vetorBaseItemConfiguracao[1].setComando(String.valueOf(map.get("COMANDOINSTALACAO")));
		} else {
			setarBaseItemConfiguracaoDesinstalacao(vetorBaseItemConfiguracao[1], map);
		}

		vetorBaseItemConfiguracao[2] = null;

		if (map.get("TIPOEXECUCAOINSTALACAO") != null && map.get("TIPOEXECUCAOINSTALACAO").equals("on")
				&& map.get("TIPOEXECUCAODESINSTALACAO") != null && map.get("TIPOEXECUCAODESINSTALACAO").equals("on")) {
			vetorBaseItemConfiguracao[2] = new BaseItemConfiguracaoDTO();
			setarBaseItemConfiguracaoDesinstalacao(vetorBaseItemConfiguracao[2], map);


			if (map.get("COMANDOSIGUAIS") == null || map.get("COMANDOSIGUAIS").equals("off")) {
				vetorBaseItemConfiguracao[2].setComando(String.valueOf(map.get("COMANDOINSTALACAO")));
			}
		}

		if (vetorBaseItemConfiguracao[0].getId() == null) {
			if (baseItemConfiguracaoService.existBaseItemConfiguracao(vetorBaseItemConfiguracao[0])) {
				document.alert(UtilI18N.internacionaliza(request, "MSE01"));
				return;
			}
			baseItemConfiguracaoService.create(vetorBaseItemConfiguracao);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			baseItemConfiguracaoService.update(vetorBaseItemConfiguracao);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		document.executeScript("limpar()");
    }

    private void setarBaseItemConfiguracaoDesinstalacao(BaseItemConfiguracaoDTO baseItemConfiguracaoDto, Map<?,?> map) {
		baseItemConfiguracaoDto.setTipoexecucao("D");
		baseItemConfiguracaoDto.setExecutavel(String.valueOf(map.get("EXECUTAVELDESINSTALACAO")));
		baseItemConfiguracaoDto.setComando(String.valueOf(map.get("COMANDODESINSTALACAO")));
    }


    /**
     * Restaura Item de Configuracao da Base.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.setBaseItemConfiguracaoBean((IDto) document.getBean());

	List<IDto> filhos = this.getBaseItemConfiguracaoService().restoreChildren(getBaseItemConfiguracaoBean());

	document.executeScript("limpar()");
	document.executeScript("restorePai("+this.getBaseItemConfiguracaoBean().getId()+", '"+UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ((BaseItemConfiguracaoDTO)filhos.get(0)).getDataInicio(), WebUtil.getLanguage(request))+"', '"+((BaseItemConfiguracaoDTO)filhos.get(0)).getNome()+"')");

	boolean comandosDiferentes = true;

	if (filhos.size() == 2) {
		document.executeScript("exibirTodasDivsExecucao()");
		if (((BaseItemConfiguracaoDTO)filhos.get(0)).getComando().equals(((BaseItemConfiguracaoDTO)filhos.get(1)).getComando())) {
			comandosDiferentes = false;
		}
	}

	for (IDto iDto : filhos) {
		BaseItemConfiguracaoDTO baseFilho = (BaseItemConfiguracaoDTO) iDto;
		if (baseFilho.getTipoexecucao().equals("I")) {
			document.executeScript("restoreInstalacao('"+baseFilho.getExecutavel().replace("\\", "\\\\")+"', '"+baseFilho.getComando()+"')");
		} else {
			if (filhos.size() == 1) {
				document.executeScript("exibirSomenteDivDesinstalacao()");
				document.executeScript("esconderInstalacao()");
			}
			if (!comandosDiferentes) {
				baseFilho.setComando("");
			}
			document.executeScript("restoreDesinstalacao('"+baseFilho.getExecutavel().replace("\\", "\\\\")+"', '"+baseFilho.getComando()+"', "+comandosDiferentes+")");
		}
	}

    }

    /**
     * Seta a data atual na data final ao excluir um registro.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void atualizaData(DocumentHTML document, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
	BaseItemConfiguracaoDTO baseItemConfiguracaoDto = (BaseItemConfiguracaoDTO) document.getBean();
	BaseItemConfiguracaoService baseItemConfiguracaoService = (BaseItemConfiguracaoService) ServiceLocator.getInstance().getService(BaseItemConfiguracaoService.class, null);

	if (baseItemConfiguracaoDto.getId().intValue() > 0) {
	    baseItemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());


	    baseItemConfiguracaoService.update(new BaseItemConfiguracaoDTO[] {baseItemConfiguracaoDto});

	}

	document.executeScript("limpar()");

	document.alert(UtilI18N.internacionaliza(request, "MSG07"));
    }


    /**
     * Retorna Service de BaseItemConfiguracao.
     *
     * @return BaseItemConfiguracaoService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damsceno
     */
    public BaseItemConfiguracaoService getBaseItemConfiguracaoService() throws ServiceException, Exception {
	return (BaseItemConfiguracaoService) ServiceLocator.getInstance().getService(BaseItemConfiguracaoService.class, null);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getBeanClass() {
	return BaseItemConfiguracaoDTO.class;
    }

    /**
     * Configura bean de BaseItemConfiguracao.
     *
     * @param baseItemConfiguracaoBean
     * @author valdoilo.damasceno
     */
    public void setBaseItemConfiguracaoBean(IDto baseItemConfiguracaoBean) {
	this.baseItemConfiguracaoBean = (BaseItemConfiguracaoDTO) baseItemConfiguracaoBean;
    }

    /**
     * Retorna bean de BaseItemConfiguracao.
     *
     * @return BaseItemConfiguracaoDTO
     * @author valdoilo.damasceno
     */
    public BaseItemConfiguracaoDTO getBaseItemConfiguracaoBean() {
	return baseItemConfiguracaoBean;
    }

}
