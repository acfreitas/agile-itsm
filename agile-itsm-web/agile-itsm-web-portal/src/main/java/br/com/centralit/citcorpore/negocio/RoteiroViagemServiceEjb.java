package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.TransactionControler;

public class RoteiroViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements RoteiroViagemService {

    private RoteiroViagemDAO dao;

    @Override
    protected RoteiroViagemDAO getDao() {
        if (dao == null) {
            dao = new RoteiroViagemDAO();
        }
        return dao;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        return null;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        return null;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public RoteiroViagemDTO findByIdIntegrante(final Integer idIntegrante) throws Exception {
        return this.getDao().findByIdIntegrante(idIntegrante);
    }

    @Override
    public Collection<RoteiroViagemDTO> findByIdIntegranteHistorico(final Integer idIntegrante) throws Exception {
        return this.getDao().findByIdIntegranteHistorico(idIntegrante);
    }

    @Override
    public Collection<RoteiroViagemDTO> findByIdIntegranteOriginal(final Integer idIntegrante) throws Exception {
        return this.getDao().findByIdIntegranteOriginal(idIntegrante);
    }

    @Override
    public Collection<RoteiroViagemDTO> findByIdIntegranteTodos(final Integer idIntegrante) throws Exception {
        return this.getDao().findByIdIntegranteTodos(idIntegrante);
    }

}
