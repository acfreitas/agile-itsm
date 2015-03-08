package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

public class AprovacaoRequisicaoLiberacaoServiceEjb extends CrudServiceImpl implements AprovacaoRequisicaoLiberacaoService {

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
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteByIdSolicitacaoServico(final Integer parm) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected CrudDAO getDao() {
        return null;
    }

}
