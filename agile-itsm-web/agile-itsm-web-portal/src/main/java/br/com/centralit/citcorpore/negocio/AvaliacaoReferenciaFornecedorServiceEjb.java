package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.centralit.citcorpore.integracao.AvaliacaoReferenciaFornecedorDao;
import br.com.citframework.service.CrudServiceImpl;

public class AvaliacaoReferenciaFornecedorServiceEjb extends CrudServiceImpl implements AvaliacaoReferenciaFornecedorService {

    private AvaliacaoReferenciaFornecedorDao dao;

    @Override
    protected AvaliacaoReferenciaFornecedorDao getDao() {
        if (dao == null) {
            dao = new AvaliacaoReferenciaFornecedorDao();
        }
        return dao;
    }

    @Override
    public Collection<AvaliacaoReferenciaFornecedorDTO> listByIdAvaliacaoFornecedor(final Integer idAvaliacaoFornecedor) throws Exception {
        final Collection<AvaliacaoReferenciaFornecedorDTO> listAvaliacaoReferenciaFornecedor = this.getDao().listByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);

        if (listAvaliacaoReferenciaFornecedor != null) {
            for (final AvaliacaoReferenciaFornecedorDTO avaliacaoReferenciaFornecedor : listAvaliacaoReferenciaFornecedor) {
                if (avaliacaoReferenciaFornecedor.getDecisao().equalsIgnoreCase("S")) {
                    avaliacaoReferenciaFornecedor.setDecisao("Sim");
                } else {
                    avaliacaoReferenciaFornecedor.setDecisao("Não");
                }
            }
        }

        return listAvaliacaoReferenciaFornecedor;
    }

    @Override
    public void deleteByIdAvaliacaoFornecedor(final Integer idAvaliacaoFornecedor) throws Exception {
        this.getDao().deleteByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);
    }

}
