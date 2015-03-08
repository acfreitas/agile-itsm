/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * Action de Característica.
 * 
 * @author valdoilo.damasceno
 */
public class Caracteristica extends AjaxFormAction {

	/** Bean de Caracteristica. */
	private CaracteristicaDTO caracteristicaBean;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	/**
	 * Inclui Nova Característica.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setCaracteristicaBean((CaracteristicaDTO) document.getBean());
		CaracteristicaDTO dto = new CaracteristicaDTO();
		
		if (this.getCaracteristicaBean().getIdCaracteristica() == null || this.getCaracteristicaBean().getIdCaracteristica() == 0) {
			if (getCaracteristicaService().verificarSeCaracteristicaExiste(getCaracteristicaBean())) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			this.getCaracteristicaService().create(this.getCaracteristicaBean(), request);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			CITCorporeUtil.limparFormulario(document);
		} else {
			if (getCaracteristicaService().verificarSeCaracteristicaExiste(getCaracteristicaBean())) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			dto = (CaracteristicaDTO) this.getCaracteristicaService().restore(this.getCaracteristicaBean());
			this.getCaracteristicaBean().setSistema(dto.getSistema());
			this.getCaracteristicaService().update(this.getCaracteristicaBean());
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			CITCorporeUtil.limparFormulario(document);
		}
	}

	/**
	 * Recupera característica.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setCaracteristicaBean((CaracteristicaDTO) document.getBean());
		this.setCaracteristicaBean((CaracteristicaDTO) this.getCaracteristicaService().restore(this.getCaracteristicaBean()));
		this.bloquearDesbloquearTag(document);
		HTMLForm form = CITCorporeUtil.limparFormulario(document);
		form.setValues(this.getCaracteristicaBean());
	}

	/**
	 * Exclui característica.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setCaracteristicaBean((CaracteristicaDTO) document.getBean());

		if (this.getCaracteristicaBean().getIdCaracteristica() != null && this.getCaracteristicaBean().getIdCaracteristica() != 0) {

			this.setCaracteristicaBean(this.getCaracteristicaService().restore(this.getCaracteristicaBean()));

			if (getCaracteristicaBean().getSistema() != null) {
				if (!this.getCaracteristicaBean().getSistema().equalsIgnoreCase("N")) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
					return;
				} else {

					try {
						this.getCaracteristicaService().excluirCaracteristica(this.getCaracteristicaBean());
						document.alert(UtilI18N.internacionaliza(request, "MSG07"));
						document.executeScript("bloquearTag(+" + false + ")");
						CITCorporeUtil.limparFormulario(document);
					} catch (LogicException logicException) {
						document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
						logicException.printStackTrace();
					}

				}
			} else {
				try {
					this.getCaracteristicaService().excluirCaracteristica(this.getCaracteristicaBean());
					document.alert(UtilI18N.internacionaliza(request, "MSG07"));
					CITCorporeUtil.limparFormulario(document);
					document.executeScript("bloquearTag(+" + false + ")");
				} catch (LogicException e) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * Bloquea ou desbloqueia TAG.
	 * 
	 * @param document
	 * @author valdoilo.damasceno
	 */
	private void bloquearDesbloquearTag(DocumentHTML document) {
		if (this.getCaracteristicaBean().getSistema() != "N") {
			document.executeScript("bloquearTag(+" + true + ")");
		} else {
			document.executeScript("bloquearTag(+" + false + ")");
		}
	}

	/**
	 * Retorna Service de Característica.
	 * 
	 * @return CaracteristicaService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	private CaracteristicaService getCaracteristicaService() throws ServiceException, Exception {
		return (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
	}

	/**
	 * Retorna bean de característica.
	 * 
	 * @return valor do atributo caracteristicaBean.
	 * @author valdoilo.damasceno
	 */
	public CaracteristicaDTO getCaracteristicaBean() {
		return caracteristicaBean;
	}

	/**
	 * Define valor do atributo caracteristicaBean.
	 * 
	 * @param caracteristicaBean
	 * @author valdoilo.damasceno
	 */
	public void setCaracteristicaBean(IDto caracteristicaBean) {
		this.caracteristicaBean = (CaracteristicaDTO) caracteristicaBean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return CaracteristicaDTO.class;
	}
}
