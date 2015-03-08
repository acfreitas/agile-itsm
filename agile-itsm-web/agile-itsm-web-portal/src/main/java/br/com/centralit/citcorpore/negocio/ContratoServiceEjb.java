package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.integracao.ClienteDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ContratoServiceEjb extends CrudServiceImpl implements ContratoService {

    private ContratoDao dao;

    @Override
    protected ContratoDao getDao() {
        if (dao == null) {
            dao = new ContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCliente(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCliente(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdFornecedor(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFornecedor(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCliente(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCliente(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ComplexidadeDTO> listaComplexidadePorContrato(final Integer idServicoContrato) throws Exception {
        return this.getDao().listaComplexidadePorContrato(idServicoContrato);
    }

    @Override
    public Collection listByIdAcordoNivelServicoAndTipo(final Integer idAcordoNivelServicoParm, final String tipoParm) throws Exception {
        return this.getDao().listByIdAcordoNivelServicoAndTipo(idAcordoNivelServicoParm, tipoParm);
    }

    @Override
    public Collection<ContratoDTO> listAtivos() throws Exception {
        return this.getDao().listAtivos();
    }

    @Override
    public Collection findByIdGrupo(final Integer idGrupo) throws Exception {
        return this.getDao().findByIdGrupo(idGrupo);
    }

    @Override
    public Collection<ContratoDTO> findAtivosByGrupos(final Collection<GrupoDTO> listGrupoDto) throws Exception {
        return this.getDao().findAtivosByGrupos(listGrupoDto);
    }

    @Override
    public Collection<ContratoDTO> findAtivosByIdEmpregado(final Integer idEmpregado) throws ServiceException, Exception {
        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

        try {
            final Collection<GrupoDTO> listGrupoDto = grupoService.getGruposByPessoa(idEmpregado);

            return this.getDao().findAtivosByGrupos(listGrupoDto);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String verificaIdCliente(final HashMap mapFields) throws Exception {
        final ClienteDao clienteDao = new ClienteDao();
        List<ClienteDTO> listaCliente = null;
        String id = mapFields.get("IDCLIENTE").toString().trim();
        if (id == null || id.equals("")) {
            id = "0";
        }
        if (UtilStrings.soContemNumeros(id)) {
            Integer idCliente;
            idCliente = Integer.parseInt(id);
            listaCliente = clienteDao.findByIdCliente(idCliente);
        } else {
            listaCliente = clienteDao.findByRazaoSocial(id);
        }
        if (listaCliente != null && listaCliente.size() > 0) {
            return String.valueOf(listaCliente.get(0).getIdCliente());
        } else {
            return "0";
        }
    }

    @Override
    public String verificaIdFornecedor(final HashMap mapFields) throws Exception {
        final FornecedorDao fornecedorDao = new FornecedorDao();
        List<FornecedorDTO> listaFornecedor = null;
        String id = mapFields.get("IDFORNECEDOR").toString().trim();
        if (id == null || id.equals("")) {
            id = "0";
        }
        if (UtilStrings.soContemNumeros(id)) {
            final Integer idFornecedor = Integer.parseInt(id);
            listaFornecedor = fornecedorDao.findByIdFornecedor(idFornecedor);
        } else {
            listaFornecedor = fornecedorDao.findByRazaoSocial(id);
        }
        if (listaFornecedor != null && listaFornecedor.size() > 0) {
            return String.valueOf(listaFornecedor.get(0).getIdFornecedor());
        } else {
            return "0";
        }
    }

    @Override
    public Collection<ContratoDTO> listAtivosWithNomeRazaoSocialCliente() throws ServiceException, Exception {
        return this.getDao().listAtivosWithNomeRazaoSocialCliente();
    }

}
