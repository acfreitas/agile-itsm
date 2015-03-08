package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosUnidadesService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.TipoUnidadeService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadesAccServicosService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.geo.GeoUtils;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Unidade extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        this.preencherComboTipoUnidade(document, request, response);
        this.preencherComboUnidadePai(document, request, response);

        document.getElementById("divListaContratos").setVisible(false);
        String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
        if (UNIDADE_VINC_CONTRATOS == null) {
            UNIDADE_VINC_CONTRATOS = "N";
        }
        if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
            final ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
            final FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            final Collection colContratos = contratoService.list();
            String bufferContratos = "<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.acessoAosContratos") + ":</b> <br><table>";
            if (colContratos != null) {
                for (final Iterator it = colContratos.iterator(); it.hasNext();) {
                    final ContratoDTO contratoDto = (ContratoDTO) it.next();
                    if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
                        String nomeCliente = "";
                        String nomeForn = "";
                        ClienteDTO clienteDto = new ClienteDTO();
                        clienteDto.setIdCliente(contratoDto.getIdCliente());
                        clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
                        if (clienteDto != null) {
                            nomeCliente = clienteDto.getNomeRazaoSocial();
                        }
                        FornecedorDTO fornecedorDto = new FornecedorDTO();
                        fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
                        fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                        if (fornecedorDto != null) {
                            nomeForn = fornecedorDto.getRazaoSocial();
                        }

                        String situacao = "";
                        if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
                            situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoAtiva");
                        }
                        if (contratoDto.getSituacao().equalsIgnoreCase("C")) {
                            situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoCancelado");
                        }
                        if (contratoDto.getSituacao().equalsIgnoreCase("F")) {
                            situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoFinalizado");
                        }
                        if (contratoDto.getSituacao().equalsIgnoreCase("P")) {
                            situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoParalisado");
                        }

                        bufferContratos += "<tr>";
                        bufferContratos += "<td>";
                        bufferContratos += "<input type='checkbox' name='idContrato' id='idContrato_" + contratoDto.getIdContrato() + "' value='0" + contratoDto.getIdContrato()
                                + "' /> Número: " + contratoDto.getNumero() + " de "
                                + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - "
                                + nomeForn + ") - " + situacao;
                        bufferContratos += "</td>";
                        bufferContratos += "</tr>";
                    }
                }
            }
            bufferContratos += "</table>";
            document.getElementById("fldListaContratos").setInnerHTML(bufferContratos);
            document.getElementById("divListaContratos").setVisible(true);
        }

        document.focusInFirstActivateField(null);

        this.preencherComboPais(document, request, response);
    }

    /**
     * Preenche a combo de 'TipoUnidade' do formulário HTML com base na lista recuperada do banco. Obs.: Este preenchimento disconsidera itens com data final, ou seja, inativos.
     * DEVE haver uma combo
     * com id='idTipoUnidade' no documento HTML. Esse elemento será recuperado pelo framework e o tratamento começa a partir dai.
     */
    public void preencherComboTipoUnidade(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final TipoUnidadeService tipoUnidadeService = (TipoUnidadeService) ServiceLocator.getInstance().getService(TipoUnidadeService.class, null);
        final HTMLSelect comboTipoUnidade = document.getSelectById("idTipoUnidade");
        final List<TipoUnidadeDTO> tipos = (List) tipoUnidadeService.list();

        this.inicializaCombo(comboTipoUnidade, request);
        for (final TipoUnidadeDTO tipo : tipos) {
            if (tipo.getDataFim() == null) {
                comboTipoUnidade.addOption(tipo.getIdTipoUnidade().toString(), Util.tratarAspasSimples(Util.retiraBarraInvertida(tipo.getNomeTipoUnidade().trim())));
            }
        }
    }

    /**
     * Preenche a combo de 'UnidadePai' do formulário HTML com base na lista recuperada do banco. Obs.: Este preenchimento disconsidera itens com data final, ou seja, inativos.
     * Obs2.: Ele recupera o
     * getBean() para verificar se há um item ativo, se houver significa que houve restore, nesse caso, ao preencher a lista, ele disconsidera o item trazido do banco que for igual
     * ao item ativo para
     * evitar que o usuário possa cadastrar uma unidade sendo unidadePai dela mesma. DEVE haver uma combo com id='idTipoUnidade' no documento HTML. Esse elemento será recuperado
     * pelo framework e o
     * tratamento começa a partir dai.
     */
    public void preencherComboUnidadePai(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HTMLSelect comboUnidadePai = document.getSelectById("idUnidadePai");
        final List<UnidadeDTO> unidades = (List) this.getUnidadeService().listHierarquia();

        this.inicializaCombo(comboUnidadePai, request);

        // getBean para saber se já existe um bean carregado (em caso de restore)
        final UnidadeDTO unidadeRestore = (UnidadeDTO) document.getBean();

        for (final UnidadeDTO unidade : unidades) {
            // se existir dataFim, está inativo, então não entra na combo.
            if (unidade.getDataFim() == null) {
                /*
                 * se houver um bean carregado(restore), comparo se o item da lista do banco Ã© igual a esse bean, se for ele não pode ser adicionado Ã  lista, se não o usuário
                 * poderá colocar a
                 * unidade como pai dela mesma.
                 */
                if (unidadeRestore.getIdUnidade() == null || unidadeRestore.getIdUnidade().compareTo(unidade.getIdUnidade()) != 0) {
                    comboUnidadePai.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
                }
            }
        }
    }

    /**
     * Executa uma inicialização padrão para as combos. Basicamente deleta todas as opçÃµes, caso haja, e insere aprimeira linha com o valor "-- Selecione --".
     *
     * @param componenteCombo
     *            Componente o qual deseja inicializar com as configuraçÃµes citadas acima.
     */
    private void inicializaCombo(final HTMLSelect componenteCombo, final HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Obtendo o objeto contendo os dados informados no formulários.
        final UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

        // Verificando se o DTO e o serviço existem.
        if (unidadeDTO != null) {
            unidadeDTO.setServicos(this.getUnidadesAccServicosService().deserealizaObjetosDoRequest(request));

            unidadeDTO.setListaDeLocalidade((List<LocalidadeUnidadeDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LocalidadeUnidadeDTO.class,
                    "localidadesSerializadas", request));

            // Verificando se a unidade já existe.
            if (this.getUnidadeService().jaExisteUnidadeComMesmoNome(unidadeDTO)) {
                // Notificando o usuário que a unidade já existe.
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                return;
            }

            Double latitude = null;
            Double longitude = null;
            
            if(!request.getParameter("latitude").trim().equalsIgnoreCase("")){
            	latitude = new Double(request.getParameter("latitude"));
            }
            
            if(!request.getParameter("longitude").trim().equalsIgnoreCase("")){
            	longitude = new Double(request.getParameter("longitude"));
            }
            if (latitude != null && longitude != null) {
	            if (!GeoUtils.validCoordinates(latitude, longitude)) {
	                throw new ServiceException(UtilI18N.internacionaliza(request,"rest.service.mobile.v2.invalid.coordinates"));
	            }
            }
            
            unidadeDTO.setLatitude(latitude);
            unidadeDTO.setLongitude(longitude);

            // Inserindo.
            if (unidadeDTO.getIdUnidade() == null) {
                unidadeDTO.setIdEmpresa(WebUtil.getIdEmpresa(request));
                this.getUnidadeService().create(unidadeDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG05"));
            } else { // Atualizando.
                this.getUnidadeService().update(unidadeDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            }
            CITCorporeUtil.limparFormulario(document);
            document.executeScript("ocultaGrid()");
            document.executeScript("ocultaGridLocalidade()");
        }

        document.executeScript("deleteAllRowsLocalidade()");
        document.executeScript("limpar_LOOKUP_UNIDADE()");
        document.executeScript("updatePositions(defaultParams.latLng)");
    }

    public void listaContrato(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();
        String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
        if (UNIDADE_VINC_CONTRATOS == null) {
            UNIDADE_VINC_CONTRATOS = "N";
        } else if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            if (unidadeDTO.getIdUnidadePai() != null) {
                document.getElementById("divListaContratos").setVisible(false);
            } else {
                document.getElementById("divListaContratos").setVisible(true);
            }
        } else {
            return;
        }
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        UnidadeDTO unidade = (UnidadeDTO) document.getBean();
        LocalidadeDTO localidadeDTO = new LocalidadeDTO();
        EnderecoDTO enderecoDto = new EnderecoDTO();
        final LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
        final LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

        unidade = (UnidadeDTO) this.getUnidadeService().restore(unidade);

        Collection<LocalidadeUnidadeDTO> listaIdlocalidade = new ArrayList<>();

        if (unidade != null) {
            if (unidade.getIdEndereco() != null) {
                enderecoDto.setIdEndereco(unidade.getIdEndereco());
                enderecoDto = (EnderecoDTO) this.getEnderecoService().restore(enderecoDto);
            }

            listaIdlocalidade = localidadeUnidadeService.listaIdLocalidades(unidade.getIdUnidade());
        }

        this.preencherComboTipoUnidade(document, request, response);
        this.preencherComboUnidadePai(document, request, response);
        this.preencherComboPais(document, request, response);
        this.preencherComboUfs(document, request, response);
        this.preencherComboCidade(document, request, response);

        document.executeScript("deleteAllRows()");
        document.executeScript("deleteAllRowsLocalidade()");
        final HTMLForm form = CITCorporeUtil.limparFormulario(document);
        document.getForm("form").setValues(enderecoDto, false);
        if (unidade != null) {
            form.setValues(unidade);
        }

        if (unidade != null) {
            this.getUnidadeService().restaurarGridServicos(document, this.getUnidadesAccServicosService().consultarServicosAtivosPorUnidade(unidade.getIdUnidade()));

            if (unidade.getIdUnidadePai() == null) {
                String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
                if (UNIDADE_VINC_CONTRATOS == null) {
                    UNIDADE_VINC_CONTRATOS = "N";
                }
                if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
                    final ContratosUnidadesService contratosUnidadesService = (ContratosUnidadesService) ServiceLocator.getInstance().getService(ContratosUnidadesService.class,
                            null);
                    final Collection col = contratosUnidadesService.findByIdUnidade(unidade.getIdUnidade());

                    if (col != null && col.size() > 0) {
                        for (final Iterator it = col.iterator(); it.hasNext();) {
                            final ContratosUnidadesDTO contratosUnidadesDTO = (ContratosUnidadesDTO) it.next();
                            document.getCheckboxById("idContrato_" + contratosUnidadesDTO.getIdContrato()).setValue("0" + contratosUnidadesDTO.getIdContrato());
                        }
                    }
                }
                document.getElementById("divListaContratos").setVisible(true);
            } else {
                document.getElementById("divListaContratos").setVisible(false);
            }
        }

        if (listaIdlocalidade != null && !listaIdlocalidade.isEmpty()) {
            for (final LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidade) {
                if (localidadeUnidadeDto.getIdLocalidade() != null) {
                    localidadeDTO.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
                    localidadeDTO = (LocalidadeDTO) localidadeService.restore(localidadeDTO);
                    document.executeScript("addLinhaTabelaLocalidade(" + localidadeDTO.getIdLocalidade() + ", '"
                            + StringEscapeUtils.escapeJavaScript(localidadeDTO.getNomeLocalidade()) + "', " + false + ");");
                }
                document.executeScript("exibeGridLocalidade()");
            }
        }
    }

    /**
     * Metodo para exclusão lógica de Unidade
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UnidadeDTO unidade = (UnidadeDTO) document.getBean();
        unidade.setListaDeLocalidade((List<LocalidadeUnidadeDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LocalidadeUnidadeDTO.class,
                "localidadesSerializadas", request));
        if (unidade.getIdUnidade() != null && unidade.getIdUnidade() != 0) {
            this.getUnidadeService().deletarUnidade(unidade, document, request);
            document.executeScript("deleteAllRows()");
            document.executeScript("deleteAllRowsLocalidade()");
            final HTMLForm form = document.getForm("form");
            form.clear();
        }

        document.executeScript("limpar_LOOKUP_UNIDADE()");
    }

    @Override
    public Class<UnidadeDTO> getBeanClass() {
        return UnidadeDTO.class;
    }

    /**
     * Método para excluir uma associação de unidade com serviços
     *
     * @author rodrigo.oliveira
     *
     */
    public void excluirAssociacaoServico(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

        final Integer idServico = !request.getParameter("servicoSerializado").isEmpty() ? Integer.parseInt(request.getParameter("servicoSerializado")) : null;

        final UnidadesAccServicosDTO unidadeAccServicosDTO = new UnidadesAccServicosDTO();

        unidadeAccServicosDTO.setIdServico(idServico);

        if (idServico != null && idServico != 0) {
            this.getUnidadesAccServicosService().excluirAssociacaoServicosUnidade(unidadeDTO.getIdUnidade(), idServico);
            document.alert(UtilI18N.internacionaliza(request, "MSG07"));
        }
    }

    /**
     * Preenche combo de Pais.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preencherComboPais(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

        final HTMLSelect comboPais = document.getSelectById("idPais");

        final List<PaisDTO> listPais = (List) paisServico.list();

        this.inicializaCombo(comboPais, request);

        if (listPais != null) {
            for (final PaisDTO paisDto : listPais) {
                comboPais.addOption(paisDto.getIdPais().toString(), Util.tratarAspasSimples(Util.retiraBarraInvertida(paisDto.getNomePais())));
            }
        }
    }

    /**
     * Preenche combo de Ufs.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preencherComboUfs(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

        EnderecoDTO enderecoDto = new EnderecoDTO();

        UnidadeDTO unidade = new UnidadeDTO();

        final UfDTO ufDto = new UfDTO();

        final UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

        if (unidadeDTO.getIdUnidade() != null) {
            unidade = (UnidadeDTO) this.getUnidadeService().restore(unidadeDTO);
        }

        if (unidade.getIdEndereco() != null) {
            enderecoDto.setIdEndereco(unidade.getIdEndereco());
            enderecoDto = (EnderecoDTO) this.getEnderecoService().restore(enderecoDto);
        }

        if (unidadeDTO.getIdPais() != null) {
            ufDto.setIdPais(unidadeDTO.getIdPais());
        } else if (enderecoDto.getIdPais() != null) {
            ufDto.setIdPais(enderecoDto.getIdPais());
        }

        final HTMLSelect comboUfs = document.getSelectById("idUf");

        final List<UfDTO> listUfs = (List) ufService.listByIdPais(ufDto);

        this.inicializaCombo(comboUfs, request);

        if (listUfs != null) {
            for (final UfDTO uf : listUfs) {
                comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
            }
        }
        document.executeScript("janelaAguardeFechar();");
    }

    /**
     * Preenche combo de Cidade.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preencherComboCidade(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

        EnderecoDTO enderecoDto = new EnderecoDTO();

        UnidadeDTO unidade = new UnidadeDTO();

        final CidadesDTO cidadeDto = new CidadesDTO();

        if (unidadeDTO.getIdUnidade() != null) {
            unidade = (UnidadeDTO) this.getUnidadeService().restore(unidadeDTO);
        }

        if (unidade.getIdEndereco() != null) {
            enderecoDto.setIdEndereco(unidade.getIdEndereco());
            enderecoDto = (EnderecoDTO) this.getEnderecoService().restore(enderecoDto);
        }

        if (unidadeDTO.getIdUf() != null) {
            cidadeDto.setIdUf(unidadeDTO.getIdUf());
        } else if (enderecoDto.getIdUf() != null) {
            cidadeDto.setIdUf(enderecoDto.getIdUf());
        }

        final HTMLSelect comboCidade = document.getSelectById("idCidade");

        final List<CidadesDTO> listCidade = (List) this.getCidadesService().listByIdCidades(cidadeDto);

        this.inicializaCombo(comboCidade, request);
        if (listCidade != null) {
            for (final CidadesDTO cidade : listCidade) {
                comboCidade.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
            }
        }
        document.executeScript("janelaAguardeFechar();");
    }

    private CidadesService cidadesService;
    private EnderecoService enderecoService;
    private UnidadeService unidadeService;
    private UnidadesAccServicosService unidadesAccServicosService;

    private CidadesService getCidadesService() throws Exception {
        if (cidadesService == null) {
            cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
        }
        return cidadesService;
    }

    private EnderecoService getEnderecoService() throws Exception {
        if (enderecoService == null) {
            enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);
        }
        return enderecoService;
    }

    private UnidadeService getUnidadeService() throws Exception {
        if (unidadeService == null) {
            unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        }
        return unidadeService;
    }

    private UnidadesAccServicosService getUnidadesAccServicosService() throws Exception {
        if (unidadesAccServicosService == null) {
            unidadesAccServicosService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
        }
        return unidadesAccServicosService;
    }

}
