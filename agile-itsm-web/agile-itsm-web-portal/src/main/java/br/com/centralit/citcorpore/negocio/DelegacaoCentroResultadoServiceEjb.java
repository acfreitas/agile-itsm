package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.InstanciaFluxoDao;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoFluxoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoDao;
import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoFluxoDao;
import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoProcessoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

public class DelegacaoCentroResultadoServiceEjb extends CrudServiceImpl implements DelegacaoCentroResultadoService {

    private DelegacaoCentroResultadoDao dao;

    @Override
    protected DelegacaoCentroResultadoDao getDao() {
        if (dao == null) {
            dao = new DelegacaoCentroResultadoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) arg0;
        if (delegacaoCentroResultadoDto.getIdCentroResultado() == null) {
            throw new LogicException(this.i18nMessage("centroResultado") + " " + this.i18nMessage("citcorpore.comum.naoInformado"));
        }

        if (delegacaoCentroResultadoDto.getIdResponsavel() == null) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.responsavel") + " " + this.i18nMessage("citcorpore.comum.naoInformado"));
        }

        if (delegacaoCentroResultadoDto.getIdEmpregado() == null) {
            throw new LogicException(this.i18nMessage("delegacaoCentroResultado.empregado") + " " + this.i18nMessage("citcorpore.comum.naoInformado"));
        }

        if (delegacaoCentroResultadoDto.getIdProcessoNegocio() == null || delegacaoCentroResultadoDto.getIdProcessoNegocio().length == 0) {
            throw new LogicException(this.i18nMessage("delegacaoCentroResultado.processo") + " " + this.i18nMessage("citcorpore.comum.naoInformado"));
        }

        if (delegacaoCentroResultadoDto.getAbrangencia() == null) {
            throw new LogicException(this.i18nMessage("delegacaoCentroResultado.abrangencia") + " " + this.i18nMessage("citcorpore.comum.naoInformado"));
        }

        if (delegacaoCentroResultadoDto.getDataInicio() == null || delegacaoCentroResultadoDto.getDataInicio().compareTo(UtilDatas.getDataAtual()) < 0) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.datainvalida"));
        }

        if (delegacaoCentroResultadoDto.getDataFim() == null || delegacaoCentroResultadoDto.getDataFim().compareTo(delegacaoCentroResultadoDto.getDataInicio()) < 0) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.dataFinalInvalida"));
        }

        if (delegacaoCentroResultadoDto.getIdResponsavel().intValue() == delegacaoCentroResultadoDto.getIdEmpregado().intValue()) {
            throw new LogicException(this.i18nMessage("delegacaoCentroResultado.empregadoNaoPermitido"));
        }

        final Collection<ResponsavelCentroResultadoProcessoDTO> colResponsavel = new ResponsavelCentroResultadoDao().findByIdCentroResultadoAndIdResponsavel(
                delegacaoCentroResultadoDto.getIdCentroResultado(), delegacaoCentroResultadoDto.getIdEmpregado());
        if (colResponsavel != null && !colResponsavel.isEmpty()) {
            throw new LogicException(this.i18nMessage("delegacaoCentroResultado.empregadoNaoPermitido"));
        }

        if (delegacaoCentroResultadoDto.getAbrangencia().equals(DelegacaoCentroResultadoDTO.ESPECIFICAS)) {
            if (delegacaoCentroResultadoDto.getRequisiçoes() == null) {
                throw new LogicException(this.i18nMessage("delegacaoCentroResultado.requisicoes") + " " + this.i18nMessage("citcorpore.comum.naoInformado"));
            }

            final Collection<ExecucaoSolicitacaoDTO> colInstancias = new ArrayList();
            SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
            final ExecucaoSolicitacaoDao execucaoSolicitacaoDao = new ExecucaoSolicitacaoDao();
            final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
            final FluxoDao fluxoDao = new FluxoDao();
            final String[] requisicoes = delegacaoCentroResultadoDto.getRequisiçoes().split(",");
            for (final String requisicoe : requisicoes) {
                Integer idSolicitacaoServico = null;
                try {
                    idSolicitacaoServico = new Integer(requisicoe.trim());
                } catch (final Exception e) {}
                if (idSolicitacaoServico == null) {
                    throw new LogicException(this.i18nMessage("delegacaoCentroResultado.numeroRequisicaoInvalido"));
                }

                solicitacaoServicoDto.setIdSolicitacaoServico(idSolicitacaoServico);
                solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoDao.restore(solicitacaoServicoDto);
                if (solicitacaoServicoDto == null) {
                    throw new LogicException(this.i18nMessage("delegacaoCentroResultado.numeroRequisicaoInvalido"));
                }

                if (solicitacaoServicoDto.atendida()) {
                    throw new LogicException("Requisição " + idSolicitacaoServico + " já encerrada");
                }

                final ExecucaoSolicitacaoDTO execucaoDto = execucaoSolicitacaoDao.findBySolicitacaoServico(solicitacaoServicoDto);

                if (execucaoDto == null) {
                    throw new LogicException(this.i18nMessage("delegacaoCentroResultado.numeroRequisicaoInvalido") + " (" + idSolicitacaoServico + ") ");
                }

                FluxoDTO fluxoDto = new FluxoDTO();
                fluxoDto.setIdFluxo(execucaoDto.getIdFluxo());
                fluxoDto = (FluxoDTO) fluxoDao.restore(fluxoDto);
                boolean bFluxoValido = false;
                for (int j = 0; j < delegacaoCentroResultadoDto.getIdProcessoNegocio().length; j++) {
                    if (fluxoDto.getIdProcessoNegocio() == null) {
                        continue;
                    }
                    if (delegacaoCentroResultadoDto.getIdProcessoNegocio()[j] == fluxoDto.getIdProcessoNegocio().intValue()) {
                        bFluxoValido = true;
                        break;
                    }
                }
                if (!bFluxoValido) {
                    throw new LogicException("Requisição " + idSolicitacaoServico + " não pertence a nenhum processo de negócio válido para a delegação");
                }

                colInstancias.add(execucaoDto);
            }
            delegacaoCentroResultadoDto.setColInstancias(colInstancias);
        }

    }

    @Override
    public Collection findByIdResponsavelAndIdCentroResultado(final Integer idResponsavel, final Integer idCentroResultado) throws Exception {
        return this.getDao().findByIdResponsavelAndIdCentroResultado(idResponsavel, idCentroResultado);
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final DelegacaoCentroResultadoDao delegacaoCentroResultadoDao = new DelegacaoCentroResultadoDao();
        final DelegacaoCentroResultadoFluxoDao delegacaoCentroResultadoFluxoDao = new DelegacaoCentroResultadoFluxoDao();
        final DelegacaoCentroResultadoProcessoDao delegacaoCentroResultadoProcessoDao = new DelegacaoCentroResultadoProcessoDao();
        final UsuarioDao usuarioDao = new UsuarioDao();
        final TransactionControler tc = new TransactionControlerImpl(delegacaoCentroResultadoDao.getAliasDB());

        try {
            this.validaCreate(model);

            delegacaoCentroResultadoDao.setTransactionControler(tc);
            delegacaoCentroResultadoFluxoDao.setTransactionControler(tc);
            delegacaoCentroResultadoProcessoDao.setTransactionControler(tc);
            usuarioDao.setTransactionControler(tc);

            tc.start();

            DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) model;
            delegacaoCentroResultadoDto.setRevogada("N");
            delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) delegacaoCentroResultadoDao.create(delegacaoCentroResultadoDto);

            for (int i = 0; i < delegacaoCentroResultadoDto.getIdProcessoNegocio().length; i++) {
                final DelegacaoCentroResultadoProcessoDTO delegProcessoDto = new DelegacaoCentroResultadoProcessoDTO();
                delegProcessoDto.setIdDelegacaoCentroResultado(delegacaoCentroResultadoDto.getIdDelegacaoCentroResultado());
                delegProcessoDto.setIdProcessoNegocio(delegacaoCentroResultadoDto.getIdProcessoNegocio()[i]);
                delegacaoCentroResultadoProcessoDao.create(delegProcessoDto);
            }

            final UsuarioDTO usuarioRespDto = usuarioDao.restoreAtivoByIdEmpregado(delegacaoCentroResultadoDto.getIdResponsavel());
            final UsuarioDTO usuarioEmpDto = usuarioDao.restoreAtivoByIdEmpregado(delegacaoCentroResultadoDto.getIdEmpregado());
            if (delegacaoCentroResultadoDto.getColInstancias() != null) {
                final AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
                atribuicaoFluxoDao.setTransactionControler(tc);
                for (final ExecucaoSolicitacaoDTO execucaoDto : delegacaoCentroResultadoDto.getColInstancias()) {
                    final DelegacaoCentroResultadoFluxoDTO delegFluxoDto = new DelegacaoCentroResultadoFluxoDTO();
                    delegFluxoDto.setIdDelegacaoCentroResultado(delegacaoCentroResultadoDto.getIdDelegacaoCentroResultado());
                    delegFluxoDto.setIdInstanciaFluxo(execucaoDto.getIdInstanciaFluxo());
                    delegacaoCentroResultadoFluxoDao.create(delegFluxoDto);
                    if (usuarioRespDto == null || usuarioEmpDto == null) {
                        continue;
                    }
                    final Collection<AtribuicaoFluxoDTO> colAtribuicoes = atribuicaoFluxoDao.findByDisponiveisByIdInstanciaAndIdUsuario(execucaoDto.getIdInstanciaFluxo(),
                            usuarioRespDto.getIdUsuario());
                    if (colAtribuicoes == null) {
                        continue;
                    }
                    for (final AtribuicaoFluxoDTO atribuicaoFluxoDto : colAtribuicoes) {
                        final AtribuicaoFluxoDTO novaAtribuicaoFluxoDto = new AtribuicaoFluxoDTO();
                        Reflexao.copyPropertyValues(atribuicaoFluxoDto, novaAtribuicaoFluxoDto);
                        novaAtribuicaoFluxoDto.setIdAtribuicao(null);
                        novaAtribuicaoFluxoDto.setIdUsuario(usuarioEmpDto.getIdUsuario());
                        atribuicaoFluxoDao.create(novaAtribuicaoFluxoDto);
                    }
                }
            }

            if (delegacaoCentroResultadoDto.getAbrangencia().equalsIgnoreCase(DelegacaoCentroResultadoDTO.NOVAS_EXISTENTES) && usuarioRespDto != null && usuarioEmpDto != null) {
                final FluxoDao fluxoDao = new FluxoDao();
                fluxoDao.setTransactionControler(tc);
                final InstanciaFluxoDao instanciaFluxoDao = new InstanciaFluxoDao();
                instanciaFluxoDao.setTransactionControler(tc);
                final AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
                atribuicaoFluxoDao.setTransactionControler(tc);
                for (int i = 0; i < delegacaoCentroResultadoDto.getIdProcessoNegocio().length; i++) {
                    final Collection<FluxoDTO> colFluxos = fluxoDao.findByIdProcessoNegocio(delegacaoCentroResultadoDto.getIdProcessoNegocio()[i]);
                    if (colFluxos == null) {
                        continue;
                    }
                    for (final FluxoDTO fluxoDto : colFluxos) {
                        final Collection<InstanciaFluxoDTO> colInstancias = instanciaFluxoDao.findAtivasByIdFluxo(fluxoDto.getIdFluxo());
                        if (colInstancias == null) {
                            continue;
                        }
                        for (final InstanciaFluxoDTO instanciaFluxoDto : colInstancias) {
                            final Collection<AtribuicaoFluxoDTO> colAtribuicoes = atribuicaoFluxoDao.findByDisponiveisByIdInstanciaAndIdUsuario(instanciaFluxoDto.getIdInstancia(),
                                    usuarioRespDto.getIdUsuario());
                            if (colAtribuicoes == null) {
                                continue;
                            }
                            for (final AtribuicaoFluxoDTO atribuicaoFluxoDto : colAtribuicoes) {
                                final AtribuicaoFluxoDTO novaAtribuicaoFluxoDto = new AtribuicaoFluxoDTO();
                                Reflexao.copyPropertyValues(atribuicaoFluxoDto, novaAtribuicaoFluxoDto);
                                novaAtribuicaoFluxoDto.setIdAtribuicao(null);
                                novaAtribuicaoFluxoDto.setIdUsuario(usuarioEmpDto.getIdUsuario());
                                atribuicaoFluxoDao.create(novaAtribuicaoFluxoDto);
                            }
                        }
                    }
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
    public void revoga(final DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto) throws Exception {
        final DelegacaoCentroResultadoDao delegacaoCentroResultadoDao = new DelegacaoCentroResultadoDao();
        final UsuarioDao usuarioDao = new UsuarioDao();
        final TransactionControler tc = new TransactionControlerImpl(delegacaoCentroResultadoDao.getAliasDB());

        try {
            tc.start();

            delegacaoCentroResultadoDao.setTransactionControler(tc);
            usuarioDao.setTransactionControler(tc);

            final DelegacaoCentroResultadoDTO delegacaoAuxDto = (DelegacaoCentroResultadoDTO) delegacaoCentroResultadoDao.restore(delegacaoCentroResultadoDto);
            if (delegacaoAuxDto.getRevogada().equalsIgnoreCase("S")) {
                throw new LogicException(this.i18nMessage("delegacaoCentroResultado.jaRevogada"));
            }

            if (delegacaoCentroResultadoDto.getIdResponsavelRevogacao() == null) {
                throw new LogicException("Usuário responsável pela revogação não informado");
            }

            delegacaoAuxDto.setRevogada("S");
            delegacaoAuxDto.setIdResponsavelRevogacao(delegacaoCentroResultadoDto.getIdResponsavelRevogacao());
            delegacaoAuxDto.setDataHoraRevogacao(UtilDatas.getDataHoraAtual());
            delegacaoCentroResultadoDao.update(delegacaoAuxDto);

            usuarioDao.restoreAtivoByIdEmpregado(delegacaoAuxDto.getIdResponsavel());
            final UsuarioDTO usuarioEmpDto = usuarioDao.restoreAtivoByIdEmpregado(delegacaoAuxDto.getIdEmpregado());

            if (delegacaoAuxDto.getAbrangencia().equals(DelegacaoCentroResultadoDTO.ESPECIFICAS)) {
                final DelegacaoCentroResultadoFluxoDao delegacaoCentroResultadoFluxoDao = new DelegacaoCentroResultadoFluxoDao();
                delegacaoCentroResultadoFluxoDao.setTransactionControler(tc);
                final Collection<DelegacaoCentroResultadoFluxoDTO> colInstancias = delegacaoCentroResultadoFluxoDao.findByIdDelegacaoCentroResultado(delegacaoAuxDto
                        .getIdDelegacaoCentroResultado());
                if (colInstancias != null) {
                    for (final DelegacaoCentroResultadoFluxoDTO delegacaoCentroResultadoFluxoDto : colInstancias) {
                        final AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
                        atribuicaoFluxoDao.setTransactionControler(tc);
                        final Collection<AtribuicaoFluxoDTO> colAtribuicoes = atribuicaoFluxoDao.findByDisponiveisByIdInstanciaAndIdUsuario(
                                delegacaoCentroResultadoFluxoDto.getIdInstanciaFluxo(), usuarioEmpDto.getIdUsuario());
                        if (colAtribuicoes == null) {
                            continue;
                        }
                        for (final AtribuicaoFluxoDTO atribuicaoFluxoDto : colAtribuicoes) {
                            atribuicaoFluxoDao.delete(atribuicaoFluxoDto);
                        }
                    }
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
