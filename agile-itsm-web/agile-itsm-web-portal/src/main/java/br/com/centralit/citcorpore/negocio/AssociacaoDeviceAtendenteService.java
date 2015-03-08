package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Serviços para {@link AssociacaoDeviceAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public interface AssociacaoDeviceAtendenteService extends CrudService {

    /**
     * Lista as associações ativas de um usuário, independentemente de conexão e plataforma
     *
     * @param usuario
     *            usuário para o qual se deseja listar as associações ativas
     * @param connection
     *            "conexão" no mobile, que é a URI acessada
     * @return {@link List} de {@link AssociacaoDeviceAtendenteDTO}
     * @throws ServiceException
     */
    List<AssociacaoDeviceAtendenteDTO> listActiveAssociationsForUserAndConnection(final UsuarioDTO usuario, final String connection) throws ServiceException;

    /**
     * Associa um device a um usuário
     *
     * @param associacao
     *            informações da associação a ser efetuada
     * @param usuario
     *            usuário para o qual será realizada a associação
     * @return {@link AssociacaoDeviceAtendenteDTO} realizada
     * @throws ServiceException
     */
    AssociacaoDeviceAtendenteDTO associateDeviceToAttendant(final AssociacaoDeviceAtendenteDTO associacao, final UsuarioDTO usuario) throws ServiceException;

    /**
     * Desassocia um device de um usuário
     *
     * @param associacao
     *            informações da associação a ser desfeita
     * @param usuario
     *            usuário para o qual será realizada a desassociação
     * @return {@link AssociacaoDeviceAtendenteDTO} desassociado
     * @throws ServiceException
     */
    AssociacaoDeviceAtendenteDTO disassociateDeviceFromAttendant(final AssociacaoDeviceAtendenteDTO associacao, final UsuarioDTO usuario) throws ServiceException;

}
