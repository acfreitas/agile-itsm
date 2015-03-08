/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.ImportanciaConhecimentoUsuarioDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Vadoilo Damasceno
 *
 */
public class ImportanciaConhecimentoUsuarioServiceEjb extends CrudServiceImpl implements ImportanciaConhecimentoUsuarioService {

    private ImportanciaConhecimentoUsuarioDAO dao;

    @Override
    protected ImportanciaConhecimentoUsuarioDAO getDao() {
        if (dao == null) {
            dao = new ImportanciaConhecimentoUsuarioDAO();
        }
        return dao;
    }

    @Override
    public void deleteByIdConhecimento(final Integer idBaseConhecimento, final TransactionControler transactionControler) throws Exception {
        final ImportanciaConhecimentoUsuarioDAO importanciaConhecimentoUsuarioDao = new ImportanciaConhecimentoUsuarioDAO();
        importanciaConhecimentoUsuarioDao.setTransactionControler(transactionControler);
        importanciaConhecimentoUsuarioDao.deleteByIdConhecimento(idBaseConhecimento);
    }

    @Override
    public void create(final ImportanciaConhecimentoUsuarioDTO importanciaConhecimentoUsuario, final TransactionControler transactionControler) throws Exception {
        final ImportanciaConhecimentoUsuarioDAO importanciaConhecimentoUsuarioDao = new ImportanciaConhecimentoUsuarioDAO();
        importanciaConhecimentoUsuarioDao.setTransactionControler(transactionControler);
        importanciaConhecimentoUsuarioDao.create(importanciaConhecimentoUsuario);
    }

    @Override
    public Collection<ImportanciaConhecimentoUsuarioDTO> listByIdBaseConhecimento(final Integer idBaseConhecimento) throws Exception {
        return this.getDao().listByIdBaseConhecimento(idBaseConhecimento);
    }

}
