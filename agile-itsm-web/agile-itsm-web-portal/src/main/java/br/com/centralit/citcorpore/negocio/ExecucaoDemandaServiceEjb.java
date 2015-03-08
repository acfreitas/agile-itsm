package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoDemandaDTO;
import br.com.centralit.citcorpore.bean.HistoricoExecucaoDTO;
import br.com.centralit.citcorpore.integracao.ExecucaoDemandaDao;
import br.com.centralit.citcorpore.integracao.HistoricoExecucaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class ExecucaoDemandaServiceEjb extends CrudServiceImpl implements ExecucaoDemandaService {

    private ExecucaoDemandaDao dao;

    @Override
    protected ExecucaoDemandaDao getDao() {
        if (dao == null) {
            dao = new ExecucaoDemandaDao();
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
    public Collection getAtividadesByGrupoAndPessoa(final Integer idEmpregado, final String[] grupo) throws LogicException, ServiceException {
        try {
            final List lst = (List) this.getDao().getAtividadesByGrupoAndPessoa(idEmpregado, grupo);
            final Collection colRetorno = new ArrayList();
            ExecucaoDemandaDTO exec = new ExecucaoDemandaDTO();
            if (lst != null) {
                for (int i = 0; i < lst.size(); i++) {
                    exec = (ExecucaoDemandaDTO) lst.get(i);
                    if (!exec.getSituacao().equalsIgnoreCase("F")) {
                        colRetorno.add(lst.get(i));
                    }
                }
            }
            return colRetorno;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean temAtividadeNaSequencia(final IDto bean) throws LogicException, ServiceException {
        /*
         * FluxoDao fluxoDao = new FluxoDao();
         * ExecucaoDemandaDTO execDemandaBean = (ExecucaoDemandaDTO)bean;
         * FluxoDTO fluxo = null;
         * try {
         * fluxo = fluxoDao.getNextAtividadeByFluxo(execDemandaBean.getIdFluxo(), execDemandaBean.getIdAtividade());
         * } catch (Exception e1) {
         * e1.printStackTrace();
         * throw new ServiceException(e1);
         * }
         * if (fluxo == null){
         * return false;
         * }
         */
        return true;
    }

    @Override
    public void updateAtribuir(final IDto bean) throws LogicException, ServiceException {
        /*
         * //Instancia Objeto controlador de transacao
         * ExecucaoDemandaDao crudDao = (ExecucaoDemandaDao)getDao();
         * HistoricoExecucaoDao historicoExecucaoDao = new HistoricoExecucaoDao();
         * FluxoDao fluxoDao = new FluxoDao();
         * TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
         * ExecucaoDemandaDTO execDemandaBean = (ExecucaoDemandaDTO)bean;
         * FluxoDTO fluxo = null;
         * try {
         * fluxo = fluxoDao.getNextAtividadeByFluxo(execDemandaBean.getIdFluxo(), execDemandaBean.getIdAtividade());
         * } catch (Exception e1) {
         * e1.printStackTrace();
         * throw new ServiceException(e1);
         * }
         * if (fluxo == null){
         * throw new LogicException("Não existe Atividade na Sequencia para este Fluxo! Não é possível efetuar atribuição!");
         * }
         * try{
         * //Seta o TransactionController para os DAOs
         * crudDao.setTransactionControler(tc);
         * historicoExecucaoDao.setTransactionControler(tc);
         * //Inicia transacao
         * tc.start();
         * //Executa operacoes pertinentes ao negocio.
         * crudDao.updateAtribuir(bean);
         * HistoricoExecucaoDTO historicoExecucaoBean = new HistoricoExecucaoDTO();
         * historicoExecucaoBean.setData(UtilDatas.getDataAtual());
         * historicoExecucaoBean.setHora(new Long(UtilDatas.getDataHoraAtual().getTime()));
         * historicoExecucaoBean.setIdExecucao(execDemandaBean.getIdExecucaoAtribuir());
         * historicoExecucaoBean.setSituacao("N");
         * historicoExecucaoBean.setIdEmpregadoExecutor(execDemandaBean.getIdEmpregadoLogado());
         * historicoExecucaoBean.setDetalhamento("Atribuição da demanda");
         * historicoExecucaoDao.create(historicoExecucaoBean);
         * ExecucaoDemandaDTO execucaoDemanda = new ExecucaoDemandaDTO();
         * execucaoDemanda.setIdAtividade(fluxo.getIdAtividade());
         * execucaoDemanda.setIdDemanda(execDemandaBean.getIdDemanda());
         * execucaoDemanda.setSituacao("N");
         * execucaoDemanda.setGrupoExecutor(fluxo.getGrupoExecutor());
         * execucaoDemanda.setIdEmpregadoExecutor(execDemandaBean.getIdEmpregadoExecutor());
         * execucaoDemanda.setQtdeHoras(execDemandaBean.getQtdeHoras());
         * execucaoDemanda.setTerminoPrevisto(execDemandaBean.getTerminoPrevisto());
         * execucaoDemanda = (ExecucaoDemandaDTO) crudDao.create(execucaoDemanda);
         * AtividadeFluxoDao atividadeFluxoDao = new AtividadeFluxoDao();
         * AtividadeFluxoDTO atividadeFluxo = new AtividadeFluxoDTO();
         * atividadeFluxo.setIdAtividade(execDemandaBean.getIdAtividade());
         * List colFluxosCriarDemanda = (List) atividadeFluxoDao.find(atividadeFluxo);
         * if (colFluxosCriarDemanda != null){
         * for (int i = 0; i < colFluxosCriarDemanda.size(); i++){
         * atividadeFluxo = (AtividadeFluxoDTO)colFluxosCriarDemanda.get(i);
         * fluxo = fluxoDao.getNextAtividadeByFluxo(atividadeFluxo.getIdFluxo(), new Integer(0));
         * execucaoDemanda = new ExecucaoDemandaDTO();
         * execucaoDemanda.setIdAtividade(fluxo.getIdAtividade());
         * execucaoDemanda.setIdDemanda(execDemandaBean.getIdDemanda());
         * execucaoDemanda.setSituacao("N");
         * execucaoDemanda.setGrupoExecutor(fluxo.getGrupoExecutor());
         * execucaoDemanda.setIdEmpregadoExecutor(null);
         * execucaoDemanda.setQtdeHoras(null);
         * execucaoDemanda = (ExecucaoDemandaDTO) crudDao.create(execucaoDemanda);
         * }
         * }
         * //Faz commit e fecha a transacao.
         * tc.commit();
         * tc.close();
         * }catch(Exception e){
         * this.rollbackTransaction(tc, e);
         * }
         */
    }

    @Override
    public void updateStatus(final IDto bean) throws LogicException, ServiceException {
        // Instancia Objeto controlador de transacao
        final ExecucaoDemandaDao crudDao = this.getDao();
        final HistoricoExecucaoDao historicoExecucaoDao = new HistoricoExecucaoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final ExecucaoDemandaDTO execDemandaBean = (ExecucaoDemandaDTO) bean;
        try {
            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            historicoExecucaoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.updateStatus(bean);

            final HistoricoExecucaoDTO historicoExecucaoBean = new HistoricoExecucaoDTO();
            historicoExecucaoBean.setData(UtilDatas.getDataAtual());
            historicoExecucaoBean.setHora(new Long(UtilDatas.getDataHoraAtual().getTime()));
            historicoExecucaoBean.setIdExecucao(execDemandaBean.getIdExecucao());
            historicoExecucaoBean.setSituacao(execDemandaBean.getSituacao());
            historicoExecucaoBean.setIdEmpregadoExecutor(execDemandaBean.getIdEmpregadoLogado());
            historicoExecucaoBean.setDetalhamento("Alteração de Situação");

            historicoExecucaoDao.create(historicoExecucaoBean);

            // Faz commit e fecha a transacao.
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public void updateFinalizar(final IDto bean) throws LogicException, ServiceException {
        /*
         * //Instancia Objeto controlador de transacao
         * ExecucaoDemandaDao crudDao = (ExecucaoDemandaDao)getDao();
         * HistoricoExecucaoDao historicoExecucaoDao = new HistoricoExecucaoDao();
         * DemandaDao demandaDao = new DemandaDao();
         * FluxoDao fluxoDao = new FluxoDao();
         * OSDao osDao = new OSDao();
         * TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
         * ExecucaoDemandaDTO execDemandaBean = (ExecucaoDemandaDTO)bean;
         * try{
         * //Seta o TransactionController para os DAOs
         * crudDao.setTransactionControler(tc);
         * historicoExecucaoDao.setTransactionControler(tc);
         * demandaDao.setTransactionControler(tc);
         * osDao.setTransactionControler(tc);
         * //Inicia transacao
         * tc.start();
         * //Executa operacoes pertinentes ao negocio.
         * crudDao.updateStatus(bean);
         * HistoricoExecucaoDTO historicoExecucaoBean = new HistoricoExecucaoDTO();
         * historicoExecucaoBean.setData(UtilDatas.getDataAtual());
         * historicoExecucaoBean.setHora(new Long(UtilDatas.getDataHoraAtual().getTime()));
         * historicoExecucaoBean.setIdExecucao(execDemandaBean.getIdExecucao());
         * historicoExecucaoBean.setSituacao(execDemandaBean.getSituacao());
         * historicoExecucaoBean.setIdEmpregadoExecutor(execDemandaBean.getIdEmpregadoLogado());
         * historicoExecucaoBean.setDetalhamento("Alteração de Situação");
         * historicoExecucaoDao.create(historicoExecucaoBean);
         * FluxoDTO fluxo = fluxoDao.getNextAtividadeByFluxo(execDemandaBean.getIdFluxo(), execDemandaBean.getIdAtividade());
         * if (fluxo == null){ //Nao ha mais atividades no fluxo, entao finaliza.
         * DemandaDTO demandaDTO = new DemandaDTO();
         * demandaDTO.setIdDemanda(execDemandaBean.getIdDemanda());
         * demandaDTO.setIdSituacaoDemanda(DemandaDTO.SITUACAO_FINALIZADA);
         * demandaDao.updateStatus(demandaDTO);
         * demandaDTO = (DemandaDTO) demandaDao.restore(demandaDTO);
         * if (demandaDTO != null){
         * if (demandaDTO.getIdOS() != null){
         * Collection colDemandas = demandaDao.findByIdOS(demandaDTO.getIdOS());
         * if (colDemandas != null){
         * boolean bTodasFechadas = true;
         * for(Iterator it = colDemandas.iterator(); it.hasNext();){
         * DemandaDTO demandaAux = (DemandaDTO)it.next();
         * if (demandaAux.getIdSituacaoDemanda().intValue() != DemandaDTO.SITUACAO_FINALIZADA.intValue()){
         * bTodasFechadas = false;
         * }
         * }
         * if (bTodasFechadas){
         * OSDTO osDto = new OSDTO();
         * osDto.setIdOS(demandaDTO.getIdOS());
         * osDto.setSituacaoOS(5);
         * osDao.updateNotNull(osDto);
         * }
         * }
         * }
         * }
         * }else{
         * ExecucaoDemandaDTO execucaoDemanda = new ExecucaoDemandaDTO();
         * execucaoDemanda.setIdAtividade(fluxo.getIdAtividade());
         * execucaoDemanda.setIdDemanda(execDemandaBean.getIdDemanda());
         * execucaoDemanda.setSituacao("N");
         * execucaoDemanda.setGrupoExecutor(fluxo.getGrupoExecutor());
         * execucaoDemanda.setIdEmpregadoExecutor(null);
         * execucaoDemanda.setQtdeHoras(null);
         * execucaoDemanda = (ExecucaoDemandaDTO) crudDao.create(execucaoDemanda);
         * }
         * AtividadeFluxoDao atividadeFluxoDao = new AtividadeFluxoDao();
         * AtividadeFluxoDTO atividadeFluxo = new AtividadeFluxoDTO();
         * atividadeFluxo.setIdAtividade(execDemandaBean.getIdAtividade());
         * List colFluxosCriarDemanda = (List) atividadeFluxoDao.find(atividadeFluxo);
         * if (colFluxosCriarDemanda != null){
         * for (int i = 0; i < colFluxosCriarDemanda.size(); i++){
         * atividadeFluxo = (AtividadeFluxoDTO)colFluxosCriarDemanda.get(i);
         * fluxo = fluxoDao.getNextAtividadeByFluxo(atividadeFluxo.getIdFluxo(), new Integer(0));
         * ExecucaoDemandaDTO execucaoDemanda = new ExecucaoDemandaDTO();
         * execucaoDemanda = new ExecucaoDemandaDTO();
         * execucaoDemanda.setIdAtividade(fluxo.getIdAtividade());
         * execucaoDemanda.setIdDemanda(execDemandaBean.getIdDemanda());
         * execucaoDemanda.setSituacao("N");
         * execucaoDemanda.setGrupoExecutor(fluxo.getGrupoExecutor());
         * execucaoDemanda.setIdEmpregadoExecutor(null);
         * execucaoDemanda.setQtdeHoras(null);
         * execucaoDemanda = (ExecucaoDemandaDTO) crudDao.create(execucaoDemanda);
         * }
         * }
         * //Faz commit e fecha a transacao.
         * tc.commit();
         * tc.close();
         * }catch(Exception e){
         * this.rollbackTransaction(tc, e);
         * }
         */
    }

}
