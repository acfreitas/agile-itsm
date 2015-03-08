package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

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
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoGrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoService;
import br.com.centralit.citcorpore.negocio.NotificacaoUsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados.OrigemNotificacao;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Notificacao extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.preencherComboTipoNotificacao(document, request, response);

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
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		NotificacaoDTO notificacao = (NotificacaoDTO) document.getBean();

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		notificacao.setListaDeUsuario((ArrayList<NotificacaoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoUsuarioDTO.class,
				"usuariosSerializados", request));
		notificacao.setListaDeGrupo((ArrayList<NotificacaoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoGrupoDTO.class,
				"gruposSerializados", request));

		if (notificacao.getIdNotificacao() == null || notificacao.getIdNotificacao().intValue() == 0) {
			if (notificacao.getOrigemNotificacao().equals("S")) {
				notificacao.setOrigemNotificacao(OrigemNotificacao.S.name());
			}
			if (notificacaoService.consultarNotificacaoAtivos(notificacao)) {
				document.alert("Registro ja existente!");
				return;
			}
			notificacaoService.create(notificacao);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			
			notificacaoService.update(notificacao);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		document.executeScript("$('#gridUsuario').hide()");
		document.executeScript("$('#gridGrupo').hide()");
		HTMLForm form = document.getForm("form");
		form.clear();

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		NotificacaoDTO notificacaoDto = (NotificacaoDTO) document.getBean();

		if (notificacaoDto.getIdNotificacao() == null) {

			return;
		}

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		NotificacaoGrupoService notificacaoGrupoService = (NotificacaoGrupoService) ServiceLocator.getInstance().getService(NotificacaoGrupoService.class, null);

		NotificacaoUsuarioService notificacaoUsuarioService = (NotificacaoUsuarioService) ServiceLocator.getInstance().getService(NotificacaoUsuarioService.class, null);

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

	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		NotificacaoDTO notificacaoDto = (NotificacaoDTO) document.getBean();

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		if (notificacaoDto.getIdNotificacao() != null || notificacaoDto.getIdNotificacao() != 0) {
			notificacaoDto.setDataFim(UtilDatas.getDataAtual());
			notificacaoService.delete(notificacaoDto);
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
		comboTipoNotificacao.addOption("T", "Tudo for alterado");
		comboTipoNotificacao.addOption("C", "Novas nofiticações for adicionadas");
		comboTipoNotificacao.addOption("A", "Notificações forem alteradas");
		comboTipoNotificacao.addOption("E", "Notificações forem excluidas");

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
