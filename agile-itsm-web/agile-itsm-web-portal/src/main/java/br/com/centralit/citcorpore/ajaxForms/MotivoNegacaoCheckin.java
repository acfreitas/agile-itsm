package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.MotivoNegacaoCheckinDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.MotivoNegacaoCheckinService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class MotivoNegacaoCheckin extends AjaxFormAction {

    @Override
    public Class<MotivoNegacaoCheckinDTO> getBeanClass() {
        return MotivoNegacaoCheckinDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MotivoNegacaoCheckinDTO motivoNegacaoCheckin = (MotivoNegacaoCheckinDTO) document.getBean();
        final MotivoNegacaoCheckinService motivoNegacaoCheckinService = this.getMotivoNegacaoCheckinService(WebUtil.getUsuarioSistema(request));

        motivoNegacaoCheckin.setDescricao(motivoNegacaoCheckin.getDescricao().trim());

        final boolean resultado = motivoNegacaoCheckinService.hasWithSameName(motivoNegacaoCheckin);
        if (resultado == true) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
            return;
        }

        if (motivoNegacaoCheckin.getIdMotivo() == null || motivoNegacaoCheckin.getIdMotivo().intValue() == 0) {
            motivoNegacaoCheckinService.create(motivoNegacaoCheckin);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            motivoNegacaoCheckinService.update(motivoNegacaoCheckin);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }
        final HTMLForm form = document.getForm("form");
        form.clear();

        document.executeScript("limpar_LOOKUP_MOTIVONEGACAOCHECKIN()");
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        MotivoNegacaoCheckinDTO motivoNegacaoCheckin = (MotivoNegacaoCheckinDTO) document.getBean();
        motivoNegacaoCheckin = (MotivoNegacaoCheckinDTO) this.getMotivoNegacaoCheckinService(WebUtil.getUsuarioSistema(request)).restore(motivoNegacaoCheckin);
        final HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(motivoNegacaoCheckin);
    }

    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MotivoNegacaoCheckinDTO motivoNegacaoCheckin = (MotivoNegacaoCheckinDTO) document.getBean();
        if (motivoNegacaoCheckin.getIdMotivo().intValue() > 0) {
            motivoNegacaoCheckin.setDataFim(UtilDatas.getDataAtual());
            this.getMotivoNegacaoCheckinService(WebUtil.getUsuarioSistema(request)).update(motivoNegacaoCheckin);
        }

        final HTMLForm form = document.getForm("form");
        form.clear();
        document.alert(UtilI18N.internacionaliza(request, "MSG07"));

        document.executeScript("limpar_LOOKUP_MOTIVONEGACAOCHECKIN()");
    }

    private MotivoNegacaoCheckinService motivoNegacaoCheckinService;

    public MotivoNegacaoCheckinService getMotivoNegacaoCheckinService(final Usuario usuario) throws ServiceException {
        if (motivoNegacaoCheckinService == null) {
            motivoNegacaoCheckinService = (MotivoNegacaoCheckinService) ServiceLocator.getInstance().getService(MotivoNegacaoCheckinService.class, usuario);
        }
        return motivoNegacaoCheckinService;
    }

}
