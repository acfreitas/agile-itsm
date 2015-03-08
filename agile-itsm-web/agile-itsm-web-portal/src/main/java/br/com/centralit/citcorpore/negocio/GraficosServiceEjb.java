/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.GraficoPizzaDTO;
import br.com.centralit.citcorpore.integracao.GraficosDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author breno.guimaraes
 *
 */
public class GraficosServiceEjb extends CrudServiceImpl implements GraficosService {

    private GraficosDao dao;

    @Override
    protected GraficosDao getDao() {
        if (dao == null) {
            dao = new GraficosDao();
        }
        return dao;
    }

    @Override
    public ArrayList<GraficoPizzaDTO> getRelatorioPorNomeCategoria() {
        return this.getDao().getRelatorioPorNomeCategoria();
    }

    @Override
    public ArrayList<GraficoPizzaDTO> getRelatorioPorSituacao() {
        return this.getDao().getRelatorioPorSituacao();
    }

    @Override
    public ArrayList<GraficoPizzaDTO> getRelatorioPorGrupo() {
        return this.getDao().getRelatorioPorGrupo();
    }

}
