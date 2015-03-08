package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoDTO;
import br.com.centralit.citcorpore.integracao.ControleRendimentoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ControleRendimentoServiceEjb extends CrudServiceImpl implements ControleRendimentoService {

    private ControleRendimentoDao dao;

    @Override
    protected ControleRendimentoDao getDao() {
        if (dao == null) {
            dao = new ControleRendimentoDao();
        }
        return dao;
    }

    @Override
    public Collection<ControleRendimentoDTO> findByMesAno(final String mes, final String ano, final Integer idGrupo) throws Exception {
        try {
            return this.getDao().findByMesAno(mes, ano, idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ControleRendimentoDTO> findPontuacaoRendimento(final String mes, final String ano, final Integer idGrupo) throws Exception {
        try {
            return this.getDao().findPontuacaoRendimento(mes, ano, idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
