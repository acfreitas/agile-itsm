package br.com.centralit.citcorpore.quartz.job;

import java.util.Collection;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class EventoPopulaConteudoBaseConhecimento implements Job {

    @Override
    public void execute(final JobExecutionContext arg0) throws JobExecutionException {
        try {
            final Collection<BaseConhecimentoDTO> lista = this.getBaseConhecimentoService().list();
            if (lista != null) {
                for (final BaseConhecimentoDTO baseConhecimentoDto : lista) {
                    if (baseConhecimentoDto.getConteudo() != null && !StringUtils.isBlank(baseConhecimentoDto.getConteudo())) {
                        final Source source = new Source(baseConhecimentoDto.getConteudo());
                        baseConhecimentoDto.setConteudoSemFormatacao(source.getTextExtractor().toString());
                    }
                    if (baseConhecimentoDto.getConteudoSemFormatacao() != null && !StringUtils.isBlank(baseConhecimentoDto.getConteudoSemFormatacao())) {
                        this.getBaseConhecimentoService().updateNotNull(baseConhecimentoDto);
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private BaseConhecimentoService baseConhecimentoService;

    private BaseConhecimentoService getBaseConhecimentoService() throws ServiceException {
        if (baseConhecimentoService == null) {
            baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
        }
        return baseConhecimentoService;
    }

}
