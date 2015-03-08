package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ModalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoServiceEjb;
import br.com.centralit.citcorpore.rh.negocio.HistoricoFuncionalService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class VisualizarCurriculoTrabalheConosco extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CandidatoDTO candidatoDTO = (CandidatoDTO) request.getSession().getAttribute("CANDIDATO");
		
		if (candidatoDTO == null) {
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location.href='" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
			return;
		}
		
		if(request.getParameter("iframe") == null || request.getParameter("iframe").equalsIgnoreCase("")) {
			document.executeScript("window.location.href='" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/visualizarCurriculoTrabalheConosco/visualizarCurriculoTrabalheConosco.load?iframe=true'");
			return ;
		}
		
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
		CurriculoDTO curriculoDto = new CurriculoDTO();
		
		curriculoDto = (CurriculoDTO) curriculoService.findIdByCpf(candidatoDTO.getCpf());
		
		if(curriculoDto == null || curriculoDto.equals("")) {
			document.alert("Você não possui currículo cadastrado!");
			document.executeScript("window.close()");
			return;
		}
		
		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);

		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);

		CandidatoDTO candidatoDto = (CandidatoDTO) candidatoService.restoreByCpf(curriculoDto.getCpf());
		
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		
		List listCidade = new ArrayList<>();
		listCidade = (List) cidadesService.findNomeByIdCidade(curriculoDto.getIdCidadeNatal());
		
		if(listCidade != null && listCidade.size() > 0){
			CidadesDTO cidadesAux = (CidadesDTO) listCidade.get(0);
			curriculoDto.setCidadeNatal(cidadesAux.getNomeCidade());
		}

		if (candidatoDto != null) {
			if (curriculoDto.getPortadorNecessidadeEspecial().equals("S")) {
				curriculoDto.setPortadorNecessidadeEspecial("Sim");
			} else {
				if (curriculoDto.getPortadorNecessidadeEspecial().equals("N")) {
					curriculoDto.setPortadorNecessidadeEspecial("Não");
				}
			}

			if (curriculoDto.getSexo().equalsIgnoreCase("F")) {
				curriculoDto.setSexo("Feminino");
			} else {
				if (curriculoDto.getSexo().equalsIgnoreCase("M")) {
					curriculoDto.setSexo("Masculino");
				}
			}
			ArrayList<EnderecoCurriculoDTO> enderecos = (ArrayList<EnderecoCurriculoDTO>) curriculoDto.getColEnderecos();
			if (enderecos != null && enderecos.size() > 0) {
				EnderecoCurriculoDTO enderecoCurriculoDto = enderecos.get(0);
				curriculoDto.setEnderecoCurriculoDto(enderecoCurriculoDto);
			}
			
			HTMLForm form = document.getForm("formModalCurriculo");
			form.clear();
			form.setValues(curriculoDto);
			
			HistoricoFuncionalService historicoFuncionalService = (HistoricoFuncionalService) ServiceLocator.getInstance().getService(HistoricoFuncionalService.class, null);
			Date dataUltimaAtualizacao = historicoFuncionalService.getUltimaAtualizacao(curriculoDto.getIdCurriculo());
			request.setAttribute("ultimaAtualizacao", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, dataUltimaAtualizacao, UtilI18N.getLocale()));
			
			document.getElementById("divFoto").setInnerHTML(dadosFotoCurriculo(curriculoDto).toString());
			document.getElementById("divDadosPessoais").setInnerHTML(dadosPessoias(curriculoDto, request).toString());
			document.getElementById("divHistoricoProfissional").setInnerHTML(historicoProfissional(curriculoDto, request).toString());
			document.getElementById("divFormacaoAcademica").setInnerHTML(formacaoAcademica(curriculoDto, request).toString());
			document.getElementById("divIdiomas").setInnerHTML(idiomas(curriculoDto, request).toString());
			document.getElementById("divCertificacao").setInnerHTML(certicacoes(curriculoDto, request).toString());
			document.getElementById("divCompetencia").setInnerHTML(competencias(curriculoDto, request).toString());
			document.getElementById("divAnexos").setInnerHTML(anexos(curriculoDto, request).toString());
			
		} else {
			document.alert("Nenhuma currículo encontrado!");
			document.executeScript("$('#modal_curriculo').modal('close');");
		}
	}

	/**
	 * Dados Pessoais.
	 * Metodo com a 1ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosPessoias(CurriculoDTO curriculoDto, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null) {
			sb.append("<div id='divInfoCandidatos' class='innerTB'>");
			sb.append("<h1 id='nomeCandidato' class='strong'>");
				if(curriculoDto.getNome() != null){
					sb.append(curriculoDto.getNome());
				}
			sb.append("</h1>");
			sb.append("<div>");
			sb.append("<p>");
				if(curriculoDto.getNacionalidade() != null){
					sb.append(curriculoDto.getNacionalidade());
				}
				if(curriculoDto.getDataNascimento() != null){
					sb.append(" "+UtilDatas.calculaIdade(curriculoDto.getDataNascimento())+" "+UtilI18N.internacionaliza(request, "citcorpore.texto.tempo.anos"));
				}
				if(curriculoDto.getEstadoCivilExtenso() != null){
					sb.append(", "+curriculoDto.getEstadoCivilExtenso());
				}
				if(curriculoDto.getFilhos() != null){
					sb.append(dadosPossuiFilhos(curriculoDto));
				}
				if(curriculoDto.getPortadorNecessidadeEspecial() !=null){
					if(!curriculoDto.getPortadorNecessidadeEspecial().equalsIgnoreCase("não")){
						sb.append(", "+UtilI18N.internacionaliza(request, "curriculo.pne"));
					}
				}
			sb.append("</p>");
				if(curriculoDto.getCpfFormatado() != null && curriculoDto.getCpfFormatado() != null){
					sb.append("<p>CPF "+curriculoDto.getCpfFormatado()+", nascido em "+curriculoDto.getCidadeNatal()+"</p>");
				}
				if(curriculoDto.getLogradouro() != null && curriculoDto.getComplemento() != null && curriculoDto.getNomeBairro() != null ){
					sb.append("<p>"+curriculoDto.getLogradouro().trim()+", "+curriculoDto.getComplemento()+" "+curriculoDto.getNomeBairro()+"</p>");
				}
				if(curriculoDto.getCep() != null && curriculoDto.getNomeCidade() != null && curriculoDto.getNomeUF() != null){
					sb.append("<p>"+curriculoDto.getCep()+" "+curriculoDto.getNomeCidade()+", "+curriculoDto.getNomeUF()+" - Brasil</p>");
				}
			sb.append("<p>"+dadosTelefones(curriculoDto, request)+"</p>");
			sb.append("<p>"+dadosEmail(curriculoDto)+"</p>");
			sb.append("</div>");
			sb.append("</div>");
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Historico Profissional
	 * Metodo com a 2ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 */
	private StringBuilder historicoProfissional(CurriculoDTO curriculoDto, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null && dadosHistoricoProfissional(curriculoDto, request).length() > 0) {
			sb.append("	<div class='innerTB'>");
			sb.append("		<h3 class='strong'>"+UtilI18N.internacionaliza(request, "rh.historicoProfissional")+"</h3>");
			sb.append("	</div>");
			sb.append(dadosHistoricoProfissional(curriculoDto, request));
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Formação academica
	 * Metodo com a 3ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 */
	private StringBuilder formacaoAcademica(CurriculoDTO curriculoDto, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null && dadosFormacaoAcademica(curriculoDto, request).length() > 0) {
			sb.append("	<div class='innerTB'>");
			sb.append("		<h3 class='strong'>"+UtilI18N.internacionaliza(request, "ManualFuncao.FormacaoAcademica")+"</h3>");
			sb.append("	</div>");
			sb.append(dadosFormacaoAcademica(curriculoDto, request));
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Informações de Idioma
	 * Metodo com a 4ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 */
	private StringBuilder idiomas(CurriculoDTO curriculoDto, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null && dadosIdiomas(curriculoDto, request).length() > 0) {
			sb.append("	<div class='innerTB'>");
			sb.append("		<h3 class='strong'>"+UtilI18N.internacionaliza(request, "solicitacaoCargo.idiomas")+"</h3>");
			sb.append("	</div>");
			sb.append(dadosIdiomas(curriculoDto, request));
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Informações das Certificações
	 * Metodo com a 5ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 */
	private StringBuilder certicacoes(CurriculoDTO curriculoDto, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null && dadosCertificacoes(curriculoDto, request).length() > 0) {
			sb.append("	<div class='innerTB'>");
			sb.append("		<h3 class='strong'>"+UtilI18N.internacionaliza(request, "ManualFuncao.Certificacoes")+"</h3>");
			sb.append("	</div>");
			sb.append(dadosCertificacoes(curriculoDto, request));
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Informações das competencias
	 * Metodo com a 6ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 */
	private StringBuilder competencias(CurriculoDTO curriculoDto, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null && dadosCompetencias(curriculoDto, request).length() > 0) {
			sb.append("	<div class='innerTB'>");
			sb.append("		<h3 class='strong'>"+UtilI18N.internacionaliza(request, "rh.competencias")+"</h3>");
			sb.append("	</div>");
			sb.append(dadosCompetencias(curriculoDto, request));
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Informações dos anexos
	 * Metodo com a 7ª parte dos dados do curriculo do candidato.
	 * 
	 * @author david.silva
	 * @throws Exception 
	 */
	private StringBuilder anexos(CurriculoDTO curriculoDto, HttpServletRequest request) throws Exception{
		StringBuilder sb = new StringBuilder();
		if (curriculoDto != null && dadosAnexos(curriculoDto, request).length() > 0) {
			sb.append("	<div class='innerTB'>");
			sb.append("		<h3 class='strong'>"+UtilI18N.internacionaliza(request, "agenda.anexos")+"</h3>");
			sb.append("	</div>");
			sb.append(dadosAnexos(curriculoDto, request));
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Metodo com coleção dos anexos
	 * 
	 * @author david.silva
	 * @throws Exception 
	 */
	private StringBuilder dadosAnexos(CurriculoDTO curriculoDto, HttpServletRequest request) throws Exception{
		StringBuilder sb = new StringBuilder();
		List list = new ArrayList<>();
		
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		list = (List) controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_CURRICULO, curriculoDto.getIdCurriculo());
		
		ControleGEDDTO controleGEDDTO = null;
		
		if(list != null && list.size() > 0){
			controleGEDDTO = (ControleGEDDTO) list.get(0);
			String str = "ID=" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();
			
			sb.append("<ul>");
			sb.append("<li>");
				if(controleGEDDTO.getDescricaoArquivo() != null){
					sb.append("<h4 class='strong'>"+controleGEDDTO.getDescricaoArquivo()+"</h4>");
				}
			sb.append("<p class='p-dados-anexos'>"+UtilI18N.internacionaliza(request, "rh.linkParaDownload"));
			sb.append(": <a href='" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/visualizarUploadTemp/visualizarUploadTemp.load?path=" + str + "' target='_blank'>"+UtilI18N.internacionaliza(request, "rh.linkDoAnexo")+"</a>");
			sb.append("</li>");
			sb.append("</ul>");
		
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Metodo com coleção de competencias
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosCompetencias(CurriculoDTO curriculoDto, HttpServletRequest request){
		ArrayList<CompetenciaCurriculoDTO> listaCompetencia = (ArrayList<CompetenciaCurriculoDTO>) curriculoDto.getColCompetencias();
		StringBuilder sb = new StringBuilder();
		if (listaCompetencia != null && listaCompetencia.size() > 0) {
			sb.append("<ul>");
			for (CompetenciaCurriculoDTO competencia : listaCompetencia) {
				sb.append("<li>");
				sb.append("<h4 class='strong'>"+competencia.getDescricaoCompetencia()+"</h4>");
				sb.append("</li>");
			}
			sb.append("</ul>");
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Metodo com coleção de certificações
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosCertificacoes(CurriculoDTO curriculoDto, HttpServletRequest request){
		ArrayList<CertificacaoCurriculoDTO> listaCertificacao = (ArrayList<CertificacaoCurriculoDTO>) curriculoDto.getColCertificacao();
		StringBuilder sb = new StringBuilder();
		if (listaCertificacao != null && listaCertificacao.size() > 0) {
			sb.append("<ul>");
			for (CertificacaoCurriculoDTO certificacao : listaCertificacao) {
				sb.append("<li>");
				sb.append("<h4 class='strong'>"+certificacao.getDescricao()+"</h4>");
				sb.append("<p>");
					if(certificacao.getVersao() != null && !certificacao.getVersao().equalsIgnoreCase("") ){
						sb.append(UtilI18N.internacionaliza(request, "citcorpore.comum.versao")+": "+certificacao.getVersao());
					}
					if(certificacao.getValidade() != null){
						if(!certificacao.getVersao().equalsIgnoreCase("")){
							sb.append(", ");
						}
						sb.append(UtilI18N.internacionaliza(request, "rh.anoValidade")+": "+certificacao.getValidade());
					}
				sb.append("<p>");
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
		
		return sb;
	}
	
	/**
	 * Metodo com coleção de idiomas
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosIdiomas(CurriculoDTO curriculoDto, HttpServletRequest request){
		ArrayList<IdiomaCurriculoDTO> listaIdiomas = (ArrayList<IdiomaCurriculoDTO>) curriculoDto.getColIdioma();
		StringBuilder sb = new StringBuilder();
		if (listaIdiomas != null && listaIdiomas.size() > 0) {
			sb.append("<ul>");
			for (IdiomaCurriculoDTO idioma : listaIdiomas) {
				sb.append("<li>");
				sb.append("<h4 class='strong'>"+idioma.getDescricaoIdioma()+"</h4>");
				if (idioma.getIdIdioma() != null) {
					sb.append("<p>");
					if(idioma.getIdNivelLeitura() != null){
						sb.append(UtilI18N.internacionaliza(request, "rh.leitura")+": ");
						if(idioma.getIdNivelLeitura().equals(1)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaBasico").replace("B", "b"));
						}else if(idioma.getIdNivelLeitura().equals(2)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaIntermediario").replace("I", "i"));
						}else if(idioma.getIdNivelLeitura().equals(3)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaAvancado").replace("A", "a"));
						}
					}	
					if(idioma.getIdNivelEscrita()!= null){
						sb.append(", "+UtilI18N.internacionaliza(request, "rh.escrita")+": ");
						if(idioma.getIdNivelEscrita().equals(1)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaBasico").replace("B", "b"));
						}else if(idioma.getIdNivelEscrita().equals(2)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaIntermediario").replace("I", "i"));
						}else if(idioma.getIdNivelEscrita().equals(3)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaAvancado").replace("A", "a"));
						}
					}
					if(idioma.getIdNivelConversa()!= null){
						sb.append(", "+UtilI18N.internacionaliza(request, "rh.conversacao")+": ");
						if(idioma.getIdNivelConversa().equals(1)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaBasico").replace("B", "b"));
						}else if(idioma.getIdNivelConversa().equals(2)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaIntermediario").replace("I", "i"));
						}else if(idioma.getIdNivelConversa().equals(3)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaAvancado").replace("A", "a"));
						}else if(idioma.getIdNivelConversa().equals(4)){
							sb.append(" "+UtilI18N.internacionaliza(request, "curriculo.idiomaFluente").replace("F", "f"));
						}
					}
					sb.append(".</p>");
				}
				sb.append("</li>");
			}
			sb.append("</ul>");
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Metodo com coleção de Formações Academicas
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosFormacaoAcademica(CurriculoDTO curriculoDto, HttpServletRequest request){
		ArrayList<FormacaoCurriculoDTO> listaFormacao = (ArrayList<FormacaoCurriculoDTO>) curriculoDto.getColFormacao();
		StringBuilder sb = new StringBuilder();
		if (listaFormacao != null && listaFormacao.size() > 0) {
			sb.append("<ul>");
				for (FormacaoCurriculoDTO formacao : listaFormacao) {
						if(formacao.getIdTipoFormacao() != null){
							sb.append("<h4 class='strong'>");
							if(formacao.getIdTipoFormacao().equals(1)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "rh.ensinoFundamental")+"</li>");
							}else if(formacao.getIdTipoFormacao().equals(2)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "rh.ensinoMedio")+"</li>");
							}else if(formacao.getIdTipoFormacao().equals(3)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "rh.graduacao")+"</li>");
							}else if(formacao.getIdTipoFormacao().equals(4)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "rh.posGraduacao")+"</li>");
							}else if(formacao.getIdTipoFormacao().equals(5)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "rh.mestrado")+"</li>");
							}else if(formacao.getIdTipoFormacao().equals(6)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "rh.doutorado")+"</li>");
							}else if(formacao.getIdTipoFormacao().equals(7)){
								sb.append("<li>"+UtilI18N.internacionaliza(request, "citcorpore.controleContrato.treinamento")+"</li>");
							}
							sb.append("</h4>");
						}
					sb.append("<p>"+formacao.getDescricao()+", "+formacao.getInstituicao());
						if(formacao.getIdSituacao() != null){
							sb.append(" - (");
							if(formacao.getIdSituacao().equals(1)){
								sb.append(UtilI18N.internacionaliza(request, "rh.concluido").replace("C", "c"));
							}else if(formacao.getIdSituacao().equals(2)){
								sb.append(UtilI18N.internacionaliza(request, "rh.cursando").replace("C", "c"));
							}else if(formacao.getIdSituacao().equals(3)){
								sb.append(UtilI18N.internacionaliza(request, "rh.trancadoInterrompido").replace("T", "t").replace("I", "i"));
							}
							sb.append(").</p>");
						}
					sb.append("</li>");
				}
			sb.append("</ul>");
		}
		return sb;
	}
	
	/**
	 * Metodo com coleção de Historico Profissional
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosHistoricoProfissional(CurriculoDTO curriculoDto, HttpServletRequest request) {
		ArrayList<ExperienciaProfissionalCurriculoDTO> listaExperiencia = (ArrayList<ExperienciaProfissionalCurriculoDTO>) curriculoDto.getColExperienciaProfissional();
		ArrayList<FuncaoExperienciaProfissionalCurriculoDTO> listaFuncao = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		ArrayList<Date> datasFuncoes = new ArrayList<Date>();
		if (listaExperiencia != null && listaExperiencia.size() > 0) {
			sb.append("<ul>");
				for (ExperienciaProfissionalCurriculoDTO experiencia : listaExperiencia) {
					listaFuncao = (ArrayList<FuncaoExperienciaProfissionalCurriculoDTO>) experiencia.getColFuncao();
					sb.append("<li><h4><b>"+experiencia.getDescricaoEmpresa()+"</b> - "+UtilI18N.internacionaliza(request, "rh.desde"));
					if (listaFuncao != null) {
						for (FuncaoExperienciaProfissionalCurriculoDTO funcao : listaFuncao) {
							datasFuncoes.add(funcao.getInicioFuncao());
						}
						if(datasFuncoes!= null && datasFuncoes.size() > 0){
							Collections.sort(datasFuncoes);
							sb.append(" "+UtilDatas.getMesAno(datasFuncoes.get(0)));
						}
						sb.append("</h4>");
						sb.append("<div>");
						for (FuncaoExperienciaProfissionalCurriculoDTO funcao : listaFuncao) {
							sb.append("<div>");
							sb.append("<h5 class='strong'>"+funcao.getNomeFuncao() + "</h5>");
							sb.append("<p>"+funcao.getDescricaoFuncao()+"<br>");
							sb.append("<i>"+UtilI18N.internacionaliza(request, "avaliacaocontrato.periodo")+" "+UtilI18N.internacionaliza(request, "rh.desde")+" "+UtilDatas.getMesAno(funcao.getInicioFuncao()));
								if(funcao.getFimFuncao() != null){
									sb.append(" a "+ UtilDatas.getMesAno(funcao.getFimFuncao())+"</p></i>");
								}else{
									sb.append("</p></i>");
								}
							sb.append("</div>");
						}
					}
					sb.append("</li>");
					sb.append("</div>");
				}
			sb.append("</ul>");
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Metodo apenas para saber se o candidato possui filhos
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosPossuiFilhos(CurriculoDTO curriculoDto){
		StringBuilder sb = new StringBuilder();
		if(curriculoDto.getFilhos() != null){
			if(curriculoDto.getFilhos().equalsIgnoreCase("N")){
				sb.append(", sem filhos");
			}else if(curriculoDto.getFilhos().equalsIgnoreCase("S")){
				sb.append(", possui filhos");
			}
		}else{
			sb.delete(0, sb.length());
		}
		return sb;
	}
	
	/**
	 * Metodo com endereço da Foto do curriculo do candidato
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosFotoCurriculo(CurriculoDTO curriculoDto) throws Exception {
		String url = ParametroUtil.getValor(Enumerados.ParametroSistema.URL_Sistema);
		StringBuilder sb = new StringBuilder();
		CurriculoServiceEjb curriculoServiceEjb = new CurriculoServiceEjb();
		String caminhoFoto = null;
		caminhoFoto = curriculoServiceEjb.retornarCaminhoFoto(curriculoDto.getIdCurriculo());
		curriculoDto.setCaminhoFoto(caminhoFoto);
		if (!caminhoFoto.equalsIgnoreCase(null) && !caminhoFoto.equalsIgnoreCase("")) {
			sb.append("<img src='" + curriculoDto.getCaminhoFoto() + "' />");
		} else {
			sb.append("<img src='" + url + "/novoLayout/common/theme/images/avatar.jpg'/>");
		}

		return sb;
	}
	
	/**
	 * Metodo com coleção de Telefones formatados
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosTelefones(CurriculoDTO curriculoDto, HttpServletRequest request) {
		
		ArrayList<TelefoneCurriculoDTO> listaTelefone = (ArrayList<TelefoneCurriculoDTO>) curriculoDto.getColTelefones();
		StringBuilder sb = new StringBuilder();
		if (listaTelefone != null && listaTelefone.size() > 0) {
			for (TelefoneCurriculoDTO telefone : listaTelefone) {
				sb.append("<p>" + telefone.getDescricaoTipoTelefone() + ": ("+telefone.getDdd()+") ");
				sb.append(telefone.getNumeroTelefone()+ "</p>");
				break;
			}
		}
		return sb;
		
	}
	
	/**
	 * Metodo com coleção de email
	 * 
	 * @author david.silva
	 */
	private StringBuilder dadosEmail(CurriculoDTO curriculoDto) {
		ArrayList<EmailCurriculoDTO> listaEmails = (ArrayList<EmailCurriculoDTO>) curriculoDto.getColEmail();
		StringBuilder sb = new StringBuilder();
		if (listaEmails != null && listaEmails.size() > 0) {
			for (EmailCurriculoDTO email : listaEmails) {
				if(email.getPrincipal() != null && email.getPrincipal().equalsIgnoreCase("S")){
					sb.append(email.getDescricaoEmail());
				}
			}
		}else {
			sb.delete(0, sb.length());
		}
		return sb;
	}

	@Override
	public Class getBeanClass() {
		return ModalCurriculoDTO.class;
	}
}
