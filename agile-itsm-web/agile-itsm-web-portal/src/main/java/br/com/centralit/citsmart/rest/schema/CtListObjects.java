package br.com.centralit.citsmart.rest.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DtoAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtListObjects", propOrder = {"objetos"})
public class CtListObjects extends CtMessageResp {

    @XmlElement(name = "Objeto", required = true)
    @XmlJavaTypeAdapter(DtoAdapter.class)
    protected List<IDto> objetos;

    public List<IDto> getObjetos() {
        if (objetos == null) {
            objetos = new ArrayList<>();
        }
        return objetos;
    }

    public void setObjetos(final List<IDto> objetos) {
        this.objetos = objetos;
    }

}
