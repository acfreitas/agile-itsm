package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.DeParaCatalogoServicosBIDTO;
import br.com.centralit.citcorpore.bean.ServicoCorporeBIDTO;
import br.com.centralit.citcorpore.negocio.DeParaCatalogoServicosBIService;
import br.com.centralit.citcorpore.negocio.ServicoCorporeBIService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class ServicoCorporeBI extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {

    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse respose) throws Exception {
        ServicoCorporeBIDTO servicoCorporeBIDTO = (ServicoCorporeBIDTO) document.getBean();

        ServicoCorporeBIService service = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(ServicoCorporeBIService.class, null);

        if (servicoCorporeBIDTO.getIdServicoCorpore() == null) {
            servicoCorporeBIDTO = (ServicoCorporeBIDTO) service.create(servicoCorporeBIDTO);
            document.alert(UtilI18N.internacionaliza(request, "servicoCorporeBI.cadastrado"));
        } else {
            service.update(servicoCorporeBIDTO);
            document.alert(UtilI18N.internacionaliza(request, "servicoCorporeBI.atualizado"));
        }

        HTMLForm form = document.getForm("form");
        form.clear();
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServicoCorporeBIDTO servicoCorporeBIDTO = (ServicoCorporeBIDTO) document.getBean();

        ServicoCorporeBIService service = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(ServicoCorporeBIService.class, null);

        servicoCorporeBIDTO = (ServicoCorporeBIDTO) service.restore(servicoCorporeBIDTO);

        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(servicoCorporeBIDTO);
    }

    public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServicoCorporeBIDTO servicoCorporeBIDTO = (ServicoCorporeBIDTO) document.getBean();

        ServicoCorporeBIService service = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(ServicoCorporeBIService.class, null);
        DeParaCatalogoServicosBIService deParaCatalogoServicoService = (DeParaCatalogoServicosBIService) ServiceLocator.getInstance().getService(DeParaCatalogoServicosBIService.class, null);

        if (servicoCorporeBIDTO.getIdServicoCorpore() != null) {
            Collection<DeParaCatalogoServicosBIDTO> servicosDePara = deParaCatalogoServicoService.findByidServicoPara(servicoCorporeBIDTO.getIdServicoCorpore(), null);

            if (servicosDePara != null && servicosDePara.size() > 0) {
                document.alert(UtilI18N.internacionaliza(request, "deParaCatalogoServicos.naoFoiPossivelExcluirRelacionado"));
            } else {
                service.delete(servicoCorporeBIDTO);
                document.alert(UtilI18N.internacionaliza(request, "servicoCorporeBI.excluido"));
            }
        } else {
            document.alert(UtilI18N.internacionaliza(request, "deParaCatalogoServicos.naoFoiPossivelExcluir"));
        }

        HTMLForm form = document.getForm("form");

        form.clear();
    }

    @Override
    public Class getBeanClass() {
        return ServicoCorporeBIDTO.class;
    }

}
