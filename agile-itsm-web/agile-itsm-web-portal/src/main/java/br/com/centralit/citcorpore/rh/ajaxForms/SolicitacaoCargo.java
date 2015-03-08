package br.com.centralit.citcorpore.rh.ajaxForms;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.CargoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.CargoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.negocio.DescricaoCargoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
 
 
public class SolicitacaoCargo extends AjaxFormAction {
 
      @SuppressWarnings("rawtypes")
	public Class getBeanClass() {
            return DescricaoCargoDTO.class;
      }
      
	  public String getAcao() {
		 return DescricaoCargoDTO.ACAO_CRIACAO; 
	  }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  restore(document,request,response); 
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
            UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null){
                  document.alert("Sessão expirada! Favor efetuar logon novamente!");
                  return;
            }
            
            DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) document.getBean();
            if (descricaoCargoDto.getIdSolicitacaoServico() == null)
                return;
          
            DescricaoCargoService descricaoCargoService = (DescricaoCargoService) ServiceLocator.getInstance().getService(DescricaoCargoService.class, null);
            descricaoCargoDto = (DescricaoCargoDTO) descricaoCargoService.findByIdSolicitacaoServico(descricaoCargoDto.getIdSolicitacaoServico());
          
            descricaoCargoDto.setAcao(getAcao());

            HTMLForm form = document.getForm("form");
            form.setValues(descricaoCargoDto);
            
            document.executeScript("inicializaContLinha()");
            
            HTMLTable tblFormacaoAcademica = document.getTableById("tblFormacaoAcademica");
            tblFormacaoAcademica.deleteAllRows();
            if (descricaoCargoDto.getColFormacaoAcademica() != null) {
            	for (CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : descricaoCargoDto.getColFormacaoAcademica()) {
            		document.executeScript("adicionarLinhaSelecionada(\"FormacaoAcademica\","+cargoFormacaoAcademicaDto.getIdFormacaoAcademica()+",\""+cargoFormacaoAcademicaDto.getDescricao()+"\",\""+cargoFormacaoAcademicaDto.getObrigatorio()+"\",\""+cargoFormacaoAcademicaDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
            tblCertificacao.deleteAllRows();
            if (descricaoCargoDto.getColCertificacao() != null) {
            	for (CargoCertificacaoDTO cargoCertificacaoDto : descricaoCargoDto.getColCertificacao()) {
            		document.executeScript("adicionarLinhaSelecionada(\"Certificacao\","+cargoCertificacaoDto.getIdCertificacao()+",\""+cargoCertificacaoDto.getDescricao()+"\",\""+cargoCertificacaoDto.getObrigatorio()+"\",\""+cargoCertificacaoDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblCurso = document.getTableById("tblCurso");
            tblCurso.deleteAllRows();
            if (descricaoCargoDto.getColCurso() != null) {
            	for (CargoCursoDTO cargoCursoDto : descricaoCargoDto.getColCurso()) {
            		document.executeScript("adicionarLinhaSelecionada(\"Curso\","+cargoCursoDto.getIdCurso()+",\""+cargoCursoDto.getDescricao()+"\",\""+cargoCursoDto.getObrigatorio()+"\",\""+cargoCursoDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
            tblExperienciaInformatica.deleteAllRows();
            if (descricaoCargoDto.getColExperienciaInformatica() != null) {
            	for (CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : descricaoCargoDto.getColExperienciaInformatica()) {
            		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\","+cargoExperienciaInformaticaDto.getIdExperienciaInformatica()+",\""+cargoExperienciaInformaticaDto.getDescricao()+"\",\""+cargoExperienciaInformaticaDto.getObrigatorio()+"\",\""+cargoExperienciaInformaticaDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblIdioma = document.getTableById("tblIdioma");
            tblIdioma.deleteAllRows();
            if (descricaoCargoDto.getColIdioma() != null) {
            	for (CargoIdiomaDTO cargoIdiomaDto : descricaoCargoDto.getColIdioma()) {
            		document.executeScript("adicionarLinhaSelecionada(\"Idioma\","+cargoIdiomaDto.getIdIdioma()+",\""+cargoIdiomaDto.getDescricao()+"\",\""+cargoIdiomaDto.getObrigatorio()+"\",\""+cargoIdiomaDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblExperienciaAnterior = document.getTableById("tblExperienciaAnterior");
            tblExperienciaAnterior.deleteAllRows();
            if (descricaoCargoDto.getColExperienciaAnterior() != null) {
            	for (CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
            		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\","+cargoExperienciaAnteriorDto.getIdConhecimento()+",\""+cargoExperienciaAnteriorDto.getDescricao()+"\",\""+cargoExperienciaAnteriorDto.getObrigatorio()+"\",\""+cargoExperienciaAnteriorDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblConhecimento = document.getTableById("tblConhecimento");
            tblConhecimento.deleteAllRows();
            if (descricaoCargoDto.getColConhecimento() != null) {
            	for (CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
            		document.executeScript("adicionarLinhaSelecionada(\"Conhecimento\","+cargoConhecimentoDto.getIdConhecimento()+",\""+cargoConhecimentoDto.getDescricao()+"\",\""+cargoConhecimentoDto.getObrigatorio()+"\",\""+cargoConhecimentoDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblHabilidade = document.getTableById("tblHabilidade");
            tblHabilidade.deleteAllRows();
            if (descricaoCargoDto.getColHabilidade() != null) {
            	for (CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
            		document.executeScript("adicionarLinhaSelecionada(\"Habilidade\","+cargoHabilidadeDto.getIdHabilidade()+",\""+cargoHabilidadeDto.getDescricao()+"\",\""+cargoHabilidadeDto.getObrigatorio()+"\",\""+cargoHabilidadeDto.getDetalhe()+"\");");
				}
            }
            
            HTMLTable tblAtitudeIndividual = document.getTableById("tblAtitudeIndividual");
            tblAtitudeIndividual.deleteAllRows();
            if (descricaoCargoDto.getColAtitudeIndividual() != null) {
            	for (CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
            		document.executeScript("adicionarLinhaSelecionada(\"AtitudeIndividual\","+cargoAtitudeIndividualDto.getIdAtitudeIndividual()+",\""+cargoAtitudeIndividualDto.getDescricao()+"\",\""+cargoAtitudeIndividualDto.getObrigatorio()+"\",\""+cargoAtitudeIndividualDto.getDetalhe()+"\");");
				}
            }
      }           

}

