package br.com.centralit.citcorpore.quartz.job;

import java.util.Collection;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class EventoPopulaDescricaoSolicitacao implements Job {

    @Override
    public void execute(final JobExecutionContext arg0) throws JobExecutionException {
        try {
            final Collection<SolicitacaoServicoDTO> lista = this.getService().list();
            for (final SolicitacaoServicoDTO solicitacaoServicoDTO : lista) {

                if (solicitacaoServicoDTO.getDescricao() != null && !StringUtils.isBlank(solicitacaoServicoDTO.getDescricao())) {
                    final Source source = new Source(solicitacaoServicoDTO.getDescricao());
                    solicitacaoServicoDTO.setDescricaoSemFormatacao(source.getTextExtractor().toString());
                }
                if (solicitacaoServicoDTO.getDescricaoSemFormatacao() != null && !StringUtils.isBlank(solicitacaoServicoDTO.getDescricaoSemFormatacao())) {
                    this.getService().updateNotNull(solicitacaoServicoDTO);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private SolicitacaoServicoService service;

    private SolicitacaoServicoService getService() throws ServiceException {
        if (service == null) {
            service = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        }
        return service;
    }

}
