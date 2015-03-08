package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoFuncaoService;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciasTecnicasDTO;
import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComplexidadeDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComportamentalFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCursoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaExperienciaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.negocio.CertificacaoService;
import br.com.centralit.citcorpore.rh.negocio.ConhecimentoService;
import br.com.centralit.citcorpore.rh.negocio.CursoService;
import br.com.centralit.citcorpore.rh.negocio.FormacaoAcademicaService;
import br.com.centralit.citcorpore.rh.negocio.IdiomaService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoFuncaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.borges
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RequisicaoFuncao extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		RequisicaoFuncaoDTO requisicaoFuncaoDto = (RequisicaoFuncaoDTO) document.getBean();
		Integer numSolicitacao = requisicaoFuncaoDto.getIdSolicitacaoServico();
		requisicaoFuncaoDto = new RequisicaoFuncaoDTO();
		requisicaoFuncaoDto.setIdSolicitacaoServico(numSolicitacao);

		this.preencherComboJustificativa(document, request, response);
		
		this.preencherCombosNivel(document, request, response);

		if (requisicaoFuncaoDto.getIdSolicitacaoServico() != null) {
			restore(document, request, response, requisicaoFuncaoDto);
		}
		
//		document.executeScript("disableDiv");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoFuncaoDTO requisicaoFuncaoDto) throws ServiceException, Exception {
		RequisicaoFuncaoService reqFuncaoService = (RequisicaoFuncaoService) ServiceLocator.getInstance().getService(RequisicaoFuncaoService.class, null);
		
		Collection<PerspectivaComplexidadeDTO> colComplexidade = new ArrayList<>();
		Collection<PerspectivaTecnicaFormacaoAcademicaDTO> colFormacao = new ArrayList<>();
		Collection<PerspectivaTecnicaCertificacaoDTO> colCertificacao = new ArrayList<>();
		Collection<PerspectivaTecnicaCursoDTO> colCurso = new ArrayList<>();
		Collection<PerspectivaTecnicaIdiomaDTO> colIdioma = new ArrayList<>();
		Collection<PerspectivaTecnicaExperienciaDTO> colExperiencia = new ArrayList<>();
		Collection<CompetenciasTecnicasDTO> colCompetencia = new ArrayList<>();
		Collection<PerspectivaComportamentalFuncaoDTO> colComportamental = new ArrayList<>();

		if (requisicaoFuncaoDto.getIdSolicitacaoServico() != null) {
			requisicaoFuncaoDto = (RequisicaoFuncaoDTO) reqFuncaoService.restore(requisicaoFuncaoDto);
			
			
			if( requisicaoFuncaoDto.getFase() != null && !requisicaoFuncaoDto.getFase().equals("etapa1")){
				
			colComplexidade = reqFuncaoService.restoreComplexidade(requisicaoFuncaoDto);
				colFormacao = reqFuncaoService.restoreFormacao(requisicaoFuncaoDto);
				colCertificacao = reqFuncaoService.restoreCertificacao(requisicaoFuncaoDto);
				colCurso = reqFuncaoService.restoreCurso(requisicaoFuncaoDto);
				colIdioma = reqFuncaoService.restoreIdiomas(requisicaoFuncaoDto);
				colExperiencia = reqFuncaoService.restoreExperiencia(requisicaoFuncaoDto);
				colCompetencia = reqFuncaoService.restoreCompetencia(requisicaoFuncaoDto);
				colComportamental = reqFuncaoService.restoreComportamental(requisicaoFuncaoDto);
				
				if(colComplexidade != null){
					HTMLTable tblComplexidade =  document.getTableById("tblPerspectivaComplexidade");
					tblComplexidade.deleteAllRows();
					for(PerspectivaComplexidadeDTO obj: colComplexidade){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblComplexidade.addRow(obj, new String[]{"descricaoPerspectivaComplexidade","nivelPerspectivaComplexidade",""}, null, null, new String[]{""}, null, null);
						}else{
							tblComplexidade.addRow(obj, new String[]{"descricaoPerspectivaComplexidade","nivelPerspectivaComplexidade",""}, null, null, new String[]{"gerarImgDelPerspectivaComplexidade"}, null, null);
						}
					}
				}
				
				if(colFormacao != null){
					HTMLTable tblFormacaoAcademica =  document.getTableById("tblFormacaoAcademica");
					tblFormacaoAcademica.deleteAllRows();
					for(PerspectivaTecnicaFormacaoAcademicaDTO obj: colFormacao){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblFormacaoAcademica.addRow(obj, new String[]{"obrigatorioFormacaoAcademica","descricaoFormacaoAcademica","detalheFormacaoAcademica",""}, null, null, new String[]{""}, null, null);
						}else{
							tblFormacaoAcademica.addRow(obj, new String[]{"obrigatorioFormacaoAcademica","descricaoFormacaoAcademica","detalheFormacaoAcademica",""}, null, null, new String[]{"gerarImgDelFormacaoAcademica"}, null, null);
						}
						
					}
				}
				
				if(colCertificacao != null){
					HTMLTable tblCertificacao =  document.getTableById("tblCertificacao");
					tblCertificacao.deleteAllRows();				
					for(PerspectivaTecnicaCertificacaoDTO obj: colCertificacao){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblCertificacao.addRow(obj, new String[]{"obrigatorioCertificacao","descricaoCertificacao","versaoCertificacao",""}, null, null, new String[]{""}, null, null);
						}else{
							tblCertificacao.addRow(obj, new String[]{"obrigatorioCertificacao","descricaoCertificacao","versaoCertificacao","gerarImgDelCertificacao"}, null, null, new String[]{""}, null, null);
						}
					}
				}
				
				if(colCurso != null){
					HTMLTable tblFormacaoAcademica =  document.getTableById("tblCurso");
					tblFormacaoAcademica.deleteAllRows();				
					for(PerspectivaTecnicaCursoDTO obj: colCurso){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblFormacaoAcademica.addRow(obj, new String[]{"obrigatorioCurso","descricaoCurso","detalheCurso",""}, null, null, new String[]{""}, null, null);
						}else{
							tblFormacaoAcademica.addRow(obj, new String[]{"obrigatorioCurso","descricaoCurso","detalheCurso",""}, null, null, new String[]{"gerarImgDelCurso"}, null, null);
						}
					}
				}
				
				if(colIdioma != null){
					HTMLTable tblIdioma =  document.getTableById("tblIdioma");
					tblIdioma.deleteAllRows();				
					for(PerspectivaTecnicaIdiomaDTO obj: colIdioma){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblIdioma.addRow(obj, new String[]{"obrigatorioIdioma","descricaoIdioma","detalheIdioma",""}, null, null, new String[]{""}, null, null);
						}else{
							tblIdioma.addRow(obj, new String[]{"obrigatorioIdioma","descricaoIdioma","detalheIdioma",""}, null, null, new String[]{"gerarImgDelIdioma"}, null, null);
						}
					}
				}
				
				if(colExperiencia != null){
					HTMLTable tblExperiencia =  document.getTableById("tblExperiencia");
					tblExperiencia.deleteAllRows();				
					for(PerspectivaTecnicaExperienciaDTO obj: colExperiencia){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblExperiencia.addRow(obj, new String[]{"obrigatorioExperiencia","descricaoExperiencia","detalheExperiencia",""}, null, null, new String[]{""}, null, null);
						}else{
							tblExperiencia.addRow(obj, new String[]{"obrigatorioExperiencia","descricaoExperiencia","detalheExperiencia",""}, null, null, new String[]{"gerarImgDelExperiencia"}, null, null);
						}
					}
				}
				
				if(colCompetencia != null){
					HTMLTable tblCompetenciasTecnicas =  document.getTableById("tblCompetenciasTecnicas");
					tblCompetenciasTecnicas.deleteAllRows();				
					for(CompetenciasTecnicasDTO obj: colCompetencia){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblCompetenciasTecnicas.addRow(obj, new String[]{"descricaoCompetenciasTecnicas","nivelCompetenciasTecnicas",""}, null, null, new String[]{""}, null, null);
						}else{
							tblCompetenciasTecnicas.addRow(obj, new String[]{"descricaoCompetenciasTecnicas","nivelCompetenciasTecnicas",""}, null, null, new String[]{"gerarImgDelCompetenciasTecnicas"}, null, null);
						}
					}
				}
				
				if(colComportamental != null){
					HTMLTable tblPerspectivaComportamental =  document.getTableById("tblPerspectivaComportamental");
					tblPerspectivaComportamental.deleteAllRows();				
					for(PerspectivaComportamentalFuncaoDTO obj: colComportamental){
						if(!requisicaoFuncaoDto.getFase().equals("etapa2")){
						tblPerspectivaComportamental.addRow(obj, new String[]{"descricaoPerspectivaComportamental","detalhePerspectivaComportamental",""}, null, null, new String[]{""}, null, null);
						}else{
							tblPerspectivaComportamental.addRow(obj, new String[]{"descricaoPerspectivaComportamental","detalhePerspectivaComportamental",""}, null, null, new String[]{"gerarImgDelPerspectivaComportamental"}, null, null);
						}
					}
				}
			}
		}
		
		
		CargosDTO cargosDTO = new CargosDTO();
		cargosDTO.setIdCargo(requisicaoFuncaoDto.getIdCargo());
		CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
		if(cargosDTO.getIdCargo() != null && cargosDTO.getIdCargo() > 0){
			cargosDTO = (CargosDTO) cargosService.restore(cargosDTO);
			requisicaoFuncaoDto.setCargo(cargosDTO.getNomeCargo());
		}
		
		if(requisicaoFuncaoDto.getFase() != null && requisicaoFuncaoDto.getFase().equalsIgnoreCase("finalizado")){
			requisicaoFuncaoDto.setFase("etapa1");
		}
			
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(requisicaoFuncaoDto);
		
		document.executeScript("controleEtapas()");

			
	}
	
	
	private void preencherCombosNivel(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboNivelPerspectivaComplexidade = (HTMLSelect) document.getSelectById("nivelPerspectivaComplexidade");
		HTMLSelect comboNivelCompetenciasTecnicas = (HTMLSelect) document.getSelectById("nivelCompetenciasTecnicas");
			
		this.inicializaCombo(comboNivelPerspectivaComplexidade, request);
		comboNivelPerspectivaComplexidade.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeAlta"));
		comboNivelPerspectivaComplexidade.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeBaixa"));
		comboNivelPerspectivaComplexidade.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeEspecialista"));
		comboNivelPerspectivaComplexidade.addOption("4", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeIntermediaria"));
		comboNivelPerspectivaComplexidade.addOption("5", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeMediana"));
			
		this.inicializaCombo(comboNivelCompetenciasTecnicas, request);	
		comboNivelCompetenciasTecnicas.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeAlta"));
		comboNivelCompetenciasTecnicas.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeBaixa"));
		comboNivelCompetenciasTecnicas.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeEspecialista"));
		comboNivelCompetenciasTecnicas.addOption("4", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeIntermediaria"));
		comboNivelCompetenciasTecnicas.addOption("5", UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeMediana"));
			
			
		
	}

	@Override
	public Class getBeanClass() {
		return RequisicaoFuncaoDTO.class;
	}


	/**
	 * Preenche combo de 'justificativa solicitação'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JustificativaRequisicaoFuncaoService justificativaRequisicaoFuncaoService = (JustificativaRequisicaoFuncaoService) ServiceLocator.getInstance().getService(JustificativaRequisicaoFuncaoService.class, null);

		Collection<JustificativaRequisicaoFuncaoDTO> colJustificativas = justificativaRequisicaoFuncaoService.listarAtivos();
		Collection<JustificativaRequisicaoFuncaoDTO> colJustificativas2 = justificativaRequisicaoFuncaoService.list();

		HTMLSelect comboJustificativaValidacao = (HTMLSelect) document.getSelectById("justificativaValidacao");
		document.getSelectById("justificativaValidacao").removeAllOptions();
		HTMLSelect comboJustificativaDescricaoFuncao = (HTMLSelect) document.getSelectById("justificativaDescricaoFuncao");
		document.getSelectById("justificativaDescricaoFuncao").removeAllOptions();
		inicializaCombo(comboJustificativaValidacao, request);
		inicializaCombo(comboJustificativaDescricaoFuncao, request);
		if (colJustificativas != null) {
			comboJustificativaValidacao.addOptions(colJustificativas, "idjustificativa", "descricao", null);
			comboJustificativaDescricaoFuncao.addOptions(colJustificativas, "idjustificativa", "descricao", null);
		}
	}

	/**
	 * Executa uma inicialização padrão para as combos. 
	 * 
	 * @param componenteCombo
	 * @param request
	 */
	public void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	/** Busca formação e preenche tabela formações*/
	public void buscaFormacaoAcademica(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoFuncaoDTO requisicaoFuncaoDto = (RequisicaoFuncaoDTO) document.getBean();
		
		if(requisicaoFuncaoDto.getIdFormacaoAcademica()==null || requisicaoFuncaoDto.getIdFormacaoAcademica()<=0){
			document.alert(UtilI18N.internacionaliza(request, "requisicaoFuncao.formacaoNaoCadastrada"));
			return;
		}
		
		FormacaoAcademicaDTO formacaoAcademicaDTO = new FormacaoAcademicaDTO();
		formacaoAcademicaDTO.setIdFormacaoAcademica(requisicaoFuncaoDto.getIdFormacaoAcademica());
		FormacaoAcademicaService formacaoAcademicaService = (FormacaoAcademicaService) ServiceLocator.getInstance().getService(FormacaoAcademicaService.class, null);
		formacaoAcademicaDTO = (FormacaoAcademicaDTO) formacaoAcademicaService.restore(formacaoAcademicaDTO);
		
		PerspectivaTecnicaFormacaoAcademicaDTO perspectivaTecnicaFormacaoAcademicaDTO = new PerspectivaTecnicaFormacaoAcademicaDTO();
		perspectivaTecnicaFormacaoAcademicaDTO.setDescricaoFormacaoAcademica(formacaoAcademicaDTO.getDescricao());
		perspectivaTecnicaFormacaoAcademicaDTO.setDetalheFormacaoAcademica(formacaoAcademicaDTO.getDetalhe());
		
		if (requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioFormacao() != null &&
				requisicaoFuncaoDto.getObrigatorioFormacao().equals("N")){
			perspectivaTecnicaFormacaoAcademicaDTO.setObrigatorioFormacaoAcademica("N");
		}else{
			perspectivaTecnicaFormacaoAcademicaDTO.setObrigatorioFormacaoAcademica("S");
		}
		if(requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioFormacao() == null){
			perspectivaTecnicaFormacaoAcademicaDTO.setObrigatorioFormacaoAcademica("N");
		}
		perspectivaTecnicaFormacaoAcademicaDTO.setIdFormacaoAcademica(formacaoAcademicaDTO.getIdFormacaoAcademica());
		
		HTMLTable tblFormacaoAcademica =  document.getTableById("tblFormacaoAcademica");
		tblFormacaoAcademica.addRow(perspectivaTecnicaFormacaoAcademicaDTO, new String[]{"obrigatorioFormacaoAcademica","descricaoFormacaoAcademica","detalheFormacaoAcademica",""}, null, null, new String[]{"gerarImgDelFormacaoAcademica"}, null, null);
		
		document.getElementById("idFormacaoAcademica").setValue("");
		document.getElementById("descricaoFormacaoAcademica").setValue("");
		document.getElementById("obrigatorioFormacao").setValue("");
	}
	
	/** Busca certificação e preenche tabela certificação*/
	public void buscaCertificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoFuncaoDTO requisicaoFuncaoDto = (RequisicaoFuncaoDTO) document.getBean();
		
		if(requisicaoFuncaoDto.getIdCertificacao() == null || requisicaoFuncaoDto.getIdCertificacao() <= 0){
			document.alert(UtilI18N.internacionaliza(request, "requisicaoFuncao.certificacaoNaoCadastrada"));
			return;
		}
		
		CertificacaoDTO certificacaoDTO = new CertificacaoDTO();
		certificacaoDTO.setIdCertificacao(requisicaoFuncaoDto.getIdCertificacao());
		CertificacaoService certificacaoService = (CertificacaoService) ServiceLocator.getInstance().getService(CertificacaoService.class, null);
		certificacaoDTO = (CertificacaoDTO) certificacaoService.restore(certificacaoDTO);
		
		PerspectivaTecnicaCertificacaoDTO perspectivaTecnicaCertificacaoDTO = new PerspectivaTecnicaCertificacaoDTO();
		perspectivaTecnicaCertificacaoDTO.setDescricaoCertificacao(certificacaoDTO.getDescricao());
		perspectivaTecnicaCertificacaoDTO.setVersaoCertificacao(certificacaoDTO.getDescricao());
		
		if (requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioCertificacao() != null &&
				requisicaoFuncaoDto.getObrigatorioCertificacao().equals("N")){
			perspectivaTecnicaCertificacaoDTO.setObrigatorioCertificacao("N");
		}else{
			perspectivaTecnicaCertificacaoDTO.setObrigatorioCertificacao("S");
		}
		if(requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioCertificacao() == null){
			perspectivaTecnicaCertificacaoDTO.setObrigatorioCertificacao("N");
		}

		perspectivaTecnicaCertificacaoDTO.setIdCertificacao(certificacaoDTO.getIdCertificacao());
		
		HTMLTable tblCertificacao =  document.getTableById("tblCertificacao");
		tblCertificacao.addRow(perspectivaTecnicaCertificacaoDTO, new String[]{"obrigatorioCertificacao","descricaoCertificacao","versaoCertificacao",""}, null, null, new String[]{"gerarImgDelCertificacao"}, null, null);

		document.getElementById("idCertificacao").setValue("");
		document.getElementById("descricaoCertificacao").setValue("");
	}
	
	/** Busca Curso e preenche tabela Curso*/
	public void buscaCurso(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoFuncaoDTO requisicaoFuncaoDto = (RequisicaoFuncaoDTO) document.getBean();
		
		if(requisicaoFuncaoDto.getIdCurso() == null || requisicaoFuncaoDto.getIdCurso() <= 0){
			document.alert(UtilI18N.internacionaliza(request, "requisicaoFuncao.cursoNaoCadastrado"));
			return;
		}
		
		CursoDTO cursoDTO = new CursoDTO();
		cursoDTO.setIdCurso(requisicaoFuncaoDto.getIdCurso());
		CursoService cursoService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
		cursoDTO = (CursoDTO) cursoService.restore(cursoDTO);
		
		PerspectivaTecnicaCursoDTO perspectivaTecnicaCursoDTO = new PerspectivaTecnicaCursoDTO();
		perspectivaTecnicaCursoDTO.setDescricaoCurso(cursoDTO.getDescricao());
		perspectivaTecnicaCursoDTO.setDetalheCurso(cursoDTO.getDetalhe());
		
		if (requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioCurso() != null &&
				requisicaoFuncaoDto.getObrigatorioCurso().equals("N")){
			perspectivaTecnicaCursoDTO.setObrigatorioCurso("N");
		}else{
			perspectivaTecnicaCursoDTO.setObrigatorioCurso("S");
		}
		if(requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioCurso() == null){
			perspectivaTecnicaCursoDTO.setObrigatorioCurso("N");
		}
		
		perspectivaTecnicaCursoDTO.setIdCurso(cursoDTO.getIdCurso());
		
		HTMLTable tblCurso =  document.getTableById("tblCurso");
		tblCurso.addRow(perspectivaTecnicaCursoDTO, new String[]{"obrigatorioCurso","descricaoCurso","detalheCurso",""}, null, null, new String[]{"gerarImgDelCurso"}, null, null);

		document.getElementById("idCurso").setValue("");
		document.getElementById("descricaoCurso").setValue("");
	}
	
	/** Busca Idioma e preenche tabela Idioma*/
	public void buscaIdioma(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoFuncaoDTO requisicaoFuncaoDto = (RequisicaoFuncaoDTO) document.getBean();
		
		if(requisicaoFuncaoDto.getIdIdioma() == null || requisicaoFuncaoDto.getIdIdioma() <= 0){
			document.alert(UtilI18N.internacionaliza(request, "requisicaoFuncao.IdiomaNaoCadastrado"));
			return;
		}
		
		IdiomaDTO idiomaDTO = new IdiomaDTO();
		idiomaDTO.setIdIdioma(requisicaoFuncaoDto.getIdIdioma());
		IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
		idiomaDTO = (IdiomaDTO) idiomaService.restore(idiomaDTO);
		
		PerspectivaTecnicaIdiomaDTO perspectivaTecnicaIdiomaDTO = new PerspectivaTecnicaIdiomaDTO();
		perspectivaTecnicaIdiomaDTO.setDescricaoIdioma(idiomaDTO.getDescricao());
		perspectivaTecnicaIdiomaDTO.setDetalheIdioma(idiomaDTO.getDetalhe());
		
		if (requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioIdioma() != null &&
				requisicaoFuncaoDto.getObrigatorioIdioma().equals("N")){
			perspectivaTecnicaIdiomaDTO.setObrigatorioIdioma("N");
		}else{
			perspectivaTecnicaIdiomaDTO.setObrigatorioIdioma("S");
		}
		if(requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioIdioma() == null){
			perspectivaTecnicaIdiomaDTO.setObrigatorioIdioma("N");
		}

		perspectivaTecnicaIdiomaDTO.setIdIdioma(idiomaDTO.getIdIdioma());

		HTMLTable tblIdioma =  document.getTableById("tblIdioma");
		tblIdioma.addRow(perspectivaTecnicaIdiomaDTO, new String[]{"obrigatorioIdioma","descricaoIdioma","detalheIdioma",""}, null, null, new String[]{"gerarImgDelIdioma"}, null, null);

		document.getElementById("idIdioma").setValue("");
		document.getElementById("descricaoIdioma").setValue("");
	}

	/** Busca Experiencia e preenche tabela Experiencia*/
	public void buscaExperiencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoFuncaoDTO requisicaoFuncaoDto = (RequisicaoFuncaoDTO) document.getBean();
		
		if(requisicaoFuncaoDto.getIdConhecimento() == null || requisicaoFuncaoDto.getIdConhecimento() <= 0){
			document.alert(UtilI18N.internacionaliza(request, "requisicaoFuncao.experienciaNaoCadastrada"));
			return;
		}
		
		ConhecimentoDTO conhecimentoDTO = new ConhecimentoDTO();
		conhecimentoDTO.setIdConhecimento(requisicaoFuncaoDto.getIdConhecimento());
		ConhecimentoService conhecimentoService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
		conhecimentoDTO = (ConhecimentoDTO) conhecimentoService.restore(conhecimentoDTO);
		
		PerspectivaTecnicaExperienciaDTO perspectivaTecnicaExperienciaDTO = new PerspectivaTecnicaExperienciaDTO();
		perspectivaTecnicaExperienciaDTO.setDescricaoExperiencia(conhecimentoDTO.getDescricao());
		perspectivaTecnicaExperienciaDTO.setDetalheExperiencia(conhecimentoDTO.getDetalhe());
		
		if (requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioExperiencia() != null &&
				requisicaoFuncaoDto.getObrigatorioExperiencia().equals("N")){
			perspectivaTecnicaExperienciaDTO.setObrigatorioExperiencia("N");
		}else{
			perspectivaTecnicaExperienciaDTO.setObrigatorioExperiencia("S");
		}
		if(requisicaoFuncaoDto != null && requisicaoFuncaoDto.getObrigatorioExperiencia() == null){
			perspectivaTecnicaExperienciaDTO.setObrigatorioExperiencia("N");
		}

		perspectivaTecnicaExperienciaDTO.setIdConhecimento(conhecimentoDTO.getIdConhecimento());

		HTMLTable tblExperiencia =  document.getTableById("tblExperiencia");
		tblExperiencia.addRow(perspectivaTecnicaExperienciaDTO, new String[]{"obrigatorioExperiencia","descricaoExperiencia","detalheExperiencia",""}, null, null, new String[]{"gerarImgDelExperiencia"}, null, null);

		document.getElementById("idConhecimento").setValue("");
		document.getElementById("descricaoExperiencia").setValue("");
	}

	
}