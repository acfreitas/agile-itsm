package br.com.centralit.citcorpore.negocio.alcada;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.citcorpore.bean.AlcadaProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoFluxoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoNivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoAutoridadeDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoProcessoDTO;
import br.com.centralit.citcorpore.bean.NivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.ParametroDTO;
import br.com.centralit.citcorpore.bean.ProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.ProcessoNivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.bean.SimulacaoAlcadaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorLimiteAprovacaoDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoDao;
import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoFluxoDao;
import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoProcessoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoNivelAutoridadeDao;
import br.com.centralit.citcorpore.integracao.LimiteAprovacaoAutoridadeDao;
import br.com.centralit.citcorpore.integracao.LimiteAprovacaoDao;
import br.com.centralit.citcorpore.integracao.LimiteAprovacaoProcessoDao;
import br.com.centralit.citcorpore.integracao.NivelAutoridadeDao;
import br.com.centralit.citcorpore.integracao.ParametroDao;
import br.com.centralit.citcorpore.integracao.ProcessoNegocioDao;
import br.com.centralit.citcorpore.integracao.ProcessoNivelAutoridadeDao;
import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoDao;
import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoProcessoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.integracao.ValorLimiteAprovacaoDao;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.MotivoRejeicaoAlcada;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"unchecked","rawtypes"})
public class AlcadaProcessoNegocio {

	TransactionControler transacao = null;

	protected CrudDAO atribuiTransacaoDao(CrudDAO dao) throws Exception {
		if (this.transacao != null)
			dao.setTransactionControler(this.transacao);
		return dao;
	}

    private static String getNomeClasse() throws Exception {
    	ParametroDTO parametroDto = new ParametroDao().getValue("ALCADA", "NOME_CLASSE_ALCADA", new Integer(1));
    	if (parametroDto != null && parametroDto.getValor() != null && !parametroDto.getValor().trim().equals(""))
    		return parametroDto.getValor();
    	return "br.com.centralit.citcorpore.negocio.alcada.AlcadaProcessoNegocio";
    }

	private HashMap<String, ProcessoNegocioDTO> mapProcessosNegocio = null;
	private HashMap<String, NivelAutoridadeDTO> mapAutoridades = null;

	public static AlcadaProcessoNegocio getInstance() throws Exception {
		return (AlcadaProcessoNegocio) Class.forName(getNomeClasse()).newInstance();
	}

    protected EmpregadoDTO recuperaEmpregado(Integer idEmpregado) throws Exception {
        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(idEmpregado);
        return (EmpregadoDTO) this.atribuiTransacaoDao(new EmpregadoDao()).restore(empregadoDto);
    }

    protected CentroResultadoDTO recuperaCentroResultado(Integer idCentroResultado) throws Exception {
        CentroResultadoDTO centroResultadoDto = new CentroResultadoDTO();
        centroResultadoDto.setIdCentroResultado(idCentroResultado);
        return (CentroResultadoDTO) this.atribuiTransacaoDao(new CentroResultadoDao()).restore(centroResultadoDto);
    }

    protected ProcessoNegocioDTO recuperaProcessoNegocio(Integer idProcessoNegocio) throws Exception {
        ProcessoNegocioDTO processoNegocioDto = new ProcessoNegocioDTO();
        processoNegocioDto.setIdProcessoNegocio(idProcessoNegocio);
        return (ProcessoNegocioDTO) this.atribuiTransacaoDao(new ProcessoNegocioDao()).restore(processoNegocioDto);
    }

    protected FluxoDTO recuperaFluxo(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		return new SolicitacaoServicoServiceEjb().recuperaFluxo(solicitacaoServicoDto);
    }

    protected boolean isSimulacao(SolicitacaoServicoDTO solicitacaoServicoDto) {
    	return SimulacaoAlcadaDTO.class.isInstance(solicitacaoServicoDto);
    }

	public ProcessoNegocioDTO recuperaProcessoNegocio(FluxoDTO fluxoDto) throws Exception {
		ProcessoNegocioDTO result = null;
		if (fluxoDto != null && fluxoDto.getIdProcessoNegocio() != null)
			result = recuperaProcessoNegocio(fluxoDto.getIdProcessoNegocio());
		if (result != null)
			recuperaAutoridadesProcesso(result, (ProcessoNivelAutoridadeDao) this.atribuiTransacaoDao(new ProcessoNivelAutoridadeDao()));
		fluxoDto.setProcessoNegocioDto(result);
		return result;
	}

	public ExecucaoSolicitacao recuperaExecucaoSolicitacao(FluxoDTO fluxoDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = (ExecucaoSolicitacao) Class.forName(fluxoDto.getNomeClasseFluxo()).newInstance();
		execucaoSolicitacao.setTransacao(transacao);
		execucaoSolicitacao.setObjetoNegocioDto(solicitacaoServicoDto);
		ExecucaoSolicitacaoDao execucaoSolicitacaoDao = new ExecucaoSolicitacaoDao();
		atribuiTransacaoDao(execucaoSolicitacaoDao);
		execucaoSolicitacao.setExecucaoSolicitacaoDto(execucaoSolicitacaoDao.findBySolicitacaoServico(solicitacaoServicoDto));
		return execucaoSolicitacao;
	}

	protected void recuperaLimitesAprovacao(ProcessoNegocioDTO processoNegocioDto) throws Exception {
		LimiteAprovacaoProcessoDao limiteAprovacaoProcessoDao = new LimiteAprovacaoProcessoDao();
		atribuiTransacaoDao(limiteAprovacaoProcessoDao);
		Collection<LimiteAprovacaoDTO> colLimitesAprovacao = new ArrayList();
		// recupera os limites de aprovação associados ao processo
		Collection<LimiteAprovacaoProcessoDTO> colLimiteAprovacaoProcesso = limiteAprovacaoProcessoDao.findByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
		if (colLimiteAprovacaoProcesso != null) {
			LimiteAprovacaoDao limiteAprovacaoDao = new LimiteAprovacaoDao();
			atribuiTransacaoDao(limiteAprovacaoDao);
			ValorLimiteAprovacaoDao valorLimiteAprovacaoDao = new ValorLimiteAprovacaoDao();
			atribuiTransacaoDao(valorLimiteAprovacaoDao);
			for (LimiteAprovacaoProcessoDTO limiteAprovacaoProcessoDto : colLimiteAprovacaoProcesso) {
				LimiteAprovacaoDTO limiteAprovacaoDto = new LimiteAprovacaoDTO();
				limiteAprovacaoDto.setIdLimiteAprovacao(limiteAprovacaoProcessoDto.getIdLimiteAprovacao());
				limiteAprovacaoDto = (LimiteAprovacaoDTO) limiteAprovacaoDao.restore(limiteAprovacaoDto);
				// recupera os valores de cada limite
				limiteAprovacaoDto.setColValores(valorLimiteAprovacaoDao.findByIdLimiteAprovacao(limiteAprovacaoProcessoDto.getIdLimiteAprovacao()));
				Collection<LimiteAprovacaoProcessoDTO> colLimiteAprovacao = limiteAprovacaoProcessoDao.findByIdLimiteAprovacao(limiteAprovacaoProcessoDto.getIdLimiteAprovacao());
				Collection<ProcessoNegocioDTO> colProcessos = new ArrayList();
				colProcessos.add(processoNegocioDto);
				if (colLimiteAprovacao != null) {
					// recupera os outros processos associados ao limite
					for (LimiteAprovacaoProcessoDTO limiteDto : colLimiteAprovacao) {
						if (limiteDto.getIdProcessoNegocio().intValue() == processoNegocioDto.getIdProcessoNegocio().intValue())
							continue;
				        ProcessoNegocioDTO processoDto = getHashMapProcessosNegocio().get(""+limiteDto.getIdProcessoNegocio());
				        if (processoDto != null)
				        	colProcessos.add(processoDto);
					}
				}
				limiteAprovacaoDto.setColProcessos(colProcessos);
				colLimitesAprovacao.add(limiteAprovacaoDto);
			}
		}
		processoNegocioDto.setColLimitesAprovacao(colLimitesAprovacao);
	}

	protected void associaLimiteAutoridadeEValores(ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, CentroResultadoDTO centroResultadoDto) throws Exception {
		if (processoNegocioDto.getColAutoridades() == null || processoNegocioDto.getColAutoridades().isEmpty())
			return;

		// recupera os limites de aprovação do processo
		// para cada limite, recupera os outros processos associados
		recuperaLimitesAprovacao(processoNegocioDto);
		Collection<LimiteAprovacaoDTO> colLimites = processoNegocioDto.getColLimitesAprovacao();
		if (colLimites == null || colLimites.isEmpty())
			return;

		// cria hashMap com os limites
		HashMap<String, LimiteAprovacaoDTO> mapLimites = new HashMap();
		for (LimiteAprovacaoDTO limiteAprovacaoDto : colLimites) {
			mapLimites.put(""+limiteAprovacaoDto.getIdLimiteAprovacao(), limiteAprovacaoDto);
		}

		LimiteAprovacaoAutoridadeDao limiteAprovacaoAutoridadeDao = new LimiteAprovacaoAutoridadeDao();
		atribuiTransacaoDao(limiteAprovacaoAutoridadeDao);
		for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : processoNegocioDto.getColAutoridades()) {
			// recupera os limites de aprovação de cada autoridade do processo de negócio
			Collection<LimiteAprovacaoAutoridadeDTO> colLimiteAprovacaoAutoridade = limiteAprovacaoAutoridadeDao.findByIdNivelAutoridade(processoNivelAutoridadeDto.getIdNivelAutoridade());
			if (colLimiteAprovacaoAutoridade != null) {
				for (LimiteAprovacaoAutoridadeDTO limiteAprovacaoAutoridadeDto : colLimiteAprovacaoAutoridade) {
					LimiteAprovacaoDTO limiteAprovacaoDto = mapLimites.get(""+limiteAprovacaoAutoridadeDto.getIdLimiteAprovacao());
					if (limiteAprovacaoDto != null) {
						// associa o limite de aprovação da autoridade, considerando que uma autoridade só pode ter um limite para determinado processo
						limiteAprovacaoDto.setValido(true);
						processoNivelAutoridadeDto.setLimiteAprovacaoDto(mapLimites.get(""+limiteAprovacaoAutoridadeDto.getIdLimiteAprovacao()));
						break;
					}
				}
			}
		}

		boolean bAtendimentoCliente = false;
		double valorIndividual = 0.0;
		if (isSimulacao(execucaoSolicitacao.getSolicitacaoServicoDto())) {
			bAtendimentoCliente = ((SimulacaoAlcadaDTO) execucaoSolicitacao.getSolicitacaoServicoDto()).getFinalidade().equalsIgnoreCase("C");
			valorIndividual = ((SimulacaoAlcadaDTO) execucaoSolicitacao.getSolicitacaoServicoDto()).getValor();
		}else{
			bAtendimentoCliente = execucaoSolicitacao.isAtendimentoCliente(execucaoSolicitacao.getSolicitacaoServicoDto());
			valorIndividual = execucaoSolicitacao.calculaValorAprovado(execucaoSolicitacao.getSolicitacaoServicoDto(), this.transacao);
		}
		recuperaValores(colLimites, execucaoSolicitacao, centroResultadoDto, bAtendimentoCliente);

		for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : processoNegocioDto.getColAutoridades()) {
			if (processoNivelAutoridadeDto.getNivelAutoridadeDto() == null)
				continue;
			processoNivelAutoridadeDto.getNivelAutoridadeDto().setAlcadaRejeitada(false);
			LimiteAprovacaoDTO limiteAprovacaoDto = processoNivelAutoridadeDto.getLimiteAprovacaoDto();

			// ignora os limites que não são por faixa de valor
			if (limiteAprovacaoDto == null || !limiteAprovacaoDto.getTipoLimitePorValor().equalsIgnoreCase("V") || limiteAprovacaoDto.getColValores() == null)
				continue;

			for (ValorLimiteAprovacaoDTO valorDto : limiteAprovacaoDto.getColValores()) {
				if (!bAtendimentoCliente && valorDto.getTipoUtilizacao().equalsIgnoreCase("C"))
					continue;
				if (bAtendimentoCliente && !valorDto.getTipoUtilizacao().equalsIgnoreCase("C"))
					continue;
				double valorRef = 0.0;
				if (valorDto.getTipoLimite().equalsIgnoreCase("I")) {
					valorRef = valorIndividual;
				}else if (limiteAprovacaoDto.getAbrangenciaCentroResultado().equalsIgnoreCase("R")) {
					if (valorDto.getTipoLimite().equalsIgnoreCase("M")) {
						if (bAtendimentoCliente)
							valorRef = limiteAprovacaoDto.getValorMensalAtendCliente() - valorIndividual;
						else
							valorRef = limiteAprovacaoDto.getValorMensalUsoInterno() - valorIndividual;
					}else if (valorDto.getTipoLimite().equalsIgnoreCase("A")) {
						if (bAtendimentoCliente)
							valorRef = limiteAprovacaoDto.getValorAnualAtendCliente() - valorIndividual;
						else
							valorRef = limiteAprovacaoDto.getValorAnualUsoInterno() - valorIndividual;
					}
				}
				// invalida autoridades fora do valor limite
				if (valorRef > valorDto.getValorLimite()) {
					processoNivelAutoridadeDto.getNivelAutoridadeDto().setAlcadaRejeitada(true);
					processoNivelAutoridadeDto.getNivelAutoridadeDto().setMotivoRejeicao(MotivoRejeicaoAlcada.LimiteValor);
					break;
				}
			}
		}
	}

	protected void recuperaValores(Collection<LimiteAprovacaoDTO> colLimites, ExecucaoSolicitacao execucaoSolicitacaoProcesso, CentroResultadoDTO centroResultadoDto, boolean bAtendimentoCliente) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = execucaoSolicitacaoProcesso.getSolicitacaoServicoDto();
		Date dataAux = UtilDatas.getDataAtual();
        int mes = UtilDatas.getMonth(dataAux);
        int ano = UtilDatas.getYear(dataAux);

		HashMap<String, ExecucaoSolicitacao> mapExecucaoSolicitacao = new HashMap();
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
		atribuiTransacaoDao(tipoFluxoDao);
		for (LimiteAprovacaoDTO limiteAprovacaoDto : colLimites) {
			// ignora os limites não associados a alguma autoridade
			if (!limiteAprovacaoDto.isValido())
				continue;

			double valorMensalUsoInterno = 0.0;
			double valorAnualUsoInterno = 0.0;
			double valorMensalAtendCliente = 0.0;
			double valorAnualAtendCliente = 0.0;
			if (limiteAprovacaoDto.getColProcessos() != null) {
				for (ProcessoNegocioDTO processoNegocioDto : limiteAprovacaoDto.getColProcessos()) {
					// recupera os fluxos de cada processo associado ao limite de aprovação
					Collection<TipoFluxoDTO> colFluxos = tipoFluxoDao.findByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
					if (colFluxos != null) {
						for (TipoFluxoDTO tipoFluxoDto : colFluxos) {
							if (tipoFluxoDto.getNomeClasseFluxo() == null || tipoFluxoDto.getNomeClasseFluxo().trim().equals(""))
								tipoFluxoDto.setNomeClasseFluxo(ExecucaoSolicitacao.class.getName());
							ExecucaoSolicitacao execucaoSolicitacao = mapExecucaoSolicitacao.get(tipoFluxoDto.getNomeClasseFluxo());
							if (execucaoSolicitacao == null) {
								// calcula os valores mensais e anuais
								try {
									execucaoSolicitacao = (ExecucaoSolicitacao) Class.forName(tipoFluxoDto.getNomeClasseFluxo()).newInstance();
									execucaoSolicitacao.setObjetoNegocioDto(solicitacaoServicoDto);
									mapExecucaoSolicitacao.put(tipoFluxoDto.getNomeClasseFluxo(), execucaoSolicitacao);
									if (isSimulacao(solicitacaoServicoDto)) {
										double valor = 0.0;
										if (execucaoSolicitacaoProcesso.getClass().getName().equals(execucaoSolicitacao.getClass().getName())) {
											valor = ((SimulacaoAlcadaDTO) solicitacaoServicoDto).getValorMensal();
										}else{
											valor = ((SimulacaoAlcadaDTO) solicitacaoServicoDto).getValorOutrasAlcadas();
										}
										if (bAtendimentoCliente) {
											execucaoSolicitacao.setValorAnualAtendCliente(valor);
											execucaoSolicitacao.setValorMensalAtendCliente(valor);
										}else{
											execucaoSolicitacao.setValorAnualUsoInterno(valor);
											execucaoSolicitacao.setValorMensalUsoInterno(valor);
										}
									}else{
										execucaoSolicitacao.calculaValorAprovadoMensal(centroResultadoDto, mes, ano, this.transacao);
										execucaoSolicitacao.calculaValorAprovadoAnual(centroResultadoDto, mes, this.transacao);
									}
								} catch (Exception e) {
								}
							}
							if (execucaoSolicitacao != null) {
								valorMensalUsoInterno += execucaoSolicitacao.getValorMensalUsoInterno();
								valorMensalAtendCliente += execucaoSolicitacao.getValorMensalAtendCliente();
								valorAnualUsoInterno += execucaoSolicitacao.getValorAnualUsoInterno();
								valorAnualAtendCliente += execucaoSolicitacao.getValorAnualAtendCliente();
							}
						}
					}
				}
			}
			limiteAprovacaoDto.setValorMensalUsoInterno(valorMensalUsoInterno);
			limiteAprovacaoDto.setValorAnualUsoInterno(valorAnualUsoInterno);
			limiteAprovacaoDto.setValorMensalAtendCliente(valorMensalAtendCliente);
			limiteAprovacaoDto.setValorAnualAtendCliente(valorAnualAtendCliente);
		}
	}

    protected void recuperaAutoridadesProcesso(ProcessoNegocioDTO processoNegocioDto, ProcessoNivelAutoridadeDao processoNivelAutoridadeDao) throws Exception {
		Collection<ProcessoNivelAutoridadeDTO> colAutoridades = processoNivelAutoridadeDao.findByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
		for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : colAutoridades) {
			NivelAutoridadeDTO nivelDto = getHashMapNivelAutoridade().get(""+processoNivelAutoridadeDto.getIdNivelAutoridade());
			if (nivelDto != null) {
				processoNivelAutoridadeDto.setNivelAutoridadeDto(nivelDto);
				processoNivelAutoridadeDto.setHierarquia(nivelDto.getHierarquia());
			}
		}
		Collections.sort((List) colAutoridades, new ObjectSimpleComparator("getHierarquia", ObjectSimpleComparator.ASC));
		processoNegocioDto.setColAutoridades(colAutoridades);
    }

    protected HashMap<String, GrupoEmpregadoDTO> getHashMapGruposEmpregado(EmpregadoDTO empregadoDto) throws Exception {
		HashMap<String, GrupoEmpregadoDTO> result = new HashMap();
		GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
		atribuiTransacaoDao(grupoEmpregadoDao);
        Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findAtivosByIdEmpregado(empregadoDto.getIdEmpregado());
		if (colGrupos != null) {
			for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
				result.put(""+grupoEmpregadoDto.getIdGrupo(), grupoEmpregadoDto);
			}
		}
		return result;
	}

    protected AlcadaProcessoNegocioDTO getAlcadaProcessoNegocio(CentroResultadoDTO centroResultadoDto, EmpregadoDTO empregadoDto, HashMap<String, GrupoEmpregadoDTO> mapGruposEmpregado, ResponsavelCentroResultadoProcessoDao responsavelCentroResultadoProcessoDao, ProcessoNegocioDTO processoNegocioRefDto) throws Exception {
    	AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto = null;
		List<ProcessoNegocioDTO> processosNegocio = new ArrayList();
		Collection<ResponsavelCentroResultadoProcessoDTO> colResponsavelProcesso = responsavelCentroResultadoProcessoDao.findByIdCentroResultadoAndIdResponsavel(centroResultadoDto.getIdCentroResultado(), empregadoDto.getIdEmpregado());
		if (colResponsavelProcesso != null) {
			for (ResponsavelCentroResultadoProcessoDTO responsavelCentroResultadoProcessoDto : colResponsavelProcesso) {
				if (processoNegocioRefDto != null && processoNegocioRefDto.getIdProcessoNegocio() != null && processoNegocioRefDto.getIdProcessoNegocio().intValue() != responsavelCentroResultadoProcessoDto.getIdProcessoNegocio().intValue())
					continue;
				ProcessoNegocioDTO processoNegocioDto = getHashMapProcessosNegocio().get(""+responsavelCentroResultadoProcessoDto.getIdProcessoNegocio());
				if (processoNegocioDto != null && processoNegocioDto.getColAutoridades() != null) {
					for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : processoNegocioDto.getColAutoridades()) {
						if (processoNivelAutoridadeDto.getNivelAutoridadeDto() != null && processoNivelAutoridadeDto.getNivelAutoridadeDto().getColGrupos() != null) {
							for (GrupoNivelAutoridadeDTO grupoNivelAutoridadeDto : processoNivelAutoridadeDto.getNivelAutoridadeDto().getColGrupos()) {
								if (mapGruposEmpregado.get(""+grupoNivelAutoridadeDto.getIdGrupo()) != null) {
									ProcessoNegocioDTO processoDto = new ProcessoNegocioDTO();
									Reflexao.copyPropertyValues(processoNegocioDto, processoDto);
									processoDto.setNivelAutoridadeDto(getHashMapNivelAutoridade().get(""+processoNivelAutoridadeDto.getIdNivelAutoridade()));
									processosNegocio.add(processoDto);
								}
							}
						}
					}
				}
			}
			if (processosNegocio.size() > 0) {
				alcadaProcessoNegocioDto = new AlcadaProcessoNegocioDTO();
				alcadaProcessoNegocioDto.setCentroResultadoDto(centroResultadoDto);
				alcadaProcessoNegocioDto.setEmpregadoDto(empregadoDto);
				alcadaProcessoNegocioDto.setProcessosNegocio(processosNegocio);
				alcadaProcessoNegocioDto.setMapGruposEmpregado(mapGruposEmpregado);
			}
		}
		return alcadaProcessoNegocioDto;
	}

	protected HashMap<String, ProcessoNegocioDTO> getHashMapProcessosNegocio() throws Exception {
		if (mapProcessosNegocio == null) {
			mapProcessosNegocio = new HashMap();
			Collection<ProcessoNegocioDTO> colProcessos = this.atribuiTransacaoDao(new ProcessoNegocioDao()).list();
			if (colProcessos != null) {
				ProcessoNivelAutoridadeDao processoNivelAutoridadeDao = new ProcessoNivelAutoridadeDao();
				atribuiTransacaoDao(processoNivelAutoridadeDao);
				for (ProcessoNegocioDTO processoNegocioDto : colProcessos) {
					recuperaAutoridadesProcesso(processoNegocioDto, processoNivelAutoridadeDao);
					mapProcessosNegocio.put(""+processoNegocioDto.getIdProcessoNegocio(), processoNegocioDto);
				}
			}
		}
		return mapProcessosNegocio;
	}

	protected HashMap<String, NivelAutoridadeDTO> getHashMapNivelAutoridade() throws Exception {
		if (mapAutoridades == null) {
			mapAutoridades = new HashMap();
			Collection<NivelAutoridadeDTO> colAutoridades = this.atribuiTransacaoDao(new NivelAutoridadeDao()).list();
			if (colAutoridades != null) {
				GrupoNivelAutoridadeDao grupoNivelAutoridadeDao = new GrupoNivelAutoridadeDao();
				atribuiTransacaoDao(grupoNivelAutoridadeDao);
				for (NivelAutoridadeDTO nivelDto : colAutoridades) {
					nivelDto.setColGrupos(grupoNivelAutoridadeDao.findByIdNivelAutoridade(nivelDto.getIdNivelAutoridade()));
					mapAutoridades.put(""+nivelDto.getIdNivelAutoridade(), nivelDto);
				}
			}
		}
		return mapAutoridades;
	}

	public List<AlcadaProcessoNegocioDTO> getAlcadasCentroResultado(CentroResultadoDTO centroResultadoDto, ProcessoNegocioDTO processoNegocioDto) throws Exception {
		List<AlcadaProcessoNegocioDTO> result = new ArrayList();

		ResponsavelCentroResultadoDao responsavelCentroResultadoDao = new ResponsavelCentroResultadoDao();
		atribuiTransacaoDao(responsavelCentroResultadoDao);
		Collection<ResponsavelCentroResultadoDTO> colCentrosResultado = responsavelCentroResultadoDao.findByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
		if (colCentrosResultado != null) {
			EmpregadoDao empregadoDao = new EmpregadoDao();
			atribuiTransacaoDao(empregadoDao);
			ResponsavelCentroResultadoProcessoDao responsavelCentroResultadoProcessoDao = new ResponsavelCentroResultadoProcessoDao();

			for (ResponsavelCentroResultadoDTO responsavelCentroResultadoDto : colCentrosResultado) {
				EmpregadoDTO empregadoDto = new EmpregadoDTO();
				empregadoDto.setIdEmpregado(responsavelCentroResultadoDto.getIdResponsavel());
				empregadoDto = (EmpregadoDTO) empregadoDao.restore(empregadoDto);
				HashMap<String, GrupoEmpregadoDTO> mapGruposEmpregado = getHashMapGruposEmpregado(empregadoDto);

				AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto = getAlcadaProcessoNegocio(centroResultadoDto, empregadoDto, mapGruposEmpregado, responsavelCentroResultadoProcessoDao, processoNegocioDto);
				if (alcadaProcessoNegocioDto != null)
					result.add(alcadaProcessoNegocioDto);
			}
		}

		return result;
	}

	public List<AlcadaProcessoNegocioDTO> getAlcadasEmpregado(EmpregadoDTO empregadoDto, ProcessoNegocioDTO processoNegocioDto) throws Exception {
		List<AlcadaProcessoNegocioDTO> result = new ArrayList();

		ResponsavelCentroResultadoDao responsavelCentroResultadoDao = new ResponsavelCentroResultadoDao();
		atribuiTransacaoDao(responsavelCentroResultadoDao);
		Collection<ResponsavelCentroResultadoDTO> colCentrosResultado = responsavelCentroResultadoDao.findByIdResponsavel(empregadoDto.getIdEmpregado());
		if (colCentrosResultado != null) {
			CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
			atribuiTransacaoDao(centroResultadoDao);
			ResponsavelCentroResultadoProcessoDao responsavelCentroResultadoProcessoDao = new ResponsavelCentroResultadoProcessoDao();
			atribuiTransacaoDao(responsavelCentroResultadoProcessoDao);
			HashMap<String, GrupoEmpregadoDTO> mapGruposEmpregado = getHashMapGruposEmpregado(empregadoDto);

			for (ResponsavelCentroResultadoDTO responsavelCentroResultadoDto : colCentrosResultado) {
				CentroResultadoDTO centroResultadoDto = new CentroResultadoDTO();
				centroResultadoDto.setIdCentroResultado(responsavelCentroResultadoDto.getIdCentroResultado());
				centroResultadoDto = (CentroResultadoDTO) centroResultadoDao.restore(centroResultadoDto);

				AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto = getAlcadaProcessoNegocio(centroResultadoDto, empregadoDto, mapGruposEmpregado, responsavelCentroResultadoProcessoDao, processoNegocioDto);
				if (alcadaProcessoNegocioDto != null)
					result.add(alcadaProcessoNegocioDto);
			}
		}

		return result;
	}

	public List<AlcadaProcessoNegocioDTO> getAlcadasNivelSuperior(ProcessoNegocioDTO processoNegocioDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		List<AlcadaProcessoNegocioDTO> result = new ArrayList();
		if (processoNegocioDto.getColLimitesAprovacao() != null) {
			Collection<NivelAutoridadeDTO> colAutoridades = new ArrayList();
			LimiteAprovacaoAutoridadeDao limiteAprovacaoAutoridadeDao = new LimiteAprovacaoAutoridadeDao();
			atribuiTransacaoDao(limiteAprovacaoAutoridadeDao);
			for (LimiteAprovacaoDTO limiteAprovacaoDto : processoNegocioDto.getColLimitesAprovacao()) {
				if (limiteAprovacaoDto.getAbrangenciaCentroResultado().equalsIgnoreCase("R"))
					continue;
				if (limiteAprovacaoDto.getTipoLimitePorValor().equalsIgnoreCase("V"))
					continue;
				Collection<LimiteAprovacaoAutoridadeDTO> colLimiteAprovacaoAutoridade = limiteAprovacaoAutoridadeDao.findByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
				if (colLimiteAprovacaoAutoridade != null) {
					for (LimiteAprovacaoAutoridadeDTO limiteAprovacaoAutoridadeDto : colLimiteAprovacaoAutoridade) {
						colAutoridades.add(getHashMapNivelAutoridade().get(""+limiteAprovacaoAutoridadeDto.getIdNivelAutoridade()));
					}
				}
			}
			HashMap<String, EmpregadoDTO> mapEmpregados = new HashMap();
			if (colAlcadas != null) {
	 			for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
	 				mapEmpregados.put(""+alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado(), alcadaProcessoNegocioDto.getEmpregadoDto());
				}
			}
			GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
			atribuiTransacaoDao(grupoEmpregadoDao);
			for (NivelAutoridadeDTO nivelAutoridadeDto : colAutoridades) {
				for (GrupoNivelAutoridadeDTO grupoNivelAutoridadeDto : nivelAutoridadeDto.getColGrupos()) {
					Collection<GrupoEmpregadoDTO> colGrupoEmpregado = grupoEmpregadoDao.findByIdGrupo(grupoNivelAutoridadeDto.getIdGrupo());
					if (colGrupoEmpregado != null) {
						for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupoEmpregado) {
							if (mapEmpregados.get(""+grupoEmpregadoDto.getIdEmpregado()) != null)
								continue;
							EmpregadoDTO empregadoDto = recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
							mapEmpregados.put(""+grupoEmpregadoDto.getIdEmpregado(), empregadoDto);

							List<ProcessoNegocioDTO> colProcessos = new ArrayList();
							ProcessoNegocioDTO procDto = new ProcessoNegocioDTO();
							Reflexao.copyPropertyValues(processoNegocioDto, procDto);
							procDto.setNivelAutoridadeDto(nivelAutoridadeDto);
							colProcessos.add(procDto);
							AlcadaProcessoNegocioDTO alcadaDto = new AlcadaProcessoNegocioDTO();
							alcadaDto.setEmpregadoDto(empregadoDto);
							alcadaDto.setProcessosNegocio(colProcessos);
							result.add(alcadaDto);
						}
					}
				}
			}
		}
		return result;
	}

	public ProcessoNegocioDTO recuperaProcessoNegocio(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		ProcessoNegocioDTO result = null;
		FluxoDTO fluxoDto = new SolicitacaoServicoServiceEjb().recuperaFluxo(solicitacaoServicoDto);
		if (fluxoDto != null && fluxoDto.getIdProcessoNegocio() != null)
			result = recuperaProcessoNegocio(fluxoDto.getIdProcessoNegocio());
		if (result != null)
			recuperaAutoridadesProcesso(result, (ProcessoNivelAutoridadeDao) atribuiTransacaoDao(new ProcessoNivelAutoridadeDao()));
		return result;
	}

	protected void validaAlcadas(FluxoDTO fluxoDto, ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, SolicitacaoServicoDTO solicitacaoServicoDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		validaAutoridades(fluxoDto, processoNegocioDto, execucaoSolicitacao, solicitacaoServicoDto, colAlcadas);
		validaSolicitante(fluxoDto, processoNegocioDto, execucaoSolicitacao, solicitacaoServicoDto, colAlcadas);
		validaAprovador(fluxoDto, processoNegocioDto, execucaoSolicitacao, solicitacaoServicoDto, colAlcadas);
		validaHierarquia(fluxoDto, processoNegocioDto, execucaoSolicitacao, solicitacaoServicoDto, colAlcadas);
		validaUsuario(fluxoDto, processoNegocioDto, execucaoSolicitacao, solicitacaoServicoDto, colAlcadas);
	}

	protected void validaHierarquia(FluxoDTO fluxoDto, ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, SolicitacaoServicoDTO solicitacaoServicoDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		if (processoNegocioDto.getPermiteAprovacaoNivelInferior() == null || processoNegocioDto.getPermiteAprovacaoNivelInferior().equalsIgnoreCase("S"))
			return;
		int hierarquia = 0;
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
			if (alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado().intValue() != solicitacaoServicoDto.getIdSolicitante().intValue())
				continue;
			NivelAutoridadeDTO nivelAutoridadeDto = alcadaProcessoNegocioDto.getProcessosNegocio().get(0).getNivelAutoridadeDto();
			hierarquia = nivelAutoridadeDto.getHierarquia().intValue();
			break;
		}
		if (hierarquia == 0 )
			return;
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
			NivelAutoridadeDTO nivelAutoridadeDto = alcadaProcessoNegocioDto.getProcessosNegocio().get(0).getNivelAutoridadeDto();
			if (nivelAutoridadeDto.getHierarquia().intValue() > hierarquia) {
				alcadaProcessoNegocioDto.setMotivoRejeicao(MotivoRejeicaoAlcada.HierarquiaAutoridade);
				alcadaProcessoNegocioDto.setAlcadaRejeitada(true);
				continue;
			}
		}
	}

	protected void validaSolicitante(FluxoDTO fluxoDto, ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, SolicitacaoServicoDTO solicitacaoServicoDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
			NivelAutoridadeDTO nivelAutoridadeDto = alcadaProcessoNegocioDto.getProcessosNegocio().get(0).getNivelAutoridadeDto();
			for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : processoNegocioDto.getColAutoridades()) {
				if (processoNivelAutoridadeDto.getIdNivelAutoridade().intValue() != nivelAutoridadeDto.getIdNivelAutoridade().intValue())
					continue;
				if (processoNivelAutoridadeDto.getPermiteAprovacaoPropria().equals("N") && alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado().intValue() == solicitacaoServicoDto.getIdSolicitante().intValue()) {
					alcadaProcessoNegocioDto.setMotivoRejeicao(MotivoRejeicaoAlcada.PermissaoAutoridade);
					alcadaProcessoNegocioDto.setAlcadaRejeitada(true);
					break;
				}
			}
		}
	}

	protected void validaUsuario(FluxoDTO fluxoDto, ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, SolicitacaoServicoDTO solicitacaoServicoDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
        UsuarioDao usuarioDao = new UsuarioDao();
        atribuiTransacaoDao(usuarioDao);
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
            UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado());
            if (usuarioDto == null) {
				alcadaProcessoNegocioDto.setAlcadaRejeitada(true);
				alcadaProcessoNegocioDto.setMotivoRejeicao(MotivoRejeicaoAlcada.UsuarioNaoExiste);
				continue;
			}
		}
	}

	protected void validaAutoridades(FluxoDTO fluxoDto, ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, SolicitacaoServicoDTO solicitacaoServicoDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
			NivelAutoridadeDTO nivelAutoridadeDto = alcadaProcessoNegocioDto.getProcessosNegocio().get(0).getNivelAutoridadeDto();
			for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : processoNegocioDto.getColAutoridades()) {
				if (processoNivelAutoridadeDto.getIdNivelAutoridade().intValue() != nivelAutoridadeDto.getIdNivelAutoridade().intValue())
					continue;
				if (processoNivelAutoridadeDto.getNivelAutoridadeDto().isAlcadaRejeitada()) {
					alcadaProcessoNegocioDto.setMotivoRejeicao(processoNivelAutoridadeDto.getNivelAutoridadeDto().getMotivoRejeicao());
					alcadaProcessoNegocioDto.setAlcadaRejeitada(true);
					break;
				}
			}
		}
	}

	protected void validaAprovador(FluxoDTO fluxoDto, ProcessoNegocioDTO processoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao, SolicitacaoServicoDTO solicitacaoServicoDto, List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
			if (!execucaoSolicitacao.permiteAprovacaoAlcada(alcadaProcessoNegocioDto, solicitacaoServicoDto)) {
				alcadaProcessoNegocioDto.setAlcadaRejeitada(true);
				alcadaProcessoNegocioDto.setMotivoRejeicao(MotivoRejeicaoAlcada.RegrasProcesso);
				continue;
			}
		}
	}


	protected Collection<AlcadaProcessoNegocioDTO> recuperaDelegacoes(AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto, ExecucaoSolicitacao execucaoSolicitacao) throws Exception {
		Collection<AlcadaProcessoNegocioDTO> colAlcadasDelgacoes = new ArrayList();
		DelegacaoCentroResultadoDao delegacaoCentroResultadoDao = new DelegacaoCentroResultadoDao();
		atribuiTransacaoDao(delegacaoCentroResultadoDao);
		Collection<DelegacaoCentroResultadoDTO> colDelegCentroResultado = delegacaoCentroResultadoDao.findByIdResponsavelAndIdCentroResultado(alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado(), alcadaProcessoNegocioDto.getCentroResultadoDto().getIdCentroResultado());
		if (colDelegCentroResultado == null)
			return colAlcadasDelgacoes;
		Collection<EmpregadoDTO> colDelegacoes = new ArrayList();
		DelegacaoCentroResultadoProcessoDao delegacaoCentroResultadoProcessoDao = new DelegacaoCentroResultadoProcessoDao();
		atribuiTransacaoDao(delegacaoCentroResultadoProcessoDao);
		DelegacaoCentroResultadoFluxoDao delegacaoCentroResultadoFluxoDao = new DelegacaoCentroResultadoFluxoDao();
		atribuiTransacaoDao(delegacaoCentroResultadoFluxoDao);
		for (DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto : colDelegCentroResultado) {
			if (delegacaoCentroResultadoDto.getDataFim().compareTo(UtilDatas.getDataAtual()) < 0)
				continue;
			if (delegacaoCentroResultadoDto.getRevogada().equalsIgnoreCase("S"))
				continue;
			DelegacaoCentroResultadoProcessoDTO delegacaoCentroResultadoProcessoDto = new DelegacaoCentroResultadoProcessoDTO();
			delegacaoCentroResultadoProcessoDto.setIdDelegacaoCentroResultado(delegacaoCentroResultadoDto.getIdDelegacaoCentroResultado());
			delegacaoCentroResultadoProcessoDto.setIdProcessoNegocio(alcadaProcessoNegocioDto.getProcessosNegocio().get(0).getIdProcessoNegocio());
			delegacaoCentroResultadoProcessoDao.restore(delegacaoCentroResultadoProcessoDto);
			if (delegacaoCentroResultadoProcessoDto == null)
				continue;
			if (delegacaoCentroResultadoDto.getAbrangencia().equalsIgnoreCase(DelegacaoCentroResultadoDTO.ESPECIFICAS)) {
				if (execucaoSolicitacao.getExecucaoSolicitacaoDto() == null)
					continue;
				DelegacaoCentroResultadoFluxoDTO delegacaoCentroResultadoFluxoDto = new DelegacaoCentroResultadoFluxoDTO();
				delegacaoCentroResultadoFluxoDto.setIdDelegacaoCentroResultado(delegacaoCentroResultadoDto.getIdDelegacaoCentroResultado());
				delegacaoCentroResultadoFluxoDto.setIdInstanciaFluxo(execucaoSolicitacao.getExecucaoSolicitacaoDto().getIdInstanciaFluxo());
				delegacaoCentroResultadoFluxoDao.restore(delegacaoCentroResultadoFluxoDto);
				if (delegacaoCentroResultadoFluxoDto == null)
					continue;
			}
			EmpregadoDTO empregadoDto = recuperaEmpregado(delegacaoCentroResultadoDto.getIdEmpregado());
			if (empregadoDto == null)
				continue;
			AlcadaProcessoNegocioDTO alcadaDelegDto = new AlcadaProcessoNegocioDTO();
			alcadaDelegDto.setAlcadaOrigemDto(alcadaProcessoNegocioDto);
			alcadaDelegDto.setCentroResultadoDto(alcadaProcessoNegocioDto.getCentroResultadoDto());
			alcadaDelegDto.setDelegacao(true);
			alcadaDelegDto.setEmpregadoDto(empregadoDto);
			alcadaDelegDto.setProcessosNegocio(alcadaProcessoNegocioDto.getProcessosNegocio());
			alcadaDelegDto.setMapGruposEmpregado(getHashMapGruposEmpregado(empregadoDto));
			colAlcadasDelgacoes.add(alcadaDelegDto);
		}
		return colAlcadasDelgacoes;
	}

	protected Collection<AlcadaProcessoNegocioDTO> getAlcadasResponsaveis(SolicitacaoServicoDTO solicitacaoServicoDto, CentroResultadoDTO centroResultadoDto, FluxoDTO fluxoDto, TransactionControler tc) throws Exception {
		this.transacao = tc;

		ProcessoNegocioDTO processoNegocioDto = recuperaProcessoNegocio(fluxoDto);
		if (processoNegocioDto == null)
			throw new LogicException("Processo de negócio não encontrado");

		ExecucaoSolicitacao execucaoSolicitacao = recuperaExecucaoSolicitacao(fluxoDto, solicitacaoServicoDto);
		if (execucaoSolicitacao == null)
			throw new LogicException("Instância do fluxo não encontrada");

		List<AlcadaProcessoNegocioDTO> colAlcadas = getAlcadasCentroResultado(centroResultadoDto, processoNegocioDto);
		if (colAlcadas == null || colAlcadas.isEmpty())
			return null;

		List<AlcadaProcessoNegocioDTO> colAlcadasDeleg = new ArrayList();
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			colAlcadasDeleg.addAll(recuperaDelegacoes(alcadaProcessoNegocioDto, execucaoSolicitacao));
		}
		colAlcadas.addAll(colAlcadasDeleg);

		associaLimiteAutoridadeEValores(processoNegocioDto, execucaoSolicitacao, centroResultadoDto);
		validaAlcadas(fluxoDto, processoNegocioDto, execucaoSolicitacao, solicitacaoServicoDto, colAlcadas);

		int i = 0;
		List<AlcadaProcessoNegocioDTO> result = null;
		boolean bFiltraHierarquia = fluxoDto.getProcessoNegocioDto().getAlcadaPrimeiroNivel() != null && fluxoDto.getProcessoNegocioDto().getAlcadaPrimeiroNivel().equalsIgnoreCase("S");
		if (bFiltraHierarquia) {
			int primeiroNivel = 0;
			for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
				if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
					continue;
				if (primeiroNivel < alcadaProcessoNegocioDto.recuperaHierarquiaNivelAutoridade())
					primeiroNivel = alcadaProcessoNegocioDto.recuperaHierarquiaNivelAutoridade();
			}

			result = new ArrayList();
			for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
				if (alcadaProcessoNegocioDto.isAlcadaRejeitada() || alcadaProcessoNegocioDto.recuperaHierarquiaNivelAutoridade() == primeiroNivel) {
					result.add(alcadaProcessoNegocioDto);
					if (!alcadaProcessoNegocioDto.isAlcadaRejeitada())
						i++;
				}
			}
			colAlcadas = result;
		}else{
			result = colAlcadas;
			for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
				if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
					continue;
				i++;
			}
		}

		if (i > 0)
			return ordenaAlcadas(result);

		result.addAll(getAlcadasNivelSuperior(processoNegocioDto, result));
		return ordenaAlcadas(result);
	}

	private Collection<AlcadaProcessoNegocioDTO> ordenaAlcadas(List<AlcadaProcessoNegocioDTO> colAlcadas) throws Exception {
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			String chaveOrdenacao = "";
			if (alcadaProcessoNegocioDto.getProcessosNegocio() != null && !alcadaProcessoNegocioDto.getProcessosNegocio().isEmpty()) {
				ProcessoNegocioDTO processoNegocioDto = alcadaProcessoNegocioDto.getProcessosNegocio().get(0);
				if (processoNegocioDto.getNivelAutoridadeDto() != null)
					chaveOrdenacao = ""+processoNegocioDto.getNivelAutoridadeDto().getHierarquia();
			}
			alcadaProcessoNegocioDto.setChaveOrdenacao(chaveOrdenacao);
		}
		Collections.sort(colAlcadas, new ObjectSimpleComparator("getChaveOrdenacao", ObjectSimpleComparator.DESC));
		return colAlcadas;
	}

	public Collection<AlcadaProcessoNegocioDTO> getSimulacaoAlcada(SimulacaoAlcadaDTO simulacaoAlcadaDto, CentroResultadoDTO centroResultadoDto, FluxoDTO fluxoDto) throws Exception {
		return getAlcadasResponsaveis(simulacaoAlcadaDto, centroResultadoDto, fluxoDto, null);
	}

	public Collection<EmpregadoDTO> getResponsaveis(SolicitacaoServicoDTO solicitacaoServicoDto, CentroResultadoDTO centroResultadoDto, TransactionControler tc) throws Exception {
		this.transacao = tc;

		FluxoDTO fluxoDto = recuperaFluxo(solicitacaoServicoDto);
		if (fluxoDto == null)
			throw new LogicException("Fluxo não encontrado");

		Collection<AlcadaProcessoNegocioDTO> colAlcadas = getAlcadasResponsaveis(solicitacaoServicoDto, centroResultadoDto, fluxoDto, tc);
		if (colAlcadas == null)
			return null;

		Collection<EmpregadoDTO> result = new ArrayList();
		for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
			if (alcadaProcessoNegocioDto.isAlcadaRejeitada())
				continue;
			result.add(alcadaProcessoNegocioDto.getEmpregadoDto());
		}
		return result;
	}

	public Collection<AlcadaProcessoNegocioDTO> getAlcadasResponsaveis(SolicitacaoServicoDTO solicitacaoServicoDto, CentroResultadoDTO centroResultadoDto, TransactionControler tc) throws Exception {
		this.transacao = tc;

		FluxoDTO fluxoDto = recuperaFluxo(solicitacaoServicoDto);
		if (fluxoDto == null)
			throw new LogicException("Fluxo não encontrado");

		return getAlcadasResponsaveis(solicitacaoServicoDto, centroResultadoDto, fluxoDto, tc);
	}
}
