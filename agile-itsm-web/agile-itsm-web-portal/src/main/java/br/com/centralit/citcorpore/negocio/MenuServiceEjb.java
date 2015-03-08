package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoMenuDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MenuServiceEjb extends CrudServiceImpl implements MenuService {

    private MenuDao dao;

    @Override
    protected MenuDao getDao() {
        if (dao == null) {
            dao = new MenuDao();
        }
        return dao;
    }

    @Override
    public void updateNotNull(final Collection<MenuDTO> menus) {
        this.getDao().updateNotNull(menus);
    }

    @Override
    public Collection listarMenus() throws Exception {
        return this.getDao().listarMenus();
    }

    @Override
    public Collection<MenuDTO> listarSubMenus(final MenuDTO submenu) throws Exception {
        return this.getDao().listarSubMenus(submenu);
    }

    @Override
    public Collection<MenuDTO> listarMenusPorPerfil(final UsuarioDTO usuario, final Integer idMenuPai) throws Exception {
        return this.getDao().listarMenusPorPerfil(usuario, idMenuPai, false);
    }

    @Override
    public Collection<MenuDTO> listarMenusPorPerfil(final UsuarioDTO usuario, final Integer idMenuPai, final boolean menuRapido) throws Exception {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        Collection<MenuDTO> collection = null;
        try {
            tc.start();

            this.getDao().setTransactionControler(tc);

            collection = this.getDao().listarMenusPorPerfil(usuario, idMenuPai, menuRapido);

            tc.commit();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.close();
        }
        return collection;
    }

    @Override
    public void criaMenus(final Integer idUsuario) throws Exception {
        final PerfilAcessoDTO perfilAcessoDTO = new PerfilAcessoDTO();
        final PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
        final PerfilAcessoUsuarioDTO perfilAcessoUsuarioDto = new PerfilAcessoUsuarioDTO();

        final Collection listaMenus = this.getDao().list();
        if (listaMenus == null || listaMenus.size() == 0) {
            final String[] paiNome = {"Gerência Conhecimento", "Gerência Configuração", "Gerência Serviços", "Gerência Contratos", "Gerência de Pessoal", "Relatório", "Cadastros",
                    "Visões e Meta Dados", "Sistema", "Justificação de Falhas", "Inventário", "Eventos", "Incidentes/Serviços"};

            final String[] paiDescricao = {"Gerenciamento de Conhecimento", "Gerenciamento de Configuração", "Gerenciamento de Serviços", "Gerenciamento de Contratos",
                    "Gerenciamento de Pessoal", "Relatórios", "Cadastros", "Visões e Meta Dados", "Sistema", "Justificação de Falhas", "Levantamento de Inventário",
                    "Execução de Eventos", "Abertura de Incidentes e Serviços"};

            final String[] paiLink = {"", "", "", "", "", "", "", "", "", "/justificacaoFalhas/justificacaoFalhas.load", "/inventario/inventario.load",
                    "/eventoItemConfig/eventoItemConfig.load", "/gerenciamentoServicos/gerenciamentoServicos.load"};

            final String[] paiOrdem = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

            final String[] paiImagem = {"user_comment.png", "books_2.png", "cog_3.png", "documents.png", "users.png", "graph.png", "list_w_images.png", "strategy.png",
                    "alert_2.png", "alert_2.png", "hard_disk.png", "month_calendar.png", "strategy.png"};

            final String[] paiHorizontal = {"N", "N", "N", "N", "N", "N", "N", "N", "N", "S", "S", "S", "S"};

            // FILHOS GERÊNCIA CONHECIMENTO
            final String[] filhoNomeGerenciaConhecimento = {"Base de Conhecimento", "Pasta"};

            final String[] filhoDescricaoGerenciaConhecimento = {"Base de Conhecimento", "Pasta"};

            final String[] filhoLinkGerenciaConhecimento = {"/baseConhecimento/baseConhecimento.load", "/pasta/pasta.load"};

            final String[] filhoOrdemGerenciaConhecimento = {"0", "1"};

            final String[] filhoImagemGerenciaConhecimento = {"user_comment.png", "documents.png"};

            final String[] filhoHorizontalGerenciaConhecimento = {"N", "N"};

            // FILHOS GERÊNCIA CONFIGURAÇÃO
            final String[] filhoNomeGerenciaConfiguracao = {"Pesquisa Item Config.", "Tipo Item Configuração", "Característica", "Softwares Inst/Des.", "Item de Configuração"};

            final String[] filhoDescricaoGerenciaConfiguracao = {"Pesquisa de Item de Configuração", "Tipo de Item Configuração", "Característica dos Itens de Configuração",
                    "Softwares para Instalação/Desinstalação", "Item de Configuração"};

            final String[] filhoLinkGerenciaConfiguracao = {"/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load", "/tipoItemConfiguracao/tipoItemConfiguracao.load",
                    "/caracteristica/caracteristica.load", "/baseItemConfiguracao/baseItemConfiguracao.load", "/itemConfiguracao/itemConfiguracao.load"};

            final String[] filhoOrdemGerenciaConfiguracao = {"0", "1", "2", "3", "4"};

            final String[] filhoImagemGerenciaConfiguracao = {"books_2.png", "books_2.png", "books_2.png", "books_2.png", "books_2.png"};

            final String[] filhoHorizontalGerenciaConfiguracao = {"N", "N", "N", "N", "N"};

            // FILHOS GERÊNCIA SERVIÇOS
            final String[] filhoNomeGerenciaServico = {"Minhas Requisições", "Mapa Desenho Serviço", "Modelo de Email", "Serviços", "Situação de Serviço", "Prioridade",
                    "Condição de Operação", "Importância Negócio", "Categoria Serviço", "Tipo Serviço", "Pesquisa Sol. Serviço"};

            final String[] filhoDescricaoGerenciaServico = {"Minhas Requisições", "Mapa Desenho Serviço", "Modelo de Email", "Serviços", "Situação de Serviço", "Prioridade",
                    "Condição de Operação", "Importância Negócio", "Categoria Serviço", "Tipo Serviço", "Pesquisa Sol. Serviço"};

            final String[] filhoLinkGerenciaServico = {"/resumoSolicitacoesServicos/resumoSolicitacoesServicos.load", "/mapaDesenhoServico/mapaDesenhoServico.load",
                    "/modeloEmail/modeloEmail.load", "/dinamicViews/dinamicViews.load?idVisao=17", "/situacaoServico/situacaoServico.load", "/prioridade/prioridade.load",
                    "/condicaoOperacao/condicaoOperacao.load", "/importanciaNegocio/importanciaNegocio.load", "/categoriaServico/categoriaServico.load",
                    "/tipoServico/tipoServico.load", "/pesquisaSolicitacoesServicos/pesquisaSolicitacoesServicos.load"};

            final String[] filhoOrdemGerenciaServico = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

            final String[] filhoImagemGerenciaServico = {"cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png",
                    "cog_3.png", "cog_3.png"};

            final String[] filhoHorizontalGerenciaServico = {"N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N"};

            // FILHOS GERÊNCIA CONTRATOS
            final String[] filhoNomeGerenciaContrato = {"Administração de Contratos", "Agenda Ativ. Periódicas", "Cadastro de Contratos", "Atividade Periódica"};

            final String[] filhoDescricaoGerenciaContrato = {"Administração de Contratos", "Agenda Atividades Periódicas", "Cadastro de Contratos", "Atividade Periódica"};

            final String[] filhoLinkGerenciaContrato = {"/informacoesContrato/informacoesContrato.load", "/agendaAtvPeriodicas/agendaAtvPeriodicas.load",
                    "/dinamicViews/dinamicViews.load?idVisao=20", "/atividadePeriodica/atividadePeriodica.load"};

            final String[] filhoOrdemGerenciaContrato = {"0", "1", "2", "3"};

            final String[] filhoImagemGerenciaContrato = {"documents.png", "documents.png", "documents.png", "documents.png"};

            final String[] filhoHorizontalGerenciaContrato = {"N", "N", "N", "N"};

            // FILHOS GERÊNCIA DE PESSOAL
            final String[] filhoNomeGerenciaPessoal = {"Calendário", "Jornada de Trabalho", "Colaborador", "Grupo", "Usuário", "Unidade", "Tipo de Unidade", "Perfil Acesso",
                    "Cargos"};

            final String[] filhoDescricaoGerenciaPessoal = {"Calendário", "Jornada de Trabalho", "Colaborador", "Grupo", "Usuário", "Unidade", "Tipo de Unidade", "Perfil Acesso",
                    "Cargos"};

            final String[] filhoLinkGerenciaPessoal = {"/calendario/calendario.load", "/jornadaTrabalho/jornadaTrabalho.load", "/empregado/empregado.load", "/grupo/grupo.load",
                    "/usuario/usuario.load", "/unidade/unidade.load", "/tipoUnidade/tipoUnidade.load", "/perfilAcesso/perfilAcesso.load", "/cargos/cargos.load"};

            final String[] filhoOrdemGerenciaPessoal = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};

            final String[] filhoImagemGerenciaPessoal = {"users.png", "users.png", "users.png", "users.png", "users.png", "users.png", "users.png", "users.png", "users.png"};

            final String[] filhoHorizontalGerenciaPessoal = {"N", "N", "N", "N", "N", "N", "N", "N", "N"};

            // FILHOS RELATÓRIO
            final String[] filhoNomeGerenciaRelatorio = {"Gantt", "Gráficos", "Gráfico Tempo Real", "Quantitativo de Incidentes / Solicitações", "Relatório Base de Conhecimento",
                    "Utilização das USTs"};

            final String[] filhoDescricaoGerenciaRelatorio = {"Gantt", "Gráficos", "Gráfico Tempo Real", "Relatório Quantitativo de Incidentes / Solicitações",
                    "Relatório Base de Conhecimento", "Relatório de Utilização das USTs"};

            final String[] filhoLinkGerenciaRelatorio = {"/ganttSolicitacaoServico/ganttSolicitacaoServico.load", "/painel/painel.load", "/graficos/graficos.load",
                    "/relatorioQuantitativo/relatorioQuantitativo.load", "/relatorioBaseConhecimento/relatorioBaseConhecimento.load",
                    "/relatorioOrdemServicoUst/relatorioOrdemServicoUst.load"};

            final String[] filhoOrdemGerenciaRelatorio = {"0", "1", "2", "3", "4", "5"};

            final String[] filhoImagemGerenciaRelatorio = {"graph.png", "graph.png", "graph.png", "documents.png", "documents.png", "documents.png"};

            final String[] filhoHorizontalGerenciaRelatorio = {"N", "N", "N", "N", "N", "N"};

            // FILHOS CADASTROS
            final String[] filhoNomeGerenciaCadastro = {"Administração de Contratos", "Agenda Ativ. Periódicas", "Cadastro de Contratos", "Atividade Periódica"};

            final String[] filhoDescricaoGerenciaCadastro = {"Administração de Contratos", "Agenda Atividades Periódicas", "Cadastro de Contratos", "Atividade Periódica"};

            final String[] filhoLinkGerenciaCadastro = {"/informacoesContrato/informacoesContrato.load", "/agendaAtvPeriodicas/agendaAtvPeriodicas.load",
                    "/dinamicViews/dinamicViews.load?idVisao=20", "/atividadePeriodica/atividadePeriodica.load"};

            final String[] filhoOrdemGerenciaCadastro = {"0", "1", "2", "3"};

            final String[] filhoImagemGerenciaCadastro = {"documents.png", "documents.png", "documents.png", "documents.png"};

            final String[] filhoHorizontalGerenciaCadastro = {"N", "N", "N", "N"};

            // FILHOS VISÕES E META DADOS
            final String[] filhoNomeGerenciaVisoesMetaDados = {"Questionário", "Carrega Meta Dados", "Manutenção de Visões"};

            final String[] filhoDescricaoGerenciaVisoesMetaDados = {"Questionário", "Carrega Meta Dados", "Manutenção de Visões"};

            final String[] filhoLinkGerenciaVisoesMetaDados = {"/questionario/questionario.load", "/dataBaseMetaDados/dataBaseMetaDados.load", "/visaoAdm/visaoAdm.load"};

            final String[] filhoOrdemGerenciaVisoesMetaDados = {"0", "1", "2"};

            final String[] filhoImagemGerenciaVisoesMetaDados = {"strategy.png", "strategy.png", "strategy.png"};

            final String[] filhoHorizontalGerenciaVisoesMetaDados = {"N", "N", "N"};

            // FILHOS SISTEMA
            final String[] filhoNomeGerenciaSistema = {"Parâmetros CITSmart"};

            final String[] filhoDescricaoGerenciaSistema = {"Parâmetros CITSmart"};

            final String[] filhoLinkGerenciaSistema = {"/parametroCorpore/parametroCorpore.load"};

            final String[] filhoOrdemGerenciaSistema = {"0"};

            final String[] filhoImagemGerenciaSistema = {"alert_2.png"};

            final String[] filhoHorizontalGerenciaSistema = {"N"};

            perfilAcessoDTO.setIdPerfilAcesso(1);
            perfilAcessoUsuarioDto.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
            perfilAcessoUsuarioDto.setIdUsuario(idUsuario);
            perfilAcessoUsuarioDto.setDataInicio(UtilDatas.getDataAtual());

            // CRIAÇÃO DOS MENUS
            int i = 0;
            for (final String pai : paiNome) {
                MenuDTO dto = new MenuDTO();
                final PerfilAcessoMenuDTO perfilAcessoMenuDTOPai = new PerfilAcessoMenuDTO();

                dto.setDataInicio(UtilDatas.getDataAtual());
                dto.setNome(pai);
                dto.setDescricao(paiDescricao[i]);
                dto.setImagem(paiImagem[i]);
                dto.setOrdem(new Integer(paiOrdem[i]));
                dto.setLink(paiLink[i]);
                dto.setHorizontal(paiHorizontal[i]);
                i++;
                dto = (MenuDTO) this.getDao().create(dto);

                perfilAcessoMenuDTOPai.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                perfilAcessoMenuDTOPai.setIdMenu(dto.getIdMenu());
                perfilAcessoMenuDTOPai.setPesquisa("S");
                perfilAcessoMenuDTOPai.setGrava("S");
                perfilAcessoMenuDTOPai.setDeleta("S");
                perfilAcessoMenuDTOPai.setDataInicio(UtilDatas.getDataAtual());
                perfilAcessoMenuDao.create(perfilAcessoMenuDTOPai);

                // FILHOS GERÊNCIA CONHECIMENTO
                if (pai.equals("Gerência Conhecimento")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaConhecimento) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaConhecimento[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaConhecimento[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaConhecimento[y]));
                        dtoFilho.setLink(filhoLinkGerenciaConhecimento[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaConhecimento[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // FILHOS GERÊNCIA CONFIGURAÇÃO
                if (pai.equals("Gerência Configuração")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaConfiguracao) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaConfiguracao[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaConfiguracao[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaConfiguracao[y]));
                        dtoFilho.setLink(filhoLinkGerenciaConfiguracao[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaConfiguracao[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // FILHOS GERÊNCIA SERVIÇO
                if (pai.equals("Gerência Serviços")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaServico) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaServico[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaServico[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaServico[y]));
                        dtoFilho.setLink(filhoLinkGerenciaServico[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaServico[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // FILHOS GERÊNCIA CONTRATOS
                if (pai.equals("Gerência Contratos")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaContrato) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaContrato[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaContrato[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaContrato[y]));
                        dtoFilho.setLink(filhoLinkGerenciaContrato[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaContrato[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // FILHOS GERÊNCIA PESSOAL
                if (pai.equals("Gerência de Pessoal")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaPessoal) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaPessoal[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaPessoal[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaPessoal[y]));
                        dtoFilho.setLink(filhoLinkGerenciaPessoal[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaPessoal[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // RELATÓRIO
                if (pai.equals("Relatório")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaRelatorio) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaRelatorio[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaRelatorio[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaRelatorio[y]));
                        dtoFilho.setLink(filhoLinkGerenciaRelatorio[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaRelatorio[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // CADASTROS
                if (pai.equals("Cadastros")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaCadastro) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaCadastro[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaCadastro[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaCadastro[y]));
                        dtoFilho.setLink(filhoLinkGerenciaCadastro[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaCadastro[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // VISÕES E META DADOS
                if (pai.equals("Visões e Meta Dados")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaVisoesMetaDados) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaVisoesMetaDados[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaVisoesMetaDados[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaVisoesMetaDados[y]));
                        dtoFilho.setLink(filhoLinkGerenciaVisoesMetaDados[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaVisoesMetaDados[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }

                // SISTEMA
                if (pai.equals("Sistema")) {
                    int y = 0;
                    for (final String filho : filhoNomeGerenciaSistema) {
                        final PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
                        MenuDTO dtoFilho = new MenuDTO();
                        dtoFilho.setDataInicio(UtilDatas.getDataAtual());
                        dtoFilho.setNome(filho);
                        dtoFilho.setDescricao(filhoDescricaoGerenciaSistema[y]);
                        dtoFilho.setImagem(filhoImagemGerenciaSistema[y]);
                        dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaSistema[y]));
                        dtoFilho.setLink(filhoLinkGerenciaSistema[y]);
                        dtoFilho.setHorizontal(filhoHorizontalGerenciaSistema[y]);
                        dtoFilho.setIdMenuPai(dto.getIdMenu());
                        y++;
                        dtoFilho = (MenuDTO) this.getDao().create(dtoFilho);

                        perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
                        perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
                        perfilAcessoMenuDTOFilho.setPesquisa("S");
                        perfilAcessoMenuDTOFilho.setGrava("S");
                        perfilAcessoMenuDTOFilho.setDeleta("S");
                        perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

                        perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
                    }
                }
            }
        }
    }

    @Override
    public Collection<MenuDTO> listaMenuByUsr(final UsuarioDTO usuario) throws Exception {
        return this.getDao().listaMenuByUsr(usuario);
    }

    @Override
    public boolean verificaSeExisteMenu(final MenuDTO menuDTO) throws Exception {
        return this.getDao().verificaSeExisteMenu(menuDTO);
    }

    @Override
    public Integer buscarIdMenu(final String link) throws Exception {
        return this.getDao().buscarIdMenu(link);
    }

    @Override
    public Collection<MenuDTO> listarMenusPais() throws Exception {
        return this.getDao().listarMenusPais();
    }

    @Override
    public Collection<MenuDTO> listarMenusFilhos(final Integer idMenuPai) throws Exception {
        return this.getDao().listarMenusFilhos(idMenuPai);
    }

    @Override
    public void gerarCarga(final File file) throws Exception {
        final MenuDao menuDao = new MenuDao();
        final PerfilAcessoDao perfilAcessoDAO = new PerfilAcessoDao();
        final PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
        final TransactionControler tc = new TransactionControlerImpl(menuDao.getAliasDB());

        tc.start();

        menuDao.setTransactionControler(tc);
        perfilAcessoMenuDao.setTransactionControler(tc);
        perfilAcessoDAO.setTransactionControler(tc);
        final Integer idPerfilAcesso = perfilAcessoDAO.listarIdAdministrador();
        if (idPerfilAcesso != null) {
            try {
                final List<MenuDTO> menusPais = (List) menuDao.listarMenusPais();
                final SAXBuilder sb = new SAXBuilder();
                final Document doc = sb.build(file);
                final Element elements = doc.getRootElement();
                final List<Element> menuSuperior = elements.getChild("menuSuperior").getChildren();
                for (final Element menuCarregadoXMLElement : menuSuperior) {
                    final int j = 0;
                    final MenuDTO menuCarregadoXmlDTO = new MenuDTO();
                    MenuDTO menuDtoAux = new MenuDTO();
                    menuCarregadoXmlDTO.setNome(menuCarregadoXMLElement.getChildText("nome").trim());
                    menuCarregadoXmlDTO.setDescricao(menuCarregadoXMLElement.getChildText("descricao"));
                    menuCarregadoXmlDTO.setOrdem(Integer.parseInt(menuCarregadoXMLElement.getChildText("ordem")));
                    menuCarregadoXmlDTO.setLink(menuCarregadoXMLElement.getChildText("link").trim());
                    menuCarregadoXmlDTO.setImagem(menuCarregadoXMLElement.getChildText("imagem"));
                    menuCarregadoXmlDTO.setHorizontal(menuCarregadoXMLElement.getChildText("horizontal"));
                    menuCarregadoXmlDTO.setMenuRapido(menuCarregadoXMLElement.getChildText("menuRapido"));
                    menuCarregadoXmlDTO.setDataInicio(UtilDatas.getDataAtual());
                    menuCarregadoXmlDTO.setMostrar(menuCarregadoXMLElement.getChildText("mostrar"));

                    for (final MenuDTO menuDoBancoDTO : menusPais) {
                        if (menuCarregadoXmlDTO.getNome() != null && menuDoBancoDTO.getNome() != null) {
                            if (menuDoBancoDTO.getNome().trim().replaceAll(" ", "").equalsIgnoreCase(menuCarregadoXmlDTO.getNome().trim().replaceAll(" ", ""))
                                    && menuDoBancoDTO.getLink().trim().equalsIgnoreCase(menuCarregadoXmlDTO.getLink().trim())) {
                                menuCarregadoXmlDTO.setIdMenu(menuDoBancoDTO.getIdMenu());
                                menuCarregadoXmlDTO.setDataFim(null);
                                menuDoBancoDTO.setDescricao(menuCarregadoXmlDTO.getDescricao());
                                menuDoBancoDTO.setMostrar(menuCarregadoXmlDTO.getMostrar());
                                menuDao.update(menuDoBancoDTO);
                                menuDtoAux = menuDoBancoDTO;
                                break;
                            }
                        }

                    }
                    if (menuCarregadoXmlDTO.getIdMenu() == null) {
                        if (!menuDao.verificaSeExisteMenuPorLink(menuCarregadoXmlDTO)) {
                            menuDtoAux = (MenuDTO) menuDao.create(menuCarregadoXmlDTO);
                        }
                    }

                    /* Cria Acesso ao administrador */
                    final PerfilAcessoMenuDTO perfilAcessoMenuDTO = new PerfilAcessoMenuDTO();
                    perfilAcessoMenuDTO.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoMenuDTO.setDeleta("S");
                    perfilAcessoMenuDTO.setGrava("S");
                    perfilAcessoMenuDTO.setPesquisa("S");
                    perfilAcessoMenuDTO.setIdMenu(menuDtoAux.getIdMenu());
                    perfilAcessoMenuDTO.setIdPerfilAcesso(idPerfilAcesso);
                    if (perfilAcessoMenuDao.restoreMenusAcesso(perfilAcessoMenuDTO).isEmpty()) {
                        perfilAcessoMenuDao.create(perfilAcessoMenuDTO);
                    }
                    if (!menuCarregadoXMLElement.getChild("subMenu" + j).getChildren().isEmpty()) {
                        this.importarFilhos(menuCarregadoXMLElement, j, menuDtoAux, menuDao, idPerfilAcesso, perfilAcessoMenuDao);
                    }
                }

                tc.commit();
                tc.close();
            } catch (final ServiceException e) {
                this.rollbackTransaction(tc, e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deletaMenusSemReferencia() throws Exception {
        final List<MenuDTO> menusPaisAtualizados = (List) this.getDao().listarMenusPais();
        for (final MenuDTO menuDoBancoAtualizadoDTO : menusPaisAtualizados) {
            final List<MenuDTO> menuFilho = (List) this.getDao().listarMenusFilhoByIdMenuPai(menuDoBancoAtualizadoDTO.getIdMenu());
            if ((menuDoBancoAtualizadoDTO.getLink() == null || menuDoBancoAtualizadoDTO.getLink().equalsIgnoreCase("")) && (menuFilho == null || menuFilho.isEmpty())) {
                this.getDao().deleteMenu(menuDoBancoAtualizadoDTO.getIdMenu());
            }
        }
    }

    private void importarFilhos(final Element menus, int j, final MenuDTO menuDtoAux, final MenuDao menuDao, final Integer idPerfilAcesso,
            final PerfilAcessoMenuDao perfilAcessoMenuDao) throws Exception {
        final List<Element> subMenu = menus.getChild("subMenu" + j).getChildren();
        final List<MenuDTO> menusFilhos = (List) menuDao.listarMenusFilhos(menuDtoAux.getIdMenu());
        /* menus filhos */
        for (final Element subMenus : subMenu) {
            MenuDTO menuDtoAux1 = new MenuDTO();
            final MenuDTO menuDTO = new MenuDTO();
            menuDTO.setIdMenuPai(menuDtoAux.getIdMenu());
            menuDTO.setNome(subMenus.getChildText("nome").trim());
            menuDTO.setDescricao(subMenus.getChildText("descricao"));
            String ordem = subMenus.getChildText("ordem");
            if (ordem == null || ordem.isEmpty()) {
                ordem = "0";
            }
            menuDTO.setOrdem(Integer.parseInt(ordem));
            menuDTO.setLink(subMenus.getChildText("link").trim());
            menuDTO.setImagem(subMenus.getChildText("imagem"));
            menuDTO.setHorizontal(subMenus.getChildText("horizontal"));
            menuDTO.setMenuRapido(subMenus.getChildText("menuRapido"));
            menuDTO.setDataInicio(UtilDatas.getDataAtual());
            for (final MenuDTO menusDTO : menusFilhos) {
                if (menuDTO.getNome() != null && menusDTO.getNome() != null) {
                    if (menusDTO.getNome().trim().replaceAll(" ", "").equalsIgnoreCase(menuDTO.getNome().trim().replaceAll(" ", ""))
                            && menusDTO.getLink().trim().equalsIgnoreCase(menuDTO.getLink().trim())) {
                        menuDTO.setIdMenu(menusDTO.getIdMenu());
                        menuDTO.setDataFim(null);
                        menusDTO.setDescricao(menuDTO.getDescricao());
                        menuDao.update(menusDTO);
                        menuDtoAux1 = menusDTO;
                        break;
                    }
                }
            }
            if (menuDTO.getIdMenu() == null) {
                if (menuDao.verificaSeExisteMenuPorLink(menuDTO) == false) {
                    menuDtoAux1 = (MenuDTO) menuDao.create(menuDTO);
                } else {
                    menuDao.alterarMenuPorNome(menuDTO);
                }
            }
            final PerfilAcessoMenuDTO perfilAcessoMenuDTO = new PerfilAcessoMenuDTO();
            perfilAcessoMenuDTO.setDataInicio(UtilDatas.getDataAtual());
            perfilAcessoMenuDTO.setDeleta("S");
            perfilAcessoMenuDTO.setGrava("S");
            perfilAcessoMenuDTO.setPesquisa("S");
            perfilAcessoMenuDTO.setIdMenu(menuDtoAux1.getIdMenu());
            perfilAcessoMenuDTO.setIdPerfilAcesso(idPerfilAcesso);
            if (perfilAcessoMenuDao.restoreMenusAcesso(perfilAcessoMenuDTO).isEmpty()) {
                perfilAcessoMenuDao.create(perfilAcessoMenuDTO);
            }
            if (subMenus.getChild("subMenu" + (j + 1)) != null) {
                this.importarFilhos(subMenus, j + 1, menuDtoAux1, menuDao, idPerfilAcesso, perfilAcessoMenuDao);
            }
        }
        j++;
    }
    
    /**
     * Método para obter um mapa com todos os menus que o usuário pode acessar
	 * @author thyen.chang
	 * @since 16/01/2015 - OPERAÇÃO USAIN BOLT
	 * @param usuario
	 * @return
	 * @throws Exception
     */
    @Override
    public Map<Integer, List<MenuDTO> > listaMenuPorUsuario(UsuarioDTO usuario) throws Exception{
    	return getDao().listaMenuPorUsuario(usuario);
    }

}
