package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface CentroResultadoService extends CrudService {
	
	public Collection list() throws ServiceException, LogicException;
	public Collection listAtivos() throws Exception;
	public Collection listAtivosVinculados(Integer idSolicitante, String tipoAlcada) throws Exception;
	public Collection listPermiteRequisicaoProduto() throws Exception;
	public Collection listPermiteRequisicaoProdutoAlcadaAtivo() throws Exception;
	public Collection findByIdPai(Integer idPai) throws Exception;
	public Collection findSemPai() throws Exception;
	public Collection find(CentroResultadoDTO centroResultadoDTO) throws Exception;
	
	public Collection getHierarquiaCentroDeCustoAtivo (boolean acrescentarInativos, boolean somenteRequisicaoProdutos, boolean somenteRequisicaoViagem) throws Exception;
	
	public Collection consultarCentroDeCustoComVinculoViagemNaAlcada (Integer idPai) throws Exception;
	
	public boolean temFilhos(int idCentroResultado) throws Exception;

	public void recuperaImagem(CentroResultadoDTO centroResultadoDTO) throws Exception;
	
	public Collection findByIdAlcada(Integer idAlcada) throws Exception;
	public IDto createAntigo(IDto model) throws ServiceException, LogicException;
	public void updateAntigo(IDto model) throws ServiceException, LogicException;
}
