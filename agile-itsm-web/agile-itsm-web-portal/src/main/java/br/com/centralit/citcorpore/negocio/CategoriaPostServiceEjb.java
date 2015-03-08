/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaPostDTO;
import br.com.centralit.citcorpore.integracao.CategoriaPostDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CategoriaPostServiceEjb extends CrudServiceImpl implements CategoriaPostService {

    private CategoriaPostDao dao;

    @Override
    protected CategoriaPostDao getDao() {
        if (dao == null) {
            dao = new CategoriaPostDao();
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
    public boolean verificarSeCategoriaExiste(final CategoriaPostDTO categoriaPostDTO) throws PersistenceException, ServiceException {
        return this.getDao().verificarSeCategoriaExiste(categoriaPostDTO);
    }

    public List<CategoriaPostDTO> listCategoriaHierarquia(final CategoriaPostDTO categoriaServicoDTO, final List<CategoriaPostDTO> listCategoriaHierarquia) throws Exception {
        CategoriaPostDTO bean = new CategoriaPostDTO();
        listCategoriaHierarquia.add(categoriaServicoDTO);
        if (categoriaServicoDTO.getIdCategoriaPostPai() != null) {
            bean.setIdCategoriaPost(categoriaServicoDTO.getIdCategoriaPostPai());
            bean = (CategoriaPostDTO) this.getDao().restore(bean);

            if (bean.getIdCategoriaPostPai() != null) {
                this.listCategoriaHierarquia(bean, listCategoriaHierarquia);
            } else {
                listCategoriaHierarquia.add(bean);
            }
        }
        return listCategoriaHierarquia;
    }

    @Override
    public boolean verificarSeCategoriaPossuiServicoOuSubCategoria(final CategoriaPostDTO categoriaPostDTO) throws PersistenceException, ServiceException {
        if (this.getDao().verificarSeCategoriaPossuiFilho(categoriaPostDTO) || this.getDao().verificarSeCategoriaPossuiPost(categoriaPostDTO)) {
            return true;
        }
        return false;
    }

}
