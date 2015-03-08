package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CategoriaMudancaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class CategoriaMudancaServiceEjb extends CrudServiceImpl implements CategoriaMudancaService {

    private CategoriaMudancaDao dao;

    @Override
    protected CategoriaMudancaDao getDao() {
        if (dao == null) {
            dao = new CategoriaMudancaDao();
        }
        return dao;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection findByIdCategoriaMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoriaMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCategoriaMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCategoriaMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection findByIdCategoriaMudancaPai(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoriaMudancaPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCategoriaMudancaPai(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCategoriaMudancaPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection findByNomeCategoria(final Integer parm) throws Exception {
        try {
            return this.getDao().findByNomeCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByNomeCategoria(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByNomeCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    // ////
    @Override
    @SuppressWarnings("rawtypes")
    public Collection listHierarquia() throws Exception {
        final Collection colFinal = new ArrayList();
        try {
            final Collection col = this.getDao().findCategoriaMudancaSemPai();
            if (col != null) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final CategoriaMudancaDTO categoriaMudancaDto = (CategoriaMudancaDTO) it.next();
                    categoriaMudancaDto.setNivel(0);
                    colFinal.add(categoriaMudancaDto);
                    final Collection colAux = this.getCollectionHierarquia(categoriaMudancaDto, 0);
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

    private Collection getCollectionHierarquia(final CategoriaMudancaDTO idCategoriaMudanca, final Integer nivel) throws Exception {
        final Collection col = this.getDao().findByIdCategoriaMudancaPai(idCategoriaMudanca.getIdCategoriaMudancaPai());
        final Collection colFinal = new ArrayList();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CategoriaMudancaDTO categoriaMudancaDto = (CategoriaMudancaDTO) it.next();
                categoriaMudancaDto.setNivel(nivel + 1);
                colFinal.add(categoriaMudancaDto);
                final Collection colAux = this.getCollectionHierarquia(categoriaMudancaDto, categoriaMudancaDto.getNivel());
                if (colAux != null && colAux.size() > 0) {
                    colFinal.addAll(colAux);
                }
            }
        }
        return colFinal;
    }

    @Override
    public Collection findCategoriaAtivos() {
        // TODO Auto-generated method stub
        return null;
    }

}
