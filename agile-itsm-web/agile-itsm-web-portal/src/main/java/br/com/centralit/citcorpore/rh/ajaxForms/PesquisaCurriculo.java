package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.PesquisaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("unchecked")
public class PesquisaCurriculo extends AjaxFormAction {

    public void adicionarCurriculosPorColecao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final Collection<CurriculoDTO> col)
            throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        document.executeScript("limparDadostableCurriculo()");
        final CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);

        final StringBuilder sb = new StringBuilder();
        if (col != null && col.size() > 0) {
            for (CurriculoDTO curriculo : col) {
                curriculo = (CurriculoDTO) curriculoService.restore(curriculo);
                if (curriculo != null) {

                    String caminhoFoto = "";
                    final Collection<ControleGEDDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, curriculo.getIdCurriculo());
                    if (colAnexos != null && colAnexos.size() > 0) {
                        final List<UploadDTO> colAnexosUploadDTO = (List<UploadDTO>) controleGedService.convertListControleGEDToUploadDTO(colAnexos);
                        caminhoFoto = colAnexosUploadDTO.get(0).getCaminhoRelativo();
                    }
                    curriculo.setCaminhoFoto(caminhoFoto);

                    final Collection<EnderecoCurriculoDTO> listaEndereco = curriculo.getColEnderecos();
                    for (final EnderecoCurriculoDTO endereco : listaEndereco) {
                        curriculo.setNomeCidade(endereco.getNomeCidade());
                        curriculo.setNomeUF(endereco.getNomeUF());
                    }

                    final Collection<FormacaoCurriculoDTO> listaFormacao = curriculo.getColFormacao();
                    if (listaFormacao != null && listaFormacao.size() > 0) {
                        Collections.sort((List<FormacaoCurriculoDTO>) listaFormacao);
                        final FormacaoCurriculoDTO formacao = ((List<FormacaoCurriculoDTO>) listaFormacao).get(listaFormacao.size() - 1);
                        curriculo.setDescricaoTipoFormacao(formacao.getDescricaoTipoFormacao());
                    }

                    final Collection<ExperienciaProfissionalCurriculoDTO> listaExperiencia = curriculo.getColExperienciaProfissional();
                    if (listaExperiencia != null) {
                        for (final ExperienciaProfissionalCurriculoDTO experiencia : listaExperiencia) {
                            final Collection<FuncaoExperienciaProfissionalCurriculoDTO> listaFuncao = experiencia.getColFuncao();
                            sb.append("<b>" + experiencia.getDescricaoEmpresa() + "</b></br>");
                            if (listaFuncao != null) {
                                for (final FuncaoExperienciaProfissionalCurriculoDTO funcao : listaFuncao) {
                                    sb.append(funcao.getNomeFuncao());
                                    sb.append(" (" + funcao.getDescricaoFuncao() + ")</br>");
                                    curriculo.setDescricaoEmpresa(sb.toString());
                                }
                            }
                        }
                        sb.delete(0, sb.length());
                    }

                    final Collection<CertificacaoCurriculoDTO> listaCertificacao = curriculo.getColCertificacao();
                    if (listaCertificacao != null) {
                        int aux = 0;
                        for (final CertificacaoCurriculoDTO experiencia : listaCertificacao) {
                            if (experiencia != null) {
                                aux = aux + 1;
                                if (aux < listaCertificacao.size()) {
                                    sb.append(experiencia.getDescricao() + " - ");
                                } else {
                                    sb.append(experiencia.getDescricao());
                                }

                                curriculo.setDescricao(sb.toString());
                            }
                        }
                        sb.delete(0, sb.length());
                    }

                    curriculo.setPretensaoSalarial(curriculo.getPretensaoSalarial());

                    curriculo.getColEnderecos();
                    document.executeScript("incluirColecaoTable('" + br.com.citframework.util.WebUtil.serializeObject(curriculo) + "');");
                }
            }
        } else {
            document.alert(UtilI18N.internacionaliza(request, "MSG04"));
        }

        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    @Override
    public Class<PesquisaCurriculoDTO> getBeanClass() {
        return PesquisaCurriculoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        this.preencherComboPais(document, request, response);
        final String idSolicitacaoServico = request.getParameter("idSolicitacaoServico");

        if (idSolicitacaoServico != null && !idSolicitacaoServico.equalsIgnoreCase("")) {
            RequisicaoPessoalDTO requisicaoPessoalDto = new RequisicaoPessoalDTO();
            requisicaoPessoalDto.setIdSolicitacaoServico(Integer.parseInt(idSolicitacaoServico));
            final RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);

            if (requisicaoPessoalDto.getAtividades() != null) {
                document.executeScript("$('#atividades').val('" + requisicaoPessoalDto.getAtividades() + "');");
            }

            final HTMLTable tblFormacaoAcademica = document.getTableById("tblFormacaoAcademica");
            tblFormacaoAcademica.deleteAllRows();

            if (requisicaoPessoalDto.getColFormacaoAcademica() != null) {
                for (final RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
                    document.executeScript("adicionarLinhaSelecionada(\"FormacaoAcademica\"," + requisicaoFormacaoAcademicaDto.getIdFormacaoAcademica() + ",\""
                            + requisicaoFormacaoAcademicaDto.getDescricao() + "\",\"" + requisicaoFormacaoAcademicaDto.getObrigatorio() + "\",\""
                            + requisicaoFormacaoAcademicaDto.getDetalhe() + "\");");
                }
            }

            final HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
            tblCertificacao.deleteAllRows();

            if (requisicaoPessoalDto.getColCertificacao() != null) {
                for (final RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
                    document.executeScript("adicionarLinhaSelecionada(\"Certificacao\"," + requisicaoCertificacaoDto.getIdCertificacao() + ",\""
                            + requisicaoCertificacaoDto.getDescricao() + "\",\"" + requisicaoCertificacaoDto.getObrigatorio() + "\",\"" + requisicaoCertificacaoDto.getDetalhe()
                            + "\");");
                }
            }

            final HTMLTable tblCurso = document.getTableById("tblCurso");
            tblCurso.deleteAllRows();

            if (requisicaoPessoalDto.getColCurso() != null) {
                for (final RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
                    document.executeScript("adicionarLinhaSelecionada(\"Curso\"," + requisicaoCursoDto.getIdCurso() + ",\"" + requisicaoCursoDto.getDescricao() + "\",\""
                            + requisicaoCursoDto.getObrigatorio() + "\",\"" + requisicaoCursoDto.getDetalhe() + "\");");
                }
            }

            final HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
            tblExperienciaInformatica.deleteAllRows();

            if (requisicaoPessoalDto.getColExperienciaInformatica() != null) {
                for (final RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
                    document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\"," + requisicaoExperienciaInformaticaDto.getIdExperienciaInformatica() + ",\""
                            + requisicaoExperienciaInformaticaDto.getDescricao() + "\",\"" + requisicaoExperienciaInformaticaDto.getObrigatorio() + "\",\""
                            + requisicaoExperienciaInformaticaDto.getDetalhe() + "\");");
                }
            }

            final HTMLTable tblIdioma = document.getTableById("tblIdioma");
            tblIdioma.deleteAllRows();

            if (requisicaoPessoalDto.getColIdioma() != null) {
                for (final RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
                    document.executeScript("adicionarLinhaSelecionada(\"Idioma\"," + requisicaoIdiomaDto.getIdIdioma() + ",\"" + requisicaoIdiomaDto.getDescricao() + "\",\""
                            + requisicaoIdiomaDto.getObrigatorio() + "\",\"" + requisicaoIdiomaDto.getDetalhe() + "\");");
                }
            }

            final HTMLTable tblExperienciaAnterior = document.getTableById("tblExperienciaAnterior");
            tblExperienciaAnterior.deleteAllRows();

            if (requisicaoPessoalDto.getColExperienciaAnterior() != null) {
                for (final RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
                    document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\"," + requisicaoExperienciaAnteriorDto.getIdConhecimento() + ",\""
                            + requisicaoExperienciaAnteriorDto.getDescricao() + "\",\"" + requisicaoExperienciaAnteriorDto.getObrigatorio() + "\",\""
                            + requisicaoExperienciaAnteriorDto.getDetalhe() + "\");");
                }
            }

            final HTMLTable tblConhecimento = document.getTableById("tblConhecimento");
            tblConhecimento.deleteAllRows();

            if (requisicaoPessoalDto.getColConhecimento() != null) {
                for (final RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
                    document.executeScript("adicionarLinhaSelecionada(\"Conhecimento\"," + requisicaoConhecimentoDto.getIdConhecimento() + ",\""
                            + requisicaoConhecimentoDto.getDescricao() + "\",\"" + requisicaoConhecimentoDto.getObrigatorio() + "\",\"" + requisicaoConhecimentoDto.getDetalhe()
                            + "\");");
                }
            }

            final HTMLTable tblHabilidade = document.getTableById("tblHabilidade");
            tblHabilidade.deleteAllRows();

            if (requisicaoPessoalDto.getColHabilidade() != null) {
                for (final RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
                    document.executeScript("adicionarLinhaSelecionada(\"Habilidade\"," + requisicaoHabilidadeDto.getIdHabilidade() + ",\"" + requisicaoHabilidadeDto.getDescricao()
                            + "\",\"" + requisicaoHabilidadeDto.getObrigatorio() + "\",\"" + requisicaoHabilidadeDto.getDetalhe() + "\");");
                }
            }

            final HTMLTable tblAtitudeIndividual = document.getTableById("tblAtitudeIndividual");
            tblAtitudeIndividual.deleteAllRows();

            if (requisicaoPessoalDto.getColAtitudeIndividual() != null) {
                for (final RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
                    document.executeScript("adicionarLinhaSelecionada(\"AtitudeIndividual\"," + requisicaoAtitudeIndividualDto.getIdAtitudeIndividual() + ",\""
                            + requisicaoAtitudeIndividualDto.getDescricao() + "\",\"" + requisicaoAtitudeIndividualDto.getObrigatorio() + "\",\""
                            + requisicaoAtitudeIndividualDto.getDetalhe() + "\");");
                }
            }
        }
    }

    public void buscaHistorico(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final PesquisaCurriculoDTO pesquisaCurriculoDto = (PesquisaCurriculoDTO) document.getBean();
        CurriculoDTO curriculoDTO = new CurriculoDTO();
        curriculoDTO.setIdCurriculo(pesquisaCurriculoDto.getIdCurriculo());

        final CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        final CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);

        curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
        pesquisaCurriculoDto.setIdCandidato(candidatoService.findByCpfCurriculo(curriculoDTO.getCpf()));

        final List<CandidatoDTO> listaCandidato = (List<CandidatoDTO>) candidatoService.findByIdCandidatoJoinIdHistorico(pesquisaCurriculoDto.getIdCandidato());

        if (listaCandidato != null && listaCandidato.size() > 0) {
            final CandidatoDTO candidato = listaCandidato.get(0);
            pesquisaCurriculoDto.setIdHistorico(candidato.getIdHistoricoFuncional());
            document.executeScript("visualizarHistorico(" + pesquisaCurriculoDto.getIdHistorico() + "," + pesquisaCurriculoDto.getIdCandidato() + ")");
        } else {
            document.alert("Candidato não possui histórico");
        }
    }

    @SuppressWarnings("rawtypes")
    public void preencherComboPais(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);
        final List<PaisDTO> paises = (List) paisServico.list();
        final HTMLSelect componenteCombo = document.getSelectById("idPais");
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        if (paises != null) {
            for (final PaisDTO pais : paises) {
                componenteCombo.addOption(pais.getIdPais().toString(), pais.getNomePais());
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public void preencherComboEstados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final PesquisaCurriculoDTO pesquisaCurriculoDto = (PesquisaCurriculoDTO) document.getBean();
        final HTMLSelect componenteCombo = document.getSelectById("idEstado");
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        if (pesquisaCurriculoDto.getIdPais() != null && pesquisaCurriculoDto.getIdPais() > 0) {
            final UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

            final UfDTO obj = new UfDTO();
            obj.setIdPais(pesquisaCurriculoDto.getIdPais());

            if (ufService != null) {
                final List<UfDTO> ufs = (List) ufService.listByIdPais(obj);
                if (ufs != null) {
                    // Ordenando ufs alfabeticamente.
                    Collections.sort(ufs, new Comparator<UfDTO>() {

                        @Override
                        public int compare(final UfDTO o1, final UfDTO o2) {
                            if (o1 == null || o1.getNomeUf().trim().equals("")) {
                                return -999;
                            }

                            if (o1 == null || o1.getNomeUf().trim().equals("")) {
                                return -999;
                            }

                            return o1.getNomeUf().compareTo(o2.getNomeUf());
                        }

                    });

                    for (final UfDTO uf : ufs) {
                        componenteCombo.addOption(uf.getIdUf().toString(), uf.getNomeUf());
                    }
                }
            }
        }
    }

}
