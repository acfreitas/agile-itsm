package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LogImportacaoBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.InvalidTransactionControler;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LogImportacaoBIDao extends CrudDaoDefaultImpl {

	public LogImportacaoBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public LogImportacaoBIDao(TransactionControler tc, Usuario usuario) throws InvalidTransactionControler {
		super(tc, usuario);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLogImportacao", "idLogImportacao", true, true, false, false));
		listFields.add(new Field("dataHoraInicio", "dataHoraInicio", false, false, false, false));
		listFields.add(new Field("dataHoraFim", "dataHoraFim", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "LOGIMPORTACAOBI";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idlogimportacao"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return LogImportacaoBIDTO.class;
	}

	public Collection<LogImportacaoBIDTO> listarLogsByConexaoBI(Integer idConexaoBI) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idConexaoBI", "=", idConexaoBI));
		ordenacao.add(new Order("dataHoraInicio", Order.DESC));

		return super.findByCondition(condicao, ordenacao);
	}

	public Integer calculaTotalPaginas(Integer idConexaoBI, Integer itensPorPagina) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

        sql.append(" select COUNT(*) from " + getTableName() + " where idconexaobi = ? ");

        parametro.add(idConexaoBI);

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        if(lista != null){
        	Object[] totalLinha = (Object[]) lista.get(0);
        	if(totalLinha != null && totalLinha.length > 0){
    			totalLinhaInteger = (Integer) totalLinha[0];
    			totalLinhaLong = Long.valueOf(totalLinhaInteger);
        	}
        }

        if (totalLinhaLong > 0) {
        	totalPagina = (totalLinhaLong / intLimite);
        	if(totalLinhaLong % intLimite != 0){
        		totalPagina = totalPagina + 1;
        	}
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;

	}

	public Collection<LogImportacaoBIDTO> paginacaoLog(Integer idConexaoBI, Integer pgAtual, Integer qtdPaginacao) throws PersistenceException {

		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append(" ;WITH TabelaTemporaria AS ( ");
		sql.append(" SELECT * ");
        sql.append(" , ROW_NUMBER() OVER (ORDER BY idlogimportacao DESC) AS Row ");
        sql.append(" FROM " + getTableName() + " ");
		sql.append(" WHERE idconexaobi = ? ");

		parametro.add(idConexaoBI);

    	Integer quantidadePaginator2 = new Integer(0);
    	if (pgAtual > 0) {
    		quantidadePaginator2 = qtdPaginacao * pgAtual;
    		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
    	}else{
    		quantidadePaginator2 = qtdPaginacao;
    		pgAtual = 0;
    	}
    	sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row > " + pgAtual + " and Row < " + (quantidadePaginator2 + 1) + " ");

        List lista = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idLogImportacao");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("status");
        listRetorno.add("detalhamento");
        listRetorno.add("tipo");
        listRetorno.add("idConexaoBI");

        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return (result == null ? new ArrayList<LogImportacaoBIDTO>() : result);
	}

}
