package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.citframework.service.CrudService;

/**
 * 
 * @author flavio.santana
 *
 */
public interface MidiaSoftwareChaveService extends CrudService {
	
	public Collection<MidiaSoftwareChaveDTO> findByMidiaSoftware(Integer idMidiaSoftware) throws Exception;
	
	public void deleteByIdMidiaSoftware(Integer idMidiaSoftware) throws Exception;
	
}
