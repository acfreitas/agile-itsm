package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.CaracteristicaMonitDTO;
import br.com.centralit.citcorpore.bean.MonitoramentoAtivosDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoMonitDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioMonitDTO;
import br.com.centralit.citcorpore.bean.ScriptMonitDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaMonitDAO;
import br.com.centralit.citcorpore.integracao.MonitoramentoAtivosDAO;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoMonitDAO;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioMonitDAO;
import br.com.centralit.citcorpore.integracao.ScriptMonitDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author euler.ramos
 *
 */
public class MonitoramentoAtivosServiceEjb extends CrudServiceImpl implements MonitoramentoAtivosService {

    private MonitoramentoAtivosDAO dao;

    @Override
    protected MonitoramentoAtivosDAO getDao() {
        if (dao == null) {
            dao = new MonitoramentoAtivosDAO();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        MonitoramentoAtivosDTO monitoramentoAtivosDTO = (MonitoramentoAtivosDTO) model;
        final MonitoramentoAtivosDAO monitoramentoAtivosDAO = this.getDao();

        final NotificacaoUsuarioMonitDAO notificacaoUsuariosDao = new NotificacaoUsuarioMonitDAO();
        final NotificacaoGrupoMonitDAO notificacaoGruposDao = new NotificacaoGrupoMonitDAO();
        final CaracteristicaMonitDAO caracteristicaMonitDao = new CaracteristicaMonitDAO();
        final ScriptMonitDAO scriptMonitDao = new ScriptMonitDAO();

        final TransactionControler tc = new TransactionControlerImpl(monitoramentoAtivosDAO.getAliasDB());

        try {
            notificacaoUsuariosDao.setTransactionControler(tc);
            notificacaoGruposDao.setTransactionControler(tc);
            caracteristicaMonitDao.setTransactionControler(tc);
            scriptMonitDao.setTransactionControler(tc);

            tc.start();

            monitoramentoAtivosDTO.setDataInicio(UtilDatas.getDataAtual());
            monitoramentoAtivosDTO = (MonitoramentoAtivosDTO) monitoramentoAtivosDAO.create(monitoramentoAtivosDTO);

            // Registra caracteristica se existir...
            this.persisteCaracteristica(monitoramentoAtivosDTO, caracteristicaMonitDao);

            // Registra script se existir...
            this.persisteScript(monitoramentoAtivosDTO, scriptMonitDao);

            // Registra notificação para usuário se existir...
            this.persisteUsuariosNotificacao(monitoramentoAtivosDTO, notificacaoUsuariosDao);

            // Registra notificação para grupo se existir...
            this.persisteGruposNotificacao(monitoramentoAtivosDTO, notificacaoGruposDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

        return monitoramentoAtivosDTO;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final MonitoramentoAtivosDTO monitoramentoAtivosDTO = (MonitoramentoAtivosDTO) model;

        if (monitoramentoAtivosDTO.getIdMonitoramentoAtivos() != null) {
            final MonitoramentoAtivosDAO monitoramentoAtivosDAO = this.getDao();
            final NotificacaoUsuarioMonitDAO notificacaoUsuariosDao = new NotificacaoUsuarioMonitDAO();
            final NotificacaoGrupoMonitDAO notificacaoGruposDao = new NotificacaoGrupoMonitDAO();
            final CaracteristicaMonitDAO caracteristicaMonitDao = new CaracteristicaMonitDAO();
            final ScriptMonitDAO scriptMonitDao = new ScriptMonitDAO();

            final TransactionControler tc = new TransactionControlerImpl(monitoramentoAtivosDAO.getAliasDB());

            try {
                notificacaoUsuariosDao.setTransactionControler(tc);
                notificacaoGruposDao.setTransactionControler(tc);
                caracteristicaMonitDao.setTransactionControler(tc);
                scriptMonitDao.setTransactionControler(tc);

                tc.start();

                monitoramentoAtivosDAO.updateNotNull(monitoramentoAtivosDTO);

                // Registra caracteristica se existir...
                caracteristicaMonitDao.deleteByIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                this.persisteCaracteristica(monitoramentoAtivosDTO, caracteristicaMonitDao);

                // Registra script se existir...
                scriptMonitDao.deleteByIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                this.persisteScript(monitoramentoAtivosDTO, scriptMonitDao);

                // Registra notificação para usuário se existir...
                notificacaoUsuariosDao.deleteByIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                this.persisteUsuariosNotificacao(monitoramentoAtivosDTO, notificacaoUsuariosDao);

                // Registra notificação para grupo se existir...
                notificacaoGruposDao.deleteByIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                this.persisteGruposNotificacao(monitoramentoAtivosDTO, notificacaoGruposDao);

                tc.commit();
                tc.close();
            } catch (final Exception e) {
                this.rollbackTransaction(tc, e);
            }
        }
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        final MonitoramentoAtivosDTO monitoramentoAtivosDTO = (MonitoramentoAtivosDTO) model;

        if (monitoramentoAtivosDTO.getIdMonitoramentoAtivos() != null) {
            try {
                monitoramentoAtivosDTO.setDataFim(UtilDatas.getDataAtual());
                this.getDao().updateNotNull(monitoramentoAtivosDTO);
            } catch (final Exception e) {
                throw new ServiceException(e);
            }
        }
    }

    public void persisteCaracteristica(final MonitoramentoAtivosDTO monitoramentoAtivosDTO, final CaracteristicaMonitDAO caracteristicaMonitDao) throws Exception {
        if (monitoramentoAtivosDTO != null && monitoramentoAtivosDTO.getTipoRegra() != null && monitoramentoAtivosDTO.getTipoRegra().equalsIgnoreCase("c")) {
            if (monitoramentoAtivosDTO.getIdCaracteristica() != null) {
                final CaracteristicaMonitDTO caracteristicaMonitDto = new CaracteristicaMonitDTO();
                caracteristicaMonitDto.setIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                caracteristicaMonitDto.setIdCaracteristica(monitoramentoAtivosDTO.getIdCaracteristica());
                caracteristicaMonitDto.setDataInicio(UtilDatas.getDataAtual());
                caracteristicaMonitDao.create(caracteristicaMonitDto);
            }
        }
    }

    public void persisteScript(final MonitoramentoAtivosDTO monitoramentoAtivosDTO, final ScriptMonitDAO scriptMonitDao) throws Exception {
        if (monitoramentoAtivosDTO != null && monitoramentoAtivosDTO.getTipoRegra() != null && monitoramentoAtivosDTO.getTipoRegra().equalsIgnoreCase("s")) {
            if (monitoramentoAtivosDTO.getScript() != null) {
                final ScriptMonitDTO scriptMonitDto = new ScriptMonitDTO();
                scriptMonitDto.setIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                scriptMonitDto.setScript(monitoramentoAtivosDTO.getScript());
                scriptMonitDto.setDataInicio(UtilDatas.getDataAtual());
                scriptMonitDao.create(scriptMonitDto);
            }
        }
    }

    public void persisteUsuariosNotificacao(final MonitoramentoAtivosDTO monitoramentoAtivosDTO, final NotificacaoUsuarioMonitDAO notificacaoUsuariosDao) throws Exception {
        if (monitoramentoAtivosDTO != null && monitoramentoAtivosDTO.getEnviarEmail() != null && monitoramentoAtivosDTO.getEnviarEmail().equalsIgnoreCase("y")) {
            if (monitoramentoAtivosDTO.getUsuariosNotificacao() != null && monitoramentoAtivosDTO.getUsuariosNotificacao().length > 0) {
                for (final Integer usuario : monitoramentoAtivosDTO.getUsuariosNotificacao()) {
                    final NotificacaoUsuarioMonitDTO notificacaoUsuarioDto = new NotificacaoUsuarioMonitDTO();
                    notificacaoUsuarioDto.setIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                    notificacaoUsuarioDto.setIdUsuario(usuario);
                    notificacaoUsuarioDto.setDataInicio(UtilDatas.getDataAtual());
                    notificacaoUsuariosDao.create(notificacaoUsuarioDto);
                }
            }
        }
    }

    public void persisteGruposNotificacao(final MonitoramentoAtivosDTO monitoramentoAtivosDTO, final NotificacaoGrupoMonitDAO notificacaoGruposDao) throws Exception {
        if (monitoramentoAtivosDTO != null && monitoramentoAtivosDTO.getEnviarEmail() != null && monitoramentoAtivosDTO.getEnviarEmail().equalsIgnoreCase("y")) {
            if (monitoramentoAtivosDTO.getGruposNotificacao() != null && monitoramentoAtivosDTO.getGruposNotificacao().length > 0) {
                for (final Integer grupo : monitoramentoAtivosDTO.getGruposNotificacao()) {
                    final NotificacaoGrupoMonitDTO notificacaoGrupoDto = new NotificacaoGrupoMonitDTO();
                    notificacaoGrupoDto.setIdMonitoramentoAtivos(monitoramentoAtivosDTO.getIdMonitoramentoAtivos());
                    notificacaoGrupoDto.setIdGrupo(grupo);
                    notificacaoGrupoDto.setDataInicio(UtilDatas.getDataAtual());
                    notificacaoGruposDao.create(notificacaoGrupoDto);
                }
            }
        }
    }

    @Override
    public MonitoramentoAtivosDTO obterMonitorametoAtivoDaCaracteristica(final Integer idTipoItemConfiguracao, final Integer idCaracteristica) throws Exception {
        return this.getDao().obterMonitorametoAtivoDaCaracteristica(idTipoItemConfiguracao, idCaracteristica);
    }

    @Override
    public MonitoramentoAtivosDTO obterMonitorametoAtivoDoTipoItemConfiguracao(final Integer idTipoItemConfiguracao) throws Exception {
        return this.getDao().obterMonitorametoAtivoDoTipoItemConfiguracao(idTipoItemConfiguracao);
    }

}
