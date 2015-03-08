package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery;
import br.com.centralit.citcorpore.batch.MonitoraDiscoveryIP;
import br.com.centralit.citcorpore.batch.ThreadProcessaInventario;
import br.com.centralit.citcorpore.batch.ThreadValidaFaixaIP;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.comm.server.IPAddress;
import br.com.centralit.citcorpore.comm.server.Subnet;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class MostraStatusInventario extends AjaxFormAction {
	private String strTable = "";
	private String ipsAtivos = "";
	private String todosIps = "";
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		mostraInfo(document, request, response);
		geraListaIps(document, request, response, false);
	}
	public void mostraInfo(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if (MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO.equals("")) {
			MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.processamentoInventarioDesativado";
		}

		 String[] array = new String[3];
		 array = MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO.split("#");
			 String MensagemParatela = "";
			 for (String string : array) {
				 String mensagem = UtilI18N.internacionaliza(request, string);
				 if(!mensagem.equals("")){
					 MensagemParatela += mensagem+" ";
				 }

			}
//		String divInfo = "" + UtilI18N.internacionaliza(request, array[0]) + "";
		String divInfo = "" + MensagemParatela + "";
		document.getElementById("divInfo2").setInnerHTML("<b>" + divInfo + "</b>");
	}
	public void refreshIPs(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		geraListaIps(document, request, response, true);
	}
	public void geraListaIps(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response, boolean geraEventoRefresh) throws Exception {
		List<NetMapDTO> listNetMapDto = new ArrayList<NetMapDTO>();
		if(MonitoraDiscoveryIP.lstAddressDiscovery != null){
			synchronized (MonitoraDiscoveryIP.lstAddressDiscovery) {
				listNetMapDto.addAll(MonitoraDiscoveryIP.lstAddressDiscovery);
			}
		}
		if (listNetMapDto != null) {
			String table = "<table width='100%'>";
			table += "<tr><td colspan='4'><b>"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.ipsDescobertosAteoMomento")+"</b></td></tr>";
			if (!CITCorporeUtil.START_MODE_INVENTORY){
				//document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.parametroAtivacaoInventarioNaoAtivado"));
				table += "<tr><td colspan='4'>"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.oProcessoDeInvetarioEstaDesativado")+"</td></tr>";
			}
			for (NetMapDTO netMapDTO : listNetMapDto) {
				String nameOrIp = "";
				if (netMapDTO.getNome() != null) {
					if (netMapDTO.getNome().indexOf("(") > -1){
						netMapDTO.setNome(netMapDTO.getNome().substring(0,netMapDTO.getNome().indexOf("(") -1));
					}
					netMapDTO.setNome(netMapDTO.getNome().trim());
					nameOrIp = netMapDTO.getNome();
				} else {
					nameOrIp = netMapDTO.getIp();
				}
				String td = "<td>";
				if (netMapDTO.isNovoIC()){
					td += UtilI18N.internacionaliza(request, "mostrarStatusInventario.novoIc");
				}else{
					td += "&nbsp;";
				}
				td += "</td>";
				String dataInv = "";
				if (netMapDTO.getDataInventario() != null) {
					dataInv = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, netMapDTO.getDataInventario(), WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(netMapDTO.getDataInventario());
				}
				table += "<tr>";

				String dataHoraControle = "";
				if (!netMapDTO.okTimeToProcess()){
					dataHoraControle = UtilI18N.internacionaliza(request, "mostrarStatusInventario.proximaVerificacao") + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, netMapDTO.getDateTimeControlProcessInv(), WebUtil.getLanguage(request));
				}
				if (netMapDTO.getStatusPing() == null || netMapDTO.getStatusPing().equalsIgnoreCase(NetMapDTO.INDEFINIDO)){
					table += "<td onclick='fazPing(\"" + nameOrIp + "\")' style='border:1px solid black; cursor:pointer' title='"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.clickAquiParaAtaulizarStatus")+"'><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolacinza2.png'/></td>";
				}else if (netMapDTO.getStatusPing() != null && netMapDTO.getStatusPing().equalsIgnoreCase(NetMapDTO.ATIVO)){
					table += "<td onclick='fazPing(\"" + nameOrIp + "\")' style='border:1px solid black; cursor:pointer' title='"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.clickAquiParaAtaulizarStatus")+"'><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png'/></td>";
				}else if (netMapDTO.getStatusPing() != null && netMapDTO.getStatusPing().equalsIgnoreCase(NetMapDTO.INATIVO)){
					table += "<td onclick='fazPing(\"" + nameOrIp + "\")' style='border:1px solid black; cursor:pointer' title='"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.clickAquiParaAtaulizarStatus")+"'><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png'/></td>";
				}
				table += "<td style='border:1px solid black'>" + nameOrIp + "</td><td style='border:1px solid black'><button type='button' onclick='inventarioAgora(\"" + nameOrIp + "\")'>"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.invetarioAgora")+"</button>" + dataHoraControle + "</td><td style='border:1px solid black'>" + dataInv + "</td>" + td + "</tr>";
			}
			table += "</table>";
			MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO_COMPL = table;
		}
		if (geraEventoRefresh){
			document.executeScript("refresh()");
		}
	}
	public void submeteIP(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (MonitoraDiscoveryIP.lstAddressDiscovery == null){
			document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.oProcessoDeinventarioEstaDesaticadoNestaInstacia"));
			return;
		}
		if (!CITCorporeUtil.START_MODE_INVENTORY){
			//document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.parametroAtivacaoInventarioNaoAtivado"));
			document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.invetarioAgora"));
			return;
		}
		NetMapDTO netMapDTO = (NetMapDTO)document.getBean();
		if (netMapDTO != null){
			if (netMapDTO.getIp() != null){
				String ips = netMapDTO.getIp().trim();
				ips = ips + ";";
				ips = ips.replaceAll(",", ";");
				ips = ips.replaceAll("\n", ";");
				ips = ips.replaceAll("\r", ";");
				String[] ipsArray = ips.split(";");
				if (ipsArray != null){
					for (int i = 0; i < ipsArray.length; i++){
						if (ipsArray[i] != null && !ipsArray[i].trim().equalsIgnoreCase("")){
			        		synchronized (MonitoraDiscoveryIP.lstAddressDiscovery) {
			        			NetMapDTO netMapAux = new NetMapDTO();
			        			netMapAux.setIp(ipsArray[i].trim());
			        			netMapAux.setNovoIC(true);
			        			boolean atualizado = false;
			        			if (MonitoraDiscoveryIP.hsmAddressDiscovery.containsKey(ipsArray[i].trim())){
			        				try{
			        					for (int y = 0; y < MonitoraDiscoveryIP.lstAddressDiscovery.size(); y++){
			        						NetMapDTO netMapAux2 = (NetMapDTO)MonitoraDiscoveryIP.lstAddressDiscovery.get(y);
			        						if (netMapAux2 != null && netMapAux2.getIp().trim().equalsIgnoreCase(ipsArray[i].trim())){
			        							netMapAux2.setForce(true);
			        							continue;
			        						}
			        					}
			        					atualizado = true;
			        				}catch(Exception e){}
			        			}
			        			if (atualizado){
			        				continue;
			        			}
			        			netMapAux.setForce(true);
			        			MonitoraDiscoveryIP.hsmAddressDiscovery.put(ipsArray[i].trim(), ipsArray[i].trim());
			        			MonitoraDiscoveryIP.lstAddressDiscovery.add(netMapAux);
			        		}
						}
					}
				}
			}
		}
		MonitoraAtivosDiscovery.recomeca = true;
		document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.ipshostForamAcrescentados"));
	}
	public void forcarLacoInv(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (MonitoraDiscoveryIP.lstAddressDiscovery == null){
			document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.parametroAtivacaoInventarioNaoAtivado"));
			return;
		}
		MonitoraAtivosDiscovery.iniciouDiscovery = false;
		MonitoraAtivosDiscovery.recomeca = true;
		//document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.inventarioReiniciado"));
		document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.informacoesResetadasELacoforcadoparaInicio"));
	}
	public void inventarioAgora(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (MonitoraDiscoveryIP.lstAddressDiscovery == null){
			document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.parametroAtivacaoInventarioNaoAtivado"));
			return;
		}
		NetMapDTO netMapDTO = (NetMapDTO)document.getBean();
		if (netMapDTO.getIp() != null && !netMapDTO.getIp().trim().equalsIgnoreCase("")){
			List<NetMapDTO> listNetMapDto = new ArrayList<NetMapDTO>();
			synchronized (MonitoraDiscoveryIP.lstAddressDiscovery) {
				listNetMapDto.addAll(MonitoraDiscoveryIP.lstAddressDiscovery);
			}
			if (listNetMapDto != null) {
				for (NetMapDTO netMapAux : listNetMapDto) {
					String nameOrIp = "";
					if (netMapAux.getIp() != null) {
						nameOrIp = netMapAux.getIp();
					} else {
						if (netMapAux.getNome().indexOf("(") > -1){
							netMapAux.setNome(netMapAux.getNome().substring(0,netMapAux.getNome().indexOf("(") -1));
						}
						netMapAux.setNome(netMapAux.getNome().trim());
						nameOrIp = netMapAux.getNome();
					}
					if (nameOrIp.equalsIgnoreCase(netMapDTO.getIp())){
						if (netMapAux.okTimeToProcess()){
							IPAddress ipAddr = new IPAddress(netMapAux.getIp());
							if (ipAddr.ping()){
								ThreadProcessaInventario t = new ThreadProcessaInventario();
								netMapAux.setDateTimeControlProcessInv(UtilDatas.somaSegundos(UtilDatas.getDataHoraAtual(), 300));  //Adiciona 5 minutos de controle.
								t.setNetMapDTO(netMapAux); //Faz isso pra pegar a mesma referencia de objeto pra atualizar a lista.
								t.start();
							}else{
								document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.esteHostNaoEstaAtivo"));
								return;
							}
						}else{
							document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.esteAtivoJaFoiSubmetidoaInventario"));
							return;
						}
					}
				}
			}
		}
		document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.inventarioThreadIniciada"));
	}
	public void fazPing(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (MonitoraDiscoveryIP.lstAddressDiscovery == null){
			document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.parametroAtivacaoInventarioNaoAtivado"));
			return;
		}
		NetMapDTO netMapDTO = (NetMapDTO)document.getBean();
		if (netMapDTO.getIp() != null && !netMapDTO.getIp().trim().equalsIgnoreCase("")){
			List<NetMapDTO> listNetMapDto = new ArrayList<NetMapDTO>();
			synchronized (MonitoraDiscoveryIP.lstAddressDiscovery) {
				listNetMapDto.addAll(MonitoraDiscoveryIP.lstAddressDiscovery);
			}
			if (listNetMapDto != null) {
				for (NetMapDTO netMapAux : listNetMapDto) {
					String nameOrIp = "";
					if (netMapAux.getIp() != null) {
						nameOrIp = netMapAux.getIp();
					} else {
						if (netMapAux.getNome().indexOf("(") > -1){
							netMapAux.setNome(netMapAux.getNome().substring(0,netMapAux.getNome().indexOf("(") -1));
						}
						netMapAux.setNome(netMapAux.getNome().trim());
						nameOrIp = netMapAux.getNome();
					}
					if (nameOrIp.equalsIgnoreCase(netMapDTO.getIp())){
						IPAddress ipAddr = new IPAddress(netMapDTO.getIp());
						if (ipAddr.ping()){
							netMapAux.setStatusPing(NetMapDTO.ATIVO);
			    document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.oHost") + " '" + nameOrIp + "' " + UtilI18N.internacionaliza(request, "mostrarStatusInventario.estaAtivo"));
						}else{
							netMapAux.setStatusPing(NetMapDTO.INATIVO);
			    document.alert(UtilI18N.internacionaliza(request, "mostrarStatusInventario.oHost") + " '" + nameOrIp + "' " + UtilI18N.internacionaliza(request, "mostrarStatusInventario.estaInativo"));
						}
					}
				}
			}
		}
		document.getTextAreaById("ip").setValue("");
		document.executeScript("refreshIPs()");
	}
	public void gerarFaixaIPs(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NetMapDTO netMapDTO = (NetMapDTO)document.getBean();
		String faixas = netMapDTO.getIpFaixaGerar();
		int numThreads = 10;
		if (faixas == null){
			faixas = "";
		}
		String validaIP = netMapDTO.getValidarIP();
		if (validaIP == null){
			validaIP = "";
		}
		String nativePing = netMapDTO.getNativePing();
		if (nativePing == null){
			nativePing = "";
		}
		if (netMapDTO.getNumThreads() != null && netMapDTO.getNumThreads().intValue() > 0){
			numThreads = netMapDTO.getNumThreads().intValue();
		}
		if (numThreads > 80){
			numThreads = 80; //Limita em 80 Threads...
		}
		strTable = "<table>";
		faixas = faixas.replaceAll(",", ";");
		faixas = faixas.replaceAll("\n", ";");
		faixas = faixas.replaceAll("\r", ";");
		faixas = faixas + ";";
		String[] strFaixas = faixas.split(";");
		int qtde = 0;
		for (int i = 0; i < strFaixas.length; i++){
			if (strFaixas[i] != null && !strFaixas[i].trim().equalsIgnoreCase("")){
				String strIps = strFaixas[i] + "- ";
				String[] ips = strIps.split("-");
				if (ips != null){
					if (ips.length > 1){
						if (ips[0] == null && ips[1] == null){
							continue;
						}
						if (ips[0].trim().equalsIgnoreCase("") && ips[1].trim().equalsIgnoreCase("")){
							continue;
						}
						IPAddress ip1 = null;
						IPAddress ip2 = null;
						try{
							if (ips[1] == null || ips[1].trim().equalsIgnoreCase("")){
								//O formato CIDR é 10.0.0.1/15 ou 192.168.1.255/24
								Subnet subNet = new Subnet(ips[0].trim());
								String address1 = subNet.getInfo().getLowAddress();
								String address2 = subNet.getInfo().getHighAddress();

								ip1 = new IPAddress(address1);
								ip2 = new IPAddress(address2);
							}else{
								ip1 = new IPAddress(ips[0]);
						        ip2 = new IPAddress(ips[1]);
							}
							ExecutorService exService = null;
							if (validaIP.trim().equalsIgnoreCase("S")){
								exService = Executors.newFixedThreadPool(numThreads);
							}
							IPAddress ipAux = new IPAddress(ip1.getValue());
					        do {
			    				if (todosIps != null && !todosIps.trim().equalsIgnoreCase("")){
			    					todosIps = todosIps + ";";
			    				}
			    				todosIps = todosIps + ipAux.toString();
					        	if (validaIP.trim().equalsIgnoreCase("S")){
						        	qtde++;
						        	if (qtde > numThreads){
						        		qtde = 0;
						        		try {
											Thread.sleep(5000);
										} catch (InterruptedException e) {
										}
						        	}
					        	}else{
					        		strTable += "<tr><td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolacinza2.png'/></td><td>" + ipAux.toString() + "</td></tr>";
					        	}
					        	ipAux = ipAux.next();
			    }
			    while (ipAux.getValue() <= ip2.getValue());
				    		if (validaIP.trim().equalsIgnoreCase("S")){
					    		try {
									Thread.sleep(20000);
								} catch (InterruptedException e) {
								}
					        	try {
									exService.awaitTermination(30, TimeUnit.SECONDS);
								} catch (InterruptedException e1) {
								}
					        	try{
					        		exService.shutdown();
					        	}catch(Exception e){
					        	}
				    		}
				        	exService = null;
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
		if (validaIP.trim().equalsIgnoreCase("S") && ipsAtivos != null){
			strTable += "<tr><td colspan='2'><b>"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.ipsAtivos")+"</b> " + ipsAtivos.replaceAll(";", "<br>") + "</td></tr>";
			strTable += "<tr><td colspan='2'><div style='display:none'><textarea name='txtIPSAtivos'>" + ipsAtivos + "</textarea></div></td></tr>";
			strTable += "<tr><td>&nbsp;<td><button type='button' onclick='adicionarIPSAtivosLista()'>"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.adicionarIpsAtivosnaListaParaIventario")+"</button></td></tr>";
		}
		strTable += "<tr><td colspan='2'><div style='display:none'><textarea name='txtIPSTodos'>" + todosIps + "</textarea></div></td></tr>";
		strTable += "<tr><td>&nbsp;<td><button type='button' onclick='adicionarTodosIPSLista()'>"+UtilI18N.internacionaliza(request, "mostrarStatusInventario.adicionarTodosIpsnaListaparaInventario")+"</button></td></tr>";
		strTable += "</table>";
		document.getElementById("divResultado").setInnerHTML(strTable);
	}
	public void alteraValor(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NetMapDTO netMapDTO = (NetMapDTO)document.getBean();
		if (netMapDTO.getTipoDado() == null){
			return;
		}
		if (netMapDTO.getTipoDado().equalsIgnoreCase("")){
			return;
		}
		if (netMapDTO.getValor() == null){
			return;
		}
		netMapDTO.setValor(UtilStrings.apenasNumeros(netMapDTO.getValor()));
		if (netMapDTO.getValor().equalsIgnoreCase("")){
			return;
		}
		int num = 0;
		try{
			num = Integer.parseInt(netMapDTO.getValor());
			if (num > 0){
				if (netMapDTO.getTipoDado().equalsIgnoreCase("DIS")){
					ThreadValidaFaixaIP.NUMERO_THREADS = num;
				}
				if (netMapDTO.getTipoDado().equalsIgnoreCase("INV")){
					MonitoraAtivosDiscovery.NUMERO_THREADS = num;
				}
				if (netMapDTO.getTipoDado().equalsIgnoreCase("PING")){
					IPAddress.PING_TIMEOUT = num;
				}
			}
			document.alert(UtilI18N.internacionaliza(request, "mostraStatusIventario.valorAlteradoEsseValorEValidoSomente"));
			forcarLacoInv(document, request, response);
			document.executeScript("refresh()");
		}catch(Exception e){
		}
	}
	class RunnableThread implements Runnable {
		private IPAddress ipAux = null;
		private String validaIP = "";
		private String nativePing = "";
		public RunnableThread(IPAddress ipParm, String validaIPParm, String nativePingParm) {
			ipAux = ipParm;
			validaIP = validaIPParm;
			nativePing = nativePingParm;
		}
        @Override
        public void run() {
        	if (ipAux != null){
	        	if (validaIP.trim().equalsIgnoreCase("S")){
	        		boolean ping = ipAux.ping();
	        		if (!ping){
	        			if (nativePing.trim().equalsIgnoreCase("S")){
	        				ping = ipAux.nativePing();
	        			}
	        		}
	        		if (ping){
	        			synchronized (strTable) {
	        				strTable += "<tr><td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png'/></td><td>" + ipAux.toString() + "</td></tr>";
						}
	        			synchronized (ipsAtivos) {
	        				if (ipsAtivos != null && !ipsAtivos.trim().equalsIgnoreCase("")){
	        					ipsAtivos = ipsAtivos + ";";
	        				}
	        				ipsAtivos = ipsAtivos + ipAux.toString();
	        			}
	        		}else{
	        			synchronized (strTable) {
	        				strTable += "<tr><td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png'/></td><td>" + ipAux.toString() + "</td></tr>";
	        			}
	        		}
	        	}else{
	        		synchronized (strTable) {
	        			strTable += "<tr><td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolacinza2.png'/></td><td>" + ipAux.toString() + "</td></tr>";
	        		}
	        	}
        	}
        }
    }
	@Override
	public Class getBeanClass() {
		return NetMapDTO.class;
	}
	public String getStrTable() {
		return strTable;
	}
	public void setStrTable(String strTable) {
		this.strTable = strTable;
	}

}
