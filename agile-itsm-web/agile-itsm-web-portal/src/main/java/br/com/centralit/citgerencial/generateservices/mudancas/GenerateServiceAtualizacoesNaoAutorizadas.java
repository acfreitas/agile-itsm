package br.com.centralit.citgerencial.generateservices.mudancas;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ImagemItemConfiguracaoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.StatusIC;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 */
public class GenerateServiceAtualizacoesNaoAutorizadas extends GerencialGenerateService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {

		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		String idGrupoItemConfiguracaoPaiStr = (String) parametersValues.get("PARAM.idGrupoItemConfiguracaoPai");
		String situacaoStr = (String) parametersValues.get("PARAM.situacao");
		String idCriticidadeStr = (String) parametersValues.get("PARAM.idCriticidade");
		String idTipoItemConfiguracaoStr = (String) parametersValues.get("PARAM.idTipoItemConfiguracao");

		Date datafim = null;
		Date datainicio = null;
		Integer idGrupoItemConfiguracaoPai = null;
		Integer situacao = null;
		Integer idCriticidade = null;
		Integer idTipoItemConfiguracao = null;
		List<HistoricoItemConfiguracaoDTO> historicoIC = null;
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

			historicoIC = new HistoricoItemConfiguracaoDAO().listHistoricoSemIdMudanca(datainicio, datafim);

			colGrupos = grupoItemConfiguracaoService.listHierarquiaGruposByIdGrupo(idGrupoItemConfiguracaoPai, null);

			/*
			 * if (idGrupoItemConfiguracaoPai != null && idGrupoItemConfiguracaoPai.intValue() == 997){ Collection colGrupos2 = null; try { colGrupos2 =
			 * grupoItemConfiguracaoService.listHierarquiaGrupoPaiNull(); } catch (Exception e) { e.printStackTrace(); } if (colGrupos2 != null){ colGrupos.addAll(colGrupos2); } }
			 */

			retorno = new ArrayList();

			ImagemItemConfiguracaoServiceEjb imagemItemConfiguracaoService = new ImagemItemConfiguracaoServiceEjb();

			ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
			for (HistoricoItemConfiguracaoDTO historicoDto : historicoIC) {
				Collection<ItemConfiguracaoDTO> itensImpactados = new ArrayList();

				ItemConfiguracaoDTO itemConfiguracaoDto = null;
				try {
					itemConfiguracaoDto = new ItemConfiguracaoDTO();
					itemConfiguracaoDto.setIdItemConfiguracao(historicoDto.getIdItemConfiguracao());
					itemConfiguracaoDto = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoDto);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (itemConfiguracaoDto == null)
					continue;

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

				for (ItemConfiguracaoDTO itemDto : itensImpactados) {
					List<Object> linha = new ArrayList();
					linha.add(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, historicoDto.getDataHoraAlteracao(), super.getLanguage(paramtersDefinition)));
					linha.add(itemDto.getIdentificacao());
					linha.add(itemDto.getDataInicio());
					linha.add(itemDto.getDataExpiracao());
					linha.add(itemDto.getNumeroSerie());
					linha.add(itemDto.getVersao());
					StatusIC status = StatusIC.getStatus(itemDto.getStatus());
					if (status != null)
						linha.add(status.getDescricao());
					else
						linha.add("");
					retorno.add(linha.toArray());
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
