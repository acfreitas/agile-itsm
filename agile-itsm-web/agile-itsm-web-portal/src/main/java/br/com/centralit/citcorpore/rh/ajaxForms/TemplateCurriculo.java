package br.com.centralit.citcorpore.rh.ajaxForms;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TreinamentoCurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.CursoDao;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.IdiomaService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

import com.google.gson.Gson;

@SuppressWarnings({ "rawtypes", "unchecked","unused" })
public class TemplateCurriculo extends AjaxFormAction {

	public Class getBeanClass() {
		return CurriculoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;

		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();

		IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
		Collection colIdioma = idiomaService.list();

		HTMLSelect idIdioma = document.getSelectById("idioma#idIdioma");
		idIdioma.addOption(" ", "--- Selecione ---");
		idIdioma.addOptions(colIdioma, "idIdioma", "descricao", null);

		HTMLSelect combopais = (HTMLSelect) document.getSelectById("pais");
		if (combopais != null) {
			this.preencherComboPaises(combopais, document, request, response);
		}

		/*HTMLSelect comboNacionalidade = (HTMLSelect) document.getSelectById("nacionalidade");
		if (comboNacionalidade != null) {
			this.preencherComboPaises(comboNacionalidade, document, request, response);
		}*/

		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("enderecoIdUF");
		if (comboUfs != null) {
			this.inicializarCombo(comboUfs, request);
		}

		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("enderecoIdCidade");
		if (comboCidades != null) {
			this.inicializarCombo(comboCidades, request);
		}

		this.inicializarLoad(document, request, response);

		String idCurriculoPesquisa = request.getParameter("idCurriculoPesquisa");
		if (idCurriculoPesquisa != null) {
			curriculoDto.setIdCurriculo(Integer.parseInt(idCurriculoPesquisa));
			restore(document, request, response);
		}

	}


	/**
	 * Restore completo do template do curriculo
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
		// CurriculoDao curriculoDao = new CurriculoDao();
		// curriculoDto = curriculoDao.findIdByCpf("014.462.941-02");

		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);

		// Nacionalidade
//		HTMLSelect comboNacionalidade = document.getSelectById("idNacionalidade");
//		if (curriculoDto.getIdNacionalidade()!=null){
//			document.getElementById("idNacionalidade").setValue(curriculoDto.getIdNacionalidade().toString());
//		}
//		this.preencherComboPaises(comboNacionalidade, document, request, response);

		//Cidade Natal
		HTMLSelect comboCidadeNatal = document.getSelectById("idCidadeNatal");
		if (curriculoDto.getIdEstadoNatal()!=null){
			document.getElementById("idUfNatal").setValue(curriculoDto.getIdEstadoNatal().toString());
		}
		this.preencherComboCidadeNatal(document, request, response);

		// Uf
		this.preencherComboUfs(document, request, response);
		// Cidade
		this.preencherComboCidades(document, request, response);



		if (curriculoDto.getDataNascimento() == null) {
			document.executeScript("$('spnIdade').innerHTML = ''");
		} else {
			String textoIdade = UtilDatas.calculaIdade(curriculoDto.getDataNascimento(), "LONG");
			document.executeScript("$('spnIdade').innerHTML = '" + textoIdade + "'");
		}

		HTMLTable tblTelefones = document.getTableById("tblTelefones");
		tblTelefones.deleteAllRows();
		Collection<TelefoneCurriculoDTO> colTelefones = curriculoDto.getColTelefones();

		if(colTelefones != null)
			for (TelefoneCurriculoDTO telefone : colTelefones) {
				if (telefone.getIdTipoTelefone() != null) {
					if (telefone.getIdTipoTelefone().intValue() == 1) {
						telefone.setDescricaoTipoTelefone("Residencial");
					} else {
						telefone.setDescricaoTipoTelefone("Celular");
					}
				} else {
					telefone.setDescricaoTipoTelefone("");
				}
			}
		if (curriculoDto.getColTelefones() != null) {
			tblTelefones.addRowsByCollection(curriculoDto.getColTelefones(), new String[] { "ddd", "numeroTelefone", "descricaoTipoTelefone", "" }, null,
					"Já existe registrado esta demanda na tabela", new String[] { "gerarImgDel" }, "funcaoClickRowTelefone", null);
		}
		tblTelefones.applyStyleClassInAllCells("celulaGrid");

		HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
		tblCertificacao.deleteAllRows();
		if (curriculoDto.getColCertificacao() != null) {
			tblCertificacao.addRowsByCollection(curriculoDto.getColCertificacao(), new String[] { "descricao", "versao", "validade", "" }, null, "Já existe registrado esta demanda na tabela",
					new String[] { "gerarImagemDelCertificacao" }, "funcaoClickRowCertificacao", null);
		}
		tblCertificacao.applyStyleClassInAllCells("celulaGrid");

		HTMLTable tblIdioma = document.getTableById("tblIdioma");
		tblIdioma.deleteAllRows();

		if(curriculoDto.getColIdioma() != null) {
			for (Object idioma : curriculoDto.getColIdioma()) {
				IdiomaCurriculoDTO idiomaCurriculoDTO = (IdiomaCurriculoDTO) idioma;
				if (idiomaCurriculoDTO.getIdIdioma() != null) {
					if (idiomaCurriculoDTO.getIdNivelConversa().intValue() == 1) {
						idiomaCurriculoDTO.setDescIdNivelConversa("Básico");
					}
					if (idiomaCurriculoDTO.getIdNivelConversa().intValue() == 2) {
						idiomaCurriculoDTO.setDescIdNivelConversa("Intermediário");
					}
				}
				if (idiomaCurriculoDTO.getIdNivelConversa().intValue() == 3) {
					idiomaCurriculoDTO.setDescIdNivelConversa("Avançado");
				}
				if (idiomaCurriculoDTO.getIdNivelConversa().intValue() == 4) {
					idiomaCurriculoDTO.setDescIdNivelConversa("Fluente");
				}
				if (idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 1) {
					idiomaCurriculoDTO.setDescIdNivelEscrita("Básico");
				}
				if (idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 2) {
					idiomaCurriculoDTO.setDescIdNivelEscrita("Intermediário");
				}
				if (idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 3) {
					idiomaCurriculoDTO.setDescIdNivelEscrita("Avançado");
				}
				if (idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 1) {
					idiomaCurriculoDTO.setDescIdNivelLeitura("Básico");
				}
				if (idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 2) {
					idiomaCurriculoDTO.setDescIdNivelLeitura("Intermediário");
				}
				if (idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 3) {
					idiomaCurriculoDTO.setDescIdNivelLeitura("Avançado");
				}
			}
		}

		if (curriculoDto.getColIdioma() != null) {
			tblIdioma.addRowsByCollection(curriculoDto.getColIdioma(), new String[] { "descricaoIdioma", "descIdNivelEscrita", "descIdNivelLeitura", "descIdNivelConversa", "" }, null,
					"Já existe registrado esta demanda na tabela", new String[] { "gerarImagemDelIdioma" }, "funcaoClickRowIdioma", null);
		}
		tblIdioma.applyStyleClassInAllCells("celulaGrid");

		HTMLTable tblEnderecos = document.getTableById("tblEnderecos");
		tblEnderecos.deleteAllRows();
		if (curriculoDto.getColEnderecos() != null) {
			Integer auxLinha = 0;
			for (Object obj2 : curriculoDto.getColEnderecos()) {
				auxLinha = auxLinha+1;
				EnderecoCurriculoDTO enderecoCurriculoDTO = (EnderecoCurriculoDTO) obj2;
				if (enderecoCurriculoDTO.getPrincipal() != null && !enderecoCurriculoDTO.getPrincipal().equals("")) {
					if (enderecoCurriculoDTO.getPrincipal().equalsIgnoreCase("S")){
						curriculoDto.setAuxEnderecoPrincipal(auxLinha);
						document.executeScript("validaPrincipalEndereco("+curriculoDto.getAuxEnderecoPrincipal()+")");
					}
					else{
						if(curriculoDto.getAuxEnderecoPrincipal() == null || curriculoDto.getAuxEnderecoPrincipal().equals(0)){
							curriculoDto.setAuxEnderecoPrincipal(0);
						}
						document.executeScript("validaPrincipalEndereco("+curriculoDto.getAuxEnderecoPrincipal()+")");
					}
				}
			}
			for(Object obj: curriculoDto.getColEnderecos()){
				EnderecoCurriculoDTO enderecoCurriculoDTO = (EnderecoCurriculoDTO) obj;
				String strDetalhamento = "";
				if(enderecoCurriculoDTO.getPrincipal().equals("S")){
					strDetalhamento = "<i>Correspondência</i>";
				}

				if(enderecoCurriculoDTO.getIdTipoEndereco() == 1){
					strDetalhamento = strDetalhamento +" <i>(Residencial)</i></br>";
				}else{
					strDetalhamento = strDetalhamento +" <i>(Comercial)</i></br>";
				}

				strDetalhamento = strDetalhamento + enderecoCurriculoDTO.getLogradouro()+ "," + (enderecoCurriculoDTO.getComplemento() != null ? " " + enderecoCurriculoDTO.getComplemento() + "," : "") + " " + enderecoCurriculoDTO.getNomeBairro() + "</br>";
				strDetalhamento = strDetalhamento + enderecoCurriculoDTO.getCep() + ", " + enderecoCurriculoDTO.getNomeCidade();
				strDetalhamento = strDetalhamento + ", " + enderecoCurriculoDTO.getNomeUF();
				strDetalhamento = strDetalhamento + ", " + enderecoCurriculoDTO.getNomePais();

				enderecoCurriculoDTO.setDescricaoEndereco(strDetalhamento);

				tblEnderecos.addRow(enderecoCurriculoDTO, new String[]{"descricaoEndereco", ""}, null,  "Já existe registrado esta informação na tabela", new String[]{"gerarImagemDelEndereco"}, "funcaoClickRowEndereco", null);
			}

		}
		document.executeScript("validaPrincipalEndereco("+curriculoDto.getAuxEnderecoPrincipal()+")");
		tblEnderecos.applyStyleClassInAllCells("celulaGrid");

		HTMLTable tblFormacao = document.getTableById("tblFormacao");
		tblFormacao.deleteAllRows();
		if (curriculoDto.getColFormacao() != null) {
			tblFormacao.addRowsByCollection(curriculoDto.getColFormacao(), new String[] { "descricaoTipoFormacao", "descricaoSituacao", "instituicao", "descricao", "" }, null,
					"Já existe registrado esta informação na tabela", new String[] { "gerarImagemDelFormacao" }, "funcaoClickRowFormacao", null);
		}
		tblFormacao.applyStyleClassInAllCells("celulaGrid");



		CursoDao cursoDao = new CursoDao();
		Collection<CursoDTO> colCursos = cursoDao.list();
		HTMLTable tblTreinamento =  document.getTableById("tblTreinamento");
		tblTreinamento.deleteAllRows();
		if (curriculoDto.getColTreinamentos() != null) {
			for(TreinamentoCurriculoDTO treinamentoCurriculoDTO: curriculoDto.getColTreinamentos()){
					if(colCursos != null){
						for(CursoDTO cursoDTO: colCursos){
							if(treinamentoCurriculoDTO.getIdCurso() == cursoDTO.getIdCurso()){
								treinamentoCurriculoDTO.setTreinamento(cursoDTO.getDescricao());
								treinamentoCurriculoDTO.setDescricaoTreinamento(cursoDTO.getDetalhe());
								treinamentoCurriculoDTO.setIdCurso(cursoDTO.getIdCurso());

								tblTreinamento.addRow(treinamentoCurriculoDTO, new String[]{"treinamento","descricaoTreinamento" ,""}, null, null, new String[]{"gerarImagemDelTreinamento"}, null, null);
							}
					}
				}
			}
		}



		HTMLTable tblEmail = document.getTableById("tblEmail");
		tblEmail.deleteAllRows();
		if (curriculoDto.getColEmail() != null) {
			Integer auxLinha = 0;
			for (Object obj2 : curriculoDto.getColEmail()) {
				auxLinha = auxLinha+1;
				EmailCurriculoDTO emailCurriculoDTO = (EmailCurriculoDTO) obj2;
				if (emailCurriculoDTO.getPrincipal() != null && !emailCurriculoDTO.getPrincipal().equals("")) {
					if (emailCurriculoDTO.getPrincipal().equalsIgnoreCase("S") || emailCurriculoDTO.getPrincipal().equalsIgnoreCase("S")) {
						emailCurriculoDTO.setImagemEmailprincipal("S");
						curriculoDto.setAuxEmailPrincipal(auxLinha);
					}else{
						if(curriculoDto.getAuxEmailPrincipal() == null || curriculoDto.getAuxEmailPrincipal().equals(0)){
							curriculoDto.setAuxEmailPrincipal(0);
						}
						document.executeScript("validaPrincipalEmail("+curriculoDto.getAuxEmailPrincipal()+")");
					}
				}
			}
			tblEmail.addRowsByCollection(curriculoDto.getColEmail(), new String[] { "descricaoEmail", "principal", "" }, null, "Já existe registrado esta informação na tabela",
					new String[] { "gerarImgDelEmail" }, "funcaoClickRowEmail", null);
			document.executeScript("validaPrincipalEmail("+curriculoDto.getAuxEmailPrincipal()+")");
		}


		// Experiencias
		if (curriculoDto.getColExperienciaProfissional() != null) {
			StringBuilder experienciasHtml = new StringBuilder();
			Collection<ExperienciaProfissionalCurriculoDTO> listaExperienciaProfissionalCurriculoDTO =  curriculoDto.getColExperienciaProfissional();

			if(!listaExperienciaProfissionalCurriculoDTO.isEmpty()) {
				// contador
				int count = 1;
				for(ExperienciaProfissionalCurriculoDTO experienciaProfissionalCurriculoDTO : listaExperienciaProfissionalCurriculoDTO) {
					experienciasHtml.append("<div class='widget widget-experiencias'>");
					experienciasHtml.append("	<span class='close close-experiencias'>&times;</span>");
					experienciasHtml.append("	<div class='widget-head'>");
					experienciasHtml.append("		<h4 class='heading'>" + UtilI18N.internacionaliza(request, "rh.empresa") + " <strong class='widget-experiencias-count'>" + count++ + "</strong></h4>");
					experienciasHtml.append("	</div><!-- .widget-head -->");
					experienciasHtml.append("	<div class='widget-body'>");
					experienciasHtml.append("		<div class='row-fluid'>");
					experienciasHtml.append("			<div class='span6'>");
					experienciasHtml.append("				<!-- nome da empresa -->");
					experienciasHtml.append("				<label class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "lookup.nomeEmpresa") + "</label>");
					experienciasHtml.append("				<div class='controls'>");
					experienciasHtml.append("					<input type='text' class='span12 experiencia-empresa' value='" + experienciaProfissionalCurriculoDTO.getDescricaoEmpresa() + "' name='experiencia-empresa[]' maxlength='80' />");
					experienciasHtml.append("				</div><!-- nome da empresa -->");
					experienciasHtml.append("			</div><!-- .span6 -->");
					experienciasHtml.append("			<div class='span6'>");
					experienciasHtml.append("				<!-- localidade -->");
					experienciasHtml.append("				<label class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "menu.nome.localidade") + "</label>");
					experienciasHtml.append("				<div class='controls'>");
					experienciasHtml.append("					<input type='text' class='span12 experiencia-localidade' value='" + experienciaProfissionalCurriculoDTO.getLocalidade() + "' name='experiencia-localidade[]' maxlength='80' />");
					experienciasHtml.append("				</div><!-- localidade -->");
					experienciasHtml.append("			</div><!-- .span6 -->");
					experienciasHtml.append("		</div><!-- .row-fluid -->");
					experienciasHtml.append("		<div class='widget-funcoes-container'>");

					Collection<FuncaoExperienciaProfissionalCurriculoDTO> listaFuncaoExperienciaProfissionalCurriculoDTO = experienciaProfissionalCurriculoDTO.getColFuncao();

					if(listaFuncaoExperienciaProfissionalCurriculoDTO != null && !listaFuncaoExperienciaProfissionalCurriculoDTO.isEmpty()) {
						int countFuncao = 1;

						for(FuncaoExperienciaProfissionalCurriculoDTO funcaoExperienciaProfissionalCurriculoDTO : listaFuncaoExperienciaProfissionalCurriculoDTO) {
							boolean experienciaTrabalhoAtual = false;
							if(UtilDatas.dateToSTR(funcaoExperienciaProfissionalCurriculoDTO.getFimFuncao(), "yyyy-MM-dd") == null || UtilDatas.dateToSTR(funcaoExperienciaProfissionalCurriculoDTO.getFimFuncao(), "yyyy-MM-dd") == "") {
								experienciaTrabalhoAtual = true;
							}
							experienciasHtml.append("		<div class='widget widget-funcoes'>");
							experienciasHtml.append("			<span class='close close-funcoes'>&times;</span>");
							experienciasHtml.append("			<div class='widget-head'>");
							experienciasHtml.append("				<h4 class='heading'>" + UtilI18N.internacionaliza(request, "rh.funcao") + " <strong class='widget-funcoes-count'>" + countFuncao++ + "</strong></h4>");
							experienciasHtml.append("			</div><!-- .widget-head -->");
							experienciasHtml.append("			<div class='widget-body'>");
							experienciasHtml.append("				<div class='row-fluid'>");
							experienciasHtml.append("					<div class=''>");
							experienciasHtml.append("						<label class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "rh.funcao") + "</label>");
							experienciasHtml.append("						<div class='controls'>");
							experienciasHtml.append("							<input type='text' class='span12 experiencia-nomeFuncao' value='" + funcaoExperienciaProfissionalCurriculoDTO.getNomeFuncao() + "' name='experiencia-nomeFuncao[]' maxlength='80' />");
							experienciasHtml.append("						</div><!-- .controls -->");
							experienciasHtml.append("					</div>");
							experienciasHtml.append("					<div class=''>");
							experienciasHtml.append("						<label class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "curriculo.descricaoFuncao") + "</label>");
							experienciasHtml.append("						<div class='controls'>");
							experienciasHtml.append("							<textarea rows='' cols='' class='span12 experiencia-descricaoFuncao' name='experiencia-descricaoFuncao[]' maxlength='600'>" + funcaoExperienciaProfissionalCurriculoDTO.getDescricaoFuncao() + "</textarea>");
							experienciasHtml.append("						</div><!-- .controls -->");
							experienciasHtml.append("					</div>");
							experienciasHtml.append("				</div><!-- .row-fluid -->");
							experienciasHtml.append("				<div class='row-fluid'>");
							experienciasHtml.append("					<div class='row-fluid'>");
							experienciasHtml.append("						<div class='span6'>");
							experienciasHtml.append("							<label class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "citcorpore.texto.periodo") + "</label>");
							experienciasHtml.append("						</div><!-- .span6 -->");
							experienciasHtml.append("						<div class='span6'>");
							experienciasHtml.append("							<label class='checkbox inline diasAtuais-label'>");
							experienciasHtml.append("								<input type='checkbox' " + (experienciaTrabalhoAtual ? "checked='checked'" : "") + " name='diasAtuais' class='diasAtuais send_left' value='s' />&nbsp;" + UtilI18N.internacionaliza(request, "rh.dias.atuais"));
							experienciasHtml.append("							</label>");
							experienciasHtml.append("						</div><!-- .span6 -->");
							experienciasHtml.append("					</div><!-- .row-fluid -->");

							int experienciaMesInicio = (funcaoExperienciaProfissionalCurriculoDTO.getInicioFuncao() == null ? 0 : UtilDatas.getMonth(funcaoExperienciaProfissionalCurriculoDTO.getInicioFuncao()));
							int experienciaMesFim = (funcaoExperienciaProfissionalCurriculoDTO.getFimFuncao() == null ? 0 : UtilDatas.getMonth(funcaoExperienciaProfissionalCurriculoDTO.getFimFuncao()));
							int experienciaAnoFim = (funcaoExperienciaProfissionalCurriculoDTO.getFimFuncao() == null ? 0 : UtilDatas.getYear(funcaoExperienciaProfissionalCurriculoDTO.getFimFuncao()));

							experienciasHtml.append("					<div class='row-fluid'>");
							experienciasHtml.append("						<div class='span3'>");
							experienciasHtml.append("							<label for='experiencia-mesInicio' class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "rh.mesInicio") + "</label>");
							experienciasHtml.append("							<select name='experiencia-mesInicio[]' class='span12 experiencia-mesInicio'>");
							experienciasHtml.append("								<option value=''>" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "</option>");
							experienciasHtml.append("								<option value='1' " + (experienciaMesInicio == 1 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.janeiro") + "</option>");
							experienciasHtml.append("								<option value='2' " + (experienciaMesInicio == 2 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.fevereiro") + "</option>");
							experienciasHtml.append("								<option value='3' " + (experienciaMesInicio == 3 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.marco") + "</option>");
							experienciasHtml.append("								<option value='4' " + (experienciaMesInicio == 4 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.abril") + "</option>");
							experienciasHtml.append("								<option value='5' " + (experienciaMesInicio == 5 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.maio") + "</option>");
							experienciasHtml.append("								<option value='6' " + (experienciaMesInicio == 6 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.junho") + "</option>");
							experienciasHtml.append("								<option value='7' " + (experienciaMesInicio == 7 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.julho") + "</option>");
							experienciasHtml.append("								<option value='8' " + (experienciaMesInicio == 8 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.agosto") + "</option>");
							experienciasHtml.append("								<option value='9' " + (experienciaMesInicio == 9 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.setembro") + "</option>");
							experienciasHtml.append("								<option value='10' " + (experienciaMesInicio == 10 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.outubro") + "</option>");
							experienciasHtml.append("								<option value='11' " + (experienciaMesInicio == 11 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.novembro") + "</option>");
							experienciasHtml.append("								<option value='12' " + (experienciaMesInicio == 12 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.dezembro") + "</option>");
							experienciasHtml.append("							</select>");
							experienciasHtml.append("						</div><!-- .span3 -->");
							experienciasHtml.append("						<div class='span2'>");
							experienciasHtml.append("							<label for='experiencia-anoInicio' class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "rh.anoInicio") + "</label>");
							experienciasHtml.append("							<input type='text' value='" + UtilDatas.getYear(funcaoExperienciaProfissionalCurriculoDTO.getInicioFuncao()) + "' onkeypress='return somenteNumero(event);' class='span12 experiencia-anoInicio' name='experiencia-anoInicio[]' maxlength='4' />");
							experienciasHtml.append("						</div><!-- .span12 -->");
							experienciasHtml.append("						<div class='escondedatafinal' style='" + (experienciaTrabalhoAtual ? "display: none;" : "") + "'>");
							experienciasHtml.append("							<span></span><!-- So foi adicionado para alinhar o elemento .diasAtuais -->");
							experienciasHtml.append("							<div class='span1 div-a-container'>");
							experienciasHtml.append("								<label for='div_a' class='span12'></label>");
							experienciasHtml.append("								<div class='span12' name='div_a' style='text-align: center;'>a</div>");
							experienciasHtml.append("							</div><!-- .span1 -->");
							experienciasHtml.append("							<div class='span3'>");
							experienciasHtml.append("								<label class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "rh.mesFim") + "</label>");
							experienciasHtml.append("								<select name='experiencia-mesFim[]' class='span12 experiencia-mesFim'>");
							experienciasHtml.append("									<option value=''>" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "</option>");
							experienciasHtml.append("									<option value='1' " + (experienciaMesFim == 1 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.janeiro") + "</option>");
							experienciasHtml.append("									<option value='2' " + (experienciaMesFim == 2 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.fevereiro") + "</option>");
							experienciasHtml.append("									<option value='3' " + (experienciaMesFim == 3 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.marco") + "</option>");
							experienciasHtml.append("									<option value='4' " + (experienciaMesFim == 4 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.abril") + "</option>");
							experienciasHtml.append("									<option value='5' " + (experienciaMesFim == 5 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.maio") + "</option>");
							experienciasHtml.append("									<option value='6' " + (experienciaMesFim == 6 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.junho") + "</option>");
							experienciasHtml.append("									<option value='7' " + (experienciaMesFim== 7 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.julho") + "</option>");
							experienciasHtml.append("									<option value='8' " + (experienciaMesFim == 8 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.agosto") + "</option>");
							experienciasHtml.append("									<option value='9' " + (experienciaMesFim == 9 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.setembro") + "</option>");
							experienciasHtml.append("									<option value='10' " + (experienciaMesFim == 10 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.outubro") + "</option>");
							experienciasHtml.append("									<option value='11' " + (experienciaMesFim == 11 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.novembro") + "</option>");
							experienciasHtml.append("									<option value='12' " + (experienciaMesFim == 12 ? "selected='selected'" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.texto.mes.dezembro") + "</option>");
							experienciasHtml.append("								</select>");
							experienciasHtml.append("							</div><!-- .span3 -->");
							experienciasHtml.append("							<div class='span2'>");
							experienciasHtml.append("								<label for='experiencia-anoFim' class='strong campoObrigatorio'>" + UtilI18N.internacionaliza(request, "rh.anoFim") + "</label>");
							experienciasHtml.append("								<input type='text' value='" + (experienciaAnoFim == 0 ? "" : experienciaAnoFim) + "' class='span12 experiencia-anoFim'  onkeypress='return somenteNumero(event);' name='experiencia-anoFim[]' maxlength='4' />");
							experienciasHtml.append("							</div><!-- .span12 -->");
							experienciasHtml.append("						</div><!-- .escondedatafinal -->");
							experienciasHtml.append("					</div><!-- .row-fluid -->");
							experienciasHtml.append("				</div><!-- .row-fluid -->");
							experienciasHtml.append("			</div><!-- .widget-body -->");
							experienciasHtml.append("		</div><!-- .widget -->");
						}
					}
					experienciasHtml.append("		</div><!-- .widget-funcoes-container -->");
					experienciasHtml.append("		<button id='add-funcoes-item' type='button' class='btn btn-small btn-primary btn-icon glyphicons circle_plus'><i></i>" + UtilI18N.internacionaliza(request, "rh.adicionarFuncao") + "</button>");
					experienciasHtml.append("	</div><!-- .widget-body -->");
					experienciasHtml.append("</div><!-- .widget-experiencias -->");
				}
				experienciasHtml.append("<button id='add-experiencias-item' type='button' class='btn btn-large btn-primary btn-icon glyphicons circle_plus'><i></i>" + UtilI18N.internacionaliza(request, "rh.adicionarEmpresa") + "</button>");

				document.getElementById("experiencia-container").setInnerHTML(experienciasHtml.toString());
			} else {
				document.executeScript("$('#experiencia-nao-possui').attr('checked', 'checked');$('#experiencia-container').addClass('disable')");
			}
		}

		HTMLTable tblCompetencia = document.getTableById("tblCompetencia");
		tblCompetencia.deleteAllRows();
		if (curriculoDto.getColCompetencias() != null) {
			tblCompetencia.addRowsByCollection(curriculoDto.getColCompetencias(), new String[] { "descricaoCompetencia", "nivelCompetenciaDesc", "" }, null, "Já existe registrado esta informação na tabela",
					new String[] { "gerarImagemDelCompetencia" }, "funcaoClickRowCompetencia", null);
		}

		document.executeScript("uploadAnexos.refresh()");
		restaurarAnexos(document, request, response, curriculoDto.getIdCurriculo());

		if(curriculoDto.getFilhos() != null && curriculoDto.getFilhos().equals("S")){
			document.getRadioById("filhosS").setChecked(true);
		}else{
			document.getRadioById("filhosN").setChecked(true);
		}

		if(curriculoDto.getPortadorNecessidadeEspecial() != null && curriculoDto.getPortadorNecessidadeEspecial().equals("S")){
			document.getRadioById("portadorNecessidadeEspecialS").setChecked(true);
		}else{
			document.getRadioById("portadorNecessidadeEspecialN").setChecked(true);
		}

		HTMLForm form = document.getForm("form");
		form.setValues(curriculoDto);

		document.executeScript("manipulaInputQtdeFilhos()");
		document.executeScript("manipulaDivDeficiencia()");
	}

	protected void restaurarAnexos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idCurriculo) throws ServiceException, Exception {

		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_CURRICULO, idCurriculo);
		Collection colAnexosFotos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, idCurriculo);
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		colAnexosFotos = controleGedService.convertListControleGEDToUploadDTO(colAnexosFotos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);

		if (colAnexosFotos != null && colAnexosFotos.size() > 0) {
			Iterator it = colAnexosFotos.iterator();
			// UploadFileDTO uploadItem = (UploadFileDTO)it.next();
			UploadDTO uploadItem = (UploadDTO) it.next();

			document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img src=\"" + uploadItem.getCaminhoRelativo() + "\" />'");
		} else {
			document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img src=\"" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/novoLayout/common/theme/images/avatar.jpg\" />'");
		}
	}

	public void calculaIdade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();

		if (curriculoDto.getDataNascimento() == null) {
			document.executeScript("document.getElementById('spnIdade').innerHTML = ''");
			document.alert("Informe uma data válida para a Data de Nascimento!");
			return;
		}

		String textoIdade = UtilDatas.calculaIdade(curriculoDto.getDataNascimento(), "LONG");

		document.executeScript("document.getElementById('spnIdade').innerHTML = '" + textoIdade + "'");
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		try {

			CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
			CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);

			Collection colTelefones = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(TelefoneCurriculoDTO.class, "colTelefones_Serialize", request);
			Collection<EnderecoCurriculoDTO> colEnderecos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EnderecoCurriculoDTO.class, "colEnderecos_Serialize", request);
			Collection colFormacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FormacaoCurriculoDTO.class, "colFormacao_Serialize", request);
			Collection colEmail = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EmailCurriculoDTO.class, "colEmail_Serialize", request);
			Collection colCertificacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CertificacaoCurriculoDTO.class, "colCertificacao_Serialize", request);
			Collection colIdioma = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(IdiomaCurriculoDTO.class, "colIdioma_Serialize", request);
			Collection<CompetenciaCurriculoDTO> colCompetencias = br.com.citframework.util.WebUtil
					.deserializeCollectionFromRequest(CompetenciaCurriculoDTO.class, "colCompetencias_Serialize", request);

			Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaAux = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ExperienciaProfissionalCurriculoDTO.class,
					"colExperienciaProfissional_Serialize", request);
			Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaProfissional = new ArrayList<ExperienciaProfissionalCurriculoDTO>();
			if(colExperienciaAux != null && !colExperienciaAux.isEmpty()) {
				for (ExperienciaProfissionalCurriculoDTO experienciaDTO : colExperienciaAux) {
					Collection colFuncaoExperienciaProfissional = br.com.citframework.util.WebUtil.deserializeCollectionFromString(FuncaoExperienciaProfissionalCurriculoDTO.class,	experienciaDTO.getColFuncaoSerialize());
					experienciaDTO.setColFuncao(colFuncaoExperienciaProfissional);
					colExperienciaProfissional.add(experienciaDTO);
				}
			}
			UfDTO obj = new UfDTO();
			try {
				obj.setIdPais(Integer.parseInt(request.getParameter("idPais")));
			} catch(Exception e) {
				e.printStackTrace();
			}
			UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);
			List<UfDTO> ufs = (List) ufService.listByIdPais(obj);

			// TODO É NECESSÁRIO CORRIGIR TODA ESSA LÓGICA. O CORRETO É QUE O ID DA CIDADE JÁ VENHA SERIALIZADO DA TELA. DEVIDO A URGÊNCIA NA TRATATIVA FOI NECESSÁRIO EFETUAR A CORREÇÃO DE CIDADES COM
			// A MESMA LÓGICA QUE JÁ HAVIA SIDO IMPLEMENTADA.
			// adicionar idufs a coleção
			CidadesService cidadeService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
			if (ufs != null) {
				if (colEnderecos != null && colEnderecos.size() > 0) {
					for (EnderecoCurriculoDTO endereco : colEnderecos) {
						for (UfDTO uf : ufs) {
							if (uf.getNomeUf().equalsIgnoreCase(endereco.getNomeUF())) {
								endereco.setEnderecoIdUF(uf.getIdUf());


								Collection<CidadesDTO> listCidade = cidadeService.listByIdUf(uf.getIdUf());

								for (CidadesDTO cidadeDto : listCidade) {
									if (cidadeDto.getNomeCidade().equalsIgnoreCase(endereco.getNomeCidade())) {
										endereco.setIdCidade(cidadeDto.getIdCidade());
										break;
									}
								}
							}
						}
					}
				}
			} else {
				if (colEnderecos != null && colEnderecos.size() > 0) {
					for (EnderecoCurriculoDTO endereco : colEnderecos) {
						List<CidadesDTO> listCidade = (List<CidadesDTO>) cidadeService.findByNome(endereco.getNomeCidade());
						if(listCidade != null) {
							for (CidadesDTO cidadeDto : listCidade) {
								if (cidadeDto.getNomeCidade().equalsIgnoreCase(endereco.getNomeCidade())) {
									endereco.setIdCidade(cidadeDto.getIdCidade());
									break;
								}
							}
						}
					}
				}
			}

			curriculoDto.setColTelefones(colTelefones);
			curriculoDto.setColEnderecos(colEnderecos);
			curriculoDto.setColFormacao(colFormacao);
			curriculoDto.setColEmail(colEmail);
			curriculoDto.setColExperienciaProfissional(colExperienciaProfissional);
			curriculoDto.setColCertificacao(colCertificacao);
			curriculoDto.setColIdioma(colIdioma);
			curriculoDto.setColCompetencias(colCompetencias);

			// seta o email principal
			if (curriculoDto.getColEmail() != null && curriculoDto.getColEmail().size() > 0) {
				for (Object obj2 : curriculoDto.getColEmail()) {
					EmailCurriculoDTO emailCurriculoDTO = (EmailCurriculoDTO) obj2;
					if (emailCurriculoDTO.getImagemEmailprincipal() != null && !emailCurriculoDTO.getImagemEmailprincipal().equals("")) {
						if (emailCurriculoDTO.getImagemEmailprincipal().equalsIgnoreCase("S")) {
							emailCurriculoDTO.setPrincipal("S");
						}
					}
				}
			}

			// seta idTipoTelefone
			for (Object objTel : curriculoDto.getColTelefones()) {
				TelefoneCurriculoDTO telefone = (TelefoneCurriculoDTO) objTel;
				telefone.setIdTipoTelefone(0);
				if (telefone.getIdTipoTelefone() == null) {
					if (telefone.getDescricaoTipoTelefone().trim().equalsIgnoreCase("RESIDENCIAL")) {
						telefone.setIdTipoTelefone(1);
					} else {
						telefone.setIdTipoTelefone(3);
					}
				}
			}

			Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
			curriculoDto.setAnexos(anexos);
			Collection<UploadDTO> uploadDTOs = (Collection<UploadDTO>) request.getSession().getAttribute("ARQUIVOS_UPLOAD");

			if (uploadDTOs != null && uploadDTOs.size() > 0) {

				for (UploadDTO uploadDTO : uploadDTOs) {
					curriculoDto.setFoto(uploadDTO);
				}

			}

			curriculoDto.setIdResponsavel(usuario.getIdUsuario());

			if (curriculoDto.getIdCurriculo() == null || curriculoDto.getIdCurriculo().intValue() == 0) {
				curriculoDto.setListaNegra("N");
				curriculoDto = (CurriculoDTO) curriculoService.create(curriculoDto);
			} else {
				curriculoService.update(curriculoDto);
			}

			document.alert("Registro gravado com sucesso!");

		} catch (Exception e) {
			throw new ServiceException(e);
		}

		clear(document, request, response);

	}

	public void setaFoto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Collection col = (Collection) request.getSession().getAttribute("ARQUIVOS_UPLOAD");

		if (col != null && col.size() > 0) {
			Iterator it = col.iterator();
			// UploadFileDTO uploadItem = (UploadFileDTO)it.next();
			UploadDTO uploadItem = (UploadDTO) it.next();

			document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img src=\"" + uploadItem.getCaminhoRelativo() + "\" border=0 width=\"167px\" />'");
			document.executeScript("$('#modal_foto').modal('hide')");
		}

	}

	public void clear(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		request.getSession(true).setAttribute("colUploadsGED", null);

		document.executeScript("uploadAnexos.refresh()");

		HTMLTable tblEnderecos = document.getTableById("tblEnderecos");
		HTMLTable tblTelefones = document.getTableById("tblTelefones");
		HTMLTable tblFormacao = document.getTableById("tblFormacao");
		HTMLTable tblEmail = document.getTableById("tblEmail");
		HTMLTable tblIdioma = document.getTableById("tblIdioma");
		HTMLTable tblCertificacao = document.getTableById("tblCertificacao");

//		HTMLTable tblExperiencia = document.getTableById("tblExperiencias");
		HTMLTable tblCompetencia = document.getTableById("tblCompetencia");

		tblEnderecos.deleteAllRows();
		tblTelefones.deleteAllRows();
		tblFormacao.deleteAllRows();
		tblEmail.deleteAllRows();
		tblIdioma.deleteAllRows();
		tblCertificacao.deleteAllRows();

//		tblExperiencia.deleteAllRows();
		tblCompetencia.deleteAllRows();

		document.getForm("formPesquisaCurriculo").clear();
		document.getForm("formFoto").clear();
		document.executeScript("limparGrids()");
		document.executeScript("primeiro()");

		apagarFoto(document, request, response);

	}

	public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("enderecoIdCidade");

		if (comboCidades != null) {
			comboCidades.removeAllOptions();
		}

		if (comboCidades != null) {
			this.inicializarCombo(comboCidades, request);

			int ufId = 0;

			if (request.getParameter("hiddenIdUf") != null && !request.getParameter("hiddenIdUf").equals("")) {
				ufId = Integer.parseInt(request.getParameter("hiddenIdUf"));
			} else {
				HTMLElement hiddenIdUf = document.getElementById("hiddenIdUf");

				if (hiddenIdUf != null && hiddenIdUf.getValue() != null && !hiddenIdUf.getValue().equals("")) {
					ufId = Integer.parseInt(hiddenIdUf.getValue());
				}
			}

			if (ufId > 0) {
				CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

				CidadesDTO obj = new CidadesDTO();
				obj.setIdUf(ufId);

				if (cidadesService != null && comboCidades != null) {
					// O nome do método deveria ser listByhiddenIdUf e não listByIdCidades.
					List<CidadesDTO> cidades = (List) cidadesService.listByIdCidades(obj);

					if (cidades != null) {
						// Ordenando cidades alfabeticamente.
						Collections.sort(cidades, new Comparator<CidadesDTO>() {
							@Override
							public int compare(CidadesDTO o1, CidadesDTO o2) {
								if (o1 == null || o1.getNomeCidade().trim().equals("")) {
									return -999;
								}

								if (o1 == null || o1.getNomeCidade().trim().equals("")) {
									return -999;
								}

								return o1.getNomeCidade().compareTo(o2.getNomeCidade());
							}

						});
						for (CidadesDTO cidade : cidades) {
							comboCidades.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
						}
					}
				}
			}
		}
	}

	public void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {

		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

	}

	public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("enderecoIdUF");

		if (comboUfs != null) {
			this.inicializarCombo(comboUfs, request);

			int paisId = 0;

			if (request.getParameter("pais") != null && !request.getParameter("pais").equals("")) {
				paisId = Integer.parseInt(request.getParameter("pais"));
			} else {
				HTMLElement idPais = document.getElementById("pais");

				if (idPais != null && idPais.getValue() != null && !idPais.getValue().equals("")) {
					paisId = Integer.parseInt(idPais.getValue());
				}
			}

			if (paisId > 0) {
				UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

				UfDTO obj = new UfDTO();
				obj.setIdPais(paisId);

				if (ufService != null) {
					List<UfDTO> ufs = (List) ufService.listByIdPais(obj);

					// Ordenando ufs alfabeticamente.
					Collections.sort(ufs, new Comparator<UfDTO>() {
						@Override
						public int compare(UfDTO o1, UfDTO o2) {
							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							return o1.getNomeUf().compareTo(o2.getNomeUf());
						}

					});

					if (ufs != null) {
						for (UfDTO uf : ufs) {
							comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
						}
					}
				}
			}
		}
	}

	public void preencherComboPaises(HTMLSelect combopais, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (combopais != null) {
			this.inicializarCombo(combopais, request);

			PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

			if (paisServico != null) {
				List<PaisDTO> paises = (List) paisServico.list();

				// Ordenando paises alfabeticamente.
				Collections.sort(paises, new Comparator<PaisDTO>() {
					@Override
					public int compare(PaisDTO o1, PaisDTO o2) {
						if (o1 == null || o1.getNomePais().trim().equals("")) {
							return -999;
						}

						if (o1 == null || o1.getNomePais().trim().equals("")) {
							return -999;
						}

						return o1.getNomePais().compareTo(o2.getNomePais());
					}

				});

				if (paises != null) {
					for (PaisDTO pais : paises) {
						combopais.addOption(pais.getIdPais().toString(), StringEscapeUtils.escapeJavaScript(pais.getNomePais()));
					}
				}
			}
		}
	}

	/**
	 * Metodo para apagar a foto gravada na sessão
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void apagarFoto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getForm("formFoto").clear();
		request.getSession().setAttribute("ARQUIVOS_UPLOAD", null);

	}



	public void preencherComboCidadeNatal(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("idCidadeNatal");

		if (comboCidades != null) {
			comboCidades.removeAllOptions();
		}

		if (comboCidades != null) {
			this.inicializarCombo(comboCidades, request);

			int ufId = 0;

			if (request.getParameter("idUfNatal") != null && !request.getParameter("idUfNatal").equals("")) {
				ufId = Integer.parseInt(request.getParameter("idUfNatal"));
			} else {
				HTMLElement hiddenIdUf = document.getElementById("idUfNatal");

				if (hiddenIdUf != null && hiddenIdUf.getValue() != null && !hiddenIdUf.getValue().equals("")) {
					ufId = Integer.parseInt(hiddenIdUf.getValue());
				}
			}

			if (ufId > 0) {
				CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

				CidadesDTO obj = new CidadesDTO();
				obj.setIdUf(ufId);

				if (cidadesService != null && comboCidades != null) {
					// O nome do método deveria ser listByhiddenIdUf e não listByIdCidades.
					List<CidadesDTO> cidades = (List) cidadesService.listByIdCidades(obj);

					if (cidades != null) {
						// Ordenando cidades alfabeticamente.
						Collections.sort(cidades, new Comparator<CidadesDTO>() {
							@Override
							public int compare(CidadesDTO o1, CidadesDTO o2) {
								if (o1 == null || o1.getNomeCidade().trim().equals("")) {
									return -999;
								}

								if (o1 == null || o1.getNomeCidade().trim().equals("")) {
									return -999;
								}

								return o1.getNomeCidade().compareTo(o2.getNomeCidade());
							}

						});
						for (CidadesDTO cidade : cidades) {
							comboCidades.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
						}
					}
				}
			}
		}
	}

	public void inicializarLoad(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
		Collection<IdiomaDTO> colIdioma = idiomaService.list();

		HTMLSelect idIdioma = document.getSelectById("idioma#idIdioma");
		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("enderecoIdUF");
		HTMLSelect combopais = (HTMLSelect) document.getSelectById("pais");
		HTMLSelect comboUfsNatal = (HTMLSelect) document.getSelectById("idEstadoNatal");
		HTMLSelect comboCidadeNatal = (HTMLSelect) document.getSelectById("idCidadeNatal");
//		HTMLSelect comboNacionalidade = (HTMLSelect) document.getSelectById("nacionalidade");
		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("enderecoNomeCidade");
		HTMLSelect comboSexo = (HTMLSelect) document.getSelectById("sexo");


		comboSexo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboSexo.addOption("F", UtilI18N.internacionaliza(request, "rh.sexoFeminino"));
		comboSexo.addOption("M", UtilI18N.internacionaliza(request, "rh.sexoMasculino"));

		idIdioma.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		idIdioma.addOptions(colIdioma, "idIdioma", "descricao", null);

		if (combopais != null) {
			this.preencherComboPaises(combopais, document, request, response);
		}

//		if (comboNacionalidade != null) {
//			this.preencherComboPaises(comboNacionalidade, document, request, response);
//		}

		if (comboUfs != null) {
			comboUfs.removeAllOptions();
			comboUfs.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		}

		if (comboUfsNatal != null) {
			//Foi passado que o pais padrão para a nacionalidade seja os estados Brasileiros.
			preencherComboUfsNatal(document,request,response,1);
		}

		if (comboCidadeNatal != null) {
			comboCidadeNatal.removeAllOptions();
			comboCidadeNatal.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		}

		if (comboCidades != null) {
			comboCidades.removeAllOptions();
			comboCidades.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		}
	}

	/**
	 * Método foi inserido pois o do método principal só estava inserindo os estados para o endereço,
	 * foi necessário para o estado natal.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @param idPais
	 * @throws Exception
	 */
	public void preencherComboUfsNatal(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idPais) throws Exception {

		HTMLSelect comboUfsNatal = (HTMLSelect) document.getSelectById("idEstadoNatal");

		if (comboUfsNatal != null) {
			this.inicializarCombo(comboUfsNatal, request);

//			int paisId = 0;
//
//			if (request.getParameter("pais") != null && !request.getParameter("pais").equals("")) {
//				paisId = Integer.parseInt(request.getParameter("pais"));
//			} else {
//				HTMLElement idPais = document.getElementById("pais");
//
//				if (idPais != null && idPais.getValue() != null && !idPais.getValue().equals("")) {
//					paisId = Integer.parseInt(idPais.getValue());
//				}
//			}

			if (idPais > 0) {
				UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

				UfDTO obj = new UfDTO();
				obj.setIdPais(idPais);

				if (ufService != null) {
					List<UfDTO> ufs = (List) ufService.listByIdPais(obj);

					// Ordenando ufs alfabeticamente.
					Collections.sort(ufs, new Comparator<UfDTO>() {
						@Override
						public int compare(UfDTO o1, UfDTO o2) {
							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							return o1.getNomeUf().compareTo(o2.getNomeUf());
						}

					});

					if (ufs != null) {
						for (UfDTO uf : ufs) {
							comboUfsNatal.addOption(uf.getIdUf().toString(), uf.getNomeUf());
						}
					}
				}
			}
		}
	}

	public void verificarParametroAnexos(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception{


		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if(DISKFILEUPLOAD_REPOSITORYPATH == null){
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}
		if(DISKFILEUPLOAD_REPOSITORYPATH.equals("")){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
			return;
		}
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if(!pasta.exists()){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.pastaIndicadaNaoExiste"));
			return;
		}
		document.executeScript("abrirModalFoto();");
	}

	public void getUFsJSON(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		int paisId = 0;

		Gson gson = new Gson();

		if (request.getParameter("pais") != null && !request.getParameter("pais").equals("")) {
			paisId = Integer.parseInt(request.getParameter("pais"));
		}

		if (paisId > 0) {
			UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

			UfDTO ufDTO = new UfDTO();
			ufDTO.setIdPais(paisId);

			if (ufService != null) {
				List<UfDTO> ufs = (List) ufService.listByIdPais(ufDTO);

				if (ufs != null) {
					// Ordenando ufs alfabeticamente.
					Collections.sort(ufs, new Comparator<UfDTO>() {
						@Override
						public int compare(UfDTO o1, UfDTO o2) {
							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							return o1.getNomeUf().compareTo(o2.getNomeUf());
						}

					});

					out.print(gson.toJsonTree(ufs));
				}
			}
		}
	}

	public void getCidadesJSON(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		int ufId = 0;

		Gson gson = new Gson();

		if (request.getParameter("hiddenIdUf") != null && !request.getParameter("hiddenIdUf").equals("")) {
			ufId = Integer.parseInt(request.getParameter("hiddenIdUf"));
		}

		if (ufId > 0) {
			CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

			CidadesDTO cidadesDTO = new CidadesDTO();
			cidadesDTO.setIdUf(ufId);

			if(cidadesService != null) {
				List<CidadesDTO> cidades = (List) cidadesService.listByIdCidades(cidadesDTO);

				if(cidades != null) {
					Collections.sort(cidades, new Comparator<CidadesDTO>() {
						@Override
						public int compare(CidadesDTO o1, CidadesDTO o2) {
							if(o1 == null || o1.getNomeCidade().trim().equals("")) {
								return -999;
							}

							if(o2 == null || o2.getNomeCidade().trim().equals("")) {
								return -999;
							}

							return o1.getNomeCidade().compareTo(o2.getNomeCidade());
						}
					});

					out.print(gson.toJsonTree(cidades));
				}
			}
		}
	}
}