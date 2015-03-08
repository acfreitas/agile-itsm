package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.CheckinDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CheckinService extends CrudService {

    /**
     * Realiza o checkin de um usu�rio, relacionando � uma solicita��o
     *
     * @param checkin
     *            informa��es do checkin
     * @param usuario
     *            usu�rio que est� solicitando o checkin
     * @return numero da solicita��o para a qual foi realizado o checkin
     * @throws Exception
     */
    Integer realizarCheckin(final CheckinDTO checkin, final UsuarioDTO usuario) throws Exception;

    /**
     * Verifica se existe algum checkin do usu�rio sem respectivo checkout, de forma a n�o permitir dois checkins simult�neos para o usu�rio
     *
     * @param usuario
     *            usu�rio que est� solicitando o checkin
     * @return {@code List<CheckinDTO>} dos checkins sem checkout
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/11/2014
     */
    List<CheckinDTO> listCheckinsDoUsuarioSemCheckout(final UsuarioDTO usuario) throws ServiceException;

    /**
     * Verifica se existe algum checkin para a solicita��o, independente de quem fez o checkin, sem respectivo checkout, de forma a n�o permitir dois checkins simult�neos para a
     * mesma solicita��o
     *
     * @param checkin
     *            informa��es do checkin
     * @return
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/11/2014
     */
    List<CheckinDTO> listCheckinSolicitacaoSemCheckout(final CheckinDTO checkin) throws ServiceException;

    /**
     * Verifica se existe algum checkin para a solicita��o, tarefa e usu�rio
     *
     * @param checkin
     *            informa��es do checkin
     * @return
     * @throws ServiceException
     * @author maycon.silva
     * @since 17/11/2014
     */
    public boolean isCheckinAtivo(final Integer idTarefa, final Integer idSolicitacao, final Integer idUsuario) throws Exception;

}
