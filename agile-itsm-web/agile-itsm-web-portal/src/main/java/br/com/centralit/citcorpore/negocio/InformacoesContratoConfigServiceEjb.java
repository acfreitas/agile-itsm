package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoPerfSegDTO;
import br.com.centralit.citcorpore.integracao.InformacoesContratoConfigDao;
import br.com.centralit.citcorpore.integracao.InformacoesContratoPerfSegDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class InformacoesContratoConfigServiceEjb extends CrudServiceImpl implements InformacoesContratoConfigService {

    private InformacoesContratoConfigDao dao;

    @Override
    protected InformacoesContratoConfigDao getDao() {
        if (dao == null) {
            dao = new InformacoesContratoConfigDao();
        }
        return dao;
    }

    @Override
    public Collection getAtivos() throws Exception {
        return this.getDao().getAtivos();
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final InformacoesContratoPerfSegDao daoPEPS = new InformacoesContratoPerfSegDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Seta o TransactionController para os DAOs
            this.getDao().setTransactionControler(tc);
            daoPEPS.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            InformacoesContratoConfigDTO contrato = (InformacoesContratoConfigDTO) model;
            contrato.setNome(contrato.getNome().toUpperCase());
            contrato = (InformacoesContratoConfigDTO) super.create(model);
            if (contrato.getPerfilSelecionado() != null && contrato.getPerfilSelecionado().length > 0) {
                final InformacoesContratoPerfSegDTO prontuarioSeg = new InformacoesContratoPerfSegDTO();
                prontuarioSeg.setIdInformacoesContratoConfig(contrato.getIdInformacoesContratoConfig());
                for (int i = 0; i < contrato.getPerfilSelecionado().length; i++) {
                    prontuarioSeg.setIdPerfilSeguranca(contrato.getPerfilSelecionado()[i]);
                    daoPEPS.create(prontuarioSeg);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final InformacoesContratoConfigDTO contrato = (InformacoesContratoConfigDTO) model;
        final InformacoesContratoPerfSegDao daoPEPS = new InformacoesContratoPerfSegDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Seta o TransactionController para os DAOs
            this.getDao().setTransactionControler(tc);
            daoPEPS.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            contrato.setNome(contrato.getNome().toUpperCase());
            this.getDao().update(contrato);
            daoPEPS.deleteAllByIdInformacoesContratoConfig(contrato);
            if (contrato.getPerfilSelecionado() != null && contrato.getPerfilSelecionado().length > 0) {
                final InformacoesContratoPerfSegDTO prontuarioSeg = new InformacoesContratoPerfSegDTO();
                prontuarioSeg.setIdInformacoesContratoConfig(contrato.getIdInformacoesContratoConfig());
                for (int i = 0; i < contrato.getPerfilSelecionado().length; i++) {
                    prontuarioSeg.setIdPerfilSeguranca(contrato.getPerfilSelecionado()[i]);
                    daoPEPS.create(prontuarioSeg);
                }
            }

            // Faz commit e fecha a transação.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        try {
            final InformacoesContratoConfigDTO contrato = (InformacoesContratoConfigDTO) super.restore(model);
            final InformacoesContratoPerfSegDao daoPEPS = new InformacoesContratoPerfSegDao();
            InformacoesContratoPerfSegDTO contratoSeg = new InformacoesContratoPerfSegDTO();
            contratoSeg.setIdInformacoesContratoConfig(contrato.getIdInformacoesContratoConfig());
            final Collection perfis = daoPEPS.find(contratoSeg);
            if (perfis != null && !perfis.isEmpty()) {
                contratoSeg = null;
                contrato.setPerfilSelecionado(new Integer[perfis.size()]);
                int i = 0;
                for (final Iterator it = perfis.iterator(); it.hasNext(); i++) {
                    contratoSeg = (InformacoesContratoPerfSegDTO) it.next();
                    contrato.getPerfilSelecionado()[i] = contratoSeg.getIdPerfilSeguranca();
                }
            }
            return contrato;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByPai(final Integer idCentroCustoPai) throws Exception {
        return this.getDao().findByPai(idCentroCustoPai);
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        try {
            return this.getDao().findByNome(nome);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public Collection findSemPai(final Integer idEmpresa) throws Exception {
        return this.getDao().findSemPai(idEmpresa);
    }

    @Override
    public Collection getCollectonHierarquica(final Integer idEmpresa, final boolean acrescentarInativos) throws Exception {
        final Collection colSemPai = this.findSemPai(idEmpresa);
        if (colSemPai == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();
        InformacoesContratoConfigDTO undDto;
        boolean bAcrescenta;
        for (final Iterator it = colSemPai.iterator(); it.hasNext();) {
            undDto = (InformacoesContratoConfigDTO) it.next();
            bAcrescenta = true;
            if (!acrescentarInativos) {
                if (undDto.getSituacao() == null || undDto.getSituacao().equalsIgnoreCase("A")) {
                    bAcrescenta = false;
                }
            }
            if (bAcrescenta) {
                undDto.setNivel(new Integer(0));
                colRetorno.add(undDto);

                final Collection colFilhos = this.carregaFilhos(undDto.getIdInformacoesContratoConfig(), 0, acrescentarInativos);
                if (colFilhos != null) {
                    colRetorno.addAll(colFilhos);
                }
            }
        }
        return colRetorno;
    }

    private Collection carregaFilhos(final Integer idPai, final int nivel, final boolean acrescentarInativos) throws Exception {
        final Collection colFilhos = this.findByPai(idPai);
        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        InformacoesContratoConfigDTO undDto;
        boolean bAcrescenta;
        for (final Iterator it = colFilhos.iterator(); it.hasNext();) {
            undDto = (InformacoesContratoConfigDTO) it.next();
            bAcrescenta = true;
            if (!acrescentarInativos) {
                if (undDto.getSituacao() == null || undDto.getSituacao().equalsIgnoreCase("A")) {
                    bAcrescenta = false;
                }
            }
            if (bAcrescenta) {
                undDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(undDto);

                final Collection colFilhosFilhos = this.carregaFilhos(undDto.getIdInformacoesContratoConfig(), nivel + 1, acrescentarInativos);
                if (colFilhosFilhos != null) {
                    colRetorno.addAll(colFilhosFilhos);
                }
            }
        }
        return colRetorno;
    }

}
