package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
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
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CentroResultadoAntigo extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		CentroResultadoDTO centroResultadoDTO = (CentroResultadoDTO) document.getBean();

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		// L�gica de verifica��o dos radiobuttons de acordo com a opera��o: inser��o ou atualiza��o.
		document.executeScript("$('#permiteRequisicaoProdutoSim').attr('checked', true)");
		document.executeScript("$('#situacaoAtivo').attr('checked', true)");

		if (centroResultadoDTO != null && centroResultadoDTO.getIdCentroResultado() == null) {
			document.executeScript("$('#situacaoInativo').attr('disabled', true)");
		}
		document.executeScript("GRID_ALCADAS.deleteAllRows();");

		AlcadaService alcadaService = (AlcadaService) ServiceLocator.getInstance().getService(AlcadaService.class, WebUtil.getUsuarioSistema(request));
		Collection<AlcadaDTO> colAlcadas = alcadaService.list();
		request.setAttribute("colAlcadas", colAlcadas);
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CentroResultadoDTO centroResultadoDTO = (CentroResultadoDTO) document.getBean();
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
		if (centroResultadoDTO == null || centroResultadoDTO.getIdCentroResultado() == null)
			return;

		centroResultadoDTO = (CentroResultadoDTO) centroResultadoService.restore(centroResultadoDTO);
		if (centroResultadoDTO != null) {
			if (centroResultadoDTO.getIdCentroResultadoPai() != null) {
				CentroResultadoDTO centroResultadoPaiDTO = new CentroResultadoDTO();
				centroResultadoPaiDTO.setIdCentroResultado(centroResultadoDTO.getIdCentroResultadoPai());
				centroResultadoPaiDTO = (CentroResultadoDTO) centroResultadoService.restore(centroResultadoPaiDTO);
				centroResultadoDTO.setNomeCentroResultadoPai(centroResultadoPaiDTO.getNomeCentroResultado());
			}

			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(centroResultadoDTO);
			document.executeScript("$('#situacaoInativo').attr('disabled', false)");

			AlcadaCentroResultadoService alcadaCentroResultadoService = (AlcadaCentroResultadoService) ServiceLocator.getInstance().getService(AlcadaCentroResultadoService.class, null);
			Collection<AlcadaCentroResultadoDTO> colAlcadas = alcadaCentroResultadoService.findByIdCentroResultado(centroResultadoDTO.getIdCentroResultado());
			document.executeScript("GRID_ALCADAS.deleteAllRows();");
			if (colAlcadas != null) {
				EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
				int i = 0;
				for (AlcadaCentroResultadoDTO alcadaDto : colAlcadas) {
					EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(alcadaDto.getIdEmpregado());
					if (empregadoDto != null)
						alcadaDto.setNomeEmpregado(empregadoDto.getNome());
					i++;
					document.executeScript("GRID_ALCADAS.addRow()");
					alcadaDto.setSequencia(i);
					document.executeScript("seqAlcada = NumberUtil.zerosAEsquerda(" + i + ",5)");
					document.executeScript("exibeAlcada('" + br.com.citframework.util.WebUtil.serializeObject(alcadaDto) + "')");
				}
			}
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo o DTO
		CentroResultadoDTO centroResultadoDTO = (CentroResultadoDTO) document.getBean();
		// Obtendo um Service
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
		Collection<AlcadaCentroResultadoDTO> colAlcadas = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AlcadaCentroResultadoDTO.class, "colAlcadas_Serialize", request);

		if (colAlcadas != null) {
			centroResultadoDTO.setColAlcadas(colAlcadas);
		}

		// Verificando a exist�ncia do centro de resultado
		if (centroResultadoDTO != null && centroResultadoService != null) {
			// Inser��o
			if (centroResultadoDTO.getIdCentroResultado() == null) {
				// Verificar se um DTO com o mesmo nome j� existe
				List lista = (List) centroResultadoService.find(centroResultadoDTO);

				if (lista != null && !lista.isEmpty()) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
					return;
				}

				if (centroResultadoDTO.getIdCentroResultadoPai() != null) {
					Integer idCentroResuladoPai = 0;
					idCentroResuladoPai = centroResultadoDTO.getIdCentroResultadoPai();
					if (centroResultadoDTO.getIdCentroResultado() != null) {
						if (centroResultadoDTO.getIdCentroResultado() == idCentroResuladoPai.intValue()) {
							document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroPaiIgual"));
							return;
						}
					}

				}

				centroResultadoDTO.setSituacao("A");
				centroResultadoService.createAntigo(centroResultadoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else { // Atualiza��o
				if (centroResultadoDTO.getIdCentroResultadoPai() != null) {
					Integer idCentroResuladoPai = 0;
					idCentroResuladoPai = centroResultadoDTO.getIdCentroResultadoPai();
					if (centroResultadoDTO.getIdCentroResultado() == idCentroResuladoPai.intValue()) {
						document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroPaiIgual"));
						return;
					}
				}
				centroResultadoService.updateAntigo(centroResultadoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}

			HTMLForm form = document.getForm("form");
			// Limpando o formul�rio
			form.clear();
			// Configurando objeto de transfer�ncia de dados ao documento de vis�o (p�gina .jsp)
			document.setBean(new CentroResultadoDTO());
			// Carregando a p�gina
			load(document, request, response);
		}
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CentroResultadoDTO centroResultadoDTO = (CentroResultadoDTO) document.getBean();
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
		AlcadaCentroResultadoService alcadaCentroResultadoService = (AlcadaCentroResultadoService) ServiceLocator.getInstance().getService(AlcadaCentroResultadoService.class, null);

		if (centroResultadoDTO != null && centroResultadoDTO.getIdCentroResultado() != null && centroResultadoService != null) {
			Collection<CentroResultadoDTO> centrosResultadoDependentes = centroResultadoService.findByIdPai(centroResultadoDTO.getIdCentroResultado());

			// Excluir apenas centros de resultado que n�o possuem centros de resultado dependentes (filhos).
			if (centrosResultadoDependentes != null) {

				if (centrosResultadoDependentes.size() > 0) {
					document.alert(UtilI18N.internacionaliza(request, "centroResultado.naoPodeSerExcluido.possuiRelacionamentos") );					
					return;
				}
			}

			if (alcadaCentroResultadoService.verificarVinculoCentroResultado(centroResultadoDTO.getIdCentroResultado())) {
//				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			}

			// Exclui o centro resultado
			centroResultadoService.delete(centroResultadoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			// Obtem o formul�rio na p�gina .jsp associado a esta Action.

			HTMLForm form = document.getForm("form");
			// Limpa o formul�rio
			form.clear();
			// Configura o objeto de transfer�ncia de dados ao documento de vis�o (p�gina .jsp)
			document.setBean(new CentroResultadoDTO());
			// Carrega a p�gina
			load(document, request, response);
		}
	}

	@Override
	public Class getBeanClass() {
		return CentroResultadoDTO.class;
	}

	/**
	 * M�todo respons�vel por apresentar a hierarquia de centros de resultado para o usu�rio da aplica��o.
	 * 
	 * @author thiagomonteiro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void visualizarHierarquiaCentrosResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Verificar se o elemento div onde a hierarquia ser� construida existe
		if (document.getElementById("divApresentacaoHierarquiaCentroResultado") != null) {
			// Definir uma lista para iniciar a representa��o da hierarquia.
			String htmlHierarquia = "<ul id=\"hierarquiaCentroResultado\" class=\"filetree treeview\">";
			// Obter o objeto da camada de servi�os para realizar consultas no banco de dados.
			CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
			// Obter a lista de centros de resultado sem pai (raizes ou elementos de n�vel mais alto na hierarquia - sem ancestrais).
			List centrosResultado = (List) centroResultadoService.findSemPai();
			// Gerar HTML para o primeiro n�vel da hierarquia.
			htmlHierarquia += construirHierarquiaCentrosResultado(centroResultadoService, centrosResultado, 1);
			// Finalizar a constru��o da hierarquia.
			htmlHierarquia += "</ul>";
			// Atribuir o HTML gerado a divApresentacaoHierarquiaCentroResultado.
			document.getElementById("divApresentacaoHierarquiaCentroResultado").setInnerHTML(htmlHierarquia);
			// Gerar a apresenta��o na forma de uma �rvore.
			document.executeScript("$(\"#hierarquiaCentroResultado\").treeview();");
		}
	}

	/**
	 * M�todo recursivo para constru��o da hierarquia de centros de resultado.
	 * 
	 * @author thiago.monteiro
	 * @param centroResultadoService
	 * @param centrosResultado
	 * @param nivel
	 * @return
	 * @throws Exception
	 */
	public String construirHierarquiaCentrosResultado(CentroResultadoService centroResultadoService, List centrosResultado, int nivel) throws Exception {
		// Definir objeto que ir� armazenar o HTML a ser gerado
		// para cada n�vel da hierarquia de centros de resultado.
		String htmlHierarquia = "";

		// Verificar se o servico e a lista de centros de resultado existe
		if (centroResultadoService != null && centrosResultado != null) {
			// Verificar se a lista passada n�o � vazia.
			if (!centrosResultado.isEmpty() ) {
				// Iterar sobre a lista de centros de resultado.
				for (int i = 0; i < centrosResultado.size(); i++) {
					// Obter o centro de resultado na itera��o.
					CentroResultadoDTO centroResultadoDTO = (CentroResultadoDTO) centrosResultado.get(i);

					// Verificar se o centro de resultado possui filhos.
					if (centroResultadoService.temFilhos(centroResultadoDTO.getIdCentroResultado() ) ) {
						// Se possuir, construir uma nova hierarquia.
						// Criar uma nova lista de centros de resultado.
						htmlHierarquia += String.format("<li><a href=\"#%d\">%s</a><ul></li>", centroResultadoDTO.getIdCentroResultado(), centroResultadoDTO.getNomeCentroResultado() );
						// Obter todos os centros de resultado dependentes.
						List centrosResultadoAux = (List) centroResultadoService.findByIdPai(centroResultadoDTO.getIdCentroResultado() );
						// Construir a hieraquia com base no centro de resultado atual informando seu n�vel.
						htmlHierarquia += construirHierarquiaCentrosResultado(centroResultadoService, centrosResultadoAux, ++nivel);
						// Finalizar a defini��o do n�vel atual.
						htmlHierarquia += "</li></ul>";
						// Voltar para o n�vel superior.
						--nivel;
					} else {
						// Sen�o, renderizar o centro de resultado.
						htmlHierarquia += String.format("<li><a href=\"#%d\">%s</a></li>", centroResultadoDTO.getIdCentroResultado(), centroResultadoDTO.getNomeCentroResultado() );
					}
				}
			}
		}
		// Retorna a hierarquia completa.
		return htmlHierarquia;
	}
}