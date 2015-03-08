package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.ChecklistQuestionarioDTO;
import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.citframework.service.CrudService;

public interface RequisicaoQuestionarioService extends CrudService {
	public Collection listByIdContratoAndAba(Integer idContrato, String aba) throws Exception;
	public Collection listByIdContratoAndAbaOrdemCrescente(Integer idContrato, String aba) throws Exception;
	public ContratoQuestionariosDTO getUltimoByIdContratoAndAba(Integer idContrato, String aba) throws Exception;
	public Collection listByIdContrato(Integer idContrato) throws Exception;
	public Collection listByIdContratoOrderDecrescente(Integer idContrato) throws Exception;
	public Collection listByIdContratoAndQuestionario(Integer idQuestionario, Integer idContrato) throws Exception;
	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAba(Integer idDepartamento, Integer idEstabelecimento, Integer idCargo, String aba) throws Exception;
	public void updateConteudoImpresso(Integer idPessQuest, String conteudoImpresso) throws Exception;
	public ContratoQuestionariosDTO getUltimoByIdContratoAndAbaAndPeriodo(Integer idContrato, String aba, Date dataInicio, Date dataFim) throws Exception;
	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodo(Integer idDepartamento, Integer idEstabelecimento, 
			Integer idCargo, String aba, Date dataInicio, Date dataFim) throws Exception;
	public Collection listByIdContratoOrderIdDecrescente(Integer idContrato) throws Exception;
	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodoFinalizados(Integer idDepartamento, Integer idEstabelecimento, 
			Integer idCargo, String aba, Date dataInicio, Date dataFim) throws Exception;
	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabFuncaoAndAbaAndPeriodo(Integer idDepartamento, Integer idEstabelecimento, 
            Integer idCargo, Integer idFuncao, String aba, Date dataInicio, Date dataFim) throws Exception;
	public void atualizaInformacoesQuestionario(RequisicaoQuestionarioDTO requisicaoQuestionarioDTO, HttpServletRequest request) throws Exception;
	public Collection listByIdTipoAbaAndTipoRequisicaoAndQuestionario(ChecklistQuestionarioDTO checklistQuestionarioDTO) throws Exception;
	public boolean gravaConfirmacao(Integer idRequisicao, String confirmacao);
	public Collection listNaoConfirmados(Integer id, Integer tipo) throws Exception;
}
