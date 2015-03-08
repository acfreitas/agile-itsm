package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.centralit.bpm.servico.ExecucaoFluxoService;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.citframework.dto.Usuario;
import br.com.citframework.integracao.TransactionControler;
public interface ExecucaoLiberacaoService extends ExecucaoFluxoService {
	public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(Integer idTarefa) throws Exception;
	public void trataCamposTarefa(Map<String, String> params, Collection<CamposObjetoNegocioDTO> colCampos, Map<String, Object> map, String principal) throws Exception;
	public void registraLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc, Usuario usuario) throws Exception;
	public void executa(UsuarioDTO usuarioDto, TransactionControler tc, Integer idFluxo, Integer idTarefa, String acaoFluxo, HashMap<String, String> params, Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception;
	public void executa(RequisicaoLiberacaoDTO requisicaoLiberacaoDto, Integer idTarefa, String acaoFluxo, TransactionControler tc) throws Exception;
	public void executa(UsuarioDTO usuarioDto, Integer idTarefa, String acaoFluxo) throws Exception;
}
