package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoRelacaoDTO;
import br.com.citframework.service.CrudService;

public interface ImagemItemConfiguracaoRelacaoService extends CrudService {

    Collection<ImagemItemConfiguracaoRelacaoDTO> findByIdImagemItemConfiguracao(final Integer idImagemItemConfiguracao) throws Exception;

    ImagemItemConfiguracaoRelacaoDTO findByIdImagemItemConfiguracaoAndIdItemPai(final Integer idImagemItemConfiguracao, final Integer idImagemItemConfiguracaoPai) throws Exception;

}
