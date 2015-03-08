package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FuncionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FuncionarioDao extends CrudDaoDefaultImpl {

    public FuncionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {

        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idfuncionario", "idFuncionario", true, true, false, false));
        listFields.add(new Field("idempregado", "idEmpregado", false, false, false, false));
        listFields.add(new Field("nome", "nome", false, false, false, false));
        listFields.add(new Field("cpf", "cpf", false, false, false, false));
        listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
        listFields.add(new Field("datafim", "dataFim", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_funcionario";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("nome"));
        return super.list(list);
    }

    /**
     * Realiza consulta por nome atraves do AutoComplete
     *
     * @param nome
     * @return
     * @throws Exception
     * @author david.silva
     */
    public Collection findByNome(String nome) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }

        String texto = Normalizer.normalize(nome, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {texto};

        final StringBuilder sql = new StringBuilder();

        sql.append("select distinct f.idfuncionario, f.nome, f.cpf, f.datafim, f.idempregado ");
        sql.append(" from rh_funcionario f ");
        sql.append(" INNER JOIN empregados em ");
        sql.append(" ON em.tipo <> 'N' ");
        sql.append(" where upper(f.nome) like upper(?) ");
        sql.append(" and f.datafim is null ");
        sql.append(" order by f.nome limit 0,10 ");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idFuncionario");
        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("datafim");
        listRetorno.add("idEmpregado");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public FuncionarioDTO restoreByIdEmpregado(final Integer idEmpregado) throws PersistenceException {
        final List ordem = new ArrayList();
        final FuncionarioDTO funcionarioDto = new FuncionarioDTO();
        funcionarioDto.setIdEmpregado(idEmpregado);
        final List col = (List) super.find(funcionarioDto, ordem);
        if (col == null || col.size() == 0) {
            return null;
        }
        return (FuncionarioDTO) col.get(0);
    }

    @Override
    public Class getBean() {
        return FuncionarioDTO.class;
    }

}
