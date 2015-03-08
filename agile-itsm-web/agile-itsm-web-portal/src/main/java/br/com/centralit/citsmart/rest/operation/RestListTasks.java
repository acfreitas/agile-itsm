package br.com.centralit.citsmart.rest.operation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtListTasks;
import br.com.centralit.citsmart.rest.schema.CtListTasksResp;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.util.UtilStrings;

public class RestListTasks implements IRestOperation<CtListTasks, CtListTasksResp> {

    @Override
    public CtListTasksResp execute(final RestSessionDTO restSession, final RestOperationDTO restOperation, final CtListTasks message) throws JAXBException {
        final CtListTasksResp resp = new CtListTasksResp();
        if (restSession.getUser() == null || restSession.getUser().getLogin() == null || restSession.getUser().getLogin().trim().equals("")) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Login do usuário não identificado"));
            return resp;
        }

        final List<TipoSolicitacaoServico> tipos = new ArrayList<>();
        if (UtilStrings.nullToVazio(message.getListarCompras()).equalsIgnoreCase("S")) {
            tipos.add(TipoSolicitacaoServico.COMPRA);
        }
        if (UtilStrings.nullToVazio(message.getListarIncidentes()).equalsIgnoreCase("S")) {
            tipos.add(TipoSolicitacaoServico.INCIDENTE);
        }
        if (UtilStrings.nullToVazio(message.getListarRequisicoes()).equalsIgnoreCase("S")) {
            tipos.add(TipoSolicitacaoServico.REQUISICAO);
        }
        if (UtilStrings.nullToVazio(message.getListarRH()).equalsIgnoreCase("S")) {
            tipos.add(TipoSolicitacaoServico.RH);
        }
        if (UtilStrings.nullToVazio(message.getListarViagens()).equalsIgnoreCase("S")) {
            tipos.add(TipoSolicitacaoServico.VIAGEM);
        }

        if (tipos.size() == 0) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Pelo menos um tipo de solicitação deve ser selecionado"));
            return resp;
        }

        try {
            resp.setTarefas(RestUtil.getExecucaoSolicitacaoService(restSession).recuperaTarefas(restSession.getUser().getLogin(),
                    tipos.toArray(new TipoSolicitacaoServico[tipos.size()]), "N"));
            resp.setQtdeTarefas(resp.getTarefas().size());
        } catch (final Exception e) {
            e.printStackTrace();
            resp.setError(RestOperationUtil.buildError(e));
        }

        return resp;
    }

}
