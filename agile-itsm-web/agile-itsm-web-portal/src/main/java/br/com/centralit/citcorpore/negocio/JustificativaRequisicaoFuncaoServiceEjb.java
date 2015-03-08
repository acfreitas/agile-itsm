package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.integracao.JustificativaRequisicaoFuncaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class JustificativaRequisicaoFuncaoServiceEjb extends CrudServiceImpl implements JustificativaRequisicaoFuncaoService {

    private JustificativaRequisicaoFuncaoDao dao;

    @Override
    protected JustificativaRequisicaoFuncaoDao getDao() {
        if (dao == null) {
            dao = new JustificativaRequisicaoFuncaoDao();
        }
        return dao;
    }

    @Override
    public boolean consultarJustificativasAtivas(final JustificativaRequisicaoFuncaoDTO obj) throws Exception {
        return this.getDao().consultarJustificativasAtivas(obj);
    }

    @Override
    public Collection<JustificativaRequisicaoFuncaoDTO> seJustificativaJaCadastrada(final JustificativaRequisicaoFuncaoDTO justificativaRequisicaoFuncaoDTO) throws Exception {
        return this.getDao().seJustificativaJaCadastrada(justificativaRequisicaoFuncaoDTO);
    }

    @Override
    public Collection<JustificativaRequisicaoFuncaoDTO> listarAtivos() throws Exception {
        return this.getDao().listarAtivos();
    }

}
