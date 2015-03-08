package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

;
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class ObjetivoMonitoramentoDao extends CrudDaoDefaultImpl {
	public ObjetivoMonitoramentoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idObjetivoMonitoramento", "idObjetivoMonitoramento", true, true, false, false));
		listFields.add(new Field("idObjetivoPlanoMelhoria", "idObjetivoPlanoMelhoria", false, false, false, false));
		listFields.add(new Field("tituloMonitoramento", "tituloMonitoramento", false, false, false, false));
		listFields.add(new Field("fatorCriticoSucesso", "fatorCriticoSucesso", false, false, false, false));
		listFields.add(new Field("kpi", "kpi", false, false, false, false));
		listFields.add(new Field("metrica", "metrica", false, false, false, false));
		listFields.add(new Field("medicao", "medicao", false, false, false, false));
		listFields.add(new Field("relatorios", "relatorios", false, false, false, false));
		listFields.add(new Field("responsavel", "responsavel", false, false, false, false));
		listFields.add(new Field("criadoPor", "criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoPor", "modificadoPor", false, false, false, false));
		listFields.add(new Field("dataCriacao", "dataCriacao", false, false, false, false));
		listFields.add(new Field("ultModificacao", "ultModificacao", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "ObjetivoMonitoramento";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ObjetivoMonitoramentoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdObjetivoPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idObjetivoPlanoMelhoria", "=", parm));
		ordenacao.add(new Order("tituloMonitoramento"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdObjetivoPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idObjetivoPlanoMelhoria", "=", parm));
		super.deleteByCondition(condicao);
	}

	@Override
	public void update(IDto obj) throws PersistenceException {
		ObjetivoMonitoramentoDTO objetivoMonitoramentoDTO = (br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO) restore(obj);
		if (objetivoMonitoramentoDTO != null) {
			((ObjetivoMonitoramentoDTO) obj).setCriadoPor(objetivoMonitoramentoDTO.getCriadoPor());
			((ObjetivoMonitoramentoDTO) obj).setDataCriacao(objetivoMonitoramentoDTO.getDataCriacao());
		}
		super.update(obj);
	}

	/**
	 * Retorna uma lista de objetivos monitoramento de acordo com o objetivo plano melhoria passado;
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento(ObjetivoMonitoramentoDTO obj) throws PersistenceException {

		StringBuilder sql = new StringBuilder();

		List parametro = new ArrayList();

		List list = new ArrayList();

		sql.append("select * from " + getTableName() + " where idObjetivoPlanoMelhoria = ? ");

		parametro.add(obj.getIdObjetivoPlanoMelhoria());

		list = this.execSQL(sql.toString(), parametro.toArray());
		List listaRetorno = new ArrayList();
		listaRetorno.add("idObjetivoMonitoramento");
		listaRetorno.add("idObjetivoPlanoMelhoria");
		listaRetorno.add("tituloMonitoramento");
		listaRetorno.add("fatorCriticoSucesso");
		listaRetorno.add("kpi");
		listaRetorno.add("metrica");
		listaRetorno.add("medicao");
		listaRetorno.add("relatorios");
		listaRetorno.add("responsavel");
		listaRetorno.add("criadoPor");
		listaRetorno.add("modificadoPor");
		listaRetorno.add("dataCriacao");
		listaRetorno.add("ultModificacao");

		if (list != null && !list.isEmpty()) {
			Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento = this.listConvertion(ObjetivoMonitoramentoDTO.class, list, listaRetorno);
			return listObjetivosMonitoramento;
		}

		return null;
	}

}
