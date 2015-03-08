package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.AtividadesOSService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.ProgramacaoAtividadeService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.util.Enumerados.TipoAgendamento;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilI18N;

public class AtividadePeriodica extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return AtividadePeriodicaDTO.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idContrato");
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContratos = contratoService.list();
		idContrato.removeAllOptions();
		idContrato.addOption("", "--");
		idContrato.addOptions(colContratos, "idContrato", "numero", null);
		
		HTMLSelect idGrupoAtvPeriodica = (HTMLSelect) document.getSelectById("idGrupoAtvPeriodica");
		GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
		Collection colGrupos = grupoAtvPeriodicaService.listGrupoAtividadePeriodicaAtiva();
		idGrupoAtvPeriodica.removeAllOptions();
		idGrupoAtvPeriodica.addOption("", "--");
		idGrupoAtvPeriodica.addOptions(colGrupos, "idGrupoAtvPeriodica", "nomeGrupoAtvPeriodica", null);		
	}
	@SuppressWarnings("rawtypes")
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO) document.getBean();
		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProgramacaoAtividadeDTO.class, "colItens_Serialize", request);
		
		Collection colItensOs = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(OSDTO.class, "colItensOS_Serialize", request);
		
		boolean existeDuplicacao = atividadePeriodicaService.existeDuplicacao(atividadePeriodicaDTO.getTituloAtividade(), atividadePeriodicaDTO.getIdAtividadePeriodica());
		
		if ( existeDuplicacao ) {
			document.alert(UtilI18N.internacionaliza(request, "MSE01"));
		} else {
			atividadePeriodicaDTO.setColItensOS(colItensOs);
			
			atividadePeriodicaDTO.setColItens(colItens);
			if (atividadePeriodicaDTO.getIdAtividadePeriodica()==null || atividadePeriodicaDTO.getIdAtividadePeriodica().intValue()==0){
				atividadePeriodicaService.create(atividadePeriodicaDTO);
			}else{
				atividadePeriodicaService.update(atividadePeriodicaDTO);
			}
			document.alert(UtilI18N.internacionaliza(request, "periodica.registro_gravado_sucesso"));
	        HTMLForm form = document.getForm("form");
			form.clear();
	        HTMLTable tblAgendamentos = document.getTableById("tblAgendamentos");
	        HTMLTable tblItensOS = document.getTableById("tblOS");
	        tblItensOS.deleteAllRows();
	        tblAgendamentos.deleteAllRows();
		}
	}
	@SuppressWarnings("rawtypes")
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO) document.getBean();
		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService) ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		
		atividadePeriodicaDTO = (AtividadePeriodicaDTO)atividadePeriodicaService.restore(atividadePeriodicaDTO);
		
		
		if (atividadePeriodicaDTO != null && atividadePeriodicaDTO.getIdRequisicaoMudanca() != null){
			RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();
			requisicaoMudancaDTO.setIdRequisicaoMudanca(atividadePeriodicaDTO.getIdRequisicaoMudanca());
			requisicaoMudancaDTO = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);
			if (requisicaoMudancaDTO != null){
				atividadePeriodicaDTO.setIdentMudanca(atividadePeriodicaDTO.getIdRequisicaoMudanca() + " - " + requisicaoMudancaDTO.getTitulo());
			}
		}
		HTMLForm form = document.getForm("form");
		form.clear();	
		if(atividadePeriodicaDTO != null){
			form.setValues(atividadePeriodicaDTO);
		}
		
		document.getElementById("tdBotaoGravar").setVisible(true);
		document.getElementById("tdBotaoNovoAgendamento").setVisible(true);
		document.getElementById("tdBotaoNovaOs").setVisible(true);
		
        HTMLTable tblAgendamentos = document.getTableById("tblAgendamentos");
        tblAgendamentos.deleteAllRows();
		
		if (atividadePeriodicaDTO != null){
			Collection col = programacaoAtividadeService.findByIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
			if (col != null){
			    tblAgendamentos.addRowsByCollection(col, 
                                                    new String[] {"", "", "tipoAgendamentoDescr","nomeAtividadeOs" ,"dataInicio","detalhamento","duracaoEstimadaDescr","repeticaoDescr"}, 
                                                    new String[] {"idProgramacaoAtividade"}, 
                                                    "", 
                                                    new String[] {"exibeIconesAgendamento"}, 
                                                    null, 
                                                    null);  
	             document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblAgendamentos', 'tdPontilhada');");
			}
			if (atividadePeriodicaDTO.getIdSolicitacaoServico() != null){
			    document.alert("Atenção: esta atividade foi gerada pelo módulo de solicitações/incidentes. Não é permitido alteração no agendamento!");
			    document.getElementById("tdBotaoGravar").setVisible(false);
			    document.getElementById("tdBotaoNovoAgendamento").setVisible(false);
			}
		}
		
		 HTMLTable tblOS = document.getTableById("tblOS");
		 tblOS.deleteAllRows();
			
			if (atividadePeriodicaDTO != null){
				 Collection col = osService.listOSByIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
				if (col != null){
					tblOS.addRowsByCollection(col, 
				    		 new String[] {"","", "numero","nomeAreaRequisitante","demanda"}, 
                             new String[] {"idOS"}, 
                             "O.S já cadastrada!", 
                             new String[] {"exibeIconesOS"}, 
                             null, 
                             null);  
				    document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblOS', 'tblOS');");
				}
			}
			form.setValues(atividadePeriodicaDTO);	
			document.executeScript("setaValorContrato(document.getElementById('idContrato'));");
		
	}
    public void atualizaAgendamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
        AtividadePeriodicaDTO atividadePeriodicaDto = (AtividadePeriodicaDTO) document.getBean(); 
        ProgramacaoAtividadeDTO programacaoAtividadeDto = new ProgramacaoAtividadeDTO();
        Reflexao.copyPropertyValues(atividadePeriodicaDto, programacaoAtividadeDto);
        
        programacaoAtividadeDto.setTipoAgendamentoDescr(TipoAgendamento.valueOf(programacaoAtividadeDto.getTipoAgendamento()).getDescricao());
        HTMLTable tblAgendamentos = document.getTableById("tblAgendamentos");
        tblAgendamentos.deleteAllRows();
        
        if (programacaoAtividadeDto.getSequencia() == null){
            tblAgendamentos.addRow(programacaoAtividadeDto, 
                                    new String[] {"", "", "tipoAgendamentoDescr","dataInicio","horaInicio","duracaoEstimada"}, 
                                    new String[] {"idProgramacaoAtividade"}, 
                                    "", 
                                    new String[] {"exibeIconesAgendamento"}, 
                                    null, 
                                    null);  
        }else{
            tblAgendamentos.updateRow(programacaoAtividadeDto, 
                                    new String[] {"", "", "tipoAgendamentoDescr","dataInicio","horaInicioFmt","duracaoEstimada"}, 
                                    new String[] {"idProgramacaoAtividade"}, 
                                    "", 
                                    new String[] {"exibeIconesAgendamento"}, 
                                    null, 
                                    null,
                                    programacaoAtividadeDto.getSequencia());  
        }
        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblAgendamentos', 'tdPontilhada');");
    }
    public void carregaComboAtividadeOs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	List<OSDTO> colItensOs = (List<OSDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(OSDTO.class, "colItensOS_Serialize", request);
    	
    	List<AtividadesOSDTO> listAtvidadeFinal = new  ArrayList<AtividadesOSDTO>();
    	
    	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
    	
    	if(colItensOs != null){
	    	for (OSDTO osdto : colItensOs) {
	    		List<AtividadesOSDTO> listAtvidade = (List<AtividadesOSDTO>) atividadesOSService.findByIdOS(osdto.getIdOS());	
	    		if(listAtvidade != null)
		    		for (AtividadesOSDTO atividadesOSDTO : listAtvidade) {
		    			atividadesOSDTO.setDescricaoAtividade(osdto.getNumero()+" - " + atividadesOSDTO.getDescricaoAtividade());
		    			listAtvidadeFinal.add(atividadesOSDTO);
					}
	    	
			}
	    	
	    	document.executeScript("abilitaDivAtividade()");
	    	
    	}else{
    		
    		document.executeScript("desabilitaDivAtividade()");
    	}
	    	HTMLSelect comboAtividade = (HTMLSelect) document.getSelectById("idAtividadesOs");
	    	comboAtividade.removeAllOptions();
	    	comboAtividade.addOption("", "--- Selecione ---");
	    	comboAtividade.addOptions(listAtvidadeFinal, "idAtividadesOs", "descricaoAtividade", null);    
	    	
    }
}
