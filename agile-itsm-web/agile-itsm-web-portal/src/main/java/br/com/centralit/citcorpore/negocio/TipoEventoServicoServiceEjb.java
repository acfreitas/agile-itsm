package br.com.centralit.citcorpore.negocio;

import java.util.HashMap;

import br.com.centralit.citcorpore.integracao.TipoEventoServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TipoEventoServicoServiceEjb extends CrudServiceImpl implements TipoEventoServicoService {

    private TipoEventoServicoDao dao;

    @Override
    protected TipoEventoServicoDao getDao() {
        if (dao == null) {
            dao = new TipoEventoServicoDao();
        }
        return dao;
    }

    public boolean tipoEventoVinculadoServico(final HashMap mapFields) throws LogicException, ServiceException {
        boolean retorno = false;
        if (mapFields.get("IDTIPOEVENTOSERVICO") != null && !((String) mapFields.get("IDTIPOEVENTOSERVICO")).isEmpty()) {
            final Integer IDTIPOEVENTOSERVICO = Integer.parseInt((String) mapFields.get("IDTIPOEVENTOSERVICO"));
            try {
                retorno = this.getDao().tipoEventoVinculadoServico(IDTIPOEVENTOSERVICO);
            } catch (final Exception e) {
                throw new ServiceException(e);
            }
        }
        return retorno;
    }

}
