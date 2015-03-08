package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author breno.guimaraes
 * 
 */

public class ImagemItemConfiguracaoDao extends CrudDaoDefaultImpl {

	public ImagemItemConfiguracaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public void delete(IDto obj) {
		try {
			super.delete(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.citframework.integracao.CrudDaoDefaultImpl#find(br.com.citframework
	 * .dto.IDto)
	 */
	@Override
	public Collection find(IDto obj) {
		Collection retorno = null;

		try {
			retorno = super.find(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#getFields()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idimagemitemconfiguracao", "idImagemItemConfiguracao", true, true, false, false));
		listFields.add(new Field("idservico", "idServico", false, false, false, false));
		listFields.add(new Field("iditemconfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("posx", "posx", false, false, false, false));
		listFields.add(new Field("posy", "posy", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("idimagemitemconfiguracaopai",	"idImagemItemConfiguracaoPai", false, false, false, false));
		listFields.add(new Field("caminhoimagem", "caminhoImagem", false, false, false, false));
		return listFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#getTableName()
	 */
	@Override
	public String getTableName() {
		return "imagemitemconfiguracao";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#list()
	 */
	@Override
	public Collection list() throws PersistenceException {

		return null;
	}

	@Override
	public IDto create(IDto obj) {
		IDto retorno = null;
		try {
			retorno = super.create(obj);
		} catch (Exception e) {
			System.out.println("Não foi possível cadastrar a imagem."
					+ e.getMessage());
		}

		return retorno;
	};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<ImagemItemConfiguracaoDTO> findByIdItemConfiguracao(Integer id) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", id));
		return super.findByCondition(condicao, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ImagemItemConfiguracaoDTO> findByIdImagemItemConfiguracaoPai(Integer id) {
		Collection retorno = null;
		List condicao = new ArrayList();
		condicao.add(new Condition("idImagemItemConfiguracaoPai", "=", id));
		try {
			retorno = super.findByCondition(condicao, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ImagemItemConfiguracaoDTO> findByIdImagemItemConfiguracao(Integer id) {
		Collection retorno = null;
		ImagemItemConfiguracaoDTO img = new ImagemItemConfiguracaoDTO();
		img.setIdImagemItemConfiguracao(id);
		List condicao = new ArrayList();
		condicao.add(new Condition("idimagemitemconfiguracao", "=", id ));
		try {
			retorno = super.find(img, null);
		} catch (Exception e) {
			System.out.println("Não foi possível buscar imagemItemConfiguracao com id " + id);
			e.printStackTrace();
		}
		return retorno;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ImagemItemConfiguracaoDTO> findByIdServico(Integer id) {
		ImagemItemConfiguracaoDTO img = new ImagemItemConfiguracaoDTO();
		img.setIdServico(id);
		Collection retorno = null;

		List condicao = new ArrayList();
		condicao.add(new Condition("idservico", "=", id.intValue()));
		try {
			// retorno = super.findByCondition(condicao, null);
			retorno = super.find(img, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.DaoTransactDefaultImpl#getBean()
	 */
	@Override
	public Class getBean() {
		return ImagemItemConfiguracaoDTO.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.citframework.integracao.CrudDaoDefaultImpl#update(br.com.citframework
	 * .dto.IDto)
	 */
	@Override
	public void update(IDto obj) {
		try {
			super.update(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluiIdPaiDeItensFilho(int idPai) {
		Object[] parametros = new Object[] { null, idPai };

		String sql = "UPDATE "
				+ getTableName()
				+ " SET idimagemitemconfiguracaopai = ? WHERE idimagemitemconfiguracaopai = ?";
		try {
			execUpdate(sql, parametros);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
