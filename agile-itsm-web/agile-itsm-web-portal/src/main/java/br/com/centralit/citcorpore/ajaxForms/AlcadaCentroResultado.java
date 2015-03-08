package br.com.centralit.citcorpore.ajaxForms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AlcadaCentroResultadoService;
import br.com.centralit.citcorpore.negocio.AlcadaService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class AlcadaCentroResultado extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		AlcadaCentroResultadoDTO alcadaCentroResultadoDTO = (AlcadaCentroResultadoDTO) document.getBean();

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		// L�gica de verifica��o dos radiobuttons de acordo com a opera��o: inser��o ou atualiza��o.
		document.executeScript("$('#situacaoAtivo').attr('checked', true)");
		document.executeScript("$('#situacao').val('A')");

		if (alcadaCentroResultadoDTO != null && alcadaCentroResultadoDTO.getIdAlcadaCentroResultado() == null) {
			document.executeScript("$('#situacaoInativo').attr('disabled', true)");
		}

		document.getElementById("loading_overlay").setVisible(false);
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo os DTOs.
		AlcadaCentroResultadoDTO alcadaCentroResultadoDTO = (AlcadaCentroResultadoDTO) document.getBean();
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		CentroResultadoDTO centroResultadoDTO = new CentroResultadoDTO();
		AlcadaDTO alcadaDTO = new AlcadaDTO();

		// Obtendo os Services.
		AlcadaCentroResultadoService alcadaCentroResultadoService = (AlcadaCentroResultadoService) ServiceLocator.getInstance().getService(AlcadaCentroResultadoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
		AlcadaService alcadaService = (AlcadaService) ServiceLocator.getInstance().getService(AlcadaService.class, null);

		// Verificando se os DTOs e seus respectivos Services possuem refer�ncias.
		if (alcadaCentroResultadoDTO == null || alcadaCentroResultadoDTO.getIdAlcadaCentroResultado() == null) {
			return;
		}

		if (empregadoDTO == null || empregadoService == null) {
			return;
		}

		if (centroResultadoDTO == null || centroResultadoService == null) {
			return;
		}

		if (alcadaDTO == null || alcadaService == null) {
			return;
		}

		// Recuperando a al�ada de centro de resultado a partir do banco de dados.
		alcadaCentroResultadoDTO = (AlcadaCentroResultadoDTO) alcadaCentroResultadoService.restore(alcadaCentroResultadoDTO);

		// Recuperando o empregado.
		if (alcadaCentroResultadoDTO.getIdEmpregado() != null) {
			empregadoDTO.setIdEmpregado(alcadaCentroResultadoDTO.getIdEmpregado());
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
			alcadaCentroResultadoDTO.setNomeEmpregado(empregadoDTO.getNome());
		}

		// Recuperando o centro de resultado.
		if (alcadaCentroResultadoDTO.getIdCentroResultado() != null) {
			centroResultadoDTO.setIdCentroResultado(alcadaCentroResultadoDTO.getIdCentroResultado());
			centroResultadoDTO = (CentroResultadoDTO) centroResultadoService.restore(centroResultadoDTO);
			alcadaCentroResultadoDTO.setNomeCentroResultado(centroResultadoDTO.getNomeCentroResultado());
		}

		// Recuperando a al�ada.
		if (alcadaCentroResultadoDTO.getIdAlcada() != null) {
			alcadaDTO.setIdAlcada(alcadaCentroResultadoDTO.getIdAlcada());
			alcadaDTO = (AlcadaDTO) alcadaService.restore(alcadaDTO);
			alcadaCentroResultadoDTO.setNomeAlcada(alcadaDTO.getNomeAlcada());
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(alcadaCentroResultadoDTO);
		document.executeScript("$('#situacaoInativo').attr('disabled', false)");

		if (alcadaCentroResultadoDTO.getDataFim() == null) {
			document.executeScript("$('#situacaoAtivo').attr('checked', true)");
			document.executeScript("$('#situacao').val('A')");
		} else {
			document.executeScript("$('#situacaoInativo').attr('checked', true)");
			document.executeScript("$('#situacao').val('I')");
		}

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo o objeto (DTO - Data Transfer Object) que armazena os dados do formul�rio.
		AlcadaCentroResultadoDTO alcadaCentroResultadoDTO = (AlcadaCentroResultadoDTO) document.getBean();
		// Obtendo um objeto da camada de servi�o para realizar a persist�ncia dos dados provenientes do DTO.
		AlcadaCentroResultadoService alcadaCentroResultadoService = (AlcadaCentroResultadoService) ServiceLocator.getInstance().getService(AlcadaCentroResultadoService.class, null);
		HTMLForm form = document.getForm("form");

		// Verificando se o DTO e o Service existem.
		if (alcadaCentroResultadoDTO != null && alcadaCentroResultadoService != null) {
			/*
			 * Verificando se a al�ada de centro de resultado possui um empregado, um centro de resultado e uma al�ada associados. Caso os tenha, prossegue no fluxo persistindo o objeto sen�o o
			 * abandona.
			 */
			if (alcadaCentroResultadoDTO.getIdEmpregado() == null || alcadaCentroResultadoDTO.getIdCentroResultado() == null || alcadaCentroResultadoDTO.getIdAlcada() == null) {
				return;
			}

			// Configurando a data em que o objeto foi persistido.
			alcadaCentroResultadoDTO.setDataInicio(UtilDatas.getDataAtual());

			// Inser��o (id do DTO � nulo).
			if (alcadaCentroResultadoDTO.getIdAlcadaCentroResultado() == null) {
				/*
				 * Requisito: S� � permitida uma al�ada de centro de resultado ativa (datafim = null) para um mesmo empregado, centro de resultado e al�ada.
				 */

				/*
				 * Obtem todas as al�adas de centro de resultado j� registradas com o mesmo empregado, centro resultado e al�ada.
				 */
				List lista = (List) alcadaCentroResultadoService.find(alcadaCentroResultadoDTO);

				// Verifica se a lista existe e n�o � vazia.
				if (lista != null && !lista.isEmpty()) {
					// Itera sobre a lista
					for (int i = 0; i < lista.size(); i++) {
						// Configurando a data final de cada elemento.
						AlcadaCentroResultadoDTO aux = (AlcadaCentroResultadoDTO) lista.get(i);
						aux.setDataFim(UtilDatas.getDataAtual());
						// Persistindo a altera��o no banco de dados.
						alcadaCentroResultadoService.update(aux);
					}
				}
				// Sen�o persistir no banco de dados.
				alcadaCentroResultadoService.create(alcadaCentroResultadoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
				// Limpando o formul�rio.
				form.clear();
				// Configurando novo bean.
				document.setBean(new AlcadaCentroResultadoDTO());
				// Recarregando a p�gina.
				load(document, request, response);
			} else { // Atualiza��o (id do DTO n�o � nulo).
				String situacao = request.getParameter("situacao");

				if (situacao == null || situacao.equals("")) {
					return;
				} else {
					if (situacao.equalsIgnoreCase("a")) {
						alcadaCentroResultadoDTO.setDataFim(null);
					} else if (situacao.equalsIgnoreCase("i")) {
						alcadaCentroResultadoDTO.setDataFim(UtilDatas.getDataAtual());
					} else {
						return;
					}
				}
				// Atualizando o DTO no banco de dados.
				alcadaCentroResultadoService.update(alcadaCentroResultadoDTO);
				form.clear();
				// Configurando novo bean.
				document.setBean(new AlcadaCentroResultadoDTO());
				// Recarregando a p�gina.
				load(document, request, response);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		}
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// DTO
		AlcadaCentroResultadoDTO alcadaCentroResultadoDTO = (AlcadaCentroResultadoDTO) document.getBean();
		// Service
		AlcadaCentroResultadoService alcadaCentroResultadoService = (AlcadaCentroResultadoService) ServiceLocator.getInstance().getService(AlcadaCentroResultadoService.class, null);

		if (alcadaCentroResultadoDTO != null & alcadaCentroResultadoService != null) {
			// Id do DTO n�o � nulo nem zero.
			if (alcadaCentroResultadoDTO.getIdAlcadaCentroResultado() != null && (alcadaCentroResultadoDTO.getIdAlcadaCentroResultado().intValue() > 0)) {
				// Recuperando o DTO no banco de dados atrav�s de seu ID.
				alcadaCentroResultadoDTO = (AlcadaCentroResultadoDTO) alcadaCentroResultadoService.restore(alcadaCentroResultadoDTO);
				// Configurando a data final.
				alcadaCentroResultadoDTO.setDataFim(UtilDatas.getDataAtual());
				// Atualizando o DTO.
				alcadaCentroResultadoService.update(alcadaCentroResultadoDTO);

				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
				HTMLForm form = document.getForm("form");
				form.clear();
				document.executeScript("$('#situacaoAtivo').attr('checked', true)");
				document.executeScript("$('#situacao').val('A')");
			}
		}
	}

	@Override
	public Class getBeanClass() {
		return AlcadaCentroResultadoDTO.class;
	}
}
