package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoMidiaDTO;
import br.com.citframework.service.CrudService;
public interface LigacaoRequisicaoLiberacaoMidiaService extends CrudService {
	public Collection<LigacaoRequisicaoLiberacaoHistoricoMidiaDTO> findByIdLiberacao(Integer parm) throws Exception;
}
