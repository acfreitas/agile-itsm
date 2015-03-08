package br.com.centralit.citcorpore.ajaxForms;

import java.io.FileNotFoundException;
import java.util.Collection;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.centralit.citcorpore.negocio.ExternalConnectionService;
import br.com.centralit.citcorpore.negocio.ImportarDadosService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.borges
 *
 */
@SuppressWarnings("rawtypes")
public class ExternalConnection extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("tipo");
		inicializarCombo(combo, request);

		document.focusInFirstActivateField(null);


	}

	@Override
	public Class getBeanClass() {
		return ExternalConnectionDTO.class;
	}

	/**
	 * Metodo de salve
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 * @author Thiago.Borges
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if(testeConexaoComOsDadosInformados(document, request, response))
			salvar(document, request, response);
		else
			document.executeScript("confirmarSalvarDadosInvalidos()");
	}

	/**
	 * Metodo de salvar
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 * @author Thiago.Borges
	 */

	public void salvar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ExternalConnectionDTO conexoesDTO = (ExternalConnectionDTO) document.getBean();

		ExternalConnectionService conexoesService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

		if (conexoesDTO.getIdExternalConnection() == null || conexoesDTO.getIdExternalConnection() == 0) {

				if (conexoesService.consultarConexoesAtivas(conexoesDTO)) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
					return;
				}

				if (conexoesDTO.getFileName().equals("")){
					conexoesDTO.setDeleted(null);
					conexoesDTO.setFileName(null);
				}

				conexoesService.create(conexoesDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {

				if (conexoesService.consultarConexoesAtivas(conexoesDTO)) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
					return;
				}

				conexoesDTO.setDeleted(null);
				conexoesService.update(conexoesDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_CONEXOES()");
		document.executeScript("fechaAlterarSenha()");
	}

	/**
	 * Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExternalConnectionDTO conexoesDTO = (ExternalConnectionDTO) document.getBean();

		ExternalConnectionService conexoesService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, WebUtil.getUsuarioSistema(request));

		ImportarDadosService importarDadosService = (ImportarDadosService) ServiceLocator.getInstance().getService(ImportarDadosService.class, WebUtil.getUsuarioSistema(request));

		Collection<ImportarDadosDTO> importacoesRelacionadas = importarDadosService.consultarImportarDadosRelacionados(conexoesDTO.getIdExternalConnection());

		if(importacoesRelacionadas != null && !importacoesRelacionadas.isEmpty()){

			document.alert(UtilI18N.internacionaliza(request, "MSG08"));
			return;

		}

		if (conexoesDTO.getIdExternalConnection().intValue() > 0) {
			conexoesService.deletarConexao(conexoesDTO, document);
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_CONEXOES()");
	}


	/**
	 * Metodo para testar se a conexão é valida
	 * @param document
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws ScriptException
	 * @throws Exception
	 * @author Thiago.Borges
	 * @
	 */
	public Boolean testeConexaoComOsDadosInformados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, ScriptException, Exception {
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        ExternalConnectionDTO obj = (ExternalConnectionDTO) document.getBean();
        Boolean aux = null;
        String conteudoScript = new String("function teste(){importPackage( java.sql );"
        		+ "var conn = DriverManager.getConnection( '"+obj.getUrlJdbc()+"', '"+obj.getJdbcUser()+"', '"+obj.getJdbcPassword()+"' );"
        		+ "var stmt = conn.createStatement();"
        		+ "stmt.close();"
        		+ "conn.close();"
        		+ "return stmt;"
        		+ "}"
        		);

        engine.eval(conteudoScript);
        Invocable invocable = (Invocable) engine;


          	  try{
              	 invocable.invokeFunction("teste");
                  	aux = true;
              }catch (Exception e){
              		aux = false;
              		e.printStackTrace();
              }
			return aux;




	}


	/**
	 * metodo para fazer teste de conexão
	 * @param document
	 * @param request
	 * @param response
	 * @throws FileNotFoundException
	 * @throws ScriptException
	 * @throws Exception
	 * @author Thiago.Borges
	 */
	public void testeConexao (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, ScriptException, Exception{
        ExternalConnectionDTO obj = (ExternalConnectionDTO) document.getBean();

        if(obj.getUrlJdbc().equals("")){
        	document.alert(UtilI18N.internacionaliza(request, "externalconnection.obrigatorio.url"));
        }else{
        	if(obj.getJdbcUser() .equals("")){
        		document.alert(UtilI18N.internacionaliza(request, "externalconnection.obrigatorio.usuario"));
        	}else{
        		if(obj.getJdbcPassword().equals("")){
        		document.alert(UtilI18N.internacionaliza(request, "externalconnection.obrigatorio.senha"));
        		}else{
        			if (testeConexaoComOsDadosInformados(document, request, response)){
        				document.alert(UtilI18N.internacionaliza(request, "conexaoBI.testeConexaoSucesso"));
        			}else{
        				document.alert(UtilI18N.internacionaliza(request, "externalconnection.erro.conexao"));
        			}
        		}

        	}
        }

	}


	/**
	 * @param componenteCombo
	 * @param request
	 */
	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
			componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			componenteCombo.addOption("J", UtilI18N.internacionaliza(request, "externalconnection.jdbc"));
	}

	/**
	 * Metodo para restaura os campos.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExternalConnectionDTO conexoesDTO = (ExternalConnectionDTO) document.getBean();
		ExternalConnectionService conexoesService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
		conexoesDTO = (ExternalConnectionDTO) conexoesService.restore(conexoesDTO);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(conexoesDTO);
	}

}
