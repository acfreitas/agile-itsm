package br.com.centralit.citcorpore.quartz.job;

import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.PesquisaSatisfacaoDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.util.UtilDatas;

public class AvaliarSolicitacoesNaoRespondidas implements Job  {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String avaliacaoAutomatica = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.AVALIA��O_AUTOMATICA, "");
		if(avaliacaoAutomatica.equals("S")){
		try {
			System.out.println("DISPARADO AVALIA��O AUTOM�TICA");
			PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO;
			PesquisaSatisfacaoDAO pesquisaSatisfacaoDAO = new PesquisaSatisfacaoDAO();
			SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();

			// Nota padr�o para avalia��o autom�tica do atendimento de solicita��es
			Enumerados.Nota nota = null;
			String notaAvaliacaoAutomatica = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOTA_AVALIA��O_AUTOMATICA, "");
			if(notaAvaliacaoAutomatica.equals("BOM")){
				nota = Enumerados.Nota.BOM;
			}else if(notaAvaliacaoAutomatica.equals("OTIMO")){
				nota = Enumerados.Nota.OTIMO;
			}else if(notaAvaliacaoAutomatica.equals("REGULAR")){
			nota = Enumerados.Nota.REGULAR;
			}else if(notaAvaliacaoAutomatica.equals("RUIM")){
				nota = Enumerados.Nota.RUIM;
			}
			// Calculando a data limite para resposta do usu�rio
			Integer pQtdeDias = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.QTDE_DIAS_RESP_PESQ_SASTISFACAO, "7"));

			java.sql.Date dataLimite = UtilDatas.getSqlDate(UtilDatas.incrementaDiasEmData(Util.getDataAtual(), -(new Integer(pQtdeDias))));
			
//			Date dataLimite = Util.subtraiDiaData(Util.converteStringToDataUtil(Util.converteDataUtilToString(Util.getDataAtual()), "yyyy-MM-dd 23:59:59"), pQtdeDias);

			String comentario = "Pesquisa respondida automaticamente pelo sistema citsmart.";

			// As Solicita��es n�o respondidas e que foram conclu�das at� a data limite ser�o respondidas automaticamente pelo sistema
			// Trazer as Solicita��es n�o respondidas
			Collection<SolicitacaoServicoDTO> listaIdSolicitacoesNResp = solicitacaoServicoDao.listaIDSolicitacaoNaoRespondida(dataLimite);
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaIdSolicitacoesNResp) {
				// Gravar pesquisa para Cada Solicita��o de Servi�os n�o avaliada
				pesquisaSatisfacaoDTO = new PesquisaSatisfacaoDTO();
				pesquisaSatisfacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
				pesquisaSatisfacaoDTO.setNota(nota.getNota());
				pesquisaSatisfacaoDTO.setComentario(comentario);
				pesquisaSatisfacaoDAO.create(pesquisaSatisfacaoDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
}