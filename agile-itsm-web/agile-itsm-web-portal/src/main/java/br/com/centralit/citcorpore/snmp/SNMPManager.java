package br.com.centralit.citcorpore.snmp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

@SuppressWarnings("rawtypes")
public class SNMPManager {

	Snmp snmp = null;
	String address = null;
	String community = null;

	/**
	 * Constructor
	 * 
	 * @param add
	 */
	public SNMPManager(String add) {
		address = add;
	}

	public SNMPManager(String add, String comm) {
		address = add;
		community = comm;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(SNMPManager.getSNMPXML("localhost", "161", "CentralIT"));
		// System.out.println(SNMPManager.getSNMPXML("10.0.0.2", "161"));
		// System.out.println(SNMPManager.getSNMPXML("10.4.0.192", "161"));
	}

	public static String getSNMPXML(String ipHostName, String port, String communityParm) throws Exception {
		String communitySearch = communityParm;
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		xml = xml + "<REQUEST>\n";
		xml = xml + "<CONTENT>\n";
		String xmlSystem = SNMPManager.getSNMPSystem(ipHostName, port, communitySearch);
		if (xmlSystem == null || xmlSystem.trim().equalsIgnoreCase("")) {
			communitySearch = "public"; // Tenta tambem pela community public
			xmlSystem = SNMPManager.getSNMPSystem(ipHostName, port, communitySearch);
			if (xmlSystem == null || xmlSystem.trim().equalsIgnoreCase("")) {
				return "";
			}
		}
		xml = xml + xmlSystem;
		try {
			xml = xml + SNMPManager.getSNMPStorages(ipHostName, port, communitySearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			xml = xml + SNMPManager.getSNMPSoftwares(ipHostName, port, communitySearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			xml = xml + SNMPManager.getSNMPNetworks(ipHostName, port, communitySearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xml = xml + "</CONTENT>\n";
		xml = xml + "</REQUEST>\n";
		return xml;
	}

	public static String getSNMPSystem(String ipHostName, String port, String communityParm) throws Exception {
		SNMPManager client = new SNMPManager("udp:" + ipHostName + "/" + port, communityParm);
		client.start();
		String sysDescr = "";
		String sysUpTime = "";
		String sysName = "";
		try {
			sysDescr = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
		} catch (Exception e) {
		}
		try {
			sysUpTime = client.getAsString(new OID(".1.3.6.1.2.1.1.3.0"));
		} catch (Exception e) {
		}
		try {
			sysName = client.getAsString(new OID(".1.3.6.1.2.1.1.5.0"));
		} catch (Exception e) {
		}

		String str = sysDescr;
		int index = str.indexOf("Software:");
		String nomeHard = "";
		String nomeSO = "";
		if (index > -1) {
			int indexHard = str.indexOf("Hardware:");
			if (indexHard > -1) {
				nomeHard = str.substring(indexHard + 9, index - 3);
			}
			nomeSO = str.substring(index + 9);
		}
		if (nomeHard == null || nomeHard.trim().equalsIgnoreCase("")) {
			nomeHard = sysDescr;
		}

		String workGroup = "";
		try {
			if (sysName.indexOf(".") > -1) {
				workGroup = sysName.substring(sysName.indexOf(".") + 1);
				sysName = sysName.substring(0, sysName.indexOf("."));
				sysName = sysName.replaceAll("\\.", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (workGroup == null) {
			workGroup = "";
		}
		if (sysName == null) {
			sysName = "";
		}

		if (nomeSO == null || nomeSO.trim().equalsIgnoreCase("")) {
			if (nomeHard.indexOf("Linux") > -1) {
				nomeSO = "Linux";
			} else if (nomeHard.indexOf("Windows") > -1) {
				nomeSO = "Windows";
			}
		}

		try {
			client.close();
		} catch (Exception e) {
		}
		if (!sysName.trim().equalsIgnoreCase("")) {
			String ret = "<HARDWARE>\n";
			ret = ret + "<NAME>" + sysName.toUpperCase().trim() + "</NAME>\n";
			ret = ret + "<PROCESSORT>" + nomeHard + "</PROCESSORT>\n";
			ret = ret + "<OSNAME>" + nomeSO.trim() + "</OSNAME>\n";
			ret = ret + "<UPTIME>" + sysUpTime.trim() + "</UPTIME>\n";
			ret = ret + "<OSVERSION>" + nomeSO.trim() + "</OSVERSION>\n";
			ret = ret + "<WORKGROUP>" + workGroup.trim() + "</WORKGROUP>\n";
			ret = ret + "<IPADDR>" + ipHostName.trim() + "</IPADDR>\n";
			ret = ret + "</HARDWARE>\n";

			return ret;
		} else {
			return "";
		}
	}

	public static String getSNMPStorages(String ipHostName, String port, String communityParm) throws Exception {
		SNMPManager client = new SNMPManager("udp:" + ipHostName + "/" + port, communityParm);
		client.start();
		String ret = "";
		for (int i = 1; i < 100; i++) {
			String sysDiskType = null;
			boolean erro = false;
			try {
				sysDiskType = client.getAsString(new OID(".1.3.6.1.2.1.25.2.3.1.2." + i));
			} catch (Exception e) {
				erro = true;
			}
			if (erro || sysDiskType == null) {
				break;
			}
			String sysDiskName = "";
			try {
				sysDiskName = client.getAsString(new OID(".1.3.6.1.2.1.25.2.3.1.3." + i));
			} catch (Exception e) {
				sysDiskName = null;
			}
			if (sysDiskName != null && !sysDiskName.trim().equalsIgnoreCase("") && !sysDiskName.trim().equalsIgnoreCase("Null")) {
				String alocUnitsStr = "";
				try {
					alocUnitsStr = client.getAsString(new OID(".1.3.6.1.2.1.25.2.3.1.4." + i));
				} catch (Exception e) {
					alocUnitsStr = null;
				}
				long alocUnits = 0;
				if (alocUnitsStr != null && !alocUnitsStr.trim().equalsIgnoreCase("")) {
					try {
						alocUnits = Long.parseLong(alocUnitsStr);
					} catch (Exception e) {
						alocUnits = 0;
					}
				}
				String sizeStr = "";
				try {
					sizeStr = client.getAsString(new OID(".1.3.6.1.2.1.25.2.3.1.5." + i));
				} catch (Exception e) {
					sizeStr = null;
				}
				long size = 0;
				if (sizeStr != null && !sizeStr.trim().equalsIgnoreCase("")) {
					try {
						size = Long.parseLong(sizeStr);
					} catch (Exception e) {
						size = 0;
					}
				}
				size = size * alocUnits;
				String usedStr = "";
				try {
					usedStr = client.getAsString(new OID(".1.3.6.1.2.1.25.2.3.1.6." + i));
				} catch (Exception e) {
					usedStr = null;
				}
				long used = 0;
				if (usedStr != null && !usedStr.trim().equalsIgnoreCase("")) {
					try {
						used = Long.parseLong(usedStr);
					} catch (Exception e) {
						used = 0;
					}
				}
				used = used * alocUnits;
				long free = 0;
				free = size - used;
				// --
				String type = "";
				try {
					type = client.getAsString(new OID(".1.3.6.1.2.1.25.2.3.1.2." + i));
				} catch (Exception e) {
					type = null;
				}
				if (type == null) {
					type = "";
				}
				// --
				if (sysDiskName != null && !sysDiskName.trim().equalsIgnoreCase("") && !sysDiskName.trim().equalsIgnoreCase("noSuchInstance") && !sysDiskName.trim().equalsIgnoreCase("noSuchObject")) {
					ret = ret + "<STORAGES>\n";
					ret = ret + "<DESCRIPTION>" + sysDiskName + "</DESCRIPTION>\n";
					ret = ret + "<NAME>" + sysDiskName + "</NAME>\n";
					ret = ret + "<DISKSIZE>" + size + "</DISKSIZE>\n";
					ret = ret + "<FREE>" + free + "</FREE>\n";
					// ret = ret + "<TYPE>" + type + "</TYPE>\n";
					ret = ret + "</STORAGES>\n";
				}
			}
		}
		try {
			client.close();
		} catch (Exception e) {
		}
		return ret;
	}

	public static String getSNMPSoftwares(String ipHostName, String port, String communityParm) throws Exception {
		SNMPManager client = new SNMPManager("udp:" + ipHostName + "/" + port, communityParm);
		client.start();
		String ret = "";
		for (int i = 1; i < 500; i++) {
			String sofName = "";
			try {
				sofName = client.getAsString(new OID(".1.3.6.1.2.1.25.6.3.1.2." + i));
			} catch (Exception e) {
				sofName = null;
			}
			if (sofName == null) {
				sofName = "";
			}
			if (sofName != null && !sofName.trim().equalsIgnoreCase("") && !sofName.trim().equalsIgnoreCase("Null")) {
				String installDate = "";
				try {
					installDate = client.getAsString(new OID(".1.3.6.1.2.1.25.6.3.1.5." + i));
				} catch (Exception e) {
					installDate = null;
				}
				if (installDate == null) {
					installDate = "";
				}
				// installDate = installDate.replaceAll(":", "");
				// OctetString octetString = new OctetString(installDate);
				if (sofName != null && !sofName.trim().equalsIgnoreCase("") && !sofName.trim().equalsIgnoreCase("noSuchInstance") && !sofName.trim().equalsIgnoreCase("noSuchObject")) {
					ret = ret + "<SOFTWARES>\n";
					ret = ret + "<PUBLISHER>" + sofName + "</PUBLISHER>\n";
					ret = ret + "<NAME>" + sofName + "</NAME>\n";
					// ret = ret + "<INSTALLDATE>" + octetString.toString() + "</INSTALLDATE>\n";
					ret = ret + "</SOFTWARES>\n";
				}
			}
			// --
		}
		try {
			client.close();
		} catch (Exception e) {
		}
		return ret;
	}

	public static String getSNMPNetworks(String ipHostName, String port, String communityParm) throws Exception {
		SNMPManager client = new SNMPManager("udp:" + ipHostName + "/" + port, communityParm);
		client.start();
		String ret = "";
		for (int i = 1; i < 500; i++) {
			String descrNet = "";
			try {
				descrNet = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.2." + i));
			} catch (Exception e) {
				descrNet = null;
			}
			if (descrNet == null) {
				descrNet = "";
			}
			if (descrNet != null && !descrNet.trim().equalsIgnoreCase("") && !descrNet.trim().equalsIgnoreCase("Null")) {
				descrNet = descrNet.replaceAll(":", "");
				descrNet = fromHex(descrNet);

				String typeNet = "";
				try {
					typeNet = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.3." + i));
				} catch (Exception e) {
					typeNet = null;
				}
				if (typeNet == null) {
					typeNet = "";
				}
				String ipAddr = "";
				try {
					ipAddr = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.6." + i));
				} catch (Exception e) {
					ipAddr = null;
				}
				if (ipAddr == null) {
					ipAddr = "";
				}
				String speed = "";
				try {
					speed = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.5." + i));
				} catch (Exception e) {
					speed = null;
				}
				if (speed == null) {
					speed = "";
				}
				String status = "";
				try {
					status = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.8." + i));
				} catch (Exception e) {
					status = null;
				}
				if (status == null) {
					status = "";
				}
				String index = "";
				try {
					index = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.1." + i));
				} catch (Exception e) {
					index = null;
				}
				if (index == null) {
					index = "";
				}
				// installDate = installDate.replaceAll(":", "");
				// OctetString octetString = new OctetString(installDate);
				if (descrNet != null && !descrNet.trim().equalsIgnoreCase("") && !descrNet.trim().equalsIgnoreCase("noSuchInstance") && !descrNet.trim().equalsIgnoreCase("noSuchObject")) {
					if (typeNet.trim().equalsIgnoreCase("6") || typeNet.trim().equalsIgnoreCase("71")) {
						if (typeNet.trim().equalsIgnoreCase("6")) {
							typeNet = "ethernetCsmacd";
						}
						if (typeNet.trim().equalsIgnoreCase("71")) {
							typeNet = "ieee80211";
						}
						if (status.trim().equalsIgnoreCase("1")) {
							status = "UP";
						}
						if (status.trim().equalsIgnoreCase("2")) {
							status = "DOWN";
						}
						if (status.trim().equalsIgnoreCase("6")) {
							status = "NOT PRESENT";
						}
						if (status.trim().equalsIgnoreCase("7")) {
							status = "lowerLayerDown";
						}
						if (status.trim().equalsIgnoreCase("5")) {
							status = "dormant";
						}
						ret = ret + "<NETWORKS>\n";
						ret = ret + "<DESCRIPTION>" + descrNet + "</DESCRIPTION>\n";
						ret = ret + "<MACADDR>" + ipAddr + "</MACADDR>\n";
						ret = ret + "<TYPE>" + typeNet + "</TYPE>\n";
						ret = ret + "<SPEED>" + speed + "</SPEED>\n";
						ret = ret + "<STATUS>" + status + "</STATUS>\n";
						// ret = ret + "<INSTALLDATE>" + octetString.toString() + "</INSTALLDATE>\n";
						ret = ret + "</NETWORKS>\n";
					}
				}
			}
			// --
		}
		try {
			client.close();
		} catch (Exception e) {
		}
		return ret;
	}

	public static String fromHex(String s) throws UnsupportedEncodingException {
		try {
			byte bs[] = new byte[s.length() / 2];
			for (int i = 0; i < s.length(); i += 2) {
				bs[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
			}
			return new String(bs, "UTF8");
		} catch (Exception e) {
			return s;
		}
	}

	public static String convertHexToIP(String hex) {
		String ip = "";

		for (int j = 0; j < hex.length(); j += 2) {
			String sub = hex.substring(j, j + 2);
			int num = Integer.parseInt(sub, 16);
			ip += num + ".";
		}

		ip = ip.substring(0, ip.length() - 1);
		return ip;
	}

	/**
	 * Start the Snmp session. If you forget the listen() method you will not get any answers because the communication is asynchronous and the listen() method listens for answers.
	 * 
	 * @throws IOException
	 */
	private void start() throws IOException {

		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		// Do not forget this line!
		transport.listen();
	}

	private void close() throws IOException {
		if (snmp != null) {
			try {
				snmp.close();
			} catch (Exception e) {
			}
		}
		snmp = null;
	}

	/**
	 * Method which takes a single OID and returns the response from the agent as a String.
	 * 
	 * @param oid
	 * @return
	 * @throws IOException
	 */
	public String getAsString(OID oid) throws IOException {
		ResponseEvent event = get(new OID[] { oid });
		return event.getResponse().get(0).getVariable().toString();
	}

	/**
	 * This method is capable of handling multiple OIDs
	 * 
	 * @param oids
	 * @return
	 * @throws IOException
	 */
	public ResponseEvent get(OID oids[]) throws IOException {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}
		pdu.setType(PDU.GET);
		ResponseEvent event = snmp.send(pdu, getTarget(), null);
		if (event != null) {
			return event;
		}
		throw new RuntimeException("GET timed out");
	}

	/**
	 * This method returns a Target, which contains information about where the data should be fetched and how.
	 * 
	 * @return
	 */
	private Target getTarget() {
		String snmpCommunity = "public";
		if (community == null || community.trim().equalsIgnoreCase("")) {
			try {
				snmpCommunity = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.INVENTARIO_SNMP_COMMUNITY, "public");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (snmpCommunity == null || snmpCommunity.trim().equalsIgnoreCase("")) {
				snmpCommunity = "public";
			}
		} else {
			snmpCommunity = community;
		}

		Address targetAddress = GenericAddress.parse(address);
		CommunityTarget target = new CommunityTarget();
		// target.setCommunity(new OctetString("CentralIT"));
		target.setCommunity(new OctetString(snmpCommunity));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

}