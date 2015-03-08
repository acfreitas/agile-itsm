package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.HistoricoRespCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.ProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.HistoricoRespCentroResultadoService;
import br.com.centralit.citcorpore.negocio.ProcessoNegocioService;
import br.com.centralit.citcorpore.negocio.ResponsavelCentroResultadoProcessoService;
import br.com.centralit.citcorpore.negocio.ResponsavelCentroResultadoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CentroResultado extends AjaxFormAction {

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
		document.executeScript("GRID_RESPONSAVEIS.deleteAllRows();");

        HTMLTable tblHistorico = document.getTableById("tblHistorico");
        tblHistorico.deleteAllRows();

        ProcessoNegocioService processoNegocioService = (ProcessoNegocioService) ServiceLocator.getInstance().getService(ProcessoNegocioService.class, WebUtil.getUsuarioSistema(request));
		Collection<ProcessoNegocioDTO> colProcessosNegocio = processoNegocioService.list();
		request.setAttribute("colProcessosNegocio", colProcessosNegocio);
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

			ResponsavelCentroResultadoService responsavelCentroResultadoService = (ResponsavelCentroResultadoService) ServiceLocator.getInstance().getService(ResponsavelCentroResultadoService.class, null);
			Collection<ResponsavelCentroResultadoDTO> colResponsaveis = responsavelCentroResultadoService.findByIdCentroResultado(centroResultadoDTO.getIdCentroResultado());
			document.executeScript("GRID_RESPONSAVEIS.deleteAllRows();");
			if (colResponsaveis != null) {
				ResponsavelCentroResultadoProcessoService responsavelCentroResultadoProcessoService = (ResponsavelCentroResultadoProcessoService) ServiceLocator.getInstance().getService(ResponsavelCentroResultadoProcessoService.class, null);
				EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
				int i = 0;
				for (ResponsavelCentroResultadoDTO responsavelDto : colResponsaveis) {
					EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(responsavelDto.getIdResponsavel());
					if (empregadoDto != null)
						responsavelDto.setNomeEmpregado(empregadoDto.getNome());
					Collection<ResponsavelCentroResultadoProcessoDTO> colProcessos = responsavelCentroResultadoProcessoService.findByIdCentroResultadoAndIdResponsavel(responsavelDto.getIdCentroResultado(), responsavelDto.getIdResponsavel());
					if (colProcessos != null) {
						String idProcessoNegocioStr = "";
						int p = 0;
						for (ResponsavelCentroResultadoProcessoDTO processoDto: colProcessos) {
							if (p > 0)
								idProcessoNegocioStr += ",";
							idProcessoNegocioStr += ""+processoDto.getIdProcessoNegocio();
							p++;
						}
						responsavelDto.setIdProcessoNegocioStr(idProcessoNegocioStr);
						
					}
					i++;
					document.executeScript("GRID_RESPONSAVEIS.addRow()");
					responsavelDto.setSequencia(i);
					document.executeScript("seqResponsavel = NumberUtil.zerosAEsquerda(" + i + ",5)");
					document.executeScript("exibeResponsavel('" + br.com.citframework.util.WebUtil.serializeObject(responsavelDto, WebUtil.getLanguage(request)) + "')");
				}
			}
			
	        HTMLTable tblHistorico = document.getTableById("tblHistorico");
	        tblHistorico.deleteAllRows();
	        
	        HistoricoRespCentroResultadoService historicoRespCentroResultadoService = (HistoricoRespCentroResultadoService) ServiceLocator.getInstance().getService(HistoricoRespCentroResultadoService.class, WebUtil.getUsuarioSistema(request));
	        Collection<HistoricoRespCentroResultadoDTO> colHistorico = historicoRespCentroResultadoService.findByIdCentroResultado(centroResultadoDTO.getIdCentroResultado());
	        if (colHistorico != null) {
	        	for (HistoricoRespCentroResultadoDTO historicoDto : colHistorico) {
					EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
					EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(historicoDto.getIdResponsavel());
					if (empregadoDto != null)
						historicoDto.setNomeEmpregado(empregadoDto.getNome());

				}
	        	tblHistorico.addRowsByCollection(colHistorico, 
	                                                new String[] {"nomeEmpregado","dataInicio","dataFim"}, 
	                                                null, 
	                                                "", 
	                                                null,
	                                                null, 
	                                                null);  
	        }
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo o DTO
		CentroResultadoDTO centroResultadoDTO = (CentroResultadoDTO) document.getBean();
		// Obtendo um Service
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
		Collection<ResponsavelCentroResultadoDTO> colResponsaveis = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ResponsavelCentroResultadoDTO.class, "colResponsaveis_Serialize", request);
		if (colResponsaveis != null) {
			for (ResponsavelCentroResultadoDTO responsavelCentroResultadoDto : colResponsaveis) {
				if (responsavelCentroResultadoDto.getIdProcessoNegocioStr() != null) {
					String[] ids = responsavelCentroResultadoDto.getIdProcessoNegocioStr().split(",");
					Integer[] idProcessoNegocio = new Integer[ids.length];
					for (int i = 0; i < ids.length; i++) {
						idProcessoNegocio[i] = new Integer(ids[i]);
					}
					responsavelCentroResultadoDto.setIdProcessoNegocio(idProcessoNegocio);
				}
			}
			centroResultadoDTO.setColResponsaveis(colResponsaveis);
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
				centroResultadoService.create(centroResultadoDTO);
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
				centroResultadoService.update(centroResultadoDTO);
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
		ResponsavelCentroResultadoService responsavelCentroResultadoService = (ResponsavelCentroResultadoService) ServiceLocator.getInstance().getService(ResponsavelCentroResultadoService.class, null);

		if (centroResultadoDTO != null && centroResultadoDTO.getIdCentroResultado() != null && centroResultadoService != null) {
			Collection<CentroResultadoDTO> centrosResultadoDependentes = centroResultadoService.findByIdPai(centroResultadoDTO.getIdCentroResultado());

			// Excluir apenas centros de resultado que n�o possuem centros de resultado dependentes (filhos).
			if (centrosResultadoDependentes != null) {

				if (centrosResultadoDependentes.size() > 0) {
					document.alert(UtilI18N.internacionaliza(request, "centroResultado.naoPodeSerExcluido.possuiRelacionamentos") );					
					return;
				}
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