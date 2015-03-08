package br.com.centralit.citsmart.rest.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtListTasksResp", propOrder = {"qtdeTarefas", "tarefas"})
public class CtListTasksResp extends CtMessageResp {

    @XmlElement(required = true)
    protected int qtdeTarefas;

    @XmlElement(name = "Tarefa", required = true)
    protected List<TarefaFluxoDTO> tarefas;

    public int getQtdeTarefas() {
        return qtdeTarefas;
    }

    public void setQtdeTarefas(final int qtdeTarefas) {
        this.qtdeTarefas = qtdeTarefas;
    }

    public List<TarefaFluxoDTO> getTarefas() {
        if (tarefas == null) {
            tarefas = new ArrayList<>();
        }
        return tarefas;
    }

    public void setTarefas(final List<TarefaFluxoDTO> tarefas) {
        this.tarefas = tarefas;
    }

}
