package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.rh.bean.AdmissaoEmpregadoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.AdmissaoEmpregadoService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
 
 
public class AdmissaoEmpregado extends AjaxFormAction {

      public Class getBeanClass() {
            return AdmissaoEmpregadoDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  
    	HTMLSelect comboUfOrgaoExpedidor = (HTMLSelect) document.getSelectById("idUFOrgExpedidor");
		HTMLSelect comboCtpsIdUf = (HTMLSelect) document.getSelectById("ctpsIdUf");
		HTMLSelect comboEstadoCivil = (HTMLSelect) document.getSelectById("estadoCivil");
    	
		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

		Collection colUfs = ufService.list();

		comboUfOrgaoExpedidor.removeAllOptions();
		comboUfOrgaoExpedidor.addOption("", "--");
		comboUfOrgaoExpedidor.addOptions(colUfs, "idUf", "siglaUf", null);
		comboCtpsIdUf.removeAllOptions();
		comboCtpsIdUf.addOption("", "--");
		comboCtpsIdUf.addOptions(colUfs, "idUf", "siglaUf", null);
    	
  		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
  		Collection colUnidade = unidadeService.list();
  		
  		HTMLSelect idUnidade = document.getSelectById("idUnidade");
  		idUnidade.addOption(" ", "--- Selecione ---");
  		idUnidade.addOptions(colUnidade, "idUnidade", "nome", null);
  		
  		HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipo");
        
  		comboTipo.removeAllOptions();
		comboTipo.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboTipo.addOption("C", UtilI18N.internacionaliza(request, "colaborador.contratoEmpresaPJ"));
		comboTipo.addOption("E", UtilI18N.internacionaliza(request, "colaborador.empregadoCLT"));
		comboTipo.addOption("T", UtilI18N.internacionaliza(request, "colaborador.estagio"));
		comboTipo.addOption("F", UtilI18N.internacionaliza(request, "colaborador.freeLancer"));
		comboTipo.addOption("O", UtilI18N.internacionaliza(request, "colaborador.outros"));
		comboTipo.addOption("X", UtilI18N.internacionaliza(request, "colaborador.socio"));
		comboTipo.addOption("S", UtilI18N.internacionaliza(request, "colaborador.solicitante"));
		
		comboEstadoCivil.removeAllOptions();
		comboEstadoCivil.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboEstadoCivil.addOption("2", UtilI18N.internacionaliza(request, "colaborador.casado") );
		comboEstadoCivil.addOption("3", UtilI18N.internacionaliza(request, "colaborador.divorciado"));
		comboEstadoCivil.addOption("5", UtilI18N.internacionaliza(request, "colaborador.separadoJudicialmente"));
		comboEstadoCivil.addOption("1", UtilI18N.internacionaliza(request, "colaborador.solteiro"));
		comboEstadoCivil.addOption("4", UtilI18N.internacionaliza(request, "colaborador.viuvo"));
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          AdmissaoEmpregadoDTO admissaoEmpregadoDto = (AdmissaoEmpregadoDTO) document.getBean();
          
          EntrevistaCandidatoDTO entrevistaCandidatoDto = new EntrevistaCandidatoDTO();
          
          entrevistaCandidatoDto.setIdEntrevista(admissaoEmpregadoDto.getIdEntrevista());
          EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
          entrevistaCandidatoDto = (EntrevistaCandidatoDTO) entrevistaCandidatoService.restore(entrevistaCandidatoDto);
          
          CurriculoDTO curriculoDto = new CurriculoDTO();
          
          curriculoDto.setIdCurriculo(entrevistaCandidatoDto.getIdCurriculo());
          CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
          curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
          
          
          
          TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDto = new TriagemRequisicaoPessoalDTO();
          
          triagemRequisicaoPessoalDto.setIdTriagem(entrevistaCandidatoDto.getIdTriagem());
          TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, null);
          triagemRequisicaoPessoalDto = (TriagemRequisicaoPessoalDTO) triagemRequisicaoPessoalService.restore(triagemRequisicaoPessoalDto);
          
          RequisicaoPessoalDTO requisicaoPessoalDto = new RequisicaoPessoalDTO();
          requisicaoPessoalDto.setIdSolicitacaoServico(triagemRequisicaoPessoalDto.getIdSolicitacaoServico());
          RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
          requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
          
          CargosDTO cargosDto = new CargosDTO();
          cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
          
          CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
          cargosDto = (CargosDTO) cargosService.restore(cargosDto);
          
          admissaoEmpregadoDto.setValorSalario(requisicaoPessoalDto.getSalario());
          admissaoEmpregadoDto.setTipo(requisicaoPessoalDto.getTipoContratacao());
          admissaoEmpregadoDto.setCargo(cargosDto.getNomeCargo());
          admissaoEmpregadoDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
          admissaoEmpregadoDto.setCpf(curriculoDto.getCpf());
          admissaoEmpregadoDto.setDataNascimento(curriculoDto.getDataNascimento());
          admissaoEmpregadoDto.setSexo(curriculoDto.getSexo());
          admissaoEmpregadoDto.setEstadoCivil(curriculoDto.getEstadoCivil());
          admissaoEmpregadoDto.setNome(curriculoDto.getNome());
          admissaoEmpregadoDto.setObservacoes(entrevistaCandidatoDto.getObservacoes());
          
          HTMLForm form = document.getForm("form");
          form.setValues(admissaoEmpregadoDto);
      } 
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
    	  AdmissaoEmpregadoDTO admissaoEmpregadoDto = (AdmissaoEmpregadoDTO) document.getBean();
    	  AdmissaoEmpregadoService admissaoEmpregadoService = (AdmissaoEmpregadoService) ServiceLocator.getInstance().getService(AdmissaoEmpregadoService.class, null);
    	  

    	  
    	  if (admissaoEmpregadoDto.getIdEmpregado() == null || admissaoEmpregadoDto.getIdEmpregado().intValue() == 0)
  			if (admissaoEmpregadoDto.getValorSalario() != null)
  				admissaoEmpregadoDto = admissaoEmpregadoService.calcularCustos(admissaoEmpregadoDto);
 
    	  admissaoEmpregadoDto.setIdSituacaoFuncional(1);
    	  admissaoEmpregadoDto.setData(UtilDatas.getDataAtual());
    	  admissaoEmpregadoDto.setIdResponsavel(usuario.getIdEmpregado());
    	  admissaoEmpregadoService.create(admissaoEmpregadoDto);
	  	  
    	  document.alert("Admissão efetuada com sucesso");
    	  
    	  HTMLForm form = document.getForm("form");
	  	  form.clear();
  	}
}


