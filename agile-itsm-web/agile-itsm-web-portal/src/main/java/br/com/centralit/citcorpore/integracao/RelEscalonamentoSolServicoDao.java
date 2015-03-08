package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RelEscalonamentoSolServicoDto;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class RelEscalonamentoSolServicoDao extends CrudDaoDefaultImpl {

	public RelEscalonamentoSolServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<Field>();
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idEscalonamento", "idEscalonamento", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "relEscalonamentoSolServico";
	}

	@Override
	public Collection list() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		// TODO Auto-generated method stub
		return RelEscalonamentoSolServicoDto.class;
	}
	
	/**
	 * Verifica se existe relacionamento. Se existir retorna 'true', senao retorna 'false';
	 * 
	 * @param 
	 * @return exists
	 * @throws Exception
	 */
	public boolean temRelacionamentoSolicitacaoEscalonamento(Integer idSolicitacaoServico, Integer idEscalonamento) throws PersistenceException {
		boolean exists;
		List<Integer> parametro = new ArrayList<Integer>();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where idSolicitacaoServico = ? and idEscalonamento = ?  ");
		parametro.add(idSolicitacaoServico);
		parametro.add(idEscalonamento);
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

}
