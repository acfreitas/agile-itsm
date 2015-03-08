package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLCheckbox;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.LinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RecursoProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AssinaturaAprovacaoProjetoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.LinhaBaseProjetoService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RecursoProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"unchecked","rawtypes","unused"})
public class Projeto extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		/*
		if (!WebUtil.isUserInGroup(request, Constantes.getValue("GRUPO_DIRETORIA"))){
			document.alert("Você não tem permissão para acessar esta funcionalidade!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'");
			return;
		}
		*/
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		HTMLSelect comboIdSituacaoFuncional = (HTMLSelect) document.getSelectById("situacao");
		//HTMLSelect comboClientes = (HTMLSelect) document.getSelectById("idCliente");

		comboIdSituacaoFuncional.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboIdSituacaoFuncional.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.ativo"));
		comboIdSituacaoFuncional.addOption("I", UtilI18N.internacionaliza(request, "citcorpore.comum.inativo"));

		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);

		//Collection colClientes = clienteService.list();

		//comboClientes.addOption("", "--");
		//comboClientes.addOptions(colClientes, "idCliente", "nomeFantasia", null);

		Collection colContratos = contratoService.list();
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		Collection colContratosColab = null;
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}
		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
		if (colContratos != null) {
			if (colContratos.size() > 1) {
				((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			}
			for (Iterator it = colContratos.iterator(); it.hasNext();) {
				ContratoDTO contratoDto = (ContratoDTO) it.next();
				if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
					if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se parametro de colaboradores por contrato ativo, entao filtra.
						if (colContratosColab == null) {
							continue;
						}
						if (!isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
							continue;
						}
					}
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
					if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ")";
						((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), nomeContrato);
						contratoDto.setNome(nomeContrato);
						listaContratos.add(contratoDto);
					}
				}
			}
		}

		document.focusInFirstActivateField(null);
	}
	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}
	public void Projeto_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Collection<RecursoProjetoDTO> colRecursos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RecursoProjetoDTO.class, "colRecursosSerialize", request);
		Collection<AssinaturaAprovacaoProjetoDTO> colAssinaturas = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AssinaturaAprovacaoProjetoDTO.class, "colAssinaturasSerialize", request);
		//System.out.println("---> " + colFaixasValores);
		ProjetoDTO projeto = (ProjetoDTO) document.getBean();


		//vou tirar o ultimo número do idEmpregado, que foi colocado no jsp para diferenciar os ids das linhas da assinaturaAprovacao
		if(colAssinaturas != null && colAssinaturas.size()>0){
			for (AssinaturaAprovacaoProjetoDTO assinaturaAprovacaoProjetoDTO : colAssinaturas) {
				if(assinaturaAprovacaoProjetoDTO.getIdEmpregadoAssinatura() != null){
					int tamanho = assinaturaAprovacaoProjetoDTO.getIdEmpregadoAssinatura().toString().length();
					assinaturaAprovacaoProjetoDTO.setIdEmpregadoAssinatura(Integer.parseInt(assinaturaAprovacaoProjetoDTO.getIdEmpregadoAssinatura().toString().substring(0, tamanho - 1)));
				}
			}
		}
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		if (projeto.getIdContrato() == null){
			document.alert(UtilI18N.internacionaliza(request, "projeto.contratoNaoEncontrado"));
			return;
		}
		ContratoDTO contratoDTO =  new ContratoDTO();
		contratoDTO.setIdContrato(projeto.getIdContrato());
		contratoDTO = (ContratoDTO) contratoService.restore(contratoDTO);
		if (contratoDTO == null){
			document.alert(UtilI18N.internacionaliza(request, "projeto.contratoNaoEncontrado"));
			return;
		}
		projeto.setIdCliente(contratoDTO.getIdCliente());
		projeto.setColRecursos(colRecursos);
		projeto.setColAssinaturasAprovacoes(colAssinaturas);

		if (projeto.getIdProjeto()==null || projeto.getIdProjeto().intValue()==0){
			projetoService.create(projeto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		}else{
			projetoService.update(projeto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));

		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("deleteAllRows()");
		document.executeScript("limpar()");
	}


	public void Projeto_onrestore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ProjetoDTO projeto = (ProjetoDTO) document.getBean();
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		RecursoProjetoService recursoProjetoService = (RecursoProjetoService) ServiceLocator.getInstance().getService(RecursoProjetoService.class, null);
		AssinaturaAprovacaoProjetoService assinaturaAprovacaoService = (AssinaturaAprovacaoProjetoService) ServiceLocator.getInstance().getService(AssinaturaAprovacaoProjetoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);

		projeto = (ProjetoDTO) projetoService.restore(projeto);
		document.executeScript("deleteAllRows()");

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar()");
		document.executeScript("LOOKUP_MUDANCA_select('','')");
		document.executeScript("LOOKUP_LIBERACAO_select('','')");
		if (projeto.getIdOs() != null){
			OSDTO osDto = new OSDTO();
			osDto.setIdOS(projeto.getIdOs());
			osDto = (OSDTO) osService.restore(osDto);
			if (osDto != null){
				projeto.setAno(osDto.getAno());
				projeto.setNumero(osDto.getNumero());
				projeto.setNomeAreaRequisitante(osDto.getNomeAreaRequisitante());
				projeto.setDemanda(osDto.getDemanda());
				projeto.setObjetivo(osDto.getObjetivo());
				projeto.setIdServicoContrato(osDto.getIdServicoContrato());
				projeto.setDataEmissao(osDto.getDataEmissao());
				document.setBean(projeto);
				carregaInfoOS(document, request, response);
			}
			projeto.setVinculoOS("S");
			document.getElementById("divOS").setVisible(true);
		}
		/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 13:58 - ID Citsmart: 120948 -
		* Motivo/Comentário: Erro de javascript ao usar setvalue e settext/ novo padrão de lookup nao há necessidade */
		form.setValues(projeto);
		if (projeto.getIdRequisicaoMudanca() != null){
			RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();
			requisicaoMudancaDTO.setIdRequisicaoMudanca(projeto.getIdRequisicaoMudanca());
			requisicaoMudancaDTO = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);
			if (requisicaoMudancaDTO != null){
				String aux = requisicaoMudancaDTO.getNumberAndTitulo();
				aux = aux.replaceAll("\'", "");
				aux = aux.replaceAll("\"", "");
				document.executeScript("LOOKUP_MUDANCA_select('" + projeto.getIdRequisicaoMudanca() + "','" + aux + "')");
			/*	document.executeScript("LOOKUP_MUDANCA.setvalue('" + projeto.getIdRequisicaoMudanca() + "')");
				document.executeScript("LOOKUP_MUDANCA.settext('" + aux + "')");*/
			}
		}
		/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 13:58 - ID Citsmart: 120948 -
		* Motivo/Comentário: Erro de javascript ao usar setvalue e settext/ novo padrão de lookup nao há necessidade */
		if (projeto.getIdLiberacao() != null){
			RequisicaoLiberacaoDTO requisicaoLiberacaoDTO = new RequisicaoLiberacaoDTO();
			requisicaoLiberacaoDTO.setIdRequisicaoLiberacao(projeto.getIdLiberacao());
			requisicaoLiberacaoDTO = (RequisicaoLiberacaoDTO) requisicaoLiberacaoService.restore(requisicaoLiberacaoDTO);
			if (requisicaoLiberacaoDTO != null){
				String aux = requisicaoLiberacaoDTO.getTitulo();
				aux = aux.replaceAll("\'", "");
				aux = aux.replaceAll("\"", "");
				document.executeScript("LOOKUP_LIBERACAO_select('" + projeto.getIdLiberacao() + "','" + aux + "')");
				/*document.executeScript("LOOKUP_LIBERACAO.setvalue('" + projeto.getIdLiberacao() + "')");
				document.executeScript("LOOKUP_LIBERACAO.settext('" + aux + "')");*/
			}
		}

		Collection colRecursos = recursoProjetoService.findByIdProjeto(projeto.getIdProjeto());
		if (colRecursos != null){
			for (Iterator it = colRecursos.iterator(); it.hasNext();){
				RecursoProjetoDTO recursoProjetoDTO = (RecursoProjetoDTO)it.next();
				EmpregadoDTO empregadoDTO = new EmpregadoDTO();
				empregadoDTO.setIdEmpregado(recursoProjetoDTO.getIdEmpregado());
				empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
				if (empregadoDTO != null){
					String nome = empregadoDTO.getNome();
					nome = UtilStrings.nullToVazio(nome).replaceAll("\'", "");
					String custoStr = UtilFormatacao.formatDouble(recursoProjetoDTO.getCustoHora(), 2);
					document.executeScript("addLinhaTabelaRecurso('" + recursoProjetoDTO.getIdEmpregado() + "','" + nome + "', '" + custoStr +"', true)");
				}
			}
		}

		Collection colAssinaturas = assinaturaAprovacaoService.findByIdProjeto(projeto.getIdProjeto());
		if (colAssinaturas != null){
			for (Iterator it = colAssinaturas.iterator(); it.hasNext();){
				AssinaturaAprovacaoProjetoDTO assinaturaAprovacaoProjetoDTO = (AssinaturaAprovacaoProjetoDTO)it.next();
				EmpregadoDTO empregadoDTO = new EmpregadoDTO();
				empregadoDTO.setIdEmpregado(assinaturaAprovacaoProjetoDTO.getIdEmpregadoAssinatura());
				empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
				if (empregadoDTO != null){
					String nome = empregadoDTO.getNome();
					nome = UtilStrings.nullToVazio(nome).replaceAll("\'", "");
					String papel = assinaturaAprovacaoProjetoDTO.getPapel();
					String ordem = assinaturaAprovacaoProjetoDTO.getOrdem();
					document.executeScript("addLinhaTabelaAssinaturaAprovacao('" + assinaturaAprovacaoProjetoDTO.getIdEmpregadoAssinatura() + "','" + nome + "', '" + papel +"', '" + ordem +"', true)");
				}
			}
		}

		carregaInfoLinhaBase(document, request, projeto.getIdProjeto());
		//document.alert("Registro recuperado !");
	}

	/* Desenvolvedor: Bruno Rodrigues  Data: 28/10/2013 - Horário: 10h27min  ID Citsmart: 120948  Motivo/Comentário: Problema na internacionalização dos labels dessa tabela e do botão de autorizar mudança. */

	/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 13:58 - ID Citsmart: 120948 -
	* Motivo/Comentário: Tabela com dificil visualização/ alterado layout: retirado algumas bordas */
	public void carregaInfoLinhaBase(DocumentHTML document, HttpServletRequest request, Integer idProjeto) throws ServiceException, Exception{
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		Collection colLinhasBase = linhaBaseProjetoService.findByIdProjeto(idProjeto);
		LinhaBaseProjetoDTO linhaBaseProjetoDTO = null;
		String strTableLnBase = "<table width='100%' class='table table-bordered table-striped' style='border:1px solid black;'>";
		strTableLnBase += "<tr>";

		strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
			strTableLnBase += "<b>Id</b>";
		strTableLnBase += "</td>";
		strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
			strTableLnBase += "<b>" +UtilI18N.internacionaliza(request, "citcorpore.comum.data") +"</b>";
		strTableLnBase += "</td>";
		strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
			strTableLnBase += "<b>" +UtilI18N.internacionaliza(request, "eventoItemConfiguracao.hora") +"</b>";
		strTableLnBase += "</td>";
		strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
			strTableLnBase += "<b>" +UtilI18N.internacionaliza(request, "projeto.usuarioUltAlteracao") +"</b>";
		strTableLnBase += "</td>";
		strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
			strTableLnBase += "<b>" +UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") +"</b>";
		strTableLnBase += "</td>";
		strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
			strTableLnBase += "<b>&nbsp;</b>";
		strTableLnBase += "</td>";

		strTableLnBase += "</tr>";
		if (colLinhasBase != null){
			for (Iterator it = colLinhasBase.iterator(); it.hasNext();){
				linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) it.next();
				strTableLnBase += "<tr>";

				strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
					strTableLnBase += "" + linhaBaseProjetoDTO.getIdLinhaBaseProjeto();
				strTableLnBase += "</td>";
				strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
					strTableLnBase += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, linhaBaseProjetoDTO.getDataLinhaBase(), WebUtil.getLanguage(request));
				strTableLnBase += "</td>";
				strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
					strTableLnBase += "" + UtilFormatacao.formataHoraHHMM(linhaBaseProjetoDTO.getHoraLinhaBase());
				strTableLnBase += "</td>";
				strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
					strTableLnBase += "" + linhaBaseProjetoDTO.getUsuarioUltAlteracao();
				strTableLnBase += "</td>";
				strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
				if (UtilStrings.nullToVazio(linhaBaseProjetoDTO.getSituacao()).equalsIgnoreCase("E")){
					strTableLnBase += "Em execução";
				}else if (UtilStrings.nullToVazio(linhaBaseProjetoDTO.getSituacao()).equalsIgnoreCase("I")){
					strTableLnBase += "Inativa";
				}else {
					strTableLnBase += "" + linhaBaseProjetoDTO.getSituacao();
				}
				strTableLnBase += "</td>";
				strTableLnBase += "<td style='border:1px solid #7C7C7C; font-family: arial; font-size: 12px;'>";
				if (UtilStrings.nullToVazio(linhaBaseProjetoDTO.getSituacao()).equalsIgnoreCase("E")){
					/*strTableLnBase += "<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mudancasla.png' border='0' style='cursor:pointer' title='Registrar autorização para mudança' onclick='registrarAutorizacao(\"" + linhaBaseProjetoDTO.getIdLinhaBaseProjeto() + "\")'/>";*/
					strTableLnBase += "<button type='button' title='Registrar autorização para mudança' class='icon_only text_only' onclick='registrarAutorizacao(\"" + linhaBaseProjetoDTO.getIdLinhaBaseProjeto() + "\")'/>"+ UtilI18N.internacionaliza(request, "projeto.autorizarMudanca")  +"</button>";
				}else{
					strTableLnBase += "&nbsp;";
				}
				strTableLnBase += "</td>";

				strTableLnBase += "</tr>";

				if (linhaBaseProjetoDTO.getJustificativaMudanca() != null && !linhaBaseProjetoDTO.getJustificativaMudanca().trim().equalsIgnoreCase("")){
					strTableLnBase += "<tr>";
					strTableLnBase += "<td style=' font-family: arial; font-size: 12px;' rowspan='3'>";
						strTableLnBase += "&nbsp;";
					strTableLnBase += "</td>";
					strTableLnBase += "<td colspan='20' style=' font-family: arial; font-size: 12px;'>";
					strTableLnBase += "<b>" + UtilI18N.internacionaliza(request, "projeto.solicitacaoMudancaLinhaBase")+"</b>";
					strTableLnBase += "</td>";
					strTableLnBase += "</tr>";

					strTableLnBase += "<tr>";
					strTableLnBase += "<td colspan='20' style=' font-family: arial; font-size: 12px;'>";
					strTableLnBase += linhaBaseProjetoDTO.getJustificativaMudanca();
					strTableLnBase += "</td>";
					strTableLnBase += "</tr>";

					strTableLnBase += "<tr>";
					strTableLnBase += "<td colspan='20' style=' font-family: arial; font-size: 12px;'>";
					strTableLnBase += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, linhaBaseProjetoDTO.getDataSolMudanca(), WebUtil.getLanguage(request)) + " " + UtilFormatacao.formataHoraHHMM(linhaBaseProjetoDTO.getHoraSolMudanca()) + " - " + linhaBaseProjetoDTO.getUsuarioSolMudanca();
					strTableLnBase += "</td>";
					strTableLnBase += "</tr>";
				}
			}
		}
		strTableLnBase += "</table>";

		document.getElementById("divLinhasBase").setInnerHTML(strTableLnBase);
	}
	public void carregaInfoOS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ProjetoDTO projeto = (ProjetoDTO) document.getBean();
		if (projeto.getIdContrato() == null){
			document.alert(UtilI18N.internacionaliza(request, "projeto.informeContrato"));
			HTMLCheckbox checkBox = document.getCheckboxById("vinculoOS");
			checkBox.setChecked(false);
			document.executeScript("atualizaSemVinculo()");
			document.executeScript("$('#divOS').hide()");
			document.executeScript("limparVinculacoesOS()");
			return;
		}
		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idServicoContrato");
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		Collection colServicosDoContrato = servicoContratoService.findByIdContrato(projeto.getIdContrato());
		idContrato.removeAllOptions();
		idContrato.addOption("", UtilI18N.internacionaliza(request, "projeto.selecioneServico"));
		List colFinal = new ArrayList();
		if (colServicosDoContrato != null) {
			for (Iterator it = colServicosDoContrato.iterator(); it.hasNext();) {
				ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it.next();
				if (servicoContratoDTO.getDeleted() == null || servicoContratoDTO.getDeleted().equalsIgnoreCase("n")) {
					if (servicoContratoDTO.getDataFim() == null || servicoContratoDTO.getDataFim().after(UtilDatas.getDataAtual())) {
						ServicoDTO servicoDto = new ServicoDTO();
						servicoDto.setIdServico(servicoContratoDTO.getIdServico());
						servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
						if (servicoDto != null) {
							String sigla = servicoDto.getSiglaAbrev();
							String nomeServico = servicoDto.getNomeServico();
							if (sigla != null && nomeServico != null) {
								sigla = sigla.trim();
								nomeServico = nomeServico.trim();
								if (!sigla.equals("")) {
									servicoContratoDTO.setNomeServico(sigla + " - " + nomeServico);
								} else {
									servicoContratoDTO.setNomeServico(nomeServico);
								}
							} else {
								servicoContratoDTO.setNomeServico(nomeServico);
							}
							// idContrato.addOption("" + servicoContratoDTO.getIdServicoContrato(), servicoDto.getNomeServico());
							if (servicoDto.getIdTipoDemandaServico().intValue() == 2) {
								colFinal.add(servicoContratoDTO);
							}
						}
					}
				}
			}
		}
		if(colFinal != null){
			Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
			for (Iterator it = colFinal.iterator(); it.hasNext();) {
				ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it.next();
				idContrato.addOption("" + servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getNomeServico());
			}
		}
	}
	public void gravarAutorizMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		ProjetoDTO projeto = (ProjetoDTO) document.getBean();
		if (projeto.getIdProjetoAutorizacao() == null){
			document.alert(UtilI18N.internacionaliza(request, "pagamentoProjeto.informeProjeto"));
			return;
		}
		if (projeto.getIdLinhaBaseProjeto() == null){
			document.alert(UtilI18N.internacionaliza(request, "projeto.informeLinhaBase"));
			return;
		}
		if (projeto.getJustificativaMudanca() == null || projeto.getJustificativaMudanca().trim().equalsIgnoreCase("")){
			document.alert(UtilI18N.internacionaliza(request, "projeto.justificativaLinhaBase"));
			return;
		}
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, WebUtil.getUsuarioSistema(request));
		String hora = UtilDatas.getHoraHHMM(UtilDatas.getDataHoraAtual()).replaceAll(":", "");
		LinhaBaseProjetoDTO linhaBaseProjetoDTO = new LinhaBaseProjetoDTO();
		linhaBaseProjetoDTO.setIdLinhaBaseProjeto(projeto.getIdLinhaBaseProjeto());
		linhaBaseProjetoDTO.setDataSolMudanca(UtilDatas.getDataAtual());
		linhaBaseProjetoDTO.setHoraSolMudanca(hora);
		linhaBaseProjetoDTO.setJustificativaMudanca(projeto.getJustificativaMudanca());
		linhaBaseProjetoDTO.setUsuarioSolMudanca(usuario.getNomeUsuario());
		linhaBaseProjetoService.updateAutorizacaoMudanca(linhaBaseProjetoDTO);
		document.alert(UtilI18N.internacionaliza(request, "projeto.gravaAutorizacao"));
		carregaInfoLinhaBase(document, request, projeto.getIdProjetoAutorizacao());
		document.executeScript("$(\"#POPUP_REG_AUT_MUDANCA\").dialog(\"close\");");
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProjetoDTO projetoDto = (ProjetoDTO) document.getBean();
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		RecursoProjetoService recursoProjetoService = (RecursoProjetoService) ServiceLocator.getInstance().getService(RecursoProjetoService.class, null);
		AssinaturaAprovacaoProjetoService assinaturaAprovacaoService = (AssinaturaAprovacaoProjetoService) ServiceLocator.getInstance().getService(AssinaturaAprovacaoProjetoService.class, null);

		if (projetoDto.getIdProjeto().intValue() > 0) {
             //tratar exclusão
			//Se existe vinculo com linha de base
			if (linhaBaseProjetoService.findByIdProjeto(projetoDto.getIdProjeto())!=null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluidoLinhaBase"));
				document.executeScript("limpar()");
				return;
			}
			//Se existe algum recurso, excluir se estiver vinculado
			if (recursoProjetoService.findByIdProjeto(projetoDto.getIdProjeto())!=null) {
				recursoProjetoService.deleteByIdProjeto(projetoDto.getIdProjeto());
			}
			//Se existe alguma assinatura, excluir se estiver vinculado
			if (assinaturaAprovacaoService.findByIdProjeto(projetoDto.getIdProjeto())!=null) {
				assinaturaAprovacaoService.deleteByIdProjeto(projetoDto.getIdProjeto());
			}

			projetoDto = (ProjetoDTO) projetoService.restore(projetoDto);
			projetoDto.setDeleted("y");
			projetoService.update(projetoDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		//HTMLForm form = document.getForm("form");
		//form.clear();
		document.executeScript("limpar()");
		document.executeScript("limpar_LOOKUP_PROJETO()");

	}

	public Class getBeanClass(){
		return ProjetoDTO.class;
	}

}