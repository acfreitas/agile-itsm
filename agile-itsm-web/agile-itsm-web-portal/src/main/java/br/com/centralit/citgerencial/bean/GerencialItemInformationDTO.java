package br.com.centralit.citgerencial.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class GerencialItemInformationDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996048245518604610L;
	private String type;
	private String description;
	private String hDetailSpacing;
	private String title;
	private boolean report;
	private boolean graph;
	private String reportPageLayout;
	private String defaultVisualization; /* T - Tabela; G:Type - Gráfico + Tipo do Grafico */
	
	private GerencialSQLDTO gerencialSQL = null;
	private GerencialServiceInformationDTO gerencialService = null;
	private GerencialSummaryInformationDTO gerencialSummary = null;
	private Collection listFields;
	private Collection listGroups;
	private Collection listGraphs;
	
	private String classExecute;
	private boolean porcentagem;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isGraph() {
		return graph;
	}
	public void setGraph(boolean graph) {
		this.graph = graph;
	}
	public String getHDetailSpacing() {
		return hDetailSpacing;
	}
	public void setHDetailSpacing(String detailSpacing) {
		hDetailSpacing = detailSpacing;
	}
	public boolean isReport() {
		return report;
	}
	public void setReport(boolean report) {
		this.report = report;
	}
	public String getReportPageLayout() {
		return reportPageLayout;
	}
	public void setReportPageLayout(String reportPageLayout) {
		this.reportPageLayout = reportPageLayout;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GerencialServiceInformationDTO getGerencialService() {
		return gerencialService;
	}
	public void setGerencialService(GerencialServiceInformationDTO gerencialService) {
		this.gerencialService = gerencialService;
	}
	public GerencialSQLDTO getGerencialSQL() {
		return gerencialSQL;
	}
	public void setGerencialSQL(GerencialSQLDTO gerencialSQL) {
		this.gerencialSQL = gerencialSQL;
	}
	public Collection getListFields() {
		return listFields;
	}
	public void setListFields(Collection listFields) {
		this.listFields = listFields;
	}
	public Collection getListGraphs() {
		return listGraphs;
	}
	public void setListGraphs(Collection listGraphs) {
		this.listGraphs = listGraphs;
	}
	public Collection getListGroups() {
		return listGroups;
	}
	public void setListGroups(Collection listGroups) {
		this.listGroups = listGroups;
	}
	public String getDefaultVisualization() {
		return defaultVisualization;
	}
	public void setDefaultVisualization(String defaultVisualization) {
		this.defaultVisualization = defaultVisualization;
	}
	public GerencialSummaryInformationDTO getGerencialSummary() {
		return gerencialSummary;
	}
	public void setGerencialSummary(GerencialSummaryInformationDTO gerencialSummary) {
		this.gerencialSummary = gerencialSummary;
	}
	public String getClassExecute() {
		return classExecute;
	}
	public void setClassExecute(String classExecute) {
		this.classExecute = classExecute;
	}
	public boolean isPorcentagem() {
		return porcentagem;
	}
	public void setPorcentagem(boolean porcentagem) {
		this.porcentagem = porcentagem;
	}
}
