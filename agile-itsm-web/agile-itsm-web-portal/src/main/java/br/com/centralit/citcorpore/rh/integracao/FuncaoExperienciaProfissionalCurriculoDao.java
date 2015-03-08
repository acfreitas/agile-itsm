package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FuncaoExperienciaProfissionalCurriculoDao extends CrudDaoDefaultImpl {

    public FuncaoExperienciaProfissionalCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idfuncao", "idFuncao", true, true, false, false));
        listFields.add(new Field("idexperienciaprofissionalcurriculo", "idExperienciaProfissional", false, false, false, false));
        listFields.add(new Field("nomefuncao", "nomeFuncao", false, false, false, false));
        listFields.add(new Field("descricaofuncao", "descricaoFuncao", false, false, false, false));
        listFields.add(new Field("iniciofuncao", "inicioFuncao", false, false, false, false));
        listFields.add(new Field("fimfuncao", "fimFuncao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_FuncaoExperienciaProfissionalCurriculo";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return FuncaoExperienciaProfissionalCurriculoDTO.class;
    }

    public Collection findByIdExperienciaProfissional(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idExperienciaProfissional", "=", parm));
        ordenacao.add(new Order("inicioFuncao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdExperienciaProfissional(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idExperienciaProfissional", "=", parm));
        super.deleteByCondition(condicao);
    }

}
