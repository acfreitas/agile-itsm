package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoPastaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes","unused" })
public class PesquisaErroConhecido extends BaseConhecimento {
	
	
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		this.montarArvoreDePastasErroConhecido(document, request, response);

	}
	
	@Override
	public Class getBeanClass() {
		return BaseConhecimentoDTO.class;
	}
	
	/**
	 * Restaura Base de Conhecimento do tipo FAQ.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		PastaDTO pastaDto = new PastaDTO();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);

		if (baseConhecimentoDto.getIdPasta() != null) {

			pastaDto.setId(baseConhecimentoDto.getIdPasta());

			pastaDto = (PastaDTO) pastaService.restore(pastaDto);

			baseConhecimentoDto.setNomePasta(pastaDto.getNome());

		}

		request.getSession().removeAttribute("idBaseConhecimento");

		request.getSession().setAttribute("idBaseConhecimento", baseConhecimentoDto.getIdBaseConhecimento());

		super.restaurarAnexos(request, baseConhecimentoDto);

		HTMLForm form = CITCorporeUtil.limparFormulario(document);

		form.setValues(baseConhecimentoDto);

		document.getForm("form").lockForm();

		document.getElementById("conteudo").setInnerHTML(baseConhecimentoDto.getConteudo());

	}
	
	public void montarArvoreDePastasErroConhecido(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		Collection<PastaDTO> listPastaPaiErroConhecido = pastaService.listPastaSuperiorErroConhecidoSemPai();

		StringBuilder sb = new StringBuilder();

		sb.append("<div id=\"corpoInf\">");
		sb.append("<br>");
		sb.append("<ul id=\"browser\" class=\"filetree treeview\">");

		if (listPastaPaiErroConhecido != null) {

			for (PastaDTO pasta : listPastaPaiErroConhecido) {

				String nomePasta = pasta.getNome();

				sb.append("<li>");
				sb.append("<div class=\"hitarea closed-hitarea collapsable-hitarea\">");
				sb.append("</div>");
				sb.append("<span class=\"folder\">");

				sb.append(super.obterStatusPermissao(request, pasta, nomePasta, usuarioDto));

				sb.append("</span>");
				sb.append("<ul> ");

				Collection<BaseConhecimentoDTO> listBaseconhecimentoErroConhecido = baseConhecimentoService.listarBaseConhecimentoErroConhecidoByPasta(pasta);

				if (listBaseconhecimentoErroConhecido != null && !listBaseconhecimentoErroConhecido.isEmpty()) {

					sb.append("<ul>");

					for (BaseConhecimentoDTO base : listBaseconhecimentoErroConhecido) {

						sb.append("<li>");

						sb.append("<span class=\"file\">");

						sb.append("<a  href='#'  onclick='verificarPermissaoDeAcesso(" + base.getIdPasta() + "," + base.getIdBaseConhecimento() + ");desabilitaDivPesquisa();'    id='idTitulo" + base.getIdBaseConhecimento() + "' >");

						sb.append(" " + base.getTitulo() + " ");

						sb.append("</a>");

						sb.append("</span>");

						sb.append("</li>");

					}

					sb.append("</ul>");
				}

				Collection<PastaDTO> listSubPastaErroConhecido = pastaService.listSubPastasErroConhecido(pasta);

				if (listSubPastaErroConhecido != null) {

					this.gerarSubpastasErroConhecido(sb, listSubPastaErroConhecido, pasta, request);
				}

				sb.append("</li>");
				sb.append("</ul>");
				sb.append("</li>");
			}
		}
		sb.append("</ul>");
		sb.append("</div>");

		HTMLElement divPrincipal = document.getElementById("principalBaseConhecimento");
		divPrincipal.setInnerHTML(sb.toString());
		document.executeScript("tree(\"#browser\");");

	}
	

	public void gerarSubpastasErroConhecido(StringBuilder sb, Collection<PastaDTO> listSubPastaErroConhecido, PastaDTO pasta, HttpServletRequest request) throws ServiceException, Exception {

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		for (PastaDTO subPasta : listSubPastaErroConhecido) {

			BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();

			Collection<PastaDTO> listSubSubPastaErroConhecido = pastaService.listSubPastasErroConhecido(subPasta);

			String nomeSubPasta = subPasta.getNome();

			sb.append("<li class='collapsable'>");
			sb.append("<div class=\"hitarea closed-hitarea collapsable-hitarea\">");
			sb.append("</div>");

			if (listSubSubPastaErroConhecido != null && !listSubSubPastaErroConhecido.isEmpty()) {

				sb.append("<span class=\"folder\">");

				sb.append(this.obterStatusPermissao(request, subPasta, subPasta.getNome(), usuarioDto));

				sb.append("</span >");

				Collection<BaseConhecimentoDTO> listBaseconhecimentoErroConhecidoDaSubpasta = baseConhecimentoService.listarBaseConhecimentoErroConhecidoByPasta(subPasta);

				if (listBaseconhecimentoErroConhecidoDaSubpasta != null && !listBaseconhecimentoErroConhecidoDaSubpasta.isEmpty()) {

					sb.append("<ul>");

					for (BaseConhecimentoDTO base : listBaseconhecimentoErroConhecidoDaSubpasta) {

						sb.append("<li>");

						sb.append("<span class=\"file\">");

						sb.append("<a  href='#'  onclick='verificarPermissaoDeAcesso(" + base.getIdPasta() + "," + base.getIdBaseConhecimento() + ");desabilitaDivPesquisa();'    id='idTitulo" + base.getIdBaseConhecimento() + "' >");

						sb.append(" " + base.getTitulo() + " ");

						sb.append("</a>");

						sb.append("</span>");

						sb.append("</li>");
					}

					sb.append("</ul>");
				}
			} else {

				sb.append("<span class=\"folder\">");

				sb.append(this.obterStatusPermissao(request, subPasta, subPasta.getNome(), usuarioDto));

				sb.append("</span>");

				Collection<BaseConhecimentoDTO> listBaseconhecimentoErroConhecidoDaSubpasta = baseConhecimentoService.listarBaseConhecimentoErroConhecidoByPasta(subPasta);

				if (listBaseconhecimentoErroConhecidoDaSubpasta != null && !listBaseconhecimentoErroConhecidoDaSubpasta.isEmpty()) {

					sb.append("<ul>");

					for (BaseConhecimentoDTO base : listBaseconhecimentoErroConhecidoDaSubpasta) {

						sb.append("<li>");

						sb.append("<span class=\"file\">");

						sb.append("<a  href='#'  onclick='verificarPermissaoDeAcesso(" + base.getIdPasta() + "," + base.getIdBaseConhecimento() + ");desabilitaDivPesquisa();'    id='idTitulo" + base.getIdBaseConhecimento() + "' >");

						sb.append(" " + base.getTitulo() + " ");

						sb.append("</a>");

						sb.append("</span>");

						sb.append("</li>");

					}

					sb.append("</ul>");
				}

			}

			if (listSubSubPastaErroConhecido != null && !listSubSubPastaErroConhecido.isEmpty()) {

				sb.append("<ul id=\"subBios\"> ");

				this.gerarSubpastasErroConhecido(sb, listSubSubPastaErroConhecido, pasta, request);

				sb.append("</ul> ");

				sb.append("</li>");
			}

		}
	}
	
	public void verificarPermissaoDeAcesso(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		PastaDTO pastaDto = new PastaDTO();

		pastaDto.setId(baseConhecimentoDto.getIdPasta());

		pastaDto = (PastaDTO) pastaService.restore(pastaDto);

		PermissaoAcessoPasta permissao = null;

		permissao = perfilAcessoPastaService.verificarPermissaoDeAcessoPasta(usuarioDto, pastaDto);

		if (permissao != null) {

			if (PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {

				document.alert("Usuário sem permissão de acesso.");
				document.executeScript("$('#conhecimento').hide();");
				document.executeScript("limpar()");
				return;

			} else {

				if (PermissaoAcessoPasta.LEITURA.equals(permissao)) {

					document.executeScript("fecharPesquisa();");

					document.executeScript("corTitulo(" + baseConhecimentoDto.getIdBaseConhecimento() + ");");

					document.executeScript("tituloBaseConhecimentoView(" + baseConhecimentoDto.getIdBaseConhecimento() + ")");

				} else {

					if (PermissaoAcessoPasta.LEITURAGRAVACAO.equals(permissao)) {

						document.executeScript("fecharPesquisa();");

						document.executeScript("corTitulo(" + baseConhecimentoDto.getIdBaseConhecimento() + ");");

						document.executeScript("tituloBaseConhecimentoView(" + baseConhecimentoDto.getIdBaseConhecimento() + ")");

					}
				}

			}

		} else {
			document.executeScript("limpar()");
			document.executeScript("$('#conhecimento').hide();");
			document.alert("Usuário sem permissão de acesso.");
			return;
		}

	}
	
	public void pesquisaBaseConhecimentoTipoErroConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		BaseConhecimentoDTO baseConhecimentoAux = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		List<BaseConhecimentoDTO> listaPesquisaLucene = null;
		
		if (baseConhecimentoDto.getTermoPesquisa() == null || baseConhecimentoDto.getTermoPesquisa().trim().equalsIgnoreCase("")) {

			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.validacao.informetermo"));

			return;

		} else {
			if (baseConhecimentoDto.getTermoPesquisa() != null && !baseConhecimentoDto.getTermoPesquisa().equalsIgnoreCase("")) {

				document.executeScript("$('#resultPesquisaPai').dialog('open');");
				Lucene lucene = new Lucene();
				listaPesquisaLucene = lucene.pesquisaBaseConhecimento(baseConhecimentoDto);
			}
		}

		String buffer = "<table>";

		if (listaPesquisaLucene != null && !listaPesquisaLucene.isEmpty()) {

				for (BaseConhecimentoDTO dto : listaPesquisaLucene) {

					String titulo = UtilStrings.nullToVazio(dto.getTitulo());

					titulo = titulo.replaceAll("\"", "");

					if (titulo.trim().equalsIgnoreCase("")) {
						continue;
					}

					buffer += "<tr style='height: 25px !important;'>";
					buffer += "<td style='FONT-WEIGHT: bold; FONT-SIZE: small; FONT-FAMILY: 'Arial'; width : 422px;'>";

					buffer += "<a href='#' onclick='verificarPermissaoDeAcesso(" + dto.getIdPasta() + "," + dto.getIdBaseConhecimento() + ");contadorClicks(" + dto.getIdBaseConhecimento()
							+ ");desabilitaDivPesquisa();' id='idTitulo" + dto.getIdBaseConhecimento() + "' >" + titulo + "</a>";
					buffer += "</td>";
					buffer += "</tr>";
				}


		} else {
			buffer += "<tr style='height: 25px !important;'>";
			buffer += "<td style='FONT-WEIGHT: bold; FONT-SIZE: small; FONT-FAMILY: 'Arial'; width : 422px;'>";
			buffer += "<label>"+UtilI18N.internacionaliza(request, "citcorpore.comum.resultado")+"</label>";
			buffer += "</td>";
			buffer += "</tr>";
		}

		buffer += "<table>";

		document.getElementById("resultPesquisa").setInnerHTML(buffer);

		document.getElementById("resultPesquisaPai").setVisible(true);

	}
	
}
