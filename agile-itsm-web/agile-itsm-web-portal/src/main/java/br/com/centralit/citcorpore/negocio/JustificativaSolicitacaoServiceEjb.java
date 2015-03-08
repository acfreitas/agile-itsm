package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.integracao.JustificativaSolicitacaoDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author breno.guimaraes
 *
 */
public class JustificativaSolicitacaoServiceEjb extends CrudServiceImpl implements JustificativaSolicitacaoService {

    private JustificativaSolicitacaoDao justificativaSolicitacaoDao;

    @Override
    protected JustificativaSolicitacaoDao getDao() {
        if (justificativaSolicitacaoDao == null) {
            justificativaSolicitacaoDao = new JustificativaSolicitacaoDao();
        }
        return justificativaSolicitacaoDao;
    }

    @Override
    public Collection<JustificativaSolicitacaoDTO> listAtivasParaSuspensao() throws Exception {
        return this.getDao().listAtivasParaSuspensao();
    }

    @Override
    public Collection<JustificativaSolicitacaoDTO> listAtivasParaAprovacao() throws Exception {
        return this.getDao().listAtivasParaAprovacao();
    }

    @Override
    public Collection<JustificativaSolicitacaoDTO> listAtivasParaViagem() throws Exception {
        return this.getDao().listAtivasParaViagem();
    }

}
