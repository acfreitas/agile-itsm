package br.com.centralit.citgerencial.pdf;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilImagem;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.bean.GerencialFieldDTO;
import br.com.centralit.citgerencial.bean.GerencialGroupDTO;
import br.com.centralit.citgerencial.bean.GerencialItemInformationDTO;
import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialParameterDTO;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings({"rawtypes"})
public class EndPageGerencialControl extends PdfPageEventHelper {
	private String titleReport;
	private Collection colParmsUtilizadosNoSQL;
	private HashMap hshParameters;
	private Collection colDefinicaoParametros;
	private GerencialPainelDTO gerencialPainelDto;
	private GerencialItemInformationDTO gerencialItemDto;
	private List listRetorno;
	private int tamTabela;
	private int[] tamanhoColunasReal;
	private boolean existeAgrupador;
	private HttpServletRequest request;

	public EndPageGerencialControl(boolean existeAgrupadorParm, GerencialItemInformationDTO gerencialItemDtoParm, PdfPTable tableParm, List listRetornoParm, int tamTabelaParm, int[] tamanhoColunasRealParm,
			String titleReportParm, Collection colParmsUtilizadosNoSQLParm, HashMap hshParametersParm, Collection colDefinicaoParametrosParm, GerencialPainelDTO gerencialPainelDtoParm,
			HttpServletRequest request) {
		this.titleReport = UtilI18N.internacionaliza(request, titleReportParm);
		this.colParmsUtilizadosNoSQL = colParmsUtilizadosNoSQLParm;
		this.hshParameters = hshParametersParm;
		this.colDefinicaoParametros = colDefinicaoParametrosParm;
		this.gerencialPainelDto = gerencialPainelDtoParm;
		this.gerencialItemDto = gerencialItemDtoParm;
		this.listRetorno = listRetornoParm;
		this.tamTabela = tamTabelaParm;
		this.tamanhoColunasReal = tamanhoColunasRealParm;
		this.existeAgrupador = existeAgrupadorParm;
		this.request = request;
	}

	public void onStartPage(PdfWriter writer, Document document) {
		super.onStartPage(writer, document);
	}

	public void onEndPage(PdfWriter writer, Document document) {
		try {
			/*Adicionado o header*/
			Rectangle page = document.getPageSize();
			PdfPTable header = new PdfPTable(1);
            header.setTotalWidth(550);
			header.setLockedWidth(true);
			header.getDefaultCell().setFixedHeight(55);
			header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

			PdfPCell cellC = new PdfPCell();
			cellC.setBorder(1);

			PdfPTable tableContent = new PdfPTable(3);

			/* Adicionando a LogoMarca*/
			URL url = null;
			String caminho = "";
			String urlInicial = "";
			Image image = null;
			caminho = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_LOGO_PADRAO_RELATORIO, "");

			if("".equals(caminho.trim()) || !UtilImagem.verificaSeImagemExiste(caminho)) {
				urlInicial = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "");
				caminho = urlInicial + "/imagens/logo/logo.png";
			}

			try {
			    url = new URL(caminho);
			    URLConnection conn = url.openConnection();
			    conn.connect();
			} catch (MalformedURLException e) {
			    // the URL is not in a valid form
				e.printStackTrace();
				url = null;
				//throw new LogicException("Falha no estabelecimento de conexão com a url");
			} catch (IOException e) {
			    // the connection couldn't be established
				e.printStackTrace();
				url = null;
				//throw new LogicException("Falha no estabelecimento de conexão com a url");
			}

			if (url == null) {
				if (Constantes.getValue("CAMINHO_LOGO_CITGERENCIAL") != null) {
					try {
						url = new URL(Constantes.getValue("CAMINHO_LOGO_CITGERENCIAL"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (url == null) {
				caminho = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/logoPadraoRelatorio.png";
				try {
					url = new URL(caminho);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (url != null) {
				try {
					image = Image.getInstance(url);
				} catch (BadElementException e) {
					e.printStackTrace();
				}
			}

			if (image != null) {
				image.scaleAbsolute(150, 50);
				image.setAlignment(Image.RIGHT);
				Chunk ck = new Chunk(image, -3, -25);
				PdfPCell cell = new PdfPCell();
				cell.addElement(ck);
				cell.setBorderWidth(0);
				cell.setRowspan(2);
				tableContent.addCell(cell);
			} else {
				tableContent.addCell("Citsmart");
			}

			String strCab = Constantes.getValue("TEXTO_1a_LINHA_CABECALHO_CITGERENCIAL");
			if (strCab != null && !strCab.equalsIgnoreCase("")) {
				PdfPCell cAux = new PdfPCell(new Phrase(strCab, new Font(Font.HELVETICA, 12, Font.BOLD, new Color(0, 0, 0))));
				cAux.setColspan(2);
				cAux.setBorderWidth(1);
				cAux.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				tableContent.addCell(cAux);
			}

			/*Adicionado o Titulo do relatório*/
			PdfPCell titulo = new PdfPCell(new Phrase(titleReport, new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0, 0, 0))));
			titulo.setColspan(2);
			titulo.setRowspan(1);
			titulo.setBorderWidth(0);
			titulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tableContent.addCell(titulo);

			/*Adicionado o filtro*/
			String strFiltro = trataParameters(this.hshParameters, this.colParmsUtilizadosNoSQL, this.colDefinicaoParametros);
			if (strFiltro == null) {
				strFiltro = "";
			}
			PdfPCell cFiltro = new PdfPCell(new Phrase(strFiltro, new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
			cFiltro.setBorderWidth(0);
			cFiltro.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cFiltro.setColspan(2);
			tableContent.addCell(cFiltro);
			cellC.addElement(tableContent);
			header.addCell(tableContent);
			//header.writeSelectedRows(0, -1, 20, 805, writer.getDirectContent());

			// Fim - Trata parametros
			if (!existeAgrupador) {
				if (listRetorno != null && listRetorno.size() > 0) {
					Object[] row = (Object[]) listRetorno.get(0);
					// if (!existeAgrupador){
					geraCabecalhoPDF(row.length, gerencialItemDto, header, writer, document, page);
					// }
				}
			}

			if (page.getWidth() > 650) {
				if (!existeAgrupador) {
					//header.writeSelectedRows(0, -1, 20, page.getHeight() - document.topMargin() + header.getTotalHeight(), writer.getDirectContent());
					header.writeSelectedRows(0, -1, 20, 565, writer.getDirectContent());
				} else {
					header.writeSelectedRows(0, -1, 20, 585, writer.getDirectContent());
				}
			} else {
				if (!existeAgrupador) {
					header.writeSelectedRows(0, -1, 20, page.getHeight() - document.topMargin() + header.getTotalHeight(), writer.getDirectContent());
				} else {
					header.writeSelectedRows(0, -1, 20, 805, writer.getDirectContent());
				}
			}

			/*Adicionado o footer*/
			PdfPTable footer = new PdfPTable(2);
			String emissao = (String) this.hshParameters.get("citcorpore.comum.emissao");
			String pagina = (String) this.hshParameters.get("citcorpore.comum.pagina");

			PdfPCell cAuxPageNumber = new PdfPCell(new Phrase(emissao + ": " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, UtilDatas.getDataAtual(), WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()),
					new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
			cAuxPageNumber.setBorder(0);
			footer.addCell(cAuxPageNumber);

			cAuxPageNumber = new PdfPCell(new Phrase(pagina + ": " + writer.getPageNumber(), new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
			cAuxPageNumber.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cAuxPageNumber.setBorder(0);
			footer.addCell(cAuxPageNumber);
			footer.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
			footer.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public String trataParameters(HashMap hsmParms, Collection colParmsUtilizadosNoSQL, Collection colDefinicaoParametros) {
		if (gerencialPainelDto.getClassNameProcessParameters() != null && !gerencialPainelDto.getClassNameProcessParameters().equalsIgnoreCase("")) {
			Class classe = null;
			try {
				classe = Class.forName(gerencialPainelDto.getClassNameProcessParameters());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// Deixa Passar
			}
			if (classe != null) {
				Object objeto = null;
				try {
					objeto = classe.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (objeto != null) {
					Method metodo = Reflexao.findMethod("processParameters", objeto);
					if (metodo != null) {
						Object[] param = new Object[] { hsmParms, colParmsUtilizadosNoSQL, colDefinicaoParametros };
						Object retorno = null;
						try {
							retorno = metodo.invoke(objeto, param);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						if (retorno == null) {
							return "";
						}
						return (String) retorno;
					}
				}
			}
			return "";
		} else {
			if (colParmsUtilizadosNoSQL == null || colParmsUtilizadosNoSQL.size() == 0) {
				return "";
			}
			String strRetorno = "";
			for (Iterator it = colParmsUtilizadosNoSQL.iterator(); it.hasNext();) {
				String nameParm = (String) it.next();
				String valor = (String) hsmParms.get(nameParm);

				strRetorno += getDescricaoParametro(colDefinicaoParametros, nameParm) + ": " + valor;
				strRetorno += "  ";
			}
			return strRetorno;
		}
	}

	private String getDescricaoParametro(Collection colDefinicaoParametros, String nameParm) {
		if (colDefinicaoParametros == null) {
			return "";
		}
		for (Iterator it = colDefinicaoParametros.iterator(); it.hasNext();) {
			GerencialParameterDTO gerencialParameterDTO = (GerencialParameterDTO) it.next();
			String nomeParmAux = "PARAM." + gerencialParameterDTO.getName().trim();
			if (nomeParmAux.equalsIgnoreCase(nameParm)) {
				return gerencialParameterDTO.getDescription();
			}
		}
		return "";
	}

	public void geraCabecalhoPDF(int tam, GerencialItemInformationDTO gerencialItemDto, PdfPTable tableParm, PdfWriter writer, Document document, Rectangle page) {
		PdfPTable table = new PdfPTable(tamTabela);
		table.setWidthPercentage(100);
		try {
			table.setWidths(tamanhoColunasReal);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

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
				cell.setPhrase(new Phrase(fieldDto.getTitle()));
				table.addCell(cell);
			}
		}

		PdfPCell celula0 = new PdfPCell(new Phrase(" "));
		celula0.setColspan(2);
		celula0.setBorder(0);
		tableParm.addCell(celula0);

		PdfPCell celula = new PdfPCell(table);
		celula.setColspan(2);
		tableParm.addCell(celula);
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
}
