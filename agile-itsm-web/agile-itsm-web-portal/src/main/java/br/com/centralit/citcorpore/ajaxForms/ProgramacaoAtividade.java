package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.negocio.AtividadesOSService;
import br.com.centralit.citcorpore.negocio.ProgramacaoAtividadeService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class ProgramacaoAtividade extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return ProgramacaoAtividadeDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

    public void atualizaGrid(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ProgramacaoAtividadeDTO programacaoAtividadeDto = (ProgramacaoAtividadeDTO) document.getBean();

        ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService) ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
        programacaoAtividadeService.validaProgramacao(programacaoAtividadeDto);

        HTMLTable tblAgendamentos = document.getTableById("tblAgendamentos");

        if(programacaoAtividadeDto.getIdAtividadesOs() != null){
        	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
        	AtividadesOSDTO atividadesOSDTO = new AtividadesOSDTO();
        	atividadesOSDTO.setIdAtividadesOS(programacaoAtividadeDto.getIdAtividadesOs());
        	atividadesOSDTO = (AtividadesOSDTO) atividadesOSService.restore(atividadesOSDTO);

        	programacaoAtividadeDto.setNomeAtividadeOs(atividadesOSDTO.getDescricaoAtividade());
        }

        if (programacaoAtividadeDto.getSequencia() == null){
            tblAgendamentos.addRow(programacaoAtividadeDto,
                                    new String[] {"", "", "tipoAgendamentoDescr","nomeAtividadeOs" ,"dataInicio","detalhamento","duracaoEstimadaDescr","repeticaoDescr"},
                                    null,
                                    "",
                                    new String[] {"exibeIconesAgendamento"},
                                    null,
                                    null);
        }else{
            tblAgendamentos.updateRow(programacaoAtividadeDto,
                                    new String[] {"", "", "tipoAgendamentoDescr","nomeAtividadeOs" ,"dataInicio","detalhamento","duracaoEstimadaDescr","repeticaoDescr"},
                                    null,
                                    "",
                                    new String[] {"exibeIconesAgendamento"},
                                    null,
                                    null,
                                    programacaoAtividadeDto.getSequencia());
        }
        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblAgendamentos', 'tdPontilhada');");
        document.alert(UtilI18N.internacionaliza(request, "periodica.gravado_sucesso"));
        document.executeScript("fecharAgendamento();");
    }
}
