package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.ControleQuestionariosDTO;
import br.com.centralit.citcorpore.integracao.ContratoQuestionariosDao;
import br.com.centralit.citcorpore.integracao.ControleQuestionariosDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.negocio.RespostaItemQuestionarioServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class ContratoQuestionariosServiceEjb extends CrudServiceImpl implements ContratoQuestionariosService {

    private ContratoQuestionariosDao dao;

    @Override
    protected ContratoQuestionariosDao getDao() {
        if (dao == null) {
            dao = new ContratoQuestionariosDao();
        }
        return dao;
    }

    @Override
    public Collection listByIdContratoAndAba(final Integer idContrato, final String aba) throws Exception {
        return this.getDao().listByIdContratoAndAba(idContrato, aba);
    }

    @Override
    public Collection listByIdContratoAndAbaOrdemCrescente(final Integer idContrato, final String aba) throws Exception {
        return this.getDao().listByIdContratoAndAbaOrdemCrescente(idContrato, aba);
    }

    @Override
    public ContratoQuestionariosDTO getUltimoByIdContratoAndAba(final Integer idContrato, final String aba) throws Exception {
        return this.getDao().getUltimoByIdContratoAndAba(idContrato, aba);
    }

    @Override
    public ContratoQuestionariosDTO getUltimoByIdContratoAndAbaAndPeriodo(final Integer idContrato, final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getDao().getUltimoByIdContratoAndAbaAndPeriodo(idContrato, aba, dataInicio, dataFim);
    }

    @Override
    public Collection listByIdContratoAndQuestionario(final Integer idQuestionario, final Integer idContrato) throws Exception {
        return this.getDao().listByIdContratoAndQuestionario(idQuestionario, idContrato);
    }

    @Override
    public Collection listByIdContrato(final Integer idContrato) throws Exception {
        return this.getDao().listByIdContrato(idContrato);
    }

    @Override
    public Collection listByIdContratoOrderDecrescente(final Integer idContrato) throws Exception {
        return this.getDao().listByIdContratoOrderDecrescente(idContrato);
    }

    @Override
    public Collection listByIdContratoOrderIdDecrescente(final Integer idContrato) throws Exception {
        return this.getDao().listByIdContratoOrderIdDecrescente(idContrato);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAba(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo, final String aba)
            throws Exception {
        return this.getDao().getQuantidadeByIdDepEstabAndAba(idDepartamento, idEstabelecimento, idCargo, aba);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodo(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo,
            final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getDao().getQuantidadeByIdDepEstabAndAbaAndPeriodo(idDepartamento, idEstabelecimento, idCargo, aba, dataInicio, dataFim);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodoFinalizados(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo,
            final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getDao().getQuantidadeByIdDepEstabAndAbaAndPeriodoFinalizados(idDepartamento, idEstabelecimento, idCargo, aba, dataInicio, dataFim);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabFuncaoAndAbaAndPeriodo(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo,
            final Integer idFuncao, final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getDao().getQuantidadeByIdDepEstabFuncaoAndAbaAndPeriodo(idDepartamento, idEstabelecimento, idCargo, idFuncao, aba, dataInicio, dataFim);
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final ControleQuestionariosDao controleQuestionariosDao = new ControleQuestionariosDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final ContratoQuestionariosDTO contratoQuestionariosDTO = (ContratoQuestionariosDTO) model;

        final RespostaItemQuestionarioServiceBean respostaItemQuestionarioServiceBean = new RespostaItemQuestionarioServiceBean();
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            controleQuestionariosDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            ControleQuestionariosDTO controleQuestionariosDto = new ControleQuestionariosDTO();
            controleQuestionariosDto = (ControleQuestionariosDTO) controleQuestionariosDao.create(controleQuestionariosDto);

            contratoQuestionariosDTO.setIdContratoQuestionario(controleQuestionariosDto.getIdControleQuestionario());

            // Executa operacoes pertinentes ao negocio.
            contratoQuestionariosDTO.setDatahoragrav(UtilDatas.getDataHoraAtual());
            model = crudDao.create(model);

            final Integer idIdentificadorResposta = contratoQuestionariosDTO.getIdContratoQuestionario();

            respostaItemQuestionarioServiceBean.processCollection(tc, contratoQuestionariosDTO.getColValores(), contratoQuestionariosDTO.getColAnexos(), idIdentificadorResposta,
                    null);

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
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();

        final ContratoQuestionariosDTO contratoQuestionariosDTO = (ContratoQuestionariosDTO) model;

        final RespostaItemQuestionarioServiceBean respostaItemQuestionarioServiceBean = new RespostaItemQuestionarioServiceBean();
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            respostaItemDao.setTransactionControler(tc);

            final Integer idIdentificadorResposta = contratoQuestionariosDTO.getIdContratoQuestionario();

            // Inicia transacao
            tc.start();

            respostaItemDao.deleteByIdIdentificadorResposta(idIdentificadorResposta);

            // Executa operacoes pertinentes ao negocio.
            contratoQuestionariosDTO.setDatahoragrav(UtilDatas.getDataHoraAtual());
            crudDao.update(model);

            respostaItemQuestionarioServiceBean.processCollection(tc, contratoQuestionariosDTO.getColValores(), contratoQuestionariosDTO.getColAnexos(), idIdentificadorResposta,
                    null);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateConteudoImpresso(final Integer idPessQuest, final String conteudoImpresso) throws Exception {
        this.getDao().updateConteudoImpresso(idPessQuest, conteudoImpresso);
    }

}
