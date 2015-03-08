package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AuditoriaItemConfigDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoICDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoTreeDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.ConhecimentoICDao;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.HistoricoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.HistoricoValorService;
import br.com.centralit.citcorpore.negocio.ImagemItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.MidiaSoftwareService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes","unchecked"})
public class ItemConfiguracaoTree extends ItemConfiguracao {
	private static final String INTERROGACAO = "?";
	@SuppressWarnings("unused")
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.getElementById("idGrupoResponsavel").getValue();

		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		/*Combo de status*/
		HTMLSelect comboStatus = (HTMLSelect) document.getSelectById("status");
		comboStatus.removeAllOptions();
		for (Enumerados.StatusIC st : Enumerados.StatusIC.values()) {
			comboStatus.addOption(st.getItem().toString(), UtilI18N.internacionaliza(request,st.getChaveInternacionalizacao()));
		}
		
		/**
		 * Combo de tipo responsável
		 * 
		 * @author thyen.chang
		 */
		HTMLSelect comboTipoResponsavel = (HTMLSelect) document.getSelectById("tipoResponsavel");
		comboTipoResponsavel.removeAllOptions();
		comboTipoResponsavel.addOption("U", UtilI18N.internacionaliza(request,"citcorpore.comum.usuario"));
		comboTipoResponsavel.addOption("G", UtilI18N.internacionaliza(request,"controle.grupo"));
		
		/*Combo de criticidade*/
		HTMLSelect comboCriticiidade = (HTMLSelect) document.getSelectById("criticidade");
		comboCriticiidade.removeAllOptions();
		for (Enumerados.CriticidadeIC ct : Enumerados.CriticidadeIC.values()) {
			comboCriticiidade.addOption(ct.getItem().toString(), UtilI18N.internacionaliza(request,ct.getDescricao()));
		}
		
		//Combo Contrato
		carregarComboContrato(document, usrDto);
		
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
			GrupoDTO grupoBean = new GrupoDTO();
			GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			
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
			if(itemConfiguracaoDTO.getIdGrupoResponsavel()!=null){
				grupoBean.setIdGrupo(itemConfiguracaoDTO.getIdGrupoResponsavel());
				grupoBean = (GrupoDTO) grupoService.restore(grupoBean);
				if(grupoBean!=null) {
					itemConfiguracaoDTO.setNomeResponsavel(grupoBean.getNome());
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
				subDiv.append("<h2>"+UtilI18N.internacionaliza(request, "solicitacaoServico.itemConfiguracao")+"</h2>");			
				subDiv.append("<b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+": </b>" + itemPai.getIdentificacao() + "");			
				if(itemPai.getIdGrupoItemConfiguracao() != null && itemPai.getIdGrupoItemConfiguracao() > 0){
					if(grupoPai!=null)
						subDiv.append("<b>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.grupo")+": </b>" + grupoPai.getNomeGrupoItemConfiguracao());
				} else {
					List<ParametroCorporeDTO> listGrupoNovos = parametroService.pesquisarParamentro(
							Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS.id(),
								Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS.getCampoParametroInternacionalizado(request));
					if ((parametroDTO = (ParametroCorporeDTO) listGrupoNovos.get(0)).getValor() != null) {
						subDiv.append("<b>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.grupo")+": </b>" + (parametroDTO = (ParametroCorporeDTO) listGrupoNovos.get(0)).getValor());
					}
				}			
				subDiv.append("</div>");
				
				document.executeScript("$('#titleITem').text('"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itemConfiguracaoRelacionado")+"')");
				document.executeScript("$('#principalInf').css('display','block')");
				document.executeScript("$('#itemPai').css('display','block')");
				
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
				restoreTipoItemConfiguracaoValues(document, request, response);
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
			
			/*Abas*/
			mostraIncidentesRelacionados(document, request, response);
			mostraProblemasRelacionados(document, request, response);
			mostraMudancasRelacionadas(document, request, response);
			mostraHistoricoItens(document, request, response);
			mostraBCRelacionada(document, request, response);
			mostraLiberacoesRelacionadas(document, request, response);
					
			if(itemConfiguracaoDTO.getIdGrupoResponsavel() != null){
				GrupoDTO grupoDTO = grupoService.listGrupoById(itemConfiguracaoDTO.getIdGrupoResponsavel());
				document.getElementById("nomeResponsavel");
			}
			
			document.executeScript("document.getElementById('divInventario').style.display = 'block';");
			document.executeScript("event()");
		
			form.setValues(itemConfiguracaoDTO);
		}
		
		//---
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
		String pathInfo = getRequestedPath(request);
		String strForm = getObjectName(pathInfo);
		PerfilAcessoMenuDTO perfilAcessoMenudto = new PerfilAcessoMenuDTO();
		PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();		
		String url = "/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load";
		Integer idMenu = menuService.buscarIdMenu(url);		
		String acessoGravar = "N";
		String acessoDeletar = "N";
		String acessoPesquisar = "N";
		if (idMenu != null){
			if (usrDto.getIdPerfilAcessoUsuario() != null) {
				
				perfilAcessoMenudto.setIdPerfilAcesso(usrDto.getIdPerfilAcessoUsuario());
				perfilAcessoMenudto.setIdMenu(idMenu);
				
				Collection<PerfilAcessoMenuDTO> listaPerfilAcessoMenu = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
				
				if (listaPerfilAcessoMenu != null) {
					for (PerfilAcessoMenuDTO perfilAcessoMenu : listaPerfilAcessoMenu) {
						
						if (acessoGravar.equals("N") && acessoDeletar.equals("N") && acessoPesquisar.equals("N")){
							
							if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")){
								acessoGravar = "S";
							}
							if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")){
								acessoDeletar = "S";
							}
							if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")){
								acessoPesquisar = "S";
							}								
						}
					}
				}
				
				UsuarioDTO usuario = (UsuarioDTO) usuarioService.restore(usrDto);
				Integer idEmpregado = usuario.getIdEmpregado();
				
				Collection<GrupoEmpregadoDTO> listaDeGrupoEmpregado = grupoEmpregadoService.findByIdEmpregado(idEmpregado);
				
				if (listaDeGrupoEmpregado != null) {
					for (GrupoEmpregadoDTO grupoEmpregado : listaDeGrupoEmpregado) {
						
						perfilAcessoGrupo.setIdGrupo(grupoEmpregado.getIdGrupo());
						
						perfilAcessoGrupo = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupo);
						
						perfilAcessoMenudto.setIdPerfilAcesso(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
						perfilAcessoMenudto.setIdMenu(idMenu);
						
						Collection<PerfilAcessoMenuDTO> listaAcessoMenusGrupo = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
						
						if (listaAcessoMenusGrupo != null) {
							for (PerfilAcessoMenuDTO perfilAcessoMenu : listaAcessoMenusGrupo) {
								if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")){
									acessoGravar = "S";
								}
								if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")){
									acessoDeletar = "S";
								}
								if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")){
									acessoPesquisar = "S";
								}								
							}
						}
					}
				}
			}
		}
		if (acessoGravar.equalsIgnoreCase("N")){
			document.executeScript("document.getElementById('btnGravar').style.display='none'");
			document.executeScript("document.getElementById('btnGravarBaseLine').style.display='none'");
		}else{
			document.executeScript("acessoGravar = 'S'");
		}
		if (acessoPesquisar.equalsIgnoreCase("N")){
			document.executeScript("document.getElementById('principalInf').style.display='none'");
			document.executeScript("document.getElementById('tabs').style.display='none'");
		}
		
		String valida = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA, "S");
		if(valida.trim().equals("S")){
			document.executeScript("$('#Mudancarequired').addClass('campoObrigatorio')");
		}
		
		HTMLElement divPrincipal = document.getElementById("principalInf");
		divPrincipal.setInnerHTML(subDiv.toString());
		/**
		 * Depois de carregado a tela, oculta o load
		 * @author flavio.santana
		 * 25/10/2013 12:00
		 */
		document.executeScript("parent.JANELA_AGUARDE_MENU.hide();");
		
		if(itemConfiguracaoDTO.getIdResponsavel() == null)
			document.executeScript("changeSelectedItem(\"G\")");
		else
			document.executeScript("changeSelectedItem(\"U\")");
		
	}
	private String getRequestedPath(HttpServletRequest request) {
		String path = request.getRequestURI() + request.getQueryString();
		path = path.substring(request.getContextPath().length());
		int index = path.indexOf(INTERROGACAO);
		if (index != -1)
			path = path.substring(0, index);
		return path;
	}
	public String getObjectName(String path) {
		String strResult = "";
		boolean b = false;
		for (int i = path.length() - 1; i >= 0; i--) {
			if (b) {
				if (path.charAt(i) == '/') {
					return strResult;
				} else {
					strResult = path.charAt(i) + strResult;
				}
			} else {
				if (path.charAt(i) == '.') {
					b = true;
				}
			}
		}
		return strResult;
	}	
	public void mostraHistoricoItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HistoricoItemConfiguracaoService itemService = (HistoricoItemConfiguracaoService) ServiceLocator.getInstance().getService(HistoricoItemConfiguracaoService.class, null);
		HistoricoValorService valorItemService = (HistoricoValorService) ServiceLocator.getInstance().getService(HistoricoValorService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		
		HTMLElement divPrincipal = document.getElementById("contentBaseline");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblBaselines' class='table table-bordered table-striped'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.baleline")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.versaoHistorico")+"</th>	" + 
				"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.familia")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.localidade")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.dataExpiracao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "colaborador.colaborador")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.versao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.tipoItem")+"</th>	" + 
				"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.origem")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historicoRestaurar")+"</th>	" +
				"			</tr>" +
				"		</thead>");
		List<HistoricoItemConfiguracaoDTO> list =  itemService.listHistoricoItemByIditemconfiguracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao().intValue());
		if(list!=null) {
			int count = 0;
			boolean flag = false;		
			document.executeScript("document.formBaseline.idItemConfiguracao.value = " + itemConfiguracaoTreeDTO.getIdItemConfiguracao());			
			document.executeScript("countHistorico = 0");
			for (HistoricoItemConfiguracaoDTO historicoItemConfiguracaoDTO : list) {
				//verifica se tem origem
				if(historicoItemConfiguracaoDTO.getOrigem() != null && !historicoItemConfiguracaoDTO.getOrigem() .equalsIgnoreCase("")){
					if(historicoItemConfiguracaoDTO.getOrigem() .equalsIgnoreCase("L"))
						historicoItemConfiguracaoDTO.setOrigem(UtilI18N.internacionaliza(request, "liberacao") +" N " + historicoItemConfiguracaoDTO.getIdOrigemModificacao());
					else if(historicoItemConfiguracaoDTO.getOrigem() .equalsIgnoreCase("M"))
						historicoItemConfiguracaoDTO.setOrigem(UtilI18N.internacionaliza(request, "requisicaMudanca.mudanca"));
					else if(historicoItemConfiguracaoDTO.getOrigem() .equalsIgnoreCase("P"))
						historicoItemConfiguracaoDTO.setOrigem(UtilI18N.internacionaliza(request, "problema.problema"));
				}
				
				List<HistoricoValorDTO> listValoresDtos = valorItemService.listHistoricoValorByIdHistoricoIc(historicoItemConfiguracaoDTO.getIdHistoricoIC());
				flag = (historicoItemConfiguracaoDTO.getBaseLine()!= null && historicoItemConfiguracaoDTO.getBaseLine().equals("SIM")) ? true: false;
				String checked = "";
				count++;
				document.executeScript("seqBaseline = " + count);		
				if(flag) checked = "checked='checked' disabled='disabled'";
				subDiv.append(
				"<tbody>"+
				"	<tr>"+
				"		<td width='5%'>" + "<input type='checkbox' "+ checked + " id='idHistoricoIC" + count + "'" +
						" name='idHistoricoIC" + count + "' value='0"+historicoItemConfiguracaoDTO.getIdHistoricoIC().toString()+ "'/></td>" +
				"		<td>" + historicoItemConfiguracaoDTO.getHistoricoVersao().floatValue() + " - " + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, historicoItemConfiguracaoDTO.getDataHoraAlteracao(), WebUtil.getLanguage(request))+ "</td>" +
				"		<td width='15%'>" + (historicoItemConfiguracaoDTO.getIdentificacao() == null ? "" : historicoItemConfiguracaoDTO.getIdentificacao()) + "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getFamilia() == null ? "" : historicoItemConfiguracaoDTO.getFamilia())+ "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getLocalidade() == null ? "" : historicoItemConfiguracaoDTO.getLocalidade()) + "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getDataExpiracao() == null ? "" :  UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, historicoItemConfiguracaoDTO.getDataExpiracao(), WebUtil.getLanguage(request)))+ "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getNomeProprietario() == null ? "" : historicoItemConfiguracaoDTO.getNomeProprietario())+ "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getVersao() == null ? "" : historicoItemConfiguracaoDTO.getVersao()) + "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getNomeTipoItemConfiguracao() == null ? "" : historicoItemConfiguracaoDTO.getNomeTipoItemConfiguracao()) + "</td>" +
				"		<td>" + (historicoItemConfiguracaoDTO.getOrigem() == null ? "" : historicoItemConfiguracaoDTO.getOrigem()) + "</td>" +
				"		<td>" +
				"			<a href='javascript:;' class='even' id='even-" + historicoItemConfiguracaoDTO.getIdHistoricoIC() + "'>" +
						"		<img src='../../template_new/images/icons/small/grey/documents.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historico")+"' " +
								"title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historico")+"' /></a>");
				if(flag) {
					subDiv.append(
					"		<a href='javascript:;' onclick='restaurar(\"" + historicoItemConfiguracaoDTO.getIdHistoricoIC() + "\")'>" +
					"			<img src='../../template_new/images/icons/small/grey/refresh_3.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restaurar")+"' " +
							"title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restaurar")+"' /></a>");
				}
				
				subDiv.append("		</td>" +
				"	</tr>" +
				"	<tr class='sel' id='sel-" + historicoItemConfiguracaoDTO.getIdHistoricoIC() + "'>" + 
				"		<td colspan='9'>" +
				"			<div class='sel-s'>" +
				"				<table class='table table-bordered table-striped' width='100%'><thead><tr><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.caracteristica")+
				"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.valor")+"</th></tr></thead><tbody>");
								if(listValoresDtos!=null) { for (HistoricoValorDTO historicoValorDTO : listValoresDtos) {
									subDiv.append("<tr><td>"+historicoValorDTO.getNomeCaracteristica()+"</td><td>"+(historicoValorDTO.getValorStr() == null ? "" : historicoValorDTO.getValorStr())+"</td></tr>");
								}}
				subDiv.append("</tbody></table>" +
						"	</div>" +
				"		</td>" +
				"	</tr>" +
				"</tbody>");
			}
		}
		subDiv.append(
			"	</table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
	}
	
	public void mostraIncidentesRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		
		HTMLElement divPrincipal = document.getElementById("relacionarIncidentes");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tbIncidentes' class='table table-bordered table-striped'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th>"+UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.servico")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.nome")+"</th>	" +
				//"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.dataHoraInicio")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.situacao")+"</th>	" +				
				"				<th>"+UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraabertura")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "solicitacaoServico.datahoralimite")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "solicitacaoServico.descricao")+"</th>	" +
				"				<th></th>	" +
				"			</tr>" +
				"		</thead><tbody>");
		List<SolicitacaoServicoDTO> list =  solicitacaoServicoService.listSolicitacaoServicoByItemConfiguracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao());
		if(list!=null) {
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : list) {
				
				List<ItemConfiguracaoDTO> listItens = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listItemConfiguracaoByIdIncidente(solicitacaoServicoDTO.getIdSolicitacaoServico());
				subDiv.append(
				"	<tr>"+
				"		<td>" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getNomeServico() == null ? "" : solicitacaoServicoDTO.getNomeServico()) + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getNomeSolicitante() == null ? "" : solicitacaoServicoDTO.getNomeSolicitante()) + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getDataHoraInicio() == null ? "" : UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solicitacaoServicoDTO.getDataHoraInicio(), WebUtil.getLanguage(request))) + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("EmAndamento") ? UtilI18N.internacionaliza(request,"solicitacaoServico.situacao.EmAndamento") : 
						((solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Suspensa"))?UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa"):
						((solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Resolvida"))?UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida"):
						((solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada"))?UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada"):
						((solicitacaoServicoDTO.getSituacao() == null? "" : solicitacaoServicoDTO.getSituacao())))))) + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getDataHoraSolicitacaoStr() == null ? "" : solicitacaoServicoDTO.getDataHoraSolicitacaoStr()) + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getDataHoraLimiteStr() == null ? "" : solicitacaoServicoDTO.obterDataHoraLimiteStrWithLanguage(WebUtil.getLanguage(request))) + "</td>" +
				"		<td>" + (solicitacaoServicoDTO.getDescricao() == null ? "" : solicitacaoServicoDTO.getDescricao()) + "</td>" +
				"		<td>			<a href='javascript:;' class='even' id='evenP-" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "'>" +
				"		<img src='../../template_new/images/icons/small/grey/documents.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+"'" +
						" title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+"' /></a></td>" +
				"	</tr>" +
				"	<tr class='sel' id='selP-" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "'>" + 
				"		<td colspan='11'>" +
				"			<div class='sel-s'>" +
				"				<table class='table table-bordered table-striped' width='100%'><thead><tr><th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+"</th>" +
						"<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.familia")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.classe")+
						"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.localidade")+"</th></tr></thead><tbody>");
								if(listItens!=null) { for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItens) {
									subDiv.append("<tr><td>"+itemConfiguracaoDTO.getIdentificacao()+"</td><td>"+itemConfiguracaoDTO.getFamilia()+"</td>" +
											"<td>"+itemConfiguracaoDTO.getClasse()+"</td><td>"+itemConfiguracaoDTO.getLocalidade()+"</td></tr>");
								} }
				subDiv.append("</tbody></table>" +
						"	</div>" +
				"		</td>" +
				"	</tr>" +
				"</tbody>");
			}
		}
		subDiv.append(
			"	</tbody></table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
	}
	
	public void mostraProblemasRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		
		HTMLElement divPrincipal = document.getElementById("relacionarProblemas");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
			"<div class='formBody'> " +
			"	<table id='tblProblemas' class='table table-bordered table-striped'> 	" +
			"		<thead>" +
			"			<tr>" +
			"				<th>"+UtilI18N.internacionaliza(request, "problema.numero_solicitacao")+"</th>	" +
			"				<th>"+UtilI18N.internacionaliza(request, "problema.titulo")+"</th>	" +
			"				<th>"+UtilI18N.internacionaliza(request, "problema.proprietario")+"</th>	" +
			"				<th>"+UtilI18N.internacionaliza(request, "colaborador.solicitante")+"</th>	" +
			"				<th>"+UtilI18N.internacionaliza(request, "problema.impacto")+"</th>	" +				
			"				<th>"+UtilI18N.internacionaliza(request, "problema.urgencia")+"</th>	" +
			"				<th>"+UtilI18N.internacionaliza(request, "problema.severidade")+"</th>	" +
			"				<th width='40%'>"+UtilI18N.internacionaliza(request, "problema.descricao")+"</th>	" +
			"				<th></th>	" +
			"				<th></th>	" +
			"			</tr>" +
			"		</thead>");
		List<ProblemaDTO> list =  problemaService.listProblemaByIdItemConfiguracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao());
		if(list!=null){
			for (ProblemaDTO problemaDTO : list) {
				
				List<ItemConfiguracaoDTO> listItens = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listItemConfiguracaoByIdProblema(problemaDTO.getIdProblema());
				
				subDiv.append(
				"<tbody>"+
				"	<tr>"+
				"		<td>" + problemaDTO.getIdProblema() + "</td>" +
				"		<td>" + (problemaDTO.getTitulo() == null ? "" : problemaDTO.getTitulo()) + "</td>" +
				"		<td>" + (problemaDTO.getNomeProprietario() == null ? "" : problemaDTO.getNomeProprietario()) + "</td>" +
				"		<td>" + (problemaDTO.getSolicitante()== null ? "" : problemaDTO.getSolicitante()) + "</td>" +
				"		<td>" + (problemaDTO.getImpacto() == null ? "" : 
						((problemaDTO.getImpacto().equalsIgnoreCase("B")||problemaDTO.getImpacto().equalsIgnoreCase("Baixa"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						((problemaDTO.getImpacto().equalsIgnoreCase("A")||problemaDTO.getImpacto().equalsIgnoreCase("Alta"))?UtilI18N.internacionaliza(request, "citcorpore.comum.alta"):
						((problemaDTO.getImpacto().equalsIgnoreCase("M")||problemaDTO.getImpacto().equalsIgnoreCase("Média"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						problemaDTO.getImpacto())))) + "</td>" +
				"		<td>" + (problemaDTO.getUrgencia() == null ? "" : 
						((problemaDTO.getUrgencia().equalsIgnoreCase("B")||problemaDTO.getUrgencia().equalsIgnoreCase("Baixa"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						((problemaDTO.getUrgencia().equalsIgnoreCase("A")||problemaDTO.getUrgencia().equalsIgnoreCase("Alta"))?UtilI18N.internacionaliza(request, "citcorpore.comum.alta"):
						((problemaDTO.getUrgencia().equalsIgnoreCase("M")||problemaDTO.getUrgencia().equalsIgnoreCase("Média"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						problemaDTO.getUrgencia())))) + "</td>" +
				"		<td>" + (problemaDTO.getSeveridade() == null ? "" : 
						((problemaDTO.getSeveridade().equalsIgnoreCase("B")||problemaDTO.getSeveridade().equalsIgnoreCase("Baixa"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						((problemaDTO.getSeveridade().equalsIgnoreCase("A")||problemaDTO.getSeveridade().equalsIgnoreCase("Alta"))?UtilI18N.internacionaliza(request, "citcorpore.comum.alta"):
						((problemaDTO.getSeveridade().equalsIgnoreCase("M")||problemaDTO.getSeveridade().equalsIgnoreCase("Média"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						problemaDTO.getSeveridade())))) + "</td>" +
				"		<td>" + (problemaDTO.getDescricao() == null ? "" : problemaDTO.getDescricao()) + "</td>" +
				"		<td> <img src='../../imagens/viewCadastro.png' border='0' onclick='CarregarProblema(" + problemaDTO.getIdProblema() + ")' style='cursor:pointer'/></td>" +
				"		<td>			<a href='javascript:;' class='even' id='evenP-" + problemaDTO.getIdProblema() + "'>" +
				"		<img src='../../template_new/images/icons/small/grey/documents.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+"'" +
						" title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+"' /></a></td>" +
				"	</tr>" +
				"	<tr class='sel' id='selP-" + problemaDTO.getIdProblema() + "'>" + 
				"		<td colspan='11'>" +
				"			<div class='sel-s'>" +
				"				<table class='table table-bordered table-striped' width='100%'><thead><tr><th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.familia")+
				"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.classe")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.localidade")+"</th></tr></thead><tbody>");
								if(listItens!=null) { for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItens) {
									subDiv.append("<tr><td>"+ (itemConfiguracaoDTO.getIdentificacao() == null? "" : itemConfiguracaoDTO.getIdentificacao())+"</td><td>"+(itemConfiguracaoDTO.getFamilia() == null ? "" : itemConfiguracaoDTO.getFamilia())+"</td>" +
											"<td>"+(itemConfiguracaoDTO.getClasse() == null ? "" : itemConfiguracaoDTO.getClasse())+"</td><td>"+(itemConfiguracaoDTO.getLocalidade() == null ? "" : itemConfiguracaoDTO.getLocalidade())+"</td></tr>");
								} }
				subDiv.append("</tbody></table>" +
						"	</div>" +
				"		</td>" +
				"	</tr>" +
				"</tbody>");
			}
		}
		subDiv.append(
			"	</table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
	}
	
	public void mostraBCRelacionada(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ConhecimentoICDao conhecimentoICDao = new ConhecimentoICDao();
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		HTMLElement divPrincipal = document.getElementById("relacionarBC");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblIC' class='table table-bordered table-striped'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th>ID</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "baseConhecimento.titulo")+"</th>	" +
				"			</tr>" +
				"		</thead>");
		
		Collection col = conhecimentoICDao.findByidItemConfiguracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao());
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				ConhecimentoICDTO conhecimentoICDTO = (ConhecimentoICDTO)it.next();
				BaseConhecimentoDTO bcDto = new BaseConhecimentoDTO();
				bcDto.setIdBaseConhecimento(conhecimentoICDTO.getIdBaseConhecimento());
				bcDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(bcDto);
				if (bcDto != null){
					bcDto.getIdBaseConhecimento();
					subDiv.append("<tbody><tr><td>"+ bcDto.getIdBaseConhecimento() + "</td><td>" + UtilStrings.retiraAspasApostrofe(bcDto.getTitulo()) + "</td></tbody>");			
				}
			}
		}
		subDiv.append(
				"	</table>" +
				"</div>");
			divPrincipal.setInnerHTML(subDiv.toString());		
	}
	
	public void mostraMudancasRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		
		HTMLElement divPrincipal = document.getElementById("relacionarMudancas");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblMudancas' class='table table-bordered table-striped'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaMudanca.numero_solicitacao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaMudanca.titulo")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.proprietario")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.solicitante")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.impacto")+"</th>	" +				
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.importancia")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.status")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.tipo")+"</th>	" +
				"				<th width='40%'>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.descricao")+"</th>	" +
				"				<th></th>	" +
				"			</tr>" +
				"		</thead>");
		List<RequisicaoMudancaDTO> list = requisicaoMudancaService.listMudancaByIdItemConfiguracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao());
		if(list!=null){
			for (RequisicaoMudancaDTO requisicaoMudancaDTO : list) {
				
				List<ItemConfiguracaoDTO> listItens = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listItemConfiguracaoByIdMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
				
				subDiv.append(
				"<tbody>"+
				"	<tr>"+
				"		<td>" + requisicaoMudancaDTO.getIdRequisicaoMudanca() + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getTitulo() == null ? "" : requisicaoMudancaDTO.getTitulo()) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getNomeProprietario() == null ? "" : requisicaoMudancaDTO.getNomeProprietario()) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getNomeSolicitante() == null ? "" : requisicaoMudancaDTO.getNomeSolicitante()) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getNivelImpacto() == null ? "" : 
						((requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase("B")||requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase("Baixa"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						((requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase("A")||requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase("Alta"))?UtilI18N.internacionaliza(request, "citcorpore.comum.alta"):
						((requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase("M")||requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase("Média"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
						requisicaoMudancaDTO.getNivelImpacto())))) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getNivelImportanciaNegocio() == null ? "" : requisicaoMudancaDTO.getNivelImportanciaNegocio()) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getStatus().equalsIgnoreCase("Registrada") ? UtilI18N.internacionaliza(request,"citcorpore.comum.registrada") : 
						((requisicaoMudancaDTO.getStatus().equalsIgnoreCase("Aprovada"))?UtilI18N.internacionaliza(request, "citcorpore.comum.aprovada"):
						((requisicaoMudancaDTO.getStatus().equalsIgnoreCase("Cancelada"))?UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada"):
						((requisicaoMudancaDTO.getStatus().equalsIgnoreCase("Concluida"))?UtilI18N.internacionaliza(request, "citcorpore.comum.concluida"):
						((requisicaoMudancaDTO.getStatus() == null? "" : requisicaoMudancaDTO.getStatus())))))) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getTipo() == null ? "" : requisicaoMudancaDTO.getTipo()) + "</td>" +
				"		<td>" + (requisicaoMudancaDTO.getDescricao() == null ? "" : requisicaoMudancaDTO.getDescricao()) + "</td>" +
				"		<td>			<a href='javascript:;' class='even' id='evenM-" + requisicaoMudancaDTO.getIdRequisicaoMudanca() + "'>" +
						"		<img src='../../template_new/images/icons/small/grey/documents.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+
						"' title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+"' /></a></td>" +
				"	</tr>" +
				"	<tr class='sel' id='selM-" + requisicaoMudancaDTO.getIdRequisicaoMudanca() + "'>" + 
				"		<td colspan='11'>" +
				"			<div class='sel-s'>" +
				"				<table class='table table-bordered table-striped' width='100%'><thead><tr><th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.familia")+
				"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.classe")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.localidade")+"</th></tr></thead><tbody>");
								if(listItens!=null) { for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItens) {
									subDiv.append("<tr><td>"+itemConfiguracaoDTO.getIdentificacao()+"</td><td>"+itemConfiguracaoDTO.getFamilia()+"</td>" +
											"<td>"+itemConfiguracaoDTO.getClasse()+"</td><td>"+itemConfiguracaoDTO.getLocalidade()+"</td></tr>");
								} }
				subDiv.append("</tbody></table>" +
						"	</div>" +
				"		</td>" +
				"	</tr>" +
				"</tbody>");
			}
		}
		subDiv.append(
			"	</table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
	}
	public void mostraLiberacoesRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoTreeDTO itemConfiguracaoTreeDTO = (ItemConfiguracaoTreeDTO) document.getBean();
		
		HTMLElement divPrincipal = document.getElementById("relacionarLiberacoes");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblLiberacoes' class='table table-bordered table-striped'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaMudanca.numero_solicitacao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "liberacao.titulo")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.proprietario")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.solicitante")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.impacto")+"</th>	" +				
				"				<th>"+UtilI18N.internacionaliza(request, "solicitacaoServico.urgencia")+"</th>	" +
				"				<th width='40%'>"+UtilI18N.internacionaliza(request, "requisicaoMudanca.descricao")+"</th>	" +
				"				<th></th>	" +
				"			</tr>" +
				"		</thead>");
		List<RequisicaoLiberacaoDTO> list = requisicaoLiberacaoService.listLiberacaoByItemConfiugracao(itemConfiguracaoTreeDTO.getIdItemConfiguracao());
		if(list!=null){
			for (RequisicaoLiberacaoDTO req : list) {
				
				List<ItemConfiguracaoDTO> listItens = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listItemConfiguracaoByIdLiberacao(req.getIdLiberacao());
				
				subDiv.append(
						"<tbody>"+
								"	<tr>"+
								"		<td>" + req.getIdLiberacao() + "</td>" +
								"		<td>" + (req.getTitulo() == null ? "" : req.getTitulo()) + "</td>" +
								"		<td>" + (req.getNomeProprietario() == null ? "" : req.getNomeProprietario()) + "</td>" +
								"		<td>" + (req.getNomeSolicitante() == null ? "" : req.getNomeSolicitante()) + "</td>" +
								"		<td>" + (req.getNivelImpacto() == null ? "" : 
										((req.getNivelImpacto().equalsIgnoreCase("B")||req.getNivelImpacto().equalsIgnoreCase("Baixa"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
										((req.getNivelImpacto().equalsIgnoreCase("A")||req.getNivelImpacto().equalsIgnoreCase("Alta"))?UtilI18N.internacionaliza(request, "citcorpore.comum.alta"):
										((req.getNivelImpacto().equalsIgnoreCase("M")||req.getNivelImpacto().equalsIgnoreCase("Média"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
										req.getNivelImpacto())))) + "</td>" +
								"		<td>" + (req.getNivelUrgencia() == null ? "" : 
										((req.getNivelUrgencia().equalsIgnoreCase("B")||req.getNivelUrgencia().equalsIgnoreCase("Baixa"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
										((req.getNivelUrgencia().equalsIgnoreCase("A")||req.getNivelUrgencia().equalsIgnoreCase("Alta"))?UtilI18N.internacionaliza(request, "citcorpore.comum.alta"):
										((req.getNivelUrgencia().equalsIgnoreCase("M")||req.getNivelUrgencia().equalsIgnoreCase("Média"))?UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"):
										req.getNivelUrgencia())))) + "</td>" +
								"		<td>" + (req.getDescricao() == null ? "" : req.getDescricao()) + "</td>" +
								"		<td>			<a href='javascript:;' class='even' id='evenM-" + req.getIdRequisicaoLiberacao() + "'>" +
								"		<img src='../../template_new/images/icons/small/grey/documents.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+
								"' title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.itensConfiguracaoRelacionados")+"' /></a></td>" +
								"	</tr>" +
								"	<tr class='sel' id='selM-" + req.getIdRequisicaoLiberacao() + "'>" + 
								"		<td colspan='11'>" +
								"			<div class='sel-s'>" +
								"				<table class='table table-bordered table-striped' width='100%'><thead><tr><th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.familia")+
								"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.classe")+"</th><th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.localidade")+"</th></tr></thead><tbody>");
				if(listItens!=null) { for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItens) {
					subDiv.append("<tr><td>"+itemConfiguracaoDTO.getIdentificacao()+"</td><td>"+itemConfiguracaoDTO.getFamilia()+"</td>" +
							"<td>"+itemConfiguracaoDTO.getClasse()+"</td><td>"+itemConfiguracaoDTO.getLocalidade()+"</td></tr>");
				} }
				subDiv.append("</tbody></table>" +
						"	</div>" +
						"		</td>" +
						"	</tr>" +
						"</tbody>");
			}
		}
		subDiv.append(
				"	</table>" +
				"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		this.setItemConfiguracaoBean((IDto) document.getBean());
		/*Verifica de verificação de mudanca item configuracao Ativo*/
		String valida = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA, "S");
		if(valida.trim().equals("S"))
		{
			/**
			 * Checa se já existe algum item de configuracao com esse nome
			 * @author thyen.chang
			 * @created 09/12/14
			 */
			ItemConfiguracaoTreeDTO icDto = (ItemConfiguracaoTreeDTO) document.getBean();
			ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			if(!itemConfiguracaoService.VerificaSeCadastrado(icDto)){
				/*Faz a validação para alteracao de um item de confiiguração*/
				if(this.getItemConfiguracaoBean().getIdGrupoItemConfiguracao() == null || this.getItemConfiguracaoBean().getIdMudanca() != null){				
					if(super.salvar(document, request, response))			
					
					//document.executeScript("parent.document.formTree.fireEvent('load');");
					load(document, request, response);
				}else {
					document.alert(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.validaRequiredMudanca"));
					document.executeScript("parent.JANELA_AGUARDE_MENU.hide()");
				}
			} else {
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.itemExiste"));
				//Replicado conforme alteração acima.
				load(document, request, response);
				
			}
		}else {
				
			try{
				ItemConfiguracaoTreeDTO icDto = (ItemConfiguracaoTreeDTO) document.getBean();
				ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
				if(!itemConfiguracaoService.VerificaSeCadastrado(icDto))
				{
					if(super.salvar(document, request, response))
						/**
						 * Oculta o janela aguarde
						 * @author flavio.santana
						 * 25/10/2013
						 */
						//document.executeScript("parent.document.formTree.fireEvent('load');JANELA_AGUARDE_MENU.hide();");
						load(document, request, response);
				}
				else {
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.itemExiste"));
					//Replicado conforme alteração acima.
					load(document, request, response);
					
				}
					
				
			}
			 catch (Exception e) {
		            e.printStackTrace();
			 }
				
				
								
			
			
		}
	}
	
	public void verificaHistoricoAlteracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}   			
    	
    	ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO) document.getBean();
    	
    	if(itemConfiguracaoDTO.getIdItemConfiguracao() == null || itemConfiguracaoDTO.getIdItemConfiguracao() == 0){
    		document.executeScript("JANELA_AGUARDE_MENU.hide();");
    		document.alert(UtilI18N.internacionaliza(request, "itemConfiguracao.opcao"));
    		return;
    	}
    	ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
    	List<AuditoriaItemConfigDTO> listItemConfiguracaoRetornoAlteracaoDTO = (List<AuditoriaItemConfigDTO>) itemConfiguracaoService.historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(itemConfiguracaoDTO);
        	
    	StringBuilder tbl = new StringBuilder();
    	
    	tbl.append("" +
				"<div class='formBody'> " +
				"	<table id='tblHistoricoAlteracao' class='table table-bordered table-striped'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th width:'20%>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+"</th>	" +
				"				<th width:'20%>"+ UtilI18N.internacionaliza(request, "itemConfiguracaoTree.tipoItemConfiguracao")+"</th>	" +
				"				<th width:'20%'>"+ UtilI18N.internacionaliza(request, "eventoItemConfiguracao.usuario")+"</th>	" +
				"				<th width:'20%'>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.dataAlteracao")+"</th>	" +
				"				<th width:'20%'>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.tipoModificacao")+"</th>	" +
				"			</tr>" );
    	tbl.append("</thead>");
    	

    	if(listItemConfiguracaoRetornoAlteracaoDTO != null && listItemConfiguracaoRetornoAlteracaoDTO.size() > 0){
    		for (AuditoriaItemConfigDTO auditoriaItemConfigDTO : listItemConfiguracaoRetornoAlteracaoDTO) {
    			tbl.append("	<tr>" );
    			tbl.append("			<th width:'20%>"+auditoriaItemConfigDTO.getIdentificacao() + "</th>" +
    					"				<th width:'20%>"+auditoriaItemConfigDTO.getTipoItemConfiguracao() + "</th>");
    			
    			if(auditoriaItemConfigDTO.getLogin() != null){
    				tbl.append("			<th width:'20%>"+auditoriaItemConfigDTO.getLogin() + "</th>");
    			}else{
    				tbl.append("			<th width:'20%> </th>");
    			}
    					
				tbl.append("			<th width:'20%>"+UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, auditoriaItemConfigDTO.getDataHoraAlteracao(), WebUtil.getLanguage(request)) + "</th>" +
    					"				<th width:'20%>"+auditoriaItemConfigDTO.getTipoAlteracao() + "</th>" );
    			tbl.append("	</tr>" );
			}

    	}else{
    		tbl.append("<tr><th colspan='5'>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.semInformacao")+"</th><tr>" );
    	}
    	
    	
    	tbl.append("</table>");

    	document.getElementById("historicoAlteracaoItemConfiguracao").setInnerHTML(tbl.toString());
    	document.executeScript("JANELA_AGUARDE_MENU.hide();");

	}
	
	public void verificaImpactos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
    	HashMap map = new HashMap();
    	HashMap map2 = new HashMap();
    	ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO) document.getBean();
    	ImagemItemConfiguracaoService imagemItemConfiguracaoService = (ImagemItemConfiguracaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoService.class, null);
    	Collection colHierarq = imagemItemConfiguracaoService.findItensRelacionadosHierarquia(itemConfiguracaoDTO.getIdItemConfiguracao());
    	String strTables = "<h2>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.icsImpactados")+"</h2>";
    	strTables += "<table>";
    	if (colHierarq != null && colHierarq.size() > 0){
    		for (Iterator it = colHierarq.iterator(); it.hasNext();){
    			ItemConfiguracaoDTO itemConfiguracaoAux = (ItemConfiguracaoDTO)it.next();
    			ItemConfiguracaoDTO itemConfiguracaoAux2 = (ItemConfiguracaoDTO) map.get("" + itemConfiguracaoAux.getIdItemConfiguracao());
    			if (itemConfiguracaoAux2 != null){
    				continue;
    			}
    			map.put("" + itemConfiguracaoAux.getIdItemConfiguracao(), itemConfiguracaoAux);
    			strTables += "<tr>";
					strTables += "<td>";
					if (itemConfiguracaoAux.getTipoVinculo().equalsIgnoreCase("FILHO")){
						strTables += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/gerenciaConfiguracaoTree/images/item_relation.png' border='0'/>";						
					}else{
						strTables += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/gerenciaConfiguracaoTree/images/item_menu_relation.png' border='0'/>";
					}
					strTables += "</td>";
					strTables += "<td>&nbsp;";
					strTables += "</td>";
    				strTables += "<td>";
    					strTables += "" + itemConfiguracaoAux.getIdentificacao();
    				strTables += "</td>";
    			strTables += "</tr>";
    		}
    	}else{
			strTables += "<tr>";
				strTables += "<td>";  
					strTables += UtilI18N.internacionaliza(request, "itemConfiguracaoTree.semImpacto");    
				strTables += "</td>";
			strTables += "</tr>";				
    	}
    	strTables += "</table>";
    	
    	strTables += "<h2>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.servicosImpactados")+"</h2>";
    	strTables += "<table>";
    	colHierarq = imagemItemConfiguracaoService.findServicosRelacionadosHierarquia(itemConfiguracaoDTO.getIdItemConfiguracao());
    	if (colHierarq != null && colHierarq.size() > 0){
    		for (Iterator it = colHierarq.iterator(); it.hasNext();){
    			ServicoDTO servicoDTO = (ServicoDTO)it.next();
    			ServicoDTO servicoAux2 = (ServicoDTO) map2.get("" + servicoDTO.getIdServico());
    			if (servicoAux2 != null){
    				continue;
    			}
    			map2.put("" + servicoDTO.getIdServico(), servicoDTO);
    			strTables += "<tr>";
					strTables += "<td>";
						strTables += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/gerenciaConfiguracaoTree/images/item_relation.png' border='0'/>";						
					strTables += "</td>";
					strTables += "<td>&nbsp;";
					strTables += "</td>";
    				strTables += "<td>";
    					strTables += "" + servicoDTO.getNomeServico();
    				strTables += "</td>";
    			strTables += "</tr>";
    		}
    	}else{
			strTables += "<tr>";
				strTables += "<td>";  
				strTables += UtilI18N.internacionaliza(request, "itemConfiguracaoTree.semImpacto");    
				strTables += "</td>";
			strTables += "</tr>";	
    	}
    	strTables += "</table>";
    	
    	document.getElementById("divImpactos").setInnerHTML(strTables);
    	document.executeScript("JANELA_AGUARDE_MENU.hide();");
    	/*document.executeScript("$('#POPUP_IMPACTO').dialog('open')");*/
	}

	@Override
	public Class getBeanClass() {
		return ItemConfiguracaoTreeDTO.class;
	}
	
	/**
	 * Carrega Combo de Contratos de acordo com PARÂMETRO de Vinculo de Colaboradores.
	 * 
	 * @param document
	 * @param usuario
	 * @param solicitacaoServicoDto
	 * @param contratoService
	 * @throws Exception
	 * @throws LogicException
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarComboContrato(DocumentHTML document, UsuarioDTO usuario) throws Exception {

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}

		((HTMLSelect) document.getSelectById("idContrato")).removeAllOptions();
		// HÁ NECESSIDADE DE CARREGAR TODOS OS CONTRATOS (de acordo com o Usuário Logado)
		Collection<ContratoDTO> listContratoAtivo = null;

		if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {

			// PARÂMETRO DE VINCULO ATIVO
			listContratoAtivo = contratoService.findAtivosByIdEmpregado(usuario.getIdEmpregado());

		} else {

			// PARÂMETRO DE VINCULO INATIVO
			listContratoAtivo = contratoService.listAtivos();

		}

		if (listContratoAtivo != null && !listContratoAtivo.isEmpty()) {

			for (ContratoDTO contratoDto : listContratoAtivo) {
				contratoDto.setNome(this.tratarNomeContrato(contratoDto));
			}

			if (listContratoAtivo.size() > 1) {
				((HTMLSelect) document.getSelectById("idContrato")).addOption("", "Selecione");
				((HTMLSelect) document.getSelectById("idContrato")).addOptions(listContratoAtivo, "idContrato", "nome", null);
			} else {
				ContratoDTO contratoDto = ((List<ContratoDTO>) listContratoAtivo).get(0);
				((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());
			}
		}

	}
	
	/**
	 * Concatena ao Nome do Contrato o Número do Contrato + Data do Contrato + Nome do Cliente + Nome do Fornecedor.
	 * 
	 * @param contratoDto
	 * @return String - Nome do Contrato tratado.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private String tratarNomeContrato(ContratoDTO contratoDto) throws Exception {

		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		String nomeCliente = "";
		String nomeFornecedor = "";

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
			nomeFornecedor = fornecedorDto.getRazaoSocial();
		}

		String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.dateToSTR(contratoDto.getDataContrato()) + " (" + nomeCliente + " - " + nomeFornecedor + ")";

		return nomeContrato;
	}

}