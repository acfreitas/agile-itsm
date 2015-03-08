package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.IntegracaoSistemasExternosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unchecked"})
public class IntegracaoSistemasExternosDao extends CrudDaoDefaultImpl {

    public IntegracaoSistemasExternosDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idIntegracao", "idIntegracao", true, true, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        listFields.add(new Field("processo", "processo", false, false, false, false));
        listFields.add(new Field("identificador", "identificador", false, false, false, false));
        listFields.add(new Field("idObjeto", "idObjeto", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "integracaosistemasexternos";
    }

    @Override
    public Collection<IntegracaoSistemasExternosDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<IntegracaoSistemasExternosDTO> getBean() {
        return IntegracaoSistemasExternosDTO.class;
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public IntegracaoSistemasExternosDTO findByIdProcessoAndIdentificador(final String processo, final String identificador) throws Exception {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("processo", "=", processo));
        condicao.add(new Condition("identificador", "=", identificador));
        ordenacao.add(new Order("idIntegracao"));
        final List<IntegracaoSistemasExternosDTO> result = (List<IntegracaoSistemasExternosDTO>) super.findByCondition(condicao, ordenacao);
        if (result != null) {
            return result.get(0);
        }
        return null;
    }

    public IntegracaoSistemasExternosDTO findByIdProcessoAndIdObjeto(final String processo, final String idObjeto, final String situacao) throws Exception {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("processo", "=", processo));
        condicao.add(new Condition("idObjeto", "=", idObjeto));
        if (situacao != null) {
            condicao.add(new Condition("situacao", "=", situacao));
        }
        ordenacao.add(new Order("idIntegracao"));
        final List<IntegracaoSistemasExternosDTO> result = (List<IntegracaoSistemasExternosDTO>) super.findByCondition(condicao, ordenacao);
        if (result != null) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

}
