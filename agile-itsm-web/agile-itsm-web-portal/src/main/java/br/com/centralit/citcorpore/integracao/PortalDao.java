package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PortalDao extends CrudDaoDefaultImpl  {

	public PortalDao()
	{
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}




	@Override
	public Collection getFields()
	{
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idPortal", "idPortal", true, true, false, false));
		listFields.add(new Field("idItem", "idItem", false, false, false, false));
		listFields.add(new Field("posicaoX", "posicaoX", false, false, false, false));
		listFields.add(new Field("posicaoY", "posicaoY", false, false, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("coluna", "coluna", false, false, false, false));
		listFields.add(new Field("largura", "largura", false, false, false, false));
		listFields.add(new Field("altura", "altura", false, false, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		/*listFields.add(new Field("hora", "hora", false, false, false, false));*/

		return listFields;
	}

	@Override
	public String getTableName()
	{
		return "portal";
	}
	public Collection list() throws PersistenceException
	{
		List list = new ArrayList();
		list.add(new Order("idUsuario"));
		return super.list(list);
	}

	public Collection listByUsuario(Integer idUsuario) throws Exception
	{
		Object[] objs = new Object[] {idUsuario};

		String sql = " SELECT idUsuario from portal where idUsuario = ? ";

		List lista = this.execSQL(sql, objs);
		List listRetorno = new ArrayList();
		listRetorno.add("idUsuario");

		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}

	public Collection findByCondition(Integer id) throws Exception
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idUsuario", "=", id));
		list2.add(new Order("idItem"));
		return super.findByCondition(list1, list2);
	}

	public Collection findByCondition(Integer idUsuario, Integer idItem) throws Exception
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idUsuario", "=", idUsuario));
		list1.add(new Condition("idItem", "=", idItem));
		list2.add(new Order("idItem"));
		return super.findByCondition(list1, list2);
	}

    @Override
	public void update(IDto obj) throws PersistenceException {
    	PortalDTO dto = (PortalDTO) obj;
    	List param = new ArrayList();
    	param.add(dto.getPosicaoX());
    	param.add(dto.getPosicaoY());
    	param.add(dto.getLargura());
    	param.add(dto.getAltura());
    	param.add(dto.getData());
    	/*param.add(dto.getHora());*/
    	param.add(dto.getIdUsuario());
    	param.add(dto.getIdItem());
		String str = "UPDATE " +getTableName()+ " SET posicaoX = ?, posicaoY = ?, largura=? ,altura =?, data =? WHERE idusuario = ? AND iditem = ?";
		super.execUpdate(str, param.toArray());
	}



	@Override
	public void delete(IDto obj) throws PersistenceException {
    	PortalDTO dto = (PortalDTO) obj;
    	List param = new ArrayList();
    	param.add(dto.getIdUsuario());
    	param.add(dto.getIdItem());
		String str = "DELETE FROM " +getTableName()+ " WHERE idusuario = ? AND iditem = ?";
		super.execUpdate(str, param.toArray());
	}

	public Collection find(IDto obj) throws PersistenceException
    {
		List ordem = new ArrayList();
		ordem.add(new Order("idUsuario"));
		return super.find(obj, ordem);
    }

	@Override
	public Class getBean() {
		return PortalDTO.class;
	}

	/**
	 * @param idServico
	 * @return
	 * @throws Exception
	 */
	public boolean existeQuestionarioServico(final Integer idServico) throws PersistenceException {

		try{
			Object[] param = new Object[] {idServico};

			String query = "SELECT count(*) FROM "
					+ "	servico s"
					+ " INNER JOIN"
					+ " servicocontrato sc ON sc.idservico = s.idservico"
					+ " INNER JOIN"
					+ "	templatesolicitacaoservico t ON t.idtemplate = s.idtemplatesolicitacao"
					+ " INNER JOIN"
					+ "	questionario q ON q.idquestionario = t.idquestionario"
					+ " WHERE"
					+ "	s.idservico = ?;";

			 	List lista = this.execSQL(query, param);

		        Object[] row = (Object[]) lista.get(0);

		        return Integer.parseInt(row[0].toString()) > 0;

		}catch(NumberFormatException ex){

			return Boolean.FALSE;
		}
	}

	/**
	 * @param idServico
	 * @return
	 * @throws Exception
	 */
	public boolean existeQuestionario(final Integer idServico) throws PersistenceException {

		try{

			Object[] param = new Object[] {idServico};

			String query = "SELECT COUNT(idquestionario) FROM servico s "
					+ "		INNER JOIN templatesolicitacaoservico t ON t.idtemplate = s.idtemplatesolicitacao "
					+ "		WHERE s.idservico=?";

			List lista = this.execSQL(query, param);

	        Object[] row = (Object[]) lista.get(0);

	        return Integer.parseInt(row[0].toString()) > 0;

		}catch(NumberFormatException ex){

			return Boolean.FALSE;
		}

	}

	/**
	 * @param idServicoCatalogo
	 * @return
	 */
	public Integer obterIdQuestionarioServico(Integer idServicoCatalogo) {

		Object[] param = new Object[] {idServicoCatalogo};

		String query = "select idQuestionario from servico s INNER JOIN templatesolicitacaoservico t ON t.idtemplate = s.idtemplatesolicitacao WHERE s.idservico=?";

		List lista;
		try {

			lista = this.execSQL(query, param);

			if (lista != null && lista.size() > 0){

				Object[] row = (Object[]) lista.get(0);

			    return Integer.parseInt(row[0].toString());
			}

		} catch (PersistenceException e) {

			e.printStackTrace();
		}

		return null;
	}

}
