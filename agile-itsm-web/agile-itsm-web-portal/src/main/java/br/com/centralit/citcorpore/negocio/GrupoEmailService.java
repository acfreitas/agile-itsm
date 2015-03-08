package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.citframework.service.CrudService;

public interface GrupoEmailService extends CrudService {

	Collection<GrupoEmailDTO> findByIdGrupo(Integer idGrupo) throws Exception;

	Collection<GrupoEmailDTO> obterEmailsGrupo(Integer idGrupoAtual) throws Exception;

}
