package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.centralit.citcorpore.integracao.CategoriaGaleriaImagemDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class CategoriaGaleriaImagemServiceEjb extends CrudServiceImpl implements CategoriaGaleriaImagemService {

    private CategoriaGaleriaImagemDao dao;

    @Override
    protected CategoriaGaleriaImagemDao getDao() {
        if (dao == null) {
            dao = new CategoriaGaleriaImagemDao();
        }
        return dao;
    }

    /**
     * Deleta Categoria Imagem Ativos
     *
     * @param model
     * @param document
     * @return
     * @throws Exception
     * @author cledson.junior
     */
    @Override
    public void deletarCategoriaImagem(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final CategoriaGaleriaImagemDTO categoriaGaleriaImagemDto = (CategoriaGaleriaImagemDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);
            this.getDao().setTransactionControler(tc);
            tc.start();
            categoriaGaleriaImagemDto.setDataFim(UtilDatas.getDataAtual());
            this.getDao().update(model);
            document.alert(this.i18nMessage("MSG07"));
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

    /**
     * Consultar Categoria Imagem Ativos
     *
     * @param obj
     * @return
     * @throws Exception
     * @author cledson.junior
     */
    @Override
    public boolean consultarCategoriaImagemAtivos(final CategoriaGaleriaImagemDTO obj) throws Exception {
        return this.getDao().consultarCategoriaImagemAtivos(obj);
    }

}
