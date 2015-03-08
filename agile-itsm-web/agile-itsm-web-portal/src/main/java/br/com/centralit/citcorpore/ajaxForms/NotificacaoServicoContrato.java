package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoGrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoService;
import br.com.centralit.citcorpore.negocio.NotificacaoServicoService;
import br.com.centralit.citcorpore.negocio.NotificacaoUsuarioService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados.OrigemNotificacao;
import br.com.centralit.citcorpore.util.Enumerados.TipoNotificacao;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NotificacaoServicoContrato extends AjaxFormAction {

	public void listarServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer idContrato;
		String strValor;
		Collection<ServicoContratoDTO> colServicoContrato;
		Set<ServicoDTO> colServico;
		ServicoDTO servicoDTO;
		ServicoContratoService servicoContratoService;
		ServicoService servicoService;

		servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		strValor = request.getParameter("idContrato");
		idContrato = null;
		if (strValor != null) {
			idContrato = Integer.parseInt(strValor);

			colServicoContrato = servicoContratoService.findByIdContrato(idContrato);
			servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
			colServico = new HashSet<ServicoDTO>();
			if ((colServicoContrato != null) && (!colServicoContrato.isEmpty())) {
				for (ServicoContratoDTO objServicoContrato : colServicoContrato) {
					servicoDTO = new ServicoDTO();
					servicoDTO.setIdServico(objServicoContrato.getIdServico());
					servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
					colServico.add(servicoDTO);
				}
				StringBuilder subDiv = new StringBuilder();
				subDiv.append("" + "<div class='formBody'> " + "	<table id='tbServicos' class='tableLess'> 	" + "		<thead>" + "			<tr>"
						+ "				<th><input type='checkbox' id='todos' onclick='check();' name='todos'/></th>	" + "				<th>" + UtilI18N.internacionaliza(request, "problema.servico") + "</th>	"
						+ "			</tr>" + "		</thead><tbody>");

				if ((colServico != null) && (!colServico.isEmpty())) {
					int count = 0;
					for (ServicoDTO objServico : colServico) {
						count++;
						if (objServico != null) {
							subDiv.append("	<tr>" + "		<td align='center'><input type='checkbox' id='idServico" + count + "' name='idServico' value='0" + objServico.getIdServico() + "'/></td>"
									+ "		<td>" + objServico.getNomeServico() + "</td>" + "	</tr>");
						}
					}
					subDiv.append("</tbody></table>");
					document.getElementById("divServicos").setInnerHTML(subDiv.toString());
				}
			}
		}
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.preencherComboTipoNotificacao(document, request, response);
		this.listarServicos(document, request, response);
		NotificacaoDTO notificacao = (NotificacaoDTO) document.getBean();

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		if (request.getParameter("idBaseConhecimento") != null && !StringUtils.isEmpty(request.getParameter("idBaseConhecimento"))) {
			baseConhecimentoDto.setIdBaseConhecimento(Integer.parseInt(request.getParameter("idBaseConhecimento")));
			BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
			baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);
			if (baseConhecimentoDto.getIdNotificacao() != null && baseConhecimentoDto.getIdNotificacao() != 0) {
				notificacao.setIdNotificacao(baseConhecimentoDto.getIdNotificacao());
				this.restore(document, request, response);
			}
		}
		if (notificacao.getIdNotificacao() != null) {
			this.restore(document, request, response);
		}
		// recebe o idnotificação para exclusao do item
		if (notificacao.getIdNotificacaoExcluir() != null) {
			notificacao.setIdNotificacao(notificacao.getIdNotificacaoExcluir());
			this.delete(document, request, response);
		}

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		NotificacaoDTO notificacao = (NotificacaoDTO) document.getBean();

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		notificacao.setListaDeUsuario((ArrayList<NotificacaoUsuarioDTO>) br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(NotificacaoUsuarioDTO.class, "usuariosSerializados", request));
		notificacao.setListaDeGrupo((ArrayList<NotificacaoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoGrupoDTO.class, "gruposSerializados", request));

		notificacao.setListaDeServico((ArrayList<NotificacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoServicoDTO.class, "servicosLancados", request));
		if (notificacao.getListaDeServico() != null) {
			if (notificacao.getIdNotificacao() == null || notificacao.getIdNotificacao().intValue() == 0) {
				if (notificacao.getOrigemNotificacao().equals("S")) {
					notificacao.setOrigemNotificacao(OrigemNotificacao.S.name());
				}
				if (notificacaoService.consultarNotificacaoAtivos(notificacao)) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
					return;
				}
				notificacaoService.create(notificacao);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				notificacaoService.update(notificacao, null);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
			document.executeScript("$('#gridGrupo').hide();");
			document.executeScript("$('#gridUsuario').hide();");
			document.executeScript("fecharPopup();");
			HTMLForm form = document.getForm("form");
			form.clear();
		}else{
			document.alert(UtilI18N.internacionaliza(request, "servicoContrato.selecioneServicos"));
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		NotificacaoDTO notificacaoDto = (NotificacaoDTO) document.getBean();
		if (notificacaoDto.getIdNotificacao() == null) {
			return;
		}

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);
		NotificacaoGrupoService notificacaoGrupoService = (NotificacaoGrupoService) ServiceLocator.getInstance().getService(NotificacaoGrupoService.class, null);
		NotificacaoUsuarioService notificacaoUsuarioService = (NotificacaoUsuarioService) ServiceLocator.getInstance().getService(NotificacaoUsuarioService.class, null);
		NotificacaoServicoService notificacaoServicoService = (NotificacaoServicoService) ServiceLocator.getInstance().getService(NotificacaoServicoService.class, null);
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		notificacaoDto = (NotificacaoDTO) notificacaoService.restore(notificacaoDto);

		document.executeScript("deleteAllRowsUsuario()");
		document.executeScript("deleteAllRowsGrupo()");
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(notificacaoDto);

		Collection<NotificacaoGrupoDTO> listaIdGrupo = notificacaoGrupoService.listaIdGrupo(notificacaoDto.getIdNotificacao());
		Collection<NotificacaoUsuarioDTO> listaIdUsuario = notificacaoUsuarioService.listaIdUsuario(notificacaoDto.getIdNotificacao());

		if (listaIdUsuario != null && !listaIdUsuario.isEmpty()) {
			for (NotificacaoUsuarioDTO notificacaoUsuarioDto : listaIdUsuario) {
				if (notificacaoUsuarioDto.getIdUsuario() != null) {
					UsuarioDTO usuarioDto = new UsuarioDTO();
					usuarioDto.setIdUsuario(notificacaoUsuarioDto.getIdUsuario());
					usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
					document.executeScript("addLinhaTabelaUsuario(" + usuarioDto.getIdUsuario() + ", '" + usuarioDto.getNomeUsuario() + "', " + false + ");");
					document.executeScript("exibirTabelaUsuario()");
					document.executeScript("$('#gridUsuario').show()");
				}
			}
		}

		if (listaIdGrupo != null && !listaIdGrupo.isEmpty()) {
			for (NotificacaoGrupoDTO notificacaoGrupoDto : listaIdGrupo) {
				if (notificacaoGrupoDto.getIdGrupo() != null) {
					GrupoDTO grupoDto = new GrupoDTO();
					grupoDto.setIdGrupo(notificacaoGrupoDto.getIdGrupo());
					grupoDto = (GrupoDTO) grupoService.restore(grupoDto);
					document.executeScript("addLinhaTabelaGrupo(" + grupoDto.getIdGrupo() + ", '" + grupoDto.getNome() + "', " + false + ");");
					document.executeScript("exibirTabelaGrupo()");
					document.executeScript("$('#gridGrupo').show()");
				}
			}
		}

		if (notificacaoDto.getIdContrato() != null) {
			Collection<ServicoContratoDTO> colServicoContrato;
			Set<ServicoDTO> colServico;
			ServicoDTO servicoDTO;
			ServicoContratoService servicoContratoService;
			ServicoService servicoService;
			servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
			colServicoContrato = servicoContratoService.findByIdContrato(notificacaoDto.getIdContrato());

			servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
			colServico = new HashSet<ServicoDTO>();

			if ((colServicoContrato != null) && (!colServicoContrato.isEmpty())) {
				for (ServicoContratoDTO objServicoContrato : colServicoContrato) {
					servicoDTO = new ServicoDTO();
					servicoDTO.setIdServico(objServicoContrato.getIdServico());
					servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
					colServico.add(servicoDTO);
				}
				StringBuilder subDiv = new StringBuilder();
				subDiv.append("" + "<div class='formBody'> " + "	<table id='tbServicos' class='tableLess'> 	" + "		<thead>" + "			<tr>"
						+ "				<th><input type='checkbox' id='todos' onclick='check();' name='todos'/></th>	" + "				<th>" + UtilI18N.internacionaliza(request, "problema.servico") + "</th>	"
						+ "			</tr>" + "		</thead><tbody>");

				if ((colServico != null) && (!colServico.isEmpty())) {
					int count = 0;
					boolean flag = false;
					for (ServicoDTO objServico : colServico) {
						if (objServico != null) {
							flag = notificacaoServicoService.existeServico(notificacaoDto.getIdNotificacao(), objServico.getIdServico());
							String checked = "";
							if (flag)
								checked = "checked='checked'";
							count++;
							subDiv.append("	<tr>" + "		<td align='center'><input type='checkbox' id='idServico" + count + "' name='idServico' " + checked + " value='0" + objServico.getIdServico()
									+ "'/></td>" + "		<td>" + objServico.getNomeServico() + "</td>" + "	</tr>");
						}
					}
					subDiv.append("</tbody></table>");
					document.getElementById("divServicos").setInnerHTML(subDiv.toString());
				}
			}
		}

	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NotificacaoDTO notificacaoDto = (NotificacaoDTO) document.getBean();
		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		if (notificacaoDto.getIdNotificacao() != null || notificacaoDto.getIdNotificacao() != 0) {
			notificacaoDto.setDataFim(UtilDatas.getDataAtual());
			notificacaoService.updateNotNull(notificacaoDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		document.executeScript("$('#gridUsuario').hide()");
		document.executeScript("$('#gridGrupo').hide()");
		HTMLForm form = document.getForm("form");
		form.clear();

	}

	public void preencherComboTipoNotificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboTipoNotificacao = (HTMLSelect) document.getSelectById("tipoNotificacao");
		inicializaCombo(comboTipoNotificacao, request);
		comboTipoNotificacao.addOption("A", UtilI18N.internacionaliza(request, TipoNotificacao.ServALTERADOS.getDescricao()));
		// comboTipoNotificacao.addOption("U", UtilI18N.internacionaliza(request, "citcorpore.comum.servicosForemUtilizados"));
	}

	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return NotificacaoDTO.class;
	}

}
