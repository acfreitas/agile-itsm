package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class VersaoDao extends CrudDaoDefaultImpl {

	public VersaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public VersaoDTO buscaVersaoPorNome(String nome) throws PersistenceException {
		VersaoDTO versaoDTO = new VersaoDTO();
		versaoDTO.setNomeVersao(nome);
		List col = (List) super.find(versaoDTO, null);
		if (col == null || col.size() == 0)
			return null;
		return (VersaoDTO) col.get(0);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return VersaoDTO.class;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDVERSAO", "idVersao", true, true, false, false));
		listFields.add(new Field("NOMEVERSAO", "nomeVersao", false, false, false, false));
		listFields.add(new Field("IDUSUARIO", "idUsuario", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "VERSAO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeVersao"));
		return super.list(list);
	}

	public boolean haVersoesSemValidacao() throws PersistenceException {
		Collection<VersaoDTO> versoes = list();
		for (VersaoDTO versao : versoes) {
			if (versao.getIdUsuario() == null || versao.getIdUsuario().longValue() == 0) {
				return true;
			}
		}
		return false;
	}

	public VersaoDTO versaoASerValidada() throws PersistenceException {
		VersaoDTO versaoASerValidada = null;
		Collection<VersaoDTO> versoes = list();
		for (VersaoDTO versao : versoes) {
			if (versao.getIdUsuario() == null || versao.getIdUsuario().longValue() == 0) {
				versaoASerValidada = versao;
			}
		}
		return versaoASerValidada;
	}

	public Collection<VersaoDTO> versoesComErrosScripts() throws PersistenceException {
		Collection<VersaoDTO> versoesComErrosScripts = new ArrayList<VersaoDTO>();
		Collection<VersaoDTO> versoes = list();
		for (VersaoDTO versao : versoes) {
			if (versaoPossueErrosScripts(versao)) {
				versoesComErrosScripts.add(versao);
			}
		}
		return versoesComErrosScripts;
	}

	public boolean versaoPossueErrosScripts(VersaoDTO versao) throws PersistenceException {
		String sql = "SELECT scripts.*";
		sql += "      FROM   versao versao";
		sql += "             INNER JOIN scripts scripts";
		sql += "                     ON scripts.idversao = versao.idversao";
		sql += "      WHERE  versao.nomeversao = '" + versao.getNomeVersao() + "'";
		sql += "             AND scripts.descricao LIKE 'ERRO%'";
		List list = this.execSQL(sql, null);
		return (list != null && !list.isEmpty());
	}

}
