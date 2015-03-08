package br.com.centralit.citcorpore.negocio;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.citframework.service.CrudService;
public interface TemplateSolicitacaoServicoService extends CrudService { 
    public TemplateSolicitacaoServicoDTO recuperaTemplateServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;
    
    public TemplateSolicitacaoServicoDTO recuperaTemplateProblema(ProblemaDTO problemaDto) throws Exception ;
    public TemplateSolicitacaoServicoDTO recuperaTemplateRequisicaoLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;
    public TemplateSolicitacaoServicoDTO recuperaTemplateRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception ;
    public TemplateSolicitacaoServicoDTO findByIdentificacao(String identificacao) throws Exception;
    public TemplateSolicitacaoServicoDTO recuperaTemplateServico(ItemTrabalho itemTrabalho) throws Exception;
}
