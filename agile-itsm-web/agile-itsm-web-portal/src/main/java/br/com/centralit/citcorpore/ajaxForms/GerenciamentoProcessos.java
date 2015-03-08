package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;

public interface GerenciamentoProcessos {

	public void iniciar(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, Integer tipoLista) throws Exception;

	public void renderizarLista(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, boolean flag, Integer tipoLista) throws Exception;

	public void carregarCabecalhoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request, Integer tipoLista) throws Exception;

	public void carregarValoresPaginacao(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request);

	public void carregarRodapeGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception;

	public void paginacaoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception;

	public void criarScriptPaginacao(StringBuilder sb);

	public void recarregarLista(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void paginarItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void atualizarLista(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void carregarItensPaginacao(DocumentHTML document, HttpServletRequest request, Integer totalPaginasFinal) throws Exception;

}
