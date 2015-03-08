/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.ExcecaoCalendarioService;
import br.com.centralit.citcorpore.negocio.JornadaTrabalhoService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Calendario extends AjaxFormAction {

    private JornadaTrabalhoService jornadaTrabalhoService;

    private JornadaTrabalhoService getJornadaTrabalhoService() throws Exception {
        if (jornadaTrabalhoService == null) {
            jornadaTrabalhoService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, null);
        }
        return jornadaTrabalhoService;
    }

    private CalendarioService calendarioService;

    private CalendarioService getCalendarioService() throws Exception {
        if (calendarioService == null) {
            calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
        }
        return calendarioService;
    }

    private ExcecaoCalendarioService excecaoCalendarioService;

    private ExcecaoCalendarioService getExcecaoCalendarioService() throws Exception {
        if (excecaoCalendarioService == null) {
            excecaoCalendarioService = (ExcecaoCalendarioService) ServiceLocator.getInstance().getService(ExcecaoCalendarioService.class, null);
        }
        return excecaoCalendarioService;
    }

    @Override
    public Class<CalendarioDTO> getBeanClass() {
        return CalendarioDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        preencheCombos(document, request);
        String permiteDataInferiorHoje = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PermiteDataInferiorHoje, "N");

        HTMLForm form = document.getForm("form");
        CalendarioDTO calendario = (CalendarioDTO) document.getBean();
        calendario.setPermiteDataInferiorHoje(permiteDataInferiorHoje);
        form.setValues(calendario);

    }

    private void preencheCombos(DocumentHTML document, HttpServletRequest request) throws LogicException, ServiceException, Exception {
        Collection<JornadaTrabalhoDTO> jornadas = getJornadaTrabalhoService().listarJornadasAtivas();
        geraCombo(document, "idJornadaSeg", jornadas, request);
        geraCombo(document, "idJornadaTer", jornadas, request);
        geraCombo(document, "idJornadaQua", jornadas, request);
        geraCombo(document, "idJornadaQui", jornadas, request);
        geraCombo(document, "idJornadaSex", jornadas, request);
        geraCombo(document, "idJornadaSab", jornadas, request);
        geraCombo(document, "idJornadaDom", jornadas, request);
        geraCombo(document, "idjornadaexcecao", jornadas, request);
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CalendarioDTO calendario = (CalendarioDTO) document.getBean();
        calendario = (CalendarioDTO) getCalendarioService().restore(calendario);
        HTMLForm form = document.getForm("form");
        Collection<ExcecaoCalendarioDTO> excecoes = (Collection<ExcecaoCalendarioDTO>) getExcecaoCalendarioService().findByIdCalendario(calendario.getIdCalendario());

        form.clear();
        preencheCombos(document, request);

        String permiteDataInferiorHoje = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PermiteDataInferiorHoje, "N");
        calendario.setPermiteDataInferiorHoje(permiteDataInferiorHoje);
        form.setValues(calendario);

        if (excecoes != null) {
            String serializado = WebUtil.serializeObjects(excecoes, WebUtil.getLanguage(request));
            document.executeScript("restaurarTabelaExcecoes('" + serializado + "')");
        } else {
            document.executeScript("clearTabExecoes()");
        }
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CalendarioDTO calendario = (CalendarioDTO) document.getBean();
        List<ExcecaoCalendarioDTO> listaItens = (List) WebUtil.deserializeCollectionFromRequest(ExcecaoCalendarioDTO.class, "listaExecoesSerializada", request);

        if (calendario.getConsideraFeriados() == null || calendario.getDescricao() == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
            return;
        }

        if (calendario.getIdCalendario() == null) {
            if (this.getCalendarioService().verificaSeExisteCalendario(calendario)) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                return;
            }
            getCalendarioService().create(calendario);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            if (this.getCalendarioService().verificaSeExisteCalendario(calendario)) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                return;
            }
            getCalendarioService().update(calendario);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        getExcecaoCalendarioService().deleteByIdCalendario(calendario.getIdCalendario());
        if (listaItens != null) {
            for (ExcecaoCalendarioDTO e : listaItens) {
                e.setIdCalendario(calendario.getIdCalendario());
                getExcecaoCalendarioService().create(e);
            }
        }

        HTMLForm form = document.getForm("form");
        form.clear();
        document.executeScript("tabExcecoes.limpaLista();");
        document.executeScript("setConsideraFeriados();");
    }

    /**
     * @author euler.ramos
     * @param idCalendario
     * @return
     * @throws Exception
     */
    public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CalendarioDTO calendario = (CalendarioDTO) document.getBean();
        if ((calendario.getIdCalendario() != null) && (calendario.getIdCalendario().intValue() > 0)) {
            StringBuilder resposta = new StringBuilder();
            resposta.append(this.getCalendarioService().verificaSePermiteExcluir(document, request, calendario));
            if (resposta.toString().equalsIgnoreCase("excluir")) {
                this.getExcecaoCalendarioService().deleteByIdCalendario(calendario.getIdCalendario());
                this.getCalendarioService().delete(calendario);
                document.alert(UtilI18N.internacionaliza(request, "MSG07"));
                HTMLForm form = document.getForm("form");
                form.clear();
                document.executeScript("tabExcecoes.limpaLista();");
            } else {
                document.alert(resposta.toString());
            }
        }
    }

    private void geraCombo(DocumentHTML document, String idComponente, Collection<JornadaTrabalhoDTO> lista, HttpServletRequest request) throws Exception {
        HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById(idComponente);
        comboTipoDemanda.removeAllOptions();

        comboTipoDemanda.addOption(String.valueOf(0), UtilI18N.internacionaliza(request, "calendario.naoHaJornadaTrabalho"));
        if (lista != null) {
            for (JornadaTrabalhoDTO j : lista) {
                comboTipoDemanda.addOption(j.getIdJornada().toString(), StringEscapeUtils.escapeJavaScript(j.getDescricao()));
            }
        }
    }

}
