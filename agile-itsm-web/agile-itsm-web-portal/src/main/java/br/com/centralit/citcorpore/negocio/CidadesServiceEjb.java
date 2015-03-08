package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.integracao.CidadesDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CidadesServiceEjb extends CrudServiceImpl implements CidadesService {

    private CidadesDao dao;

    @Override
    protected CidadesDao getDao() {
        if (dao == null) {
            dao = new CidadesDao();
        }
        return dao;
    }

    @Override
    public Collection<CidadesDTO> listByIdCidades(final CidadesDTO obj) throws Exception {
        try {
            return this.getDao().listByIdCidades(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<CidadesDTO> findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

    @Override
    public CidadesDTO findCidadeUF(final Integer idCidade) throws Exception {
        final List<CidadesDTO> lista = this.getDao().findCidadeUF(idCidade);
        if (lista != null) {
            return lista.get(0);
        }
        return null;
    }

    @Override
    public Collection<CidadesDTO> listByIdUf(final Integer idUf) throws Exception {
        return this.getDao().listByIdUf(idUf);
    }

    @Override
    public Collection<CidadesDTO> findByIdEstadoAndNomeCidade(final Integer idEstado, final String nomeCidade) throws Exception {
        return this.getDao().findByIdEstadoAndNomeCidade(idEstado, nomeCidade);
    }

    @Override
    public Collection findNomeByIdCidade(final Integer idCidade) throws Exception {
        return this.getDao().findCidadeUF(idCidade);
    }

    @Override
    public CidadesDTO findByIdCidade(final Integer idCidade) throws Exception {
        return this.getDao().findByIdCidade(idCidade);
    }

}
