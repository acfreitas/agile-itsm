package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AssociacaoDeviceAtendenteDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Assert;

public class AssociacaoDeviceAtendenteServiceEjb extends CrudServiceImpl implements AssociacaoDeviceAtendenteService {

    private static final String ASSOCIACAO_MUST_NOT_BE_NULL = "'Associação' must not be null.";
    private static final String USUARIO_MUST_NOT_BE_NULL = "'Usuário' must not be null.";

    private AssociacaoDeviceAtendenteDAO dao;

    @Override
    protected AssociacaoDeviceAtendenteDAO getDao() {
        if (dao == null) {
            dao = new AssociacaoDeviceAtendenteDAO();
        }
        return dao;
    }

    @Override
    public AssociacaoDeviceAtendenteDTO associateDeviceToAttendant(final AssociacaoDeviceAtendenteDTO associacao, final UsuarioDTO usuario) throws ServiceException {
        Assert.notNull(associacao, ASSOCIACAO_MUST_NOT_BE_NULL);
        List<AssociacaoDeviceAtendenteDTO> associacoes;
        try {
            associacoes = this.getDao().listActiveWithSameProperties(associacao);

            if (associacoes.isEmpty()) {
                return (AssociacaoDeviceAtendenteDTO) this.getDao().create(associacao);
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

        associacao.setId(associacoes.get(0).getId());

        return associacao;
    }

    @Override
    public AssociacaoDeviceAtendenteDTO disassociateDeviceFromAttendant(final AssociacaoDeviceAtendenteDTO associacao, final UsuarioDTO usuario) throws ServiceException {
        Assert.notNull(associacao, ASSOCIACAO_MUST_NOT_BE_NULL);
        Assert.notNull(usuario, USUARIO_MUST_NOT_BE_NULL);
        List<AssociacaoDeviceAtendenteDTO> associacoes;
        try {
            associacoes = this.getDao().listActiveWithSameProperties(associacao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

        if (associacoes.size() != 1) {
            throw new ServiceException(this.i18nMessage(usuario.getLocale(), "rest.service.mobile.v2.device.disassociate.associacao.inexistente"));
        }

        final AssociacaoDeviceAtendenteDTO toUpdate = associacoes.get(0);
        toUpdate.setActive(0);

        try {
            this.getDao().update(toUpdate);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        return toUpdate;
    }

    @Override
    public List<AssociacaoDeviceAtendenteDTO> listActiveAssociationsForUserAndConnection(final UsuarioDTO usuario, final String connection) throws ServiceException {
        Assert.notNull(usuario, USUARIO_MUST_NOT_BE_NULL);
        Assert.notNullAndNotEmpty(connection, "'Connection' must not be null or empty.");
        try {
            return this.getDao().listActiveAssociationForUser(usuario, connection);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
