package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConhecimentoProblemaDao extends CrudDaoDefaultImpl {


	public ConhecimentoProblemaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDPROBLEMA", "idProblema", false, false, false, false));
		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "CONHECIMENTOPROBLEMA";
	}

	@Override
	public Class getBean() {
		return ConhecimentoProblemaDTO.class;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	public void deleteByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		super.deleteByCondition(condicao);
	}

	public void deleteByIdBaseConhecimento(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idBaseConhecimento", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdBaseConhecimento(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idBaseConhecimento", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna uma lista de base de conhecimento de acordo com o idProblema passado
	 * @param parm
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public ConhecimentoProblemaDTO getErroConhecidoByProblema(Integer parm) throws PersistenceException {
		
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();
		
		List lista = new ArrayList();
		
		List listRetorno = new ArrayList();
		
		sql.append("select conhecimentoproblema.idbaseconhecimento,conhecimentoproblema.idproblema,baseconhecimento.titulo,CASE WHEN baseconhecimento.status ='S' THEN 'Publicado' WHEN baseconhecimento.status ='N' THEN 'Não Publicado' ELSE baseconhecimento.status END as status ");
		sql.append("from conhecimentoproblema ");
		sql.append("inner join baseconhecimento on baseconhecimento.idbaseconhecimento = conhecimentoproblema.idbaseconhecimento ");
		sql.append("where  baseconhecimento.dataFim is null and baseconhecimento.erroconhecido = ? and baseconhecimento.arquivado = ?");
		
		parametro.add("S");
		parametro.add("N");
		
		if(parm!=null){
			sql.append(" and conhecimentoproblema.idproblema = ? ");
			parametro.add(parm);
		}
		
		lista = this.execSQL(sql.toString(), parametro.toArray());
		
		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idProblema");
		listRetorno.add("titulo");
		listRetorno.add("status");
		
		if (lista != null && !lista.isEmpty()) {
			Collection<ConhecimentoProblemaDTO> listaBaseConhecimentoByIdProblema = this.listConvertion(ConhecimentoProblemaDTO.class, lista, listRetorno);
			return (ConhecimentoProblemaDTO) listaBaseConhecimentoByIdProblema.toArray()[0];
		}
		
		return null;
		
	}
	
	/**
	 * Retorna uma lista de Erro conhecido de acordo com o idProblema passado
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listaErroConhecidoByIdProblema(Integer parm) throws PersistenceException { 
		
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();
		
		List lista = new ArrayList();
		
		List listRetorno = new ArrayList();

		
		sql.append("select bc.idbaseconhecimento  ");
		sql.append(" from conhecimentoproblema cp ");
		sql.append(" inner join baseconhecimento bc on cp.idbaseconhecimento = bc.idbaseconhecimento   ");
		sql.append(" where cp.idproblema = ? ");

		
		if(parm!=null){
			parametro.add(parm);
		}
		
		lista = this.execSQL(sql.toString(), parametro.toArray());
		
		listRetorno.add("idBaseConhecimento");
		
		if (lista != null && !lista.isEmpty()) {
			Collection<BaseConhecimentoDTO> listaBaseConhecimentoByIdProblema = this.listConvertion(BaseConhecimentoDTO.class, lista, listRetorno);
			return listaBaseConhecimentoByIdProblema;
		}
		
		return null;
		
	}

}
