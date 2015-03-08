/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;

/**
 * Action de Característica.
 * 
 * @author valdoilo.damasceno
 */
public class ProcessamentoEventual extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*		Integer id = null;
		try{
			SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
			Collection<SolicitacaoServicoDTO> col = solicitacaoDao.list();
			if (col == null)
				return;
	
			OcorrenciaSolicitacaoDao ocorrenciaDao = new OcorrenciaSolicitacaoDao();
			for (SolicitacaoServicoDTO solicitacaoDto : col) {
				if (solicitacaoDto.getDataHoraCaptura() != null && solicitacaoDto.getDataHoraCaptura().compareTo(solicitacaoDto.getDataHoraInicio()) >= 0)
					continue;
				
				id = solicitacaoDto.getIdSolicitacaoServico();
				Collection<OcorrenciaSolicitacaoDTO> colOcorrencias = ocorrenciaDao.listByIdSolicitacaoAndCategoria(solicitacaoDto.getIdSolicitacaoServico(), CategoriaOcorrencia.Direcionamento);
				if (colOcorrencias != null) {
					OcorrenciaSolicitacaoDTO ocorrenciaDto = (OcorrenciaSolicitacaoDTO) ((List) colOcorrencias).get(0);
					solicitacaoDto.setDataHoraCaptura(Timestamp.valueOf(UtilDatas.dateToSTR(ocorrenciaDto.getDataregistro(), "yyyy-MM-dd") + " " + ocorrenciaDto.getHoraregistro() + ":00"));
				}
				if (solicitacaoDto.getDataHoraCaptura() == null || solicitacaoDto.getDataHoraCaptura().compareTo(solicitacaoDto.getDataHoraInicio()) < 0)
					solicitacaoDto.setDataHoraCaptura(solicitacaoDto.getDataHoraInicio());
				solicitacaoDao.updateNotNull(solicitacaoDto);
			}
	
			col = solicitacaoDao.listBySituacao(SituacaoSolicitacaoServico.Fechada);
			if (col == null)
				return;
			
			ExecucaoSolicitacao execucao = new ExecucaoSolicitacao();
			for (SolicitacaoServicoDTO solicitacaoDto : col) {
				id = solicitacaoDto.getIdSolicitacaoServico();
				execucao.calculaTempoCaptura(solicitacaoDto);
				execucao.calculaTempoAtraso(solicitacaoDto);
				execucao.calculaTempoAtendimento(solicitacaoDto);
				solicitacaoDao.updateNotNull(solicitacaoDto);
			}
		}catch (Exception e) {
			String msg = e.getMessage();
			if (id != null)
				msg = "Solicitação " + id + " -> " + msg;
			throw new Exception(msg);
		}finally{
			document.alert("Processamento executado com sucesso");
		}*/
	}
	
	public void executaSolSemPesqSatisf(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
	    Collection col = solicitacaoDao.listaSolicitacoesSemPesquisaSatisfacao();
	    if (col != null){
        	    for (Iterator it = col.iterator(); it.hasNext();){
        		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO)it.next();
        		
        		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
        		if (remetente == null)
        			throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");
        		
        		String urlSistema = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");
        		
        		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDTO.getIdSolicitacaoServico(), null);
        		solicitacaoAuxDto.setNomeTarefa("");
        		
        		String idHashValidacao = CriptoUtils.generateHash("CODED" + solicitacaoAuxDto.getIdSolicitacaoServico(), "MD5");
        		solicitacaoAuxDto.setHashPesquisaSatisfacao(idHashValidacao);
        		solicitacaoAuxDto.setUrlSistema(urlSistema);
        		solicitacaoAuxDto.setLinkPesquisaSatisfacao("<a href=\"" + urlSistema + 
        			"/pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idSolicitacaoServico=" + solicitacaoAuxDto.getIdSolicitacaoServico() +
        			"&hash=" + idHashValidacao + "\">Clique aqui para fazer a avaliação do Atendimento</a>");
        		
        		MensagemEmail mensagem = new MensagemEmail(2, new IDto[] {solicitacaoAuxDto});
        		try {
        		    System.out.println("Enviando email p/ " + solicitacaoAuxDto.getEmailcontato());
        			mensagem.envia(solicitacaoAuxDto.getEmailcontato(), remetente, remetente);
        		} catch (Exception e) {
        		}  
        	    }
	    }
	    document.alert("PRONTO!");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}
}
