package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaSolucaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class CategoriaSolucaoServiceEjb extends CrudServiceImpl implements CategoriaSolucaoService {

    private CategoriaSolucaoDao dao;

    @Override
    protected CategoriaSolucaoDao getDao() {
        if (dao == null) {
            dao = new CategoriaSolucaoDao();
        }
        return dao;
    }

    @Override
    public Collection<CategoriaSolucaoDTO> findByIdCategoriaSolucaoPai(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoriaSolucaoPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCategoriaSolucaoPai(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCategoriaSolucaoPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<CategoriaSolucaoDTO> listHierarquia() throws Exception {
        final Collection<CategoriaSolucaoDTO> colFinal = new ArrayList<>();
        try {
            final Collection<CategoriaSolucaoDTO> col = this.getDao().findSemPai();
            if (col != null) {
                for (final CategoriaSolucaoDTO categoriaSolucaoDTO : col) {
                    categoriaSolucaoDTO.setNivel(0);
                    colFinal.add(categoriaSolucaoDTO);
                    final Collection<CategoriaSolucaoDTO> colAux = this.getCollectionHierarquia(categoriaSolucaoDTO.getIdCategoriaSolucao(), 0);
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
    public Collection<CategoriaSolucaoDTO> getCollectionHierarquia(final Integer idCateg, final Integer nivel) throws Exception {
        final Collection<CategoriaSolucaoDTO> col = this.getDao().findByIdPai(idCateg);
        final Collection<CategoriaSolucaoDTO> colFinal = new ArrayList<>();
        if (col != null) {
            for (final CategoriaSolucaoDTO categoriaSolucaoDTO : col) {
                categoriaSolucaoDTO.setNivel(nivel + 1);
                colFinal.add(categoriaSolucaoDTO);
                final Collection<CategoriaSolucaoDTO> colAux = this.getCollectionHierarquia(categoriaSolucaoDTO.getIdCategoriaSolucao(), categoriaSolucaoDTO.getNivel());
                if (colAux != null && colAux.size() > 0) {
                    colFinal.addAll(colAux);
                }
            }
        }
        return colFinal;
    }

    @Override
    public String verificaDescricaoDuplicadaCategoriaAoCriar(final Map mapFields) throws Exception {
        List<CategoriaSolucaoDTO> listaCategoriaSolucao = null;
        final String descricaoCategoria = mapFields.get("DESCRICAOCATEGORIASOLUCAO").toString().trim();
        listaCategoriaSolucao = (List<CategoriaSolucaoDTO>) this.getDao().verificaDescricaoDuplicadaCategoriaAoCriar(descricaoCategoria);
        if (listaCategoriaSolucao == null || listaCategoriaSolucao.isEmpty()) {
            return "1";
        }
        return "0";
    }

    @Override
    public String verificaDescricaoDuplicadaCategoriaAoAtualizar(final Map mapFields) throws Exception {
        List<CategoriaSolucaoDTO> listaCategoriaSolucao = null;
        final String descricaoCategoria = mapFields.get("DESCRICAOCATEGORIASOLUCAO").toString().trim();
        final String idCategoria = mapFields.get("IDCATEGORIASOLUCAO").toString().trim();
        listaCategoriaSolucao = (List<CategoriaSolucaoDTO>) this.getDao().verificaDescricaoDuplicadaCategoriaAoAtualizar(Integer.valueOf(idCategoria), descricaoCategoria);
        if (listaCategoriaSolucao == null || listaCategoriaSolucao.isEmpty()) {
            return "1";
        }
        return "0";
    }

    @Override
    public Collection<CategoriaSolucaoDTO> listaCategoriasSolucaoAtivas() throws Exception {
        return this.getDao().listaCategoriasSolucaoAtivas();
    }

}
