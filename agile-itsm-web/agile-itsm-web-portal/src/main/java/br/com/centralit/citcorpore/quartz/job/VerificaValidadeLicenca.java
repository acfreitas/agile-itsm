package br.com.centralit.citcorpore.quartz.job;

import java.util.Collection;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class VerificaValidadeLicenca implements Job {

    @Override
    public void execute(final JobExecutionContext arg0) throws JobExecutionException {
        Collection<ItemConfiguracaoDTO> colItemConfiguracao = null;
        EmpregadoDTO empregadoDTO = new EmpregadoDTO();
        try {
            String AVISAR_DATAEXPIRACAO_LICENCA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA, "90");
            String ENVIAR_EMAIL_DATAEXPIRACAO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO, "2");
            String ID_MODELO_EMAIL_EXPIRACAO_LICENCA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_MODELO_EMAIL_EXPIRACAO_LICENCA, "6");
            final String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);

            if (AVISAR_DATAEXPIRACAO_LICENCA == null || AVISAR_DATAEXPIRACAO_LICENCA.isEmpty()) {
                AVISAR_DATAEXPIRACAO_LICENCA = "90";
            }

            if (ID_MODELO_EMAIL_EXPIRACAO_LICENCA == null || ID_MODELO_EMAIL_EXPIRACAO_LICENCA.isEmpty()) {
                ID_MODELO_EMAIL_EXPIRACAO_LICENCA = "6";
            }

            Date dataAtual = UtilDatas.getDataAtual();

            dataAtual = UtilDatas.incrementaDiasEmData(dataAtual, Integer.parseInt(AVISAR_DATAEXPIRACAO_LICENCA));

            final ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
            colItemConfiguracao = itemConfiguracaoService.pesquisaDataExpiracao(dataAtual);

            if (!colItemConfiguracao.isEmpty()) {
                if (ENVIAR_EMAIL_DATAEXPIRACAO == null || ENVIAR_EMAIL_DATAEXPIRACAO.isEmpty()) {
                    ENVIAR_EMAIL_DATAEXPIRACAO = "2";
                }
                if (ENVIAR_EMAIL_DATAEXPIRACAO.trim().equals("2")) {
                    final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
                    for (final ItemConfiguracaoDTO itemConfiguracao : colItemConfiguracao) {
                        empregadoDTO = empregadoService.restoreByIdEmpregado(itemConfiguracao.getIdProprietario());
                        final MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_EXPIRACAO_LICENCA.trim()), new IDto[] {itemConfiguracao});
                        mensagem.envia(empregadoDTO.getEmail(), "", remetente);
                    }
                } else {
                    for (final ItemConfiguracaoDTO itemConfiguracao : colItemConfiguracao) {
                        final MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_EXPIRACAO_LICENCA.trim()), new IDto[] {itemConfiguracao});
                        mensagem.envia(itemConfiguracao.getEmailGrupoItemConfiguracao(), "", remetente);
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
