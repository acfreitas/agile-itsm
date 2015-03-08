package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RecursoProjetoDTO;
import br.com.centralit.citcorpore.integracao.AssinaturaAprovacaoProjetoDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RecursoProjetoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ProjetoServiceEjb extends CrudServiceImpl implements ProjetoService {

    private ProjetoDao dao;

    @Override
    protected ProjetoDao getDao() {
        if (dao == null) {
            dao = new ProjetoDao();
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
    public Collection findByIdCliente(final Integer parm) throws Exception {
        return this.getDao().findByIdCliente(parm);
    }

    @Override
    public Collection listHierarquia(final Integer idCliente, final boolean acrescentarInativos) throws Exception {
        final ProjetoDao projetoDao = this.getDao();
        final Collection<ProjetoDTO> colSemPai = projetoDao.findSemPai(idCliente);
        if (colSemPai == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();
        boolean bAcrescenta;
        for (final ProjetoDTO projetoDto : colSemPai) {
            bAcrescenta = true;
            if (!acrescentarInativos && !projetoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                projetoDto.setNivel(new Integer(0));
                colRetorno.add(projetoDto);

                final Collection colFilhos = this.carregaFilhos(projetoDto.getIdProjeto(), 0, idCliente, acrescentarInativos);
                if (colFilhos != null) {
                    colRetorno.addAll(colFilhos);
                }
            }
        }
        return colRetorno;
    }

    private Collection carregaFilhos(final Integer idPai, final int nivel, final Integer idCliente, final boolean acrescentarInativos) throws Exception {
        final Collection<ProjetoDTO> colFilhos = this.getDao().findByIdPai(idPai, idCliente);
        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        boolean bAcrescenta;
        for (final ProjetoDTO projetoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !projetoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                projetoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(projetoDto);

                final Collection colFilhosFilhos = this.carregaFilhos(projetoDto.getIdProjeto(), nivel + 1, idCliente, acrescentarInativos);
                if (colFilhosFilhos != null) {
                    colRetorno.addAll(colFilhosFilhos);
                }
            }
        }
        return colRetorno;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final ProjetoDao crudDao = this.getDao();
        final RecursoProjetoDao recursoProjetoDao = new RecursoProjetoDao();
        final AssinaturaAprovacaoProjetoDao assinaturaAprovacaoProjetoDao = new AssinaturaAprovacaoProjetoDao();
        final OSDao osDao = new OSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            recursoProjetoDao.setTransactionControler(tc);
            assinaturaAprovacaoProjetoDao.setTransactionControler(tc);
            osDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            final ProjetoDTO projetoDTO = (ProjetoDTO) model;

            if (projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S")) {
                this.validaObrigatoriedade(projetoDTO);
            }

            if (projetoDTO.getColRecursos() != null) {
                for (final Iterator it = projetoDTO.getColRecursos().iterator(); it.hasNext();) {
                    final RecursoProjetoDTO recursoProjetoDTO = (RecursoProjetoDTO) it.next();
                    recursoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
                    if (recursoProjetoDTO.getCustoHora() == null) {
                        recursoProjetoDTO.setCustoHora(new Double(0));
                    }
                    recursoProjetoDao.create(recursoProjetoDTO);
                }
            }

            if (projetoDTO.getColAssinaturasAprovacoes() != null) {
                for (final Iterator it = projetoDTO.getColAssinaturasAprovacoes().iterator(); it.hasNext();) {
                    final AssinaturaAprovacaoProjetoDTO assinaturaAprovacaoProjetoDTO = (AssinaturaAprovacaoProjetoDTO) it.next();
                    assinaturaAprovacaoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
                    if (assinaturaAprovacaoProjetoDTO.getPapel() == null) {
                        assinaturaAprovacaoProjetoDTO.setPapel(" ");
                    }
                    if (assinaturaAprovacaoProjetoDTO.getOrdem() == null) {
                        assinaturaAprovacaoProjetoDTO.setOrdem(" ");
                    }
                    assinaturaAprovacaoProjetoDao.create(assinaturaAprovacaoProjetoDTO);
                }
            }

            if (projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S")) {
                OSDTO osDto = new OSDTO();
                osDto.setIdContrato(projetoDTO.getIdContrato());
                osDto.setNumero(projetoDTO.getNumero());
                osDto.setIdServicoContrato(projetoDTO.getIdServicoContrato());
                osDto.setAno(projetoDTO.getAno());
                osDto.setNomeAreaRequisitante(projetoDTO.getNomeAreaRequisitante());
                osDto.setDemanda(projetoDTO.getDemanda());
                osDto.setObjetivo(projetoDTO.getObjetivo());
                osDto.setSituacaoOS(OSDTO.EM_CRIACAO);
                osDto.setDataInicio(UtilDatas.getDataAtual());
                osDto.setDataFim(UtilDatas.getDataAtual());
                osDto.setDataEmissao(projetoDTO.getDataEmissao());
                osDto = (OSDTO) osDao.create(osDto);

                ProjetoDTO projetoAux = new ProjetoDTO();
                projetoAux = (ProjetoDTO) crudDao.restore(model);
                projetoAux.setIdProjeto(projetoDTO.getIdProjeto());
                projetoAux.setIdOs(osDto.getIdOS());
                crudDao.updateNotNull(projetoAux);
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
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final ProjetoDao crudDao = this.getDao();
        final RecursoProjetoDao recursoProjetoDao = new RecursoProjetoDao();
        final AssinaturaAprovacaoProjetoDao assinaturaAprovacaoProjetoDao = new AssinaturaAprovacaoProjetoDao();
        final OSDao osDao = new OSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            recursoProjetoDao.setTransactionControler(tc);
            assinaturaAprovacaoProjetoDao.setTransactionControler(tc);
            osDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            final ProjetoDTO projetoDTO = (ProjetoDTO) model;

            if (projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S")) {
                this.validaObrigatoriedade(projetoDTO);
            }

            Integer idOs = null;
            if (projetoDTO.getIdOs() != null) {
                idOs = new Integer(projetoDTO.getIdOs());
            }
            if (projetoDTO.getVinculoOS() == null || !projetoDTO.getVinculoOS().equalsIgnoreCase("S")) {
                projetoDTO.setIdOs(null);
            }
            crudDao.update(model);
            recursoProjetoDao.deleteByIdProjeto(projetoDTO.getIdProjeto());
            if (projetoDTO.getColRecursos() != null) {
                for (final Iterator it = projetoDTO.getColRecursos().iterator(); it.hasNext();) {
                    final RecursoProjetoDTO recursoProjetoDTO = (RecursoProjetoDTO) it.next();
                    recursoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
                    if (recursoProjetoDTO.getCustoHora() == null) {
                        recursoProjetoDTO.setCustoHora(new Double(0));
                    }
                    recursoProjetoDao.create(recursoProjetoDTO);
                }
            }

            assinaturaAprovacaoProjetoDao.deleteByIdProjeto(projetoDTO.getIdProjeto());
            if (projetoDTO.getColAssinaturasAprovacoes() != null) {
                for (final Iterator it = projetoDTO.getColAssinaturasAprovacoes().iterator(); it.hasNext();) {
                    final AssinaturaAprovacaoProjetoDTO assinaturaAprovacaoProjetoDTO = (AssinaturaAprovacaoProjetoDTO) it.next();
                    assinaturaAprovacaoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
                    if (assinaturaAprovacaoProjetoDTO.getPapel() == null) {
                        assinaturaAprovacaoProjetoDTO.setPapel(" ");
                    }
                    if (assinaturaAprovacaoProjetoDTO.getOrdem() == null) {
                        assinaturaAprovacaoProjetoDTO.setOrdem(" ");
                    }
                    assinaturaAprovacaoProjetoDao.create(assinaturaAprovacaoProjetoDTO);
                }
            }

            if (projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S")) {
                if (projetoDTO.getIdOs() == null) {
                    OSDTO osDto = new OSDTO();
                    osDto.setIdContrato(projetoDTO.getIdContrato());
                    osDto.setNumero(projetoDTO.getNumero());
                    osDto.setIdServicoContrato(projetoDTO.getIdServicoContrato());
                    osDto.setAno(projetoDTO.getAno());
                    osDto.setNomeAreaRequisitante(projetoDTO.getNomeAreaRequisitante());
                    osDto.setDemanda(projetoDTO.getDemanda());
                    osDto.setObjetivo(projetoDTO.getObjetivo());
                    osDto.setSituacaoOS(OSDTO.EM_CRIACAO);
                    osDto.setDataEmissao(projetoDTO.getDataEmissao());
                    osDto.setDataInicio(UtilDatas.getDataAtual());
                    osDto.setDataFim(UtilDatas.getDataAtual());
                    osDto = (OSDTO) osDao.create(osDto);

                    ProjetoDTO projetoAux = new ProjetoDTO();
                    projetoAux = (ProjetoDTO) crudDao.restore(model);
                    projetoAux.setIdProjeto(projetoDTO.getIdProjeto());
                    projetoAux.setIdOs(osDto.getIdOS());
                    crudDao.updateNotNull(projetoAux);
                } else {
                    OSDTO osDto = new OSDTO();
                    osDto.setIdOS(projetoDTO.getIdOs());
                    osDto = (OSDTO) osDao.restore(osDto);
                    if (osDto != null) {
                        osDto.setIdContrato(projetoDTO.getIdContrato());
                        osDto.setNumero(projetoDTO.getNumero());
                        osDto.setIdServicoContrato(projetoDTO.getIdServicoContrato());
                        osDto.setAno(projetoDTO.getAno());
                        osDto.setNomeAreaRequisitante(projetoDTO.getNomeAreaRequisitante());
                        osDto.setDemanda(projetoDTO.getDemanda());
                        osDto.setObjetivo(projetoDTO.getObjetivo());
                        osDto.setDataEmissao(projetoDTO.getDataEmissao());
                        osDao.update(osDto);
                    }
                }
            } else {
                if (idOs != null) {
                    final OSDTO osDto = new OSDTO();
                    osDto.setIdOS(idOs);
                    osDao.delete(osDto);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private void validaObrigatoriedade(final ProjetoDTO projetoDTO) throws LogicException {
        if (projetoDTO.getIdServicoContrato() == null) {
            throw new LogicException(this.i18nMessage("projeto.obrigatorioServico"));
        }
        if (projetoDTO.getNumero() == null || projetoDTO.getNumero().equals("")) {
            throw new LogicException(this.i18nMessage("projeto.obrigatorioNumero"));
        }
        if (projetoDTO.getNomeAreaRequisitante() == null || projetoDTO.getNomeAreaRequisitante().equals("")) {
            throw new LogicException(this.i18nMessage("projeto.obrigatorioRequisitante"));
        }
    }

}
