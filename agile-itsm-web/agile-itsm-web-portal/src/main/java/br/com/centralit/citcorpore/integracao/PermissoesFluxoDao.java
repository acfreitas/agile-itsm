package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PermissoesFluxoDao extends CrudDaoDefaultImpl {
    public PermissoesFluxoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idTipoFluxo", "idTipoFluxo", true, false, false, false));
	listFields.add(new Field("idGrupo", "idGrupo", true, false, false, false));
	listFields.add(new Field("criar", "criar", false, false, false, false));
	listFields.add(new Field("executar", "executar", false, false, false, false));
	listFields.add(new Field("delegar", "delegar", false, false, false, false));
	listFields.add(new Field("suspender", "suspender", false, false, false, false));
	listFields.add(new Field("reativar", "reativar", false, false, false, false));
	listFields.add(new Field("alterarsla", "alterarSLA", false, false, false, false));
	listFields.add(new Field("reabrir", "reabrir", false, false, false, false));
	listFields.add(new Field("cancelar", "cancelar", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return this.getOwner() + "PermissoesFluxo";
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Class getBean() {
	return PermissoesFluxoDTO.class;
    }

    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }

    public Collection findByIdTipoFluxo(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idTipoFluxo", "=", parm));
	ordenacao.add(new Order("idGrupo"));
	return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdTipoFluxo(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idTipoFluxo", "=", parm));
	super.deleteByCondition(condicao);
    }

    public List<PermissoesFluxoDTO> findByIdFluxoAndIdUsuario(Integer idUsuario, Integer idItemtrabalho) throws PersistenceException {
        List param = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();

        sql.append("select distinct tipo.idTipoFluxo, ");
        sql.append("    perm.idGrupo, ");
        sql.append("    criar, ");
        sql.append("    executar, ");
        sql.append("    delegar, ");
        sql.append("    suspender, ");
        sql.append("    reativar, ");
        sql.append("    alterarSLA, ");
        sql.append("    reabrir, ");
        sql.append("    cancelar from bpm_tipofluxo tipo ");
        sql.append("	INNER JOIN bpm_fluxo flx on tipo.idtipofluxo = flx.idtipofluxo ");
        sql.append("	INNER JOIN permissoesFluxo perm on tipo.idtipofluxo = perm.idtipofluxo ");
        sql.append("	INNER JOIN gruposempregados gremp  ON perm.idgrupo = gremp.idgrupo ");
        sql.append("	INNER JOIN empregados emp  ON emp.idempregado = gremp.idempregado ");
        sql.append("	INNER JOIN usuario usu ON usu.idempregado = emp.idempregado ");
        sql.append("	INNER JOIN bpm_instanciafluxo  inst ON flx.idfluxo = inst.idfluxo ");
        sql.append("	INNER JOIN bpm_itemtrabalhofluxo item ON  item.idinstancia = inst.idinstancia ");
        sql.append("where item.iditemtrabalho = ? and usu.idusuario = ? ");

        param.add(idItemtrabalho);
        param.add(idUsuario);

        list = this.execSQL(sql.toString(), param.toArray());

        if (list != null && !list.isEmpty()) {
            return (List<PermissoesFluxoDTO>) this.listConvertion(getBean(), list, this.getListRetorno());
        } else {
            return null;
        }
    }

    public List getListRetorno(){
    	List listRetorno = new ArrayList();
        listRetorno.add("idTipoFluxo");
        listRetorno.add("idGrupo");
        listRetorno.add("criar");
        listRetorno.add("executar");
        listRetorno.add("delegar");
        listRetorno.add("suspender");
        listRetorno.add("reativar");
        listRetorno.add("alterarSLA");
        listRetorno.add("reabrir");
        listRetorno.add("cancelar");
        return listRetorno;
    }

    public Collection findByIdGrupo(Integer parm) throws PersistenceException {
    String sql = "SELECT pf.idTipoFluxo, idGrupo, criar, executar, delegar, suspender, reativar, alterarsla, reabrir, cancelar FROM "+ getTableName() + " pf JOIN bpm_tipofluxo tf ON pf.idtipofluxo = tf.idtipofluxo WHERE idgrupo = ? ORDER BY pf.idtipofluxo, criar, executar, delegar, suspender";
    List param = new ArrayList();
    List listRetorno = new ArrayList();
	List list = new ArrayList();
    param.add(parm);
    listRetorno.add("idTipoFluxo");
    listRetorno.add("idGrupo");
    listRetorno.add("criar");
    listRetorno.add("executar");
    listRetorno.add("delegar");
    listRetorno.add("suspender");
    listRetorno.add("reativar");
    listRetorno.add("alterarSLA");
    listRetorno.add("reabrir");
    listRetorno.add("cancelar");
    list = this.execSQL(sql.toString(), param.toArray());
	if (list != null && !list.isEmpty()) {

		return (List<PermissoesFluxoDTO>) this.listConvertion(getBean(), list, listRetorno);

	} else {

		return null;
	}
	/*List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idGrupo", "=", parm));
	ordenacao.add(new Order("idTipoFluxo"));
	ordenacao.add(new Order("criar"));
	ordenacao.add(new Order("executar"));
	ordenacao.add(new Order("delegar"));
	ordenacao.add(new Order("suspender"));
	return super.findByCondition(condicao, ordenacao);*/
    }

    public void deleteByIdGrupo(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idGrupo", "=", parm));
	super.deleteByCondition(condicao);
    }

    @SuppressWarnings("unused")
	public boolean permissaoGrupoExecutor(Integer idTipoMudanca, Integer idGrupoExecutor) throws PersistenceException {
    	StringBuilder sql = new StringBuilder();
    	boolean resultado = false;
    	List parametro = new ArrayList();
    	parametro.add(idGrupoExecutor);
    	parametro.add(idTipoMudanca);

    	sql.append("SELECT pf.idtipofluxo ");
    	sql.append(" FROM permissoesFluxo pf ");
    	sql.append(" INNER JOIN tipomudanca tm ON tm.idtipofluxo = pf.idtipofluxo ");
    	sql.append(" INNER JOIN bpm_tipofluxo tf ON tf.idtipofluxo = tm.idtipofluxo ");
    	sql.append(" where pf.idgrupo = ? ");
    	sql.append(" AND tm.idtipomudanca = ? ");
    	sql.append(" AND pf.criar = 'S' ");

    	List result = null;
    	result = this.execSQL(sql.toString(), parametro.toArray());

    	if (result == null || result.size() <= 0) {
    		resultado = false;
    	}else{
    		resultado = true;
    	}

    	return resultado;
    }

    @SuppressWarnings("unused")
	public boolean permissaoGrupoExecutorLiberacao(Integer idTipoMudanca, Integer idGrupoExecutor) throws PersistenceException {
    	StringBuilder sql = new StringBuilder();
    	boolean resultado = false;
    	List parametro = new ArrayList();
    	parametro.add(idGrupoExecutor);
    	parametro.add(idTipoMudanca);

    	sql.append("SELECT pf.idtipofluxo ");
    	sql.append(" FROM permissoesFluxo pf ");
    	sql.append(" INNER JOIN tipoliberacao tl ON tl.idtipofluxo = pf.idtipofluxo ");
    	sql.append(" INNER JOIN bpm_tipofluxo tf ON tf.idtipofluxo = tl.idtipofluxo ");
    	sql.append(" where pf.idgrupo = ? ");
    	sql.append(" AND tl.idtipoliberacao = ? ");
    	sql.append(" AND pf.criar = 'S' ");

    	List result = null;
    	result = this.execSQL(sql.toString(), parametro.toArray());

    	if (result == null || result.size() <= 0) {
    		resultado = false;
    	}else{
    		resultado = true;
    	}

    	return resultado;
    }

    @SuppressWarnings("unused")
	public boolean permissaoGrupoExecutorProblema(Integer idCategoriaProblema, Integer idGrupoExecutor) throws PersistenceException {
    	StringBuilder sql = new StringBuilder();
    	boolean resultado = false;
    	List parametro = new ArrayList();
    	parametro.add(idGrupoExecutor);
    	parametro.add(idCategoriaProblema);

    	sql.append("SELECT pf.idtipofluxo ");
    	sql.append(" FROM permissoesFluxo pf ");
    	sql.append(" INNER JOIN categoriaproblema tl ON tl.idtipofluxo = pf.idtipofluxo ");
    	sql.append(" INNER JOIN bpm_tipofluxo tf ON tf.idtipofluxo = tl.idtipofluxo ");
    	sql.append(" where pf.idgrupo = ? ");
    	sql.append(" AND tl.idcategoriaproblema = ? ");
    	sql.append(" AND pf.criar = 'S' ");

    	List result = null;
    	result = this.execSQL(sql.toString(), parametro.toArray());

    	if (result == null || result.size() <= 0) {
    		resultado = false;
    	}else{
    		resultado = true;
    	}

    	return resultado;
    }

    public PermissoesFluxoDTO permissaoGrupoCancelar(Integer idGrupo, Integer idTipoFluxo) throws PersistenceException{
    	StringBuilder sql = new StringBuilder();
    	List parametro = new ArrayList();
    	List listRetorno = new ArrayList();
    	List lista = new ArrayList();

    	sql.append("select cancelar from permissoesfluxo where idgrupo = ? and idtipofluxo = ?");

    	parametro.add(idGrupo);
    	parametro.add(idTipoFluxo);

    	lista = this.execSQL(sql.toString(), parametro.toArray());

    	listRetorno.add("cancelar");

		try {
			List<PermissoesFluxoDTO> resultado = this.listConvertion(getBean(), lista, listRetorno);
			return  resultado.get(0);
		} catch (Exception e) {
			return null;
		}
    }

    public PermissoesFluxoDTO permissaoGrupoCriar(Integer idGrupo, Integer idTipoFluxo) throws PersistenceException{
    	StringBuilder sql = new StringBuilder();
    	List parametro = new ArrayList();
    	List listRetorno = new ArrayList();
    	List lista = new ArrayList();

    	sql.append("select criar from permissoesfluxo where idgrupo = ? and idtipofluxo = ?");

    	parametro.add(idGrupo);
    	parametro.add(idTipoFluxo);

    	lista = this.execSQL(sql.toString(), parametro.toArray());

    	listRetorno.add("criar");

		try {
			List<PermissoesFluxoDTO> resultado = this.listConvertion(getBean(), lista, listRetorno);
			return  resultado.get(0);
		} catch (Exception e) {
			return null;
		}
    }

    @SuppressWarnings("unused")
	public boolean permissaoGrupoExecutorLiberacaoServico(Integer idGrupoExecutor, Integer idTipoFluxo) throws PersistenceException {
    	StringBuilder sql = new StringBuilder();
    	boolean resultado = false;
    	List parametro = new ArrayList();
    	parametro.add(idGrupoExecutor);
    	parametro.add(idTipoFluxo);

    	sql.append("SELECT pf.idtipofluxo ");
    	sql.append(" FROM permissoesFluxo pf ");
    	sql.append(" where pf.idgrupo = ? ");
    	sql.append(" AND idtipofluxo = ? ");
    	sql.append(" AND criar = 'S' ");

    	List result = null;
    	result = this.execSQL(sql.toString(), parametro.toArray());

    	if (result == null || result.size() <= 0) {
    		resultado = false;
    	}else{
    		resultado = true;
    	}

    	return resultado;
    }

}
