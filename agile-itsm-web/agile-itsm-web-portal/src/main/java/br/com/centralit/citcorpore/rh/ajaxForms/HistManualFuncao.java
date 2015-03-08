package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.HistPerspectivaComportamentalDTO;
import br.com.centralit.citcorpore.rh.negocio.HistManualFuncaoService;
import br.com.citframework.service.ServiceLocator;

public class HistManualFuncao extends ManualFuncao {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("idHistManualFuncao") != null) {
			HistManualFuncaoService manualFuncaoService = (HistManualFuncaoService) ServiceLocator.getInstance().getService(HistManualFuncaoService.class, null);
			
			super.load(document, request, response);
			
			HistManualFuncaoDTO histManualFuncaoDTO = new HistManualFuncaoDTO();
			histManualFuncaoDTO.setIdhistManualFuncao(Integer.parseInt(request.getParameter("idHistManualFuncao")));
			histManualFuncaoDTO =  (HistManualFuncaoDTO) manualFuncaoService.restore(histManualFuncaoDTO);
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(histManualFuncaoDTO);
			form.lockForm();
			document.executeScript("$('#resumoFuncao').attr('disabled',true)");
			
			HTMLTable tblResponsabilidades = document.getTableById("tblResponsabilidades");
	  		tblResponsabilidades.deleteAllRows();
	  		if (histManualFuncaoDTO.getColAtribuicaoResponsabilidadeDTO()!=null){
	  			tblResponsabilidades.addRowsByCollection(histManualFuncaoDTO.getColAtribuicaoResponsabilidadeDTO(), new String[] {"descricaoPerspectivaComplexidade", "idNivel"}, null,  "" , null, 	null, null);		
	  		}
	  		
	  		HTMLTable tblCertificacaoRA = document.getTableById("tblCertificacoesRA");
	  		tblCertificacaoRA.deleteAllRows();
	  		Collection<CertificacaoDTO> colCertificadosRA = histManualFuncaoDTO.getColCertificacaoDTORA(); 	
	  		if (histManualFuncaoDTO.getColCertificacaoDTORA()!=null){
	  			tblCertificacaoRA.addRowsByCollection(histManualFuncaoDTO.getColCertificacaoDTORA(), new String[] {"descricao"}, null, "", null, null, null);		
	  		}
	  		HTMLTable tblCertificacaoRF = document.getTableById("tblCertificacoesRF");
	  		tblCertificacaoRF.deleteAllRows();
	  		Collection<CertificacaoDTO> colCertificadosRF = histManualFuncaoDTO.getColCertificacaoDTORF(); 	
	  		if (histManualFuncaoDTO.getColCertificacaoDTORF()!=null){
	  			tblCertificacaoRF.addRowsByCollection(histManualFuncaoDTO.getColCertificacaoDTORF(), new String[] {"descricao"}, null, "", null, null, null);		
	  		}
	  		
	  		HTMLTable tblCursoRA = document.getTableById("tblCursosRA");
	  		tblCursoRA.deleteAllRows();
	  		Collection<CursoDTO> colCursosRA = histManualFuncaoDTO.getColCursoDTORA(); 	
	  		if (histManualFuncaoDTO.getColCursoDTORA()!=null){
	  			tblCursoRA.addRowsByCollection(histManualFuncaoDTO.getColCursoDTORA(), new String[] {"descricao"}, null, "", null, null, null);		
	  		}
	  		
	  		HTMLTable tblCursoRF = document.getTableById("tblCursoRF");
	  		tblCursoRF.deleteAllRows();
	  		Collection<CursoDTO> colCursosRF = histManualFuncaoDTO.getColCursoDTORF(); 	
	  		if (histManualFuncaoDTO.getColCursoDTORF()!=null){
	  			tblCursoRF.addRowsByCollection(histManualFuncaoDTO.getColCursoDTORF(), new String[] {"descricao"},  null, "", null, null, null);		
	  		}
	  		
	  		HTMLTable tblCompetencias = document.getTableById("tblCompetencias");
			tblCompetencias.deleteAllRows();
			if(histManualFuncaoDTO.getColCompetenciaTecnicaDTO() != null) {
				tblCompetencias.addRowsByCollection(histManualFuncaoDTO.getColCompetenciaTecnicaDTO(), new String[] {"descricao","idNivelCompetenciaAcesso","idNivelCompetenciaFuncao"}, 
						null, "", null, null, null);
			}
			
			HTMLTable tblPerspComp = document.getTableById("tblPerspectivaComportamental");
			tblPerspComp.deleteAllRows();
			Collection<HistPerspectivaComportamentalDTO> colPerspComp = histManualFuncaoDTO.getColPerspectivaComportamentalDTO(); 
			if (histManualFuncaoDTO.getColCompetenciaTecnicaDTO()!=null){
				tblPerspComp.addRowsByCollection(histManualFuncaoDTO.getColPerspectivaComportamentalDTO(), new String[] {"cmbCompetenciaComportamental", "comportamento"}, null, "", null, null, null);		
			}
			
		} else {
			
		}

	}

	@Override
	public Class getBeanClass() {
		return HistManualFuncaoDTO.class;
	}

}
