package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface ProdutoContratoService extends CrudService {
	public Collection findByIdContrato(Integer parm) throws Exception;
	public void deleteByIdContrato(Integer parm) throws Exception;
	public Collection getProdutosByIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrjParm, Integer idLinhaBaseProjetoParm) throws Exception;
}
