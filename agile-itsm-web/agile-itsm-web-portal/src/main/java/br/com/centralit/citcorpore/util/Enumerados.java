package br.com.centralit.citcorpore.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.util.UtilI18N;

public class Enumerados implements Serializable {

    private static final long serialVersionUID = -523719211080468112L;

    public enum TipoAgendamento {

        D("Diariamente"),
        S("Semanalmente"),
        M("Mensalmente"),
        U("Uma vez");

        private final String descricao;

        private TipoAgendamento(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum OrigemBaseConhecimento {

        CONHECIMENTO(1, "Conhecimento"),
        EVENTO(2, "Evento"),
        MUDANCA(3, "Mudança"),
        INCIDENTE(4, "Incidente"),
        SERVICO(5, "Serviço"),
        PROBLEMA(6, "Problema");

        private final Integer origem;
        private final String descOrigem;

        public Integer getOrigem() {
            return origem;
        }

        public String getDescOrigem() {
            return descOrigem;
        }

        public static String getDescOrigemByOrigem(final Integer origem) {
            String descStrOrigem = "";
            if (origem.intValue() == 1) {
                descStrOrigem = OrigemBaseConhecimento.CONHECIMENTO.getDescOrigem();
            }
            if (origem.intValue() == 2) {
                descStrOrigem = OrigemBaseConhecimento.EVENTO.getDescOrigem();
            }
            if (origem.intValue() == 3) {
                descStrOrigem = OrigemBaseConhecimento.MUDANCA.getDescOrigem();
            }
            if (origem.intValue() == 4) {
                descStrOrigem = OrigemBaseConhecimento.INCIDENTE.getDescOrigem();
            }
            if (origem.intValue() == 5) {
                descStrOrigem = OrigemBaseConhecimento.SERVICO.getDescOrigem();
            }
            if (origem.intValue() == 6) {
                descStrOrigem = OrigemBaseConhecimento.PROBLEMA.getDescOrigem();
            }
            return descStrOrigem;
        }

        private OrigemBaseConhecimento(final Integer origem, final String descOrigem) {
            this.origem = origem;
            this.descOrigem = descOrigem;
        }

    }

    public enum SituacaoSolicitacaoServico {

        EmAndamento(1, "Em andamento", true),
        Suspensa(2, "Suspensa", true),
        Cancelada(3, "Cancelada", true),
        Resolvida(4, "Resolvida", true),
        Reaberta(5, "Reaberta", false),
        Fechada(6, "Fechada", false),
        ReClassificada(7, "Reclassificada", false);

        private final Integer id;
        private final String descricao;
        private final boolean isToMobile;

        private SituacaoSolicitacaoServico(final Integer id, final String descricao, final boolean isToMobile) {
            this.id = id;
            this.descricao = descricao;
            this.isToMobile = isToMobile;
        }

        public Integer getId() {
            return id;
        }

        public String getDescricao() {
            return descricao;
        }

        public boolean isToMobile() {
            return isToMobile;
        }

        /**
         * Recupera uma {@link SituacaoSolicitacaoServico} de acordo com seu id
         *
         * @param id
         *            id a ser verificado se há uma {@link SituacaoSolicitacaoServico}
         * @return {@link SituacaoSolicitacaoServico} caso encontre. {@link IllegalArgumentException}, caso contrário
         * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
         * @since 30/09/2014
         */
        public static SituacaoSolicitacaoServico fromId(final Integer id) {
            final SituacaoSolicitacaoServico[] situacoes = SituacaoSolicitacaoServico.values();
            for (final SituacaoSolicitacaoServico situacao : situacoes) {
                if (situacao.getId().equals(id)) {
                    return situacao;
                }
            }
            throw new IllegalArgumentException(String.format("SituacaoSolicitacaoServico not found for id '%s'", id));
        }

        /**
         * Recupera a lista de {@link SituacaoSolicitacaoServico} que podem ser usadas como situação no Mobile
         *
         * @return {@code List<SituacaoSolicitacaoServico>}
         * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
         * @since 30/09/2014
         */
        public static List<SituacaoSolicitacaoServico> getSituacoesSolicitacaoServicoToMobile() {
            final List<SituacaoSolicitacaoServico> result = new ArrayList<>();
            final SituacaoSolicitacaoServico[] situacoes = SituacaoSolicitacaoServico.values();
            for (final SituacaoSolicitacaoServico situacao : situacoes) {
                if (situacao.isToMobile()) {
                    result.add(situacao);
                }
            }
            return result;
        }

    }

    /**
     * Enumerado que representa a situação de uma solicitação em uma rota de atendimento
     *
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 26/11/2014
     *
     */
    public enum SituacaoSolicitacaoServicoNaRota {

        ATENDIDA_FINALIZADA(1, "gestao.forca.atendimento.status.atendimento.atendido.finalizado"),
        NAO_ATENDIDA(2, "gestao.forca.atendimento.status.atendimento.nao.atendido"),
        EM_ATENDIMENTO(3, "gestao.forca.atendimento.status.atendimento.atendendo"),
        ATENDIDA_COM_PENDENCIA (4, "gestao.forca.atendimento.status.atendimento.pendencia");

        private final Integer id;
        private final String description;

        private SituacaoSolicitacaoServicoNaRota(final Integer id, final String description) {
            this.id = id;
            this.description = description;
        }

        public Integer getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        /**
         * Recupera uma {@link SituacaoSolicitacaoServicoNaRota} de acordo com seu id
         *
         * @param id
         *            id a ser verificado se há uma {@link SituacaoSolicitacaoServicoNaRota}
         * @return {@link SituacaoSolicitacaoServicoNaRota} caso encontre. {@link IllegalArgumentException}, caso contrário
         * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
         * @since 26/11/2014
         */
        public static SituacaoSolicitacaoServicoNaRota fromId(final Integer id) {
            final SituacaoSolicitacaoServicoNaRota[] situacoes = SituacaoSolicitacaoServicoNaRota.values();
            for (final SituacaoSolicitacaoServicoNaRota situacao : situacoes) {
                if (situacao.getId().equals(id)) {
                    return situacao;
                }
            }
            throw new IllegalArgumentException(String.format("SituacaoSolicitacaoServico not found for id '%s'", id));
        }

    }

    static final String DATA = "Date";
    static final String HORA = "Hora";
    static final String CNPJ = "CNPJ";
    static final String CPF = "CPF";
    static final String NUMERO = "Numero";
    static final String MOEDA = "MOEDA";
    static final String TEXTO = "Texto";
    static final String BOOLEAN = "Boolean";
    static final String EMAIL = "Email";
    static final String CEP = "CEP";
    static final String TELEFONE = "Telefone";
    static final String SENHA = "Senha";

    public enum ParametroSistema {

        ORIGEM_SISTEMA(0, "parametro.0", TEXTO),
        CaminhoArquivoNetMap(1, "parametro.1", TEXTO),
        FaixaIp(2, "parametro.2", TEXTO),
        NoPesquisa(3, "parametro.3", TEXTO),
        Atributo(4, "parametro.4", TEXTO),
        CaminhoBaseItemCfg(5, "parametro.5", TEXTO),
        DiretorioXmlAgente(6, "parametro.6", TEXTO),
        DiasInventario(7, "parametro.7", NUMERO),
        CaminhoNmap(8, "parametro.8", TEXTO),
        ID_GRUPO_PADRAO_NIVEL1(9, "parametro.9", NUMERO),
        RemetenteNotificacoesSolicitacao(10, "parametro.10", TEXTO),
        EmailAutenticacao(11, "parametro.11", BOOLEAN),
        EmailUsuario(12, "parametro.12", TEXTO),
        EmailSenha(13, "parametro.13", SENHA),
        EmailSMTP(14, "parametro.14", TEXTO),
        GedInterno(15, "parametro.15", BOOLEAN),
        GedInternoBD(16, "parametro.16", BOOLEAN),
        GedExternoClasse(17, "parametro.17", TEXTO),
        GedDiretorio(18, "parametro.18", TEXTO),
        EMPRESA_Nome(19, "parametro.19", TEXTO),
        METODO_AUTENTICACAO_Pasta(22, "parametro.22", TEXTO),
        SMTP_LEITURA_Servidor(23, "parametro.23", TEXTO),
        SMTP_LEITURA_Caixa(24, "parametro.24", TEXTO),
        SMTP_LEITURA_Senha(25, "parametro.25", SENHA),
        SMTP_LEITURA_Provider(26, "parametro.26", TEXTO),
        SMTP_LEITURA_Porta(27, "parametro.27", TEXTO),
        SMTP_LEITURA_Pasta(28, "parametro.28", TEXTO),
        NomeFluxoPadraoServicos(29, "parametro.29", TEXTO),
        IDFaseExecucaoServicos(30, "parametro.30", NUMERO),
        EnviaEmailFluxo(31, "parametro.31", BOOLEAN),
        DB_SCHEMA(32, "parametro.32", TEXTO),
        URL_Sistema(33, "parametro.33", TEXTO),
        LDAP_URL(34, "parametro.34", TEXTO),
        DOMINIO_AD(35, "parametro.35", TEXTO),
        DOMINIO_EMAIL(36, "parametro.36", TEXTO),
        LOGIN_AD(37, "parametro.37", TEXTO),
        SENHA_AD(38, "parametro.38", SENHA),
        ID_PERFIL_ACESSO_DEFAULT(39, "parametro.39", TEXTO),
        CONTROLE_ACC_UNIDADE_INC_SOLIC(40, "parametro.40", BOOLEAN),
        COLABORADORES_VINC_CONTRATOS(41, "parametro.41", BOOLEAN),
        PAGE_CADADTRO_SOLICITACAOSERVICO(42, "parametro.42", TEXTO),
        LDAD_SUFIXO_DOMINIO(43, "parametro.43", TEXTO),
        DISKFILEUPLOAD_REPOSITORYPATH(44, "parametro.44", TEXTO),
        ID_GRUPO_PADRAO_LDAP(45, "parametro.45", TEXTO),
        LOGIN_PORTAL(46, "parametro.46", BOOLEAN), // valores S ou N
        FLUXO_PADRAO_MUDANCAS(47, "parametro.47", TEXTO),
        VALIDAR_BOTOES(48, "parametro.48", BOOLEAN), // valores S ou N
        LDAP_SN_LAST_NAME(49, "parametro.49", BOOLEAN),
        OS_VALOR_ZERO(50, "parametro.50", BOOLEAN),
        FORMULA_CALCULO_GLOSA_OS(51, "parametro.51", NUMERO),
        USE_LOG(52, "parametro.52", TEXTO),
        TIPO_LOG(53, "parametro.53", TEXTO),
        PATH_LOG(54, "parametro.54", TEXTO),
        FILE_LOG(55, "parametro.55", TEXTO),
        EXT_LOG(56, "parametro.56", TEXTO),
        ID_MODELO_EMAIL_GRUPO_DESTINO(57, "parametro.57", NUMERO),
        NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO(58, "parametro.58", BOOLEAN),
        PATRIMONIO_IDTIPOITEMCONFIGURACAO(59, "parametro.59", NUMERO),
        NOME_GRUPO_ITEM_CONFIG_NOVOS(60, "parametro.60", TEXTO),
        UNIDADE_VINC_CONTRATOS(61, "parametro.61", BOOLEAN),
        SERVICO_PADRAO_SOLICITACAO(62, "parametro.62", NUMERO),
        PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL(63, "parametro.63", TEXTO),
        LDAP_ATRIBUTO(64, "parametro.64", TEXTO),
        ORIGEM_PADRAO_SOLICITACAO(65, "parametro.65", NUMERO),
        IDIOMAPADRAO(66, "parametro.66", TEXTO),
        LDAP_FILTRO(67, "parametro.67", TEXTO),
        LDAP_MOSTRA_BOTAO(68, "parametro.68", BOOLEAN),
        CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO(69, "parametro.69", BOOLEAN),
        MOSTRAR_BOTOES_IMPORTACAO_XML_CADASTRO_MENU(70, "parametro.70", BOOLEAN),
        LER_ARQUIVO_PADRAO_XML_MENUS(71, "parametro.71", BOOLEAN),
        SMTP_LEITURA_LIMITE_(72, "parametro.72", TEXTO),
        AVISAR_DATAEXPIRACAO_LICENCA(73, "parametro.73", NUMERO),
        ENVIAR_EMAIL_DATAEXPIRACAO(74, "parametro.74", NUMERO),
        ID_MODELO_EMAIL_EXPIRACAO_LICENCA(75, "parametro.75", NUMERO),
        DOMINIO_REDE(76, "parametro.76", TEXTO),
        AVISAR_DATAEXPIRACAO_BASECONHECIMENTO(78, "parametro.78", NUMERO),
        ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA(79, "parametro.79", NUMERO),
        ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA(80, "parametro.80", NUMERO),
        ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA(81, "parametro.81", NUMERO),
        ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO(82, "parametro.82", NUMERO),
        ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO(83, "parametro.83", NUMERO),
        ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO(84, "parametro.84", NUMERO),
        ITEM_CONFIGURACAO_MUDANCA(85, "parametro.85", BOOLEAN),
        MOSTRAR_CATEGORIA_SERVICO_EM_INCIDENTE(86, "parametro.86", BOOLEAN),
        ID_MODELO_EMAIL_AVISAR_CRIACAO_IC(87, "parametro.87", NUMERO),
        ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC(88, "parametro.88", NUMERO),
        ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC_GRUPO(89, "parametro.89", NUMERO),
        ENVIO_PADRAO_EMAIL_IC(90, "parametro.90", NUMERO),
        SMTP_GMAIL(91, "parametro.91", BOOLEAN),
        CICLO_DE_VIDA_IC_DESENVOLVIMENTO(92, "parametro.92", TEXTO),
        CICLO_DE_VIDA_IC_PRODUCAO(93, "parametro.93", TEXTO),
        CICLO_DE_VIDA_IC_HOMOLOGACAO(94, "parametro.94", TEXTO),
        NOME_INVENTARIO(95, "parametro.95", TEXTO),
        ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO(96, "parametro.96", NUMERO),
        ID_GRUPO_PADRAO_IC_PRODUCAO(97, "parametro.97", NUMERO),
        ID_GRUPO_PADRAO_IC_HOMOLOGACA0(98, "parametro.98", NUMERO),
        ID_GRUPO_PADRAO_IC_INVENTARIO(99, "parametro.99", NUMERO),
        ID_GRUPO_PADRAO_REQ_PRODUTOS(100, "parametro.100", NUMERO),
        PERC_MAX_VAR_PRECO_COTACAO(101, "parametro.101", TEXTO),
        NUMERO_COLABORADORES_CONSULTA_AD(102, "parametro.102", NUMERO),
        ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO(103, "parametro.103", NUMERO),
        CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE(104, "parametro.104", BOOLEAN),
        ORIGEM_PADRAO(105, "parametro.105", NUMERO),
        DETERMINA_URGENCIA_IMPACTO_REQPROD(106, "parametro.106", BOOLEAN),
        COTACAO_PESO_PRECO(107, "parametro.107", NUMERO),
        COTACAO_PESO_PRAZO_ENTREGA(108, "parametro.108", NUMERO),
        COTACAO_PESO_PRAZO_PAGTO(109, "parametro.109", NUMERO),
        COTACAO_PESO_GARANTIA(110, "parametro.110", NUMERO),
        COTACAO_PESO_JUROS(111, "parametro.111", NUMERO),
        PATH_NAGIOS_STATUS(112, "parametro.112", TEXTO),
        ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA(113, "parametro.113", NUMERO),
        PEMITE_REQUISICAO_EMPREGADO_COMPRAS(114, "parametro.114", BOOLEAN),
        TIRAR_VINCULO_LOCALIDADE_UNIDADE(115, "parametro.115", BOOLEAN),
        ID_MODELO_EMAIL_ALTERACAO_SENHA(116, "parametro.116", SENHA),
        INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS(117, "parametro.117", TEXTO),
        CONTRATO_PADRAO(118, "parametro.118", NUMERO),
        TIPO_CAPTURA_SOLICITACOES(119, "parametro.119", NUMERO),
        ID_MODELO_EMAIL_GRUPO_COMITE_REQUISICAOMUDANCA(120, "parametro.120", NUMERO),
        LDAP_OPEN_LDAP(121, "parametro.121", BOOLEAN),
        ID_MODELO_EMAIL_GRUPO_DESTINO_REQUISICAOMUDANCA(122, "parametro.122", NUMERO),
        NomeFluxoPadraoProblema(123, "parametro.123", TEXTO),
        ID_MODELO_EMAIL_CRIACAO_PROBLEMA(124, "parametro.124", NUMERO),
        ID_MODELO_EMAIL_ANDAMENTO_PROBLEMA(125, "parametro.125", NUMERO),
        ID_MODELO_EMAIL_FINALIZADO_PROBLEMA(126, "parametro.126", NUMERO),
        ID_MODELO_EMAIL_GRUPO_DESTINO_PROBLEMA(127, "parametro.127", NUMERO),
        ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO(128, "parametro.128", NUMERO),
        NOTIFICAR_RESPONSAVEL_GRUPO_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO(129, "parametro.129", BOOLEAN),
        LIBERAR_ORDEM_SERVICO_DATA_ANTERIOR(130, "parametro.130", BOOLEAN),
        QUANT_RETORNO_PESQUISA(131, "parametro.131", NUMERO),
        QUANT_RETORNO_PESQUISA_ORDEM_SERVICO(132, "parametro.132", TEXTO), // Não esta sendo usado
        ID_MODELO_EMAIL_AVISAR_REUNIAO_MARCADA(133, "parametro.133", NUMERO),
        ID_GRUPO_PADRAO_REQ_RH(134, "parametro.134", NUMERO),
        ID_PERFIL_ACESSO_ADMINISTRADOR(135, "parametro.135", NUMERO),
        URL_LOGO_PADRAO_RELATORIO(136, "parametro.136", TEXTO),
        ID_MODELO_EMAIL_AVISAR_PESQUISA_SATISFACAO_RUIM_OU_REGULAR(137, "parametro.137", NUMERO),
        ID_GRUPO_PADRAO_AVISAR_PESQUISA_SATISFACAO_RUIM_OU_REGULAR(138, "parametro.138", NUMERO),
        QTDE_DIAS_RESP_PESQ_SASTISFACAO(139, "parametro.139", NUMERO),
        ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO_PADRAO(140, "parametro.140", NUMERO),
        ID_GRUPO_PADRAO_IC_HOMOLOGACAO_PADRAO(141, "parametro.141", NUMERO),
        ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO(142, "parametro.142", NUMERO),
        NOME_GRUPO_PADRAO_DESENVOLVIMENTO(143, "parametro.143", TEXTO),
        NOME_GRUPO_PADRAO_HOMOLOGACAO(144, "parametro.144", TEXTO),
        NOME_GRUPO_PADRAO_PRODUCAO(145, "parametro.145", TEXTO),
        TEMPLATE_QUESTIONARIO(146, "parametro.146", NUMERO),
        LUCENE_DIR_BASECONHECIMENTO(147, "parametro.147", TEXTO),
        LUCENE_DIR_PALAVRAGEMEA(148, "parametro.148", TEXTO),
        LUCENE_DIR_ANEXOBASECONHECIMENTO(149, "parametro.149", TEXTO),
        LUCENE_REFAZER_INDICES(150, "parametro.150", BOOLEAN),
        AVALIAÇÃO_AUTOMATICA(151, "parametro.151", BOOLEAN),
        NOTA_AVALIAÇÃO_AUTOMATICA(152, "parametro.152", TEXTO),
        PONTUACAO_PRODUTIVIDADE_BAIXA_DENTRO_DO_PRAZO(153, "parametro.153", NUMERO),
        PONTUACAO_PRODUTIVIDADE_BAIXA_RETRABALHO(154, "parametro.154", NUMERO),
        PONTUACAO_PRODUTIVIDADE_BAIXA_FORA_DO_PRAZO(155, "parametro.155", NUMERO),
        PONTUACAO_PRODUTIVIDADE_BAIXA_RETRABALHADO_PRAZO_ESTOURADO(156, "parametro.156", NUMERO),
        PONTUACAO_PRODUTIVIDADE_MEDIA_DENTRO_DO_PRAZO(157, "parametro.157", NUMERO),
        PONTUACAO_PRODUTIVIDADE_MEDIA_RETRABALHO(158, "parametro.158", NUMERO),
        PONTUACAO_PRODUTIVIDADE_MEDIA_FORA_DO_PRAZO(159, "parametro.159", NUMERO),
        PONTUACAO_PRODUTIVIDADE_MEDIA_RETRABALHADO_PRAZO_ESTOURADO(160, "parametro.160", NUMERO),
        PONTUACAO_PRODUTIVIDADE_ALTA_DENTRO_DO_PRAZO(161, "parametro.161", NUMERO),
        PONTUACAO_PRODUTIVIDADE_ALTA_RETRABALHO(162, "parametro.162", NUMERO),
        PONTUACAO_PRODUTIVIDADE_ALTA_FORA_DO_PRAZO(163, "parametro.163", NUMERO),
        PONTUACAO_PRODUTIVIDADE_ALTA_RETRABALHADO_PRAZO_ESTOURADO(164, "parametro.164", NUMERO),
        NIVEL_EXCELENCIA_EXIGIDO(165, "parametro.165", NUMERO),
        ID_GRUPO_PADRAO_TESTE(166, "parametro.166", NUMERO),
        ID_GRUPO_PADRAO_EXECUTOR(167, "parametro.167", NUMERO),
        FILTRO_FLUXO_NOME(168, "parametro.168", TEXTO),
        FILTRO_FLUXO_ENCERRAMENTO(169, "parametro.169", TEXTO),
        ID_GRUPO_PADRAO_REQ_VIAGEM_EXECUCAO(170, "parametro.170", NUMERO),
        DIAS_LIMITE_REABERTURA_INCIDENTE_REQUISICAO(171, "parametro.171", NUMERO),
        MOSTRAR_GERENCIA_RECURSOS_HUMANOS(172, "parametro.172", BOOLEAN),
        MOSTRAR_COMPRAS(173, "parametro.173", BOOLEAN),
        ID_GRUPO_PADRAO_RESPONSAVEL_COTACAO_VIAGEM(174, "parametro.174", NUMERO),
        ID_GRUPO_PADRAO_RESPONSAVEL_ADIANTAMENTO_VIAGEM(175, "parametro.175", NUMERO),
        ID_GRUPO_PADRAO_RESPONSAVEL_CONFERENCIA_VIAGEM(176, "parametro.176", NUMERO),
        SERVASTERISKATIVAR(177, "parametro.177", BOOLEAN),
        SERVASTERISKIP(178, "parametro.178", TEXTO),
        SERVASTERISKLOGIN(179, "parametro.179", TEXTO),
        SERVASTERISKSENHA(180, "parametro.180", SENHA),
        SERVASTERISKINTERVALO(181, "parametro.181", NUMERO),
        PASTA_SALVA_DESCRICAO_RESPOSTA_DE_SOLICITACAOSERVICO_EM_BASECONHECIMENTO(182, "parametro.182", TEXTO),
        HABILITA_MONITORAMENTO_NAGIOS(183, "parametro.183", BOOLEAN),
        IP_SERVIDOR_INVENTARIO(184, "parametro.184", TEXTO),
        INVENTARIO_PROCESSAMENTO_ATIVO(185, "parametro.185", BOOLEAN),
        INVENTARIO_SNMP_COMMUNITY(186, "parametro.186", TEXTO),
        FAIXA_DISCOVERY_IP(187, "parametro.187", TEXTO),
        ID_MODELO_EMAIL_SOFTWARE_LISTA_NEGRA(188, "parametro.188", NUMERO),
        ID_GRUPO_PADRAO_RESPONSAVEL_SOFTWARE_LISTA_NEGRA(189, "parametro.189", NUMERO),
        HABILITA_REGRA_ESCALONAMENTO(190, "parametro.190", BOOLEAN),
        ATIVA_NOVO_LAYOUT(191, "parametro.191", BOOLEAN),
        MOSTRAR_GRAVAR_BASE_CONHECIMENTO(192, "parametro.192", BOOLEAN),
        HABILITA_ESCALONAMENTO_MUDANÇA(193, "parametro.193", BOOLEAN),
        HABILITA_ESCALONAMENTO_PROBLEMA(194, "parametro.194", BOOLEAN),
        ID_MODELO_EMAIL_PRAZO_VENCENDO(195, "parametro.195", NUMERO),
        ID_MODELO_EMAIL_PRESTACAO_CONTAS_NAO_APROVADA(196, "parametro.196", NUMERO),
        LOGIN_USUARIO_ENVIO_EMAIL_AUTOMATICO(197, "parametro.197", TEXTO),
        PATH_ARQ_BANCO_LOG(198, "parametro.198", TEXTO),
        EmailStartTLS(199, "parametro.199", BOOLEAN),
        HABILITA_ROTINA_DE_LEITURA_EMAIL(200, "parametro.200", BOOLEAN),
        HABILITA_BOTAO_ORDEMSERVICO(201, "parametro.201", BOOLEAN),
        DISCOVERY_QTDE_THREADS(202, "parametro.202", NUMERO),
        CAMINHOEXPORTACAOMANUALBICITSMART(203, "parametro.203", TEXTO),
        BICITSMART_EXECUTAR_ROTINA_AUTOMATICA(204, "parametro.204", BOOLEAN),
        BICITSMART_NOTIFICAR_ERRO_IMPORTACAO_POR_EMAIL(205, "parametro.205", BOOLEAN),
        BICITSMART_EMAIL_NOTIFICACAO_GERAL(206, "parametro.206", TEXTO),
        BICITSMART_ID_MODELO_EMAIL_ERRO_AGEND_EXCECAO(207, "parametro.207", NUMERO),
        BICITSMART_ID_MODELO_EMAIL_ERRO_AGEND_ESPECIFICO(208, "parametro.208", NUMERO),
        BICITSMART_ID_MODELO_EMAIL_ERRO_AGEND_PADRAO(209, "parametro.209", NUMERO),
        BICITSMART_ID_MODELO_EMAIL_ERRO_PARAMETRO(210, "parametro.210", NUMERO),
        BICITSMART_ID_MODELO_EMAIL_ERRO_EXCECUCAO(211, "parametro.211", NUMERO),
        BICITSMART_ID_CONEXAO(212, "parametro.212", NUMERO),
        GRUPO_PERMISSAO_DELEGAR_PRESTACAO_VIAGEM(213, "parametro.213", NUMERO),
        LDAP_SUBDOMINIO(214, "parametro.214", TEXTO),
        RECEBER_NOTIFICACAO_ENCERRAR_ESCALONAR_SOLICITACOES_VINCULADAS(215, "parametro.215", BOOLEAN),
        ID_MODELO_EMAIL_CRIACAO_GRUPO_EXECUTOR_SOLICITACAO_RELACIONADA(216, "parametro.216", NUMERO),
        ID_MODELO_EMAIL_ACOES_GRUPO_EXECUTOR_SOLICITACAO_RELACIONADA(217, "parametro.217", NUMERO),
        ID_MODELO_EMAIL_ENCERRAMENTO_GRUPO_EXECUTOR_SOLICITACAO_RELACIONADA(218, "parametro.218", NUMERO),
        HABILITAR_MIGRACAO_DE_DADOS_AUTOMATICA(219, "parametro.219", TEXTO),
        ID_USUARIO_CANDIDATO_EXTERNO(220, "parametro.220", NUMERO),
        SERVICO_TIPO_REQUISICAO_TESTE(221, "parametro.221", TEXTO),
        SERVICO_VALIDACAO_DOCUMENTACAO(222, "parametro.222", TEXTO),
        CONFIGURACAO_EMAIL_SUPORTE(223, "parametro.223", TEXTO),
        CONFIGURACAO_TELEFONE_SUPORTE(224, "parametro.224", TEXTO),
        CONFIGURACAO_EMAIL_SUPORTE_TELA_LOGIN(225, "parametro.225", TEXTO),
        CONFIGURACAO_TELEFONE_SUPORTE_TELA_LOGIN(226, "parametro.226", TEXTO),
        BPM_ELEMENTO_EXECUCAO(227, "parametro.227", TEXTO),
        VALOR_ALCADA_SEM_NESSIDADE_AUTORIZACAO(228, "parametro.228", NUMERO),
        RECONFIGURAR_FORMULASOS_DASATIVIDADES(229, "parametro.229", TEXTO),
        PermiteDataInferiorHoje(230, "parametro.230", TEXTO),
        ATIVAR_ENVIO_EMAIL_UPDATE_INCIDENTE(231, "parametro.231", TEXTO),
        ERROCONHECIDO_ARQUIVAR_AO_CONCLUIR_MUDANCA(232, "parametro.232", BOOLEAN),
        NAGIOS_CONEXOES_LIVESTATUS(233, "parametro.233", TEXTO),
        NAGIOS_TIPO_ACESSO(234, "parametro.234", NUMERO),
        UNIDADE_AUTOCOMPLETE(235, "parametro.235", BOOLEAN),
        SELENIUM_USUARIO_TESTE(236, "parametro.236", TEXTO),
        SELENIUM_SENHA_TEST(237, "parametro.237", SENHA),
        SELENIUM_NAVEGADOR_TESTE(238, "parametro.238", TEXTO),
        SELENIUM_URL_TESTE(239, "parametro.239", TEXTO),
        SELENIUM_URL_CLIENTE_TESTE(240, "parametro.240", TEXTO),
        SELENIUM_CAMINHO_COMPLETO_DRIVER_IE(241, "parametro.241", TEXTO),
        SELENIUM_CAMINHO_COMPLETO_DRIVER_CHROME(242, "parametro.242", TEXTO),
        ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS(243, "parametro.243", BOOLEAN),
        MENSAGEM_TELA_LOGIN(244, "parametro.244", TEXTO),
        HABILITA_PRECO_CARRINHO_PORTAL(245, "parametro.245", BOOLEAN),
        OCULTAR_BTN_NOVA_SOLICITACAO_PORTAL(246, "parametro.246", BOOLEAN),
        DESMARCAR_SERVICOS_CARRINHO_PORTAL(247, "parametro.247", BOOLEAN),
        MONITORAMENTO_ATIVOS_ID_MODELO_EMAIL_NOTIFICACAO(248, "parametro.248", NUMERO),
        MONITORAMENTO_ATIVOS_NUMERO_THREADS(249, "parametro.249", NUMERO),
        EXIBIR_NOME_ITEM_CONFIGURACAO_TREE(250, "parametro.250", BOOLEAN),
        ID_MODELO_EMAIL_NOTIFICAR_SOLICITANTE(251, "parametro.251", NUMERO),
        REGISTRAR_OCORRENCIA_PELO_PORTAL(252, "parametro.252", TEXTO),
        ID_EMAIL_REGISTRO_OCORRENCIA_PELO_PORTAL(253, "parametro.253", NUMERO),
        GOOGLE_API_KEY_WEB(254, "parametro.254", TEXTO),
        MOBILE_RANGE_ACTION(255, "parametro.255", NUMERO),
        MOBILE_LOCATION_INTERVAL(256, "parametro.256", NUMERO),
        REST_SERVICES_DEFAULT_PAGE_SIZE(257, "parametro.257", NUMERO),
        PERIODO_MAXIMO_DIAS_LISTAGEM(258, "parametro.258", NUMERO),
        GOOGLE_API_KEY_WEB_FOR_WORK(259, "parametro.259", TEXTO),
        TIPO_HIERARQUIA_UNIDADE(260, "parametro.260", NUMERO),
        QUANTIDADE_REGISTROS_PESQUISA_AVANCADA(261, "parametro.261", NUMERO),
        ID_CATEGORIA_REGISTRA_OCORRENCIA_PORTAL(262, "parametro.262", NUMERO),
        ID_ORIGEM_REGISTRA_OCORRENCIA_PORTAL(263, "parametro.263", NUMERO),
        FILTRAR_SOLICITACAO_ANDAMENTO(264, "parametro.264", BOOLEAN),
		SERVASTERISKALGORITMOCAPTURA(265,"parametro.265", NUMERO),
        ID_CALENDARIO_PADRAO(266, "parametro.266", NUMERO);

        private final int id;

        private final String campo;

        private final String tipoCampo;

        public int id() {
            return id;
        }

        public String campo() {
            return campo;
        }

        public String getCampoParametroInternacionalizado(final HttpServletRequest request) {
            return UtilI18N.internacionaliza(request, campo);
        }

        public String tipoCampo() {
            return tipoCampo;
        }

        private ParametroSistema(final int id, final String campo, final String tipoCampo) {
            this.id = id;
            this.campo = campo;
            this.tipoCampo = tipoCampo;
        }

    }

    /**
     * ENUM Tipo Demanda Serviço.
     *
     * @author valdoilo.damasceno
     *
     */
    public enum TipoDemandaServico {

        REQUISICAO("R", "Requisição"),
        INCIDENTE("I", "Incidente"),
        OS("O", "Ordem de Serviço");

        private final String classificacao;

        private final String campo;

        private TipoDemandaServico(final String classificacao, final String campo) {
            this.classificacao = classificacao;
            this.campo = campo;
        }

        public String getClassificacao() {
            return classificacao;
        }

        public String getCampo() {
            return campo;
        }

    }

    /**
     *
     * @author breno.guimaraes
     */
    public enum OrigemOcorrencia {

        EMAIL("Email", 'E'),
        FONE_FAX("Fone/Fax", 'F'),
        VOICE_MAIL("Voice Mail", 'V'),
        PESSOALMENTE("Pessoalmente", 'P'),
        OUTROS("Outros", 'O');

        private final String descricao;
        private final Character sigla;

        private OrigemOcorrencia(final String descricao, final Character sigla) {
            this.descricao = descricao;
            this.sigla = sigla;
        }

        public String getDescricao() {
            return descricao;
        }

        public Character getSigla() {
            return sigla;
        }

    }

    /**
     * @author breno.guimaraes
     *
     */
    public enum CategoriaOcorrencia {

        Criacao("Registro da Solicitação"),
        Acompanhamento("Acompanhamento com o Cliente"),
        Atualizacao("Atualização de Status"),
        Diagnostico("Diagnóstico"),
        Investigacao("Investigação"),
        Memorando("Memorando"),
        Informacao("Pedido de Informação"),
        Retorno("Retorno do Cliente"),
        Sintoma("Sintoma do Problema"),
        Contorno("Solução de Contorno"),
        Execucao("Registro de Execução"),
        MudancaSLA("Mudança de SLA"),
        Reclassificacao("Reclassificação"),
        Agendamento("Agendamento de Atividade"),
        Suspensao("Suspensão da Solicitação"),
        Reativacao("Reativação da Solicitação"),
        Encerramento("Encerramento da Solicitação"),
        Reabertura("Reabertura da Solicitação"),
        Direcionamento("Direcionamento da Solicitação"),
        Compartilhamento("Compartilhamento de Tarefa"),
        CancelamentoTarefa("Cancelamento de Tarefa"),
        InicioSLA("Inicio do SLA"),
        SuspensaoSLA("Suspensão do SLA"),
        Aprovar("Liberação de Requisição Liberação"),
        ReativacaoSLA("Reativação do SLA");

        private final String descricao;

        private CategoriaOcorrencia(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getSigla() {
            return this.name();
        }

    }

    /**
     * ENUM para listar as Notas da Pesquisa de Satisfação.
     *
     * @author valdoilo
     */
    public enum Nota {

        OTIMO(4, "Ótimo", "citcorpore.comum.otimo"),
        BOM(3, "Bom", "citcorpore.comum.bom"),
        REGULAR(2, "Regular", "citcorpore.comum.regular"),
        RUIM(1, "Ruim", "citcorpore.comum.ruim");

        private final Integer nota;

        private final String descricao;

        private final String chaveInternacionalizacao;

        private Nota(final Integer nota, final String descricao, final String chaveInternacionalizacao) {
            this.nota = nota;
            this.descricao = descricao;
            this.chaveInternacionalizacao = chaveInternacionalizacao;
        }

        public Integer getNota() {
            return nota;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getChaveInternacionalizacao() {
            return chaveInternacionalizacao;
        }

    }

    public enum FaseRequisicaoMudanca {

        Proposta("Proposta", SituacaoRequisicaoMudanca.Proposta),
        Registrada("Registrada", SituacaoRequisicaoMudanca.Registrada),
        Aprovacao("Aprovação", SituacaoRequisicaoMudanca.Aprovada),
        Planejamento("Planejamento", SituacaoRequisicaoMudanca.Planejada),
        Execucao("Execução", SituacaoRequisicaoMudanca.Executada),
        Avaliacao("Avaliação", SituacaoRequisicaoMudanca.Concluida);

        private final String descricao;
        private final SituacaoRequisicaoMudanca situacao;

        private FaseRequisicaoMudanca(final String descricao, final SituacaoRequisicaoMudanca situacao) {
            this.descricao = descricao;
            this.situacao = situacao;
        }

        public SituacaoRequisicaoMudanca getSituacao() {
            return situacao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoRequisicaoMudanca {

        Registrada("Registrada"),
        Proposta("Aguardando Aprovação"),
        Aprovada("Aprovada"),
        Planejada("Planejada"),
        EmExecucao("Em execução"),
        Executada("Executada"),
        Suspensa("Suspensa"),
        Cancelada("Cancelada"),
        Rejeitada("Rejeitada"),
        Resolvida("Resolvida"),
        Reaberta("Reaberta"),
        Concluida("Concluída");

        private final String descricao;

        private SituacaoRequisicaoMudanca(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum FaseRequisicaoLiberacao {

        Liberada("Liberada", SituacaoRequisicaoLiberacao.Aprovada),
        NaoResolvida("Não Resolvida", SituacaoRequisicaoLiberacao.NaoResolvida),
        Execucao("Execução", SituacaoRequisicaoLiberacao.Executada),
        Resolvida("Resolvida", SituacaoRequisicaoLiberacao.Resolvida),
        Finalizada("Fechada", SituacaoRequisicaoLiberacao.Fechada);

        private final String descricao;
        private final SituacaoRequisicaoLiberacao situacao;

        private FaseRequisicaoLiberacao(final String descricao, final SituacaoRequisicaoLiberacao situacao) {
            this.descricao = descricao;
            this.situacao = situacao;
        }

        public SituacaoRequisicaoLiberacao getSituacao() {
            return situacao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoRequisicaoLiberacao {

        Registrada("Registrada"),
        NaoResolvida("Não Resolvida"),
        Aprovada("Aprovada"),
        Planejada("Planejada"),
        EmExecucao("Execução"),
        Executada("Executada"),
        Suspensa("Suspensa"),
        Cancelada("Cancelada"),
        Rejeitada("Rejeitada"),
        Resolvida("Resolvida"),
        Reaberta("Reaberta"),
        Fechada("Fechada"),
        Concluida("Concluída");

        private final String descricao;

        private SituacaoRequisicaoLiberacao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum FaseRequisicaoProblema {

        Registrada("Registrada", SituacaoRequisicaoProblema.Registrada),
        Aprovacao("Aprovação", SituacaoRequisicaoProblema.Aprovada),
        Planejamento("Planejamento", SituacaoRequisicaoProblema.Planejada),
        Execucao("Execução", SituacaoRequisicaoProblema.Executada),
        Avaliacao("Avaliação", SituacaoRequisicaoProblema.Concluida),
        EmInvestigacao("Em Investigação", SituacaoRequisicaoProblema.EmInvestigacao),
        SolucaoContorno("Solução de Contorno", SituacaoRequisicaoProblema.SolucaoContorno),
        Revisado("Revisado", SituacaoRequisicaoProblema.Revisado),
        Resolucao("Resolução", SituacaoRequisicaoProblema.Resolucao),
        Encerramento("Encerramento", SituacaoRequisicaoProblema.Encerramento),
        Revisar("Revisar", SituacaoRequisicaoProblema.Revisar),
        RegistroErroConhecido("Registro de Erro Conhecido", SituacaoRequisicaoProblema.RegistroErroConhecido);

        private final String descricao;
        private final SituacaoRequisicaoProblema situacao;

        private FaseRequisicaoProblema(final String descricao, final SituacaoRequisicaoProblema situacao) {
            this.descricao = descricao;
            this.situacao = situacao;
        }

        public SituacaoRequisicaoProblema getSituacao() {
            return situacao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoRequisicaoProblema {

        Registrada("Registrada"),
        Aprovada("Aprovada"),
        Planejada("Planejada"),
        EmExecucao("Em execução"),
        Executada("Executada"),
        Suspensa("Suspensa"),
        Cancelada("Cancelada"),
        Rejeitada("Rejeitada"),
        Resolvida("Resolvida"),
        Reaberta("Reaberta"),
        Concluida("Concluída"),
        EmInvestigacao("Em Investigação"),
        SolucaoContorno("Solução de Contorno"),
        Revisado("Revisado"),
        Resolucao("Resolução"),
        Encerramento("Encerramento"),
        Revisar("Revisar"),
        RegistroErroConhecido("Registro de Erro Conhecido");

        private final String descricao;

        private SituacaoRequisicaoProblema(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /**
     * Enum armazena os Tipos de Carga do Sistema.
     *
     * @author Vadoilo Damasceno
     *
     */
    public enum TipoCargaSmart {

        Empregado("Colaboradores");

        private final String descricao;

        private TipoCargaSmart(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum Complexidade {

        BAIXA(1, "Baixa"),
        INTERMEDIARIA(2, "Intermediária"),
        MEDIANA(3, "Mediana"),
        ALTA(4, "Alta"),
        ESPECIALISTA(5, "Especialista");

        private final int index;
        private final String descricao;

        private Complexidade(final int index, final String descricao) {
            this.index = index;
            this.descricao = descricao;
        }

        public int getIndex() {
            return index;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum PeriodoFormula {

        MENSAL(1, "Mensal"),
        SEMANAL(2, "Semanal"),
        DIARIO(3, "Diário"),
        DIAS_UTEIS(4, "Dias úteis");

        private final int index;
        private final String descricao;

        private PeriodoFormula(final int index, final String descricao) {
            this.index = index;
            this.descricao = descricao;
        }

        public int getIndex() {
            return index;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum StatusIC {

        ATIVADO(1, "Ativado", "baseItemConfiguracao.Ativado"),
        DESATIVADO(2, "Desativado", "baseItemConfiguracao.Desativado"),
        EM_MANUTENCAO(3, "Em Manutenção", "baseItemConfiguracao.Em_Manutenção"),
        IMPLANTACAO(4, "Implantação", "baseItemConfiguracao.Implantação"),
        HOMOLOGACAO(5, "Homologação", "baseItemConfiguracao.Homologação"),
        EM_DESENVOLVIMENTO(6, "Em Desenvolvimento", "baseItemConfiguracao.Em_Desenvolvimento"),
        ARQUIVADO(7, "Arquivado", "baseItemConfiguracao.Arquivado"),
        VALIDAR(8, "Validar Item", "baseItemConfiguracao.Validar_Item");

        private final Integer item;
        private final String descricao;
        private final String chaveInternacionalizacao;

        private StatusIC(final Integer item, final String descricao, final String chaveInternacionalizacao) {
            this.item = item;
            this.descricao = descricao;
            this.chaveInternacionalizacao = chaveInternacionalizacao;
        }

        public Integer getItem() {
            return item;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getChaveInternacionalizacao() {
            return chaveInternacionalizacao;
        }

        public static StatusIC getStatus(final Integer id) {
            if (id == null) {
                return null;
            }

            StatusIC result = null;
            for (final StatusIC status : StatusIC.values()) {
                if (status.getItem().intValue() == id.intValue()) {
                    result = status;
                    break;
                }
            }
            return result;
        }

    }

    public enum CriticidadeIC {

        CRITICA(1, "citcorpore.comum.critica"),
        ALTA(2, "citcorpore.comum.alta"),
        MEDIA(3, "citcorpore.comum.media"),
        BAIXA(4, "citcorpore.comum.baixa");

        private final Integer item;
        private final String descricao;

        private CriticidadeIC(final Integer item, final String descricao) {
            this.item = item;
            this.descricao = descricao;

        }

        public Integer getItem() {
            return item;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /**
     * Enum utilizado para listar as Permissões de Acesso ás Pasta de Base de Conhecimento.
     *
     */
    public enum PermissaoAcessoPasta {

        SEMPERMISSAO("SEMPERMISSAO"),
        LEITURA("LEITURA"),
        LEITURAGRAVACAO("LEITURAGRAVACAO");

        private final String permissao;

        private PermissaoAcessoPasta(final String permissao) {
            this.permissao = permissao;
        }

        public String getPermissao() {
            return permissao;
        }

    }

    /**
     * Enum utilizado para listar as Permissões de Acesso ás Pasta de Base de Conhecimento.
     *
     */
    public enum TipoNotificacao {

        ServTodasAlt("tipoNotificacao.ocorrerQualquerAlteracaoServico", "T"),
        ServADICIONADOS("tipoNotificacao.novosServicosForemAdicionados", "C"),
        ServALTERADOS("tipoNotificacao.servicosForemAlterados", "A"),
        ServEXCLUIDOS("tipoNotificacao.servicosForemExcluidos", "E");

        private final String descricao;
        private final String tipoNotificacao;

        private TipoNotificacao(final String descricao, final String tipoNotificacao) {
            this.descricao = descricao;
            this.tipoNotificacao = tipoNotificacao;
        }

        public String getTipoNotificacao() {
            return tipoNotificacao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /**
     * Enum com os Graus de Importância do Conhecimento.
     *
     * @author Vadoilo Damasceno
     *
     */
    public enum EnumGrauImportanciaConhecimento {

        BAIXO("Baixo"),
        MEDIO("Médio"),
        ALTO("Alto");

        private final String grauImportancia;

        private EnumGrauImportanciaConhecimento(final String grauImportancia) {
            this.grauImportancia = grauImportancia;
        }

        public String getGrauImportancia() {
            return grauImportancia;
        }

    }

    /*
     * Enumerador para situação de FAQ - Frequently Asked Questions
     */
    public enum SituacaoFAQ {

        PUBLICADO("Publicado"),
        NAO_PUBLICADO("Não Publicado");

        private final String descricao;

        private SituacaoFAQ(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /*
     * Enumerador para situação de SIM e NAO
     */
    public enum Situacao {

        SIM("Sim"),
        NAO("Não");

        private final String descricao;

        private Situacao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum CategoriaTipoItemConfiguracao {

        HARDWARE(1, "Hardware"),
        SOFTWARE(2, "Software"),
        BIOS(3, "Bios");

        private final Integer item;
        private final String descricao;

        private CategoriaTipoItemConfiguracao(final Integer item, final String descricao) {
            this.item = item;
            this.descricao = descricao;

        }

        public Integer getItem() {
            return item;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoItemRequisicaoProduto {

        AguardandoValidacao("Aguardando validação", AcaoItemRequisicaoProduto.Criacao),
        RejeitadoCompras("Requisição rejeitada", AcaoItemRequisicaoProduto.Validacao),
        AlteradoCompras("Alterado pela área de compras",AcaoItemRequisicaoProduto.Validacao),
        Inviabilizado("Inviabilizado", AcaoItemRequisicaoProduto.Validacao),
        AguardandoAutorizacaoCompra("Aguardando autorização",AcaoItemRequisicaoProduto.Validacao),
        AlteradoAutorizador("Alterado pelo autorizador",AcaoItemRequisicaoProduto.Autorizacao),
        RejeitadoAutorizador("Requisição rejeitada", AcaoItemRequisicaoProduto.Autorizacao),
        CompraNaoAutorizada("Compra não autorizada", AcaoItemRequisicaoProduto.Autorizacao),
        AguardandoCotacao("Aguardando cotação", AcaoItemRequisicaoProduto.Autorizacao),
        AguardandoAprovacaoCotacao("Aguardando aprovação da cotação", AcaoItemRequisicaoProduto.Publicacao),
        CotacaoNaoAprovada("Cotação não aprovada", AcaoItemRequisicaoProduto.Aprovacao),
        AguardandoPedido("Aguardando pedido de compra", AcaoItemRequisicaoProduto.Aprovacao),
        AguardandoEntrega("Aguardando entrega", AcaoItemRequisicaoProduto.Pedido),
        AguardandoInspecao("Aguardando inspeção", AcaoItemRequisicaoProduto.Entrega),
        AguardandoInspecaoGarantia("Aguardando inspeção", AcaoItemRequisicaoProduto.Garantia),
        InspecaoRejeitada("Inspeção rejeitada", AcaoItemRequisicaoProduto.Inspecao),
        Cancelado("Cancelado", AcaoItemRequisicaoProduto.Cancelamento),
        Finalizado("Finalizado", AcaoItemRequisicaoProduto.Inspecao);

        private final String descricao;
        private final AcaoItemRequisicaoProduto acaoPadrao;

        private SituacaoItemRequisicaoProduto(final String descricao, final AcaoItemRequisicaoProduto acaoPadrao) {
            this.descricao = descricao;
            this.acaoPadrao = acaoPadrao;
        }

        public String getDescricao() {
            return descricao;
        }

        public AcaoItemRequisicaoProduto getAcaoPadrao() {
            return acaoPadrao;
        }

    }

    public enum AcaoItemRequisicaoProduto {

        Criacao("Criação da requisição"),
        Alteracao("Alteração da requisição"),
        Validacao("Validação pela área de compras"),
        Autorizacao("Autorização da compra"),
        Publicacao("Publicação dos resultados"),
        Aprovacao("Aprovação da cotação"),
        Reabertura("Reabertura das coletas de preço"),
        Pedido("Geração do pedido"),
        Entrega("Entrega do pedido"),
        Garantia("Retorno de garantia"),
        Inspecao("Inspeção do solicitante"),
        Cancelamento("Exclusão/Cancelamento do item"),
        ExclusaoPedido("Exclusão do pedido"),
        ExclusaoItemCotacao("Exclusão do item de cotação"),
        EncerramentoCotacao("Encerramento da cotação");

        private final String descricao;

        private AcaoItemRequisicaoProduto(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum TipoAlcada {

        Compras("Autorização de compras"),
        Pessoal("Requisição de pessoal"),
        Viagem("Requisicao de Viagem");

        private final String descricao;

        private TipoAlcada(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoCotacao {

        EmAndamento("Em andamento"),
        Calculada("Resultado calculado"),
        Publicada("Resultado publicado"),
        Pedido("Pedido(s) gerado(s)"),
        Entrega("Pedido(s) entregue(s)"),
        Finalizada("Finalizada"),
        Cancelada("Cancelada");

        private final String descricao;

        private SituacaoCotacao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoCotacaoItemRequisicao {

        AguardaAprovacao("Aguardando aprovação"),
        PreAprovado("Pré aprovado"),
        Aprovado("Aprovado"),
        NaoAprovado("Não aprovado");

        private final String descricao;

        private SituacaoCotacaoItemRequisicao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum OrigemNotificacao {

        B("Notificação de base de conhecimento"),
        P("Notificação de Pasta"),
        S("Notificação de serviço de contrato");

        private final String descricao;

        private OrigemNotificacao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum ResultadoValidacao {

        V("Validado"),
        A("Aviso"),
        E("Erro não impeditivo"),
        I("Erro impeditivo");

        private final String descricao;

        private ResultadoValidacao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoPedidoCompra {

        Efetivado("Efetivado"),
        Analise("Aguardando análise de crédito"),
        Aprovacao("Aguardando aprovação do pagamento"),
        Aprovado("Pagamento aprovado"),
        Transportadora("Entregue à transportadora"),
        Transporte("Em rota de entrega"),
        Entregue("Entregue");

        private final String descricao;

        private SituacaoPedidoCompra(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoEntregaItemRequisicao {

        Aguarda("Aguardando inspeção"),
        Aprovada("Entrega aprovada"),
        AprovadaPrazo("Aprovada por decurso de prazo"),
        NaoAprovada("Entrega nao aprovada");

        private final String descricao;

        private SituacaoEntregaItemRequisicao(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoSLA {

        N("Não iniciado"),
        S("Suspenso"),
        M("Multiplo"),
        A("Em andamento");

        private final String descricao;

        private SituacaoSLA(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public static SituacaoSLA fromNameIgnoreCase(final String name) {
            final String nameUpper = name.toUpperCase();
            return SituacaoSLA.valueOf(nameUpper);
        }

    }

    public enum Moeda {

        UST("UST"),
        REAL("Real");

        private final String descricao;

        private Moeda(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum SituacaoProblema {

        EmAndamento("Em andamento"),
        Suspensa("Suspensa"),
        Cancelada("Cancelada"),
        Resolvida("Resolvida"),
        Reaberta("Reaberta"),
        Fechada("Fechada"),
        ReClassificada("Reclassificada");

        private final String descricao;

        SituacaoProblema(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /**
     * Identifica qual modulo que faz referencia.
     *
     * @author maycnn.fernandes
     */
    public enum TipoRequisicao {

        LIBERCAO(1, "Liberação"),
        MUDANCA(2, "Mudança"),
        SOLICITACAOSERVICO(3, "Solicitação Serviço"),
        PROBLEMA(4, "Problema");

        private final Integer id;
        private final String descricao;

        private TipoRequisicao(final Integer id, final String descricao) {
            this.id = id;
            this.descricao = descricao;
        }

        public Integer getId() {
            return id;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /**
     * Enumerado para definir qual aba será vinculano na requisição.<br>
     *
     * Ex: Módulo Liberação aba TesteLiberação. onde o iframe irá abri.<br>
     *
     * Não criar outro enumerado adicionar novas abas.
     *
     * @author maycon.fernandes
     *
     */
    public enum Aba {

        LIBERCAOTESTE(1, "Teste Liberação"),
        LIBERACAOETAPAS(2, "Liberação Etapas"),
        LIBERACAOITEMCONFIGUACAO(3, "Liberação Item Configuração"),
        LIBERACAOSERVICO(4, "Liberação Serviço"),
        MUDANCAGENERICO(5, "Questionário Mudança");

        private final Integer id;
        private final String aba;

        private Aba(final Integer id, final String aba) {
            this.id = id;
            this.aba = aba;
        }

        public Integer getId() {
            return id;
        }

        public String getAba() {
            return aba;
        }

    }

    public enum TipoEntrevista {

        RH("Entrevista com RH"),
        Gestor("Entrevista com Gestor");

        private final String descricao;

        private TipoEntrevista(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum ResultadoEntrevista {

        N("Não avaliada"),
        A("Entrevista com RH"),
        R("Reprovado"),
        S("2ª Oportunidade"),
        D("Descarte");

        private final String descricao;

        private ResultadoEntrevista(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    /**
     * Enumerados do tipo de movimentação financeira do módulo de viagens.
     *
     * @author ronnie.lopes
     */
    public enum TipoMovFinViagem {

        D("requisicaoViagem.itemReferente", 1),
        R("requisicaoViagem.ressarcimento", 2);

        private final String descricao;
        private final Integer id;

        private TipoMovFinViagem(final String descricao, final Integer id) {
            this.descricao = descricao;
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public Integer getId() {
            return id;
        }

    }

    /**
     * Enumerados da classificação de movimentação financeira do módulo de viagens.
     *
     * @author ronnie.lopes
     */
    public enum ClassificacaoMovFinViagem {

        Passagem("Passagem", 1),
        Hospedagem("Hospedagem", 2),
        LocacaoVeiculo("Locação Veículo", 3),
        Diaria("Diária", 4),
        DespesaExtra("Despesa Extra", 5);

        private final String descricao;
        private final Integer id;

        private ClassificacaoMovFinViagem(final String descricao, final Integer id) {
            this.descricao = descricao;
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public Integer getId() {
            return id;
        }

    }

    public enum TipoSolicitacaoServico {

        COMPRA(1, "Compra"),
        VIAGEM(2, "Viagem"),
        RH(3, "Requisição de pessoal"),
        INCIDENTE(4, "Incidente"),
        REQUISICAO(5, "Requisição");

        private final Integer identifier;
        private final String descricao;

        private TipoSolicitacaoServico(final Integer identifier, final String descricao) {
            this.identifier = identifier;
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public Integer getIdentifier() {
            return identifier;
        }

        /**
         * Recupera uma {@link TipoSolicitacaoServico} de acordo com seu identificador
         *
         * @param identifier
         *            id a ser verificado se há uma {@link TipoSolicitacaoServico}
         * @return {@link TipoSolicitacaoServico} caso encontre. {@link IllegalArgumentException}, caso contrário
         * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
         * @since 08/10/2014
         */
        public static TipoSolicitacaoServico fromIdentifier(final Integer identifier) {
            final TipoSolicitacaoServico[] tipos = TipoSolicitacaoServico.values();
            for (final TipoSolicitacaoServico tipo : tipos) {
                if (tipo.getIdentifier().equals(identifier)) {
                    return tipo;
                }
            }
            throw new IllegalArgumentException(String.format("TipoSolicitacaoServico not found for identifier '%s'", identifier));
        }

    }

    // Mário Júnior - 25/10/2013 19:40 Para Ordem em SolicitaçãoServiço
    public enum OrdemSolicitacaoServico {

        PRAZOLIMITE("dataHoraLimite", "solicitacaoServico.prazoLimite"),
        DATACRIADA("dataHoraInicio", "visao.dataCriacao"),
        SOLICITANTE("solicitanteUnidade", "solicitacaoServico.solicitante"),
        CRIADOPOR("responsavel", "requisitosla.criador"),
        ATRASO("atrasoSLAStr", "tarefa.atraso"),
        GRUPOEXECUTOR("grupoAtual", "tipoLiberacao.nomeGrupoExecutor"),
        PRIORIDADE("prioridade", "solicitacaoServico.prioridade"),
        SERVICO("nomeServico", "servico.servico"),
        NUMEROSOLICITACAO("idSolicitacaoServico", "solicitacaoServico.numerosolicitacao"),
        CONTRATO("idContrato", "requisitosla.contrato"),
        SITUACAO("situacao", "requisitosla.situacao");

        private final String ordem;
        private final String valor;

        private OrdemSolicitacaoServico(final String ordem, final String valor) {
            this.ordem = ordem;
            this.valor = valor;
        }

        public String getOrdem() {
            return ordem;
        }

        public String getValor() {
            return valor;
        }

    }

    public enum ItensPorPagina {

        CINCO(5),
        DEZ(10),
        QUIZE(15),
        VINTE(20),
        CINQUENTA(50),
        CEM(100),
        DUZENTOS(200),
        QUINHETOS(500);

        private final Integer valor;

        private ItensPorPagina(final Integer valor) {
            this.valor = valor;
        }

        public Integer getValor() {
            return valor;
        }

    }

    /**
     * Enumerado para armazenar os Tipos de Data.
     *
     * <pre>
     * DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy,
     * TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm,
     * TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou MM/dd/yyyy HH:mm:ss,
     * FORMAT_DATABASE: yyyy-MM-dd
     * </pre>
     *
     * @author valdoilo.damasceno
     * @since 04.02.2013
     */
    public enum TipoDate {

        DATE_DEFAULT("DATE_DEFAULT"),
        TIMESTAMP_DEFAULT("TIMESTAMP_DEFAULT"),
        TIMESTAMP_WITH_SECONDS("TIMESTAMP_WITH_SECONDS"),
        FORMAT_DATABASE("FORMAT_DATABASE"),
        FORMAT_DATABASE_WITH_HOUR_AND_SECOND("FORMAT_DATABASE_WITH_HOUR_AND_SECOND");

        private final String tipoData;

        private TipoDate(final String tipoDate) {
            tipoData = tipoDate;
        }

        public String getTipoDate() {
            return tipoData;
        }

    }

    /**
     * Enumerado para armazenar os Tipos de Origem na leitura de e-mail.
     *
     * @author rodrigo.acorse
     * @since 02.06.2014
     */
    public enum TipoOrigemLeituraEmail {

        SOLICITACAO_SERVICO("SOLICITACAO_SERVICO"),
        PROBLEMA("PROBLEMA");

        private final String tipoOrigemLeituraEmail;

        private TipoOrigemLeituraEmail(final String tipoOrigemLeituraEmail) {
            this.tipoOrigemLeituraEmail = tipoOrigemLeituraEmail;
        }

        public String getTipoOrigemLeituraEmail() {
            return tipoOrigemLeituraEmail;
        }

    }

    public enum MotivoRejeicaoAlcada {

        LimiteValor("Fora dos limites de aprovação"),
        HierarquiaAutoridade("Nível de hierarquia abaixo da hierarquia do solicitante"),
        PermissaoAutoridade("Autoridade não tem permissão para aprovar as próprias solicitações"),
        RegrasProcesso("Regras do processo de negócio"),
        UsuarioNaoExiste("Não existe um usuário ativo associado ao empregado autorizador"),
        PertenceGrupoAdministrador("Solicitante pertence ao grupo administrador do processo de negócio");

        private final String descricao;

        private MotivoRejeicaoAlcada(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public enum TopListEnum {

        TOP10(10, "citcorpore.comum.topList10"),
        TOP20(20, "citcorpore.comum.topList20"),
        TOP50(50, "citcorpore.comum.topList50"),
        TOP100(100, "citcorpore.comum.topList100"),
        TOP200(200, "citcorpore.comum.topList200"),
        TOP500(500, "citcorpore.comum.topList500"),
        TOPTODOS(0, "citcorpore.comum.todos");

        private final Integer valorTopList;
        private final String nomeTopList;

        private TopListEnum(final Integer valorTopList, final String nomeTopList) {
            this.nomeTopList = nomeTopList;
            this.valorTopList = valorTopList;
        }

        public Integer getValorTopList() {
            return valorTopList;
        }

        public String getNomeTopList() {
            return nomeTopList;
        }

    }

	/**
	 * Enumerados com os Tipos de Item Configuração Default de um XML de Inventário.
	 * 
	 * @author valdoilo.damasceno
	 * @since 19.01.2014
	 */
	public enum TagTipoItemConfiguracaoDefault {

		ACCOUNTS("ACCOUNTS"),
		BIOS("BIOS"),
		CAPTION("CAPTION"), 
		DESCRIPTION("DESCRIPTION"),
		DISKSIZE("DISKSIZE"),
		ENVIRONMENTS("ENVIRONMENTS"),
		HARDWARE("HARDWARE"),
		MACADDR("MACADDR"),
		MEMORIES("MEMORIES"),
		NAME("NAME"), 
		NETWORKS("NETWORKS"),
		PRODUCT("PRODUCT"),
		PRODUCTID("PRODUCTID"),
		INPUTS("INPUTS"), 
		SERVICES("SERVICES"),
		STORAGES("STORAGES"),
		SOFTWARES("SOFTWARES"),
		OFFICEPACK("OFFICEPACK"),
		TYPE("TYPE"),
		VERSION("VERSION"),  ;

		private final String tagTipoItemConfiguracao;

		private TagTipoItemConfiguracaoDefault(final String tagTipoItemConfiguracao) {
			this.tagTipoItemConfiguracao = tagTipoItemConfiguracao;
}

		public String getTagTipoItemConfiguracao() {
			return tagTipoItemConfiguracao;
		}
	}
    
	/**
	 * Enumerado com os Nomes de Características de Identificação.
	 * 
	 * @author valdoilo.damasceno
	 * @since 19.01.2014
	 */
	public enum CaracteristicaIdentificacao {
		
		CAPTION("CAPTION"),
		CAPACITY("CAPACITY"),
		DESCRIPTION("DESCRIPTION"),
		DEVICEID("DEVICEID"),
		DISKSIZE("DISKSIZE"),
		DOMAIN("DOMAIN"),
		IPADDR("IPADDR"),
		MACADDR("MACADDR"),
		NAME("NAME"),
		PRODUCT("PRODUCT"),
		PRODUCTID("PRODUCTID"),
		SMANUFACTURER("SMANUFACTURER"),
		SSN("SSN"),
		SERIALNUMBER("SERIALNUMBER"),
		STARTMODE("STARTMODE"),
		TYPE("TYPE"),
		USERNAME("USERNAME"),
		VERSION("VERSION");
		
		private CaracteristicaIdentificacao(final String nomeCaracteristica) {
			this.nomeCaracteristica = nomeCaracteristica;
		}

		final String nomeCaracteristica;

		public String getNomeCaracteristica() {
			return nomeCaracteristica;
		}

	}

}
