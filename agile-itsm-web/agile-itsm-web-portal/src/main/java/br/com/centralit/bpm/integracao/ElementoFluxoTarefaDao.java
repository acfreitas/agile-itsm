package br.com.centralit.bpm.integracao;

import br.com.centralit.bpm.dto.ElementoFluxoTarefaDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class ElementoFluxoTarefaDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoTarefaDTO> getBean() {
        return ElementoFluxoTarefaDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Tarefa;
    }

}
