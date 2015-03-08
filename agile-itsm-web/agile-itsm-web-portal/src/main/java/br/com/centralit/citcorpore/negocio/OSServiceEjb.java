package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.RelatorioAcompanhamentoDTO;
import br.com.centralit.citcorpore.bean.RelatorioOrdemServicoUstDTO;
import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.GlosaOSDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

@SuppressWarnings({"rawtypes", "unchecked"})
public class OSServiceEjb extends CrudServiceImpl implements OSService {

    private OSDao oSDao;

    @Override
    protected OSDao getDao() {
        if (oSDao == null) {
            oSDao = new OSDao();
        }
        return oSDao;
    }

    @Override
    public Collection findByIdContratoAndSituacao(final Integer parm, final Integer sit) throws Exception {
        try {
            return this.getDao().findByIdContratoAndSituacao(parm, sit);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdClassificacaoOS(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdClassificacaoOS(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdClassificacaoOS(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdClassificacaoOS(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByAno(final Integer parm) throws Exception {
        try {
            return this.getDao().findByAno(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByAno(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByAno(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        final CrudDAO crudDao = this.getDao();
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);
            final OSDTO osDTO = (OSDTO) model;
            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            atividadesOSDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            if (osDTO.getColItens() != null) {
                int i = 0;
                for (final Iterator it = osDTO.getColItens().iterator(); it.hasNext();) {
                    i++;
                    final AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
                    atividadesOSDTO.setIdOS(osDTO.getIdOS());
                    atividadesOSDTO.setSequencia(i);
                    atividadesOSDTO.setDeleted("N");
                    atividadesOSDao.create(atividadesOSDTO);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public boolean retornaRegistroOsPai(final OSDTO osDTO) throws Exception {
        Collection col = null;
        try {
            col = this.getDao().retornaSeExisteOSFilha(osDTO.getIdOS());
            if (col != null && col.size() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void retornaAtividadeCadastradaByPai(final OSDTO osDTO) throws Exception {
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final Collection colAtividadeOs = atividadesOSDao.findByIdOS(osDTO.getIdOSPai());

        if (colAtividadeOs != null && colAtividadeOs.size() > 0) {
            final List<AtividadesOSDTO> listAtividadeOs = (List<AtividadesOSDTO>) colAtividadeOs;
            final Collection listFinal = new ArrayList();
            for (final AtividadesOSDTO atividadesOSDTO : listAtividadeOs) {
                if (osDTO.getQuantidade() != null) {
                    atividadesOSDTO.setCustoAtividade(atividadesOSDTO.getCustoAtividade() * osDTO.getQuantidade());
                }

                listFinal.add(atividadesOSDTO);
            }
            osDTO.setColItens(listFinal);
        }
    }

    @Override
    public void duplicarOS(final Integer idOS) throws Exception {
        final OSDao osDao = this.getDao();
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final GlosaOSDao glosaOSDao = new GlosaOSDao();

        OSDTO osDto = new OSDTO();

        osDto.setIdOS(idOS);

        osDto = (OSDTO) osDao.restore(osDto);

        osDto.setDataInicio(null);
        osDto.setDataFim(null);
        osDto.setSituacaoOS(null);
        osDto.setNumero(null);

        final Collection<GlosaOSDTO> glosasOS = glosaOSDao.findByIdOs(idOS);

        if (glosasOS != null && !glosasOS.isEmpty()) {

            if (osDto.getQuantidadeGlosasAnterior() != null) {
                osDto.setQuantidadeGlosasAnterior(glosasOS.size() + osDto.getQuantidadeGlosasAnterior());
            } else {
                osDto.setQuantidadeGlosasAnterior(glosasOS.size());
            }
        }

        final Collection<AtividadesOSDTO> atividadesOS = atividadesOSDao.findByIdOS(idOS);

        final TransactionControler transaction = new TransactionControlerImpl(osDao.getAliasDB());

        try {
            osDao.setTransactionControler(transaction);
            atividadesOSDao.setTransactionControler(transaction);

            transaction.start();

            osDto.setIdOS(null);
            osDto = (OSDTO) osDao.create(osDto);

            if (atividadesOS != null && !atividadesOS.isEmpty()) {

                for (final AtividadesOSDTO atividade : atividadesOS) {

                    atividade.setIdAtividadesOS(null);

                    atividade.setGlosaAtividade(null);

                    atividade.setIdOS(osDto.getIdOS());

                    atividadesOSDao.create(atividade);
                }
            }

            transaction.commit();
            transaction.close();

        } catch (final Exception e) {
            this.rollbackTransaction(transaction, e);
        }

    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);
            final OSDTO osDTO = (OSDTO) model;

            final OSDTO osDTOAux = (OSDTO) crudDao.restore(osDTO);

            if (!osDTOAux.getIdServicoContrato().equals(osDTO.getIdServicoContrato())) {
                osDTO.setQuantidadeGlosasAnterior(null);
            } else {
                osDTO.setQuantidadeGlosasAnterior(osDTOAux.getQuantidadeGlosasAnterior());
            }

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            atividadesOSDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(osDTO);
            atividadesOSDao.deleteByIdOS(osDTO.getIdOS());
            if (osDTO.getColItens() != null) {
                int i = 0;
                for (final Iterator it = osDTO.getColItens().iterator(); it.hasNext();) {
                    i++;
                    final AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
                    atividadesOSDTO.setIdOS(osDTO.getIdOS());
                    atividadesOSDTO.setSequencia(i);
                    atividadesOSDTO.setDeleted("N");
                    atividadesOSDao.create(atividadesOSDTO);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateSituacao(final OSDTO os, final Collection colGlosas, final Collection colItens) throws Exception {
        final OSDao dao = this.getDao();
        final GlosaOSDao glosaOSDao = new GlosaOSDao();
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
        new ArrayList();
        try {
            dao.setTransactionControler(tc);
            glosaOSDao.setTransactionControler(tc);
            atividadesOSDao.setTransactionControler(tc);

            tc.start();

            if (os.getSituacaoOS() != null) {
                dao.updateSituacao(os.getIdOS(), os.getSituacaoOS());
            }

            if (os.getObsFinalizacao() != null) {
                dao.updateObsFinal(os.getIdOS(), os.getObsFinalizacao());
            }

            if (os.getQuantidade() != null) {
                dao.updateQuantidade(os.getIdOS(), os.getQuantidade());
            }

            if (colItens != null) {
                for (final Iterator it = colItens.iterator(); it.hasNext();) {
                    final AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
                    AtividadesOSDTO atividadesOSAux = new AtividadesOSDTO();
                    atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
                    atividadesOSAux.setGlosaAtividade(atividadesOSDTO.getGlosaAtividade());
                    atividadesOSAux.setQtdeExecutada(atividadesOSDTO.getQtdeExecutada());
                    atividadesOSAux.setCustoAtividade(atividadesOSDTO.getCustoAtividade());
                    if (atividadesOSDTO.getGlosaAtividade() != null || atividadesOSDTO.getQtdeExecutada() != null) {
                        atividadesOSDao.updateNotNull(atividadesOSAux);
                    } else if (atividadesOSDTO.getCustoAtividade() != null && os.getIdOSPai() != null) {
                        atividadesOSAux = null;
                        atividadesOSAux = new AtividadesOSDTO();
                        atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
                        atividadesOSAux.setCustoAtividade(atividadesOSDTO.getCustoAtividade());
                        atividadesOSDao.updateNotNull(atividadesOSAux);
                    }
                }
            }

            if (colGlosas != null) {
                for (final Iterator it = colGlosas.iterator(); it.hasNext();) {
                    GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
                    glosaOSDTO.setIdOs(os.getIdOS());
                    glosaOSDTO.setDataUltModificacao(UtilDatas.getDataAtual());
                    if (glosaOSDTO.getIdGlosaOS() == null || glosaOSDTO.getIdGlosaOS().intValue() == 0) {
                        glosaOSDTO.setDataCriacao(UtilDatas.getDataAtual());
                        glosaOSDTO = (GlosaOSDTO) glosaOSDao.create(glosaOSDTO);
                    } else {
                        GlosaOSDTO glosaOSAux = new GlosaOSDTO();
                        glosaOSAux.setIdGlosaOS(glosaOSDTO.getIdGlosaOS());
                        glosaOSAux = (GlosaOSDTO) glosaOSDao.restore(glosaOSAux);
                        glosaOSDTO.setDataCriacao(glosaOSAux.getDataCriacao());
                        glosaOSDao.update(glosaOSDTO);
                    }
                    // colIdsNaoApagar.add(glosaOSDTO.getIdGlosaOS());
                }/*
                  * String notIn = "";
                  * for (Iterator it = colIdsNaoApagar.iterator(); it.hasNext();) {
                  * Integer id = (Integer) it.next();
                  * if (!notIn.equalsIgnoreCase("")) {
                  * notIn += ",";
                  * }
                  * notIn += "" + id.intValue();
                  * }
                  * if (!notIn.equalsIgnoreCase("")) {
                  * glosaOSDao.deleteByOsNotIn(os.getIdOS(), notIn);
                  * }
                  */
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

    @Override
    public void updateSituacao(final Integer idOs, final Integer situacao, final Collection colGlosasOS, final Collection colItens, final String obsFinalizacao) throws Exception {
        final OSDao dao = this.getDao();
        final GlosaOSDao glosaOSDao = new GlosaOSDao();
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
        new ArrayList();
        try {
            dao.setTransactionControler(tc);
            glosaOSDao.setTransactionControler(tc);
            atividadesOSDao.setTransactionControler(tc);

            tc.start();

            dao.updateSituacao(idOs, situacao);
            dao.updateObsFinal(idOs, obsFinalizacao);

            if (colItens != null) {
                for (final Iterator it = colItens.iterator(); it.hasNext();) {
                    final AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
                    final AtividadesOSDTO atividadesOSAux = new AtividadesOSDTO();
                    atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
                    atividadesOSAux.setGlosaAtividade(atividadesOSDTO.getGlosaAtividade());
                    atividadesOSAux.setQtdeExecutada(atividadesOSDTO.getQtdeExecutada());
                    if (atividadesOSDTO.getGlosaAtividade() != null || atividadesOSDTO.getQtdeExecutada() != null) {
                        atividadesOSDao.updateNotNull(atividadesOSAux);
                    }
                }
            }

            if (colGlosasOS != null) {
                for (final Iterator it = colGlosasOS.iterator(); it.hasNext();) {
                    GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
                    glosaOSDTO.setIdOs(idOs);
                    glosaOSDTO.setDataUltModificacao(UtilDatas.getDataAtual());
                    if (glosaOSDTO.getIdGlosaOS() == null || glosaOSDTO.getIdGlosaOS().intValue() == 0) {
                        glosaOSDTO.setDataCriacao(UtilDatas.getDataAtual());
                        glosaOSDTO = (GlosaOSDTO) glosaOSDao.create(glosaOSDTO);
                    } else {
                        GlosaOSDTO glosaOSAux = new GlosaOSDTO();
                        glosaOSAux.setIdGlosaOS(glosaOSDTO.getIdGlosaOS());
                        glosaOSAux = (GlosaOSDTO) glosaOSDao.restore(glosaOSAux);
                        glosaOSDTO.setDataCriacao(glosaOSAux.getDataCriacao());
                        glosaOSDao.update(glosaOSDTO);
                    }
                    // colIdsNaoApagar.add(glosaOSDTO.getIdGlosaOS());
                }/*
                  * String notIn = "";
                  * for (Iterator it = colIdsNaoApagar.iterator(); it.hasNext();) {
                  * Integer id = (Integer) it.next();
                  * if (!notIn.equalsIgnoreCase("")) {
                  * notIn += ",";
                  * }
                  * notIn += "" + id.intValue();
                  * }
                  * if (!notIn.equalsIgnoreCase("")) {
                  * glosaOSDao.deleteByOsNotIn(idOs, notIn);
                  * }
                  */
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public Collection findByIdContratoAndPeriodoAndSituacao(final Integer idContrato, final Date dataInicio, final Date dataFim, final Integer[] situacao, final Integer idospai)
            throws Exception {
        final OSDao dao = this.getDao();
        return dao.findByIdContratoAndPeriodoAndSituacao(idContrato, dataInicio, dataFim, situacao, idospai);
    }

    @Override
    public Collection findByIdContratoAndPeriodoAndSituacao(final Integer idContrato, final Date dataInicio, final Date dataFim, final Integer[] situacao) throws Exception {
        final OSDao dao = this.getDao();
        return dao.findByIdContratoAndPeriodoAndSituacao(idContrato, dataInicio, dataFim, situacao);
    }

    @Override
    public Collection listOSHomologadasENaoAssociadasFatura(final Integer idContrato) throws Exception {
        final OSDao dao = this.getDao();
        return dao.listOSHomologadasENaoAssociadasFatura(idContrato);
    }

    @Override
    public Collection listOSByIds(final Integer idContrato, final Integer[] idOSs) throws Exception {
        final OSDao dao = this.getDao();
        return dao.listOSByIds(idContrato, idOSs);
    }

    @Override
    public Collection listOSAssociadasFatura(final Integer idFatura) throws Exception {
        final OSDao dao = this.getDao();
        return dao.listOSAssociadasFatura(idFatura);
    }

    @Override
    public Collection listOSByIdAtividadePeriodica(final Integer idatividade) throws Exception {
        final OSDao dao = this.getDao();
        return dao.listOSByIdAtividadePeriodica(idatividade);
    }

    @Override
    public Collection listAtividadePeridodicaByIdos(final Integer idos) throws Exception {
        final OSDao dao = this.getDao();
        return dao.listAtividadePeridodicaByIdos(idos);
    }

    @Override
    public Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadeOrdemServicoPorPeriodo(final RelatorioOrdemServicoUstDTO relatorio) throws Exception {
        final OSDao dao = this.getDao();
        Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadePorPerido = null;
        try {
            listaCustoAtividadePorPerido = dao.listaCustoAtividadeOrdemServicoPorPeriodo(relatorio);
            if (listaCustoAtividadePorPerido != null) {
                for (final RelatorioOrdemServicoUstDTO ust : listaCustoAtividadePorPerido) {
                    if (ust.getPeriodo().equals(1)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.janeiro"));
                    }
                    if (ust.getPeriodo().equals(2)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.fevereiro"));
                    }
                    if (ust.getPeriodo().equals(3)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.marco"));
                    }
                    if (ust.getPeriodo().equals(4)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.abril"));
                    }
                    if (ust.getPeriodo().equals(5)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.maio"));
                    }
                    if (ust.getPeriodo().equals(6)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.junho"));
                    }
                    if (ust.getPeriodo().equals(7)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.julho"));
                    }
                    if (ust.getPeriodo().equals(8)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.agosto"));
                    }
                    if (ust.getPeriodo().equals(9)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.setembro"));
                    }
                    if (ust.getPeriodo().equals(10)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.outubro"));
                    }
                    if (ust.getPeriodo().equals(11)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.novembro"));
                    }
                    if (ust.getPeriodo().equals(12)) {
                        ust.setMes(this.i18nMessage("citcorpore.texto.mes.dezembro"));
                    }

                    final String valor1 = UtilFormatacao.formatDoubleSemPontos(ust.getCustoAtividade(), 2);
                    ust.setCustoAtividadeFormatada(valor1);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listaCustoAtividadePorPerido;
    }

    @Override
    public Collection<RelatorioOrdemServicoUstDTO> listaAnos() throws Exception {
        final OSDao osDao = this.getDao();
        return osDao.listaAnos();
    }

    @Override
    public Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato(final RelatorioAcompanhamentoDTO relatorio) throws Exception {

        ContratoDTO contratoDto = new ContratoDTO();

        final OSDao osDao = this.getDao();

        final ContratoDao contratoDao = new ContratoDao();

        contratoDto.setIdContrato(relatorio.getIdContrato());

        contratoDto = (ContratoDTO) contratoDao.restore(contratoDto);

        List<RelatorioAcompanhamentoDTO> listaDeMesesAcompanhamentoPorPeriodoDoContrato = new ArrayList<RelatorioAcompanhamentoDTO>();

        final List<RelatorioAcompanhamentoDTO> listRelatorioAcompanhamentoFinal = new ArrayList<RelatorioAcompanhamentoDTO>();

        final Calendar dataInicioContrato = Calendar.getInstance();

        dataInicioContrato.setTime(contratoDto.getDataContrato());

        final Integer anoInicial = dataInicioContrato.get(Calendar.YEAR);

        final Integer mesInicial = dataInicioContrato.get(Calendar.MONTH) + 1;

        relatorio.setDataInicioContrato(contratoDto.getDataContrato());

        relatorio.setDataFimContrato(contratoDto.getDataFimContrato());

        final Integer quantidadeMesesPeriodoContratro = this.verificarPeriodoDeVigenciaContrato(relatorio);
        Integer cont = 0;

        try {

            listaDeMesesAcompanhamentoPorPeriodoDoContrato = (List<RelatorioAcompanhamentoDTO>) osDao.listaAcompanhamentoPorPeriodoDoContrato(relatorio);

            if (quantidadeMesesPeriodoContratro != null && quantidadeMesesPeriodoContratro > 0) {

                int mes = 0;
                Integer ano = anoInicial;
                for (int numeroMes = mesInicial; numeroMes < quantidadeMesesPeriodoContratro + mesInicial; numeroMes++) {

                    mes = numeroMes;

                    if (numeroMes > 12) {

                        Integer diferenca = 0;
                        diferenca = numeroMes / 12;

                        mes = numeroMes - diferenca * 12;

                        if (mes == 0) {
                            mes = 12;
                            diferenca = diferenca - 1;
                        }
                        ano = anoInicial + diferenca;
                    }

                    final RelatorioAcompanhamentoDTO relatorioAcompanhamentoDto = new RelatorioAcompanhamentoDTO();

                    relatorioAcompanhamentoDto.setMes(this.i18nMessage(UtilDatas.getDescricaoMes(mes)) + " / " + ano);

                    /* relatorioAcompanhamentoDto.setNumeroMes(mes); */

                    relatorioAcompanhamentoDto.setNumeroMesDouble((double) mes);

                    relatorioAcompanhamentoDto.setAnoDouble((double) ano);

                    if (listaDeMesesAcompanhamentoPorPeriodoDoContrato != null && !listaDeMesesAcompanhamentoPorPeriodoDoContrato.isEmpty()) {

                        for (final RelatorioAcompanhamentoDTO mesComAtividadeRealizada : listaDeMesesAcompanhamentoPorPeriodoDoContrato) {

                            if (mesComAtividadeRealizada.getNumeroMes().equals(relatorioAcompanhamentoDto.getNumeroMes())
                                    && mesComAtividadeRealizada.getAno().equals(relatorioAcompanhamentoDto.getAno())) {
                                cont = cont + 1;
                                relatorioAcompanhamentoDto.setCustoAtividade(mesComAtividadeRealizada.getCustoAtividade());

                            }
                            relatorioAcompanhamentoDto.setValorEstimadoContrato(mesComAtividadeRealizada.getValorEstimadoContrato());
                            relatorioAcompanhamentoDto.setQuantidadePeriodoRealizado(cont);
                            if (this.verificarPeriodoDeVigenciaContrato(mesComAtividadeRealizada) != null) {
                                relatorioAcompanhamentoDto.setPeridoVigenciaContrato(this.verificarPeriodoDeVigenciaContrato(mesComAtividadeRealizada));
                            }

                        }
                    }

                    listRelatorioAcompanhamentoFinal.add(relatorioAcompanhamentoDto);

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listRelatorioAcompanhamentoFinal;
    }

    public Integer verificarPeriodoDeVigenciaContrato(final RelatorioAcompanhamentoDTO relatorio) {
        Integer quantidade = 0;
        quantidade = UtilDatas.getDifMeses(relatorio.getDataInicioContrato(), relatorio.getDataFimContrato());
        if (quantidade != null) {
            return quantidade;
        } else {
            return null;
        }
    }

    @Override
    public void cancelaOsFilhas(final OSDTO os) throws Exception {
        try {
            if (os.getSituacaoOS() != null) {
                this.getDao().cancelaOSFilhas(os);
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection retornaSeExisteOSFilha(final OSDTO os) throws Exception {
        try {
            return this.getDao().retornaSeExisteOSFilha(os.getIdOS());
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);
            final OSDTO osDTO = (OSDTO) model;

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            atividadesOSDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            atividadesOSDao.deleteByIdOS(osDTO.getIdOS());
            crudDao.delete(osDTO);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
