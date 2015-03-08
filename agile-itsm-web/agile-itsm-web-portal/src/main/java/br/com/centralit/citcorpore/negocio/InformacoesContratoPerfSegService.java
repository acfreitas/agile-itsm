package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface InformacoesContratoPerfSegService extends CrudService {
	public Collection findByIdProntuarioEletronicoConfig(Integer idProntuarioEletronicoConfig) throws Exception;
}
