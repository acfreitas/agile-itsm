package br.com.citframework.tld;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.centralit.citcorpore.ajaxForms.GerenciamentoProcessos;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

public class GerenciamentoField extends BodyTagSupport {

	private static final long serialVersionUID = 259097494202966188L;
	private boolean paginacao;
	private String classeExecutora;
	private Integer tipoLista;

	public int doStartTag() throws JspException {
		GerenciamentoProcessos gp = null;
		Integer paginaSelecionada = 1;
		@SuppressWarnings("unused")
		Integer itensPorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));
		StringBuilder strBuff = new StringBuilder();
		try {

			gp = (GerenciamentoProcessos) Class.forName(this.getClasseExecutora()).newInstance();
			gp.iniciar(strBuff, (HttpServletRequest) pageContext.getRequest(), Enumerados.ItensPorPagina.CINCO.getValor(), paginaSelecionada, this.getTipoLista());
			pageContext.getOut().println(strBuff.toString());

		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("Classe executora não encontrada");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

	public boolean isPaginacao() {
		return paginacao;
	}

	public void setPaginacao(boolean paginacao) {
		this.paginacao = paginacao;
	}

	public String getClasseExecutora() {
		return classeExecutora;
	}

	public void setClasseExecutora(String classeExecutora) {
		this.classeExecutora = classeExecutora;
	}

	public Integer getTipoLista() {
		return tipoLista;
	}

	public void setTipoLista(Integer tipoLista) {
		this.tipoLista = tipoLista;
	}

}
