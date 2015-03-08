package br.com.centralit.citcorpore.bpm.negocio;

import java.util.Map;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoFuncaoDao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

public class ExecucaoRequisicaoFuncao extends ExecucaoSolicitacao {

    @Override
    public InstanciaFluxo inicia() throws Exception {
        return super.inicia();
    }

    @Override
    public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {
        String idGrupo = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_REQ_RH, getTransacao(), null);
        if (idGrupo == null || idGrupo.trim().equals(""))
            throw new Exception("Grupo padrão para atendimento de solicitações de recursos humanos não parametrizado");
        getSolicitacaoServicoDto().setIdGrupoAtual(new Integer(idGrupo));
        return super.inicia(fluxoDto, idFase);
    }

    @Override
    public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
        super.mapObjetoNegocio(map);
    }

    @Override
    public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
        super.executaEvento(eventoFluxoDto);
    }

    public boolean requisicaoAprovada() throws Exception {

        RequisicaoFuncaoDao requisicaoFuncaoDao = new RequisicaoFuncaoDao();
        setTransacaoDao(requisicaoFuncaoDao);
        SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
        RequisicaoFuncaoDTO requisicaoFuncaoDto = new RequisicaoFuncaoDTO();
        requisicaoFuncaoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoFuncaoDto = (RequisicaoFuncaoDTO) requisicaoFuncaoDao.restore(requisicaoFuncaoDto);

        Boolean status = null;
        if (requisicaoFuncaoDto.getRequisicaoValida().equals("N")) {
            status = false;
        } else {
            if (requisicaoFuncaoDto.getRequisicaoValida().equals("S")) {
                status = true;
            }
        }
        return status;
    }

    public boolean descricaoAprovada() throws Exception {

        RequisicaoFuncaoDao requisicaoFuncaoDao = new RequisicaoFuncaoDao();
        setTransacaoDao(requisicaoFuncaoDao);
        SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
        RequisicaoFuncaoDTO requisicaoFuncaoDto = new RequisicaoFuncaoDTO();
        requisicaoFuncaoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoFuncaoDto = (RequisicaoFuncaoDTO) requisicaoFuncaoDao.restore(requisicaoFuncaoDto);

        Boolean status = null;
        if (requisicaoFuncaoDto.getDescricaoValida().equals("N")) {
            status = false;
        } else {
            if (requisicaoFuncaoDto.getDescricaoValida().equals("S")) {
                status = true;
            }
        }
        return status;
    }

}
