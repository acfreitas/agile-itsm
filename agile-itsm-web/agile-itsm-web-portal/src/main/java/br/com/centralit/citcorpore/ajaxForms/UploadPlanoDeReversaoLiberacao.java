package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class UploadPlanoDeReversaoLiberacao extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return UploadDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		br.com.centralit.citcorpore.util.Upload upload = new br.com.centralit.citcorpore.util.Upload();
		Collection colUploadsGED = null;
		request.getAttribute("descUploadFile_uploadPlanoDeReversaoLiberacao");
		request.getParameter("descUploadFile_uploadPlanoDeReversaoLiberacao");
		HashMap hshRetorno[] = new HashMap[2];
		hshRetorno = upload.doUploadAll(request);

		/*
		 * String idRequisicaoMudanca = request.getParameter("idMudanca");
		 * if(idRequisicaoMudanca != null){
		 * request.getSession().setAttribute("colUploadPlanoDeReversaoLiberacaoGED"
		 * , null); this.restaurarAnexosPlanoDeReversao(request,
		 * Integer.parseInt(idRequisicaoMudanca)); }
		 */

		if (hshRetorno[0] != null) {
			Collection fileItems = hshRetorno[1].values();
			HashMap formItems = hshRetorno[0];

			String descUploadFile = (String) formItems.get("DESCUPLOADFILE_UPLOADPLANODEREVERSAOLIBERACAO");
			String idMudanca = (String) formItems.get("IDMUDANCA");
			String nameUploadFile = (String) formItems.get("FILE_UPLOADPLANODEREVERSAOLIBERACAO");
			nameUploadFile = UtilStrings.nullToVazio(nameUploadFile);
			String versao = (String) formItems.get("VERSAO_UPLOADPLANODEREVERSAOLIBERACAO");
			
			if(versao.equalsIgnoreCase("")){
				versao = UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado");
			}
			
			if(nameUploadFile == null){
				nameUploadFile = UtilStrings.nullToVazio(nameUploadFile);
				}

			if (nameUploadFile.trim().equalsIgnoreCase("arquivo")) {

				nameUploadFile = (String) formItems.get("FILE_UPLOADANEXOS");
				nameUploadFile = UtilStrings.decodeCaracteresEspeciais(nameUploadFile);
				// nameUploadFile = nameUploadFile.replaceAll("#10##13#", "\n");
				// //codificacao do CITFramework
			}

			if (descUploadFile != null) {
				descUploadFile = UtilStrings.decodeCaracteresEspeciais(descUploadFile);
				descUploadFile = descUploadFile.replaceAll("#10##13#", "\n"); // codificacao
																				// do
																				// CITFramework
			}

			if (nameUploadFile != null) {
				nameUploadFile = CITCorporeUtil.getNameFile(nameUploadFile);
			}

			String notaTecnicaUpload = (String) formItems.get("NOTATECNICAUPLOAD");
			if (notaTecnicaUpload == null) {
				notaTecnicaUpload = "N";
			}

			colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadPlanoDeReversaoLiberacaoGED");
			if (colUploadsGED == null) {
				colUploadsGED = new ArrayList();
			}

			File f = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload");
			if (!f.exists()) {
				f.mkdirs();
			}

			String fileName = "";
			FileItem fi;
			if (!fileItems.isEmpty()) {
				File arquivo;
				List filesDel = new ArrayList();
				Iterator it = fileItems.iterator();
				while (it.hasNext()) {
					fi = (FileItem) it.next();

					if (fi.getSize() > 0 && (fi.getFieldName() != null && fi.getFieldName().equalsIgnoreCase("file_uploadPlanoDeReversaoLiberacao"))) {
						UploadDTO uploadDTO = new UploadDTO();
						uploadDTO.setDescricao(descUploadFile);
						uploadDTO.setNameFile(nameUploadFile);
						uploadDTO.setSituacao("Temporário");
						uploadDTO.setTemporario("S");
						uploadDTO.setVersao(versao);
						uploadDTO.setIdMudanca(idMudanca);
						uploadDTO.setNotaTecnicaUpload(notaTecnicaUpload);
						uploadDTO.setPath(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/" + uploadDTO.getNameFile());

						arquivo = new File(uploadDTO.getPath());
						fi.write(arquivo);

						colUploadsGED.add(uploadDTO);
					}
				}
			}
		}
		request.getSession(true).setAttribute("colUploadPlanoDeReversaoLiberacaoGED", colUploadsGED);

	}

	protected void restaurarAnexosPlanoDeReversao(HttpServletRequest request, Integer idRequisicaoMudanca) throws ServiceException, Exception {
		/*
		 * ControleGEDService controleGedService = (ControleGEDService)
		 * ServiceLocator.getInstance().getService(ControleGEDService.class,
		 * null); Collection<UploadDTO> colAnexosUploadDTO = null; if
		 * (requisicaoMudancaDTO != null &&
		 * requisicaoMudancaDTO.getIdRequisicaoMudanca() != null) { Collection
		 * colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.
		 * TABELA_REQUISICAOMUDANCA,
		 * requisicaoMudancaDTO.getIdRequisicaoMudanca()); colAnexosUploadDTO =
		 * controleGedService.convertListControleGEDToUploadDTO(colAnexos); if
		 * (colAnexosUploadDTO != null) { for (UploadDTO uploadDTO :
		 * colAnexosUploadDTO) { if (uploadDTO.getDescricao() == null) {
		 * uploadDTO.setDescricao(""); } } } }
		 * request.getSession(true).setAttribute("colUploadsGED",
		 * colAnexosUploadDTO);
		 */
		Collection<UploadDTO> colAnexosUploadDTO = null;
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndIdLiberacaoAndLigacao(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, idRequisicaoMudanca);
		colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		/**
		 * ================================= Restaura anexo(s) principal.
		 * =================================
		 * **/
		if (colAnexosUploadDTO != null) {
			for (UploadDTO uploadDTO : colAnexosUploadDTO) {
				if (uploadDTO.getDescricao() == null) {
					uploadDTO.setDescricao("");
				}
			}
		}

		request.getSession(true).setAttribute("colUploadPlanoDeReversaoLiberacaoGED", colAnexosUploadDTO);
	}
}
