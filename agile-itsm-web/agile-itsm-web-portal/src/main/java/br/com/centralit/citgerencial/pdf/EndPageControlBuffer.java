package br.com.centralit.citgerencial.pdf;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialParameterDTO;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class EndPageControlBuffer extends PdfPageEventHelper {
	private String titleReport;
	private Collection colParmsUtilizadosNoSQL;
	private HashMap hshParameters;
	private Collection colDefinicaoParametros;
	private GerencialPainelDTO gerencialPainelDto;

	public EndPageControlBuffer(String titleReportParm, Collection colParmsUtilizadosNoSQLParm, HashMap hshParametersParm, Collection colDefinicaoParametrosParm,
			GerencialPainelDTO gerencialPainelDtoParm) {
		this.titleReport = titleReportParm;
		this.colParmsUtilizadosNoSQL = colParmsUtilizadosNoSQLParm;
		this.hshParameters = hshParametersParm;
		this.colDefinicaoParametros = colDefinicaoParametrosParm;
		this.gerencialPainelDto = gerencialPainelDtoParm;
	}

	public void onStartPage(PdfWriter writer, Document document) {
		/*
		 * try { document.add(new Paragraph(" ")); document.add(new Paragraph(" ")); document.add(new Paragraph(" ")); document.add(new Paragraph(" ")); //document.add(new
		 * Paragraph(gerencialItemDto.getTitle())); } catch (DocumentException e2) { e2.printStackTrace(); }
		 */
		super.onStartPage(writer, document);
	}

	public void onEndPage(PdfWriter writer, Document document) {
		try {
			Rectangle page = document.getPageSize();

			PdfPTable head = new PdfPTable(2);
			try {
				double tam1 = page.getWidth() * 0.17;
				double tam2 = page.getWidth() * 0.83;

				int tamX1 = (int) tam1;
				int tamX2 = (int) tam2;
				head.setWidths(new int[] { tamX1, tamX2 });
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			Image image = null;
			try {
				// URL url = this.servletContext.getContext("/").getResource("/imagens/logo.gif");
				// URL url = this.getClass().getClassLoader().getResource("/imagens/logo.jpg");
				URL url = new URL(Constantes.getValue("CAMINHO_LOGO_CITGERENCIAL"));
				if (url != null) {
					try {
						image = Image.getInstance(url);
					} catch (BadElementException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			if (image != null) {
				// image.scaleAbsolute(40, 54);
				// image.scaleAbsolute(150, 84);
				image.scaleAbsolute(150, 50);
				image.setAlignment(Image.RIGHT);
				// Chunk ck = new Chunk(image, -3, -60);
				Chunk ck = new Chunk(image, 40, -25);
				PdfPCell c1 = new PdfPCell();
				c1.addElement(ck);
				c1.setBorderWidth(0);
				head.addCell(c1);
			} else {
				// PdfPCell c1 = new PdfPCell();
				head.addCell("");
			}

			String strCab = Constantes.getValue("TEXTO_1a_LINHA_CABECALHO_CITGERENCIAL");
			if (strCab != null && !strCab.equalsIgnoreCase("")) {
				PdfPCell cAux = new PdfPCell(new Phrase(strCab, new Font(Font.HELVETICA, 12, Font.BOLD, new Color(0, 0, 0))));
				cAux.setBorderWidth(0);
				cAux.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				head.addCell(cAux);

				cAux = new PdfPCell(new Phrase(""));
				cAux.setBorderWidth(0);
				cAux.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				head.addCell(cAux);
			}

			PdfPCell cAux = new PdfPCell(new Phrase(titleReport, new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0, 0, 0))));
			cAux.setBorderWidth(0);
			cAux.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			head.addCell(cAux);

			// Trata parametros
			String strFiltro = trataParameters(this.hshParameters, this.colParmsUtilizadosNoSQL, this.colDefinicaoParametros);

			PdfPCell cFiltro = new PdfPCell(new Phrase(""));
			cFiltro.setBorderWidth(0);
			cFiltro.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			head.addCell(cFiltro);

			cFiltro = new PdfPCell(new Phrase(strFiltro, new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
			cFiltro.setBorderWidth(0);
			cFiltro.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			head.addCell(cFiltro);
			// Fim - Trata parametros

			head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
			if (page.getWidth() > 650) {
				/*
				 * head.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin() + head.getTotalHeight(), writer.getDirectContent());
				 */
				head.writeSelectedRows(0, -1, document.leftMargin(), 585, writer.getDirectContent());
			} else {
				head.writeSelectedRows(0, -1, document.leftMargin(), 825, writer.getDirectContent());
			}

			PdfPTable foot = new PdfPTable(2);
			String strSistema = Constantes.getValue("TEXTO_1a_LINHA_RODAPE_CITGERENCIAL");
			if (strSistema != null && !strSistema.equalsIgnoreCase("")) {
				PdfPCell cAuxSistema = new PdfPCell(new Phrase(strSistema, new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
				cAuxSistema.setColspan(2);
				foot.addCell(cAuxSistema);
			}

			String emissao = (String) this.hshParameters.get("citcorpore.comum.emissao");
			String pagina = (String) this.hshParameters.get("citcorpore.comum.pagina");

			PdfPCell cAuxPageNumber = new PdfPCell(new Phrase(emissao + ": " + UtilDatas.dateToSTR(UtilDatas.getDataAtual()) + " " + UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()),
					new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
			foot.addCell(cAuxPageNumber);

			cAuxPageNumber = new PdfPCell(new Phrase(pagina + ": " + writer.getPageNumber(), new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0))));
			cAuxPageNumber.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			foot.addCell(cAuxPageNumber);

			foot.setTotalWidth((page.getWidth() - document.leftMargin() - document.rightMargin()) - 40);
			foot.writeSelectedRows(0, -1, document.leftMargin() + 40, document.bottomMargin() - 30, writer.getDirectContent());
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

}
