package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author thiago.monteiro
 */
public class CategoriaOcorrenciaServiceEjb extends CrudServiceImpl implements CategoriaOcorrenciaService {

    private CategoriaOcorrenciaDAO dao;

    @Override
    protected CategoriaOcorrenciaDAO getDao() {
        if (dao == null) {
            dao = new CategoriaOcorrenciaDAO();
        }
        return dao;
    }

    @Override
    public void deletarCategoriaOcorrencia(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);
            // Configura transações para cada entidade a ser registrada no banco de dados
            this.getDao().setTransactionControler(tc);
            // inicia transação
            tc.start();
            categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) this.getDao().restore(categoriaOcorrenciaDTO);
            categoriaOcorrenciaDTO.setDataFim(UtilDatas.getDataAtual());
            this.getDao().update(categoriaOcorrenciaDTO);
            document.alert(this.i18nMessage("MSG07"));
            // confirma transação
            tc.commit();
            // encerra transação
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public boolean consultarCategoriaOcorrenciaAtiva(final CategoriaOcorrenciaDTO obj) throws Exception {
        return this.getDao().consultarCategoriaOcorrenciaAtiva(obj);
    }

    @Override
    public CategoriaOcorrenciaDTO restoreAll(final Integer idCategoriaOcorrencia) throws Exception {
        return this.getDao().restoreAll(idCategoriaOcorrencia);
    }

}
