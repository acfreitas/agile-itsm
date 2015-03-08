package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoRelacaoDTO;
import br.com.centralit.citcorpore.integracao.ImagemItemConfiguracaoRelacaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class ImagemItemConfiguracaoRelacaoServiceEjb extends CrudServiceImpl implements ImagemItemConfiguracaoRelacaoService {

    private ImagemItemConfiguracaoRelacaoDao dao;

    @Override
    protected ImagemItemConfiguracaoRelacaoDao getDao() {
        if (dao == null) {
            dao = new ImagemItemConfiguracaoRelacaoDao();
        }
        return dao;
    }

    @Override
    public Collection<ImagemItemConfiguracaoRelacaoDTO> findByIdImagemItemConfiguracao(final Integer idImagemItemConfiguracao) throws Exception {
        return this.getDao().findByIdImagemItemConfiguracao(idImagemItemConfiguracao);
    }

    @Override
    public ImagemItemConfiguracaoRelacaoDTO findByIdImagemItemConfiguracaoAndIdItemPai(final Integer idImagemItemConfiguracao, final Integer idImagemItemConfiguracaoPai)
            throws Exception {
        return this.getDao().findByIdImagemItemConfiguracaoAndIdItemPai(idImagemItemConfiguracao, idImagemItemConfiguracaoPai);
    }

}
