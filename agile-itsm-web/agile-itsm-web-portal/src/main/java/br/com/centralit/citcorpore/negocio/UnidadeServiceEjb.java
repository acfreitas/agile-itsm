package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.integracao.ContratosUnidadesDAO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.EnderecoDao;
import br.com.centralit.citcorpore.integracao.LocalidadeUnidadeDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.integracao.UnidadesAccServicosDao;
import br.com.centralit.citcorpore.util.Arvore;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.geo.GeoUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
public class UnidadeServiceEjb extends CrudServiceImpl implements UnidadeService {

    private static final Logger LOGGER = Logger.getLogger(UnidadeServiceEjb.class.getName());

    private UnidadeDao dao;
    private EnderecoDao enderecoDAO;

    @Override
    protected UnidadeDao getDao() {
        if (dao == null) {
            dao = new UnidadeDao();
        }
        return dao;
    }

    private EnderecoDao getEnderecoDAO() {
        if (enderecoDAO == null) {
            enderecoDAO = new EnderecoDao();
        }
        return enderecoDAO;
    }

    private UnidadesAccServicosDao unidadesAccServicosDAO;

    private UnidadesAccServicosDao getUnidadesAccServicosDAO() {
        if (unidadesAccServicosDAO == null) {
            unidadesAccServicosDAO = new UnidadesAccServicosDao();
        }
        return unidadesAccServicosDAO;
    }

    public void setUnidadesAccServicosDao(final UnidadesAccServicosDao unidadesAccServicosDao) {
        unidadesAccServicosDAO = unidadesAccServicosDao;
    }

    /** Bean de UnidadeDTO. */
    private UnidadeDTO unidadeDTO;

    private UnidadeDTO getUnidadeDTO() {
        return unidadeDTO;
    }

    public Class<UnidadeDTO> getBean() {
        return UnidadeDTO.class;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final UnidadeDao unidadeDao = this.getDao();
        final ContratosUnidadesDAO contratosUnidadesDAO = new ContratosUnidadesDAO();
        final LocalidadeUnidadeDAO localidadeUnidadeDao = new LocalidadeUnidadeDAO();
        final EnderecoDao enderecoDao = new EnderecoDao();

        UnidadeDTO unidade = (UnidadeDTO) model;
        final ContratosUnidadesDTO contratosUnidadesDTO = new ContratosUnidadesDTO();
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        unidade.setDataInicio(UtilDatas.getDataAtual());

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            unidadeDao.setTransactionControler(tc);
            contratosUnidadesDAO.setTransactionControler(tc);
            localidadeUnidadeDao.setTransactionControler(tc);
            enderecoDao.setTransactionControler(tc);
            tc.start();

            enderecoDTO.setLogradouro(unidade.getLogradouro());
            enderecoDTO.setComplemento(unidade.getComplemento());
            enderecoDTO.setNumero(unidade.getNumero());
            enderecoDTO.setBairro(unidade.getBairro());
            enderecoDTO.setCep(unidade.getCep());
            enderecoDTO.setIdCidade(unidade.getIdCidade());
            enderecoDTO.setIdPais(unidade.getIdPais());
            enderecoDTO.setIdUf(unidade.getIdUf());
            enderecoDTO.setLatitude(unidade.getLatitude());
            enderecoDTO.setLongitude(unidade.getLongitude());

            enderecoDTO = (EnderecoDTO) enderecoDao.create(enderecoDTO);

            if (enderecoDTO.getIdEndereco() != null) {
                unidade.setIdEndereco(enderecoDTO.getIdEndereco());
            }

            if (unidade.getAceitaEntregaProduto() == null) {
                unidade.setAceitaEntregaProduto("N");
            }

            unidade = (UnidadeDTO) unidadeDao.create(unidade);

            this.criarEAssociarServicoAUnidade(unidade);

            if (unidade.getIdContrato() != null) {
                for (int i = 0; i < unidade.getIdContrato().length; i++) {
                    contratosUnidadesDTO.setIdUnidade(unidade.getIdUnidade());
                    contratosUnidadesDTO.setIdContrato(unidade.getIdContrato()[i]);
                    if (contratosUnidadesDTO.getIdContrato() != null) {
                        contratosUnidadesDAO.create(contratosUnidadesDTO);
                    }
                }
            }

            if (unidade.getListaDeLocalidade() != null) {
                if (unidade.getIdUnidade() != null && unidade.getIdUnidade() != 0) {
                    for (final LocalidadeUnidadeDTO localidadeUnidadeDto : unidade.getListaDeLocalidade()) {
                        final LocalidadeUnidadeDTO localidade = new LocalidadeUnidadeDTO();
                        localidade.setIdUnidade(unidade.getIdUnidade());
                        localidade.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
                        localidade.setDataInicio(UtilDatas.getDataAtual());
                        localidadeUnidadeDao.create(localidade);
                    }
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.rollbackTransaction(tc, e);
        }

        return this.getUnidadeDTO();
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final UnidadeDao unidadeDAO = this.getDao();

        final UnidadeDTO unidade = (UnidadeDTO) model;
        final ContratosUnidadesDAO contratosUnidadesDAO = new ContratosUnidadesDAO();
        final LocalidadeUnidadeDAO localidadeUnidadeDAO = new LocalidadeUnidadeDAO();
        final ContratosUnidadesDTO contratosUnidadesDTO = new ContratosUnidadesDTO();

        final EnderecoDao enderecoDAO = this.getEnderecoDAO();

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        final TransactionControler transactionControler = new TransactionControlerImpl(unidadeDAO.getAliasDB());

        try {
            unidadeDAO.setTransactionControler(transactionControler);
            contratosUnidadesDAO.setTransactionControler(transactionControler);
            localidadeUnidadeDAO.setTransactionControler(transactionControler);
            enderecoDAO.setTransactionControler(transactionControler);
            transactionControler.start();

            enderecoDTO.setLogradouro(unidade.getLogradouro());
            enderecoDTO.setComplemento(unidade.getComplemento());
            enderecoDTO.setNumero(unidade.getNumero());
            enderecoDTO.setBairro(unidade.getBairro());
            enderecoDTO.setCep(unidade.getCep());
            enderecoDTO.setIdPais(unidade.getIdPais());
            enderecoDTO.setIdUf(unidade.getIdUf());
            enderecoDTO.setIdCidade(unidade.getIdCidade());
            enderecoDTO.setLatitude(unidade.getLatitude());
            enderecoDTO.setLongitude(unidade.getLongitude());

            if (unidade.getIdEndereco() != null) {
                enderecoDTO.setIdEndereco(unidade.getIdEndereco());
                enderecoDAO.update(enderecoDTO);
            } else {
                enderecoDTO = (EnderecoDTO) enderecoDAO.create(enderecoDTO);

                if (enderecoDTO.getIdEndereco() != null) {
                    unidade.setIdEndereco(enderecoDTO.getIdEndereco());
                }
            }

            if (unidade.getAceitaEntregaProduto() == null) {
                unidade.setAceitaEntregaProduto("N");
            }

            String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
            if (UNIDADE_VINC_CONTRATOS == null) {
                UNIDADE_VINC_CONTRATOS = "N";
            }
            if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
                contratosUnidadesDAO.deleteByIdUnidade(unidade.getIdUnidade());
                if (unidade.getIdContrato() != null) {
                    for (int i = 0; i < unidade.getIdContrato().length; i++) {
                        contratosUnidadesDTO.setIdUnidade(unidade.getIdUnidade());
                        contratosUnidadesDTO.setIdContrato(unidade.getIdContrato()[i]);
                        if (contratosUnidadesDTO.getIdContrato() != null) {
                            contratosUnidadesDAO.create(contratosUnidadesDTO);
                        }
                    }
                }
            }

            // Obtendo o serviço.
            final LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

            // Verificando se o serviço existe.
            if (localidadeUnidadeService != null) {
                // Excluindo todas as associacoes entre a unidade e localidades.
                localidadeUnidadeService.deleteByIdUnidade(unidade.getIdUnidade());

                // Recuperando as localidades informadas pelo usuário.
                if (unidade.getListaDeLocalidade() != null) {
                    // Percorrendo cada localidade informada.
                    for (final LocalidadeUnidadeDTO localidadeUnidadeDto : unidade.getListaDeLocalidade()) {
                        final LocalidadeUnidadeDTO localidade = new LocalidadeUnidadeDTO();
                        localidade.setIdUnidade(unidade.getIdUnidade());
                        localidade.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
                        localidade.setDataInicio(UtilDatas.getDataAtual());

                        // Criando a associacao entre a unidade e a localidade no BD.
                        localidadeUnidadeDAO.create(localidade);
                    }
                }
            }

            this.criarEAssociarServicoAUnidade(unidade);

            transactionControler.commit();
            transactionControler.close();
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        super.update(unidade);
    }

    /**
     * Associa serviços a unidade em questão.
     *
     * @throws Exception
     * @author rodrigo.oliveira
     */
    private void criarEAssociarServicoAUnidade(final UnidadeDTO unidade) throws Exception {
        final UnidadesAccServicosDTO unidadesAccServicosDTO = new UnidadesAccServicosDTO();

        if (unidade.getServicos() != null && !unidade.getServicos().isEmpty()) {
            for (int i = 0; i < unidade.getServicos().size(); i++) {
                final Integer idServico = ((UnidadesAccServicosDTO) unidade.getServicos().get(i)).getIdServico();
                if (!this.getUnidadesAccServicosDAO().existeAssociacaoComUnidade(idServico, unidade.getIdUnidade())) {
                    unidadesAccServicosDTO.setIdServico(idServico);
                    unidadesAccServicosDTO.setIdUnidade(unidade.getIdUnidade());
                    this.getUnidadesAccServicosDAO().create(unidadesAccServicosDTO);
                }
            }
        }
    }

    @Override
    public Collection findByIdUnidade() throws Exception {
        try {
            return this.getDao().findByIdUnidade();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdEmpregado() throws Exception {
        try {
            return this.getDao().findByIdEmpregado();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listHierarquia() throws Exception {
        final Collection colFinal = new ArrayList();
        try {
            final Collection col = this.getDao().findSemPai();
            if (col != null) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
                    unidadeDTO.setNivel(0);
                    colFinal.add(unidadeDTO);
                    final Collection colAux = this.getCollectionHierarquia(unidadeDTO.getIdUnidade(), 0);
                    if (colAux != null && colAux.size() > 0) {
                        colFinal.addAll(colAux);
                    }
                }
            }
            return colFinal;
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public Collection getCollectionHierarquia(final Integer idUnidade, final Integer nivel) throws Exception {
        final Collection col = this.getDao().findByIdPai(idUnidade);
        final Collection colFinal = new ArrayList();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
                unidadeDTO.setNivel(nivel + 1);
                colFinal.add(unidadeDTO);
                final Collection colAux = this.getCollectionHierarquia(unidadeDTO.getIdUnidade(), unidadeDTO.getNivel());
                if (colAux != null && colAux.size() > 0) {
                    colFinal.addAll(colAux);
                }
            }
        }
        return colFinal;
    }

    @Override
    public boolean jaExisteUnidadeComMesmoNome(final UnidadeDTO unidade) {
        final String nome = unidade.getNome();
        final Integer idunidade = unidade.getIdUnidade();

        final List<Condition> condicoes = new ArrayList<>();
        condicoes.add(new Condition("nome", "=", nome));
        condicoes.add(new Condition("dataFim", "is", null));
        condicoes.add(new Condition("idUnidade", "<>", idunidade == null ? 0 : idunidade.intValue()));
        Collection retorno = null;

        try {
            retorno = this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        return retorno == null ? false : true;
    }

    @Override
    public void deletarUnidade(final IDto model, final DocumentHTML document, final HttpServletRequest request) throws ServiceException, Exception {
        final UnidadeDTO unidadeDTO = (UnidadeDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ContratosUnidadesDAO contratosUnidadesDAO = new ContratosUnidadesDAO();
        final EmpregadoDao empregadoDAO = new EmpregadoDao();
        final UnidadeDao unidadeDAO = this.getDao();
        final LocalidadeUnidadeDAO localidadeUnidadeDAO = new LocalidadeUnidadeDAO();
        final SolicitacaoServicoDao solicitacaoDAO = new SolicitacaoServicoDao();

        try {
            empregadoDAO.setTransactionControler(tc);
            unidadeDAO.setTransactionControler(tc);
            contratosUnidadesDAO.setTransactionControler(tc);
            localidadeUnidadeDAO.setTransactionControler(tc);
            solicitacaoDAO.setTransactionControler(tc);
            if (empregadoDAO.verificarSeUnidadePossuiEmpregado(unidadeDTO.getIdUnidade()) || unidadeDAO.verificarSeUnidadePossuiFilho(unidadeDTO.getIdUnidade())) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
                return;
            }
            if (solicitacaoDAO.verificarExistenciaDeUnidade(unidadeDTO.getIdUnidade())) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
                return;
            }
            tc.start();
            unidadeDTO.setDataFim(UtilDatas.getDataAtual());
            unidadeDAO.update(model);
            contratosUnidadesDAO.deleteByIdUnidade(unidadeDTO.getIdUnidade());
            if (unidadeDTO.getListaDeLocalidade() != null) {
                for (final LocalidadeUnidadeDTO localidadeUnidadeDto : unidadeDTO.getListaDeLocalidade()) {
                    localidadeUnidadeDAO.updateDataFim(localidadeUnidadeDto);
                }
            }
            document.alert(UtilI18N.internacionaliza(request, "MSG07"));
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void restaurarGridServicos(final DocumentHTML document, final Collection<UnidadesAccServicosDTO> servicos) {
        if (servicos != null && !servicos.isEmpty()) {
            int count = 0;
            document.executeScript("countServico = 0");

            for (final UnidadesAccServicosDTO servicosBean : servicos) {
                count++;
                document.executeScript("restoreRow()");
                document.executeScript("seqSelecionada = " + count);

                String nomeServico = servicosBean.getNomeServico() != null ? servicosBean.getNomeServico() : "";
                String descricaoServico = servicosBean.getDetalheServico() != null ? servicosBean.getDetalheServico() : "";

                if (nomeServico != null) {
                    nomeServico = nomeServico.replaceAll("'", "");
                }

                if (descricaoServico != null) {
                    descricaoServico = descricaoServico.replaceAll("'", "");
                }

                document.executeScript("setRestoreServico('" + servicosBean.getIdServico() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(nomeServico) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(descricaoServico) + "')");
            }
            document.executeScript("exibeGrid()");
        } else {
            document.executeScript("ocultaGrid()");
        }
    }

    @Override
    public Collection findById(final Integer idUnidade) throws Exception {
        try {
            return this.getDao().findById(idUnidade);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listHierarquiaMultiContratos(final Integer idContrato) throws Exception {
        final Collection colFinal = new ArrayList();
        try {
            final Collection col = this.getDao().findSemPaiMultContrato(idContrato);
            if (col != null) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
                    unidadeDTO.setNivel(0);
                    colFinal.add(unidadeDTO);
                    final Collection colAux = this.getCollectionHierarquia(unidadeDTO.getIdUnidade(), 0);
                    if (colAux != null && colAux.size() > 0) {
                        colFinal.addAll(colAux);
                    }
                }
            }
            return colFinal;
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listarAtivasPorContrato(final Integer idContrato) {
        try {
            return this.getDao().listarAtivasPorContrato(idContrato);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return new ArrayList<UnidadeDTO>();
    }

    @Override
    public Collection<UnidadeDTO> listUnidadePorContrato(final Integer idcontrato) throws Exception {
        try {
            return this.getDao().listUnidadePorContrato(idcontrato);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByNomeEcontrato(final String nome, final Integer idContrato, final Integer limite) throws Exception {
        try {
            final Collection<UnidadeDTO> arvore = this.getDao().findByNomeEcontrato(nome, idContrato, limite);
            /**
             * Seleciona as filhas da existente, montando uma árvore
             */
            if (idContrato != null && idContrato != 0 && idContrato != -1) {
                for (int i = 0; i < arvore.size(); i++) {
                    final Object aux = arvore.toArray()[i];
                    arvore.addAll(this.getDao().findByIdPai(((UnidadeDTO) aux).getIdUnidade()));
                }
            }
            return arvore;
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdEcontrato(final Integer idUnidade, final Integer idContrato) throws Exception {
        try {
            return this.getDao().findByIdEcontrato(idUnidade, idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UnidadeDTO> recuperaHierarquiaUnidade(final UnidadeDTO unidadeDTO) throws Exception {
        try {
            return this.getDao().recuperaHierarquiaUnidade(unidadeDTO);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String retornaNomeUnidadeByID(final Integer id) throws Exception {
        return this.getDao().retornaNomeUnidadeByID(id);
    }

    @Override
    public void updateCoordenadas(final String locale, final UnidadeDTO unidade) throws Exception {
        final Double latitude = unidade.getLatitude();
        final Double longitude = unidade.getLongitude();
        if (latitude == null || longitude == null) {
            throw new ServiceException(this.i18nMessage(locale, "rest.service.mobile.v2.null.coordinates"));
        }

        if (!GeoUtils.validCoordinates(latitude, longitude)) {
            throw new ServiceException(this.i18nMessage(locale, "rest.service.mobile.v2.invalid.coordinates"));
        }

        final Integer idUnidade = unidade.getIdUnidade();

        if (idUnidade == null) {
            throw new ServiceException(this.i18nMessage(locale, "rest.service.mobile.v2.unit.id.null"));
        }

        final UnidadeDTO toFind = new UnidadeDTO();
        toFind.setIdUnidade(idUnidade);

        TransactionControler tc = null;
        try {
            final UnidadeDTO toUpdate = (UnidadeDTO) this.getDao().restore(toFind);
            if (toUpdate == null) {
                throw new ServiceException(this.i18nMessage(locale, "rest.service.mobile.v2.unit.notfound", idUnidade));
            }

            final UnidadeDao unidadeDAO = this.getDao();
            final EnderecoDao enderecoDAO = this.getEnderecoDAO();
            tc = new TransactionControlerImpl(unidadeDAO.getAliasDB());

            unidadeDAO.setTransactionControler(tc);
            enderecoDAO.setTransactionControler(tc);

            tc.start();

            final EnderecoDTO endereco = new EnderecoDTO();
            final Integer idEndereco = toUpdate.getIdEndereco();
            if (idEndereco != null && idEndereco > 0) {
                endereco.setIdEndereco(idEndereco);
                final EnderecoDTO addressToUpdate = (EnderecoDTO) enderecoDAO.restore(endereco);

                addressToUpdate.setLatitude(latitude);
                addressToUpdate.setLongitude(longitude);

                enderecoDAO.update(addressToUpdate);
            } else {
                endereco.setLatitude(latitude);
                endereco.setLongitude(longitude);

                final EnderecoDTO saved = (EnderecoDTO) enderecoDAO.create(endereco);
                toUpdate.setIdEndereco(saved.getIdEndereco());

                unidadeDAO.update(toUpdate);
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Arvore obtemArvoreUnidades(final String nome, final Integer idContrato, final Integer idUnidadeColaborador, final String tipoHierarquia, final Integer limite)
            throws ServiceException, Exception {
        Collection<UnidadeDTO> listaResultado;
        final Arvore arvore = new Arvore();
        List<UnidadeDTO> hierarquiaUnidade;

        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

        if (tipoHierarquia.equals("3")) { // Hierarquia que monta apenas a árvore da unidade e os nós que estão superiores a ela
            // Obter a unidade diretamente, assim, ganhamos em performance
            if (idContrato == null || idContrato == 0 || idContrato == -1) {
                final UnidadeDTO aux = (UnidadeDTO) this.getDao().findById(idUnidadeColaborador).toArray()[0];
                listaResultado = unidadeService.recuperaHierarquiaUnidade(aux);
            } else {
                listaResultado = null;
                final UnidadeDTO unidadeColaborador = (UnidadeDTO) this.getDao().findById(idUnidadeColaborador).toArray()[0];
                // Pegar pai raiz
                UnidadeDTO paiRaiz = this.getPaiRaiz(idUnidadeColaborador);
                // Checar se pai esta no contrato
                if (paiRaiz != null) {
                    paiRaiz = this.getDao().checkIsInContrato(paiRaiz.getIdUnidade(), idContrato);
                }
                // retornarHirearquia de unidade
                if (paiRaiz != null) {
                    listaResultado = unidadeService.recuperaHierarquiaUnidade(unidadeColaborador);
                }
            }
            if (listaResultado != null) {
                for (final UnidadeDTO unidadeh : listaResultado) {
                    arvore.adicionaNo(unidadeh.getIdUnidade(), unidadeh.getNome(), unidadeh.getIdUnidadePai());
                }
            }
        } else {
            // Implementa a pesquisa autoComplete ou não filtra por nome se ele não for informado
            listaResultado = unidadeService.findByNomeEcontrato(nome, idContrato, limite);
        }
        // Alimentando a árvore apartir da pesquisa
        if (listaResultado != null) {
            for (final UnidadeDTO unidadeDTO : listaResultado) {
                if (unidadeDTO.getIdUnidade() != null) {
                    hierarquiaUnidade = unidadeService.recuperaHierarquiaUnidade(unidadeDTO); // É importante que a ordenação dos nós desta lista seja mantida seguindo a orientação
                                                                                              // de nós pai para os nós filhos
                    for (final UnidadeDTO unidadeh : hierarquiaUnidade) {
                        arvore.adicionaNo(unidadeh.getIdUnidade(), unidadeh.getNome(), unidadeh.getIdUnidadePai());
                    }
                }
            }
        }

        switch (tipoHierarquia) {
        case "1":
            arvore.geraListaSemRestricao();
            break;
        case "2":
            arvore.geraListaUnidadeEsuasFilhas(idUnidadeColaborador);
            break;
        case "3":
            arvore.geraListaSemRestricao(); // Não usei o método geraListaUnidadeEsuperiores porque a SQL já foi restringida logo acima.
            break;
        default:
            arvore.geraListaSemRestricao();
        }
        return arvore;
    }

    public UnidadeDTO getPaiRaiz(final Integer idUnidade) throws ServiceException, Exception {
        UnidadeDTO aux = (UnidadeDTO) this.getDao().findById(idUnidade).toArray()[0];
        while (aux.getIdUnidadePai() != null) {
            aux = this.getDao().retornaUnidadePai(aux);
        }
        return aux;
    }

}
