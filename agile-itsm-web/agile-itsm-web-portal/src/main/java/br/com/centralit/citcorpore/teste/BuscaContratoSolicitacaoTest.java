package br.com.centralit.citcorpore.teste;

import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTest;

public class BuscaContratoSolicitacaoTest {
	public String testPesquisa() {
		ExecucaoSolicitacaoService execucaoSolicitacaoService;
		try {
			execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
			//GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
			GerenciamentoServicosDTO gerenciamentoBean = new GerenciamentoServicosDTO();
			gerenciamentoBean.setAcaoFluxo("true");
			gerenciamentoBean.setIdFluxo(1);
			gerenciamentoBean.setIdSolicitacao(613);
			gerenciamentoBean.setIdSolicitacaoSel("78");
			gerenciamentoBean.setIdTarefa(1);
			gerenciamentoBean.setNomeCampoOrdenacao("");
			gerenciamentoBean.setNumeroContratoSel("001");
			gerenciamentoBean.setOrdenacaoAsc("");
			gerenciamentoBean.setDescricaoSolicitacao("teste");
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setIdUsuario(430);
			usuario.setLogin("layanne.batista");
			
			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			solicitacaoServicoDto.setContrato("001");
			solicitacaoServicoDto.setIdSolicitacaoServicoPesquisa(null);
			
			List<TarefaFluxoDTO> colTarefas = null;
			colTarefas = execucaoSolicitacaoService.recuperaTarefas(usuario.getLogin());
			
			boolean bFiltroPorContrato = gerenciamentoBean.getNumeroContratoSel() != null && gerenciamentoBean.getNumeroContratoSel().length() > 0;
			boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdSolicitacaoSel() != null && gerenciamentoBean.getIdSolicitacaoSel().length() > 0;
			boolean bDescricao = gerenciamentoBean.getDescricaoSolicitacao() != null && gerenciamentoBean.getDescricaoSolicitacao().trim().length() > 0;
			
			List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
			if (!bFiltroPorContrato && !bFiltroPorSolicitacao && !bDescricao){
				colTarefasFiltradas.addAll(colTarefas);
			}	else {
				for (TarefaFluxoDTO tarefaDto : colTarefas) {

					boolean bAdicionar = false;
					String contrato = UtilStrings.nullToVazio(((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getContrato());
					String idSolicitacao = "" + ((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdSolicitacaoServico();

					Source source = new Source(UtilStrings.nullToVazio(((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getDescricao()));

					String descricao = UtilStrings.nullToVazio(source.getTextExtractor().toString().toLowerCase());

					if (bFiltroPorContrato && bDescricao && bFiltroPorSolicitacao) {

						bAdicionar = contrato.indexOf(gerenciamentoBean.getNumeroContratoSel()) >= 0 && idSolicitacao.indexOf(gerenciamentoBean.getIdSolicitacaoSel()) >= 0
								&& StringUtils.contains(descricao, gerenciamentoBean.getDescricaoSolicitacao().toLowerCase());

					} else {

						if (bFiltroPorContrato) {

							bAdicionar = contrato.indexOf(gerenciamentoBean.getNumeroContratoSel()) >= 0;

						} else {

							if (bDescricao) {

								bAdicionar = StringUtils.contains(descricao, gerenciamentoBean.getDescricaoSolicitacao().toLowerCase());

							} else {

								bAdicionar = idSolicitacao.indexOf(gerenciamentoBean.getIdSolicitacaoSel()) >= 0;

							}

						}
					}

					if (bAdicionar) {

						colTarefasFiltradas.add(tarefaDto);
					}

				}
			}
		return new UtilTest().testCollection(colTarefasFiltradas);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}