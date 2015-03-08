package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.CheckinDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CheckinService extends CrudService {

    /**
     * Realiza o checkin de um usuário, relacionando à uma solicitação
     *
     * @param checkin
     *            informações do checkin
     * @param usuario
     *            usuário que está solicitando o checkin
     * @return numero da solicitação para a qual foi realizado o checkin
     * @throws Exception
     */
    Integer realizarCheckin(final CheckinDTO checkin, final UsuarioDTO usuario) throws Exception;

    /**
     * Verifica se existe algum checkin do usuário sem respectivo checkout, de forma a não permitir dois checkins simultâneos para o usuário
     *
     * @param usuario
     *            usuário que está solicitando o checkin
     * @return {@code List<CheckinDTO>} dos checkins sem checkout
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/11/2014
     */
    List<CheckinDTO> listCheckinsDoUsuarioSemCheckout(final UsuarioDTO usuario) throws ServiceException;

    /**
     * Verifica se existe algum checkin para a solicitação, independente de quem fez o checkin, sem respectivo checkout, de forma a não permitir dois checkins simultâneos para a
     * mesma solicitação
     *
     * @param checkin
     *            informações do checkin
     * @return
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/11/2014
     */
    List<CheckinDTO> listCheckinSolicitacaoSemCheckout(final CheckinDTO checkin) throws ServiceException;

    /**
     * Verifica se existe algum checkin para a solicitação, tarefa e usuário
     *
     * @param checkin
     *            informações do checkin
     * @return
     * @throws ServiceException
     * @author maycon.silva
     * @since 17/11/2014
     */
    public boolean isCheckinAtivo(final Integer idTarefa, final Integer idSolicitacao, final Integer idUsuario) throws Exception;

}
