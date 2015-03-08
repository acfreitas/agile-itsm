package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AcaoPlanoMelhoriaDao extends CrudDaoDefaultImpl {

	public AcaoPlanoMelhoriaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAcaoPlanoMelhoria", "idAcaoPlanoMelhoria", true, true, false, false));
		listFields.add(new Field("idPlanoMelhoria", "idPlanoMelhoria", false, false, false, false));
		listFields.add(new Field("idObjetivoPlanoMelhoria", "idObjetivoPlanoMelhoria", false, false, false, false));
		listFields.add(new Field("tituloAcao", "tituloAcao", false, false, false, false));
		listFields.add(new Field("detalhamentoAcao", "detalhamentoAcao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("responsavel", "responsavel", false, false, false, false));
		listFields.add(new Field("dataConclusao", "dataConclusao", false, false, false, false));
		listFields.add(new Field("criadoPor", "criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoPor", "modificadoPor", false, false, false, false));
		listFields.add(new Field("dataCriacao", "dataCriacao", false, false, false, false));
		listFields.add(new Field("ultModificacao", "ultModificacao", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "AcaoPlanoMelhoria";
	}

	public Collection<AcaoPlanoMelhoriaDTO> list() throws PersistenceException {
		return null;
	}

	public Class<AcaoPlanoMelhoriaDTO> getBean() {
		return AcaoPlanoMelhoriaDTO.class;
	}

	public Collection<AcaoPlanoMelhoriaDTO> find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPlanoMelhoria", "=", parm));
		ordenacao.add(new Order("tituloAcao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPlanoMelhoria", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdObjetivoPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idObjetivoPlanoMelhoria", "=", parm));
		ordenacao.add(new Order("tituloAcao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdObjetivoPlanoMelhoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idObjetivoPlanoMelhoria", "=", parm));
		super.deleteByCondition(condicao);
	}

	/**
	 * Retorna uma lista de acao plano melhoria de acordo com o plano melhoria passado
	 *
	 * @param acaoPlanoMelhoriaDto
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria(AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDto) throws PersistenceException {
		StringBuilder sql = new StringBuilder();

		List parametro = new ArrayList();

		List list = new ArrayList();

		sql.append("select * from " + getTableName() + " where idobjetivoplanomelhoria = ? ");

		parametro.add(acaoPlanoMelhoriaDto.getIdObjetivoPlanoMelhoria());

		list = this.execSQL(sql.toString(), parametro.toArray());
		List listaRetorno = new ArrayList();
		listaRetorno.add("idAcaoPlanoMelhoria");
		listaRetorno.add("idPlanoMelhoria");
		listaRetorno.add("idObjetivoPlanoMelhoria");
		listaRetorno.add("tituloAcao");
		listaRetorno.add("detalhamentoAcao");
		listaRetorno.add("dataInicio");
		listaRetorno.add("dataFim");
		listaRetorno.add("responsavel");
		listaRetorno.add("dataConclusao");
		listaRetorno.add("criadoPor");
		listaRetorno.add("modificadoPor");
		listaRetorno.add("dataCriacao");
		listaRetorno.add("ultModificacao");

		if (list != null && !list.isEmpty()) {
			Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria = this.listConvertion(AcaoPlanoMelhoriaDTO.class, list, listaRetorno);
			return listAcaoPlanoMelhoria;
		}

		return null;
	}
	@Override
	public void update(IDto obj) throws PersistenceException {
		AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDTO = (br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO) restore(obj);
		if (acaoPlanoMelhoriaDTO != null){
			((AcaoPlanoMelhoriaDTO)obj).setCriadoPor(acaoPlanoMelhoriaDTO.getCriadoPor());
			((AcaoPlanoMelhoriaDTO)obj).setDataCriacao(acaoPlanoMelhoriaDTO.getDataCriacao());
		}
		super.update(obj);
	}

}
