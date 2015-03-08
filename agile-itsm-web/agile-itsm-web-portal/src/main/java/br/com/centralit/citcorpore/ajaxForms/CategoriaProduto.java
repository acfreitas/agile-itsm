package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.CriterioCotacaoCategoriaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProdutoService;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoService;
import br.com.centralit.citcorpore.negocio.CriterioCotacaoCategoriaService;
import br.com.centralit.citcorpore.util.Arquivo;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CategoriaProduto extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		request.getSession(true).setAttribute("colUploadsGED", null);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, WebUtil.getUsuarioSistema(request));
		Collection<CriterioAvaliacaoDTO> colCriterios = criterioAvaliacaoService.findByAplicavelCotacao();
		request.setAttribute("colCriterios", colCriterios);

		montaHierarquiaCategoria(document, request, response);
		this.atualizaImagem(document, request, response);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaProdutoDTO categoriaProduto = (CategoriaProdutoDTO) document.getBean();
		CategoriaProdutoService categoriaService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);
		if (categoriaProduto != null && categoriaService != null) {
			String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
			if (categoriaService.existeIgual(categoriaProduto)) {
				document.alert(UtilI18N.internacionaliza(request, "MSE01"));
				return;
			}
			if (categoriaProduto.getIdCategoria() != null && categoriaProduto.getIdCategoria().equals(categoriaProduto.getIdCategoriaPai())) {
				document.alert(UtilI18N.internacionaliza(request, "categoriaProduto.categoria_invalida"));
				return;
			}
			if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
				prontuarioGedInternoBancoDados = "N";
			}
			Collection<CriterioCotacaoCategoriaDTO> colCriterios = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CriterioCotacaoCategoriaDTO.class, "colCriterios_Serialize",
					request);
			if (colCriterios != null && !colCriterios.isEmpty()) {
				categoriaProduto.setColCriterios(colCriterios);
				for (CriterioCotacaoCategoriaDTO criterio : colCriterios) {
					if (criterio.getPesoCotacao() < 0 || criterio.getPesoCotacao() > 10) {
						document.alert(String.format("%s %s %d%s %s %s", UtilI18N.internacionaliza(request, "categoriaProduto.valorInformado"),
								UtilI18N.internacionaliza(request, "citcorpore.texto.artigoDefinido.o"), criterio.getSequencia(),
								UtilI18N.internacionaliza(request, "citcorpore.texto.sufixo.ordemNumero.masculino"), UtilI18N.internacionaliza(request, "avaliacaoFornecedor.criterio").toLowerCase(),
								UtilI18N.internacionaliza(request, "categoriaProduto.invalidoforaIntervalo")));
						String seqZeros = "00000";
						if (criterio.getSequencia() > 0 && criterio.getSequencia() < 10) {
							seqZeros = "0000";
						} else if (criterio.getSequencia() > 10 && criterio.getSequencia() < 100) {
							seqZeros = "000";
						} else if (criterio.getSequencia() > 100 && criterio.getSequencia() < 1000) {
							seqZeros = "00";
						} else if (criterio.getSequencia() > 1000 && criterio.getSequencia() < 10000) {
							seqZeros = "0";
						} else {
							seqZeros = "";
						}
						document.executeScript(String.format("$('#pesoCotacao%s%s').val('').focus()", seqZeros, criterio.getSequencia()));
						return;
					}
				}
			}
			Collection<UploadDTO> fotos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
			categoriaProduto.setFotos(fotos);
			if (categoriaProduto.getIdCategoria() == null) {
				categoriaProduto = (CategoriaProdutoDTO) categoriaService.create(categoriaProduto);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				categoriaService.update(categoriaProduto);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
			request.getSession(true).setAttribute("colUploadsGED", null);
			HTMLForm form = document.getForm("form");
			HTMLForm formFotos = document.getForm("formUpload");
			formFotos.clear();
			form.clear();
			document.setBean(new CategoriaProdutoDTO());
			montaHierarquiaCategoria(document, request, response);
			document.executeScript("situacaoAtivo()");
			document.executeScript("document.getElementById('divImgFoto').innerHTML = ''");
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("colUploadsGED", null);
		CategoriaProdutoDTO categoriaProduto = (CategoriaProdutoDTO) document.getBean();
		CategoriaProdutoService categoriaService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);

		if (categoriaProduto != null && categoriaService != null) {
			categoriaProduto = (CategoriaProdutoDTO) categoriaService.restore(categoriaProduto);

			if (categoriaProduto != null) {
				if (categoriaProduto.getIdCategoriaPai() != null) {
					CategoriaProdutoDTO categoriaProdutoPai = new CategoriaProdutoDTO();
					categoriaProdutoPai.setIdCategoria(categoriaProduto.getIdCategoriaPai());
					categoriaProdutoPai = (CategoriaProdutoDTO) categoriaService.restore(categoriaProdutoPai);
					/*
					 * Rodrigo Pecci Acorse - 19/03/2014 10h40 - #132777
					 * StringEscapeUtils.escapeJavaScript removido pois estava quebrando a acentuação na exibição do nome
					 */
					categoriaProduto.setNomeCategoriaPai(categoriaProdutoPai.getNomeCategoria());
				}
				if(categoriaProduto.getNomeCategoria()!=null){
					//categoriaProduto.setNomeCategoria(StringEscapeUtils.escapeJavaScript(categoriaProduto.getNomeCategoria()));
					categoriaProduto.setNomeCategoria(UtilStrings.decodeCaracteresEspeciais(categoriaProduto.getNomeCategoria()));
				}

				HTMLForm form = document.getForm("form");
				HTMLForm formFotos = document.getForm("formUpload");

				form.clear();
				formFotos.clear();

				form.setValues(categoriaProduto);

				restaurarAnexos(request, categoriaProduto.getIdCategoria());

				CriterioCotacaoCategoriaService criterioCotacaoService = (CriterioCotacaoCategoriaService) ServiceLocator.getInstance().getService(CriterioCotacaoCategoriaService.class, null);

				Collection<CriterioCotacaoCategoriaDTO> colCriterios = criterioCotacaoService.findByIdCategoria(categoriaProduto.getIdCategoria());

				document.executeScript("GRID_CRITERIOS.deleteAllRows();");

				if (colCriterios != null) {
					int i = 0;
					for (CriterioCotacaoCategoriaDTO criterioDto : colCriterios) {
						i++;
						document.executeScript("GRID_CRITERIOS.addRow()");
						criterioDto.setSequencia(i);
						document.executeScript("seqCriterio = NumberUtil.zerosAEsquerda(" + i + ",5)");
						document.executeScript("exibeCriterio('" + br.com.citframework.util.WebUtil.serializeObject(criterioDto, WebUtil.getLanguage(request)) + "')");
					}
				}
				categoriaService.recuperaImagem(categoriaProduto);
				document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img id=\"img\" name=\"img\" style=\"max-height: 100px; max-width: 60%;\" src=\""+(categoriaProduto.getImagem() != null ? categoriaProduto.getImagem() : "")+"\" />'");
			}
		} else {
			document.executeScript("document.getElementById('divImgFoto').innerHTML = ''");
		}
	}

	public void remove(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaProdutoDTO categoriaProduto = (CategoriaProdutoDTO) document.getBean();
		CategoriaProdutoService categoriaService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);

		try {
			categoriaService.delete(categoriaProduto);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			
			request.getSession(true).setAttribute("colUploadsGED", null);
			HTMLForm form = document.getForm("form");
			HTMLForm formFotos = document.getForm("formUpload");
			formFotos.clear();
			form.clear();
			document.executeScript("document.getElementById('divImgFoto').innerHTML = ''");
			document.setBean(new CategoriaProdutoDTO());
			montaHierarquiaCategoria(document, request, response);
			
		} catch (Exception e) {
			document.alert(UtilI18N.internacionaliza(request, "categoriaProduto.possui_relacionamento"));
		}
	}

	protected void restaurarAnexos(HttpServletRequest request, Integer idCategoria) throws ServiceException, Exception {
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_CATEGORIAPRODUTO, idCategoria);
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
	}

	public void montaHierarquiaCategoria(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";
		CategoriaProdutoService categoriaProdutoService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, WebUtil.getUsuarioSistema(request));
		Collection<CategoriaProdutoDTO> colCategorias = categoriaProdutoService.list();
		if (colCategorias != null) {
			int maiorNivel = 0;
			for (CategoriaProdutoDTO categoriaProdutoDto : colCategorias) {
				if (categoriaProdutoDto.getNivel().intValue() > maiorNivel)
					maiorNivel = categoriaProdutoDto.getNivel().intValue();
			}
			result = "<table>";
			for (CategoriaProdutoDTO categoriaProdutoDto : colCategorias) {
				categoriaProdutoService.recuperaImagem(categoriaProdutoDto);
				String path = categoriaProdutoDto.getImagem();
				if (path == null || path.equals(""))
					path = Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/folder.png";
				result += "<tr id='trCateg_" + categoriaProdutoDto.getIdCategoria() + "' style=\"cursor: pointer;\" >";
				result += "<td><table><tr>";
				for (int i = 0; i <= categoriaProdutoDto.getNivel().intValue(); i++) {
					if (i < categoriaProdutoDto.getNivel().intValue())
						result += "<td width='20px'>&nbsp;</td>";
					else
						result += "<td onclick=\"restaurarCategoria(" + categoriaProdutoDto.getIdCategoria() + ");\"><img style='width:16px;height:16px' src=\"" + path + "\" />"
								+ categoriaProdutoDto.getNomeCategoria() + "</td>";
				}
				result += "</tr></table></td>";
				result += "</tr>";
			}
			result += "</table>";
		}
		document.getElementById("divCategoria").setInnerHTML(result);
	}
	
	public void atualizaImagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		CategoriaProdutoDTO categoriaProduto = (CategoriaProdutoDTO) document.getBean();
		CategoriaProdutoService categoriaService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);
		categoriaService.recuperaImagem(categoriaProduto);
		document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img id=\"img\" name=\"img\" style=\"max-height: 100px; max-width: 60%;\" src=\""+(categoriaProduto.getImagem() != null ? categoriaProduto.getImagem() : "") +"\" />'");
	}
	
	public void mostraImagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		Collection<UploadDTO> fotos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
		if (fotos != null && fotos.size() > 0) {
			for (UploadDTO uploadDTO : fotos) {
				document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img id=\"img\" name=\"img\" style=\"max-height: 100px; max-width: 60%;\" src=\""+ (Arquivo.getDirUploadImagem(uploadDTO) != null ? Arquivo.getDirUploadImagem(uploadDTO) : "") +"\" />'");
			}
		}
	}
	/**
	 * Copia Arquivo do diretorio temporario para Diretorio definitivo.
	 * 
	 * @param fonte
	 * @param destino
	 * @throws IOException
	 * @author valdoilo.damasceno
	 */
	public void copiarArquivo(File fonte, String destino) throws IOException {
		InputStream in;
		try {
			in = new FileInputStream(fonte);
			OutputStream out = new FileOutputStream(new File(destino));

			byte[] buf = new byte[1024];
			int len;
			try {
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Class getBeanClass() {
		return CategoriaProdutoDTO.class;
	}

}