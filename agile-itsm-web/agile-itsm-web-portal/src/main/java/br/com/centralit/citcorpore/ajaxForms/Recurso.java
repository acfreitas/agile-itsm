package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.FaixaValoresRecursoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoRecursosDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NagiosConexaoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoRecursosService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NagiosConexaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.RecursoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;
@SuppressWarnings({ "rawtypes", "unused" })
public class Recurso extends AjaxFormAction {

	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Boolean acao = false;
		GrupoRecursosService grupoRecursosService = (GrupoRecursosService) ServiceLocator.getInstance().getService(GrupoRecursosService.class, null);
		Collection colGrupos = grupoRecursosService.list();
		HTMLSelect idGrupoRecurso = document.getSelectById("idGrupoRecurso");
		idGrupoRecurso.removeAllOptions();
		idGrupoRecurso.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colGrupos != null) {
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoRecursosDTO grupoRecursosDTO = (GrupoRecursosDTO) it.next();
				if (grupoRecursosDTO.getDeleted() == null	|| grupoRecursosDTO.getDeleted().equalsIgnoreCase("n")) {
					idGrupoRecurso.addOption("" + grupoRecursosDTO.getIdGrupoRecurso(),StringEscapeUtils.escapeJavaScript(grupoRecursosDTO.getNomeGrupoRecurso()));
				}
			}
		}

		RecursoService recursoService = (RecursoService) ServiceLocator.getInstance().getService(RecursoService.class, null);
		Collection colRecursos = recursoService.list();
		HTMLSelect idRecursoPai = document.getSelectById("idRecursoPai");
		idRecursoPai.removeAllOptions();
		idRecursoPai.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colRecursos != null) {
			for (Iterator it = colRecursos.iterator(); it.hasNext();) {
				RecursoDTO recursoDTO = (RecursoDTO) it.next();
				if (recursoDTO.getDeleted() == null
						|| recursoDTO.getDeleted().equalsIgnoreCase("n")) {
					idRecursoPai.addOption("" + recursoDTO.getIdRecurso(),StringEscapeUtils.escapeJavaScript(recursoDTO.getNomeRecurso()));
				}
			}
		}
		
		NagiosConexaoService nagiosConexaoService = (NagiosConexaoService) ServiceLocator.getInstance().getService(NagiosConexaoService.class, null);
		Collection colNagiosConexao = nagiosConexaoService.list();
		HTMLSelect idNagiosConexao = document.getSelectById("idNagiosConexao");
		idNagiosConexao.removeAllOptions();
		idNagiosConexao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colNagiosConexao != null) {
			for (Iterator it = colNagiosConexao.iterator(); it.hasNext();) {
				NagiosConexaoDTO nagiosConexaoDTO = (NagiosConexaoDTO) it.next();
				idNagiosConexao.addOption("" + nagiosConexaoDTO.getIdNagiosConexao(), StringEscapeUtils.escapeJavaScript(nagiosConexaoDTO.getNome()));
			}
		}	
		
		CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
		Collection colCalendarios = calendarioService.list();
		HTMLSelect idCalendario = document.getSelectById("idCalendario");
		idCalendario.removeAllOptions();
		idCalendario.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colCalendarios != null) {
			for (Iterator it = colCalendarios.iterator(); it.hasNext();) {
				CalendarioDTO calendarioDTO = (CalendarioDTO) it.next();
				idCalendario.addOption("" + calendarioDTO.getIdCalendario(), StringEscapeUtils.escapeJavaScript(calendarioDTO.getDescricao()));
			}
		}	
		
		EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class, null);
		Collection colEventosMon = eventoMonitoramentoService.list();
		HTMLSelect idEventoMonitoramento = document.getSelectById("idEventoMonitoramento");
		idEventoMonitoramento.removeAllOptions();
		idEventoMonitoramento.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colEventosMon != null) {
			for (Iterator it = colEventosMon.iterator(); it.hasNext();) {
				EventoMonitoramentoDTO eventoMonitoramentoDTO = (EventoMonitoramentoDTO) it.next();
				idEventoMonitoramento.addOption("" + eventoMonitoramentoDTO.getIdEventoMonitoramento(), StringEscapeUtils.escapeJavaScript(eventoMonitoramentoDTO.getNomeEvento()));
			}
		}			
		
		HTMLSelect urgencia = (HTMLSelect) document.getSelectById("urgencia");
		urgencia.removeAllOptions();
		urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
		urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
		urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

		HTMLSelect impacto = (HTMLSelect) document.getSelectById("impacto");
		impacto.removeAllOptions();
		impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
		impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
		impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
		
		OrigemAtendimentoService origemService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		HTMLSelect idOrigem = (HTMLSelect) document.getSelectById("idOrigem");
		idOrigem.removeAllOptions();
		idOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection col = origemService.list();
		if (col != null) {
			idOrigem.addOptions(col, "idOrigem", "descricao", null);
		}
		
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		Collection colContratos = contratoService.list();	
		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
		((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
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
					if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ")";
						//((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), nomeContrato);
						contratoDto.setNome(nomeContrato);
						listaContratos.add(contratoDto);
					}
				}
			}
		}
		if(listaContratos != null){
			((HTMLSelect) document.getSelectById("idContrato")).addOptions(listaContratos, "idContrato", "nome", null);		
		}
		
		HTMLSelect idGrupo = (HTMLSelect) document.getSelectById("idGrupo");
		idGrupo.removeAllOptions();
		idGrupo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGruposPess = grupoSegurancaService.findGruposAtivos();
		if (colGruposPess != null)
			idGrupo.addOptions(colGruposPess, "idGrupo", "nome", null);
		
	}

	@Override
	public Class getBeanClass() {
		return RecursoDTO.class;
	}
	
	public void listaServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		RecursoDTO recursoDTO = (RecursoDTO) document.getBean();
		HTMLSelect idServico = (HTMLSelect) document.getSelectById("idServico");
		idServico.removeAllOptions();
		Collection col = servicoService.findByIdTipoDemandaAndIdContrato(3, recursoDTO.getIdContrato(), null);
		if (col != null) {
			idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

			for (Iterator it = col.iterator(); it.hasNext();) {
				ServicoDTO servicoDTO = (ServicoDTO) it.next();
				if (servicoDTO.getDeleted() == null || servicoDTO.getDeleted().equalsIgnoreCase("N")) {
					if (servicoDTO.getIdSituacaoServico().intValue() == 1) { // ATIVO
						idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), StringEscapeUtils.escapeJavaScript(servicoDTO.getNomeServico()));
					}
				}
			}
		}
	}
	
	public void listaICSVinc(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		RecursoDTO recursoDTO = (RecursoDTO) document.getBean();
		HTMLSelect idItemConfiguracao = (HTMLSelect) document.getSelectById("idItemConfiguracao");
		idItemConfiguracao.removeAllOptions();
		if (recursoDTO.getIdItemConfiguracaoPai() != null){
			Collection col = itemConfiguracaoService.findByIdItemConfiguracaoPai(recursoDTO.getIdItemConfiguracaoPai());
			if (col != null) {
				idItemConfiguracao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	
				for (Iterator it = col.iterator(); it.hasNext();) {
					ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO) it.next();
					idItemConfiguracao.addOptionIfNotExists("" + itemConfiguracaoDTO.getIdItemConfiguracao(), StringEscapeUtils.escapeJavaScript(itemConfiguracaoDTO.getIdentificacao()));
				}
			}
		}
	}	

	public void save(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RecursoDTO objDto = (RecursoDTO) document.getBean();
		Collection colFaixasValores = WebUtil.deserializeCollectionFromRequest(
				FaixaValoresRecursoDTO.class, "colFaixasSerialize", request);
		RecursoService objService = (RecursoService) ServiceLocator
				.getInstance().getService(RecursoService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator
				.getInstance().getService(ServicoContratoService.class, null);		

		objDto.setColFaixasValores(colFaixasValores);
		
		if (objDto.getIdItemConfiguracao() == null){
			objDto.setIdItemConfiguracao(objDto.getIdItemConfiguracaoPai());
		}
		
		if (objDto.getIdServico() != null && objDto.getIdContrato() != null){
			ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.findByIdContratoAndIdServico(objDto.getIdContrato(), objDto.getIdServico());
			if (servicoContratoDTO != null){
				objDto.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
			}
		}
		
		if (objDto.getIdRecurso() == null
				|| objDto.getIdRecurso().intValue() == 0) {
			objService.create(objDto);
		} else {
			objService.update(objDto);
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		HTMLTable tbl = document.getTableById("tblFaixaControle");
		tbl.deleteAllRows();

		document.alert("Registro gravado com sucesso!");
	}

	public void restore(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RecursoDTO objDto = (RecursoDTO) document.getBean();
		RecursoService recursoService = (RecursoService) ServiceLocator.getInstance().getService(RecursoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

		objDto = (RecursoDTO) recursoService.restore(objDto);
		
		HTMLTable tbl = document.getTableById("tblFaixaControle");
		tbl.deleteAllRows();

		if (objDto != null && objDto.getColFaixasValores() != null) {
			tbl.addRowsByCollection(objDto.getColFaixasValores(), new String[] {
					"valorInicio", "valorFim", "corInner", "" }, null,
					"Registro Duplicado", new String[] { "geraBtnExcluir" },
					null, null);
		}
		
		HTMLSelect idServico = (HTMLSelect) document.getSelectById("idServico");
		idServico.removeAllOptions();		
		HTMLSelect idItemConfiguracao = (HTMLSelect) document.getSelectById("idItemConfiguracao");
		idItemConfiguracao.removeAllOptions();		

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(objDto);
		if (objDto != null){
			if (objDto.getIdSolicitante() != null){
				EmpregadoDTO empregadoDto = new EmpregadoDTO();
				empregadoDto.setIdEmpregado(objDto.getIdSolicitante());
				try{
					empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
				}catch(Exception e){
					empregadoDto = null;
				}
				if (empregadoDto != null){
					String str = empregadoDto.getNome();
					str = str.replaceAll("'", "");
					document.executeScript("LOOKUP_SOLICITANTE.setvalue('" + empregadoDto.getIdEmpregado() + "')");
					document.executeScript("document.form.nomeSolicitante.value = '" + str + "'");
					document.executeScript("LOOKUP_SOLICITANTE.settext('" + str + "')");
				}
			}
			if (objDto.getIdServicoContrato() != null){
				ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
				servicoContratoDTO.setIdServicoContrato(objDto.getIdServicoContrato());
				try{
					servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
				}catch(Exception e){
					e.printStackTrace();
					servicoContratoDTO = null;
				}
				if (servicoContratoDTO != null){
					document.getElementById("idContrato").setValue("" + servicoContratoDTO.getIdContrato());
					
					ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
					Collection col = servicoService.findByIdTipoDemandaAndIdContrato(3, servicoContratoDTO.getIdContrato(), null);
					if (col != null) {
						idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

						for (Iterator it = col.iterator(); it.hasNext();) {
							ServicoDTO servicoDTO = (ServicoDTO) it.next();
							if (servicoDTO.getDeleted() == null || servicoDTO.getDeleted().equalsIgnoreCase("N")) {
								if (servicoDTO.getIdSituacaoServico().intValue() == 1) { // ATIVO
									idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
								}
							}
						}
					}
					
					document.getElementById("idServico").setValue("" + servicoContratoDTO.getIdServico());
				}
			}
			if (objDto.getIdItemConfiguracao() != null){
				ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
				itemConfiguracaoDTO.setIdItemConfiguracao(objDto.getIdItemConfiguracao());
				try{
					itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);
				}catch(Exception e){
					itemConfiguracaoDTO = null;
				}
				if (itemConfiguracaoDTO != null){
					if (itemConfiguracaoDTO.getIdItemConfiguracaoPai() != null){
						ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
						itemConfiguracaoAux.setIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracaoPai());
						try{
							itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoAux);
						}catch(Exception e){
							itemConfiguracaoAux = null;
						}						
						if (itemConfiguracaoAux != null){
							String str = itemConfiguracaoAux.getIdentificacao();
							str = str.replaceAll("'", "");						
							document.executeScript("LOOKUP_PESQUISAITEMCONFIGURACAO.setvalue('" + itemConfiguracaoAux.getIdItemConfiguracao() + "')");
							document.executeScript("document.form.idItemConfiguracaoPai.value = '" + itemConfiguracaoAux.getIdItemConfiguracao() + "'");
							document.executeScript("document.form.nomeItemConfiguracaoPai.value = '" + str + "'");
							document.executeScript("LOOKUP_PESQUISAITEMCONFIGURACAO.settext('" + str + "')");
							
							Collection col = itemConfiguracaoService.findByIdItemConfiguracaoPai(itemConfiguracaoDTO.getIdItemConfiguracaoPai());
							if (col != null) {
								idItemConfiguracao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
					
								for (Iterator it = col.iterator(); it.hasNext();) {
									ItemConfiguracaoDTO itemConfiguracaoAuxDTO = (ItemConfiguracaoDTO) it.next();
									idItemConfiguracao.addOptionIfNotExists("" + itemConfiguracaoAuxDTO.getIdItemConfiguracao(), StringEscapeUtils.escapeJavaScript(itemConfiguracaoAuxDTO.getIdentificacao()));
								}
								idItemConfiguracao.setValue("" + itemConfiguracaoDTO.getIdItemConfiguracao());
							}
						}
					}else{
						String str = itemConfiguracaoDTO.getIdentificacao();
						str = str.replaceAll("'", "");						
						document.executeScript("LOOKUP_PESQUISAITEMCONFIGURACAO.setvalue('" + itemConfiguracaoDTO.getIdItemConfiguracao() + "')");
						document.executeScript("document.form.idItemConfiguracaoPai.value = '" + itemConfiguracaoDTO.getIdItemConfiguracao() + "'");
						document.executeScript("document.form.nomeItemConfiguracaoPai.value = '" + str + "'");
						document.executeScript("LOOKUP_PESQUISAITEMCONFIGURACAO.settext('" + str + "')");
						
						Collection col = itemConfiguracaoService.findByIdItemConfiguracaoPai(itemConfiguracaoDTO.getIdItemConfiguracao());
						if (col != null) {
							idItemConfiguracao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
				
							for (Iterator it = col.iterator(); it.hasNext();) {
								ItemConfiguracaoDTO itemConfiguracaoAuxDTO = (ItemConfiguracaoDTO) it.next();
								idItemConfiguracao.addOptionIfNotExists("" + itemConfiguracaoAuxDTO.getIdItemConfiguracao(), itemConfiguracaoAuxDTO.getIdentificacao());
							}
						}						
					}
				}
			}
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
}
