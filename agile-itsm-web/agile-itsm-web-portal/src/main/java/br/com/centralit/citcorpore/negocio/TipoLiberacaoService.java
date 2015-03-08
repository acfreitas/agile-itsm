package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface TipoLiberacaoService extends CrudService {

    Collection findByIdTipoLiberacao(final Integer parm) throws Exception;

    void deleteByIdTipoLiberacao(final Integer parm) throws Exception;

    // método baseado em categoriaLiberacao
    // public Collection findByIdTipoLiberacaoPai(Integer parm) throws Exception;

    // método baseado em categoriaLiberacao
    // public void deleteByIdTipoLiberacaoPai(Integer parm) throws Exception;

    Collection findByNomeTipoLiberacao(final Integer parm) throws Exception;

    void deleteByNomeTipoLiberacao(final Integer parm) throws Exception;

    // baseado em categoriaLiberacao
    // public Collection listHierarquia() throws Exception;

    Collection<TipoLiberacaoDTO> tiposAtivosPorNome(final String nome);

    boolean verificarTipoLiberacaoAtivos(final TipoLiberacaoDTO obj) throws Exception;

    Collection encontrarPorNomeTipoLiberacao(final TipoLiberacaoDTO obj) throws Exception;

    Collection getAtivos() throws Exception;

}
