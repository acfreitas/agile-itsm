package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtListTasks", propOrder = {"listarIncidentes", "listarRequisicoes", "listarCompras", "listarViagens", "listarRH"})
public class CtListTasks extends CtMessage {

    @XmlElement(name = "ListarIncidentes", required = true)
    protected String listarIncidentes;

    @XmlElement(name = "ListarRequisicoes", required = true)
    protected String listarRequisicoes;

    @XmlElement(name = "ListarCompras", required = true)
    protected String listarCompras;

    @XmlElement(name = "ListarViagens", required = true)
    protected String listarViagens;

    @XmlElement(name = "ListarRH", required = true)
    protected String listarRH;

    public String getListarIncidentes() {
        return listarIncidentes;
    }

    public void setListarIncidentes(final String listarIncidentes) {
        this.listarIncidentes = listarIncidentes;
    }

    public String getListarRequisicoes() {
        return listarRequisicoes;
    }

    public void setListarRequisicoes(final String listarRequisicoes) {
        this.listarRequisicoes = listarRequisicoes;
    }

    public String getListarCompras() {
        return listarCompras;
    }

    public void setListarCompras(final String listarCompras) {
        this.listarCompras = listarCompras;
    }

    public String getListarViagens() {
        return listarViagens;
    }

    public void setListarViagens(final String listarViagens) {
        this.listarViagens = listarViagens;
    }

    public String getListarRH() {
        return listarRH;
    }

    public void setListarRH(final String listarRH) {
        this.listarRH = listarRH;
    }

}
