package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class SolicitacaoServicoQuestionarioDao extends CrudDaoDefaultImpl {

	public SolicitacaoServicoQuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);

	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection<Field> getFields() {
		List lista = new ArrayList();

		lista.add(new Field("idSolicitacaoQuestionario","idSolicitacaoQuestionario",true,false,false,false));
		lista.add(new Field("idQuestionario","idQuestionario",false,false,false,false));
		lista.add(new Field("idSolicitacaoServico","idSolicitacaoServico",false,false,false,false));
		lista.add(new Field("dataQuestionario","dataQuestionario",false,false,false,false));
		lista.add(new Field("idResponsavel","idResponsavel",false,false,false,false));
		lista.add(new Field("idTarefa","idTarefa",false,false,false,false));
		lista.add(new Field("aba","aba",false,false,false,false));
		lista.add(new Field("situacao","situacao",false,false,false,false));
		lista.add(new Field("dataHoraGrav","dataHoraGrav",false,false,false,false));
		lista.add(new Field("conteudoImpresso","conteudoImpresso",false,false,false,false));

		return lista;
	}

	public String getTableName() {
		return "solicitacaoservicoquestionario";
	}
	public static String getTableNameAssDigital() {
		return "solicitacaoservicoquestionario";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return SolicitacaoServicoQuestionarioDTO.class;
	}

	public Collection listByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("dataQuestionario"));
		list.add(new Order("aba"));
		SolicitacaoServicoQuestionarioDTO obj = new SolicitacaoServicoQuestionarioDTO();
		obj.setIdSolicitacaoServico(idSolicitacaoServico);
		return super.find(obj, list);
	}

	public SolicitacaoServicoQuestionarioDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
        List list = new ArrayList();
        list.add(new Order("dataHoraGrav", Order.DESC));
        list.add(new Order("idSolicitacaoQuestionario", Order.DESC));
        SolicitacaoServicoQuestionarioDTO obj = new SolicitacaoServicoQuestionarioDTO();
        obj.setIdSolicitacaoServico(idSolicitacaoServico);
        List<SolicitacaoServicoQuestionarioDTO> result = (List<SolicitacaoServicoQuestionarioDTO>) super.find(obj, list);
        if (result != null && result.size() > 0)
            return result.get(0);
        else
            return null;
    }

	public void update(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	public void updateSituacaoComplemento(Integer idPessQuest, String situacaoComplemento) throws PersistenceException {
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContratoQuestionario(idPessQuest);
		obj.setSituacaoComplemento(situacaoComplemento);
		super.updateNotNull(obj);
	}
	public void updateConteudoImpresso(Integer idPessQuest, String conteudoImpresso) throws PersistenceException {
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContratoQuestionario(idPessQuest);
		obj.setConteudoImpresso(conteudoImpresso);
		super.updateNotNull(obj);
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
	    	    super.updateNotNull(obj);
	}
}
