package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.citframework.service.CrudService;

public interface RiscoService extends CrudService{
	boolean jaExisteRegistroComMesmoNome(RiscoDTO risco);
	public boolean existeNoBanco(RiscoDTO risco);
	ArrayList<RiscoDTO> riscoAtivo();

}
