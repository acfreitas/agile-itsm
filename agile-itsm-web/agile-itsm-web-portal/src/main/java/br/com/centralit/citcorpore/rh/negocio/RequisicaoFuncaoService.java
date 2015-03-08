package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoService;
import br.com.centralit.citcorpore.rh.bean.CompetenciasTecnicasDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComplexidadeDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComportamentalFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCursoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaExperienciaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;

public interface RequisicaoFuncaoService extends ComplemInfSolicitacaoServicoService {

	public Collection<PerspectivaComplexidadeDTO> restoreComplexidade(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<PerspectivaTecnicaFormacaoAcademicaDTO> restoreFormacao(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<PerspectivaTecnicaCertificacaoDTO> restoreCertificacao(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<PerspectivaTecnicaCursoDTO> restoreCurso(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<PerspectivaTecnicaIdiomaDTO> restoreIdiomas(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<PerspectivaTecnicaExperienciaDTO> restoreExperiencia(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<CompetenciasTecnicasDTO> restoreCompetencia(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<PerspectivaComportamentalFuncaoDTO> restoreComportamental(RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception;

	public Collection<RequisicaoFuncaoDTO> retornaFuncoesAprovadas() throws Exception;

	/**
	 * Retorna RequisicaoFuncaoDTO com nomeCargo, de acordo com o idSolicitacaoServico.
	 * 
	 * @param requisicaoFuncaoDto
	 * @return RequisicaoFuncaoDTO
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public RequisicaoFuncaoDTO restoreWithNomeCargo(RequisicaoFuncaoDTO requisicaoFuncaoDto) throws Exception;
	public RequisicaoFuncaoDTO findByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;

}
