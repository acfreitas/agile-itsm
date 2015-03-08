package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.List;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoInicioDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.Order;

public class ElementoFluxoInicioDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoInicioDTO> getBean() {
        return ElementoFluxoInicioDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Inicio;
    }

    public ElementoFluxoInicioDTO restoreByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        condicao.add(new Condition("tipoElemento", "=", this.getTipoElemento().name()));
        ordenacao.add(new Order("idElemento"));
        final List<ElementoFluxoDTO> list = (List<ElementoFluxoDTO>) super.findByCondition(condicao, ordenacao);
        if (list != null && !list.isEmpty()) {
            return (ElementoFluxoInicioDTO) list.get(0);
        }
        return null;
    }

}
