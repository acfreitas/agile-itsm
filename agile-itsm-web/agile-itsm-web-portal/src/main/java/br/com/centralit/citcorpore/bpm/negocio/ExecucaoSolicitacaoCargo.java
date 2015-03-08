package br.com.centralit.citcorpore.bpm.negocio;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.integracao.DescricaoCargoDao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

public class ExecucaoSolicitacaoCargo extends ExecucaoSolicitacao {

    public boolean solicitacaoRejeitada() throws Exception {
        DescricaoCargoDTO descricaoCargoDTO = recuperaDescricaoCargo();
        return descricaoCargoDTO.getSituacao() != null && descricaoCargoDTO.getSituacao().equalsIgnoreCase("X");
    }

    public DescricaoCargoDTO recuperaDescricaoCargo() throws Exception {
        DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();
        setTransacaoDao(descricaoCargoDao);
        SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
        DescricaoCargoDTO descricaoCargoDTO = (DescricaoCargoDTO) descricaoCargoDao.findByIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        return descricaoCargoDTO;
    }

    @Override
    public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {
        String idGrupo = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_REQ_RH, getTransacao(), null);
        if (idGrupo == null || idGrupo.trim().equals(""))
            throw new Exception("Grupo padrão para atendimento de solicitações de recursos humanos não parametrizado");
        getSolicitacaoServicoDto().setIdGrupoAtual(new Integer(idGrupo));
        return super.inicia(fluxoDto, idFase);
    }

}
