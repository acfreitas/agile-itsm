/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.AnexoBaseConhecimentoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * EJB de AnexoBaseConhecimento.
 *
 * @author valdoilo.damasceno
 */
public class AnexoBaseConhecimentoServiceEjb extends CrudServiceImpl implements AnexoBaseConhecimentoService {

    private AnexoBaseConhecimentoDAO dao;

    @Override
    protected AnexoBaseConhecimentoDAO getDao() {
        if (dao == null) {
            dao = new AnexoBaseConhecimentoDAO();
        }
        return dao;
    }

    @Override
    public Collection<AnexoBaseConhecimentoDTO> consultarAnexosDaBaseConhecimento(final BaseConhecimentoDTO baseConhecimentoBean) throws ServiceException, Exception {
        return this.getDao().consultarAnexosDaBaseConhecimento(baseConhecimentoBean);
    }

}
