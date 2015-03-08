package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoViagem;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class ConferenciaViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements ConferenciaViagemService {

    private PrestacaoContasViagemDao dao;

    @Override
    protected PrestacaoContasViagemDao getDao() {
        if (dao == null) {
            dao = new PrestacaoContasViagemDao();
        }
        return dao;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        PrestacaoContasViagemDTO prestacaoContasViagemDto = null;
        if (serialize != null) {
            prestacaoContasViagemDto = (PrestacaoContasViagemDTO) WebUtil.deserializeObject(PrestacaoContasViagemDTO.class, serialize);
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
        return null;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;
        RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
        final RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
        ParecerDTO parecerDto = new ParecerDTO();
        final ParecerDao parecerDao = new ParecerDao();

        this.getDao().setTransactionControler(tc);
        parecerDao.setTransactionControler(tc);
        reqViagemDao.setTransactionControler(tc);

        try {
            parecerDto.setAprovado(prestacaoContasViagemDto.getAprovado());
            if (prestacaoContasViagemDto.getAprovado().equalsIgnoreCase("N")) {
                if (prestacaoContasViagemDto.getIdJustificativaAutorizacao() == null) {
                    throw new LogicException(this.i18nMessage("requisicaoViagem.justificativaCampoObrigatorio"));
                }
                if (prestacaoContasViagemDto.getComplemJustificativaAutorizacao() == null || prestacaoContasViagemDto.getComplemJustificativaAutorizacao().equalsIgnoreCase("")) {
                    throw new LogicException(this.i18nMessage("rh.informeComplementoJustificativa"));
                }
            }
            parecerDto.setIdJustificativa(prestacaoContasViagemDto.getIdJustificativaAutorizacao());
            parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
            // parecerDto.setObservacoes(prestacaoContasViagemDto.getObservacoes());
            parecerDto.setComplementoJustificativa(prestacaoContasViagemDto.getComplemJustificativaAutorizacao());
            requisicaoViagemDto.setComplemJustificativaAutorizacao(prestacaoContasViagemDto.getComplemJustificativaAutorizacao());
            parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());

            parecerDto = (ParecerDTO) parecerDao.create(parecerDto);

            prestacaoContasViagemDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());

            requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDto = (RequisicaoViagemDTO) reqViagemDao.restore(requisicaoViagemDto);

            final PrestacaoContasViagemDTO prestacaoAux = (PrestacaoContasViagemDTO) this.getDao().restore(prestacaoContasViagemDto);
            if (prestacaoAux != null) {
                prestacaoContasViagemDto.setIdEmpregado(prestacaoAux.getIdEmpregado());
            }
            if (solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")) {
                if (prestacaoContasViagemDto.getAprovado().equalsIgnoreCase("S")) {
                    prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.APROVADA);

                    requisicaoViagemDto.setEstado(RequisicaoViagemDTO.FINALIZADA);
                    reqViagemDao.update(requisicaoViagemDto);

                    final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
                    final IntegranteViagemDTO integranteViagemDTO = integranteViagemDao.findByIdSolicitacaoServicoIdTarefa(solicitacaoServicoDto.getIdSolicitacaoServico(),
                            solicitacaoServicoDto.getIdTarefa());
                    integranteViagemDTO.setEstado(RequisicaoViagemDTO.FINALIZADA);
                    integranteViagemDao.updateNotNull(integranteViagemDTO);
                } else {
                    final ExecucaoRequisicaoViagem execViagem = new ExecucaoRequisicaoViagem();
                    try {
                        final Integer idModeloEmail = Integer.parseInt(ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_PRESTACAO_CONTAS_NAO_APROVADA));
                        execViagem.enviaEmailNaoAprovado(idModeloEmail, requisicaoViagemDto, prestacaoContasViagemDto, tc);
                    } catch (final Exception e) {}

                    prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.NAO_APROVADA);
                    if (prestacaoContasViagemDto.getAprovado().equalsIgnoreCase("N")) {
                        prestacaoContasViagemDto.setIdItemTrabalho(null);
                    }
                    requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS);
                    reqViagemDao.update(requisicaoViagemDto);

                    final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
                    final IntegranteViagemDTO integranteViagemDTO = integranteViagemDao.findByIdSolicitacaoServicoIdTarefa(solicitacaoServicoDto.getIdSolicitacaoServico(),
                            solicitacaoServicoDto.getIdTarefa());
                    integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_CORRECAO);
                    if (prestacaoContasViagemDto.getAprovado().equalsIgnoreCase("N")) {
                        integranteViagemDTO.setIdTarefa(null);
                    }
                    integranteViagemDao.update(integranteViagemDTO);
                }
            }

            if (parecerDto != null) {
                prestacaoContasViagemDto.setIdAprovacao(parecerDto.getIdParecer());
            }

            this.getDao().update(prestacaoContasViagemDto);

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

}
