package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.MatrizPrioridadeDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;

/**
 *
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings("rawtypes")
public interface PrioridadeSolicitacoesService extends CrudService {

    Collection findById(final Integer idMatrizPrioridade) throws Exception;

    void createImpacto(final IDto impacto) throws Exception;

    void deleteImpacto() throws Exception;

    void createUrgencia(final IDto urgencia) throws Exception;

    void deleteUrgencia() throws Exception;

    void createMatrizPrioridade(final IDto matrizPrioridade) throws Exception;

    void deleteMatrizPrioridade() throws Exception;

    void restaurarGridMatrizPrioridade(final DocumentHTML document, final Collection<MatrizPrioridadeDTO> matrizPrioridade);

    Integer consultaValorPrioridade(final Integer idImpacto, final Integer idUrgencia);

    boolean consultaCadastros() throws Exception;

    Collection consultaImpacto() throws Exception;

    Collection consultaUrgencia() throws Exception;

    Collection consultaMatrizPrioridade() throws Exception;

    IDto restoreImpacto(final IDto impacto) throws Exception;

    IDto restoreImpactoBySigla(final ImpactoDTO impacto) throws Exception;

    IDto restoreUrgencia(final IDto urgencia) throws Exception;

    IDto restoreUrgenciaBySigla(final UrgenciaDTO impacto) throws Exception;

    boolean verificaImpactoJaExiste(final ImpactoDTO impacto) throws Exception;

    boolean verificaUrgenciaJaExiste(final UrgenciaDTO urgencia) throws Exception;

}
