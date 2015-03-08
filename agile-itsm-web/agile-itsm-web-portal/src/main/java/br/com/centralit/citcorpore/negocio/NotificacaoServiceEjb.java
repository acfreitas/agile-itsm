package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoServicoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class NotificacaoServiceEjb extends CrudServiceImpl implements NotificacaoService {

    private NotificacaoDao dao;

    @Override
    protected NotificacaoDao getDao() {
        if (dao == null) {
            dao = new NotificacaoDao();
        }
        return dao;
    }

    public void deletarNotificacao(final IDto model) throws ServiceException, Exception {
        final NotificacaoDTO notificacaoDto = (NotificacaoDTO) model;
        final TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);
            transactionControler.start();
            notificacaoDto.setDataFim(UtilDatas.getDataAtual());
            this.getDao().update(model);
            transactionControler.commit();
            transactionControler.close();
        } catch (final Exception e) {
            this.rollbackTransaction(transactionControler, e);
        }

    }

    @Override
    public boolean consultarNotificacaoAtivos(final NotificacaoDTO obj) throws Exception {
        return this.getDao().consultarNotificacaoAtivos(obj);
    }

    @Override
    public void update(final NotificacaoDTO notificacaoDto, TransactionControler transactionControler) throws Exception {
        final NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
        final NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
        final NotificacaoServicoDao notificacaoServicoDao = new NotificacaoServicoDao();

        if (transactionControler == null) {
            transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
            transactionControler.start();
        }
        final NotificacaoGrupoDTO notificacaoGrupoDto = new NotificacaoGrupoDTO();
        final NotificacaoUsuarioDTO notificacaoUsuarioDto = new NotificacaoUsuarioDTO();
        final NotificacaoServicoDTO notificacaoServicoDto = new NotificacaoServicoDTO();

        this.getDao().setTransactionControler(transactionControler);
        notificacaoGrupoDao.setTransactionControler(transactionControler);
        notificacaoUsuarioDao.setTransactionControler(transactionControler);

        this.getDao().update(notificacaoDto);
        /* deletando as notificações para usuario */
        notificacaoUsuarioDao.deleteByIdNotificacaoUsuario(notificacaoDto.getIdNotificacao());
        if (notificacaoDto.getListaDeUsuario() != null) {
            if (notificacaoDto.getIdNotificacao() != null && notificacaoDto.getIdNotificacao() != 0) {
                notificacaoUsuarioDao.deleteByIdNotificacaoUsuario(notificacaoDto.getIdNotificacao());
                for (final NotificacaoUsuarioDTO notificacaoUsuario : notificacaoDto.getListaDeUsuario()) {
                    notificacaoUsuarioDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                    notificacaoUsuarioDto.setIdUsuario(notificacaoUsuario.getIdUsuario());
                    notificacaoUsuarioDao.create(notificacaoUsuarioDto);
                }
            }

        }
        /* deletando as notificações para grupo */
        notificacaoGrupoDao.deleteByIdNotificacaoGrupo(notificacaoDto.getIdNotificacao());
        if (notificacaoDto.getListaDeGrupo() != null) {
            if (notificacaoDto.getIdNotificacao() != null && notificacaoDto.getIdNotificacao() != 0) {
                notificacaoGrupoDao.deleteByIdNotificacaoGrupo(notificacaoDto.getIdNotificacao());
                for (final NotificacaoGrupoDTO notificacaoGrupo : notificacaoDto.getListaDeGrupo()) {
                    notificacaoGrupoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                    notificacaoGrupoDto.setIdGrupo(notificacaoGrupo.getIdGrupo());
                    notificacaoGrupoDao.create(notificacaoGrupoDto);
                }
            }

        }
        /* deletando as notificações para serviço */
        notificacaoServicoDao.deleteByIdNotificacaoServico(notificacaoDto.getIdNotificacao());
        if (notificacaoDto.getListaDeServico() != null) {
            for (final NotificacaoServicoDTO notificacaoServico : notificacaoDto.getListaDeServico()) {
                if (notificacaoServico.getIdServico() != null && notificacaoServico.getIdServico() != 0) {
                    notificacaoServicoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                    notificacaoServicoDto.setIdServico(notificacaoServico.getIdServico());
                    notificacaoServicoDao.create(notificacaoServicoDto);
                }

            }
        }

    }

    @Override
    public NotificacaoDTO create(NotificacaoDTO notificacaoDto, final TransactionControler transactionControler) throws Exception {
        final NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
        final NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
        final NotificacaoServicoDao notificacaoServicoDao = new NotificacaoServicoDao();

        final NotificacaoGrupoDTO notificacaoGrupoDto = new NotificacaoGrupoDTO();
        final NotificacaoUsuarioDTO notificacaoUsuarioDto = new NotificacaoUsuarioDTO();
        final NotificacaoServicoDTO notificacaoServicoDto = new NotificacaoServicoDTO();

        this.getDao().setTransactionControler(transactionControler);
        notificacaoGrupoDao.setTransactionControler(transactionControler);
        notificacaoUsuarioDao.setTransactionControler(transactionControler);
        notificacaoServicoDao.setTransactionControler(transactionControler);

        notificacaoDto.setDataInicio(UtilDatas.getDataAtual());

        notificacaoDto = (NotificacaoDTO) this.getDao().create(notificacaoDto);

        if (notificacaoDto.getListaDeUsuario() != null) {
            for (final NotificacaoUsuarioDTO notificacaoUsuario : notificacaoDto.getListaDeUsuario()) {
                if (notificacaoUsuario.getIdUsuario() != null && notificacaoUsuario.getIdUsuario() != 0) {
                    notificacaoUsuarioDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                    notificacaoUsuarioDto.setIdUsuario(notificacaoUsuario.getIdUsuario());
                    notificacaoUsuarioDao.create(notificacaoUsuarioDto);
                }
            }
        }

        if (notificacaoDto.getListaDeGrupo() != null) {
            for (final NotificacaoGrupoDTO notificacaoGrupo : notificacaoDto.getListaDeGrupo()) {
                if (notificacaoGrupo.getIdGrupo() != null && notificacaoGrupo.getIdGrupo() != 0) {
                    notificacaoGrupoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                    notificacaoGrupoDto.setIdGrupo(notificacaoGrupo.getIdGrupo());
                    notificacaoGrupoDao.create(notificacaoGrupoDto);
                }
            }
        }

        if (notificacaoDto.getListaDeServico() != null) {
            for (final NotificacaoServicoDTO notificacaoServico : notificacaoDto.getListaDeServico()) {
                if (notificacaoServico.getIdServico() != null && notificacaoServico.getIdServico() != 0) {
                    notificacaoServicoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                    notificacaoServicoDto.setIdServico(notificacaoServico.getIdServico());
                    notificacaoServicoDao.create(notificacaoServicoDto);
                }
            }
        }

        return notificacaoDto;
    }

    @Override
    public Collection<NotificacaoDTO> consultarNotificacaoAtivosOrigemServico(final Integer idContrato) throws Exception {
        return this.getDao().consultarNotificacaoAtivosOrigemServico(idContrato);
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, br.com.citframework.excecao.LogicException {

        final NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
        final NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
        final NotificacaoServicoDao notificacaoServicoDao = new NotificacaoServicoDao();

        NotificacaoDTO notificacaoDto = (NotificacaoDTO) model;
        final NotificacaoGrupoDTO notificacaoGrupoDto = new NotificacaoGrupoDTO();
        final NotificacaoUsuarioDTO notificacaoUsuarioDto = new NotificacaoUsuarioDTO();
        final NotificacaoServicoDTO notificacaoServicoDTO = new NotificacaoServicoDTO();

        final TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.getDao().setTransactionControler(transactionControler);
            notificacaoGrupoDao.setTransactionControler(transactionControler);
            notificacaoUsuarioDao.setTransactionControler(transactionControler);
            notificacaoServicoDao.setTransactionControler(transactionControler);

            transactionControler.start();
            notificacaoDto.setDataInicio(UtilDatas.getDataAtual());
            notificacaoDto = (NotificacaoDTO) this.getDao().create(notificacaoDto);

            if (notificacaoDto.getListaDeUsuario() != null) {
                for (final NotificacaoUsuarioDTO notificacaoUsuario : notificacaoDto.getListaDeUsuario()) {
                    if (notificacaoUsuario.getIdUsuario() != null && notificacaoUsuario.getIdUsuario() != 0) {
                        notificacaoUsuarioDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        notificacaoUsuarioDto.setIdUsuario(notificacaoUsuario.getIdUsuario());
                        notificacaoUsuarioDao.create(notificacaoUsuarioDto);
                    }
                }
            }

            if (notificacaoDto.getListaDeGrupo() != null) {
                for (final NotificacaoGrupoDTO notificacaoGrupo : notificacaoDto.getListaDeGrupo()) {
                    if (notificacaoGrupo.getIdGrupo() != null && notificacaoGrupo.getIdGrupo() != 0) {
                        notificacaoGrupoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        notificacaoGrupoDto.setIdGrupo(notificacaoGrupo.getIdGrupo());
                        notificacaoGrupoDao.create(notificacaoGrupoDto);
                    }
                }
            }

            if (notificacaoDto.getListaDeServico() != null) {
                for (final NotificacaoServicoDTO notificacaoServico : notificacaoDto.getListaDeServico()) {
                    if (notificacaoServico.getIdServico() != null && notificacaoServico.getIdServico() != 0) {
                        notificacaoServicoDTO.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        notificacaoServicoDTO.setIdServico(notificacaoServico.getIdServico());
                        notificacaoServicoDao.create(notificacaoServicoDTO);
                    }
                }
            }

            transactionControler.commit();
            transactionControler.close();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transactionControler, e);
        }

        return notificacaoDto;
    }

    @Override
    public Collection<NotificacaoDTO> listaIdContrato(final Integer idContrato) throws Exception {
        return this.getDao().listaIdContrato(idContrato);
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

}
