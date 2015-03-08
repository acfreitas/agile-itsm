package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class FornecedorServiceEjb extends CrudServiceImpl implements FornecedorService {

    private FornecedorDao dao;

    @Override
    protected FornecedorDao getDao() {
        if (dao == null) {
            dao = new FornecedorDao();
        }
        return dao;
    }

    @Override
    public Collection<FornecedorDTO> listEscopoFornecimento(final FornecedorDTO fornecedorDto) throws Exception {
        try {
            return this.getDao().listEscopoFornecimento(fornecedorDto);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean consultarCargosAtivos(final FornecedorDTO fornecedor) throws Exception {
        return this.getDao().consultarCargosAtivos(fornecedor);
    }

    @Override
    public boolean excluirFornecedor(final FornecedorDTO fornecedor) throws Exception {
        return this.getDao().excluirFornecedor(fornecedor);
    }

    /**
     * Consulta os fornecedores pelo campo razaoSocial
     * 
     * @param razaoSocial
     * @return
     * @throws Exception
     */
    @Override
    public Collection<FornecedorDTO> consultarFornecedorPorRazaoSocialAutoComplete(final String razaoSocial) throws Exception {
        try {
            return this.getDao().consultarFornecedorPorRazaoSocialAutoComplete(razaoSocial);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param idFornecedor
     * @return
     * @throws Exception
     * @author mario.haysaki
     */
    @Override
    public boolean existeFornecedorAssociadoContrato(final Integer idFornecedor) throws Exception {
        try {
            return this.getDao().existeFornecedorAssociadoContrato(idFornecedor);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
