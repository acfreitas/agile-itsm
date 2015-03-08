package br.com.centralit.citcorpore.negocio;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ValidacaoRequisicaoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.ValidacaoRequisicaoProblemaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

/**
 *
 * @author geber.costa
 *
 */
public class ValidacaoRequisicaoProblemaServiceEjb extends CrudServiceImpl implements ValidacaoRequisicaoProblemaService {

    private ValidacaoRequisicaoProblemaDAO dao;

    @Override
    protected ValidacaoRequisicaoProblemaDAO getDao() {
        if (dao == null) {
            dao = new ValidacaoRequisicaoProblemaDAO();
        }
        return dao;
    }

    @Override
    public ValidacaoRequisicaoProblemaDTO recuperaTemplateRequisicaoProblema(final ProblemaDTO problemaDto) throws Exception {

        ValidacaoRequisicaoProblemaDTO templateValidacaoDto = null;
        if (problemaDto.getIdTarefa() != null) {
            TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
            tarefaFluxoDto.setIdItemTrabalho(problemaDto.getIdTarefa());
            tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
            final ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
            if (elementoDto.getTemplate() != null) {
                templateValidacaoDto = this.getDao().findByObservacaoProblema(elementoDto.getTemplate());
            }
        }

        if (templateValidacaoDto == null && problemaDto.getIdServico() != null && problemaDto.getIdContrato() != null) {
            ServicoDTO servicoDto = new ServicoDTO();
            servicoDto.setIdServico(problemaDto.getIdServico());
            servicoDto = (ServicoDTO) new ServicoDao().restore(servicoDto);
            if (servicoDto != null) {
                Integer idTemplate = servicoDto.getIdTemplateSolicitacao();
                if (problemaDto.getIdSolicitacaoServico() != null && servicoDto.getIdTemplateAcompanhamento() != null) {
                    idTemplate = servicoDto.getIdTemplateAcompanhamento();
                }
                if (idTemplate != null) {
                    templateValidacaoDto = new ValidacaoRequisicaoProblemaDTO();
                    templateValidacaoDto.setIdValidacaoRequisicaoProblema(idTemplate);
                    templateValidacaoDto = (ValidacaoRequisicaoProblemaDTO) this.getDao().restore(templateValidacaoDto);
                }
            }

        }

        return templateValidacaoDto;

    }

    @Override
    public ValidacaoRequisicaoProblemaDTO findByIdProblema(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(final TransactionControler tc, final ProblemaDTO problemaDto, final IDto model) throws Exception {
        ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) model;

        this.getDao().setTransactionControler(tc);

        if (problemaDto.getIdProblema() != null) {

            validacaoRequisicaoProblemaDto.setIdProblema(problemaDto.getIdProblema());
        }

        validacaoRequisicaoProblemaDto.setDataInicio(UtilDatas.getDataAtual());

        validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) this.getDao().create(validacaoRequisicaoProblemaDto);

        return validacaoRequisicaoProblemaDto;
    }

    @Override
    public void update(final TransactionControler tc, final ProblemaDTO problemaDto, final IDto model) throws Exception {
        final ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) model;

        this.getDao().setTransactionControler(tc);

        if (problemaDto.getIdProblema() != null) {

            validacaoRequisicaoProblemaDto.setIdProblema(problemaDto.getIdProblema());
        }
        if (validacaoRequisicaoProblemaDto.getIdValidacaoRequisicaoProblema() != null) {
            this.getDao().update(validacaoRequisicaoProblemaDto);
        }

    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = null;

        if (serialize != null) {

            validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) WebUtil.deserializeObject(ValidacaoRequisicaoProblemaDTO.class, serialize);

        }
        return validacaoRequisicaoProblemaDto;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

}
