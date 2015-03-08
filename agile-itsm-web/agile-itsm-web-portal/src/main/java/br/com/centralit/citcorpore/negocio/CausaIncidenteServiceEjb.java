package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.integracao.CausaIncidenteDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings({"rawtypes", "unchecked",})
public class CausaIncidenteServiceEjb extends CrudServiceImpl implements CausaIncidenteService {

    private CausaIncidenteDao dao;

    @Override
    protected CausaIncidenteDao getDao() {
        if (dao == null) {
            dao = new CausaIncidenteDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCausaIncidentePai(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCausaIncidentePai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCausaIncidentePai(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCausaIncidentePai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listHierarquia() throws Exception {
        final Collection colFinal = new ArrayList();
        try {
            final Collection col = this.getDao().findSemPai();
            if (col != null) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final CausaIncidenteDTO causaIncidenteDTO = (CausaIncidenteDTO) it.next();
                    causaIncidenteDTO.setNivel(0);
                    colFinal.add(causaIncidenteDTO);
                    final Collection colAux = this.getCollectionHierarquia(causaIncidenteDTO.getIdCausaIncidente(), 0);
                    if (colAux != null && colAux.size() > 0) {
                        colFinal.addAll(colAux);
                    }
                }
            }
            return colFinal;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getCollectionHierarquia(final Integer idCausa, final Integer nivel) throws Exception {
        final Collection col = this.getDao().findByIdPai(idCausa);
        final Collection colFinal = new ArrayList();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CausaIncidenteDTO causaIncidenteDTO = (CausaIncidenteDTO) it.next();
                causaIncidenteDTO.setNivel(nivel + 1);
                colFinal.add(causaIncidenteDTO);
                final Collection colAux = this.getCollectionHierarquia(causaIncidenteDTO.getIdCausaIncidente(), causaIncidenteDTO.getNivel());
                if (colAux != null && colAux.size() > 0) {
                    colFinal.addAll(colAux);
                }
            }
        }
        return colFinal;
    }

    @Override
    public Collection listaCausaByDescricaoCausa(final String descricaoCausa) throws Exception {
        return this.getDao().listaCausaByDescricaoCausa(descricaoCausa);
    }

    @Override
    public Collection listaCausasAtivas() throws Exception {
        return this.getDao().listaCausasAtivas();
    }

}
