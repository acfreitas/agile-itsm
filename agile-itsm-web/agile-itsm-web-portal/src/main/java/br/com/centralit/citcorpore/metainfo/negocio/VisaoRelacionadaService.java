package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface VisaoRelacionadaService extends CrudService {

    Collection findByIdVisaoPaiAtivos(final Integer idVisaoPai) throws Exception;

}
