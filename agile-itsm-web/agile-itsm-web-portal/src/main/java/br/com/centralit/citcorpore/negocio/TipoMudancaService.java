package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface TipoMudancaService extends CrudService {

    Collection findByIdTipoMudanca(final Integer parm) throws Exception;

    void deleteByIdTipoMudanca(final Integer parm) throws Exception;

    // método baseado em categoriaMudanca
    // public Collection findByIdTipoMudancaPai(Integer parm) throws Exception;

    // método baseado em categoriaMudanca
    // public void deleteByIdTipoMudancaPai(Integer parm) throws Exception;

    Collection findByNomeTipoMudanca(final Integer parm) throws Exception;

    void deleteByNomeTipoMudanca(final Integer parm) throws Exception;

    // baseado em categoriaMudanca
    // public Collection listHierarquia() throws Exception;

    Collection<TipoMudancaDTO> tiposAtivosPorNome(final String nome);

    boolean verificarTipoMudancaAtivos(final TipoMudancaDTO obj) throws Exception;

    Collection encontrarPorNomeTipoMudanca(final TipoMudancaDTO obj) throws Exception;

    Collection getAtivos() throws Exception;

}
