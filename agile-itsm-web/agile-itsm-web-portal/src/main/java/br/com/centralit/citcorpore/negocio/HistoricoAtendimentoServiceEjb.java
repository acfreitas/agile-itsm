package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.com.centralit.citcorpore.bean.HistoricoAtendimentoDTO;
import br.com.centralit.citcorpore.bean.result.HistoricoAtendimentoResultDTO;
import br.com.centralit.citcorpore.bean.result.HistoricoAtendimentoResultDTO.Solicitation;
import br.com.centralit.citcorpore.bean.result.HistoricoAtendimentoSearchResultDTO;
import br.com.centralit.citcorpore.integracao.HistoricoAtendimentoDAO;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.citframework.util.Assert;
import br.com.citframework.util.geo.Coordinate;

import com.google.common.base.Function;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class HistoricoAtendimentoServiceEjb implements HistoricoAtendimentoService {

    private HistoricoAtendimentoDAO dao;

    private HistoricoAtendimentoDAO getDAO() {
        if (dao == null) {
            dao = new HistoricoAtendimentoDAO();
        }
        return dao;
    }

    private static final Function<Solicitation, Integer> groupSolicitationFunction = new Function<Solicitation, Integer>() {

        @Override
        public Integer apply(final Solicitation item) {
            return item.getNum();
        }

    };

    private static final Function<HistoricoAtendimentoSearchResultDTO, Integer> groupAtendenteFunction = new Function<HistoricoAtendimentoSearchResultDTO, Integer>() {

        @Override
        public Integer apply(final HistoricoAtendimentoSearchResultDTO item) {
            return item.getAtendenteId();
        }

    };

    private static final Function<HistoricoAtendimentoSearchResultDTO, Integer> groupSolicitationInResultFunction = new Function<HistoricoAtendimentoSearchResultDTO, Integer>() {

        @Override
        public Integer apply(final HistoricoAtendimentoSearchResultDTO item) {
            return item.getSolicitacaoNumero();
        }

    };

    @Override
    public List<HistoricoAtendimentoResultDTO> listHistoricoAtendimentoWithSolicitationInfo(final HistoricoAtendimentoDTO historicoAtendimento) throws Exception {
        Assert.notNull(historicoAtendimento, "'HistoricoAtendimento' must not be null.");
        final List<HistoricoAtendimentoSearchResultDTO> onDB = this.getDAO().listHistoricoAtendimentoWithSolicitationInfo(historicoAtendimento);
        final int onDBResultSize = onDB.size();
        List<HistoricoAtendimentoResultDTO> result = new ArrayList<>();
        if (onDBResultSize > 0) {
            result = this.generateHistoricoByUser(this.groupResultByUser(onDB));
        }
        return result;
    }

    private List<HistoricoAtendimentoSearchResultDTO> getHistoricoForSteps(final List<HistoricoAtendimentoSearchResultDTO> result) {
        final Multimap<Integer, HistoricoAtendimentoSearchResultDTO> maped = Multimaps.index(result.iterator(), groupSolicitationInResultFunction);

        final Integer num = (Integer) maped.keySet().toArray()[0];
        return (List<HistoricoAtendimentoSearchResultDTO>) maped.get(num);
    }

    private List<List<HistoricoAtendimentoSearchResultDTO>> groupResultByUser(final List<HistoricoAtendimentoSearchResultDTO> result) {
        final List<List<HistoricoAtendimentoSearchResultDTO>> grouped = new ArrayList<>();

        final Multimap<Integer, HistoricoAtendimentoSearchResultDTO> maped = Multimaps.index(result.iterator(), groupAtendenteFunction);

        final Set<Integer> ids = maped.keySet();
        for (final Integer integer : ids) {
            grouped.add((List<HistoricoAtendimentoSearchResultDTO>) maped.get(integer));
        }

        return grouped;
    }

    private List<HistoricoAtendimentoResultDTO> generateHistoricoByUser(final List<List<HistoricoAtendimentoSearchResultDTO>> grouped) {
        final List<HistoricoAtendimentoResultDTO> byUser = new ArrayList<>();
        for (final List<HistoricoAtendimentoSearchResultDTO> list : grouped) {
            final HistoricoAtendimentoResultDTO historico = new HistoricoAtendimentoResultDTO();

            makeSteps(historico, this.getHistoricoForSteps(list));

            final int size = list.size();
            if (size > 1) {
                final HistoricoAtendimentoSearchResultDTO start = list.get(0);

                historico.setIdAtendente(start.getAtendenteId());
                historico.setAtendente(start.getAtendenteNome());

                makeStartPoint(historico, start);

                for (final HistoricoAtendimentoSearchResultDTO position : list) {
                    makeSolicitation(historico, position);
                }

                this.adjustSolicitations(historico);

                final HistoricoAtendimentoSearchResultDTO finish = list.get(size - 1);
                makeFinishPoint(historico, finish);
            } else {
                final HistoricoAtendimentoSearchResultDTO unique = list.get(0);
                historico.setIdAtendente(unique.getAtendenteId());
                historico.setAtendente(unique.getAtendenteNome());

                makeStartPoint(historico, unique);

                makeSolicitation(historico, unique);

                makeFinishPoint(historico, unique);
            }
            byUser.add(historico);
        }
        return byUser;
    }

    private void adjustSolicitations(final HistoricoAtendimentoResultDTO historico) {
        final List<Solicitation> solicitations = Collections.unmodifiableList(historico.getSolicitations());

        final Multimap<Integer, Solicitation> maped = Multimaps.index(solicitations.iterator(), groupSolicitationFunction);
        final Set<Integer> nums = maped.keySet();

        historico.getSolicitations().clear();
        for (final Integer integer : nums) {
            final Solicitation solicitation = (Solicitation) maped.get(integer).toArray()[0];
            historico.getSolicitations().add(solicitation);
        }
    }

    private static void makeStartPoint(final HistoricoAtendimentoResultDTO historico, final HistoricoAtendimentoSearchResultDTO unique) {
        final Coordinate startPoint = new Coordinate();
        startPoint.setLat(unique.getPosicaoLatitude());
        startPoint.setLng(unique.getPosicaoLongitude());
        historico.setStart(startPoint);
    }

    private static void makeFinishPoint(final HistoricoAtendimentoResultDTO historico, final HistoricoAtendimentoSearchResultDTO unique) {
        final Coordinate finishPoint = new Coordinate();
        finishPoint.setLat(unique.getPosicaoLatitude());
        finishPoint.setLng(unique.getPosicaoLongitude());
        historico.setFinish(finishPoint);
    }

    private static void makeSteps(final HistoricoAtendimentoResultDTO historico, final List<HistoricoAtendimentoSearchResultDTO> forSteps) {
        for (final HistoricoAtendimentoSearchResultDTO position : forSteps) {
            final Coordinate step = new Coordinate();
            step.setLat(position.getPosicaoLatitude());
            step.setLng(position.getPosicaoLongitude());
            historico.getSteps().add(step);
        }
    }

    private static void makeSolicitation(final HistoricoAtendimentoResultDTO historico, final HistoricoAtendimentoSearchResultDTO position) {
        final Solicitation solicitation = historico.new Solicitation();
        solicitation.setNum(position.getSolicitacaoNumero());
        solicitation.setStatus(position.getSolicitacaoSituacao());
        solicitation.setServ(position.getSolicitacaoServico());
        solicitation.setDesc(position.getSolicitacaoDescricao());
        solicitation.setUnid(position.getUnidadeNome());
        solicitation.setSol(position.getSolicitacaoSolicitante());
        solicitation.setHH(position.getSolicitacaoPrazoHH());
        solicitation.setPrazoMM(position.getSolicitacaoPrazoMM());
        
        final Integer atribuicaoId = position.getAtribuicaoId();
        final Timestamp atribuicaoDatetime = position.getAtribuicaoDatetime();
        final SituacaoSolicitacaoServico sitSolicitacao = SituacaoSolicitacaoServico.valueOf(position.getSolicitacaoSituacao());

        if (sitSolicitacao.equals(SituacaoSolicitacaoServico.Fechada) || sitSolicitacao.equals(SituacaoSolicitacaoServico.Resolvida)) {
            solicitation.setSit(1);
        } else if (sitSolicitacao.equals(SituacaoSolicitacaoServico.EmAndamento) && atribuicaoId != null && atribuicaoDatetime != null) {
            solicitation.setSit(2);
        } else if (sitSolicitacao.equals(SituacaoSolicitacaoServico.EmAndamento) && atribuicaoId != null) {
            solicitation.setSit(3);
        } else if (sitSolicitacao.equals(SituacaoSolicitacaoServico.EmAndamento) && atribuicaoId == null) {
            solicitation.setSit(4);
        }
        
                
        final Coordinate coord = new Coordinate();        
        coord.setLat(0);
        coord.setLng(0);       
        if(position.getSolicitacaoLatitude() != null && position.getSolicitacaoLongitude() != null ){
	        coord.setLat(position.getSolicitacaoLatitude());
	        coord.setLng(position.getSolicitacaoLongitude());
        }
        
        solicitation.setCoord(coord);
        historico.getSolicitations().add(solicitation);
    }

}
