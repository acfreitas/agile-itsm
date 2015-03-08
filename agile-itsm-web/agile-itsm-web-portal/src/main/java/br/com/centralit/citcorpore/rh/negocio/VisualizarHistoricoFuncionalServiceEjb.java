package br.com.centralit.citcorpore.rh.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.rh.bean.ItemHistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.integracao.ItemHistoricoFuncionalDao;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("unchecked")
public class VisualizarHistoricoFuncionalServiceEjb extends CrudServiceImpl implements VisualizarHistoricoFuncionalService {

    private ItemHistoricoFuncionalDao itemHistoricoFuncionalDao;

    @Override
    protected CrudDAO getDao() {
        return null;
    }

    private ItemHistoricoFuncionalDao getItemHistoricoFuncionalDao() {
        if (itemHistoricoFuncionalDao == null) {
            itemHistoricoFuncionalDao = new ItemHistoricoFuncionalDao();
        }
        return itemHistoricoFuncionalDao;
    }

    @Override
    public ItemHistoricoFuncionalDTO restoreUsuario(final Integer id) throws Exception {
        ItemHistoricoFuncionalDTO itemHistoricoFuncionalDto = null;

        final ArrayList<ItemHistoricoFuncionalDTO> list = (ArrayList<ItemHistoricoFuncionalDTO>) this.getItemHistoricoFuncionalDao().findByIdResponsavel(id);
        if (list != null) {
            itemHistoricoFuncionalDto = list.get(0);
        }
        return itemHistoricoFuncionalDto;
    }

}
