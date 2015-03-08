package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ObjetivoPlanoMelhoriaDao extends CrudDaoDefaultImpl {
	public ObjetivoPlanoMelhoriaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idObjetivoPlanoMelhoria" ,"idObjetivoPlanoMelhoria", true, true, false, false));
		listFields.add(new Field("idPlanoMelhoria" ,"idPlanoMelhoria", false, false, false, false));
		listFields.add(new Field("tituloObjetivo" ,"tituloObjetivo", false, false, false, false));
		listFields.add(new Field("detalhamento" ,"detalhamento", false, false, false, false));
		listFields.add(new Field("resultadoEsperado" ,"resultadoEsperado", false, false, false, false));
		listFields.add(new Field("medicao" ,"medicao", false, false, false, false));
		listFields.add(new Field("responsavel" ,"responsavel", false, false, false, false));
		listFields.add(new Field("criadoPor" ,"criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoPor" ,"modificadoPor", false, false, false, false));
		listFields.add(new Field("dataCriacao" ,"dataCriacao", false, false, false, false));
		listFields.add(new Field("ultModificacao" ,"ultModificacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ObjetivoPlanoMelhoria";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ObjetivoPlanoMelhoriaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPlanoMelhoria", "=", parm));
		ordenacao.add(new Order("tituloObjetivo"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPlanoMelhoria", "=", parm));
		super.deleteByCondition(condicao);
	}


	public Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoria(ObjetivoPlanoMelhoriaDTO obj) throws PersistenceException {

		StringBuilder sql = new StringBuilder();

		List parametro = new ArrayList();

		List list = new ArrayList();

		sql.append("select idobjetivoplanomelhoria,tituloobjetivo,resultadoesperado,medicao,responsavel from " + getTableName() + " where idplanomelhoria = ? ");

		parametro.add(obj.getIdPlanoMelhoria());

		list = this.execSQL(sql.toString(), parametro.toArray());
		List listaRetorno = new ArrayList();
		listaRetorno.add("idObjetivoPlanoMelhoria");
		listaRetorno.add("tituloObjetivo");
		listaRetorno.add("resultadoEsperado");
		listaRetorno.add("medicao");
		listaRetorno.add("responsavel");


		if (list != null && !list.isEmpty()) {
			Collection<ObjetivoPlanoMelhoriaDTO> listObjetivoPlanoMelhoria = this.listConvertion(ObjetivoPlanoMelhoriaDTO.class, list, listaRetorno);
			return listObjetivoPlanoMelhoria;
		}

		return null;
	}
	@Override
	public void update(IDto obj) throws PersistenceException {
		ObjetivoPlanoMelhoriaDTO objetivoPlanoMelhoriaDTO = (br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO) restore(obj);
		if (objetivoPlanoMelhoriaDTO != null){
			((ObjetivoPlanoMelhoriaDTO)obj).setCriadoPor(objetivoPlanoMelhoriaDTO.getCriadoPor());
			((ObjetivoPlanoMelhoriaDTO)obj).setDataCriacao(objetivoPlanoMelhoriaDTO.getDataCriacao());
		}
		super.update(obj);
	}
}
