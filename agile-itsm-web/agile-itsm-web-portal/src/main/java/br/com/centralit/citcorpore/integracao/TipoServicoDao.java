package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.ajaxForms.Servico;
import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author leandro.viana
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TipoServicoDao extends CrudDaoDefaultImpl {

	public TipoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return TipoServicoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTipoServico", "idTipoServico", true, true, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("nomeTipoServico", "nomeTipoServico", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		return listFields;
	}

	/**
	 * Obtém o nome da tabela a ser usada no banco de dados.
	 */
	public String getTableName() {
		return "TIPOSERVICO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nomeTipoServico"));
		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		/*
		 * List list = new ArrayList(); list.add(new Order("nomeTipoServico"));
		 *
		 * return super.list(list);
		 */
		Collection colFinal = new ArrayList();
		Collection col = super.list("nomeTipoServico");
		for (Iterator it = col.iterator(); it.hasNext();) {
			TipoServicoDTO tipoServicoDTO = (TipoServicoDTO) it.next();
			if (tipoServicoDTO.getSituacao() == null || tipoServicoDTO.getSituacao().equalsIgnoreCase("A")) {
				colFinal.add(tipoServicoDTO);
			}
		}
		return colFinal;
	}

	/**
	 * Verifica se tipo serviço.
	 *
	 * @param tipoServicoDTO
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeTipoServicoExiste(TipoServicoDTO tipoServicoDTO) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT nomeTipoServico FROM tiposervico ");
		sql.append("WHERE nomeTipoServico LIKE ? AND situacao LIKE ? ");
		parametros.add(tipoServicoDTO.getNomeTipoServico());
		parametros.add("A");

		if (tipoServicoDTO.getIdTipoServico() != null) {
			sql.append("AND idtiposervico <> ?");
			parametros.add(tipoServicoDTO.getIdTipoServico());
		}

		List tiposServico = execSQL(sql.toString(), parametros.toArray());

		if (tiposServico != null && !tiposServico.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorna lista de Tipo Serviço por nome.
	 *
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByNome(TipoServicoDTO tipoServicoDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeTipoServico", "=", tipoServicoDTO.getNomeTipoServico()));
		ordenacao.add(new Order("nomeTipoServico"));
		return super.findByCondition(condicao, ordenacao);
	}


	/**
	 * Metodo responsavel por verificar na base de dados se existe algum servico com o idTipoServico informado.
	 *
	 * @param idTipoServico
	 * @return
	 */
	public Boolean existeVinculadoCadastroServico(Integer idTipoServico) throws PersistenceException {

		final StringBuilder query = new StringBuilder();

		query.append(" select idtiposervico from servico where idtiposervico = " + idTipoServico);

		List servicos = execSQL(query.toString(), new ArrayList().toArray());

		List retornos = new ArrayList();

		retornos.add("idservico");

		List resultado = engine.listConvertion(Servico.class, servicos, retornos);

        return resultado != null && resultado.size() > 0;
	}
}
