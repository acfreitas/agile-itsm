package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.componenteMaquina.ThreadDisparaEvento;
import br.com.centralit.citcorpore.integracao.EventoGrupoDao;
import br.com.centralit.citcorpore.integracao.EventoItemConfigDao;
import br.com.centralit.citcorpore.integracao.EventoItemConfigRelDao;
import br.com.centralit.citcorpore.integracao.ItemConfigEventoDao;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class EventoItemConfigServiceEjb extends CrudServiceImpl implements EventoItemConfigService {

    private EventoItemConfigDao dao;

    @Override
    protected EventoItemConfigDao getDao() {
        if (dao == null) {
            dao = new EventoItemConfigDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final CrudDAO crudDao = this.getDao();
        final ItemConfigEventoDao itemConfigEventoDao = new ItemConfigEventoDao();
        final EventoGrupoDao eventoGrupoDao = new EventoGrupoDao();
        final EventoItemConfigRelDao eventoItemConfigRelDao = new EventoItemConfigRelDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            this.validaCreate(model);

            crudDao.setTransactionControler(tc);
            itemConfigEventoDao.setTransactionControler(tc);
            eventoGrupoDao.setTransactionControler(tc);
            eventoItemConfigRelDao.setTransactionControler(tc);

            tc.start();

            crudDao.create(model);

            final EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) model;

            this.generateTableRelationship(eventoDto, eventoGrupoDao, eventoItemConfigRelDao);

            if (eventoDto.getLstItemConfigEvento() != null && eventoDto.getLstItemConfigEvento().size() > 0) {
                for (final ItemConfigEventoDTO itemConfigEventoDto : eventoDto.getLstItemConfigEvento()) {
                    itemConfigEventoDto.setIdEvento(eventoDto.getIdEvento());
                    itemConfigEventoDao.create(itemConfigEventoDto);

                    // Dispara evento
                    this.shootEvent(itemConfigEventoDto, eventoDto);
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final CrudDAO crudDao = this.getDao();
        final ItemConfigEventoDao itemConfigEventoDao = new ItemConfigEventoDao();
        final EventoGrupoDao eventoGrupoDao = new EventoGrupoDao();
        final EventoItemConfigRelDao eventoItemConfigRelDao = new EventoItemConfigRelDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            this.validaUpdate(model);

            crudDao.setTransactionControler(tc);
            itemConfigEventoDao.setTransactionControler(tc);
            eventoGrupoDao.setTransactionControler(tc);
            eventoItemConfigRelDao.setTransactionControler(tc);

            tc.start();

            crudDao.update(model);

            final EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) model;

            this.deleteObsoleteRelationship(eventoGrupoDao, eventoItemConfigRelDao, eventoDto.getIdEvento());

            this.generateTableRelationship(eventoDto, eventoGrupoDao, eventoItemConfigRelDao);

            if (eventoDto.getLstItemConfigEvento() != null && eventoDto.getLstItemConfigEvento().size() > 0) {
                itemConfigEventoDao.deleteByIdEvento(eventoDto.getIdEvento());
                for (final ItemConfigEventoDTO itemConfigEventoDto : eventoDto.getLstItemConfigEvento()) {
                    itemConfigEventoDto.setIdEvento(eventoDto.getIdEvento());
                    itemConfigEventoDao.create(itemConfigEventoDto);

                    // Dispara evento
                    this.shootEvent(itemConfigEventoDto, eventoDto);
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public ValorDTO pegarCaminhoItemConfig(final String nomeBaseItemConfig) throws ServiceException {
        try {
            return this.getDao().pegarCaminhoItemConfig(nomeBaseItemConfig);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Método que gera o relacionamento dos grupos de item configuração com o evento e dos itens de configuração com o evento.
     *
     * @param eventoDto
     *            EventoItemConfigDTO evento a ser relacionado
     * @param tc
     *            TransactionControler controlador de transição do DAO
     * @throws Exception
     */
    private void generateTableRelationship(final EventoItemConfigDTO eventoDto, final EventoGrupoDao eventoGrupoDao, final EventoItemConfigRelDao eventoItemConfigRelDao)
            throws Exception {
        // Cria o relacionamento Evento com o Grupo
        if (eventoDto.getLstGrupo() != null && eventoDto.getLstGrupo().size() > 0) {
            for (final EventoGrupoDTO eventoGrupoDTO : eventoDto.getLstGrupo()) {
                eventoGrupoDTO.setIdEvento(eventoDto.getIdEvento());
                eventoGrupoDao.create(eventoGrupoDTO);
            }
        }

        // Cria o relacionamento Evento com o Item de Configuração
        if (eventoDto.getLstItemConfiguracao() != null && eventoDto.getLstItemConfiguracao().size() > 0) {
            for (final EventoItemConfigRelDTO itemConfigRelDTO : eventoDto.getLstItemConfiguracao()) {
                itemConfigRelDTO.setIdEvento(eventoDto.getIdEvento());
                eventoItemConfigRelDao.create(itemConfigRelDTO);
            }
        }
    }

    /**
     * Método que verifica se é para executar o evento agora e dispara a thread.
     *
     * @param itemConfigEventoDto
     * @param eventoDto
     * @throws Exception
     */
    private void shootEvent(final ItemConfigEventoDTO itemConfigEventoDto, final EventoItemConfigDTO eventoDto) throws Exception {
        if (itemConfigEventoDto.getGerarQuando().equalsIgnoreCase("A")) {
            /*
             * if (eventoDto.getLstEmpregado() != null && eventoDto.getLstEmpregado().size() > 0) {
             * for (EventoEmpregadoDTO eventoEmpDto : eventoDto.getLstEmpregado()) {
             * new Thread(new ThreadDisparaEvento(eventoEmpDto.getIdEmpregado(), itemConfigEventoDto.getIdBaseItemConfiguracao(), itemConfigEventoDto.getIdEvento(),
             * itemConfigEventoDto.getTipoExecucao(), itemConfigEventoDto.getLinhaComando())).start();
             * }
             * }
             */
            List<EventoItemConfigRelDTO> listItemConfiguracao = null;
            if (eventoDto != null) {
                listItemConfiguracao = eventoDto.getLstItemConfiguracao();
            }
            if (listItemConfiguracao == null) {
                listItemConfiguracao = new ArrayList<EventoItemConfigRelDTO>();
            }
            // Busca Itens de Configuração relacionados ao grupo
            final List<EventoGrupoDTO> lstGrupo = eventoDto.getLstGrupo();
            final GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();
            for (final EventoGrupoDTO eventoGrupoDTO : lstGrupo) {
                final Integer idGrupo = eventoGrupoDTO.getIdGrupo();
                final ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
                grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(idGrupo);
                final Collection<ItemConfiguracaoDTO> lstItemConfigGrupo = itemConfiguracaoDao.listByGrupo(grupoItemConfiguracaoDTO, null, null);
                for (final ItemConfiguracaoDTO itemConfiguracaoDTO : lstItemConfigGrupo) {
                    final EventoItemConfigRelDTO configRelDTO = new EventoItemConfigRelDTO();
                    configRelDTO.setIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracao());
                    // Verifica se o Item de Configuração consta na lista
                    if (!listItemConfiguracao.contains(configRelDTO)) {
                        listItemConfiguracao.add(configRelDTO);
                    }
                }
            }

            if (listItemConfiguracao.size() > 0) {
                for (final EventoItemConfigRelDTO itemConfiguracaoRel : listItemConfiguracao) {
                    new Thread(new ThreadDisparaEvento(itemConfiguracaoRel.getIdItemConfiguracao(), itemConfigEventoDto.getIdBaseItemConfiguracao(),
                            itemConfigEventoDto.getIdEvento(), itemConfigEventoDto.getTipoExecucao(), itemConfigEventoDto.getLinhaComando(),
                            itemConfigEventoDto.getLinhaComandoLinux())).start();
                }
            }
        }

    }

    /**
     * Método que deleta os relacionamentos com Grupo de item configuração e Item configuração com o evento
     *
     * @param eventoGrupoDao
     *            DAO de EventoGrupo
     * @param eventoItemConfigRelDao
     *            DAO de EventoItemConfigRel
     * @param idEvento
     * @throws Exception
     */
    private void deleteObsoleteRelationship(final EventoGrupoDao eventoGrupoDao, final EventoItemConfigRelDao eventoItemConfigRelDao, final Integer idEvento) throws Exception {
        eventoGrupoDao.deleteByIdEvento(idEvento);
        eventoItemConfigRelDao.deleteByIdEvento(idEvento);
    }

    @Override
    public Collection<CaracteristicaDTO> pegarNetworksItemConfiguracao(final Integer idItemConfiguracao) throws ServiceException {
        try {
            return this.getDao().pegarNetworksItemConfiguracao(idItemConfiguracao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String pegarSistemaOperacionalItemConfiguracao(final Integer idItemConfiguracao) throws ServiceException {
        try {
            return this.getDao().pegarSistemaOperacionalItemConfiguracao(idItemConfiguracao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
