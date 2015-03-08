package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface HistoricoSituacaoCotacaoService extends CrudService {
	public Collection findByIdCotacao(Integer parm) throws Exception;
	public void deleteByIdCotacao(Integer parm) throws Exception;
	public Collection findByIdResponsavel(Integer parm) throws Exception;
	public void deleteByIdResponsavel(Integer parm) throws Exception;
}
