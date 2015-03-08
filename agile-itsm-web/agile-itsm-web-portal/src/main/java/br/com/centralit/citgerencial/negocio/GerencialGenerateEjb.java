package br.com.centralit.citgerencial.negocio;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.bean.GerencialFieldDTO;
import br.com.centralit.citgerencial.bean.GerencialGroupDTO;
import br.com.centralit.citgerencial.bean.GerencialInfoGenerateDTO;
import br.com.centralit.citgerencial.bean.GerencialItemInformationDTO;
import br.com.centralit.citgerencial.bean.GerencialItemPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionsDTO;
import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialParameterDTO;
import br.com.centralit.citgerencial.integracao.GerencialGenerateDao;
import br.com.centralit.citgerencial.pdf.EndPageControl;
import br.com.centralit.citgerencial.pdf.EndPageControlBuffer;
import br.com.centralit.citgerencial.pdf.EndPageGerencialControl;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Html2Pdf;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings({"rawtypes","unchecked","unused"})
public class GerencialGenerateEjb extends CrudServiceImpl implements GerencialGenerate {
	private static final Logger LOGGER = Logger.getLogger(GerencialGenerateEjb.class);

	private static final Color COR_FUNDO = Color.WHITE;
	private static final Color COR_TITULO = Color.BLACK;

	private String caminhoRelativoAuxiliar = "";

	private GerencialGenerateDao dao;

	protected GerencialGenerateDao getDao() {
		if (dao == null) {
			dao = new GerencialGenerateDao();
		}
		return dao;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------------------------------------- generate
	 */
	public Object generate(GerencialItemInformationDTO gerencialItemDto, Usuario usuario, GerencialInfoGenerateDTO infoGenerate, GerencialItemPainelDTO gerencialItemPainelAuxDto,
			GerencialPainelDTO gerencialPainelDto, HttpServletRequest request) throws ServiceException {
		try {
			Collection<GerencialParameterDTO> gerencialParameter = gerencialPainelDto.getListParameters();
			for (GerencialParameterDTO gerencial : gerencialParameter) {
				gerencial.setDescription(UtilI18N.internacionaliza(request, gerencial.getDescription()));

			}
			gerencialPainelDto.setListParameters(gerencialParameter);
			if (gerencialItemDto.getType().equalsIgnoreCase("SQL")) {
				Collection colParmsUtilizadosNoSQL = new ArrayList();
				String sql = trataSQL(gerencialItemDto.getGerencialSQL().getSql(), usuario, colParmsUtilizadosNoSQL);

				List listParms = trataParameters(infoGenerate.getHashParametros(), usuario, colParmsUtilizadosNoSQL, gerencialPainelDto.getListParameters());
				List listRetorno = null;
				if (listParms != null) {
					listRetorno = (List) getDao().executaSQL(sql, listParms.toArray());
				} else {
					listRetorno = (List) getDao().executaSQL(sql, null);
				}

				return generateRetorno(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, infoGenerate.getHashParametros(),
						gerencialPainelDto.getListParameters(), gerencialPainelDto, request);
			} else if (gerencialItemDto.getType().equalsIgnoreCase("CLASS_GENERATE_SQL")) {
				String sql = "";
				Class classeExecutar = Class.forName(gerencialItemDto.getClassExecute());
				if (classeExecutar != null) {
					Object objetoClasseExecutar = classeExecutar.newInstance();

					Method metodo = Reflexao.findMethod("generateSQL", objetoClasseExecutar);
					if (metodo != null) {
						Object[] param = new Object[] { infoGenerate.getHashParametros(), gerencialPainelDto.getListParameters() };
						Object retorno = metodo.invoke(objetoClasseExecutar, param);
						sql = (String) retorno;
					}
				}

				Collection colParmsUtilizadosNoSQL = new ArrayList();
				sql = trataSQL(sql, usuario, colParmsUtilizadosNoSQL);

				List listParms = trataParameters(infoGenerate.getHashParametros(), usuario, colParmsUtilizadosNoSQL, gerencialPainelDto.getListParameters());
				List listRetorno = null;
				if (listParms != null) {
					listRetorno = (List) getDao().executaSQL(sql, listParms.toArray());
				} else {
					listRetorno = (List) getDao().executaSQL(sql, null);
				}

				return generateRetorno(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, infoGenerate.getHashParametros(),
						gerencialPainelDto.getListParameters(), gerencialPainelDto, request);
			} else if (gerencialItemDto.getType().equalsIgnoreCase("SERVICE")) {
				Class classeExecutar = Class.forName(gerencialItemDto.getClassExecute());
				List listRetorno = null;
				if (classeExecutar != null) {
					Object objetoClasseExecutar = classeExecutar.newInstance();

					Method metodo = Reflexao.findMethod("execute", objetoClasseExecutar);
					if (metodo != null) {
						gerencialPainelDto.getListParameters().add(request);
						Object[] param = new Object[] { infoGenerate.getHashParametros(), gerencialPainelDto.getListParameters() };
						listRetorno = (List) metodo.invoke(objetoClasseExecutar, param);
					}
				}
				gerencialPainelDto.getListParameters().remove(request);
				return generateRetorno(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, null, infoGenerate.getHashParametros(), gerencialPainelDto.getListParameters(),
						gerencialPainelDto, request);
			} else if (gerencialItemDto.getType().equalsIgnoreCase("SERVICE_BUFFER")) {
				Class classeExecutar = Class.forName(gerencialItemDto.getClassExecute());
				String strRetorno = null;
				if (classeExecutar != null) {
					Object objetoClasseExecutar = classeExecutar.newInstance();

					Method metodo = Reflexao.findMethod("execute", objetoClasseExecutar);
					if (metodo != null) {
						gerencialPainelDto.getListParameters().add(request);
						Object[] param = new Object[] { infoGenerate.getHashParametros(), gerencialPainelDto.getListParameters() };
						strRetorno = (String) metodo.invoke(objetoClasseExecutar, param);
					}
				}
				gerencialPainelDto.getListParameters().remove(request);
				//return generateRetornoPDF_Buffer(strRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, null, infoGenerate.getHashParametros(),
				//		gerencialPainelDto.getListParameters(), gerencialPainelDto);
                return generateRetorno_Buffer(strRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, null, infoGenerate.getHashParametros(),
                        gerencialPainelDto.getListParameters(), gerencialPainelDto, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return null;
	}

	private List trataParameters(HashMap hsmParms, Usuario usuario, Collection colParmsUtilizadosNoSQL, Collection colDefinicaoParametros) {
		if (colParmsUtilizadosNoSQL == null || colParmsUtilizadosNoSQL.size() == 0) {
			return null;
		}
		List lstRetorno = new ArrayList();
		for (Iterator it = colParmsUtilizadosNoSQL.iterator(); it.hasNext();) {
			String nameParm = (String) it.next();
			String type = getTypeParametro(colDefinicaoParametros, nameParm);

			String valor = (String) hsmParms.get(nameParm);
			if (type.equalsIgnoreCase("java.sql.Date")) {
				Date data = null;
				try {
					data = UtilDatas.strToSQLDate(valor);
				} catch (LogicException e) {
					e.printStackTrace();
					LOGGER.error(e);
				}
				lstRetorno.add(data);
			}
			if (type.equalsIgnoreCase("java.lang.Integer")) {
				Integer intAux = null;
				if (valor == null) {
					intAux = new Integer(0);
				} else {
					intAux = new Integer(valor);
				}
				lstRetorno.add(intAux);
			}
			if (type.equalsIgnoreCase("java.lang.Double")) {
				Double duplo;

				String aux = valor;
				aux = aux.replaceAll("\\.", "");
				aux = aux.replaceAll("\\,", "\\.");

				duplo = new Double(Double.parseDouble(aux));
				lstRetorno.add(duplo);
			}
			if (type.equalsIgnoreCase("java.lang.String")) {
				lstRetorno.add(valor);
			}
		}
		return lstRetorno;
	}

	private String getTypeParametro(Collection colDefinicaoParametros, String nameParm) {
		if (colDefinicaoParametros == null) {
			return "";
		}
		for (Iterator it = colDefinicaoParametros.iterator(); it.hasNext();) {
			GerencialParameterDTO gerencialParameterDTO = (GerencialParameterDTO) it.next();
			String nomeParmAux = "PARAM." + gerencialParameterDTO.getName().trim();
			if (nomeParmAux.equalsIgnoreCase(nameParm)) {
				return gerencialParameterDTO.getType();
			}
		}
		return "";
	}
	public Collection executaSQLOptions(GerencialOptionsDTO options, GerencialPainelDTO gerencialPainelDto, HashMap hashParametros, Usuario user) throws ServiceException {
		return executaSQLOptions(options, gerencialPainelDto.getListParameters(), hashParametros, user);
	}
	public Collection executaSQLOptions(GerencialOptionsDTO options, Collection listParameters, HashMap hashParametros, Usuario user) throws ServiceException {
		String sqlParm = "";
		if (options.getType().equalsIgnoreCase("CLASS_GENERATE_SQL")) {
			Class classeExecutar;
			try {
				classeExecutar = Class.forName(options.getClassExecute());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
			if (classeExecutar != null) {
				Object objetoClasseExecutar;
				try {
					objetoClasseExecutar = classeExecutar.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
					throw new ServiceException(e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new ServiceException(e);
				}

				Method metodo = Reflexao.findMethod("generateSQL", objetoClasseExecutar);
				if (metodo != null) {
					Object[] param = new Object[] { hashParametros, listParameters };
					Object retorno = null;
					try {
						retorno = metodo.invoke(objetoClasseExecutar, param);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						throw new ServiceException(e);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new ServiceException(e);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						throw new ServiceException(e);
					}
					sqlParm = (String) retorno;
				}
			}
		} else { // SQL
			sqlParm = options.getSql();
		}
		Collection colRetorno = new ArrayList();
		Collection colParmsUtilizadosNoSQL = new ArrayList();
		String sql = trataSQL(sqlParm, user, colParmsUtilizadosNoSQL);

		List listParms = trataParameters(hashParametros, user, colParmsUtilizadosNoSQL, listParameters);
		List listRetorno = null;
		try {
			if (listParms != null) {
				listRetorno = (List) getDao().executaSQL(sql, listParms.toArray());
			} else {
				listRetorno = (List) getDao().executaSQL(sql, null);
			}
		} catch (Exception e) {
			listRetorno = null;
			/*
			 * e.printStackTrace(); throw new ServiceException(e);
			 */
		}

		if (listRetorno != null && listRetorno.size() > 0) {
			for (int i = 0; i < listRetorno.size(); i++) {
				Object[] row = (Object[]) listRetorno.get(i);
				GerencialOptionDTO option = new GerencialOptionDTO();
				for (int z = 0; z < row.length; z++) {
					Object obj = row[z];
					if(obj!=null){
						if (z == 0) {
							option.setValue(obj.toString());
						}
						if (z == 1) {
							option.setText(obj.toString());
						}
					}
				}
				colRetorno.add(option);
			}
		}
		return colRetorno;
	}

	/**
	 * Funcao para Tratar o SQL. ATENCAO: O 3.o Parametro eh Utilizado como Referencia! Sera limpo e atualizado dentro da funcao
	 *
	 * @param sql
	 * @param usuario
	 * @param colParmsUtilizadosNoSQL
	 *            - Parametro por Referencia.
	 * @return
	 */
	private String trataSQL(String sql, Usuario usuario, Collection colParmsUtilizadosNoSQL) {
		sql = sql.replaceAll("\\{IDEMPRESA\\}", "" + usuario.getIdEmpresa());
		sql = sql.replaceAll("\\{DATAATUAL\\}", "'" + UtilDatas.dateToSTR(UtilDatas.getDataAtual()) + "'");

		boolean b = true;
		while (b) {
			int beginIndex = sql.indexOf("{PARAM");
			if (beginIndex >= 0) {
				int endIndex = sql.indexOf("}");
				String nameParm = sql.substring(beginIndex, endIndex);
				nameParm = nameParm.replaceAll("\\{", "");

				sql = sql.replaceFirst("\\{" + nameParm + "\\}", "?");

				colParmsUtilizadosNoSQL.add(nameParm);
			} else {
				b = false;
			}
		}

		return sql;
	}

	private Object generateRetorno(List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto,
			HttpServletRequest request) throws Exception {
		if (infoGenerate.getSaida().equalsIgnoreCase("TABLE")) {
			return generateRetornoTabela(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros,
					gerencialPainelDto, request);
		} else if (infoGenerate.getSaida().equalsIgnoreCase("GRAPH")) {
			return generateRetornoGrafico(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros,
					gerencialPainelDto, request);
		} else if (infoGenerate.getSaida().equalsIgnoreCase("PDF")) {
			return generateRetornoPDF(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros,
					gerencialPainelDto, request);
		}
		return null;
	}

	private Object generateRetornoTabela(List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto,
			HttpServletRequest request) throws LogicException {
		Double[][] totals = new Double[100][100];
		Double[][] counts = new Double[100][100];
		String[][] anteriorValue = new String[100][100];

		Double[] totalLinha = new Double[100];
		Double[] countLinha = new Double[100];

		boolean[][] isTotals = new boolean[100][100];
		boolean[][] isCounts = new boolean[100][100];
		boolean[] temItensNoGrupo = new boolean[100];

		int colunasGrupos[] = new int[100];

		String bufferRetorno = "";
		int qtdeColunas = 0;
		int qtdeColunasSemAgrupadores = 0;
		int qtdeColunasAgrupadoras = 0;

		boolean temTotals = false;
		boolean temCounts = false;

		boolean existeAgrupador = false;

		bufferRetorno += "<table width='100%'>";

		EndPageControl endPageControl = new EndPageControl(existeAgrupador, gerencialItemDto, null, listRetorno, 100, new int[] { 100 },
				UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto, request);
		String strFiltro = endPageControl.trataParameters(hshParameters, colParmsUtilizadosNoSQL, colDefinicaoParametros);
		if (strFiltro == null) {
			strFiltro = "";
		}
		bufferRetorno += "<tr>";
		bufferRetorno += "<td colspan='50' style='text-align: center;'>";
		// bufferRetorno += strFiltro.trim();
		bufferRetorno += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio") + ": " + infoGenerate.getHashParametros().get("PARAM.dataInicial") + " - "
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.datafim") + ": " + infoGenerate.getHashParametros().get("PARAM.dataFinal");
		bufferRetorno += "</td>";
		bufferRetorno += "</tr>";

		if (listRetorno != null && listRetorno.size() > 0) {
			Object[] row = (Object[]) listRetorno.get(0);

			qtdeColunas = row.length;
			for (int j = 0; j < row.length; j++) {
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);
				GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());
				if (grupoDefinicaoDto == null) { // So mostra se nao for um agrupador
					qtdeColunasSemAgrupadores++;
				} else {
					colunasGrupos[qtdeColunasAgrupadoras] = j;
					qtdeColunasAgrupadoras++;
					existeAgrupador = true;
				}
			}

			if (!existeAgrupador) {
				bufferRetorno += geraCabecalhoTabela(row.length, gerencialItemDto, request);
			}
		} else {
			bufferRetorno += "<tr>";
			bufferRetorno += "<td colspan='50' style=' text-align: center; font-size: 12px; font-weight:bold; color: red'>";
			bufferRetorno += " <br><br><br><br><br><br><br><br><br><br><br><br><br><br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaDadosParaImpressao");
			bufferRetorno += "</td>";
			bufferRetorno += "</tr>";

		}

		if (listRetorno != null && listRetorno.size() != 0) {
			for (int i = 0; i < listRetorno.size(); i++) {
				Object[] row = (Object[]) listRetorno.get(i);

				bufferRetorno += "<tr >";
				qtdeColunas = row.length;
				int j = 0;
				boolean quebrouLinha = false;
				int indiceGrupoAdicionado = 0;

				for (int x = 0; x < totalLinha.length; x++) {
					totalLinha[x] = new Double(0);
					countLinha[x] = new Double(0);
				}

				String bufferLinha = "";
				for (int z = 0; z < row.length; z++) {
					Object obj = row[z];
					GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(z);

					GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());

					if (grupoDefinicaoDto == null) {
						bufferLinha += "<td class=\"celulaGrid\">";
					}
					if (obj != null) {
						String valorAtualAux = null;

						// O trecho abaixo serve para controlar a totalização geral (indice 0).
						if (totals[totals.length - 1][z] == null) {
							totals[totals.length - 1][z] = new Double(0);
						}
						if (counts[counts.length - 1][z] == null) {
							counts[counts.length - 1][z] = new Double(0);
						}

						// O trecho abaixo server para controlar totalizacoes dos grupos (indice > 0 - numero da coluna agrupadora);
						if (totalLinha[z] == null) {
							totalLinha[z] = new Double(0);
						}
						if (countLinha[z] == null) {
							countLinha[z] = new Double(0);
						}

						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.String")) {
							String objString = "";
							if (Integer.class.isInstance(obj)) {
								objString = "" + ((Integer) obj).intValue();
							} else {
								objString = (String) obj;
							}

							valorAtualAux = objString;

							if (grupoDefinicaoDto == null) {
								bufferLinha += objString;
							}

							if (fieldDto.isCount()) {
								temCounts = true;
								counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
								isCounts[counts.length - 1][z] = true;

								countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
							}
						}
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
							Double objDouble = null;
							if (Integer.class.isInstance(obj)) {
								objDouble = new Double(((Integer) obj).doubleValue());
							} else {
								if (BigDecimal.class.isInstance(obj)) {
									objDouble = new Double(((BigDecimal) obj).doubleValue());
								} else if (Long.class.isInstance(obj)) {
									objDouble = new Double(((Long) obj).doubleValue());
								} else if (Integer.class.isInstance(obj)) {
									objDouble = new Double(((Integer) obj).doubleValue());
								} else if (BigInteger.class.isInstance(obj)) {
									objDouble = new Double(((BigInteger) obj).doubleValue());
								} else {
									objDouble = (Double) obj;
								}
							}
							if (objDouble == null) {
								objDouble = new Double(0);
							}

							valorAtualAux = objDouble.toString();

							if (grupoDefinicaoDto == null) {
								bufferLinha += UtilFormatacao.formatDouble(objDouble, fieldDto.getDecimals().intValue());
							}

							if (fieldDto.isTotals()) {
								temTotals = true;
								totals[counts.length - 1][z] = new Double(totals[counts.length - 1][z].doubleValue() + objDouble.doubleValue());
								isTotals[counts.length - 1][z] = true;

								totalLinha[z] = new Double(totalLinha[z].doubleValue() + objDouble.doubleValue());
							}
							if (fieldDto.isCount()) {
								temCounts = true;
								counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
								isCounts[counts.length - 1][z] = true;

								countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
							}
						}
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
							Integer objInteger = null;
							if (Integer.class.isInstance(obj)) {
								objInteger = (Integer) obj;
							} else if (Long.class.isInstance(obj)) {
								Long objLong = (Long) obj;
								if (objLong != null) {
									objInteger = new Integer(objLong.intValue());
								} else {
									objInteger = new Integer(0);
								}
							}

							if (grupoDefinicaoDto == null) {
								bufferLinha += objInteger;
							}

							if (objInteger != null)
								valorAtualAux = objInteger.toString();
							else
								valorAtualAux = null;

							if (fieldDto.isTotals()) {
								temTotals = true;
								totals[counts.length - 1][z] = new Double(totals[counts.length - 1][z].doubleValue() + objInteger.intValue());
								isTotals[counts.length - 1][z] = true;

								totalLinha[z] = new Double(totalLinha[z].doubleValue() + objInteger.intValue());
							}
							if (fieldDto.isCount()) {
								temCounts = true;
								counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
								isCounts[counts.length - 1][z] = true;

								countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
							}
						}
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.sql.Date")) {
							Date objDate = null;
							if (Timestamp.class.isInstance(obj)) {
								objDate = new java.sql.Date(((Timestamp) obj).getTime());
							} else if (Date.class.isInstance(obj)) {
								objDate = (Date) obj;
							}

							if (objDate != null) {
								//valorAtualAux = UtilDatas.dateToSTR(objDate);
								valorAtualAux = UtilDatas.convertDateToString(UtilDatas.getTipoDate(objDate.toString(), WebUtil.getLanguage(request)), objDate, WebUtil.getLanguage(request));

								if (grupoDefinicaoDto == null) {
									//bufferLinha += UtilDatas.dateToSTR(objDate);
									bufferLinha += UtilDatas.convertDateToString(UtilDatas.getTipoDate(objDate.toString(), WebUtil.getLanguage(request)), objDate, WebUtil.getLanguage(request));
								}
							} else {
								valorAtualAux = null;
							}

							if (fieldDto.isCount()) {
								temCounts = true;
								counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
								isCounts[counts.length - 1][z] = true;

								countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
							}
						}

						if (grupoDefinicaoDto != null) { // Encontrou definicao de Grupo para este campo.
							if (!valorAtualAux.equalsIgnoreCase(anteriorValue[0][z])) {
								// Quebrou - Entra neste trecho

								// Verifica se tem totalizacao, se sim, entao gera os totais.
								if (temCounts || temTotals) {
									for (int x = temItensNoGrupo.length - 1; x >= z; x--) {
										if (temItensNoGrupo[x]) {
											bufferRetorno += geraTotais(qtdeColunas, x, gerencialItemDto, isCounts, isTotals, totals, counts, infoGenerate.getHashParametros());
											bufferRetorno += "<tr><td colspan='" + qtdeColunasSemAgrupadores + "'>";
											bufferRetorno += "&nbsp;";
											bufferRetorno += "</td></tr>";
										}
									}
									for (int x = z; x < temItensNoGrupo.length; x++) {
										temItensNoGrupo[x] = false;
									}
								}

								indiceGrupoAdicionado = z;

								// Gera o cabecalho com o grupo
								bufferRetorno += "<td><tr></td></tr>";
								bufferRetorno += "<tr><td class=\"celulaGrid\" style=\"background-color: #B5B5B5;\" colspan='" + qtdeColunasSemAgrupadores + "'>";

								for (int x = 0; x < z; x++)
									bufferRetorno += "&nbsp;&nbsp;&nbsp;&nbsp;";

								bufferRetorno += "<label style=\"fonte-size: 12px\">" + UtilI18N.internacionaliza(request, fieldDto.getTitle()) + ":</label> <b>" + valorAtualAux + "</b>";

								bufferRetorno += "</td></tr>";

								quebrouLinha = true;
							}
						} else {
							j++;
						}

						anteriorValue[0][z] = valorAtualAux; // O Valor Atual passa a ser o Valor Anterior.

					} else {
						if (grupoDefinicaoDto == null) {
							j++;
							bufferLinha += "&nbsp;";
						}
					}

					if (grupoDefinicaoDto == null) {
						bufferLinha += "</td>";
					}
				}

				if (quebrouLinha) {
					bufferRetorno += geraCabecalhoTabela(row.length, gerencialItemDto, request);
				}

				for (int x = 0; x < qtdeColunasAgrupadoras; x++) {
					for (int z = 0; z < qtdeColunas; z++) {
						if (totals[colunasGrupos[x]][z] == null) {
							totals[colunasGrupos[x]][z] = new Double(0);
						}
						if (counts[colunasGrupos[x]][z] == null) {
							counts[colunasGrupos[x]][z] = new Double(0);
						}
						if (totalLinha[z] == null) {
							totalLinha[z] = new Double(0);
						}
						if (countLinha[z] == null) {
							countLinha[z] = new Double(0);
						}
						totals[colunasGrupos[x]][z] = new Double(totals[colunasGrupos[x]][z].doubleValue() + totalLinha[z].doubleValue());
						counts[colunasGrupos[x]][z] = new Double(counts[colunasGrupos[x]][z].doubleValue() + countLinha[z].doubleValue());
					}
				}

				bufferRetorno += bufferLinha;
				bufferRetorno += "</tr>";

				if (existeAgrupador)
					temItensNoGrupo[indiceGrupoAdicionado] = true;
			}
		}

		if (temCounts || temTotals) {
			for (int x = temItensNoGrupo.length - 1; x >= 0; x--) {
				if (temItensNoGrupo[x]) {
					bufferRetorno += geraTotais(qtdeColunas, x, gerencialItemDto, isCounts, isTotals, totals, counts, infoGenerate.getHashParametros());
					bufferRetorno += "<tr><td colspan='" + qtdeColunasSemAgrupadores + "'>";
					bufferRetorno += "&nbsp;";
					bufferRetorno += "</td></tr>";
				}
			}

			bufferRetorno += "<tr><td colspan='" + qtdeColunasSemAgrupadores + "'>";
			bufferRetorno += "&nbsp;";
			bufferRetorno += "</td></tr>";

			/*
			 * Rodrigo Pecci Acorse - 30/01/2014 10h00 - #132390
			 * Adiciona a soma total dos itens no relatório
			 */
			String somaTotal = geraSomaTotal(listRetorno, gerencialItemPainelAuxDto, gerencialItemDto, request);

			if (!somaTotal.equals("")) {
				bufferRetorno += "<tr>";
				for (int j = 0; j < qtdeColunas; j++) {
					if (j < (qtdeColunas - 1)) {
						bufferRetorno += "<td class=\"celulaGrid\" style=\"background-color:#dcdcdc;\"></td>";
					} else {
						bufferRetorno += "<td class=\"celulaGrid\" style=\"background-color:#dcdcdc;\">" + somaTotal + "</td>";
					}
				}
				bufferRetorno += "</tr>";
			}

			//Adiciona o total de registros (linhas) no relatório
			bufferRetorno += geraTotais(qtdeColunas, counts.length - 1, gerencialItemDto, isCounts, isTotals, totals, counts, infoGenerate.getHashParametros());
		}

		bufferRetorno += "</table>";

		if(listRetorno!= null && listRetorno.size()!= 0){
			return bufferRetorno;
		} else{
			return null;
		}

	}

	public Object geraTabelaVazia(GerencialInfoGenerateDTO infoGenerate, HttpServletRequest request){

		String bufferRetorno = "";
		bufferRetorno += "<table width='100%'>";
		bufferRetorno += "<tr>";
		bufferRetorno += "<td colspan='50' style='text-align: center;'>";
		// bufferRetorno += strFiltro.trim();
		bufferRetorno += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio") + ": " + infoGenerate.getHashParametros().get("PARAM.dataInicial") + " - "
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.datafim") + ": " + infoGenerate.getHashParametros().get("PARAM.dataFinal");
		bufferRetorno += "<tr>";
		bufferRetorno += "<td colspan='50' style=' text-align: center; font-size: 12px; font-weight:bold; color: red'>";
		bufferRetorno += " <br><br><br><br><br><br><br><br><br><br><br><br><br><br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaDadosParaImpressao");
		bufferRetorno += "</td>";
		bufferRetorno += "</tr>";
		bufferRetorno += "</td>";
		bufferRetorno += "</tr>";
		bufferRetorno += "</table>";
		return bufferRetorno;
	}

	private String geraCabecalhoTabela(int tam, GerencialItemInformationDTO gerencialItemDto, HttpServletRequest request) {
		String bufferRetorno = "<tr>";
		for (int j = 0; j < tam; j++) {
			GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

			GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());

			if (grupoDefinicaoDto == null) { // So mostra se nao for um agrupador
				bufferRetorno += "<td width=\"" + fieldDto.getWidth() + "\" class=\"linhaSubtituloGrid\">";

				bufferRetorno += "<b>" + UtilI18N.internacionaliza(request, fieldDto.getTitle()) + "</b>";

				bufferRetorno += "</td>";
			}
		}

		bufferRetorno += "</tr>";

		return bufferRetorno;
	}

	private GerencialGroupDTO fieldInGroupDefinition(String fieldName, Collection colGrupos) {
		if (colGrupos == null) {
			return null;
		}
		for (Iterator it = colGrupos.iterator(); it.hasNext();) {
			GerencialGroupDTO gerencialGroup = (GerencialGroupDTO) it.next();
			if (gerencialGroup.getFieldName().trim().equalsIgnoreCase(fieldName)) {
				return gerencialGroup;
			}
		}
		return null;
	}

	/**
	 * Rodrigo Pecci Acorse - 30/01/2014 10h00 - #132390
	 * Retorna a soma total do resultado das colunas do relatório
	 *
	 * @param listRetorno
	 * @param gerencialItemPainelAuxDto
	 * @param gerencialItemDto
	 * @param request
	 * @return String
	 * @author rodrigo.acorse
	 */
	private String geraSomaTotal(List listRetorno, GerencialItemPainelDTO gerencialItemPainelAuxDto, GerencialItemInformationDTO gerencialItemDto, HttpServletRequest request) {
		String bufferRetorno = "";

		if (gerencialItemPainelAuxDto != null && gerencialItemPainelAuxDto.getTotal() != null && gerencialItemPainelAuxDto.getTotal().equalsIgnoreCase("y")) {
			Double objDouble;
			Double objTotal = new Double(0);

			if (listRetorno == null) {
				listRetorno = new ArrayList();
			}
			for (int i = 0; i < listRetorno.size(); i++) {
				Object[] row = (Object[]) listRetorno.get(i);

				for (int j = 0; j < row.length; j++) {
					Object obj = row[j];
					GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

					if (fieldDto.isCount()) {
						objDouble = new Double(0);
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
							if (Integer.class.isInstance(obj)) {
								objDouble = new Double(((Integer) obj).intValue());
							} else if (Long.class.isInstance(obj)) {
								objDouble = new Double(((Long) obj).intValue());
							}
						}
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
							if (Integer.class.isInstance(obj)) {
								objDouble = new Double(((Integer) obj).doubleValue());
							} else {
								if (BigDecimal.class.isInstance(obj)) {
									objDouble = new Double(((BigDecimal) obj).doubleValue());
								} else if (Long.class.isInstance(obj)) {
									objDouble = new Double(((Long) obj).doubleValue());
								} else if (Integer.class.isInstance(obj)) {
									objDouble = new Double(((Integer) obj).doubleValue());
								} else if (BigInteger.class.isInstance(obj)) {
									objDouble = new Double(((BigInteger) obj).doubleValue());
								} else {
									objDouble = (Double) obj;
								}
							}
						}

						objTotal = objTotal + objDouble;
					}
				}
			}

			bufferRetorno += UtilI18N.internacionaliza(request, "carrinho.total") + " " + objTotal.toString();
		}

		return bufferRetorno.toString();
	}

	private String geraTotais(int qtdeColunas, int indice, GerencialItemInformationDTO gerencialItemDto, boolean[][] isCounts, boolean[][] isTotals, Double[][] totals, Double[][] counts,
			HashMap parametersValues) {
		String bufferRetorno = "<tr>";
		String quantidadeTotalRegistros = (String) parametersValues.get("citcorpore.comum.quantidadeDeOrigens");
		String quantidadeRegistros = (String) parametersValues.get("citcorpore.comum.quantidadeRegistros");
		int iAux = 0;
		for (int j = 0; j < qtdeColunas; j++) {
			GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

			GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());
			if (grupoDefinicaoDto == null) {
				bufferRetorno += "<td class=\"celulaGrid\" style=\"background-color:#dcdcdc;\">";
				if (indice == counts.length - 1) {
					if (isCounts[counts.length - 1][j]) {
						bufferRetorno += quantidadeTotalRegistros + ": <b>" + UtilFormatacao.formatDouble(counts[indice][j], 0) + "</b>";
					} else if (isTotals[counts.length - 1][j]) {
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
							bufferRetorno += "<b>" + UtilFormatacao.formatDouble(totals[indice][j], 2) + "</b>";
						} else {
							bufferRetorno += "<b>" + UtilFormatacao.formatDouble(totals[indice][j], 0) + "</b>";
						}

					} else {
						bufferRetorno += "&nbsp;";
					}
				} else {
					if (isCounts[counts.length - 1][j]) {
						bufferRetorno += quantidadeRegistros + ": <b>" + UtilFormatacao.formatDouble(counts[indice][j], 0) + "</b>";
						// Zera o Item
						counts[indice][j] = new Double(0);
					} else if (isTotals[counts.length - 1][j]) {
						if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
							bufferRetorno += "<b>" + UtilFormatacao.formatDouble(totals[indice][j], 2) + "</b>";
						} else {
							bufferRetorno += "<b>" + UtilFormatacao.formatDouble(totals[indice][j], 0) + "</b>";
						}
						// Zera o Item
						totals[indice][j] = new Double(0);
					} else {
						bufferRetorno += "&nbsp;";
					}
				}
				bufferRetorno += "</td>";

				iAux++;
			}
		}
		bufferRetorno += "</tr>";

		return bufferRetorno;
	}

	private Object generateRetornoGrafico(List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto,
			HttpServletRequest request) throws Exception {
		String bufferRetorno = "";
		EndPageControl endPageControl = new EndPageControl(false, gerencialItemDto, null, listRetorno, 100, new int[] { 100 }, UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()),
				colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto, request);
		String strFiltro = endPageControl.trataParameters(hshParameters, colParmsUtilizadosNoSQL, colDefinicaoParametros);
		if (strFiltro == null) {
			strFiltro = "";
		}
		bufferRetorno += "<table width='100%'> ";
		bufferRetorno += "<tr>";
		bufferRetorno += "<td style='text-align: center;font-size:13px'>";
		bufferRetorno += "" + UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio") + ":" + infoGenerate.getHashParametros().get("PARAM.dataInicial") + " - "
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.datafim") + ": " + infoGenerate.getHashParametros().get("PARAM.dataFinal");
		bufferRetorno += "</td>";
		bufferRetorno += "</tr>";
		bufferRetorno += "<tr>";
		bufferRetorno += "<td>";

		if (infoGenerate.getGraphType()!=null){
			if (infoGenerate.getGraphType().equalsIgnoreCase("BARRA3D")) {
				bufferRetorno += generateRetornoGrafico_Barra(true, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("BARRA2D")) {
				bufferRetorno += generateRetornoGrafico_Barra(false, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("PIZZA3D")) {
				bufferRetorno += generateRetornoGrafico_Pizza(true, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("PIZZA2D")) {
				bufferRetorno += generateRetornoGrafico_Pizza(false, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("LINHA3D")) {
				bufferRetorno += generateRetornoGrafico_Linha(true, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("LINHA2D")) {
				bufferRetorno += generateRetornoGrafico_Linha(false, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			}
		}
		bufferRetorno += "</td>";
		bufferRetorno += "</tr>";

		/*
		 * Rodrigo Pecci Acorse - 30/01/2014 10h00 - #132390
		 * Adiciona a soma total dos itens no relatório
		 */
		String somaTotal = geraSomaTotal(listRetorno, gerencialItemPainelAuxDto, gerencialItemDto, request);

		if (!somaTotal.equals("")) {
			bufferRetorno += "<tr>";
			bufferRetorno += "	<td style=\"text-align: center;font-weight: bold;font-size: 14px;\">" + somaTotal + "</td>";
			bufferRetorno += "</tr>";
		}

		bufferRetorno += "</table>";
		if(listRetorno != null && listRetorno.size()!= 0){
			return bufferRetorno;
		} else {
			return geraTabelaVazia(infoGenerate, request);
		}
	}

	private Object generateRetornoGrafico_Barra(boolean is3D, List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, HttpServletRequest request) throws Exception {
		JFreeChart chart;
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		Double objDouble;
		String objString1;
		String objString2;
		int qtdeColunas = 0;
		int posString = 0;

		int qtdeLinhas = 0;
		for (int i = 0; i < listRetorno.size(); i++) {
			Object[] row = (Object[]) listRetorno.get(i);
			qtdeColunas = row.length - 1;
			objDouble = new Double(0);
			objString1 = "";
			objString2 = "";
			posString = 0;

			qtdeLinhas++;
			for (int j = 0; j < row.length; j++) {
				Object obj = row[j];
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).intValue());
					} else if (Long.class.isInstance(obj)) {
						objDouble = new Double(((Long) obj).intValue());
					}

				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
					objDouble = null;
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).doubleValue());
					} else {
						if (BigDecimal.class.isInstance(obj)) {
							objDouble = new Double(((BigDecimal) obj).doubleValue());
						} else if (Long.class.isInstance(obj)) {
							objDouble = new Double(((Long) obj).doubleValue());
						} else if (Integer.class.isInstance(obj)) {
							objDouble = new Double(((Integer) obj).doubleValue());
						} else if (BigInteger.class.isInstance(obj)) {
							objDouble = new Double(((BigInteger) obj).doubleValue());
						} else {
							objDouble = (Double) obj;
						}
					}
					if (objDouble == null) {
						objDouble = new Double(0);
					}
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.String")) {
					if (obj == null) {
						obj = new String("");
					}
					String str = "";
					if (Integer.class.isInstance(obj)) {
						str = "" + (Integer) obj;
					} else {
						str = (String) obj;
					}
					if (posString == 0) {
						objString1 = str;
					} else {
						objString2 = str;
					}
					posString++;
				}
			}

			if (objString1 == null || objString1.trim().equalsIgnoreCase("")) {
				Object obj = row[0];
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(0);

				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
					objString1 = new String("" + ((Integer) obj).intValue());
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
					objDouble = null;
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).doubleValue());
					} else {
						if (BigDecimal.class.isInstance(obj)) {
							objDouble = new Double(((BigDecimal) obj).doubleValue());
						} else if (Long.class.isInstance(obj)) {
							objDouble = new Double(((Long) obj).doubleValue());
						} else if (Integer.class.isInstance(obj)) {
							objDouble = new Double(((Integer) obj).doubleValue());
						} else if (BigInteger.class.isInstance(obj)) {
							objDouble = new Double(((BigInteger) obj).doubleValue());
						} else {
							objDouble = (Double) obj;
						}
					}
					if (objDouble == null) {
						objDouble = new Double(0);
					}
					objString1 = UtilFormatacao.formatDouble(objDouble, 0);
				}
			}

			dados.addValue(objDouble.doubleValue(), objString1, objString2);
		}

		String t1 = null;
		String t2 = null;

		GerencialFieldDTO fieldDto = null;

		try {
			fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(0);
			t1 = fieldDto.getTitle();
		} catch (Exception e) {
		}
		try {
			fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(qtdeColunas);
			t2 = fieldDto.getTitle();
		} catch (Exception e) {
		}

		if (is3D) {
			chart = ChartFactory.createBarChart3D(UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), t1, t2, dados, PlotOrientation.VERTICAL, true, true, false);
		} else {
			chart = ChartFactory.createBarChart(UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), t1, t2, dados, PlotOrientation.VERTICAL, true, true, false);
		}

		// Setando o valor maximo para nunca passar de 100, ja q se trata de
		// porcentagem
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.getRenderer().setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.CENTER));
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setAxisLineVisible(true);
		rangeAxis.setTickLabelsVisible(true);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// Formatando cores de fundo, fonte do titulo, etc...
		chart.setBackgroundPaint(COR_FUNDO); // Cor do fundo do grafico
		chart.getTitle().setPaint(COR_TITULO); // Cor do titulo
		chart.getTitle().setFont(new java.awt.Font("arial", Font.BOLD, 10)); // Fonte do
		chart.getPlot().setBackgroundPaint(new Color(221, 227, 213));// Cor de
		chart.getLegend().setItemFont(new java.awt.Font("arial", Font.BOLD, 8));
		chart.setBorderVisible(false); // Visibilidade da borda do grafico
		BarRenderer rend = (BarRenderer) plot.getRenderer();
		// CategoryItemRenderer rend = (CategoryItemRenderer) plot.getRenderer();
		rend.setSeriesOutlinePaint(0, Color.BLACK); // Cor da borda das barras do
		rend.setBaseItemLabelFont(new java.awt.Font("SansSerif", Font.BOLD, 10));

		// rend.setSeriesPaint(0, new Color(70 ,130 ,180)); // Cor das barras do
		// rend.setItemMargin(0.10); // Margem entre o eixo Y e a primeira barra do
		rend.setBaseItemLabelsVisible(true);
		// rend.setBaseItemLabelGenerator(new CustomLabelGenerator());
		rend.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		rend.setSeriesItemLabelsVisible(0, new Boolean(true));
		rend.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
		// rend.setSeriesPositiveItemLabelPosition(1, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE11, TextAnchor.BASELINE_CENTER, TextAnchor.BASELINE_CENTER, 50.0));
		rend.setPositiveItemLabelPositionFallback(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_CENTER, TextAnchor.CENTER, 0.0));

		String caminhoRelativo = "";
		String caminho = "";
		try {
			File arquivo = new File(infoGenerate.getCaminhoArquivosGraficos());
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}
			caminho = infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_"
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";
			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_" +
			// UtilDatas.formatTimestamp(UtilDatas.getDataHoraAtual()).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";
			String urlInicial = "";
			if (urlInicial == null || urlInicial.trim().equalsIgnoreCase("")){
				urlInicial = Constantes.getValue("CONTEXTO_APLICACAO");
			}
			caminhoRelativo =  urlInicial + "/tempFiles" + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_"
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";

			caminhoRelativoAuxiliar = caminhoRelativo;

			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			int tam = Integer.parseInt(UtilStrings.apenasNumeros(gerencialItemPainelAuxDto.getWidth()));
			int alt = Integer.parseInt(UtilStrings.apenasNumeros(gerencialItemPainelAuxDto.getHeigth()));

			tam = tam - 15;
			alt = alt - 70;
			/*
			 * if (qtdeLinhas > 20){ tam = qtdeLinhas * 18; }
			 */
			ChartUtilities.saveChartAsPNG(arquivo, chart, tam, alt);
			LOGGER.info("Grafico de Barras gerado em:\n\t" + caminho);

		} catch (IOException e) {
			LOGGER.error("Problemas durante a criação do Gráfico de Barras.", e);
		}

		if(listRetorno != null && listRetorno.size()!= 0){
			return "<img src=\"" + caminhoRelativo + "\"/>";
		} else {
			return null;
		}
	}

	private Object generateRetornoGrafico_Pizza(boolean is3D, List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, HttpServletRequest request) {
		JFreeChart chart;
		DefaultPieDataset dados = new DefaultPieDataset();
		Double objDouble;
		String objString1;
		if (listRetorno == null) {
			listRetorno = new ArrayList();
		}
		for (int i = 0; i < listRetorno.size(); i++) {
			Object[] row = (Object[]) listRetorno.get(i);

			objDouble = new Double(0);
			objString1 = "";
			for (int j = 0; j < row.length; j++) {
				Object obj = row[j];
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).intValue());
					} else if (Long.class.isInstance(obj)) {
						objDouble = new Double(((Long) obj).intValue());
					}
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
					objDouble = null;
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).doubleValue());
					} else {
						if (BigDecimal.class.isInstance(obj)) {
							objDouble = new Double(((BigDecimal) obj).doubleValue());
						} else if (Long.class.isInstance(obj)) {
							objDouble = new Double(((Long) obj).doubleValue());
						} else if (Integer.class.isInstance(obj)) {
							objDouble = new Double(((Integer) obj).doubleValue());
						} else if (BigInteger.class.isInstance(obj)) {
							objDouble = new Double(((BigInteger) obj).doubleValue());
						} else {
							objDouble = (Double) obj;
						}
					}
					if (objDouble == null) {
						objDouble = new Double(0);
					}
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.String")) {
					if (obj == null) {
						obj = new String("");
					}
					objString1 = (String) obj;
				}
			}

			dados.setValue(objString1, objDouble.doubleValue());
		}
		if (is3D) {
			chart = ChartFactory.createPieChart3D(UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), dados, true, true, false);
		} else {
			chart = ChartFactory.createPieChart(UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), dados, true, true, false);
		}

		chart.setBackgroundPaint(COR_FUNDO);
		chart.getTitle().setPaint(COR_TITULO);
		chart.getTitle().setFont(new java.awt.Font("arial", Font.BOLD, 12));
		chart.getPlot().setOutlinePaint(null);
		chart.getPlot().setBackgroundPaint(new Color(221, 227, 213));

		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setNoDataMessage(UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaDadosParaApresentar"));

		if(gerencialItemDto.isPorcentagem())
			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("({0}) {1} - {2}"));
		else
			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}", new DecimalFormat("0"), new DecimalFormat("0")));

		String caminhoRelativo = "";
		String caminho = "";
		try {
			File arquivo = new File(infoGenerate.getCaminhoArquivosGraficos());
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}
			// caminho = infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario() + "/" +
			// UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".png";
			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".png";

			caminho = infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_"
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";
			String urlInicial = "";
			try {
				//urlInicial = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (urlInicial == null || urlInicial.trim().equalsIgnoreCase("")){
				urlInicial = Constantes.getValue("CONTEXTO_APLICACAO");
			}
			caminhoRelativo = urlInicial + "/tempFiles" + "/" + usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription()))
					+ "_" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";

			caminhoRelativoAuxiliar = caminhoRelativo;

			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			int tam = Integer.parseInt(UtilStrings.apenasNumeros(gerencialItemPainelAuxDto.getWidth()));
			int alt = Integer.parseInt(UtilStrings.apenasNumeros(gerencialItemPainelAuxDto.getHeigth()));

			tam = tam - 15;
			alt = alt - 70;
			ChartUtilities.saveChartAsPNG(arquivo, chart, tam, alt);
			LOGGER.info(UtilI18N.internacionaliza(request, "citcorpore.comum.graficoPizzaGerado") + ":\n\t" + caminho);

		} catch (IOException e) {
			LOGGER.error(UtilI18N.internacionaliza(request, "citcorpore.comum.problemasDuranteCriacaoGraficoBarras"), e);
		}

		if(listRetorno != null && listRetorno.size()!= 0){
			return "<img src=\"" + caminhoRelativo + "\"/>";
		} else {
			return null;
		}
	}

	private Object generateRetornoGrafico_Linha(boolean is3D, List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, HttpServletRequest request) throws Exception {
		JFreeChart chart;
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		Double objDouble;
		String objString1;
		String objString2 = gerencialItemDto.getTitle();
		int posString = 0;
		for (int i = 0; i < listRetorno.size(); i++) {
			Object[] row = (Object[]) listRetorno.get(i);

			objDouble = new Double(0);
			objString1 = "";
			objString2 = gerencialItemDto.getTitle();
			posString = 0;
			for (int j = 0; j < row.length; j++) {
				Object obj = row[j];
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).intValue());
					} else if (Long.class.isInstance(obj)) {
						objDouble = new Double(((Long) obj).intValue());
					}
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
					objDouble = null;
					if (Integer.class.isInstance(obj)) {
						objDouble = new Double(((Integer) obj).doubleValue());
					} else {
						if (BigDecimal.class.isInstance(obj)) {
							objDouble = new Double(((BigDecimal) obj).doubleValue());
						} else if (Long.class.isInstance(obj)) {
							objDouble = new Double(((Long) obj).doubleValue());
						} else if (Integer.class.isInstance(obj)) {
							objDouble = new Double(((Integer) obj).doubleValue());
						} else if (BigInteger.class.isInstance(obj)) {
							objDouble = new Double(((BigInteger) obj).doubleValue());
						} else {
							objDouble = (Double) obj;
						}
					}
					if (objDouble == null) {
						objDouble = new Double(0);
					}
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.String")) {
					if (obj == null) {
						obj = new String("");
					}
					String str = "";
					if (Integer.class.isInstance(obj)) {
						str = "" + (Integer) obj;
					} else {
						if (BigDecimal.class.isInstance(obj)) {
							str = obj.toString();
						} else {
							str = (String) obj;
						}
					}
					if (posString == 0) {
						objString1 = str;
					} else {
						objString2 = str;
					}
					posString++;
				}
			}
			dados.addValue(objDouble.doubleValue(), objString2, objString1);
		}
		if (is3D) {
			chart = ChartFactory.createLineChart3D(UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), null, null, dados, PlotOrientation.VERTICAL, true, true, false);
		} else {
			chart = ChartFactory.createLineChart(UtilI18N.internacionaliza(request, gerencialItemDto.getTitle()), null, null, dados, PlotOrientation.VERTICAL, true, true, false);
		}

		// Setando o valor maximo para nunca passar de 100, ja q se trata de
		// porcentagem
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.09);

		// Formatando cores de fundo, fonte do titulo, etc...
		chart.setBackgroundPaint(COR_FUNDO); // Cor do fundo do grafico
		chart.getTitle().setPaint(COR_TITULO); // Cor do titulo
		chart.getTitle().setFont(new java.awt.Font("arial", Font.BOLD, 12)); // Fonte do
																				// titulo
		chart.getPlot().setBackgroundPaint(new Color(221, 227, 213));// Cor de
																		// fundo da
																		// plot (area
																		// do grafico)
		chart.setBorderVisible(false); // Visibilidade da borda do grafico

		// Marcador de Mídia de Resolubilidade
		// IntervalMarker target = new IntervalMarker(y - 0.3, y + 0.3);// A
		// principio, a mídia será o TOTAL-MF
		/*
		 * target.setLabel(" Resolubilidade Midia"); target.setLabelFont(new Font("arial", Font.BOLD, 12)); target.setLabelPaint(Color.RED); target.setLabelAnchor(RectangleAnchor.CENTER);
		 * target.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
		 */
		// target.setPaint(Color.RED); // Cor da linha marcadora
		// plot.addRangeMarker(target, Layer.FOREGROUND);

		String caminhoRelativo = "";
		String caminho = "";
		try {
			File arquivo = new File(infoGenerate.getCaminhoArquivosGraficos());
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}

			// caminho = infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario() + "/" +
			// UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".png";
			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".png";

			caminho = infoGenerate.getCaminhoArquivosGraficos() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_"
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";

			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_" +
			// UtilDatas.formatTimestamp(UtilDatas.getDataHoraAtual()).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";
			String urlInicial = "";
			if (urlInicial == null || urlInicial.trim().equalsIgnoreCase("")){
				urlInicial = Constantes.getValue("CONTEXTO_APLICACAO");
			}
			caminhoRelativo = urlInicial +  "/tempFiles" + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "_"
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_") + ".png";

			caminhoRelativoAuxiliar = caminhoRelativo;

			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			int tam = Integer.parseInt(UtilStrings.apenasNumeros(gerencialItemPainelAuxDto.getWidth()));
			int alt = Integer.parseInt(UtilStrings.apenasNumeros(gerencialItemPainelAuxDto.getHeigth()));

			tam = tam - 15;
			alt = alt - 70;

			ChartUtilities.saveChartAsPNG(arquivo, chart, tam, alt);
			LOGGER.info("Grafico de Linhas gerado em:\n\t" + caminho);

		} catch (IOException e) {
			LOGGER.error("Problemas durante a criação do Gráfico de Barras.", e);
		}

		if(listRetorno != null && listRetorno.size()!= 0){
			return "<img src=\"" + caminhoRelativo + "\"/>";
		} else {
			return null;
		}
	}

	private Object generateRetornoPDF(List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto,
			HttpServletRequest request) throws Exception {
		if (infoGenerate.getTipoSaidaApresentada().equalsIgnoreCase("GRAPH") && gerencialItemDto.isGraph()) {
			return generateRetornoPDF_Graph(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros,
					gerencialPainelDto, request);
		} else {
			return generateRetornoPDF_Table(listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros,
					gerencialPainelDto, request);
		}
	}

	private Object generateRetornoPDF_Table(List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto,
			HttpServletRequest request) throws LogicException {

		if (listRetorno == null) {
			return null;
		}

		Double[][] totals = new Double[100][100];
		Double[][] counts = new Double[100][100];
		String[][] anteriorValue = new String[100][100];

		Double[] totalLinha = new Double[100];
		Double[] countLinha = new Double[100];

		boolean[][] isTotals = new boolean[100][100];
		boolean[][] isCounts = new boolean[100][100];
		boolean[] temItensNoGrupo = new boolean[100];

		int colunasGrupos[] = new int[100];

		int qtdeColunas = 0;
		int qtdeColunasSemAgrupadores = 0;
		int qtdeColunasAgrupadoras = 0;

		boolean temTotals = false;
		boolean temCounts = false;

		boolean existeAgrupador = false;

		Document document = null;
		if ("L".equalsIgnoreCase(gerencialItemDto.getReportPageLayout())) { // Se Landscape
			document = new Document(PageSize.A4.rotate(), 20, 20, 100, 40);
		} else {
			document = new Document(PageSize.A4, 20, 20, 100, 40);
		}

		PdfWriter writer = null;

		if (usuario.getIdUsuario() == null || usuario.getIdUsuario().equalsIgnoreCase("null")) {
			usuario.setIdUsuario("0");
		}

		String caminho = "";
		String caminhoRelativo = "";
		try {
			File arquivo = new File(infoGenerate.getCaminhoArquivosPdfs());
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}
			caminho = infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription()))
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_")
					+".pdf";
			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".pdf";
			String urlInicial = "";
			if (urlInicial == null || urlInicial.trim().equalsIgnoreCase("")){
				urlInicial = Constantes.getValue("CONTEXTO_APLICACAO");
			}
			caminhoRelativo = urlInicial +  "/tempFiles" + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription()))
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_")
					+ ".pdf";

			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}

			writer = PdfWriter.getInstance(document, new FileOutputStream(caminho));

			document.open();
		} catch (Exception e) {
			e.printStackTrace();
			// handle exception
		}

		int tamTabela = 1;
		int[] tamanhoColunas = null;
		int[] tamanhoColunasReal = null;
		int iAux = 0;
		if (listRetorno != null && listRetorno.size() > 0) {
			Object[] row = (Object[]) listRetorno.get(0);

			tamanhoColunas = new int[row.length];
			for (int j = 0; j < row.length; j++) {
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

				GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());
				if (grupoDefinicaoDto == null) { // So mostra se nao for um agrupador
					qtdeColunasSemAgrupadores++;
					tamanhoColunas[iAux] = Integer.parseInt(UtilStrings.apenasNumeros(fieldDto.getWidth()));
					iAux++;
				} else {
					colunasGrupos[qtdeColunasAgrupadoras] = j;
					qtdeColunasAgrupadoras++;
					existeAgrupador = true;
				}
			}
			tamTabela = qtdeColunasSemAgrupadores;
		}

		tamanhoColunasReal = new int[qtdeColunasSemAgrupadores];
		for (int j = 0; j < qtdeColunasSemAgrupadores; j++) {
			tamanhoColunasReal[j] = tamanhoColunas[j];
		}

		PdfPTable table = new PdfPTable(tamTabela);
		table.setWidthPercentage(100);

		Rectangle page = document.getPageSize();

		if (page.getWidth() > 600) {
			PdfPCell prime1 = new PdfPCell();
			prime1.setPhrase(new Phrase(" "));
			prime1.setColspan(tamTabela);
			prime1.setBorder(0);
			PdfPCell prime2 = new PdfPCell();
			prime2.setPhrase(new Phrase(" "));
			prime2.setColspan(tamTabela);
			table.addCell(prime1);
			table.addCell(prime2);
		}

		try {
			if (tamanhoColunasReal != null && tamanhoColunasReal.length > 0) {
				table.setWidths(tamanhoColunasReal);
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

		EndPageControl endPageControl = new EndPageControl(existeAgrupador, gerencialItemDto, table, listRetorno, tamTabela, tamanhoColunasReal, UtilI18N.internacionaliza(request,
				gerencialItemDto.getTitle()), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto, request);
		writer.setPageEvent(endPageControl);

		for (int i = 0; i < listRetorno.size(); i++) {
			Object[] row = (Object[]) listRetorno.get(i);

			boolean quebrouLinha = false;
			int indiceGrupoAdicionado = 0;

			for (int x = 0; x < totalLinha.length; x++) {
				totalLinha[x] = new Double(0);
				countLinha[x] = new Double(0);
			}

			int j = 0;

			PdfPCell[] cell = new PdfPCell[100];
			qtdeColunas = row.length;
			for (int z = 0; z < row.length; z++) {
				Object obj = row[z];
				GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(z);
				GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());

				if (obj != null) {
					String valorAtualAux = null;

					if (totals[totals.length - 1][z] == null) {
						totals[totals.length - 1][z] = new Double(0);
					}
					if (counts[totals.length - 1][z] == null) {
						counts[totals.length - 1][z] = new Double(0);
					}

					// O trecho abaixo server para controlar totalizacoes dos grupos
					if (totalLinha[z] == null) {
						totalLinha[z] = new Double(0);
					}
					if (countLinha[z] == null) {
						countLinha[z] = new Double(0);
					}

					if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.String")) {
						String objString = "";
						if (Integer.class.isInstance(obj)) {
							objString = "" + ((Integer) obj).intValue();
						} else {
							if (BigDecimal.class.isInstance(obj)) {
								objString = obj.toString();
							} else {
								objString = (String) obj;
							}
						}

						valorAtualAux = objString;

						if (grupoDefinicaoDto == null) {
							if (cell[j] == null) {
								cell[j] = new PdfPCell();
							}
							cell[j].setHorizontalAlignment(Element.ALIGN_LEFT);
							if (objString != null) {
								cell[j].setPhrase(new Phrase(objString));
							} else {
								cell[j].setPhrase(new Phrase(" "));
							}
						}

						if (fieldDto.isCount()) {
							temCounts = true;
							counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
							isCounts[counts.length - 1][z] = true;

							countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
						}
					}
					if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
						Double objDouble = null;
						if (Integer.class.isInstance(obj)) {
							objDouble = new Double(((Integer) obj).doubleValue());
						} else {
							if (BigDecimal.class.isInstance(obj)) {
								objDouble = new Double(((BigDecimal) obj).doubleValue());
							} else if (Long.class.isInstance(obj)) {
								objDouble = new Double(((Long) obj).doubleValue());
							} else if (Integer.class.isInstance(obj)) {
								objDouble = new Double(((Integer) obj).doubleValue());
							} else if (BigInteger.class.isInstance(obj)) {
								objDouble = new Double(((BigInteger) obj).doubleValue());
							} else {
								objDouble = (Double) obj;
							}
						}
						if (objDouble == null) {
							objDouble = new Double(0);
						}

						valorAtualAux = objDouble.toString();

						if (grupoDefinicaoDto == null) {
							if (cell[j] == null) {
								cell[j] = new PdfPCell();
							}
							cell[j].setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell[j].setPhrase(new Phrase(UtilFormatacao.formatDouble(objDouble, fieldDto.getDecimals().intValue())));
						}

						if (fieldDto.isTotals()) {
							temTotals = true;
							totals[counts.length - 1][z] = new Double(totals[counts.length - 1][z].doubleValue() + objDouble.doubleValue());
							isTotals[counts.length - 1][z] = true;

							totalLinha[z] = new Double(totalLinha[z].doubleValue() + objDouble.doubleValue());
						}
						if (fieldDto.isCount()) {
							temCounts = true;
							counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
							isCounts[counts.length - 1][z] = true;

							countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
						}
					}
					if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
						Integer objInteger = null;
						if (Integer.class.isInstance(obj)) {
							objInteger = (Integer) obj;
						} else {
							Long objLong = (Long) obj;
							if (objLong != null) {
								objInteger = new Integer(objLong.intValue());
							} else {
								objInteger = new Integer(0);
							}
						}

						if (grupoDefinicaoDto == null) {
							if (cell[j] == null) {
								cell[j] = new PdfPCell();
							}
							cell[j].setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell[j].setPhrase(new Phrase("" + objInteger));
						}

						if (objInteger != null)
							valorAtualAux = objInteger.toString();
						else
							valorAtualAux = null;

						if (fieldDto.isTotals()) {
							temTotals = true;
							totals[counts.length - 1][z] = new Double(totals[counts.length - 1][z].doubleValue() + objInteger.intValue());
							isTotals[counts.length - 1][z] = true;

							totalLinha[z] = new Double(totalLinha[z].doubleValue() + objInteger.intValue());
						}
						if (fieldDto.isCount()) {
							temCounts = true;
							counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
							isCounts[counts.length - 1][z] = true;

							countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
						}
					}
					if (fieldDto.getClassField().getName().equalsIgnoreCase("java.sql.Date")) {
						Date objDate = null;
						if (Timestamp.class.isInstance(obj)) {
							objDate = new java.sql.Date(((Timestamp) obj).getTime());
						} else if (Date.class.isInstance(obj)) {
							objDate = (Date) obj;
						}

						if (objDate != null) {
							//valorAtualAux = UtilDatas.dateToSTR(objDate);
							valorAtualAux = UtilDatas.convertDateToString(UtilDatas.getTipoDate(objDate.toString(), WebUtil.getLanguage(request)), objDate, WebUtil.getLanguage(request));

							if (grupoDefinicaoDto == null) {
								if (cell[j] == null) {
									cell[j] = new PdfPCell();
								}
								cell[j].setHorizontalAlignment(Element.ALIGN_LEFT);
								cell[j].setPhrase(new Phrase("" + UtilDatas.convertDateToString(UtilDatas.getTipoDate(objDate.toString(), WebUtil.getLanguage(request)), objDate, WebUtil.getLanguage(request))));
							}
						} else {
							valorAtualAux = null;
							if (grupoDefinicaoDto == null) {
								if (cell[j] == null) {
									cell[j] = new PdfPCell();
								}
								cell[j].setHorizontalAlignment(Element.ALIGN_LEFT);
								cell[j].setPhrase(new Phrase(" "));
							}
						}

						if (fieldDto.isCount()) {
							temCounts = true;
							counts[counts.length - 1][z] = new Double(counts[counts.length - 1][z].doubleValue() + 1);
							isCounts[counts.length - 1][z] = true;

							countLinha[z] = new Double(countLinha[z].doubleValue() + 1);
						}
					}

					if (grupoDefinicaoDto != null) { // Encontrou definicao de Grupo para este campo.
						if (!valorAtualAux.equalsIgnoreCase(anteriorValue[0][z])) {
							// Quebrou - Entra neste trecho

							// Verifica se tem totalizacao, se sim, entao gera os totais.
							if (temCounts || temTotals) {
								for (int x = temItensNoGrupo.length - 1; x >= z; x--) {
									if (temItensNoGrupo[x]) {
										geraTotaisPDF(table, qtdeColunas, x, gerencialItemDto, isCounts, isTotals, totals, counts, infoGenerate.getHashParametros());
										PdfPCell p = new PdfPCell();
										p.setHorizontalAlignment(Element.ALIGN_LEFT);
										p.setColspan(qtdeColunasSemAgrupadores);
										p.setPhrase(new Phrase(" "));
										p.setBorder(Rectangle.NO_BORDER);
										table.addCell(p);
									}
								}
								for (int x = z; x < temItensNoGrupo.length; x++) {
									temItensNoGrupo[x] = false;
								}
							}

							indiceGrupoAdicionado = z;

							String aux = "";
							for (int x = 0; x < z; x++)
								aux += "    ";

							PdfPCell p = new PdfPCell();
							p.setHorizontalAlignment(Element.ALIGN_LEFT);
							p.setColspan(qtdeColunasSemAgrupadores);
							if (fieldDto.getTitle() != null && !fieldDto.getTitle().trim().equalsIgnoreCase("")) {
								p.setPhrase(new Phrase(aux + UtilI18N.internacionaliza(request, fieldDto.getTitle()) + ": " + valorAtualAux + ""));
							} else {
								p.setPhrase(new Phrase(aux + valorAtualAux + ""));
							}
							p.setBackgroundColor(Color.LIGHT_GRAY);
							table.addCell(p);

							quebrouLinha = true;
						}
					} else {
						j++;
					}

					anteriorValue[0][z] = valorAtualAux; // O Valor Atual passa a ser o Valor Anterior.

				} else {
					if (grupoDefinicaoDto == null) {
						if (cell[j] == null) {
							cell[j] = new PdfPCell();
						}
						cell[j].setHorizontalAlignment(Element.ALIGN_LEFT);
						cell[j].setPhrase(new Phrase(" "));

						j++;
						// table.addCell(" ");
					}
				}
			}

			if (quebrouLinha) {
				geraCabecalhoPDF(row.length, gerencialItemDto, table, request);
			}

			for (int x = 0; x < qtdeColunasAgrupadoras; x++) {
				for (int z = 0; z < qtdeColunas; z++) {
					if (totals[colunasGrupos[x]][z] == null) {
						totals[colunasGrupos[x]][z] = new Double(0);
					}
					if (counts[colunasGrupos[x]][z] == null) {
						counts[colunasGrupos[x]][z] = new Double(0);
					}
					if (totalLinha[z] == null) {
						totalLinha[z] = new Double(0);
					}
					if (countLinha[z] == null) {
						countLinha[z] = new Double(0);
					}
					totals[colunasGrupos[x]][z] = new Double(totals[colunasGrupos[x]][z].doubleValue() + totalLinha[z].doubleValue());
					counts[colunasGrupos[x]][z] = new Double(counts[colunasGrupos[x]][z].doubleValue() + countLinha[z].doubleValue());
				}
			}

			for (int x = 0; x < j; x++) {
				if (cell[x] != null) {
					table.addCell(cell[x]);
				}
			}

			if (existeAgrupador)
				temItensNoGrupo[indiceGrupoAdicionado] = true;
		}

		if (temCounts || temTotals) {
			for (int x = temItensNoGrupo.length - 1; x >= 0; x--) {
				if (temItensNoGrupo[x]) {
					geraTotaisPDF(table, qtdeColunas, x, gerencialItemDto, isCounts, isTotals, totals, counts, infoGenerate.getHashParametros());

					//Adiciona uma linha em branco
					PdfPCell space = new PdfPCell();
					space.setHorizontalAlignment(Element.ALIGN_LEFT);
					space.setColspan(qtdeColunasSemAgrupadores);
					space.setPhrase(new Phrase(" "));
					space.setBorder(Rectangle.NO_BORDER);
					table.addCell(space);
				}
			}

			// Adiciona uma linha em branco
			PdfPCell space = new PdfPCell();
			space.setHorizontalAlignment(Element.ALIGN_LEFT);
			space.setColspan(qtdeColunasSemAgrupadores);
			space.setPhrase(new Phrase(" "));
			space.setBorder(Rectangle.NO_BORDER);
			table.addCell(space);

			/*
			 * Rodrigo Pecci Acorse - 30/01/2014 10h00 - #132390
			 * Adiciona a soma total dos itens no relatório
			 */
			String somaTotal = geraSomaTotal(listRetorno, gerencialItemPainelAuxDto, gerencialItemDto, request);

			if (!somaTotal.equals("")) {
				// Adiciona uma linha em branco
				PdfPCell space1 = new PdfPCell();
				space1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				space1.setPhrase(new Phrase(" "));
				//space.setBackgroundColor(Color.LIGHT_GRAY);
				space1.setBorder(Rectangle.NO_BORDER);
				table.addCell(space1);

				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				PdfPCell cell = new PdfPCell();
				cell.setBackgroundColor(Color.LIGHT_GRAY);
				cell.setPhrase(new Phrase(somaTotal));
				table.addCell(cell);
			}

			//Adiciona o total de registros (linhas) no relatório
			geraTotaisPDF(table, qtdeColunas, counts.length - 1, gerencialItemDto, isCounts, isTotals, totals, counts, infoGenerate.getHashParametros());
		}

		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		try {
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(listRetorno!= null && listRetorno.size() !=0){
			return caminhoRelativo;
		} else {
			return null;
		}
	}

	public void geraCabecalhoPDF(int tam, GerencialItemInformationDTO gerencialItemDto, PdfPTable table, HttpServletRequest request) {
		for (int j = 0; j < tam; j++) {
			PdfPCell cell = new PdfPCell();
			GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

			GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());
			if (grupoDefinicaoDto == null) { // So mostra se nao for um agrupador
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.String")) {
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Integer")) {
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				if (fieldDto.getClassField().getName().equalsIgnoreCase("java.sql.Date")) {
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				cell.setBackgroundColor(Color.GRAY);
				cell.setPhrase(new Phrase(UtilI18N.internacionaliza(request, fieldDto.getTitle())));
				table.addCell(cell);
			}
		}
	}

	private void geraTotaisPDF(PdfPTable table, int qtdeColunas, int indice, GerencialItemInformationDTO gerencialItemDto, boolean[][] isCounts, boolean[][] isTotals, Double[][] totals,
			Double[][] counts, HashMap parametersValues) {
		boolean jaEntrou = false;
		String quantidadeTotalRegistros = (String) parametersValues.get("citcorpore.comum.quantidadeDeOrigens");
		String quantidadeRegistros = (String) parametersValues.get("citcorpore.comum.quantidadeRegistros");
		for (int j = 0; j < qtdeColunas; j++) {
			GerencialFieldDTO fieldDto = (GerencialFieldDTO) ((List) gerencialItemDto.getListFields()).get(j);

			GerencialGroupDTO grupoDefinicaoDto = fieldInGroupDefinition(fieldDto.getName(), gerencialItemDto.getListGroups());
			if (grupoDefinicaoDto == null) {
				if (isCounts[counts.length - 1][j]) {
					jaEntrou = true;

					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					PdfPCell cell = new PdfPCell();
					cell.setBackgroundColor(Color.LIGHT_GRAY);
					String strQtdeItens = quantidadeRegistros + ": ";
					if (indice == counts.length - 1) {
						strQtdeItens = quantidadeTotalRegistros + ": ";
					}
					cell.setPhrase(new Phrase(strQtdeItens + UtilFormatacao.formatDouble(counts[indice][j], 0)));
					table.addCell(cell);

					counts[indice][j] = new Double(0);
				} else if (isTotals[counts.length - 1][j]) {
					jaEntrou = true;

					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
					PdfPCell cell = new PdfPCell();
					cell.setBackgroundColor(Color.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					if (fieldDto.getClassField().getName().equalsIgnoreCase("java.lang.Double")) {
						cell.setPhrase(new Phrase("" + UtilFormatacao.formatDouble(totals[indice][j], 2)));
					} else {
						cell.setPhrase(new Phrase("" + UtilFormatacao.formatDouble(totals[indice][j], 0)));
					}
					table.addCell(cell);

					totals[indice][j] = new Double(0);
				} else {
					PdfPCell cell = new PdfPCell();
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(" "));
					if (!jaEntrou) {
						cell.setBorder(Rectangle.NO_BORDER);
					} else {
						cell.setBackgroundColor(Color.LIGHT_GRAY);
					}
					table.addCell(cell);
				}
			}
		}
	}

	//
	private Object generateRetornoPDF_Graph(List listRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto,
			HttpServletRequest request) throws Exception {
		if (infoGenerate.getGraphType()!=null){
			if (infoGenerate.getGraphType().equalsIgnoreCase("BARRA3D")) {
				generateRetornoGrafico_Barra(true, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("BARRA2D")) {
				generateRetornoGrafico_Barra(false, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("PIZZA3D")) {
				generateRetornoGrafico_Pizza(true, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("PIZZA2D")) {
				generateRetornoGrafico_Pizza(false, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("LINHA3D")) {
				generateRetornoGrafico_Linha(true, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else if (infoGenerate.getGraphType().equalsIgnoreCase("LINHA2D")) {
				generateRetornoGrafico_Linha(false, listRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, request);
			} else {
				return null;
			}
		} else {
			return null;
		}

		// String strUrlGraph = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
		// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".png";

		String strUrlGraph = caminhoRelativoAuxiliar;

		String [] replace = strUrlGraph.split("citsmart/tempFiles");

		Document document = null;
		PdfWriter writer = null;
		if ("L".equalsIgnoreCase(gerencialItemDto.getReportPageLayout())) { // Se Landscape
			document = new Document(PageSize.A4.rotate());
			document.setMargins(30, 10, 60, 30);// cleison

		} else {
			//document = new Document();
			//document.setMargins(10, 10, 70 , 40);// cleison
			document = new Document(PageSize.A4, 25, 20, 100, 30);
		}

		String caminho = "";
		String caminhoRelativo = "";
		try {
			File arquivo = new File(infoGenerate.getCaminhoArquivosPdfs());
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}
			caminho = infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription()))
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_")
					+".pdf";
			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".pdf";//
			String urlInicial = "";
			if (urlInicial == null || urlInicial.trim().equalsIgnoreCase("")){
				urlInicial = Constantes.getValue("CONTEXTO_APLICACAO");
			}
			caminhoRelativo = urlInicial + "/tempFiles" + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription()))
					+ UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_")
					+".pdf";

			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}

			writer = PdfWriter.getInstance(document, new FileOutputStream(caminho));
			writer.setPageEvent(new EndPageControl(false, null, null, null, 0, null, gerencialItemDto.getTitle(), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto,
					request));

			document.open();
			// document.add(new Paragraph(gerencialItemDto.getTitle()));
			// document.add(new Paragraph(" "));
		} catch (Exception e) {
			e.printStackTrace();
			// handle exception
		}

		try {

			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			// document.add(new Paragraph(gerencialItemDto.getTitle()));
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}

		//foi comentado para funcionar corretamente, não sei o motivo mas tratar o caminho aqui dava nullpointer mesmo com o caminho certo.

/*		URL url = null;
		try {
			url = new URL(infoGenerate.getCaminhoArquivosPdfs() + replace[1]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}*/

		Image image = null;
		try {
			image = Image.getInstance(infoGenerate.getCaminhoArquivosPdfs() + replace[1]);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image != null) {
			float intW = Integer.parseInt(gerencialItemPainelAuxDto.getWidth());
			float intH = Integer.parseInt(gerencialItemPainelAuxDto.getHeigth());
			float perc = 1;

			if (intW > writer.getPageSize().getWidth()) {
				float intNew = (writer.getPageSize().getWidth() - 50);

				perc = (intNew) / intW;

				intW = intNew;

				intH = intH * perc; // Ajusta proporcionalmente para nao ocorrer distorcoes.
			}
			image.scaleAbsolute(intW, intH);

			try {
				document.add(image);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		/*
		 * Rodrigo Pecci Acorse - 30/01/2014 10h00 - #132390
		 * Adiciona a soma total dos itens no relatório
		 */
		String somaTotal = geraSomaTotal(listRetorno, gerencialItemPainelAuxDto, gerencialItemDto, request);
		if (!somaTotal.equals("")) {
			document.add(new Paragraph(somaTotal));
		}

		document.close();

		if(listRetorno != null && listRetorno.size()!=0){
			return caminhoRelativo;
		} else {
			return null;
		}
	}

	private Object generateRetornoPDF_Buffer(String strRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
			GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto, HttpServletRequest request) {
		/*
		 * Document document = null; if ("L".equalsIgnoreCase(gerencialItemDto.getReportPageLayout())){ //Se Landscape document = new Document(PageSize.A4.rotate()); }else{ document = new Document();
		 * }
		 *
		 * PdfWriter writer = null;
		 */

		String caminho = "";
		String caminho2 = "";
		String caminhoHTML = "";
		String caminhoRelativo = "";

		try {
			File arquivo = new File(infoGenerate.getCaminhoArquivosPdfs());
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}
			caminho = infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".pdf";
			caminho2 = infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "2.pdf";
			caminhoHTML = infoGenerate.getCaminhoArquivosPdfs() + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + ".html";
			// caminhoRelativo = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" +
			// usuario.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "2.pdf";
			String urlInicial = "";
			if (urlInicial == null || urlInicial.trim().equalsIgnoreCase("")){
				urlInicial = Constantes.getValue("CONTEXTO_APLICACAO");
			}
			caminhoRelativo = urlInicial + "/tempFiles" + "/" + usuario.getIdUsuario() + "/"
					+ UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemDto.getDescription())) + "2.pdf";

			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			arquivo = new File(caminhoHTML);
			if (arquivo.exists()) {
				arquivo.delete();
			}

			//codigo abaixo necessário para tratar o encode nos servidores linux
			FileOutputStream fOut = new FileOutputStream(arquivo);
			String str = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" >";
			BufferedWriter out = null;
			try {
				 out = new BufferedWriter(new OutputStreamWriter(fOut,"ISO-8859-1"));
			} catch (Exception e) {
				out = new BufferedWriter(new OutputStreamWriter(fOut));
				e.printStackTrace();
			}
			out.write(str);
			out.close();

			/*
			 * FileOutputStream fHtml = new FileOutputStream(caminhoHTML); fHtml.write(strRetorno.getBytes()); fHtml.flush(); fHtml.close();
			 */

			// writer = PdfWriter.getInstance(document, new FileOutputStream(caminho));
			// writer.setPageEvent(new EndPageControlBuffer(gerencialItemDto.getTitle(), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto));

			// document.open();
			// document.add(new Paragraph(gerencialItemDto.getTitle()));
			// document.add(new Paragraph(" "));
		} catch (Exception e) {
			e.printStackTrace();
			// handle exception
		}

		/*
		 * try { Html2Pdf.convert(document, caminhoHTML); } catch (DocumentException e) { e.printStackTrace(); }
		 */

		// document.close();

		OutputStream os;
		try {
			os = new FileOutputStream(caminho);
			Html2Pdf.convert(strRetorno, os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			PdfReader reader = new PdfReader(caminho);
			Rectangle r = reader.getPageSizeWithRotation(1);
			Document document = new Document(r);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(caminho2));;
			if (gerencialItemPainelAuxDto != null && gerencialItemPainelAuxDto.getFile() != null && gerencialItemPainelAuxDto.getFile().equalsIgnoreCase("xmlsGerenciais/requisicoesProdutos/requisicoesProdGeral.xml")) {
				document.setMargins(20, 50, 70, 0);
				writer.setPageEvent(new EndPageGerencialControl(false, null, null, null, 0, null, gerencialItemDto.getTitle(), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto, request));
			}else{
				document.setMargins(50, 50, 70, 40);
				writer.setPageEvent(new EndPageControlBuffer(gerencialItemDto.getTitle(), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto));
			}

			document.open();
			int total = reader.getNumberOfPages() + 1;
			for (int i = 1; i < total; i++) {
				PdfImportedPage pageImport = writer.getImportedPage(reader, i);
				Image image = Image.getInstance(pageImport);
				image.scalePercent(94f);
				document.add(image);
			}
			document.close();
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}

		/*
		 * try{ PdfContentByte over; PdfReader reader = new PdfReader(caminho); Document document = new Document(reader.getPageSizeWithRotation(1)); PdfStamper copy = new PdfStamper(reader, new
		 * FileOutputStream(caminho2));
		 *
		 * EndPageControl evt = new EndPageControl(gerencialItemDto.getTitle(), colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto); document.open(); int total =
		 * reader.getNumberOfPages() + 1; for (int i = 1; i < total; i++) { over = copy.getUnderContent(i); over.beginText();
		 *
		 * Rectangle page = document.getPageSize();
		 *
		 * PdfPTable head = new PdfPTable(2); try { double tam1 = page.getWidth() * 0.17; double tam2 = page.getWidth() * 0.83;
		 *
		 * int tamX1 = (int) tam1; int tamX2 = (int) tam2; head.setWidths(new int[] {tamX1, tamX2}); } catch (DocumentException e1) { e1.printStackTrace(); } Image image = null; try { // URL url =
		 * this.servletContext.getContext("/").getResource("/imagens/logo.gif"); //URL url = this.getClass().getClassLoader().getResource("/imagens/logo.jpg"); URL url = new
		 * URL(Constantes.getValue("CAMINHO_LOGO_CITGERENCIAL")); if (url != null) { try { image = Image.getInstance(url); } catch (BadElementException e) { e.printStackTrace(); } } }catch
		 * (IOException ioe) { ioe.printStackTrace(); } if (image != null) { //image.scaleAbsolute(40, 54); image.scaleAbsolute(150, 84); image.setAlignment(Image.RIGHT); Chunk ck = new Chunk(image,
		 * -3, -60); PdfPCell c1 = new PdfPCell(); c1.addElement(ck); c1.setBorderWidth(0); head.addCell(c1); } else { //PdfPCell c1 = new PdfPCell(); head.addCell(""); }
		 *
		 * String strCab = Constantes.getValue("TEXTO_1a_LINHA_CABECALHO_CITGERENCIAL"); if (strCab != null && !strCab.equalsIgnoreCase("")){ PdfPCell cAux = new PdfPCell(new Phrase(strCab, new
		 * Font(Font.HELVETICA, 12, Font.BOLD, new Color(0, 0, 0)))); cAux.setBorderWidth(0); cAux.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); head.addCell(cAux);
		 *
		 * cAux = new PdfPCell(new Phrase("")); cAux.setBorderWidth(0); cAux.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); head.addCell(cAux); }
		 *
		 * PdfPCell cAux = new PdfPCell(new Phrase(gerencialItemDto.getTitle(), new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0, 0, 0)))); cAux.setBorderWidth(0);
		 * cAux.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); head.addCell(cAux);
		 *
		 * //Trata parametros String strFiltro = evt.trataParameters(hshParameters, colParmsUtilizadosNoSQL, colDefinicaoParametros);
		 *
		 * PdfPCell cFiltro = new PdfPCell(new Phrase("")); cFiltro.setBorderWidth(0); cFiltro.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); head.addCell(cFiltro);
		 *
		 * cFiltro = new PdfPCell(new Phrase(strFiltro, new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0)))); cFiltro.setBorderWidth(0); cFiltro.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		 * head.addCell(cFiltro); //Fim - Trata parametros
		 *
		 * head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin()); if (page.getWidth() > 600){ head.writeSelectedRows(0, -1, document.leftMargin(), 585, over); }else{
		 * head.writeSelectedRows(0, -1, document.leftMargin(), 825, over); }
		 *
		 * PdfPTable foot = new PdfPTable(2); String strSistema = Constantes.getValue("TEXTO_1a_LINHA_RODAPE_CITGERENCIAL"); if (strSistema != null && !strSistema.equalsIgnoreCase("")){ PdfPCell
		 * cAuxSistema = new PdfPCell( new Phrase(strSistema, new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0)))); cAuxSistema.setColspan(2); foot.addCell(cAuxSistema); }
		 *
		 * PdfPCell cAuxPageNumber = new PdfPCell( new Phrase("Emissão: " + UtilDatas.dateToSTR(UtilDatas.getDataAtual()) + " " + UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()), new
		 * Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0)))); foot.addCell(cAuxPageNumber);
		 *
		 * cAuxPageNumber = new PdfPCell(new Phrase("Pagina: " + i, new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0)))); cAuxPageNumber.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		 * foot.addCell(cAuxPageNumber);
		 *
		 * foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin()); foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), over);
		 *
		 * over.endText(); over.stroke(); } copy.close(); document.close(); } catch (Exception e) { throw new ExceptionConverter(e); }
		 */

		if(strRetorno!= null && strRetorno!=""){
			return caminhoRelativo;
		} else {
			return null;
		}
	}

	   private String generateRetorno_Buffer(String strRetorno, GerencialItemInformationDTO gerencialItemDto, GerencialInfoGenerateDTO infoGenerate, Usuario usuario,
	            GerencialItemPainelDTO gerencialItemPainelAuxDto, Collection colParmsUtilizadosNoSQL, HashMap hshParameters, Collection colDefinicaoParametros, GerencialPainelDTO gerencialPainelDto, HttpServletRequest request) {
		   if(infoGenerate.getSaida().equalsIgnoreCase("PDF"))
			   return (String) generateRetornoPDF_Buffer(strRetorno, gerencialItemDto, infoGenerate, usuario, gerencialItemPainelAuxDto, colParmsUtilizadosNoSQL, hshParameters, colDefinicaoParametros, gerencialPainelDto, request);
	        return strRetorno;
	    }

	public Object generateByQuestaoQuestionario(Integer idQuestaoQuestionario, Usuario usuario, GerencialInfoGenerateDTO infoGenerate) throws ServiceException {
		return null;
	}

}
