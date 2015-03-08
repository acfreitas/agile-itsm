package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.centralit.citcorpore.bean.PosicionamentoAtendenteDTO;
import br.com.centralit.citcorpore.bean.result.PosicionamentoAtendenteResultDTO;
import br.com.centralit.citcorpore.integracao.PosicionamentoAtendenteDAO;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Assert;
import br.com.citframework.util.UtilDatas;

import com.google.common.base.Function;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class PosicionamentoAtendenteServiceEjb extends CrudServiceImpl implements PosicionamentoAtendenteService {

    private PosicionamentoAtendenteDAO dao;

    @Override
    protected PosicionamentoAtendenteDAO getDao() {
        if (dao == null) {
            dao = new PosicionamentoAtendenteDAO();
        }
        return dao;
    }

    private static final Function<PosicionamentoAtendenteResultDTO, Integer> groupUsuarioFunction = new Function<PosicionamentoAtendenteResultDTO, Integer>() {

        @Override
        public Integer apply(final PosicionamentoAtendenteResultDTO item) {
            return item.getIdUsuario();
        }

    };

    @Override
    public List<PosicionamentoAtendenteResultDTO> listLastLocationWithSolicitationInfo(final PosicionamentoAtendenteDTO posicionamentoAtendente) throws Exception {
        Assert.notNull(posicionamentoAtendente, "'PosicionamentoAtendente' must not be null.");
        final List<PosicionamentoAtendenteResultDTO> onDB = this.getDao().listLastLocationWithSolicitationInfo(posicionamentoAtendente);
        for (final PosicionamentoAtendenteResultDTO posicionamentoAtendenteResult : onDB) {
            final Timestamp ultimaVisualizacao = posicionamentoAtendenteResult.getUltimaVisualizacao();
            posicionamentoAtendenteResult.setUltimaVisualizacao(null);
            posicionamentoAtendenteResult.setLastSeem(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, ultimaVisualizacao));
        }

        final List<PosicionamentoAtendenteResultDTO> posicionamentos = new ArrayList<>();
        if (onDB != null) {
            final Multimap<Integer, PosicionamentoAtendenteResultDTO> maped = Multimaps.index(onDB.iterator(), groupUsuarioFunction);
            final Set<Integer> ids = maped.keySet();
            for (final Integer integer : ids) {
                posicionamentos.add(((List<PosicionamentoAtendenteResultDTO>) maped.get(integer)).get(0));
            }
        }

        return posicionamentos;
    }

}
