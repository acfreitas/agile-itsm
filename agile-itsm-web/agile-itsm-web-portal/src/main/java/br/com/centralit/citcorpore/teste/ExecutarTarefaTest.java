package br.com.centralit.citcorpore.teste;

import java.util.HashMap;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.util.UtilTest;

public class ExecutarTarefaTest {
	protected CrudDAO getDao() throws ServiceException {
		return new ExecucaoSolicitacaoDao();
	}
	public String testIniciarExecutarTarefa() {
		try {
			GerenciamentoServicosDTO gerenciamentoBean = new GerenciamentoServicosDTO();
			gerenciamentoBean.setAcaoFluxo("true");
			gerenciamentoBean.setIdFluxo(1);
			gerenciamentoBean.setIdSolicitacao(613);
			gerenciamentoBean.setIdSolicitacaoSel("213");
			gerenciamentoBean.setIdTarefa(1);
			gerenciamentoBean.setNomeCampoOrdenacao("");
			gerenciamentoBean.setNumeroContratoSel("1");
			gerenciamentoBean.setOrdenacaoAsc("");
			gerenciamentoBean.setDescricaoSolicitacao("");
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setIdUsuario(430);
			usuarioDto.setLogin("Layanne.Batista");
			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			solicitacaoServicoDto.setIdSolicitacaoServico(213);
			solicitacaoServicoDto.setUsuarioDto(usuarioDto);
			SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
			ElementoFluxoDTO elementoFluxoDto = new ElementoFluxoDTO();
			elementoFluxoDto.setIdElemento(1);
			HashMap<String, Object> objetos = new HashMap();
			TransactionControler tc ;
			tc = new TransactionControlerImpl(getDao().getAliasDB());
			new ExecucaoSolicitacao(tc).executa(solicitacaoServicoDto.getUsuarioDto().getLogin(), solicitacaoAuxDto, gerenciamentoBean.getIdTarefa(), Enumerados.ACAO_EXECUTAR, objetos);
		return new UtilTest().testNotNull(Enumerados.ACAO_EXECUTAR);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
