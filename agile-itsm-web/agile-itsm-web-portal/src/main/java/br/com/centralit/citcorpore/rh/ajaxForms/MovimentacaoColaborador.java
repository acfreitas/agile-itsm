package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.JornadaTrabalhoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.MovimentacaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoFuncaoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
 
 
public class MovimentacaoColaborador extends AjaxFormAction {
	
	  private  String localeSession = null;

      public Class getBeanClass() {
            return MovimentacaoPessoalDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  
    	  String idCurriculo = request.getParameter("idCurriculo");
    	  String idSolServico = request.getParameter("idSolicitacaoServico");
    	  String idEntrevista = request.getParameter("idEntrevista");
    	  
    	  MovimentacaoPessoalDTO movimentacaoPessoalDTO = new MovimentacaoPessoalDTO();
    	  movimentacaoPessoalDTO.setIdSolicitacao(Integer.parseInt(idSolServico));
    	  movimentacaoPessoalDTO.setIdEntrevista(Integer.parseInt(idEntrevista));
    	  movimentacaoPessoalDTO.setIdCurriculo(Integer.parseInt(idCurriculo));
    	  if(movimentacaoPessoalDTO.getIdCurriculo() != null){
    		  restore(document, request, response, movimentacaoPessoalDTO);
    	  }

      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, MovimentacaoPessoalDTO movimentacaoPessoalDTO) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
          RequisicaoPessoalDTO requisicaoPessoalDTO = new RequisicaoPessoalDTO();
          
          CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
          CurriculoDTO curriculoDTO = new  CurriculoDTO();
          CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
          CargosDTO cargosDTO = new CargosDTO();
          RequisicaoFuncaoDTO funcaoDTO = new RequisicaoFuncaoDTO();
          RequisicaoFuncaoService requisicaoFuncaoService = (RequisicaoFuncaoService) ServiceLocator.getInstance().getService(RequisicaoFuncaoService.class, null);
          UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
          UnidadeDTO unidadeDTO = new UnidadeDTO();
          JornadaTrabalhoService jornadaService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, null);
          JornadaTrabalhoDTO jornadaDTO = new JornadaTrabalhoDTO();
          CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
          CentroResultadoDTO centroResultadoDTO = new CentroResultadoDTO();
          ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
          ProjetoDTO projetoDTO = new ProjetoDTO();
          EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
          EntrevistaCandidatoDTO entrevistaCandidatoDTO = new EntrevistaCandidatoDTO();
          UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
          UsuarioDTO usuarioDTO = new  UsuarioDTO();
          
          
          curriculoDTO.setIdCurriculo(movimentacaoPessoalDTO.getIdCurriculo());
          curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
          //Nome do colaborador
          if(curriculoDTO != null){
        	  movimentacaoPessoalDTO.setNomeCandidato(curriculoDTO.getNome());
          }
          //Data
          movimentacaoPessoalDTO.setData(UtilDatas.getDataAtual());
          
          requisicaoPessoalDTO.setIdSolicitacaoServico(movimentacaoPessoalDTO.getIdSolicitacao());
          if(requisicaoPessoalDTO.getIdSolicitacaoServico() != null){
        	  requisicaoPessoalDTO = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDTO);
          }
          funcaoDTO.setIdSolicitacaoServico(requisicaoPessoalDTO.getIdFuncao());
          if(funcaoDTO.getIdSolicitacaoServico() != null){
        	  funcaoDTO = (RequisicaoFuncaoDTO) requisicaoFuncaoService.restore(funcaoDTO);
        	  cargosDTO.setIdCargo(funcaoDTO.getIdCargo());
        	  cargosDTO = (CargosDTO) cargosService.restore(cargosDTO);
	          if(cargosDTO != null){
	        	  movimentacaoPessoalDTO.setCargo(cargosDTO.getNomeCargo());
	          }
          }
          unidadeDTO.setIdUnidade(requisicaoPessoalDTO.getIdUnidade());
          if(unidadeDTO.getIdUnidade() != null){
        	  unidadeDTO = (UnidadeDTO) unidadeService.restore(unidadeDTO);
        	  if(unidadeDTO != null){
        		  movimentacaoPessoalDTO.setLotacao(unidadeDTO.getNome());
        	  }
          }
         
          jornadaDTO.setIdJornada(requisicaoPessoalDTO.getIdJornada());
          if(jornadaDTO.getIdJornada() != null){
	          jornadaDTO = (JornadaTrabalhoDTO) jornadaService.restore(jornadaDTO);
	          if(jornadaDTO != null){
	        	  movimentacaoPessoalDTO.setHorarioTrabalho(jornadaDTO.getDescricao());
	          }
          }
          
          centroResultadoDTO.setIdCentroResultado(requisicaoPessoalDTO.getIdCentroCusto());
          if(centroResultadoDTO.getIdCentroResultado() != null){
        	  centroResultadoDTO = (CentroResultadoDTO) centroResultadoService.restore(centroResultadoDTO);
        	  if(centroResultadoDTO != null){
        		  movimentacaoPessoalDTO.setResultado(centroResultadoDTO.getNomeCentroResultado());
        	  }
          }
          
          projetoDTO.setIdProjeto(requisicaoPessoalDTO.getIdProjeto());
          if(projetoDTO.getIdProjeto() != null){
        	  projetoDTO = (ProjetoDTO) projetoService.restore(projetoDTO);
        	  if(projetoDTO != null){
        		  movimentacaoPessoalDTO.setProjeto(projetoDTO.getNomeProjeto());
        	  }
          }
          if(requisicaoPessoalDTO.getSalarioACombinar() != null){
        	  if(requisicaoPessoalDTO.getSalarioACombinar().equalsIgnoreCase("S")){
        		  movimentacaoPessoalDTO.setSalario(UtilI18N.internacionaliza(request, "citcorpore.comum.acombinar"));
        	  }
          }
          if(requisicaoPessoalDTO.getSalario() != null){
        	  movimentacaoPessoalDTO.setSalario(requisicaoPessoalDTO.getSalario().toString());
          }
          
          //Nome do projeto
         
          entrevistaCandidatoDTO.setIdEntrevista(movimentacaoPessoalDTO.getIdEntrevista());
          if(entrevistaCandidatoDTO != null){
        	  entrevistaCandidatoDTO = (EntrevistaCandidatoDTO) entrevistaCandidatoService.restore(entrevistaCandidatoDTO);
        	  usuarioDTO = (UsuarioDTO) usuarioService.restoreByIdEmpregado(entrevistaCandidatoDTO.getIdEntrevistadorGestor());
        	  movimentacaoPessoalDTO.setGestor(usuarioDTO.getNomeUsuario());
        }
          
          
          	HTMLForm form = document.getForm("form");
  			form.clear();
  			form.setValues(movimentacaoPessoalDTO);
  			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
 
      } 
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
  	}
      
	public void imprimirRelatorioMovimentacaoColaborador(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Collection<MovimentacaoPessoalDTO> col = new ArrayList<MovimentacaoPessoalDTO>();
		MovimentacaoPessoalDTO movimentacaoPessoalDTO = (MovimentacaoPessoalDTO) document.getBean();
		
		if (movimentacaoPessoalDTO != null) {
			if (movimentacaoPessoalDTO.getData() != null) {
				movimentacaoPessoalDTO.setDataStr(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, movimentacaoPessoalDTO.getData(), WebUtil.getLanguage(request)));
			}
			if (movimentacaoPessoalDTO.getDataInicio() != null) {
				movimentacaoPessoalDTO.setDataIncioStr(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, movimentacaoPessoalDTO.getDataInicio(), WebUtil.getLanguage(request)));
			}
			col.add(movimentacaoPessoalDTO);
		}
		
		
		JRDataSource dataSource = null;
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		UsuarioDTO usuario = WebUtil.getUsuario(request);


		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "FichaMovimentacaoColaborador.FichaMovimentacaoColaborador"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		
		parametros.put("citcorporeRelatorio.comum.data", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.data"));
		parametros.put("citcorporeRelatorio.comum.numeroSolicitacaoPessoal", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.numeroSolicitacaoPessoal"));
		parametros.put("citcorporeRelatorio.comum.gestor", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.gestor"));
		parametros.put("citcorporeRelatorio.comum.nomeColaborador", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.nomeColaborador"));
		parametros.put("citcorporeRelatorio.comum.indicacao", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.indicacao"));
		parametros.put("citcorporeRelatorio.comum.cargo", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.cargo"));
		parametros.put("citcorporeRelatorio.comum.lotacao", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.lotacao"));
		parametros.put("citcorporeRelatorio.comum.horarioTrabalho", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.horarioTrabalho"));
		parametros.put("citcorporeRelatorio.comum.centroResultado", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.centroResultado"));
		parametros.put("citcorporeRelatorio.comum.projeto", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.projeto"));
		parametros.put("citcorporeRelatorio.comum.dataInicio", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.dataInicio"));
		parametros.put("citcorporeRelatorio.comum.salario", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.salario"));
		parametros.put("citcorporeRelatorio.comum.planoExcelencia", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.planoExcelencia"));
		
		parametros.put("citcorporeRelatorio.comum.aperfeicoamento", "");
		parametros.put("citcorporeRelatorio.comum.comprometimento", "");
		parametros.put("citcorporeRelatorio.comum.eficacia", "");
		parametros.put("citcorporeRelatorio.comum.iniciativa", "");
		parametros.put("citcorporeRelatorio.comum.integridade", "");
		parametros.put("citcorpore.comum.justificativa", "");
		
		if (usuario != null && usuario.getNomeUsuario() != null) {
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		}
		

		if (col.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		} 
		
		try
		{
			dataSource = new JRBeanCollectionDataSource(col);
		
			Date dt = new Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "FichaMovimentacaoColaboradorDetalhado.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			 
			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			 
			//Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			 
			//Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/FichaMovimentacaoColaborador" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS + "/FichaMovimentacaoColaborador"
					+ strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
			document.executeScript("parent.window.location.reload()");
		
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}
	
	public void imprimirRelatorioMovimentacaoColaboradorDetalhado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Collection<MovimentacaoPessoalDTO> col = new ArrayList<MovimentacaoPessoalDTO>();
		MovimentacaoPessoalDTO movimentacaoPessoalDTO = (MovimentacaoPessoalDTO) document.getBean();
		
		if (movimentacaoPessoalDTO != null) {
			if (movimentacaoPessoalDTO.getData() != null) {
				movimentacaoPessoalDTO.setDataStr(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, movimentacaoPessoalDTO.getData(), WebUtil.getLanguage(request)));
			}
			if (movimentacaoPessoalDTO.getDataInicio() != null) {
				movimentacaoPessoalDTO.setDataIncioStr(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, movimentacaoPessoalDTO.getDataInicio(), WebUtil.getLanguage(request)));
			}
			col.add(movimentacaoPessoalDTO);
		}
		
		
		JRDataSource dataSource = null;
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		UsuarioDTO usuario = WebUtil.getUsuario(request);


		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "FichaMovimentacaoColaborador.FichaMovimentacaoColaborador"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		
		parametros.put("citcorporeRelatorio.comum.data", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.data"));
		parametros.put("citcorporeRelatorio.comum.numeroSolicitacaoPessoal", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.numeroSolicitacaoPessoal"));
		parametros.put("citcorporeRelatorio.comum.gestor", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.gestor"));
		parametros.put("citcorporeRelatorio.comum.nomeColaborador", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.nomeColaborador"));
		parametros.put("citcorporeRelatorio.comum.indicacao", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.indicacao"));
		parametros.put("citcorporeRelatorio.comum.cargo", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.cargo"));
		parametros.put("citcorporeRelatorio.comum.lotacao", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.lotacao"));
		parametros.put("citcorporeRelatorio.comum.horarioTrabalho", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.horarioTrabalho"));
		parametros.put("citcorporeRelatorio.comum.centroResultado", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.centroResultado"));
		parametros.put("citcorporeRelatorio.comum.projeto", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.projeto"));
		parametros.put("citcorporeRelatorio.comum.dataInicio", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.dataInicio"));
		parametros.put("citcorporeRelatorio.comum.salario", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.salario"));
		parametros.put("citcorporeRelatorio.comum.planoExcelencia", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.planoExcelencia"));
		
		parametros.put("citcorporeRelatorio.comum.aperfeicoamento", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.aperfeicoamento"));
		parametros.put("citcorporeRelatorio.comum.comprometimento", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.comprometimento"));
		parametros.put("citcorporeRelatorio.comum.eficacia", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.eficacia"));
		parametros.put("citcorporeRelatorio.comum.iniciativa", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.iniciativa"));
		parametros.put("citcorporeRelatorio.comum.integridade", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.integridade"));
		parametros.put("citcorpore.comum.justificativa", UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa"));
		
		
		if (usuario != null && usuario.getNomeUsuario() != null) {
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		}
		

		if (col.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		} 
		
		try
		{
			dataSource = new JRBeanCollectionDataSource(col);
		
			Date dt = new Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "FichaMovimentacaoColaboradorDetalhado.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			 
			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			 
			//Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			 
			//Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/FichaMovimentacaoColaboradorDetalhado" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS + "/FichaMovimentacaoColaboradorDetalhado"
					+ strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
			document.executeScript("parent.window.location.reload()");
		
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}
}


