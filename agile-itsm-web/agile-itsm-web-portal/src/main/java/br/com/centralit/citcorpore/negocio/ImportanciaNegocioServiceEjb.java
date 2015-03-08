package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.ImportanciaNegocioDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ImportanciaNegocioServiceEjb extends CrudServiceImpl implements ImportanciaNegocioService {

    private ImportanciaNegocioDao dao;

    @Override
    protected ImportanciaNegocioDao getDao() {
        if (dao == null) {
            dao = new ImportanciaNegocioDao();
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
    public boolean existeRegistro(final String nome) {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nomeImportanciaNegocio", "=", nome));
        condicoes.add(new Condition("situacao", "=", "A"));
        Collection retorno = null;
        try {
            retorno = this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retorno == null ? false : true;

    }

    /**
     * Metodo reponsavel por verificar se existe um vinculo entre Inmportancia Negocio e Cadastro de Serviço
     *
     * @param idImportanciaNegocio
     * @author Ezequiel Bispo Nunes
     * @throws Exception
     * @throws PersistenceException
     * @date 26/11/2014
     */
    @Override
    public void existeVinculoCadastroServico(final Integer idImportanciaNegocio) throws PersistenceException, ServiceException, Exception {
        final Boolean existe = this.getDao().existeVinculoCadastroServico(idImportanciaNegocio);
        if (existe) {
            throw new PersistenceException("exclusao.comun.cadastroServico");
        }
    }

}
