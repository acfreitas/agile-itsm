package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AssinaturaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemGrupoAssinaturaDTO;
import br.com.centralit.citcorpore.negocio.AssinaturaService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemGrupoAssinaturaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author euler.ramos
 *
 */
@SuppressWarnings("rawtypes")
public class Assinatura extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
	StringBuilder objeto;
	objeto = new StringBuilder();
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.solicitacao"));
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.autorizacao"));
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.aprovacao"));
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.execucao"));
	document.getElementById("listaFases").setInnerHTML(objeto.toString());

	objeto = new StringBuilder();
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.solicitanteServicos"));
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.gestorOperacionalContrato"));
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.gestorContrato"));
	objeto.append("<option> "+UtilI18N.internacionaliza(request, "citcorpore.comum.prepostoContratada"));
	document.getElementById("listaPapeis").setInnerHTML(objeto.toString());
    }

    @Override
    public Class getBeanClass() {
	return AssinaturaDTO.class;
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	AssinaturaDTO assinaturaDTO = (AssinaturaDTO) document.getBean();
	AssinaturaService assinaturaService = (AssinaturaService) ServiceLocator.getInstance().getService(AssinaturaService.class,null);

	// Há possibilidade de o usuário desejar uma assinatura sem empregado.
	if ((assinaturaDTO.getIdEmpregado() != null)
		&& (assinaturaDTO.getIdEmpregado().equals(0))) {
	    assinaturaDTO.setIdEmpregado(null);
	}

	if (!assinaturaService.violaIndiceUnico(assinaturaDTO)) {
	    if (assinaturaDTO.getIdAssinatura() != null) {
		assinaturaService.update(assinaturaDTO);
		document.alert(UtilI18N.internacionaliza(request,
			"assinatura.assinaturaAtualizada"));
	    } else {
		assinaturaDTO.setDataInicio(UtilDatas.getDataAtual());
		assinaturaService.create(assinaturaDTO);
		document.alert(UtilI18N.internacionaliza(request,
			"assinatura.assinaturaCadastrada"));
	    }
	    HTMLForm form = document.getForm("form");
	    form.clear();
	} else {
	    document.alert(UtilI18N.internacionaliza(request,
		    "citcorpore.comum.registroJaAdicionado"));
	}
    }


    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	AssinaturaDTO assinaturaDTO = (AssinaturaDTO) document.getBean();
	AssinaturaService assinaturaService = (AssinaturaService) ServiceLocator.getInstance().getService(AssinaturaService.class,null);

	assinaturaDTO = (AssinaturaDTO) assinaturaService.restore(assinaturaDTO);

	if (assinaturaDTO.getIdEmpregado()!= null){
	    EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
	    EmpregadoDTO empregadoDTO = new EmpregadoDTO();
	    empregadoDTO.setIdEmpregado(assinaturaDTO.getIdEmpregado());
	    empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
	    assinaturaDTO.setNomeResponsavel(empregadoDTO.getNome());
	}

	HTMLForm form = document.getForm("form");
	form.clear();
	form.setValues(assinaturaDTO);
    }

    public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	AssinaturaDTO assinaturaDTO = (AssinaturaDTO) document.getBean();

	ItemGrupoAssinaturaService itemGrupoAssinaturaService = (ItemGrupoAssinaturaService) ServiceLocator.getInstance().getService(ItemGrupoAssinaturaService.class, null);
	ArrayList<ItemGrupoAssinaturaDTO> listaItemGrupoAssinaturas = (ArrayList<ItemGrupoAssinaturaDTO>) itemGrupoAssinaturaService.findByIdAssinatura(assinaturaDTO.getIdAssinatura());

	if ((listaItemGrupoAssinaturas == null) || (listaItemGrupoAssinaturas.size() <= 0)) {
	    if (assinaturaDTO.getIdAssinatura() != null) {
		AssinaturaService assinaturaService = (AssinaturaService) ServiceLocator.getInstance().getService(AssinaturaService.class, null);
		assinaturaDTO.setDataFim(UtilDatas.getDataAtual());
		assinaturaService.update(assinaturaDTO);
		document.alert(UtilI18N.internacionaliza(request, "assinatura.assinaturaExcluida"));
	    }
	    HTMLForm form = document.getForm("form");
	    form.clear();
	} else {
	    document.alert(UtilI18N.internacionaliza(request, "assinatura.alerta.exclusaoNaoPermitida"));
	}

    }

}