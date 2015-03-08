package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.citframework.service.CrudServiceImpl;

public class IntegranteViagemServiceEjb extends CrudServiceImpl implements IntegranteViagemService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414511296205878674L;

	@Override
	protected IntegranteViagemDao getDao() {
		return new IntegranteViagemDao();
	}		

	@Override
	protected void validaCreate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO findById(Integer idIntegranteViagem) throws Exception{
		return this.getDao().findById(idIntegranteViagem);
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public void atualizarIdItemTrabalho(Integer idTarefa, Integer idSolicitacaoServico){
		this.getDao().atualizarIdItemTrabalho(idTarefa, idSolicitacaoServico);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public void atualizarRemarcacaoDoIntegrante(Integer idIntegrante){
		this.getDao().atualizarRemarcacaoDoIntegrante(idIntegrante);
	}
	
	public Collection<IntegranteViagemDTO> findAllRemarcacaoByIdSolicitacao(Integer idSolicitacaoServico){
		
		try {
			return this.getDao().findAllRemarcacaoByIdSolicitacao(idSolicitacaoServico);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO recuperaIntegranteFuncionario(Integer idsolicitacaoServico,Integer idEmpregado) throws Exception{
		
		return this.getDao().recuperaIntegrante(idsolicitacaoServico, idEmpregado);
		
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO recuperaIntegranteNaoFuncionario(Integer idsolicitacaoServico, String nomeNaoFuncionario) throws Exception{
		
		return this.getDao().recuperaIntegranteNaoFuncionario(idsolicitacaoServico, nomeNaoFuncionario);
		
	}
	
	@Override
	public Collection<IntegranteViagemDTO> recuperaIntegrantesRemarcacao(IntegranteViagemDTO integranteViagemDTO, String eOu) throws Exception{
		IntegranteViagemDao integranteViagemDao = this.getDao();
		Collection<IntegranteViagemDTO> colIntegrantes = new ArrayList<IntegranteViagemDTO>();
		
		colIntegrantes = integranteViagemDao.findAllIntegrantesParaRemarcacao(integranteViagemDTO, eOu);
		
		return colIntegrantes;
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	@Override
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByCompras(Integer idSolicitacaoServico) throws Exception {
		IntegranteViagemDao integranteViagemDao = this.getDao();
		Collection<IntegranteViagemDTO> ColIntegrantes =  integranteViagemDao.recuperaIntegrantesViagemByCompras(idSolicitacaoServico);
		return ColIntegrantes;
	}
	
	@Override
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByIdSolicitacao(Integer idSolicitacaoServico) throws Exception {
		IntegranteViagemDao integranteViagemDao = this.getDao();
		Collection<IntegranteViagemDTO> ColIntegrantes =  integranteViagemDao.findAllByIdSolicitacao(idSolicitacaoServico);
		return ColIntegrantes;
	}
	
	@Override
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByIdSolicitacaoEstado(Integer idSolicitacao, String estado) throws Exception {
		IntegranteViagemDao dao = (IntegranteViagemDao) this.getDao();
		return dao.recuperaIntegrantesViagemByIdSolicitacaoEstado(idSolicitacao, estado);
	}
	
	@Override
	public IntegranteViagemDTO getIntegranteByIdSolicitacaoAndTarefa(Integer idsolicitacaoServico, Integer idTarefa) throws Exception {
		IntegranteViagemDao dao = (IntegranteViagemDao) this.getDao();
		return dao.getIntegranteByIdSolicitacaoAndTarefa(idsolicitacaoServico, idTarefa);
	}
}