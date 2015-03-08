package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MotivoNegacaoCheckinDTO;
import br.com.citframework.service.CrudService;

public interface MotivoNegacaoCheckinService extends CrudService {

    boolean hasWithSameName(final MotivoNegacaoCheckinDTO motivo);

    Collection<MotivoNegacaoCheckinDTO> listAllActiveDeniedReasons() throws Exception;

}
