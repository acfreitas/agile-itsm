package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.ExecucaoAtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.MotivoSuspensaoAtividadeService;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class ExecucaoAtividadePeriodica extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
	return ExecucaoAtividadePeriodicaDTO.class;
    }

    @Override
    public void load(DocumentHTML arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {

    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) document.getBean();

		if (execucaoAtividadePeriodicaDTO.getSituacao() == null) {
		    document.alert("Informe a situação");
		    return;
		}
		if (execucaoAtividadePeriodicaDTO.getSituacao().equals("S") && execucaoAtividadePeriodicaDTO.getIdMotivoSuspensao() == null) {
		    document.alert("Informe o motivo da suspensão");
		    return;
		}

		ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService) ServiceLocator.getInstance()
			.getService(ExecucaoAtividadePeriodicaService.class, null);

		execucaoAtividadePeriodicaDTO.setDataRegistro(UtilDatas.getDataAtual());
		execucaoAtividadePeriodicaDTO.setHoraRegistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));

		Collection colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED");
		if (colUploadsGED == null) {
		    colUploadsGED = new ArrayList();
		}
		execucaoAtividadePeriodicaDTO.setColArquivosUpload(colUploadsGED);

		if (execucaoAtividadePeriodicaDTO.getIdExecucaoAtividadePeriodica() == null || execucaoAtividadePeriodicaDTO.getIdExecucaoAtividadePeriodica().intValue() == 0) {
		    execucaoAtividadePeriodicaService.create(execucaoAtividadePeriodicaDTO);
		} else {
		    execucaoAtividadePeriodicaService.update(execucaoAtividadePeriodicaDTO);
		}
		HTMLForm form = document.getForm("form");
		// document.getJanelaPopupById("POPUP_REGISTRO").hide();

		document.executeScript("refreshEvents()");
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) document.getBean();
	ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService) ServiceLocator.getInstance()
		.getService(ExecucaoAtividadePeriodicaService.class, null);

	request.getSession(true).setAttribute("colUploadsGED", null);
	document.executeScript("uploadAnexos.clear()");
	document.executeScript("uploadAnexos.refresh()");

	if (execucaoAtividadePeriodicaDTO.getIdExecucaoAtividadePeriodica() == null || execucaoAtividadePeriodicaDTO.getIdExecucaoAtividadePeriodica().intValue() == 0)
	    return;

	execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) execucaoAtividadePeriodicaService.restore(execucaoAtividadePeriodicaDTO);

	Collection colUploadsGED = new ArrayList();
	if (execucaoAtividadePeriodicaDTO.getColArquivosUpload() != null) {
	    for (Iterator it = execucaoAtividadePeriodicaDTO.getColArquivosUpload().iterator(); it.hasNext();) {
		ControleGEDDTO controleGEDDTO = (ControleGEDDTO) it.next();

		UploadDTO uploadDTO = new UploadDTO();
		uploadDTO.setDescricao(controleGEDDTO.getDescricaoArquivo());
		uploadDTO.setNameFile(controleGEDDTO.getNomeArquivo());
		uploadDTO.setSituacao("Arquivado");
		uploadDTO.setTemporario("N");
		uploadDTO.setPath("ID=" + controleGEDDTO.getIdControleGED());

		colUploadsGED.add(uploadDTO);
	    }
	}
	request.getSession(true).setAttribute("colUploadsGED", colUploadsGED);

	HTMLForm form = document.getForm("form");
	form.setValues(execucaoAtividadePeriodicaDTO);
	document.executeScript("configuraMotivoSuspensao('" + execucaoAtividadePeriodicaDTO.getSituacao() + "');");
	document.executeScript("document.getElementById('tabTela').tabber.tabShow(0);");
	document.executeScript("uploadAnexos.refresh()");
    }

    public void visualizarOrientacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) document.getBean();
	AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);

	AtividadePeriodicaDTO atvDto = new AtividadePeriodicaDTO();
	atvDto.setIdAtividadePeriodica(execucaoAtividadePeriodicaDTO.getIdAtividadePeriodica());
	atvDto = (AtividadePeriodicaDTO) atividadePeriodicaService.restore(atvDto);

	if (atvDto != null) {
	    String str = atvDto.getOrientacaoTecnica();
	    if (str == null) {
		str = "";
	    }
	    str = str.replaceAll("'", "");
	    str = str.replaceAll("\n", "<br>");
	    document.getElementById("divOrientacao").setInnerHTML(str);
	   /* document.getJanelaPopupById("POPUP_ORIENTACAO").showInYPosition(50);*/
	    document.executeScript("$('#POPUP_ORIENTACAO').dialog('open')");
	}
    }

	public void carregarComboMotivo(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		((HTMLSelect) document.getSelectById("idMotivoSuspensao")).removeAllOptions();
		HTMLSelect idMotivoSuspensao = (HTMLSelect) document.getSelectById("idMotivoSuspensao");
		idMotivoSuspensao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		MotivoSuspensaoAtividadeService motivoSuspensaoService = (MotivoSuspensaoAtividadeService) ServiceLocator.getInstance().getService(MotivoSuspensaoAtividadeService.class, null);
	    Collection colMotivos = motivoSuspensaoService.listarMotivosSuspensaoAtividadeAtivos();
	    idMotivoSuspensao.addOptions(colMotivos, "idMotivo", "descricao", "");
	}

}
