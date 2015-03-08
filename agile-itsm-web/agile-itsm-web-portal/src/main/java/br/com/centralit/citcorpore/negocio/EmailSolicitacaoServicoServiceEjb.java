package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.EmailSolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class EmailSolicitacaoServicoServiceEjb extends CrudServiceImpl implements EmailSolicitacaoServicoService {

    private EmailSolicitacaoServicoDao dao;

    @Override
    protected EmailSolicitacaoServicoDao getDao() {
        if (dao == null) {
            dao = new EmailSolicitacaoServicoDao();
        }
        return dao;
    }

    @Override
    public EmailSolicitacaoServicoDTO listSituacao(final String messageid) throws Exception {
        try {
            return this.getDao().listSituacao(messageid);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EmailSolicitacaoServicoDTO> getEmailByOrigem(final String origem) throws Exception {
        try {
            return this.getDao().getEmailByOrigem(origem);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmailSolicitacaoServicoDTO getEmailByIdSolicitacaoAndOrigem(final Integer idSolicitacao, final String origem) throws Exception {
        try {
            return this.getDao().getEmailByIdSolicitacaoAndOrigem(idSolicitacao, origem);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmailSolicitacaoServicoDTO getEmailByIdMessageAndOrigem(final String idMessage, final String origem) throws Exception {
        try {
            return this.getDao().getEmailByIdMessageAndOrigem(idMessage, origem);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmailSolicitacaoServicoDTO getEmailByIdMessage(final String idMessage) throws Exception {
        try {
            return this.getDao().getEmailByIdMessage(idMessage);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto createWithID(final IDto model) throws Exception {
        return this.getDao().createWithID(model);
    }

}
