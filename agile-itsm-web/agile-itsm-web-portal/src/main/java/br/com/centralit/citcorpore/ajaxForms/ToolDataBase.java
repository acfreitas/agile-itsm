package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ToolDataBaseDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 *
 * @author flávio.santana
 *
 */
public class ToolDataBase extends AjaxFormAction {

	protected JdbcEngine engine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
	/**
	 * Inicializa os dados ao carregar a tela.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ToolDataBaseDTO toolDataBase = (ToolDataBaseDTO) document.getBean();
		Connection conn = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));
		if (conn != null) {
			try {
				request.getSession().setAttribute("dadosSGBD", null);
				Map<Integer, String> dadosSGBD = new HashMap<Integer, String>();
				conn.getCatalog();
				conn.getTypeMap();

				dadosSGBD.put(1, CITCorporeUtil.SGBD_PRINCIPAL);
				if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("oracle")){
					dadosSGBD.put(2, conn.getMetaData().getUserName());
				}else{
					dadosSGBD.put(2, conn.getCatalog());
				}

				dadosSGBD.put(3, conn.getMetaData().getURL());
				request.getSession().setAttribute("dadosSGBD", dadosSGBD);
			} catch (Exception e) {
			} finally {
				try {
					if(conn!=null && !conn.isClosed()){
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		String sql = "";
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("oracle")){
			sql = "SELECT TABLE_NAME FROM USER_TABLES ORDER BY TABLE_NAME ";
		}else{
			sql = "SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog', 'information_schema') order by table_name";
		}

		StringBuilder dadosResutado = new StringBuilder();
		List<Object[]> tabelas = execSQL(sql, toolDataBase.getQuantRows());
		dadosResutado.append("<label class='tabTab'><b>"+UtilI18N.internacionaliza(request, "tooldatabase.tabela")+"</b></label>");
		dadosResutado.append("<label class='tabAtu' onclick='createTable()'><b>"+UtilI18N.internacionaliza(request, "tooldatabase.criaTabela")+"</b></label><br /><br />");
		if (tabelas != null) {
			for (Object[] dados : tabelas) {
				dadosResutado.append("<b><a href='#' onclick=\"acao('" + dados[0] + "')\">" + dados[0] + "</a></b><br />");
			}
		}
		document.getElementById("estTabelas").setInnerHTML(dadosResutado.toString());
	}

	public List<Object[]> execSQL(String sql, String quantRows)throws Exception{
		if(quantRows == null){
		quantRows = "1000";
		}
		List<Object[]> tabelas = engine.execSQL(sql, null, new Integer(quantRows));
		return tabelas;
	}

	public void executaSQL(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ToolDataBaseDTO toolDataBase = (ToolDataBaseDTO) document.getBean();
		StringBuilder sql = new StringBuilder();
		sql.append("<label><b>"+UtilI18N.internacionaliza(request, "tooldatabase.resultadoScriptSQL")+"</b></label><label><b>"+UtilI18N.internacionaliza(request, "tooldatabase.quantRows") +": "
				+ "<input type=\"text\" id=\"quantRows\" name=\"quantRows\" value=\"1000\" /> </b></label><br /><br />");
		if (toolDataBase.getTipoAcao() == null) {
			toolDataBase.setTipoAcao("");
		}
		Integer resultado = 0;
		if (toolDataBase.getStrExec() != null || !toolDataBase.getStrExec().equalsIgnoreCase("")) {
			if (toolDataBase.getStrExec().toLowerCase().contains("select")) {
				List<Object[]> lista = null;
				try {
					lista = execSQL(toolDataBase.getStrExec(), toolDataBase.getQuantRows());
					sql.append("<label class='infoResultado'>"+UtilI18N.internacionaliza(request, "tooldatabase.resultadoConsulta")+"</label><br /><table>");
					if (lista != null) {
						for (Object[] dados : lista) {
							sql.append("<tr>");
							for (Object dados2 : dados) {
								sql.append("<td>" + dados2 + "</td>");
							}
							sql.append("</tr>");
						}
					}
				} catch (Exception e) {
					sql.append(e.getMessage());
				}

			} else {
				try {
					resultado = engine.execUpdate(toolDataBase.getStrExec(), null);
					if (resultado != null)
						sql.append(UtilI18N.internacionaliza(request, "tooldatabase.executadoSucesso"));
				} catch (Exception e) {
					sql.append(e.getMessage());
				}
			}
			sql.append("</table>");
		}
		document.getElementById("outputSQL").setInnerHTML(sql.toString());
		load(document, request, response);
	}

	public void executaMontaSQL(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ToolDataBaseDTO toolDataBase = (ToolDataBaseDTO) document.getBean();
		StringBuilder sql = new StringBuilder();
		StringBuilder saidaResultado = new StringBuilder();
		if(toolDataBase.getTipoAcao() == null){
			toolDataBase.setTipoAcao("");
		}
		if(!toolDataBase.getTipoAcao().equalsIgnoreCase("")){
			String sqlCamposTabela = "select attname from pg_attribute where attstattarget = -1 and attrelid = ( select pg_class.oid as table_id from pg_class left join pg_namespace on pg_class.relnamespace = pg_namespace.oid "
					+ "where pg_class.relname = '"+toolDataBase.getTabela().trim()+"' and  pg_namespace.nspname = 'public');";
			if(toolDataBase.getTipoAcao().equalsIgnoreCase("list")){
				sql.append("SELECT ");
				List<Object[]> lista = execSQL(sqlCamposTabela, toolDataBase.getQuantRows());
				if(lista != null){
					int cont = 1;
					for(Object[] dados : lista){
						if(cont != lista.size()){
							sql.append( dados[0] + " ,");
						}else{
							sql.append( dados[0] + " ");
						}
						cont++;
					}
					sql.append(" FROM "+ toolDataBase.getTabela().trim() +" ");
					sql.append(";");
					document.getElementById("strExec").setValue(sql.toString());
					toolDataBase.setTipoAcao("");
					toolDataBase.setStrExec(sql.toString());
					document.setBean(toolDataBase);
					executaSQL(document, request, response);
				}
			}else if(toolDataBase.getTipoAcao().equalsIgnoreCase("insert")){
				sql.append("INSERT INTO "+ toolDataBase.getTabela().trim() +" ( ");
				List<Object[]> lista = execSQL(sqlCamposTabela, toolDataBase.getQuantRows());
				if(lista != null){
					int cont = 1;
					for(Object[] dados : lista){
						if(cont != lista.size()){
							sql.append( dados[0] + " ,");
						}else{
							sql.append( dados[0] + " ");
						}
						cont++;
					}
					sql.append(" ) VALUES ( ");
					cont = 1;
					for(Object[] dados : lista){
						if(cont != lista.size()){
							sql.append( "? ,");
						}else{
							sql.append( "? ");
						}
						cont++;
					}
					sql.append(" ) ;");
					document.getElementById("strExec").setValue(sql.toString());
				}
			}else if(toolDataBase.getTipoAcao().equalsIgnoreCase("update")){
				sql.append("UPDATE "+ toolDataBase.getTabela().trim() +" SET ");
				List<Object[]> lista = execSQL(sqlCamposTabela, toolDataBase.getQuantRows());
				if(lista != null){
					int cont = 1;
					for(Object[] dados : lista){
						if(cont != lista.size()){
							sql.append( dados[0] + " = ? ,");
						}else{
							sql.append( dados[0] + " = ? ");
						}
						cont++;
					}
					sql.append(" WHERE ");
					for(Object[] dados : lista){
						sql.append( dados[0] + " = ? ");
						break;
					}
					sql.append(";");
					document.getElementById("strExec").setValue(sql.toString());
				}
			}else if(toolDataBase.getTipoAcao().equalsIgnoreCase("createTable")){
				sql.append("CREATE TABLE nomeTabela ( ");
				sql.append(");");
				document.getElementById("strExec").setValue(sql.toString());
			}else if(toolDataBase.getTipoAcao().equalsIgnoreCase("del")){
				sql.append("DELETE FROM "+ toolDataBase.getTabela().trim() +" WHERE ");
				List<Object[]> lista = execSQL(sqlCamposTabela, toolDataBase.getQuantRows());
				if(lista != null){
					for(Object[] dados : lista){
						sql.append( dados[0] + " = ? ");
						break;
					}
					sql.append(";");
					document.getElementById("strExec").setValue(sql.toString());
				}
			}else if(toolDataBase.getTipoAcao().equalsIgnoreCase("drop")){
				Integer resultado = null;
				saidaResultado.append("<label><b>"+UtilI18N.internacionaliza(request, "tooldatabase.resultadoConsulta")+"</b></label><label><b>"+UtilI18N.internacionaliza(request, "tooldatabase.quantRows")+": "
						+ "<input type=\"text\" id=\"quantRows\" name=\"quantRows\" value=\"1000\" /> </b></label><br /><br />");
				sql.append("DROP TABLE "+ toolDataBase.getTabela().trim() +"");
				sql.append(";");
				document.getElementById("strExec").setValue(sql.toString());
				toolDataBase.setStrExec(sql.toString());
				try {
					resultado = engine.execUpdate(toolDataBase.getStrExec(), null);
					if (resultado != null)
						saidaResultado.append(UtilI18N.internacionaliza(request, "tooldatabase.executadoSucesso"));
				} catch (Exception e) {
					saidaResultado.append(e.getMessage());
				}
				document.getElementById("outputSQL").setInnerHTML(saidaResultado.toString());
				load(document, request, response);
			}else if(toolDataBase.getTipoAcao().equalsIgnoreCase("addColumn")){
				sql.append("ALTER TABLE "+ toolDataBase.getTabela().trim() +" ADD COLUMN nomeColuna tipoColuna");
				sql.append(";");
				document.getElementById("strExec").setValue(sql.toString());
			}
		}
		document.executeScript("$(\"#POPUP_ACAO\").dialog(\"close\");");
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void commit(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ToolDataBaseDTO toolDataBase = (ToolDataBaseDTO) document.getBean();
	}

	/**
	 * recupera os dados ao carregar página
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public Class<ToolDataBaseDTO> getBeanClass() {
		return ToolDataBaseDTO.class;
	}

}
