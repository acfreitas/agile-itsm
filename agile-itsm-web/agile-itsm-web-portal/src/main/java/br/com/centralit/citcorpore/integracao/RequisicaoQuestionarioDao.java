package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ChecklistQuestionarioDTO;
import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({"unchecked", "rawtypes"})
public class RequisicaoQuestionarioDao extends CrudDaoDefaultImpl {

	public RequisicaoQuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);

	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}




	public Collection<Field> getFields() {
		List lista = new ArrayList();


		lista.add(new Field("idRequisicaoQuestionario","idRequisicaoQuestionario",true,false,false,false));
		lista.add(new Field("idQuestionario","idQuestionario",false,false,false,false));
		lista.add(new Field("idRequisicao","idRequisicao",false,false,false,false));
		lista.add(new Field("dataQuestionario","dataQuestionario",false,false,false,false));
		lista.add(new Field("idResponsavel","idResponsavel",false,false,false,false));
		lista.add(new Field("idTarefa","idTarefa",false,false,false,false));
		lista.add(new Field("aba","aba",false,false,false,false));
		lista.add(new Field("situacao","situacao",false,false,false,false));
		lista.add(new Field("dataHoraGrav","dataHoraGrav",false,false,false,false));
		lista.add(new Field("conteudoImpresso","conteudoImpresso",false,false,false,false));
		lista.add(new Field("idtiporequisicao","idTipoRequisicao",false,false,false,false));
		lista.add(new Field("idTipoAba","idTipoAba",false,false,false,false));
		lista.add(new Field("confirmacao","confirmacao",false,false,false,false));

		return lista;
	}

	public String getTableName() {
		return "requisicaoquestionario";
	}
	public static String getTableNameAssDigital() {
		return "requisicaoquestionario";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoQuestionarioDTO.class;
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

	public Collection listByIdTipoAbaAndTipoRequisicaoAndQuestionario(ChecklistQuestionarioDTO checklistQuestionarioDTO) throws PersistenceException {

		StringBuilder sqlBuffer = new StringBuilder();
		List param = new ArrayList();

			sqlBuffer.append("select requisicaoquestionario.idrequisicaoquestionario, requisicaoquestionario.idquestionario, requisicaoquestionario.idrequisicao,") ;
			sqlBuffer.append(" requisicaoquestionario.datahoragrav,  ");
			sqlBuffer.append(" requisicaoquestionario.aba, requisicaoquestionario.situacao, questionario.nomequestionario, requisicaoquestionario.confirmacao ");
			sqlBuffer.append(" from requisicaoquestionario inner join ");
			sqlBuffer.append(" questionario on requisicaoquestionario.idquestionario = questionario.idquestionario ");
			sqlBuffer.append(" inner join  questionario quest on requisicaoquestionario.idquestionario = quest.idquestionario ");
			if(checklistQuestionarioDTO.getIdQuestionario() != null ){
				sqlBuffer.append(" where (requisicaoquestionario.idquestionario = ? ");
				param.add(checklistQuestionarioDTO.getIdQuestionario());
				if(checklistQuestionarioDTO.getIdQuestionarioOrigem() != null){
		        	sqlBuffer.append(" or questionario.idquestionarioorigem = ? ");
		        	param.add(checklistQuestionarioDTO.getIdQuestionarioOrigem());
		        }
				sqlBuffer.append(")");
			}
	        if(checklistQuestionarioDTO.getIdTipoAba() != null){
	        	sqlBuffer.append(" and requisicaoquestionario.idtipoaba = ? ");
	        	param.add(checklistQuestionarioDTO.getIdTipoAba());
	        }
	        if(checklistQuestionarioDTO.getIdTipoRequisicao() != null){
	        	sqlBuffer.append(" and requisicaoquestionario.idtiporequisicao = ? ");
	        	param.add(checklistQuestionarioDTO.getIdTipoRequisicao());
	        }
	        if(checklistQuestionarioDTO.getIdRequisicao() != null){
	        	sqlBuffer.append(" and requisicaoquestionario.idrequisicao = ? ");
	        	param.add(checklistQuestionarioDTO.getIdRequisicao());
	        }

	        //sqlBuffer.append(" order by requisicaoquestionario.dataquestionario desc, idrequisicaoquestionario desc; ");
	        sqlBuffer.append(" order by requisicaoquestionario.datahoragrav desc, idrequisicaoquestionario desc ");

		List lista = this.execSQL(sqlBuffer.toString(), param.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idRequisicaoQuestionario");
		listRetorno.add("idQuestionario");
		listRetorno.add("idRequisicao");
		listRetorno.add("dataHoraGrav");
		listRetorno.add("aba");
		listRetorno.add("situacao");
		listRetorno.add("nomeQuestionario");
		listRetorno.add("confirmacao");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
	    // TODO Auto-generated method stub
	    super.updateNotNull(obj);
	}

	public boolean gravaConfirmacao(Integer idRequisicao, String confirmacao){
		String sql = "update " + getTableName() + " set confirmacao = ? where idRequisicaoQuestionario = ? ";
		Object[] parametros = {confirmacao, idRequisicao};
		try {
			this.execUpdate(sql, parametros);
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	public Collection listNaoConfirmados(Integer id, Integer tipo) throws PersistenceException {
		StringBuilder sqlBuffer = new StringBuilder();
		List param = new ArrayList();

		sqlBuffer.append("select requisicaoquestionario.idrequisicaoquestionario from requisicaoquestionario where idrequisicao = ? and idtiporequisicao = ? and (confirmacao <> 'S' or confirmacao is null)") ;
		param.add(id);
		param.add(tipo);
		List lista = this.execSQL(sqlBuffer.toString(), param.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idRequisicaoQuestionario");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;

	}
}
