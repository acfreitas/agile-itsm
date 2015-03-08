package br.com.centralit.citcorpore.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.asterisk.Asterisk;
import br.com.centralit.asterisk.ChamadaDTO;
import br.com.centralit.citajax.framework.AjaxReverse;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

/**
 * @author euler.ramos
 *
 */
public class MonitoraAsterisk extends Thread {

	private final String nomeDaFuncaoJavaScript = "exibirNotificacaoAsterisk";
	private static Asterisk asterisk;
	private StringBuilder listatelefones = null;
	private List<ChamadaDTO> listChamadaDto = new ArrayList<ChamadaDTO>();
	private Integer intervalo = 1000;

	@Override
	public void run() {
		while (true) {
			String asteriskAtivo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N");
			synchronized (this) {
				if (asteriskAtivo.equalsIgnoreCase("S")) {
					try {
						if (asterisk == null) {
							asterisk = new Asterisk();
						}
						listChamadaDto = asterisk.telefonesChamando();
						listatelefones = new StringBuilder();
						for (ChamadaDTO chamadaDTO : listChamadaDto) {
							listatelefones.append(chamadaDTO.getNumeroOrigem() + "," + chamadaDTO.getNumeroDestino() + "#");
						}

						if (listatelefones.length() > 0) {
							// Enviando lista de novas chamadas ativas para os
							// computadores clientes.
							AjaxReverse.executarAjaxReverseWithAllSessions(nomeDaFuncaoJavaScript, listatelefones.toString());
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			try {
				try {
					intervalo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKINTERVALO, "2000"));
				} catch (NumberFormatException e) {
					intervalo = 2000;
				}

				Thread.sleep(intervalo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}