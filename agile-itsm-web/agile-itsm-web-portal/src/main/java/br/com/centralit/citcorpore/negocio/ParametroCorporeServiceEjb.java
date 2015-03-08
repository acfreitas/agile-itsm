/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.integracao.ParametroCorporeDAO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author valdoilo.damasceno
 *
 */
@SuppressWarnings("unchecked")
public class ParametroCorporeServiceEjb extends CrudServiceImpl implements ParametroCorporeService {

    private ParametroCorporeDAO dao;

    @Override
    protected ParametroCorporeDAO getDao() {
        if (dao == null) {
            dao = new ParametroCorporeDAO();
        }
        return dao;
    }

    @Override
    public List<ParametroCorporeDTO> pesquisarParamentro(final Integer id, final String nomeParametro) throws ServiceException, LogicException, Exception {
        try {
            return this.getDao().pesquisarParamentro(id, nomeParametro);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public ParametroCorporeDTO getParamentroAtivo(final Integer id) throws Exception {
        try {
            return this.getDao().getParamentroAtivo(id);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public void create(final ParametroCorporeDTO parametroBean, final HttpServletRequest request) throws ServiceException, LogicException {
        parametroBean.setIdEmpresa(WebUtil.getIdEmpresa(request));
        parametroBean.setDataInicio(UtilDatas.getDataAtual());

        try {
            super.create(parametroBean);

            if (parametroBean.getId() != null) {
                ParametroUtil.atualizarHashMapParametroCitSmart(parametroBean.getId(), parametroBean.getValor());
            }
        } catch (final LogicException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
	 * 
	 * @see br.com.centralit.citcorpore.negocio.ParametroCorporeService# criarParametrosNovos()
     */
    @Override
    public void criarParametrosNovos() throws Exception {
        final ParametroCorporeDAO dao = this.getDao();
        final TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());

        try {
            tc.start();

            dao.setTransactionControler(tc);

            for (final ParametroSistema parametroCitSmart : ParametroSistema.values()) {
                ParametroCorporeDTO parametro = new ParametroCorporeDTO();
                parametro = dao.getParamentroAtivo(parametroCitSmart.id());
                if (parametro != null) {
                    parametro.setNome(parametroCitSmart.campo());
                    parametro.setTipoDado(parametroCitSmart.tipoCampo());

                    try {
                        dao.updateNotNull(parametro);
                        ParametroUtil.atualizarHashMapParametroCitSmart(parametroCitSmart.id(), parametro.getValor());
                    } catch (final Exception e) {
						System.out.println("ERRO AO ATUALIZAR PARÂMETRO " + parametroCitSmart);
                        e.printStackTrace();
                    }
                } else {
                    parametro = new ParametroCorporeDTO();
                    parametro.setNome(parametroCitSmart.campo());
                    try {
                        parametro.setId(parametroCitSmart.id());
                        parametro.setDataInicio(UtilDatas.getDataAtual());
                        parametro.setIdEmpresa(1);
                        parametro.setTipoDado(parametroCitSmart.tipoCampo());
                        parametro.setValor(" ");
                        dao.create(parametro);
                        ParametroUtil.atualizarHashMapParametroCitSmart(parametroCitSmart.id(), parametro.getValor());
                    } catch (final Exception e) {
						System.out.println("ERRO AO CRIAR PARÂMETRO " + parametroCitSmart);
                        e.printStackTrace();
                    }
                }
            }
            tc.commit();
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public void atualizarParametros(final ParametroCorporeDTO parametroDto) throws Exception {
        try {
            this.getDao().updateNotNull(parametroDto);

            if (parametroDto.getId() != null) {
                ParametroUtil.atualizarHashMapParametroCitSmart(parametroDto.getId(), parametroDto.getValor());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateNotNull(final IDto dto) {
        try {
            this.validaUpdate(dto);
            this.getDao().updateNotNull(dto);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza o valor do parametro pelo id
     *
     */
    @Override
    public void atualizarParametro(final Integer id, final String valor) throws Exception {
        try {
            this.getDao().atualizarParametro(id, valor);
            ParametroUtil.atualizarHashMapParametroCitSmart(id, valor);
        } catch (final Exception e) {
            System.out.println("ERRO AO ATUALIZAR PARAMETRO");
            e.printStackTrace();
        }
    }

}
