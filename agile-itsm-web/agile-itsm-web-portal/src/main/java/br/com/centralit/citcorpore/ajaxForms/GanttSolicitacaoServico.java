/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.GanttSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDemandaServico;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings("rawtypes")
public class GanttSolicitacaoServico extends AjaxFormAction {

    private UsuarioDTO usuarioDto;

    private GanttSolicitacaoServicoDTO ganttSolicitacaoServicoBean;

    @Override
    public Class getBeanClass() {
	return GanttSolicitacaoServicoDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.setUsuarioDto(WebUtil.getUsuario(request));

	this.gerarComboTipoDemandaServico(document,request);
	this.gerarComboGrupoSeguranca(document,request);

	/*
	 * Retirado por Emauri - nao estava apresentando corretamente!
	Collection<SolicitacaoServicoDTO> listaSolicitacaoServico = this.getSolicitacaoServicoService().listSolicitacaoServico(null, null, this.getUsuarioDto());

	if (listaSolicitacaoServico != null && !listaSolicitacaoServico.isEmpty()) {
	    StringBuilder gantt = gerarGantt(listaSolicitacaoServico);
	    document.executeScript(gantt.toString());
	} else {
	    document.executeScript("alert('Nenhum registro encontrado!')");
	    CITCorporeUtil.limparFormulario(document);
	}
	*/
	
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    /**
     * Filtra GANTT de acordo com Tipo de Solicitacao e Grupo de Seguranï¿½a selecionado.
     * 
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author valdoilo
     */
    public void filtrarGantt(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	this.setGanttSolicitacaoServicoBean((IDto) document.getBean());

		if (this.getGanttSolicitacaoServicoBean().getDataInicio() == null){
		    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
		    return;
		}
		if (this.getGanttSolicitacaoServicoBean().getDataFim() == null){
		    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
		    return;
		}
	
		String tipoDemandaServico = new String();
	
		if (this.getGanttSolicitacaoServicoBean().getTipoDemandaServico() != null && !this.getGanttSolicitacaoServicoBean().getTipoDemandaServico().trim().isEmpty()) {
		    if (this.getGanttSolicitacaoServicoBean().getTipoDemandaServico().equalsIgnoreCase("R")) {
			tipoDemandaServico = TipoDemandaServico.REQUISICAO.getClassificacao();
		    }
	
		    if (this.getGanttSolicitacaoServicoBean().getTipoDemandaServico().equalsIgnoreCase("I")) {
			tipoDemandaServico = TipoDemandaServico.INCIDENTE.getClassificacao();
		    }
	
		    if (this.getGanttSolicitacaoServicoBean().getTipoDemandaServico().equalsIgnoreCase("O")) {
			tipoDemandaServico = TipoDemandaServico.OS.getClassificacao();
		    }
		}

		GrupoDTO grupoSeguranca = new GrupoDTO();
		if (this.getGanttSolicitacaoServicoBean().getIdGruposSeguranca() != null && !this.getGanttSolicitacaoServicoBean().getIdGruposSeguranca().trim().isEmpty()) {
		    grupoSeguranca.setIdGrupo(Integer.parseInt(this.getGanttSolicitacaoServicoBean().getIdGruposSeguranca()));
		}
	
		Collection<SolicitacaoServicoDTO> listaSolicitacaoServico = null;
		
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(this.getGanttSolicitacaoServicoBean().getDataFim());  
		calendar.add(GregorianCalendar.DATE, 1);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		java.sql.Date sqlDate = new java.sql.Date(calendar.getTime().getTime());
	
		this.getGanttSolicitacaoServicoBean().setDataFim(sqlDate);
		
		listaSolicitacaoServico = this.getSolicitacaoServicoService().listSolicitacaoServico(tipoDemandaServico, grupoSeguranca, null, this.getGanttSolicitacaoServicoBean().getDataInicio(), this.getGanttSolicitacaoServicoBean().getDataFim(), this.getGanttSolicitacaoServicoBean().getSituacao());
	
		if (listaSolicitacaoServico != null && !listaSolicitacaoServico.isEmpty()) {
		    StringBuilder gantt = gerarGantt(listaSolicitacaoServico, this.getGanttSolicitacaoServicoBean(), request);
		    document.executeScript(gantt.toString());
		    document.executeScript("ocultarInformacao();");
		} else {
			document.executeScript("setGantt('');");
		    document.executeScript("alert(i18n_message('MSG04'))");
		}

    }

    /**
     * Gera GANTT a partir da Lista de Solicitaï¿½ï¿½es de Serviço.
     * 
     * @param listaSolicitacaoServico
     * @return <code>StringBuilder</code>
     * @author valdoilo
     */
    private StringBuilder gerarGantt(Collection<SolicitacaoServicoDTO> listaSolicitacaoServico, GanttSolicitacaoServicoDTO ganttSolicitacaoServicoDTO, HttpServletRequest request) {
	StringBuilder gantt = new StringBuilder();

	gantt.append("$(function() {");
	gantt.append("'use strict';");
	gantt.append("$('.gantt').gantt({");
	gantt.append("source: [");

	for (SolicitacaoServicoDTO solicitacao : listaSolicitacaoServico) {
	    gantt.append("{");
	    if(solicitacao.getNomeTipoDemandaServico().equalsIgnoreCase("Requisição")){
	    	gantt.append("name: '" + UtilI18N.internacionaliza(request, "requisicaoProduto.requisicao") + " " + solicitacao.getIdSolicitacaoServico() + "',");
	    } else if (solicitacao.getNomeTipoDemandaServico().equalsIgnoreCase("Incidente")) {
	    	gantt.append("name: '" + UtilI18N.internacionaliza(request, "requisitosla.incidente") + " " + solicitacao.getIdSolicitacaoServico() + "',");
	    } else {
	    	gantt.append("name: '" + solicitacao.getNomeTipoDemandaServico() + " " + solicitacao.getIdSolicitacaoServico() + "',");
	    }
	    if(solicitacao.getSituacao().equalsIgnoreCase("EmAndamento")){
	    	gantt.append("desc: '" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "', ");
	    } else if(solicitacao.getSituacao().equalsIgnoreCase("Fechada")){
	    	gantt.append("desc: '" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada") +  "', ");
	    } else if(solicitacao.getSituacao().equalsIgnoreCase("Resolvida")){
	    	gantt.append("desc: '" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida") +  "', ");
	    } else if(solicitacao.getSituacao().equalsIgnoreCase("Cancelada")){
	    	gantt.append("desc: '" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "', ");
	    } else{
	    	gantt.append("desc: '" + solicitacao.getSituacao() + "', ");
	    }
	    gantt.append("values:[{ ");
	    Date date = new Date(solicitacao.getDataHoraSolicitacao().getTime());
	    Date dateFim = null;
	    String styleCustomClass = "ganttRed";
	    if (solicitacao.getDataHoraLimite() != null && solicitacao.getDataHoraLimite().after(UtilDatas.getDataHoraAtual())){
	    	styleCustomClass = "ganttBlue";
	    }	    
	    if (solicitacao.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.getDescricao())){
			dateFim = (solicitacao.getDataHoraFim() == null ? new Date() : solicitacao.getDataHoraFim());
			styleCustomClass = "ganttBlue";
	    }else{
	    	dateFim = new Date();
	    }
	    gantt.append("from: '/Date(" + date.getTime() + ")/',");
	    gantt.append("to: '/Date(" + dateFim.getTime() + ")/', ");
	    if(solicitacao.getNomeTipoDemandaServico().equalsIgnoreCase("Requisição")){
	    	gantt.append("label: '" + UtilI18N.internacionaliza(request, "requisicaoProduto.requisicao") + " " + solicitacao.getIdSolicitacaoServico() + "',");
	    } else if (solicitacao.getNomeTipoDemandaServico().equalsIgnoreCase("Incidente")) {
	    	gantt.append("label: '" + UtilI18N.internacionaliza(request, "requisitosla.incidente") + " " + solicitacao.getIdSolicitacaoServico() + "',");
	    } else {
	    	gantt.append("label: '" + solicitacao.getNomeTipoDemandaServico() + " " + solicitacao.getIdSolicitacaoServico() + "',");
	    }
	    gantt.append("customClass: '" + styleCustomClass + "',");
	    gantt.append("}]");
	    gantt.append("},");
	}

	gantt.append("],");
	gantt.append("navigate: 'scroll',");
	gantt.append("scale: 'days',");
	gantt.append("maxScale: 'months',");
	gantt.append("minScale: 'days',");
	gantt.append("itemsPerPage: 10,");
	gantt.append("onItemClick: function(data) {");
	gantt.append("},");
	gantt.append("onAddClick: function(dt, rowId) {");
	gantt.append("}");
	gantt.append("});");
	gantt.append("});");
	return gantt;
    }

    /**
     * Gera Combo de Pastas que não possuem SubPastas.
     * 
     * Somente elas podem armazenar Base de Conhecimento.
     * 
     * @param document
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void gerarComboTipoDemandaServico(DocumentHTML document,HttpServletRequest request) throws Exception {
	HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById("comboTipoDemanda");
	comboTipoDemanda.removeAllOptions();

	comboTipoDemanda.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	comboTipoDemanda.addOption(Enumerados.TipoDemandaServico.INCIDENTE.getClassificacao(), Enumerados.TipoDemandaServico.INCIDENTE.getCampo());
	comboTipoDemanda.addOption(Enumerados.TipoDemandaServico.OS.getClassificacao(), Enumerados.TipoDemandaServico.OS.getCampo());
	comboTipoDemanda.addOption(Enumerados.TipoDemandaServico.REQUISICAO.getClassificacao(), Enumerados.TipoDemandaServico.REQUISICAO.getCampo());
    }

    /**
     * Gera combo de Grupos de Seguranï¿½a.
     * 
     * @param document
     * @throws Exception
     * @author valdoilo
     */
    public void gerarComboGrupoSeguranca(DocumentHTML document,HttpServletRequest request) throws Exception {
	HTMLSelect comboGrupoSeguranca = (HTMLSelect) document.getSelectById("comboGruposSeguranca");
	comboGrupoSeguranca.removeAllOptions();

	comboGrupoSeguranca.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	for (GrupoDTO grupoSeguranca : this.getGrupoService().listGruposServiceDesk()) {
	    if (grupoSeguranca.getNome() != null) {
		comboGrupoSeguranca.addOption(grupoSeguranca.getIdGrupo().toString(), grupoSeguranca.getNome());
	    }
	}
    }

    /**
     * Retorna service de solicitacaoServico.
     * 
     * @return SolicitacaoServicoService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo
     */
    private SolicitacaoServicoService getSolicitacaoServicoService() throws ServiceException, Exception {
	return (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
    }

    /**
     * Retorna service de GrupoSegurancaService.
     * 
     * @return GrupoSegurancaService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo
     */
    private GrupoService getGrupoService() throws ServiceException, Exception {
	return (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
    }

    /**
     * @return valor do atributo usuarioDto.
     */
    public UsuarioDTO getUsuarioDto() {
	return usuarioDto;
    }

    /**
     * Define valor do atributo usuarioDto.
     * 
     * @param usuarioDto
     */
    public void setUsuarioDto(UsuarioDTO usuarioDto) {
	this.usuarioDto = usuarioDto;
    }

    /**
     * @return valor do atributo ganttSolicitacaoServicoBean.
     */
    public GanttSolicitacaoServicoDTO getGanttSolicitacaoServicoBean() {
	return ganttSolicitacaoServicoBean;
    }

    /**
     * Define valor do atributo ganttSolicitacaoServicoBean.
     * 
     * @param ganttSolicitacaoServicoBean
     */
    public void setGanttSolicitacaoServicoBean(IDto ganttSolicitacaoServicoBean) {
	this.ganttSolicitacaoServicoBean = (GanttSolicitacaoServicoDTO) ganttSolicitacaoServicoBean;
    }

}
