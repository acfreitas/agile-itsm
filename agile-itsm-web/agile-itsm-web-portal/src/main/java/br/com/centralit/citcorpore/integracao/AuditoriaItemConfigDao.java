package br.com.centralit.citcorpore.integracao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AuditoriaItemConfigDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AuditoriaItemConfigDao extends CrudDaoDefaultImpl {

	public AuditoriaItemConfigDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idAuditoriaItemConfig", "idAuditoriaItemConfig", true, true, false, false));
		listFields.add(new Field("idItemConfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("idItemConfiguracaoPai", "idItemConfiguracaoPai", false, false, false, false));
		listFields.add(new Field("idValor", "idValor", false, false, false, false));
		listFields.add(new Field("idHistoricoIC", "idHistoricoIC", false, false, false, false));
		listFields.add(new Field("idHistoricoValor", "idHistoricoValor", false, false, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("dataHoraAlteracao", "dataHoraAlteracao", false, false, false, false));
		listFields.add(new Field("tipoAlteracao", "tipoAlteracao", false, false, false, false));



		return listFields;
	}

	@Override
	public String getTableName() {
		return "AuditoriaItemConfig";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("dataHoraAlteracao"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return AuditoriaItemConfigDTO.class;
	}

	public Collection findByIdItemconfiguracaoPai(AuditoriaItemConfigDTO auditoriaItemConfigDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idItemConfiguracaoPai", "=", auditoriaItemConfigDTO.getIdItemConfiguracaoPai()));
		ordenacao.add(new Order("dataHoraAlteracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdItemconfiguracao(AuditoriaItemConfigDTO auditoriaItemConfigDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idItemConfiguracao", "=", auditoriaItemConfigDTO.getIdItemConfiguracao()));
		ordenacao.add(new Order("dataHoraAlteracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdHistoricoIC(AuditoriaItemConfigDTO auditoriaItemConfigDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idHistoricoIC", "=", auditoriaItemConfigDTO.getIdHistoricoIC()));
		ordenacao.add(new Order("dataHoraAlteracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdHistoricoValor(AuditoriaItemConfigDTO auditoriaItemConfigDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idHistoricoValor", "=", auditoriaItemConfigDTO.getIdHistoricoValor()));
		ordenacao.add(new Order("dataHoraAlteracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public List<AuditoriaItemConfigDTO> historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception{
		List parametro = new ArrayList();
		List fields = new ArrayList();
		StringBuilder str = new StringBuilder();

		str.append(" select it.identificacao, tp.nometipoitemconfiguracao ,au.datahoraalteracao,  us.login , au.tipoalteracao FROM itemconfiguracao it  "
				+ "  inner join auditoriaitemconfig au  on it.iditemconfiguracao = au.iditemconfiguracao "
				+ "	 inner join tipoitemconfiguracao tp on tp.idtipoitemconfiguracao = it.idtipoitemconfiguracao "
				+ "  left join usuario us on us.idusuario = au.idusuario   "
				+ " where it.iditemconfiguracaopai = ? ");


		if(itemConfiguracaoDTO.getIdItemConfiguracao() != null && itemConfiguracaoDTO.getIdItemConfiguracaoPai() != null){
			str.append(" and it.iditemconfiguracao = ? ");
			parametro.add(itemConfiguracaoDTO.getIdItemConfiguracaoPai());
			parametro.add(itemConfiguracaoDTO.getIdItemConfiguracao());
		}else{
			parametro.add(itemConfiguracaoDTO.getIdItemConfiguracao());
		}

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			str.append("  and au.datahoraalteracao BETWEEN ? and ? ");
			parametro.add(formatter.format(itemConfiguracaoDTO.getDataInicioHistorico()));
			parametro.add(formatter.format(itemConfiguracaoDTO.getDataFimHistorico()));
		} else {
			str.append("  and au.datahoraalteracao BETWEEN ? and ? ");
			parametro.add(itemConfiguracaoDTO.getDataInicioHistorico());
			parametro.add(transformaHoraFinal(itemConfiguracaoDTO.getDataFimHistorico()));
		}

		str.append(" group by au.idauditoriaitemConfig, it.identificacao, tp.nometipoitemconfiguracao, us.login, it.iditemconfiguracao, au.datahoraalteracao order by au.datahoraalteracao desc, it.iditemconfiguracao, au.tipoalteracao  ");

		List dados =  execSQL(str.toString(), parametro.toArray());
		fields.add("identificacao");
		fields.add("tipoItemConfiguracao");
		fields.add("dataHoraAlteracao");
		fields.add("login");
		fields.add("tipoAlteracao");

		return listConvertion(AuditoriaItemConfigDTO.class, dados, fields) ;
	}

	private Timestamp transformaHoraFinal(Timestamp dataTimestamp) throws ParseException {

		java.sql.Date data = new java.sql.Date(dataTimestamp.getTime());
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}

}
