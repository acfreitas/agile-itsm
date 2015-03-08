package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.InventarioDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.comm.server.NetDiscover;
import br.com.centralit.citcorpore.comm.server.Servidor;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;


/**
 * @author junior
 * 
 */
public class InventarioNew extends Inventario {

	private NetMapDTO netMapDto;
	private DocumentHTML documentAux;
	private HttpServletRequest requestAux;
	private static final String VALUE = "HARDWARE";	
	private static final List<String> VALUES_ATRIBUTOS = new ArrayList<String>(Arrays.asList(
		     new String[]  {"NAME", "WORKGROUP", "USERDOMAIN", "OSNAME", "OSVERSION", "OSCOMMENTS", "ARCH", "PROCESSORT", "PROCESSORS", 
		 			"PROCESSORN",	"MEMORY", "SWAP", "IPADDR", "ETIME", "LASTDATE", "USERID", "LASTLOGGEDUSER", "TYPE", "DESCRIPTION", "WINCOMPANY", "WINOWNER", "WINPRODID", "WINPRODKEY",
		 			"UUID", "VMSYSTEM", "CHECKSUM"}
	));
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}

	/**
	 * Preencher tabela IP Formulario Inventario
	 * 
	 * @param listnetMap
	 *            List NetMapDto
	 * @param document
	 * @throws Exception
	 */
	public void getPreencheTabelaIP(List<NetMapDTO> listnetMap, DocumentHTML document, HttpServletRequest request) throws Exception {
		StringBuilder dados = new StringBuilder();
		try {
			dados.append("<table class='table' width='100%' >");
			dados.append("<tr>");
			dados.append("<td colspan = 10 style=' width: 100%; border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
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
						dados.append("<label><input  type='radio' name='ip' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + corRetorno.replaceAll("null", "--"));
						dados.append( netMapDTO.getNome() + "</label></td>");
						cont++;

					} else if (cont == 5) {
						dados.append("<td>");
						dados.append("<label><input  type='radio' name='ip' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'" + netMapDTO.getAtivo() + " /> " + corRetorno.replaceAll("null", "--"));
						dados.append(netMapDTO.getNome() + "</label></td>" + trFecha);
						cont = 0;
					} else {
						dados.append("<td> ");
						dados.append("<label><input  type='radio' name='ip' value = '" + netMapDTO.getIp() + "_" + netMapDTO.getMac() + "'  " + netMapDTO.getAtivo() + " /> " + corRetorno.replaceAll("null", "--"));
						dados.append(netMapDTO.getNome() + "</label></td>");
					}
					cont++;
				}
			}
			dados.append("</table>");

			dados.append("<table class='table' width='100%' >");
			dados.append("<tr>");
			dados.append("<td>");
			dados.append("<label class='quantIP'>"+UtilI18N.internacionaliza(request, "inventario.qtdItensConfiguracao")+"</label>");
			dados.append("<label style='margin-left: 40px'>");

			if (listnetMap != null && listnetMap.size() > 0) {
				dados.append(listnetMap.size());
			} else {
				dados.append(0);
			}
			dados.append("</label>");
			dados.append("</td></tr>");
			dados.append("<tr>");
			dados.append("<td colspan = 10 style=' border: 1px solid #ddd;padding: 4px 10px; border-left: none; border-right: none; background: #eee; ' > ");
			dados.append("<button type='button' name='btnGravar' class='light' onclick='selecionaItemConfiguracao();'>");
			dados.append("<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/icons/small/grey/pencil.png'>");
			dados.append("<span>"+UtilI18N.internacionaliza(request, "inventario.selecionarItemConfiguracao")+"</span>");
			dados.append("</button>");
			dados.append("</td></tr>");
			dados.append("</table>");
			
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
	@Override	
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

	public void selecionaItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InventarioDTO inventarioDTO = (InventarioDTO) document.getBean();
		NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
		this.documentAux = document; 
		this.netMapDto = new NetMapDTO();
		this.requestAux = request;
		String[] ipMac = null;
		if (inventarioDTO.getIp() != null && !inventarioDTO.getIp().equals("")) {
			netMapDto = new NetMapDTO();
			ipMac = inventarioDTO.getIp().split("_");
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

			String atributos = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.Atributo, " ");
			String noPesquisa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NoPesquisa, " ");
			
			String[] arr = (String[]) (atributos == null ? "" : atributos.trim().split(","));

			if (VALUE.equalsIgnoreCase((noPesquisa == null ? "" : noPesquisa.trim())) && VALUES_ATRIBUTOS.containsAll(Arrays.asList(arr))) {
				new Servidor().carregarIP(netMapDto);
				
				Thread messageHandling = new Thread() {
					public void run() {
						try {
							this.restoreItem(netMapDto , documentAux, requestAux);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					private void restoreItem(NetMapDTO netMapDto, DocumentHTML document, HttpServletRequest requestAux) throws Exception {
						ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null) ;
						ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
						itemConfiguracaoDTO.setIdentificacao(netMapDto.getIp());
						itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.findByIdentificacaoItemConfiguracao(itemConfiguracaoDTO);
						document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
						if(itemConfiguracaoDTO != null && itemConfiguracaoDTO.getIdItemConfiguracao() != null){
							document.executeScript("parent.LOOKUP_ITEMCONFIGURACAO_select("+itemConfiguracaoDTO.getIdItemConfiguracao()+","+"'')");
							document.executeScript("parent.fechaPopupAtivos()");
						} else{
							document.alert(UtilI18N.internacionaliza(requestAux, "itemConfiguracao.identificacaoNaoEncontrada"));
						}
					}
				};

				messageHandling.start();
				messageHandling.join();
				document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
			} else {
				document.alert(UtilI18N.internacionaliza(request, "inventario.validacao.atributoNoPesquisa"));
				document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
			}
		}else{
			document.alert(UtilI18N.internacionaliza(request, "itemConfiguracao.opcao"));
			document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
		}
	}
}