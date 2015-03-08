package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ControleQuestionariosDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemPedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.ControleQuestionariosDao;
import br.com.centralit.citcorpore.integracao.ItemPedidoPortalDAO;
import br.com.centralit.citcorpore.integracao.PedidoPortalDAO;
import br.com.centralit.citcorpore.integracao.PortalDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoQuestionarioDao;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.negocio.RespostaItemQuestionarioServiceBean;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class PortalServiceEjb extends CrudServiceImpl implements PortalService {

    private PortalDao dao;

    @Override
    protected PortalDao getDao() {
        if (dao == null) {
            dao = new PortalDao();
        }
        return dao;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Collection findByCondition(final Integer id) throws ServiceException, Exception {
        return this.getDao().findByCondition(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PortalDTO> findByCondition(final Integer idUsuario, final Integer idItem) throws ServiceException, Exception {
        return this.getDao().findByCondition(idUsuario, idItem);
    }

    @Override
    public Collection<PortalDTO> listByUsuario(final Integer idUsuario) throws Exception {
        return this.getDao().listByUsuario(idUsuario);
    }

    @Override
    public PedidoPortalDTO criarPedidoSolicitacao(PedidoPortalDTO pedidoPortalDTO, final UsuarioDTO usuarioDTO,
            final Collection<SolicitacaoServicoQuestionarioDTO> colecaoRespQuestionario, final Collection<UploadDTO> arquivosUpados) throws ServiceException, Exception {
        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        new SolicitacaoServicoDao();
        final PedidoPortalDAO pedidoPortalDAO = new PedidoPortalDAO();
        final ItemPedidoPortalDAO itemPedidoPortalDAO = new ItemPedidoPortalDAO();
        final ServicoDao servicoDao = new ServicoDao();
        final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();

        final TransactionControler tc = new TransactionControlerImpl(pedidoPortalDAO.getAliasDB());

        final List<ServicoContratoDTO> listaServicos = pedidoPortalDTO.getListaServicoContrato();
        final List<SolicitacaoServicoDTO> listaSolicitacoes = new ArrayList<SolicitacaoServicoDTO>();
        try {
            pedidoPortalDAO.setTransactionControler(tc);
            servicoDao.setTransactionControler(tc);
            itemPedidoPortalDAO.setTransactionControler(tc);

            tc.start();

            pedidoPortalDTO = (PedidoPortalDTO) pedidoPortalDAO.create(pedidoPortalDTO);

            double valorTotal = 0.0;
            if (listaServicos != null) {
                for (final ServicoContratoDTO servicoContratoDTO : listaServicos) {
                    final ServicoContratoDTO servicoContratoDTOAux = (ServicoContratoDTO) servicoContratoDao.restore(servicoContratoDTO);

                    SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
                    final ItemPedidoPortalDTO itemPedidoPortalDTO = new ItemPedidoPortalDTO();
                    valorTotal += servicoContratoDTO.getValorServico() == null ? 0.0 : servicoContratoDTO.getValorServico();

                    solicitacaoServicoDto.setDescricao(UtilStrings.getParameter(servicoContratoDTO.getDescricao() + "<br>"
                            + (!servicoContratoDTO.getObservacaoPortal().equalsIgnoreCase("undefined") ? servicoContratoDTO.getObservacaoPortal() : "")));

                    /* Solicitação */
                    solicitacaoServicoDto.setIdContrato(servicoContratoDTOAux.getIdContrato());
                    solicitacaoServicoDto.setIdServico(servicoContratoDTOAux.getIdServico());
                    solicitacaoServicoDto.setEnviaEmailCriacao("S");
                    /* Restaurando o tipo demanda serviço */
                    ServicoDTO servicoDto = new ServicoDTO();
                    servicoDto.setIdServico(servicoContratoDTOAux.getIdServico());
                    servicoDto = (ServicoDTO) servicoDao.restore(servicoDto);
                    solicitacaoServicoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());

                    if (servicoContratoDTOAux.getIdGrupoExecutor() != null) {
                        solicitacaoServicoDto.setIdGrupoAtual(servicoContratoDTOAux.getIdGrupoExecutor());
                    }

                    this.setarValoresPadrao(solicitacaoServicoDto, usuarioDTO);
                    this.relacionaImpactoUrgencia(solicitacaoServicoDto);

                    solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto, tc, true, true, true);

                    if (arquivosUpados != null && arquivosUpados.size() > 0) {

                        final Integer idEmpresa = 1;

                        this.gravaInformacoesGED(arquivosUpados, idEmpresa, solicitacaoServicoDto, null, solicitacaoServicoDto.getIdServico());

                    }

                    /*
                     * Para tratar a iniciativa 483, foi adicionado o vinculo de questionario com a solicitação de servico dentro do portal
                     * "colecaoRespQuestionario" parametro recuperado da sessão.
                     */
                    if (colecaoRespQuestionario != null && colecaoRespQuestionario.size() > 0) {

                        for (final SolicitacaoServicoQuestionarioDTO soQuestionarioDTO : colecaoRespQuestionario) {

                            if (soQuestionarioDTO.getIdServico().equals(servicoContratoDTO.getIdServico())) {

                                solicitacaoServicoDto.setSolicitacaoServicoQuestionarioDTO(soQuestionarioDTO);

                                this.atualizaInformacoesQuestionario(solicitacaoServicoDto, tc);

                            }

                        }

                    }
                    listaSolicitacoes.add(solicitacaoServicoDto);

                    itemPedidoPortalDTO.setIdPedidoPortal(pedidoPortalDTO.getIdPedidoPortal());;
                    itemPedidoPortalDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                    itemPedidoPortalDTO.setValor(servicoContratoDTO.getValorServico() == null ? 0.0 : servicoContratoDTO.getValorServico());
                    itemPedidoPortalDAO.create(itemPedidoPortalDTO);

                }
            }
            /* Pedido */
            pedidoPortalDTO.setPrecoTotal(valorTotal);
            pedidoPortalDAO.update(pedidoPortalDTO);

            pedidoPortalDTO.setListaSolicitacoes(listaSolicitacoes);

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return pedidoPortalDTO;
    }

    private void setarValoresPadrao(final SolicitacaoServicoDTO solicitacaoServicoDto, final UsuarioDTO usuario) throws Exception {
        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        final String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");
        final Integer idOrigem = ORIGEM.trim().equalsIgnoreCase("") ? 0 : Integer.valueOf(ORIGEM.trim());
        /* Inicializações obrigatórias */
        solicitacaoServicoDto.setIdOrigem(idOrigem);
        solicitacaoServicoDto.setSituacao("EmAndamento");
        solicitacaoServicoDto.setRegistroexecucao("");
        solicitacaoServicoDto.setIdSolicitante(usuario.getIdEmpregado());

        /* Setando as informações do usuário */
        solicitacaoServicoDto.setUsuarioDto(usuario);
        solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());

        /* Restaurando as informações do empregado */
        final EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuario.getIdEmpregado());
        solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());

        /* Setando as informações de contato */
        solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
        solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
        solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
    }

    @Override
    public void relacionaImpactoUrgencia(final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
        final ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(),
                solicitacaoServicoDto.getIdServico());

        if (servicoContratoDto != null) {
            final AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
            AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
            if (acordoNivelServicoDto == null) {
                // Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja vinculado ao servicocontrato.
                final AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(
                        AcordoServicoContratoService.class, null);
                final AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
                if (acordoServicoContratoDTO != null) {
                    // Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
                    acordoNivelServicoDto = new AcordoNivelServicoDTO();
                    acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
                    acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao().restore(acordoNivelServicoDto);

                    if (acordoNivelServicoDto != null) {
                        if (acordoNivelServicoDto.getImpacto() != null) {
                            solicitacaoServicoDto.setImpacto(acordoNivelServicoDto.getImpacto());
                        } else {
                            solicitacaoServicoDto.setImpacto("B");
                        }

                        if (acordoNivelServicoDto.getUrgencia() != null) {
                            solicitacaoServicoDto.setUrgencia(acordoNivelServicoDto.getUrgencia());
                        } else {
                            solicitacaoServicoDto.setUrgencia("B");
                        }
                    }
                }
            }
        } else {
            solicitacaoServicoDto.setImpacto("B");
            solicitacaoServicoDto.setUrgencia("B");
        }
    }

    public void atualizaInformacoesQuestionario(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
        final ControleQuestionariosDao controleQuestionariosDao = new ControleQuestionariosDao();
        final SolicitacaoServicoQuestionarioDao solicitacaoServicoQuestionarioDao = new SolicitacaoServicoQuestionarioDao();
        final RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
        final RespostaItemQuestionarioServiceBean respostaItemQuestionarioServiceBean = new RespostaItemQuestionarioServiceBean();

        controleQuestionariosDao.setTransactionControler(tc);
        solicitacaoServicoQuestionarioDao.setTransactionControler(tc);
        respostaItemDao.setTransactionControler(tc);

        final SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDto = solicitacaoServicoDto.getSolicitacaoServicoQuestionarioDTO();
        if (solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario() != null && solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario().intValue() > 0) {
            solicitacaoServicoQuestionarioDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            solicitacaoServicoQuestionarioDto.setDataHoraGrav(UtilDatas.getDataHoraAtual());
            solicitacaoServicoQuestionarioDao.updateNotNull(solicitacaoServicoQuestionarioDto);

            respostaItemDao.deleteByIdIdentificadorResposta(solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario());
            respostaItemQuestionarioServiceBean.processCollection(tc, solicitacaoServicoQuestionarioDto.getColValores(), solicitacaoServicoQuestionarioDto.getColAnexos(),
                    solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario(), null);
        } else {
            ControleQuestionariosDTO controleQuestionariosDto = new ControleQuestionariosDTO();
            controleQuestionariosDto = (ControleQuestionariosDTO) controleQuestionariosDao.create(controleQuestionariosDto);

            solicitacaoServicoQuestionarioDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            solicitacaoServicoQuestionarioDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
            solicitacaoServicoQuestionarioDto.setIdTarefa(solicitacaoServicoDto.getIdTarefa());
            if (solicitacaoServicoQuestionarioDto.getDataQuestionario() == null) {
                solicitacaoServicoQuestionarioDto.setDataQuestionario(UtilDatas.getDataAtual());
            }
            solicitacaoServicoQuestionarioDto.setSituacao("E");
            solicitacaoServicoQuestionarioDto.setIdSolicitacaoQuestionario(controleQuestionariosDto.getIdControleQuestionario());
            solicitacaoServicoQuestionarioDto.setDataHoraGrav(UtilDatas.getDataHoraAtual());

            if (solicitacaoServicoQuestionarioDto.getIdQuestionario() != null && solicitacaoServicoQuestionarioDto.getIdQuestionario() != 0) {

                final SolicitacaoServicoQuestionarioDTO solQuestionariosDTO = (SolicitacaoServicoQuestionarioDTO) solicitacaoServicoQuestionarioDao
                        .create(solicitacaoServicoQuestionarioDto);

                final Integer idIdentificadorResposta = solQuestionariosDTO.getIdSolicitacaoQuestionario();
                respostaItemQuestionarioServiceBean.processCollection(tc, solQuestionariosDTO.getColValores(), solQuestionariosDTO.getColAnexos(), idIdentificadorResposta, null);
            }
        }
    }

    private void gravaInformacoesGED(final Collection colArquivosUpload, final Integer idEmpresa, final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc,
            final Integer idServico) throws Exception {

        // Setando a transaction no GED
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        if (tc != null) {
            controleGEDDao.setTransactionControler(tc);
        }

        String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "");
        if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = "";
        }

        if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
        }

        if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = "/ged";
        }
        String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
        if (PRONTUARIO_GED_INTERNO == null) {
            PRONTUARIO_GED_INTERNO = "S";
        }
        String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }
        String pasta = "";
        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
            pasta = controleGEDDao.getProximaPastaArmazenar();
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

        for (final Iterator it = colArquivosUpload.iterator(); it.hasNext();) {

            final UploadDTO uploadDTO = (UploadDTO) it.next();

            if (uploadDTO.getIdLinhaPai().equals(idServico)) {

                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_SOLICITACAOSERVICO);

                controleGEDDTO.setId(solicitacaoServicoDto.getIdSolicitacaoServico());

                controleGEDDTO.setDataHora(UtilDatas.getDataAtual());

                controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());

                controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));

                controleGEDDTO.setPasta(pasta);

                controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

                if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) {
                    continue;
                }

                if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se

                    controleGEDDTO.setPathArquivo(uploadDTO.getPath());
                } else {
                    controleGEDDTO.setPathArquivo(null);
                }
                controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

                if (controleGEDDTO != null) {
                    uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
                }
                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se

                    if (controleGEDDTO != null) {
                        try {
                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                    + Util.getFileExtension(uploadDTO.getNameFile()));
                            CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED()
                                    + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            arquivo.delete();
                        } catch (final Exception e) {

                        }

                    }
                }
            }

        }
    }

    @Override
    public boolean existeQuestionario(final Integer idServico) throws Exception {
        return this.getDao().existeQuestionario(idServico);
    }

    @Override
    public boolean existeQuestionarioServico(final Integer idServico) throws ServiceException, Exception {
        return this.getDao().existeQuestionarioServico(idServico);
    }

    @Override
    public Integer obterIdQuestionarioServico(final Integer idServicoCatalogo) throws ServiceException {
        return this.getDao().obterIdQuestionarioServico(idServicoCatalogo);
    }

}
