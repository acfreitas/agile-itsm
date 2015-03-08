package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.OpiniaoDTO;
import br.com.citframework.service.CrudService;

public interface OpiniaoService extends CrudService {
	public Collection findByTipoAndPeriodo(String tipo, Integer idContrato, Date dataInicial, Date dataFinal) throws Exception;
	public OpiniaoDTO findByIdSolicitacao(Integer idSolicitacao) throws Exception ;
}
