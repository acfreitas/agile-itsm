package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.InventarioDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.comm.server.NetDiscoverAdvanced;
import br.com.centralit.citcorpore.comm.server.Servidor;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

/**
 * @author Maycon.Fernandes
 *
 */
public class InventarioAdvanced extends AjaxFormAction {

	private List<NetMapDTO> listNetMap;
	private List<NetMapDTO> lstErro;
	private DocumentHTML document1;
	private static final String VALUE = "HARDWARE";
	private static final List<String> VALUES_ATRIBUTOS = new ArrayList<String>(Arrays.asList(
		     new String[]  {"NAME", "WORKGROUP", "USERDOMAIN", "OSNAME", "OSVERSION", "OSCOMMENTS", "ARCH", "PROCESSORT", "PROCESSORS",
		 			"PROCESSORN",	"MEMORY", "SWAP", "IPADDR", "ETIME", "LASTDATE", "USERID", "LASTLOGGEDUSER", "TYPE", "DESCRIPTION", "WINCOMPANY", "WINOWNER", "WINPRODID", "WINPRODKEY",
		 			"UUID", "VMSYSTEM", "CHECKSUM"}
		));
	DocumentHTML doc;

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			doc = document;
			List<NetMapDTO> listNetMapDto = this.getPesquisaListIp();
			NetDiscoverAdvanced netDiscover = new NetDiscoverAdvanced();
			if (listNetMapDto != null) {
				for (NetMapDTO netMapDTO : listNetMapDto) {
					String ipCor ="";

					if(netMapDTO.getNome()!= null){
						ipCor= netMapDTO.getNome();
					}else{
						ipCor = netMapDTO.getIp();
					}

					netMapDTO.setAtivo("");
					netMapDTO.setCorRetorno("<span>" + ipCor + "</span>");
				}
			} else {
				listNetMapDto = netDiscover.listaIpsAtivos();
				for (NetMapDTO netMapDTO : listNetMapDto) {
					String ipCor ="";
					if(netMapDTO.getNome()!= null){
						ipCor = "<span style='color: green'>" + netMapDTO.getNome() + "</span>";
					}else{
						ipCor = "<span style='color: green'>" + netMapDTO.getIp() + "</span>";
					}
					netMapDTO.setAtivo("");
					netMapDTO.setCorRetorno(ipCor);
				}
			}
			this.getPreencheTabelaIP(listNetMapDto, document);
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * Pesquisar ips no banco de dados
	 *
	 * @return lista NetMap
	 * @throws Exception
	 * @throws ServiceException
	 */
	public List<NetMapDTO> getPesquisaListIp() throws ServiceException, Exception {

		List<NetMapDTO> listnetMap = null;
		try {
			NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
			listnetMap = (List<NetMapDTO>) netMapService.listIp();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


//		try {
//			netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
//			ParametroCorporeDTO paramentroDTO = null;

			/*ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

			List<ParametroCorporeDTO> listDiasInventario = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.DiasInventario.id(), Enumerados.ParametroSistema.DiasInventario.campo());

			String diasInventario = "";
			if (listDiasInventario == null) {
				return null;
			}
			if ((paramentroDTO = (ParametroCorporeDTO) listDiasInventario.get(0)).getValor() != null) {
				diasInventario = (paramentroDTO = (ParametroCorporeDTO) listDiasInventario.get(0)).getValor();
			}

			Date dataInventario = UtilDatas.getSqlDate(UtilDatas.incrementaDiasEmData(Util.getDataAtual(), -(new Integer(diasInventario.trim()))));
			*/

			// Pesquisa ips para geracao de inventario, passa data como
			// parametro, para identificar a partir de que data sera inventariado.
			//listnetMap = (List<NetMapDTO>) netMapService.listIpByDataInventario(dataInventario);


		/*	} catch (Exception e) {
			System.out.println("Problema ao pesquisar lista de IPs: " + e.getMessage());
			doc.alert("Problema ao pesquisar lista de IPs, confira os parâmentros do sistema.");
			e.printStackTrace();
		}*/
		return listnetMap;
	}

	/**
	 * Preencher tabela IP Formulario Inventario
	 *
	 * @param listnetMap
	 *            List NetMapDto
	 * @param document
	 * @throws Exception
	 */
	public void getPreencheTabelaIP(List<NetMapDTO> listnetMap, DocumentHTML document) throws Exception {
		StringBuilder dados = new StringBuilder();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		try {
			dados.append("<table class='table' id='tblip' width='100%'>");
			dados.append("<tr>");
			dados.append("<td colspan = 10 style=' width: 100%; border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
			dados.append("<label class='marcaTodos'><input  type='checkbox' id='marcatodos' name='marcatodos' onclick='marcarTodosCheckbox();'>Marcar Todos</label>");
			dados.append("<label class='persquisaNet'> <button  type='button' id='pesquisarNetMap' name='pesquisarNetMap' onclick='netMapManual();'>Realizar Discovery&nbsp;&nbsp;&nbsp;</button> </label>");
			dados.append("</td></tr>");
			int cont = 0;
			String trAbre = "<tr>";
			String trFecha = "</tr>";
			if (listnetMap != null) {
				for (NetMapDTO netMapDTO : listnetMap) {
					String corRetorno = netMapDTO.getCorRetorno();
					if (corRetorno == null)
						corRetorno = "";

					ItemConfiguracaoDTO itemConfiguracaoDTO = null;
					Collection lstItens = itemConfiguracaoService.listByIdentificacao(netMapDTO.getIp());
					if (lstItens != null){
						for (Iterator it = lstItens.iterator(); it.hasNext();){
							ItemConfiguracaoDTO itemConfiguracaoAux = (ItemConfiguracaoDTO)it.next();
							itemConfiguracaoDTO = itemConfiguracaoAux;
							break; //Pega apenas o 1.o
						}
					}

					String memorias = "";
					String storages = "";
					if (itemConfiguracaoDTO != null){
						memorias = getInfoMemorias(itemConfiguracaoDTO.getIdItemConfiguracao());
						storages = getInfoStorages(itemConfiguracaoDTO.getIdItemConfiguracao());
					}

					String iconSO = "";
					if (netMapDTO.getSistemaoper() != null && !netMapDTO.getSistemaoper().trim().equalsIgnoreCase("")){
						if (netMapDTO.getSistemaoper().indexOf("Windows") > -1){
							iconSO = "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/Windows-icon.png' border='0'/>";
						}
					}
					if (cont == 0) {
						dados.append(trAbre);
						mostraInfoItem(dados, netMapDTO, memorias, storages, iconSO);
						/*
						dados.append("<td width='20%'> ");
						dados.append("<table width='100%'> ");
						dados.append("<tr>");
						dados.append("<td onclick='abreFecha(\"div_" + netMapDTO.getIdNetMap() + "\")'>");
						dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + iconSO + "<span style='font-size:16px'><u>" + corRetorno.replaceAll("null", "--") + "</u></span>");
						dados.append("</td>");
						dados.append("</tr>");
						dados.append("<tr><td>");
						dados.append("<div id='div_" + netMapDTO.getIdNetMap() + "' style='display:none'> ");
						if (netMapDTO.getHardware() != null && !netMapDTO.getHardware().trim().equalsIgnoreCase("")){
							dados.append("<br>" + netMapDTO.getHardware() + "");
						}
						if (netMapDTO.getSistemaoper() != null && !netMapDTO.getSistemaoper().trim().equalsIgnoreCase("")){
							dados.append("SO: <b>" + netMapDTO.getSistemaoper() + "</b>");
						}
						if (netMapDTO.getUptime() != null && !netMapDTO.getUptime().trim().equalsIgnoreCase("")){
							dados.append("<br>Up time:" + netMapDTO.getUptime());
						}
						if (!memorias.equalsIgnoreCase("")){
							dados.append("<br><b>Memória: </b><br>" + memorias);
						}
						if (!storages.equalsIgnoreCase("")){
							dados.append("<br><b>Storages: </b><br>" + storages);
						}
						dados.append("</div> ");
						dados.append("</td></tr>");
						dados.append("</label>");
						dados.append("</table>");
						dados.append("</td>");
						*/
						cont++;

					} else if (cont == 5) {
						mostraInfoItem(dados, netMapDTO, memorias, storages, iconSO);
						/*
						dados.append("<td width='20%'>");
						dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'" + netMapDTO.getAtivo() + " /> " + iconSO + "<span style='font-size:16px'><u>" + corRetorno.replaceAll("null", "--") + "</u></span>");
						dados.append("<div id='div_" + netMapDTO.getIdNetMap() + "'> ");
						if (netMapDTO.getHardware() != null && !netMapDTO.getHardware().trim().equalsIgnoreCase("")){
							dados.append("<br>" + netMapDTO.getHardware() + "");
						}
						if (netMapDTO.getSistemaoper() != null && !netMapDTO.getSistemaoper().trim().equalsIgnoreCase("")){
							dados.append("<br>SO: <b>" + netMapDTO.getSistemaoper() + "</b>");
						}
						if (netMapDTO.getUptime() != null && !netMapDTO.getUptime().trim().equalsIgnoreCase("")){
							dados.append("<br>Up time:" + netMapDTO.getUptime());
						}
						if (!memorias.equalsIgnoreCase("")){
							dados.append("<br><b>Memória: </b><br>" + memorias);
						}
						if (!storages.equalsIgnoreCase("")){
							dados.append("<br><b>Storages: </b><br>" + storages);
						}
						dados.append("</div> ");
						dados.append("</label></td>" + trFecha);
						*/
						dados.append(trFecha);
						cont = 0;
					} else {
						mostraInfoItem(dados, netMapDTO, memorias, storages, iconSO);
						/*
						dados.append("<td width='20%'> ");
						dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + iconSO + "<span style='font-size:16px'><u>" + corRetorno.replaceAll("null", "--") + "</u></span>");
						if (netMapDTO.getHardware() != null && !netMapDTO.getHardware().trim().equalsIgnoreCase("")){
							dados.append("<br>" + netMapDTO.getHardware() + "");
						}
						if (netMapDTO.getSistemaoper() != null && !netMapDTO.getSistemaoper().trim().equalsIgnoreCase("")){
							dados.append("<br>SO: <b>" + netMapDTO.getSistemaoper() + "</b>");
						}
						if (netMapDTO.getUptime() != null && !netMapDTO.getUptime().trim().equalsIgnoreCase("")){
							dados.append("<br>Up time:" + netMapDTO.getUptime());
						}
						if (!memorias.equalsIgnoreCase("")){
							dados.append("<br><b>Memória: </b><br>" + memorias);
						}
						if (!storages.equalsIgnoreCase("")){
							dados.append("<br><b>Storages: </b><br>" + storages);
						}
						dados.append("</label></td>");
						*/
					}
					cont++;
				}
			}
			dados.append("</table>");

			dados.append("<table class='table' id='totalIP' >");
			dados.append("<tr>");
			dados.append("<td colspan = 10 style=' width: 1206px; border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
			dados.append("<label class='quantIP'>Quantidade de IP Ativo</label>");
			dados.append("<label style='margin-left: 940px'>");

			if (listnetMap != null && listnetMap.size() > 0) {
				dados.append(listnetMap.size());
			} else {
				dados.append(0);
			}
			dados.append("</label>");

			dados.append("</td></tr>");

			HTMLElement htmlele = document.getElementById("ipMac");
			htmlele.setInnerHTML(dados.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void mostraInfoItem(StringBuilder dados, NetMapDTO netMapDTO, String memorias, String storages, String iconSO){
		String corRetorno = netMapDTO.getCorRetorno();
		dados.append("<td width='20%'> ");
		dados.append("<table width='100%'> ");
		dados.append("<tr>");
		dados.append("<td>");
		dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + iconSO + "<span style='font-size:16px'><u>" + corRetorno.replaceAll("null", "--") + "</u></span>");
		dados.append("</td>");
		dados.append("</tr>");
		dados.append("<tr><td>");
		dados.append("<div><img onclick='abreFecha(\"div_" + netMapDTO.getIdNetMap() + "\")' id='div_" + netMapDTO.getIdNetMap() + "_CTRL' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/expand.gif' border='0'/></div>");
		dados.append("<div id='div_" + netMapDTO.getIdNetMap() + "' style='display:none'> ");
		boolean vazio = true;
		if (netMapDTO.getHardware() != null && !netMapDTO.getHardware().trim().equalsIgnoreCase("")){
			dados.append("<br>" + netMapDTO.getHardware() + "");
			vazio = false;
		}
		if (netMapDTO.getSistemaoper() != null && !netMapDTO.getSistemaoper().trim().equalsIgnoreCase("")){
			dados.append("<br>SO: <b>" + netMapDTO.getSistemaoper() + "</b>");
			vazio = false;
		}
		if (netMapDTO.getUptime() != null && !netMapDTO.getUptime().trim().equalsIgnoreCase("")){
			dados.append("<br>Up time:" + netMapDTO.getUptime());
			vazio = false;
		}
		if (!memorias.equalsIgnoreCase("")){
			dados.append("<br><b>Memória: </b><br>" + memorias);
			vazio = false;
		}
		if (!storages.equalsIgnoreCase("")){
			dados.append("<br><b>Storages: </b><br>" + storages);
			vazio = false;
		}
		if (vazio){
			dados.append("<b>Não há informações.</b>");
		}
		dados.append("</div> ");
		dados.append("</td></tr>");
		dados.append("</label>");
		dados.append("</table>");
		dados.append("</td>");
	}

	public String getInfoMemorias(Integer idItemConfiguracao) throws ServiceException, Exception{
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ValorService valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
		String memorias = "";
		if (idItemConfiguracao != null){
			Collection colMemorias = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(idItemConfiguracao, "MEMORIES");
			if (colMemorias != null){
				for (Iterator it = colMemorias.iterator(); it.hasNext();){
					ItemConfiguracaoDTO itemConfiguracaoMem = (ItemConfiguracaoDTO)it.next();
					Collection colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "CAPACITY");
					double dblValorCapacidade = 0;
					double dblValorUtilizado = 0;
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
					Collection colUsed = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "USED");
					ValorDTO valorUtilizado = null;
					if (colUsed != null){
						for (Iterator it2 = colUsed.iterator(); it2.hasNext();){
							valorUtilizado = (ValorDTO)it2.next();
							try{
								dblValorUtilizado = Double.valueOf(valorUtilizado.getValorStr());
							}catch(Exception e){
							}
							break;
						}
					}
					if (dblValorCapacidade > 0){
						percentual = (dblValorUtilizado / dblValorCapacidade) * 100;
						memorias += itemConfiguracaoMem.getIdentificacao() + " - % Utilizado: " + UtilFormatacao.formatDouble(percentual,2) + "%<br>"; // (Capacidade: " + UtilFormatacao.formatDouble(dblValorCapacidade,2) + " - Utilizado: " + UtilFormatacao.formatDouble(dblValorUtilizado,2) + ")<br>" ;
						memorias += "<table width='10px'>";
						memorias += "<tr>";
						for (int i = 1; i <= 10; i++){
							double perc = percentual / 10;
							if (i > perc){
								memorias += "<td style='background-color:green;width:1px' width='1px'>";
									//memorias += "<img src='http://localhost:8080/citsmart/imagens/title-bg.gif' border='0'/>";
								memorias += "</td>";
							}else{
								memorias += "<td style='background-color:red;width:1px' width='1px'>";
									//memorias += "&nbsp;";
								memorias += "</td>";
							}
						}
						memorias += "<td>";
							memorias += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/regra-settings-icon.png' onclick='abreItensRegrasIC(\"" + itemConfiguracaoMem.getIdItemConfiguracao() + "\")' border='0'/>";
						memorias += "</td>";
						memorias += "</tr>";
						memorias += "</table>";
					}
				}
			}
		}
		return memorias;
	}

	public String getInfoStorages(Integer idItemConfiguracao) throws ServiceException, Exception{
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
					double dblValorUtilizado = 0;
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
					Collection colUsed = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "USED");
					ValorDTO valorUtilizado = null;
					if (colUsed != null){
						for (Iterator it2 = colUsed.iterator(); it2.hasNext();){
							valorUtilizado = (ValorDTO)it2.next();
							try{
								dblValorUtilizado = Double.valueOf(valorUtilizado.getValorStr());
							}catch(Exception e){
							}
							break;
						}
					}
					if (dblValorCapacidade > 0){
						percentual = (dblValorUtilizado / dblValorCapacidade) * 100;
						memorias += itemConfiguracaoMem.getIdentificacao() + " - % Utilizado: " + UtilFormatacao.formatDouble(percentual,2) + "%<br>"; // (Capacidade: " + UtilFormatacao.formatDouble(dblValorCapacidade,2) + " - Utilizado: " + UtilFormatacao.formatDouble(dblValorUtilizado,2) + ")<br>" ;
						memorias += "<table width='10px'>";
						memorias += "<tr>";
						for (int i = 1; i <= 10; i++){
							double perc = percentual / 10;
							if (i > perc){
								memorias += "<td style='background-color:green;width:1px' width='1px'>";
									//memorias += "<img src='http://localhost:8080/citsmart/imagens/title-bg.gif' border='0'/>";
								memorias += "</td>";
							}else{
								memorias += "<td style='background-color:red;width:1px' width='1px'>";
									//memorias += "&nbsp;";
								memorias += "</td>";
							}
						}
						memorias += "<td>";
							memorias += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/regra-settings-icon.png' border='0'/>";
						memorias += "</td>";
						memorias += "</tr>";
						memorias += "</table>";
					}
				}
			}
		}
		return memorias;
	}

	/**
	 * Rotina para verificar ips ativos na rede e preencher tabela de Ips
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void netMapManual(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, LogicException, Exception {
		NetDiscoverAdvanced netDiscover = new NetDiscoverAdvanced();

		// Pesquisa Ips Ativos na Rede
		List<NetMapDTO> lstAtivos = null;
		List<NetMapDTO> lstPesquisa = null;
		try {
			lstAtivos = netDiscover.listaIpsAtivos();
			lstPesquisa = getPesquisaListIp();

			if((lstAtivos != null && !lstAtivos.isEmpty()) || (lstPesquisa != null && !lstPesquisa.isEmpty() )){
				this.getPreencheTabelaIP(this.verificarIpInventarioAtivo(lstPesquisa, lstAtivos),document);
			} else{
				document.alert(UtilI18N.internacionaliza(request, "inventario.validacao.listaAtivos"));
			}

		} catch (LogicException e) {
			document.alert(e.getMessage());
			document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
	}

	/**
	 * Verifica quais ips Estão Ativos e seta CHECKED
	 *
	 * @param lstPesquisa
	 *            NetMapDTO
	 * @param lstAtivos
	 *            NetMapDTO
	 * @return List NetMapDTO
	 */
	public List<NetMapDTO> verificarIpInventarioAtivo(List<NetMapDTO> lstPesquisa, List<NetMapDTO> lstAtivos) {

		List<NetMapDTO> lstRetorno = new ArrayList<NetMapDTO>();
		NetMapService netMapService;
		try {
			netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
			if (lstPesquisa.size() >= lstAtivos.size()) {
				for (NetMapDTO netMapDTO : lstPesquisa) {
					for (NetMapDTO netMapDTOPesquisa : lstAtivos) {

						if (netMapDTOPesquisa == null || netMapDTO == null)
							continue;

						if (netMapDTO.getIp() != null && netMapDTO.getIp().equals(netMapDTOPesquisa.getIp())) {
							String ipCor = "";
							if (netMapDTO.getNome() != null) {
								ipCor = netMapDTO.getNome();
							} else {
								ipCor = netMapDTO.getIp();
							}
							netMapDTO.setAtivo("CHECKED");
							List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDTO);
							String icNovo = "";
							if(netMapDTOPesquisa.getIcNovo() != null){
								if(netMapDTOPesquisa.getIcNovo().equalsIgnoreCase("true")){
									icNovo = "<a href='#' style='color: red;' > NEW IC </a>";
								}
							}
							netMapDTO.setCorRetorno("<span style='color: green'>" + ipCor + "</span>" + icNovo);
							break;
						} else {
							String ipCor = "";
							if (netMapDTO.getNome() != null) {
								ipCor = netMapDTO.getNome();
							} else {
								ipCor = netMapDTO.getIp();
							}
							netMapDTO.setCorRetorno("<span style='color: black'>" + ipCor + "</span>");
							netMapDTO.setAtivo("");
						}
					}
					lstRetorno.add(netMapDTO);
				}
			} else {
				for (NetMapDTO netMapDTO : lstAtivos) {
					for (NetMapDTO netMapDTOPesquisa : lstPesquisa) {
						if (netMapDTO.getIp().equals(netMapDTOPesquisa.getIp())) {
							String ipCor = "";
							if (netMapDTO.getNome() != null) {
								ipCor = netMapDTO.getNome();
							} else {
								ipCor = netMapDTO.getIp();
							}
							netMapDTO.setAtivo("CHECKED");
							List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDTO);
							String icNovo = "";
							if(netMapDTOPesquisa.getIcNovo() != null){
								if(netMapDTOPesquisa.getIcNovo().equalsIgnoreCase("true")){
									icNovo = "<a href='#' style='color: red;' > NEW IC </a>";
								}
							}
							netMapDTO.setCorRetorno("<span style='color: green'>" + ipCor + "</span>" + icNovo);
							break;

						} else {
							String ipCor = "";
							if (netMapDTO.getNome() != null) {
								ipCor = netMapDTO.getNome();
							} else {
								ipCor = netMapDTO.getIp();
							}
							netMapDTO.setCorRetorno("<span style='color: black'>" + ipCor + "</span>");
							netMapDTO.setAtivo("");
						}
					}
					lstRetorno.add(netMapDTO);
				}
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstRetorno;
	}

	/**
	 * Rotina que gera inventario manual
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public void InventarioManual(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document1 = document;
		try {
			InventarioDTO inventarioDTO = (InventarioDTO) document.getBean();
			Map map = document.getForm("form").getDocument().getValuesForm();
			Object ips = map.get("IPS");
			listNetMap = new ArrayList<NetMapDTO>();
			NetMapDTO netMapDto = null;
			String[] ipMac = null;
			NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
			if (ips != null) {
				if (ips.getClass().getName().equalsIgnoreCase("[Ljava.lang.String;")) {
					String[] arrayIp = (String[]) ips;
					for (String ip : arrayIp) {
						netMapDto = new NetMapDTO();
						ipMac = ip.split("_");
						netMapDto.setIp(ipMac[0]);
						if (ipMac.length > 1) {
							netMapDto.setMac(ipMac[1]);
						}
						List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDto);
						if (lstNetMap != null && !lstNetMap.isEmpty()) {
							netMapDto.setIcNovo("false");
						} else {
							netMapDto.setIcNovo("true");
						}
						listNetMap.add(netMapDto);
					}
				} else {
					String iP = (String) ips;
					if (!iP.equals("")) {
						netMapDto = new NetMapDTO();
						ipMac = iP.split("_");
						netMapDto.setIp(ipMac[0]);
						if (ipMac.length > 1) {
							netMapDto.setMac(ipMac[1]);
						}
						List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDto);
						if (lstNetMap != null && !lstNetMap.isEmpty()) {
							netMapDto.setIcNovo("false");
						} else {
							netMapDto.setIcNovo("true");
						}
						listNetMap.add(netMapDto);
					}
				}
			}

			String atributos = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.Atributo, " ");
			String noPesquisa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NoPesquisa, " ");

			String[] arr = (String[]) (atributos == null ? "" : atributos.trim().split(","));

			if (VALUE.equalsIgnoreCase((noPesquisa == null ? "" : noPesquisa.trim())) && VALUES_ATRIBUTOS.containsAll(Arrays.asList(arr))) {
				lstErro = new Servidor().carregarListaIP(listNetMap);

				Thread messageHandling = new Thread() {
					public void run() {
						try {
							this.getPreencheTabelaResultado(this.tabelaResultado(lstErro, listNetMap), document1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					private void getPreencheTabelaResultado(List<NetMapDTO> listnetMap, DocumentHTML document) throws Exception {
						StringBuilder dados = new StringBuilder();
						dados.append("<table class='table' id='tblipResultado' >");
						dados.append("<tr>");
						dados.append("<td colspan = 10 style=' width: 590px; border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
						dados.append("</td></tr>");
						int cont = 0;
						String trAbre = "<tr>";
						String trFecha = "</tr>";
						if (listnetMap != null) {
							for (NetMapDTO netMapDTO : listnetMap) {
								if (cont == 0) {
									dados.append(trAbre);
									dados.append("<td> ");
									dados.append("<label>" + netMapDTO.getCorRetorno());
									dados.append("</label></td>");
									cont++;

								} else if (cont == 5) {
									dados.append("<td>");
									dados.append("<label>" + netMapDTO.getCorRetorno());
									dados.append("</label></td>" + trFecha);
									cont = 0;
								} else {
									dados.append("<td> ");
									dados.append("<label>" + netMapDTO.getCorRetorno());
									dados.append("</label></td>");
								}
								cont++;
							}
						}
						dados.append("</table>");
						HTMLElement htmlele = document.getElementById("resultado");
						htmlele.setInnerHTML(dados.toString());
						document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
						document.executeScript("$('#POPUP_RESULTADO_INVENTARIO').dialog('open');");

					}

					private List<NetMapDTO> tabelaResultado(List<NetMapDTO> lstNetMapDtoErro, List<NetMapDTO> netMapDtoInventariar) {
						List<NetMapDTO> lstRetorno = new ArrayList<NetMapDTO>();
						for (NetMapDTO netMapDTO : netMapDtoInventariar) {
							if (lstNetMapDtoErro != null && lstNetMapDtoErro.size() > 0) {
								for (NetMapDTO netMapDTOErro : lstNetMapDtoErro) {
									if (netMapDTO.getIp().equals(netMapDTOErro.getIp())) {
										String ipCor = netMapDTO.getIp();
										netMapDTO.setCorRetorno("<span style='color: red'>" + ipCor + "</span>");
										break;
									} else {
										String ipCor = netMapDTO.getIp();
										String icNovo = "";
										if (netMapDTO.getIcNovo() == null && netMapDTOErro.getIcNovo() != null) {
											netMapDTO.setIcNovo(netMapDTOErro.getIcNovo());
										}
										if (netMapDTO.getIcNovo().equalsIgnoreCase("true")) {
											icNovo = "";
										}
										netMapDTO.setCorRetorno("<span style='color: green'>" + ipCor + "</span>" + icNovo);
										netMapDTO.setAtivo("");
									}
								}
							} else {
								String ipCor = netMapDTO.getIp();
								String icNovo = "";
								if (netMapDTO.getIcNovo().equalsIgnoreCase("true")) {
									icNovo = "";
								}
								netMapDTO.setCorRetorno("<span style='color: green'>" + ipCor + "</span>" + icNovo);
								netMapDTO.setAtivo("");
							}
							lstRetorno.add(netMapDTO);
						}
						return lstRetorno;
					}
				};

				messageHandling.start();
				messageHandling.join();
			} else {
				document.alert(UtilI18N.internacionaliza(request, "inventario.validacao.atributoNoPesquisa"));
				document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Verifica Qual Ip nao foi feito o inventario e qual fez
	 *
	 * @param lstNetMapDtoErro
	 *            (List NetMapDTO)
	 * @param netMapDtoInventariar
	 *            (List NetMapDTO)
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private List<NetMapDTO> tabelaResultado(List<NetMapDTO> lstNetMapDtoErro, List<NetMapDTO> netMapDtoInventariar) throws Exception {
		List<NetMapDTO> lstRetorno = new ArrayList<NetMapDTO>();
		try {
			for (NetMapDTO netMapDTO : netMapDtoInventariar) {
				if (lstNetMapDtoErro != null && lstNetMapDtoErro.size() > 0) {
					for (NetMapDTO netMapDTOErro : lstNetMapDtoErro) {
						if (netMapDTO.getIp().equals(netMapDTOErro.getIp())) {
							String ipCor = netMapDTO.getIp();
							netMapDTO.setCorRetorno("<span style='color: red'>" + ipCor + "</span>");
							break;
						} else {
							String ipCor = netMapDTO.getIp();
							String icNovo = "";
							if(netMapDTO.getIcNovo() == null && netMapDTOErro.getIcNovo() != null){
								netMapDTO.setIcNovo(netMapDTOErro.getIcNovo());
							}
							if (netMapDTO.getIcNovo().equalsIgnoreCase("true")) {
								icNovo = "";
							}
							netMapDTO.setCorRetorno("<span style='color: green'>" + ipCor + "</span>" + icNovo);
							netMapDTO.setAtivo("");
						}
					}
				} else {
					String ipCor = netMapDTO.getIp();
					String icNovo = "";
					if (netMapDTO.getIcNovo().equalsIgnoreCase("true")) {
						icNovo = "";
					}
					netMapDTO.setCorRetorno("<span style='color: green'>" + ipCor + "</span>" + icNovo);
					netMapDTO.setAtivo("");
				}
				lstRetorno.add(netMapDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstRetorno;
	}

	/**
	 * Preenche tabela na POPUP_RESULTADO_INVENTARIO
	 *
	 * @param listnetMap
	 * @param document
	 * @throws Exception
	 */
	public void getPreencheTabelaResultado(List<NetMapDTO> listnetMap, DocumentHTML document) throws Exception {
		StringBuilder dados = new StringBuilder();
		dados.append("<table class='table' id='tblipResultado' >");
		try {
			dados.append("<tr>");
			dados.append("<td colspan = 10 style=' width: 590px; border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
			dados.append("</td></tr>");
			int cont = 0;
			String trAbre = "<tr>";
			String trFecha = "</tr>";
			if (listnetMap != null) {
				for (NetMapDTO netMapDTO : listnetMap) {
					if (cont == 0) {
						dados.append(trAbre);
						dados.append("<td> ");
						dados.append("<label>" + netMapDTO.getCorRetorno());
						dados.append("</label></td>");
						cont++;

					} else if (cont == 5) {
						dados.append("<td>");
						dados.append("<label>" + netMapDTO.getCorRetorno());
						dados.append("</label></td>" + trFecha);
						cont = 0;
					} else {
						dados.append("<td> ");
						dados.append("<label>" + netMapDTO.getCorRetorno());
						dados.append("</label></td>");
					}
					cont++;
				}
			}
			dados.append("</table>");
			HTMLElement htmlele = document.getElementById("resultado");
			htmlele.setInnerHTML(dados.toString());
			document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
			document.executeScript("$('#POPUP_RESULTADO_INVENTARIO').dialog('open');");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void listaServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		InventarioDTO inventarioDTO = (InventarioDTO) document.getBean();
		HTMLSelect idServico = (HTMLSelect) document.getSelectById("idServico");
		idServico.removeAllOptions();
		Collection col = servicoService.findByIdTipoDemandaAndIdContrato(3, inventarioDTO.getIdContrato(), null);
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

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return InventarioDTO.class;
	}

	/**
	 * Rotina que gera inventario manual
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({"unused"})
	public void InventarioAutomatico(String ip) throws Exception {

		NetMapDTO netMapDto = new NetMapDTO();
		List<NetMapDTO> listNetMap = new ArrayList<NetMapDTO>();
		netMapDto.setIp(ip);
		String[] ipMac = null;
		listNetMap.add(netMapDto);

		List<NetMapDTO> lstErro = new Servidor().carregarListaIP(listNetMap);
	}
}