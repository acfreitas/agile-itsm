package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImagemHistoricoDTO;
import br.com.centralit.citcorpore.integracao.ImagemHistoricoDao;
import br.com.citframework.service.CrudServiceImpl;

public class ImagemHistoricoServiceEjb extends CrudServiceImpl implements ImagemHistoricoService {

    private ImagemHistoricoDao dao;

    @Override
    protected ImagemHistoricoDao getDao() {
        if (dao == null) {
            dao = new ImagemHistoricoDao();
        }
        return dao;
    }

    @Override
    public Collection listByIdContrato(final Integer idContrato) throws Exception {
        return this.getDao().listByIdContrato(idContrato);
    }

    @Override
    public Collection listByIdContratoAndAba(final Integer idContrato, final String aba) throws Exception {
        return this.getDao().listByIdContratoAndAba(idContrato, aba);
    }

    @Override
    public ImagemHistoricoDTO findByNomeArquivo(final ImagemHistoricoDTO filter) throws Exception {
        return this.getDao().findByNomeArquivo(filter);
    }

}
