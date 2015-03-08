package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.LocalidadeItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RegiaoDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.LocalidadeItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.RegiaoService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class LocalidadeItemConfiguracao extends AjaxFormAction {

	private LocalidadeItemConfiguracaoDTO localidadeItemConfiguracaoDto;

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return LocalidadeItemConfiguracaoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
		preencherComboGrupo(document, request, response);
		preencherComboUnidade(document, request, response);
		preencherComboRegioes(document, request, response);

	}

	/**
	 * Iniciliza combo.
	 * 
	 * @param componenteCombo
	 * @author thays.araujo
	 */
	private void inicializarCombo(HTMLSelect componenteCombo) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", "-- Selecione --");
	}

	/**
	 * Preenche a combo Unidade.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */

	public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
		ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.list();

		inicializarCombo(comboUnidade);
		if (unidades != null) {
			for (UnidadeDTO unidade : unidades)
				if (unidade.getDataFim() == null)
					comboUnidade.addOption(unidade.getIdUnidade().toString(), unidade.getNome());
		}

	}

	/**
	 * Preenche combo de Grupos.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */

	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		HTMLSelect comboGrupo = (HTMLSelect) document.getSelectById("departamento");
		ArrayList<GrupoDTO> grupos = (ArrayList) grupoService.list();
		inicializarCombo(comboGrupo);
		for (GrupoDTO grupo : grupos)
			if (grupo.getDataFim() == null) {
				comboGrupo.addOption(grupo.getNome(), grupo.getNome());
			}

	}

	/**
	 * Preenche combo de Regioes.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */

	public void preencherComboRegioes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegiaoService regiaoService = (RegiaoService) ServiceLocator.getInstance().getService(RegiaoService.class, null);
		HTMLSelect comboRegioes = (HTMLSelect) document.getSelectById("idRegioes");
		ArrayList<RegiaoDTO> regioes = (ArrayList) regiaoService.list();
		inicializarCombo(comboRegioes);
		for (RegiaoDTO regiao : regioes) {

			comboRegioes.addOption(regiao.getIdRegioes().toString(), regiao.getNome());

		}

	}

	/**
	 * Preenche combo de Ufs.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */

	public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);
		this.setLocalidadeItemconfiguracaoDto((LocalidadeItemConfiguracaoDTO) document.getBean());
		UfDTO ufRegiao = new UfDTO();
		ufRegiao.setIdRegioes(this.getLocalidadeItemconfiguracaoDto().getIdRegioes());
		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("idUf");
		inicializarCombo(comboUfs);
		if (getLocalidadeItemconfiguracaoDto().getIdRegioes() != null) {

			Collection<UfDTO> ufs = (Collection<UfDTO>) ufService.listByIdRegioes(ufRegiao);
			for (UfDTO uf : ufs)
				comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
		} else {
			this.setLocalidadeItemconfiguracaoDto(this.getLocalidadeItemConfiguracaoService().restore(getLocalidadeItemconfiguracaoDto()));
			UfDTO ufRegiaorestore = new UfDTO();
			ufRegiaorestore.setIdRegioes(this.getLocalidadeItemconfiguracaoDto().getIdRegioes());
			Collection<UfDTO> ufs = (Collection<UfDTO>) ufService.listByIdRegioes(ufRegiaorestore);
			for (UfDTO uf : ufs)
				comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
		}
	}

	/**
	 * Preenche combo de cidades.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */

	public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		this.setLocalidadeItemconfiguracaoDto((LocalidadeItemConfiguracaoDTO) document.getBean());
		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("cidade");
		inicializarCombo(comboCidades);
		CidadesDTO cidadeUf = new CidadesDTO();
		cidadeUf.setIdUf(this.getLocalidadeItemconfiguracaoDto().getIdUf());
		if (getLocalidadeItemconfiguracaoDto().getIdUf() != null) {
			ArrayList<CidadesDTO> cidades = (ArrayList) cidadesService.listByIdCidades(cidadeUf);
			for (CidadesDTO cidade : cidades)
				comboCidades.addOption(cidade.getNomeCidade(), cidade.getNomeCidade());

		} else {
			this.setLocalidadeItemconfiguracaoDto(this.getLocalidadeItemConfiguracaoService().restore(getLocalidadeItemconfiguracaoDto()));
			CidadesDTO cidadeUfRestore = new CidadesDTO();
			cidadeUfRestore.setIdUf(this.getLocalidadeItemconfiguracaoDto().getIdUf());
			ArrayList<CidadesDTO> cidades = (ArrayList) cidadesService.listByIdCidades(cidadeUfRestore);
			for (CidadesDTO cidade : cidades)
				comboCidades.addOption(cidade.getNomeCidade(), cidade.getNomeCidade());
		}
	}

	/**
	 * Inclui Nova LocalidadeItemConfiguracao.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		this.setLocalidadeItemconfiguracaoDto((LocalidadeItemConfiguracaoDTO) document.getBean());
		if (this.getLocalidadeItemconfiguracaoDto().getIdLocalidade() == null || this.getLocalidadeItemconfiguracaoDto().getIdLocalidade().intValue() == 0) {
			this.getLocalidadeItemconfiguracaoDto().setDataInicio(UtilDatas.getDataAtual());
			this.getLocalidadeItemConfiguracaoService().create(this.getLocalidadeItemconfiguracaoDto());
			document.alert("Registro gravado com sucesso!");
		} else {
			this.getLocalidadeItemConfiguracaoService().update(this.getLocalidadeItemconfiguracaoDto());
			document.alert("Registro alterado com sucesso!");
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * Recupera LalidadeItemconfiguracao
	 * 
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 * */

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		this.setLocalidadeItemconfiguracaoDto((LocalidadeItemConfiguracaoDTO) document.getBean());
		this.setLocalidadeItemconfiguracaoDto(this.getLocalidadeItemConfiguracaoService().restore(getLocalidadeItemconfiguracaoDto()));
		LocalidadeItemConfiguracaoDTO dadoRegiao = this.getLocalidadeItemConfiguracaoService().listByIdRegiao(getLocalidadeItemconfiguracaoDto());
		this.preencherComboGrupo(document, request, response);
		this.preencherComboUnidade(document, request, response);
		this.preencherComboRegioes(document, request, response);
		this.preencherComboUfs(document, request, response);
		this.preencherComboCidades(document, request, response);
		getLocalidadeItemconfiguracaoDto().setIdRegioes(dadoRegiao.getIdRegioes());
		LocalidadeItemConfiguracaoDTO dadoUf = this.getLocalidadeItemConfiguracaoService().listByIdUf(getLocalidadeItemconfiguracaoDto());
		getLocalidadeItemconfiguracaoDto().setIdUf(dadoUf.getIdUf());
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(getLocalidadeItemconfiguracaoDto());

	}

	/**
	 * Exclui Empregado atribuindo sua data fim em Empregado.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		this.setLocalidadeItemconfiguracaoDto((LocalidadeItemConfiguracaoDTO) document.getBean());

		if (this.getLocalidadeItemconfiguracaoDto().getIdLocalidade().intValue() > 0) {

			getLocalidadeItemconfiguracaoDto().setDataFim(UtilDatas.getDataAtual());
			this.getLocalidadeItemConfiguracaoService().update(getLocalidadeItemconfiguracaoDto());
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

	/**
	 * Retorna instancia de LocalidadeItemconfiguracaoService.
	 * 
	 * @return EmpregadoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public LocalidadeItemConfiguracaoService getLocalidadeItemConfiguracaoService() throws ServiceException, Exception {
		return (LocalidadeItemConfiguracaoService) ServiceLocator.getInstance().getService(LocalidadeItemConfiguracaoService.class, null);
	}

	/**
	 * Atribui valor de LocalidadeItemconfiguracaoDTO.
	 * 
	 * @param empregado
	 * @author thays.araujo
	 */
	public void setLocalidadeItemconfiguracaoDto(IDto localidadeItemConfiguracao) {
		this.localidadeItemConfiguracaoDto = (LocalidadeItemConfiguracaoDTO) localidadeItemConfiguracao;
	}

	/**
	 * Retorna bean de LocalidadeItemconfiguracao.
	 * 
	 * @return EmpregadoDTO
	 * @author thays.araujo
	 */
	public LocalidadeItemConfiguracaoDTO getLocalidadeItemconfiguracaoDto() {
		return this.localidadeItemConfiguracaoDto;
	}
}
