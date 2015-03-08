package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.MarcoPagamentoPrjDTO;
import br.com.centralit.citcorpore.integracao.MarcoPagamentoPrjDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class MarcoPagamentoPrjServiceEjb extends CrudServiceImpl implements MarcoPagamentoPrjService {

    private MarcoPagamentoPrjDao dao;

    @Override
    protected MarcoPagamentoPrjDao getDao() {
        if (dao == null) {
            dao = new MarcoPagamentoPrjDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProjeto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProjeto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveFromCollection(final Collection colItens, final Integer idProjetoParm) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final MarcoPagamentoPrjDao crudDao = new MarcoPagamentoPrjDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            for (final Iterator it = colItens.iterator(); it.hasNext();) {
                MarcoPagamentoPrjDTO model = (MarcoPagamentoPrjDTO) it.next();

                model.setDataUltAlteracao(UtilDatas.getDataAtual());
                final String hora = UtilDatas.getHoraHHMM(UtilDatas.getDataHoraAtual()).replaceAll(":", "");
                model.setHoraUltAlteracao(hora);
                model.setUsuarioUltAlteracao(usuario.getNomeUsuario());
                if (model.getIdMarcoPagamentoPrj() != null && model.getIdMarcoPagamentoPrj().intValue() > 0) {
                    // Faz validacao, caso exista.
                    this.validaUpdate(model);

                    // Executa operacoes pertinentes ao negocio.
                    crudDao.updateNotNull(model);
                } else {
                    model.setSituacao("E");

                    // Faz validacao, caso exista.
                    this.validaCreate(model);

                    // Executa operacoes pertinentes ao negocio.
                    model = (MarcoPagamentoPrjDTO) crudDao.create(model);
                }
            }
            crudDao.deleteByIdProjetoNotIn(idProjetoParm, colItens);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
