package br.com.centralit.citcorpore.batch;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.comm.server.IPAddress;
import br.com.centralit.citcorpore.comm.server.Subnet;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings("unchecked")
public class MonitoraDiscoveryIP extends Thread {
	public static List<NetMapDTO> lstAddressDiscovery;
	public static ConcurrentHashMap<String, String> hsmAddressDiscovery;

	public MonitoraDiscoveryIP() {
		lstAddressDiscovery = new ArrayList<NetMapDTO>();
		hsmAddressDiscovery = new ConcurrentHashMap<String, String>();

		System.out.println("CITSMART --> Inciando MonitoraDiscoveryIP...");

		ItemConfiguracaoService itemConfiguracaoService = null;
		ValorService valorService = null;

		try {
			valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
			itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Collection<ItemConfiguracaoDTO> colItens = null;
		try {
			colItens = itemConfiguracaoService.listAtivos();
			if (colItens != null && !colItens.isEmpty()) {

				for (ItemConfiguracaoDTO itemConfiguracaoAux : colItens) {
					Collection<ItemConfiguracaoDTO> colHardware = null;
					String enderecoIp = "";
					Date dataUltInv = null;
					if (itemConfiguracaoAux.getDtUltimaCaptura() != null) {
						dataUltInv = new Date(itemConfiguracaoAux.getDtUltimaCaptura().getTime());
					}

					colHardware = itemConfiguracaoService.listByIdItemPaiAndTagTipoItemCfg(itemConfiguracaoAux.getIdItemConfiguracao(), "HARDWARE");

					if (colHardware != null && !colHardware.isEmpty()) {

						for (ItemConfiguracaoDTO itemConfiguracaoMem : colHardware) {

							Collection<ValorDTO> colCapacity = valorService.listByItemConfiguracaoAndTagCaracteristica(itemConfiguracaoMem.getIdItemConfiguracao(), "IPADDR");

							if (colCapacity != null && !colCapacity.isEmpty()) {

								for (ValorDTO valorCapacidade : colCapacity) {
									enderecoIp = valorCapacidade.getValorStr();
									break;
								}
							}
						}
					}
					if (enderecoIp != null && !enderecoIp.trim().equalsIgnoreCase("")) {

						synchronized (lstAddressDiscovery) {
							NetMapDTO netMapDTO = new NetMapDTO();
							netMapDTO.setIp(enderecoIp);
							netMapDTO.setDataInventario(dataUltInv);
							if (!MonitoraDiscoveryIP.hsmAddressDiscovery.containsKey(enderecoIp)) {
								hsmAddressDiscovery.put(enderecoIp, enderecoIp);
								lstAddressDiscovery.add(netMapDTO);
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		if (!CITCorporeUtil.START_MODE_DISCOVERY) {
			System.out.println("CITSMART --> Processo de Discovery Desativado... Ver arquivo de Configuração...");
			return;
		}
		String faixas = "";
		faixas = CITCorporeUtil.IP_RANGE_DISCOVERY;
		if (faixas == null) {
			faixas = "";
		}
		if (faixas.trim().equalsIgnoreCase("")) {
			try {
				faixas = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.FAIXA_DISCOVERY_IP, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (faixas == null) {
			faixas = "";
		}
		if (faixas.trim().equalsIgnoreCase("N")) {
			faixas = "";
		}
		if (faixas.trim().equalsIgnoreCase("")) {
			faixas = "10.0.0.1-10.255.255.255";
		}
		faixas = faixas + ";";
		String[] strFaixas = faixas.split(";");
		for (int i = 0; i < strFaixas.length; i++) {
			if (strFaixas[i] != null && !strFaixas[i].trim().equalsIgnoreCase("")) {
				String strIps = strFaixas[i] + "- ";
				String[] ips = strIps.split("-");
				if (ips != null) {
					if (ips.length > 1) {
						if (ips[0] == null && ips[1] == null) {
							continue;
						}
						if (ips[0].trim().equalsIgnoreCase("") && ips[1].trim().equalsIgnoreCase("")) {
							continue;
						}
						IPAddress ip1 = null;
						IPAddress ip2 = null;
						try {
							if (ips[1] == null || ips[1].trim().equalsIgnoreCase("")) {
								// O formato CIDR é 10.0.0.1/15 ou 192.168.1.255/24
								Subnet subNet = new Subnet(ips[0].trim());
								String address1 = subNet.getInfo().getLowAddress();
								String address2 = subNet.getInfo().getHighAddress();

								ip1 = new IPAddress(address1);
								ip2 = new IPAddress(address2);
							} else {
								ip1 = new IPAddress(ips[0]);
								ip2 = new IPAddress(ips[1]);
							}
							ThreadValidaFaixaIP t = new ThreadValidaFaixaIP();
							t.setIp1(ip1);
							t.setIp2(ip2);
							t.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
