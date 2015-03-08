package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DadosEmailRegOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class OcorrenciaServiceEjb extends CrudServiceImpl implements OcorrenciaService {

    private OcorrenciaDao dao;

    @Override
    protected OcorrenciaDao getDao() {
        if (dao == null) {
            dao = new OcorrenciaDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public Collection findByDemanda(final Integer idDemanda) throws LogicException, ServiceException {
        try {
            return this.getDao().findByDemanda(idDemanda);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateResposta(final IDto bean) throws LogicException, ServiceException {
        try {
            this.getDao().updateResposta(bean);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<OcorrenciaDTO> findByIdSolicitacao(final Integer idSolicitacao) throws Exception {
        try {
            return this.getDao().findByIdSolicitacao(idSolicitacao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public OcorrenciaDTO findSiglaGrupoExecutorByIdSolicitacao(final Integer idSolicitacao) throws Exception {
        try {
            return this.getDao().findSiglaGrupoExecutorByIdSolicitacao(idSolicitacao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.com.centralit.citcorpore.negocio.OcorrenciaService#obterDadosResponsavelEmailRegOcorrencia(java.lang.Integer)
     */
    @Override
    public DadosEmailRegOcorrenciaDTO obterDadosResponsavelEmailRegOcorrencia(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().obterDadosResponsavelEmailRegOcorrencia(idSolicitacaoServico);
    }

}
