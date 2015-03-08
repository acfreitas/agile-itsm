package br.com.citframework.tld;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class Calendar extends BodyTagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5251526307674678800L;
	private int mes;
	private int ano;
	public int doStartTag() throws JspException {
		//	int dia = 1;
		int diaSemana = 0;
		String data = "";
		Date retorno = null;
		java.util.Calendar c;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatoAux = new DecimalFormat("00");
		data = formatoAux.format(1) + "/" + formatoAux.format(this.getMes()) + "/" + formatoAux.format(this.getAno());
		c = new GregorianCalendar(this.getAno(), this.getMes() -1, 1);
		diaSemana = c.get(java.util.Calendar.DAY_OF_WEEK);	
		int j = 0;
		int calendario[][] = new int[7][6];
		String calendarioStr[][] = new String[7][6];
		String dtAux = "";
		
		for(int i = 1; i <= 31; i++){
			data = formatoAux.format(i) + "/" + formatoAux.format(this.getMes()) + "/" + formatoAux.format(this.getAno());
			try{
				retorno = sdf.parse(data);		
				dtAux = sdf.format(retorno);
				if (!data.equalsIgnoreCase(dtAux)){
					break;
				}
			}catch(Exception e){
				break;
			}
			calendario[diaSemana - 1][j] = i;
			calendarioStr[diaSemana - 1][j] = formatoAux.format(i) + "_" + formatoAux.format(this.getMes()) + "_" + formatoAux.format(this.getAno());
			if (diaSemana >= java.util.Calendar.SATURDAY){
				diaSemana = java.util.Calendar.SUNDAY;
				j++;
			}else{
				diaSemana++;
			}
		}
		
		try{
		pageContext.getOut().print("<table border='1'>");
		pageContext.getOut().print("<tr>");
		pageContext.getOut().print("<td></td>");
		pageContext.getOut().print("<td colspan='7' align='center'><b>"+this.getMes()+"/"+this.getAno()+"</b></td>");
		pageContext.getOut().print("</tr>");
		pageContext.getOut().print("<tr>");
			//pageContext.getOut().print("<td></td>");
			pageContext.getOut().print("<td><b>D</b></td>");
			pageContext.getOut().print("<td><b>S</b></td>");
			pageContext.getOut().print("<td><b>T</b></td>");
			pageContext.getOut().print("<td><b>Q</b></td>");
			pageContext.getOut().print("<td><b>Q</b></td>");
			pageContext.getOut().print("<td><b>S</b></td>");
			pageContext.getOut().print("<td><b>S</b></td>");
		pageContext.getOut().print("</tr>");
		for(int x = 0; x < 6; x++){
			pageContext.getOut().print("<tr>");
			//pageContext.getOut().print("<td style='cursor:hand' onclick=\"eventSemanaCalendario('"+calendarioStr[0][x]+"')\">X</td>");
			for(int i = 0; i < 7; i++){
					if (calendario[i][x] > 0){
						pageContext.getOut().print("<td id='"+calendarioStr[i][x]+"' style='cursor:pointer' onclick=\"eventDiaCalendario('"+calendarioStr[i][x]+"',this)\">"+calendario[i][x]+"</td>");
					}else{
						pageContext.getOut().print("<td>&nbsp;</td>");
					}
			}
			pageContext.getOut().print("</tr>");
		}
		pageContext.getOut().print("</table>");
		}catch(IOException e){
			e.printStackTrace();
			throw new JspException(e);			
		}
		return SKIP_BODY;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
}
