package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

/**
 * @author breno.guimaraes
 *
 */
public interface IRequisicaoMudancaRelacionamento {

    @SuppressWarnings("rawtypes")
    public void gravarRelacionamentos(final Integer idRequisicaoMudanca, final Collection listaDeserializada) throws LogicException, ServiceException, Exception;

    @SuppressWarnings("rawtypes")
    public ArrayList listItensRelacionadosRequisicaoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception;

}
