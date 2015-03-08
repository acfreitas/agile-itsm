package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.negocio.LinguaService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Menu extends AjaxFormAction {

    private static final Logger LOGGER = Logger.getLogger(Menu.class);

    private static final String DOLAR = "$";

    private MenuDTO menuBean;

    @Override
    public Class<MenuDTO> getBeanClass() {
        return MenuDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        document.focusInFirstActivateField(null);
        this.preencherComboMenuPai(document, request, response);

        final String mostraBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.MOSTRAR_BOTOES_IMPORTACAO_XML_CADASTRO_MENU, "S");

        if (mostraBotoes.trim().equals("S")) {
            document.getElementById("btnGerar").setVisible(true);
            document.getElementById("btnAtualizar").setVisible(true);
        } else {
            document.getElementById("btnGerar").setVisible(false);
            document.getElementById("btnAtualizar").setVisible(false);
        }
        final HttpSession session = request.getSession();

        LinguaDTO lingua = new LinguaDTO();
        lingua.setSigla((String) session.getAttribute("locale"));
        if (lingua.getSigla() == null) {
            lingua.setSigla(UtilI18N.PORTUGUESE_SIGLA);
        } else if (lingua.getSigla().equals("")) {
            lingua.setSigla(UtilI18N.PORTUGUESE_SIGLA);
        }
        final LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
        lingua = linguaService.getIdLingua(lingua);
        document.executeScript("setaLingua(" + lingua.getIdLingua() + ")");
    }

    /**
     * Preenche a combo MenuPerfis Pai.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    private void preencherComboMenuPai(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HTMLSelect comboMenuPai = document.getSelectById("idMenuPai");
        final Collection<MenuDTO> menus = this.getMenuService().list();

        this.inicializarCombo(comboMenuPai, request);

        for (final MenuDTO menuDto : menus) {
            if (menuDto.getDataFim() == null && StringUtils.isBlank(menuDto.getLink())) {
                comboMenuPai.addOption(menuDto.getIdMenu().toString(), UtilI18N.internacionaliza(request, menuDto.getNome()));
            }
        }
    }

    /**
     * Inclui Novo MenuPesfis.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        this.setMenuBean((MenuDTO) document.getBean());

        final DicionarioDTO dicionarioDTO = new DicionarioDTO();
        if (!this.getMenuBean().getNome().startsWith(DOLAR)) {
            document.alert(UtilI18N.internacionaliza(request, "menu.nomeInvalidoChave"));
            return;
        } else {
            dicionarioDTO.setNome(StringUtils.remove(this.getMenuBean().getNome(), DOLAR));
        }

        /* Setando o menu Vertical como padrão do sistema */
        if (this.getMenuBean().getMenuRapido() == null) {
            this.getMenuBean().setHorizontal("N");
        } else {
            this.getMenuBean().setHorizontal("S");
        }
        if (this.getMenuBean().getIdMenuPai() == null) {
            this.getMenuBean().setMenuRapido(null);
        }

        if (this.getMenuBean().getIdMenu() == null || this.getMenuBean().getIdMenu().intValue() == 0) {
            if (this.getMenuService().verificaSeExisteMenu(this.getMenuBean())) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                return;
            }
            this.getMenuBean().setDataInicio(UtilDatas.getDataAtual());
            /* Seta perfil de acesso ao administrador */
            final PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
            final MenuDTO menuDTO = this.getMenuService().create(this.getMenuBean());
            final PerfilAcessoMenuDTO perfilAcessoMenuDTO = new PerfilAcessoMenuDTO();
            perfilAcessoMenuDTO.setDataInicio(UtilDatas.getDataAtual());
            perfilAcessoMenuDTO.setDeleta("S");
            perfilAcessoMenuDTO.setGrava("S");
            perfilAcessoMenuDTO.setPesquisa("S");
            perfilAcessoMenuDTO.setIdMenu(menuDTO.getIdMenu());
            perfilAcessoMenuDTO.setIdPerfilAcesso(1);
            perfilAcessoMenuService.create(perfilAcessoMenuDTO);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            // Verifica se o menu alterado é o mesmo que o Menu Pai
            if (this.getMenuBean().getIdMenu().equals(this.getMenuBean().getIdMenuPai())) {
                document.alert(UtilI18N.internacionaliza(request, "menu.validacao.menuPaiMenu"));
                return;
            }
            if (this.getMenuService().verificaSeExisteMenu(this.getMenuBean())) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                return;
            }
            this.getMenuService().update(this.getMenuBean());
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        final HTMLForm form = document.getForm("form");
        form.clear();
        document.executeScript("ativaMenuPai()");
    }

    public void saveNewPositions(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Usuario usuario = WebUtil.getUsuario(request);

        final String nomeUsuario = usuario.getNomeUsuario().trim();
        if (!nomeUsuario.equalsIgnoreCase("administrador") && !nomeUsuario.equalsIgnoreCase("admin.centralit") || !nomeUsuario.equalsIgnoreCase("consultor")) {
            document.alert(UtilI18N.internacionaliza(request, "menu.validacao.editarMenu"));
            return;
        }

        final List<MenuDTO> listaItens = (List) WebUtil.deserializeCollectionFromRequest(MenuDTO.class, "listaOrdensMenusSerializada", request);
        this.getMenuService().updateNotNull(listaItens);
    }

    /**
     * Recupera menu.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        this.setMenuBean((MenuDTO) document.getBean());

        this.preencherComboMenuPai(document, request, response);

        this.setMenuBean((MenuDTO) this.getMenuService().restore(this.getMenuBean()));

        if (this.getMenuBean().getIdMenuPai() == null) {
            document.executeScript("desativaMenuRapido()");
        } else {
            document.executeScript("ativaMenuRapido()");
        }

        final HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(this.getMenuBean());
    }

    /**
     * Exclui Tipo Item Configuração e suas características.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void update(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        this.setMenuBean((MenuDTO) document.getBean());

        if (this.getMenuBean().getIdMenu() != null && this.getMenuBean().getIdMenu() != 0) {
            this.getMenuBean().setDataFim(UtilDatas.getDataAtual());
            this.getMenuService().update(this.getMenuBean());
            document.executeScript("ativaMenuPai()");
        }
        final HTMLForm form = document.getForm("form");
        form.clear();
        document.alert(UtilI18N.internacionaliza(request, "MSG07"));
    }

    private MenuService menuService;

    /**
     * Retorna instância de MenuPerfisService.
     *
     * @return EmpregadoService
     * @throws ServiceException
     * @throws Exception
     * @author thays.araujo
     */
    private MenuService getMenuService() throws ServiceException {
        if (menuService == null) {
            menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
        }
        return menuService;
    }

    /**
     * Atribui valor de MenuPerfisBean.
     *
     * @param empregado
     * @author thays.araujo
     */
    private void setMenuBean(final MenuDTO menuBean) {
        this.menuBean = menuBean;
    }

    /**
     * Retorna bean de menuPerfis.
     *
     * @return EmpregadoDTO
     * @author thays.araujo
     */
    private MenuDTO getMenuBean() {
        return menuBean;
    }

    /**
     * Iniciliza combo.
     *
     * @param componenteCombo
     * @author thays.araujo
     */
    private void inicializarCombo(final HTMLSelect componenteCombo, final HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    public void exportarMenuXml(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final Element tagMenuSuperior = new Element("menus");

        final Element tagMenu = new Element("menuSuperior");

        final Collection<MenuDTO> menusPais = this.getMenuService().listarMenusPais();

        if (menusPais != null && !menusPais.isEmpty()) {
            for (final MenuDTO menuPai : menusPais) {
                final int j = 0;

                final Element tagMenuPai = new Element("menu");
                final Element nomeMenuPai = new Element("nome");
                final Element descricaoMenuPai = new Element("descricao");
                final Element ordemMenuPai = new Element("ordem");
                final Element menuRapidoPai = new Element("menuRapido");
                final Element linkMenuPai = new Element("link");
                final Element imagemMenuPai = new Element("imagem");
                final Element horizontalMenuPai = new Element("horizontal");

                nomeMenuPai.setText(menuPai.getNome());
                descricaoMenuPai.setText(menuPai.getDescricao());
                ordemMenuPai.setText(String.valueOf(menuPai.getOrdem()));
                menuRapidoPai.setText(String.valueOf(menuPai.getMenuRapido()));
                linkMenuPai.setText(menuPai.getLink());
                imagemMenuPai.setText(menuPai.getImagem());
                horizontalMenuPai.setText(menuPai.getHorizontal());

                tagMenuPai.addContent(nomeMenuPai);
                tagMenuPai.addContent(descricaoMenuPai);
                tagMenuPai.addContent(ordemMenuPai);
                tagMenuPai.addContent(menuRapidoPai);
                tagMenuPai.addContent(linkMenuPai);
                tagMenuPai.addContent(imagemMenuPai);
                tagMenuPai.addContent(horizontalMenuPai);

                Element tagMenuInferior = new Element("subMenu" + j);

                tagMenuInferior = this.gerarTagMenu(menuPai, tagMenuInferior, j);

                tagMenuPai.addContent(tagMenuInferior);

                tagMenu.addContent(tagMenuPai);
            }
        }

        tagMenuSuperior.addContent(tagMenu);

        final Document doc = new Document();

        doc.setRootElement(tagMenuSuperior);

        try {
            final String separator = System.getProperty("file.separator");
            final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator;

            final File file = new File(diretorioReceita + "menu.xml");

            final Writer out = new OutputStreamWriter(new FileOutputStream(file));

            final XMLOutputter xout = new XMLOutputter();

            xout.setFormat(Format.getCompactFormat().setEncoding("ISO-8859-1"));

            xout.output(doc, out);

            document.alert(UtilI18N.internacionaliza(request, "menu.criarXml"));
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private Element gerarTagMenu(final MenuDTO menuPai, final Element tagMenuInferior, int j) throws Exception {
        final Collection<MenuDTO> menusFilhos = this.getMenuService().listarMenusFilhos(menuPai.getIdMenu());

        if (menuPai.getIdMenuPai() != null) {
            j++;
            final Element MenusInferiores = new Element("subMenu" + j);

            for (final MenuDTO menuFilho : menusFilhos) {
                Element tagMenuFilho = new Element("menu");

                final Element nomeMenuFilho = new Element("nome");
                final Element descricaoMenuFilho = new Element("descricao");
                final Element ordemMenuFilho = new Element("ordem");
                final Element menuRapidoMenuFilho = new Element("menuRapido");
                final Element linkMenuFilho = new Element("link");
                final Element imagemMenuFilho = new Element("imagem");
                final Element horizontalMenuFilho = new Element("horizontal");

                nomeMenuFilho.setText(menuFilho.getNome());
                descricaoMenuFilho.setText(menuFilho.getDescricao());
                ordemMenuFilho.setText(String.valueOf(menuFilho.getOrdem()));
                menuRapidoMenuFilho.setText(String.valueOf(menuFilho.getMenuRapido()));
                linkMenuFilho.setText(menuFilho.getLink());
                imagemMenuFilho.setText(menuFilho.getImagem());
                horizontalMenuFilho.setText(menuFilho.getHorizontal());

                tagMenuFilho.addContent(nomeMenuFilho);
                tagMenuFilho.addContent(descricaoMenuFilho);
                tagMenuFilho.addContent(ordemMenuFilho);
                tagMenuFilho.addContent(menuRapidoMenuFilho);
                tagMenuFilho.addContent(linkMenuFilho);
                tagMenuFilho.addContent(imagemMenuFilho);
                tagMenuFilho.addContent(horizontalMenuFilho);

                tagMenuFilho = this.gerarTagMenu(menuFilho, tagMenuFilho, j);

                MenusInferiores.addContent(tagMenuFilho);

            }
            tagMenuInferior.addContent(MenusInferiores);
        } else {
            for (final MenuDTO menuFilho : menusFilhos) {
                Element tagMenuFilho = new Element("menu");

                final Element nomeMenuFilho = new Element("nome");
                final Element descricaoMenuFilho = new Element("descricao");
                final Element ordemMenuFilho = new Element("ordem");
                final Element menuRapidoFilho = new Element("menuRapido");
                final Element linkMenuFilho = new Element("link");
                final Element imagemMenuFilho = new Element("imagem");
                final Element horizontalMenuFilho = new Element("horizontal");

                nomeMenuFilho.setText(menuFilho.getNome());
                descricaoMenuFilho.setText(menuFilho.getDescricao());
                ordemMenuFilho.setText(String.valueOf(menuFilho.getOrdem()));
                menuRapidoFilho.setText(String.valueOf(menuFilho.getMenuRapido()));
                linkMenuFilho.setText(menuFilho.getLink());
                imagemMenuFilho.setText(menuFilho.getImagem());
                horizontalMenuFilho.setText(menuFilho.getHorizontal());

                tagMenuFilho.addContent(nomeMenuFilho);
                tagMenuFilho.addContent(descricaoMenuFilho);
                tagMenuFilho.addContent(ordemMenuFilho);
                tagMenuFilho.addContent(menuRapidoFilho);
                tagMenuFilho.addContent(linkMenuFilho);
                tagMenuFilho.addContent(imagemMenuFilho);
                tagMenuFilho.addContent(horizontalMenuFilho);

                tagMenuFilho = this.gerarTagMenu(menuFilho, tagMenuFilho, j);

                tagMenuInferior.addContent(tagMenuFilho);
            }
        }
        return tagMenuInferior;
    }

    public void atualizarMenuXml(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final String separator = System.getProperty("file.separator");
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator;
        final File file = new File(diretorioReceita + "menu.xml");
        this.getMenuService().gerarCarga(file);
        document.alert(UtilI18N.internacionaliza(request, "menu.atualizarXml"));
    }

}
