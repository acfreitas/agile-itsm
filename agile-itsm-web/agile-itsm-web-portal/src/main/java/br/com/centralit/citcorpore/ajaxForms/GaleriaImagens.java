package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.centralit.citcorpore.bean.GaleriaImagensDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaGaleriaImagemService;
import br.com.centralit.citcorpore.negocio.GaleriaImagensService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Upload;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

public class GaleriaImagens extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return GaleriaImagensDTO.class;
	}

	public void listaImagens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO user = WebUtil.getUsuario(request);
		String url = "";
		if (user == null) {
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}
		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if (StringUtils.isBlank(DISKFILEUPLOAD_REPOSITORYPATH) || !pasta.exists()) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		GaleriaImagensDTO galeriaImagensDTO = (GaleriaImagensDTO) document.getBean();
		GaleriaImagensService galeriaImagensService = (GaleriaImagensService) ServiceLocator.getInstance().getService(GaleriaImagensService.class, null);
		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);

		String PRONTUARIO_QTDE_ANEXOS_LADO_LADO = "10";
		String PRONTUARIO_WIDTH_IMAGENS_ANEXOS = "100px";
		String PRONTUARIO_HEIGTH_IMAGENS_ANEXOS = "100px";

		String PRONTUARIO_GED_DIRETORIO = null;
		try {
			PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, " ");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		}

		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		}

		int qtdeImagens = Integer.parseInt(PRONTUARIO_QTDE_ANEXOS_LADO_LADO);

		boolean booleanBorda = false;
		Collection col = galeriaImagensService.findByCategoria(galeriaImagensDTO.getIdCategoriaGaleriaImagem());
		String strTabela = "<table>";
		int iContador = 0;
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				GaleriaImagensDTO galeriaImagensAux = (GaleriaImagensDTO) it.next();

				if (iContador == 0) {
					strTabela += "<tr>";
				}

				ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
				controleGEDDTO.setIdControleGED(new Integer(galeriaImagensAux.getNomeImagem()));
				controleGEDDTO = (ControleGEDDTO) controleGEDService.restore(controleGEDDTO);
				if (controleGEDDTO != null) {
					File fileDir2 = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens");
					if (!fileDir2.exists()) {
						fileDir2.mkdirs();
					}
					fileDir2 = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa());
					if (!fileDir2.exists()) {
						fileDir2.mkdirs();
					}
					fileDir2 = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa() + "/" + galeriaImagensAux.getIdCategoriaGaleriaImagem());
					if (!fileDir2.exists()) {
						fileDir2.mkdirs();
					}

					try {
						CriptoUtils.decryptFile(
								PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged",
								CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa() + "/" + galeriaImagensAux.getIdCategoriaGaleriaImagem() + "/"
										+ controleGEDDTO.getIdControleGED() + "." + galeriaImagensAux.getExtensao(),
								System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));
					} catch (Exception e) {
						e.printStackTrace();
					}

					String caminhoAnexoReal = "";
					// -- ATENCAO: foi proposital o uso de idControleGED=" + imagemHistoricoDTO.getNomeArquivo()! Pois o GED Interno necessita que seja assim.
					caminhoAnexoReal = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/recuperaFromGed/recuperaFromGed.load?idControleGED="
							+ galeriaImagensAux.getNomeImagem() + "&nomeArquivo=" + galeriaImagensAux.getNomeImagem();

					String borda = "";
					if (booleanBorda) {
						borda = " border=\"1\" ";
					}

					String strObs = "Não há";
					if (galeriaImagensAux.getDetalhamento() != null) {
						strObs = galeriaImagensAux.getDetalhamento();
					}
					if (strObs != null) {
						strObs = UtilStrings.decodeCaracteresEspeciais(strObs);
					}

					/* Desenvolvedor: Euler.Ramos Data: 29/10/2013 Horário: 17h08min ID Citsmart: 120393 Motivo/Comentário: Não aparecer a imagem nesta tela ou nos relatórios era um problema
					 * recorrente da forma que estava, a imagem não era listada pela url do sistema dependendo da rede.*/
					//url = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");
					url = br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO");

					String caminhoAnexo = url+"/galeriaImagens/" + user.getIdEmpresa() + "/" + galeriaImagensAux.getIdCategoriaGaleriaImagem() + "/" + controleGEDDTO.getIdControleGED() + "."
							+ galeriaImagensAux.getExtensao();

					strTabela += "<td>";
					if (galeriaImagensDTO.getSelecaoImagemEdicao() == null || galeriaImagensDTO.getSelecaoImagemEdicao().equalsIgnoreCase("")) {
						strTabela += "<a href=\"" + caminhoAnexoReal + "\" target=_blank><img " + " style=\"cursor:hand\" src=\"" + caminhoAnexo + "\" width=\"" + PRONTUARIO_WIDTH_IMAGENS_ANEXOS
								+ "\" height=\"" + PRONTUARIO_HEIGTH_IMAGENS_ANEXOS + "\" " + borda + " /></a> <br> ";
						strTabela += "<button type='button' class='icon_only text_only' name='btnGetEnd" + galeriaImagensAux.getIdImagem()
								+ "'  onclick='prompt(\""+UtilI18N.internacionaliza(request, "galeriaImagens.utilizeEnderecoAbaixo")+":\", \"" + caminhoAnexo + "\")'>Url</button>";
						strTabela += "<button type='button' class='icon_only text_only' name='btnExcluiImagem" + galeriaImagensAux.getIdImagem()
								+ "' onclick='excluirImagem("+galeriaImagensAux.getIdImagem()+")');'>"+UtilI18N.internacionaliza(request, "carrinho.excluir")+"</button>";
					} else {
						strTabela += "<a href=\"#\" onclick=\"setImagem('" + caminhoAnexo + "');" + galeriaImagensDTO.getSelecaoImagemEdicao() + "\"><img " + " style=\"cursor:hand\" src=\""
								+ caminhoAnexo + "\" width=\"" + PRONTUARIO_WIDTH_IMAGENS_ANEXOS + "\" height=\"" + PRONTUARIO_HEIGTH_IMAGENS_ANEXOS + "\" " + borda + " /></a> <br> ";
						strTabela += "<button type='button' class='icon_only text_only' name='btnGetEnd" + galeriaImagensAux.getIdImagem()
								+ "' onclick='prompt(\""+UtilI18N.internacionaliza(request, "galeriaImagens.utilizeEnderecoAbaixo")+":\", \"" + caminhoAnexo + "\")'>Url</button>";
						strTabela += "<button type='button' class='icon_only text_only' name='btnExcluiImagem" + galeriaImagensAux.getIdImagem()
								+ "' onclick='excluirImagem("+galeriaImagensAux.getIdImagem()+")');'>"+UtilI18N.internacionaliza(request, "carrinho.excluir")+"</button>";
					}
					strTabela += "</td>";
					strTabela += "<td>";
					strTabela += "&nbsp;";
					strTabela += "</td>";

				}

				iContador++;

				if (iContador >= qtdeImagens) {
					iContador = 0;
					strTabela += "</tr>";
				}
			}
		}
		strTabela += "</table>";

		document.getElementById("divImagens").setInnerHTML(strTabela);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO user = WebUtil.getUsuario(request);
		if (user == null) {
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}
		GaleriaImagensDTO galeriaImagensDTO = new GaleriaImagensDTO();
		Upload upload = new Upload();
		HashMap hshRetorno[] = null;
		try {
			hshRetorno = upload.doUploadAll(request);
		} catch (Exception e) {
		}
		try {
			CategoriaGaleriaImagemService categoriaGaleriaImagemService = (CategoriaGaleriaImagemService) ServiceLocator.getInstance().getService(CategoriaGaleriaImagemService.class, null);
			ArrayList<CategoriaGaleriaImagemDTO> cols = (ArrayList) categoriaGaleriaImagemService.list();
			ArrayList<CategoriaGaleriaImagemDTO> popular = new ArrayList<CategoriaGaleriaImagemDTO>();
			HTMLSelect idCategoriaGaleriaImagemCombo = document.getSelectById("idCategoriaGaleriaImagem");
			idCategoriaGaleriaImagemCombo.removeAllOptions();
			if (cols != null) {
				for (CategoriaGaleriaImagemDTO origemAtendimento : cols)
					if (origemAtendimento.getDataFim() == null)
						popular.add(origemAtendimento);
			}
			idCategoriaGaleriaImagemCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			idCategoriaGaleriaImagemCombo.addOptions(popular, "idCategoriaGaleriaImagem", "nomeCategoria", null);
		} catch (Exception e) {
		}
		String idCategoriaGaleriaImagemStr = null;
		int idCategoriaGaleriaImagem = 0;
		HashMap formItems = null;
		Collection fileItems = null;
		try {
			fileItems = hshRetorno[1].values();
			formItems = hshRetorno[0];
			idCategoriaGaleriaImagemStr = (String) formItems.get("IDCATEGORIAGALERIAIMAGEM");
			idCategoriaGaleriaImagem = Integer.parseInt(idCategoriaGaleriaImagemStr);

			if (idCategoriaGaleriaImagem == 0) {
				System.out.println("CATEGORIA VAZIA......");
				return;
			}
			String descricaoImagem = (String) formItems.get("DESCRICAOIMAGEM");
			if (descricaoImagem != null) {
				descricaoImagem = UtilStrings.decodeCaracteresEspeciais(descricaoImagem);
				descricaoImagem = descricaoImagem.replaceAll("#10##13#", "\n");
			}
			String detalhamento = (String) formItems.get("DETALHAMENTO");
			if (detalhamento != null) {
				detalhamento = UtilStrings.decodeCaracteresEspeciais(detalhamento);
				detalhamento = detalhamento.replaceAll("#10##13#", "\n");
			}

			String PRONTUARIO_GED_DIRETORIO = null;
			try {
				PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, " ");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
				PRONTUARIO_GED_DIRETORIO = "";
			}

			if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
				PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
			}

			if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
				PRONTUARIO_GED_DIRETORIO = "/ged";
			}

			ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
			String pasta = "";
			pasta = controleGEDService.getProximaPastaArmazenar();
			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			String prontuarioGedInternoBancoDados = "N";

			File fileDir2 = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens");
			if (!fileDir2.exists()) {
				fileDir2.mkdirs();
			}
			fileDir2 = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa());
			if (!fileDir2.exists()) {
				fileDir2.mkdirs();
			}
			fileDir2 = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa() + "/" + idCategoriaGaleriaImagem);
			if (!fileDir2.exists()) {
				fileDir2.mkdirs();
			}

			String fileName = "";
			FileItem fi;
			if (!fileItems.isEmpty()) {
				File arquivo;
				GaleriaImagensService galeriaImagensService = (GaleriaImagensService) ServiceLocator.getInstance().getService(GaleriaImagensService.class, null);
				Iterator it = fileItems.iterator();
				while (it.hasNext()) {
					fi = (FileItem) it.next();

					ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
					controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_GALERIA_IMAGENS);
					controleGEDDTO.setId(new Integer(0));
					controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
					controleGEDDTO.setDescricaoArquivo(fi.getName());
					controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(fi.getName()));
					controleGEDDTO.setPasta(pasta);
					controleGEDDTO.setNomeArquivo(fi.getName());

					java.util.Date now = new java.util.Date();
					if ("S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
						arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/TMP_" + now.getTime() + "." + Util.getFileExtension(fi.getName()));
						fi.write(arquivo);

						CriptoUtils.encryptFile(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/TMP_" + now.getTime() + "." + Util.getFileExtension(fi.getName()),
								PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/TMP_" + now.getTime() + ".ged",
								this.getClass().getResourceAsStream(Constantes.getValue("CAMINHO_CHAVE_PUBLICA")));

						controleGEDDTO.setPathArquivo(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/TMP_" + now.getTime() + ".ged");
					}

					controleGEDDTO = (ControleGEDDTO) controleGEDService.create(controleGEDDTO);
					if ("S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
						UtilTratamentoArquivos.copyFile(
								PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/TMP_" + now.getTime() + "." + Util.getFileExtension(fi.getName()),
								CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa() + "/" + idCategoriaGaleriaImagem + "/" + controleGEDDTO.getIdControleGED() + "."
										+ Util.getFileExtension(fi.getName()));
					}

					if (!"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
						arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(fi.getName()));
						fi.write(arquivo);

						UtilTratamentoArquivos.copyFile(
								PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(fi.getName()),
								CITCorporeUtil.CAMINHO_REAL_APP + "/galeriaImagens/" + user.getIdEmpresa() + "/" + idCategoriaGaleriaImagem + "/" + controleGEDDTO.getIdControleGED() + "."
										+ Util.getFileExtension(fi.getName()));

						CriptoUtils.encryptFile(
								PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(fi.getName()),
								PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir")
										+ Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
					}

					galeriaImagensDTO = new GaleriaImagensDTO();
					galeriaImagensDTO.setIdCategoriaGaleriaImagem(idCategoriaGaleriaImagem);
					galeriaImagensDTO.setDescricaoImagem(descricaoImagem);
					galeriaImagensDTO.setDetalhamento(detalhamento);
					galeriaImagensDTO.setNomeImagem(controleGEDDTO.getIdControleGED().toString());
					galeriaImagensDTO.setExtensao(Util.getFileExtension(fi.getName()));
					galeriaImagensService.create(galeriaImagensDTO);
				}
			}
		} catch (Exception e) {
			document.executeScript("$('#loading_overlay').hide();");
		}
		document.executeScript("$('#loading_overlay').hide();");
	}

	public void verificarParametroAnexos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if (DISKFILEUPLOAD_REPOSITORYPATH == null) {
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}
		if (DISKFILEUPLOAD_REPOSITORYPATH.equals("")) {
			throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
		}
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if (!pasta.exists()) {
			throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.pastaIndicadaNaoExiste"));
		} else {
			document.executeScript("adicionarImagemLiberada();");
		}
	}

	public void excluirImagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GaleriaImagensDTO galeriaImagensDTO = (GaleriaImagensDTO) document.getBean();
		GaleriaImagensService galeriaImagensService = (GaleriaImagensService) ServiceLocator.getInstance().getService(GaleriaImagensService.class, null);
		if(galeriaImagensDTO!= null){
			galeriaImagensService.delete(galeriaImagensDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}
		listaImagens(document, request, response);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
}
