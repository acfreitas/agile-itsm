/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.PatrimonioDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.InformacaoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.WebUtil;

/**
 * @author Vadoilo Damasceno
 * 
 */
@SuppressWarnings("rawtypes")
public class Patrimonio extends AjaxFormAction {

	private UsuarioDTO UsuarioDto = new UsuarioDTO();

	private EmpregadoDTO empregadoDTO = new EmpregadoDTO();

	private UnidadeDTO unidadeDto = new UnidadeDTO();

	@Override
	public Class getBeanClass() {
		return PatrimonioDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String parametroIdTipoItemConfiguracao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PATRIMONIO_IDTIPOITEMCONFIGURACAO, null);

		if (parametroIdTipoItemConfiguracao != null) {

			document.executeScript("ocultarItemConfiguracaoFilho()");
		} else {

			document.alert("Tipo de Item Configuração Patrimônio não definido.");

			return;
		}

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String parametroIdTipoItemConfiguracao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PATRIMONIO_IDTIPOITEMCONFIGURACAO, null);

		if (parametroIdTipoItemConfiguracao == null || StringUtils.isBlank(parametroIdTipoItemConfiguracao)) {

			document.alert("Tipo de Item Configuração Patrimônio não definido.");

			return;
		}

		PatrimonioDTO patrimonioDto = new PatrimonioDTO();

		patrimonioDto = (PatrimonioDTO) document.getBean();

		ItemConfiguracaoService itemService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

		ItemConfiguracaoDTO itemConfiguracaoPaiDto = new ItemConfiguracaoDTO();

		ItemConfiguracaoDTO itemConfiguracaoFilhoDto = new ItemConfiguracaoDTO();

		TipoItemConfiguracaoDTO tipoItemConfiguracaoPai = new TipoItemConfiguracaoDTO();

		TipoItemConfiguracaoDTO tipoItemConfiguracaoFilho = new TipoItemConfiguracaoDTO();

		tipoItemConfiguracaoPai.setCaracteristicas((List) WebUtil.deserializeCollectionFromRequest(CaracteristicaDTO.class, "caracteristicasSerializadas", request));

		tipoItemConfiguracaoFilho.setCaracteristicas((List) WebUtil.deserializeCollectionFromRequest(CaracteristicaDTO.class, "caracteristicasSerializadasItemFilho", request));

		itemConfiguracaoPaiDto.setTipoItemConfiguracaoSerializadas(tipoItemConfiguracaoPai);

		itemConfiguracaoFilhoDto.setTipoItemConfiguracaoSerializadas(tipoItemConfiguracaoFilho);

		if (patrimonioDto.getIdItemConfiguracao() != null) {

			if (patrimonioDto.getIdItemConfiguracao() != null && patrimonioDto.getIdItemConfiguracaoFilho() == null) {

				itemConfiguracaoPaiDto.setIdItemConfiguracao(patrimonioDto.getIdItemConfiguracao());

				itemService.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracaoPaiDto, null, null);

				document.alert("Patrimônio gravado com sucesso.");

			}

			if (patrimonioDto.getIdItemConfiguracaoFilho() != null) {

				itemConfiguracaoPaiDto.setIdItemConfiguracao(patrimonioDto.getIdItemConfiguracao());

				itemConfiguracaoFilhoDto.setIdItemConfiguracao(patrimonioDto.getIdItemConfiguracaoFilho());

				itemService.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracaoPaiDto, null, null);

				itemService.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracaoFilhoDto, null, null);

				document.alert("Patrimônio gravado com sucesso.");

			}

		}

		CITCorporeUtil.limparFormulario(document);
		document.executeScript("limpar()");
	}

	/**
	 * Restaura o Tipo de Item Configuração e carrega a Grid de Características Ativas.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void restoreTipoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String parametroIdTipoItemConfiguracao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PATRIMONIO_IDTIPOITEMCONFIGURACAO, null);

		if (parametroIdTipoItemConfiguracao == null || StringUtils.isBlank(parametroIdTipoItemConfiguracao)) {

			document.alert("Tipo de Item Configuração Patrimônio não definido.");

			return;
		}

		TipoItemConfiguracaoService tipoService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);

		CaracteristicaService caracteristicaService = (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);

		PatrimonioDTO patrimonioDto = new PatrimonioDTO();

		patrimonioDto = (PatrimonioDTO) document.getBean();

		document.executeScript("deleteAllRows()");

		document.executeScript("deleteAllRowsItemFilho()");

		if (patrimonioDto.getIdItemConfiguracao() == null) {

			tipoService.restaurarGridCaracteristicas(document, caracteristicaService.consultarCaracteristicasAtivas(Integer.parseInt(parametroIdTipoItemConfiguracao)));

		} else {

			if (patrimonioDto.getIdItemConfiguracao() != null) {

				tipoService.restaurarGridCaracteristicas(document,
						caracteristicaService.consultarCaracteristicasComValoresItemConfiguracao(Integer.parseInt(parametroIdTipoItemConfiguracao), patrimonioDto.getIdItemConfiguracao()));

			}

			if (patrimonioDto.getIdItemConfiguracaoFilho() != null) {

				tipoService.restaurarGridCaracteristicasItemConfiguracaoFilho(document, caracteristicaService.consultarCaracteristicasComValoresItemConfiguracao(32, patrimonioDto.getIdItemConfiguracaoFilho()));

			}

		}

		document.executeScript("fecharPopup()");
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
		return (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
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
