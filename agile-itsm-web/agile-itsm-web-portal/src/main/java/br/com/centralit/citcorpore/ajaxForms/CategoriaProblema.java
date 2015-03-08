package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProblemaService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 *
 * @author geber.costa
 *
 */

@SuppressWarnings({"rawtypes","unchecked","unused"})
public class CategoriaProblema extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
		this.preencherComboTipoFluxo(document, request, response);
		this.preencherComboGrupoExecutor(document, request, response);
		this.preencherComboTemplate(document, request, response);

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaProblemaDTO categoriaProblemaDto = (CategoriaProblemaDTO)document.getBean();
		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);
		categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaService.restore(categoriaProblemaDto);

		if(categoriaProblemaDto != null){
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(categoriaProblemaDto);
		}

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaProblemaDTO categoriaProblemaDTO = (CategoriaProblemaDTO) document.getBean();
		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, WebUtil.getUsuarioSistema(request));



			if(categoriaProblemaService.consultarCategoriasAtivas(categoriaProblemaDTO)){
					document.alert(UtilI18N.internacionaliza(request, "MSE01"));
				return;
				}





		if(categoriaProblemaDTO.getIdCategoriaProblema()==null ||  categoriaProblemaDTO.getIdCategoriaProblema() == 0){
			categoriaProblemaDTO.setDataInicio(UtilDatas.getDataAtual());
			categoriaProblemaService.create(categoriaProblemaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}else{
			categoriaProblemaService.update(categoriaProblemaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaProblemaDTO categoriaProblemaDTO = (CategoriaProblemaDTO) document.getBean();
		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, WebUtil.getUsuarioSistema(request));
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, WebUtil.getUsuarioSistema(request));

		if (categoriaProblemaDTO.getIdCategoriaProblema().intValue() > 0) {

			List<ProblemaDTO> colProblema = (List<ProblemaDTO>) problemaService.findByIdCategoriaProblema(categoriaProblemaDTO.getIdCategoriaProblema());
			if(colProblema!=null){
				document.alert(UtilI18N.internacionaliza(request, "MSG09"));
				return;
			}

			//Setar dataFim
			categoriaProblemaDTO.setDataFim(UtilDatas.getDataAtual());
			categoriaProblemaService.update(categoriaProblemaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}


	@Override
	public Class getBeanClass() {

		return CategoriaProblemaDTO.class;
	}



	public void preencherComboTipoFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);

		HTMLSelect comboTipoFluxo =  document.getSelectById("idTipoFluxo");

		Collection<TipoFluxoDTO> tipoFluxoDto = tipoFluxoService.list();

		comboTipoFluxo.removeAllOptions();
		comboTipoFluxo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));



		if(tipoFluxoDto!=null){
			for(TipoFluxoDTO tipoFluxo : tipoFluxoDto){
				comboTipoFluxo.addOption(tipoFluxo.getIdTipoFluxo().toString(),tipoFluxo.getNomeFluxo());
			}
		}
	}

	public void preencherComboGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		HTMLSelect comboGrupoExecutor = document.getSelectById("idGrupoExecutor");
		ArrayList<GrupoDTO> grupoDTO = (ArrayList<GrupoDTO>) grupoService.listarGruposAtivos();

		comboGrupoExecutor.removeAllOptions();
		comboGrupoExecutor.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (grupoDTO != null) {
			for (GrupoDTO grupo : grupoDTO) {
				comboGrupoExecutor.addOption(grupo.getIdGrupo().toString(), grupo.getNome());
			}
		}

	}

	public void preencherComboTemplate(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TemplateSolicitacaoServicoService templateSolicitacaoServicoService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class, null);

		HTMLSelect comboTemplate =  document.getSelectById("idTemplate");

		Collection<TemplateSolicitacaoServicoDTO> listTemplateSolicitacaoServicoDto = templateSolicitacaoServicoService.list();

		comboTemplate.removeAllOptions();
		comboTemplate.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));



		if(listTemplateSolicitacaoServicoDto!=null){
			for(TemplateSolicitacaoServicoDTO templateSolicitacaoServicoDto : listTemplateSolicitacaoServicoDto){
				comboTemplate.addOption(templateSolicitacaoServicoDto.getIdTemplate().toString(),templateSolicitacaoServicoDto.getNomeTemplate());
			}
		}
	}


}
