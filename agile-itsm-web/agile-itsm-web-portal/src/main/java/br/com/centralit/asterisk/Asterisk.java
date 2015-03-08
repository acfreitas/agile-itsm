package br.com.centralit.asterisk;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.AsteriskQueueEntry;
import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.AsteriskServerListener;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.live.MeetMeUser;
import org.asteriskjava.live.internal.AsteriskAgentImpl;
import org.asteriskjava.manager.ManagerConnectionState;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

public class Asterisk implements AsteriskServerListener, PropertyChangeListener{

	AsteriskServer asteriskServer;

	public List<ChamadaDTO> telefonesChamando() throws IOException {
		String asteriskIP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKIP, "0.0.0.0");
		String asteriskLogin = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKLOGIN, "admin");
		String asteriskSenha = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKSENHA, "0");
		asteriskServer = new DefaultAsteriskServer(asteriskIP, asteriskLogin, asteriskSenha);

		List<ChamadaDTO> listaTelefones = new ArrayList<ChamadaDTO> ();
		// listen for new events
        asteriskServer.addAsteriskServerListener(this);
		if (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKALGORITMOCAPTURA, "1").equalsIgnoreCase("1")) {
			listaTelefones = capturaUniqueID(asteriskServer.getChannels());
		} else {
			listaTelefones = capturaDialingChannel(asteriskServer.getChannels());
		}
		this.closeServerAsterisk();
		return listaTelefones;
	}

	public List<ChamadaDTO> capturaDialingChannel(Collection<AsteriskChannel> canaisAsterisk) {
		List<ChamadaDTO> chamadas = new ArrayList<ChamadaDTO>();
		ChamadaDTO chamadaDTO;
		try {
			for (AsteriskChannel asteriskChannel : canaisAsterisk) {
				if ((asteriskChannel.getState() != null) && (asteriskChannel.getState().toString().equals("RINGING"))) {
					if ((asteriskChannel.getCallerId() != null) && (asteriskChannel.getCallerId().getNumber() != null) && (asteriskChannel.getDialingChannel() != null) && (asteriskChannel.getDialingChannel().getCallerId() != null) && (asteriskChannel.getDialingChannel().getCallerId().getNumber() != null)) {
						chamadaDTO = new ChamadaDTO();
						chamadaDTO.setNumeroOrigem(asteriskChannel.getDialingChannel().getCallerId().getNumber().toString());
						chamadaDTO.setNumeroDestino(asteriskChannel.getCallerId().getNumber().toString());
						chamadas.add(chamadaDTO);
					}
				}
				asteriskChannel.addPropertyChangeListener(this);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return chamadas;
	}

	private List<ChamadaDTO> capturaUniqueID(Collection<AsteriskChannel> canaisAsterisk) {
		List<ChamadaDTO> chamadas = new ArrayList<ChamadaDTO>();
		ChamadaDTO chamadaDTO;
		AsteriskChannel asteriskChannelDestino;
		for (AsteriskChannel asteriskChannelOrigem : canaisAsterisk) {
			if ((asteriskChannelOrigem.getState() != null) && (asteriskChannelOrigem.getState().toString().equals("RING")) && (asteriskChannelOrigem.getCallerId() != null) && (asteriskChannelOrigem.getCallerId().getNumber() != null)) {
				asteriskChannelDestino = this.findRingingCall(asteriskChannelOrigem, canaisAsterisk);
				if ((asteriskChannelDestino != null) && (asteriskChannelDestino.getCallerId() != null) && (asteriskChannelDestino.getCallerId().getNumber() != null)) {
					chamadaDTO = new ChamadaDTO();
					chamadaDTO.setNumeroOrigem(asteriskChannelOrigem.getCallerId().getNumber().toString());
					chamadaDTO.setNumeroDestino(asteriskChannelDestino.getCallerId().getNumber().toString());
					chamadas.add(chamadaDTO);
				}
			}
			asteriskChannelOrigem.addPropertyChangeListener(this);
		}
		return chamadas;
	}

	private AsteriskChannel findRingingCall(AsteriskChannel asteriskChannelOrigem, Collection<AsteriskChannel> canaisAsterisk) {
		String idLigacao = asteriskChannelOrigem.getId().substring(0, asteriskChannelOrigem.getId().indexOf("."));
		AsteriskChannel ringingCall = null;
		for (AsteriskChannel asteriskChannel : canaisAsterisk) {
			if ((asteriskChannel.getState() != null) && (asteriskChannel.getId() != null) && (asteriskChannel.getState().toString().equalsIgnoreCase("RINGING")) && (asteriskChannel.getId().contains(idLigacao))) {
				ringingCall = asteriskChannel;
				break;
			}
			asteriskChannel.addPropertyChangeListener(this);
		}
		return ringingCall;
	}

	public void closeServerAsterisk() {
		if (asteriskServer.getManagerConnection().getState().equals(ManagerConnectionState.CONNECTED) || asteriskServer.getManagerConnection().getState().equals(ManagerConnectionState.RECONNECTING)) {
			asteriskServer.getManagerConnection().logoff();
		}
		asteriskServer = null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
	}

	@Override
	public void onNewAgent(AsteriskAgentImpl arg0) {
		
	}

	@Override
	public void onNewAsteriskChannel(AsteriskChannel channel) {
		channel.addPropertyChangeListener(this);
	}

	@Override
	public void onNewMeetMeUser(MeetMeUser user) {
		user.addPropertyChangeListener(this);
	}

	@Override
	public void onNewQueueEntry(AsteriskQueueEntry arg0) {
		
	}

}