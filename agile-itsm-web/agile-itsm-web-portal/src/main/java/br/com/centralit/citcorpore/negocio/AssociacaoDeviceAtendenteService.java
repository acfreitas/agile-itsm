package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Servi�os para {@link AssociacaoDeviceAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public interface AssociacaoDeviceAtendenteService extends CrudService {

    /**
     * Lista as associa��es ativas de um usu�rio, independentemente de conex�o e plataforma
     *
     * @param usuario
     *            usu�rio para o qual se deseja listar as associa��es ativas
     * @param connection
     *            "conex�o" no mobile, que � a URI acessada
     * @return {@link List} de {@link AssociacaoDeviceAtendenteDTO}
     * @throws ServiceException
     */
    List<AssociacaoDeviceAtendenteDTO> listActiveAssociationsForUserAndConnection(final UsuarioDTO usuario, final String connection) throws ServiceException;

    /**
     * Associa um device a um usu�rio
     *
     * @param associacao
     *            informa��es da associa��o a ser efetuada
     * @param usuario
     *            usu�rio para o qual ser� realizada a associa��o
     * @return {@link AssociacaoDeviceAtendenteDTO} realizada
     * @throws ServiceException
     */
    AssociacaoDeviceAtendenteDTO associateDeviceToAttendant(final AssociacaoDeviceAtendenteDTO associacao, final UsuarioDTO usuario) throws ServiceException;

    /**
     * Desassocia um device de um usu�rio
     *
     * @param associacao
     *            informa��es da associa��o a ser desfeita
     * @param usuario
     *            usu�rio para o qual ser� realizada a desassocia��o
     * @return {@link AssociacaoDeviceAtendenteDTO} desassociado
     * @throws ServiceException
     */
    AssociacaoDeviceAtendenteDTO disassociateDeviceFromAttendant(final AssociacaoDeviceAtendenteDTO associacao, final UsuarioDTO usuario) throws ServiceException;

}
