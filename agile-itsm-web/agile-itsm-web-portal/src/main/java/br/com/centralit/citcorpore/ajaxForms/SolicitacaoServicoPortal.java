package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SolicitacaoServicoPortal extends SolicitacaoServicoMultiContratos {
    ContratoDTO contratoDtoAux = new ContratoDTO();

    private Boolean acao = false;

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS")
                    + request.getContextPath() + "'");
            return;
        }

        final String SERVICO = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "0").trim();
        final Integer idServico = SERVICO.equalsIgnoreCase("") ? 0 : Integer.valueOf(SERVICO);

        // Imprime os dados do solicitante
        informaDadosSolicitacao(document, request, response);
        carregaServico(document, request, response);

        final GrupoEmpregadoService grupoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(
                GrupoEmpregadoService.class, null);
        final ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance()
                .getService(ContratosGruposService.class, null);
        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(
                ContratoService.class, null);
        final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance()
                .getService(ServicoContratoService.class, null);
        final List<GrupoEmpregadoDTO> listGrupos = (ArrayList<GrupoEmpregadoDTO>) grupoService
                .findByIdEmpregado(usuario.getIdEmpregado());
        final List<ServicoContratoDTO> listServicoContrato = new ArrayList<ServicoContratoDTO>();
        final Set<ContratosGruposDTO> listContratosGrupos = new HashSet<ContratosGruposDTO>();
        final Set colContratos = new HashSet<ContratoDTO>();
        /* Realiza a listagem de grupos empregados */
        if (listGrupos != null) {
            for (final GrupoEmpregadoDTO grupoEmpregadoDTO : listGrupos) {
                List<ContratosGruposDTO> temp = null;
                temp = (List<ContratosGruposDTO>) contratosGruposService.findByIdGrupo(grupoEmpregadoDTO.getIdGrupo());
                if (temp != null) {
                    for (final ContratosGruposDTO contratosGruposDTO : temp) {
                        listContratosGrupos.add(contratosGruposDTO);
                    }
                }
            }
        }

        if (listContratosGrupos != null) {
            for (final ContratosGruposDTO contratosGruposDTO : listContratosGrupos) {
                ServicoContratoDTO temp = null;
                temp = servicoContratoService.findByIdContratoAndIdServico(contratosGruposDTO.getIdContrato(),
                        idServico);
                if (temp != null) {
                    listServicoContrato.add(temp);
                }
            }
        }

        if (listServicoContrato != null) {
            for (final ServicoContratoDTO servicoContratoDTO : listServicoContrato) {
                List<ContratoDTO> temp2 = null;
                temp2 = (List<ContratoDTO>) contratoService.findByIdContrato(servicoContratoDTO.getIdContrato());
                if (temp2 != null) {
                    colContratos.add(temp2.get(0));
                }
            }
        }

        final ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(
                ClienteService.class, null);
        final FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(
                FornecedorService.class, null);

        String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
        if (COLABORADORES_VINC_CONTRATOS == null) {
            COLABORADORES_VINC_CONTRATOS = "N";
        }
        Collection colContratosColab = null;
        if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
        }

        if (colContratos != null) {
            if (colContratos.size() > 1) {
                document.getSelectById("idContrato").addOption("",
                        UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            } else {
                acao = true;
            }
            for (final Iterator it = colContratos.iterator(); it.hasNext();) {
                final ContratoDTO contratoDto = (ContratoDTO) it.next();
                if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
                    if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se parametro de colaboradores por
                                                                              // contrato ativo, entao filtra.
                        if (colContratosColab == null) {
                            continue;
                        }
                        if (!isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
                            continue;
                        }
                    }
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
                    contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
                    if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
                        final String nomeContrato = ""
                                + contratoDto.getNumero()
                                + " de "
                                + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(),
                                        WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ")";
                        document.getSelectById("idContrato").addOption("" + contratoDto.getIdContrato(), nomeContrato);
                    }
                }
            }
        }

        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        if (solicitacaoServicoDto.getIdContrato() != null) {
            document.getSelectById("idGrupoAtual").setValue("" + solicitacaoServicoDto.getIdGrupoAtual());
        }
        if (acao) {
            if (solicitacaoServicoDto.getIdSolicitacaoServico() == null
                    || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
                this.verificaImpactoUrgencia(document, request, response);
            }

        }

    }

    @Override
    public void verificaImpactoUrgencia(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
            solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
        }

        if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null) {
            return;
        }

        final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance()
                .getService(ServicoContratoService.class, null);
        final ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(
                solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

        if (servicoContratoDto != null) {
            final AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator
                    .getInstance().getService(AcordoNivelServicoService.class, null);
            AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(
                    servicoContratoDto.getIdServicoContrato(), "T");
            if (acordoNivelServicoDto == null) {
                // Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo
                // geral que esteja vinculado ao servicocontrato.
                final AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator
                        .getInstance().getService(AcordoServicoContratoService.class, null);
                final AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService
                        .findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
                if (acordoServicoContratoDTO == null) {
                    document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
                    return;
                }

                // Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de
                // servico.
                acordoNivelServicoDto = new AcordoNivelServicoDTO();
                acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
                acordoNivelServicoDto = (AcordoNivelServicoDTO) acordoNivelServicoService
                        .restore(acordoNivelServicoDto);
                if (acordoNivelServicoDto == null) {
                    // Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato
                    document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
                    return;
                }
            }
            if (acordoNivelServicoDto.getImpacto() != null) {
                document.getSelectById("impacto").setValue("" + acordoNivelServicoDto.getImpacto());
                if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null
                        && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
                    document.getSelectById("impacto").setDisabled(true);
                }
            } else {
                document.getSelectById("impacto").setValue("B");
            }
            if (acordoNivelServicoDto.getUrgencia() != null) {
                document.getSelectById("urgencia").setValue("" + acordoNivelServicoDto.getUrgencia());
                if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null
                        && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
                    document.getSelectById("urgencia").setDisabled(true);
                }
            } else {
                document.getSelectById("urgencia").setValue("B");
            }
        } else {
            document.getSelectById("impacto").setValue("B");
            document.getSelectById("urgencia").setValue("B");
        }
    }

    private boolean isContratoInList(final Integer idContrato, final Collection colContratosColab) {
        if (colContratosColab != null) {
            for (final Iterator it = colContratosColab.iterator(); it.hasNext();) {
                final ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
                if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * informaDadosSolicitação - Informa dados iniciais da solicitação no momento da abertura
     * 
     * @author Flavio.Junior
     */
    public void informaDadosSolicitacao(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS")
                    + request.getContextPath() + "'");
            return;
        }

        /* Setando o id do Solicitante diretamente d sessão */
        document.getElementById("idSolicitante").setValue(usuario.getIdEmpregado().toString());

        final EmpregadoDTO eb = this.getEmpregadoService().restoreByIdEmpregado(usuario.getIdEmpregado());
        if (eb != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append(eb.getNome() + ";" + eb.getEmail() + ";" + eb.getTelefone() + ";");

            document.executeScript("informaDadosSolicitacao(\"" + sb.toString() + "\")");
        }

    }

    public void salvar(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS")
                    + request.getContextPath() + "'");
            return;
        }

        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(
                EmpregadoService.class, WebUtil.getUsuarioSistema(request));

        final String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");
        final Integer idOrigem = ORIGEM.trim().equalsIgnoreCase("") ? 0 : Integer.valueOf(ORIGEM.trim());
        solicitacaoServicoDto.setIdOrigem(idOrigem);

        solicitacaoServicoDto.setUsuarioDto(usuario);
        solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());
        final EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuario.getIdEmpregado());
        solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
        try {
            if (solicitacaoServicoDto.getIdSolicitacaoServico() == null
                    || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
                solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto);
                // document.alert("Registro efetuado com sucesso. Solicitação N.o: " +
                // solicitacaoServicoDto.getIdSolicitacaoServico() + " criada.");
                String comando = "mostraMensagemInsercao('" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>"
                        + UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + " <b><u>"
                        + solicitacaoServicoDto.getIdSolicitacaoServico() + "</u></b> "
                        + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>"
                        + UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": "
                        + solicitacaoServicoDto.getIdPrioridade();
                if (solicitacaoServicoDto.getPrazoHH() > 0 || solicitacaoServicoDto.getPrazoMM() > 0) {
                    comando = comando + " - SLA: " + solicitacaoServicoDto.getSLAStr() + "";
                }
                comando = comando + "')";
                document.executeScript(comando);
                document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

                return;
            } else {
                solicitacaoServicoService.updateInfo(solicitacaoServicoDto);
                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            }
        } catch (final Exception e) {
            String msgErro = e.getMessage();
            msgErro = msgErro.replaceAll("java.lang.Exception:", "");
            document.alert("" + msgErro);
            this.verificaImpactoUrgencia(document, request, response);
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        document.executeScript("fechar();");
    }

    public void carregaServico(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(
                ServicoService.class, null);

        final String SERVICO = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "0").trim();
        final Integer idServico = SERVICO.equalsIgnoreCase("") ? 0 : Integer.valueOf(SERVICO);
        ServicoDTO servicoDto = new ServicoDTO();
        servicoDto.setIdServico(idServico);
        servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
        /* Setando o tipo de demanda de serviço */

        if (servicoDto != null) {
            document.getElementById("idTipoDemandaServico").setValue(servicoDto.getIdTipoDemandaServico().toString());
            document.getElementById("idServico").setValue(servicoDto.getIdServico().toString());
            document.getElementById("divServico").setValue(servicoDto.getNomeServico());
        } else {
            document.getElementById("idTipoDemandaServico").setValue("");
            document.getElementById("idServico").setValue("");
            document.getElementById("divServico").setValue("");
            document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicolocalizado"));
        }

    }
}
