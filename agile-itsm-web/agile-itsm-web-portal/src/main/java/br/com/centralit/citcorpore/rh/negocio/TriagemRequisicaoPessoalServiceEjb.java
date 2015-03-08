package br.com.centralit.citcorpore.rh.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.TriagemRequisicaoPessoalDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings({"unchecked", "rawtypes"})
public class TriagemRequisicaoPessoalServiceEjb extends CrudServiceImpl implements TriagemRequisicaoPessoalService {

    private TriagemRequisicaoPessoalDao dao;

    @Override
    protected TriagemRequisicaoPessoalDao getDao() {
        if (dao == null) {
            dao = new TriagemRequisicaoPessoalDao();
        }
        return dao;
    }

    @Override
    public Collection<CurriculoDTO> sugereCurriculos(final RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {

        final Collection<CurriculoDTO> listaCurriculos = new CurriculoDao().list();
        final Collection<CurriculoDTO> listaCurriculosTriados = new ArrayList<CurriculoDTO>();

        boolean condicaoAtendida = false;

        // Percorre todos os curriculos adicionando os que atendem aos critérios da Requisição de pessoal
        for (final CurriculoDTO curriculo : listaCurriculos) {
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getChkFormacao().equalsIgnoreCase("F")) {
                condicaoAtendida = this.atendeFormacaoAcademica(curriculo, requisicaoPessoalDto);
            }

            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getChkCertificacao().equalsIgnoreCase("C")
                    && (requisicaoPessoalDto.getChkFormacao().equalsIgnoreCase("F") && condicaoAtendida || !requisicaoPessoalDto.getChkFormacao().equalsIgnoreCase("F"))) {
                condicaoAtendida = this.atendeCertificacao(curriculo, requisicaoPessoalDto);
            }

            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getChkIdioma().equalsIgnoreCase("I")
                    && (requisicaoPessoalDto.getChkFormacao().equalsIgnoreCase("F") && condicaoAtendida || !requisicaoPessoalDto.getChkFormacao().equalsIgnoreCase("F"))
                    && (requisicaoPessoalDto.getChkCertificacao().equalsIgnoreCase("C") && condicaoAtendida || !requisicaoPessoalDto.getChkCertificacao().equalsIgnoreCase("C"))) {
                condicaoAtendida = this.atendeIdioma(curriculo, requisicaoPessoalDto);
            }

            /*
             * Não tem correspondente de ATITUDE INDIVIDUAL em currículo
             * if(condicaoAtendida){
             * //ATITUDE INDIVIDUAL
             * condicaoAtendida = this.atendeAtitudeIndividual(curriculo, requisicaoPessoalDto);
             * }
             */

            /*
             * Não tem correspondente de CONHECIMENTO em currículo
             * if(condicaoAtendida){
             * //CONHECIMENTO
             * condicaoAtendida = this.atendeConhecimento(curriculo, requisicaoPessoalDto);
             * }
             */

            /*
             * Não tem correspondente de CURSO em currículo
             * if(condicaoAtendida){
             * //CURSO
             * condicaoAtendida = this.atendeCurso(curriculo, requisicaoPessoalDto);
             * }
             */

            /*
             * Não tem correspondente de EXPERIÊNCIA ANTERIOR em currículo
             * if(condicaoAtendida){
             * //EXPERIÊNCIA ANTERIOR
             * condicaoAtendida = this.atendeExperienciaAnterior(curriculo, requisicaoPessoalDto);
             * }
             */

            /*
             * Não tem correspondente de EXPERIÊNCIA EM INFORMÁTICA em currículo
             * if(condicaoAtendida){
             * //EXPERIÊNCIA EM INFORMÁTICA
             * condicaoAtendida = this.atendeExperienciaAnterior(curriculo, requisicaoPessoalDto);
             * }
             */

            /*
             * Não tem correspondente de HABILIDADE em currículo
             * if(condicaoAtendida){
             * //HABILIDADE
             * condicaoAtendida = this.atendeExperienciaAnterior(curriculo, requisicaoPessoalDto);
             * }
             */

            // Verifica se o curriculo atende a todos os requisitos
            if (condicaoAtendida) {
                listaCurriculosTriados.add(curriculo);
            }
            condicaoAtendida = false;
        }
        return listaCurriculosTriados;
    }

    private boolean atendeFormacaoAcademica(final CurriculoDTO curriculo, final RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        boolean atende = false;
        final FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();
        final RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();

        // Pega a colecao de formacao exigida pela SOLICITACAO
        final Collection<RequisicaoFormacaoAcademicaDTO> listaRequisicaoFormacaoAcademica = requisicaoFormacaoAcademicaDao.findByFormacaoAcademicaObrigatoria(requisicaoPessoalDto
                .getIdSolicitacaoServico());
        if (listaRequisicaoFormacaoAcademica != null && !listaRequisicaoFormacaoAcademica.isEmpty()) {
            for (final RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademica : listaRequisicaoFormacaoAcademica) {
                // Pega a lista de formações ligada ao CURRICULO
                final Collection<FormacaoCurriculoDTO> listaFormacaoCurriculos = formacaoCurriculoDao.findByIdCurriculo(curriculo.getIdCurriculo());
                if (listaFormacaoCurriculos != null && !listaFormacaoCurriculos.isEmpty()) {
                    for (final FormacaoCurriculoDTO formacaoCurriculo : listaFormacaoCurriculos) {
                        if (formacaoCurriculo.getIdTipoFormacao().equals(requisicaoFormacaoAcademica.getIdFormacaoAcademica())) {
                            // Usuario atende a requisicao!
                            atende = true;
                            break;
                        } else {
                            atende = false;
                        }
                    }
                }

                if (!atende) {
                    break;
                }
            }
        } else {
            atende = true;
        }
        return atende;
    }

    private boolean atendeCertificacao(final CurriculoDTO curriculo, final RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        boolean atende = false;
        final CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();
        final RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
        final CertificacaoDao certificacaoDao = new CertificacaoDao();

        // Pega a colecao de certificados exigida pela SOLICITACAO
        final Collection<RequisicaoCertificacaoDTO> listaRequisicaoCertificacao = requisicaoCertificacaoDao.findByCertificacaoObrigatoria(requisicaoPessoalDto
                .getIdSolicitacaoServico());
        if (listaRequisicaoCertificacao != null && !listaRequisicaoCertificacao.isEmpty()) {
            for (final RequisicaoCertificacaoDTO requisicaoCertificacao : listaRequisicaoCertificacao) {
                CertificacaoDTO certificacaoDto = new CertificacaoDTO();
                certificacaoDto.setIdCertificacao(requisicaoCertificacao.getIdCertificacao());
                certificacaoDto = (CertificacaoDTO) certificacaoDao.restore(certificacaoDto);

                // Pega a lista de certificações ligada ao CURRICULO
                final Collection<CertificacaoCurriculoDTO> listaCertificacaoCurriculos = certificacaoCurriculoDao.findByIdCurriculo(curriculo.getIdCurriculo());
                if (listaCertificacaoCurriculos != null && !listaCertificacaoCurriculos.isEmpty()) {
                    for (final CertificacaoCurriculoDTO certificacaoCurriculo : listaCertificacaoCurriculos) {
                        if (certificacaoCurriculo.getDescricao().toLowerCase().contains(certificacaoDto.getDescricao().toLowerCase())) {
                            // Usuario atende a requisicao!
                            atende = true;
                            break;
                        } else {
                            atende = false;
                        }
                    }
                }

                if (!atende) {
                    break;
                }
            }
        } else {
            atende = true;
        }
        return atende;
    }

    private boolean atendeIdioma(final CurriculoDTO curriculo, final RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        boolean atende = false;
        final IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();
        final RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
        // Pega a colecao de formacao exigida pela SOLICITACAO
        final Collection<RequisicaoIdiomaDTO> listaRequisicaoIdioma = requisicaoIdiomaDao.findByIdiomaObrigatorio(requisicaoPessoalDto.getIdSolicitacaoServico());
        if (listaRequisicaoIdioma != null && !listaRequisicaoIdioma.isEmpty()) {
            for (final RequisicaoIdiomaDTO requisicaoIdiomaDTO : listaRequisicaoIdioma) {
                // Pega a lista de formações ligada ao CURRICULO
                final Collection<IdiomaCurriculoDTO> listaIdiomaCurriculo = idiomaCurriculoDao.findByIdCurriculo(curriculo.getIdCurriculo());
                if (listaIdiomaCurriculo != null && !listaIdiomaCurriculo.isEmpty()) {
                    for (final IdiomaCurriculoDTO idiomaCurriculoDTO : listaIdiomaCurriculo) {
                        if (idiomaCurriculoDTO.getIdIdioma().equals(requisicaoIdiomaDTO.getIdIdioma())) {
                            // Usuario atende a requisicao!
                            atende = true;
                            break;
                        } else {
                            atende = false;
                        }
                    }
                }

                if (!atende) {
                    break;
                }
            }
        } else {
            atende = true;
        }
        return atende;
    }

    @Override
    public Collection<CurriculoDTO> triagemManualPorCriterios(final RequisicaoPessoalDTO requisicaoPessoalDTO, final String idsCurriculosTriados, final Integer pagSelecionada,
            final Integer itensPorPagina) throws Exception {
        return new CurriculoDao().listaCurriculosPorCriterios(requisicaoPessoalDTO, idsCurriculosTriados, pagSelecionada, itensPorPagina);
    }

    public boolean validaInsercao(final Collection<CurriculoDTO> colecao, final int id) throws Exception {
        for (final CurriculoDTO curriculoDTO : colecao) {
            if (curriculoDTO.getIdCurriculo().intValue() == id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdSolicitacaoServicoAndIdTarefa(final Integer idSolicitacaoServico, final Integer idTarefa) throws Exception {
        final Collection<TriagemRequisicaoPessoalDTO> result = new ArrayList();
        final Collection<TriagemRequisicaoPessoalDTO> colTriagem = this.getDao().findByIdSolicitacaoServico(idSolicitacaoServico);
        if (colTriagem != null && !colTriagem.isEmpty()) {
            for (final TriagemRequisicaoPessoalDTO triagemDto : colTriagem) {
                boolean bValido = false;
                bValido = triagemDto.getIdItemTrabalhoEntrevistaGestor() != null && triagemDto.getIdItemTrabalhoEntrevistaGestor().intValue() == idTarefa.intValue();
                if (bValido) {
                    triagemDto.setTipoEntrevista(TipoEntrevista.Gestor.name());
                } else {
                    bValido = triagemDto.getIdItemTrabalhoEntrevistaRH() != null && triagemDto.getIdItemTrabalhoEntrevistaRH().intValue() == idTarefa.intValue();
                    if (bValido) {
                        triagemDto.setTipoEntrevista(TipoEntrevista.RH.name());
                    }
                }
                if (bValido) {
                    result.add(triagemDto);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<CurriculoDTO> triagemManual() throws Exception {
        return null;
    }

    @Override
    public boolean candidatoParticipaProcessoSelecao(final Integer idCurriculo, final Integer idSolicitacaoServico) throws PersistenceException {
        return this.getDao().candidatoParticipaProcessoSelecao(idCurriculo, idSolicitacaoServico);
    }

}
