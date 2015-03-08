package br.com.centralit.citcorpore.rh.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class TrabalheConosco extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CandidatoDTO candidatoDTO = (CandidatoDTO) request.getSession().getAttribute("CANDIDATO");
		String idCandidatoParametro = request.getParameter("id");
		String autentica = request.getParameter("autentica");

		if (idCandidatoParametro != null) {

			String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
			if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
				algoritmo = "SHA-1";
			}

			CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
			CandidatoDTO colCandidatoAux = candidatoService.findByHashID(idCandidatoParametro);
			if (colCandidatoAux != null) {
				candidatoDTO = colCandidatoAux;
			} else {
				document.alert(UtilI18N.internacionaliza(request, "rh.recuperaSenhaExpirada"));
				document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
				return;
			}

			if (autentica != null && autentica.equals("S")) {
				if (candidatoDTO != null && candidatoDTO.getAutenticado() != null && candidatoDTO.getAutenticado().equals("N")) {
					candidatoDTO.setAutenticado("S");
					candidatoDTO.setHashID(idCandidatoParametro);
					candidatoService.update(candidatoDTO);
					document.alert(UtilI18N.internacionaliza(request, "candidato.contaValidadaComSucesso"));
					document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
				} else {
					document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
					return;
				}
			}

			if (candidatoDTO != null && candidatoDTO.getAutenticado() != null && candidatoDTO.getAutenticado().equals("N")) {
				document.alert(UtilI18N.internacionaliza(request, "candidato.usarioNaoAutenticado"));
				document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
				return;
			}
			
			if(idCandidatoParametro != null && candidatoDTO.getAutenticado().equals("S") && autentica == null) {
				String hashID = CriptoUtils.generateHash(UtilDatas.getDataHoraAtual() + "", algoritmo);
				candidatoDTO.setHashID(hashID);
				candidatoService.update(candidatoDTO);
				request.getSession(true).setAttribute("CANDIDATO",candidatoDTO);
			}
		}

		if (candidatoDTO != null) {
			request.setAttribute("nomeCandidatoAbrev", abreviarNomeCandidato(candidatoDTO.getNome()));
			request.setAttribute("nomeCandidato", candidatoDTO.getNome());
			request.setAttribute("emailCandidato", candidatoDTO.getEmail());
			request.setAttribute("metodoAutenticacao", candidatoDTO.getMetodoAutenticacao());
		} else {
			document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return CandidatoDTO.class;
	}

	private String abreviarNomeCandidato(String nameUser) {
		StringBuilder finalNameUser = new StringBuilder();
		if (nameUser != null) {
			String[] array = new String[15];
			int index;

			if (nameUser.contains(" ")) {
				int cont = 0;

				nameUser = nameUser.trim();
				array = nameUser.split(" ");
				index = array.length;

				for (String nome : array) {
					if (cont == 0) {
						finalNameUser.append(nome.toUpperCase() + " ");
						cont++;
					} else {
						if (cont == index - 1) {
							finalNameUser.append(" " + nome.toUpperCase());
						} else {
							if (nome.length() > 3) {
								finalNameUser.append(nome.substring(0, 1).toUpperCase() + ". ");
							}
							cont++;
						}
					}
				}
			} else {
				finalNameUser.append(nameUser.toUpperCase());
			}
		}
		return finalNameUser.toString();
	}

}
