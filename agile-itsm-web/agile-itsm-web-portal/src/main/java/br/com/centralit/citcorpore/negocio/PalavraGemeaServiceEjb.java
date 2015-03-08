/**
 * CentralIT - CITSMart
 */
package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.integracao.PalavraGemeaDAO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudServiceImpl;

public class PalavraGemeaServiceEjb extends CrudServiceImpl implements PalavraGemeaService {

    private PalavraGemeaDAO dao;

    @Override
    protected PalavraGemeaDAO getDao() {
        if (dao == null) {
            dao = new PalavraGemeaDAO();
        }
        return dao;
    }

    @Override
    public boolean VerificaSeCadastrado(final PalavraGemeaDTO palavraGemeaDto) throws PersistenceException {
        return this.getDao().VerificaSeCadastrado(palavraGemeaDto);
    }

    @Override
    public boolean VerificaSePalavraCorrespondenteExiste(final PalavraGemeaDTO palavraGemeaDTO) throws PersistenceException {
        return this.getDao().VerificaSePalavraCorrespondenteExiste(palavraGemeaDTO);
    }

}
