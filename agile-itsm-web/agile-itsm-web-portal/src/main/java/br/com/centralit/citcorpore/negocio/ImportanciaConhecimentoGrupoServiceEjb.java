/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ImportanciaConhecimentoGrupoDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Vadoilo Damasceno
 *
 */
public class ImportanciaConhecimentoGrupoServiceEjb extends CrudServiceImpl implements ImportanciaConhecimentoGrupoService {

    private ImportanciaConhecimentoGrupoDAO dao;

    @Override
    protected ImportanciaConhecimentoGrupoDAO getDao() {
        if (dao == null) {
            dao = new ImportanciaConhecimentoGrupoDAO();
        }
        return dao;
    }

    @Override
    public void deleteByIdConhecimento(final Integer idBaseConhecimento, final TransactionControler transactionControler) throws Exception {
        final ImportanciaConhecimentoGrupoDAO importanciaConhecimentoGrupoDao = new ImportanciaConhecimentoGrupoDAO();
        importanciaConhecimentoGrupoDao.setTransactionControler(transactionControler);
        importanciaConhecimentoGrupoDao.deleteByIdConhecimento(idBaseConhecimento);
    }

    @Override
    public void create(final ImportanciaConhecimentoGrupoDTO importanciaConhecimentoGrupo, final TransactionControler transactionControler) throws Exception {
        final ImportanciaConhecimentoGrupoDAO importanciaConhecimentoGrupoDao = new ImportanciaConhecimentoGrupoDAO();
        importanciaConhecimentoGrupoDao.setTransactionControler(transactionControler);
        importanciaConhecimentoGrupoDao.create(importanciaConhecimentoGrupo);
    }

    @Override
    public Collection<ImportanciaConhecimentoGrupoDTO> listByIdBaseConhecimento(final Integer idBaseConhecimento) throws Exception {
        return this.getDao().listByIdBaseConhecimento(idBaseConhecimento);
    }

    @Override
    public ImportanciaConhecimentoGrupoDTO obterGrauDeImportancia(final BaseConhecimentoDTO baseConhecimentoDto, final Collection<GrupoEmpregadoDTO> listGrupoEmpregado,
            final UsuarioDTO usuarioDto) throws Exception {
        return this.getDao().obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregado, usuarioDto);
    }

}
