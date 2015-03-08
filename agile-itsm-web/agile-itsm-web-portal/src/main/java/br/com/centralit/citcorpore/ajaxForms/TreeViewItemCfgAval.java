package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CicloIcDto;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

public class TreeViewItemCfgAval extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO)document.getBean();
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
		
		HTMLSelect idGrupoItemConfiguracao = document.getSelectById("idGrupoItemConfiguracao");
		idGrupoItemConfiguracao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		List<String> listaImagens = null;
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		//
		String CICLO_DESENVOLVIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.desenvolvimento"));
		String CICLO_PRODUCAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.producao"));
		String CICLO_HOMOLOGACAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.homologacao"));
		String NOME_INVENTARIO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_INVENTARIO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.inventario"));
		String NOME_DESENVOLVIMENTO_PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_GRUPO_PADRAO_DESENVOLVIMENTO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.desenvolvimentoPadrao"));
		String NOME_HOMOLOCACAO_PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOME_GRUPO_PADRAO_HOMOLOGACAO, UtilI18N.internacionaliza(request, "itemConfiguracao.ciclo.homolocacaoPadrao"));
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
			
			ID_CICLO_DESENVOLVIMENTO_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO_PADRAO, "1001").isEmpty() ? "1001" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO_PADRAO, "1001")));
			ID_CICLO_HOMOLOGACA0_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACAO_PADRAO, "1002").isEmpty() ? "1002" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_HOMOLOGACAO_PADRAO, "1002")));
			ID_CICLO_PRODUCAO_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003").isEmpty() ? "1003" : ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003")));
		} catch (Exception e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.informativoItemConfiguracao"));
			// document.executeScript("");
		}
		ItemConfiguracaoDTO itemFiltro = new ItemConfiguracaoDTO();
		
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
		//
		/*Capturando os itens do inventário (Não possuem grupos)*/
		List<ItemConfiguracaoDTO> listaItemSemGrupo = (List<ItemConfiguracaoDTO>) itemConfiguracaoService.listItensSemGrupo(itemFiltro);
		
		String nivel = "...";
		for (CicloIcDto c : ciclos) {
			List<GrupoItemConfiguracaoDTO> listaGrupos = new ArrayList<GrupoItemConfiguracaoDTO>();
			List<ItemConfiguracaoDTO> listaItensRelacionados = new ArrayList<ItemConfiguracaoDTO>();
			
			idGrupoItemConfiguracao.addOption("", c.getNome());
			
			if (c.getId().equals(ID_CICLO_DESENVOLVIMENTO))
				listaGrupos = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracaoDesenvolvimento(c.getId());
			else
				listaGrupos = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracaoPai(c.getId());
			
			if (listaGrupos != null){
				for (Iterator it = listaGrupos.iterator(); it.hasNext();){
					GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO)it.next();
					idGrupoItemConfiguracao.addOption("" + grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), nivel + StringEscapeUtils.escapeJavaScript(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao()));
					montaEstruturaIC(document, request, response, grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), nivel);
				}
			}
			
			if (c.getId().equals(ID_CICLO_DESENVOLVIMENTO)) {
				if (listaItemSemGrupo != null){
					idGrupoItemConfiguracao.addOption("0", nivel + NOME_INVENTARIO);
				}				
			}			
		}		

		if (itemConfiguracaoDTO.getProcessar() != null && itemConfiguracaoDTO.getProcessar().equalsIgnoreCase("S")){
			mostraInfo(document, request, response);
		}
		if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null){
			idGrupoItemConfiguracao.setValue("" + itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
		}
		
		HTMLSelect comboStatus;
		HTMLSelect comboCriticiidade;
		
		comboStatus = (HTMLSelect) document.getSelectById("status");
		comboStatus.removeAllOptions();
		comboStatus.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (Enumerados.StatusIC st : Enumerados.StatusIC.values()) {
			comboStatus.addOption(st.getItem().toString(), UtilI18N.internacionaliza(request,st.getChaveInternacionalizacao()));
		}
		
		/*Combo de criticidade*/
		 comboCriticiidade = (HTMLSelect) document.getSelectById("criticidade");
		comboCriticiidade.removeAllOptions(); 
		comboCriticiidade.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (Enumerados.CriticidadeIC ct : Enumerados.CriticidadeIC.values()) {
			comboCriticiidade.addOption(ct.getItem().toString(), UtilI18N.internacionaliza(request,ct.getDescricao()));
		}		
		
		HTMLSelect sistemaOperacional = document.getSelectById("sistemaOperacional");
		sistemaOperacional.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection colValues = valorService.listUniqueValuesByTagCaracteristica("OSNAME");
		if (colValues != null){
			sistemaOperacional.addOptions(colValues, "valorStr", "valorStr", itemConfiguracaoDTO.getSistemaOperacional());
		}
		HTMLSelect grupoTrabalho = document.getSelectById("grupoTrabalho");
		grupoTrabalho.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		colValues = valorService.listUniqueValuesByTagCaracteristica("WORKGROUP");
		if (colValues != null){
			grupoTrabalho.addOptions(colValues, "valorStr", "valorStr", itemConfiguracaoDTO.getGrupoTrabalho());
		}		
		HTMLSelect tipoMembroDominio = document.getSelectById("tipoMembroDominio");
		tipoMembroDominio.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		colValues = valorService.listUniqueValuesByTagCaracteristica("DESCDOMAINROLE");
		if (colValues != null){
			tipoMembroDominio.addOptions(colValues, "valorStr", "valorStr", itemConfiguracaoDTO.getTipoMembroDominio());
		}	
		HTMLSelect processador = document.getSelectById("processador");
		processador.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		colValues = valorService.listUniqueValuesByTagCaracteristica("PROCESSORT");
		if (colValues != null){
			processador.addOptions(colValues, "valorStr", "valorStr", itemConfiguracaoDTO.getProcessador());
		}	
		HTMLSelect usuario = document.getSelectById("usuario");
		usuario.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		colValues = valorService.listUniqueValuesByTagCaracteristica("USERID");
		if (colValues != null){
			for (Iterator it = colValues.iterator(); it.hasNext();){
				ValorDTO valorDTO = (ValorDTO)it.next();
				String user = valorDTO.getValorStr();
				if (user != null){
					int index = user.indexOf("-");
					if (index > -1){
						user = user.substring(index + 1);
					}
					usuario.addOption(user, user);
				}
			}
		}	
		document.getForm("form").setValues(itemConfiguracaoDTO);
	}
	public void montaEstruturaIC(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response, Integer idPai, String nivel) throws Exception {
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);		
		HTMLSelect idGrupoItemConfiguracao = document.getSelectById("idGrupoItemConfiguracao");
		
		String nivelAux = new String("" + nivel + "...");
		List<GrupoItemConfiguracaoDTO> listaGrupos = new ArrayList<GrupoItemConfiguracaoDTO>();
		listaGrupos = (List<GrupoItemConfiguracaoDTO>) grupoICService.listByIdGrupoItemConfiguracaoPai(idPai);	
		if (listaGrupos != null){
			for (Iterator it = listaGrupos.iterator(); it.hasNext();){
				GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO)it.next();
				idGrupoItemConfiguracao.addOption("" + grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), nivelAux + StringEscapeUtils.escapeJavaScript(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao()));
				montaEstruturaIC(document, request, response, grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), nivelAux);
			}
		}		
	}
	public void mostraInfo(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO)document.getBean();
		/*
		if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() == null){
			request.setAttribute("divInfo", "");
			return;
		}
		*/
		String softs = itemConfiguracaoDTO.getSoftwares();
		if (softs == null){
			softs = "";
		}
		softs = softs + ";";
		softs = softs.replaceAll(",", ";");
		String[] strsSofts = softs.split(";");
		List lstSofts = new ArrayList();
		if (strsSofts != null){
			for (int i = 0; i < strsSofts.length; i++){
				if (strsSofts[i] != null && !strsSofts[i].trim().equalsIgnoreCase("")){
					lstSofts.add(strsSofts[i]);
				}
			}
		}
		
		GrupoItemConfiguracaoDTO grupoICDto = new GrupoItemConfiguracaoDTO();
		grupoICDto.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
		Collection<ItemConfiguracaoDTO> colItens = null;
		if (itemConfiguracaoDTO.getCriticidade() == null){
			itemConfiguracaoDTO.setCriticidade(0);
		}
		if (itemConfiguracaoDTO.getStatus() == null){
			itemConfiguracaoDTO.setStatus(0);
		}		
		if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() == null || itemConfiguracaoDTO.getIdGrupoItemConfiguracao().intValue() == 0){
			colItens = itemConfiguracaoService.listItensSemGrupo("" + itemConfiguracaoDTO.getCriticidade(), "" + itemConfiguracaoDTO.getStatus(), itemConfiguracaoDTO.getSistemaOperacional(), itemConfiguracaoDTO.getGrupoTrabalho(), 
					itemConfiguracaoDTO.getTipoMembroDominio(), itemConfiguracaoDTO.getUsuario(), itemConfiguracaoDTO.getProcessador(), lstSofts);
		}else{
			colItens = itemConfiguracaoService.listByGrupo(grupoICDto, "", "");
		}
		Integer idTipo = 10;
		String table = "<table border='1' width='100%'>";
		table += "<tr>";
		table += "<td style='border:1px solid black'>";
			table += "<b>"+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.equipamento")+"</b>";
		table += "</td>";
		table += "<td style='border:1px solid black'>";
			table += "<b>&nbsp;</b>";
		table += "</td>";
		table += "<td style='border:1px solid black'>";
			table += "<b>&nbsp;</b>";
		table += "</td>";		
		table += "<td style='border:1px solid black'>";
			table += "<b>"+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.descricao")+"</b>";
		table += "</td>";	
		table += "<td style='border:1px solid black'>";
			table += "<b>"+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.IP")+"</b>";
		table += "</td>";		
		table += "<td style='border:1px solid black'>";
			table += "<b>"+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.ultimoInventario")+"</b>";
		table += "</td>";	
		table += "<td style='border:1px solid black'>";
			table += "<b>&nbsp;</b>";
		table += "</td>";		
		table += "<td style='border:1px solid black'>";
			table += "<b>"+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.sistemaOperacional")+"</b>";
		table += "</td>";	
		table += "<td style='border:1px solid black'>";
			table += "<b>"+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.storages")+"</b>";
		table += "</td>";	
		table += "</tr>";
		if (colItens != null){
			for (Iterator it = colItens.iterator(); it.hasNext();){
				ItemConfiguracaoDTO itemConfiguracaoAux = (ItemConfiguracaoDTO)it.next();
				ItemConfiguracaoDTO itemConfiguracaoAux2 = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoAux);
				String soName = "";
				String equipamento = "";
				String userId = "N/A";
				String descDomain = "N/A";
				String enderecoIp = "";
				String comments = "";
				String typeBios = "";
				Collection colHardware = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(itemConfiguracaoAux.getIdItemConfiguracao(), "HARDWARE");
				if (colHardware != null && colHardware.size() > 0){
					for (Iterator itX = colHardware.iterator(); itX.hasNext();){
						ItemConfiguracaoDTO itemConfiguracaoMem = (ItemConfiguracaoDTO)itX.next();
						Collection colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "OSNAME");
						ValorDTO valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								soName = valorCapacidade.getValorStr();
								break;
							}
						}
						colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "OSCOMMENTS");
						valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								comments = valorCapacidade.getValorStr();
								break;
							}
						}						
						colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "PROCESSORT");
						valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								equipamento = valorCapacidade.getValorStr() + "\n<br>";
							}
						}	
						colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "USERID");
						valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								userId = valorCapacidade.getValorStr() + "\n<br>";
							}
						}	
						colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "DESCDOMAINROLE");
						valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								descDomain = valorCapacidade.getValorStr() + "\n<br>";
							}
						}						
						colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "IPADDR");
						valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								enderecoIp = valorCapacidade.getValorStr();
								break;
							}
						}							
					}
				}
				Collection colBios = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(itemConfiguracaoAux.getIdItemConfiguracao(), "BIOS");
				if (colBios != null && colBios.size() > 0){
					for (Iterator itX = colBios.iterator(); itX.hasNext();){
						ItemConfiguracaoDTO itemConfiguracaoMem = (ItemConfiguracaoDTO)itX.next();
						Collection colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "TYPE");
						ValorDTO valorCapacidade = null;
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								typeBios = valorCapacidade.getValorStr();
								break;
							}
						}
					}
				}
				table += "<tr>";
					table += "<td style='border:1px solid black'>";
						table += "<b><u>" + itemConfiguracaoAux.getIdentificacao() + "</u></b>";
						if (typeBios != null && !typeBios.trim().equalsIgnoreCase("")){
							table += "<br><br>" + typeBios;
						}
					table += "</td>";
					table += "<td style='border:1px solid black'>";
						table += "<img title='" + UtilI18N.internacionaliza(request, "treeViewItemCfgAval.softwares") + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/viewCadastro.png' border='0' style='cursor:pointer' onclick='visualizaSofts(\"" + itemConfiguracaoAux.getIdItemConfiguracao() + "\")'/>";
					table += "</td>";
					table += "<td style='border:1px solid black'>";
						table += "<img title='" + UtilI18N.internacionaliza(request, "treeViewItemCfgAval.rede") + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/pegar.png' border='0' style='cursor:pointer' onclick='visualizaNet(\"" + itemConfiguracaoAux.getIdItemConfiguracao() + "\")'/>";	
					table += "</td>";
					table += "<td style='border:1px solid black'>";
						table += equipamento + "<br>User: " + userId + " Domain Type: " + descDomain;
					table += "</td>";
					table += "<td style='border:1px solid black'>";
						table += enderecoIp;
					table += "</td>";						
					table += "<td style='border:1px solid black'>";
					if (itemConfiguracaoAux2.getDtUltimaCaptura() != null){
						table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, itemConfiguracaoAux2.getDtUltimaCaptura(), WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(itemConfiguracaoAux2.getDtUltimaCaptura());
					}else{
						table += "--";
					}
					table += "</td>";					
					table += "<td style='border:1px solid black'>";
					if (soName.indexOf("Windows") > -1){
						table += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/Windows-icon.png' border='0'/>";
					}else if (soName.indexOf("Linux") > -1){
						table += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/Linux-icon.png' border='0'/>";
					}
					table += "</td>";
					table += "<td style='border:1px solid black'>";
						table += soName;
						if (comments != null && !comments.trim().equalsIgnoreCase("")){
							table += "<br>" + comments;	
						}
					table += "</td>";					
					Integer idGrupo = null;
					if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null && itemConfiguracaoDTO.getIdGrupoItemConfiguracao().intValue() != 0){
						idGrupo = itemConfiguracaoDTO.getIdGrupoItemConfiguracao();
					}
					table += "<td style='border:1px solid black'>";
						table += getInfoStorages(itemConfiguracaoAux.getIdItemConfiguracao(), request);
					table += "</td>";
					/*
					Collection colStorges = itemConfiguracaoService.listByIdGrupoAndTipoItemAndIdItemPaiAtivos(idGrupo, idTipo, itemConfiguracaoAux.getIdItemConfiguracao()); //STORAGES
					if (colStorges != null){
						for (Iterator it2 = colStorges.iterator(); it.hasNext();){
							ItemConfiguracaoDTO itemConfiguracaoAux2 = (ItemConfiguracaoDTO)it.next();
						}
					}
					*/
				table += "</tr>";
			}
		}
		table += "</table>";
		request.setAttribute("divInfo", table);
		//document.getElementById("divInfo").setInnerHTML(table);
	}
	public void viewNetwork(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO)document.getBean();
		if (itemConfiguracaoDTO.getIdItemConfiguracao() == null){
			document.alert(UtilI18N.internacionaliza(request, "treeViewItemCfgAval.informeItemConf"));
			return;
		}	
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null); 
		ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null); 		
		String memorias = "";
		Integer idItemConfiguracao = itemConfiguracaoDTO.getIdItemConfiguracao();
		memorias += "<table>";
		if (idItemConfiguracao != null){
			Collection colMemorias = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(idItemConfiguracao, "NETWORKS");
			if (colMemorias != null && colMemorias.size() > 0){
				for (Iterator it = colMemorias.iterator(); it.hasNext();){
					ItemConfiguracaoDTO itemConfiguracaoMem = (ItemConfiguracaoDTO)it.next();
					Collection colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "IPADDRESS");
					String ipAddr = "";
					ValorDTO valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								ipAddr = valorCapacidade.getValorStr();
							}catch(Exception e){
							}
							break;
						}
					}
					colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "DESCRIPTION");
					String descr = "";
					valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								descr = valorCapacidade.getValorStr();
							}catch(Exception e){
							}
							break;
						}
					}	
					colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "MACADDR");
					String macrAddr = "";
					valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								macrAddr = valorCapacidade.getValorStr();
							}catch(Exception e){
							}
							break;
						}
					}						
					memorias += "<tr>";
					memorias += "<td style='border:1px solid black'>";			
					memorias += itemConfiguracaoMem.getIdentificacao();
					memorias += "</td>";
					memorias += "<td style='border:1px solid black'>";
					memorias += "" + descr;
					memorias += "</td>";					
					memorias += "<td style='border:1px solid black'>";
					memorias += "" + ipAddr;
					memorias += "</td>";
					memorias += "<td style='border:1px solid black'>";
					memorias += "" + macrAddr;
					memorias += "</td>";					
					memorias += "</tr>";
				}
			}else{
				memorias += "<tr>";
				memorias += "<td style='border:1px solid black'>";			
				memorias += UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaDadosParaApresentar");
				memorias += "</td>";
				memorias += "<tr>";
			}
		}	
		memorias += "</table>";	
		document.executeScript("$('#POPUP_SOFTS').dialog('open');");
		document.getElementById("divInfoSoftware").setInnerHTML(memorias);
	}	
	public void viewSoftwares(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO)document.getBean();
		if (itemConfiguracaoDTO.getIdItemConfiguracao() == null){
			document.alert(UtilI18N.internacionaliza(request, "treeViewItemCfgAval.informeItemConf"));
			return;
		}	
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null); 
		ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null); 		
		String memorias = "";
		Integer idItemConfiguracao = itemConfiguracaoDTO.getIdItemConfiguracao();
		memorias += "<table>";
		if (idItemConfiguracao != null){
			Collection colMemorias = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(idItemConfiguracao, "SOFTWARES");
			if (colMemorias != null && colMemorias.size() > 0){
				for (Iterator it = colMemorias.iterator(); it.hasNext();){
					ItemConfiguracaoDTO itemConfiguracaoMem = (ItemConfiguracaoDTO)it.next();
					Collection colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "FOLDER");
					String folder = "";
					ValorDTO valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								folder = valorCapacidade.getValorStr();
							}catch(Exception e){
							}
							break;
						}
					}
					colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "VERSION");
					String versao = "";
					valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								versao = valorCapacidade.getValorStr();
							}catch(Exception e){
							}
							break;
						}
					}	
					colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "VENDOR");
					String fabcr = "";
					valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								fabcr = valorCapacidade.getValorStr();
							}catch(Exception e){
							}
							break;
						}
					}						
					memorias += "<tr>";
					memorias += "<td style='border:1px solid black'>";			
					memorias += itemConfiguracaoMem.getIdentificacao();
					memorias += "</td>";
					memorias += "<td style='border:1px solid black'>";
					memorias += "" + versao;
					memorias += "</td>";					
					memorias += "<td style='border:1px solid black'>";
					memorias += "" + folder;
					memorias += "</td>";
					memorias += "<td style='border:1px solid black'>";
					memorias += "" + fabcr;
					memorias += "</td>";					
					memorias += "</tr>";
				}
			}else{
				memorias += "<tr>";
				memorias += "<td style='border:1px solid black'>";			
				memorias += UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaDadosParaApresentar");
				memorias += "</td>";
				memorias += "<tr>";				
			}
		}	
		memorias += "</table>";	
		document.executeScript("$('#POPUP_SOFTS').dialog('open');");
		document.getElementById("divInfoSoftware").setInnerHTML(memorias);
	}
	public String getInfoStorages(Integer idItemConfiguracao, HttpServletRequest request) throws ServiceException, Exception{
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null); 
		ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null); 		
		String memorias = "";
		if (idItemConfiguracao != null){
			Collection colMemorias = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(idItemConfiguracao, "STORAGES");
			if (colMemorias != null && colMemorias.size() > 0){
				for (Iterator it = colMemorias.iterator(); it.hasNext();){
					ItemConfiguracaoDTO itemConfiguracaoMem = (ItemConfiguracaoDTO)it.next();
					Collection colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "CAPACITY");
					double dblValorCapacidade = 0;
					double dblValorLivre = 0;
					double percentual = 0;
					ValorDTO valorCapacidade = null;
					if (colCapacity != null){
						for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
							valorCapacidade = (ValorDTO)it2.next();
							try{
								dblValorCapacidade = Double.valueOf(valorCapacidade.getValorStr());
							}catch(Exception e){
							}
							break;
						}
					}
					if (dblValorCapacidade == 0){
						colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "DISKSIZE");
						if (colCapacity != null){
							for (Iterator it2 = colCapacity.iterator(); it2.hasNext();){
								valorCapacidade = (ValorDTO)it2.next();
								try{
									dblValorCapacidade = Double.valueOf(valorCapacidade.getValorStr());
								}catch(Exception e){
								}
								break;
							}
						}						
					}
					Collection colUsed = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "FREE");
					ValorDTO valorFree = null;
					if (colUsed != null){
						for (Iterator it2 = colUsed.iterator(); it2.hasNext();){
							valorFree = (ValorDTO)it2.next();
							try{
								dblValorLivre = Double.valueOf(valorFree.getValorStr());
							}catch(Exception e){
							}										
							break;
						}
					}	
					if (dblValorCapacidade > 0){
						percentual = ((dblValorCapacidade - dblValorLivre) / dblValorCapacidade) * 100;
						memorias += "<table>";
						memorias += "<tr>";
						memorias += "<td>";			
						
						memorias += "<table width='10px'>";
						memorias += "<tr>";
						for (int i = 1; i <= 10; i++){
							double perc = percentual / 10;
							if (i > perc){
								memorias += "<td style='background-color:green;width:1px' width='1px'>";	
								memorias += "&nbsp;";
									//memorias += "<img src='http://localhost:8080/citsmart/imagens/title-bg.gif' border='0'/>";
								memorias += "</td>";
							}else{
								memorias += "<td style='background-color:red;width:1px' width='1px'>";
								memorias += "&nbsp;";
									//memorias += "&nbsp;";
								memorias += "</td>";
							}
						}
						//memorias += "<td>";	
						//	memorias += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/regra-settings-icon.png' border='0'/>";
						//memorias += "</td>";						
						memorias += "</tr>";
						memorias += "</table>";
						
						memorias += "</td>";
						memorias += "<td>";
						memorias += itemConfiguracaoMem.getIdentificacao() + " - % "+UtilI18N.internacionaliza(request, "treeViewItemCfgAval.utilizado")+": " + UtilFormatacao.formatDouble(percentual,2) + "%<br>"; // (Capacidade: " + UtilFormatacao.formatDouble(dblValorCapacidade,2) + " - Utilizado: " + UtilFormatacao.formatDouble(dblValorUtilizado,2) + ")<br>" ;
						memorias += "</td>";
						memorias += "</tr>";
						memorias += "</table>";						
					}
				}
			}
		}	
		return memorias;
	}

	@Override
	public Class getBeanClass() {
		return ItemConfiguracaoDTO.class;
	}

}
