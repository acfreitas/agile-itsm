package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AssinaturaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoAssinaturaDTO;
import br.com.centralit.citcorpore.bean.ItemGrupoAssinaturaDTO;
import br.com.centralit.citcorpore.negocio.AssinaturaService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoAssinaturaService;
import br.com.centralit.citcorpore.negocio.ItemGrupoAssinaturaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author euler.ramos
 *
 */
public class GrupoAssinatura extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.alimentaComboAssinatura(document, request, response);
    }

    @Override
    public Class getBeanClass() {
        return GrupoAssinaturaDTO.class;
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Collection<ItemGrupoAssinaturaDTO> tblAssinaturas_serialize = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemGrupoAssinaturaDTO.class, "tblAssinaturas_serialize", request);
        if ((tblAssinaturas_serialize != null) && (tblAssinaturas_serialize.size() > 0)) {
            GrupoAssinaturaDTO grupoAssinaturaDTO = (GrupoAssinaturaDTO) document.getBean();
            GrupoAssinaturaService grupoAssinaturaService = (GrupoAssinaturaService) ServiceLocator.getInstance().getService(GrupoAssinaturaService.class, null);
            if (!grupoAssinaturaService.violaIndiceUnico(grupoAssinaturaDTO)) {
                if (grupoAssinaturaDTO.getIdGrupoAssinatura() != null) {
                    grupoAssinaturaService.update(grupoAssinaturaDTO, (ArrayList<ItemGrupoAssinaturaDTO>) tblAssinaturas_serialize);
                    document.alert(UtilI18N.internacionaliza(request, "grupoAssinatura.grupoAssinaturaAtualizado"));
                } else {
                    grupoAssinaturaDTO.setDataInicio(UtilDatas.getDataAtual());
                    grupoAssinaturaService.create(grupoAssinaturaDTO, (ArrayList<ItemGrupoAssinaturaDTO>) tblAssinaturas_serialize);
                    document.alert(UtilI18N.internacionaliza(request, "grupoAssinatura.grupoAssinaturaCadastrado"));
                }
                document.executeScript("limpar();");
            } else {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaAdicionado"));
            }
        } else {
            document.alert(UtilI18N.internacionaliza(request, "grupoAssinatura.alerta.lanceAoMenosUmaAssinatura"));
        }
    }

    @SuppressWarnings("unchecked")
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GrupoAssinaturaDTO grupoAssinaturaDTO = (GrupoAssinaturaDTO) document.getBean();
        GrupoAssinaturaService grupoAssinaturaService = (GrupoAssinaturaService) ServiceLocator.getInstance().getService(GrupoAssinaturaService.class, null);

        grupoAssinaturaDTO = (GrupoAssinaturaDTO) grupoAssinaturaService.restore(grupoAssinaturaDTO);

        document.executeScript("limpar();");

        HTMLForm form = document.getForm("form");
        form.setValues(grupoAssinaturaDTO);

        this.alimentaTblAssinaturas(document, request, response, grupoAssinaturaDTO);
    }

    public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GrupoAssinaturaDTO grupoAssinaturaDTO = (GrupoAssinaturaDTO) document.getBean();
        GrupoAssinaturaService grupoAssinaturaService = (GrupoAssinaturaService) ServiceLocator.getInstance().getService(GrupoAssinaturaService.class, null);

        if (grupoAssinaturaService.naoEstaSendoUtilizado(grupoAssinaturaDTO.getIdGrupoAssinatura())) {
            if (grupoAssinaturaDTO.getIdGrupoAssinatura() != null) {
                grupoAssinaturaDTO.setDataFim(UtilDatas.getDataAtual());
                grupoAssinaturaService.update(grupoAssinaturaDTO, new ArrayList<ItemGrupoAssinaturaDTO>());
                document.alert(UtilI18N.internacionaliza(request, "grupoAssinatura.grupoAssinaturaExcluida"));
            }
            document.executeScript("limpar();");
        } else {
            document.alert(UtilI18N.internacionaliza(request, "grupoAssinatura.alerta.exclusaoNaoPermitida"));
        }
    }

    @SuppressWarnings("unchecked")
    private void alimentaComboAssinatura(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HTMLSelect comboAssinatura = document.getSelectById("idAssinatura");
        comboAssinatura.removeAllOptions();
        comboAssinatura.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        AssinaturaService assinaturaService = (AssinaturaService) ServiceLocator.getInstance().getService(AssinaturaService.class, null);

        Collection<AssinaturaDTO> colAssinatura = assinaturaService.list();

        StringBuilder nomeAssinatura;

        if (colAssinatura != null) {
            for (AssinaturaDTO assinaturaDTO : colAssinatura) {
                nomeAssinatura = new StringBuilder();
                if (assinaturaDTO.getIdEmpregado() != null) {
                    EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
                    EmpregadoDTO empregadoDTO = new EmpregadoDTO();
                    empregadoDTO.setIdEmpregado(assinaturaDTO.getIdEmpregado());
                    empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
                    assinaturaDTO.setNomeResponsavel(empregadoDTO.getNome());
                } else {
                    assinaturaDTO.setNomeResponsavel("---------- ");
                }

                if (assinaturaDTO.getPapel() == null) {
                    assinaturaDTO.setPapel("---------- ");
                }

                if (assinaturaDTO.getFase() == null) {
                    assinaturaDTO.setFase("---------- ");
                }

                nomeAssinatura.append(assinaturaDTO.getNomeResponsavel());
                nomeAssinatura.append(" / ");
                nomeAssinatura.append(assinaturaDTO.getPapel());
                nomeAssinatura.append(" / ");
                nomeAssinatura.append(assinaturaDTO.getFase());

                comboAssinatura.addOption(assinaturaDTO.getIdAssinatura().toString(), nomeAssinatura.toString());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void alimentaTblAssinaturas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, GrupoAssinaturaDTO grupoAssinaturaDTO) throws Exception {
        HTMLTable tblAssinaturas = document.getTableById("tblAssinaturas");
        tblAssinaturas.deleteAllRows();

        ItemGrupoAssinaturaService itemGrupoAssinaturaService = (ItemGrupoAssinaturaService) ServiceLocator.getInstance().getService(ItemGrupoAssinaturaService.class, null);
        Collection<ItemGrupoAssinaturaDTO> colItemGrupoAssinaturaDTOs = itemGrupoAssinaturaService.findByIdGrupoAssinatura(grupoAssinaturaDTO.getIdGrupoAssinatura());

        if ((tblAssinaturas != null) && ((colItemGrupoAssinaturaDTOs != null) && (colItemGrupoAssinaturaDTOs.size() > 0))) {
            tblAssinaturas.addRowsByCollection(colItemGrupoAssinaturaDTOs, new String[] { "", "nomeResponsavel", "papel", "fase", "ordem" }, new String[] { "idAssinatura", "ordem" }, UtilI18N.internacionaliza(request, "grupoAssinatura.alerta.assinaturaJaAdicionada"), new String[] { "exibeIconesItemAssinatura" }, null, null);
        }

        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblAssinaturas', 'tblAssinaturas');");
    }

}