package br.com.centralit.citgerencial.processparameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citgerencial.bean.GerencialOptionDTO;
import br.com.centralit.citgerencial.bean.GerencialParameterDTO;
import br.com.centralit.citgerencial.bean.GerencialProcessParameters;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.CompareException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProcessParametersGeral extends GerencialProcessParameters {

	public String processParameters(final HashMap hsmParms, final Collection colParmsUtilizadosNoSQL, final Collection colDefinicaoParametros) {
		if (colDefinicaoParametros == null || colDefinicaoParametros.isEmpty())
			return "";
		String valor = null;
		String strRetorno = "";
		String nameParam = null;
		List nomeParams = new ArrayList();
		GerencialParameterDTO param = null;
		for (Iterator it = colDefinicaoParametros.iterator(); it.hasNext();) {
			param = (GerencialParameterDTO) it.next();
			nameParam = "PARAM." + param.getName();
			if (nomeParams.indexOf(nameParam) < 0) {
				nomeParams.add(nameParam);
				if (String[].class.isInstance(hsmParms.get(nameParam))) {
					valor = "";
					String[] val = (String[]) hsmParms.get(nameParam);
					for (int i = 0; i < ((String[]) val).length; i++) {
						if (valor.length() > 0) {
							valor += ",";
						}
						valor += val[i];
					}
				} else if (String.class.isInstance(hsmParms.get(nameParam))) {
					valor = (String) hsmParms.get(nameParam);
				} else {
					valor = null;
				}
				if (nameParam.equalsIgnoreCase("PARAM.idPrioridade")) {
					if (UtilStrings.isNotVazio(valor) && !valor.equalsIgnoreCase("-1")) {
						PrioridadeDTO prioridadeDto = new PrioridadeDTO();
						prioridadeDto.setIdPrioridade(new Integer(valor));
						try {
							PrioridadeService serv = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
							prioridadeDto = (PrioridadeDTO) serv.restore(prioridadeDto);
						} catch (Exception e) {
							e.printStackTrace();
							prioridadeDto = null;
						}
						String nomePrioridade = "";
						if (prioridadeDto != null)
							nomePrioridade = prioridadeDto.getNomePrioridade();
						else
							nomePrioridade = (String) hsmParms.get("citcorpore.comum.naoInformado");
						strRetorno += param.getDescription() + ": " + nomePrioridade;
					} else
						strRetorno += getDescricaoParametro(colDefinicaoParametros, nameParam) + ": " + (String) hsmParms.get("citcorpore.comum.todos");
				} else if (nameParam.equalsIgnoreCase("PARAM.idServico")) {
					if (UtilStrings.isNotVazio(valor) && !valor.equalsIgnoreCase("-1")) {
						ServicoDTO servicoDto = new ServicoDTO();
						servicoDto.setIdServico(new Integer(valor));
						try {
							ServicoService serv = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
							servicoDto = (ServicoDTO) serv.restore(servicoDto);
						} catch (Exception e) {
							e.printStackTrace();
							servicoDto = null;
						}
						String nomeServico = "";
						if (servicoDto != null)
							nomeServico = servicoDto.getNomeServico();
						else
							nomeServico = (String) hsmParms.get("citcorpore.comum.naoInformado");
						strRetorno += param.getDescription() + ": " + nomeServico;
					} else
						strRetorno += getDescricaoParametro(colDefinicaoParametros, nameParam) + ": " + (String) hsmParms.get("citcorpore.comum.todos");
				} else if (nameParam.equalsIgnoreCase("PARAM.idUnidade")) {
					if (UtilStrings.isNotVazio(valor) && !valor.equalsIgnoreCase("-1")) {
						UnidadeDTO unidadeDto = new UnidadeDTO();
						unidadeDto.setIdUnidade(new Integer(valor));
						try {
							UnidadeService serv = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
							unidadeDto = (UnidadeDTO) serv.restore(unidadeDto);
						} catch (Exception e) {
							e.printStackTrace();
							unidadeDto = null;
						}
						String nomeUnidade = "";
						if (unidadeDto != null)
							nomeUnidade = unidadeDto.getNome();
						else
							nomeUnidade = (String) hsmParms.get("citcorpore.comum.naoInformado");
						strRetorno += param.getDescription() + ": " + nomeUnidade;
					} else
						strRetorno += getDescricaoParametro(colDefinicaoParametros, nameParam) + ": " + (String) hsmParms.get("citcorpore.comum.todos");
				} else if (nameParam.equalsIgnoreCase("PARAM.idOrigem")) {
					if (UtilStrings.isNotVazio(valor) && !valor.equalsIgnoreCase("-1")) {
						OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
						origemDto.setIdOrigem((new Integer(valor)));
						try {
							OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
							origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
						} catch (Exception e) {
							e.printStackTrace();
							origemDto = null;
						}
						String nomeOrigemAtendimento = "";
						if (origemDto != null)
							nomeOrigemAtendimento = origemDto.getDescricao();
						else
							nomeOrigemAtendimento = (String) hsmParms.get("citcorpore.comum.naoInformado");
						strRetorno += param.getDescription() + ": " + nomeOrigemAtendimento;
					} else
						strRetorno += getDescricaoParametro(colDefinicaoParametros, nameParam) + ": " + (String) hsmParms.get("citcorpore.comum.todos");
				} else {
					if (UtilStrings.isNotVazio(param.getTypeHTML()) && "select".equalsIgnoreCase(param.getTypeHTML()) && param.getColOptions() != null && !param.getColOptions().isEmpty()) {
						try {
							ObjectSimpleComparator osc = new ObjectSimpleComparator("getValue", ObjectSimpleComparator.ASC);

							List options = new ArrayList(param.getColOptions());
							GerencialOptionDTO aux = new GerencialOptionDTO();
							aux.setValue(valor);
							if (options != null && osc != null) {
								try {
									Collections.sort(options, osc);
									Integer index = Collections.binarySearch(options, aux, osc);
									if (index != null && index >= 0) {
										try {
											valor = ((GerencialOptionDTO) options.get(index)).getText();
										} catch (Exception e) {
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (CompareException ce) {
							ce.printStackTrace();
						} catch (NullPointerException e) {
							e.printStackTrace();
						}
					}
					try {
						if (valor != null) {
							if (valor.equalsIgnoreCase((String) hsmParms.get("citcorpore.comum.selecione"))) {
								valor = (String) hsmParms.get("citcorpore.comum.todos");
							}
						}
						if (valor == null || valor.equalsIgnoreCase("-1")) {
							valor = "--";
						}
						strRetorno += getDescricaoParametro(colDefinicaoParametros, nameParam) + ": " + valor;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			strRetorno += "   ";
		}
		return strRetorno;
	}

	private String getDescricaoParametro(final Collection colDefinicaoParametros, final String nameParm) {
		if (colDefinicaoParametros == null)
			return "";
		for (Iterator it = colDefinicaoParametros.iterator(); it.hasNext();) {
			GerencialParameterDTO gerencialParameterDTO = (GerencialParameterDTO) it.next();
			String nomeParmAux = "PARAM." + gerencialParameterDTO.getName().trim();
			if (nomeParmAux.equalsIgnoreCase(nameParm)) {
				String desc = gerencialParameterDTO.getDescription();
				int p = desc.indexOf(" (");
				if (p > 0)
					desc = desc.substring(0, p);
				return desc;
			}
		}
		return "";
	}

}