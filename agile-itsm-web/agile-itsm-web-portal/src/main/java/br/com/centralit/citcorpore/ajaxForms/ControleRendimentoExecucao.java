
package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ControleRendimentoDTO;
import br.com.centralit.citcorpore.bean.ControleRendimentoExecucaoDTO;
import br.com.centralit.citcorpore.bean.ControleRendimentoUsuarioDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoListaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ControleRendimentoService;
import br.com.centralit.citcorpore.negocio.ControleRendimentoUsuarioService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

public class ControleRendimentoExecucao extends AjaxFormAction{

	static Boolean flagCalculaPessoa;
	static Collection<ControleRendimentoExecucaoDTO> colecaoPessoasGlobal;
	private UsuarioDTO usuario;
	private String localeSession = null;

	Integer pontosSlaBaixoNoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_BAIXA_DENTRO_DO_PRAZO, "1")));
	Integer pontosSlaBaixoRetrabalhado = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_BAIXA_RETRABALHO, "-1")));
	Integer pontosSlaBaixoForaDoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_BAIXA_FORA_DO_PRAZO, "-1")));
	Integer pontosSlaBaixoRetrabalhadoEForaDoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_BAIXA_RETRABALHADO_PRAZO_ESTOURADO, "-2")));

	Integer pontosSlaMedioNoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_MEDIA_DENTRO_DO_PRAZO, "2")));
	Integer pontosSlaMedioRetrabalhado = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_MEDIA_RETRABALHO, "-2")));
	Integer pontosSlaMedioForaDoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_MEDIA_FORA_DO_PRAZO, "-2")));
	Integer pontosSlaMedioRetrabalhadoEForaDoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_MEDIA_RETRABALHADO_PRAZO_ESTOURADO, "-4")));

	Integer pontosSlaAltoNoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_ALTA_DENTRO_DO_PRAZO, "3")));
	Integer pontosSlaAltoRetrabalhado = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_ALTA_RETRABALHO, "-3")));
	Integer pontosSlaAltoForaDoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_ALTA_FORA_DO_PRAZO, "-3")));
	Integer pontosSlaAltoRetrabalhadoEForaDoPrazo = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PONTUACAO_PRODUTIVIDADE_ALTA_RETRABALHADO_PRAZO_ESTOURADO, "-6")));

	Integer idGrupoTeste = new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_TESTE, "93")));
	Integer idGrupoExecutor= new Integer(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_EXECUTOR, "94")));

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ControleRendimento controleRendimento = new ControleRendimento();
		controleRendimento.carregaCombos(document, request);
	}

	@Override
	public Class getBeanClass() {
		return ControleRendimentoExecucaoDTO.class;
	}


	public void restoreNomeGrupoExecucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();
		controle.setIdGrupo(controle.getIdGrupoExecucao());
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		GrupoDTO grupo = (GrupoDTO) grupoService.restore(controle);
		document.getElementById("addGrupoExecucao").setValue(grupo.getNome());

		Collection<EmpregadoDTO> listaEmpregados = listaEmpregados(grupo.getIdGrupo());

		((HTMLSelect) document.getSelectById("comboPessoa")).removeAllOptions();
		((HTMLSelect) document.getSelectById("comboPessoa")).addOption("", UtilI18N.internacionaliza(request, "controle.todos"));

		for (EmpregadoDTO empregadoDTO : listaEmpregados) {
			((HTMLSelect) document.getSelectById("comboPessoa")).addOption(empregadoDTO.getIdEmpregado().toString(), empregadoDTO.getNome());
		}

	}

	public void restoreNomeGrupoRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();
		controle.setIdGrupo(controle.getIdGrupoRelatorio());
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		GrupoDTO grupo = (GrupoDTO) grupoService.restore(controle);
		document.getElementById("addGrupoRelatorio").setValue(grupo.getNome());
	}

	public void restoreNomeGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		GrupoDTO grupo = (GrupoDTO) grupoService.restore(controle);
		document.getElementById("addGrupo").setValue(grupo.getNome());
	}

	public Collection<EmpregadoDTO> listaEmpregados(Integer idGrupo) throws ServiceException, Exception{
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		Collection<GrupoEmpregadoDTO> grupoFabrica = (Collection<GrupoEmpregadoDTO>) grupoEmpregadoService.findByIdGrupo(idGrupo);
		Collection<EmpregadoDTO> empregadosFabrica = new ArrayList<EmpregadoDTO>();

		for (GrupoEmpregadoDTO grupoEmpregadoDTO : grupoFabrica) {
			EmpregadoDTO empregado = new EmpregadoDTO();
			empregado.setIdEmpregado(grupoEmpregadoDTO.getIdEmpregado());
			empregado = (EmpregadoDTO) empregadoService.restore(empregado);
			empregadosFabrica.add(empregado);
		}

		return empregadosFabrica;
	}

	public void carregaTabelasExecucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		controleRendimentoDto.setIdGrupo(controleRendimentoDto.getIdGrupoExecucao());

		Boolean verificaSeGrupoTeste = false;
		flagCalculaPessoa = false;

		if(controleRendimentoDto.getIdGrupo().intValue() == idGrupoTeste.intValue()){
			verificaSeGrupoTeste = true;
		}

		ArrayList<Object> listaComTodosOsPontos = null;

		if(verificaSeGrupoTeste){
			listaComTodosOsPontos = calculaPontuacaoGrupo(idGrupoExecutor, controleRendimentoDto.getDataInicio(), controleRendimentoDto.getDataFim(), document);
		} else {
			listaComTodosOsPontos = calculaPontuacaoGrupo(controleRendimentoDto.getIdGrupo(), controleRendimentoDto.getDataInicio(), controleRendimentoDto.getDataFim(), document);
		}

		Collection<ControleRendimentoExecucaoDTO> colecao = montaColecaoParaCarregarAsTabelas(listaComTodosOsPontos);

		ArrayList<Object> listaTotal = (ArrayList<Object>) listaComTodosOsPontos.get(3);

		if((listaTotal.get(0).toString()).contains(",")){
			listaTotal.set(0,(listaTotal.get(0).toString()).replace(",", "."));
		}

		if((listaTotal.get(1).toString()).contains(",")){
			listaTotal.set(1,(listaTotal.get(1).toString()).replace(",", "."));
		}

		if((Double.parseDouble(listaTotal.get(0).toString())) < (Double.parseDouble(listaTotal.get(1).toString()))){
			document.getElementById("divResultadoGrupo").setInnerHTML("<br><span style='font-size: 13pt; color: red'> Resultado: "+ listaTotal.get(0) +"%</span><br><span style='font-size: 13pt; color: red'>GRUPO NÃO APROVADO PARA GRATIFICAÇÃO</span><br>");
			document.getElementById("divTotalRendimentoPessoa").setVisible(false);
			flagCalculaPessoa = false;
		} else{
			document.getElementById("divResultadoGrupo").setInnerHTML("<br><span style='font-size: 12pt; color: green'> Resultado: "+ listaTotal.get(0) +"%</span><br>");
			document.getElementById("divTotalRendimentoPessoa").setVisible(true);
			flagCalculaPessoa = true;
		}

		if (colecao != null) {

			HTMLTable table;
			table = document.getTableById("tblrendimentoGrupoExecucao");
			table.deleteAllRows();
			table.addRowsByCollection(colecao, new String[] {"", "tipoSla", "qtdSolicitacoes", "qtdTotalPontos", "qtdPontosPositivos", "qtdPontosNegativos", "mediaRelativa" }, null, null, null, null, null);
		}


		if(flagCalculaPessoa){
			if(verificaSeGrupoTeste){
				calculaPontuacaoPessoaExecucaoGrupoTeste(controleRendimentoDto.getIdPessoa(), controleRendimentoDto.getDataInicio(), controleRendimentoDto.getDataFim(), document);
			} else {
				calculaPontuacaoPessoaExecucao(controleRendimentoDto.getIdPessoa(), controleRendimentoDto.getDataInicio(), controleRendimentoDto.getDataFim(), document);
			}

		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();	");
		return;
	}

	public void carregaTabelas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controleRendimentoExecucaoDto = (ControleRendimentoExecucaoDTO) document.getBean();

		Boolean verificaSeGrupoTeste = false;
		if(controleRendimentoExecucaoDto.getIdGrupo().intValue() == idGrupoTeste.intValue()){
			verificaSeGrupoTeste = true;
		}

		ArrayList<Object> listaComTodosOsPontos = new ArrayList<Object>();

		if(verificaSeGrupoTeste){
			listaComTodosOsPontos = calculaPontuacaoMesGrupo(controleRendimentoExecucaoDto.getAno(), controleRendimentoExecucaoDto.getMes(),idGrupoExecutor, document);
		} else {
			listaComTodosOsPontos = calculaPontuacaoMesGrupo(controleRendimentoExecucaoDto.getAno(), controleRendimentoExecucaoDto.getMes(), controleRendimentoExecucaoDto.getIdGrupo(), document);
		}

		Collection<ControleRendimentoExecucaoDTO> colecao = montaColecaoParaCarregarAsTabelas(listaComTodosOsPontos);

		if (colecao != null) {

			HTMLTable table;
			table = document.getTableById("tblrendimentoGrupo");
			table.deleteAllRows();
			table.addRowsByCollection(colecao, new String[] {"", "tipoSla", "qtdSolicitacoes", "qtdTotalPontos", "qtdPontosPositivos", "qtdPontosNegativos", "mediaRelativa" }, null, null, null, null, null);
		}


		if(verificaSeGrupoTeste){
			calculaPontuacaoMesPessoaGrupoTeste(controleRendimentoExecucaoDto.getAno(), controleRendimentoExecucaoDto.getMes(), controleRendimentoExecucaoDto.getIdGrupo(), document);
		} else {
			calculaPontuacaoMesPorPessoa(controleRendimentoExecucaoDto.getAno(), controleRendimentoExecucaoDto.getMes(), controleRendimentoExecucaoDto.getIdGrupo(), document);
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();	");
	}

	public Collection<ControleRendimentoExecucaoDTO> montaColecaoParaCarregarAsTabelas(ArrayList<Object> listaComTodosOsPontos){
		ArrayList<Object> listaComSolicitacoesBaixas = (ArrayList<Object>) listaComTodosOsPontos.get(0);
		ArrayList<Object> listaComSolicitacoesMedias = (ArrayList<Object>) listaComTodosOsPontos.get(1);
		ArrayList<Object> listaComSolicitacoesAltas = (ArrayList<Object>) listaComTodosOsPontos.get(2);
		ArrayList<Object> listaTotal = (ArrayList<Object>) listaComTodosOsPontos.get(3);
		ArrayList<Object> listaRodape = (ArrayList<Object>) listaComTodosOsPontos.get(4);

		ControleRendimentoExecucaoDTO controleRendimento1 = new ControleRendimentoExecucaoDTO("Baixa (x1)", listaComSolicitacoesBaixas.get(0).toString(), listaComSolicitacoesBaixas.get(1).toString(), listaComSolicitacoesBaixas.get(2).toString(), listaComSolicitacoesBaixas.get(3).toString(), listaComSolicitacoesBaixas.get(4).toString());
		ControleRendimentoExecucaoDTO controleRendimento2 = new ControleRendimentoExecucaoDTO("Média (x2)", listaComSolicitacoesMedias.get(0).toString(), listaComSolicitacoesMedias.get(1).toString(), listaComSolicitacoesMedias.get(2).toString(), listaComSolicitacoesMedias.get(3).toString(), listaComSolicitacoesMedias.get(4).toString());
		ControleRendimentoExecucaoDTO controleRendimento3 = new ControleRendimentoExecucaoDTO("Alta   (x3)", listaComSolicitacoesAltas.get(0).toString(), listaComSolicitacoesAltas.get(1).toString(), listaComSolicitacoesAltas.get(2).toString(), listaComSolicitacoesAltas.get(3).toString(), listaComSolicitacoesAltas.get(4).toString());
		ControleRendimentoExecucaoDTO controleRendimento4 = new ControleRendimentoExecucaoDTO("Total", listaRodape.get(0).toString(), listaRodape.get(1).toString(), listaRodape.get(2).toString(), listaRodape.get(3).toString(), listaRodape.get(4).toString() + "%");

		Collection<ControleRendimentoExecucaoDTO> colecao = new ArrayList<ControleRendimentoExecucaoDTO>();
		colecao.add(controleRendimento1);
		colecao.add(controleRendimento2);
		colecao.add(controleRendimento3);
		colecao.add(controleRendimento4);

		return colecao;
	}

	public ArrayList<Object> calculaPontuacaoMesGrupo(String ano, String mes, Integer idGrupo, DocumentHTML document) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();

		Integer mesInt = Integer.parseInt(mes);
		Integer ultimoDia = ControleRendimento.confereQualUltimoDiaDoMes(controleRendimentoDto);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dataInicio = null;
		java.util.Date dataFim = null;
		try {
			dataInicio = (java.util.Date) formatador.parse("01/"+ mesInt +"/" + ano);
			dataFim = (java.util.Date) formatador.parse(ultimoDia +"/"+ mesInt +"/" + controleRendimentoDto.getAno());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		java.sql.Date dInicio = null;
		if(dataInicio != null){
			dInicio = new java.sql.Date (dataInicio.getTime());
		}
		java.sql.Date dFim = null;
		if(dataFim != null){
			dFim = new java.sql.Date (dataFim.getTime());
		}

		ArrayList<Object> listaResposta = calculaPontuacaoGrupo(idGrupo, dInicio, dFim, document);

		Integer qtdTotalSolicitacoes = (Integer)((ArrayList)listaResposta.get(0)).get(0) + (Integer)((ArrayList)listaResposta.get(1)).get(0) + (Integer)((ArrayList)listaResposta.get(2)).get(0);
		document.getElementById("qtdSolicitacoes").setValue(qtdTotalSolicitacoes.toString());

		Integer qtdTotalPontos = (Integer)((ArrayList)listaResposta.get(0)).get(1) + (Integer)((ArrayList)listaResposta.get(1)).get(1) + (Integer)((ArrayList)listaResposta.get(2)).get(1);
		document.getElementById("qtdTotalPontos").setValue(qtdTotalPontos.toString());

		Integer qtdPontosPositivos = (Integer)((ArrayList)listaResposta.get(0)).get(2) + (Integer)((ArrayList)listaResposta.get(1)).get(2) + (Integer)((ArrayList)listaResposta.get(2)).get(2);
		document.getElementById("qtdPontosPositivos").setValue(qtdPontosPositivos.toString());

		Integer qtdPontosNegativos = (Integer)((ArrayList)listaResposta.get(0)).get(3) + (Integer)((ArrayList)listaResposta.get(1)).get(3) + (Integer)((ArrayList)listaResposta.get(2)).get(3);
		document.getElementById("qtdPontosNegativos").setValue(qtdPontosNegativos.toString());

		String mediaRelativa = (String)((ArrayList)listaResposta.get(3)).get(0);
		document.getElementById("mediaRelativa").setValue(mediaRelativa);

		return listaResposta;
	}

	static Integer qtdItensRetornados = 0;
	public void calculaPontuacaoMesPorPessoa(String ano, String mes, Integer idGrupo, DocumentHTML document) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();

		Integer mesInt = Integer.parseInt(mes);
		Integer ultimoDia = ControleRendimento.confereQualUltimoDiaDoMes(controle);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dataInicio = null;
		java.util.Date dataFim = null;
		try {
			dataInicio = (java.util.Date) formatador.parse("01/"+ mesInt +"/" + ano);
			dataFim = (java.util.Date) formatador.parse(ultimoDia +"/"+ mesInt +"/" + ano);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		java.sql.Date dInicio = null;
		if(dataInicio != null){
			dInicio = new java.sql.Date (dataInicio.getTime());
		}
		java.sql.Date dFim = null;
		if(dataFim != null){
			dFim = new java.sql.Date (dataFim.getTime());
		}

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		Collection<ControleRendimentoExecucaoDTO> colecao = new ArrayList<ControleRendimentoExecucaoDTO>();

		Collection<EmpregadoDTO> listaEmpregadosFabrica = listaEmpregadosDoGrupo(controle.getIdGrupo());

		for (EmpregadoDTO empregadoDTO : listaEmpregadosFabrica) {

			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());

			if(usuarioDto != null){

				Integer qtdItensEntregues = new Integer(0);

				Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEDataAtendidas(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dInicio, dFim);
				if(listaSolicitacoesAtendidasPessoa != null)
					qtdItensEntregues = listaSolicitacoesAtendidasPessoa.size();

				//calcula os pontos positivos da pessoa
				Integer qtdTotalPontosPositivos = calculaQtdTotalPontosPositivosDaPessoa(listaSolicitacoesAtendidasPessoa);

				//calcular os pontos negativos por pessoa.
				qtdItensRetornados = 0;
				Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEData(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dInicio, dFim);

				Integer qtdTotalPontosnegativos = calculaQtdTotalPontosNegativosPessoa(listaTotalSolicitacoesPessoa, dInicio, dFim, document);

				Integer pontosTotal = qtdTotalPontosPositivos + qtdTotalPontosnegativos;

				ControleRendimentoExecucaoDTO controleRendimento1;

				if(pontosTotal < 0){
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Não", usuarioDto.getIdUsuario());
					controleRendimento1.setQtdPontosPositivos(qtdTotalPontosPositivos.toString());
					controleRendimento1.setQtdPontosNegativos(qtdTotalPontosnegativos.toString());
					controleRendimento1.setQtdItensRetornados(qtdItensRetornados + "");
					controleRendimento1.setQtdItensEntregues(qtdItensEntregues.toString());
					controleRendimento1.setQtdTotalPontos(pontosTotal.toString());
				} else {
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Sim", usuarioDto.getIdUsuario());
					controleRendimento1.setQtdPontosPositivos(qtdTotalPontosPositivos.toString());
					controleRendimento1.setQtdPontosNegativos(qtdTotalPontosnegativos.toString());
					controleRendimento1.setQtdItensRetornados(qtdItensRetornados + "");
					controleRendimento1.setQtdItensEntregues(qtdItensEntregues.toString());
					controleRendimento1.setQtdTotalPontos(pontosTotal.toString());
				}

				colecao.add(controleRendimento1);

			}
		}
		if (colecao != null) {

			colecaoPessoasGlobal = colecao;
			HTMLTable table;
			table = document.getTableById("tblrendimentoPessoa");
			table.deleteAllRows();
			table.addRowsByCollection(colecao, new String[] {"", "nomePessoa", "qtdTotalPontos", "aprovacao"}, null, null, null, null, null);
		}
	}

	public Integer calculaQtdTotalPontosPositivosDaPessoa(Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasPessoa) {

		Integer qtdPontosPositivosBaixo = new Integer(0);
		Integer qtdPontosPositivosMedio = new Integer(0);
		Integer qtdPontosPositivosAlto = new Integer(0);

		if(listaSolicitacoesAtendidasPessoa != null) {
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoesAtendidasPessoa) {
				if(solicitacaoServicoDTO.getDataHoraFim() != null && solicitacaoServicoDTO.getDataHoraFim().before(solicitacaoServicoDTO.getDataHoraLimite())){
					if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
						qtdPontosPositivosBaixo += pontosSlaBaixoNoPrazo;
					if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
						qtdPontosPositivosMedio += pontosSlaMedioNoPrazo;
					if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
						qtdPontosPositivosAlto += pontosSlaAltoNoPrazo;
				}
			}
		}

		Integer qtdTotalPontosPositivos = qtdPontosPositivosBaixo + qtdPontosPositivosMedio + qtdPontosPositivosAlto;

		return qtdTotalPontosPositivos;
	}

	public Integer calculaQtdTotalPontosNegativosPessoa(Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa, Date dataInicio, Date dataFim, DocumentHTML document) throws ServiceException, Exception{
		Integer qtdPontosNegativosBaixa = new Integer(0);
		Integer qtdPontosNegativosMedia = new Integer(0);
		Integer qtdPontosNegativosAlta = new Integer(0);

		//pontos de retorno
		if(listaTotalSolicitacoesPessoa != null){
			SolicitacaoServicoDTO solicitacaoAux = new SolicitacaoServicoDTO();
			solicitacaoAux.setDataInicio(dataInicio);
			solicitacaoAux.setDataFim(dataFim);
			RelatorioQuantitativoRetorno retornos = new RelatorioQuantitativoRetorno();
			Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = retornos.trazListaRetornos(solicitacaoAux, document.getLanguage());
			ArrayList<Integer> listaRetornosFiltrada = filtraListaRetornos(listaPorPeriodo);
			Boolean jaCalculou;
		for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaTotalSolicitacoesPessoa) {

			jaCalculou = false;

				for (Integer idSolicitacao : listaRetornosFiltrada) {

				if(idSolicitacao.intValue() == solicitacaoServicoDTO.getIdSolicitacaoServico().intValue()){
					//significa que houve retorno da atividade
					jaCalculou = true;
					//retorno + sla estourado
					if(solicitacaoServicoDTO.getDataHoraLimite() != null){
						if(solicitacaoServicoDTO.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())) {
							if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B")){
								qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhadoEForaDoPrazo;
								qtdItensRetornados++;
							} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M")) {
								qtdPontosNegativosMedia += pontosSlaMedioRetrabalhadoEForaDoPrazo;
								qtdItensRetornados++;
							} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A")) {
								qtdPontosNegativosAlta += pontosSlaAltoRetrabalhadoEForaDoPrazo;
								qtdItensRetornados++;
							}
						} else {
							//retorno no prazo
							if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B")) {
								qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhado;
								qtdItensRetornados++;
							} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M")) {
								qtdPontosNegativosMedia += pontosSlaMedioRetrabalhado;
								qtdItensRetornados++;
							} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A")) {
								qtdPontosNegativosAlta += pontosSlaAltoRetrabalhado;
								qtdItensRetornados++;
								}
							}
						}
					}

				}

				if(jaCalculou == false){
					//não houve retorno mas está fora do prazo
					if(solicitacaoServicoDTO.getDataHoraLimite() != null){
						if(solicitacaoServicoDTO.getDataHoraFim() == null && solicitacaoServicoDTO.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())){
							if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
								qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
								qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
								qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
						}
					}
					if(solicitacaoServicoDTO.getDataHoraFim() != null){
						if(solicitacaoServicoDTO.getDataHoraFim().after(solicitacaoServicoDTO.getDataHoraLimite())){
							if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
								qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
								qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
								qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
						}
					}
				}
			}
		}

		Integer qtdTotalPontosnegativos = qtdPontosNegativosBaixa + qtdPontosNegativosMedia + qtdPontosNegativosAlta;

		return qtdTotalPontosnegativos;
	}

	public void calculaPontuacaoMesPessoaGrupoTeste(String ano, String mes, Integer idGrupo, DocumentHTML document) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();

		Integer mesInt = Integer.parseInt(mes);
		Integer ultimoDia = ControleRendimento.confereQualUltimoDiaDoMes(controle);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dataInicio = null;
		java.util.Date dataFim = null;
		try {
			dataInicio = (java.util.Date) formatador.parse("01/"+ mesInt +"/" + ano);
			dataFim = (java.util.Date) formatador.parse(ultimoDia +"/"+ mesInt +"/" + ano);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		java.sql.Date dInicio = null;
		if(dataInicio != null){
			dInicio = new java.sql.Date (dataInicio.getTime());
		}
		java.sql.Date dFim = null;
		if(dataFim != null){
			dFim = new java.sql.Date (dataFim.getTime());
		}

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);

		Collection<ControleRendimentoExecucaoDTO> colecao = new ArrayList<ControleRendimentoExecucaoDTO>();

		Collection<EmpregadoDTO> listaEmpregadosFabrica = listaEmpregadosDoGrupo(controle.getIdGrupo());

		for (EmpregadoDTO empregadoDTO : listaEmpregadosFabrica) {

			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());

			if(usuarioDto != null){

				Integer qtdPontosPositivosBaixo = new Integer(0);
				Integer qtdPontosPositivosMedio = new Integer(0);
				Integer qtdPontosPositivosAlto = new Integer(0);

				Integer qtdItensEntregues = new Integer(0);

				Collection<OcorrenciaSolicitacaoDTO> listaSolicitacoesAtendidasPessoa = (Collection<OcorrenciaSolicitacaoDTO>) ocorrenciaSolicitacaoService.findByIdPessoaEDataAtendidasGrupoTeste(usuarioDto.getIdUsuario(), dataInicio, dataFim);
				if(listaSolicitacoesAtendidasPessoa != null)
					qtdItensEntregues = listaSolicitacoesAtendidasPessoa.size();

				if(listaSolicitacoesAtendidasPessoa != null) {
					for (OcorrenciaSolicitacaoDTO ocorrenciaDTO : listaSolicitacoesAtendidasPessoa) {
						if(ocorrenciaDTO.getUrgencia().equalsIgnoreCase("B"))
							qtdPontosPositivosBaixo += pontosSlaBaixoNoPrazo;
						if(ocorrenciaDTO.getUrgencia().equalsIgnoreCase("M"))
							qtdPontosPositivosMedio += pontosSlaMedioNoPrazo;
						if(ocorrenciaDTO.getUrgencia().equalsIgnoreCase("A"))
							qtdPontosPositivosAlto += pontosSlaAltoNoPrazo;
					}
				}


				Integer qtdTotalPontosPositivos = qtdPontosPositivosBaixo + qtdPontosPositivosMedio + qtdPontosPositivosAlto;

			//calcular os pontos negativos por pessoa.
				Integer qtdPontosNegativosBaixa = new Integer(0);
				Integer qtdPontosNegativosMedia = new Integer(0);
				Integer qtdPontosNegativosAlta = new Integer(0);

				Integer qtdItensRetornados = new Integer(0);

				Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEData(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dInicio, dFim);

				//pontos de retorno
				if(listaTotalSolicitacoesPessoa != null){
					Boolean jaCalculou;
					SolicitacaoServicoDTO solicitacaoAux = new SolicitacaoServicoDTO();
					solicitacaoAux.setDataInicio(dInicio);
					solicitacaoAux.setDataFim(dFim);
					RelatorioQuantitativoRetorno retornos = new RelatorioQuantitativoRetorno();
					Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = retornos.trazListaRetornos(solicitacaoAux, document.getLanguage());
				for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaTotalSolicitacoesPessoa) {

					jaCalculou = false;

					for (RelatorioQuantitativoRetornoListaDTO relatorioQuantitativoRetornoListaDTO : listaPorPeriodo) {

						Collection<RelatorioQuantitativoRetornoDTO> col = relatorioQuantitativoRetornoListaDTO.getListaPorPeriodo();
						for (RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO : col) {

						if(relatorioQuantitativoRetornoDTO.getIdSolicitacaoServico().intValue() == solicitacaoServicoDTO.getIdSolicitacaoServico().intValue()){
							//significa que houve retorno da atividade
							jaCalculou = true;
							//retorno + sla estourado
							if(solicitacaoServicoDTO.getDataHoraLimite() != null){
								if(solicitacaoServicoDTO.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())) {
									if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B")){
										qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhadoEForaDoPrazo;
										qtdItensRetornados++;
									} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M")) {
										qtdPontosNegativosMedia += pontosSlaMedioRetrabalhadoEForaDoPrazo;
										qtdItensRetornados++;
									} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A")) {
										qtdPontosNegativosAlta += pontosSlaAltoRetrabalhadoEForaDoPrazo;
										qtdItensRetornados++;
									}
								} else {
									//retorno no prazo
									if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B")) {
										qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhado;
										qtdItensRetornados++;
									} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M")) {
										qtdPontosNegativosMedia += pontosSlaMedioRetrabalhado;
										qtdItensRetornados++;
									} else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A")) {
										qtdPontosNegativosAlta += pontosSlaAltoRetrabalhado;
										qtdItensRetornados++;
										}
									}
								}
							}
						}
					}

					if(jaCalculou == false){
						//não houve retorno mas está fora do prazo
						if(solicitacaoServicoDTO.getDataHoraLimite() != null){
							if(solicitacaoServicoDTO.getDataHoraFim() == null && solicitacaoServicoDTO.getDataHoraLimite().after(UtilDatas.getDataHoraAtual())){
								if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
									qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
									qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
									qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
							}
						}
						if(solicitacaoServicoDTO.getDataHoraFim() != null){
							if(solicitacaoServicoDTO.getDataHoraFim().after(solicitacaoServicoDTO.getDataHoraLimite())){
								if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
									qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
									qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
									qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
							}
						}
					}
				}
			}


				Integer qtdTotalPontosnegativos = qtdPontosNegativosBaixa + qtdPontosNegativosMedia + qtdPontosNegativosAlta;

				Integer pontosTotal = qtdTotalPontosPositivos + qtdTotalPontosnegativos;

				ControleRendimentoExecucaoDTO controleRendimento1;

				if(pontosTotal < 0){
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Não", usuarioDto.getIdUsuario());
					controleRendimento1.setQtdPontosPositivos(qtdTotalPontosPositivos.toString());
					controleRendimento1.setQtdPontosNegativos(qtdTotalPontosnegativos.toString());
					controleRendimento1.setQtdItensRetornados(qtdItensRetornados.toString());
					controleRendimento1.setQtdItensEntregues(qtdItensEntregues.toString());
					controleRendimento1.setQtdTotalPontos(pontosTotal.toString());
				} else {
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Sim", usuarioDto.getIdUsuario());
					controleRendimento1.setQtdPontosPositivos(qtdTotalPontosPositivos.toString());
					controleRendimento1.setQtdPontosNegativos(qtdTotalPontosnegativos.toString());
					controleRendimento1.setQtdItensRetornados(qtdItensRetornados.toString());
					controleRendimento1.setQtdItensEntregues(qtdItensEntregues.toString());
					controleRendimento1.setQtdTotalPontos(pontosTotal.toString());
				}

				colecao.add(controleRendimento1);

			}
		}
		if (colecao != null) {

			colecaoPessoasGlobal = colecao;
			HTMLTable table;
			table = document.getTableById("tblrendimentoPessoa");
			table.deleteAllRows();
			table.addRowsByCollection(colecao, new String[] {"", "nomePessoa", "qtdTotalPontos", "aprovacao"}, null, null, null, null, null);
		}
	}

	public ArrayList calculaPontuacaoGrupo(Integer idGrupo, Date dataInicio, Date dataFim, DocumentHTML document) throws ServiceException, Exception{

		String nivelExcelencia = getNivelExcelenciaExigido();

		if(!validaFormatoNivelExcelencia(nivelExcelencia)){
			document.alert("Verifique o formato do parâmetro de nível de excelência, deve possuir apenas números.");
			return null;
		}

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		//traz todas as solicitacoes de servico
		Collection<SolicitacaoServicoDTO> listaSolicitacoesGrupoBaixa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataBaixa(idGrupo, dataInicio, dataFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesGrupoMedia = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataMedia(idGrupo, dataInicio, dataFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesGrupoAlta = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataAlta(idGrupo, dataInicio, dataFim);

		Collection<SolicitacaoServicoDTO> listaSolicitacoesGrupoTotal = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataTotal(idGrupo, dataInicio, dataFim);

		Integer qtdSolicitacoesGrupoBaixa = new Integer(0);
		Integer qtdSolicitacoesGrupoMedia = new Integer(0);
		Integer qtdSolicitacoesGrupoAlta = new Integer(0);

		if(listaSolicitacoesGrupoBaixa != null)
			qtdSolicitacoesGrupoBaixa = listaSolicitacoesGrupoBaixa.size();

		if(listaSolicitacoesGrupoMedia != null)
			qtdSolicitacoesGrupoMedia = listaSolicitacoesGrupoMedia.size();

		if (listaSolicitacoesGrupoAlta != null)
			qtdSolicitacoesGrupoAlta = listaSolicitacoesGrupoAlta.size();

		Integer qtdTotalPontosBaixa = qtdSolicitacoesGrupoBaixa * pontosSlaBaixoNoPrazo;
		Integer qtdTotalPontosMedia = qtdSolicitacoesGrupoMedia * pontosSlaMedioNoPrazo;
		Integer qtdTotalPontosAlta = qtdSolicitacoesGrupoAlta * pontosSlaAltoNoPrazo;

		//traz todas as solicitações que foram finalizadas no prazo
		Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasGrupoBaixa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataAtendidasBaixa(idGrupo, dataInicio, dataFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasGrupoMedia = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataAtendidasMedia(idGrupo, dataInicio, dataFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasGrupoAlta = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataAtendidasAlta(idGrupo, dataInicio, dataFim);

		Integer qtdSolicitacoesAtendidasGrupoBaixa = new Integer(0);
		Integer qtdSolicitacoesAtendidasGrupoMedia = new Integer(0);
		Integer qtdSolicitacoesAtendidasGrupoAlta = new Integer(0);

		if(listaSolicitacoesAtendidasGrupoBaixa != null)
			qtdSolicitacoesAtendidasGrupoBaixa = listaSolicitacoesAtendidasGrupoBaixa.size();

		if(listaSolicitacoesAtendidasGrupoMedia != null)
			qtdSolicitacoesAtendidasGrupoMedia = listaSolicitacoesAtendidasGrupoMedia.size();

		if (listaSolicitacoesAtendidasGrupoAlta != null)
			qtdSolicitacoesAtendidasGrupoAlta = listaSolicitacoesAtendidasGrupoAlta.size();

		Integer qtdPontosPositivosBaixa = qtdSolicitacoesAtendidasGrupoBaixa * pontosSlaBaixoNoPrazo;
		Integer qtdPontosPositivosMedia = qtdSolicitacoesAtendidasGrupoMedia * pontosSlaMedioNoPrazo;
		Integer qtdPontosPositivosAlta = qtdSolicitacoesAtendidasGrupoAlta * pontosSlaAltoNoPrazo;

		//Calcular os pontos negativos.
		//Solicitacoes com retorno

		Integer qtdPontosNegativosBaixa = new Integer(0);
		Integer qtdPontosNegativosMedia = new Integer(0);
		Integer qtdPontosNegativosAlta = new Integer(0);

		//variavel criada para controlar a pontuação negativa, não deixar calcular duas vezes caso haja retorno
		Boolean jaCalculou;
		if(listaSolicitacoesGrupoTotal != null){

			SolicitacaoServicoDTO solicitacaoAux = new SolicitacaoServicoDTO();
			solicitacaoAux.setDataInicio(dataInicio);
			solicitacaoAux.setDataFim(dataFim);
			RelatorioQuantitativoRetorno retornos = new RelatorioQuantitativoRetorno();
			Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = retornos.trazListaRetornos(solicitacaoAux, document.getLanguage());

		for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoesGrupoTotal) {

			jaCalculou = false;

			for (RelatorioQuantitativoRetornoListaDTO relatorioQuantitativoRetornoListaDTO : listaPorPeriodo) {

				Collection<RelatorioQuantitativoRetornoDTO> col = relatorioQuantitativoRetornoListaDTO.getListaPorPeriodo();
				for (RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO : col) {

				if(relatorioQuantitativoRetornoDTO.getIdSolicitacaoServico().intValue() == solicitacaoServicoDTO.getIdSolicitacaoServico().intValue()){
					//significa que houve retorno da atividade
					jaCalculou = true;
					//retorno + sla estourado
					if(solicitacaoServicoDTO.getDataHoraLimite() != null){
						if(solicitacaoServicoDTO.getDataHoraFim() == null && solicitacaoServicoDTO.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())) {
							if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
								qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhadoEForaDoPrazo;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
								qtdPontosNegativosMedia += pontosSlaMedioRetrabalhadoEForaDoPrazo;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
								qtdPontosNegativosAlta += pontosSlaAltoRetrabalhadoEForaDoPrazo;
						} else {
							//retorno no prazo
							if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
								qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhado;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
								qtdPontosNegativosMedia += pontosSlaMedioRetrabalhado;
							else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
								qtdPontosNegativosAlta += pontosSlaAltoRetrabalhado;
							}
						}
					break;
					}
				}
			}

			if(jaCalculou == false){
				//não houve retorno mas está fora do prazo
				if(solicitacaoServicoDTO.getDataHoraLimite() != null){
					if(solicitacaoServicoDTO.getDataHoraFim() == null && solicitacaoServicoDTO.getDataHoraLimite().after(UtilDatas.getDataHoraAtual())){
						if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
							qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
						else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
							qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
						else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
							qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
					}
				}
				if(solicitacaoServicoDTO.getDataHoraFim() != null){
					if(solicitacaoServicoDTO.getDataHoraFim().after(solicitacaoServicoDTO.getDataHoraLimite())){
						if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
							qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
						else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
							qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
						else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
							qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
					}
				}
			}
		}
		}

		//criar as variaveis com pontos totais

		Integer pontosFinaisBaixa = qtdPontosPositivosBaixa + qtdPontosNegativosBaixa;
		Integer pontosFinaisMedia = qtdPontosPositivosMedia + qtdPontosNegativosMedia;
		Integer pontosFinaisAlta = qtdPontosPositivosAlta + qtdPontosNegativosAlta;

		//calcula a média de rendimento parcial
		Double mediaBaixaDouble = new Double(0);
		Double mediaMediaDouble = new Double(0);
		Double mediaAltaDouble = new Double(0);

		if(qtdTotalPontosBaixa != 0)
			mediaBaixaDouble = new Double( (double)qtdPontosPositivosBaixa / qtdTotalPontosBaixa).doubleValue();
		if(qtdTotalPontosMedia != 0)
			mediaMediaDouble = new Double( (double)qtdPontosPositivosMedia/ qtdTotalPontosMedia).doubleValue();
		if(qtdTotalPontosAlta != 0)
			mediaAltaDouble = new Double( (double)qtdPontosPositivosAlta / qtdTotalPontosAlta).doubleValue();

		String mediaBaixa = Math.round(mediaBaixaDouble * 100) + "%";
		String mediaMedia = Math.round(mediaMediaDouble * 100) + "%";
		String mediaAlta = Math.round(mediaAltaDouble * 100) + "%";

		//calcula média geral
		//verificar se houve solicitação daquele tipo
		int divisor = 0;
		if (qtdSolicitacoesGrupoBaixa != 0)
			divisor++;
		if(qtdSolicitacoesGrupoMedia != 0)
			divisor++;
		if(qtdSolicitacoesGrupoAlta != 0)
			divisor++;

		Long totalRodape = Math.round((mediaBaixaDouble * 100) + (mediaMediaDouble * 100) + (mediaAltaDouble * 100));
		if(divisor == 0)
			totalRodape = 0l;
		else
			totalRodape = totalRodape/divisor;

		Double a = Double.parseDouble(qtdPontosPositivosBaixa.toString());
		Double b = Double.parseDouble(qtdPontosPositivosMedia.toString());
		Double c = Double.parseDouble(qtdPontosPositivosAlta.toString());

		Double x = Double.parseDouble(qtdTotalPontosBaixa.toString());
		Double y = Double.parseDouble(qtdTotalPontosMedia.toString());
		Double z = Double.parseDouble(qtdTotalPontosAlta.toString());

		Double total;
		if(x.doubleValue() == 0 && y.doubleValue() == 0 && z.doubleValue() == 0){
			total = 0d;
		} else {
			total= ((a + b + c) / (x + y + z)) * 100;
		}

		String strTotal = UtilFormatacao.formatDouble(total, 2);
		Long nivelExcelenciaLong = Long.parseLong(nivelExcelencia);

		ArrayList<Object> listaRespostaBaixaGrupo = new ArrayList<Object>();
		listaRespostaBaixaGrupo.add(qtdSolicitacoesGrupoBaixa);
		listaRespostaBaixaGrupo.add(qtdTotalPontosBaixa);
		listaRespostaBaixaGrupo.add(qtdPontosPositivosBaixa);
		listaRespostaBaixaGrupo.add(qtdPontosNegativosBaixa);
		listaRespostaBaixaGrupo.add(mediaBaixa);

		ArrayList<Object> listaRespostaMediaGrupo = new ArrayList<Object>();
		listaRespostaMediaGrupo.add(qtdSolicitacoesGrupoMedia);
		listaRespostaMediaGrupo.add(qtdTotalPontosMedia);
		listaRespostaMediaGrupo.add(qtdPontosPositivosMedia);
		listaRespostaMediaGrupo.add(qtdPontosNegativosMedia);
		listaRespostaMediaGrupo.add(mediaMedia);

		ArrayList<Object> listaRespostaAltaGrupo = new ArrayList<Object>();
		listaRespostaAltaGrupo.add(qtdSolicitacoesGrupoAlta);
		listaRespostaAltaGrupo.add(qtdTotalPontosAlta);
		listaRespostaAltaGrupo.add(qtdPontosPositivosAlta);
		listaRespostaAltaGrupo.add(qtdPontosNegativosAlta);
		listaRespostaAltaGrupo.add(mediaAlta);

		ArrayList<Object> listaRodape = new ArrayList<Object>();
		listaRodape.add(qtdSolicitacoesGrupoBaixa + qtdSolicitacoesGrupoMedia + qtdSolicitacoesGrupoAlta);
		listaRodape.add(qtdTotalPontosBaixa + qtdTotalPontosMedia + qtdTotalPontosAlta);
		listaRodape.add(qtdPontosPositivosBaixa + qtdPontosPositivosMedia + qtdPontosPositivosAlta);
		listaRodape.add(qtdPontosNegativosBaixa + qtdPontosNegativosMedia + qtdPontosNegativosAlta);
		listaRodape.add(totalRodape);

		ArrayList<Object> listaTotal = new ArrayList<Object>();
		listaTotal.add(strTotal);
		listaTotal.add(nivelExcelenciaLong);

		ArrayList<Object> listaResposta = new ArrayList<Object>();
		listaResposta.add(listaRespostaBaixaGrupo);
		listaResposta.add(listaRespostaMediaGrupo);
		listaResposta.add(listaRespostaAltaGrupo);
		listaResposta.add(listaTotal);
		listaResposta.add(listaRodape);

		return listaResposta;
	}

	public String getNivelExcelenciaExigido(){

		String nivelExcelencia = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NIVEL_EXCELENCIA_EXIGIDO, "90");
		return nivelExcelencia;
	}

	public boolean validaFormatoNivelExcelencia(String nivelExcelencia){

		int tamanhoString = nivelExcelencia.trim().length();

		try {
			Integer numero = Integer.parseInt(nivelExcelencia.substring(0, tamanhoString));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void calculaPontuacaoPessoaExecucao(Integer idPessoa, Date dataInicio, Date dataFim, DocumentHTML document) throws ServiceException, Exception{

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();

		Collection<ControleRendimentoExecucaoDTO> colecao = new ArrayList<ControleRendimentoExecucaoDTO>();

		if(idPessoa == null){
			//listar todas as pessoas
			Collection<EmpregadoDTO> listaEmpregadosFabrica = listaEmpregadosDoGrupo(controle.getIdGrupo());

			for (EmpregadoDTO empregadoDTO : listaEmpregadosFabrica) {

				UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
				UsuarioDTO usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());

				if(usuarioDto != null){

					Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEDataAtendidas(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dataInicio, dataFim);

					//calcula os pontos positivos da pessoa
					Integer qtdTotalPontosPositivos = calculaQtdTotalPontosPositivosDaPessoa(listaSolicitacoesAtendidasPessoa);

					//calcular os pontos negativos por pessoa.
					qtdItensRetornados = 0;
					Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEData(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dataInicio, dataFim);

					Integer qtdTotalPontosnegativos = calculaQtdTotalPontosNegativosPessoa(listaTotalSolicitacoesPessoa, dataInicio, dataFim, document);

					Integer pontosTotal = qtdTotalPontosPositivos + qtdTotalPontosnegativos;


					ControleRendimentoExecucaoDTO controleRendimento1;

					if(pontosTotal < 0)
						controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Não", usuarioDto.getIdUsuario());
					else
						controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Sim", usuarioDto.getIdUsuario());

					colecao.add(controleRendimento1);

				}
			}

			if (colecao != null) {

				HTMLTable table;
				table = document.getTableById("tblrendimentoPessoaExecucao");
				table.deleteAllRows();
				table.addRowsByCollection(colecao, new String[] {"", "nomePessoa", "qtdTotalPontos", "aprovacao"}, null, null, null, null, null);
			}

		} else {
			EmpregadoDTO empregado = new EmpregadoDTO();
			empregado.setIdEmpregado(idPessoa);
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregado);

			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());

			if(usuarioDto != null){

				Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEDataAtendidas(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dataInicio, dataFim);

				//calcula os pontos positivos da pessoa
				Integer qtdTotalPontosPositivos = calculaQtdTotalPontosPositivosDaPessoa(listaSolicitacoesAtendidasPessoa);

				//calcular os pontos negativos por pessoa.
				Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEData(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dataInicio, dataFim);
				Integer qtdTotalPontosnegativos = calculaQtdTotalPontosNegativosPessoa(listaTotalSolicitacoesPessoa, dataInicio, dataFim, document);

				Integer pontosTotal = qtdTotalPontosPositivos + qtdTotalPontosnegativos;

				ControleRendimentoExecucaoDTO controleRendimento1;

				if(pontosTotal < 0)
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Não", usuarioDto.getIdUsuario());
				else
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Sim", usuarioDto.getIdUsuario());

				colecao.add(controleRendimento1);

				if (colecao != null) {

					HTMLTable table;
					table = document.getTableById("tblrendimentoPessoa");
					table.deleteAllRows();
					table.addRowsByCollection(colecao, new String[] {"", "nomePessoa", "qtdTotalPontos", "aprovacao"}, null, null, null, null, null);
				}
			}
		}

		return;
	}

	public void calculaPontuacaoPessoaExecucaoGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim, DocumentHTML document) throws ServiceException, Exception{
		//esse método serve apenas para calcular a pontuação do grupo Teste da fábrica
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		OcorrenciaSolicitacaoService ocorrenciaServicoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
		ControleRendimentoExecucaoDTO controle = (ControleRendimentoExecucaoDTO) document.getBean();

		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		Collection<ControleRendimentoExecucaoDTO> colecao = new ArrayList<ControleRendimentoExecucaoDTO>();

		if(idPessoa == null){
			//listar todas as pessoas
			Collection<EmpregadoDTO> listaEmpregadosTeste = listaEmpregadosDoGrupo(idGrupoTeste);

			for (EmpregadoDTO empregadoTesteDTO : listaEmpregadosTeste) {

				Integer qtdPontosPositivosBaixo = new Integer(0);
				Integer qtdPontosPositivosMedio = new Integer(0);
				Integer qtdPontosPositivosAlto = new Integer(0);

				Integer qtdPontosNegativosBaixa = new Integer(0);
				Integer qtdPontosNegativosMedia = new Integer(0);
				Integer qtdPontosNegativosAlta = new Integer(0);

				UsuarioDTO usuarioTesteDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoTesteDTO.getIdEmpregado());

				if(usuarioTesteDto != null){

					Collection<OcorrenciaSolicitacaoDTO> listaSolicitacoesAtendidasPessoa = (Collection<OcorrenciaSolicitacaoDTO>) ocorrenciaServicoService.findByIdPessoaEDataAtendidasGrupoTeste(usuarioTesteDto.getIdUsuario(), dataInicio, dataFim);

					if(listaSolicitacoesAtendidasPessoa != null) {
						for (OcorrenciaSolicitacaoDTO ocorrenciaDTO : listaSolicitacoesAtendidasPessoa) {
							if(ocorrenciaDTO.getUrgencia().equalsIgnoreCase("B"))
								qtdPontosPositivosBaixo += pontosSlaBaixoNoPrazo;
							if(ocorrenciaDTO.getUrgencia().equalsIgnoreCase("M"))
								qtdPontosPositivosMedio += pontosSlaMedioNoPrazo;
							if(ocorrenciaDTO.getUrgencia().equalsIgnoreCase("A"))
								qtdPontosPositivosAlto += pontosSlaAltoNoPrazo;
							}
						}
					}
					Integer qtdTotalPontosPositivos = qtdPontosPositivosBaixo + qtdPontosPositivosMedio + qtdPontosPositivosAlto;

				//calcular os pontos negativos por pessoa.

					Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa = null;
					if(controle != null && usuarioTesteDto != null){
						listaTotalSolicitacoesPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEData(controle.getIdGrupo(), usuarioTesteDto.getLogin(), usuarioTesteDto.getNomeUsuario(), dataInicio, dataFim);
					}

					//pontos de retorno
					if(listaTotalSolicitacoesPessoa != null){
						Boolean jaCalculou;
						SolicitacaoServicoDTO solicitacaoAux = new SolicitacaoServicoDTO();
						solicitacaoAux.setDataInicio(dataInicio);
						solicitacaoAux.setDataFim(dataFim);
						RelatorioQuantitativoRetorno retornos = new RelatorioQuantitativoRetorno();
						Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = retornos.trazListaRetornos(solicitacaoAux, document.getLanguage());
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaTotalSolicitacoesPessoa) {

						jaCalculou = false;

						for (RelatorioQuantitativoRetornoListaDTO relatorioQuantitativoRetornoListaDTO : listaPorPeriodo) {

							Collection<RelatorioQuantitativoRetornoDTO> col = relatorioQuantitativoRetornoListaDTO.getListaPorPeriodo();
							for (RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO : col) {

							if(relatorioQuantitativoRetornoDTO.getIdSolicitacaoServico().intValue() == solicitacaoServicoDTO.getIdSolicitacaoServico().intValue()){
								//significa que houve retorno da atividade
								jaCalculou = true;
								//retorno + sla estourado
								if(solicitacaoServicoDTO.getDataHoraLimite() != null){
									if(solicitacaoServicoDTO.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())) {
										if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
											qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhadoEForaDoPrazo;
										else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
											qtdPontosNegativosMedia += pontosSlaMedioRetrabalhadoEForaDoPrazo;
										else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
											qtdPontosNegativosAlta += pontosSlaAltoRetrabalhadoEForaDoPrazo;
									} else {
										//retorno no prazo
										if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
											qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhado;
										else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
											qtdPontosNegativosMedia += pontosSlaMedioRetrabalhado;
										else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
											qtdPontosNegativosAlta += pontosSlaAltoRetrabalhado;
										}
									}
								}
							}
						}

						if(jaCalculou == false){
							//não houve retorno mas está fora do prazo
							if(solicitacaoServicoDTO.getDataHoraLimite() != null){
								if(solicitacaoServicoDTO.getDataHoraFim() == null && solicitacaoServicoDTO.getDataHoraLimite().after(UtilDatas.getDataHoraAtual())){
									if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
										qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
										qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
										qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
								}
							}
							if(solicitacaoServicoDTO.getDataHoraFim() != null){
								if(solicitacaoServicoDTO.getDataHoraFim().after(solicitacaoServicoDTO.getDataHoraLimite())){
									if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
										qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
										qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
										qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
								}
							}
						}
					}
				}


					Integer qtdTotalPontosnegativos = qtdPontosNegativosBaixa + qtdPontosNegativosMedia + qtdPontosNegativosAlta;

					Integer pontosTotal = qtdTotalPontosPositivos + qtdTotalPontosnegativos;

					ControleRendimentoExecucaoDTO controleRendimento1;

					if(pontosTotal < 0)
						controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioTesteDto.getNomeUsuario(), pontosTotal.toString(), "Não", usuarioTesteDto.getIdUsuario());
					else
						controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioTesteDto.getNomeUsuario(), pontosTotal.toString(), "Sim", usuarioTesteDto.getIdUsuario());

					colecao.add(controleRendimento1);

					if (colecao != null) {

						HTMLTable table;
						table = document.getTableById("tblrendimentoPessoaExecucao");
						table.deleteAllRows();
						table.addRowsByCollection(colecao, new String[] {"", "nomePessoa", "qtdTotalPontos", "aprovacao"}, null, null, null, null, null);
					}
				}


		}else {
			EmpregadoDTO empregado = new EmpregadoDTO();
			empregado.setIdEmpregado(idPessoa);
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregado);

			//UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());

			if(usuarioDto != null){

				Integer qtdPontosPositivosBaixo = new Integer(0);
				Integer qtdPontosPositivosMedio = new Integer(0);
				Integer qtdPontosPositivosAlto = new Integer(0);

				Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEDataAtendidas(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dataInicio, dataFim);
				if(listaSolicitacoesAtendidasPessoa != null) {
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoesAtendidasPessoa) {

						if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
							qtdPontosPositivosBaixo += pontosSlaBaixoNoPrazo;
						if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
							qtdPontosPositivosMedio += pontosSlaMedioNoPrazo;
						if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
							qtdPontosPositivosAlto += pontosSlaAltoNoPrazo;

					}
				}

				Integer qtdTotalPontosPositivos = qtdPontosPositivosBaixo + qtdPontosPositivosMedio + qtdPontosPositivosAlto;

			//calcular os pontos negativos por pessoa.
				Integer qtdPontosNegativosBaixa = new Integer(0);
				Integer qtdPontosNegativosMedia = new Integer(0);
				Integer qtdPontosNegativosAlta = new Integer(0);

				Collection<SolicitacaoServicoDTO> listaTotalSolicitacoesPessoa = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdPessoaEData(controle.getIdGrupo(), usuarioDto.getLogin(), usuarioDto.getNomeUsuario(), dataInicio, dataFim);

				//pontos de retorno
				if(listaTotalSolicitacoesPessoa != null){
					Boolean jaCalculou;
					SolicitacaoServicoDTO solicitacaoAux = new SolicitacaoServicoDTO();
					solicitacaoAux.setDataInicio(dataInicio);
					solicitacaoAux.setDataFim(dataFim);
					RelatorioQuantitativoRetorno retornos = new RelatorioQuantitativoRetorno();
					Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = retornos.trazListaRetornos(solicitacaoAux, document.getLanguage());
				for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaTotalSolicitacoesPessoa) {

					jaCalculou = false;

					for (RelatorioQuantitativoRetornoListaDTO relatorioQuantitativoRetornoListaDTO : listaPorPeriodo) {

						Collection<RelatorioQuantitativoRetornoDTO> col = relatorioQuantitativoRetornoListaDTO.getListaPorPeriodo();
						for (RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO : col) {

						if(relatorioQuantitativoRetornoDTO.getIdSolicitacaoServico().intValue() == solicitacaoServicoDTO.getIdSolicitacaoServico().intValue()){
							//significa que houve retorno da atividade
							jaCalculou = true;
							//retorno + sla estourado
							if(solicitacaoServicoDTO.getDataHoraLimite() != null){
								if(solicitacaoServicoDTO.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())) {
									if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
										qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhadoEForaDoPrazo;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
										qtdPontosNegativosMedia += pontosSlaMedioRetrabalhadoEForaDoPrazo;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
										qtdPontosNegativosAlta += pontosSlaAltoRetrabalhadoEForaDoPrazo;
								} else {
									//retorno no prazo
									if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
										qtdPontosNegativosBaixa += pontosSlaBaixoRetrabalhado;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
										qtdPontosNegativosMedia += pontosSlaMedioRetrabalhado;
									else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
										qtdPontosNegativosAlta += pontosSlaAltoRetrabalhado;
									}
								}
							}
						}
					}

					if(jaCalculou == false){
						//não houve retorno mas está fora do prazo
						if(solicitacaoServicoDTO.getDataHoraLimite() != null){
							if(solicitacaoServicoDTO.getDataHoraFim() == null && solicitacaoServicoDTO.getDataHoraLimite().after(UtilDatas.getDataHoraAtual())){
								if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
									qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
									qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
									qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
							}
						}
						if(solicitacaoServicoDTO.getDataHoraFim() != null){
							if(solicitacaoServicoDTO.getDataHoraFim().after(solicitacaoServicoDTO.getDataHoraLimite())){
								if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("B"))
									qtdPontosNegativosBaixa += pontosSlaBaixoForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("M"))
									qtdPontosNegativosMedia += pontosSlaMedioForaDoPrazo;
								else if(solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase("A"))
									qtdPontosNegativosAlta += pontosSlaAltoForaDoPrazo;
							}
						}
					}
				}
			}


				Integer qtdTotalPontosnegativos = qtdPontosNegativosBaixa + qtdPontosNegativosMedia + qtdPontosNegativosAlta;

				Integer pontosTotal = qtdTotalPontosPositivos + qtdTotalPontosnegativos;

				ControleRendimentoExecucaoDTO controleRendimento1;

				if(pontosTotal < 0)
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Não", usuarioDto.getIdUsuario());
				else
					controleRendimento1 = new ControleRendimentoExecucaoDTO(usuarioDto.getNomeUsuario(), pontosTotal.toString(), "Sim", usuarioDto.getIdUsuario());

				colecao.add(controleRendimento1);

				if (colecao != null) {

					HTMLTable table;
					table = document.getTableById("tblrendimentoPessoa");
					table.deleteAllRows();
					table.addRowsByCollection(colecao, new String[] {"", "nomePessoa", "qtdTotalPontos", "aprovacao"}, null, null, null, null, null);
				}
			}
		}

		return;
	}

	public Collection<EmpregadoDTO> listaEmpregadosDoGrupo(Integer idGrupo) throws ServiceException, Exception{
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		Collection<GrupoEmpregadoDTO> grupo = (Collection<GrupoEmpregadoDTO>) grupoEmpregadoService.findByIdGrupo(idGrupo);
		Collection<EmpregadoDTO> empregados = new ArrayList<EmpregadoDTO>();

		for (GrupoEmpregadoDTO grupoEmpregadoDTO : grupo) {
			EmpregadoDTO empregado = new EmpregadoDTO();
			empregado.setIdEmpregado(grupoEmpregadoDTO.getIdEmpregado());
			empregado = (EmpregadoDTO) empregadoService.restore(empregado);
			empregados.add(empregado);
		}

		return empregados;
	}

	public void fecharMes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleRendimento controleRendimento = new ControleRendimento();
		controleRendimento.fecharMes(document, request, response, colecaoPessoasGlobal);
	}

	public void imprimirRelatorioFuncionarioMaisEficiente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoUsuarioDTO usuarioDto = new ControleRendimentoUsuarioDTO();
		usuarioDto.setIdGrupo(controleRendimentoDto.getIdGrupoRelatorio());
		usuarioDto.setMes(controleRendimentoDto.getMes());
		usuarioDto.setAno(controleRendimentoDto.getAno());

		if(controleRendimentoDto.getIdGrupoRelatorio() == null){
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "controle.grupoObrigatorio"));
			return;
		}

		ControleRendimentoUsuarioService usuarioService = (ControleRendimentoUsuarioService) ServiceLocator.getInstance().getService(ControleRendimentoUsuarioService.class, null);

		Collection<ControleRendimentoUsuarioDTO> col = usuarioService.findByIdControleRendimentoUsuario(usuarioDto.getIdGrupo(), usuarioDto.getMes(), usuarioDto.getAno());

		ControleRendimentoUsuarioDTO usuarioMaisEficiente = new ControleRendimentoUsuarioDTO();
		if (col != null) {
			Collections.sort((List<ControleRendimentoUsuarioDTO>) col);
			Collections.reverse((List<ControleRendimentoUsuarioDTO>) col);
			if(col.iterator().hasNext())
				usuarioMaisEficiente = col.iterator().next();

		}

		UsuarioService usuarioService1 = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UsuarioDTO usuarioAux = new UsuarioDTO();
		usuarioAux.setIdUsuario(usuarioMaisEficiente.getIdUsuario());
		UsuarioDTO usuario = null;
		if(usuarioAux.getIdUsuario() != null) {
			usuario = (UsuarioDTO) usuarioService1.restore(usuarioAux);
			usuarioMaisEficiente.setNomeUsuario(usuario.getNomeUsuario());
		}

		if(usuarioMaisEficiente.getQtdTotalPontos() != null)
			usuarioMaisEficiente.setQtdTotalPontos(usuarioMaisEficiente.getQtdTotalPontos()/10);

		Collection<ControleRendimentoUsuarioDTO> colFinal = new ArrayList<ControleRendimentoUsuarioDTO>();
		colFinal.add(usuarioMaisEficiente);

		JRDataSource dataSource = null;

		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "controle.melhorFuncionario"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());

		if (colFinal == null || colFinal.size() == 0 || usuarioMaisEficiente.getIdUsuario() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			dataSource = new JRBeanCollectionDataSource(colFinal);

			java.util.Date dt = new java.util.Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleRendimentoFuncionarioMaisEficiente.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleRendimentoFuncionarioMaisEficiente" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioControleRendimentoFuncionarioMaisEficiente" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void imprimirRelatorioFuncionarioMenosEficiente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoUsuarioDTO usuarioDto = new ControleRendimentoUsuarioDTO();
		usuarioDto.setIdGrupo(controleRendimentoDto.getIdGrupoRelatorio());
		usuarioDto.setMes(controleRendimentoDto.getMes());
		usuarioDto.setAno(controleRendimentoDto.getAno());

		if(controleRendimentoDto.getIdGrupoRelatorio() == null){
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "controle.grupoObrigatorio"));
			return;
		}

		ControleRendimentoUsuarioService usuarioService = (ControleRendimentoUsuarioService) ServiceLocator.getInstance().getService(ControleRendimentoUsuarioService.class, null);

		Collection<ControleRendimentoUsuarioDTO> col = usuarioService.findByIdControleRendimentoUsuario(usuarioDto.getIdGrupo(), usuarioDto.getMes(), usuarioDto.getAno());

		ControleRendimentoUsuarioDTO usuarioMenosEficiente = new ControleRendimentoUsuarioDTO();
		if (col != null) {
			Collections.sort((List<ControleRendimentoUsuarioDTO>) col);
			if(col.iterator().hasNext())
				usuarioMenosEficiente = col.iterator().next();

		}

		if (col == null || col.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		UsuarioService usuarioService1 = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UsuarioDTO usuarioAux = new UsuarioDTO();
		usuarioAux.setIdUsuario(usuarioMenosEficiente.getIdUsuario());
		if(usuarioAux.getIdUsuario() != null) {
			UsuarioDTO usuario = (UsuarioDTO) usuarioService1.restore(usuarioAux);
			usuarioMenosEficiente.setNomeUsuario(usuario.getNomeUsuario());
		}

		if(usuarioMenosEficiente.getQtdPontosPositivos() == null)
			usuarioMenosEficiente.setQtdPontosPositivos("0");
		if(usuarioMenosEficiente.getQtdPontosNegativos() == null)
			usuarioMenosEficiente.setQtdPontosNegativos("0");
		if(usuarioMenosEficiente.getQtdItensEntregues() == null)
			usuarioMenosEficiente.setQtdItensEntregues("0");
		if(usuarioMenosEficiente.getQtdItensRetornados() == null)
			usuarioMenosEficiente.setQtdItensRetornados("0");

		usuarioMenosEficiente.setQtdTotalPontos(usuarioMenosEficiente.getQtdTotalPontos()/10);

		Collection<ControleRendimentoUsuarioDTO> colFinal = new ArrayList<ControleRendimentoUsuarioDTO>();
		colFinal.add(usuarioMenosEficiente);

		JRDataSource dataSource = null;

		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "controle.piorFuncionario"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());


		if (colFinal == null || colFinal.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			dataSource = new JRBeanCollectionDataSource(colFinal);

			java.util.Date dt = new java.util.Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleRendimentoFuncionarioMenosEficiente.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleRendimentoFuncionarioMenosEficiente" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioControleRendimentoFuncionarioMenosEficiente" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void imprimirRelatorioMelhoresFuncionarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoUsuarioDTO usuarioDto = new ControleRendimentoUsuarioDTO();
		usuarioDto.setIdGrupo(controleRendimentoDto.getIdGrupoRelatorio());
		usuarioDto.setMes(controleRendimentoDto.getMes());
		usuarioDto.setAno(controleRendimentoDto.getAno());

		if(controleRendimentoDto.getIdGrupoRelatorio() == null){
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "controle.grupoObrigatorio"));
			return;
		}

		Calendar dataAtual = new GregorianCalendar();
		java.util.Date trialTime = new java.util.Date();
		dataAtual.setTime(trialTime);

		Calendar dataSeisMesesAtras = calculaPeriodoPesquisa();

		//subtrair 1, pois o mês atual não foi fechado.
		String mesAtual = (dataAtual.get(GregorianCalendar.MONTH)) + "";
		String anoAtual = (dataAtual.get(GregorianCalendar.YEAR)) + "";

		String mesSeisMesesAtras = (dataSeisMesesAtras.get(GregorianCalendar.MONTH)) + "";
		String anoSeisMesesAtras = (dataSeisMesesAtras.get(GregorianCalendar.YEAR)) + "";


		ControleRendimentoUsuarioService usuarioService = (ControleRendimentoUsuarioService) ServiceLocator.getInstance().getService(ControleRendimentoUsuarioService.class, null);
		Collection<ControleRendimentoUsuarioDTO> col;
		Collection<ControleRendimentoUsuarioDTO> colecaoIdsUsuarios;

		if(dataAtual.get(GregorianCalendar.YEAR) != dataSeisMesesAtras.get(GregorianCalendar.YEAR) && Integer.parseInt(mesSeisMesesAtras) > Integer.parseInt(mesAtual)){
			col = usuarioService.findByIdControleRendimentoMelhoresUsuario(usuarioDto.getIdGrupo(), mesSeisMesesAtras, mesAtual, anoSeisMesesAtras, anoAtual, true);
			//traz os id's dos usuarios do banco.
			colecaoIdsUsuarios = usuarioService.findIdsControleRendimentoUsuarioPorPeriodo(usuarioDto.getIdGrupo(), mesSeisMesesAtras, mesAtual, anoAtual, anoAtual, true);
		} else if(Integer.parseInt(mesSeisMesesAtras) > Integer.parseInt(mesAtual)){
			//significa que é de um ano para o outro
			col = usuarioService.findByIdControleRendimentoMelhoresUsuario(usuarioDto.getIdGrupo(), mesSeisMesesAtras, mesAtual, anoAtual, anoAtual, true);
			//traz os id's dos usuarios do banco.
			colecaoIdsUsuarios = usuarioService.findIdsControleRendimentoUsuarioPorPeriodo(usuarioDto.getIdGrupo(), mesSeisMesesAtras, mesAtual, anoAtual, anoAtual, true);
		} else {
			col = usuarioService.findByIdControleRendimentoMelhoresUsuario(usuarioDto.getIdGrupo(), mesSeisMesesAtras, mesAtual, anoAtual, anoAtual, false);
			//traz os id's dos usuarios do banco.
			colecaoIdsUsuarios = usuarioService.findIdsControleRendimentoUsuarioPorPeriodo(usuarioDto.getIdGrupo(), mesSeisMesesAtras, mesAtual, anoAtual, anoAtual, false);
		}

		if(colecaoIdsUsuarios != null) {
			for (ControleRendimentoUsuarioDTO controleRendimentoUsuarioDTO : colecaoIdsUsuarios) {
				controleRendimentoUsuarioDTO.setQtdTotalPontos(0.0);
			}
		}

		Double somatorio = 0.0;
		if (col != null) {
			for (ControleRendimentoUsuarioDTO usuario : colecaoIdsUsuarios) {
				somatorio = 0.0;
				for (ControleRendimentoUsuarioDTO controleRendimentoUsuarioComPontos : col) {

					if(usuario.getIdUsuario().intValue() == controleRendimentoUsuarioComPontos.getIdUsuario().intValue()){
						if(controleRendimentoUsuarioComPontos.getQtdTotalPontos() != null) {
							somatorio = controleRendimentoUsuarioComPontos.getQtdTotalPontos() + usuario.getQtdTotalPontos();
							usuario.setQtdTotalPontos(somatorio);
						}
					}
				}
			}
		}

		if(colecaoIdsUsuarios != null){
			for (ControleRendimentoUsuarioDTO controleRendimentoUsuarioDTO : colecaoIdsUsuarios) {
				controleRendimentoUsuarioDTO.setQtdTotalPontos(controleRendimentoUsuarioDTO.getQtdTotalPontos() / 10);
			}
		}
		//selecionar os 10 melhores = maior qtdTotalPontos

		ArrayList melhores10Funcionarios = new ArrayList();

		if(colecaoIdsUsuarios != null) {
			for (ControleRendimentoUsuarioDTO usuario : colecaoIdsUsuarios) {
				melhores10Funcionarios.add(usuario);
			}
		}

		Collections.sort(melhores10Funcionarios);
		Collections.reverse(melhores10Funcionarios);


		ArrayList melhores10FuncionariosAux = new ArrayList();

		//pega só os 10 primeiros registros, se não tiver 10, pega todos
		int qtdMinimaFuncionarios = melhores10Funcionarios.size();
		if(qtdMinimaFuncionarios < 10){
			for (int i = 0; i < qtdMinimaFuncionarios; i++) {
				melhores10FuncionariosAux.add(melhores10Funcionarios.get(i));
			}
		} else {
			for (int i = 0; i < 10; i++) {
				melhores10FuncionariosAux.add(melhores10Funcionarios.get(i));
			}
		}

		UsuarioService usuarioService1 = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		double somatorioPontos = 0;
		for (int i = 0; i < melhores10FuncionariosAux.size(); i++) {
			UsuarioDTO usuarioAux = new UsuarioDTO();
			usuarioAux.setIdUsuario(((ControleRendimentoUsuarioDTO) melhores10FuncionariosAux.get(i)).getIdUsuario());
			UsuarioDTO usuario = (UsuarioDTO) usuarioService1.restore(usuarioAux);
			((ControleRendimentoUsuarioDTO) melhores10FuncionariosAux.get(i)).setNomeUsuario(usuario.getNomeUsuario());
			somatorioPontos += ((ControleRendimentoUsuarioDTO) melhores10FuncionariosAux.get(i)).getQtdTotalPontos();
		}

		JRDataSource dataSource = null;

		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);

		String dataFormatadaParaORelatorio = (Integer.parseInt(mesSeisMesesAtras) + 1) + "/" + anoSeisMesesAtras  + " a " + mesAtual + "/" + anoAtual;

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "controle.melhoresFuncionarios"));
		parametros.put("PERIODO_PESQUISA", UtilI18N.internacionaliza(request, "avaliacaocontrato.periodo") + " " + dataFormatadaParaORelatorio);
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("SOMATORIO_PONTOS", UtilI18N.internacionaliza(request, "controle.somatorioTotalPontos") + ": " + somatorioPontos);

		if (melhores10FuncionariosAux == null || melhores10FuncionariosAux.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			dataSource = new JRBeanCollectionDataSource(melhores10FuncionariosAux);

			java.util.Date dt = new java.util.Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleRendimentoMelhoresFuncionarios.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleRendimentoMelhoresFuncionarios" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioControleRendimentoMelhoresFuncionarios" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public Calendar calculaPeriodoPesquisa(){
		 Calendar calendar = new GregorianCalendar();
		 java.util.Date trialTime = new java.util.Date();
		 calendar.setTime(trialTime);
		 calendar.add(Calendar.MONTH, -6);

		 return calendar;
	}

	public void imprimirRelatorioPorGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoDTO grupoDto = new ControleRendimentoDTO();
		grupoDto.setIdGrupo(controleRendimentoDto.getIdGrupoRelatorio());
		grupoDto.setMesApuracao(controleRendimentoDto.getMes());
		grupoDto.setAnoApuracao(controleRendimentoDto.getAno());

		if(controleRendimentoDto.getIdGrupoRelatorio() == null){
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "controle.grupoObrigatorio"));
			return;
		}

		ControleRendimentoService usuarioService = (ControleRendimentoService) ServiceLocator.getInstance().getService(ControleRendimentoService.class, null);

		Collection<ControleRendimentoDTO> col = usuarioService.findPontuacaoRendimento(grupoDto.getMesApuracao(), grupoDto.getAnoApuracao(), grupoDto.getIdGrupo());

		ControleRendimentoDTO grupoAvaliado = new ControleRendimentoDTO();
		if (col != null) {
			if(col.iterator().hasNext()){
				grupoAvaliado = col.iterator().next();
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		GrupoDTO grupoAux = new GrupoDTO();
		grupoAux.setIdGrupo(grupoAvaliado.getIdGrupo());
		GrupoDTO grupo = (GrupoDTO) grupoService.restore(grupoAux);
		grupoAvaliado.setNomeGrupo(grupo.getNome());


		Collection<ControleRendimentoDTO> colFinal = new ArrayList<ControleRendimentoDTO>();
		colFinal.add(grupoAvaliado);

		JRDataSource dataSource = null;

		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "controle.rendimentoGrupo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());

		if (colFinal == null || colFinal.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			dataSource = new JRBeanCollectionDataSource(colFinal);

			java.util.Date dt = new java.util.Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleRendimentoPorGrupo.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleRendimentoPorGrupo" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioControleRendimentoPorGrupo" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void imprimirRelatorioPorPessoa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoUsuarioDTO usuarioDto = new ControleRendimentoUsuarioDTO();
		usuarioDto.setIdGrupo(controleRendimentoDto.getIdGrupoRelatorio());
		usuarioDto.setMes(controleRendimentoDto.getMes());
		usuarioDto.setAno(controleRendimentoDto.getAno());

		ControleRendimentoUsuarioService usuarioService = (ControleRendimentoUsuarioService) ServiceLocator.getInstance().getService(ControleRendimentoUsuarioService.class, null);

		Collection<ControleRendimentoUsuarioDTO> col = usuarioService.findByIdControleRendimentoUsuario(usuarioDto.getIdGrupo(), usuarioDto.getMes(), usuarioDto.getAno());

		UsuarioService usuarioService1 = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		if(col != null) {
			for (ControleRendimentoUsuarioDTO controleRendimentoUsuarioDTO : col) {
				UsuarioDTO usuarioAux = new UsuarioDTO();
				usuarioAux.setIdUsuario(controleRendimentoUsuarioDTO.getIdUsuario());
				UsuarioDTO usuario = (UsuarioDTO) usuarioService1.restore(usuarioAux);
				controleRendimentoUsuarioDTO.setNomeUsuario(usuario.getNomeUsuario());
				controleRendimentoUsuarioDTO.setQtdTotalPontos(controleRendimentoUsuarioDTO.getQtdTotalPontos()/10);
			}
		}

		JRDataSource dataSource = null;

		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "controle.rendimentoPessoa"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());

		if (col == null || col.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			dataSource = new JRBeanCollectionDataSource(col);

			java.util.Date dt = new java.util.Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleRendimentoPorPessoa.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleRendimentoPorPessoa" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioControleRendimentoPorPessoa" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void imprimirRelatorioMediaAtraso(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControleRendimentoExecucaoDTO controleRendimentoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoDTO grupoDto = new ControleRendimentoDTO();
		grupoDto.setIdGrupo(controleRendimentoDto.getIdGrupoRelatorio());
		grupoDto.setMesApuracao(controleRendimentoDto.getMes());
		grupoDto.setAnoApuracao(controleRendimentoDto.getAno());
		String numeroSolicitacoes = "";

		if(controleRendimentoDto.getIdGrupoRelatorio() == null){
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "controle.grupoObrigatorio"));
			return;
		}

		Integer mesInt = Integer.parseInt(controleRendimentoDto.getMes());
		Integer ultimoDia = ControleRendimento.confereQualUltimoDiaDoMes(controleRendimentoDto);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dataInicio = null;
		java.util.Date dataFim = null;
		try {
			dataInicio = (java.util.Date) formatador.parse("01/"+ mesInt +"/" + controleRendimentoDto.getAno());
			dataFim = (java.util.Date) formatador.parse(ultimoDia +"/"+ mesInt +"/" + controleRendimentoDto.getAno());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		java.sql.Date dInicio = null;
		if(dataInicio != null){
			dInicio = new java.sql.Date (dataInicio.getTime());
		}
		java.sql.Date dFim = null;
		if(dataFim != null){
			dFim = new java.sql.Date (dataFim.getTime());
		}

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		Collection<SolicitacaoServicoDTO> listaSolicitacoesGrupoTotal = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataTotal(controleRendimentoDto.getIdGrupoRelatorio(), dInicio, dFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesAtendidasTotal = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataAtendidasTotal(controleRendimentoDto.getIdGrupoRelatorio(), dInicio, dFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesAtrasadasTotal = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataAtrasadasTotal(controleRendimentoDto.getIdGrupoRelatorio(), dInicio, dFim);
		Collection<SolicitacaoServicoDTO> listaSolicitacoesSuspensasTotal = (Collection<SolicitacaoServicoDTO>) solicitacaoServicoService.findByIdGrupoEDataSuspensasTotal(controleRendimentoDto.getIdGrupoRelatorio(), dInicio, dFim);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		GrupoDTO grupoAux = new GrupoDTO();
		grupoAux.setIdGrupo(grupoDto.getIdGrupo());
		GrupoDTO grupo = (GrupoDTO) grupoService.restore(grupoAux);
		grupoDto.setNomeGrupo(grupo.getNome());

		if(listaSolicitacoesGrupoTotal != null){
			grupoDto.setQtdSolicitacoes(listaSolicitacoesGrupoTotal.size());
		} else {
			grupoDto.setQtdSolicitacoes(0);
		}

		if(listaSolicitacoesAtendidasTotal != null){
			grupoDto.setQtdItensEntreguesNoPrazo(listaSolicitacoesAtendidasTotal.size() + "");
		} else {
			grupoDto.setQtdItensEntreguesNoPrazo("0");
		}

		if(listaSolicitacoesAtrasadasTotal != null){
			grupoDto.setQtdItensAtrasados(listaSolicitacoesAtrasadasTotal.size() + "");
			int contadorMaximo = listaSolicitacoesAtrasadasTotal.size();
			int contador = 0;
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoesAtrasadasTotal) {
				contador++;
				if(contador < contadorMaximo)
					numeroSolicitacoes += solicitacaoServicoDTO.getIdSolicitacaoServico().toString() + ", ";
				else
					numeroSolicitacoes += solicitacaoServicoDTO.getIdSolicitacaoServico().toString() + ".";
			}

		} else {
			grupoDto.setQtdItensAtrasados("0");
		}

		if(listaSolicitacoesSuspensasTotal != null){
			grupoDto.setQtdItensSuspensos(listaSolicitacoesSuspensasTotal.size() + "");
		} else {
			grupoDto.setQtdItensSuspensos("0");
		}

		grupoDto.setNumeroSolicitacoes(numeroSolicitacoes);

		Double mediaAtraso = (Double.parseDouble(grupoDto.getQtdItensAtrasados()) / Double.parseDouble(grupoDto.getQtdSolicitacoes() + "")) * 100;
		String mediaAtrasoStr = String.format( "%.2f", mediaAtraso ) + "%";
		grupoDto.setMediaAtraso(mediaAtrasoStr);



		Collection<ControleRendimentoDTO> colFinal = new ArrayList<ControleRendimentoDTO>();
		colFinal.add(grupoDto);

		JRDataSource dataSource = null;

		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "controle.mediaAtrasoEquipe"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());

		if (colFinal == null || colFinal.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			dataSource = new JRBeanCollectionDataSource(colFinal);

			java.util.Date dt = new java.util.Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleRendimentoMediaAtrasoEquipe.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleRendimentoMediaAtrasoEquipe" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioControleRendimentoMediaAtrasoEquipe" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public ArrayList<Integer> filtraListaRetornos(Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo){
		ArrayList<Integer> arrayPrincipal = new ArrayList<Integer>();

		if(listaPorPeriodo != null && (listaPorPeriodo != null || !listaPorPeriodo.isEmpty())){

			for (RelatorioQuantitativoRetornoListaDTO relatorioQuantitativoRetornoListaDTO : listaPorPeriodo) {
				Collection<RelatorioQuantitativoRetornoDTO> col = relatorioQuantitativoRetornoListaDTO.getListaPorPeriodo();
				for (RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO : col) {
					arrayPrincipal.add(relatorioQuantitativoRetornoDTO.getIdSolicitacaoServico());
				}
			}

			for (int i = 0; i < arrayPrincipal.size() - 1; i++ ) {
				if(arrayPrincipal.get(i).intValue() == arrayPrincipal.get(i+1).intValue()){
					arrayPrincipal.remove(i+1);
				}
			}

			return arrayPrincipal;

		}else{
			return null;
		}
	}
}
