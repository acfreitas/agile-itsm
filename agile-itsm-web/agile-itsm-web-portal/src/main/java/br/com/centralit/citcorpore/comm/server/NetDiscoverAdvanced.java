package br.com.centralit.citcorpore.comm.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * @author Maycon.Fernandes
 *
 */
public class NetDiscoverAdvanced implements Runnable {

	NetMapDTO netMapDto = new NetMapDTO();
	private List<NetMapDTO> listNetMap = new ArrayList<NetMapDTO>();

	public static void main(String[] args) throws IOException {
		NetDiscoverAdvanced netDiscoverAdvanced = new NetDiscoverAdvanced();
		for (int i = 1; i < 255; i++){
			System.out.println("Endereço 10.0.0." + i + "  >>> " + netDiscoverAdvanced.pingOK("10.0.0." + i));
		}
		//netDiscoverAdvanced.pingOK("10.0.0.1");
		//new Thread(new NetDiscoverAdvanced()).start();
	}

	public List<NetMapDTO> listaIpsAtivos() throws ServiceException, Exception, LogicException {
		List<NetMapDTO> lstIpsAtivos = getListNetMap();
		lstIpsAtivos = this.gravarDados(lstIpsAtivos);
		return lstIpsAtivos;
	}

	/**
	 * @return valor do atributo listNetMapAux.
	 * @throws Exception
	 * @throws ServiceException
	 */
	public List<NetMapDTO> getListNetMap() throws ServiceException, LogicException,  Exception {
		Integer i = 0;
		List<NetMapDTO> listaRetorno = new ArrayList<NetMapDTO>();
		List<String> listaVmapFiles = new ArrayList<String>();
		File arqv;// = new File(this.getParametroCaminhoArquivoNmap() + "nmapFile" + i);
		//if (arqv.exists())
		//	arqv.delete();
		try {
			// boolean bFile = true;
			Process exec = null;
			String caminhoNetMap = (this.getParametroCaminhoArquivoNmap() == null ? "" : this.getParametroCaminhoArquivoNmap().trim());
			if (new File(caminhoNetMap).isDirectory()) {
				caminhoNetMap += File.separator;
				String [] arrayFaixaIp = {};
				arrayFaixaIp = this.getParametroFaixaIPNmap();
				for(String faixaIp : arrayFaixaIp){
					if (faixaIp.startsWith("file:")){
						listaVmapFiles.add(faixaIp.substring(faixaIp.indexOf("file:") + 5));
					}else{
						arqv = new File(caminhoNetMap + "nmapFile" + i);
						if (arqv.exists())
							arqv.delete();
						try {
							System.out.println("Realizando scan na rede ..." + faixaIp.trim());
							exec = Runtime.getRuntime().exec(new String[] { this.getParametroCaminhoNmap(), "-sP", faixaIp.trim(), "-oN", caminhoNetMap + "nmapFile" + i });
							new PrintStream( exec.getInputStream() ).start();
							exec.waitFor();
							Thread.sleep(100);
							listaVmapFiles.add(caminhoNetMap + "nmapFile" + i);
							i++;
						}catch (IOException er) {
							throw new LogicException("Parâmentro de Configuração do Caminho nmap inválido");
						}
					}
				}
				Thread.sleep(2000); //Aguarda pra dar um tempo dos arquivos ficarem prontos.
				// Pega Arquivo Gerado pelo nmap
				for(String nmapFile : listaVmapFiles){
					arqv = new File(nmapFile);
					if (arqv.exists()) {
						boolean flag = true;
						arqv = new File(nmapFile);
						Scanner arq = new Scanner(arqv);
						while (flag) {
							String linha = arq.nextLine();
							// Flag para identificar fim do arquivo ser encontrar
							// retorna false
							if (linha.substring(0, 11).equalsIgnoreCase("# Nmap done"))
								flag = false;
							if (linha.indexOf("#") > -1){ //Existem alguns nmap que dao a mensagem: # Nmap run completed at ...
								if (linha.indexOf("completed") > -1){
									flag = false;
								}
							}
							if (!arq.hasNext()) {
								arq.close();
								arq = null;
								Thread.sleep(1000);
								arqv = new File(nmapFile);
								arq = new Scanner(arqv);
							}
						}
						arq.close();
						// Chama metodo que irá fazer leitura de arquivo nmap
						// Pegar lista Preenchida pelo metodo netDiscover
						listaRetorno.addAll(readFile(arqv));
					}
				}
			} else {
				throw new LogicException("Parâmentro de Configuração de Diretório Arquivo NetMap não Encontrado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaRetorno;
	}

	class PrintStream extends Thread {
		java.io.InputStream __is = null;

		public PrintStream(java.io.InputStream is) {
			__is = is;
		}

		public void run() {
			try {
				while (this != null) {
					int _ch = __is.read();
					if (_ch != -1){
						//System.out.print((char) _ch);
					} else
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private ParametroCorporeService getServiceParametro() throws ServiceException, Exception {
		ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
		return parametroService;
	}

	/**
	 * metodo para percorre arquivo e preencher obj netmap
	 *
	 * @param file
	 *            diretorio do arquivo
	 */
	public List<NetMapDTO> readFile(File file) {
		try {
			listNetMap = new ArrayList<NetMapDTO>();
			Scanner arq = new Scanner(file);
			while (arq.hasNext()) {
				String linha = arq.nextLine();
				if (linha.length() > 0) {
					// Identifica se é o final do arquivo
					boolean fim = false;
					if (linha.substring(0, 11).equalsIgnoreCase("# Nmap done")) {
						fim = true;
					}
					if (linha.indexOf("#") > -1){
						if (linha.indexOf("completed") > -1){
							fim = true;
						}
					}
					if (!fim) {
						// Verifica se é ip
						if (linha.substring(0, 9).equalsIgnoreCase("Nmap scan")) {
								getNetMapDto();

								if (linha.length() > 38) {
									String texto = linha.toString();
									Pattern p = Pattern.compile("\\(.*\\)$");
									Matcher m = p.matcher(texto);
									String ip;
									if (m.find()) {
										ip = m.group();
										ip = ip.replace('(', ' ').replace(')', ' ').replaceAll(" ", "");
										ip = ip.trim();
										this.netMapDto.setIp(ip);
									}

								} else {
									this.netMapDto.setIp(linha.substring(21, linha.length()).replaceAll("[^0-9.]",""));
								}
								this.netMapDto.setNome(linha.substring(21, linha.length()));
								//this.netMapDto.setIcNovo("true");
								if (pingOK(this.netMapDto.getIp())){
									listNetMap.add(this.netMapDto);
								}
							// Veririca se mac
						} else if (linha.startsWith("Host")) { //para nmaps que estao no padrao de resposta: Ex.: Host 10.4.0.62 appears to be up.
							getNetMapDto();
							String aux = "";
							try{
								aux = linha.substring(linha.indexOf("Host") + 4);
								aux = aux.substring(0,aux.indexOf("appears"));
								aux = aux.trim();

								this.netMapDto.setNome(aux);
								this.netMapDto.setIp(aux.replaceAll("[^0-9.]",""));
								if (pingOK(this.netMapDto.getIp())){
									listNetMap.add(this.netMapDto);
								}
							}catch(Exception e){
							}
						} else if (linha.substring(0, 11).equalsIgnoreCase("MAC Address")) {
							this.netMapDto.setMac(linha.substring(13, 30));
//							A linha abaixo foi comentada por estar duplicando os IPs no inventario
//							listNetMap.add(this.netMapDto);
							getNetMapDto();
						}
					}
				}
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.getFreeSpace();
		return listNetMap;
	}

	public boolean pingOK(String ipAddress){
	    InetAddress inet;
		try {
			inet = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}

		boolean bRet = false;
		try {
			bRet = inet.isReachable(2000);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return bRet;
	}

	/**
	 * Grava a lista de dados obtidos do NMap. Se os dados ja estiverem gravados eles serao atualizados.
	 *
	 * @param lst
	 *            Lista de dados NMap
	 */
	private List<NetMapDTO> gravarDados(List<NetMapDTO> lst) {
		List<NetMapDTO> lstRetorno = new ArrayList<NetMapDTO>();
		try {
			ItemConfiguracaoService itemConfService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			if (lst != null) {
				for (NetMapDTO netMapDTO : lst) {

					NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
					ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
					List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDTO);
					NetMapDTO mapDTO = new NetMapDTO();
					netMapDTO.setDate(Util.getSqlDataAtual());
					mapDTO.setMac(netMapDTO.getMac());
					try{
						//getSNMPSystem(netMapDTO.getIp(), netMapDTO);
					}catch(Exception e){
					}
					// Verifica existencia, se existir faz update se nao grava
					if (lstNetMap != null && !lstNetMap.isEmpty()) {
						mapDTO = lstNetMap.get(0);
						mapDTO.setDate(Util.getSqlDataAtual());
						mapDTO.setNome(netMapDTO.getNome());
						//mapDTO.setHardware(netMapDTO.getHardware());
						//mapDTO.setSistemaoper(netMapDTO.getSistemaoper());
						//mapDTO.setUptime(netMapDTO.getUptime());
						netMapDTO.setIcNovo("false");
						netMapService.update(mapDTO);
					} else {
						netMapDTO.setIcNovo("true");
						netMapService.create(netMapDTO);
						itemConfiguracaoDto.setIdentificacao(netMapDTO.getIp());
						itemConfiguracaoDto.setDataInicio(netMapDTO.getDate());
						itemConfiguracaoDto = itemConfService.obterICFilhoPorIdentificacaoIdPaiEIdTipo(itemConfiguracaoDto);
						if (itemConfiguracaoDto.getIdItemConfiguracao() != null) {
							itemConfService.create(itemConfiguracaoDto);
					    }
					}
					lstRetorno.add(netMapDTO);

					List lstStorages = new ArrayList();
					try{
						//lstStorages = getSNMPStorages(netMapDTO.getIp(), netMapDTO);
					}catch(Exception e){
					}
					if (lstStorages != null && lstStorages.size() > 0){
						itemConfiguracaoDto.setIdentificacao(netMapDTO.getIp());
						itemConfiguracaoDto.setIdentificacaoPadrao(netMapDTO.getIp());
						itemConfiguracaoDto.setDataInicio(netMapDTO.getDate());
						itemConfiguracaoDto = itemConfService.obterICFilhoPorIdentificacaoIdPaiEIdTipo(itemConfiguracaoDto);
						if (itemConfiguracaoDto.getIdItemConfiguracao() != null) {
							itemConfiguracaoDto.setIdentificacaoPadrao(netMapDTO.getIp());
					    	List<TipoItemConfiguracaoDTO> lstTipoItemConfi = new ArrayList<TipoItemConfiguracaoDTO>();
							/*
							 * for (Iterator it = lstStorages.iterator(); it.hasNext();){ SNMPStorageDTO snmpStorageDTO = (SNMPStorageDTO)it.next(); TipoItemConfiguracaoDTO tpItemConfigbean = new
							 * TipoItemConfiguracaoDTO(); if (snmpStorageDTO.getTipo().equalsIgnoreCase(SNMPStorageDTO.FISICA)){ tpItemConfigbean.setTag("MEMORIES");
							 * tpItemConfigbean.setNome("MEMORIES");
							 *
							 * List<CaracteristicaDTO> lstCaracteristica = new ArrayList<CaracteristicaDTO>(); CaracteristicaDTO caracteristicabean = null;
							 *
							 * caracteristicabean = new CaracteristicaDTO(); ValorDTO valorbean = new ValorDTO(); caracteristicabean.setNome("CAPTION"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("CAPTION"); valorbean.setValorStr(snmpStorageDTO.getDescricao()); caracteristicabean.setValor(valorbean);
							 * lstCaracteristica.add(caracteristicabean);
							 *
							 * caracteristicabean = new CaracteristicaDTO(); valorbean = new ValorDTO(); caracteristicabean.setNome("DESCRIPTION"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("DESCRIPTION"); valorbean.setValorStr(snmpStorageDTO.getDescricao()); caracteristicabean.setValor(valorbean);
							 * lstCaracteristica.add(caracteristicabean);
							 *
							 * caracteristicabean = new CaracteristicaDTO(); valorbean = new ValorDTO(); caracteristicabean.setNome("CAPACITY"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("CAPACITY"); valorbean.setValorStr("" + (((snmpStorageDTO.getSize() * snmpStorageDTO.getAllocationUnits()) / 1024) / 1024));
							 * caracteristicabean.setValor(valorbean); lstCaracteristica.add(caracteristicabean);
							 *
							 * caracteristicabean = new CaracteristicaDTO(); valorbean = new ValorDTO(); caracteristicabean.setNome("USED"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("USED"); valorbean.setValorStr("" + (((snmpStorageDTO.getUsed() * snmpStorageDTO.getAllocationUnits()) / 1024) / 1024));
							 * caracteristicabean.setValor(valorbean); lstCaracteristica.add(caracteristicabean);
							 *
							 * tpItemConfigbean.setCaracteristicas(lstCaracteristica); lstTipoItemConfi.add(tpItemConfigbean); }else{ tpItemConfigbean.setTag("STORAGES");
							 * tpItemConfigbean.setNome("STORAGES");
							 *
							 * List<CaracteristicaDTO> lstCaracteristica = new ArrayList<CaracteristicaDTO>(); CaracteristicaDTO caracteristicabean = null;
							 *
							 * caracteristicabean = new CaracteristicaDTO(); ValorDTO valorbean = new ValorDTO(); caracteristicabean.setNome("NAME"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("NAME"); valorbean.setValorStr(snmpStorageDTO.getDescricao()); caracteristicabean.setValor(valorbean);
							 * lstCaracteristica.add(caracteristicabean);
							 *
							 * caracteristicabean = new CaracteristicaDTO(); valorbean = new ValorDTO(); caracteristicabean.setNome("DESCRIPTION"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("DESCRIPTION"); valorbean.setValorStr(snmpStorageDTO.getDescricao()); caracteristicabean.setValor(valorbean);
							 * lstCaracteristica.add(caracteristicabean);
							 *
							 * caracteristicabean = new CaracteristicaDTO(); valorbean = new ValorDTO(); caracteristicabean.setNome("DISKSIZE"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("DISKSIZE"); valorbean.setValorStr("" + (((snmpStorageDTO.getSize() * snmpStorageDTO.getAllocationUnits()) / 1024) / 1024));
							 * caracteristicabean.setValor(valorbean); lstCaracteristica.add(caracteristicabean);
							 *
							 * caracteristicabean = new CaracteristicaDTO(); valorbean = new ValorDTO(); caracteristicabean.setNome("USED"); caracteristicabean.setTipo("A");
							 * caracteristicabean.setTag("USED"); valorbean.setValorStr("" + (((snmpStorageDTO.getUsed() * snmpStorageDTO.getAllocationUnits()) / 1024) / 1024));
							 * caracteristicabean.setValor(valorbean); lstCaracteristica.add(caracteristicabean);
							 *
							 * tpItemConfigbean.setCaracteristicas(lstCaracteristica); lstTipoItemConfi.add(tpItemConfigbean); } }
							*/
							itemConfiguracaoDto.setTipoItemConfiguracao(lstTipoItemConfi);
							List<ItemConfiguracaoDTO> list = new ArrayList<ItemConfiguracaoDTO>();
							list.add(itemConfiguracaoDto);
							ItemConfiguracaoService serviceItem = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
							for (ItemConfiguracaoDTO itemConfiguracaoDTO2 : list) {
								serviceItem.createItemConfiguracao(itemConfiguracaoDTO2, null);
							}
					    }
					}
				}
			} else {
				System.out.println("Não foi encontrado IP na rede!");
			}
		} catch (ServiceException e) {
			System.out.println("Problema ao gravar dados NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema ao gravar dados NetMap: " + e.getMessage());
			e.printStackTrace();
		}
		return lstRetorno;
	}

	/*
	 * public static String getSNMPSystem(String ipAddr, NetMapDTO netMapDTO) { String strXML = getSNMPInfo("http://10.0.1.94:3000/transaction/system/1/" + ipAddr); DocumentBuilderFactory factory =
	 * DocumentBuilderFactory.newInstance(); try { InputStream ioos = new ByteArrayInputStream(strXML.getBytes("UTF-8")); DocumentBuilder builder = factory.newDocumentBuilder(); Document doc = null;
	 * doc = builder.parse(ioos); Node noRoot = null; noRoot = doc.getChildNodes().item(0); try{ for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){ Node noLookup =
	 * noRoot.getChildNodes().item(j); if (noLookup == null || noLookup.getNodeName() == null){ continue; } if(noLookup.getNodeName().equals("#text")) continue; for(int x = 0; x <
	 * noLookup.getChildNodes().getLength(); x++){ Node noItem = noLookup.getChildNodes().item(x); if(noItem.getNodeName().equals("#text")) continue; for(int z = 0; z <
	 * noItem.getChildNodes().getLength(); z++){ Node noItemX = noItem.getChildNodes().item(z); if(noItemX.getNodeName().equals("#text")) continue; //System.out.println("NodeValue2: ===> " +
	 * noItemX.getNodeName() + ":" + noItemX.getTextContent()); if (noItemX.getNodeName().equalsIgnoreCase("descr")){ String str = noItemX.getTextContent(); int index = str.indexOf("Software:");
	 * String nomeHard = ""; String nomeSO = ""; if (index > -1){ int indexHard = str.indexOf("Hardware:"); if (indexHard > -1){ nomeHard = str.substring(indexHard + 9, index - 3); } nomeSO =
	 * str.substring(index + 9); } netMapDTO.setHardware(nomeHard.trim()); netMapDTO.setSistemaoper(nomeSO.trim()); } if (noItemX.getNodeName().equalsIgnoreCase("name")){
	 * netMapDTO.setNome(noItemX.getTextContent()); } if (noItemX.getNodeName().equalsIgnoreCase("upTime")){ netMapDTO.setUptime(noItemX.getTextContent()); } } } } }catch(Exception e){
	 * e.printStackTrace(); } } catch (ParserConfigurationException e) { e.printStackTrace(); } catch (SAXException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 *
	 * return ""; }
	 *
	 * public static List getSNMPStorages(String ipAddr, NetMapDTO netMapDTO) { String strXML = getSNMPInfo("http://10.0.1.94:3000/transaction/storage/1/" + ipAddr); DocumentBuilderFactory factory =
	 * DocumentBuilderFactory.newInstance(); List lstStorages = new ArrayList(); try { InputStream ioos = new ByteArrayInputStream(strXML.getBytes("UTF-8")); DocumentBuilder builder =
	 * factory.newDocumentBuilder(); Document doc = null; doc = builder.parse(ioos); Node noRoot = null; noRoot = doc.getChildNodes().item(0); try{ for(int j = 0; j <
	 * noRoot.getChildNodes().getLength(); j++){ Node noLookup = noRoot.getChildNodes().item(j); if (noLookup == null || noLookup.getNodeName() == null){ continue; }
	 * if(noLookup.getNodeName().equals("#text")) continue; for(int x = 0; x < noLookup.getChildNodes().getLength(); x++){ Node noItem = noLookup.getChildNodes().item(x);
	 * if(noItem.getNodeName().equals("#text")) continue; SNMPStorageDTO snmpStorageDTO = new SNMPStorageDTO(); snmpStorageDTO.setAllocationUnits(0); for(int z = 0; z <
	 * noItem.getChildNodes().getLength(); z++){ Node noItemX = noItem.getChildNodes().item(z); if(noItemX.getNodeName().equals("#text")) continue; //System.out.println("NodeValue2: ===> " +
	 * noItemX.getNodeName() + ":" + noItemX.getTextContent()); if (noItemX.getNodeName().equalsIgnoreCase("storageDescr")){ snmpStorageDTO.setDescricao(noItemX.getTextContent());
	 * snmpStorageDTO.setTipo(SNMPStorageDTO.DISCO); if (snmpStorageDTO.getDescricao().startsWith("Physical Memory")){ snmpStorageDTO.setTipo(SNMPStorageDTO.FISICA); } } if
	 * (noItemX.getNodeName().equalsIgnoreCase("storageAllocationUnits")){ String str = noItemX.getTextContent(); if (str != null){ Integer int1 = 0; try{ int1 = Integer.parseInt(str.trim());
	 * snmpStorageDTO.setAllocationUnits(int1); }catch(Exception e){ } } } if (noItemX.getNodeName().equalsIgnoreCase("storageSize")){ String str = noItemX.getTextContent(); if (str != null){ Long
	 * int1 = (long) 0; try{ int1 = Long.parseLong(str.trim()); snmpStorageDTO.setSize(int1); }catch(Exception e){ } } } if (noItemX.getNodeName().equalsIgnoreCase("storageUsed")){ String str =
	 * noItemX.getTextContent(); if (str != null){ Long int1 = (long) 0; try{ int1 = Long.parseLong(str.trim()); snmpStorageDTO.setUsed(int1); }catch(Exception e){ } } } }
	 * lstStorages.add(snmpStorageDTO); } } }catch(Exception e){ e.printStackTrace(); } } catch (ParserConfigurationException e) { e.printStackTrace(); } catch (SAXException e) { e.printStackTrace();
	 * } catch (IOException e) { e.printStackTrace(); }
	 *
	 * return lstStorages; }
	 *
	 * public static String getSNMPInfo(String strurl) {
	 *
	 * try { URL url = new URL(strurl);
	 *
	 * URLConnection urlConnection = url.openConnection(); urlConnection.setRequestProperty("accept", "text/xml"); BufferedReader xml = new BufferedReader(new InputStreamReader(
	 * urlConnection.getInputStream())); String str; String strFinal = ""; while ((str = xml.readLine()) != null) { if (!strFinal.equalsIgnoreCase("")){ strFinal = strFinal + ""; } strFinal = strFinal
	 * + str; //System.out.println(str); }
	 *
	 * xml.close(); return strFinal; } catch (Exception e) { System.out.println(e); } return null; }
*/
	public NetMapDTO getNetMapDto() {
		return this.netMapDto = new NetMapDTO();
	}

	public void run() {
		try {
			NetDiscoverAdvanced netDiscover = new NetDiscoverAdvanced();
			List<NetMapDTO> lst = netDiscover.getListNetMap();
			// Metodo Gravar dados
			this.gravarDados(lst);

		} catch (IOException e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String[] getParametroFaixaIPNmap() throws ServiceException, LogicException, Exception {
		ParametroCorporeDTO paramentroDTO = null;
		ParametroCorporeService parametroService = getServiceParametro();

		// List<ParametroCorporeDTO> listFaixaIp = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.FaixaIp.id(),
		// Enumerados.ParametroSistema.FaixaIp.getCampoParametroInternacionalizado(request));
		String strFaixaIp = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FaixaIp, " ");

		String faixaIp = "";
		String [] arrayIp = {""};
//		if ((paramentroDTO = (ParametroCorporeDTO) listFaixaIp.get(0)).getValor() != null) {
//			faixaIp = (paramentroDTO = (ParametroCorporeDTO) listFaixaIp.get(0)).getValor();
			if (strFaixaIp != null) {
				faixaIp = strFaixaIp;
			if(faixaIp.trim().indexOf(";") != -1){
				arrayIp = faixaIp.split(";");
			}else {
				throw new LogicException("Parâmentro de Configuração de Faixa de IP inválido");
			}
			return arrayIp;
		} else {
			System.out.println("Caminho Nmap não definida em Parâmetro de Sistema 'Faixa Ip'!");
		}
		return null;
	}

	private String getParametroCaminhoArquivoNmap() throws ServiceException, Exception {
//		ParametroCorporeService parametroService = getServiceParametro();
//		List<ParametroCorporeDTO> listcaminhoArquivoNetMap = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.CaminhoArquivoNetMap.id(),
//				Enumerados.ParametroSistema.CaminhoArquivoNetMap.getCampoParametroInternacionalizado(request));
//
//		String caminhoArquivoNetMap = "";
//		if (((ParametroCorporeDTO) listcaminhoArquivoNetMap.get(0)).getValor() != null) {
//			caminhoArquivoNetMap = ((ParametroCorporeDTO) listcaminhoArquivoNetMap.get(0)).getValor();
//		}
//		return caminhoArquivoNetMap;
		return ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CaminhoArquivoNetMap, " ");
	}

	private String getParametroCaminhoNmap() throws ServiceException, Exception {
//		ParametroCorporeService parametroService = getServiceParametro();
		// List<ParametroCorporeDTO> listCaminhoNmap = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.CaminhoNmap.id(),
		// Enumerados.ParametroSistema.CaminhoNmap.getCampoParametroInternacionalizado(request));
//		return ((ParametroCorporeDTO) listCaminhoNmap.get(0)).getValor();
		return ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CaminhoNmap, " ");
	}
}
