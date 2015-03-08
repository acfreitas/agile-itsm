package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.citframework.service.CrudService;

public interface MidiaSoftwareService extends CrudService {

	public boolean consultarMidiasAtivas(MidiaSoftwareDTO midiaSoftware) throws Exception;

}
