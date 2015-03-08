package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Map;

import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.citframework.service.CrudService;

public interface TipoDemandaServicoService extends CrudService {

    Collection<TipoDemandaServicoDTO> listSolicitacoes() throws Exception;

    /**
     * Metodo responsavel por validar se existe vinculo entre o Tipo de Demanda e Servico
     *
     * @param mapFields
     * @return <p>
     *         Boolean
     *         </p>
     *
     * @author Ezequiel
     */
    boolean existeCadastroServicoVinculado(final Map mapFields) throws Exception;

}
