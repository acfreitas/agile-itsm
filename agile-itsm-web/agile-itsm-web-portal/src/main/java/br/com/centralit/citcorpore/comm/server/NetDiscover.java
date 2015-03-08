package br.com.centralit.citcorpore.comm.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
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
public class NetDiscover implements Runnable {

	private NetMapDTO netMapDto = new NetMapDTO();
	private List<NetMapDTO> listNetMap = new ArrayList<NetMapDTO>();

	public static void main(final String[] args) throws IOException {
		new Thread(new NetDiscover()).start();
	}

	public List<NetMapDTO> listaIpsAtivos() throws ServiceException, Exception, LogicException {
		List<NetMapDTO> lstIpsAtivos = this.getListNetMap();
		lstIpsAtivos = this.gravarDados(lstIpsAtivos);
		return lstIpsAtivos;
	}

	/**
	 * @return valor do atributo listNetMapAux.
	 * @throws Exception
	 * @throws ServiceException
	 */
	public List<NetMapDTO> getListNetMap() throws ServiceException, LogicException, Exception {
		Integer i = 0;
		final List<NetMapDTO> listaRetorno = new ArrayList<NetMapDTO>();
		final List<String> listaVmapFiles = new ArrayList<String>();
		File arqv;
		try {
			Process exec = null;
			String caminhoNetMap = this.getParametroCaminhoArquivoNmap() == null ? "" : this.getParametroCaminhoArquivoNmap().trim();
			if (new File(caminhoNetMap).isDirectory()) {
				caminhoNetMap += File.separator;
				String[] arrayFaixaIp = {};
				arrayFaixaIp = this.getParametroFaixaIPNmap();
				for (final String faixaIp : arrayFaixaIp) {
					arqv = new File(caminhoNetMap + "nmapFile" + i);
					if (arqv.exists()) {
						arqv.delete();
					}
					try {
						System.out.println("Realizando port scan..." + faixaIp.trim());
						exec = Runtime.getRuntime().exec(new String[] { this.getParametroCaminhoNmap(), "-sP", faixaIp.trim(), "-oN", caminhoNetMap + "nmapFile" + i });
						exec.waitFor();
						listaVmapFiles.add(caminhoNetMap + "nmapFile" + i);
						i++;
					} catch (final IOException er) {
						throw new LogicException("Parâmentro de Configuração do Caminho nmap inválido");
					}
				}
				// Pega Arquivo Gerado pelo nmap
				for (final String nmapFile : listaVmapFiles) {
					arqv = new File(nmapFile);
					if (arqv.exists()) {
						boolean flag = true;
						arqv = new File(nmapFile);
						Scanner arq = new Scanner(arqv);
						while (flag) {
							final String linha = arq.nextLine();
							// Flag para identificar fim do arquivo ser encontrar
							// retorna false
							if (linha.substring(0, 11).equalsIgnoreCase("# Nmap done")) {
								flag = false;
							}
							if (!arq.hasNext()) {
								arq.close();
								arq = null;
								Thread.sleep(1000);
								arqv = new File(nmapFile);
								arq = new Scanner(arqv);
							}
						}
						// Chama metodo que irá fazer leitura de arquivo nmap
						// Pegar lista Preenchida pelo metodo netDiscover
						listaRetorno.addAll(this.readFile(arqv));
						arq.close();
					}
				}

			} else {
				throw new LogicException("Parâmentro de Configuração de Diretório Arquivo NetMap não Encontrado");
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return listaRetorno;
	}

	/**
	 * metodo para percorre arquivo e preencher obj netmap
	 *
	 * @param file
	 *            diretorio do arquivo
	 */
	public List<NetMapDTO> readFile(final File file) {
		try {
			listNetMap = new ArrayList<NetMapDTO>();
			final Scanner arq = new Scanner(file);
			while (arq.hasNext()) {
				final String linha = arq.nextLine();
				if (linha.length() > 0) {
					// Identifica se é o final do arquivo
					if (!linha.substring(0, 11).equalsIgnoreCase("# Nmap done")) {
						// Verifica se é ip
						if (linha.substring(0, 9).equalsIgnoreCase("Nmap scan")) {
							this.getNetMapDto();

							if (linha.length() > 38) {
								final String texto = linha.toString();
								final Pattern p = Pattern.compile("\\(.*\\)$");
								final Matcher m = p.matcher(texto);
								String ip;
								if (m.find()) {
									ip = m.group();
									ip = ip.replace('(', ' ').replace(')', ' ').replaceAll(" ", "");
									ip = ip.trim();
									netMapDto.setIp(ip);
								}
							} else {
								netMapDto.setIp(linha.substring(21, linha.length()).replaceAll("[^0-9.]", ""));
							}
							netMapDto.setNome(linha.substring(21, linha.length()));
							listNetMap.add(netMapDto);
							// Veririca se mac
						} else if (linha.substring(0, 11).equalsIgnoreCase("MAC Address")) {
							netMapDto.setMac(linha.substring(13, 30));
							this.getNetMapDto();
						}
					}
				}
			}
			arq.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		file.getFreeSpace();
		return listNetMap;
	}

	/**
	 * Grava a lista de dados obtidos do NMap. Se os dados ja estiverem gravados eles serao atualizados.
	 *
	 * @param lst
	 *            Lista de dados NMap
	 */
	private List<NetMapDTO> gravarDados(final List<NetMapDTO> lst) {
		final List<NetMapDTO> lstRetorno = new ArrayList<NetMapDTO>();
		try {
			final ItemConfiguracaoService itemConfService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			if (lst != null) {
				for (final NetMapDTO netMapDTO : lst) {

					final NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
					ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
					final List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDTO);
					netMapDTO.setDate(Util.getSqlDataAtual());
					// Verifica existencia, se existir faz update se nao grava
					if (lstNetMap != null && !lstNetMap.isEmpty()) {
						NetMapDTO mapDTO = new NetMapDTO();
						mapDTO = lstNetMap.get(0);
						mapDTO.setDate(Util.getSqlDataAtual());
						mapDTO.setMac(netMapDTO.getMac());
						netMapDTO.setIcNovo("false");
						netMapService.update(mapDTO);
					} else {
						netMapDTO.setIcNovo("true");
						netMapService.create(netMapDTO);
						itemConfiguracaoDTO.setIdentificacao(netMapDTO.getIp());
						itemConfiguracaoDTO.setDataInicio(netMapDTO.getDate());
						itemConfiguracaoDTO = itemConfService.obterICFilhoPorIdentificacaoIdPaiEIdTipo(itemConfiguracaoDTO);
						if (itemConfiguracaoDTO.getIdItemConfiguracao() == null) {
							itemConfService.create(itemConfiguracaoDTO);
						}
					}
					lstRetorno.add(netMapDTO);

				}
			} else {
				System.out.println("Não foi encontrado IP na rede!");
			}
		} catch (final Exception e) {
			System.out.println("Problema ao gravar dados NetMap: " + e.getMessage());
			e.printStackTrace();
		}
		return lstRetorno;
	}

	public NetMapDTO getNetMapDto() {
		return netMapDto = new NetMapDTO();
	}

	@Override
	public void run() {
		try {
			final NetDiscover netDiscover = new NetDiscover();
			final List<NetMapDTO> lst = netDiscover.getListNetMap();
			// Metodo Gravar dados
			this.gravarDados(lst);
		} catch (final Exception e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private String[] getParametroFaixaIPNmap() throws ServiceException, LogicException, Exception {
		String faixaIp = "";
		String[] arrayIp = { "" };
		if (this.getParametroFaixaIp() != null && !this.getParametroFaixaIp().equalsIgnoreCase("")) {
			faixaIp = this.getParametroFaixaIp();

			if (faixaIp.trim().indexOf(";") != -1) {
				arrayIp = faixaIp.split(";");
			} else {
				throw new LogicException("Parâmentro de Configuração de Faixa de IP inválido");
			}
			return arrayIp;
		} else {
			System.out.println("Caminho Nmap não definida em Parâmetro de Sistema 'Faixa Ip'!");
		}
		return null;
	}

	private String getParametroFaixaIp() throws Exception {
		final String faixaIp = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FaixaIp, " ");
		return faixaIp;
	}

	private String getParametroCaminhoArquivoNmap() throws Exception {
		final String caminhoArquivoNetMap = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CaminhoArquivoNetMap, " ");
		return caminhoArquivoNetMap;
	}

	private String getParametroCaminhoNmap() throws Exception {
		final String caminhoNmap = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CaminhoNmap, " ");
		return caminhoNmap;
	}

}
