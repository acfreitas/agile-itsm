package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.integracao.LocalidadeUnidadeDAO;
import br.com.citframework.service.CrudServiceImpl;

public class LocalidadeUnidadeServiceEjb extends CrudServiceImpl implements LocalidadeUnidadeService {

    private LocalidadeUnidadeDAO dao;

    @Override
    protected LocalidadeUnidadeDAO getDao() {
        if (dao == null) {
            dao = new LocalidadeUnidadeDAO();
        }
        return dao;
    }

    @Override
    public Collection<LocalidadeUnidadeDTO> listaIdLocalidades(final Integer idUnidade) throws Exception {
        return this.getDao().listaIdLocalidades(idUnidade);
    }

    @Override
    public boolean verificarExistenciaDeLocalidadeEmUnidade(final Integer idLocalidade) throws Exception {
        return this.getDao().verificarExistenciaDeLocalidadeEmUnidade(idLocalidade);
    }

    @Override
    public void deleteByIdUnidade(final Integer idUnidade) throws Exception {
        this.getDao().deleteByIdUnidade(idUnidade);
    }

}
