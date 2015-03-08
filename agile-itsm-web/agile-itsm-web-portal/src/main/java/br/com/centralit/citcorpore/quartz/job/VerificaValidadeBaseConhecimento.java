package br.com.centralit.citcorpore.quartz.job;

import java.sql.Date;
import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class VerificaValidadeBaseConhecimento implements Job {

    @Override
    public void execute(final JobExecutionContext arg0) throws JobExecutionException {
        Collection<BaseConhecimentoDTO> colBaseConhecimento = null;
        UsuarioDTO usuarioDtoAutor = new UsuarioDTO();
        UsuarioDTO usuarioDtoAvaliador = new UsuarioDTO();

        EmpregadoDTO empregadoDtoAutor = new EmpregadoDTO();
        EmpregadoDTO empregadoDtoAvaliador = new EmpregadoDTO();

        try {
            String AVISAR_DATAEXPIRACAO_BASECONHECIMENTO = "1";
            String ID_MODELO_EMAIL_EXPIRACAO_BASECONHECIMENTO = "7";
            final String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);

            if (AVISAR_DATAEXPIRACAO_BASECONHECIMENTO == null || AVISAR_DATAEXPIRACAO_BASECONHECIMENTO.isEmpty()) {
                AVISAR_DATAEXPIRACAO_BASECONHECIMENTO = "90";
            }

            if (ID_MODELO_EMAIL_EXPIRACAO_BASECONHECIMENTO == null || ID_MODELO_EMAIL_EXPIRACAO_BASECONHECIMENTO.isEmpty()) {
                ID_MODELO_EMAIL_EXPIRACAO_BASECONHECIMENTO = "6";
            }

            Date dataAtual = UtilDatas.getDataAtual();

            dataAtual = (Date) UtilDatas.incrementaDiasEmData(dataAtual, Integer.parseInt(AVISAR_DATAEXPIRACAO_BASECONHECIMENTO));

            final BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
            final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
            final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

            final BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
            baseConhecimento.setDataExpiracao(dataAtual);

            colBaseConhecimento = baseConhecimentoService.listaBaseConhecimentoUltimasVersoes(baseConhecimento);

            if (!colBaseConhecimento.isEmpty()) {
                for (final BaseConhecimentoDTO baseConhecimentoDTO : colBaseConhecimento) {
                    if (baseConhecimentoDTO.getIdUsuarioAutor() != null) {
                        usuarioDtoAutor = usuarioService.restoreByID(baseConhecimentoDTO.getIdUsuarioAutor());
                    }
                    if (baseConhecimentoDTO.getIdUsuarioAprovador() != null) {
                        usuarioDtoAvaliador = usuarioService.restoreByID(baseConhecimentoDTO.getIdUsuarioAprovador());
                    }
                    if (usuarioDtoAutor.getIdEmpregado() != null) {
                        empregadoDtoAutor = empregadoService.restoreByIdEmpregado(usuarioDtoAutor.getIdEmpregado());
                    }
                    if (usuarioDtoAvaliador.getIdEmpregado() != null) {
                        empregadoDtoAvaliador = empregadoService.restoreByIdEmpregado(usuarioDtoAvaliador.getIdEmpregado());
                    }
                    final MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_EXPIRACAO_BASECONHECIMENTO), new IDto[] {baseConhecimentoDTO});
                    mensagem.envia(empregadoDtoAutor.getEmail(), "", remetente);
                    mensagem.envia(empregadoDtoAvaliador.getEmail(), "", remetente);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
