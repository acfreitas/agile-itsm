package br.com.centralit.citcorpore.negocio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ScriptsDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ScriptsService extends CrudService {

    ScriptsDTO buscaScriptPorId(final Integer id) throws Exception;

    void deletarScript(final IDto model, final DocumentHTML document) throws Exception;

    String executaRotinaScripts() throws Exception;

    List<String[]> executarScriptConsulta(final ScriptsDTO script) throws Exception;

    String executarScriptUpdate(final ScriptsDTO script) throws Exception;

    boolean haScriptDeVersaoComErro() throws Exception;

    /**
     * Verifica se já existe algum ScriptDTO com o mesmo NOME
     *
     * @return Retorna se já existe algum ScriptDTO com o mesmo NOME
     * @author Murilo Gabriel
     */
    boolean temScriptsAtivos(final ScriptsDTO script) throws Exception;

    void marcaErrosScriptsComoCorrigidos() throws Exception;

    String verificaPermissoesUsuarioBanco(final HttpServletRequest request) throws ServiceException;

    List<ScriptsDTO> pesquisaScriptsComFaltaPermissao() throws Exception;

}
