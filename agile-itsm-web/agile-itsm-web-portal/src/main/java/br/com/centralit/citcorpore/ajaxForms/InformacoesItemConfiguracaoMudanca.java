package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoTreeDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.MidiaSoftwareService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.RiscoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class InformacoesItemConfiguracaoMudanca extends AjaxFormAction{
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*Combo de status*/
		HTMLSelect comboStatus = (HTMLSelect) document.getSelectById("status");
		comboStatus.removeAllOptions();
		for (Enumerados.StatusIC st : Enumerados.StatusIC.values()) {
			comboStatus.addOption(st.getItem().toString(), UtilI18N.internacionaliza(request,st.getChaveInternacionalizacao()));
		}
		
		/*Combo de criticidade*/
		HTMLSelect comboCriticiidade = (HTMLSelect) document.getSelectById("criticidade");
		comboCriticiidade.removeAllOptions();
		for (Enumerados.CriticidadeIC ct : Enumerados.CriticidadeIC.values()) {
			comboCriticiidade.addOption(ct.getItem().toString(), UtilI18N.internacionaliza(request,ct.getDescricao()));
		}
		
		//Combo Contrato
		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);
		
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
		
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
		itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restoreByIdItemConfiguracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao());
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		
		StringBuilder subDiv = new StringBuilder();
		
		if(itemConfiguracaoDTO!=null) {
			EmpregadoDTO empregadoBean = new EmpregadoDTO();
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			if(itemConfiguracaoDTO.getIdProprietario()!=null) {
				empregadoBean.setIdEmpregado(itemConfiguracaoDTO.getIdProprietario());
				empregadoBean = (EmpregadoDTO) empregadoService.restore(empregadoBean);
				if(empregadoBean!=null) {
					itemConfiguracaoDTO.setNomeUsuario(empregadoBean.getNome());
				}
			}
			if(itemConfiguracaoDTO.getIdResponsavel()!=null){
				empregadoBean.setIdEmpregado(itemConfiguracaoDTO.getIdResponsavel());
				empregadoBean = (EmpregadoDTO) empregadoService.restore(empregadoBean);
				if(empregadoBean!=null) {
					itemConfiguracaoDTO.setNomeResponsavel(empregadoBean.getNome());
				}
			}
			
			GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();
			ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
			ParametroCorporeDTO parametroDTO = new ParametroCorporeDTO();
			
			if(itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null && itemConfiguracaoDTO.getIdGrupoItemConfiguracao() > 0){
				grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
				GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
				grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoItemConfiguracaoDTO);
				if(grupoItemConfiguracaoDTO!=null) {
					itemConfiguracaoDTO.setIdGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao());
					itemConfiguracaoDTO.setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
				}
			}
			
			if(itemConfiguracaoDTO.getIdItemConfiguracaoPai()!=null){
				ItemConfiguracaoDTO itemPai = new ItemConfiguracaoDTO();
				itemPai = (ItemConfiguracaoDTO) itemConfiguracaoService.restoreByIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracaoPai());
				
				GrupoItemConfiguracaoDTO grupoPai = new GrupoItemConfiguracaoDTO();
				if(itemPai.getIdGrupoItemConfiguracao()!=null) {
					grupoPai.setIdGrupoItemConfiguracao(itemPai.getIdGrupoItemConfiguracao());
					GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
					grupoPai = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoPai);
				}
				
				/* Cabeçalho */
				subDiv.append("<div id='cabecalhoInf'>");
				subDiv.append("<h2>Item de Configura&ccedil;&atilde;o</h2>");			
				subDiv.append("<b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+": </b>" + itemPai.getIdentificacao() + "");			
				if(itemPai.getIdGrupoItemConfiguracao() != null && itemPai.getIdGrupoItemConfiguracao() > 0){
					if(grupoPai!=null)
						subDiv.append("<b>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.grupo")+": </b>" + grupoPai.getNomeGrupoItemConfiguracao());
				} else {
	
					if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS, " ").trim().equalsIgnoreCase("")) {
						subDiv.append("<b>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.grupo")+": </b>" + ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS, " "));
					}
				}			
				subDiv.append("</div>");
				
//				document.executeScript("$('#titleITem').text('"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itemConfiguracaoRelacionado")+"')");
				document.executeScript("$('#principalInf').css('display','block')");
//				document.executeScript("$('#itemPai').css('display','block')");
				
			} else {
				document.executeScript("$('#titleITem').text('"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itemConfiguracao")+"')");			
				document.executeScript("$('#principalInf').css('display','none')");
				document.executeScript("$('#divGrupoItemConfiguracao').css('display','block')");
				
			}
			
			TipoItemConfiguracaoService tipoItemService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
			SolicitacaoServicoService solService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			RequisicaoMudancaService reqMudanca = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
			ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
			ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
			MidiaSoftwareService midiaSoftwareService = (MidiaSoftwareService) ServiceLocator.getInstance().getService(MidiaSoftwareService.class, null);
			RequisicaoLiberacaoService liberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
			Collection<ValorDTO> valores;
			/*Setando o form do item de configuração pai*/		
			HTMLForm form = document.getForm("form");
			form.clear();
			
			if(itemConfiguracaoDTO.getIdTipoItemConfiguracao()!=null && itemConfiguracaoDTO.getIdTipoItemConfiguracao().intValue() > 0) {
				TipoItemConfiguracaoDTO item = new TipoItemConfiguracaoDTO();
				item.setId(itemConfiguracaoDTO.getIdTipoItemConfiguracao());
				item = (TipoItemConfiguracaoDTO) tipoItemService.restore(item);
				if(item!=null)
					itemConfiguracaoDTO.setNomeTipoItemConfiguracao(item.getNome());
				//restoreTipoItemConfiguracaoValues(document, request, response);
			}
			
			if(itemConfiguracaoDTO.getIdIncidente()!=null) {
				SolicitacaoServicoDTO ss = new SolicitacaoServicoDTO();
				ss = (SolicitacaoServicoDTO) solService.findByIdSolicitacaoServico(itemConfiguracaoDTO.getIdIncidente());
				if(ss!=null)
					itemConfiguracaoDTO.setNumeroIncidente(ss.getIdSolicitacaoServico().toString() + " - " + ss.getNomeServico() + " - "+ ss.getSituacao());
			}
			
			if(itemConfiguracaoDTO.getIdMudanca()!=null) {
				RequisicaoMudancaDTO rm = new RequisicaoMudancaDTO();
				rm.setIdRequisicaoMudanca(itemConfiguracaoDTO.getIdMudanca());
				rm = (RequisicaoMudancaDTO) reqMudanca.restore(rm);
				if(rm!=null)
					itemConfiguracaoDTO.setNumeroMudanca(rm.getIdRequisicaoMudanca()+" - "+rm.getTitulo()+" - "+rm.getStatus());
			}
			
			if(itemConfiguracaoDTO.getIdProblema()!=null) {
				ProblemaDTO rm = new ProblemaDTO();
				rm.setIdProblema(itemConfiguracaoDTO.getIdProblema());
				rm = (ProblemaDTO) problemaService.restore(rm);
				if(rm!=null)
					itemConfiguracaoDTO.setNumeroProblema(rm.getIdProblema()+" - "+rm.getTitulo()+" - "+rm.getStatus());
			}
			if(itemConfiguracaoDTO.getIdMidiaSoftware()!=null) {
				MidiaSoftwareDTO rm = new MidiaSoftwareDTO();
				rm.setIdMidiaSoftware(itemConfiguracaoDTO.getIdMidiaSoftware());
				rm = (MidiaSoftwareDTO) midiaSoftwareService.restore(rm);
				if(rm!=null)
					itemConfiguracaoDTO.setNomeMidia(rm.getNome());
			}		
			if(itemConfiguracaoDTO.getIdLiberacao()!=null) {
				RequisicaoLiberacaoDTO rm = new RequisicaoLiberacaoDTO();
				rm.setIdRequisicaoLiberacao(itemConfiguracaoDTO.getIdLiberacao());
				rm = (RequisicaoLiberacaoDTO) liberacaoService.restore(rm);
				if(rm!=null)
					itemConfiguracaoDTO.setTituloLiberacao(rm.getTitulo());
			}
			
			document.executeScript("event()");
			
			form.setValues(itemConfiguracaoDTO);
		}
		
		HTMLElement divPrincipal = document.getElementById("principalInf");
		divPrincipal.setInnerHTML(subDiv.toString());
		
	}

	
//	public void restoreTipoItemConfiguracaoValues(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
//    	if(usrDto == null){
//    		return;
//    	}
//    	
//		this.setItemConfiguracaoBean((IDto) document.getBean());
//		if(this.getItemConfiguracaoBean().getIdTipoItemConfiguracao()==null) {
//			this.setItemConfiguracaoBean(this.getItemConfiguracaoService().restoreByIdItemConfiguracao(this.getItemConfiguracaoBean().getIdItemConfiguracao()));
//		}
//		this.getTipoItemConfiguracaoBean().setId(this.getItemConfiguracaoBean().getIdTipoItemConfiguracao());
//		this.setTipoItemConfiguracaoBean(this.getTipoItemConfiguracaoService().restore(this.getTipoItemConfiguracaoBean()));
//		this.getItemConfiguracaoBean().setNomeTipoItemConfiguracao(this.getTipoItemConfiguracaoBean().getNome());
//
//		document.executeScript("deleteAllRows()");
//		HTMLForm form = document.getForm("form");
//		form.setValues(this.getItemConfiguracaoBean());
//
//		if (this.getTipoItemConfiguracaoBean() != null) {
//			this.getTipoItemConfiguracaoService().restaurarGridCaracteristicas(document,
//					this.getCaracteristicaService().
//					consultarCaracteristicasComValoresItemConfiguracao(this.getTipoItemConfiguracaoBean().getId(), this.getItemConfiguracaoBean().getIdItemConfiguracao()));
//		}
//	}
	
	@Override
	public Class getBeanClass() {
		return ItemConfiguracaoTreeDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RiscoDTO risco = (RiscoDTO) document.getBean();
		risco.setDataInicio(UtilDatas.getDataAtual());
		RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, WebUtil.getUsuarioSistema(request));
		if (risco.getIdRisco() == null || risco.getIdRisco().intValue() == 0) {
			riscoService.create(risco);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			riscoService.update(risco);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_RISCO()");
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RiscoDTO risco = (RiscoDTO) document.getBean();
		RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, WebUtil.getUsuarioSistema(request));

		risco = (RiscoDTO) riscoService.restore(risco);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(risco);

	}
	
	public void atualizaData(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RiscoDTO risco = (RiscoDTO) document.getBean();
		RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, null);
		if (risco.getIdRisco().intValue() > 0) {
			risco.setDataFim(UtilDatas.getDataAtual());

			riscoService.update(risco);

		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		document.executeScript("limpar_LOOKUP_RISCO()");

	}
}
