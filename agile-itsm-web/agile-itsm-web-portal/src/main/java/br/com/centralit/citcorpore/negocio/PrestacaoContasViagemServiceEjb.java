package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ItemPrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

/**
 * @author ronnie.lopes
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PrestacaoContasViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements PrestacaoContasViagemService {

    private PrestacaoContasViagemDao dao;

    @Override
    protected PrestacaoContasViagemDao getDao() {
        if (dao == null) {
            dao = new PrestacaoContasViagemDao();
        }
        return dao;
    }

    public String i18n_Message(final UsuarioDTO usuario, final String key) {
        if (usuario != null) {
            if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
                return UtilI18N.internacionaliza(usuario.getLocale(), key);
            }
            return key;
        }
        return key;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        return null;
    }

    @Override
    public Collection<IntegranteViagemDTO> restoreByIntegranteSolicitacao(final IntegranteViagemDTO integrante) throws Exception {
        final IntegranteViagemDao dao = new IntegranteViagemDao();
        return dao.restoreByIntegranteSolicitacao(integrante);
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        PrestacaoContasViagemDTO prestacaoContasViagemDto = null;
        if (serialize != null) {
            prestacaoContasViagemDto = (PrestacaoContasViagemDTO) WebUtil.deserializeObject(PrestacaoContasViagemDTO.class, serialize);
            if (prestacaoContasViagemDto != null && prestacaoContasViagemDto.getItensPrestacaoContasViagemSerialize() != null) {
                prestacaoContasViagemDto.setListaItemPrestacaoContasViagemDTO(WebUtil.deserializeCollectionFromString(ItemPrestacaoContasViagemDTO.class,
                        prestacaoContasViagemDto.getItensPrestacaoContasViagemSerialize()));
            }
            if (prestacaoContasViagemDto.getIntegranteSerialize() != null) {
                prestacaoContasViagemDto.setIntegranteViagemDto((IntegranteViagemDTO) WebUtil.deserializeObject(IntegranteViagemDTO.class,
                        prestacaoContasViagemDto.getIntegranteSerialize()));
            }
        }
        return prestacaoContasViagemDto;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

        PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;

        final RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
        RequisicaoViagemDTO reqViagemDto = new RequisicaoViagemDTO();
        final PrestacaoContasViagemDao prestacaoContasViagemDao = this.getDao();
        final ItemPrestacaoContasViagemDao itemPrestacaoContasViagemDao = new ItemPrestacaoContasViagemDao();
        final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
        final IntegranteViagemDao viagemDao = new IntegranteViagemDao();
        final IntegranteViagemDTO integranteViagem = new IntegranteViagemDTO();

        try {

            reqViagemDao.setTransactionControler(tc);
            prestacaoContasViagemDao.setTransactionControler(tc);
            itemPrestacaoContasViagemDao.setTransactionControler(tc);
            solicitacaoServicoDao.setTransactionControler(tc);
            viagemDao.setTransactionControler(tc);

            if (prestacaoContasViagemDto.getRemarcacao() == null || prestacaoContasViagemDto.getRemarcacao().equalsIgnoreCase("N")
                    || prestacaoContasViagemDto.getRemarcacao().equalsIgnoreCase("")) {
                prestacaoContasViagemDto.setRemarcacao("N");
            } else {
                prestacaoContasViagemDto.setRemarcacao("S");
            }
            prestacaoContasViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            integranteViagem.setRemarcacao(prestacaoContasViagemDto.getRemarcacao());
            integranteViagem.setIdRespPrestacaoContas(prestacaoContasViagemDto.getIdRespPrestacaoContas());

            if (prestacaoContasViagemDto.getIdEmpregado() != null && !prestacaoContasViagemDto.getIdEmpregado().equals("")) {
                integranteViagem.setIdEmpregado(prestacaoContasViagemDto.getIdEmpregado());
            } else {
                integranteViagem.setIdEmpregado(prestacaoContasViagemDto.getIntegranteViagemDto().getIdEmpregado());
            }

            integranteViagem.setIdSolicitacaoServico(prestacaoContasViagemDto.getIdSolicitacaoServico());

            prestacaoContasViagemDto.setDataHora(UtilDatas.getDataHoraAtual());

            prestacaoContasViagemDto.setIdEmpregado(prestacaoContasViagemDto.getIntegranteViagemDto().getIdEmpregado());
            prestacaoContasViagemDto.setIdResponsavel(prestacaoContasViagemDto.getIntegranteViagemDto().getIdRespPrestacaoContas());

            reqViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());

            reqViagemDto = (RequisicaoViagemDTO) reqViagemDao.restore(reqViagemDto);

            prestacaoContasViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
            viagemDao.updateBySolicitacaoEmpregado(integranteViagem);

            prestacaoContasViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            prestacaoContasViagemDto.setIntegranteFuncionario(prestacaoContasViagemDto.getIntegranteViagemDto().getIntegranteFuncionario());
            prestacaoContasViagemDto.setNomeNaoFuncionario(prestacaoContasViagemDto.getIntegranteViagemDto().getNomeNaoFuncionario());

            if (solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")) {
                this.validaCreate(solicitacaoServicoDto, model);
                prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
                prestacaoContasViagemDto.setIdItemTrabalho(null);
                if (reqViagemDto != null) {
                    reqViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_CONFERENCIA);
                    reqViagemDao.update(reqViagemDto);
                }
            }

            prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemDao.create(prestacaoContasViagemDto);

            if (prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO() != null && prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO().size() > 0) {
                for (final ItemPrestacaoContasViagemDTO itemPrestacaoContasViagemDTO : prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO()) {
                    itemPrestacaoContasViagemDTO.setIdPrestacaoContasViagem(prestacaoContasViagemDto.getIdPrestacaoContasViagem());
                    itemPrestacaoContasViagemDao.create(itemPrestacaoContasViagemDTO);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return prestacaoContasViagemDto;
    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    public AdiantamentoViagemDTO recuperaAdiantamentoViagem(final Integer idSolicitacaoServico, final Integer idEmpregado) throws Exception {
        final AdiantamentoViagemService adiantamentoViagemService = (AdiantamentoViagemService) ServiceLocator.getInstance().getService(AdiantamentoViagemService.class, null);

        return adiantamentoViagemService.consultarAdiantamentoViagem(idSolicitacaoServico, idEmpregado);
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDTO, final IDto model) throws Exception {
        final ItemPrestacaoContasViagemDao itemPrestacaoContasViagemDAO = new ItemPrestacaoContasViagemDao();
        final IntegranteViagemDao integranteViagemDAO = new IntegranteViagemDao();

        itemPrestacaoContasViagemDAO.setTransactionControler(tc);
        integranteViagemDAO.setTransactionControler(tc);

        PrestacaoContasViagemDTO prestacaoContasViagemDTO = (PrestacaoContasViagemDTO) model;
        IntegranteViagemDTO integranteViagemDTO = new IntegranteViagemDTO();

        prestacaoContasViagemDTO.setIntegranteViagemDto(integranteViagemDAO.getIntegranteByIdSolicitacaoAndTarefa(solicitacaoServicoDTO.getIdSolicitacaoServico(),
                solicitacaoServicoDTO.getIdTarefa()));

        if (prestacaoContasViagemDTO.getIdEmpregado() == null || prestacaoContasViagemDTO.getIdEmpregado().equals("")) {
            prestacaoContasViagemDTO.setIdEmpregado(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado());
        }

        if (prestacaoContasViagemDTO.getValorDiferenca() > 0.0 && solicitacaoServicoDTO.getAcaoFluxo().equalsIgnoreCase("E")) {
            throw new Exception(this.i18n_Message(solicitacaoServicoDTO.getUsuarioDto(), "requisicaoViagem.valorDiferencaPrestacaoAdiantamento"));
        }

        if (solicitacaoServicoDTO.getAcaoFluxo().equalsIgnoreCase("E")) {
            prestacaoContasViagemDTO.setSituacao(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
            prestacaoContasViagemDTO.setIdItemTrabalho(null);
        }

        if (prestacaoContasViagemDTO.getIdPrestacaoContasViagem() == null) {
            prestacaoContasViagemDTO = (PrestacaoContasViagemDTO) this.create(tc, solicitacaoServicoDTO, prestacaoContasViagemDTO);
            prestacaoContasViagemDTO.setCorrecao("N");
        } else {
            prestacaoContasViagemDTO.setIdItemTrabalho(null);
            prestacaoContasViagemDTO.setSituacao(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
            this.update(prestacaoContasViagemDTO);
            prestacaoContasViagemDTO.setCorrecao("S");
        }

        if (prestacaoContasViagemDTO.getListaItemPrestacaoContasViagemDTO() != null && !prestacaoContasViagemDTO.getListaItemPrestacaoContasViagemDTO().isEmpty()) {
            itemPrestacaoContasViagemDAO.deleteByIdPrestacaoContasViagem(prestacaoContasViagemDTO.getIdPrestacaoContasViagem());

            for (final ItemPrestacaoContasViagemDTO itemPrestacaoContasViagemDTO : prestacaoContasViagemDTO.getListaItemPrestacaoContasViagemDTO()) {
                itemPrestacaoContasViagemDTO.setIdPrestacaoContasViagem(prestacaoContasViagemDTO.getIdPrestacaoContasViagem());
                itemPrestacaoContasViagemDAO.create(itemPrestacaoContasViagemDTO);
            }
        }

        if (solicitacaoServicoDTO.getAcaoFluxo().equalsIgnoreCase("E")) {
            final RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
            requisicaoViagemDAO.setTransactionControler(tc);

            RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();

            requisicaoViagemDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
            requisicaoViagemDTO = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDTO);

            requisicaoViagemDTO.setEstado(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);

            requisicaoViagemDAO.update(requisicaoViagemDTO);
        }

        integranteViagemDTO = integranteViagemDAO.getIntegranteByIdSolicitacaoAndTarefa(solicitacaoServicoDTO.getIdSolicitacaoServico(), prestacaoContasViagemDTO
                .getIntegranteViagemDto().getIdTarefa());

        if (solicitacaoServicoDTO.getAcaoFluxo().equalsIgnoreCase("E")) {
            if (prestacaoContasViagemDTO != null && prestacaoContasViagemDTO.getCorrecao() != null && !prestacaoContasViagemDTO.getCorrecao().equals("N")) {
                integranteViagemDTO.setEstado(PrestacaoContasViagemDTO.EM_CONFERENCIA);
                integranteViagemDTO.setIdTarefa(null);
            } else {
                integranteViagemDTO.setEstado(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
                integranteViagemDTO.setIdTarefa(null);
            }
        }

        integranteViagemDAO.update(integranteViagemDTO);

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public Integer recuperaIdPrestacaoSeExistir(final Integer idSolicitacaoServico, final Integer idEmpregado) throws ServiceException, Exception {
        final PrestacaoContasViagemDao dao = this.getDao();
        final PrestacaoContasViagemDTO prestacaoContasViagemDto = dao.findBySolicitacaoAndEmpregado(idSolicitacaoServico, idEmpregado);
        if (prestacaoContasViagemDto != null && prestacaoContasViagemDto.getIdPrestacaoContasViagem() != null) {
            return prestacaoContasViagemDto.getIdPrestacaoContasViagem();
        } else {
            return null;
        }
    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    @Override
    public Integer recuperaIdPrestacaoSeExistir(final Integer idSolicitacaoServico, final String nomeNaoFunc) throws ServiceException, Exception {

        final PrestacaoContasViagemDao dao = this.getDao();
        final PrestacaoContasViagemDTO prestacaoContasViagemDto = dao.findBySolicitacaoAndNomeNaoFuncionario(idSolicitacaoServico, nomeNaoFunc);

        if (prestacaoContasViagemDto != null && prestacaoContasViagemDto.getIdPrestacaoContasViagem() != null) {
            return prestacaoContasViagemDto.getIdPrestacaoContasViagem();
        } else {
            return null;
        }
    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    public boolean validaPeriodo(final PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception {
        RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
        final RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();

        requisicaoViagemDto.setIdRequisicaoMudanca(prestacaoContasViagemDto.getIdSolicitacaoServico());
        requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);

        final Timestamp dataRetorno = UtilDatas.strToTimestamp(requisicaoViagemDto.getDataFimViagem().toString());
        final Timestamp dataPrestacao = prestacaoContasViagemDto.getDataHora();
        final Timestamp dataLitimte = (Timestamp) UtilDatas.incrementaDiasEmData(dataRetorno, 5);

        if (dataPrestacao.compareTo(dataLitimte) >= 0) {
            return true;
        }

        return false;

    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    @Override
    public PrestacaoContasViagemDTO recuperaCorrecao(final PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
        final PrestacaoContasViagemDao dao = this.getDao();
        PrestacaoContasViagemDTO prestacaoContas = null;
        final List result = (List) dao.findBySolicitacaoEmpregadoSeCorrecao(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIntegranteViagemDto()
                .getIdEmpregado());
        if (result != null) {
            prestacaoContas = (PrestacaoContasViagemDTO) result.get(0);
        }
        return prestacaoContas;

    }
}
