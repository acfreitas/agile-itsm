package br.com.centralit.citcorpore.bpm.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.Tarefa;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.AlcadaProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaRequisicaoPessoal;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.EntrevistaCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoPessoalDao;
import br.com.centralit.citcorpore.rh.integracao.TriagemRequisicaoPessoalDao;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Reflexao;


@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class ExecucaoRequisicaoPessoal extends ExecucaoSolicitacao {

	public boolean preRequisitoEntrevistaGestor() throws Exception{
        RequisicaoPessoalDTO requisicaoDto = recuperaRequisicaoPessoal();
        return requisicaoDto.getPreRequisitoEntrevistaGestor() != null && requisicaoDto.getPreRequisitoEntrevistaGestor().equalsIgnoreCase("S");
	}

    public boolean existeEntrevistaPendenteRH() throws Exception{
    	TriagemRequisicaoPessoalDao triagemDao = new TriagemRequisicaoPessoalDao();
    	setTransacaoDao(triagemDao);
    	Collection<TriagemRequisicaoPessoalDTO> colTriagem = triagemDao.findDisponiveisEntrevistaRH(getSolicitacaoServicoDto().getIdSolicitacaoServico());
    	return colTriagem != null && !colTriagem.isEmpty();
    }

    public boolean existeEntrevistaPendenteGestor() throws Exception{
    	TriagemRequisicaoPessoalDao triagemDao = new TriagemRequisicaoPessoalDao();
    	setTransacaoDao(triagemDao);
    	Collection<TriagemRequisicaoPessoalDTO> colTriagem = triagemDao.findDisponiveisEntrevistaGestor(getSolicitacaoServicoDto().getIdSolicitacaoServico());
    	return colTriagem != null && !colTriagem.isEmpty();
    }

    public void associaItemTrabalhoEntrevistaRH(Tarefa tarefa) throws Exception{
    	TriagemRequisicaoPessoalDao triagemDao = new TriagemRequisicaoPessoalDao();
    	setTransacaoDao(triagemDao);
    	Collection<TriagemRequisicaoPessoalDTO> colTriagem = triagemDao.findDisponiveisEntrevistaRH(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colTriagem != null) {
            for (TriagemRequisicaoPessoalDTO triagemDto : colTriagem) {
            	triagemDto.setIdItemTrabalhoEntrevistaRH(tarefa.getIdItemTrabalho());
            	triagemDao.update(triagemDto);
            }
        }
    }

    public boolean classificaCandidato() throws Exception{
    	RequisicaoPessoalDTO requisicaoDto = recuperaRequisicaoPessoal();
    	Collection<TriagemRequisicaoPessoalDTO> colTriagens = new TriagemRequisicaoPessoalServiceEjb().findByIdSolicitacaoServico(requisicaoDto.getIdSolicitacaoServico());
    	if (colTriagens != null) {
    		EntrevistaCandidatoDao entrevistaDao = new EntrevistaCandidatoDao();
    		for (TriagemRequisicaoPessoalDTO triagemDto : colTriagens) {
				Collection colEntrevistas = entrevistaDao.findFinalizadasByIdTriagemAndResultado(triagemDto.getIdTriagem(), "A");
				if(colEntrevistas != null){
					int x = 0;
				}
			}
    	}

    	return true;
    }

    public void associaItemTrabalhoEntrevistaGestor(Tarefa tarefa) throws Exception{
    	TriagemRequisicaoPessoalDao triagemDao = new TriagemRequisicaoPessoalDao();
    	setTransacaoDao(triagemDao);
    	Collection<TriagemRequisicaoPessoalDTO> colTriagem = triagemDao.findDisponiveisEntrevistaGestor(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colTriagem != null) {
            for (TriagemRequisicaoPessoalDTO triagemDto : colTriagem) {
            	triagemDto.setIdItemTrabalhoEntrevistaGestor(tarefa.getIdItemTrabalho());
            	triagemDao.update(triagemDto);
            }
        }
    }

    public AlcadaDTO recuperaAlcada(RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        return new AlcadaRequisicaoPessoal().determinaAlcada(requisicaoPessoalDto, recuperaCentroCusto(requisicaoPessoalDto), getTransacao());
    }

    public StringBuilder recuperaLoginGestores() throws Exception{
        StringBuilder result = new StringBuilder();
        RequisicaoPessoalDTO requisicaoDto = recuperaRequisicaoPessoal();
        AlcadaDTO alcadaDto = recuperaAlcada(requisicaoDto);
        if (alcadaDto != null && alcadaDto.getColResponsaveis() != null) {
            int i = 0;
            UsuarioDao usuarioDao = new UsuarioDao();
            for (EmpregadoDTO empregadoDto: alcadaDto.getColResponsaveis()) {
                UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
                if (usuarioDto != null) {
                    if (i > 0)
                        result.append(";");
                    result.append(usuarioDto.getLogin());
                    i++;
                }
            }
        }
        if (result.length() == 0)
        	throw new LogicException("Não foi encontrado nenhum gestor para a requisição");
        return result;
    }

    public CentroResultadoDTO recuperaCentroCusto(RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        CentroResultadoDTO centroCustoDto = new CentroResultadoDTO();
        centroCustoDto.setIdCentroResultado(requisicaoPessoalDto.getIdCentroCusto());
        return (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);
    }

    public boolean seVagasNaoPreenchidasEmAndamentoNaoRejeitada(){
    	//boolean vVagasNaoPreenchidas = false;
    	boolean vEmAndamento = false;
    	boolean vNaoRejeitada = false;
    	try {
    		RequisicaoPessoalDTO requisicaoDto = recuperaRequisicaoPessoal();
    		//vVagasNaoPreenchidas = !this.vagasPreenchidas(requisicaoDto);
			vEmAndamento = !this.requisicaoCancelada(requisicaoDto);
			vNaoRejeitada = !this.requisicaoRejeitada(requisicaoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (vEmAndamento)&&(vNaoRejeitada);
    }

    private boolean vagasPreenchidas(RequisicaoPessoalDTO requisicaoDto) throws Exception{
    	int qtdeEntrevistasAprovadas = 0;
    	boolean teste;
    	Collection<TriagemRequisicaoPessoalDTO> colTriagens = new TriagemRequisicaoPessoalServiceEjb().findByIdSolicitacaoServico(requisicaoDto.getIdSolicitacaoServico());
    	if (colTriagens != null) {
    		EntrevistaCandidatoDao entrevistaDao = new EntrevistaCandidatoDao();
    		for (TriagemRequisicaoPessoalDTO triagemDto : colTriagens) {
				Collection colEntrevistas = entrevistaDao.findFinalizadasByIdTriagemAndResultado(triagemDto.getIdTriagem(), "A");
				if ((colEntrevistas != null) && (!colEntrevistas.isEmpty()) && (requisicaoDto.getQtdCandidatosAprovados() != null && requisicaoDto.getQtdCandidatosAprovados() > 0)) {
					qtdeEntrevistasAprovadas += requisicaoDto.getQtdCandidatosAprovados();
				}
			}
    	}
    	teste = qtdeEntrevistasAprovadas > 0 && qtdeEntrevistasAprovadas >= requisicaoDto.getVagas().intValue();
    	return teste;
    }

    public boolean seVagasPreenchidas(){
    	RequisicaoPessoalDTO requisicaoDto;
		try {
			requisicaoDto = recuperaRequisicaoPessoal();
			return vagasPreenchidas(requisicaoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }

    private boolean requisicaoCancelada(RequisicaoPessoalDTO requisicaoDto){
    	return (requisicaoDto!=null)&&(requisicaoDto.getSituacao()!=null) && (requisicaoDto.getSituacao().equals("Cancelada"));
    }

    public boolean seCancelada(){
    	RequisicaoPessoalDTO requisicaoDto;
		try {
			requisicaoDto = recuperaRequisicaoPessoal();
			return requisicaoCancelada(requisicaoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }

    private boolean requisicaoRejeitada(RequisicaoPessoalDTO requisicaoDto) throws Exception{
    	return (requisicaoDto!=null)&&(requisicaoDto.getRejeitada()!=null) && (requisicaoDto.getRejeitada().equalsIgnoreCase("S"));
    }

    public boolean seRequisicaoRejeitada() throws Exception{
    	RequisicaoPessoalDTO requisicaoDto = recuperaRequisicaoPessoal();
    	return this.requisicaoRejeitada(requisicaoDto);
    }

    public RequisicaoPessoalDTO recuperaRequisicaoPessoal() throws Exception{
    	RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
        setTransacaoDao(requisicaoPessoalDao);
        SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
        RequisicaoPessoalDTO requisicaoPessoalDTO = new RequisicaoPessoalDTO();
        requisicaoPessoalDTO.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoPessoalDTO = (RequisicaoPessoalDTO) requisicaoPessoalDao.restore(requisicaoPessoalDTO);
        Reflexao.copyPropertyValues(solicitacaoDto, requisicaoPessoalDTO);
        return requisicaoPessoalDTO;
    }

	@Override
	public InstanciaFluxo inicia() throws Exception {
		return super.inicia();
	}

    @Override
    public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {
        String idGrupo = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_REQ_RH, getTransacao(), null);
        if (idGrupo == null || idGrupo.trim().equals(""))
            throw new Exception("Grupo padrão para atendimento de solicitações de recursos humanos");
        getSolicitacaoServicoDto().setIdGrupoAtual(new Integer(idGrupo));
        return super.inicia(fluxoDto,idFase);
    }

    @Override
    public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
        super.mapObjetoNegocio(map);
    }

    @Override
    public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
        super.executaEvento(eventoFluxoDto);
    }

    public boolean entrevistaAprovada() throws Exception{
    	int qtdCandidatosAprovados = 0;
    	Collection colEntrevistas = new ArrayList<EntrevistaCandidatoDTO>();
        RequisicaoPessoalDTO requisicaoDto = recuperaRequisicaoPessoal();
    	Collection<TriagemRequisicaoPessoalDTO> colTriagens = new TriagemRequisicaoPessoalServiceEjb().findByIdSolicitacaoServico(requisicaoDto.getIdSolicitacaoServico());
    	if (colTriagens != null && colTriagens.size() > 0) {
    		EntrevistaCandidatoDao entrevistaDao = new EntrevistaCandidatoDao();
    		for (TriagemRequisicaoPessoalDTO triagemDto : colTriagens) {
				colEntrevistas = entrevistaDao.findFinalizadasByIdTriagemAndResultado(triagemDto.getIdTriagem(), "A");
				if (colEntrevistas != null && !colEntrevistas.isEmpty()){
					for (Object entrevistaDto : colEntrevistas) {
						qtdCandidatosAprovados += 1;
					}
				}
			}
    	}
    	return qtdCandidatosAprovados > 0;
    }

    @Override
    public boolean permiteAprovacaoAlcada(AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
    	return true;
    }
}
