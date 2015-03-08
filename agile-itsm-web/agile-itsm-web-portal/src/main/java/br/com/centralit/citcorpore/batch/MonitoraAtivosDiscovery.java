package br.com.centralit.citcorpore.batch;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.comm.server.IPAddress;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class MonitoraAtivosDiscovery extends Thread {
	public static Semaphore semaforo = new Semaphore(1);
	public static int PORTA_AGENTE_DOTNET = 7103;
	public static String PORTA_SNMP = "161";
	public static String DIRETORIO_ARQUIVOS_INVENTARIO;

	public static String MENSAGEM_PROCESSAMENTO = "";
	public static String MENSAGEM_PROCESSAMENTO_COMPL = "";

	public static int NUMERO_THREADS = 1;
	public static int QTDE_THREADS = 50;

	public static boolean recomeca = false;
	public static boolean iniciouDiscovery = false;

	@Override
	public void run() {
		iniciouDiscovery = false;
		String INVENTARIO_PROCESSAMENTO_ATIVO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.INVENTARIO_PROCESSAMENTO_ATIVO, "N");
		boolean isAtivo = INVENTARIO_PROCESSAMENTO_ATIVO.equalsIgnoreCase("S");

		if (!isAtivo) {
			MENSAGEM_PROCESSAMENTO_COMPL = "";
			MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.processamentoInventarioDesativado";

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Iniciando discovery de IPs");
			MonitoraDiscoveryIP monitoraDiscoveryIP = new MonitoraDiscoveryIP();
			monitoraDiscoveryIP.start();
			iniciouDiscovery = true;
		}

		DIRETORIO_ARQUIVOS_INVENTARIO = CITCorporeUtil.CAMINHO_REAL_APP + "/tempInventario";

		try {
			System.out.println("Deletando arquivos que ainda existam na pasta..");

			File fX = new File(DIRETORIO_ARQUIVOS_INVENTARIO);
			if (fX.exists()) {
				for (File file : fX.listFiles()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.iniciandoProcessamentoAguarde";

		if (!CITCorporeUtil.START_MODE_INVENTORY) {

			System.out.println("CITSMART --> Processo de Inventário Desativado.");

			MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.processoInvetarioDesativadoVerArquivosConfiguracao";
		}
		try {
			Thread.sleep(240000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {

			if (!iniciouDiscovery) {
				INVENTARIO_PROCESSAMENTO_ATIVO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.INVENTARIO_PROCESSAMENTO_ATIVO, "N");
				isAtivo = INVENTARIO_PROCESSAMENTO_ATIVO.equalsIgnoreCase("S");
				if (!isAtivo) {
					MENSAGEM_PROCESSAMENTO_COMPL = "";
					MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.processamentoInventarioDesativado";
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					iniciouDiscovery = false;
				} else {
					MonitoraDiscoveryIP monitoraDiscoveryIP = new MonitoraDiscoveryIP();
					monitoraDiscoveryIP.start();
					iniciouDiscovery = true;
				}
			}

			try {
				processa();
			} catch (Exception e) {
				e.printStackTrace();
			}
			MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.aguardandoProximaExecucao";

			if (!recomeca) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void processa() throws Exception {

		if (!CITCorporeUtil.START_MODE_INVENTORY) {
			return;
		}

		semaforo.acquireUninterruptibly();

		try {
			String ipServidorInv = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.IP_SERVIDOR_INVENTARIO, null);

			String INVENTARIO_PROCESSAMENTO_ATIVO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.INVENTARIO_PROCESSAMENTO_ATIVO, "N");
			if (INVENTARIO_PROCESSAMENTO_ATIVO != null && INVENTARIO_PROCESSAMENTO_ATIVO.equalsIgnoreCase("N")) {
				MENSAGEM_PROCESSAMENTO_COMPL = "";
				MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.processamentoInventarioDesativado";
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
				}
				semaforo.release();
				iniciouDiscovery = false;
				return; // Nao processa
			}

			List<NetMapDTO> listNetMapDto = new ArrayList<NetMapDTO>();

			MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.realizandoDiscoveryIpsAtivos#(" + ipServidorInv + ")";

			List<NetMapDTO> listaSincronizada = Collections.synchronizedList(new ArrayList<NetMapDTO>(MonitoraDiscoveryIP.lstAddressDiscovery));
			synchronized (listaSincronizada) {
				listNetMapDto.addAll(listaSincronizada);
			}

			DIRETORIO_ARQUIVOS_INVENTARIO = CITCorporeUtil.CAMINHO_REAL_APP + "/tempInventario";
			File fX = new File(DIRETORIO_ARQUIVOS_INVENTARIO);
			if (!fX.exists()) {
				fX.mkdirs();
			}
			DIRETORIO_ARQUIVOS_INVENTARIO = DIRETORIO_ARQUIVOS_INVENTARIO + "/";
			// --
			recomeca = false;
			if (listNetMapDto != null) {
				String table = "<table width='100%'>";
				table += "<tr><td colspan='4'><b>IPs descobertos até o momento (clique em Refresh para atualizar a lista): </b></td></tr>";
				for (NetMapDTO netMapDTO : listNetMapDto) {
					String nameOrIp = "";
					if (netMapDTO.getNome() != null) {
						if (netMapDTO.getNome().indexOf("(") > -1) {
							netMapDTO.setNome(netMapDTO.getNome().substring(0, netMapDTO.getNome().indexOf("(") - 1));
						}
						netMapDTO.setNome(netMapDTO.getNome().trim());
						nameOrIp = netMapDTO.getNome();
					} else {
						nameOrIp = netMapDTO.getIp();
					}
					String td = "<td>";
					if (netMapDTO.isNovoIC()) {
						td += "NOVO IC";
					} else {
						td += "&nbsp;";
					}
					td += "</td>";
					String dataInv = "";
					if (netMapDTO.getDataInventario() != null) {
						dataInv = UtilDatas.dateToSTR(netMapDTO.getDataInventario()) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(netMapDTO.getDataInventario());
					}
					table += "<tr>";
					if (netMapDTO.getStatusPing() == null || netMapDTO.getStatusPing().equalsIgnoreCase(NetMapDTO.INDEFINIDO)) {
						table += "<td onclick='fazPing(\"" + nameOrIp
								+ "\")' style='border:1px solid black; cursor:pointer' title='<i18n:message key=\"mostrarStatusInventario.clickAquiParaAtaulizarStatus\"/>'><img src='"
								+ Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolacinza2.png'/></td>";
					} else if (netMapDTO.getStatusPing() != null && netMapDTO.getStatusPing().equalsIgnoreCase(NetMapDTO.ATIVO)) {
						table += "<td onclick='fazPing(\"" + nameOrIp
								+ "\")' style='border:1px solid black; cursor:pointer' title='<i18n:message key=\"mostrarStatusInventario.clickAquiParaAtaulizarStatus\"/>'><img src='"
								+ Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png'/></td>";
					} else if (netMapDTO.getStatusPing() != null && netMapDTO.getStatusPing().equalsIgnoreCase(NetMapDTO.INATIVO)) {
						table += "<td onclick='fazPing(\"" + nameOrIp
								+ "\")' style='border:1px solid black; cursor:pointer' title='<i18n:message key=\"mostrarStatusInventario.clickAquiParaAtaulizarStatus\"/>'><img src='"
								+ Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png'/></td>";
					}
					table += "<td style='border:1px solid black'>" + nameOrIp + "</td><td style='border:1px solid black'><button type='button' onclick='inventarioAgora(\"" + nameOrIp
							+ "\")'><i18n:message key=\"mostrarStatusInventario.numeroThreadsDiscovery\"/></button></td><td style='border:1px solid black'>" + dataInv + "</td>" + td + "</tr>";
				}
				table += "</table>";
				MENSAGEM_PROCESSAMENTO_COMPL = table;
				boolean temICNovo = false;
				ExecutorService exService = null;
				exService = Executors.newFixedThreadPool(NUMERO_THREADS);
				int qtde = 0;
				for (NetMapDTO netMapDTO : listNetMapDto) {
					if ((netMapDTO.isNovoIC() || netMapDTO.isForce()) && netMapDTO.okTimeToProcess()) { // Primeiro prioriza os ICS novos.
						// temICNovo = true;
						MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.verificandoStatusIp#" + netMapDTO.getIp();
						IPAddress ipAddr = new IPAddress(netMapDTO.getIp());
						if (ipAddr.ping()) {
							netMapDTO.setStatusPing(NetMapDTO.ATIVO);
							try {
								ThreadProcessaInventario t = new ThreadProcessaInventario();
								netMapDTO.setDateTimeControlProcessInv(UtilDatas.somaSegundos(UtilDatas.getDataHoraAtual(), 300)); // Adiciona 5 minutos de controle.
								t.setNetMapDTO(netMapDTO);
								exService.submit(new RunnableThread(t), "done");
								qtde++;
							} finally {
								Thread.sleep(2000); // Dá um tempo pra tudo se organizar, Garbage, etc... Da tempo entre uma chamada e outra
							}
						} else {
							netMapDTO.setStatusPing(NetMapDTO.INATIVO);
						}
						if (recomeca) {
							break;
						}
						if (qtde > QTDE_THREADS) {
							qtde = 0;
							try {
								Thread.sleep(60000);
								Thread.sleep(60000);
							} catch (InterruptedException e) {
							}
						}
					}
				}
				qtde = 0;
				if (!temICNovo && !recomeca) {
					for (NetMapDTO netMapDTO : listNetMapDto) {
						if (netMapDTO.okTimeToProcess()) {
							MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.verificandoStatusIp#" + netMapDTO.getIp();
							IPAddress ipAddr = new IPAddress(netMapDTO.getIp());
							if (ipAddr.ping()) {
								netMapDTO.setStatusPing(NetMapDTO.ATIVO);
								try {
									ThreadProcessaInventario t = new ThreadProcessaInventario();
									netMapDTO.setDateTimeControlProcessInv(UtilDatas.somaSegundos(UtilDatas.getDataHoraAtual(), 300)); // Adiciona 5 minutos de controle.
									t.setNetMapDTO(netMapDTO);
									exService.submit(new RunnableThread(t), "done");
									qtde++;
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									Thread.sleep(2000); // Dá um tempo pra tudo se organizar, Garbage, etc... Da tempo entre uma chamada e outra
								}
							} else {
								netMapDTO.setStatusPing(NetMapDTO.INATIVO);
							}
							if (recomeca) {
								break;
							}
							if (qtde > QTDE_THREADS) {
								qtde = 0;
								try {
									Thread.sleep(60000);
									Thread.sleep(60000);
								} catch (InterruptedException e) {
								}
							}
						}
					}
				}
			}
		} finally {
			semaforo.release();
		}
	}

	class RunnableThread implements Runnable {
		private ThreadProcessaInventario t = null;

		public RunnableThread(ThreadProcessaInventario tParm) {
			t = tParm;
		}

		@Override
		public void run() {
			try {
				if (t != null) {
					int contador = 0;
					t.start();
					while (!t.isFinish()) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
						}
						if (contador >= 400) {
							break;
						}
						contador++;
					}
					t.setBreakThread(true);
					t.setFinish(true);
					Thread.sleep(3000);
					t.interrupt();
					t = null;
				}
			} catch (Exception e) {
			} finally {
				if (t != null) {
					try {
						t.setBreakThread(true);
					} catch (Exception e) {
					}
					t.setFinish(true);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
					}
					try {
						t.interrupt();
					} catch (Exception e) {
					}
				}
				t = null;
			}
		}
	}

	public static void main(String[] args) {
		ThreadProcessaInventario t = new ThreadProcessaInventario();
		NetMapDTO netMapDTO = new NetMapDTO();
		netMapDTO.setNome("10.0.1.114");
		netMapDTO.setIp("10.0.1.114");
		t.setNetMapDTO(netMapDTO);
		SimpleDateFormat spd = new SimpleDateFormat("yyyyMMdd");
		Date dataAtual = UtilDatas.getDataAtual();
		String dataAtualInv = spd.format(dataAtual).trim();
		t.geraInventarioAtivo("localhost", "10.0.1.114", "10.0.1.114", dataAtualInv);
		/*
		 * SimpleDateFormat spd = new SimpleDateFormat("yyyyMMdd"); String dataAtualInv = spd.format(UtilDatas.getDataAtual()).trim(); System.out.println("INICIANDO..."); MonitoraAtivosDiscovery
		 * monitoraAtivosDiscovery = new MonitoraAtivosDiscovery(); monitoraAtivosDiscovery.run();
		 */
		/*
		 * NetMapDTO netMapDTO = new NetMapDTO(); netMapDTO.setNome("CITDFPC0470"); //netMapDTO.setNome("CITDFNTB001"); //netMapDTO.setNome("10.0.1.63"); //netMapDTO.setNome("localhost");
		 * netMapDTO.setIp("10.0.1.116"); boolean agenteLocalDOTNET = false; boolean agenteServerWindows = false; if(netMapDTO.getNome() != null){ agenteLocalDOTNET =
		 * monitoraAtivosDiscovery.geraInventarioAtivoAgenteLocal (netMapDTO.getNome(), dataAtualInv); }else{ agenteLocalDOTNET = monitoraAtivosDiscovery
		 * .geraInventarioAtivoAgenteLocal(netMapDTO.getIp(), dataAtualInv); } if (!agenteLocalDOTNET){ //Se nao tem AGENTE LOCAL .NET if(netMapDTO.getNome() != null){ agenteServerWindows =
		 * monitoraAtivosDiscovery .geraInventarioAtivo(IP_SERVER_WINDOWS_INVENTARIO, netMapDTO.getNome(), netMapDTO.getNome(), dataAtualInv); }else{ agenteServerWindows =
		 * monitoraAtivosDiscovery.geraInventarioAtivo(IP_SERVER_WINDOWS_INVENTARIO , netMapDTO.getIp(), netMapDTO.getIp(), dataAtualInv); } }
		 */
		System.out.println("FINALIZANDO...");
	}
}
