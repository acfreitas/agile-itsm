/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * @author valdoilo
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ComentariosDAO extends CrudDaoDefaultImpl {
    
    /**
     * Consulta Comentários da Base de Conhecimento informada.
     * 
     * @param baseConhecimentoBean
     * @return comentarios
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public Collection<ComentariosDTO> consultarComentarios(BaseConhecimentoDTO baseConhecimentoBean) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idBaseConhecimento", "=", baseConhecimentoBean.getIdBaseConhecimento()));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("dataInicio", "desc"));
		
		return super.findByCondition(condicao, ordenacao);
    }
    
    public ComentariosDAO() {
    	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }
    
    @Override
    public Collection find(IDto arg0) throws PersistenceException {
    	return null;
    }
    
    @Override
    public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDCOMENTARIO", "idComentario", true, true,false, true));
		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento",false, false, false, false));
		listFields.add(new Field("COMENTARIO", "comentario", false, false,false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));
		listFields.add(new Field("NOTA", "nota", false, false, false, false));
		listFields.add(new Field("EMAIL", "email", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false,false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false,false));
		
		return listFields;
    }
    
    @Override
    public String getTableName() {
    	return "COMENTARIOS";
    }
    
    @Override
    public Collection list() throws PersistenceException {
    	return null;
    }
    
    @Override
    public Class getBean() {
    	return ComentariosDTO.class;
    }
    
    public Double calcularNota(Integer idBaseConhecimento) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		
		if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)){
			sql.append("SELECT  ROUND(AVG(cast(c.nota as double  precision))) AS media  FROM COMENTARIOS c  where c.idbaseconhecimento = ? ");
			
		}else if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
			sql.append("SELECT  ROUND(AVG(cast(c.nota as decimal)),2) AS media  FROM COMENTARIOS c  where c.idbaseconhecimento = ? ");
		}else{
			 sql.append("SELECT  ROUND(AVG(c.nota)) AS media  FROM "+ getTableName() + " c  where c.idbaseconhecimento = ?");
		}
		
		list = this.execSQL(sql.toString(), new Object[] {idBaseConhecimento});
		fields.add("media");
		List novaLista = this.listConvertion(getBean(), list, fields);
		if (novaLista != null && !novaLista.isEmpty()) {
		    if ((ComentariosDTO) novaLista.get(0) != null && ((ComentariosDTO) novaLista.get(0)).getMedia() != null){
			return ((ComentariosDTO) novaLista.get(0)).getMedia();
		    }else{
			return null;
		    }
		} else {
		    return null;
		}
    }
    
    public Long contarVotos(Integer idBaseConhecimento) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "SELECT  COUNT(c.nota) AS votos FROM " + getTableName()+ " c where c.idbaseconhecimento = ? ";
		list = this.execSQL(sql, new Object[] {idBaseConhecimento});
		fields.add("votos");
		List novaLista = this.listConvertion(getBean(), list, fields);
		if (novaLista != null && !novaLista.isEmpty()) {
		    return ((ComentariosDTO) novaLista.get(0)).getVotos();
		} else {
		    return null;
		}
    }
    
    public Collection<ComentariosDTO> consultarComentariosPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
    	List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select c.nota, b.titulo ")
		.append("from "+this.getTableName()+" c ")
		.append("inner join baseconhecimento b on b.idbaseconhecimento = c.idbaseconhecimento ");
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)) {
			sql.append("where to_char(c.datainicio, 'dd-MM-yyyy') between ? and ? and b.datafim is null ");
		}else{
			sql.append("where c.datainicio between ? and ? and b.datafim is null ");
		}
		
		sql.append("group by c.nota, b.titulo ")
		.append("order by c.nota, b.titulo ");
		
		parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(baseConhecimentoDTO.getDataInicio(), "yyyy-MM-dd") + " 00:00:00"));
		//parametro.add(baseConhecimentoDTO.getDataInicio() + " 00:00:00");
		parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(baseConhecimentoDTO.getDataFim(), "yyyy-MM-dd") + " 23:59:59"));
		//parametro.add(baseConhecimentoDTO.getDataFim() + " 23:59:59");
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("nota");
		listRetornor.add("titulo");
		
		if (list != null) {
			return this.listConvertion(ComentariosDTO.class, list, listRetornor);
		}else{
			return new ArrayList();
		}
    	
    }
    
}
