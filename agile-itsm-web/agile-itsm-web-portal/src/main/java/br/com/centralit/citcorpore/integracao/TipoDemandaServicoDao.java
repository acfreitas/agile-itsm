package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TipoDemandaServicoDao extends CrudDaoDefaultImpl {

	public TipoDemandaServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return TipoDemandaServicoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idTipoDemandaServico", "idTipoDemandaServico", true, true, false, false));
		listFields.add(new Field("nomeTipoDemandaServico", "nomeTipoDemandaServico", false, false, false, false));
		listFields.add(new Field("classificacao", "classificacao", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "TIPODEMANDASERVICO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeTipoDemandaServico"));
		return super.list(list);
	}

	public Collection<TipoDemandaServicoDTO> listSolicitacoes() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("classificacao", "<>", "O"));
		condicao.add(new Condition("deleted", "is", null));
		condicao.add(new Condition(Condition.OR, "deleted", "<>", "Y"));
		ordenacao.add(new Order("nomeTipoDemandaServico"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Retorna lista de Tipo Demanda por nome.
	 *
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByNome(TipoDemandaServicoDTO tipoDemandaServicoDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeTipoDemandaServico", "=", tipoDemandaServicoDTO.getNomeTipoDemandaServico()));
		ordenacao.add(new Order("nomeTipoDemandaServico"));
		return super.findByCondition(condicao, ordenacao);
	}


	/**
	 * @author euler.ramos
	 * @param tipoDemandaServicoDTO
	 * @return
	 * @throws Exception
	 */
	public Collection findByClassificacao(String classificacao) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from tipodemandaservico");

		if ((classificacao!=null)&&(classificacao.length()>0)){
			sql.append(" where (classificacao in ("+classificacao+"))");
		}
		sql.append(" order by idtipodemandaservico ");
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), null);

		List listRetorno = new ArrayList();
		listRetorno.add("idTipoDemandaServico");
		listRetorno.add("nomeTipoDemandaServico");
		listRetorno.add("classificacao");
		listRetorno.add("deleted");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return (result == null ? new ArrayList<AnexoBaseConhecimentoDTO>() : result);
	}

	/**
	 * @see br.com.centralit.citcorpore.negocio.TipoDemandaService#validarExclusaoVinculada(HashMap)
	 *
	 * @author Ezequiel
	 */
	public List validarExclusaoVinculada(Map mapFields) throws PersistenceException {

		StringBuilder query = new StringBuilder();

		final Integer idTipoDemanda = Integer.valueOf(mapFields.get("IDTIPODEMANDASERVICO").toString());

		query.append(" SELECT COUNT(IDSERVICO) as quantidade");

		query.append(" FROM SERVICO s ");

		query.append(" INNER JOIN TIPODEMANDASERVICO tp ON tp.idtipodemandaservico = s.idtipodemandaservico");

		query.append(" WHERE s.idtipodemandaservico = " +  idTipoDemanda);

		final List parametro = new ArrayList();

        List list = this.execSQL(query.toString(), parametro.toArray());

        final List listRetorno = new ArrayList();

        listRetorno.add("quantidade");

        return engine.listConvertion(getBean(), list, listRetorno);

	}
}