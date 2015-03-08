package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.util.Enumerados.ItensPorPagina;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.util.UtilI18N;

/**
 * Esta classe é utilizada apenas para a Renderização da Paginação.
 */

public class ConexaoBI_Impl extends ConexaoBI implements GerenciamentoProcessos {

	public void iniciar(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, Integer tipoLista) throws Exception {
		criarScriptPaginacao(sb);
		Integer totalPaginasFinal = totalPaginas(request, itensPorPagina, this.getConexaoBIDTO());
		carregarCabecalhoGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request, tipoLista);
		if (totalPaginasFinal != null && totalPaginasFinal.intValue() > 0) {
			renderizarLista(sb, request, itensPorPagina, paginaSelecionada, true, tipoLista);
		}
		renderizarFiltroPesquisa(sb, request, true);
		carregarRodapeGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request);
	}

	public void criarScriptPaginacao(StringBuilder sb) {
		sb.append("<script type='text/javascript'>");
		sb.append("	function paginarItens(paginaSelecionada) {");
		sb.append("		if (paginaSelecionada <= -1) {");
		sb.append("		paginaSelecionada = 1; }");
		sb.append("		janelaAguarde();");
		sb.append("		document.formGerenciamento.paginaSelecionada.value = paginaSelecionada;");
		sb.append("		document.formGerenciamento.fireEvent('paginarItens');");
		sb.append("	}");
		sb.append("	function atualizarListaPorQtdItens() {");
		sb.append("		janelaAguarde();");
		sb.append("		$('#paginaSelecionada').val('1');");
		sb.append("		document.formGerenciamento.fireEvent('atualizarLista');");
		sb.append("	}");
		sb.append("	function janelaAguarde() {");
		sb.append("		JANELA_AGUARDE_MENU.show();");
		sb.append("	}");
		sb.append("	function fechaJanelaAguarde() {");
		sb.append("		JANELA_AGUARDE_MENU.hide();");
		sb.append("	}");
		sb.append("function carregarValorClasse(valor, classe) {");
		sb.append("		divs = document.getElementsByClassName(classe);");
		sb.append("		[].slice.call( divs ).forEach(function ( div ) {");
		sb.append("		div.innerHTML = valor;");
		sb.append("	});");
		sb.append("}");
		sb.append("function fecharJanelaAguarde() {");
		sb.append("	JANELA_AGUARDE_MENU.hide();");
		sb.append("}");
		sb.append("</script>");
	}
	
	/**
	 * Realiza a paginação dos itens e recarrega a lista de conexões
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void paginarItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setConexaoBIDTO((ConexaoBIDTO) document.getBean());
		this.recarregarLista(document, request, response);
	}
	
	public void carregarCabecalhoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request, Integer tipoLista) throws Exception {
		/* Inicio Abertura da div maior */
		sb.append("<div class='row-fluid' >");
		sb.append("		<div class='span12'>");
		/* Inicio Abertura da div maior */

		sb.append("	<input type='hidden' id='paginaSelecionada' name='paginaSelecionada' />");
		sb.append("	<input type='hidden' id='quantidadeTotal' name='quantidadeTotal' />");
		sb.append("	<input type='hidden' id='nomeCampoOrdenacao' name='nomeCampoOrdenacao' />");
		sb.append("	<input type='hidden' id='ordenacaoAsc' name='ordenacaoAsc' />");
		sb.append(" <input type='hidden' id='idConexaoBI' name='idConexaoBI' />	");
		sb.append(" <input type='hidden' id='abriuAgendamentoExcecao' name='abriuAgendamentoExcecao' />	");
		sb.append(" <input type='hidden' id='status' name='status' />	");
		sb.append(" <div id='titulo' >");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span6'>");
		sb.append("				<span class='btn btn-icon btn-primary' onclick='AbrirModalNovaConexaoBI();'><i></i>"+UtilI18N.internacionaliza(request,"bi.painelControle.conexao.novoClienteBI")+"</span>");
		sb.append("				<select id='itensPorPagina' name='itensPorPagina' onchange='atualizarListaPorQtdItens();' class='span1 lFloat marginless itensPorPagina' >");
									for (ItensPorPagina valor : ItensPorPagina.values()) {
		sb.append(						"<option>" + valor.getValor() + "</option>");
									}
		sb.append("				</select> ");
		sb.append(				UtilI18N.internacionaliza(request, "citcorpore.comum.verporpagina"));
		sb.append("			</div>");
		sb.append("			<div class='span6 paginacaoGerenciamento'>");
								paginacaoGerenciamento(totalPaginas, sb, paginaSelecionada, request);
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class='span8'>");
        sb.append("		</div>");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span4 right paginacaoGerenciamentoQuantidade'>");
		carregarValoresPaginacao(totalPaginas, sb, paginaSelecionada, request);
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("</div>");
	}
	
	public void carregarRodapeGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception {
		sb.append("<div id='sub'>");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span12 right paginacaoGerenciamento'>");
		paginacaoGerenciamento(totalPaginas, sb, paginaSelecionada, request);
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class='row-fluid'>");
		sb.append("			<div class='span12 right paginacaoGerenciamentoQuantidade'>");
		carregarValoresPaginacao(totalPaginas, sb, paginaSelecionada, request);
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("</div>");
		/* Inicio Fechamento da div maior */
		sb.append("		</div>");
		sb.append("</div>");
		/* Fim Fechamento da div maior */
	}
	
	public void renderizarFiltroPesquisa(StringBuilder sb, HttpServletRequest request, boolean flag) throws ServiceException, Exception {
		StringBuilder sbFiltro = new StringBuilder();
		if (flag) {
			sbFiltro.append("<div class='span12 filtro filtrobar main'>");
			sbFiltro.append("	<div class='row-fluid' >");
			sbFiltro.append("		<div class='span10 topfiltro'>");
			sbFiltro.append("			<div id='filtroLabel' class='span1'>");
			sbFiltro.append("		    	<label class='content-row'>");
			sbFiltro.append("		  		   <span class='escuro-negrito'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.filtro")+"</span>");
			sbFiltro.append("		   		 </label>");
			sbFiltro.append("			</div>");
			sbFiltro.append("			<select id='statusFiltro' name='statusFiltro' class='span1 lFloat marginless statusFiltro' >");
			sbFiltro.append("				<option value='T'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.todos")+"</option>");
			sbFiltro.append("				<option value=''>"+UtilI18N.internacionaliza(request,"bi.painelControle.conexao.semStatus")+"</option>");
			sbFiltro.append("				<option value='S'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.sucesso")+"</option>");
			sbFiltro.append("				<option value='F'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.falha")+"</option>");
			sbFiltro.append("				<option value='I'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.inativo")+"</option>");
			sbFiltro.append("			</select>");
			sbFiltro.append("			<button type='button' class='btn btn-default' id='filtro' onclick='pesquisarItensFiltro();'>"+UtilI18N.internacionaliza(request,"gantt.filtrar")+"</button>");
			sbFiltro.append("		</div>");
			sbFiltro.append("	</div>");
			sbFiltro.append("</div>");
			sb.insert(0, sbFiltro.toString());
		}
	}
}