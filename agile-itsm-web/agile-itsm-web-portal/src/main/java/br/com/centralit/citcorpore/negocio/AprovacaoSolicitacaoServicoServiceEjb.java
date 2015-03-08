package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

@SuppressWarnings("rawtypes")
public class AprovacaoSolicitacaoServicoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements AprovacaoSolicitacaoServicoService {

    private AprovacaoSolicitacaoServicoDao dao;

    @Override
    protected AprovacaoSolicitacaoServicoDao getDao() {
        if (dao == null) {
            dao = new AprovacaoSolicitacaoServicoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        return null;
    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        AprovacaoSolicitacaoServicoDTO aprovacaoDto = null;

        if (serialize != null) {
            aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) WebUtil.deserializeObject(AprovacaoSolicitacaoServicoDTO.class, serialize);
        }

        return aprovacaoDto;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaUpdate(solicitacaoServicoDto, model);

        AprovacaoSolicitacaoServicoDTO aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) model;
        this.getDao().setTransactionControler(tc);

        aprovacaoDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
        aprovacaoDto.setDataHora(UtilDatas.getDataHoraAtual());
        aprovacaoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        aprovacaoDto.setIdTarefa(solicitacaoServicoDto.getIdTarefa());
        aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) this.getDao().create(aprovacaoDto);

        solicitacaoServicoDto.setIdUltimaAprovacao(aprovacaoDto.getIdAprovacaoSolicitacaoServico());
        final SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
        solicitacaoDao.setTransactionControler(tc);
        solicitacaoDao.atualizaIdUltimaAprovacao(solicitacaoServicoDto);

        solicitacaoServicoDto.setAprovacao(aprovacaoDto.getAprovacao());
    }

    public void validaAtualizacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final AprovacaoSolicitacaoServicoDTO aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) model;

        if (aprovacaoDto.getAprovacao() == null || aprovacaoDto.getAprovacao().trim().length() == 0) {
            throw new LogicException("Aprovação não informada");
        }
        if (aprovacaoDto.getAprovacao().equalsIgnoreCase("N") && aprovacaoDto.getIdJustificativa() == null) {
            throw new LogicException("Justificativa não informada");
        }
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAtualizacao(solicitacaoServicoDto, model);
    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAtualizacao(solicitacaoServicoDto, model);
    }

    @Override
    public AprovacaoSolicitacaoServicoDTO findNaoAprovadaBySolicitacaoServico(final SolicitacaoServicoDTO solicitacaoServicoDto) {
        return this.getDao().findNaoAprovadaBySolicitacaoServico(solicitacaoServicoDto);
    }

    @Override
    public void preparaSolicitacaoParaAprovacao(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho, final String aprovacao, final Integer idJustificativa,
            final String observacoes) throws Exception {
        final AprovacaoSolicitacaoServicoDTO aprovacaoDto = new AprovacaoSolicitacaoServicoDTO();
        aprovacaoDto.setAprovacao(aprovacao);
        if (aprovacao.equalsIgnoreCase("N")) {
            aprovacaoDto.setIdJustificativa(idJustificativa);
            aprovacaoDto.setComplementoJustificativa(observacoes);
        }
        solicitacaoDto.setInformacoesComplementares(aprovacaoDto);
        solicitacaoDto.setAcaoFluxo(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR);
        solicitacaoDto.setIdTarefa(itemTrabalho.getIdItemTrabalho());
    }

}
