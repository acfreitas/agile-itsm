package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author thiago.monteiro
 */
public class OrigemOcorrenciaServiceEjb extends CrudServiceImpl implements OrigemOcorrenciaService {

    private OrigemOcorrenciaDAO dao;

    @Override
    protected OrigemOcorrenciaDAO getDao() {
        if (dao == null) {
            dao = new OrigemOcorrenciaDAO();
        }
        return dao;
    }

    @Override
    public void deletarOrigemOcorrencia(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            this.validaUpdate(model);
            this.getDao().setTransactionControler(tc);
            tc.start();
            origemOcorrenciaDTO = (OrigemOcorrenciaDTO) this.getDao().restore(origemOcorrenciaDTO);
            origemOcorrenciaDTO.setDataFim(UtilDatas.getDataAtual());
            this.getDao().update(origemOcorrenciaDTO);
            document.alert(this.i18nMessage("MSG07"));
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public boolean consultarOrigemOcorrenciaAtiva(final OrigemOcorrenciaDTO origemOcorrenciaDTO) throws Exception {
        return this.getDao().consultarOrigemOcorrenciaAtiva(origemOcorrenciaDTO);
    }

    /**
     * Metodo responsavel por retornar todos os dados da Origem de uma ocorrência
     *
     * @param idOrigem
     * @return
     * @author Ezequiel
     * @throws Exception
     * @throws ServiceException
     */
    @Override
    public OrigemOcorrenciaDTO restoreAll(final Integer idOrigem) throws ServiceException, Exception {
        return this.getDao().restoreAll(idOrigem);
    }

}
