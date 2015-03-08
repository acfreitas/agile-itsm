package br.com.centralit.citcorpore.util;
import java.util.TreeSet;


/**
 * @author euler.ramos
 *
 */
public class NoArvore implements Comparable<NoArvore>{
	
	private Integer id;
	private String texto;
	private Integer idPai;
	private Integer nivel;
	private TreeSet<NoArvore> filhos;
	
	public NoArvore(Integer id, String texto, Integer idPai) {
		super();
		this.id = id;
		this.texto = texto;
		this.idPai = idPai;
		this.filhos = new TreeSet<NoArvore>();
	}

	public Integer getId() {
		return id;
	}

	public String getTexto() {
		return texto;
	}

	public Integer getIdPai() {
		return idPai;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	public TreeSet<NoArvore> getFilhos() {
		return filhos;
	}

	@Override
	public int compareTo(NoArvore outroNoArvore) {
		return this.getTexto().compareTo(outroNoArvore.getTexto());
	}

}