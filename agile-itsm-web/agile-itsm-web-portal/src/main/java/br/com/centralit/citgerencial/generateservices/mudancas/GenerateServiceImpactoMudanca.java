package br.com.centralit.citgerencial.generateservices.mudancas;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ImagemItemConfiguracaoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.StatusIC;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateServiceImpactoMudanca extends GerencialGenerateService {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) {

		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		String idGrupoItemConfiguracaoPaiStr = (String) parametersValues.get("PARAM.idGrupoItemConfiguracaoPai");
		String idCriticidadeStr = (String) parametersValues.get("PARAM.idCriticidade");
		String idTipoItemConfiguracaoStr = (String) parametersValues.get("PARAM.idTipoItemConfiguracao");
		String situacaoMudanca = (String) parametersValues.get("PARAM.situacaoMudanca");
		String situacaoStr = (String) parametersValues.get("PARAM.situacao");

		Date datafim = null;
		Date datainicio = null;
		Integer idGrupoItemConfiguracaoPai = null;
		Integer situacao = null;
		Integer idCriticidade = null;
		Integer idTipoItemConfiguracao = null;
		List<RequisicaoMudancaDTO> mudancas = null;
		GrupoItemConfiguracaoService grupoItemConfiguracaoService = null;
		Collection<GrupoItemConfiguracaoDTO> colGrupos = null;
		List retorno = null;

		try {
			datainicio = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition));
			datafim = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition));
			idGrupoItemConfiguracaoPai = new Integer(idGrupoItemConfiguracaoPaiStr);
			situacao = new Integer(situacaoStr);
			idCriticidade = new Integer(idCriticidadeStr);
			idTipoItemConfiguracao = new Integer(idTipoItemConfiguracaoStr);
			grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
			ImagemItemConfiguracaoServiceEjb imagemItemConfiguracaoService = new ImagemItemConfiguracaoServiceEjb();
			ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
			RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();

			/* Realiza o filtro de mudanças */
			mudancas = new RequisicaoMudancaDao().findByPeriodoAndSituacao(datainicio, datafim, situacaoMudanca);

			/* Filtra os grupos relacionados */
			colGrupos = grupoItemConfiguracaoService.listHierarquiaGruposByIdGrupo(idGrupoItemConfiguracaoPai, null);

			/*
			 * Removido, filtra apenas os grupos relacionados a desenvolvimento if (idGrupoItemConfiguracaoPai != null && idGrupoItemConfiguracaoPai.intValue() == 997){ if (idGrupoItemConfiguracaoPai
			 * != null){ Collection colGrupos2 = null; colGrupos2 = grupoItemConfiguracaoService.listHierarquiaGrupoPaiNull(); if (colGrupos2 != null && !colGrupos2.isEmpty()){
			 * colGrupos.addAll(colGrupos2); } }
			 */

			retorno = new ArrayList();

			for (RequisicaoMudancaDTO requisicaoMudancaDto : mudancas) {
				Collection<ItemConfiguracaoDTO> itensImpactados = new ArrayList<ItemConfiguracaoDTO>();
				Collection<RequisicaoMudancaItemConfiguracaoDTO> itensMudanca = null;

				itensMudanca = requisicaoMudancaItemConfiguracaoDao.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

				if (itensMudanca != null) {
					for (RequisicaoMudancaItemConfiguracaoDTO requisicaoItemDto : itensMudanca) {
						ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
						itemConfiguracaoDto.setIdItemConfiguracao(requisicaoItemDto.getIdItemConfiguracao());
						itemConfiguracaoDto = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoDto);

						if (!itemValido(itemConfiguracaoDto, situacao, idTipoItemConfiguracao, idCriticidade, colGrupos))
							continue;

						HashMap<String, ItemConfiguracaoDTO> mapItens = new HashMap();
						itensImpactados.add(itemConfiguracaoDto);
						mapItens.put("" + itemConfiguracaoDto.getIdItemConfiguracao(), itemConfiguracaoDto);
						try {
							Collection<ItemConfiguracaoDTO> colHierarq = imagemItemConfiguracaoService.findItensRelacionadosHierarquia(itemConfiguracaoDto.getIdItemConfiguracao());
							if (colHierarq != null) {
								for (ItemConfiguracaoDTO itemDto : colHierarq) {
									if (mapItens.get("" + itemDto.getIdItemConfiguracao()) != null)
										continue;

									if (!itemValido(itemDto, situacao, idTipoItemConfiguracao, idCriticidade, colGrupos))
										continue;

									mapItens.put("" + itemDto.getIdItemConfiguracao(), itemDto);
									itensImpactados.add(itemDto);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				if (itensImpactados != null && itensImpactados.size() > 0) {
					requisicaoMudancaDto.setDataHoraInicio(requisicaoMudancaDto.getDataHoraInicio());
					requisicaoMudancaDto.setStatus(requisicaoMudancaDto.getStatus());

					List<Object> linha = new ArrayList<Object>();
					linha.add("" + requisicaoMudancaDto.getIdRequisicaoMudanca());
					linha.add(requisicaoMudancaDto.getTitulo());
					linha.add(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, requisicaoMudancaDto.getDataHoraSolicitacao(), super.getLanguage(paramtersDefinition)));
					linha.add(requisicaoMudancaDto.getDescrSituacao());
					if (requisicaoMudancaDto.getDataHoraConclusao() != null)
						linha.add(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, requisicaoMudancaDto.getDataHoraConclusao(), super.getLanguage(paramtersDefinition)));
					else
						linha.add("");

					int i = 0;
					for (ItemConfiguracaoDTO itemDto : itensImpactados) {
						if (i > 0) {
							linha = new ArrayList();
							linha.add("" + requisicaoMudancaDto.getIdRequisicaoMudanca());
							linha.add("");
							linha.add("");
							linha.add("");
							linha.add("");
						}
						linha.add(itemDto.getIdentificacao());
						StatusIC status = StatusIC.getStatus(itemDto.getStatus());
						if (status != null)
							linha.add(status.getDescricao());
						else
							linha.add("");
						i++;
						retorno.add(linha.toArray());
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	private boolean itemValido(ItemConfiguracaoDTO itemConfiguracaoDto, Integer situacao, Integer idTipoItemConfiguracao, Integer idCriticidade, Collection<GrupoItemConfiguracaoDTO> colGrupos) {
		if (situacao != null && situacao.intValue() != -1) {
			if (itemConfiguracaoDto.getStatus() == null && situacao.intValue() == 1)
				return true;
			if (itemConfiguracaoDto.getStatus() == null || itemConfiguracaoDto.getStatus().intValue() != situacao)
				return false;
		}

		if (idTipoItemConfiguracao != null && idTipoItemConfiguracao.intValue() != -1) {
			if (itemConfiguracaoDto.getIdTipoItemConfiguracao() == null || itemConfiguracaoDto.getIdTipoItemConfiguracao().intValue() != idTipoItemConfiguracao.intValue())
				return false;
		}

		if (idCriticidade != null && idCriticidade.intValue() != -1) {
			if (itemConfiguracaoDto.getCriticidade() == null || itemConfiguracaoDto.getCriticidade().intValue() != idCriticidade.intValue())
				return false;
		}

		if (colGrupos != null && colGrupos.size() > 0) {
			boolean bAdicionar = false;
			for (GrupoItemConfiguracaoDTO grupoDto : colGrupos) {
				if (grupoDto.getIdGrupoItemConfiguracao().intValue() == itemConfiguracaoDto.getIdGrupoItemConfiguracao()) {
					bAdicionar = true;
					break;
				}
			}
			if (!bAdicionar)
				return false;
		}
		return true;
	}

}
