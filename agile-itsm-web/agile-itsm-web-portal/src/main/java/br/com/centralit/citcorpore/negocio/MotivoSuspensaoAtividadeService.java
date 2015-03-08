package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.citframework.service.CrudService;

public interface MotivoSuspensaoAtividadeService extends CrudService {

    boolean jaExisteRegistroComMesmoNome(final MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO);

    Collection<MotivoSuspensaoAtividadeDTO> listarMotivosSuspensaoAtividadeAtivos() throws Exception;

}
