package br.com.centralit.citcorpore.rh.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.borges
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class Candidato extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return CandidatoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.focusInFirstActivateField(null);
		this.preencherComboTipo(document, request, response);
	}

	/**
	 * Preenche o combo do tipo
	 * 
	 * @author Thiago.Borges
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void preencherComboTipo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect tipos = (HTMLSelect) document.getSelectById("tipo");
		tipos.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		tipos.addOption("C", UtilI18N.internacionaliza(request, "colaborador.colaborador"));
		tipos.addOption("E", UtilI18N.internacionaliza(request, "candidato.exColaborador"));
		tipos.addOption("A", UtilI18N.internacionaliza(request, "candidato.colaboradorAfastado"));
		tipos.addOption("F", UtilI18N.internacionaliza(request, "candidato.candidatoExterno"));
	}

	/**
	 * Salva ou atualiza candidato
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		CandidatoDTO candidatoDTO = (CandidatoDTO) document.getBean();

		candidatoDTO.setIdResponsavel(usuario.getIdUsuario());
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);

		if (candidatoDTO.getIdCandidato() == null || candidatoDTO.getIdCandidato() == 0) {
			if (candidatoService.consultarCandidatosAtivos(candidatoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			
			CandidatoDTO candidatoDtoAux = null;
			candidatoDtoAux = candidatoService.findByEmail(candidatoDTO.getEmail());
			
			if(candidatoDtoAux != null){
				document.alert(UtilI18N.internacionaliza(request, "candidato.emailEmUso"));
				return;
			}

			
			if (candidatoDTO.getSenha() == null || candidatoDTO.getSenha().equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "candidato.senhaObrigatorio"));
				return;
			}
			String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
			if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
				algoritmo = "SHA-1";
			}
			candidatoDTO.setSenha(CriptoUtils.generateHash(candidatoDTO.getSenha(), algoritmo));
			candidatoDTO.setDataInicio(UtilDatas.getDataAtual());
			candidatoDTO.setSituacao("C");
			candidatoDTO.setCpf(candidatoDTO.getCpf().replaceAll("[^0-9]*", ""));
			candidatoService.create(candidatoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			HTMLForm form = document.getForm("form");
			form.clear();

		} else {
			if (candidatoDTO.getSenha() == null || candidatoDTO.getSenha().equals("")) {
				CandidatoDTO auxCandidatoDTO = (CandidatoDTO) candidatoService.restore(candidatoDTO);
				candidatoDTO.setSenha(auxCandidatoDTO.getSenha());
			} else {
				String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
				if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
					algoritmo = "SHA-1";
				}
				candidatoDTO.setSenha(CriptoUtils.generateHash(candidatoDTO.getSenha(), algoritmo));
			}
			candidatoService.update(candidatoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			HTMLForm form = document.getForm("form");
			form.clear();
		}
	}

	/**
	 * Recupera candidato.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CandidatoDTO candidatoDto = (CandidatoDTO) document.getBean();

		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);

		candidatoDto = (CandidatoDTO) candidatoService.restore(candidatoDto);
		candidatoDto.setCpf(candidatoDto.getCpfFormatado());
		candidatoDto.setSenha(null);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(candidatoDto);
	}

	/**
	 * Seta uma data final para o candidato("exclusão")
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CandidatoDTO candidatoDto = (CandidatoDTO) document.getBean();
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);

		if (candidatoDto.getSenha() == null || candidatoDto.getSenha().equals("")) {
			CandidatoDTO auxCandidatoDTO = (CandidatoDTO) candidatoService.restore(candidatoDto);
			candidatoDto.setSenha(auxCandidatoDTO.getSenha());
		} else {
			String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
			if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
				algoritmo = "SHA-1";
			}
			candidatoDto.setSenha(CriptoUtils.generateHash(candidatoDto.getSenha(), algoritmo));
		}
		if (candidatoDto.getIdCandidato().intValue() > 0) {
			candidatoService.deletarCandidato(candidatoDto, document);
		}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		HTMLForm form = document.getForm("form");
		form.clear();
	}
}
