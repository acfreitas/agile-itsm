package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.citframework.service.CrudService;
public interface ColetaPrecoService extends CrudService {
    public Collection findHabilitadasByIdCotacao(Integer parm) throws Exception;
	public Collection findByIdItemCotacao(Integer parm) throws Exception;
	public void deleteByIdItemCotacao(Integer parm) throws Exception;
	public Collection findByIdPedido(Integer parm) throws Exception;
	public Collection findByIdFornecedor(Integer parm) throws Exception;
	public void deleteByIdFornecedor(Integer parm) throws Exception;
	public Collection<ColetaPrecoDTO> findByIdItemCotacaoAndIdFornecedor(Integer idFornecedor, Integer idItemCotacao) throws Exception;
	public void deleteByIdItemCotacaoAndIdFornecedor(Integer idFornecedor, Integer idItemCotacao) throws Exception;
    public Collection<ColetaPrecoDTO> findByIdCotacao(Integer idCotacao) throws Exception;
    public Collection findResultadoByIdItemCotacao(Integer idItemCotacao) throws Exception;
    public void defineResultado(ColetaPrecoDTO coletaPrecoBean) throws Exception;
}
