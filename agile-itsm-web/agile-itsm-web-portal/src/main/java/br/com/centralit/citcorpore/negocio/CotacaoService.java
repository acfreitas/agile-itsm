package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudService;
public interface CotacaoService extends CrudService {
    public void incluiItensRequisicao(UsuarioDTO usuarioDto, Integer idCotacao, ItemCotacaoDTO itemCotacaoRefDto, String tipoCriacaoItem, Collection<ItemRequisicaoProdutoDTO> colItensRequisicao)throws Exception;
    public ItemCotacaoDTO verificaInclusaoItensRequisicao(String tipoCriacaoItem, Collection<ItemRequisicaoProdutoDTO> colItensRequisicao)throws Exception;
    public Collection<FornecedorDTO> sugereFornecedores(CotacaoDTO cotacaoDto) throws Exception;
    public void incluiFornecedores(CotacaoDTO cotacaoDto, Collection<FornecedorDTO> colFornecedores) throws Exception;
    public void calculaResultado(CotacaoDTO cotacaoDto) throws Exception;
    public void publicaResultado(CotacaoDTO cotacaoDto) throws Exception;
    public void reabreColetaPrecos(CotacaoDTO cotacaoDto) throws Exception;
    public Collection findItensPendentesAprovacao(CotacaoDTO cotacaoDto) throws Exception;
    public void encerra(CotacaoDTO cotacaoDto) throws Exception;
}
