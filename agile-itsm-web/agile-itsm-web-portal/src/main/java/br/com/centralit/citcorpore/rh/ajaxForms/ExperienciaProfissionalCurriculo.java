package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.FuncaoExperienciaProfissionalCurriculoDao;
import br.com.centralit.citcorpore.rh.negocio.ExperienciaProfissionalCurriculoService;
import br.com.citframework.service.ServiceLocator;

public class ExperienciaProfissionalCurriculo extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	public void restauraTabelaFuncao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ExperienciaProfissionalCurriculoDTO experienciaProfissionalDto = (ExperienciaProfissionalCurriculoDTO)document.getBean();
		ExperienciaProfissionalCurriculoService experienciaProfissionalCurriculoService = (ExperienciaProfissionalCurriculoService) ServiceLocator.getInstance().getService(ExperienciaProfissionalCurriculoService.class, null);
		
		Collection colFuncao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FuncaoExperienciaProfissionalCurriculoDTO.class, "colFuncaoExperiencia_Serialize", request);
		
		if(experienciaProfissionalDto.getIdExperienciaProfissional()!= null){
			experienciaProfissionalDto = (ExperienciaProfissionalCurriculoDTO) experienciaProfissionalCurriculoService.restore(experienciaProfissionalDto);
		}
		if(colFuncao!= null){
			experienciaProfissionalDto.setColFuncao(colFuncao);
		}
		
		HTMLTable tblExperiencias = document.getTableById("tblDescFuncaoPopUp");
		tblExperiencias.deleteAllRows();
		if (experienciaProfissionalDto.getColFuncao() != null) {
			tblExperiencias.addRowsByCollection(experienciaProfissionalDto.getColFuncao(), new String[] { "nomeFuncao", "descricaoFuncao", "" }, null,
					"Já existe registrado esta informação na tabela", new String[] { "" }, "marcaEndereco", null);
		}
		document.executeScript("$(\"#modal_funcaocandidato\").modal(\"show\");");
		
	}
	
	public void restauraExperiencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ExperienciaProfissionalCurriculoDTO experienciaProfissionalDto = (ExperienciaProfissionalCurriculoDTO)document.getBean();
		ExperienciaProfissionalCurriculoService experienciaProfissionalCurriculoService = (ExperienciaProfissionalCurriculoService) ServiceLocator.getInstance().getService(ExperienciaProfissionalCurriculoService.class, null);
		FuncaoExperienciaProfissionalCurriculoDao funcaoExperienciaProfissionalDao = new FuncaoExperienciaProfissionalCurriculoDao();
		Collection funcaoExperiencia = null;
		Collection colFuncao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FuncaoExperienciaProfissionalCurriculoDTO.class, "colFuncaoExperiencia_Serialize", request);
	
		if(experienciaProfissionalDto.getIdExperienciaProfissional()!= null && experienciaProfissionalDto.getIdExperienciaProfissional()!= 0){
			experienciaProfissionalDto = (ExperienciaProfissionalCurriculoDTO) experienciaProfissionalCurriculoService.restore(experienciaProfissionalDto);
			funcaoExperiencia = funcaoExperienciaProfissionalDao.findByIdExperienciaProfissional(experienciaProfissionalDto.getIdExperienciaProfissional());
			if(funcaoExperiencia!= null && !funcaoExperiencia.isEmpty()){
				experienciaProfissionalDto.setColFuncao(funcaoExperiencia);
			}
		}
			
			
		if(colFuncao!=null){
			experienciaProfissionalDto.setColFuncao(colFuncao);
		}
		
		String periodoMesInicio = "";
		String periodoAnoInicio = "";
		String periodoMesFim = "";
		String periodoAnoFim = "";
		String atualmente = "";
		
		String [] periodoStr = ((experienciaProfissionalDto.getPeriodo().replace("- ", "")).replaceAll(" ", ";")).split(";");
		if(periodoStr != null){
			if(periodoStr.length>=3){
				periodoMesInicio = periodoStr[0];
				periodoAnoInicio = periodoStr[1];
				if(periodoStr.length > 3){
					periodoMesFim = periodoStr[2];
					periodoAnoFim = periodoStr[3];
				} else{
					atualmente = "S";
				}
			}
		}
		
		document.getElementById("experiencia#empresa").setValue(experienciaProfissionalDto.getDescricaoEmpresa());
		document.getElementById("experiencia#localidade").setValue(experienciaProfissionalDto.getLocalidade());
		
		document.getElementById("experiencia#idMesInicio").setValue(periodoMesInicio);
		document.getElementById("experiencia#anoInicio").setValue(periodoAnoInicio);
		if(atualmente.equals("S")){
			document.executeScript("$('#escondedatafinal').hide();");
			document.executeScript("document.getElementById('diasAtuais').checked = true;");
		}else{
			document.getElementById("experiencia#idMesFim").setValue(periodoMesFim);
			document.getElementById("experiencia#anoFim").setValue(periodoAnoFim);	
			document.executeScript("$('#escondedatafinal').show();");
		}
		
		
		HTMLTable tblDesFuncao = document.getTableById("tblDescFuncao");
		tblDesFuncao.deleteAllRows();
		if (experienciaProfissionalDto.getColFuncao() != null) {
			tblDesFuncao.addRowsByCollection(experienciaProfissionalDto.getColFuncao(), new String[] { "nomeFuncao", "descricaoFuncao", "" }, null,
					"Já existe registrado esta informação na tabela", new String[] { "gerarImagemDelExperienciaFuncao" }, "funcaoClickDescFunc", null);
		}
		
		
	}
	
	
	@Override
	public Class getBeanClass() {
		return ExperienciaProfissionalCurriculoDTO.class;
	}

}
