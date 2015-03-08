package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class HistoricoItemConfiguracaoDAO extends CrudDaoDefaultImpl {

	public HistoricoItemConfiguracaoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idHistoricoIC", "idHistoricoIC", true, true, false, false));
		listFields.add(new Field("idItemConfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("identificacao", "identificacao", false, false, false, false));
		listFields.add(new Field("idItemConfiguracaoPai", "idItemConfiguracaoPai", false, false, false, false));
		listFields.add(new Field("idTipoItemConfiguracao", "idTipoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("idGrupoItemConfiguracao", "idGrupoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("idProprietario", "idProprietario", false, false, false, false));
		listFields.add(new Field("versao", "versao", false, false, false, false));
		listFields.add(new Field("familia", "familia", false, false, false, false));
		listFields.add(new Field("classe", "classe", false, false, false, false));
		listFields.add(new Field("localidade", "localidade", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		listFields.add(new Field("criticidade", "criticidade", false, false, false, false));
		listFields.add(new Field("numeroSerie", "numeroSerie", false, false, false, false));
		listFields.add(new Field("dataExpiracao", "dataExpiracao", false, false, false, false));
		listFields.add(new Field("idMudanca", "idMudanca", false, false, false, false));
		listFields.add(new Field("idProblema", "idProblema", false, false, false, false));
		listFields.add(new Field("idIncidente", "idIncidente", false, false, false, false));
		listFields.add(new Field("idAutorAlteracao", "idAutorAlteracao", false, false, false, false));
		listFields.add(new Field("dataHoraAlteracao", "dataHoraAlteracao", false, false, false, false));
		listFields.add(new Field("baseLine", "baseLine", false, false, false, false));
		listFields.add(new Field("restauracao", "restauracao", false, false, false, false));
		listFields.add(new Field("idMidiaSoftware", "idMidiaSoftware", false, false, false, false));
		listFields.add(new Field("historicoVersao", "historicoVersao", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		listFields.add(new Field("dtultimacaptura", "dtUltimaCaptura", false, false, false, false));
		listFields.add(new Field("origemmodificacao", "origem", false, false, false, false));
		listFields.add(new Field("idmodificacao", "idOrigemModificacao", false, false, false, false));
		listFields.add(new Field("idcontrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idliberacao", "idLiberacao", false, false, false, false));
		listFields.add(new Field("idresponsavel", "idResponsavel", false, false, false, false));
		listFields.add(new Field("ativofixo", "ativoFixo", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "HistoricoIC";


	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return HistoricoItemConfiguracaoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}


	@Override
	public Collection findByCondition(List condicao, List ordenacao) throws PersistenceException {
		return super.findByCondition(condicao, ordenacao);
	}

	public HistoricoItemConfiguracaoDTO maxIdHistorico(ItemConfiguracaoDTO itemConfiguracao) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT MAX(idhistoricoic) AS idhistoricoic FROM " + getTableName() + " WHERE iditemconfiguracao = ?");
		parametro.add(itemConfiguracao.getIdItemConfiguracao());
		List resultado = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idHistoricoIC");
		List result = listConvertion(HistoricoItemConfiguracaoDTO.class , resultado, listRetorno);
		return (HistoricoItemConfiguracaoDTO) result.get(0);
	}

	public List<HistoricoItemConfiguracaoDTO> listHistoricoItemByIditemconfiguracao(Integer idItemconfiguracao) throws PersistenceException {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		String sql = "select hic.idHistoricoIC, hic.idItemConfiguracao, "+
						" hic.identificacao, hic.idItemConfiguracaoPai, hic.idTipoItemConfiguracao, hic.idGrupoItemConfiguracao, 	hic.idProprietario, hic.versao, hic.familia, hic.classe, hic.localidade,  "+
						" hic.status, hic.criticidade, hic.numeroSerie, hic.dataExpiracao, hic.idMudanca, hic.idProblema, hic.idIncidente, hic.idAutorAlteracao, 	hic.dataHoraAlteracao, 	hic.baseLine, hic.restauracao , "+
						" emp.nome , tipo.nometipoitemconfiguracao , hic.historicoVersao , hic.origemmodificacao, hic.idmodificacao "+
						" from historicoic hic  " +
						" left join tipoitemconfiguracao tipo on hic.idtipoitemconfiguracao = tipo.idtipoitemconfiguracao  " +
						" left join empregados emp on emp.idempregado = hic.idproprietario where hic.iditemconfiguracao = ? order by hic.idHistoricoIC desc";

		parametro.add(idItemconfiguracao);

		List resultado  = execSQL(sql, parametro.toArray());

		listRetorno = getListRetorno();
		listRetorno.add("nomeProprietario");
		listRetorno.add("nomeTipoItemConfiguracao");
		listRetorno.add("historicoVersao");
		listRetorno.add("origem");
		listRetorno.add("idOrigemModificacao");
		return listConvertion(HistoricoItemConfiguracaoDTO.class , resultado, listRetorno);
	}

	public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(Integer idHistoricoIC) throws PersistenceException {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		String sql = "	select hic.idHistoricoIC, hic.idItemConfiguracao,   "+
							" hic.identificacao, hic.idItemConfiguracaoPai, hic.idTipoItemConfiguracao, hic.idGrupoItemConfiguracao, 	hic.idProprietario, hic.versao, hic.familia, hic.classe, hic.localidade,   "+
							" hic.status, hic.criticidade, hic.numeroSerie, hic.dataExpiracao, hic.idMudanca, hic.idProblema, hic.idIncidente, hic.idAutorAlteracao, 	hic.dataHoraAlteracao, 	hic.baseLine, hic.restauracao,  "+
							" emp.nome , tipo.nometipoitemconfiguracao, car.nomecaracteristica, hicvalor.valorstr  "+
						"  from historicoic hic   "+
						" inner join historicovalor hisval on hic.idhistoricoic = hisval.idhistoricoic  "+
						" inner join tipoitemconfiguracao tipo on tipo.idtipoitemconfiguracao = hic.idtipoitemconfiguracao  "+
						" inner join caracteristica car on car.idcaracteristica = hisval.idcaracteristica  "+
						" inner join historicovalor hicvalor on hicvalor.idhistoricoic = hic.idhistoricoic  "+
						" inner join empregados emp on emp.idempregado = hic.idproprietario  "+
						" where hic.idhistoricoic = ?  ";

		parametro.add(idHistoricoIC);
		List resultado  = execSQL(sql, parametro.toArray());
		listRetorno = getListRetorno();
		listRetorno.add("nomeProprietario");
		listRetorno.add("nomeTipoItemConfiguracao");
		listRetorno.add("nomeCaracteristica");
		listRetorno.add("valorstr");

		return listConvertion(HistoricoItemConfiguracaoDTO.class , resultado, listRetorno);
	}

	public List getListRetorno(){
		List retorno = new ArrayList();
		retorno.add("idHistoricoIC");
		retorno.add("idItemConfiguracao");
		retorno.add("identificacao" );
		retorno.add("idItemConfiguracaoPai");
		retorno.add("idTipoItemConfiguracao" );
		retorno.add("idGrupoItemConfiguracao");
		retorno.add("idProprietario");
		retorno.add("versao");
		retorno.add("familia");
		retorno.add("classe");
		retorno.add("localidade");
		retorno.add("status");
		retorno.add("criticidade");
		retorno.add("numeroSerie");
		retorno.add("dataExpiracao");
		retorno.add("idMudanca");
		retorno.add("idProblema");
		retorno.add("idIncidente");
		retorno.add("idAutorAlteracao");
		retorno.add("dataHoraAlteracao");
		retorno.add("baseLine");
		retorno.add("restauracao");
		return retorno;
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
				super.updateNotNull(obj);
	}

    public List<HistoricoItemConfiguracaoDTO> listHistoricoSemIdMudanca(Date dataInicio, Date dataFim) throws PersistenceException {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        String sql = "select hic.idHistoricoIC, hic.idItemConfiguracao, "+
                        " hic.identificacao, hic.idItemConfiguracaoPai, hic.idTipoItemConfiguracao, hic.idGrupoItemConfiguracao,    hic.idProprietario, hic.versao, hic.familia, hic.classe, hic.localidade,  "+
                        " hic.status, hic.criticidade, hic.numeroSerie, hic.dataExpiracao, hic.idMudanca, hic.idProblema, hic.idIncidente, hic.idAutorAlteracao,    hic.dataHoraAlteracao,  hic.baseLine, hic.restauracao , "+
                        " emp.nome , tipo.nometipoitemconfiguracao , hic.historicoVersao "+
                        " from historicoic hic  " +
                        " inner join tipoitemconfiguracao tipo on hic.idtipoitemconfiguracao = tipo.idtipoitemconfiguracao  " +
                        " inner join empregados emp on emp.idempregado = hic.idproprietario " +
                        "where idMudanca is null ";

        sql += "AND dataHoraAlteracao BETWEEN ? AND ? ";

        parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataInicio, "yyyy-MM-dd") + " 00:00:00"));
        parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataFim, "yyyy-MM-dd") + " 23:59:59"));

        sql += "order by hic.dataHoraAlteracao desc, hic.idHistoricoIC ";

        List resultado  = execSQL(sql, parametro.toArray());

        listRetorno = getListRetorno();
        listRetorno.add("nomeProprietario");
        listRetorno.add("nomeTipoItemConfiguracao");
        listRetorno.add("historicoVersao");
        return listConvertion(HistoricoItemConfiguracaoDTO.class , resultado, listRetorno);
    }

}