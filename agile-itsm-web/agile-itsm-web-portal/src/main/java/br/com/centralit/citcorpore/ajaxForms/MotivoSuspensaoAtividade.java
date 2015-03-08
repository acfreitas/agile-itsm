package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.MotivoSuspensaoAtividadeService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class MotivoSuspensaoAtividade extends AjaxFormAction {

    @Override
    public Class<MotivoSuspensaoAtividadeDTO> getBeanClass() {
        return MotivoSuspensaoAtividadeDTO.class;
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
        final MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) document.getBean();
        final MotivoSuspensaoAtividadeService motivoSuspensaoAtividadeService = this.getMotivoSuspensaoAtividadeService(WebUtil.getUsuarioSistema(request));

        if (motivoSuspensaoAtividadeDTO.getIdMotivo() == null || motivoSuspensaoAtividadeDTO.getIdMotivo().intValue() == 0) {
            final boolean resultado = motivoSuspensaoAtividadeService.jaExisteRegistroComMesmoNome(motivoSuspensaoAtividadeDTO);
            if (resultado == true) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                return;
            }
            motivoSuspensaoAtividadeService.create(motivoSuspensaoAtividadeDTO);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            motivoSuspensaoAtividadeService.update(motivoSuspensaoAtividadeDTO);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }
        final HTMLForm form = document.getForm("form");
        form.clear();

        document.executeScript("limpar_LOOKUP_MOTIVOSUSPENSAOATIVIDADE()");
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) document.getBean();

        motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) this.getMotivoSuspensaoAtividadeService(WebUtil.getUsuarioSistema(request))
                .restore(motivoSuspensaoAtividadeDTO);

        final HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(motivoSuspensaoAtividadeDTO);
    }

    public void atualizaData(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) document.getBean();
        if (motivoSuspensaoAtividadeDTO.getIdMotivo().intValue() > 0) {
            motivoSuspensaoAtividadeDTO.setDataFim(UtilDatas.getDataAtual());
            this.getMotivoSuspensaoAtividadeService(WebUtil.getUsuarioSistema(request)).update(motivoSuspensaoAtividadeDTO);
        }

        final HTMLForm form = document.getForm("form");
        form.clear();
        document.alert(UtilI18N.internacionaliza(request, "MSG07"));

        document.executeScript("limpar_LOOKUP_MOTIVOSUSPENSAOATIVIDADE()");
    }

    private MotivoSuspensaoAtividadeService motivoSuspensaoAtividadeService;

    public MotivoSuspensaoAtividadeService getMotivoSuspensaoAtividadeService(final Usuario usuario) throws ServiceException {
        if (motivoSuspensaoAtividadeService == null) {
            motivoSuspensaoAtividadeService = (MotivoSuspensaoAtividadeService) ServiceLocator.getInstance().getService(MotivoSuspensaoAtividadeService.class, usuario);
        }
        return motivoSuspensaoAtividadeService;
    }

}
