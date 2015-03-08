/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoICDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoMudancaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.HistoricoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AnexoBaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.ComentariosDAO;
import br.com.centralit.citcorpore.integracao.ConhecimentoICDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoMudancaDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoProblemaDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.HistoricoBaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.NotificacaoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Arquivo;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

/**
 * ServiceEJB de BaseConhecimento.
 *
 * @author valdoilo.damasceno
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseConhecimentoServiceEjb extends CrudServiceImpl implements BaseConhecimentoService {

    private File pastaDaBaseConhecimento;

    private BaseConhecimentoDAO dao;
    private ComentariosDAO comentariosDAO;

    @Override
    protected BaseConhecimentoDAO getDao() {
        if (dao == null) {
            dao = new BaseConhecimentoDAO();
        }
        return dao;
    }

    private ComentariosDAO geComentariosDAO() {
        if (comentariosDAO == null) {
            comentariosDAO = new ComentariosDAO();
        }
        return comentariosDAO;
    }

    private Integer idBaseConhecimento;

    @Override
    public Integer getIdBaseConhecimento() {
        return idBaseConhecimento;
    }

    @Override
    public BaseConhecimentoDTO create(BaseConhecimentoDTO baseConhecimentoDto, final Collection<UploadDTO> arquivosUpados, final Integer idEmpresa, final UsuarioDTO usuarioDto)
            throws Exception {
        NotificacaoDTO notificacaoDto = new NotificacaoDTO();
        final ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();

        final ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");

        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }

        final String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");
        final String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");

        final TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());

        final AnexoBaseConhecimentoDAO anexoBaseConhecimentoDao = new AnexoBaseConhecimentoDAO();
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        final HistoricoBaseConhecimentoDAO historicoBaseConhecimentoDao = new HistoricoBaseConhecimentoDAO();
        final ConhecimentoProblemaDao conhecimentoProblemaDao = new ConhecimentoProblemaDao();

        try {

            this.getDao().setTransactionControler(transactionControler);
            anexoBaseConhecimentoDao.setTransactionControler(transactionControler);
            controleGEDDao.setTransactionControler(transactionControler);
            historicoBaseConhecimentoDao.setTransactionControler(transactionControler);
            conhecimentoProblemaDao.setTransactionControler(transactionControler);

            transactionControler.start();

            this.validaCreate(baseConhecimentoDto);

            baseConhecimentoDto.setDataInicio(UtilDatas.getDataAtual());
            baseConhecimentoDto.setIdUsuarioAutor(usuarioDto.getIdUsuario());
            baseConhecimentoDto.setArquivado("N");

            if (baseConhecimentoDto.getErroConhecido() == null || baseConhecimentoDto.getErroConhecido().equalsIgnoreCase("")) {
                baseConhecimentoDto.setErroConhecido("N");
            }

            final boolean isAprovaBaseConhecimento = this.usuarioAprovaBaseConhecimento(usuarioDto, baseConhecimentoDto.getIdPasta());

            if (!isAprovaBaseConhecimento) {
                baseConhecimentoDto.setStatus("N");
            } else {
                if (baseConhecimentoDto.getStatus().equalsIgnoreCase("S")) {
                    baseConhecimentoDto.setVersao("1.0");
                    baseConhecimentoDto.setIdUsuarioAprovador(usuarioDto.getIdUsuario());
                    baseConhecimentoDto.setDataPublicacao(UtilDatas.getDataAtual());
                }

            }

            notificacaoDto = this.criarNotificacao(baseConhecimentoDto, transactionControler);

            if (notificacaoDto.getIdNotificacao() != null) {
                baseConhecimentoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
            }

            // TODO CREATE
            baseConhecimentoDto = (BaseConhecimentoDTO) this.getDao().create(baseConhecimentoDto);

            HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto = new HistoricoBaseConhecimentoDTO();

            Reflexao.copyPropertyValues(baseConhecimentoDto, historicoBaseConhecimentoDto);

            historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

            historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);

            baseConhecimentoDto.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());

            this.getDao().updateNotNull(baseConhecimentoDto);

            this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "C");

            // TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.

            this.criarImportanciaConhecimentoUsuario(baseConhecimentoDto, transactionControler);
            this.criarImportanciaConhecimentoGrupo(baseConhecimentoDto, transactionControler);
            this.criarRelacionamentoEntreConhecimentos(baseConhecimentoDto, transactionControler);
            this.criarComentarios(baseConhecimentoDto, transactionControler);
            this.criarRelacionamentoEntreEventoMonitConhecimento(baseConhecimentoDto, transactionControler);
            // TODO CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
            if (baseConhecimentoDto.getIdProblema() != null && baseConhecimentoDto.getIdBaseConhecimento() != null) {
                conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
				conhecimentoProblemaDao.deleteByIdProblema(baseConhecimentoDto.getIdProblema());
                conhecimentoProblemaDTO.setIdProblema(baseConhecimentoDto.getIdProblema());
                conhecimentoProblemaDTO.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
            }

            String pasta = "";
            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
                pasta = controleGEDService.getProximaPastaArmazenar();

                File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
            }

            final ArrayList<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento = new ArrayList<AnexoBaseConhecimentoDTO>();
            if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

                for (final UploadDTO uploadDto : arquivosUpados) {

                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                    controleGEDDTO.setId(baseConhecimentoDto.getIdBaseConhecimento());
                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                    controleGEDDTO.setPasta(pasta);
                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                        controleGEDDTO.setPathArquivo(uploadDto.getPath());
                    } else {
                        controleGEDDTO.setPathArquivo(null);
                    }

                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                        if (controleGEDDTO != null) {
                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                    + Util.getFileExtension(uploadDto.getNameFile()));
                            CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                    + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
                            arquivo.delete();
                        }
                    }

                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                    if (controleGEDDTO != null) {
                        anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                        anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                    }
                    anexoBaseConhecimento.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                    try {
                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                    listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
                }
            } else {
                System.out.println("BaseConhecimento - A arquivosUpados esta vazia!");
            }

            // @Author euler.ramos
            // Poderia tratar a exclusão quando a base se tona arquivada ou não publicada, mas por enquanto prefiro deixá-la indexada!
            // INDEXAÇÃO LUCENE
            // Indexando Base de Conhecimento
            final Lucene lucene = new Lucene();
            // Publicada, Não arquivada e Não excluída!
            lucene.indexarBaseConhecimento(baseConhecimentoDto, listaAnexoBaseConhecimento);

            transactionControler.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("GED BaseConhecimento - Erro ao gravar Arquivo ged e gravar o ged no banco de dados: " + e);
            this.rollbackTransaction(transactionControler, e);
        } finally {
            try {
                transactionControler.close();
            } catch (final PersistenceException e) {
                e.printStackTrace();
            }
        }

        return baseConhecimentoDto;
    }

    @Override
    public void update(final BaseConhecimentoDTO baseConhecimentoDto, final Collection<UploadDTO> arquivosUpados, final Integer idEmpresa, final UsuarioDTO usuarioDto)
            throws ServiceException, Exception {
        // @Author euler.ramos
        final Lucene lucene = new Lucene();
        final ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final ImportanciaConhecimentoUsuarioService importanciaConhecimentoUsuarioService = (ImportanciaConhecimentoUsuarioService) ServiceLocator.getInstance().getService(
                ImportanciaConhecimentoUsuarioService.class, null);
        final ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(
                ImportanciaConhecimentoGrupoService.class, null);
        final BaseConhecimentoRelacionadoService baseConhecimentoRelacionadoService = (BaseConhecimentoRelacionadoService) ServiceLocator.getInstance().getService(
                BaseConhecimentoRelacionadoService.class, null);

        NotificacaoDTO notificacaoDto = new NotificacaoDTO();
        HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto = new HistoricoBaseConhecimentoDTO();
        final ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();

        String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");

        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }

        final String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");
        final String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");

        final AnexoBaseConhecimentoDAO anexoBaseConhecimentoDao = new AnexoBaseConhecimentoDAO();
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        final HistoricoBaseConhecimentoDAO historicoBaseConhecimentoDao = new HistoricoBaseConhecimentoDAO();
        final ConhecimentoProblemaDao conhecimentoProblemaDao = new ConhecimentoProblemaDao();

        final TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {

            this.getDao().setTransactionControler(transactionControler);
            anexoBaseConhecimentoDao.setTransactionControler(transactionControler);
            controleGEDDao.setTransactionControler(transactionControler);
            historicoBaseConhecimentoDao.setTransactionControler(transactionControler);
            conhecimentoProblemaDao.setTransactionControler(transactionControler);

            transactionControler.start();

            BaseConhecimentoDTO novaBaseConhecimento = this.atribuirValoresNovaBaseConhecimento(baseConhecimentoDto);

            final String status = ((BaseConhecimentoDTO) this.getDao().restore(baseConhecimentoDto)).getStatus();

            String pasta = "";
            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
                pasta = controleGEDService.getProximaPastaArmazenar();

                File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
            }

            final boolean isAprovaBaseConhecimento = this.usuarioAprovaBaseConhecimento(usuarioDto, baseConhecimentoDto.getIdPasta());

            // A BASE IRÁ SER APROVADA
            if (novaBaseConhecimento.getStatus() != null && isAprovaBaseConhecimento && novaBaseConhecimento.getStatus().equalsIgnoreCase("S")) {
                if (novaBaseConhecimento.getVersao() != null && !novaBaseConhecimento.getVersao().equals("")) {
                    final Double novaVersao = Double.parseDouble(novaBaseConhecimento.getVersao()) + 0.1;

                    novaBaseConhecimento.setVersao(novaVersao.toString().substring(0, 3));
                    novaBaseConhecimento.setTitulo(baseConhecimentoDto.getTitulo().split(" - v")[0] + " - v" + novaBaseConhecimento.getVersao());
                } else {
                    novaBaseConhecimento.setVersao("1.0");
                }

                // BASE NÃO APROVADA - VAI SER APROVADA
                if (baseConhecimentoDto.getIdBaseConhecimentoPai() != null || status.equalsIgnoreCase("N")) {
                    try {
                        novaBaseConhecimento.setStatus("S");
                        novaBaseConhecimento.setIdBaseConhecimentoPai(null);
                        novaBaseConhecimento.setIdUsuarioAprovador(usuarioDto.getIdUsuario());
                        novaBaseConhecimento.setDataPublicacao(UtilDatas.getDataAtual());

                        if (novaBaseConhecimento.getErroConhecido() == null || novaBaseConhecimento.getErroConhecido().equalsIgnoreCase("")) {
                            novaBaseConhecimento.setErroConhecido("N");
                        }

                        notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

                        if (notificacaoDto.getIdNotificacao() != null) {
                            novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        }

                        // TODO CREATE
                        novaBaseConhecimento = (BaseConhecimentoDTO) this.getDao().create(novaBaseConhecimento);

                        if (baseConhecimentoDto.getIdBaseConhecimentoPai() != null) {

                            final BaseConhecimentoDTO baseConhecimentoPai = new BaseConhecimentoDTO();

                            baseConhecimentoPai.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimentoPai());
                            baseConhecimentoPai.setStatus("S");

                            this.getDao().updateNotNull(baseConhecimentoPai);

                        }

                        Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

                        historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

                        historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

                        if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
                        } else {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
                        }

                        if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {

                            novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());

                            this.getDao().updateNotNull(novaBaseConhecimento);
                        }

                        // TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.
                        this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");

                        idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

                        this.criarImportanciaConhecimentoUsuario(novaBaseConhecimento, transactionControler);
                        this.criarImportanciaConhecimentoGrupo(novaBaseConhecimento, transactionControler);
                        this.criarRelacionamentoEntreConhecimentos(novaBaseConhecimento, transactionControler);
                        this.criarComentarios(novaBaseConhecimento, transactionControler);
                        this.criarRelacionamentoEntreEventoMonitConhecimento(novaBaseConhecimento, transactionControler);

                        // TODO CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
                        if (novaBaseConhecimento.getIdProblema() != null && novaBaseConhecimento.getIdBaseConhecimento() != null) {
                            conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
							conhecimentoProblemaDao.deleteByIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                            conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
                        }

                        final ArrayList<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento = new ArrayList<AnexoBaseConhecimentoDTO>();
                        if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

                            for (final UploadDTO uploadDto : arquivosUpados) {

                                if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
                                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                                        controleGEDDTO.setPathArquivo(uploadDto.getPath());
                                    } else {
                                        controleGEDDTO.setPathArquivo(null);
                                    }

                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

                                        if (controleGEDDTO != null) {

                                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                                    + Util.getFileExtension(uploadDto.getNameFile()));

                                            CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

                                            anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged");

                                            arquivo.delete();
                                        }
                                    }

                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    if (controleGEDDTO != null) {
                                        anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                        anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    }
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    try {
                                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }

                                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                                    listaAnexoBaseConhecimento.add(anexoBaseConhecimento);

                                } else {

                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    final File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

                                    this.copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

                                    anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                    anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());

                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    try {
                                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }

                                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                                    listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
                                }
                            }
                        }

                        // @Author euler.ramos
                        lucene.indexarBaseConhecimento(novaBaseConhecimento, listaAnexoBaseConhecimento);

                        baseConhecimentoRelacionadoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
                        importanciaConhecimentoUsuarioService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
                        importanciaConhecimentoGrupoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
                        conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						conhecimentoProblemaDao.deleteByIdProblema(baseConhecimentoDto.getIdProblema());
                        final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
                        final ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao = new ConhecimentoSolicitacaoDao();
                        solicitacaoServicoDao.setTransactionControler(transactionControler);
                        conhecimentoSolicitacaoDao.setTransactionControler(transactionControler);

                        final List<SolicitacaoServicoDTO> listaSolicitacoes = solicitacaoServicoDao.listaSolicitacoesRelacionadasBaseconhecimento(baseConhecimentoDto
                                .getIdBaseConhecimento());

                        conhecimentoSolicitacaoDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());

                        if (listaSolicitacoes != null) {
                            for (final SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoes) {
                                final ConhecimentoSolicitacaoDTO obj = new ConhecimentoSolicitacaoDTO();
                                obj.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                obj.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
                                conhecimentoSolicitacaoDao.create(obj);
                            }
                        }

                        this.getDao().delete(baseConhecimentoDto);

                        // Excluir Base de Conhecimento Antiga do índice, atendendo solicitação: 111236 nessa exclusão os anexos são excluidos juntamente.
                        lucene.excluirBaseConhecimento(baseConhecimentoDto, true);

                        // TODO EXCLUIR COMENTÁRIOS

                        // TODO EXCLUIR NOTIFICAÇÕES

                        // TODO EXCLUIR IMPORTÂNCIA CONHECIMENTO

                        // TODO EXCLUIR COMENTÁRIOS

                        // TODO EXCLUIR CONHECIMENTOPROBLEMA

                        // EXCLUO OS ANEXOS DA BASE DE CONHECIMENTO ANTIGA.

                        final Collection<AnexoBaseConhecimentoDTO> anexosBaseConhecimento = anexoBaseConhecimentoDao.consultarAnexosDaBaseConhecimento(baseConhecimentoDto);
                        if (anexosBaseConhecimento != null && !anexosBaseConhecimento.isEmpty()) {
                            for (final AnexoBaseConhecimentoDTO anexo : anexosBaseConhecimento) {
                                anexoBaseConhecimentoDao.delete(anexo);
                                this.deleteDir(new File(anexo.getLink()));
                            }
                        }

                        // EXCLUO OS GEDS DA BASE DE CONHECIMENTO ANTIGA.

                        final Collection<ControleGEDDTO> gedsBaseConhecimento = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_BASECONHECIMENTO,
                                baseConhecimentoDto.getIdBaseConhecimento());
                        if (gedsBaseConhecimento != null && !gedsBaseConhecimento.isEmpty()) {
                            for (final ControleGEDDTO ged : gedsBaseConhecimento) {
                                controleGEDDao.delete(ged);
                            }
                        }

                        transactionControler.commit();

                    } catch (final Exception e) {
                        throw e;
                    }

                } else {
                    // BASE PUBLICADA, ALTERADA VAI SER PUBLICADA NOVAMENTE COM OUTRA VERSÃO
                    try {
                        novaBaseConhecimento.setIdUsuarioAprovador(usuarioDto.getIdUsuario());
                        novaBaseConhecimento.setDataPublicacao(UtilDatas.getDataAtual());

                        notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

                        if (notificacaoDto.getIdNotificacao() != null) {
                            novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        }

                        // TODO CREATE
                        novaBaseConhecimento = (BaseConhecimentoDTO) this.getDao().create(novaBaseConhecimento);

                        Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

                        historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

                        historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

                        if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
                        } else {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
                        }

                        if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {

                            novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());

                            this.getDao().updateNotNull(novaBaseConhecimento);
                        }

                        this.getDao().restore(baseConhecimentoDto);

                        baseConhecimentoDto.setArquivado("S");

                        this.getDao().updateNotNull(baseConhecimentoDto);

                        lucene.excluirBaseConhecimento(baseConhecimentoDto, true);

                        // TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.
                        this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");

                        idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

                        this.criarImportanciaConhecimentoUsuario(novaBaseConhecimento, transactionControler);
                        this.criarImportanciaConhecimentoGrupo(novaBaseConhecimento, transactionControler);
                        this.criarRelacionamentoEntreConhecimentos(novaBaseConhecimento, transactionControler);
                        this.criarComentarios(novaBaseConhecimento, transactionControler);
                        this.criarRelacionamentoEntreEventoMonitConhecimento(novaBaseConhecimento, transactionControler);

                        // TODO CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
                        if (novaBaseConhecimento.getIdProblema() != null && novaBaseConhecimento.getIdBaseConhecimento() != null) {

                            conhecimentoProblemaDao.deleteByIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.deleteByIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                            conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
                        }

                        final ArrayList<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento = new ArrayList<AnexoBaseConhecimentoDTO>();
                        if (arquivosUpados != null && !arquivosUpados.isEmpty()) {
                            for (final UploadDTO uploadDto : arquivosUpados) {
                                if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
                                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                                        controleGEDDTO.setPathArquivo(uploadDto.getPath());
                                    } else {
                                        controleGEDDTO.setPathArquivo(null);
                                    }

                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                                        if (controleGEDDTO != null) {
                                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                                    + Util.getFileExtension(uploadDto.getNameFile()));
                                            CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                                            anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged");
                                            arquivo.delete();
                                        }
                                    }

                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    if (controleGEDDTO != null) {
                                        anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                        anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    }
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    try {
                                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }

                                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                                    listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
                                } else {
                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    final File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

                                    this.copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

                                    anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                    anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    try {
                                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }

                                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                                    listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
                                }
                            }
                        }

                        // Publicada, Não arquivada e Não excluída!
                        lucene.indexarBaseConhecimento(novaBaseConhecimento, listaAnexoBaseConhecimento);

                        transactionControler.commit();

                    } catch (final Exception e) {
                        throw e;
                    }
                }

                // A BASE NÃO SERÁ PUBLICADA
            } else {
                // BASE NÃO APROVADA E QUE CONTINUARÁ NÃO APROVADA
                if (status.equalsIgnoreCase("N")) {
                    try {
                        notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

                        if (notificacaoDto.getIdNotificacao() != null) {
                            novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        }

                        // TODO CREATE
                        novaBaseConhecimento = (BaseConhecimentoDTO) this.getDao().create(novaBaseConhecimento);

                        Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

                        historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

                        historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

                        if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
                        } else {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
                        }

                        if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {
                            novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());
                            this.getDao().updateNotNull(novaBaseConhecimento);
                        }

                        // TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.
                        this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");

                        // TODO CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
                        if (novaBaseConhecimento.getIdProblema() != null && novaBaseConhecimento.getIdBaseConhecimento() != null) {

                            conhecimentoProblemaDao.deleteByIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.deleteByIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                            conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
                        }

                        if (arquivosUpados != null && !arquivosUpados.isEmpty()) {
                            for (final UploadDTO uploadDto : arquivosUpados) {
                                if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
                                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                                        controleGEDDTO.setPathArquivo(uploadDto.getPath());
                                    } else {
                                        controleGEDDTO.setPathArquivo(null);
                                    }

                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

                                        if (controleGEDDTO != null) {
                                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                                    + Util.getFileExtension(uploadDto.getNameFile()));
                                            CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                                            anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged");
                                            arquivo.delete();
                                        }
                                    }

                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    if (controleGEDDTO != null) {
                                        anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                        anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    }
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    try {
                                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }

                                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                                } else {

                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
                                    AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();
                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    final File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

                                    this.copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

                                    anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                    anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    // Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
                                    try {
                                        final Arquivo arquivo = new Arquivo(controleGEDDTO);
                                        anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }

                                    anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);
                                }
                            }
                        }

                        // EXCLUO BASE DE CONHECIMENTO ANTIGA

                        baseConhecimentoRelacionadoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
                        importanciaConhecimentoUsuarioService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
                        importanciaConhecimentoGrupoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
                        conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());

                        final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
                        final ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao = new ConhecimentoSolicitacaoDao();
                        solicitacaoServicoDao.setTransactionControler(transactionControler);
                        conhecimentoSolicitacaoDao.setTransactionControler(transactionControler);

                        final List<SolicitacaoServicoDTO> listaSolicitacoes = solicitacaoServicoDao.listaSolicitacoesRelacionadasBaseconhecimento(baseConhecimentoDto
                                .getIdBaseConhecimento());

                        conhecimentoSolicitacaoDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());

                        if (listaSolicitacoes != null) {
                            for (final SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoes) {
                                final ConhecimentoSolicitacaoDTO obj = new ConhecimentoSolicitacaoDTO();
                                obj.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                obj.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
                                conhecimentoSolicitacaoDao.create(obj);
                            }
                        }

                        this.getDao().delete(baseConhecimentoDto);
                        lucene.excluirBaseConhecimento(baseConhecimentoDto, true);

                        // EXCLUO OS ANEXOS DA BASE DE CONHECIMENTO ANTIGA.

                        final Collection<AnexoBaseConhecimentoDTO> anexosBaseConhecimento = anexoBaseConhecimentoDao.consultarAnexosDaBaseConhecimento(baseConhecimentoDto);

                        if (anexosBaseConhecimento != null && !anexosBaseConhecimento.isEmpty()) {

                            for (final AnexoBaseConhecimentoDTO anexo : anexosBaseConhecimento) {
                                anexoBaseConhecimentoDao.delete(anexo);
                                this.deleteDir(new File(anexo.getLink()));
                            }
                        }

                        // EXCLUO OS GEDS DA BASE DE CONHECIMENTO ANTIGA.

                        final Collection<ControleGEDDTO> gedsBaseConhecimento = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_BASECONHECIMENTO,
                                baseConhecimentoDto.getIdBaseConhecimento());

                        if (gedsBaseConhecimento != null && !gedsBaseConhecimento.isEmpty()) {
                            for (final ControleGEDDTO ged : gedsBaseConhecimento) {
                                controleGEDDao.delete(ged);
                            }
                        }

                        idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

                        transactionControler.commit();

                    } catch (final Exception e) {
                        throw e;
                    }
                } else {
                    // BASE APROVADA, ALTERADA NÃO SERÁ PUBLICADA
                    novaBaseConhecimento.setStatus("N");

                    novaBaseConhecimento.setIdBaseConhecimento(null);
                    novaBaseConhecimento.setIdBaseConhecimentoPai(baseConhecimentoDto.getIdBaseConhecimento());

                    try {
                        // TODO CREATE
                        novaBaseConhecimento = (BaseConhecimentoDTO) this.getDao().create(novaBaseConhecimento);

                        notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

                        if (notificacaoDto.getIdNotificacao() != null) {
                            novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
                        }

                        Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

                        historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

                        historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

                        if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
                        } else {
                            historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
                        }

                        if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {
                            novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());
                        }

                        this.getDao().updateNotNull(novaBaseConhecimento);

                        this.getDao().restore(baseConhecimentoDto);

                        baseConhecimentoDto.setArquivado("S");

                        this.getDao().updateNotNull(baseConhecimentoDto);
                        lucene.excluirBaseConhecimento(baseConhecimentoDto, true);

                        this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");

                        // TODO CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
                        if (novaBaseConhecimento.getIdProblema() != null && novaBaseConhecimento.getIdBaseConhecimento() != null) {

                            conhecimentoProblemaDao.deleteByIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.deleteByIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
                            conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                            conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
                        }

                        idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

                        if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

                            for (final UploadDTO uploadDto : arquivosUpados) {

                                if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                                    final AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                                        controleGEDDTO.setPathArquivo(uploadDto.getPath());
                                    } else {
                                        controleGEDDTO.setPathArquivo(null);
                                    }

                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

                                        if (controleGEDDTO != null) {

                                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                                    + Util.getFileExtension(uploadDto.getNameFile()));

                                            CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

                                            anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                                    + ".ged");

                                            arquivo.delete();

                                        }
                                    }

                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    if (controleGEDDTO != null) {
                                        anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                        anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    }
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

                                } else {
                                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                                    final AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

                                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
                                    controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
                                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                                    controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                                    controleGEDDTO.setPasta(pasta);
                                    controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

                                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                                    final File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

                                    this.copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

                                    anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
                                    anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
                                    anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
                                    anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
                                    anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
                                    anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
                                    anexoBaseConhecimentoDao.create(anexoBaseConhecimento);
                                }
                            }
                        }

                        transactionControler.commit();

                    } catch (final Exception e) {
                        throw e;
                    }
                }
            }

        } catch (final Exception e) {
            this.rollbackTransaction(transactionControler, e);
            e.printStackTrace();
        } finally {
            try {
                transactionControler.close();
            } catch (final PersistenceException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void excluir(final BaseConhecimentoDTO baseConhecimentoBean, final boolean isAprovaBaseConhecimento) throws Exception {

        if (isAprovaBaseConhecimento) {

            final Lucene lucene = new Lucene();
            lucene.excluirBaseConhecimento(baseConhecimentoBean, true);

            this.excluirBaseConhecimentoComentarios(baseConhecimentoBean);

            this.excluirBaseConhecimento(baseConhecimentoBean);

            this.enviarEmailNotificacaoConhecimento(baseConhecimentoBean, null, "D");

        }
    }

    /**
     * Atribui os valores dos atributos da nova Base de Conhecimento a ser cadastrada.
     *
     * @return BaseConhecimentoDTO
     * @author breno.guimaraes
     * @throws Exception
     * @throws ServiceException
     * @author valdoilo.damasceno
     */
    private BaseConhecimentoDTO atribuirValoresNovaBaseConhecimento(final BaseConhecimentoDTO baseConhecimento) throws ServiceException, Exception {
        final BaseConhecimentoDTO novaBaseConhecimentoBean = new BaseConhecimentoDTO();

        novaBaseConhecimentoBean.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());
        novaBaseConhecimentoBean.setTitulo(baseConhecimento.getTitulo());
        novaBaseConhecimentoBean.setConteudo(baseConhecimento.getConteudo());
        novaBaseConhecimentoBean.setDataExpiracao(baseConhecimento.getDataExpiracao());
        novaBaseConhecimentoBean.setDataInicio(UtilDatas.getDataAtual());
        novaBaseConhecimentoBean.setIdPasta(baseConhecimento.getIdPasta());
        novaBaseConhecimentoBean.setStatus(baseConhecimento.getStatus());
        novaBaseConhecimentoBean.setIdUsuarioAutor(baseConhecimento.getIdUsuarioAutor());
        novaBaseConhecimentoBean.setComentarios(baseConhecimento.getComentarios());
        novaBaseConhecimentoBean.setListImportanciaConhecimentoUsuario(baseConhecimento.getListImportanciaConhecimentoUsuario());
        novaBaseConhecimentoBean.setListImportanciaConhecimentoGrupo(baseConhecimento.getListImportanciaConhecimentoGrupo());
        novaBaseConhecimentoBean.setJustificativaObservacao(baseConhecimento.getJustificativaObservacao());
        novaBaseConhecimentoBean.setFonteReferencia(baseConhecimento.getFonteReferencia());
        novaBaseConhecimentoBean.setIdNotificacao(baseConhecimento.getIdNotificacao());
        novaBaseConhecimentoBean.setListaDeUsuarioNotificacao(baseConhecimento.getListaDeUsuarioNotificacao());
        novaBaseConhecimentoBean.setListaDeGrupoNotificacao(baseConhecimento.getListaDeGrupoNotificacao());
        novaBaseConhecimentoBean.setTituloNotificacao(baseConhecimento.getTituloNotificacao());
        novaBaseConhecimentoBean.setTipoNotificacao(baseConhecimento.getTipoNotificacao());
        novaBaseConhecimentoBean.setListBaseConhecimentoRelacionado(baseConhecimento.getListBaseConhecimentoRelacionado());
        novaBaseConhecimentoBean.setFaq(baseConhecimento.getFaq());
        novaBaseConhecimentoBean.setOrigem(baseConhecimento.getOrigem());
        novaBaseConhecimentoBean.setIdHistoricoBaseConhecimento(baseConhecimento.getIdHistoricoBaseConhecimento());
        novaBaseConhecimentoBean.setArquivado(baseConhecimento.getArquivado());
        novaBaseConhecimentoBean.setIdBaseConhecimentoPai(baseConhecimento.getIdBaseConhecimentoPai());
        novaBaseConhecimentoBean.setPrivacidade(baseConhecimento.getPrivacidade());
        novaBaseConhecimentoBean.setSituacao(baseConhecimento.getSituacao());
        novaBaseConhecimentoBean.setListEventoMonitoramento(baseConhecimento.getListEventoMonitoramento());
        novaBaseConhecimentoBean.setGerenciamentoDisponibilidade(baseConhecimento.getGerenciamentoDisponibilidade());
        novaBaseConhecimentoBean.setDireitoAutoral(baseConhecimento.getDireitoAutoral());
        novaBaseConhecimentoBean.setLegislacao(baseConhecimento.getLegislacao());
        novaBaseConhecimentoBean.setErroConhecido(baseConhecimento.getErroConhecido());
        novaBaseConhecimentoBean.setIdProblema(baseConhecimento.getIdProblema());
        novaBaseConhecimentoBean.setIdHistoricoBaseConhecimento(null);

        final String versao = ((BaseConhecimentoDTO) this.getDao().restore(baseConhecimento)).getVersao();
        novaBaseConhecimentoBean.setVersao(versao);

        return novaBaseConhecimentoBean;
    }

    /**
     * Exclui diretório e arquivos.
     *
     * @param dir
     * @author valdoilo.damasceno
     */
    public boolean deleteDir(final File dir) {
        if (dir.isDirectory()) {
            final String[] children = dir.list();
            for (final String element : children) {
                final boolean success = this.deleteDir(new File(dir, element));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * Copia Arquivo do diretÃ³rio temporÃ¡rio para DiretÃ³rio definitivo.
     *
     * @param fonte
     * @param destino
     * @throws IOException
     * @author valdoilo.damasceno
     */
    public void copiarArquivo(final File fonte, final String destino) throws IOException {
        InputStream in;
        try {
            in = new FileInputStream(fonte);
            final OutputStream out = new FileOutputStream(new File(destino));

            final byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            in.close();
            out.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Seta dataFim à Base de Conhecimento.
     *
     * @param base
     * @throws Exception
     */
    private void excluirBaseConhecimento(final BaseConhecimentoDTO base) throws Exception {
        this.excluirAnexosBaseConhecimento(base);
        base.setDataFim(UtilDatas.getDataAtual());
        final Lucene lucene = new Lucene();
        lucene.excluirBaseConhecimento(base, true);
        this.getDao().update(base);
    }

    /**
     * Seta dataFim à Comentarios.
     *
     * @param base
     * @throws Exception
     */
    private void excluirBaseConhecimentoComentarios(final BaseConhecimentoDTO base) throws Exception {
        final Collection<ComentariosDTO> comentarios = this.geComentariosDAO().consultarComentarios(base);
        if (comentarios != null) {
            for (final ComentariosDTO comentarioDto : comentarios) {
                comentarioDto.setDataFim(UtilDatas.getDataAtual());
                this.geComentariosDAO().update(comentarioDto);
            }
        }

    }

    /**
     * Excluir Anexos da Base Conhecimento atribuindo sua dataFim.
     *
     * @throws Exception
     * @author valdoilo.damasceno
     */
    private void excluirAnexosBaseConhecimento(final BaseConhecimentoDTO base) throws Exception {
        final AnexoBaseConhecimentoDAO anexoBaseConhecimentoDao = new AnexoBaseConhecimentoDAO();

        final Collection<AnexoBaseConhecimentoDTO> anexosBaseConhecimento = anexoBaseConhecimentoDao.consultarAnexosDaBaseConhecimento(base);

        if (anexosBaseConhecimento != null && !anexosBaseConhecimento.isEmpty()) {
            for (final AnexoBaseConhecimentoDTO anexo : anexosBaseConhecimento) {
                anexoBaseConhecimentoDao.delete(anexo);

                this.deleteDir(new File(anexo.getLink()));
            }
        }

        final Lucene lucene = new Lucene();
        lucene.excluiAnexosDaBaseConhecimento(Long.parseLong(String.valueOf(base.getIdBaseConhecimento())));
    }

    /**
     * @return valor do atributo subDiretorio.
     */
    public File getPastaDaBaseConhecimento() {
        return pastaDaBaseConhecimento;
    }

    /**
     * Define valor do atributo subDiretorio.
     *
     * @param pastaDaBaseConhecimento
     */
    public void setPastaDaBaseConhecimento(final File pastaDaBaseConhecimento) {
        this.pastaDaBaseConhecimento = pastaDaBaseConhecimento;
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPasta(final PastaDTO pasta) throws Exception {
        return this.getDao().listarBaseConhecimentoByPasta(pasta);
    }

    @Override
    public Double calcularNota(final Integer idBaseConhecimento) throws Exception {
        final ComentariosDAO comentarioDao = new ComentariosDAO();
        return comentarioDao.calcularNota(idBaseConhecimento);
    }

    @Override
    public Long contarVotos(final Integer idBaseConhecimento) throws Exception {
        return this.geComentariosDAO().contarVotos(idBaseConhecimento);
    }

    @Override
    public boolean verificarSeBaseConhecimentoJaPossuiNovaVersao(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        return this.getDao().verificarSeBaseConhecimentoJaPossuiNovaVersao(baseConhecimento);
    }

    @Override
    public boolean verificarSeBaseConhecimentoPossuiVersaoAnterior(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        final String versao = StringUtils.remove(baseConhecimento.getTitulo(), baseConhecimento.getVersao());
        if (StringUtils.isBlank(versao)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean verificaBaseConhecimentoExistente(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        return this.getDao().verificaSeBaseConhecimentoExiste(baseConhecimentoDTO);
    }

    @Override
    public List<BaseConhecimentoDTO> validaNota(final BaseConhecimentoDTO baseconhecimento) throws Exception {
        return this.getDao().validaNota(baseconhecimento);
    }

    @Override
    public Collection<BaseConhecimentoDTO> listaBaseConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        return this.getDao().listaBaseConhecimento(baseConhecimento);
    }

    @Override
    public Collection<BaseConhecimentoDTO> listaBaseConhecimentoUltimasVersoes(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        return this.getDao().listaBaseConhecimentoUltimasVersoes(baseConhecimento);
    }

    /**
     * Verifica se usuário aprova Base Conhecimento na pasta selecionada.
     *
     * @param usuarioDto
     * @param idPasta
     * @return true = aprova; false = não aprova.
     * @throws ServiceException
     * @throws Exception
     */
    private boolean usuarioAprovaBaseConhecimento(final UsuarioDTO usuarioDto, final Integer idPasta) throws ServiceException, Exception {
        boolean aprovaBaseConhecimento = false;

        final PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

        aprovaBaseConhecimento = perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, idPasta);

        return aprovaBaseConhecimento;
    }

    /**
     * Cria comentários da Base de Conhecimento.
     *
     * @param baseConhecimentoDto
     * @param transactionControler
     * @throws Exception
     * @author Vadoilo Damasceno
     */
    private void criarComentarios(final BaseConhecimentoDTO baseConhecimentoDto, final TransactionControler transactionControler) throws Exception {
        final Collection<ComentariosDTO> comentarios = baseConhecimentoDto.getComentarios();

        final ComentariosDAO comentariosDao = new ComentariosDAO();

        comentariosDao.setTransactionControler(transactionControler);

        if (comentarios != null && !comentarios.isEmpty()) {
            for (final ComentariosDTO comentario : comentarios) {
                comentario.setDataInicio(UtilDatas.getDataAtual());

                comentariosDao.create(comentario);
            }
        }
    }

    @Override
    public void criarImportanciaConhecimentoUsuario(final BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception {
        if (baseConhecimentoDto.getIdBaseConhecimento() != null) {

            final ImportanciaConhecimentoUsuarioService importanciaConhecimentoUsuarioService = (ImportanciaConhecimentoUsuarioService) ServiceLocator.getInstance().getService(
                    ImportanciaConhecimentoUsuarioService.class, null);

            if (transactionControler == null) {
                transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
            }

            importanciaConhecimentoUsuarioService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

            final Collection<ImportanciaConhecimentoUsuarioDTO> listImportanciaConhecimentoUsuario = baseConhecimentoDto.getListImportanciaConhecimentoUsuario();
            if (listImportanciaConhecimentoUsuario != null && !listImportanciaConhecimentoUsuario.isEmpty()) {
                for (final ImportanciaConhecimentoUsuarioDTO importanciaConhecimentoUsuario : listImportanciaConhecimentoUsuario) {
                    if (importanciaConhecimentoUsuario.getIdBaseConhecimento() == null) {
                        importanciaConhecimentoUsuario.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                        importanciaConhecimentoUsuarioService.create(importanciaConhecimentoUsuario, transactionControler);
                    } else {
                        importanciaConhecimentoUsuario.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                        importanciaConhecimentoUsuarioService.create(importanciaConhecimentoUsuario, transactionControler);
                    }
                }
            }
        }
    }

    @Override
    public void criarImportanciaConhecimentoGrupo(final BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception {
        if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
            final ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(
                    ImportanciaConhecimentoGrupoService.class, null);

            if (transactionControler == null) {
                transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
            }

            importanciaConhecimentoGrupoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

            final Collection<ImportanciaConhecimentoGrupoDTO> listImportanciaConhecimentoGrupo = baseConhecimentoDto.getListImportanciaConhecimentoGrupo();
            if (listImportanciaConhecimentoGrupo != null && !listImportanciaConhecimentoGrupo.isEmpty()) {
                for (final ImportanciaConhecimentoGrupoDTO importanciaConhecimentoGrupo : listImportanciaConhecimentoGrupo) {
                    if (importanciaConhecimentoGrupo.getIdBaseConhecimento() == null) {
                        importanciaConhecimentoGrupo.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                        importanciaConhecimentoGrupoService.create(importanciaConhecimentoGrupo, transactionControler);
                    } else {
                        importanciaConhecimentoGrupo.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                        importanciaConhecimentoGrupoService.create(importanciaConhecimentoGrupo, transactionControler);
                    }
                }
            }
        }
    }

    @Override
    public void criarRelacionamentoEntreConhecimentos(final BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception {
        if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
            final BaseConhecimentoRelacionadoService baseConhecimentoRelacionadoService = (BaseConhecimentoRelacionadoService) ServiceLocator.getInstance().getService(
                    BaseConhecimentoRelacionadoService.class, null);

            if (transactionControler == null) {
                transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
            }

            baseConhecimentoRelacionadoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

            final Collection<BaseConhecimentoRelacionadoDTO> listBaseConhecimentoRelacionado = baseConhecimentoDto.getListBaseConhecimentoRelacionado();
            if (listBaseConhecimentoRelacionado != null && !listBaseConhecimentoRelacionado.isEmpty()) {
                for (final BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionado : listBaseConhecimentoRelacionado) {
                    if (baseConhecimentoRelacionado.getIdBaseConhecimento() == null) {
                        baseConhecimentoRelacionado.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                        baseConhecimentoRelacionadoService.create(baseConhecimentoRelacionado, transactionControler);
                    } else {
                        baseConhecimentoRelacionado.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                        baseConhecimentoRelacionadoService.create(baseConhecimentoRelacionado, transactionControler);
                    }
                }
            }
        }
    }

    /**
     * Cria relacionamento entre Evento Monitoramento e Conhecimento.
     *
     * @param baseConhecimentoDto
     * @param transactionControler
     * @throws ServiceException
     * @throws Exception
     * @author Thays
     */
    @Override
    public void criarRelacionamentoEntreEventoMonitConhecimento(final BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException,
            Exception {

        if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
            final EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator.getInstance().getService(
                    EventoMonitConhecimentoService.class, null);

            if (transactionControler == null) {
                transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
            }

            eventoMonitConhecimentoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

            final Collection<EventoMonitConhecimentoDTO> listEventoMonitConhecimento = baseConhecimentoDto.getListEventoMonitoramento();
            if (listEventoMonitConhecimento != null && !listEventoMonitConhecimento.isEmpty()) {
                for (final EventoMonitConhecimentoDTO eventoMonitConhecimento : listEventoMonitConhecimento) {
                    eventoMonitConhecimento.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
                    eventoMonitConhecimentoService.create(eventoMonitConhecimento, transactionControler);
                }
            }
        }
    }

    @Override
    public NotificacaoDTO criarNotificacao(final BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception {

        final NotificacaoDTO notificacaoDto = new NotificacaoDTO();

        final NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

        if (baseConhecimentoDto.getTituloNotificacao() != null && !StringUtils.isEmpty(baseConhecimentoDto.getTituloNotificacao().trim())
                && baseConhecimentoDto.getTipoNotificacao() != null && !StringUtils.isEmpty(baseConhecimentoDto.getTipoNotificacao().trim())) {

            if (transactionControler == null) {
                transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
            }

            if (baseConhecimentoDto.getIdNotificacao() != null) {
                notificacaoDto.setIdNotificacao(baseConhecimentoDto.getIdNotificacao());
                notificacaoDto.setListaDeUsuario(baseConhecimentoDto.getListaDeUsuarioNotificacao());
                notificacaoDto.setListaDeGrupo(baseConhecimentoDto.getListaDeGrupoNotificacao());
                notificacaoDto.setTitulo(baseConhecimentoDto.getTituloNotificacao());
                notificacaoDto.setTipoNotificacao(baseConhecimentoDto.getTipoNotificacao());
                notificacaoDto.setOrigemNotificacao(Enumerados.OrigemNotificacao.B.name());

                notificacaoService.update(notificacaoDto, transactionControler);

                return notificacaoDto;

            } else {
                notificacaoDto.setListaDeUsuario(baseConhecimentoDto.getListaDeUsuarioNotificacao());
                notificacaoDto.setListaDeGrupo(baseConhecimentoDto.getListaDeGrupoNotificacao());
                notificacaoDto.setTitulo(baseConhecimentoDto.getTituloNotificacao());
                notificacaoDto.setTipoNotificacao(baseConhecimentoDto.getTipoNotificacao());
                notificacaoDto.setOrigemNotificacao(Enumerados.OrigemNotificacao.B.name());

                return notificacaoService.create(notificacaoDto, transactionControler);
            }
        } else {
            return notificacaoDto;
        }
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQByPasta(final PastaDTO pasta) throws Exception {
        return this.getDao().listarBaseConhecimentoFAQByPasta(pasta);
    }

    private void enviarEmailNotificacaoConhecimento(final BaseConhecimentoDTO baseConhecimentoDTO, final TransactionControler transactionControler, final String crud)
            throws Exception {

        final EmpregadoDao empregadoDao = new EmpregadoDao();
        final NotificacaoDao notificacaoDao = new NotificacaoDao();

        Collection<EmpregadoDTO> colEmpregados = new ArrayList();

        if (transactionControler != null) {
            empregadoDao.setTransactionControler(transactionControler);
            notificacaoDao.setTransactionControler(transactionControler);
        }

        if (baseConhecimentoDTO.getIdNotificacao() != null && baseConhecimentoDTO.getIdNotificacao() != 0) {

            final String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
            String ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO, "8");
            String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(
                    ParametroSistema.ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO, "9");
            String ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO,
                    "10");
            String ID_MODELO_EMAIL = "";

            if (ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO == null || ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO.isEmpty()) {
                ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO = "11";
            }

            if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO.isEmpty()) {
                ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO = "12";
            }

            if (ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO == null || ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO.isEmpty()) {
                ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO = "13";
            }

            if (crud.equals("C")) {
                if (baseConhecimentoDTO.getTipoNotificacao().equals("T") || baseConhecimentoDTO.getTipoNotificacao().equals("C")) {
                    ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO;
                }
            } else if (crud.equals("U")) {
                if (baseConhecimentoDTO.getTipoNotificacao().equals("T") || baseConhecimentoDTO.getTipoNotificacao().equals("A")) {
                    ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO;
                }
            } else {
                if (baseConhecimentoDTO.getTipoNotificacao().equals("T") || baseConhecimentoDTO.getTipoNotificacao().equals("E")) {
                    ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO;
                }
            }

            if (!ID_MODELO_EMAIL.isEmpty()) {

                colEmpregados = empregadoDao.listarEmailsNotificacoesConhecimento(baseConhecimentoDTO.getIdBaseConhecimento());

                if (colEmpregados != null) {

                    for (final EmpregadoDTO empregados : colEmpregados) {

                        final MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL.trim()), new IDto[] {baseConhecimentoDTO});

                        if (empregados.getEmail() != null) {
                            mensagem.envia(empregados.getEmail(), "", remetente);
                        }

                    }
                }

            }
        }
    }

    @Override
    public BaseConhecimentoDTO getBaseConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        return this.getDao().getBaseConhecimento(baseConhecimento);
    }

    @Override
    public Collection<BaseConhecimentoDTO> listPesquisaBaseConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        return this.getDao().listPesquisaBaseConhecimento(baseConhecimento);
    }

    @Override
    public void arquivarConhecimento(final BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
        baseConhecimentoDto.setArquivado("S");
        this.getDao().arquivarConhecimento(baseConhecimentoDto);
    }

    @Override
    public Integer obterGrauDeImportanciaParaUsuario(final BaseConhecimentoDTO baseConhecimentoDto, final UsuarioDTO usuarioDto) throws Exception {
        final ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(
                ImportanciaConhecimentoGrupoService.class, null);
        final GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

        final Collection<GrupoEmpregadoDTO> listGrupoEmpregadoDto = grupoEmpregadoService.findByIdEmpregado(usuarioDto.getIdEmpregado());

        final ImportanciaConhecimentoGrupoDTO importanciaConhecimento = importanciaConhecimentoGrupoService.obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregadoDto,
                usuarioDto);

        if (importanciaConhecimento != null) {
            return Integer.parseInt(importanciaConhecimento.getGrauImportancia());
        }
        return 0;
    }

    @Override
    public void restaurarConhecimento(final BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
        baseConhecimentoDto.setArquivado("N");
        this.getDao().restaurarConhecimento(baseConhecimentoDto);
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPastaRelatorio(final PastaDTO pasta) throws Exception {
        return this.getDao().listarBaseConhecimentoByPastaRelatorio(pasta);
    }

    @Override
    public Collection<BaseConhecimentoDTO> obterHistoricoDeVersoes(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        return this.getDao().obterHistoricoDeVersoes(baseConhecimento);
    }

    @Override
    public void gravarSolicitacoesConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        if (baseConhecimento.getIdBaseConhecimento() != null) {
            final ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao = new ConhecimentoSolicitacaoDao();

            conhecimentoSolicitacaoDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

            try {
                if (baseConhecimento.getColItensIncidentes() != null) {
                    for (final Object element : baseConhecimento.getColItensIncidentes()) {

                        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) element;

                        final ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDto = new ConhecimentoSolicitacaoDTO();

                        conhecimentoSolicitacaoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                        conhecimentoSolicitacaoDto.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

                        conhecimentoSolicitacaoDao.create(conhecimentoSolicitacaoDto);
                    }
                }
            } catch (final Exception e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void gravarProblemasConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {
        if (baseConhecimento.getIdBaseConhecimento() != null) {
            final ConhecimentoProblemaDao conhecimentoProblemaDAO = new ConhecimentoProblemaDao();

            conhecimentoProblemaDAO.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

            try {
                if (baseConhecimento.getColItensProblema() != null) {
                    for (final Object element : baseConhecimento.getColItensProblema()) {
                        final ProblemaDTO problemaDTO = (ProblemaDTO) element;

                        final ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();
						conhecimentoProblemaDAO.deleteByIdProblema(problemaDTO.getIdProblema());
                        conhecimentoProblemaDTO.setIdProblema(problemaDTO.getIdProblema());
                        conhecimentoProblemaDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

                        conhecimentoProblemaDAO.create(conhecimentoProblemaDTO);
                    }
                }

            } catch (final Exception e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void gravarMudancaConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {

        if (baseConhecimento.getIdBaseConhecimento() != null) {

            final ConhecimentoMudancaDao conhecimentoMudancaDao = new ConhecimentoMudancaDao();
            conhecimentoMudancaDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

            try {
                if (baseConhecimento.getColItensMudanca() != null) {

                    for (final Object element : baseConhecimento.getColItensMudanca()) {

                        final RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) element;

                        final ConhecimentoMudancaDTO conhecimentoMudancaDTO = new ConhecimentoMudancaDTO();

                        conhecimentoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                        conhecimentoMudancaDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

                        conhecimentoMudancaDao.create(conhecimentoMudancaDTO);
                    }
                }
            } catch (final Exception e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void gravarLiberacaoConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {

        if (baseConhecimento.getIdBaseConhecimento() != null) {

            final ConhecimentoLiberacaoDao conhecimentoLiberacaoDao = new ConhecimentoLiberacaoDao();

            conhecimentoLiberacaoDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

            try {
                if (baseConhecimento.getColItensLiberacao() != null) {

                    for (final Object element : baseConhecimento.getColItensLiberacao()) {

                        final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO = (RequisicaoLiberacaoDTO) element;

                        final ConhecimentoLiberacaoDTO conhecimentoLiberacaoDTO = new ConhecimentoLiberacaoDTO();

                        conhecimentoLiberacaoDTO.setIdRequisicaoLiberacao(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                        conhecimentoLiberacaoDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

                        conhecimentoLiberacaoDao.create(conhecimentoLiberacaoDTO);
                    }
                }
            } catch (final Exception e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void gravarICConhecimento(final BaseConhecimentoDTO baseConhecimento) throws Exception {

        if (baseConhecimento.getIdBaseConhecimento() != null) {
            final ConhecimentoICDao conhecimentoICDao = new ConhecimentoICDao();

            conhecimentoICDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

            try {
                if (baseConhecimento.getColItensICSerialize() != null) {

                    for (final Object element : baseConhecimento.getColItensICSerialize()) {

                        final ItemConfiguracaoDTO itemConfiguracaoDto = (ItemConfiguracaoDTO) element;

                        final ConhecimentoICDTO conhecimentoICDTO = new ConhecimentoICDTO();

                        conhecimentoICDTO.setIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao());
                        conhecimentoICDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

                        conhecimentoICDao.create(conhecimentoICDTO);
                    }
                }
            } catch (final Exception e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByIds(final Integer[] ids) throws Exception {
        return this.getDao().listarBaseConhecimentoByIds(ids);
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

    @Override
    public Collection<BaseConhecimentoDTO> quantidadeBaseConhecimentoPorPeriodo(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {

        Collection<BaseConhecimentoDTO> listaBaseConhecimento = null;
        final Collection<BaseConhecimentoDTO> listaQuantitativoBaseConhecimentoDTOs = new ArrayList<BaseConhecimentoDTO>();

        final BaseConhecimentoDTO relatorioBaseConhecimentoDTO = new BaseConhecimentoDTO();

        Integer qtdPublicados = 0, qtdNaoPublicados = 0, qtdAcessados = 0, qtdAvaliados = 0, qtdExcluidos = 0, qtdArquivados = 0, qtdAtualizados = 0, tipoFaq = 0, qtdErroConhecido = 0, qtdDocumentos = 0;

        try {
            listaBaseConhecimento = this.getDao().listaBaseConhecimentoTotal(baseConhecimentoDTO);
            if (listaBaseConhecimento != null) {
                for (final BaseConhecimentoDTO baseConhecimentoDTO2 : listaBaseConhecimento) {
                    // Conta publicados e criados (não publicados) no período
                    if (baseConhecimentoDTO2.getStatus().trim().equalsIgnoreCase("S")) {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataPublicacao(), baseConhecimentoDTO.getDataInicio(),
                                baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdPublicados++;
                        }
                    } else {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(),
                                baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdNaoPublicados++;
                        }
                    }

                    // Conta excluídos
                    if (baseConhecimentoDTO2.getDataFim() != null) {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataFim(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdExcluidos++;
                        }
                    }

                    // Conta arquivados
                    if (baseConhecimentoDTO2.getArquivado() != null && baseConhecimentoDTO2.getArquivado().trim().equalsIgnoreCase("S")) {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(),
                                baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdArquivados++;
                        }
                    }

                    // Conta tipo FAQ
                    if (baseConhecimentoDTO2.getFaq() != null && baseConhecimentoDTO2.getFaq().trim().equalsIgnoreCase("S")) {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(),
                                baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            tipoFaq++;
                        }
                    }

                    // Conta tipo erro conhecidos
                    if (baseConhecimentoDTO2.getErroConhecido() != null && baseConhecimentoDTO2.getErroConhecido().trim().equalsIgnoreCase("S")) {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(),
                                baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdErroConhecido++;
                        }
                    }

                    // Conta Bases de Conhecimento que são qualificadas como Documento
                    if ((baseConhecimentoDTO2.getFaq() == null || !baseConhecimentoDTO2.getFaq().trim().equalsIgnoreCase("S"))
                            && (baseConhecimentoDTO2.getErroConhecido() == null || !baseConhecimentoDTO2.getErroConhecido().trim().equalsIgnoreCase("S"))) {
                        final boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(),
                                baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdDocumentos++;
                        }
                    }

                }
            }

            // Conta acessados no período
            final ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);
            final Integer contadorAcesso = contadorAcessoService.quantidadesDeAcessoPorPeriodo(baseConhecimentoDTO);
            if (contadorAcesso != null) {
                qtdAcessados = contadorAcesso;
            }

            // Conta avaliados no período
            final ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
            final Integer contadorComentarios = comentariosService.consultarComentariosPorPeriodo(baseConhecimentoDTO);
            if (contadorComentarios != null) {
                qtdAvaliados++;
            }

            // Conta atualizados no período
            final HistoricoBaseConhecimentoService historicoBaseConhecimentoService = (HistoricoBaseConhecimentoService) ServiceLocator.getInstance().getService(
                    HistoricoBaseConhecimentoService.class, null);
            final Collection<HistoricoBaseConhecimentoDTO> listHistoricoAlteracao = historicoBaseConhecimentoService.list();
            if (listHistoricoAlteracao != null) {
                for (final HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDTO : listHistoricoAlteracao) {
                    final Timestamp alteracao = historicoBaseConhecimentoDTO.getDataHoraAlteracao();
                    if (alteracao != null) {
                        final Date dataAlteracao = new Date(alteracao.getTime());
                        final boolean resp = UtilDatas.dataEntreIntervalo(dataAlteracao, baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
                        if (resp) {
                            qtdAtualizados++;
                        }
                    }
                }
            }

            relatorioBaseConhecimentoDTO.setQtdPublicados(qtdPublicados);
            relatorioBaseConhecimentoDTO.setQtdNaoPublicados(qtdNaoPublicados);
            relatorioBaseConhecimentoDTO.setQtdAcessados(qtdAcessados);
            relatorioBaseConhecimentoDTO.setQtdAvaliados(qtdAvaliados);
            relatorioBaseConhecimentoDTO.setQtdExcluidos(qtdExcluidos);
            relatorioBaseConhecimentoDTO.setQtdArquivados(qtdArquivados);
            relatorioBaseConhecimentoDTO.setQtdAtualizados(qtdAtualizados);
            relatorioBaseConhecimentoDTO.setTipoFaq(tipoFaq);
            relatorioBaseConhecimentoDTO.setQtdErroConhecido(qtdErroConhecido);
            relatorioBaseConhecimentoDTO.setQtdDocumentos(qtdDocumentos);

            listaQuantitativoBaseConhecimentoDTOs.add(relatorioBaseConhecimentoDTO);

        } catch (final ServiceException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listaQuantitativoBaseConhecimentoDTOs;
    }

    @Override
    public Collection<ComentariosDTO> consultaConhecimentosAvaliados(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        final ComentariosDAO dao = new ComentariosDAO();
        Collection<ComentariosDTO> listaComentarios = new ArrayList<ComentariosDTO>();
        try {
            listaComentarios = dao.consultarComentariosPorPeriodo(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return listaComentarios;
    }

    @Override
    public Collection<BaseConhecimentoDTO> consultaConhecimentosPorAutores(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        List<BaseConhecimentoDTO> listaConhecimentoPorAutor = new ArrayList<BaseConhecimentoDTO>();
        try {
            listaConhecimentoPorAutor = (List<BaseConhecimentoDTO>) this.getDao().consultaConhecimentoPorAutor(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return listaConhecimentoPorAutor;
    }

    @Override
    public Collection<BaseConhecimentoDTO> consultaConhecimentosPorAprovadores(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        List<BaseConhecimentoDTO> listaConhecimentoPorAprovador = new ArrayList<BaseConhecimentoDTO>();
        try {
            listaConhecimentoPorAprovador = (List<BaseConhecimentoDTO>) this.getDao().consultaConhecimentoPorAprovador(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return listaConhecimentoPorAprovador;
    }

    @Override
    public Collection<BaseConhecimentoDTO> consultaConhecimentosPublicadosPorOrigem(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        List<BaseConhecimentoDTO> listaConsultaConhecimentoPublicadosPorOrigem = new ArrayList<BaseConhecimentoDTO>();
        try {
            listaConsultaConhecimentoPublicadosPorOrigem = (List<BaseConhecimentoDTO>) this.getDao().consultaConhecimentosPublicadosPorOrigem(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return listaConsultaConhecimentoPublicadosPorOrigem;
    }

    @Override
    public Collection<BaseConhecimentoDTO> consultaConhecimentosNaoPublicadosPorOrigem(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        List<BaseConhecimentoDTO> listaConsultaConhecimentoNaoPublicadosPorOrigem = new ArrayList<BaseConhecimentoDTO>();
        try {
            listaConsultaConhecimentoNaoPublicadosPorOrigem = (List<BaseConhecimentoDTO>) this.getDao().consultaConhecimentosNaoPublicadosPorOrigem(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return listaConsultaConhecimentoNaoPublicadosPorOrigem;
    }

    @Override
    public Collection<BaseConhecimentoDTO> consultaConhecimentoQuantitativoEmLista(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        List<BaseConhecimentoDTO> listaConsultaConhecimentoQuantitativoEmLista = new ArrayList<BaseConhecimentoDTO>();
        List<BaseConhecimentoDTO> listaIncidente = new ArrayList<BaseConhecimentoDTO>();
        List<BaseConhecimentoDTO> listaRequisitos = new ArrayList<BaseConhecimentoDTO>();
        List<BaseConhecimentoDTO> listaProblema = new ArrayList<BaseConhecimentoDTO>();
        List<BaseConhecimentoDTO> listaMudanca = new ArrayList<BaseConhecimentoDTO>();
        List<BaseConhecimentoDTO> listaIC = new ArrayList<BaseConhecimentoDTO>();
        List<BaseConhecimentoDTO> listaServico = new ArrayList<BaseConhecimentoDTO>();

        try {
            listaConsultaConhecimentoQuantitativoEmLista = (List<BaseConhecimentoDTO>) this.getDao().consultaConhecimentoQuantitativoEmLista(baseConhecimentoDTO);
            for (final BaseConhecimentoDTO baseConhecimentoDTO2 : listaConsultaConhecimentoQuantitativoEmLista) {
                listaIncidente = (List<BaseConhecimentoDTO>) this.getDao().consultaIncidenteLista(baseConhecimentoDTO2);
                baseConhecimentoDTO2.setListaIncidente(listaIncidente);
                listaRequisitos = (List<BaseConhecimentoDTO>) this.getDao().consultaRequisicaoLista(baseConhecimentoDTO2);
                baseConhecimentoDTO2.setListaRequisitos(listaRequisitos);
                listaProblema = (List<BaseConhecimentoDTO>) this.getDao().consultaProblemaLista(baseConhecimentoDTO2);
                baseConhecimentoDTO2.setListaProblema(listaProblema);
                listaMudanca = (List<BaseConhecimentoDTO>) this.getDao().consultaMudancaLista(baseConhecimentoDTO2);
                baseConhecimentoDTO2.setListaMudanca(listaMudanca);
                listaIC = (List<BaseConhecimentoDTO>) this.getDao().consultaItemConfiguracaoLista(baseConhecimentoDTO2);
                baseConhecimentoDTO2.setListaIC(listaIC);
                listaServico = (List<BaseConhecimentoDTO>) this.getDao().consultaServicoLista(baseConhecimentoDTO2);
                baseConhecimentoDTO2.setListaServico(listaServico);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return listaConsultaConhecimentoQuantitativoEmLista;
    }

    @Override
    public Collection findByServico(final SolicitacaoServicoDTO solicitacaoServicoDto) throws ServiceException, LogicException {
        try {
            return this.getDao().findByServico(solicitacaoServicoDto);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBaseConhecimentoErroConhecidoByPasta(final PastaDTO pasta) throws Exception {
        return this.getDao().listarBaseConhecimentoErroConhecidoByPasta(pasta);
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQ() throws Exception {
        return this.getDao().listarBaseConhecimentoFAQ();
    }

    @Override
    public Collection<BaseConhecimentoDTO> listarBasesConhecimentoPublicadas() throws Exception {
        return this.getDao().listarBasesConhecimentoPublicadas();
    }

    @Override
    public String verificaIdScriptOrientacao(final HashMap mapFields) throws Exception {
        List<BaseConhecimentoDTO> listaBaseConhecimento = null;
        String id = mapFields.get("IDBASECONHECIMENTO").toString().trim();
        if (id == null || id.equals("")) {
            // Campo SCRIPT de Orientação (Base de Conhecimento) não é obrigatorio, por isso passei um valor qualquer para não validar mais.
            id = "campoVazio";
            return id;
        }
        if (UtilStrings.soContemNumeros(id)) {
            final Integer idBaseConhecimento = Integer.parseInt(id);
            listaBaseConhecimento = this.getDao().findByIdBaseConhecimento(idBaseConhecimento);
        } else {
            listaBaseConhecimento = this.getDao().findByBaseConhecimento(id);
        }
        if (listaBaseConhecimento != null && listaBaseConhecimento.size() > 0) {
            return String.valueOf(listaBaseConhecimento.get(0).getIdBaseConhecimento());
        } else {
            return "0";
        }
    }
    
    @Override
    public Page<BaseConhecimentoDTO> listarBaseConhecimentoPortal(final Pageable pageable, final boolean isTotalizacao) throws PersistenceException{
    	return this.getDao().listarBaseConhecimentoPortal(pageable, isTotalizacao, null);
    }
    
    @Override
    public Page<BaseConhecimentoDTO> listarBaseConhecimentoFAQPortal(final Pageable pageable, final boolean isTotalizacao) throws PersistenceException{
    	return this.getDao().listarBaseConhecimentoFAQPortal(pageable, isTotalizacao, null);
    }

	@Override
	public Page<BaseConhecimentoDTO> pesquisaBaseConhecimentoPortal(Pageable pageable, boolean isTotalizacao, String titulo) throws PersistenceException {
    	return this.getDao().listarBaseConhecimentoPortal(pageable, isTotalizacao, titulo);
	}

	@Override
	public Page<BaseConhecimentoDTO> pesquisaBaseConhecimentoFAQPortal(Pageable pageable, boolean isTotalizacao, String titulo) throws PersistenceException {
    	return this.getDao().listarBaseConhecimentoFAQPortal(pageable, isTotalizacao, titulo);
	}

}
