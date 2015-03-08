package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.centralit.citcorpore.integracao.VersaoDao;
import br.com.centralit.citcorpore.util.FiltroSegurancaCITSmart;
import br.com.citframework.service.CrudServiceImpl;

public class VersaoServiceEjb extends CrudServiceImpl implements VersaoService {

    private VersaoDao dao;

    @Override
    protected VersaoDao getDao() {
        if (dao == null) {
            dao = new VersaoDao();
        }
        return dao;
    }

    @Override
    public void validaVersoes(final UsuarioDTO usuario) throws Exception {
        final Collection<VersaoDTO> versoes = this.getDao().list();
        for (final VersaoDTO versao : versoes) {
            if (versao.getIdUsuario() == null || versao.getIdUsuario().longValue() == 0) {
                versao.setIdUsuario(usuario.getIdUsuario());
                this.update(versao);
            }
        }
        FiltroSegurancaCITSmart.setHaVersoesSemValidacao(this.haVersoesSemValidacao());
    }

    @Override
    public VersaoDTO versaoASerValidada() throws Exception {
        return this.getDao().versaoASerValidada();
    }

    @Override
    public Collection<VersaoDTO> versoesComErrosScripts() throws Exception {
        return this.getDao().versoesComErrosScripts();
    }

    @Override
    public boolean haVersoesSemValidacao() throws Exception {
        return this.getDao().haVersoesSemValidacao();
    }

    @Override
    public VersaoDTO buscaVersaoPorNome(final String nome) throws Exception {
        return this.getDao().buscaVersaoPorNome(nome);
    }

}
