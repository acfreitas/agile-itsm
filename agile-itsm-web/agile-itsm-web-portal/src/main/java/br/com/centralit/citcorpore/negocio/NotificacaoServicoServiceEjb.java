package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoServicoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceImpl;

public class NotificacaoServicoServiceEjb extends CrudServiceImpl implements NotificacaoServicoService {

    private NotificacaoServicoDao dao;

    @Override
    protected NotificacaoServicoDao getDao() {
        if (dao == null) {
            dao = new NotificacaoServicoDao();
        }
        return dao;
    }

    @Override
    public Collection<NotificacaoServicoDTO> listaIdServico(final Integer idServico) throws Exception {
        return this.getDao().listaIdServico(idServico);
    }

    @Override
    public Collection<NotificacaoServicoDTO> listaIdNotificacao(final Integer idNotificacao) throws Exception {
        return this.getDao().listaIdNotificacao(idNotificacao);
    }

    @Override
    public boolean existeServico(final Integer idNotificacao, final Integer idservico) throws Exception {
        return this.getDao().existeServico(idNotificacao, idservico);
    }

    public void enviarEmailNotificacao(final ServicoDTO servicoDto) throws Exception {
        try {
            final EmpregadoDao empregadoDao = new EmpregadoDao();
            Collection<EmpregadoDTO> colEmpregados = new ArrayList<>();
            final String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);

            final String ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO, "");
            if (ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO != null && !ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO.isEmpty()) {

                colEmpregados = empregadoDao.listarEmailsNotificacoesServico(servicoDto.getIdServico());

                if (colEmpregados != null) {

                    for (final EmpregadoDTO empregados : colEmpregados) {

                        final MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO.trim()), new IDto[] {servicoDto});

                        if (empregados.getEmail() != null) {
                            mensagem.envia(empregados.getEmail(), "", remetente);
                        }

                    }
                }

            }
        } catch (final Exception e) {}
    }

}
