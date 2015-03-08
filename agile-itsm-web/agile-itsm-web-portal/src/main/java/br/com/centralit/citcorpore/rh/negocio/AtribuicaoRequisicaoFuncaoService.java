package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.AtribuicaoRequisicaoFuncaoDTO;
import br.com.citframework.service.CrudService;

public interface AtribuicaoRequisicaoFuncaoService extends CrudService {

    boolean consultarAtribuicoesAtivas(final AtribuicaoRequisicaoFuncaoDTO obj) throws Exception;

    Collection<AtribuicaoRequisicaoFuncaoDTO> seAtribuicaoJaCadastrada(final AtribuicaoRequisicaoFuncaoDTO atribuicaoRequisicaoFuncaoDTO) throws Exception;

    Collection<AtribuicaoRequisicaoFuncaoDTO> listarAtivos() throws Exception;

}
