package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OpiniaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class OpiniaoDao extends CrudDaoDefaultImpl {

	public OpiniaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return OpiniaoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idOpiniao", "idOpiniao", true, true, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("idSolicitacao", "idSolicitacao", false, false, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		listFields.add(new Field("hora", "hora", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "opiniao";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idOpiniao"));
		return super.list(list);
	}

	public Collection findByTipoAndPeriodo(String tipo, Integer idContrato, Date dataInicial, Date dataFinal) throws PersistenceException {
		List parametros = new ArrayList();
		parametros.add(idContrato);
		parametros.add(dataInicial);
		parametros.add(dataFinal);
		parametros.add(tipo);
		String sql = "select " + this.getNamesFieldsStr() + " from " + this.getTableName() + " where idSolicitacao in (select idSolicitacao from solicitacaoservico where idservicocontrato in (select idservicocontrato from servicocontrato where idcontrato = ?) and datahorasolicitacao between ? and ?) and tipo = ? order by idSolicitacao";
		List lista = this.execSQL(sql, parametros.toArray());
		return this.listConvertion(getBean(), lista, this.getListNamesFieldClass());
	}
	
	public OpiniaoDTO findByIdSolicitacao(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacao", "=", idSolicitacao));
		ordenacao.add(new Order("idOpiniao"));
		Collection col = super.findByCondition(condicao, ordenacao);
		if (col != null && !col.isEmpty())
			return (OpiniaoDTO) ((List) col).get(0);
		else
			return null;
	}
}
