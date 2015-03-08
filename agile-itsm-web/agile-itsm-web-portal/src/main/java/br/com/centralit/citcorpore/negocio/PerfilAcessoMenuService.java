package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;

public interface PerfilAcessoMenuService extends CrudService{
    public Collection<PerfilAcessoMenuDTO> restoreMenusAcesso(IDto obj) throws Exception ;
    public void atualizaPerfis() throws Exception;
    /**
     * Obtém um Mapa<idMenu, List<PerfilAcessoMenu> > de todos os menus deste usuário
     * 
     * @author thyen.chang
     * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
     * @param usuario
     * @return
     * @throws Exception
     */
    public Map<Integer, List<PerfilAcessoMenuDTO> > getPerfilAcessoBotoesMenu(UsuarioDTO usuario) throws Exception;
}
