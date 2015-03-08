package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUsuarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.Order;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public class PrioridadeServicoUsuarioServiceEjb extends CrudServiceImpl implements PrioridadeServicoUsuarioService {

    private PrioridadeServicoUsuarioDao dao;

    @Override
    protected PrioridadeServicoUsuarioDao getDao() {
        if (dao == null) {
            dao = new PrioridadeServicoUsuarioDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdAcordoNivelServico(final Integer idAcordoNivelServico) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServico(idAcordoNivelServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PrioridadeServicoUsuarioDTO findByIdAcordoNivelServicoAndIdUsuario(final Integer idAcordoNivelServico, final Integer idUsuario) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServicoAndIdUsuario(idAcordoNivelServico, idUsuario);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer recuperaPrioridade(final Integer idAcordoNivelServico, final Integer idUsuario) throws Exception {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();

        condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
        condicao.add(new Condition("idUsuario", "=", idUsuario));
        ordenacao.add(new Order("idUsuario"));

        try {
            final List<PrioridadeServicoUsuarioDTO> resp = (List<PrioridadeServicoUsuarioDTO>) this.getDao().findByCondition(condicao, ordenacao);
            if (resp != null) {
                final PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO = resp.get(0);
                return prioridadeServicoUsuarioDTO.getIdPrioridade();
            } else {
                return 0;
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAcordoNivelServico(final Integer idAcordoNivelServico) throws Exception {
        try {
            this.getDao().deleteByIdAcordoNivelServico(idAcordoNivelServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
