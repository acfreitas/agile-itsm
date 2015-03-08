package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoHistoricoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.bean.RevisarSlaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SlaRequisitoSlaDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoHistoricoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUnidadeDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUsuarioDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.centralit.citcorpore.integracao.RevisarSlaDao;
import br.com.centralit.citcorpore.integracao.SlaRequisitoSLADao;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class AcordoNivelServicoServiceEjb extends CrudServiceImpl implements AcordoNivelServicoService {

    private AcordoNivelServicoDao dao;

    @Override
    protected AcordoNivelServicoDao getDao() {
        if (dao == null) {
            dao = new AcordoNivelServicoDao();
        }
        return dao;
    }

    @Override
    public AcordoNivelServicoDTO findAtivoByIdServicoContrato(final Integer idServicoContrato, final String tipo) throws Exception {
        return this.getDao().findAtivoByIdServicoContrato(idServicoContrato, tipo);
    }

    @Override
    public void copiarSLA(final Integer idAcordoNivelServico, final Integer idServicoContratoOrigem, final Integer[] idServicoCopiarPara) throws Exception {

        final TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
        final PrioridadeServicoUnidadeDao prioridadeServicoUnidadeDao = new PrioridadeServicoUnidadeDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {

            tc.start();

            this.getDao().setTransactionControler(tc);
            tempoAcordoNivelServicoDao.setTransactionControler(tc);
            prioridadeServicoUnidadeDao.setTransactionControler(tc);

            AcordoNivelServicoDTO acordoNivelServicoDTO = new AcordoNivelServicoDTO();
            acordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
            acordoNivelServicoDTO = (AcordoNivelServicoDTO) this.getDao().restore(acordoNivelServicoDTO);

            final Collection colPrioridadesUnidades = prioridadeServicoUnidadeDao.findByIdServicoContrato(idServicoContratoOrigem);

            for (final Integer element : idServicoCopiarPara) {
                acordoNivelServicoDTO.setIdAcordoNivelServico(null);
                acordoNivelServicoDTO.setIdServicoContrato(element);

                acordoNivelServicoDTO = (AcordoNivelServicoDTO) this.getDao().create(acordoNivelServicoDTO);
                if (acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")) { // TEMPO
                    final Collection colTempos = tempoAcordoNivelServicoDao.findByIdAcordo(idAcordoNivelServico);
                    if (colTempos != null) {
                        for (final Iterator it = colTempos.iterator(); it.hasNext();) {
                            final TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) it.next();
                            tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(acordoNivelServicoDTO.getIdAcordoNivelServico());
                            try {
                                tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (colPrioridadesUnidades != null) {
                        for (final Iterator it = colPrioridadesUnidades.iterator(); it.hasNext();) {
                            final PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeDTO = (PrioridadeServicoUnidadeDTO) it.next();
                            prioridadeServicoUnidadeDTO.setIdServicoContrato(element);
                            try {
                                prioridadeServicoUnidadeDao.delete(prioridadeServicoUnidadeDTO);
                            } catch (final Exception e) {
                                e.printStackTrace(); // Deixa passar o erro, pos não influencia.
                            }
                            try {
                                prioridadeServicoUnidadeDao.create(prioridadeServicoUnidadeDTO);
                            } catch (final Exception e) {
                                e.printStackTrace(); // Deixa passar o erro, pos não influencia.
                            }
                        }
                    }
                }
            }
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public Collection findByIdServicoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection consultaPorIdServicoContrato(final Integer parm) throws Exception {
        final ResultadosEsperadosDAO dao = new ResultadosEsperadosDAO();
        final Collection colRetorno = new ArrayList<>();
        try {
            final Collection col = dao.findByIdServicoContrato(parm);
            if (col != null && col.size() > 0) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final ResultadosEsperadosDTO resultados = (ResultadosEsperadosDTO) it.next();
                    if (resultados.getDeleted() == null || resultados.getDeleted().equalsIgnoreCase("N") || resultados.getDeleted().trim().equals("")) {
                        if (!this.consultaAcordoNivelServicoAtivo(resultados)) {
                            colRetorno.add(resultados);
                        }
                    }
                }
            }
            return colRetorno;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private boolean consultaAcordoNivelServicoAtivo(final ResultadosEsperadosDTO resultadosEsperadosDTO) throws ServiceException {
        AcordoNivelServicoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoDTO();
        acordoNivelServicoContratoDTO.setIdAcordoNivelServico(resultadosEsperadosDTO.getIdAcordoNivelServico());
        try {
            acordoNivelServicoContratoDTO = (AcordoNivelServicoDTO) this.getDao().restore(acordoNivelServicoContratoDTO);
            final String situacao = acordoNivelServicoContratoDTO.getSituacao();
            if (!situacao.equalsIgnoreCase("A")) {
                return true;
            }
            return false;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdServicoContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdPrioridadePadrao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPrioridadePadrao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPrioridadePadrao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPrioridadePadrao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Método para retornar os serviços que possuem o SLA selecionado já copiado, para ser tratado evitando duplicação
     * de SLA.
     *
     * @param titulo
     *            do SLA selecionado
     * @return retorna os serviços que possuem o SLA selecionado
     * @throws Exception
     * @author rodrigo.oliveira
     */
    @Override
    public List<ServicoContratoDTO> buscaServicosComContrato(final String tituloSla) throws Exception {
        try {
            return this.getDao().buscaServicosComContrato(tituloSla);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Método para verificar se existe cadastrado um cadastro o mesmo nome.
     *
     * @param HashMap
     *            mapFields
     * @return true se o nome existir e false se não existir
     * @throws Exception
     * @author rodrigo.oliveira
     */
    @Override
    public boolean verificaSeNomeExiste(final HashMap mapFields) throws Exception {
        final String tituloSLA = (String) mapFields.get("TITULOSLA");
        try {
            return this.getDao().verificaSeNomeExiste(tituloSLA);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AcordoNivelServicoDTO> findAcordosSemVinculacaoDireta() throws Exception {
        try {
            return this.getDao().findAcordosSemVinculacaoDireta();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public AcordoNivelServicoDTO create(AcordoNivelServicoDTO acordoNivelServicoDTO, final AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException,
            LogicException {

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        final TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
        final PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
        final PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
        final SlaRequisitoSLADao slaRequisitoSlaDao = new SlaRequisitoSLADao();
        final AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
        final RevisarSlaDao revisarSlaDao = new RevisarSlaDao();

        Integer idAcordoNivelServico = 0;

        try {
            this.getDao().setTransactionControler(transaction);
            tempoAcordoNivelServicoDao.setTransactionControler(transaction);
            prioridadeAcordoNivelServicoDao.setTransactionControler(transaction);
            prioridadeServicoUsuarioDao.setTransactionControler(transaction);
            slaRequisitoSlaDao.setTransactionControler(transaction);
            acordoNivelServicoHistoricoDao.setTransactionControler(transaction);
            revisarSlaDao.setTransactionControler(transaction);

            transaction.start();

            acordoNivelServicoDTO = (AcordoNivelServicoDTO) this.getDao().create(acordoNivelServicoDTO);
            acordoNivelServicoDTO.setIdAcordoNivelServico(acordoNivelServicoDTO.getIdAcordoNivelServico());

            if (acordoNivelServicoDTO.getIdAcordoNivelServico() != null) {
                idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();
            }

            for (int i = 1; i <= 5; i++) {
                final TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
                tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                tempoAcordoNivelServicoDTO.setIdFase(1);
                tempoAcordoNivelServicoDTO.setIdPrioridade(i);
                tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhCaptura()[i - 1]);
                tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmCaptura()[i - 1]);
                tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
            }
            for (int i = 1; i <= 5; i++) {
                final TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
                tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                tempoAcordoNivelServicoDTO.setIdFase(2);
                tempoAcordoNivelServicoDTO.setIdPrioridade(i);
                tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhResolucao()[i - 1]);
                tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmResolucao()[i - 1]);
                tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
            }

            final List<PrioridadeAcordoNivelServicoDTO> colUnidades = acordoNivelServicoDTO.getListaPrioridadeUnidade();
            if (colUnidades != null && colUnidades.size() > 0) {
                for (final PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : colUnidades) {
                    prioridadeAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    prioridadeAcordoNivelServicoDTO.setDataInicio(UtilDatas.getDataAtual());
                    prioridadeAcordoNivelServicoDao.create(prioridadeAcordoNivelServicoDTO);
                }
            }

            final List<PrioridadeServicoUsuarioDTO> colUsuarios = acordoNivelServicoDTO.getListaPrioridadeUsuario();
            if (colUsuarios != null && colUsuarios.size() > 0) {
                for (final PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : colUsuarios) {
                    prioridadeServicoUsuarioDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    prioridadeServicoUsuarioDTO.setDataInicio(UtilDatas.getDataAtual());
                    prioridadeServicoUsuarioDao.create(prioridadeServicoUsuarioDTO);
                }
            }

            final List<SlaRequisitoSlaDTO> colSlaRequisitoSla = acordoNivelServicoDTO.getListaSlaRequisitoSlaDTO();
            if (colSlaRequisitoSla != null && colSlaRequisitoSla.size() > 0) {
                for (final SlaRequisitoSlaDTO slaRequisitoSlaDTO : colSlaRequisitoSla) {
                    slaRequisitoSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    slaRequisitoSlaDTO.setDataUltModificacao(UtilDatas.getDataAtual());
                    slaRequisitoSlaDao.create(slaRequisitoSlaDTO);
                }
            }

            final List<RevisarSlaDTO> colRevisarSla = acordoNivelServicoDTO.getListaRevisarSlaDTO();
            if (colRevisarSla != null && colRevisarSla.size() > 0) {
                for (final RevisarSlaDTO revisarSlaDTO : colRevisarSla) {
                    revisarSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    revisarSlaDao.create(revisarSlaDTO);
                }
            }

            final AcordoNivelServicoDTO acordoNivelServicoAux = new AcordoNivelServicoDTO();

            acordoNivelServicoAux.setTempoAuto(acordoNivelServicoDTO.getTempoAuto());
            acordoNivelServicoAux.setIdPrioridadeAuto1(acordoNivelServicoDTO.getIdPrioridadeAuto1());
            acordoNivelServicoAux.setIdGrupo1(acordoNivelServicoDTO.getIdGrupo1());
            acordoNivelServicoAux.setIdAcordoNivelServico(idAcordoNivelServico);
            this.getDao().updateTemposAcoes(acordoNivelServicoAux);

            acordoNivelServicoHistoricoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
            acordoNivelServicoHistoricoDao.create(acordoNivelServicoHistoricoDTO);

            transaction.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                transaction.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return acordoNivelServicoDTO;

    }

    @Override
    public AcordoNivelServicoDTO update(final AcordoNivelServicoDTO acordoNivelServicoDTO, final AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO)
            throws ServiceException, LogicException {

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        final TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
        final PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
        final PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
        final SlaRequisitoSLADao slaRequisitoSlaDao = new SlaRequisitoSLADao();
        final AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
        final RevisarSlaDao revisarSlaDao = new RevisarSlaDao();

        final Integer idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();

        try {
            this.getDao().setTransactionControler(transaction);
            tempoAcordoNivelServicoDao.setTransactionControler(transaction);
            prioridadeAcordoNivelServicoDao.setTransactionControler(transaction);
            prioridadeServicoUsuarioDao.setTransactionControler(transaction);
            slaRequisitoSlaDao.setTransactionControler(transaction);
            acordoNivelServicoHistoricoDao.setTransactionControler(transaction);
            revisarSlaDao.setTransactionControler(transaction);

            transaction.start();

            this.getDao().updateNotNull(acordoNivelServicoDTO);

            tempoAcordoNivelServicoDao.deleteByIdAcordo(idAcordoNivelServico);

            for (int i = 1; i <= 5; i++) {
                final TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
                tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                tempoAcordoNivelServicoDTO.setIdFase(1);
                tempoAcordoNivelServicoDTO.setIdPrioridade(i);
                tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhCaptura()[i - 1]);
                tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmCaptura()[i - 1]);
                tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
            }
            for (int i = 1; i <= 5; i++) {
                final TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
                tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                tempoAcordoNivelServicoDTO.setIdFase(2);
                tempoAcordoNivelServicoDTO.setIdPrioridade(i);
                tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhResolucao()[i - 1]);
                tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmResolucao()[i - 1]);
                tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
            }

            // Atualiza prioridade unidades
            final List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidadeAtual = (List<PrioridadeAcordoNivelServicoDTO>) prioridadeAcordoNivelServicoDao
                    .findByIdAcordoNivelServico(idAcordoNivelServico);
            if (listaPrioridadeUnidadeAtual != null && listaPrioridadeUnidadeAtual.size() > 0) {
                for (final PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : listaPrioridadeUnidadeAtual) {
                    if (prioridadeAcordoNivelServicoDTO.getDataFim() == null) {
                        prioridadeAcordoNivelServicoDTO.setDataFim(UtilDatas.getDataAtual());
                        prioridadeAcordoNivelServicoDao.update(prioridadeAcordoNivelServicoDTO);
                    }
                }
            }
            final List<PrioridadeAcordoNivelServicoDTO> colUnidades = acordoNivelServicoDTO.getListaPrioridadeUnidade();
            if (colUnidades != null && colUnidades.size() > 0) {
                for (final PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : colUnidades) {
                    prioridadeAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    prioridadeAcordoNivelServicoDTO.setDataInicio(UtilDatas.getDataAtual());
                    prioridadeAcordoNivelServicoDTO.setDataFim(null);
                    final PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoAux = (PrioridadeAcordoNivelServicoDTO) prioridadeAcordoNivelServicoDao
                            .restore(prioridadeAcordoNivelServicoDTO);
                    if (prioridadeAcordoNivelServicoAux != null) {
                        prioridadeAcordoNivelServicoDao.update(prioridadeAcordoNivelServicoDTO);
                    } else {
                        prioridadeAcordoNivelServicoDao.create(prioridadeAcordoNivelServicoDTO);
                    }
                }
            }

            // Atualiza prioridade usuários
            final List<PrioridadeServicoUsuarioDTO> listaPrioridadeServicoUsuarioAtual = (List<PrioridadeServicoUsuarioDTO>) prioridadeServicoUsuarioDao
                    .findByIdAcordoNivelServico(idAcordoNivelServico);
            if (listaPrioridadeServicoUsuarioAtual != null && listaPrioridadeServicoUsuarioAtual.size() > 0) {
                for (final PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : listaPrioridadeServicoUsuarioAtual) {
                    if (prioridadeServicoUsuarioDTO.getDataFim() == null) {
                        prioridadeServicoUsuarioDTO.setDataFim(UtilDatas.getDataAtual());
                        prioridadeServicoUsuarioDao.update(prioridadeServicoUsuarioDTO);
                    }
                }
            }
            final List<PrioridadeServicoUsuarioDTO> colUsuarios = acordoNivelServicoDTO.getListaPrioridadeUsuario();
            if (colUsuarios != null && colUsuarios.size() > 0) {
                for (final PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : colUsuarios) {
                    prioridadeServicoUsuarioDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    prioridadeServicoUsuarioDTO.setDataInicio(UtilDatas.getDataAtual());
                    prioridadeServicoUsuarioDTO.setDataFim(null);
                    final PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioAux = (PrioridadeServicoUsuarioDTO) prioridadeServicoUsuarioDao.restore(prioridadeServicoUsuarioDTO);
                    if (prioridadeServicoUsuarioAux != null) {
                        prioridadeServicoUsuarioDao.update(prioridadeServicoUsuarioDTO);
                    } else {
                        prioridadeServicoUsuarioDao.create(prioridadeServicoUsuarioDTO);
                    }
                }
            }

            // Atualiza requisitoSla
            slaRequisitoSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
            final List<SlaRequisitoSlaDTO> colSlaRequisitoSla = acordoNivelServicoDTO.getListaSlaRequisitoSlaDTO();
            if (colSlaRequisitoSla != null && colSlaRequisitoSla.size() > 0) {
                for (final SlaRequisitoSlaDTO slaRequisitoSlaDTO : colSlaRequisitoSla) {
                    slaRequisitoSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    slaRequisitoSlaDTO.setDataUltModificacao(UtilDatas.getDataAtual());
                    slaRequisitoSlaDao.create(slaRequisitoSlaDTO);
                }
            }

            // Atualiza revisarSla
            revisarSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
            final List<RevisarSlaDTO> colRevisarSla = acordoNivelServicoDTO.getListaRevisarSlaDTO();
            if (colRevisarSla != null && colRevisarSla.size() > 0) {
                for (final RevisarSlaDTO revisarSlaDTO : colRevisarSla) {
                    revisarSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
                    revisarSlaDao.create(revisarSlaDTO);
                }
            }

            final AcordoNivelServicoDTO acordoNivelServicoAux = new AcordoNivelServicoDTO();

            acordoNivelServicoAux.setTempoAuto(acordoNivelServicoDTO.getTempoAuto());
            acordoNivelServicoAux.setIdPrioridadeAuto1(acordoNivelServicoDTO.getIdPrioridadeAuto1());
            acordoNivelServicoAux.setIdGrupo1(acordoNivelServicoDTO.getIdGrupo1());
            acordoNivelServicoAux.setIdAcordoNivelServico(idAcordoNivelServico);

            this.getDao().updateTemposAcoes(acordoNivelServicoAux);

            acordoNivelServicoHistoricoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
            acordoNivelServicoHistoricoDao.create(acordoNivelServicoHistoricoDTO);

            transaction.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                transaction.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return acordoNivelServicoDTO;
    }

    @Override
    public void excluir(final AcordoNivelServicoDTO acordoNivelServicoDTO) throws Exception {
        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        final TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
        final PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
        final PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
        final SlaRequisitoSLADao slaRequisitoSlaDao = new SlaRequisitoSLADao();
        final AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
        final RevisarSlaDao revisarSlaDao = new RevisarSlaDao();

        final Integer idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();

        try {
            this.getDao().setTransactionControler(transaction);
            tempoAcordoNivelServicoDao.setTransactionControler(transaction);
            prioridadeAcordoNivelServicoDao.setTransactionControler(transaction);
            prioridadeServicoUsuarioDao.setTransactionControler(transaction);
            slaRequisitoSlaDao.setTransactionControler(transaction);
            acordoNivelServicoHistoricoDao.setTransactionControler(transaction);
            revisarSlaDao.setTransactionControler(transaction);

            transaction.start();

            acordoNivelServicoDTO.setDeleted("y");
            this.getDao().updateNotNull(acordoNivelServicoDTO);

            tempoAcordoNivelServicoDao.deleteByIdAcordo(idAcordoNivelServico);

            final List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidadeAtual = (List<PrioridadeAcordoNivelServicoDTO>) prioridadeAcordoNivelServicoDao
                    .findByIdAcordoNivelServico(idAcordoNivelServico);
            if (listaPrioridadeUnidadeAtual != null && listaPrioridadeUnidadeAtual.size() > 0) {
                for (final PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : listaPrioridadeUnidadeAtual) {
                    if (prioridadeAcordoNivelServicoDTO.getDataFim() == null) {
                        prioridadeAcordoNivelServicoDTO.setDataFim(UtilDatas.getDataAtual());
                        prioridadeAcordoNivelServicoDao.update(prioridadeAcordoNivelServicoDTO);
                    }
                }
            }

            final List<PrioridadeServicoUsuarioDTO> listaPrioridadeServicoUsuarioAtual = (List<PrioridadeServicoUsuarioDTO>) prioridadeServicoUsuarioDao
                    .findByIdAcordoNivelServico(idAcordoNivelServico);
            if (listaPrioridadeServicoUsuarioAtual != null && listaPrioridadeServicoUsuarioAtual.size() > 0) {
                for (final PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : listaPrioridadeServicoUsuarioAtual) {
                    if (prioridadeServicoUsuarioDTO.getDataFim() == null) {
                        prioridadeServicoUsuarioDTO.setDataFim(UtilDatas.getDataAtual());
                        prioridadeServicoUsuarioDao.update(prioridadeServicoUsuarioDTO);
                    }
                }
            }

            slaRequisitoSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);

            revisarSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);

            transaction.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                transaction.close();
            } catch (final PersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<AcordoNivelServicoDTO> findIdEmailByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception {
        try {
            return this.getDao().findIdEmailByIdSolicitacaoServico(idSolicitacaoServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String verificaIdAcordoNivelServico(final HashMap mapFields) throws Exception {
        List<AcordoNivelServicoDTO> listaAcordoNivelServicoDTO = null;
        String id = mapFields.get("IDACORDONIVELSERVICO").toString().trim();
        if (id == null || id.equals("")) {
            id = "0";
        }
        if (UtilStrings.soContemNumeros(id)) {
            final Integer idAcordoNivelServico = Integer.parseInt(id);
            listaAcordoNivelServicoDTO = this.getDao().findByIdAcordoSemVinculacaoDireta(idAcordoNivelServico);
        } else {
            listaAcordoNivelServicoDTO = this.getDao().findByTituloSLA(id);
        }
        if (listaAcordoNivelServicoDTO != null && listaAcordoNivelServicoDTO.size() > 0) {
            return String.valueOf(listaAcordoNivelServicoDTO.get(0).getIdAcordoNivelServico());
        }
        return "0";
    }

}
