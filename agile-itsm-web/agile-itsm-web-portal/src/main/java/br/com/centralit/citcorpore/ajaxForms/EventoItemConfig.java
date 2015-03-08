package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.BaseItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.EventoItemConfigService;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfigEventoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class EventoItemConfig extends AjaxFormAction {
	public Class<?> getBeanClass() {
		return EventoItemConfigDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario usuario = (Usuario) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
		if (usuario == null) {
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}

		Map<?, ?> map = document.getForm("form").getDocument().getValuesForm();

		ItemConfigEventoDTO itemConfigEventoDto;
		List<ItemConfigEventoDTO> lstItensConfig = new ArrayList<ItemConfigEventoDTO>();

		// Adiciona na lista os ids dos grupos selecionados
		List<EventoGrupoDTO> lstEventoGrupos = new ArrayList<EventoGrupoDTO>();
		EventoGrupoDTO eventoGrupoDTO;
		Object mapIdGrupo = map.get("IDGRUPO");
		if (mapIdGrupo != null) {
			if (mapIdGrupo.getClass().getName().equalsIgnoreCase("[Ljava.lang.String;")) {
				String[] arrayIdGrupo = (String[]) mapIdGrupo;
				for (String idGrupo : arrayIdGrupo) {
					eventoGrupoDTO = new EventoGrupoDTO();
					eventoGrupoDTO.setIdGrupo(Integer.valueOf(idGrupo));
					lstEventoGrupos.add(eventoGrupoDTO);
				}
			} else {
				String idGrupo = (String) mapIdGrupo;
				if (!idGrupo.equals("")) {
					eventoGrupoDTO = new EventoGrupoDTO();
					eventoGrupoDTO.setIdGrupo(Integer.valueOf(idGrupo));
					lstEventoGrupos.add(eventoGrupoDTO);
				}
			}
		}

		// Adiciona na lista ids dos itens de configuração
		List<EventoItemConfigRelDTO> lstItensConfiguracao = new ArrayList<EventoItemConfigRelDTO>();
		EventoItemConfigRelDTO eventoItemConfigRelDTO;
		Object mapIdItemConfiguracao = map.get("IDITEMCONFIGURACAO");
		if (mapIdItemConfiguracao != null) {
			if (mapIdItemConfiguracao.getClass().getName().equalsIgnoreCase("[Ljava.lang.String;")) {
				String[] arrayIdItemConf = (String[]) mapIdItemConfiguracao;
				for (String idItemConf : arrayIdItemConf) {
					eventoItemConfigRelDTO = new EventoItemConfigRelDTO();
					eventoItemConfigRelDTO.setIdItemConfiguracao(Integer.valueOf(idItemConf));
					lstItensConfiguracao.add(eventoItemConfigRelDTO);
				}
			} else {
				String idItemConf = (String) mapIdItemConfiguracao;
				if (!idItemConf.equals("")) {
					eventoItemConfigRelDTO = new EventoItemConfigRelDTO();
					eventoItemConfigRelDTO.setIdItemConfiguracao(Integer.valueOf(idItemConf));
					lstItensConfiguracao.add(eventoItemConfigRelDTO);
				}
			}
		}

		// Adiciona na lista de itens de configuração
		Object mapIdItemConfig = map.get("IDITEMCONFIG");
		Object mapTipoExec = map.get("TIPOEXECUCAO");
		Object mapGerarQuando = map.get("DISPARAEVENTO");
		Object mapData = map.get("DATAEVENTO");
		Object mapHora = map.get("HORAEVENTO");
		Object mapComando = map.get("COMANDO");
		Object mapComandoLinux = map.get("COMANDOLINUX");
		if (mapIdItemConfig != null) {
			if (mapIdItemConfig.getClass().getName().equalsIgnoreCase("[Ljava.lang.String;")) {
				String[] arrayIdItemConfig = (String[]) mapIdItemConfig;
				String[] arrayTipoExec = (String[]) mapTipoExec;
				String[] arrayGerarQuando = (String[]) mapGerarQuando;
				String[] arrayData = (String[]) mapData;
				String[] arrayHora = (String[]) mapHora;
				String[] arrayComando = (String[]) mapComando;
				String[] arrayComandoLinux = (String[]) mapComandoLinux;
				for (int i = 0; i < arrayIdItemConfig.length; i++) {
					itemConfigEventoDto = new ItemConfigEventoDTO();
					itemConfigEventoDto.setIdBaseItemConfiguracao(Integer.valueOf(arrayIdItemConfig[i]));
					itemConfigEventoDto.setTipoExecucao(arrayTipoExec[i]);
					itemConfigEventoDto.setGerarQuando(arrayGerarQuando[i]);
					if (arrayGerarQuando[i].equalsIgnoreCase("F")) {
						itemConfigEventoDto.setData(UtilDatas.strToSQLDate(arrayData[i]));
						itemConfigEventoDto.setHora(arrayHora[i].replace(":", ""));
					} else {
						itemConfigEventoDto.setData(UtilDatas.getDataAtual());
						itemConfigEventoDto.setHora(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()).replaceAll(":", ""));
					}
					itemConfigEventoDto.setLinhaComando(arrayComando[i]);
					itemConfigEventoDto.setLinhaComandoLinux(arrayComandoLinux[i]);
					lstItensConfig.add(itemConfigEventoDto);
				}
			} else {
				String idItemConfig = (String) mapIdItemConfig;
				String tipoExec = (String) mapTipoExec;
				String gerarQuando = (String) mapGerarQuando;
				String data = (String) mapData;
				String hora = (String) mapHora;
				String comando = (String) mapComando;
				String comandoLinux = (String) mapComandoLinux;
				if (!idItemConfig.equals("")) {
					itemConfigEventoDto = new ItemConfigEventoDTO();
					itemConfigEventoDto.setIdBaseItemConfiguracao(Integer.valueOf(idItemConfig));
					itemConfigEventoDto.setTipoExecucao(tipoExec);
					itemConfigEventoDto.setGerarQuando(gerarQuando);
					if (gerarQuando.equalsIgnoreCase("F")) {
						itemConfigEventoDto.setData(UtilDatas.strToSQLDate(data));
						itemConfigEventoDto.setHora(hora.replace(":", ""));
					} else {
						itemConfigEventoDto.setData(UtilDatas.getDataAtual());
						itemConfigEventoDto.setHora(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()).replaceAll(":", ""));
					}
					itemConfigEventoDto.setLinhaComando(comando);
					itemConfigEventoDto.setLinhaComandoLinux(comandoLinux);
					lstItensConfig.add(itemConfigEventoDto);
				}
			}
		}

		EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) document.getBean();
		eventoDto.setIdEmpresa(usuario.getIdEmpresa());
		eventoDto.setLstItemConfigEvento(lstItensConfig);
		eventoDto.setLstGrupo(lstEventoGrupos);
		eventoDto.setLstItemConfiguracao(lstItensConfiguracao);

		EventoItemConfigService eventoService = (EventoItemConfigService) ServiceLocator.getInstance().getService(EventoItemConfigService.class, null);

		if (eventoDto.getIdEvento() == null || eventoDto.getIdEvento().intValue() == 0) {
			eventoDto.setDataInicio(UtilDatas.getDataAtual());
			eventoService.create(eventoDto);
		} else {
			eventoService.update(eventoDto);
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpaFormulario()");
		document.executeScript("limpar_LOOKUP_EVENTOS()");

		document.alert("Registro gravado com sucesso!");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) document.getBean();
		EventoItemConfigService eventoService = (EventoItemConfigService) ServiceLocator.getInstance().getService(EventoItemConfigService.class, null);
		ItemConfigEventoService itemConfigEventoService = (ItemConfigEventoService) ServiceLocator.getInstance().getService(ItemConfigEventoService.class, null);
		GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

		eventoDto = (EventoItemConfigDTO) eventoService.restore(eventoDto);

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpaFormulario()");

		HTMLElement btnExcluirElement = (HTMLElement) document.getElementById("btnExcluir");
		if (eventoDto != null && eventoDto.getIdEvento() > 0) {
			btnExcluirElement.setVisible(true);

			form.setValues(eventoDto);
		}

		Collection<GrupoItemConfiguracaoDTO> colEventoGrupo = new ArrayList<GrupoItemConfiguracaoDTO>();

		if (eventoDto != null) {
			colEventoGrupo = grupoItemConfiguracaoService.listByEvento(eventoDto.getIdEvento());
		}

		if (colEventoGrupo != null && !colEventoGrupo.isEmpty()) {

			for (GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO : colEventoGrupo) {
				document.executeScript("addLinhaTabelaGrupoItemConfig(" + grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao() + ", '" + grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao()
						+ "', " + false + ");");
			}
		}

		Collection<ItemConfiguracaoDTO> colItemConf = new ArrayList<ItemConfiguracaoDTO>();

		if (eventoDto != null) {
			colItemConf = itemConfiguracaoService.listByEvento(eventoDto.getIdEvento());
		}

		if (colItemConf != null && !colItemConf.isEmpty()) {

			for (ItemConfiguracaoDTO itemConfigEventoDTO : colItemConf) {
				document.executeScript("addLinhaTabelaItemConfig(" + itemConfigEventoDTO.getIdItemConfiguracao() + ", '" + itemConfigEventoDTO.getIdentificacao() + "', " + false + ");");
			}
		}

		Collection<ItemConfigEventoDTO> colItemConfigEvento = new ArrayList<ItemConfigEventoDTO>();

		if (eventoDto != null) {

			colItemConfigEvento = itemConfigEventoService.listByIdEvento(eventoDto.getIdEvento());
		}

		if (colItemConfigEvento != null) {

			for (ItemConfigEventoDTO itemConfigEventoDto : colItemConfigEvento) {
				document.executeScript("addItemConfigRestore(" + itemConfigEventoDto.getIdBaseItemConfiguracao() + ", '" + itemConfigEventoDto.getNomeBaseItemConfiguracao() + "', '"
						+ itemConfigEventoDto.getTipoExecucao() + "', '" + itemConfigEventoDto.getGerarQuando() + "', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, itemConfigEventoDto.getData(), WebUtil.getLanguage(request)) + "', '"
						+ UtilDatas.formatHoraStr(itemConfigEventoDto.getHora()) + "', '" + itemConfigEventoDto.getLinhaComando() + "', '"
						+ (itemConfigEventoDto.getLinhaComandoLinux() == null ? "" : itemConfigEventoDto.getLinhaComandoLinux()) + "')");
			}
		}
	}

	public void restoreIdentificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventoItemConfigDTO eventoItemConfigDto = (EventoItemConfigDTO) document.getBean();

		BaseItemConfiguracaoDTO baseItemConfiguracaoDto = new BaseItemConfiguracaoDTO();
		BaseItemConfiguracaoService baseItemConfiguracaoService = (BaseItemConfiguracaoService) ServiceLocator.getInstance().getService(BaseItemConfiguracaoService.class, null);
		baseItemConfiguracaoDto.setId(eventoItemConfigDto.getIdItemCfg());
		baseItemConfiguracaoDto = (BaseItemConfiguracaoDTO) baseItemConfiguracaoService.restore(baseItemConfiguracaoDto);

		eventoItemConfigDto.setNomeItemCfg(baseItemConfiguracaoDto.getNome());
		eventoItemConfigDto.setLinhaComando(baseItemConfiguracaoDto.getComando());

		HTMLForm form = document.getForm("form");
		form.setValues(eventoItemConfigDto);
		document.executeScript("fecharPopup()");

	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario usuario = (Usuario) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
		if (usuario == null) {
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}

		EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) document.getBean();
		eventoDto.setIdEmpresa(usuario.getIdEmpresa());
		EventoItemConfigService eventoService = (EventoItemConfigService) ServiceLocator.getInstance().getService(EventoItemConfigService.class, null);

		if (eventoDto.getIdEvento().intValue() > 0) {
			eventoDto.setDataFim(UtilDatas.getDataAtual());
			eventoService.update(eventoDto);
		}

		document.getElementById("btnExcluir").setVisible(false);

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpaFormulario()");
		document.executeScript("limpar_LOOKUP_EVENTOS()");

		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

}
