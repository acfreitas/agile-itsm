package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.InventarioDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.comm.server.NetDiscover;
import br.com.centralit.citcorpore.comm.server.Servidor;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author Maycon.Fernandes
 *
 */
public class Inventario extends AjaxFormAction {

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
			NetDiscover netDiscover = new NetDiscover();
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
			this.getPreencheTabelaIP(listNetMapDto, document,request);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
	public void getPreencheTabelaIP(List<NetMapDTO> listnetMap, DocumentHTML document, HttpServletRequest request ) throws Exception {
		StringBuilder dados = new StringBuilder();
		try {
			dados.append("<table class='table' id='tblip' >");
			dados.append("<tr>");
			dados.append("<td colspan = 10 style=' width: 100%; border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
			dados.append("<label class='marcaTodos'><input  type='checkbox' id='marcatodos' name='marcatodos' onclick='marcarTodosCheckbox();'>"+UtilI18N.internacionaliza(request, "justificacaoFalhas.marcarTodos")+"</label>");
			dados.append("<label class='persquisaNet'> <button  type='button' class='light' id='pesquisarNetMap' name='pesquisarNetMap' onclick='netMapManual();'>"+UtilI18N.internacionaliza(request, "inventario.pesquisarIpNaRede")+"</button> </label>");
			dados.append("</td></tr>");
			int cont = 0;
			String trAbre = "<tr>";
			String trFecha = "</tr>";
			if (listnetMap != null) {
				for (NetMapDTO netMapDTO : listnetMap) {
					String corRetorno = netMapDTO.getCorRetorno();
					if (corRetorno == null)
						corRetorno = "";
					if (cont == 0) {
						dados.append(trAbre);
						dados.append("<td> ");
						dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + corRetorno.replaceAll("null", "--"));
						dados.append("</label></td>");
						cont++;

					} else if (cont == 5) {
						dados.append("<td>");
						dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'" + netMapDTO.getAtivo() + " /> " + corRetorno.replaceAll("null", "--"));
						dados.append("</label></td>" + trFecha);
						cont = 0;
					} else {
						dados.append("<td> ");
						dados.append("<label><input  type='checkbox' name='ips' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + corRetorno.replaceAll("null", "--"));
						dados.append("</label></td>");
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
		NetDiscover netDiscover = new NetDiscover();

		// Pesquisa Ips Ativos na Rede
		List<NetMapDTO> lstAtivos = null;
		List<NetMapDTO> lstPesquisa = null;
		try {
			lstAtivos = netDiscover.listaIpsAtivos();
			lstPesquisa = getPesquisaListIp();

			if((lstAtivos != null && !lstAtivos.isEmpty()) || (lstPesquisa != null && !lstPesquisa.isEmpty() )){
				this.getPreencheTabelaIP(this.verificarIpInventarioAtivo(lstPesquisa, lstAtivos),document,request);
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
						if (lstNetMap != null && lstNetMap.size() == 0) {
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
						if (netMapDto != null && lstNetMap != null && lstNetMap.size() == 0) {
							netMapDto.setIcNovo("false");
						} else if(netMapDto != null) {
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