package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.ResultResponsaveisDTO;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmailService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoService;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author thays.araujo
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Grupo extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		document.focusInFirstActivateField(null);

		preencherComboPerfilAcessoGrupo(document, request, response);

		TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);

		ArrayList<TipoFluxoDTO> tiposFluxos = (ArrayList) tipoFluxoService.list();

		request.setAttribute("tiposFluxos", tiposFluxos);

		document.getElementById("divListaContratos").setVisible(false);
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
			FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
			Collection colContratos = contratoService.list();
			String bufferContratos = "<b" + UtilI18N.internacionaliza(request, "citcorpore.comum.acessoAosContratos") + ":</b> <br><table>";
			if (colContratos != null) {
				for (Iterator it = colContratos.iterator(); it.hasNext();) {
					ContratoDTO contratoDto = (ContratoDTO) it.next();
					if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
						String nomeCliente = "";
						String nomeForn = "";
						ClienteDTO clienteDto = new ClienteDTO();
						clienteDto.setIdCliente(contratoDto.getIdCliente());
						clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
						if (clienteDto != null) {
							nomeCliente = clienteDto.getNomeRazaoSocial();
						}
						FornecedorDTO fornecedorDto = new FornecedorDTO();
						fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
						fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
						if (fornecedorDto != null) {
							nomeForn = fornecedorDto.getRazaoSocial();
						}

						String situacao = "";
						if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoAtiva");
						}
						if (contratoDto.getSituacao().equalsIgnoreCase("C")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoCancelado");
						}
						if (contratoDto.getSituacao().equalsIgnoreCase("F")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoFinalizado");
						}
						if (contratoDto.getSituacao().equalsIgnoreCase("P")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoParalisado");
						}

						String checked = "";
						// if (this.getEmpregadoBean().getIdContrato() != null){
						// for(int i = 0; i < this.getEmpregadoBean().getIdContrato().length; i++){
						// if (this.getEmpregadoBean().getIdContrato()[i].intValue() == contratoDto.getIdContrato().intValue()){
						// checked = " checked=checked ";
						// break;
						// }
						// }
						// }

						bufferContratos += "<tr>";
						bufferContratos += "<td>";
						bufferContratos += "<input type='checkbox' name='idContrato' id='idContrato_" + contratoDto.getIdContrato() + "' value='0" + contratoDto.getIdContrato() + "' " + checked
								+ "/> Número: " + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ") - " + situacao;
						bufferContratos += "</td>";
						bufferContratos += "</tr>";
					}
				}
			}
			bufferContratos += "</table>";
			document.getElementById("fldListaContratos").setInnerHTML(bufferContratos);
			document.getElementById("divListaContratos").setVisible(true);
		}
	}

	/**
	 * Inclui novo Grupo.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupoDto = (GrupoDTO) document.getBean();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);

		ArrayList<TipoFluxoDTO> tiposFluxos = (ArrayList) tipoFluxoService.list();
		ArrayList<PermissoesFluxoDTO> permissoesFluxos = new ArrayList<PermissoesFluxoDTO>();

		if (tiposFluxos != null) {
			for (Iterator it = tiposFluxos.iterator(); it.hasNext();) {
				TipoFluxoDTO tipoFluxoDto = (TipoFluxoDTO) it.next();
				PermissoesFluxoDTO permissoesFluxoDTO = new PermissoesFluxoDTO();
				String criacao = request.getParameter("C_" + tipoFluxoDto.getIdTipoFluxo());
				if (criacao == null) {
					criacao = "N";
				}
				String execucao = request.getParameter("E_" + tipoFluxoDto.getIdTipoFluxo());
				if (execucao == null) {
					execucao = "N";
				}
				String delegar = request.getParameter("D_" + tipoFluxoDto.getIdTipoFluxo());
				if (delegar == null) {
					delegar = "N";
				}
				String suspensao = request.getParameter("S_" + tipoFluxoDto.getIdTipoFluxo());
				if (suspensao == null) {
					suspensao = "N";
				}
				String reativar = request.getParameter("R_" + tipoFluxoDto.getIdTipoFluxo());
				if (reativar == null) {
					reativar = "N";
				}
				String alterarSLA = request.getParameter("A_" + tipoFluxoDto.getIdTipoFluxo());
				if (alterarSLA == null) {
					alterarSLA = "N";
				}
				String reabrir = request.getParameter("X_" + tipoFluxoDto.getIdTipoFluxo());
				if (reabrir == null) {
					reabrir = "N";
				}
				String cancelar = request.getParameter("N_" + tipoFluxoDto.getIdTipoFluxo());
				if (cancelar == null) {
					cancelar = "N";
				}

				permissoesFluxoDTO.setIdTipoFluxo(tipoFluxoDto.getIdTipoFluxo());
				permissoesFluxoDTO.setCriar(criacao);
				permissoesFluxoDTO.setExecutar(execucao);
				permissoesFluxoDTO.setDelegar(delegar);
				permissoesFluxoDTO.setSuspender(suspensao);
				permissoesFluxoDTO.setReativar(reativar);
				permissoesFluxoDTO.setAlterarSLA(alterarSLA);
				permissoesFluxoDTO.setReabrir(reabrir);
				permissoesFluxoDTO.setCancelar(cancelar);
				permissoesFluxos.add(permissoesFluxoDTO);
			}
		}
		grupoDto.setPermissoesFluxos(permissoesFluxos);

		if (grupoDto.getSigla() != null) {
			grupoDto.setSigla(grupoDto.getSigla().trim());
		}

		boolean isCriado = false;
		boolean isAlterado = false;

		if (grupoDto.getIdGrupo() == null) {
			if (grupoService.verificarSeGrupoExiste(grupoDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			} else {				
				grupoDto.setIdEmpresa(WebUtil.getIdEmpresa(request));
				grupoService.create(grupoDto, request);
				//document.executeScript("contEmpregado = 0;");
				isCriado = true;
			}
		} else {
			if (grupoService.verificarSeGrupoExiste(grupoDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			} else {
//			    for(GrupoEmpregadoDTO grupoempregadoaux : grupoempregado){	
//		    }
				//gravarEmail(document, request, response);
				grupoService.update(grupoDto, request);
				//document.executeScript("contEmpregado = 0;");
				isAlterado = true;
			}
		}
		document.executeScript("deleteAllRows()");
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_GRUPO()");
		document.executeScript("JANELA_AGUARDE_MENU.hide()");

		if (isCriado) {
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}
		if (isAlterado) {
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
	}

	/**
	 * Recupera Grupo.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		GrupoDTO grupo = (GrupoDTO) document.getBean();
		PerfilAcessoGrupoDTO perfilAcessoGrupoDTO = new PerfilAcessoGrupoDTO();

		// GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		// EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
		GrupoEmailService grupoEmailService = (GrupoEmailService) ServiceLocator.getInstance().getService(GrupoEmailService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

		grupo = (GrupoDTO) grupoService.restore(grupo);

		perfilAcessoGrupoDTO.setIdGrupo(grupo.getIdGrupo());
		// grupoEmpregadoDTO.setIdGrupo(grupo.getIdGrupo());

		PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupoDTO);

		this.preencherComboPerfilAcessoGrupo(document, request, response);
		this.preencherComboTipoFluxo(document, request, response);

		if (perfilAcessoGrupo != null) {
			grupo.setIdPerfilAcessoGrupo(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
		}

		document.executeScript("deleteAllRows();");
		HTMLForm form = document.getForm("form");
		form.clear();


		
		//Collection<GrupoEmpregadoDTO> grupoDeEmpregados = (Collection<GrupoEmpregadoDTO>) grupoEmpregadoService.findGrupoAndEmpregadoByIdGrupo(grupo.getIdGrupo());
		
		
		Collection<GrupoEmailDTO> grupoDeEmails = (Collection<GrupoEmailDTO>) grupoEmailService.findByIdGrupo(grupo.getIdGrupo());

		Collection<PermissoesFluxoDTO> permissaoGrupo = (Collection<PermissoesFluxoDTO>) permissoesFluxoService.findByIdGrupo(grupo.getIdGrupo());

		document.executeScript("contEmpregado = 0;");
		document.executeScript("contEmail = 0;");
		if (permissaoGrupo != null && !permissaoGrupo.isEmpty()) {
			for (PermissoesFluxoDTO permissao : permissaoGrupo) {
				if (permissao.getCriar() != null && permissao.getCriar().equalsIgnoreCase("S")) {
					document.getCheckboxById("C_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getExecutar() != null && permissao.getExecutar().equalsIgnoreCase("S")) {
					document.getCheckboxById("E_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getDelegar() != null && permissao.getDelegar().equalsIgnoreCase("S")) {
					document.getCheckboxById("D_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getSuspender() != null && permissao.getSuspender().equalsIgnoreCase("S")) {
					document.getCheckboxById("S_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getReativar() != null && permissao.getReativar().equalsIgnoreCase("S")) {
					document.getCheckboxById("R_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getAlterarSLA() != null && permissao.getAlterarSLA().equalsIgnoreCase("S")) {
					document.getCheckboxById("A_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getReabrir() != null && permissao.getReabrir().equalsIgnoreCase("S")) {
					document.getCheckboxById("X_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
				if (permissao.getCancelar() != null && permissao.getCancelar().equalsIgnoreCase("S")) {
					document.getCheckboxById("N_" + permissao.getIdTipoFluxo()).setChecked(true);
				}
			}
		}

        //Insere elementos na tabela empregado
		Integer itensPorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		Integer totalPaginas = grupoEmpregadoService.calculaTotalPaginas(itensPorPagina, grupo.getIdGrupo());
		Integer paginaSelecionadaColaborador = grupo.getPaginaSelecionadaColaborador();
        if (paginaSelecionadaColaborador == null) {
        	paginaSelecionadaColaborador = 1;
        }
		//if(totalPaginas>1){
	        paginacaoColaborador(totalPaginas,paginaSelecionadaColaborador,request, document);
	    //}
		preencheTabelaColaborador(document, request, response);

		if (grupoDeEmails != null && !grupoDeEmails.isEmpty()) {
			for (GrupoEmailDTO grupoEmailBean : grupoDeEmails) {
				if (grupoEmailBean.getIdEmpregado() == null) {
					grupoEmailBean.setIdEmpregado(0);
				}
				document.executeScript("addLinhaTabelaEmail(" + grupoEmailBean.getIdEmpregado() + ", '" + StringEscapeUtils.escapeJavaScript(grupoEmailBean.getNome()) + "', '"
						+ StringEscapeUtils.escapeJavaScript(grupoEmailBean.getEmail()) + "',  " + false + ");");

				document.executeScript("exibirGrid1();");
			}
		}

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
			Collection col = contratosGruposService.findByIdGrupo(grupo.getIdGrupo());

			if (col != null && col.size() > 0) {
				for (Iterator it = col.iterator(); it.hasNext();) {

					ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
					document.getCheckboxById("idContrato_" + contratosGruposDTO.getIdContrato()).setValue("0" + contratosGruposDTO.getIdContrato());

				}
			}
		}

		form.setValues(grupo);

		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}

	/**
	 * Exclui Grupo atribuindo sua data fim em Grupo.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, WebUtil.getUsuarioSistema(request));
		if (grupo.getIdGrupo().intValue() > 0) {
			grupoService.delete(grupo, document);
		}
	}

	/**
	 * Exclui Grupo atribuindo sua data fim em Grupo.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void deleteEmpregado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		
		if (!grupo.getAllEmpregadosCheckados().equals("")){
			grupo.setColEmpregadoCheckado(grupo.getAllEmpregadosCheckados());
		} else {
			grupo.setColEmpregadoCheckado(null);
		}
		
		GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		Integer idGrupo = grupo.getIdGrupo();
		
		// Verifica-se se há empregadosCheckados caso haja faz-se um for para excluir um a um!
		if (grupo.getColEmpregadoCheckado() != null && grupo.getColEmpregadoCheckado().length >= 1){
			Integer count = 0;
			for (String empregadoCheckado : grupo.getColEmpregadoCheckado()){
				ResultResponsaveisDTO resultResponsaveisDto = new ResultResponsaveisDTO();
				Integer idEmpregado = Integer.parseInt(empregadoCheckado);
				grupoEmpregadoDTO.setIdGrupo(idGrupo);
				grupoEmpregadoDTO.setIdEmpregado(idEmpregado);
				Collection<GrupoEmpregadoDTO> colGrupoEmpregadoDTO = grupoEmpregadoService.verificacaoResponsavelPorSolicitacao(grupoEmpregadoDTO.getIdGrupo(), grupoEmpregadoDTO.getIdEmpregado());
				if (colGrupoEmpregadoDTO == null || colGrupoEmpregadoDTO.isEmpty()){
					if (grupo.getIdGrupo() != null && grupo.getIdGrupo().intValue() > 0) {
						grupoEmpregadoService.deleteByIdGrupoAndEmpregado(idGrupo, idEmpregado);
						count++;
					}
				} else {
					//Informar individualmente o colaborador que não pode ser excluido
					resultResponsaveisDto.setResultado(true);
					for (GrupoEmpregadoDTO grupoEmpregado : colGrupoEmpregadoDTO){
						resultResponsaveisDto.concatMensagem(UtilI18N.internacionaliza(request, "grupo.oColaborador") + grupoEmpregado.getNomeEmpregado());
						resultResponsaveisDto.concatMensagem(UtilI18N.internacionaliza (request, "grupo.naoPodeExcluir"));
						document.alert(resultResponsaveisDto.getMensagem());
						break;
					}
				}
				
			}
			if (count > 0)
				document.alert(UtilI18N.internacionaliza(request,"grupo.empregados.excluidosSucesso"));
		} else {
			document.alert(UtilI18N.internacionaliza(request,"grupo.empregados.naoSelecionado"));
		}
		document.executeScript("document.form.AllEmpregadosCheckados.value = ''");
		document.executeScript("empregadosCheckados = ''");
		preencheTabelaColaborador(document, request, response);
	}
	
	public void deleteTodosEmpregados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		
		GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		Integer idGrupo = grupo.getIdGrupo();
		Collection<GrupoEmpregadoDTO> colGrupoEmpregadoDTO = grupoEmpregadoService.findByIdGrupo(idGrupo);
	
		if (colGrupoEmpregadoDTO != null && !colGrupoEmpregadoDTO.isEmpty()){
			Integer count = 0;
			for (GrupoEmpregadoDTO grupoEmpregado : colGrupoEmpregadoDTO){
				ResultResponsaveisDTO resultResponsaveisDto = new ResultResponsaveisDTO();
				grupoEmpregadoDTO.setIdGrupo(idGrupo);
				grupoEmpregadoDTO.setIdEmpregado(grupoEmpregado.getIdEmpregado());
				
				Collection<GrupoEmpregadoDTO> colGrupoEmpregadoResponsaveis = grupoEmpregadoService.verificacaoResponsavelPorSolicitacao(grupoEmpregado.getIdGrupo(), grupoEmpregado.getIdEmpregado());
				
				if (colGrupoEmpregadoResponsaveis == null || colGrupoEmpregadoResponsaveis.isEmpty()){
					if (grupo.getIdGrupo() != null && grupo.getIdGrupo().intValue() > 0) {
						grupoEmpregadoService.deleteByIdGrupoAndEmpregado(grupoEmpregadoDTO.getIdGrupo(), grupoEmpregadoDTO.getIdEmpregado());
						count++;
					}
				} else {
					//Informar individualmente o colaborador que não pode ser excluido
					resultResponsaveisDto.setResultado(true);
					for (GrupoEmpregadoDTO grupoEmpregadoResponsavel : colGrupoEmpregadoResponsaveis){
						resultResponsaveisDto.concatMensagem(UtilI18N.internacionaliza(request, "grupo.oColaborador") + grupoEmpregadoResponsavel.getNomeEmpregado()+" , ");
						resultResponsaveisDto.concatMensagem(UtilI18N.internacionaliza (request, "grupo.naoPodeExcluir"));
						document.alert(resultResponsaveisDto.getMensagem());
						break;
					}
				}
				
			}
			if (count > 0)
				document.alert(UtilI18N.internacionaliza(request,"grupo.empregados.excluidosSucesso"));
		} else {
			document.alert(UtilI18N.internacionaliza(request,"grupo.empregados.naoSelecionado"));
		}
		document.executeScript("document.form.AllEmpregadosCheckados.value = ''");
		document.executeScript("empregadosCheckados = ''");
		preencheTabelaColaborador(document, request, response);
	}

	public void deleteEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		GrupoEmailDTO grupoEmailDTO = new GrupoEmailDTO();
		GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
		Integer idGrupo = grupo.getIdGrupo();
		String idEmailAux = request.getParameter("emailGrupos");

		String email = idEmailAux;
		grupoEmailDTO.setIdGrupo(idGrupo);
		grupoEmailDTO.setEmail(email);
		if (grupo.getIdGrupo() != null && grupo.getIdGrupo().intValue() > 0) {
			grupoEmailDao.deleteByIdGrupoAndEmail(idGrupo, email);
		}

		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

	/**
	 * Preenche a combo PerfilAcesso.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	private void preencherComboPerfilAcessoGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PerfilAcessoService perfilAcessoService = (PerfilAcessoService) ServiceLocator.getInstance().getService(PerfilAcessoService.class, null);

		HTMLSelect comboPerfilAcessoGrupo = (HTMLSelect) document.getSelectById("idPerfilAcessoGrupo");
		ArrayList<PerfilAcessoDTO> perfilAcessos = (ArrayList) perfilAcessoService.list();
		inicializarCombo(comboPerfilAcessoGrupo, request);

		for (PerfilAcessoDTO perfilAcessoDto : perfilAcessos) {
			if (perfilAcessoDto.getDataFim() == null) {
				System.out.println(perfilAcessoDto.getNomePerfilAcesso());
				comboPerfilAcessoGrupo.addOption(perfilAcessoDto.getIdPerfilAcesso().toString(), StringEscapeUtils.escapeJavaScript(perfilAcessoDto.getNomePerfilAcesso()));
			}
		}
	}

	/**
	 * Preenche a combo PerfilAcesso.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	private void preencherComboTipoFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);

		HTMLSelect ComboTipoFluxo = (HTMLSelect) document.getSelectById("idTipoFluxo");
		ArrayList<TipoFluxoDTO> tiposFluxos = (ArrayList) tipoFluxoService.list();
		inicializarCombo(ComboTipoFluxo, request);

		for (TipoFluxoDTO tipoFluxo : tiposFluxos) {
			ComboTipoFluxo.addOption(tipoFluxo.getIdTipoFluxo().toString(), StringEscapeUtils.escapeJavaScript(tipoFluxo.getNomeFluxo()));
		}

	}

	/**
	 * Iniciliza combo.
	 * 
	 * @param componenteCombo
	 * @author thays.araujo
	 */
	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}
	
	
	public void preencheTabelaColaborador(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		
		
		Integer itensPorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		Integer totalPaginas = grupoEmpregadoService.calculaTotalPaginas(itensPorPagina, grupo.getIdGrupo());
		Integer paginaSelecionadaColaborador = grupo.getPaginaSelecionadaColaborador();
        if (paginaSelecionadaColaborador == null) {
        	paginaSelecionadaColaborador = 1;
        }
        
		//Insere a paginaçao
        //if(totalPaginas>1){
        	paginacaoColaborador(totalPaginas,paginaSelecionadaColaborador,request, document);
        //}
		
		//Elementos paginados
        Collection<GrupoEmpregadoDTO> grupoDeEmpregadostes = (Collection<GrupoEmpregadoDTO>) grupoEmpregadoService.paginacaoGrupoEmpregado(grupo.getIdGrupo(),paginaSelecionadaColaborador,itensPorPagina);

		document.executeScript("deleteEmpregadoRows()");
		if (grupoDeEmpregadostes != null && !grupoDeEmpregadostes.isEmpty()) {

			for (GrupoEmpregadoDTO grupoEmpregadoDto : grupoDeEmpregadostes) {
				document.executeScript("addLinhaTabelaEmpregado(" + grupoEmpregadoDto.getIdEmpregado() + ", '" + StringEscapeUtils.escapeJavaScript(grupoEmpregadoDto.getNomeEmpregado()) + "', "
						+ false + ", " + (grupoEmpregadoDto.getEnviaEmail() != null && grupoEmpregadoDto.getEnviaEmail().equalsIgnoreCase("S")) + " );");

				document.executeScript("exibirGrid();");
			}
		
			//Focus na tabela
			document.executeScript("document.getElementById('tabelaEmpregado').scrollIntoView(true);");
			document.executeScript("memoriaEmpregadosCheckados();");
		}
	}
	
	
	//Insere os elementos de paginação
	public void paginacaoColaborador(Integer totalPaginas, Integer paginaSelecionada, HttpServletRequest request, DocumentHTML document) throws Exception {
		HTMLElement divPrincipal = document.getElementById("paginas");
		StringBuilder sb = new StringBuilder();
		if (totalPaginas>1){
			final Integer adjacentes = 2;
			if (paginaSelecionada == null)
				paginaSelecionada = 1;
			sb.append(" <div id='itenPaginacaoColaborador' class='pagination pagination-right margin-none' > ");
			sb.append(" <ul>");
			sb.append(" <li " + (paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='paginarItens(this.value);'") + " ><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.primeiro")+"</a></li></font>");
			sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='"+(paginaSelecionada-1)+"' onclick='paginarItens(this.value);'") + "><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.anterior")+"</a></li></font>");
			if(totalPaginas <= 5) {
				for (int i = 1; i <= totalPaginas; i++) {
					if (i == paginaSelecionada) {
						sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font>");
					} else {
						sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> " );
					}
				}
			} else {
				if (totalPaginas > 5) {
					if (paginaSelecionada < 1 + (2 * adjacentes)) {
						for (int i=1; i< 2 + (2 * adjacentes); i++) {
							if (i == paginaSelecionada) {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
							} else {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> " );
							}
						}
					} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
						for (int i = paginaSelecionada-adjacentes; i<= paginaSelecionada + adjacentes; i++) {
							if (i == paginaSelecionada) {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
							} else {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
							}
						}
					} else {
						for (int i = totalPaginas - (0 + (2 * adjacentes)); i <= totalPaginas; i++) {
							if (i == paginaSelecionada) {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
							} else {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
							}
						}
					}
				}
			}
			sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='"+(paginaSelecionada+1)+"' onclick='paginarItens(this.value);'") + " ><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.proximo")+"</a></li></font>");
			sb.append(" <li id='"+totalPaginas+"' value='"+totalPaginas+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.ultimo")+"</a></li></font>");
			sb.append(" </ul>");
			sb.append(" </div>");
		}
		divPrincipal.setInnerHTML(sb.toString());
	}
	
	//Manipula dados do empregado
	public void manipulaEmpregado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		String idEmpregadoAux = request.getParameter("iddEmpregado");
		String descEmpregado = UtilStrings.decodeCaracteresEspeciais(request.getParameter("descEmpregado"));
		Integer idEmpregado = Integer.parseInt(idEmpregadoAux);
		//verifica se está criando ou incluindo empregado
		if (grupo.getIdGrupo() != null) {
			//verifica se existe empregado ao grupo
			if (!grupoEmpregadoService.grupoempregado(idEmpregado,grupo.getIdGrupo())) {
				grupoEmpregadoDTO.setIdGrupo(grupo.getIdGrupo());
				grupoEmpregadoDTO.setIdEmpregado(idEmpregado);
				grupoEmpregadoService.create(grupoEmpregadoDTO);
				document.executeScript("addLinhaTabelaEmpregado('"+ idEmpregado + "','" + descEmpregado + "'," + true + " , false);");
			} else {
				
				document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.registroJaAdicionado"));
			}
	
	} else{
		//Quando não tem o grupo apenas adiciona a linha
		document.executeScript("addLinhaTabelaEmpregado('"+ idEmpregado + "','" + descEmpregado + "'," + true + ", false);");
	}	

	}
	
	//Manipula email
	public void gravarEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoDTO grupo = (GrupoDTO) document.getBean();
		GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		if (grupo.getIdGrupo() != null) {
			//Elementos que estão na tabela da pagina selecionada e setando se envia email
			Collection <GrupoEmpregadoDTO> grupoempregado =  br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoEmpregadoDTO.class, "empregadosSerializadosAux", request);
			if(grupoempregado!=null){
			for(GrupoEmpregadoDTO grupoempregadoaux : grupoempregado){
				if(grupoempregadoaux!=null){
						grupoEmpregadoService.deleteByIdGrupoAndEmpregado(grupo.getIdGrupo(),grupoempregadoaux.getIdEmpregado());
						grupoEmpregadoDTO.setIdEmpregado(grupoempregadoaux.getIdEmpregado());
						grupoEmpregadoDTO.setIdGrupo(grupo.getIdGrupo());
						grupoEmpregadoDTO.setEnviaEmail(grupoempregadoaux.getEnviaEmail());
						grupoEmpregadoService.create(grupoEmpregadoDTO);
					}

				}
			}
		}

	}

	public Class getBeanClass() {
		return GrupoDTO.class;
	}

}
