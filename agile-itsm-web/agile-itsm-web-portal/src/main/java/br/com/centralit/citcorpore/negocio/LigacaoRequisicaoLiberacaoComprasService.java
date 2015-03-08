package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoComprasDTO;
import br.com.citframework.service.CrudService;
public interface LigacaoRequisicaoLiberacaoComprasService extends CrudService {
	public Collection<LigacaoRequisicaoLiberacaoHistoricoComprasDTO> findByIdLiberacao(Integer parm) throws Exception;
}
