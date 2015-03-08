package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.DepartamentoService;
import br.com.centralit.citcorpore.negocio.JornadaService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCursoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaExperienciaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaCursoDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaExperienciaDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaIdiomaDao;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoFuncaoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
 
 @SuppressWarnings({"rawtypes","unchecked"})
public class RequisicaoPessoal extends AjaxFormAction {
 
	  public String getAcao() {
		 return RequisicaoPessoalDTO.ACAO_CRIACAO; 
	  }
	  
      public Class getBeanClass() {
            return RequisicaoPessoalDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
    	  
    	  RequisicaoFuncaoService requisicaoFuncaoService = (RequisicaoFuncaoService) ServiceLocator.getInstance().getService(RequisicaoFuncaoService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idFuncao = (HTMLSelect) document.getSelectById("idFuncao");
          idFuncao.removeAllOptions();
          idFuncao.addOption("", "--- Selecione ---");
          Collection<RequisicaoFuncaoDTO> colRequisicaoFuncao = requisicaoFuncaoService.retornaFuncoesAprovadas();          
          if(colRequisicaoFuncao != null && !colRequisicaoFuncao.isEmpty()) {
        	  for (RequisicaoFuncaoDTO requisicaoFuncaoDTO : colRequisicaoFuncao) {
        		  idFuncao.addOption(""+requisicaoFuncaoDTO.getIdSolicitacaoServico(), requisicaoFuncaoDTO.getNomeFuncao());
        	  }
          }
          request.getSession().setAttribute("IdSolicitacaoServico", requisicaoPessoalDto.getIdSolicitacaoServico());
          HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
          idProjeto.removeAllOptions();
          idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
          if (requisicaoPessoalDto.getIdContrato() != null) {
              ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
              ContratoDTO contratoDto = new ContratoDTO();
              contratoDto.setIdContrato(requisicaoPessoalDto.getIdContrato());
              contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
              if (contratoDto != null) {
                  ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
                  Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
                  if(colProjetos != null && !colProjetos.isEmpty()) 
                      idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
              }
          }
          
          JornadaService jornadaService = (JornadaService) ServiceLocator.getInstance().getService(JornadaService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idJornada = (HTMLSelect) document.getSelectById("idJornada");
          idJornada.removeAllOptions();
          idJornada.addOption("", "--- Selecione ---");
          Collection colJornada = jornadaService.list();
          if(colJornada != null && !colJornada.isEmpty())
              idJornada.addOptions(colJornada, "idJornada", "descricao", null);
          
          DepartamentoService departamentoService = (DepartamentoService) ServiceLocator.getInstance().getService(DepartamentoService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idLotacao = (HTMLSelect) document.getSelectById("idLotacao");
          idLotacao.removeAllOptions();
          idLotacao.addOption("", "--- Selecione ---");
          Collection colLotacoes = departamentoService.list();
          if(colLotacoes != null && !colLotacoes.isEmpty())
              idLotacao.addOptions(colLotacoes, "idDepartamento", "descricao", null);
          
          CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
          idCentroCusto.removeAllOptions();
          idCentroCusto.addOption("", "--- Selecione ---");
          if(request.getSession().getAttribute("idSolicitante") != null){
              requisicaoPessoalDto.setIdSolicitante((Integer) request.getSession().getAttribute("idSolicitante"));
          } else {
        	  requisicaoPessoalDto.setIdSolicitante(0);
          }
          Collection colCentroCusto = centroResultadoService.listAtivosVinculados(requisicaoPessoalDto.getIdSolicitante(), "Pessoal");
          if(colCentroCusto != null && !colCentroCusto.isEmpty())
              idCentroCusto.addOptions(colCentroCusto, "idCentroResultado", "nomeHierarquizado", null);
          HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipoContratacao");
          
          comboTipo.removeAllOptions();
  		  comboTipo.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
//  		  comboTipo.addOption("C", UtilI18N.internacionaliza(request, "colaborador.contratoEmpresaPJ"));
  		  comboTipo.addOption("T", UtilI18N.internacionaliza(request, "colaborador.estagio"));
  		  comboTipo.addOption("E", UtilI18N.internacionaliza(request, "colaborador.empregadoCLT"));
  		  comboTipo.addOption("A", UtilI18N.internacionaliza(request, "colaborador.autonomo"));
  		  comboTipo.addOption("O", UtilI18N.internacionaliza(request, "colaborador.temporario"));
  		  
//  		  comboTipo.addOption("F", UtilI18N.internacionaliza(request, "colaborador.freeLancer"));
//  		  comboTipo.addOption("O", UtilI18N.internacionaliza(request, "colaborador.outros"));
//  		  comboTipo.addOption("X", UtilI18N.internacionaliza(request, "colaborador.socio"));
//  		  comboTipo.addOption("S", UtilI18N.internacionaliza(request, "colaborador.solicitante"));

  		  preencherComboPais(document, request, response);
  		  
    	  restore(document,request,response); 
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
          
          if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
	            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
	            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);

	            document.executeScript("inicializaContLinha()");
	            
	            HTMLTable tblFormacaoAcademica = document.getTableById("tblFormacaoAcademica");
	            tblFormacaoAcademica.deleteAllRows();
	            if (requisicaoPessoalDto.getColFormacaoAcademica() != null) {
	            	for (RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"FormacaoAcademica\","+requisicaoFormacaoAcademicaDto.getIdFormacaoAcademica()+",\""+requisicaoFormacaoAcademicaDto.getDescricao()+"\",\""+requisicaoFormacaoAcademicaDto.getObrigatorio()+"\",\""+requisicaoFormacaoAcademicaDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
	            tblCertificacao.deleteAllRows();
	            if (requisicaoPessoalDto.getColCertificacao() != null) {
	            	for (RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Certificacao\","+requisicaoCertificacaoDto.getIdCertificacao()+",\""+requisicaoCertificacaoDto.getDescricao()+"\",\""+requisicaoCertificacaoDto.getObrigatorio()+"\",\""+requisicaoCertificacaoDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblCurso = document.getTableById("tblCurso");
	            tblCurso.deleteAllRows();
	            if (requisicaoPessoalDto.getColCurso() != null) {
	            	for (RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Curso\","+requisicaoCursoDto.getIdCurso()+",\""+requisicaoCursoDto.getDescricao()+"\",\""+requisicaoCursoDto.getObrigatorio()+"\",\""+requisicaoCursoDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
	            tblExperienciaInformatica.deleteAllRows();
	            if (requisicaoPessoalDto.getColExperienciaInformatica() != null) {
	            	for (RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\","+requisicaoExperienciaInformaticaDto.getIdExperienciaInformatica()+",\""+requisicaoExperienciaInformaticaDto.getDescricao()+"\",\""+requisicaoExperienciaInformaticaDto.getObrigatorio()+"\",\""+requisicaoExperienciaInformaticaDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblIdioma = document.getTableById("tblIdioma");
	            tblIdioma.deleteAllRows();
	            if (requisicaoPessoalDto.getColIdioma() != null) {
	            	for (RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Idioma\","+requisicaoIdiomaDto.getIdIdioma()+",\""+requisicaoIdiomaDto.getDescricao()+"\",\""+requisicaoIdiomaDto.getObrigatorio()+"\",\""+requisicaoIdiomaDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblExperienciaAnterior = document.getTableById("tblExperienciaAnterior");
	            tblExperienciaAnterior.deleteAllRows();
	            if (requisicaoPessoalDto.getColExperienciaAnterior() != null) {
	            	for (RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\","+requisicaoExperienciaAnteriorDto.getIdConhecimento()+",\""+requisicaoExperienciaAnteriorDto.getDescricao()+"\",\""+requisicaoExperienciaAnteriorDto.getObrigatorio()+"\",\""+requisicaoExperienciaAnteriorDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblConhecimento = document.getTableById("tblConhecimento");
	            tblConhecimento.deleteAllRows();
	            if (requisicaoPessoalDto.getColConhecimento() != null) {
	            	for (RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Conhecimento\","+requisicaoConhecimentoDto.getIdConhecimento()+",\""+requisicaoConhecimentoDto.getDescricao()+"\",\""+requisicaoConhecimentoDto.getObrigatorio()+"\",\""+requisicaoConhecimentoDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblHabilidade = document.getTableById("tblHabilidade");
	            tblHabilidade.deleteAllRows();
	            if (requisicaoPessoalDto.getColHabilidade() != null) {
	            	for (RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Habilidade\","+requisicaoHabilidadeDto.getIdHabilidade()+",\""+requisicaoHabilidadeDto.getDescricao()+"\",\""+requisicaoHabilidadeDto.getObrigatorio()+"\",\""+requisicaoHabilidadeDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblAtitudeIndividual = document.getTableById("tblAtitudeIndividual");
	            tblAtitudeIndividual.deleteAllRows();
	            if (requisicaoPessoalDto.getColAtitudeIndividual() != null) {
	            	for (RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"AtitudeIndividual\","+requisicaoAtitudeIndividualDto.getIdAtitudeIndividual()+",\""+requisicaoAtitudeIndividualDto.getDescricao()+"\",\""+requisicaoAtitudeIndividualDto.getObrigatorio()+"\",\""+requisicaoAtitudeIndividualDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            preencherComboUfs(document,request,requisicaoPessoalDto);
	            preencherComboCidade(document,request,requisicaoPessoalDto); 
          }
          
          requisicaoPessoalDto.setAcao(getAcao());
          HTMLForm form = document.getForm("form");
          form.setValues(requisicaoPessoalDto);
          
      } 

  /**
   * Restaura uma função que ja foi criada atraves da requisição de função.
   * 	
   */
    public void restoreFuncao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
          if (requisicaoPessoalDto.getIdFuncao() == null) {
        	  document.executeScript("configuraPerfilFuncao(0)");
        	  return;
          }
          document.executeScript("configuraPerfilFuncao(1)");
          
          //HTMLForm form = document.getForm("form");
          //form.setValues(descricaoCargoDto);
          
          document.executeScript("inicializaContLinha()");
          
          if (requisicaoPessoalDto.getIdFuncao()==null){
        	  requisicaoPessoalDto.setIdFuncao(0);
          }
              
          HTMLTable tblFormacaoAcademica = document.getTableById("tblFormacaoAcademica");
          tblFormacaoAcademica.deleteAllRows();
          PerspectivaTecnicaFormacaoAcademicaDao perspectivaTecnicaFormacaoAcademicaDao = new PerspectivaTecnicaFormacaoAcademicaDao();
          ArrayList<PerspectivaTecnicaFormacaoAcademicaDTO> colFormacaoAcademica = (ArrayList<PerspectivaTecnicaFormacaoAcademicaDTO>) perspectivaTecnicaFormacaoAcademicaDao.findByIdFuncao(requisicaoPessoalDto.getIdFuncao());
          if (colFormacaoAcademica != null) {
          	for (PerspectivaTecnicaFormacaoAcademicaDTO perspectivaTecnicaFormacaoAcademicaDTO : colFormacaoAcademica) {
          		document.executeScript("adicionarLinhaSelecionada(\"FormacaoAcademica\","+perspectivaTecnicaFormacaoAcademicaDTO.getIdFormacaoAcademica()+",\""+perspectivaTecnicaFormacaoAcademicaDTO.getDescricaoFormacaoAcademica()+"\",\""+perspectivaTecnicaFormacaoAcademicaDTO.getObrigatorioFormacaoAcademica()+"\",\""+perspectivaTecnicaFormacaoAcademicaDTO.getDetalheFormacaoAcademica()+"\");");
			}
          }
          
          HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
          tblCertificacao.deleteAllRows();
          PerspectivaTecnicaCertificacaoDao perspectivaTecnicaCertificacaoDao = new PerspectivaTecnicaCertificacaoDao();
          ArrayList<PerspectivaTecnicaCertificacaoDTO> colCertificacao = (ArrayList<PerspectivaTecnicaCertificacaoDTO>) perspectivaTecnicaCertificacaoDao.findByIdFuncao(requisicaoPessoalDto.getIdFuncao());
          if (colCertificacao != null) {
          	for (PerspectivaTecnicaCertificacaoDTO perspectivaTecnicaCertificacaoDTO : colCertificacao) {
          		document.executeScript("adicionarLinhaSelecionada(\"Certificacao\","+perspectivaTecnicaCertificacaoDTO.getIdCertificacao()+",\""+perspectivaTecnicaCertificacaoDTO.getDescricaoCertificacao()+"\",\""+perspectivaTecnicaCertificacaoDTO.getObrigatorioCertificacao()+"\",\""+perspectivaTecnicaCertificacaoDTO.getVersaoCertificacao()+"\");");
			}
          }
          
          HTMLTable tblCurso = document.getTableById("tblCurso");
          tblCurso.deleteAllRows();
          PerspectivaTecnicaCursoDao perspectivaTecnicaCursoDao = new PerspectivaTecnicaCursoDao();
          ArrayList<PerspectivaTecnicaCursoDTO> colCurso = (ArrayList<PerspectivaTecnicaCursoDTO>) perspectivaTecnicaCursoDao.findByIdFuncao(requisicaoPessoalDto.getIdFuncao());
          if (colCurso != null) {
          	for (PerspectivaTecnicaCursoDTO perspectivaTecnicaCursoDTO : colCurso) {
          		document.executeScript("adicionarLinhaSelecionada(\"Curso\","+perspectivaTecnicaCursoDTO.getIdCurso()+",\""+perspectivaTecnicaCursoDTO.getDescricaoCurso()+"\",\""+perspectivaTecnicaCursoDTO.getObrigatorioCurso()+"\",\""+perspectivaTecnicaCursoDTO.getDetalheCurso()+"\");");
			}
          }
          
          HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
          tblExperienciaInformatica.deleteAllRows();
/*          RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
          ArrayList<RequisicaoExperienciaInformaticaDTO> colExperienciaInformatica = (ArrayList<RequisicaoExperienciaInformaticaDTO>) requisicaoExperienciaInformaticaDao.findByIdFuncao(requisicaoPessoalDto.getIdFuncao());
          if (colExperienciaInformatica != null) {
          	for (RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDTO : colExperienciaInformatica) {
          		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\","+requisicaoExperienciaInformaticaDTO.getIdExperienciaInformatica()+",\""+requisicaoExperienciaInformaticaDTO.getDescricao()+"\",\""+requisicaoExperienciaInformaticaDTO.getObrigatorio()+"\",\""+requisicaoExperienciaInformaticaDTO.getDetalhe()+"\");");
			}
          }*/
          
  /*      
   * 
   * Alterado para consulta Exeperiencia informatica
   * 
   *       HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
          tblExperienciaInformatica.deleteAllRows();
          PerspectivaTecnicaExperienciaDao perspectivaTecnicaExperienciaDao = new PerspectivaTecnicaExperienciaDao();
          ArrayList<PerspectivaTecnicaExperienciaDTO> colExperiencia = (ArrayList<PerspectivaTecnicaExperienciaDTO>) perspectivaTecnicaExperienciaDao.findByidSolicitacao(requisicaoPessoalDto.getIdFuncao());
          if (colExperiencia != null) {
          	for (PerspectivaTecnicaExperienciaDTO perspectivaTecnicaExperienciaDTO : colExperiencia) {
          		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\","+perspectivaTecnicaExperienciaDTO.getIdPerspectivaTecnicaExperiencia()+",\""+perspectivaTecnicaExperienciaDTO.getDescricaoExperiencia()+"\",\""+perspectivaTecnicaExperienciaDTO.getObrigatorioExperiencia()+"\",\""+perspectivaTecnicaExperienciaDTO.getDetalheExperiencia()+"\");");
			}
          }*/
          
          HTMLTable tblIdioma = document.getTableById("tblIdioma");
          tblIdioma.deleteAllRows();
          PerspectivaTecnicaIdiomaDao perspectivaTecnicaIdiomaDao = new PerspectivaTecnicaIdiomaDao();
          ArrayList<PerspectivaTecnicaIdiomaDTO> colIdioma = (ArrayList<PerspectivaTecnicaIdiomaDTO>) perspectivaTecnicaIdiomaDao.findByIdFuncao(requisicaoPessoalDto.getIdFuncao());
          if (colIdioma != null) {
          	for (PerspectivaTecnicaIdiomaDTO perspectivaTecnicaIdiomaDTO : colIdioma) {
          		document.executeScript("adicionarLinhaSelecionada(\"Idioma\","+perspectivaTecnicaIdiomaDTO.getIdIdioma()+",\""+perspectivaTecnicaIdiomaDTO.getDescricaoIdioma()+"\",\""+perspectivaTecnicaIdiomaDTO.getObrigatorioIdioma()+"\",\""+perspectivaTecnicaIdiomaDTO.getDetalheIdioma()+"\");");
			}
          }         
          
          HTMLTable tblExperienciaAnterior = document.getTableById("tblExperienciaAnterior");
          tblExperienciaAnterior.deleteAllRows();
          
          PerspectivaTecnicaExperienciaDao perspectivaTecnicaExperienciaDao = new PerspectivaTecnicaExperienciaDao();
          ArrayList<PerspectivaTecnicaExperienciaDTO> colExperiencia = (ArrayList<PerspectivaTecnicaExperienciaDTO>) perspectivaTecnicaExperienciaDao.findByIdFuncao(requisicaoPessoalDto.getIdFuncao());
          if (colExperiencia != null) {
          	for (PerspectivaTecnicaExperienciaDTO perspectivaTecnicaExperienciaDTO : colExperiencia) {
          		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\","+perspectivaTecnicaExperienciaDTO.getIdConhecimento()+",\""+perspectivaTecnicaExperienciaDTO.getDescricaoExperiencia()+"\",\""+perspectivaTecnicaExperienciaDTO.getObrigatorioExperiencia()+"\",\""+perspectivaTecnicaExperienciaDTO.getDetalheExperiencia()+"\");");
          	}
          }
          

          //if (descricaoCargoDto.getColExperienciaAnterior() != null) {
          //	for (CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
          //		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\","+cargoExperienciaAnteriorDto.getIdConhecimento()+",\""+cargoExperienciaAnteriorDto.getDescricao()+"\",\""+cargoExperienciaAnteriorDto.getObrigatorio()+"\",\""+cargoExperienciaAnteriorDto.getDetalhe()+"\");");
		  //	}
          //}
          
          HTMLTable tblConhecimento = document.getTableById("tblConhecimento");
          tblConhecimento.deleteAllRows();
          //Não tem correspondente em Perspectiva para sugestão!
          //if (descricaoCargoDto.getColConhecimento() != null) {
          //	for (CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
          //		document.executeScript("adicionarLinhaSelecionada(\"Conhecimento\","+cargoConhecimentoDto.getIdConhecimento()+",\""+cargoConhecimentoDto.getDescricao()+"\",\""+cargoConhecimentoDto.getObrigatorio()+"\",\""+cargoConhecimentoDto.getDetalhe()+"\");");
		  //	}
          //}
          
          HTMLTable tblHabilidade = document.getTableById("tblHabilidade");
          tblHabilidade.deleteAllRows();
          //Não tem correspondente em Perspectiva para sugestão!
          //if (descricaoCargoDto.getColHabilidade() != null) {
          //	for (CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
          //		document.executeScript("adicionarLinhaSelecionada(\"Habilidade\","+cargoHabilidadeDto.getIdHabilidade()+",\""+cargoHabilidadeDto.getDescricao()+"\",\""+cargoHabilidadeDto.getObrigatorio()+"\",\""+cargoHabilidadeDto.getDetalhe()+"\");");
		  //	}
          //}
          
          HTMLTable tblAtitudeIndividual = document.getTableById("tblAtitudeIndividual");
          tblAtitudeIndividual.deleteAllRows();
          //Não tem correspondente em Perspectiva para sugestão!
          //if (descricaoCargoDto.getColAtitudeIndividual() != null) {
          //	for (CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
          //		document.executeScript("adicionarLinhaSelecionada(\"AtitudeIndividual\","+cargoAtitudeIndividualDto.getIdAtitudeIndividual()+",\""+cargoAtitudeIndividualDto.getDescricao()+"\",\""+cargoAtitudeIndividualDto.getObrigatorio()+"\",\""+cargoAtitudeIndividualDto.getDetalhe()+"\");");
		  //	}
          //}
      } 
      
      private void preencherComboPais(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
    	  
    		PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

    		HTMLSelect comboPais = (HTMLSelect) document.getSelectById("idPais");
    		ArrayList<PaisDTO> listPais = (ArrayList) paisServico.list();
    		comboPais.removeAllOptions();
    		comboPais.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

    		if (listPais != null) {
    			for (PaisDTO paisDto : listPais) {
    				comboPais.addOption(paisDto.getIdPais().toString(), paisDto.getNomePais());
    			}
    	  }
    	}
        
      protected void preencherComboUfs(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
    		UfDTO ufDto = new UfDTO();

    		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

    		if (requisicaoPessoalDTO.getIdPais() != null) 
    			ufDto.setIdPais(requisicaoPessoalDTO.getIdPais());

    		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("idUf");

    		ArrayList<UfDTO> listUfs = (ArrayList) ufService.listByIdPais(ufDto);

    		comboUfs.removeAllOptions();
    		comboUfs.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

    		if (listUfs != null) {
    			for (UfDTO uf : listUfs) {
    				comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
    			}
    		}
    	}
      protected void preencherComboCidade(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
    		CidadesDTO cidadeDto = new CidadesDTO();

    		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

    		if (requisicaoPessoalDTO.getIdUf() != null) {
    			cidadeDto.setIdUf(requisicaoPessoalDTO.getIdUf());
    		}
    		HTMLSelect comboCidade = (HTMLSelect) document.getSelectById("idCidade");

    		ArrayList<CidadesDTO> listCidade = (ArrayList) cidadesService.listByIdCidades(cidadeDto);

    		comboCidade.removeAllOptions();
    		comboCidade.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    		if (listCidade != null) {
    			for (CidadesDTO cidade : listCidade) {
    				comboCidade.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
    			}
    		}
    	}
    	
      public void preencherComboPais(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
  		RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();
  		preencherComboPais(document, request, requisicaoPessoalDTO);
  	}
      
  	public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

  		RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();  		
  		preencherComboUfs(document, request, requisicaoPessoalDTO);
  	}
  	public void preencherComboCidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

  		RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();  		
  		preencherComboCidade(document, request, requisicaoPessoalDTO);
  	}
}