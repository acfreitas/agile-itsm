package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.config.Config;
import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.SQLConfig;

public class ElementoFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_elementofluxo";

    public ElementoFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idElemento", "idElemento", true, true, false, false));
        listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
        listFields.add(new Field("tipoElemento", "tipoElemento", false, false, false, false));
        listFields.add(new Field("subTipo", "subTipo", false, false, false, false));
        listFields.add(new Field("nome", "nome", false, false, false, false));
        listFields.add(new Field("documentacao", "documentacao", false, false, false, false));
        listFields.add(new Field("acaoEntrada", "acaoEntrada", false, false, false, false));
        listFields.add(new Field("acaoSaida", "acaoSaida", false, false, false, false));
        listFields.add(new Field("tipoInteracao", "tipoInteracao", false, false, false, false));
        listFields.add(new Field("visao", "visao", false, false, false, false));
        listFields.add(new Field("url", "url", false, false, false, false));
        listFields.add(new Field("grupos", "grupos", false, false, false, false));
        listFields.add(new Field("usuarios", "usuarios", false, false, false, false));
        listFields.add(new Field("script", "script", false, false, false, false));
        listFields.add(new Field("textoEmail", "textoEmail", false, false, false, false));
        listFields.add(new Field("nomeFluxoEncadeado", "nomeFluxoEncadeado", false, false, false, false));
        listFields.add(new Field("modeloEmail", "modeloEmail", false, false, false, false));
        listFields.add(new Field("intervalo", "intervalo", false, false, false, false));
        listFields.add(new Field("condicaoDisparo", "condicaoDisparo", false, false, false, false));
        listFields.add(new Field("multiplasInstancias", "multiplasInstancias", false, false, false, false));
        listFields.add(new Field("contabilizaSLA", "contabilizaSLA", false, false, false, false));
        listFields.add(new Field("percExecucao", "percExecucao", false, false, false, false));
        listFields.add(new Field("destinatariosEmail", "destinatariosEmail", false, false, false, false));
        listFields.add(new Field("posX", "posX", false, false, false, false));
        listFields.add(new Field("posY", "posY", false, false, false, false));
        listFields.add(new Field("largura", "largura", false, false, false, false));
        listFields.add(new Field("altura", "altura", false, false, false, false));
        listFields.add(new Field("template", "template", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<ElementoFluxoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<?> getBean() {
        return ElementoFluxoDTO.class;
    }

    @Override
    public Collection<ElementoFluxoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public List<ElementoFluxoDTO> findAllByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        ordenacao.add(new Order("idElemento"));
        return (List<ElementoFluxoDTO>) super.findByCondition(condicao, ordenacao);
    }

    public List<ElementoFluxoDTO> findByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        condicao.add(new Condition("tipoElemento", "=", this.getTipoElemento().name()));
        ordenacao.add(new Order("idElemento"));
        return (List<ElementoFluxoDTO>) super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        condicao.add(new Condition("tipoElemento", "=", this.getTipoElemento().name()));
        super.deleteByCondition(condicao);
    }

    public void deleteAllByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        super.deleteByCondition(condicao);
    }

    @Override
    public IDto create(final IDto obj) throws PersistenceException {
        ((ElementoFluxoDTO) obj).setTipoElemento(this.getTipoElemento().name());
        return super.create(obj);
    }

    @Override
    public IDto restore(IDto obj) throws PersistenceException {
        obj = super.restore(obj);
        if (obj == null) {
            return null;
        }
        final String nomeClasse = Config.getClasseDtoElemento(((ElementoFluxoDTO) obj).getTipoElemento());
        ElementoFluxoDTO elementoDto = null;
        try {
            elementoDto = (ElementoFluxoDTO) Class.forName(nomeClasse).newInstance();
            Reflexao.copyPropertyValues(obj, elementoDto);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return elementoDto;
    }

    public ElementoFluxoDTO restore(final Integer idElementoFluxo) throws PersistenceException {
        final ElementoFluxoDTO elementoDto = new ElementoFluxoDTO();
        elementoDto.setIdElemento(idElementoFluxo);
        return (ElementoFluxoDTO) this.restore(elementoDto);
    }

    protected TipoElementoFluxo getTipoElemento() {
        return null;
    }

    public List<ElementoFluxoDTO> listaElementoFluxo(final String documentacao) throws PersistenceException {
        final List<String> parametro = new ArrayList<>();
        final List<String> listRetorno = new ArrayList<>();
        List<?> lista = new ArrayList<>();

        String sql = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql = "select documentacao from bpm_elementofluxo where trim(documentacao) ";
        } else {
            sql = "select distinct(documentacao) from bpm_elementofluxo where trim(documentacao) ";
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += " ilike '%" + documentacao + "%'";
        } else {
            sql += " like '%" + documentacao + "%'";
        }

        sql += " ORDER BY documentacao ";

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            String sqlOracleSqlServer = "";
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sqlOracleSqlServer = "select distinct cast(documentacao as varchar2(4000)) documentacao  from bpm_elementofluxo where UPPER(documentacao) ";
            }
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                sqlOracleSqlServer = "select distinct cast(documentacao as varchar(4000)) documentacao  from bpm_elementofluxo where documentacao ";
            }
            sqlOracleSqlServer += " like UPPER('%" + documentacao + "%')";
            sqlOracleSqlServer += " and documentacao is not null ";
            sqlOracleSqlServer += " order by documentacao ";

            listRetorno.add("documentacao");

            lista = this.execSQL(sqlOracleSqlServer.toString(), parametro.toArray());

            return engine.listConvertion(this.getBean(), lista, listRetorno);
        }

        if (!CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sql, parametro.toArray());
        }

        listRetorno.add("documentacao");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

}
