package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.ChecklistQuestionarioDTO;
import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.ControleQuestionariosDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.integracao.ContratoQuestionariosDao;
import br.com.centralit.citcorpore.integracao.ControleQuestionariosDao;
import br.com.centralit.citcorpore.integracao.RequisicaoQuestionarioDao;
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

@SuppressWarnings("rawtypes")
public class RequisicaoQuestionarioServiceEjb extends CrudServiceImpl implements RequisicaoQuestionarioService {

    private RequisicaoQuestionarioDao dao;
    private ContratoQuestionariosDao contratoQuestionariosDao;

    @Override
    protected RequisicaoQuestionarioDao getDao() {
        if (dao == null) {
            dao = new RequisicaoQuestionarioDao();
        }
        return dao;
    }

    public ContratoQuestionariosDao getContratoQuestionariosDao() {
        if (contratoQuestionariosDao == null) {
            contratoQuestionariosDao = new ContratoQuestionariosDao();
        }
        return contratoQuestionariosDao;
    }

    @Override
    public Collection listByIdContratoAndAba(final Integer idContrato, final String aba) throws Exception {
        return this.getContratoQuestionariosDao().listByIdContratoAndAba(idContrato, aba);
    }

    @Override
    public Collection listByIdContratoAndAbaOrdemCrescente(final Integer idContrato, final String aba) throws Exception {
        return this.getContratoQuestionariosDao().listByIdContratoAndAbaOrdemCrescente(idContrato, aba);
    }

    @Override
    public ContratoQuestionariosDTO getUltimoByIdContratoAndAba(final Integer idContrato, final String aba) throws Exception {
        return this.getContratoQuestionariosDao().getUltimoByIdContratoAndAba(idContrato, aba);
    }

    @Override
    public ContratoQuestionariosDTO getUltimoByIdContratoAndAbaAndPeriodo(final Integer idContrato, final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getContratoQuestionariosDao().getUltimoByIdContratoAndAbaAndPeriodo(idContrato, aba, dataInicio, dataFim);
    }

    @Override
    public Collection listByIdContratoAndQuestionario(final Integer idQuestionario, final Integer idContrato) throws Exception {
        return this.getContratoQuestionariosDao().listByIdContratoAndQuestionario(idQuestionario, idContrato);
    }

    @Override
    public Collection listByIdContrato(final Integer idContrato) throws Exception {
        return this.getContratoQuestionariosDao().listByIdContrato(idContrato);
    }

    @Override
    public Collection listByIdContratoOrderDecrescente(final Integer idContrato) throws Exception {
        return this.getContratoQuestionariosDao().listByIdContratoOrderDecrescente(idContrato);
    }

    @Override
    public Collection listByIdContratoOrderIdDecrescente(final Integer idContrato) throws Exception {
        return this.getContratoQuestionariosDao().listByIdContratoOrderIdDecrescente(idContrato);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAba(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo, final String aba)
            throws Exception {
        return this.getContratoQuestionariosDao().getQuantidadeByIdDepEstabAndAba(idDepartamento, idEstabelecimento, idCargo, aba);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodo(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo,
            final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getContratoQuestionariosDao().getQuantidadeByIdDepEstabAndAbaAndPeriodo(idDepartamento, idEstabelecimento, idCargo, aba, dataInicio, dataFim);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodoFinalizados(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo,
            final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getContratoQuestionariosDao().getQuantidadeByIdDepEstabAndAbaAndPeriodoFinalizados(idDepartamento, idEstabelecimento, idCargo, aba, dataInicio, dataFim);
    }

    @Override
    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabFuncaoAndAbaAndPeriodo(final Integer idDepartamento, final Integer idEstabelecimento, final Integer idCargo,
            final Integer idFuncao, final String aba, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getContratoQuestionariosDao().getQuantidadeByIdDepEstabFuncaoAndAbaAndPeriodo(idDepartamento, idEstabelecimento, idCargo, idFuncao, aba, dataInicio, dataFim);
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final ControleQuestionariosDao controleQuestionariosDao = new ControleQuestionariosDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final SolicitacaoServicoQuestionarioDTO solQuestionariosDTO = (SolicitacaoServicoQuestionarioDTO) model;

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

            solQuestionariosDTO.setIdSolicitacaoQuestionario(controleQuestionariosDto.getIdControleQuestionario());

            // Executa operacoes pertinentes ao negocio.
            solQuestionariosDTO.setDataHoraGrav(UtilDatas.getDataHoraAtual());
            model = crudDao.create(model);

            final Integer idIdentificadorResposta = solQuestionariosDTO.getIdSolicitacaoQuestionario();

            respostaItemQuestionarioServiceBean.processCollection(tc, solQuestionariosDTO.getColValores(), solQuestionariosDTO.getColAnexos(), idIdentificadorResposta, null);

            // Faz commit e fecha a transacao.
            tc.commit();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
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

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public void atualizaInformacoesQuestionario(final RequisicaoQuestionarioDTO requisicaoQuestionarioDTO, final HttpServletRequest request) throws Exception {
        final ControleQuestionariosDao controleQuestionariosDao = new ControleQuestionariosDao();
        final RequisicaoQuestionarioDao requisicaoQuestionarioDao = this.getDao();
        final RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
        final RespostaItemQuestionarioServiceBean respostaItemQuestionarioServiceBean = new RespostaItemQuestionarioServiceBean();

        final TransactionControler tc = new TransactionControlerImpl(requisicaoQuestionarioDao.getAliasDB());
        try {
            tc.start();
            controleQuestionariosDao.setTransactionControler(tc);
            requisicaoQuestionarioDao.setTransactionControler(tc);
            respostaItemDao.setTransactionControler(tc);

            if (requisicaoQuestionarioDTO.getIdRequisicaoQuestionario() != null && requisicaoQuestionarioDTO.getIdRequisicaoQuestionario().intValue() > 0) {
                requisicaoQuestionarioDTO.setDataHoraGrav(UtilDatas.getDataHoraAtual());
                requisicaoQuestionarioDao.updateNotNull(requisicaoQuestionarioDTO);
                respostaItemDao.deleteByIdIdentificadorResposta(requisicaoQuestionarioDTO.getIdRequisicaoQuestionario());
                respostaItemQuestionarioServiceBean.processCollection(tc, requisicaoQuestionarioDTO.getColValores(), requisicaoQuestionarioDTO.getColAnexos(),
                        requisicaoQuestionarioDTO.getIdRequisicaoQuestionario(), request);
            } else {
                ControleQuestionariosDTO controleQuestionariosDto = new ControleQuestionariosDTO();
                controleQuestionariosDto = (ControleQuestionariosDTO) controleQuestionariosDao.create(controleQuestionariosDto);

                if (requisicaoQuestionarioDTO.getDataQuestionario() == null) {
                    requisicaoQuestionarioDTO.setDataQuestionario(UtilDatas.getDataAtual());
                }
                requisicaoQuestionarioDTO.setSituacao("E");
                requisicaoQuestionarioDTO.setIdRequisicaoQuestionario(controleQuestionariosDto.getIdControleQuestionario());
                requisicaoQuestionarioDTO.setDataHoraGrav(UtilDatas.getDataHoraAtual());

                final Integer idIdentificadorResposta = requisicaoQuestionarioDTO.getIdRequisicaoQuestionario();
                respostaItemQuestionarioServiceBean.processCollection(tc, requisicaoQuestionarioDTO.getColValores(), requisicaoQuestionarioDTO.getColAnexos(),
                        idIdentificadorResposta, request);

                requisicaoQuestionarioDao.create(requisicaoQuestionarioDTO);

            }
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public void updateConteudoImpresso(final Integer idPessQuest, final String conteudoImpresso) throws Exception {
        this.getContratoQuestionariosDao().updateConteudoImpresso(idPessQuest, conteudoImpresso);
    }

    @Override
    public Collection listByIdTipoAbaAndTipoRequisicaoAndQuestionario(final ChecklistQuestionarioDTO checklistQuestionarioDTO) throws Exception {
        return this.getDao().listByIdTipoAbaAndTipoRequisicaoAndQuestionario(checklistQuestionarioDTO);
    }

    @Override
    public boolean gravaConfirmacao(final Integer idRequisicao, final String confirmacao) {
        return this.getDao().gravaConfirmacao(idRequisicao, confirmacao);
    }

    @Override
    public Collection listNaoConfirmados(final Integer id, final Integer tipo) throws Exception {
        return this.getDao().listNaoConfirmados(id, tipo);
    }

}
