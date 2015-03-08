package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.centralit.citsmart.rest.service.RestDomainService;
import br.com.centralit.citsmart.rest.service.RestOperationService;
import br.com.centralit.citsmart.rest.service.RestParameterService;
import br.com.centralit.citsmart.rest.service.RestPermissionService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author CentralIT
 *
 */
public class OperacaoRest extends AjaxFormAction {

    @Override
    public Class<RestOperationDTO> getBeanClass() {
        return RestOperationDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        CITCorporeUtil.limparFormulario(document);
        document.focusInFirstActivateField(null);

        this.retoreGUIToDefault(document);

        final RestParameterService parametrosService = (RestParameterService) ServiceLocator.getInstance().getService(RestParameterService.class,
                WebUtil.getUsuarioSistema(request));
        request.setAttribute("colParametros", parametrosService.list());
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final RestOperationDTO restDTO = (RestOperationDTO) document.getBean();

            final Collection<RestPermissionDTO> colGrupos = br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RestPermissionDTO.class, "colGrupoSerialize", request);
            if (colGrupos != null) {
                for (final RestPermissionDTO restPermissionDTO : colGrupos) {
                    restPermissionDTO.setIdGroup(restPermissionDTO.getIdGrupo());
                }
            }

            final Collection<RestDomainDTO> colDominios = br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RestDomainDTO.class, "colParametros_Serialize", request);
            restDTO.setColDominios(colDominios);

            final RestOperationService restService = (RestOperationService) ServiceLocator.getInstance().getService(RestOperationService.class, null);

            restDTO.setColGrupos(colGrupos);

            if (restDTO.getIdRestOperation() != null) {
                restService.update(restDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            } else {
                restService.create(restDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG05"));
            }
            this.retoreGUIToDefault(document);
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide()");
        }
    }

    public void gravarParametro(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RestOperationDTO restDTO = (RestOperationDTO) document.getBean();

        RestParameterDTO restParamDTO = new RestParameterDTO();
        restParamDTO.setDescription(restDTO.getDescriptionParameter());
        restParamDTO.setIdentifier(restDTO.getIdentifier());
        if (restParamDTO.getIdentifier().indexOf(" ") >= 0) {
            document.alert(UtilI18N.internacionaliza(request, "parametros.formatoIdentificador"));
            return;
        }
        restParamDTO.setIdentifier(restParamDTO.getIdentifier().toUpperCase());

        final RestParameterService restService = (RestParameterService) ServiceLocator.getInstance().getService(RestParameterService.class, null);

        if (restParamDTO.getIdRestParameter() != null) {
            restService.update(restParamDTO);
        } else {
            restParamDTO = (RestParameterDTO) restService.create(restParamDTO);
            document.executeScript("adicionaParam(" + restParamDTO.getIdRestParameter() + ",\"" + restParamDTO.getDescription() + "\")");
        }
        final HTMLForm form = document.getForm("formCadastroParam");
        form.clear();
        document.executeScript("$(\"#POPUP_PARAMETRO\").dialog(\"close\")");
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *             Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
     */
    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RestOperationDTO restDTO = (RestOperationDTO) document.getBean();

        final RestOperationService restService = (RestOperationService) ServiceLocator.getInstance().getService(RestOperationService.class, null);

        if (restDTO.getIdRestOperation().intValue() > 0) {
            restService.delete(restDTO);
        }

        final HTMLForm form = document.getForm("form");
        form.clear();
        document.alert(UtilI18N.internacionaliza(request, "MSG07"));

        document.executeScript("limpar()");
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            RestOperationDTO restDTO = (RestOperationDTO) document.getBean();
            final RestOperationService restService = (RestOperationService) ServiceLocator.getInstance().getService(RestOperationService.class, null);
            final RestPermissionService restPermissionService = (RestPermissionService) ServiceLocator.getInstance().getService(RestPermissionService.class, null);
            final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
            Integer id = null;

            restDTO = (RestOperationDTO) restService.restore(restDTO);
            id = restDTO.getIdRestOperation();

            document.executeScript("deleteAllRows()");
            final Collection<RestPermissionDTO> colPermissoes = restPermissionService.findByIdOperation(id);
            if (colPermissoes != null) {
                for (final RestPermissionDTO restPermDto : colPermissoes) {
                    restPermDto.setIdGrupo(restPermDto.getIdGroup());
                    GrupoDTO grupoDto = new GrupoDTO();
                    grupoDto.setIdGrupo(restPermDto.getIdGroup());
                    grupoDto = (GrupoDTO) grupoService.restore(grupoDto);
                    if (grupoDto != null) {
                        restPermDto.setSigla(grupoDto.getSigla());
                    }
                }
                final HTMLTable table = document.getTableById("tabelaGrupo");
                table.deleteAllRows();
                table.addRowsByCollection(colPermissoes, new String[] {"", "idGrupo", "sigla"}, null, null, new String[] {"gerarButtonDeleteGrupo"}, null, null);
            }

            final HTMLForm form = document.getForm("form");
            form.clear();
            form.setValues(restDTO);

            final RestDomainService restDomainService = (RestDomainService) ServiceLocator.getInstance().getService(RestDomainService.class, null);
            final Collection<RestDomainDTO> colParametros = restDomainService.findByIdRestOperation(restDTO.getIdRestOperation());
            document.executeScript("GRID_PARAMETROS.deleteAllRows();");
            if (colParametros != null) {
                int i = 0;
                for (final RestDomainDTO dominioDto : colParametros) {
                    i++;
                    document.executeScript("GRID_PARAMETROS.addRow()");
                    dominioDto.setSequencia(i);
                    document.executeScript("seqParametro = NumberUtil.zerosAEsquerda(" + i + ",5)");
                    document.executeScript("exibeParametro('" + br.com.citframework.util.WebUtil.serializeObject(dominioDto, WebUtil.getLanguage(request)) + "')");
                }
            }
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide()");
        }
    }

    private void retoreGUIToDefault(final DocumentHTML document) throws Exception {
        final HTMLForm form = document.getForm("form");
        form.clear();
        final HTMLTable tabelaGrupo = document.getTableById("tabelaGrupo");
        tabelaGrupo.deleteAllRows();
        document.executeScript("GRID_PARAMETROS.deleteAllRows();");
    }

}
