package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoFuncaoDTO;
import br.com.citframework.service.CrudService;

public interface JustificativaRequisicaoFuncaoService extends CrudService {

	public boolean consultarJustificativasAtivas(JustificativaRequisicaoFuncaoDTO obj) throws Exception;
	public Collection<JustificativaRequisicaoFuncaoDTO> seJustificativaJaCadastrada(JustificativaRequisicaoFuncaoDTO justificativaRequisicaoFuncaoDTO) throws Exception;
	public Collection<JustificativaRequisicaoFuncaoDTO> listarAtivos() throws Exception;

}
