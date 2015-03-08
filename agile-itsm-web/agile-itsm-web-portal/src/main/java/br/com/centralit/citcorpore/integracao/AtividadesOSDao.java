package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({  "rawtypes", "unchecked" })
public class AtividadesOSDao extends CrudDaoDefaultImpl {
	public AtividadesOSDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAtividadesOS", "idAtividadesOS", true, true, false, false));
		listFields.add(new Field("idOS", "idOS", false, false, false, false));
		listFields.add(new Field("sequencia", "sequencia", false, false, false, false));
		listFields.add(new Field("idAtividadeServicoContrato", "idAtividadeServicoContrato", false, false, false, false));
		listFields.add(new Field("descricaoAtividade", "descricaoAtividade", false, false, false, false));
		listFields.add(new Field("obsAtividade", "obsAtividade", false, false, false, false));
		listFields.add(new Field("custoAtividade", "custoAtividade", false, false, false, false));
		listFields.add(new Field("glosaAtividade", "glosaAtividade", false, false, false, false));
		listFields.add(new Field("qtdeExecutada", "qtdeExecutada", false, false, false, false));
		listFields.add(new Field("complexidade", "complexidade", false, false, false, false));
		listFields.add(new Field("formula", "formula", false, false, false, false));
		listFields.add(new Field("contabilizar", "contabilizar", false, false, false, false));
		listFields.add(new Field("idServicoContratoContabil", "idServicoContratoContabil", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "AtividadesOS";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AtividadesOSDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdOS(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idOS", "=", parm));
		ordenacao.add(new Order("sequencia"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdOS(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idOS", "=", parm));
		super.deleteByCondition(condicao);
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	/**
	 * Retorna o custo de atividades por id OS.
	 * 
	 * @param idOs
	 * @return <code>Double</code>
	 * @throws Exception
	 */
	public Double retornarCustoAtividadeOSByIdOs(Integer idOs) throws PersistenceException {
		String sql = "select sum(custoatividade) from atividadesos where idos = ?";
		List dados = this.execSQL(sql, new Object[] { idOs });
		List fields = new ArrayList();
		fields.add("custoAtividade");

		Collection<AtividadesOSDTO> listaDeAtividadeOS = this.listConvertion(
				getBean(), dados, fields);

		if (listaDeAtividadeOS != null && !listaDeAtividadeOS.isEmpty()) {
			for (AtividadesOSDTO atividade : listaDeAtividadeOS) {
				return atividade.getCustoAtividade();

			}
			return null;
		} else {
			return null;
		}
	}
	
	/**
	 * Retorna list de atividades por id OS.
	 * 
	 * @param idOs
	 * @return <code>Double</code>
	 * @throws Exception
	 */
	public Collection listOSNumeroAtividade(Integer idAtividadesOS) throws PersistenceException {
		String sql = "SELECT os.numero, osAtv.descricaoatividade,  osAtv.idos, osAtv.idatividadesos " +
				"FROM atividadesos osAtv INNER JOIN os  ON os.idos = osAtv.idos WHERE osAtv.idatividadesos = ?";
		List dados = this.execSQL(sql, new Object[] { idAtividadesOS });
		List fields = new ArrayList();
		fields.add("numeroOS");
		fields.add("descricaoAtividade");
		fields.add("idOS");
		fields.add("idAtividadesOS");


		return  this.listConvertion(getBean(), dados, fields);
	}

	/**
	 * Retorna a quantidadedeExecução de atividades por id OS.
	 * 
	 * @param idOs
	 * @return <code>Double</code>
	 * @throws Exception
	 */
	public Double retornarQtdExecucao(Integer idOs) throws PersistenceException {
		String sql = "select sum(qtdeExecutada) from atividadesos where idos = ?";
		List dados = this.execSQL(sql, new Object[] { idOs });
		List fields = new ArrayList();
		fields.add("qtdeExecutada");

		Collection<AtividadesOSDTO> listaDeAtividadeOS = this.listConvertion(
				getBean(), dados, fields);

		if (listaDeAtividadeOS != null && !listaDeAtividadeOS.isEmpty()) {
			for (AtividadesOSDTO atividade : listaDeAtividadeOS) {
				return atividade.getQtdeExecutada();
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 *  Retorna a soma das glosas de atividades por id OS.
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public Double retornarGlosaAtividadeOSByIdOs(Integer idOs) throws PersistenceException {
		String sql = "select sum(glosaatividade) from atividadesos where idos = ?";
		List dados = this.execSQL(sql, new Object[] { idOs });
		List fields = new ArrayList();
		fields.add("glosaAtividade");

		Collection<AtividadesOSDTO> listaDeAtividadeOS = this.listConvertion(
				getBean(), dados, fields);

		if (listaDeAtividadeOS != null && !listaDeAtividadeOS.isEmpty()) {
			for (AtividadesOSDTO atividade : listaDeAtividadeOS) {
				return atividade.getGlosaAtividade();
			}
			return null;
		} else {
			return null;
		}

	}
	
	/**
	 * Método para atualizar observao de os não homologadas
	 * 
	 * @param observacao
	 * @param os
	 * @throws Exception
	 */
	public boolean atualizaObservacao(Integer idatividadeservicocontrato, String observacao, List<OSDTO> os) throws PersistenceException {
		int resp = 0;
		for (OSDTO osdto : os) {
			List parametros = new ArrayList();
			parametros.add(observacao);
			parametros.add(idatividadeservicocontrato);
			parametros.add(osdto.getIdOS());
			String sql = "UPDATE atividadesos SET obsatividade = ? WHERE idatividadeservicocontrato = ? AND idos = ? ";
			int temp = super.execUpdate(sql, parametros.toArray());
			if(temp > 0)
				resp = temp;
		}
		if (resp>0)
			return true;
		else
			return false;
	}
	
	public Collection findByIdOsServicoContratoContabil(Integer idOS, Integer idServicoContratoContabil) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idOS", "=", idOS));
		condicao.add(new Condition("idServicoContratoContabil", "=", idServicoContratoContabil));
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idOS"));
		return super.findByCondition(condicao, ordenacao);
	}
}
