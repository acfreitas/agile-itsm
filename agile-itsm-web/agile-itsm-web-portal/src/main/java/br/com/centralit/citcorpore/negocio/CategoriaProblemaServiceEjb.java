package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CategoriaProblemaServiceEjb extends CrudServiceImpl implements CategoriaProblemaService {

    private CategoriaProblemaDAO dao;

    @Override
    protected CategoriaProblemaDAO getDao() {
        if (dao == null) {
            dao = new CategoriaProblemaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdCategoriaProblema(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoriaProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCategoriaProblema(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCategoriaProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByNomeCategoria(final String parm) throws Exception {
        try {
            return this.getDao().findByNomeCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByNomeCategoria(final String parm) throws Exception {
        try {
            this.getDao().deleteByNomeCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listHierarquia() throws Exception {
        final Collection colFinal = new ArrayList();
        try {
            final Collection col = this.getDao().findCategoriaProblemaSemPai();
            if (col != null) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final CategoriaProblemaDTO categoriaMudancaDto = (CategoriaProblemaDTO) it.next();
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

    private Collection getCollectionHierarquia(final CategoriaProblemaDTO idCategoriaProblema, final Integer nivel) throws Exception {
        final Collection col = this.getDao().findByIdCategoriaProblemaPai(idCategoriaProblema.getIdCategoriaProblemaPai());
        final Collection colFinal = new ArrayList();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CategoriaProblemaDTO categoriaMudancaDto = (CategoriaProblemaDTO) it.next();
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
    public Collection findByNomeCategoriaProblema(final CategoriaProblemaDTO categoriaProblemaDto) throws Exception {
        try {
            return this.getDao().findByNomeCategoriaProblema(categoriaProblemaDto);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<CategoriaProblemaDTO> findByIdTemplate(final Integer idTemplate) throws Exception {
        return this.getDao().findByIdTemplate(idTemplate);
    }

    @Override
    public void desvincularCategoriaProblemasRelacionadasTemplate(final Integer idTemplate) throws Exception {
        final TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.getDao().setTransactionControler(transactionControler);

        final Collection<CategoriaProblemaDTO> listaCategoriaProblema = this.findByIdTemplate(idTemplate);

        if (listaCategoriaProblema != null && !listaCategoriaProblema.isEmpty()) {
            transactionControler.start();
            for (final CategoriaProblemaDTO categoriaProblemaDTO : listaCategoriaProblema) {
                categoriaProblemaDTO.setIdTemplate(null);
                this.getDao().update(categoriaProblemaDTO);
            }
            transactionControler.commit();
            transactionControler.close();
        }
    }

    @Override
    public Collection getAtivos() throws Exception {
        return this.getDao().getAtivos();
    }

    @Override
    public boolean consultarCategoriasAtivas(final CategoriaProblemaDTO obj) throws Exception {
        return this.getDao().consultarCategoriasAtivas(obj);
    }
}
