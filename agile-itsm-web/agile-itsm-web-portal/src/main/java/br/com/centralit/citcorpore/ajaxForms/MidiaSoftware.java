package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.MidiaDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.bean.TipoSoftwareDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.MidiaService;
import br.com.centralit.citcorpore.negocio.MidiaSoftwareService;
import br.com.centralit.citcorpore.negocio.TipoSoftwareService;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class MidiaSoftware extends AjaxFormAction {

	private MidiaSoftwareDTO midiaSoftwareBean;

	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MidiaService midiaService = (MidiaService) ServiceLocator.getInstance().getService(
				MidiaService.class, null);
		HTMLSelect selectMidia = (HTMLSelect) document.getSelectById("idMidia");
		Collection<MidiaDTO> lista =  midiaService.list();

		selectMidia.removeAllOptions();
		selectMidia.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (MidiaDTO sis : lista) {
			if(sis.getNome().equalsIgnoreCase("Cartão de Memória")){
				selectMidia.addOption(String.valueOf(sis.getIdMidia()),UtilI18N.internacionaliza(request, "midiaSoftware.cartaoMemoria"));
			} else if(sis.getNome().equalsIgnoreCase("Disquete")){
				selectMidia.addOption(String.valueOf(sis.getIdMidia()), UtilI18N.internacionaliza(request, "midiaSoftware.disquete"));
			} else if(sis.getNome().equalsIgnoreCase("Fita Magnética")){
				selectMidia.addOption(String.valueOf(sis.getIdMidia()), UtilI18N.internacionaliza(request, "midiaSoftware.fitaMagnetica"));
			} else if(sis.getNome().equalsIgnoreCase("Outros")){
				selectMidia.addOption(String.valueOf(sis.getIdMidia()), UtilI18N.internacionaliza(request, "colaborador.outros"));
			} else{
				selectMidia.addOption(String.valueOf(sis.getIdMidia()), sis.getNome());
			}
		}

		TipoSoftwareService tipoSoftwareService = (TipoSoftwareService) ServiceLocator.getInstance().getService(
				TipoSoftwareService.class, null);
		HTMLSelect selectTipo = (HTMLSelect) document.getSelectById("idTipoSoftware");
		Collection<TipoSoftwareDTO> lista2 = tipoSoftwareService.list();

		selectTipo.removeAllOptions();
		selectTipo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (TipoSoftwareDTO sis : lista2) {
			if(sis.getNome().equalsIgnoreCase("Auxiliar de Escritório")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "midiaSoftware.auxiliarDeEscritorio"));
			}else if(sis.getNome().equalsIgnoreCase("Comunicador Instantâneo")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "midiaSoftware.comunicadorInstantaneo"));
			}else if(sis.getNome().equalsIgnoreCase("Editor de Imagem")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "midiaSoftware.editorImagem"));
			}else if(sis.getNome().equalsIgnoreCase("Editor de Texto")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "midiaSoftware.editorTexto"));
			}else if(sis.getNome().equalsIgnoreCase("Navegador")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "midiaSoftware.navegador"));
			}else if(sis.getNome().equalsIgnoreCase("Outros")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "colaborador.outros"));
			}else if(sis.getNome().equalsIgnoreCase("Sistema Operacional")){
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), UtilI18N.internacionaliza(request, "citcorpore.comum.sistemaOperacional"));
			} else {
				selectTipo.addOption(String.valueOf(sis.getIdTipoSoftware()), sis.getNome());
		    }
		}
	}

	@SuppressWarnings("unchecked")
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MidiaSoftwareDTO midiaSoftware = (MidiaSoftwareDTO) document.getBean();

		MidiaSoftwareService midiaSoftwareService = (MidiaSoftwareService) ServiceLocator.getInstance().getService(MidiaSoftwareService.class, null);
		List<MidiaSoftwareChaveDTO> listaChaves =  (List<MidiaSoftwareChaveDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(MidiaSoftwareChaveDTO.class, "midiaSoftwareChaveSerializada", request);
		midiaSoftware.setMidiaSoftwareChaves(listaChaves);

		if (midiaSoftware.getIdMidiaSoftware() == null || midiaSoftware.getIdMidiaSoftware() == 0) {
			if (midiaSoftwareService.consultarMidiasAtivas(midiaSoftware)) {
				document.alert(UtilI18N.internacionaliza(request, "MSE01"));
				return;
			}
			midiaSoftware.setDataInicio(UtilDatas.getDataAtual());
			midiaSoftwareService.create(midiaSoftware);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (midiaSoftwareService.consultarMidiasAtivas(midiaSoftware)) {
				document.alert(UtilI18N.internacionaliza(request, "MSE01"));
				return;
			}
			midiaSoftwareService.update(midiaSoftware);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar()");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MidiaSoftwareDTO midiaSoftware = (MidiaSoftwareDTO) document.getBean();
		MidiaSoftwareService midiaSoftwareService = (MidiaSoftwareService) ServiceLocator.getInstance().getService(MidiaSoftwareService.class, null);
		midiaSoftware = (MidiaSoftwareDTO) midiaSoftwareService.restore(midiaSoftware);

		if(midiaSoftware.getMidiaSoftwareChaves() != null) {
			HTMLTable table;
			table = document.getTableById("tblMidiaSoftwareChave");
			table.deleteAllRows();
			table.addRowsByCollection(midiaSoftware.getMidiaSoftwareChaves(), new String[] {"", "chave", "qtdPermissoes"}, null, null, new String[] {"gerarButtonDelete"}, "funcaoClickRow", null);
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(midiaSoftware);
	}

	public void update(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MidiaSoftwareDTO midiaSoftware = (MidiaSoftwareDTO) document.getBean();
		MidiaSoftwareService midiaSoftwareService = (MidiaSoftwareService) ServiceLocator.getInstance().getService(MidiaSoftwareService.class, null);
		ItemConfiguracaoService itemService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

		if(!itemService.verificaMidiaSoftware(midiaSoftware.getIdMidiaSoftware())) {
			midiaSoftware.setDataFim(UtilDatas.getDataAtual());
			midiaSoftwareService.update(midiaSoftware);

			HTMLForm form = document.getForm("form");
			form.clear();
			document.executeScript("limpar()");
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}else {
			document.alert("Mídia possui vínculos a Item de configuração");
		}
	}

	public void setMidiaSoftwareBean(IDto softwares) {
		this.midiaSoftwareBean = (MidiaSoftwareDTO) softwares;
	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return MidiaSoftwareDTO.class;
	}

	public MidiaSoftwareDTO getMidiaSoftwareBean() {
		return this.midiaSoftwareBean;
	}



}
