package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.integracao.OrigemAtendimentoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class OrigemAtendimentoServiceEjb extends CrudServiceImpl implements OrigemAtendimentoService {

    private OrigemAtendimentoDao dao;

    @Override
    protected OrigemAtendimentoDao getDao() {
        if (dao == null) {
            dao = new OrigemAtendimentoDao();
        }
        return dao;
    }

    @Override
    public void deletarOrigemAtendimento(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final OrigemAtendimentoDTO origemAtendimentoDTO = (OrigemAtendimentoDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(origemAtendimentoDTO);
            this.getDao().setTransactionControler(tc);
            tc.start();
            origemAtendimentoDTO.setDataFim(UtilDatas.getDataAtual());
            this.getDao().update(origemAtendimentoDTO);
            document.alert(this.i18nMessage("MSG07"));
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

    @Override
    public boolean consultarOrigemAtendimentoAtivos(final OrigemAtendimentoDTO obj) throws Exception {
        return this.getDao().consultarOrigemAtendimentoAtivos(obj);
    }

    /**
     * Retorna todos os registros de Origens de Antedimento que possuim dataFim == null;
     *
     * @author riubbe.oliveira
     * @return Collection<OrigemAtendimentoDTO>
     * @throws Exception
     */
    @Override
    public Collection<OrigemAtendimentoDTO> recuperaAtivos() throws Exception {
        return this.getDao().listarTodosAtivos();
    }

    @Override
    public OrigemAtendimentoDTO buscarOrigemAtendimento(final String descricao) throws ServiceException {
        return this.getDao().buscarOrigemAtendimento(descricao);
    }

}
