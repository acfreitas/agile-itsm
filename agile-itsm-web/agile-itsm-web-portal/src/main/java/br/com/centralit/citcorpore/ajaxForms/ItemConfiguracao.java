/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.InformacaoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.MidiaSoftwareService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

/**
 * @author thays.araujo
 *
 */
@SuppressWarnings("rawtypes")
public class ItemConfiguracao extends AjaxFormAction {

	/** Bean de Base Item Configuração. */
	private ItemConfiguracaoDTO itemConfiguracaoBean;

	/** Bean de Tipo Item Configuração. */
	private TipoItemConfiguracaoDTO tipoItemConfiguracaoBean = new TipoItemConfiguracaoDTO();

	/** Bean de Usuario */
	private UsuarioDTO UsuarioDto = new UsuarioDTO();

	/** Bean Empregado. */
	private EmpregadoDTO empregadoDTO = new EmpregadoDTO();

	/** Bean Unidade. */
	private UnidadeDTO unidadeDto = new UnidadeDTO();

	@Override
	public Class getBeanClass() {
		return ItemConfiguracaoDTO.class;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * br.com.centralit.citajax.html.AjaxFormAction#load(br.com.centralit.citajax
	 * .html.DocumentHTML, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		document.executeScript("ocultarItemConfiguracao()");

	}

	/**
	 * Inclui Novo Item de Configuração na Base.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public boolean salvar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return false;
    	}
    	MidiaSoftwareService midiaSoftwareService = (MidiaSoftwareService) ServiceLocator.getInstance().getService(MidiaSoftwareService.class, null);

		this.setItemConfiguracaoBean((IDto) document.getBean());
		/**
		 * Checa qual tipo de responsável para gravar no banco o ID correspondente
		 *
		 * @author thyen.chang
		 */
		if(this.getItemConfiguracaoBean().getTipoResponsavel().equals("U")){
			this.getItemConfiguracaoBean().setIdGrupoResponsavel(null);
		} else {
			this.getItemConfiguracaoBean().setIdGrupoResponsavel(this.getItemConfiguracaoBean().getIdGrupoResponsavel());
			this.getItemConfiguracaoBean().setIdResponsavel(null);
		}

		this.getTipoItemConfiguracaoBean().setCaracteristicas((List) WebUtil.deserializeCollectionFromRequest(CaracteristicaDTO.class, "caracteristicasSerializadas", request));
		this.getItemConfiguracaoBean().setTipoItemConfiguracaoSerializadas(this.getTipoItemConfiguracaoBean());
		if(this.getItemConfiguracaoBean().getIdItemConfiguracaoPai()!=null) {
			ItemConfiguracaoDTO i = new ItemConfiguracaoDTO();
			i.setIdItemConfiguracao(this.getItemConfiguracaoBean().getIdItemConfiguracaoPai());
			i = (ItemConfiguracaoDTO) this.getItemConfiguracaoService().restore(i);
			this.getItemConfiguracaoBean().setIdGrupoItemConfiguracao(i.getIdGrupoItemConfiguracao());
		}

		if (this.getItemConfiguracaoBean().getIdItemConfiguracao() == null) {
			/*
			 * Alteracao - Emauri - 27/11/2013
			 */
			//ThreadProcessaInventario.performanceDataSemaphoreInventario.acquireUninterruptibly();
			try{
				this.getItemConfiguracaoService().createItemConfiguracaoAplicacao(this.getItemConfiguracaoBean(), usrDto);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				//ThreadProcessaInventario.performanceDataSemaphoreInventario.release();
			}
			/*
			 * Fim - Alteracao - Emauri - 27/11/2013
			 */
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			document.executeScript("parent.JANELA_AGUARDE_MENU.hide()");

		} else {
			/*Verificando se as mídias estão esgotadas*/
			if(this.getItemConfiguracaoBean().getIdMidiaSoftware()!=null) {
				MidiaSoftwareDTO midia = new MidiaSoftwareDTO();
				midia.setIdMidiaSoftware(this.getItemConfiguracaoBean().getIdMidiaSoftware());
				midia = (MidiaSoftwareDTO) midiaSoftwareService.restore(midia);

				if(midia.getLicencas() <= this.getItemConfiguracaoService().quantidadeMidiaSoftware(this.getItemConfiguracaoBean())) {
					document.alert(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.licencaEsgotada"));
					document.executeScript("parent.JANELA_AGUARDE_MENU.hide()");
					return false;
				} else	{
					/*
					 * Alteracao - Emauri - 27/11/2013
					 */
					//ThreadProcessaInventario.performanceDataSemaphoreInventario.acquireUninterruptibly();
					try{
						this.getItemConfiguracaoService().updateItemConfiguracao(this.getItemConfiguracaoBean(), usrDto);
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						//ThreadProcessaInventario.performanceDataSemaphoreInventario.release();
					}
					/*
					 * Fim - Alteracao - Emauri - 27/11/2013
					 */
					document.alert(UtilI18N.internacionaliza(request, "MSG06"));
					document.executeScript("parent.JANELA_AGUARDE_MENU.hide()");
				}
			} else	{
				this.getItemConfiguracaoService().updateItemConfiguracao(this.getItemConfiguracaoBean(), usrDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
				document.executeScript("parent.JANELA_AGUARDE_MENU.hide()");
			}
		}
		if(this.getItemConfiguracaoBean().getIdItemConfiguracaoPai() == null) {
			document.executeScript("ocultaGrid();reload("+this.getItemConfiguracaoBean().getIdItemConfiguracao()+")");
		} else {
			document.executeScript("ocultaGrid();reload("+this.getItemConfiguracaoBean().getIdItemConfiguracaoPai()+")");
		}
		return true;

	}
	public UsuarioDTO getUsuarioSessao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
	}


	/**
	 * Restaura Item de Configuracao.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		this.setItemConfiguracaoBean((IDto) document.getBean());
		ItemConfiguracaoDTO itemConfiguracaoPai = new ItemConfiguracaoDTO();

		this.setItemConfiguracaoBean(this.getItemConfiguracaoService().restore(this.getItemConfiguracaoBean()));

		document.executeScript("deleteAllRows()");
		HTMLForm form = CITCorporeUtil.limparFormulario(document);
		form.setValues(this.getItemConfiguracaoBean());

		if ((this.getItemConfiguracaoBean().getIdItemConfiguracaoPai() == null)) {
			document.executeScript("ocultarItemConfiguracao()");

		} else {
			this.getTipoItemConfiguracaoBean().setId(this.getItemConfiguracaoBean().getIdTipoItemConfiguracao());
			this.getItemConfiguracaoBean().setNomeTipoItemConfiguracao(
					((TipoItemConfiguracaoDTO) this.getTipoItemConfiguracaoService().restore(this.getTipoItemConfiguracaoBean())).getNome());
			this.getTipoItemConfiguracaoService().restaurarGridCaracteristicas(
					document,
					this.getCaracteristicaService().consultarCaracteristicasComValoresItemConfiguracao(this.getItemConfiguracaoBean().getIdTipoItemConfiguracao(),
							this.getItemConfiguracaoBean().getIdItemConfiguracao()));
			itemConfiguracaoPai.setIdItemConfiguracao(this.getItemConfiguracaoBean().getIdItemConfiguracaoPai());
			itemConfiguracaoPai = (ItemConfiguracaoDTO) this.getItemConfiguracaoService().restore(itemConfiguracaoPai);
			this.getItemConfiguracaoBean().setNomeItemConfiguracaoPai(itemConfiguracaoPai.getIdentificacao());
			document.executeScript("visualizarItemConfiguracaoPai()");

		}

		if (this.getItemConfiguracaoBean() != null) {
			if (this.getItemConfiguracaoBean().getIdGrupoItemConfiguracao() != null && this.getItemConfiguracaoBean().getIdGrupoItemConfiguracao() > 0) {
				GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
				GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();
				grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(this.getItemConfiguracaoBean().getIdGrupoItemConfiguracao());
				grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoItemConfiguracaoDTO);
				this.getItemConfiguracaoBean().setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
			}
		}

		form.setValues(this.getItemConfiguracaoBean());
	}

	/**
	 * Restaura o Tipo de Item Configuração e carrega a Grid de Características
	 * Ativas.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void restoreTipoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.restoreTipoItemConfiguracaoValues(document, request, response);
	}

	/**
	 * Restaura o Tipo de Item Configuração e carrega a Grid de Características
	 * Ativas.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author flavio.santana
	 */
	public void restoreTipoItemConfiguracaoValues(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}

		this.setItemConfiguracaoBean((IDto) document.getBean());
		if(this.getItemConfiguracaoBean().getIdTipoItemConfiguracao()==null) {
			this.setItemConfiguracaoBean(this.getItemConfiguracaoService().restoreByIdItemConfiguracao(this.getItemConfiguracaoBean().getIdItemConfiguracao()));
		}
		this.getTipoItemConfiguracaoBean().setId(this.getItemConfiguracaoBean().getIdTipoItemConfiguracao());
		this.setTipoItemConfiguracaoBean(this.getTipoItemConfiguracaoService().restore(this.getTipoItemConfiguracaoBean()));
		/**
		 * Valida se o campo é diferente de null
		 * @author flavio.santana
		 * 25/10/2013 11:40
		 */
		if (this.getTipoItemConfiguracaoBean() != null) {
			this.getItemConfiguracaoBean().setNomeTipoItemConfiguracao(this.getTipoItemConfiguracaoBean().getNome());
		}

		document.executeScript("deleteAllRows()");
		HTMLForm form = document.getForm("form");
		form.setValues(this.getItemConfiguracaoBean());

		if (this.getTipoItemConfiguracaoBean() != null) {
			this.getTipoItemConfiguracaoService().restaurarGridCaracteristicas(document,
					this.getCaracteristicaService().
					consultarCaracteristicasComValoresItemConfiguracao(this.getTipoItemConfiguracaoBean().getId(), this.getItemConfiguracaoBean().getIdItemConfiguracao()));
		}
	}

	public void restoreUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		this.setItemConfiguracaoBean((IDto) document.getBean());
		this.setUsuarioDto(this.getUsuarioService().restore(this.getUsuarioDto()));
		this.getEmpregadoDto().setIdEmpregado(getUsuarioDto().getIdEmpregado()) ;
		this.setEmpregado(this.getEmpregadoService().restore(getEmpregadoDto()));

		HTMLForm form = document.getForm("form");
		form.setValues(this.getItemConfiguracaoBean());
	}

	/**
	 * Restaura o Grupo do Item de Configuracao.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restoreGrupoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);

		ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO) ((IDto) document.getBean());
		GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();

		grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());

		grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoItemConfiguracaoDTO);

		itemConfiguracaoDTO.setIdGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao());
		itemConfiguracaoDTO.setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());

		HTMLForm form = document.getForm("form");
		form.setValues(itemConfiguracaoDTO);

		document.executeScript("fecharPopupGrupo()");
	}


	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *             Metodo colocar status Data fim quando for solicitado a
	 *             exclusão do ItemConfiguracao.
	 * @author thays.araujo
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		this.setItemConfiguracaoBean((IDto) document.getBean());

		if (this.getItemConfiguracaoBean().getIdItemConfiguracao().intValue() > 0) {
			this.getItemConfiguracaoService().delete((getItemConfiguracaoBean()));
		}

		HTMLForm form = document.getForm("form");
		document.executeScript("deleteAllRows()");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}


	/**
	 * Retorna Service de ItemConfiguracao.
	 *
	 * @return ItemConfiguracaoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException, Exception {
		return (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class,null);
	}

	/**
	 * Retorna Service de TipoItemConfiguracao.
	 *
	 * @return TipoItemConfiguracaoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public TipoItemConfiguracaoService getTipoItemConfiguracaoService() throws ServiceException, Exception {
		return (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
	}

	/**
	 * Retorna Service de Caracteristica.
	 *
	 * @return CaracteristicaService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public CaracteristicaService getCaracteristicaService() throws ServiceException, Exception {
		return (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
	}

	/**
	 * Retorna Service de Unidade.
	 *
	 * @return UnidadeService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public UnidadeService getUnidadeService() throws ServiceException, Exception {
		return (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
	}

	/**
	 * Retorna Service de Usuario.
	 *
	 * @return UsuarioService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public UsuarioService getUsuarioService() throws ServiceException, Exception {
		return (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
	}

	/**
	 * Configura bean ItemConfiguracaoDTO.
	 *
	 * @param itemConfiguracaoBean
	 * @author thays.araujo
	 */
	public void setItemConfiguracaoBean(IDto itemConfiguracaoBean) {
		this.itemConfiguracaoBean = (ItemConfiguracaoDTO) itemConfiguracaoBean;
	}

	/**
	 * Retorna bean de ItemConfiguracaoDTO.
	 *
	 * @return ItemConfiguracaoDTO
	 * @author thays.araujo
	 */
	public ItemConfiguracaoDTO getItemConfiguracaoBean() {
		return itemConfiguracaoBean;
	}

	/**
	 * Retorna Bean Tipo Item Configuração.
	 *
	 * @return TipoItemConfiguracaoDTO
	 * @author thays.araujo
	 */
	public TipoItemConfiguracaoDTO getTipoItemConfiguracaoBean() {
		return this.tipoItemConfiguracaoBean;
	}

	/**
	 * Retorna Bean Usuario.
	 *
	 * @return TipoItemConfiguracaoDTO
	 * @author thays.araujo
	 */
	public UsuarioDTO getUsuarioDto() {
		return this.UsuarioDto;
	}

	/**
	 * Retorna Bean Empregado.
	 *
	 * @return TipoItemConfiguracaoDTO
	 * @author thays.araujo
	 */
	public EmpregadoDTO getEmpregadoDto() {
		return this.empregadoDTO;
	}

	/**
	 * Configura Empregado.
	 *
	 * @param empregadoDto
	 *            IDto
	 * @author thays.araujo
	 */
	public void setEmpregado(IDto empregadoDto) {
		this.empregadoDTO = (EmpregadoDTO) empregadoDto;
	}

	/**
	 * Retorna Bean Empregado.
	 *
	 * @return TipoItemConfiguracaoDTO
	 * @author thays.araujo
	 */
	public UnidadeDTO getUnidadeDto() {
		return this.unidadeDto;
	}

	/**
	 * Configura Empregado.
	 *
	 * @param empregadoDto
	 *            IDto
	 * @author thays.araujo
	 */
	public void setUnidadeDto(IDto unidadeDto) {
		this.unidadeDto = (UnidadeDTO) unidadeDto;
	}

	/**
	 * Configura Tipo Item Configuração.
	 *
	 * @param tipoItemConfiguracao
	 *            IDto
	 * @author thays.araujo
	 */
	public void setTipoItemConfiguracaoBean(IDto tipoItemConfiguracao) {
		this.tipoItemConfiguracaoBean = (TipoItemConfiguracaoDTO) tipoItemConfiguracao;
	}

	/**
	 * Configura Usuario.
	 *
	 * @param usuarioDto
	 *            IDto
	 * @author thays.araujo
	 */
	public void setUsuarioDto(IDto usuarioDto) {
		this.UsuarioDto = (UsuarioDTO) usuarioDto;
	}

	/**
	 * Retorna Service de InformacaoItemConfiguracao.
	 *
	 * @return InformacaoItemConfiguracaoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author rosana.godinho
	 */
	public InformacaoItemConfiguracaoService getInformacaoItemConfiguracaoService() throws ServiceException, Exception {
		return (InformacaoItemConfiguracaoService) ServiceLocator.getInstance().getService(InformacaoItemConfiguracaoService.class, null);
	}

	/**
	 * Retorna Service de Empregado.
	 *
	 * @return InformacaoItemConfiguracaoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author rosana.godinho
	 */
	public EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		return (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
	}


}
