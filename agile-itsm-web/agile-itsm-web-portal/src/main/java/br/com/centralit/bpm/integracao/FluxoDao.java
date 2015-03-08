package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;

public class FluxoDao extends CrudDaoDefaultImpl {

	private static final String TABLE_NAME = "bpm_fluxo";

	private static final String SQL_RESTORE = "SELECT f.idFluxo, t.nomeFluxo, t.descricao, t.idTipoFluxo, t.nomeClasseFluxo, f.variaveis, f.versao, f.conteudoXml, f.dataInicio, f.dataFim, t.idProcessoNegocio FROM Bpm_Fluxo f INNER JOIN Bpm_TipoFluxo t ON t.idTipoFluxo = f.idTipoFluxo ";

	private ElementoFluxoDao elementoFluxoDao;

	private SequenciaFluxoDao sequenciaFluxoDao;

	public FluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Class<FluxoDTO> getBean() {
		return FluxoDTO.class;
	}

	private List<String> getListaDeCampos() {
		final List<String> listRetorno = new ArrayList<>();
		listRetorno.add("idFluxo");
		listRetorno.add("nomeFluxo");
		listRetorno.add("descricao");
		listRetorno.add("idTipoFluxo");
		listRetorno.add("nomeClasseFluxo");
		listRetorno.add("variaveis");
		listRetorno.add("versao");
		listRetorno.add("conteudoXml");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		listRetorno.add("idProcessoNegocio");
		return listRetorno;
	}

	private List<FluxoDTO> recuperaEstrutura(final List<FluxoDTO> list) throws PersistenceException {
		if (list != null) {
			final ElementoFluxoDao elementoDao = new ElementoFluxoDao();
			final ElementoFluxoInicioDao fluxoInicioDao = new ElementoFluxoInicioDao();
			final ElementoFluxoFinalizacaoDao fluxoFinalizacaoDao = new ElementoFluxoFinalizacaoDao();
			final ElementoFluxoTarefaDao fluxoTarefaDao = new ElementoFluxoTarefaDao();
			final ElementoFluxoPortaDao fluxoPortaDao = new ElementoFluxoPortaDao();
			final ElementoFluxoScriptDao fluxoScriptDao = new ElementoFluxoScriptDao();
			final ElementoFluxoEventoDao fluxoEventoDao = new ElementoFluxoEventoDao();
			final SequenciaFluxoDao fluxoSequenciaDao = new SequenciaFluxoDao();

			final TransactionControler tc = this.getTransactionControler();
			fluxoInicioDao.setTransactionControler(tc);
			fluxoFinalizacaoDao.setTransactionControler(tc);
			fluxoTarefaDao.setTransactionControler(tc);
			fluxoPortaDao.setTransactionControler(tc);
			fluxoScriptDao.setTransactionControler(tc);
			fluxoSequenciaDao.setTransactionControler(tc);
			fluxoEventoDao.setTransactionControler(tc);
			elementoDao.setTransactionControler(tc);

			for (final FluxoDTO fluxoDto : list) {
				String id = fluxoDto.getNomeFluxo().trim();
				if (fluxoDto.getVersao() != null) {
					id += "_v" + fluxoDto.getVersao();
				}
				fluxoDto.setIdentificador(id);

				final Integer idFluxo = fluxoDto.getIdFluxo();
				fluxoDto.setInicioFluxo(fluxoInicioDao.restoreByIdFluxo(idFluxo));
				fluxoDto.setColTarefas(fluxoTarefaDao.findByIdFluxo(idFluxo));
				fluxoDto.setColScripts(fluxoScriptDao.findByIdFluxo(idFluxo));
				fluxoDto.setColPortas(fluxoPortaDao.findByIdFluxo(idFluxo));
				fluxoDto.setColEventos(fluxoEventoDao.findByIdFluxo(idFluxo));
				fluxoDto.setColFinalizacoes(fluxoFinalizacaoDao.findByIdFluxo(idFluxo));
				fluxoDto.setColSequenciamentos(fluxoSequenciaDao.findByIdFluxo(idFluxo));
				fluxoDto.setColElementos(elementoDao.findAllByIdFluxo(idFluxo));
			}
		}
		return list;
	}

	@Override
	public Collection<Field> getFields() {
		final Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFluxo", "idFluxo", true, true, false, false));
		listFields.add(new Field("idTipoFluxo", "idTipoFluxo", false, false, false, false));
		listFields.add(new Field("versao", "versao", false, false, false, false));
		listFields.add(new Field("variaveis", "variaveis", false, false, false, false));
		listFields.add(new Field("conteudoXml", "conteudoXml", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Collection<FluxoDTO> find(final IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection list() throws PersistenceException {
		final String sql = SQL_RESTORE + " WHERE f.dataFim IS NULL  ORDER BY t.nomeFluxo, f.idFluxo";
		final List<?> lista = this.execSQL(sql, null);

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//return this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
	}

	public Collection listAll() throws PersistenceException {
		final String sql = SQL_RESTORE + " ORDER BY t.nomeFluxo, f.idFluxo";

		final List<?> lista = this.execSQL(sql, null);

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//return this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
	}

	@Override
	public IDto restore(final IDto obj) throws PersistenceException {

		List<Integer> parametros = new ArrayList<>();

		final StringBuilder sql = new StringBuilder(SQL_RESTORE + " WHERE f.idFluxo = ? ");

		parametros.add(((FluxoDTO) obj).getIdFluxo());

		final List<?> lista = this.execSQL(sql.toString(), parametros.toArray());

		final List<FluxoDTO> result = engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());

		if (result != null && !result.isEmpty()) {
			/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
			//return this.recuperaEstrutura((FluxoDTO) result.get(0));
			return result.get(0);
		}
		return null;
	}

	public FluxoDTO findByTipoFluxo(final Integer idTipoFluxo) throws PersistenceException {
		final String sql = SQL_RESTORE + " WHERE f.dataFim IS NULL   AND f.idTipoFluxo = ? ";

		final List<?> lista = this.execSQL(sql, new Object[] { idTipoFluxo });

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//final List result = this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		final List result = engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
		if (result != null && !result.isEmpty()) {
			return (FluxoDTO) result.get(0);
		}
		return null;
	}

	public Collection findTodosByTipoFluxo(final Integer idTipoFluxo) throws PersistenceException {
		final String sql = SQL_RESTORE + " WHERE f.idTipoFluxo = ? ";

		final List<?> lista = this.execSQL(sql, new Object[] { idTipoFluxo });

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//return this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());		
	}

	public FluxoDTO findByVersao(final FluxoDTO fluxoDto) throws PersistenceException {
		final String sql = SQL_RESTORE + " WHERE t.nomeFluxo = ?   AND f.versao    = ? ";

		final List lista = this.execSQL(sql, new Object[] { fluxoDto.getNomeFluxo(), fluxoDto.getVersao() });

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//final List result = this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		final List result = engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
		if (result != null && !result.isEmpty()) {
			return (FluxoDTO) result.get(0);
		}
		return null;
	}

	public FluxoDTO findByNome(final String nomeFluxo) throws PersistenceException {
		final String sql = SQL_RESTORE + " WHERE t.nomeFluxo = ?   AND f.dataFim IS NULL ";

		final List<?> lista = this.execSQL(sql, new Object[] { nomeFluxo });

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//final List result = this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		final List result = engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
		if (result != null && !result.isEmpty()) {
			return (FluxoDTO) result.get(0);
		}
		return null;
	}

	@Override
	public void updateNotNull(final IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	public Collection findByIdProcessoNegocio(final Integer parm) throws PersistenceException {
		final String sql = SQL_RESTORE + " WHERE t.idProcessoNegocio = ? ";

		final List<?> lista = this.execSQL(sql, new Object[] { parm });

		/** A recuperação da estrutura não é necessária. Operação Usain Bolt - 27.01.2015 - carlos.santos */
		//return this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
		return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
	}

	/**
	 * Recupera os Elementos do Fluxo (Tarefa, Script, Porta, Evento e Finalização).
	 *
	 * @param fluxoDto
	 * @return FluxoDTO - Fluxo com os Elementos.
	 * @throws PersistenceException
	 * @author valdoilo.damasceno
	 * @since 23.01.2015 - Operação Usain Bolt.
	 */
	private FluxoDTO recuperaEstrutura(final FluxoDTO fluxoDto) throws PersistenceException {

		final TransactionControler tc = this.getTransactionControler();
		this.getElementoFluxoDao().setTransactionControler(tc);

		String id = fluxoDto.getNomeFluxo().trim();

		if (fluxoDto.getVersao() != null) {
			id += "_v" + fluxoDto.getVersao();
		}
		fluxoDto.setIdentificador(id);

		List<ElementoFluxoDTO> listElementoFluxoDto = this.getElementoFluxoDao().findAllByIdFluxo(fluxoDto.getIdFluxo());

		List<ElementoFluxoDTO> listTarefa = new ArrayList<ElementoFluxoDTO>();
		List<ElementoFluxoDTO> listScript = new ArrayList<ElementoFluxoDTO>();
		List<ElementoFluxoDTO> listPorta = new ArrayList<ElementoFluxoDTO>();
		List<ElementoFluxoDTO> listEvento = new ArrayList<ElementoFluxoDTO>();
		List<ElementoFluxoDTO> listFinalizacao = new ArrayList<ElementoFluxoDTO>();

		if (listElementoFluxoDto != null && !listElementoFluxoDto.isEmpty()) {

			for (ElementoFluxoDTO elementoFluxoDto : listElementoFluxoDto) {

				TipoElementoFluxo tipoElementoFluxo = TipoElementoFluxo.valueOf(elementoFluxoDto.getTipoElemento());

				switch (tipoElementoFluxo) {

				case Inicio:
					fluxoDto.setInicioFluxo(elementoFluxoDto);
					break;
				case Tarefa:
					listTarefa.add(elementoFluxoDto);
					break;
				case Script:
					listScript.add(elementoFluxoDto);
					break;
				case Porta:
					listPorta.add(elementoFluxoDto);
					break;
				case Evento:
					listEvento.add(elementoFluxoDto);
					break;
				case Finalizacao:
					listFinalizacao.add(elementoFluxoDto);
					break;
				default:
					break;
				}
			}
		}

		fluxoDto.setColTarefas(listTarefa);
		fluxoDto.setColScripts(listScript);
		fluxoDto.setColPortas(listPorta);
		fluxoDto.setColEventos(listEvento);
		fluxoDto.setColFinalizacoes(listFinalizacao);
		fluxoDto.setColSequenciamentos(this.getSequenciaFluxoDao().findByIdFluxo(fluxoDto.getIdFluxo()));
		fluxoDto.setColElementos(listElementoFluxoDto);

		return fluxoDto;
	}

	/**
	 * Retorna uma instância de ElementoFluxoDao.
	 *
	 * @return ElementoFluxoDao
	 * @author valdoilo.damasceno
	 * @since 23.01.2015 - Uperação Osain Bolt.
	 */
	private ElementoFluxoDao getElementoFluxoDao() {
		if (elementoFluxoDao == null) {
			elementoFluxoDao = new ElementoFluxoDao();
		}
		return elementoFluxoDao;
	}

	private SequenciaFluxoDao getSequenciaFluxoDao() {
		if (sequenciaFluxoDao == null) {
			sequenciaFluxoDao = new SequenciaFluxoDao();
		}
		return sequenciaFluxoDao;
	}
	
	/**
	 * Restore do FluxoDTO com a estrutura.
	 *
	 * @param obj
	 * @return IDto - Fluxo com os Elementos.
	 * @throws PersistenceException
	 * @author carlos.santos
	 * @since 27.01.2015 - Operação Usain Bolt.
	 */
	public IDto restoreComEstrutura(final IDto obj) throws PersistenceException {

		List<Integer> parametros = new ArrayList<>();

		final StringBuilder sql = new StringBuilder(SQL_RESTORE + " WHERE f.idFluxo = ? ");

		parametros.add(((FluxoDTO) obj).getIdFluxo());

		final List<?> lista = this.execSQL(sql.toString(), parametros.toArray());

		List<FluxoDTO> result = engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());

		if (result != null && !result.isEmpty()) {
			result = this.recuperaEstrutura(engine.listConvertion(this.getBean(), lista, this.getListaDeCampos()));
			return result.get(0);
		}else
			return null;
	}
}
