package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.integracao.JustificativaParecerDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author breno.guimaraes
 *
 */
public class JustificativaParecerServiceEjb extends CrudServiceImpl implements JustificativaParecerService {

    private JustificativaParecerDao dao;

    @Override
    protected JustificativaParecerDao getDao() {
        if (dao == null) {
            dao = new JustificativaParecerDao();
        }
        return dao;
    }

    @Override
    public Collection<JustificativaParecerDTO> listAplicaveisCotacao() throws Exception {
        return this.getDao().listAplicaveisCotacao();
    }

    @Override
    public Collection<JustificativaParecerDTO> listAplicaveisRequisicao() throws Exception {
        return this.getDao().listAplicaveisRequisicao();
    }

    @Override
    public Collection<JustificativaParecerDTO> listAplicaveisInspecao() throws Exception {
        return this.getDao().listAplicaveisInspecao();
    }

    @Override
    public boolean consultarJustificativaAtiva(final JustificativaParecerDTO justificativa) throws Exception {
        return this.getDao().consultarJustificativaAtiva(justificativa);
    }

    @Override
    public Collection<JustificativaParecerDTO> listAplicaveisRequisicaoViagem() throws Exception {
        return this.getDao().listAplicaveisRequisicaoViagem();
    }

}
