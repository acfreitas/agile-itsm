package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CicloIcDto;
import br.com.centralit.citcorpore.bean.GerenciaConfiguracaoTreeDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.StatusIC;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

public class GerenciaConfiguracaoTree extends AjaxFormAction {
	
	private String IMAGEM_ITEM_DEFAULT = "computerpng";
	
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/**
		 * Checa qual a preferência de exibição do nome do item de configuração na árvore
		 * 
		 * @author thyen.chang
		 */
		boolean exibeNome = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EXIBIR_NOME_ITEM_CONFIGURACAO_TREE, "S").equals("N")? false : true;
		
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
    	}
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		
		if (gerenciaConfiguracaoTreeDTO != null && gerenciaConfiguracaoTreeDTO.getIframe() != null && gerenciaConfiguracaoTreeDTO.getIframe().equalsIgnoreCase("true")) {
			document.executeScript("esconderBotaoVoltar();");
		}
		
		StringBuilder sb = new StringBuilder();
		String identificacao = gerenciaConfiguracaoTreeDTO.getIdentificacao();
		String criticidade = gerenciaConfiguracaoTreeDTO.getCriticidade();
		String status = gerenciaConfiguracaoTreeDTO.getStatus();
		identificacao = UtilStrings.nullToVazio(identificacao);
		criticidade = UtilStrings.nullToVazio(criticidade);
		status = UtilStrings.nullToVazio(status);
		
		ItemConfiguracaoDTO itemFiltro = new ItemConfiguracaoDTO();
		itemFiltro.setIdentificacao(identificacao);
		itemFiltro.setCriticidade((!criticidade.equals("") ? new Integer(criticidade) : null));
		itemFiltro.setStatus((!status.equals("") ? new Integer(status) : null));
		
		/*carregaCombosFiltro(document, request, response);*/
		
		
		/* CAPTURANDO AS FASES DO CICLO DE VIDA DO IC */
		String CICLO_DESENVOLVIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.desenvolvimento"));
		String CICLO_PRODUCAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.producao"));
		String CICLO_HOMOLOGACAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.homologacao"));
		String NOME_INVENTARIO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_INVENTARIO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.inventario"));
		String NOME_DESENVOLVIMENTO_PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_GRUPO_PADRAO_DESENVOLVIMENTO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.desenvolvimentoPadrao"));
		String NOME_HOMOLOCACAO_PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_GRUPO_PADRAO_HOMOLOGACAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.homologacaoPadrao"));
		String NOME_PRODUCAO_PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_GRUPO_PADRAO_PRODUCAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.producaoPadrao"));
		
		Integer ID_CICLO_DESENVOLVIMENTO = 0, ID_CICLO_PRODUCAO = 0, ID_CICLO_HOMOLOGACA0 = 0, ID_INVENTARIO = 0, ID_CICLO_DESENVOLVIMENTO_PADRAO = 0, ID_CICLO_PRODUCAO_PADRAO = 0, ID_CICLO_HOMOLOGACA0_PADRAO = 0;
		try {
			ID_CICLO_DESENVOLVIMENTO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO, "997").isEmpty() ? "997" : ParametroUtil.getValorParametroCitSmartHashMap(
					ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO, "997")));
			ID_CICLO_PRODUCAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998").isEmpty() ? "998" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998")));
			ID_CICLO_HOMOLOGACA0 = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACA0, "999").isEmpty() ? "999" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACA0,
					"999")));
			ID_INVENTARIO = Integer
					.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_INVENTARIO, "1000").isEmpty() ? "1000" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_INVENTARIO, "1000")));
			
			ID_CICLO_DESENVOLVIMENTO_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO_PADRAO, "1001")));
			ID_CICLO_HOMOLOGACA0_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACAO_PADRAO, "1002")));
			ID_CICLO_PRODUCAO_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003")));
		} catch (Exception e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.informativoItemConfiguracao"));
			// document.executeScript("");
		}
		
		//Verifica se existe as pastas padroes, e as cria se nao existir
		grupoICService.autenticaGrupoPadrao(ID_CICLO_DESENVOLVIMENTO_PADRAO, ID_CICLO_DESENVOLVIMENTO, NOME_DESENVOLVIMENTO_PADRAO);
		grupoICService.autenticaGrupoPadrao(ID_CICLO_HOMOLOGACA0_PADRAO, ID_CICLO_HOMOLOGACA0, NOME_HOMOLOCACAO_PADRAO);
		grupoICService.autenticaGrupoPadrao(ID_CICLO_PRODUCAO_PADRAO, ID_CICLO_PRODUCAO, NOME_PRODUCAO_PADRAO);
		
		CicloIcDto c1 = new CicloIcDto();
		c1.setId(ID_CICLO_DESENVOLVIMENTO);
		c1.setNome(CICLO_DESENVOLVIMENTO);

		CicloIcDto c3 = new CicloIcDto();
		c3.setId(ID_CICLO_HOMOLOGACA0);
		c3.setNome(CICLO_HOMOLOGACAO);

		CicloIcDto c2 = new CicloIcDto();
		c2.setId(ID_CICLO_PRODUCAO);
		c2.setNome(CICLO_PRODUCAO);

		List<CicloIcDto> ciclos = new ArrayList<CicloIcDto>();
		ciclos.add(c1);
		ciclos.add(c3);
		ciclos.add(c2);
		List<String> listaImagens = null;
		HttpSession session = ((HttpServletRequest) request).getSession();
		listaImagens = listDirectorioImagens(new File(CITCorporeUtil.CAMINHO_REAL_APP +"/pages/tipoItemConfiguracao/imagens/16/"));    		
		session.setAttribute("css",gerarCSS(listaImagens));
		
		/*Capturando os itens do inventário (Não possuem grupos)*/
		List<ItemConfiguracaoDTO> listaItemSemGrupo = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listItensSemGrupo(itemFiltro);
		

		for (CicloIcDto c : ciclos) {
			List<GrupoItemConfiguracaoDTO> listaGrupos = new ArrayList<GrupoItemConfiguracaoDTO>();
			List<ItemConfiguracaoDTO> listaItensRelacionados = new ArrayList<ItemConfiguracaoDTO>();

			if (c.getId().equals(ID_CICLO_DESENVOLVIMENTO))
				listaGrupos = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracaoDesenvolvimento(c.getId());
			else
				listaGrupos = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracaoPai(c.getId());

			sb.append("<ul>");
			sb.append("<li rel='database' id='grupo_" + c.getId() + "'>");
			sb.append("<a style='cursor: pointer;'>");
			sb.append(c.getNome());
			sb.append("</a>");

			sb.append("<ul>");
			gerarTreeView(listaGrupos, sb, itemConfiguracaoService, itemFiltro,request);

			if (c.getId().equals(ID_CICLO_DESENVOLVIMENTO)) {
				sb.append("<li rel='grupoRelacionado' id='grupo_" + ID_INVENTARIO + "'>");
				sb.append("<a style='cursor: pointer;'>");
				sb.append("Inventario - " + NOME_INVENTARIO);
				sb.append("	</a>");
				// sb.append("</li>");
				if (listaItemSemGrupo != null && listaItemSemGrupo.size() > 0) {
					sb.append("<ul>");
					for (ItemConfiguracaoDTO itemSemGrupoDto : listaItemSemGrupo) {
						sb.append("<li rel='item' id='item_" + itemSemGrupoDto.getIdItemConfiguracao() + "'>");
						sb.append("<a  class='r "+(itemSemGrupoDto.getImagem() != null ? itemSemGrupoDto.getImagem().replace(".", "") : IMAGEM_ITEM_DEFAULT)+"' style='cursor: pointer;'>");
						if(exibeNome)
							sb.append(itemSemGrupoDto.getNome() + " - " + "<span class='" + getLegenda(itemSemGrupoDto.getStatus()) + "'>" + getStatus(itemSemGrupoDto.getStatus(),request) + "</span>");
						else
							sb.append(itemSemGrupoDto.getIdentificacao() + " - " + "<span class='" + getLegenda(itemSemGrupoDto.getStatus()) + "'>" + getStatus(itemSemGrupoDto.getStatus(),request) + "</span>");
						sb.append("</a>");
						sb.append("</li>");
					}
					sb.append("</ul>");
				}
				sb.append("</li>");
				sb.append("<div id='legenda' onclick='legenda();'><img src='../../imagens/graudeimportancia.png' title='"
						+ UtilI18N.internacionaliza(request, "gerenciaConfiguracaoTree.legenda.titulo") + "' /></div>");
			}
			sb.append("</ul>");

			// Itens soltos
			GrupoItemConfiguracaoDTO grupo = new GrupoItemConfiguracaoDTO();
			grupo.setIdGrupoItemConfiguracao(0);
			listaItensRelacionados = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listByGrupo(grupo, itemFiltro);
			if (listaItensRelacionados != null && listaItensRelacionados.size() > 0) {
				for (ItemConfiguracaoDTO itensRelacionados : listaItensRelacionados) {
					sb.append("<li rel='item' id='item_" + itensRelacionados.getIdItemConfiguracao() + "'>");
					sb.append("<a  class='r "+(itensRelacionados.getImagem() != null ? itensRelacionados.getImagem().replace(".", "") : IMAGEM_ITEM_DEFAULT)+"' style='cursor: pointer;'>");
					if(exibeNome)
						sb.append(itensRelacionados.getNome() + " - " + "<span class='" + getLegenda(itensRelacionados.getStatus()) + "'>" + getStatus(itensRelacionados.getStatus(),request) + "</span>");
					else
						sb.append(itensRelacionados.getIdentificacao() + " - " + "<span class='" + getLegenda(itensRelacionados.getStatus()) + "'>" + getStatus(itensRelacionados.getStatus(),request) + "</span>");
					sb.append("</a>");
					sb.append("</li>");
				}
			}

			sb.append("</ul>");
		}

		HTMLElement divPrincipal = document.getElementById("jsTreeIC");
		divPrincipal.setInnerHTML(sb.toString());
		document.executeScript("load()");

		// ---
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		PerfilAcessoMenuDTO perfilAcessoMenudto = new PerfilAcessoMenuDTO();
		PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();
		String url = "/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load";
		Integer idMenu = menuService.buscarIdMenu(url);
		String acessoGravar = "N";
		String acessoDeletar = "N";
		String acessoPesquisar = "N";
		if (idMenu != null) {
			if (usrDto.getIdPerfilAcessoUsuario() != null) {

				perfilAcessoMenudto.setIdPerfilAcesso(usrDto.getIdPerfilAcessoUsuario());
				perfilAcessoMenudto.setIdMenu(idMenu);

				Collection<PerfilAcessoMenuDTO> listaPerfilAcessoMenu = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);

				if (listaPerfilAcessoMenu != null) {
					for (PerfilAcessoMenuDTO perfilAcessoMenu : listaPerfilAcessoMenu) {

						if (acessoGravar.equals("N") && acessoDeletar.equals("N") && acessoPesquisar.equals("N")) {

							if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")) {
								acessoGravar = "S";
							}
							if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")) {
								acessoDeletar = "S";
							}
							if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")) {
								acessoPesquisar = "S";
							}
						}
					}
				}

				UsuarioDTO usuario = (UsuarioDTO) usuarioService.restore(usrDto);
				Integer idEmpregado = usuario.getIdEmpregado();

				@SuppressWarnings("unchecked")
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
								if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")) {
									acessoGravar = "S";
								}
								if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")) {
									acessoDeletar = "S";
								}
								if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")) {
									acessoPesquisar = "S";
								}
							}
						}
					}
				}
			}
		}
		if (acessoGravar.equalsIgnoreCase("S")) {
			document.executeScript("acessoGravar = 'S'");
		}
		if (acessoDeletar.equalsIgnoreCase("S")) {
			document.executeScript("acessoDeletar = 'S'");
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
		
	}
	


	@SuppressWarnings("unused")
	private String getRequestedPath(HttpServletRequest request) {
		String path = request.getRequestURI() + request.getQueryString();
		path = path.substring(request.getContextPath().length());
		int index = path.indexOf("?");
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
	
	@SuppressWarnings("unused")
	private void gerarTreeView(List<GrupoItemConfiguracaoDTO> listaGrupos, StringBuilder sb, ItemConfiguracaoService itemConfiguracaoService, String criticidade, String status,HttpServletRequest request) throws Exception {
		List<ItemConfiguracaoDTO> listaItemConfiguracao = null;
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		if(listaGrupos != null && listaGrupos.size() > 0){
			for (GrupoItemConfiguracaoDTO grupoICDto : listaGrupos) {

				sb.append("<li rel='grupoRelacionado' id='grupo_" + grupoICDto.getIdGrupoItemConfiguracao() + "'>");
				sb.append("<a style='cursor: pointer;'>");
				sb.append(grupoICDto.getNomeGrupoItemConfiguracao());
				sb.append("	</a>");

				List<GrupoItemConfiguracaoDTO> listaGrupoRelacionados = new ArrayList<GrupoItemConfiguracaoDTO>();
				listaGrupoRelacionados = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracao(grupoICDto.getIdGrupoItemConfiguracao());
				if (listaGrupoRelacionados != null && listaGrupoRelacionados.size() > 0) {
					sb.append("<ul>");
					gerarTreeView(listaGrupoRelacionados, sb, itemConfiguracaoService, criticidade, status,request);
					sb.append("</ul>");
				}
				
			

				listaItemConfiguracao = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listByGrupo(grupoICDto, criticidade, status);
				if (listaItemConfiguracao != null && listaItemConfiguracao.size() > 0) {
					sb.append("<ul>");
				}
				if(listaItemConfiguracao != null){
					for (ItemConfiguracaoDTO itemConfiguracaoDto : listaItemConfiguracao) {
						String criticos = "";
						if(itemConfiguracaoService.verificaItemCriticos(itemConfiguracaoDto.getIdItemConfiguracao()))
							criticos = "<img src='../../imagens/b.gif' />";
						
						sb.append("<li rel='item' id='item_" + itemConfiguracaoDto.getIdItemConfiguracao() + "'>");
						sb.append("<a  class='r "+(itemConfiguracaoDto.getImagem() != null ? itemConfiguracaoDto.getImagem().replace(".", "") : IMAGEM_ITEM_DEFAULT)+"' style='cursor: pointer;'>");
						sb.append(itemConfiguracaoDto.getIdentificacao() +" - " +
							"<span class='"+getLegenda(itemConfiguracaoDto.getStatus())+"'>"+getStatus(itemConfiguracaoDto.getStatus(),request)+"</span>");
						sb.append("</a>");
						sb.append(criticos);
					}
				}
				if (listaItemConfiguracao != null && listaItemConfiguracao.size() > 0) {
					sb.append("</ul>");
				}
				sb.append("</li>");
			}
		}
		// sb.append("</ul>");
	}
	
	private void gerarTreeView(List<GrupoItemConfiguracaoDTO> listaGrupos, StringBuilder sb, ItemConfiguracaoService itemConfiguracaoService, ItemConfiguracaoDTO itemConfiguracaoDTO,HttpServletRequest request) throws Exception {
		List<ItemConfiguracaoDTO> listaItemConfiguracao = null;
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		
		/**
		 * Checa qual a preferência de exibição do nome do item de configuração na árvore
		 */
		boolean exibeNome = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EXIBIR_NOME_ITEM_CONFIGURACAO_TREE, "S").equals("N")? false : true;
		
		if(listaGrupos != null && listaGrupos.size() > 0){
			for (GrupoItemConfiguracaoDTO grupoICDto : listaGrupos) {

				sb.append("<li rel='grupoRelacionado' id='grupo_" + grupoICDto.getIdGrupoItemConfiguracao() + "'>");
				sb.append("<a style='cursor: pointer;'>");
				sb.append(grupoICDto.getNomeGrupoItemConfiguracao());
				sb.append("	</a>");

				List<GrupoItemConfiguracaoDTO> listaGrupoRelacionados = new ArrayList<GrupoItemConfiguracaoDTO>();
				listaGrupoRelacionados = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracao(grupoICDto.getIdGrupoItemConfiguracao());
				if (listaGrupoRelacionados != null && listaGrupoRelacionados.size() > 0) {
					sb.append("<ul>");
					gerarTreeView(listaGrupoRelacionados, sb, itemConfiguracaoService, itemConfiguracaoDTO,request);
					sb.append("</ul>");
				}
				
			

				listaItemConfiguracao = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listByGrupo(grupoICDto, itemConfiguracaoDTO);
				if (listaItemConfiguracao != null && listaItemConfiguracao.size() > 0) {
					sb.append("<ul>");
				}
				if(listaItemConfiguracao != null){
					for (ItemConfiguracaoDTO itemConfiguracaoDto : listaItemConfiguracao) {
						String criticos = "";
						if(itemConfiguracaoService.verificaItemCriticos(itemConfiguracaoDto.getIdItemConfiguracao()))
							criticos = "<img src='../../imagens/b.gif' />";
						
						sb.append("<li rel='item' id='item_" + itemConfiguracaoDto.getIdItemConfiguracao() + "'>");
						sb.append("<a  class='r "+(itemConfiguracaoDto.getImagem() != null ? itemConfiguracaoDto.getImagem().replace(".", "") : IMAGEM_ITEM_DEFAULT)+"' style='cursor: pointer;'>");
						if(exibeNome)
							sb.append(itemConfiguracaoDto.getNome() +" - " +
									"<span class='"+getLegenda(itemConfiguracaoDto.getStatus())+"'>"+getStatus(itemConfiguracaoDto.getStatus(),request)+"</span>");
						else
							sb.append(itemConfiguracaoDto.getIdentificacao() +" - " +
									"<span class='"+getLegenda(itemConfiguracaoDto.getStatus())+"'>"+getStatus(itemConfiguracaoDto.getStatus(),request)+"</span>");
						sb.append("</a>");
						sb.append(criticos);
					}
				}
				if (listaItemConfiguracao != null && listaItemConfiguracao.size() > 0) {
					sb.append("</ul>");
				}
				sb.append("</li>");
			}
		}
		// sb.append("</ul>");
	}

	@SuppressWarnings("unused")
	public void CriarGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		GrupoItemConfiguracaoDTO grupoIcDTO = new GrupoItemConfiguracaoDTO();
		grupoIcDTO.setNomeGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getNomeGrupoItemConfiguracao());
		grupoIcDTO.setIdGrupoItemConfiguracaoPai(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracaoPai());
		grupoIcDTO.setDataInicio(UtilDatas.getDataAtual());

		String PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ENVIO_PADRAO_EMAIL_IC, "1");
		// ParametroCorporeDTO parametroDTO = new ParametroCorporeDTO();
		String grupoItensNovos = "";

		boolean jaCadastrado = grupoICService.VerificaSeCadastrado(grupoIcDTO);

		if (jaCadastrado || grupoItensNovos.equalsIgnoreCase(grupoIcDTO.getNomeGrupoItemConfiguracao())) {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaConfiguracaoTree.grupoExistente"));
			document.executeScript("$('#jsTreeIC').jstree('remove','#grupo_novo');");
		} else {
			/* Criando o grupo e atribuindo o novo grupo ao DTO. */
			grupoIcDTO = (GrupoItemConfiguracaoDTO) grupoICService.create(grupoIcDTO);

			/* Procurando o novo grupo criado na arvore e atribuindo o ID. */
			String novoId = "grupo_" + grupoIcDTO.getIdGrupoItemConfiguracao();
			document.executeScript("atualizarId('grupo_novo', '" + novoId + "')");
		}
	}

	public void renomearGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		GrupoItemConfiguracaoDTO grupoIcDTO = new GrupoItemConfiguracaoDTO();

		grupoIcDTO.setNomeGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getNomeGrupoItemConfiguracao());
		grupoIcDTO.setIdGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao());

		if(validaRenomearGrupoPadrao(document, request, response, grupoIcDTO)){
			document.alert(UtilI18N.internacionaliza(request, "grupoItemConfiguracao.renomearGrupoPadrao"));
			return;
		}
		
		grupoICService.updateNotNull(grupoIcDTO);
	}

	public void apagarGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		GrupoItemConfiguracaoDTO grupoIcDTO = new GrupoItemConfiguracaoDTO();

		grupoIcDTO.setIdGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao());
		grupoIcDTO.setDataFim(UtilDatas.getDataAtual());

		grupoICService.updateNotNull(grupoIcDTO);
	}

	public void mudarGrupoDeGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		GrupoItemConfiguracaoDTO grupoIcDTO = new GrupoItemConfiguracaoDTO();

		grupoIcDTO.setIdGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao());
		grupoIcDTO.setIdGrupoItemConfiguracaoPai(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracaoPai());

		grupoICService.updateNotNull(grupoIcDTO);
	}

	public void mudarItemDeGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		Integer ID_CICLO_DESENVOLVIMENTO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO, "997").isEmpty() ? "997" : ParametroUtil.getValorParametroCitSmartHashMap(
				ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO, "997")));
		Integer ID_CICLO_PRODUCAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998").isEmpty() ? "998" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998")));
		Integer ID_CICLO_HOMOLOGACA0 = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACA0, "999").isEmpty() ? "999" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACA0,
				"999")));
		Integer ID_INVENTARIO = Integer
				.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_INVENTARIO, "1000").isEmpty() ? "1000" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_INVENTARIO, "1000")));
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

		itemConfiguracaoDTO.setIdItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracao());
		itemConfiguracaoDTO.setIdGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao());
		if (gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao().intValue() == ID_CICLO_DESENVOLVIMENTO || 
				gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao().intValue() == ID_CICLO_HOMOLOGACA0 || 
				gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao().intValue() == ID_CICLO_PRODUCAO) {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaConfiguracaoTree.informativoMovimentacao"));
			document.executeScript("recarregar()");
			return;
		}
		if(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao().intValue() != ID_INVENTARIO)
			itemConfiguracaoService.atualizaGrupo(itemConfiguracaoDTO, usrDto);
	}

	public void CriarItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemDTO = new ItemConfiguracaoDTO();

		/* Preenchendo DTO de item de configuracao. */
		itemDTO.setIdentificacao(gerenciaConfiguracaoTreeDTO.getIdentificacao());
		itemDTO.setNome(gerenciaConfiguracaoTreeDTO.getIdentificacao());
		itemDTO.setInformacoesAdicionais(UtilStrings.nullToVazio(null));
		if (gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao() != null && gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao() > 0) {
			itemDTO.setIdGrupoItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdGrupoItemConfiguracao());
		}
		itemDTO.setDataInicio(UtilDatas.getDataAtual());

		boolean jaCadastrado = itemConfiguracaoService.VerificaSeCadastrado(itemDTO);

		if (jaCadastrado) {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaConfiguracaoTree.itemExistente"));
			document.executeScript("$('#jsTreeIC').jstree('remove','#item_novo');");
		} else {
			/* Criando o item e atribuindo o novo item ao DTO. */
			itemDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.createItemConfiguracaoAplicacao(itemDTO, usrDto);

			/* Procurando o novo item criado na arvore e atribuindo o ID. */
			String novoId = "item_" + itemDTO.getIdItemConfiguracao();
			document.executeScript("$('#identificacao').val('');");
			document.executeScript("atualizarId('item_novo', '" + novoId + "')");
			document.executeScript("abrirEditarItem('" + novoId + "');");
		}
	}

	public void CriarItemRelacionado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemDTO = new ItemConfiguracaoDTO();
		ItemConfiguracaoDTO itemPaiDTO = new ItemConfiguracaoDTO();

		/* Preenchendo DTO de item de configuracao. */
		itemDTO.setIdentificacao(gerenciaConfiguracaoTreeDTO.getIdentificacao());
		itemDTO.setNome(gerenciaConfiguracaoTreeDTO.getIdentificacao());
		if (gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoPai() > 0) {
			itemDTO.setIdItemConfiguracaoPai(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoPai());
			itemPaiDTO.setIdItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoPai());
			itemPaiDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemPaiDTO);

			if (itemPaiDTO != null) {
				itemDTO.setIdGrupoItemConfiguracao(itemPaiDTO.getIdGrupoItemConfiguracao());
			}
		}
		itemDTO.setDataInicio(UtilDatas.getDataAtual());

		boolean jaCadastrado = itemConfiguracaoService.VerificaSeCadastrado(itemDTO);

		if (jaCadastrado) {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaConfiguracaoTree.itemExistente"));
			document.executeScript("$('#jsTreeIC').jstree('remove','#itemRel_novo');");
		} else {
			/* Criando o item e atribuindo o novo item ao DTO. */
			itemDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.createItemConfiguracaoAplicacao(itemDTO, usrDto);

			/* Procurando o novo item criado na arvore e atribuindo o ID. */
			String novoId = "item_" + itemDTO.getIdItemConfiguracao();
			document.executeScript("atualizarId('itemRel_novo', '" + novoId + "')");
			document.executeScript("abrirEditarItem('" + novoId + "');");
		}
	}

	public void ApagarItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemDTO = new ItemConfiguracaoDTO();

		itemDTO.setIdItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracao());

		itemDTO.setDataFim(UtilDatas.getDataAtual());

		itemConfiguracaoService.updateNotNull(itemDTO);
	}

	public void pesquisarRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemDTO = new ItemConfiguracaoDTO();

		itemDTO.setIdItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracao());
		itemDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemDTO);
		if (itemDTO != null) {
			document.executeScript("setaValor(" + itemDTO.getIdItemConfiguracaoPai() + ")");
			document.executeScript("$('#POPUP_PESQUISA').dialog('open')");
		}
	}

	public void ListarRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		/**
		 * Checa qual a preferência de exibição do nome do item de configuração na árvore
		 * 
		 * @author thyen.chang
		 */
		boolean exibeNome = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EXIBIR_NOME_ITEM_CONFIGURACAO_TREE, "S").equals("N")? false : true;
		
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemDTO = new ItemConfiguracaoDTO();
		Integer ID_INVENTARIO = Integer
				.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_INVENTARIO, "1000").isEmpty() ? "1000" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_INVENTARIO, "1000")));
		itemDTO.setIdItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracao());
		itemDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemDTO);
		if(itemDTO!=null) {
			

		        List<ItemConfiguracaoDTO> listaItensRelacionados = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listByIdItemConfiguracaoPai(itemDTO.getIdItemConfiguracao());
	
			
			if (listaItensRelacionados != null && listaItensRelacionados.size() > 0) {
		             /*rcs (Rafael César Soyer) - o código abaixo foi comentado, porque definiu-se que o mesmo, não deveria ser mostrado
	                     * data: 15/12/2014
	                        
	                        String json = "";
				json += "{\"nodes\" : [";
				for (ItemConfiguracaoDTO itemRelacionado : listaItensRelacionados) {
					if (!json.endsWith("[")) json += ", ";
					
					if(itemRelacionado.getImagem() != null) {
						json += "{";
						if(exibeNome)
							json += "	\"data\" : { \"title\" : \"" + StringEscapeUtils.escapeJavaScript(itemRelacionado.getNome()) + "\", \"icon\" : \"../tipoItemConfiguracao/imagens/16/" + itemRelacionado.getImagem() + "\" },";
						else
							json += "	\"data\" : { \"title\" : \"" + StringEscapeUtils.escapeJavaScript(itemRelacionado.getIdentificacao()) + "\", \"icon\" : \"../tipoItemConfiguracao/imagens/16/" + itemRelacionado.getImagem() + "\" },";

						json += "	\"attr\" : { \"rel\" : \"itemRelacionado\", \"id\" : \"item_" + itemRelacionado.getIdItemConfiguracao() + "\" }";
						json += "}";
					} else {
						json += "{";
						if(exibeNome)
							json += "	\"data\" : { \"title\" : \"" + StringEscapeUtils.escapeJavaScript(itemRelacionado.getNome()) + "\", \"icon\" : \"\" },";
						else
							json += "	\"data\" : { \"title\" : \"" + StringEscapeUtils.escapeJavaScript(itemRelacionado.getIdentificacao()) + "\", \"icon\" : \"\" },";
						json += "	\"attr\" : { \"rel\" : \"itemRelacionado\", \"id\" : \"item_" + itemRelacionado.getIdItemConfiguracao() + "\" }";
						json += "}";
					}					
				}
				json += "]}";
				
				
				document.executeScript("adicionarRelacionadosJson('item_" + itemDTO.getIdItemConfiguracao() + "', " + json + ")");*/
				document.executeScript("criticos('item_" + itemDTO.getIdItemConfiguracao() + "');");
			}
			if(exibeNome){
				document.executeScript("renomearMoverItemConfiguracao("+itemDTO.getIdItemConfiguracao()+",\""+itemDTO.getNome()
					+"\","+itemDTO.getIdGrupoItemConfiguracao()+",\""+getStatus(itemDTO.getStatus(),request)+"\",\""+getLegenda(itemDTO.getStatus())+"\", "
						+ " \""+ID_INVENTARIO+"\")");
			}else
				document.executeScript("renomearMoverItemConfiguracao("+itemDTO.getIdItemConfiguracao()+",\""+itemDTO.getIdentificacao()
						+"\","+itemDTO.getIdGrupoItemConfiguracao()+",\""+getStatus(itemDTO.getStatus(),request)+"\",\""+getLegenda(itemDTO.getStatus())+"\", "
						+ " \""+ID_INVENTARIO+"\")");;
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public void exportarCMDB(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
			if (usuarioDto == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
			ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
			CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
			ObjetoNegocioDTO objetoNegocioDTO = objetoNegocioService.getByNomeTabelaDB("ITEMCONFIGURACAO");
			if (objetoNegocioDTO == null) {
				return;
			}
			DataManager dataManager = new DataManager();
			String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
			if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

			Integer ID_CICLO_DESENVOLVIMENTO = 0;
			try {
				ID_CICLO_DESENVOLVIMENTO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO, "997").isEmpty() ? "997" : ParametroUtil.getValorParametroCitSmartHashMap(
						ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO, "997")));
			} catch (Exception e) {
			}

			// ---
			HashMap hashValoresItem = new HashMap();
			hashValoresItem.put("NOMETABELADB", "ITEMCONFIGURACAO");

			String nomeTabelaItem = "ITEMCONFIGURACAO";

			Collection col = camposObjetoNegocioService.findByIdObjetoNegocioAndNomeDB(objetoNegocioDTO.getIdObjetoNegocio(), "IDGRUPOITEMCONFIGURACAO");
			CamposObjetoNegocioDTO camposObjetoNegocioDTO = null;
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
				}
			}
			if (camposObjetoNegocioDTO == null) {
				return;
			}

			hashValoresItem.put("COND_" + camposObjetoNegocioDTO.getIdCamposObjetoNegocio(), "IN");
			hashValoresItem.put("VALOR_" + camposObjetoNegocioDTO.getIdCamposObjetoNegocio(), "(SELECT idgrupoitemconfiguracao FROM grupoitemconfiguracao WHERE idGrupoItemConfiguracaoPai = "
					+ gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoExport() + ")");

			// ---
			ObjetoNegocioDTO objetoNegocioGrpDTO = objetoNegocioService.getByNomeTabelaDB("GRUPOITEMCONFIGURACAO");
			if (objetoNegocioGrpDTO == null) {
				return;
			}
			HashMap hashValoresGrp = new HashMap();
			hashValoresGrp.put("NOMETABELADB", "GRUPOITEMCONFIGURACAO");

			String nomeTabelaGrp = "GRUPOITEMCONFIGURACAO";

			col = camposObjetoNegocioService.findByIdObjetoNegocioAndNomeDB(objetoNegocioGrpDTO.getIdObjetoNegocio(), "IDGRUPOITEMCONFIGURACAOPAI");
			camposObjetoNegocioDTO = null;
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
				}
			}
			if (camposObjetoNegocioDTO == null) {
				return;
			}

			if (ID_CICLO_DESENVOLVIMENTO.intValue() == gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoExport().intValue()) {
				hashValoresGrp.put("COND_" + camposObjetoNegocioDTO.getIdCamposObjetoNegocio(), "=");
				hashValoresGrp.put("VALOR_" + camposObjetoNegocioDTO.getIdCamposObjetoNegocio(), "" + gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoExport()
						+ " OR IDGRUPOITEMCONFIGURACAOPAI IS NULL");
			} else {
				hashValoresGrp.put("COND_" + camposObjetoNegocioDTO.getIdCamposObjetoNegocio(), "=");
				hashValoresGrp.put("VALOR_" + camposObjetoNegocioDTO.getIdCamposObjetoNegocio(), "" + gerenciaConfiguracaoTreeDTO.getIdItemConfiguracaoExport());
			}
			String sqlDelete = "";
			String filterAditional = "";
			// StringBuilder strAux = dataManager.geraRecursiveExportObjetoNegocio(hashValoresItem, objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, "ITEMCONFIGURACAO", filterAditional);
			StringBuilder strAuxGrp = dataManager.geraRecursiveExportObjetoNegocio(hashValoresGrp, objetoNegocioGrpDTO.getIdObjetoNegocio(), sqlDelete, "GRUPOITEMCONFIGURACAO", filterAditional, "");

			// String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + strAuxGrp.toString() + "\n" + strAux.toString();
			String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + strAuxGrp.toString();
			str = "" + str + "\n</tables>";

			String strDateTime = (new java.util.Date()).toString();
			strDateTime = strDateTime.replaceAll(" ", "_");
			strDateTime = strDateTime.replaceAll(":", "_");
			UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_" + strDateTime + ".smart", str);

			// JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
			LogDados logDados = new LogDados();
			logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual());
			logDados.setIdUsuario(usuarioDto.getIdUsuario());
			logDados.setDataInicio(UtilDatas.getDataAtual());
			logDados.setDataLog(UtilDatas.getDataHoraAtual());
			logDados.setNomeTabela(nomeTabelaItem);
			logDados.setOperacao("Export");
			logDados.setLocalOrigem(usuarioDto.getNomeUsuario());
			logDados.setDados("Execute export... " + nomeTabelaItem);

			LogDadosService lds = new LogDadosServiceBean();

			try {
				logDados = (LogDados) lds.create(logDados);
			} catch (Exception e) {
			}

			document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
			document.executeScript("getFile('" + CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_" + strDateTime + ".smart', 'export_data_" + strDateTime + ".smart')");
		} finally {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}

	public void verificaCriticidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		GerenciaConfiguracaoTreeDTO gerenciaConfiguracaoTreeDTO = (GerenciaConfiguracaoTreeDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemDTO = new ItemConfiguracaoDTO();

		itemDTO.setIdItemConfiguracao(gerenciaConfiguracaoTreeDTO.getIdItemConfiguracao());
		itemDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemDTO);
		if(itemDTO != null && itemDTO.getStatus() != null && (itemDTO.getStatus().equals(StatusIC.VALIDAR.getItem()))){
			if (itemDTO.getIdItemConfiguracaoPai() == null)
				document.executeScript("addCriticoPai('item_" + itemDTO.getIdItemConfiguracao() + "')");
			else
				document.executeScript("addCritico('item_" + itemDTO.getIdItemConfiguracao() + "')");
		}
		if (itemDTO != null && itemConfiguracaoService.verificaItemCriticos(itemDTO.getIdItemConfiguracao())) {
			if (itemDTO.getIdItemConfiguracaoPai() == null)
				document.executeScript("addCriticoPai('item_" + itemDTO.getIdItemConfiguracao() + "')");
			else
				document.executeScript("addCritico('item_" + itemDTO.getIdItemConfiguracao() + "')");
		}
	}

	private String getStatus(Integer s,HttpServletRequest request ) {
		//String retorno = StatusIC.ATIVADO.getDescricao();
		String retorno = UtilI18N.internacionaliza(request, "baseItemConfiguracao.Ativado");
		if (s != null) {
			switch (s) {
			case 1:
				//retorno = StatusIC.ATIVADO.getDescricao();
				retorno = UtilI18N.internacionaliza(request, "baseItemConfiguracao.Ativado");
				break;
			case 2:
				//retorno = StatusIC.DESATIVADO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Desativado");
				break;
			case 3:
				//retorno = StatusIC.EM_MANUTENCAO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Em_Manutenção");
				break;
			case 4:
				//retorno = StatusIC.IMPLANTACAO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Implantação");
				break;
			case 5:
				//retorno = StatusIC.HOMOLOGACAO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Homologação");
				break;
			case 6:
				//retorno = StatusIC.EM_DESENVOLVIMENTO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Em_Desenvolvimento");
				break;
			case 7:
				//retorno = StatusIC.ARQUIVADO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Arquivado");
				break;
			case 8:
				//retorno = StatusIC.VALIDAR.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Validar_Item");
				break;
			default:
				//retorno = StatusIC.ATIVADO.getDescricao();
				retorno =UtilI18N.internacionaliza(request, "baseItemConfiguracao.Ativado");
				break;
			}
		}
		return retorno.toUpperCase();
	}

	/* Retorna o CSS para lagenda */
	private String getLegenda(Integer s) {
		String retorno = "lAtivado";
		if (s != null) {
			switch (s) {
			case 1:
				retorno = "lAtivado";
				break;
			case 2:
				retorno = "lDesativado";
				break;
			case 3:
				retorno = "lEmManutencao";
				break;
			case 4:
				retorno = "lImplantacao";
				break;
			case 5:
				retorno = "lHomologacao";
				break;
			case 6:
				retorno = "lEmDesenvolvimento";
				break;
			case 7:
				retorno = "lArquivado";
				break;
			case 8:
				retorno = "lvalidar";
				break;
			default:
				retorno = "lAtivado";
				break;
			}
		}
		return retorno;
	}
	
	public void carregaCombosFiltro(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//Preenchendos as combos filtro;
				/*Combo de status*/
		HTMLSelect comboStatus;
		HTMLSelect comboCriticiidade;
		
			comboStatus = (HTMLSelect) document.getSelectById("comboStatus");
			comboStatus.removeAllOptions();
			comboStatus.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
			for (Enumerados.StatusIC st : Enumerados.StatusIC.values()) {
				comboStatus.addOption(st.getItem().toString(), UtilI18N.internacionaliza(request,st.getChaveInternacionalizacao()));
			}
			
			/*Combo de criticidade*/
			 comboCriticiidade = (HTMLSelect) document.getSelectById("cboCriticidade");
			comboCriticiidade.removeAllOptions(); 
			comboCriticiidade.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
			for (Enumerados.CriticidadeIC ct : Enumerados.CriticidadeIC.values()) {
				comboCriticiidade.addOption(ct.getItem().toString(), UtilI18N.internacionaliza(request,ct.getDescricao()));
			}
		
		 
	} 

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return GerenciaConfiguracaoTreeDTO.class;
	}
	
	public String gerarCSS(List<String> imagens) {
		StringBuilder sbi = new StringBuilder();
		for (String element : imagens) {
			sbi.append("a."+element.replace(".", "")+" .jstree-icon {");
			sbi.append("		background-image: url(\"../tipoItemConfiguracao/imagens/16/"+element+"\") !important;");
			sbi.append("}");
		}
    	return sbi.toString();
	}
	
    public static java.util.List<String> listDirectorioImagens(File dir) {  
	    List<String> lista = new ArrayList<String>();
		if (dir.isDirectory()) {  
	        String[] filhos = dir.list();  
	        for (int i = 0; i < filhos.length; i++) {
	            File nome = new File(dir, filhos[i]);  
	            if (nome.isFile()) {  
	                if (nome.getName().endsWith(".png"))
	                    lista.add(nome.getName());  
	            }
	        }  
	    } 
	    return lista;  
	}
    
    private boolean validaRenomearGrupoPadrao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, GrupoItemConfiguracaoDTO grupoIC){
    	Integer idDesenvPadrao = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO_PADRAO, "1001")));
		Integer idHomoloPadrao = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACAO_PADRAO, "1002")));
		Integer idProducPadrao = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003")));
		
		return (grupoIC.getIdGrupoItemConfiguracao().intValue() == idDesenvPadrao || grupoIC.getIdGrupoItemConfiguracao().intValue() == idHomoloPadrao || grupoIC.getIdGrupoItemConfiguracao().intValue() == idProducPadrao);
    }
    
}