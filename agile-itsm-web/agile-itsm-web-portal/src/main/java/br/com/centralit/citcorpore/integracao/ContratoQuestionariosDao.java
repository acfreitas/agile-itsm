package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ContratoQuestionariosDao extends CrudDaoDefaultImpl {

	/**
	 *
	 */
    private static final String SQL_LIST_BY_ID_CONTRATO_AND_ABA = "SELECT CONTRATOQUESTIONARIOS.IDCONTRATOQUESTIONARIO AS idContratoQuestionario, CONTRATOQUESTIONARIOS.IDQUESTIONARIO AS idQuestionario, CONTRATOQUESTIONARIOS.IDCONTRATO AS idContrato, "+
                                                                "       CONTRATOQUESTIONARIOS.DATAQUESTIONARIO AS dataQuestionario, CONTRATOQUESTIONARIOS.IDPROFISSIONAL as idProfissional, CONTRATOQUESTIONARIOS.IDEMPRESA as idEmpresa, "+
                                                                "       CONTRATOQUESTIONARIOS.ABA AS aba, CONTRATOQUESTIONARIOS.SITUACAO as situacao, QUESTIONARIO.NOMEQUESTIONARIO AS nomeQuestionario, CONTRATOQUESTIONARIOS.situacaoComplemento as situacaoComplemento "+
                                                                "  FROM CONTRATOQUESTIONARIOS LEFT OUTER JOIN "+
                                                                "       QUESTIONARIO ON CONTRATOQUESTIONARIOS.IDQUESTIONARIO = QUESTIONARIO.IDQUESTIONARIO "+
                                                                " WHERE CONTRATOQUESTIONARIOS.IDCONTRATO = ? "+
                                                                "   AND UPPER(CONTRATOQUESTIONARIOS.ABA) IN (#ABA#) "+
                                                                " ORDER BY CONTRATOQUESTIONARIOS.DATAQUESTIONARIO DESC, IDCONTRATOQUESTIONARIO DESC";

    private static final String SQL_LIST_BY_ID_CONTRATO_AND_ABA_CRESCENTE = "SELECT CONTRATOQUESTIONARIOS.IDCONTRATOQUESTIONARIO AS idContratoQuestionario, CONTRATOQUESTIONARIOS.IDQUESTIONARIO AS idQuestionario, CONTRATOQUESTIONARIOS.IDCONTRATO AS idContrato, "+
    "       CONTRATOQUESTIONARIOS.DATAQUESTIONARIO AS dataQuestionario, CONTRATOQUESTIONARIOS.IDPROFISSIONAL as idProfissional, CONTRATOQUESTIONARIOS.IDEMPRESA as idEmpresa, "+
    "       CONTRATOQUESTIONARIOS.ABA AS aba, CONTRATOQUESTIONARIOS.SITUACAO as situacao, Profissionais.ConhecidoComo AS profissional, QUESTIONARIO.NOMEQUESTIONARIO AS nomeQuestionario, CONTRATOQUESTIONARIOS.situacaoComplemento as situacaoComplemento "+
    "  FROM CONTRATOQUESTIONARIOS INNER JOIN "+
    "       Profissionais ON CONTRATOQUESTIONARIOS.IDPROFISSIONAL = Profissionais.IdProfissional INNER JOIN "+
    "       QUESTIONARIO ON CONTRATOQUESTIONARIOS.IDQUESTIONARIO = QUESTIONARIO.IDQUESTIONARIO "+
    " WHERE CONTRATOQUESTIONARIOS.IDCONTRATO = ? "+
    "   AND UPPER(CONTRATOQUESTIONARIOS.ABA) IN (#ABA#) "+
    " ORDER BY CONTRATOQUESTIONARIOS.DATAQUESTIONARIO, IDCONTRATOQUESTIONARIO DESC";

    private static final String SQL_ULTIMO_BY_ID_CONTRATO_AND_ABA = "SELECT CONTRATOQUESTIONARIOS.IDCONTRATOQUESTIONARIO AS idContratoQuestionario, CONTRATOQUESTIONARIOS.IDQUESTIONARIO AS idQuestionario, CONTRATOQUESTIONARIOS.IDCONTRATO AS idContrato, "+
    "       CONTRATOQUESTIONARIOS.DATAQUESTIONARIO AS dataQuestionario, CONTRATOQUESTIONARIOS.IDPROFISSIONAL as idProfissional, CONTRATOQUESTIONARIOS.IDEMPRESA as idEmpresa, "+
    "       CONTRATOQUESTIONARIOS.ABA AS aba, CONTRATOQUESTIONARIOS.SITUACAO as situacao, Profissionais.ConhecidoComo AS profissional, QUESTIONARIO.NOMEQUESTIONARIO AS nomeQuestionario"+
    "  FROM CONTRATOQUESTIONARIOS INNER JOIN "+
    "       Profissionais ON CONTRATOQUESTIONARIOS.IDPROFISSIONAL = Profissionais.IdProfissional INNER JOIN "+
    "       QUESTIONARIO ON CONTRATOQUESTIONARIOS.IDQUESTIONARIO = QUESTIONARIO.IDQUESTIONARIO "+
    " WHERE CONTRATOQUESTIONARIOS.IDCONTRATO = ? "+
    "   AND CONTRATOQUESTIONARIOS.ABA IN (#ABA#) "+
    " ORDER BY IDCONTRATOQUESTIONARIO DESC, CONTRATOQUESTIONARIOS.DATAQUESTIONARIO DESC";

    private static final String SQL_ULTIMO_BY_ID_CONTRATO_AND_ABA_PERIODO = "SELECT CONTRATOQUESTIONARIOS.IDCONTRATOQUESTIONARIO AS idContratoQuestionario, CONTRATOQUESTIONARIOS.IDQUESTIONARIO AS idQuestionario, CONTRATOQUESTIONARIOS.IDCONTRATO AS idContrato, "+
    "       CONTRATOQUESTIONARIOS.DATAQUESTIONARIO AS dataQuestionario, CONTRATOQUESTIONARIOS.IDPROFISSIONAL as idProfissional, CONTRATOQUESTIONARIOS.IDEMPRESA as idEmpresa, "+
    "       CONTRATOQUESTIONARIOS.ABA AS aba, CONTRATOQUESTIONARIOS.SITUACAO as situacao, Profissionais.ConhecidoComo AS profissional, QUESTIONARIO.NOMEQUESTIONARIO AS nomeQuestionario"+
    "  FROM CONTRATOQUESTIONARIOS INNER JOIN "+
    "       Profissionais ON CONTRATOQUESTIONARIOS.IDPROFISSIONAL = Profissionais.IdProfissional INNER JOIN "+
    "       QUESTIONARIO ON CONTRATOQUESTIONARIOS.IDQUESTIONARIO = QUESTIONARIO.IDQUESTIONARIO "+
    " WHERE CONTRATOQUESTIONARIOS.IDCONTRATO = ? "+
    "   AND CONTRATOQUESTIONARIOS.ABA IN (#ABA#) "+
    "   AND CONTRATOQUESTIONARIOS.DATAQUESTIONARIO BETWEEN ? AND ? "+
    " ORDER BY IDCONTRATOQUESTIONARIO DESC, CONTRATOQUESTIONARIOS.DATAQUESTIONARIO DESC";

    private static final String SQL_LIST_BY_ID_CONTRATO_AND_QUEST = "SELECT CONTRATOQUESTIONARIOS.IDCONTRATOQUESTIONARIO AS idContratoQuestionario, CONTRATOQUESTIONARIOS.IDQUESTIONARIO AS idQuestionario, CONTRATOQUESTIONARIOS.IDCONTRATO AS idContrato, "+
    "       CONTRATOQUESTIONARIOS.DATAQUESTIONARIO AS dataQuestionario, CONTRATOQUESTIONARIOS.IDEMPRESA as idEmpresa, "+
    "       CONTRATOQUESTIONARIOS.ABA AS aba, CONTRATOQUESTIONARIOS.SITUACAO as situacao, QUESTIONARIO.NOMEQUESTIONARIO AS nomeQuestionario"+
    "  FROM CONTRATOQUESTIONARIOS INNER JOIN "+
    "       QUESTIONARIO ON CONTRATOQUESTIONARIOS.IDQUESTIONARIO = QUESTIONARIO.IDQUESTIONARIO "+
    " WHERE CONTRATOQUESTIONARIOS.idQuestionario = ? "+
    "   AND CONTRATOQUESTIONARIOS.idContrato = ? "+
    " ORDER BY CONTRATOQUESTIONARIOS.DATAQUESTIONARIO DESC, IDCONTRATOQUESTIONARIO DESC";

    private static final String SQL_LIST_BY_ID_REL_ANUAL_AND_ABA = "SELECT COUNT(*) "+
    "  FROM CONTRATOQUESTIONARIOS "+
    " WHERE CONTRATOQUESTIONARIOS.IDCONTRATO IN (SELECT IDCONTRATO FROM LOTACAO WHERE DATAFIM IS NULL #COMPLEMENTO#) "+
    "   AND CONTRATOQUESTIONARIOS.ABA = ? ";

    private static final String SQL_LIST_BY_ID_REL_ANUAL_AND_ABA_AND_PERIODO = "SELECT COUNT(DISTINCT IDCONTRATO) "+
    "  FROM CONTRATOQUESTIONARIOS "+
    " WHERE CONTRATOQUESTIONARIOS.IDCONTRATO IN (SELECT IDCONTRATO FROM LOTACAO WHERE DATAFIM IS NULL #COMPLEMENTO#) "+
    "   AND CONTRATOQUESTIONARIOS.ABA = ? AND CONTRATOQUESTIONARIOS.DATAQUESTIONARIO BETWEEN ? AND ? ";

	public ContratoQuestionariosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);

	}

	public Collection findByIdContratoAndAbaAndData(Integer idContrato, String aba, Date data) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idContrato", "=", idContrato));
		condicao.add(new Condition("aba", "=", aba));
		condicao.add(new Condition("dataQuestionario", "=", data));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection<Field> getFields() {
		List lista = new ArrayList();

		lista.add(new Field("idContratoQuestionario","idContratoQuestionario",true,false,false,false));
		lista.add(new Field("idQuestionario","idQuestionario",false,false,false,false));
		lista.add(new Field("idContrato","idContrato",false,false,false,false));
		lista.add(new Field("dataQuestionario","dataQuestionario",false,false,false,false));
		lista.add(new Field("idProfissional","idProfissional",false,false,false,false));
		lista.add(new Field("idEmpresa","idEmpresa",false,false,false,false));
		lista.add(new Field("aba","aba",false,false,false,false));
		lista.add(new Field("situacao","situacao",false,false,false,false));
		lista.add(new Field("situacaoComplemento","situacaoComplemento",false,false,false,false));
		lista.add(new Field("datahoragrav","datahoragrav",false,false,false,false));
		lista.add(new Field("migrado","migrado",false,false,false,false));
		lista.add(new Field("conteudoImpresso","conteudoImpresso",false,false,false,false));
        lista.add(new Field("idMigracao","idMigracao",false,false,false,false));

		return lista;
	}

	public String getTableName() {
		return "ContratoQuestionarios";
	}
	public static String getTableNameAssDigital() {
		return "ContratoQuestionarios";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ContratoQuestionariosDTO.class;
	}

	public Collection listByIdContratoAndAba(Integer idContrato, String aba) throws PersistenceException {
/*		List list = new ArrayList();
		list.add(new Order("dataQuestionario"));
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContrato(idContrato);
		obj.setAba(aba);
		return super.find(obj, list); */
        Object[] objs = new Object[] {idContrato};

        String sql = SQL_LIST_BY_ID_CONTRATO_AND_ABA;

        String abaX = "";
        boolean bPrimeiro = true;
        String[] abaAux = aba.split(",");
        if (abaAux != null && abaAux.length > 0){
        	for(int i = 0; i < abaAux.length; i++){
        		if (!bPrimeiro){
        			abaX += ",";
        		}
        		abaX += "'" + abaAux[i] + "'";
        		bPrimeiro = false;
        	}
        }else{
        	abaX = "'" + aba + "'";
        }

        abaX = abaX.toUpperCase();
        sql = sql.replaceAll("\\#ABA\\#", abaX);

        List lista = this.execSQL(sql, objs);

        List listRetorno = new ArrayList();
        listRetorno.add("idContratoQuestionario");
        listRetorno.add("idQuestionario");
        listRetorno.add("idContrato");
        listRetorno.add("dataQuestionario");
        listRetorno.add("idProfissional");
        listRetorno.add("idEmpresa");
        listRetorno.add("aba");
        listRetorno.add("situacao");
        listRetorno.add("nomeQuestionario");
        listRetorno.add("situacaoComplemento");
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        return result;
	}
	public Collection listByIdContratoAndAbaOrdemCrescente(Integer idContrato, String aba) throws PersistenceException {
		/*		List list = new ArrayList();
		list.add(new Order("dataQuestionario"));
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContrato(idContrato);
		obj.setAba(aba);
		return super.find(obj, list); */
		Object[] objs = new Object[] {idContrato};

		String sql = SQL_LIST_BY_ID_CONTRATO_AND_ABA_CRESCENTE;

		String abaX = "";
		boolean bPrimeiro = true;
		String[] abaAux = aba.split(",");
		if (abaAux != null && abaAux.length > 0){
			for(int i = 0; i < abaAux.length; i++){
				if (!bPrimeiro){
					abaX += ",";
				}
				abaX += "'" + abaAux[i] + "'";
				bPrimeiro = false;
			}
		}else{
			abaX = "'" + aba + "'";
		}

		abaX = abaX.toUpperCase();
		sql = sql.replaceAll("\\#ABA\\#", abaX);

		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idContratoQuestionario");
		listRetorno.add("idQuestionario");
		listRetorno.add("idContrato");
		listRetorno.add("dataQuestionario");
		listRetorno.add("idEmpresa");
		listRetorno.add("aba");
		listRetorno.add("situacao");
		listRetorno.add("profissional");
		listRetorno.add("nomeQuestionario");
		listRetorno.add("situacaoComplemento");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}
	public ContratoQuestionariosDTO getUltimoByIdContratoAndAba(Integer idContrato, String aba) throws PersistenceException {
		Object[] objs = new Object[] {idContrato};

		String sql = SQL_ULTIMO_BY_ID_CONTRATO_AND_ABA;

		String abaX = "";
		boolean bPrimeiro = true;
		String[] abaAux = aba.split(",");
		if (abaAux != null && abaAux.length > 0){
			for(int i = 0; i < abaAux.length; i++){
				abaX += "'" + abaAux[i] + "'";
				if (!bPrimeiro){
					abaX += ",";
				}
				bPrimeiro = false;
			}
		}else{
			abaX = "'" + aba + "'";
		}

		sql = sql.replaceAll("\\#ABA\\#", abaX);

		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idContratoQuestionario");
		listRetorno.add("idQuestionario");
		listRetorno.add("idContrato");
		listRetorno.add("dataQuestionario");
		listRetorno.add("idEmpresa");
		listRetorno.add("aba");
		listRetorno.add("situacao");
		listRetorno.add("profissional");
		listRetorno.add("nomeQuestionario");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0){
			return null;
		}
		return (ContratoQuestionariosDTO)result.get(0);
	}
	public ContratoQuestionariosDTO getUltimoByIdContratoAndAbaAndPeriodo(Integer idContrato, String aba, Date dataInicio, Date dataFim) throws PersistenceException {
		Object[] objs = new Object[] {idContrato, dataInicio, dataFim};

		String sql = SQL_ULTIMO_BY_ID_CONTRATO_AND_ABA_PERIODO;

		String abaX = "";
		boolean bPrimeiro = true;
		String[] abaAux = aba.split(",");
		if (abaAux != null && abaAux.length > 0){
			for(int i = 0; i < abaAux.length; i++){
				abaX += "'" + abaAux[i] + "'";
				if (!bPrimeiro){
					abaX += ",";
				}
				bPrimeiro = false;
			}
		}else{
			abaX = "'" + aba + "'";
		}

		sql = sql.replaceAll("\\#ABA\\#", abaX);

		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idContratoQuestionario");
		listRetorno.add("idQuestionario");
		listRetorno.add("idContrato");
		listRetorno.add("dataQuestionario");
		listRetorno.add("idEmpresa");
		listRetorno.add("aba");
		listRetorno.add("situacao");
		listRetorno.add("profissional");
		listRetorno.add("nomeQuestionario");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0){
			return null;
		}
		return (ContratoQuestionariosDTO)result.get(0);
	}

	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAba(Integer idDepartamento, Integer idEstabelecimento, Integer idCargo, String aba) throws PersistenceException {
		String sql = SQL_LIST_BY_ID_REL_ANUAL_AND_ABA;
		String complemento = "";
		List lstParms = new ArrayList();
		if (idEstabelecimento != null){
			complemento += " AND idEstabelecimento = ?";
			lstParms.add(idEstabelecimento);
		}
		if (idDepartamento != null){
			complemento += " AND idDepartamento = ?";
			lstParms.add(idDepartamento);
		}
		if (idCargo != null){
			complemento += " AND idCargo = ?";
			lstParms.add(idCargo);
		}
		sql = sql.replaceAll("#COMPLEMENTO#", complemento);

		lstParms.add(aba);
		Object[] objs = lstParms.toArray();
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("qtde");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0){
			return null;
		}
		return (ContratoQuestionariosDTO)result.get(0);
	}
	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodo(Integer idDepartamento, Integer idEstabelecimento, Integer idCargo,
			String aba, Date dataInicio, Date dataFim) throws PersistenceException {
		String sql = SQL_LIST_BY_ID_REL_ANUAL_AND_ABA_AND_PERIODO;
		String complemento = "";
		List lstParms = new ArrayList();
		if (idEstabelecimento != null){
			complemento += " AND idEstabelecimento = ?";
			lstParms.add(idEstabelecimento);
		}
		if (idDepartamento != null){
			complemento += " AND idDepartamento = ?";
			lstParms.add(idDepartamento);
		}
		if (idCargo != null){
			complemento += " AND idCargo = ?";
			lstParms.add(idCargo);
		}
		sql = sql.replaceAll("#COMPLEMENTO#", complemento);

		lstParms.add(aba);
		lstParms.add(dataInicio);
		lstParms.add(dataFim);
		Object[] objs = lstParms.toArray();
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("qtde");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0){
			return null;
		}
		return (ContratoQuestionariosDTO)result.get(0);
	}
	public ContratoQuestionariosDTO getQuantidadeByIdDepEstabAndAbaAndPeriodoFinalizados(Integer idDepartamento, Integer idEstabelecimento, Integer idCargo,
			String aba, Date dataInicio, Date dataFim) throws PersistenceException {
		String sql = SQL_LIST_BY_ID_REL_ANUAL_AND_ABA_AND_PERIODO;
		String complemento = "";
		List lstParms = new ArrayList();
		if (idEstabelecimento != null){
			complemento += " AND idEstabelecimento = ?";
			lstParms.add(idEstabelecimento);
		}
		if (idDepartamento != null){
			complemento += " AND idDepartamento = ?";
			lstParms.add(idDepartamento);
		}
		if (idCargo != null){
			complemento += " AND idCargo = ?";
			lstParms.add(idCargo);
		}
		complemento += " AND CONTRATOQUESTIONARIOS.situacao = 'F' ";

		sql = sql.replaceAll("#COMPLEMENTO#", complemento);

		lstParms.add(aba);
		lstParms.add(dataInicio);
		lstParms.add(dataFim);
		Object[] objs = lstParms.toArray();
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("qtde");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0){
			return null;
		}
		return (ContratoQuestionariosDTO)result.get(0);
	}

    public ContratoQuestionariosDTO getQuantidadeByIdDepEstabFuncaoAndAbaAndPeriodo(Integer idDepartamento, Integer idEstabelecimento, Integer idCargo, Integer idFuncao,
            String aba, Date dataInicio, Date dataFim) throws PersistenceException {
        String sql = SQL_LIST_BY_ID_REL_ANUAL_AND_ABA_AND_PERIODO;
        String complemento = "";
        List lstParms = new ArrayList();
        if (idEstabelecimento != null){
            complemento += " AND idEstabelecimento = ?";
            lstParms.add(idEstabelecimento);
        }
        if (idDepartamento != null){
            complemento += " AND idDepartamento = ?";
            lstParms.add(idDepartamento);
        }
        if (idCargo != null){
            complemento += " AND idCargo = ?";
            lstParms.add(idCargo);
        }
        if (idFuncao != null){
            complemento += " AND idFuncao = ?";
            lstParms.add(idFuncao);
        }
        sql = sql.replaceAll("#COMPLEMENTO#", complemento);

        lstParms.add(aba);
        lstParms.add(dataInicio);
        lstParms.add(dataFim);
        Object[] objs = lstParms.toArray();
        List lista = this.execSQL(sql, objs);

        List listRetorno = new ArrayList();
        listRetorno.add("qtde");
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        if (result == null || result.size() == 0){
            return null;
        }
        return (ContratoQuestionariosDTO)result.get(0);
    }
	public Collection listByIdContratoAndQuestionario(Integer idQuestionario, Integer idContrato) throws PersistenceException {
		Object[] objs = new Object[] {idQuestionario, idContrato};
		List lista = this.execSQL(SQL_LIST_BY_ID_CONTRATO_AND_QUEST, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idContratoQuestionario");
		listRetorno.add("idQuestionario");
		listRetorno.add("idContrato");
		listRetorno.add("dataQuestionario");
		listRetorno.add("idEmpresa");
		listRetorno.add("aba");
		listRetorno.add("situacao");
		listRetorno.add("nomeQuestionario");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection listByIdContrato(Integer idContrato) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("dataQuestionario"));
		list.add(new Order("aba"));
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContrato(idContrato);
		return super.find(obj, list);
	}
	public Collection listByIdContratoOrderDecrescente(Integer idContrato) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("dataQuestionario", Order.DESC));
		list.add(new Order("aba"));
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContrato(idContrato);
		return super.find(obj, list);
	}
	public Collection listByIdContratoOrderIdDecrescente(Integer idContrato) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idContratoQuestionario", Order.DESC));
		list.add(new Order("dataQuestionario", Order.DESC));
		list.add(new Order("aba"));

		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContrato(idContrato);
		return super.find(obj, list);
	}

	public void update(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	public void updateSituacaoComplemento(Integer idPessQuest, String situacaoComplemento) throws PersistenceException {
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContratoQuestionario(idPessQuest);
		obj.setSituacaoComplemento(situacaoComplemento);
		super.updateNotNull(obj);
	}
	public void updateConteudoImpresso(Integer idPessQuest, String conteudoImpresso) throws PersistenceException {
		ContratoQuestionariosDTO obj = new ContratoQuestionariosDTO();
		obj.setIdContratoQuestionario(idPessQuest);
		obj.setConteudoImpresso(conteudoImpresso);
		super.updateNotNull(obj);
	}

}
