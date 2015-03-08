/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CategoriaServicoServiceEjb extends CrudServiceImpl implements CategoriaServicoService {

    private CategoriaServicoDao dao;

    @Override
    protected CategoriaServicoDao getDao() {
        if (dao == null) {
            dao = new CategoriaServicoDao();
        }
        return dao;
    }

    @Override
    public Collection listCategoriasAtivas() throws Exception {
        return this.getDao().listCategoriasAtivas();
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public boolean verificarSeCategoriaPossuiServicoOuSubCategoria(final CategoriaServicoDTO categoriaServico) throws PersistenceException, ServiceException {
        if (this.getDao().verificarSeCategoriaPossuiFilho(categoriaServico) || this.getDao().verificarSeCategoriaPossuiServico(categoriaServico)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verificarSeCategoriaExiste(final CategoriaServicoDTO categoriaServicoDTO) throws PersistenceException, ServiceException {
        return this.getDao().verificarSeCategoriaExiste(categoriaServicoDTO);
    }

    @Override
    public Collection listHierarquia() throws Exception {
        final Collection colFinal = new ArrayList();
        try {
            final Collection col = this.getDao().findSemPai();
            if (col != null) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final CategoriaServicoDTO dto = (CategoriaServicoDTO) it.next();
                    dto.setNivel(0);
                    colFinal.add(dto);
                    final Collection colAux = this.getCollectionHierarquia(dto.getIdCategoriaServico(), 0);
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

    public Collection getCollectionHierarquia(final Integer idUnidade, final Integer nivel) throws Exception {
        final Collection col = this.getDao().findByIdPai(idUnidade);
        final Collection colFinal = new ArrayList();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CategoriaServicoDTO dto = (CategoriaServicoDTO) it.next();
                dto.setNivel(nivel + 1);
                colFinal.add(dto);
                final Collection colAux = this.getCollectionHierarquia(dto.getIdCategoriaServico(), dto.getNivel());
                if (colAux != null && colAux.size() > 0) {
                    colFinal.addAll(colAux);
                }
            }
        }
        return colFinal;
    }

    @Override
    public List<CategoriaServicoDTO> listCategoriaHierarquia(final CategoriaServicoDTO categoriaServicoDTO, final List<CategoriaServicoDTO> listCategoriaHierarquia)
            throws Exception {
        CategoriaServicoDTO bean = new CategoriaServicoDTO();
        listCategoriaHierarquia.add(categoriaServicoDTO);
        if (categoriaServicoDTO.getIdCategoriaServicoPai() != null) {
            bean.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServicoPai());
            bean = (CategoriaServicoDTO) this.getDao().restore(bean);

            if (bean.getIdCategoriaServicoPai() != null) {
                this.listCategoriaHierarquia(bean, listCategoriaHierarquia);
            } else {
                listCategoriaHierarquia.add(bean);
            }
        }
        return listCategoriaHierarquia;
    }

    @Override
    public String verificaIdCategoriaServico(final HashMap mapFields) throws Exception {
        List<CategoriaServicoDTO> listaCategoriaServico = null;

        final String id = mapFields.get("IDCATEGORIASERVICO").toString().trim();
        if (id == null || id.equals("")) {
            return "0";
        }
        if (UtilStrings.soContemNumeros(id)) {
            final Integer idCategoriaServico = Integer.parseInt(id);
            listaCategoriaServico = this.getDao().findByIdCategoriaServico(idCategoriaServico);
        } else {
            listaCategoriaServico = this.getDao().findByNomeCategoria(id);
        }
        if (listaCategoriaServico != null && listaCategoriaServico.size() > 0) {
            return String.valueOf(listaCategoriaServico.get(0).getIdCategoriaServico());
        } else {
            return "0";
        }
    }

}
