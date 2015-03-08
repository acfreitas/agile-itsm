package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoMenuDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class PerfilAcessoMenuServiceEjb extends CrudServiceImpl implements PerfilAcessoMenuService {

    private PerfilAcessoMenuDao dao;

    @Override
    protected PerfilAcessoMenuDao getDao() {
        if (dao == null) {
            dao = new PerfilAcessoMenuDao();
        }
        return dao;
    }

    @Override
    public Collection<PerfilAcessoMenuDTO> restoreMenusAcesso(final IDto obj) throws Exception {
        return this.getDao().restoreMenusAcesso(obj);
    }

    @Override
    public void atualizaPerfis() throws Exception {
        final PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
        final MenuDao menuDao = new MenuDao();

        final TransactionControler tc = new TransactionControlerImpl(perfilAcessoMenuDao.getAliasDB());

        tc.start();

        perfilAcessoMenuDao.setTransactionControler(tc);
        menuDao.setTransactionControler(tc);

        final List<PerfilAcessoMenuDTO> colecaoPerfisAcessoMenu = (List) this.getDao().list();
        for (final PerfilAcessoMenuDTO perfilAcessoMenu : colecaoPerfisAcessoMenu) {
            final List<MenuDTO> menuPai = (List<MenuDTO>) menuDao.listarMenuPai(perfilAcessoMenu.getIdMenu());

            if (menuPai != null && !menuPai.isEmpty() && menuPai.get(0).getIdMenuPai() != null && menuPai.get(0).getIdMenuPai() != 0) {
                final List<PerfilAcessoMenuDTO> perfilAcessoComMenuPai = (List) this.getDao().pesquisaSeJaExisteAcessoMenuPai(perfilAcessoMenu.getIdPerfilAcesso(),
                        menuPai.get(0).getIdMenuPai());
                if (perfilAcessoComMenuPai == null || perfilAcessoComMenuPai.isEmpty()) {
                    // criar acesso para o menu pai
                    final PerfilAcessoMenuDTO perfilAcessoMenuPaiDto = new PerfilAcessoMenuDTO();
                    perfilAcessoMenuPaiDto.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoMenuPaiDto.setDeleta("S");
                    perfilAcessoMenuPaiDto.setGrava("S");
                    perfilAcessoMenuPaiDto.setPesquisa("S");
                    perfilAcessoMenuPaiDto.setIdMenu(menuPai.get(0).getIdMenuPai());
                    perfilAcessoMenuPaiDto.setIdPerfilAcesso(perfilAcessoMenu.getIdPerfilAcesso());
                    perfilAcessoMenuDao.create(perfilAcessoMenuPaiDto);
                }
            }
        }

        tc.commit();
        tc.close();
    }

    /**
     * Obtém um Mapa<idMenu, List<PerfilAcessoMenu> > de todos os menus deste usuário
     * 
     * @author thyen.chang
     * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
     */
	@Override
	public Map<Integer, List<PerfilAcessoMenuDTO> > getPerfilAcessoBotoesMenu(UsuarioDTO usuario) throws Exception {
		return this.getDao().getPerfilAcessoBotoesMenu(usuario);
	}

}
