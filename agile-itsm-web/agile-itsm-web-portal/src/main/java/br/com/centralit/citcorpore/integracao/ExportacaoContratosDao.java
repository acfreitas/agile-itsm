package br.com.centralit.citcorpore.integracao;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExportacaoContratosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ExportacaoContratosDao extends CrudDaoDefaultImpl {

    public ExportacaoContratosDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class<ExportacaoContratosDTO> getBean() {
        return ExportacaoContratosDTO.class;
    }

    @Override
    public Collection<ExportacaoContratosDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Collection<ExportacaoContratosDTO> list() throws PersistenceException {
        return null;
    }

}
